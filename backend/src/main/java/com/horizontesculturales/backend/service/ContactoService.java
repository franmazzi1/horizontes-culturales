package com.horizontesculturales.backend.service;

import org.springframework.stereotype.Service;

import com.horizontesculturales.backend.dto.ContactoRequest;
import com.horizontesculturales.backend.model.Contacto;
import com.horizontesculturales.backend.repository.ContactoRepository;

@Service
public class ContactoService {

    private final ContactoRepository contactoRepository;

    public ContactoService(ContactoRepository contactoRepository) {
        this.contactoRepository = contactoRepository;
    }

    public Contacto obtener() {
        return contactoRepository.findById(Contacto.ID_UNICO).orElseGet(Contacto::new);
    }

    public Contacto actualizar(ContactoRequest request) {
        Contacto contacto = obtener();
        contacto.setEmail(request.email());
        contacto.setTelefono(request.telefono());
        contacto.setWhatsapp(request.whatsapp());
        contacto.setInstagram(request.instagram());
        contacto.setFacebook(request.facebook());
        contacto.setYoutube(request.youtube());
        return contactoRepository.save(contacto);
    }
}