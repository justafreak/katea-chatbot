package ml.strikers.kateaserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class SerializationUtil {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> T read(String input, Class<T> classToConvert) throws IOException {
        return OBJECT_MAPPER.readValue(input, classToConvert);
    }

    public static String write(Object input) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(input);
    }
}
