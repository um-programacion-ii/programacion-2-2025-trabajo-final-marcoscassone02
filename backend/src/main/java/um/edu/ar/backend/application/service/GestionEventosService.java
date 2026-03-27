package um.edu.ar.backend.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import um.edu.ar.backend.domain.model.Evento;
import um.edu.ar.backend.domain.ports.in.GestionEventosUseCase;
import um.edu.ar.backend.domain.ports.out.EventoRepository;

import um.edu.ar.backend.domain.ports.out.EventosRemotosPort;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GestionEventosService implements GestionEventosUseCase {

    private final EventoRepository eventoRepository;
    private final EventosRemotosPort eventosRemotosPort;

    @Override
    public List<Evento> listarEventos() {
        List<Evento> eventosRemotos = eventosRemotosPort.obtenerEventosDesdeServidorCatedra();
        eventoRepository.saveAll(eventosRemotos);
        return eventoRepository.findAll();
    }

    @Override
    public Optional<Evento> obtenerEventoPorId(Long id) {
        return eventoRepository.findById(id);
    }
}
