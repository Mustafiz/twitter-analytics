package edu.miu.bdt.service;

import edu.miu.bdt.repo.TweetRepository;
import edu.miu.bdt.util.Utils;
import lombok.RequiredArgsConstructor;
import org.apache.spark.api.java.JavaPairRDD;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author Mustafizur Rahman Hilaly
 */
@Service
@RequiredArgsConstructor
public class ElasticSearchService {

    private final RestHighLevelClient esRestClient;

    private final TweetRepository tweetRepository;

    public void indexTweet(JavaPairRDD<String, String> rdd) throws IOException {
        if (rdd.count() > 0) {
            rdd.collect().stream()
                    .map(tuple -> Utils.jsonToTweet(tuple._2))
                    .forEach(tweet -> tweet
                            .ifPresent(value -> tweetRepository
                                    .saveAll(value.getTweetsForAllTags())));
        }
    }
}
