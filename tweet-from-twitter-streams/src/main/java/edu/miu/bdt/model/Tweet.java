package edu.miu.bdt.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import edu.miu.bdt.util.Utils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

/**
 * @author Mustafizur Rahman Hilaly
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tweet {
    private long tweetId;
    private String text;
    private Date createdAt;
    private String fromUser;
    private String languageCode;
    private String source;
    private List<HashTagEntity> hashTagEntities;
    private String tag;
    private TwitterProfile user;

    public static Tweet of(org.springframework.social.twitter.api.Tweet tweet) {
        Tweet tweetModel = new Tweet();
        TwitterProfile user = new TwitterProfile();
        BeanUtils.copyProperties(tweet, tweetModel);
        BeanUtils.copyProperties(tweet.getUser(), user);
        tweetModel.setUser(user);
        tweetModel.setTweetId(tweet.getId());
        tweetModel.setHashTagEntities(HashTagEntity.of(tweet));
        tweetModel.setSource(Utils.parseSourceField(tweetModel.getSource()));
        return tweetModel;
    }
}
