package um.edu.ar.backend.infrastructure.http.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient catedraWebClient() {
        return WebClient.builder()
                .baseUrl("http://192.168.194.250:8080")   // servidor real
                .build();
    }
}
