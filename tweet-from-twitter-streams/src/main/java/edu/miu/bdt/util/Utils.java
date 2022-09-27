package edu.miu.bdt.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.bdt.model.Tweet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Optional;

/**
 * @author Mustafizur Rahman Hilaly
 */
public class Utils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static Optional<String> toJsonString(Tweet model) {
        try {
            return Optional.of(mapper.writeValueAsString(model));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static String parseSourceField(String htmlFormattedString) {
        Document doc = Jsoup.parse(htmlFormattedString);
        Element link = doc.select("a").first();
        if (link != null) {
            return link.text();
        }
        return "UNKNOWN";
    }
}
