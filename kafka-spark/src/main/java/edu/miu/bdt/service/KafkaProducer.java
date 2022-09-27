package edu.miu.bdt.service;

import edu.miu.bdt.entity.Tweet;
import edu.miu.bdt.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.api.java.JavaPairRDD;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mustafizur Rahman Hilaly
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(JavaPairRDD<String, String> rdd) {
        if (rdd.count() > 0) {
            rdd.collect().stream()
                    .map(tuple -> Utils.jsonToTweet(tuple._2))
                    .forEach(tweet -> tweet
                            .ifPresent(value -> {
                                List<Tweet> tweetsForAllTags = value.getTweetsForAllTags();
                                for (Tweet t : tweetsForAllTags) {
                                    if (t.getTag() != null) {
                                        String payload = t.toCsvLine();
                                        String topic = "tweet.csv";
                                        log.info("sending payload='{}' to topic='{}'", payload, topic);
                                        kafkaTemplate.send(topic, payload);
                                    }
                                }
                            }));
        }

    }
}
