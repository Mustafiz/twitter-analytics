package edu.miu.bdt.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.bdt.entity.Tweet;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Mustafizur Rahman Hilaly
 */
@Slf4j
public class Utils {
    private static final Pattern HASHTAG_PATTERN = Pattern.compile("#\\w+");
    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<String> hashTagsFromTweet(String text) {
        List<String> hashTags = new ArrayList<>();
        Matcher matcher = HASHTAG_PATTERN.matcher(text);
        while (matcher.find()) {
            String handle = matcher.group();
            if (handle.startsWith("#"))
                hashTags.add(handle.substring(1));
        }
        return hashTags;
    }

    public static Optional<Tweet> jsonToTweet(String jsonString) {
        try {
            return Optional.of(mapper.readValue(jsonString, Tweet.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static String toDateStringFromDate(Date date, DateFormat dateFormat) {
        if (date == null) return null;
        try {
            SimpleDateFormat fmt = new SimpleDateFormat(dateFormat.getValue());
            return fmt.format(date);
        } catch (Exception ex) {
            log.debug("ERROR PARSING DATE", ex);
        }
        return null;
    }
}
