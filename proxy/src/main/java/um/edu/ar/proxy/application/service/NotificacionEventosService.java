package um.edu.ar.proxy.application.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import um.edu.ar.proxy.infrastructure.http.BackendEventsClient;

@Service
@RequiredArgsConstructor
public class NotificacionEventosService {

    private final BackendEventsClient backendEventsClient;

    public void manejarMensajeEventos(String mensajeKafka) {
        System.out.println("[Kafka→Proxy] Mensaje recibido en eventos-actualizacion: " + mensajeKafka);

        backendEventsClient.notificarSincronizacionEventos();
    }
}
