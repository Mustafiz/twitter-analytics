package edu.miu.bdt.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.social.twitter.api.Tweet;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Mustafizur Rahman Hilaly
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HashTagEntity {
    private String text;
    private int[] indices;

    static List<HashTagEntity> of(Tweet tweet) {
        List<HashTagEntity> hashTagEntities = new ArrayList<>();
        Function<org.springframework.social.twitter.api.HashTagEntity, HashTagEntity> copyToHashtag = htag -> {
            HashTagEntity hashTagEntity = new HashTagEntity();
            BeanUtils.copyProperties(htag, hashTagEntity);
            return hashTagEntity;
        };
        return tweet.getEntities().getHashTags()
                .stream()
                .map(copyToHashtag)
                .collect(Collectors.toList());
    }
}
