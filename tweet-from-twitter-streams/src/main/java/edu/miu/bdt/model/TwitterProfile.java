package edu.miu.bdt.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Mustafizur Rahman Hilaly
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TwitterProfile {
    private long id;
    private String screenName;
    private String name;
    private String url;
    private String profileImageUrl;
    private String description;
    private String location;
    private Date createdDate;
    private String language;
    private int statusesCount;
    private int friendsCount;
    private int followersCount;
    private int favoritesCount;
    private int listedCount;
    private String timeZone;
}
