package edu.miu.bdt.service;

import edu.miu.bdt.config.KafkaConsumerConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.util.Collection;

/**
 * @author Mustafizur Rahman Hilaly
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaSparkService {

    private final SparkConf sparkConf;
    private final KafkaConsumerConfig kafkaConsumerConfig;
    private final ElasticSearchService esService;
    private final KafkaProducer kafkaProducer;

    @Value("${spring.kafka.template.default-topic}")
    private Collection<String> topics;

    public void sync() throws InterruptedException {
        log.debug("Running Spark Consumer Service..");

        // Create context with a 5 seconds batch interval
        JavaStreamingContext javaStreamingContext = new JavaStreamingContext(sparkConf, Durations.seconds(5));

        // create connection to kafka
        JavaInputDStream<ConsumerRecord<String, String>> messages = KafkaUtils.createDirectStream(
                javaStreamingContext,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.Subscribe(topics, kafkaConsumerConfig.consumerConfigs()));

        // get kafka message and process it
        JavaPairDStream<String, String> tweets = messages
                .mapToPair(record -> new Tuple2<>(record.key(), record.value()));

        tweets.foreachRDD(esService::indexTweet);
        //tweets.foreachRDD(kafkaProducer::send);

        // Start the computation
        javaStreamingContext.start();
        javaStreamingContext.awaitTermination();
    }
}