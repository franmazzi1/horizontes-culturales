package com.horizontesculturales.backend.dto;

import com.horizontesculturales.backend.model.Contacto;

public record ContactoResponse(
    String email,
    String telefono,
    String whatsapp,
    String instagram,
    String facebook,
    String youtube
) {

    public static ContactoResponse desde(Contacto contacto) {
        return new ContactoResponse(
            contacto.getEmail(),
            contacto.getTelefono(),
            contacto.getWhatsapp(),
            contacto.getInstagram(),
            contacto.getFacebook(),
            contacto.getYoutube()
        );
    }
}