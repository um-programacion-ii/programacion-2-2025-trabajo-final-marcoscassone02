package um.edu.ar.backend.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import um.edu.ar.backend.infrastructure.http.dto.AuthRequest;
import um.edu.ar.backend.infrastructure.http.dto.AuthResponse;

@Service
@RequiredArgsConstructor
public class CatedraAuthService {

    private final WebClient catedraClient;

    public String autenticar(String username, String password) {

        var request = new AuthRequest(username, password, false);

        var response = catedraClient.post()
                .uri("/api/authenticate")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AuthResponse.class)
                .block();

        return response.getId_token();
    }
}
