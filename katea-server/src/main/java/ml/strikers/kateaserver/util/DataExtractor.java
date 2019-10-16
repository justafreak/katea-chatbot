package ml.strikers.kateaserver.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class DataExtractor {

    private final ObjectMapper objectMapper;


    public DataExtractor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> List<T> extract(Resource resource, Class<T[]> clazz) {
        try {
            return Arrays.asList(objectMapper.readValue(resource.getInputStream(), clazz));
        } catch (IOException e) {
            log.error("Error occurred at deserialization!");
        }
        return Collections.emptyList();
    }
}
