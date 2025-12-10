package um.edu.ar.backend.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import um.edu.ar.backend.domain.model.Evento;
import um.edu.ar.backend.domain.ports.out.EventoRepository;
import um.edu.ar.backend.domain.ports.out.EventosRemotosPort;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SincronizarEventosService {

    private final EventosRemotosPort eventosRemotosPort;
    private final EventoRepository eventoRepository;

    @Transactional
    public void sincronizarDesdeCatedra() {

        List<Evento> eventosRemotos = eventosRemotosPort.obtenerEventosDesdeServidorCatedra();
        System.out.println("[Backend] Eventos remotos recibidos = " + eventosRemotos.size());
        eventoRepository.saveAll(eventosRemotos);
        System.out.println("[Backend] Sincronización de eventos completada (saveAll).");
    }
}