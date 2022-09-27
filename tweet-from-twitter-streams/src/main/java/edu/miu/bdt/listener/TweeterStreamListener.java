package edu.miu.bdt.listener;

import edu.miu.bdt.service.KafkaProducer;
import edu.miu.bdt.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.twitter.api.StreamDeleteEvent;
import org.springframework.social.twitter.api.StreamListener;
import org.springframework.social.twitter.api.StreamWarningEvent;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Service;

/**
 * @author Mustafizur Rahman Hilaly
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TweeterStreamListener implements StreamListener {

    @Value(value = "${spring.kafka.template.default-topic}")
    private String kafkaTopic;

    private final KafkaProducer kafkaProducer;

    @Override
    public void onTweet(Tweet tweet) {
        String lang = tweet.getLanguageCode();

        log.debug("Tweet Language [{}]", lang);
        log.debug("Tweet Text [{}]", tweet.getText());
        log.debug("Tweet User [{}]", tweet.getUser());
        log.debug("Tweet HashTags [{}]", tweet.hasTags());

        // get only english tweets
        if (!"en".equals(lang)) {
            return;
        }

        edu.miu.bdt.model.Tweet model = edu.miu.bdt.model.Tweet.of(tweet);
        Utils.toJsonString(model).ifPresent(json -> {
            log.debug("Tweet Json: [{}]", json);
            kafkaProducer.send(kafkaTopic, json);
        });
    }

    @Override
    public void onDelete(StreamDeleteEvent deleteEvent) {
        log.debug("onDelete");
    }

    @Override
    public void onLimit(int numberOfLimitedTweets) {
        log.debug("onLimit");
    }

    @Override
    public void onWarning(StreamWarningEvent warningEvent) {
        log.debug("onLimit");
    }
}
