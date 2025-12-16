package um.edu.ar.proxy.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import um.edu.ar.proxy.application.service.NotificacionEventosService;

@Component
@RequiredArgsConstructor
public class EventosKafkaListener {

    private final NotificacionEventosService notificacionEventosService;

    @KafkaListener(
            topics = "eventos-actualizacion",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void onEventoActualizado(String mensaje) {
        notificacionEventosService.manejarMensajeEventos(mensaje);
    }
}
