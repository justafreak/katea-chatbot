package org.contest.scrapper;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class MetadataScrapper {
    private static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        DefaultPrettyPrinter prettyPrinter = new DefaultPrettyPrinter();
        prettyPrinter.indentArraysWith(DefaultPrettyPrinter.Lf2SpacesIndenter.instance);

        InputStream input = ReviewsScrapper.class.getResourceAsStream("/input.json");
        List<Map<String, Object>> hotels = mapper.readValue(input, List.class);

        FileWriter writer = new FileWriter("/Users/nicolae.popa/katea-chatbot/scraper/src/main/resources/metadata.json");
        writer.append("[\n");
        hotels.stream().map(MetadataScrapper::mapNewObject)
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

    }

    private static Map<String, Object> mapNewObject(Map<String, Object> map) {
        try {

            Document doc = Jsoup.connect(map.get("url").toString()).get();
//            Document doc = Jsoup.connect("https://www.booking.com/hotel/fr/etap-paris-porte-de-montmartre.en-gb.html").get();
            Map<String, Object> newMap = new LinkedHashMap<>(map);
            newMap.put("venueType", getVenueType(doc));
            newMap.put("zone", getZone(doc));
            newMap.put("reviewCount", getReviewCount(doc));
            newMap.put("facilities", getFacilities(doc));
            newMap.put("lat-long", getLatLong(doc));
            return newMap;
        } catch (IOException e) {
            return new HashMap<>();
        }
    }

    private static String getVenueType(Document doc) {
        try {
            return doc.getElementsByClass("hp__hotel-type-badge").get(0).childNode(0).toString();
        } catch (Exception e) {
            return "Hotel";
        }
    }

    private static String getZone(Document doc) {
        String address = doc.getElementsByClass("hp_address_subtitle").get(0).childNode(0).toString();
        int firstCommaIndex = address.indexOf(",");
        String fromFirstComma = address.substring(firstCommaIndex + 1);
        int secondCommaIndex = fromFirstComma.indexOf(",");
        return fromFirstComma.substring(0, secondCommaIndex).trim();
    }

    private static int getReviewCount(Document doc) {
        try {
            String reviewValue = doc.getElementById("show_reviews_tab").childNodes().get(1).childNodes().get(2).toString();
            String reviewAsString = reviewValue.replace("(", "").replace(")", "").replace(",", "").trim();
            return Integer.parseInt(reviewAsString);
        } catch (Exception e) {
            return 123;
        }
    }

    private static List<String> getFacilities(Document doc) {
        Set<String> facilities = doc.getElementsByClass("important_facility").stream().map(e -> e.childNode(2).toString().trim()).collect(Collectors.toSet());
        return new ArrayList<>(facilities);
    }

    private static String getLatLong(Document doc) {
        return doc.getElementById("hotel_header").attr("data-atlas-latlng");
    }
}
