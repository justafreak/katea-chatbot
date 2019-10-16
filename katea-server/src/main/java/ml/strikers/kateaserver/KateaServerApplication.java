package ml.strikers.kateaserver;

import com.google.api.client.util.Maps;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.*;
import org.apache.http.client.CredentialsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;


@SpringBootApplication
public class KateaServerApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(KateaServerApplication.class, args);

    }


}
