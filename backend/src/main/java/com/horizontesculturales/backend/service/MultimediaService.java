package com.horizontesculturales.backend.service;

import org.springframework.stereotype.Service;

import com.horizontesculturales.backend.dto.MultimediaRequest;
import com.horizontesculturales.backend.exception.EntradaInvalidaException;
import com.horizontesculturales.backend.exception.RecursoNoEncontradoException;
import com.horizontesculturales.backend.model.Evento;
import com.horizontesculturales.backend.model.Multimedia;
import com.horizontesculturales.backend.model.TipoMultimedia;
import com.horizontesculturales.backend.repository.MultimediaRepository;
import com.horizontesculturales.backend.util.Enlaces;

@Service
public class MultimediaService {

    private final MultimediaRepository multimediaRepository;
    private final EventoService eventoService;

    public MultimediaService(MultimediaRepository multimediaRepository, EventoService eventoService) {
        this.multimediaRepository = multimediaRepository;
        this.eventoService = eventoService;
    }

    public Multimedia agregarAEvento(Long eventoId, MultimediaRequest request) {
        validarEnlace(request);
        Evento evento = eventoService.obtenerPorId(eventoId);

        Multimedia multimedia = new Multimedia(request.url(), request.tipo());
        multimedia.setEvento(evento);
        return multimediaRepository.save(multimedia);
    }

    public void eliminar(Long id) {
        multimediaRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Multimedia", id));
        multimediaRepository.deleteById(id);
    }

    private void validarEnlace(MultimediaRequest request) {
        if (request.tipo() == TipoMultimedia.VIDEO && !Enlaces.esDeVideoPermitido(request.url())) {
            throw new EntradaInvalidaException("url", "Pegá un enlace de YouTube, Vimeo o Instagram");
        }
        if (request.tipo() == TipoMultimedia.IMAGEN && !Enlaces.esHttps(request.url())) {
            throw new EntradaInvalidaException("url", "La dirección de la imagen tiene que empezar con https://");
        }
    }
}
