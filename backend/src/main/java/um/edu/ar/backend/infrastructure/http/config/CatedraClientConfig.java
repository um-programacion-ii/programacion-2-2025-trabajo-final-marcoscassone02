package um.edu.ar.backend.infrastructure.http.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ConfigurationProperties(prefix = "catedra")
@Data
public class CatedraClientConfig {

    private String baseUrl;
    private String authToken;

    @Bean
    public WebClient catedraWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + authToken)
                .build();
    }
}
