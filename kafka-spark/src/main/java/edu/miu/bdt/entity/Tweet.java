package edu.miu.bdt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.miu.bdt.util.Utils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.hadoop.classification.InterfaceAudience;
import org.springframework.beans.BeanUtils;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.persistence.Transient;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mustafizur Rahman Hilaly
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "tweets")
public class Tweet extends ElasticEntityCommon {
    private long tweetId;
    private String text;
    private Date createdAt;
    private String fromUser;
    private String languageCode;
    private String source;
    private List<HashTagEntity> hashTagEntities;
    private String tag;
    private TwitterProfile user;

    public List<Tweet> getTweetsForAllTags() {
        List<String> tags = this.getHashTagEntities().stream()
                .map(HashTagEntity::getText)
                .filter(tag -> tag.startsWith("#"))
                .map(tag -> tag.substring(1))
                .collect(Collectors.toList());

        this.hashTagEntities = null;

        Utils.hashTagsFromTweet(this.getText())
                .stream()
                .filter(tag -> !tags.contains(tag))
                .forEach(tags::add);

        return tags.stream()
                .map(tag -> Tweet.of(this, tag))
                .collect(Collectors.toList());
    }

    public static Tweet of(Tweet tweet, String tag) {
        Tweet t = new Tweet();
        BeanUtils.copyProperties(tweet, t);
        HTMLEditorKit.Parser parser = new ParserDelegator();
        t.setTag(tag);
        return t;
    }

    public String toCsvLine() {
        return "@" + this.getUser().getScreenName() + ",#" + this.getTag() + "," + this.getCreatedAt();
    }
}
