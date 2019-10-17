package ml.strikers.kateaserver.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;

import static java.util.Objects.requireNonNull;

@Slf4j
@Configuration
public class DataStorageConfig {

    @Value("${datastore.credentials.location}")
    private Resource resourceFile;

    @Value("${datastore.project-id}")
    private String projectId;


    @Bean
    public Datastore datastore() {
        GoogleCredentials googleCredentials = null;
        try {
            googleCredentials = GoogleCredentials.fromStream(resourceFile.getInputStream());
        } catch (IOException e) {
            log.error("Can not get the DataStore Credentials!");
        }
        return DatastoreOptions.newBuilder()
                .setProjectId(projectId)
                .setCredentials(requireNonNull(googleCredentials))
                .build()
                .getService();
    }
}
