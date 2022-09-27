package edu.miu.bdt.config;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mustafizur Rahman Hilaly
 */
@Configuration
public class SparkConfig {

    @Bean
    public SparkConf sparkConf() {
        return new SparkConf()
                .setAppName("Tweet Sync")
                .setMaster("local")
                .set("spark.executor.memory","1g");
    }
}