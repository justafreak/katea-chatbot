package ml.strikers.kateaserver.service.auth;

import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GoogleAuthentication {

   public GoogleCredentials getGoogleCredentials(Resource resource){
        try {
            return GoogleCredentials.fromStream(resource.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
