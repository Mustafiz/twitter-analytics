package edu.miu.bdt.repo;

import edu.miu.bdt.entity.Tweet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Mustafizur Rahman Hilaly
 */
public interface TweetRepository extends ElasticsearchRepository<Tweet, String> {
}
