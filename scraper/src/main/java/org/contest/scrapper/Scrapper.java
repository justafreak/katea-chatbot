package org.contest.scrapper;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class Scrapper {
    private static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
        prettyPrinter.indentArraysWith(DefaultPrettyPrinter.Lf2SpacesIndenter.instance);

        InputStream input = Scrapper.class.getResourceAsStream("/input.json");
        List<Map<String, Object>> hotels = mapper.readValue(input, List.class);

        FileWriter writer = new FileWriter("/Users/nicolae.popa/code/scrapper/src/main/resources/reviews.json");
        writer.append("[\n");

        hotels.stream().map(map -> mapNewObject(map.get("id"), map.get("url")))
            .forEach(m -> {
                try {
                    String s = mapper.writer(prettyPrinter).writeValueAsString(m);
                    writer.append(s);
                    writer.append(",\n");
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        writer.append("\n]");
        writer.flush();

        System.out.println("tada");
    }

    private static Map<String, Object> mapNewObject(Object id, Object url) {
        Map<String, Object> newMap = new LinkedHashMap<>();
        newMap.put("id", id);
        newMap.put("url", url);
        newMap.put("reviews", bookingReviews(url.toString()));
        return newMap;
    }

    private static List<String> bookingReviews(String uri) {
        try {
            Document doc = Jsoup.connect(uri).get();
            Elements elementsByClass = doc.getElementsByClass("trackit");

            List<String> langs = Arrays.asList("xu", "en");

            return elementsByClass.stream().filter(f -> langs.contains(f.attr("lang")))
                .map(f -> filterReview(f.childNodes().get(1).childNodes().get(0).toString())).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException(uri);
        }
    }

    private static String filterReview(String review) {
        if (review.length() > 120) {
            int pointIndex = review.indexOf('.');
            return review.substring(0, pointIndex == -1 ? 120 : pointIndex);
        } else {
            return review;
        }
    }

    private static void airbnb() throws IOException {
        Document doc = Jsoup.connect("https://www.airbnb.com/s/London--United-Kingdom/homes?refinement_paths%5B%5D=%2Fhomes&source=structured_search_input_header&search_type=autocomplete_click&screen_size=large&hide_dates_and_guests_filters=true&checkin=2019-11-12&checkout=2019-11-16&query=London%2C%20United%20Kingdom&place_id=ChIJdd4hrwug2EcRmSrV3Vo6llI").get();
//        Document doc = Jsoup.connect("https://www.airbnb.com/s/Paris--France/homes?query=Paris%2C%20France&place_id=ChIJD7fiBh9u5kcRYJSMaMOCCwQ&checkin=2019-11-12&checkout=2019-11-16&refinement_paths%5B%5D=%2Fhomes&source=structured_search_input_header&search_type=unknown").get();
        Element elementById = doc.getElementById("data-state");
        String json = elementById.dataNodes().get(0).getWholeData();
        Map<String, Object> map = mapper.readValue(json, Map.class);
        Map<String, Object> response = (Map) ((Map) (((Map) (((Map) map.get("bootstrapData")).get("reduxData"))).get("exploreTab"))).get("response");
        List<Map<String, Object>> listings = (List) ((Map) (((List) (((Map) ((List) response.get("explore_tabs")).get(0)).get("sections"))).get(1))).get("listings");

        System.out.println("test");
    }
}
