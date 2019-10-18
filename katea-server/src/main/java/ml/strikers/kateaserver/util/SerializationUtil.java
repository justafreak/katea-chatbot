package ml.strikers.kateaserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ml.strikers.kateaserver.exception.DeserializationException;
import ml.strikers.kateaserver.exception.SerializationException;

import java.io.IOException;

@UtilityClass
@Slf4j
public class SerializationUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> T read(String input, Class<T> classToConvert) {
        try {
            return OBJECT_MAPPER.readValue(input, classToConvert);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new DeserializationException(e.getMessage());
        }
    }

    public static String write(Object input) {
        try {
            return OBJECT_MAPPER.writeValueAsString(input);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new SerializationException(e.getMessage());
        }
    }
}
