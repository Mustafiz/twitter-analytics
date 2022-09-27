package edu.miu.bdt;

import edu.miu.bdt.service.StreamingService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class TwitterStreamsToKafkaApplication implements CommandLineRunner {

    private final StreamingService tweetEventService;

    public static void main(String[] args) {
        SpringApplication.run(TwitterStreamsToKafkaApplication.class, args);
    }

    @Override
    public void run(String... args) throws InterruptedException {
        tweetEventService.run();
        Thread.sleep(5000);
    }
}
