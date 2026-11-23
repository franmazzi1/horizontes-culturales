package com.horizontesculturales.backend.dto;

import com.horizontesculturales.backend.util.Textos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ContactoRequest(

    @Email(message = "El email no es válido")
    @Size(max = 150, message = "El email no puede superar los 150 caracteres")
    String email,

    @Pattern(regexp = "^[0-9+()\\-\\s]{6,50}$",
             message = "El teléfono solo puede tener números, espacios, + y guiones")
    String telefono,

    @Pattern(regexp = "^[0-9+()\\-\\s]{6,50}$",
             message = "El WhatsApp solo puede tener números, espacios, + y guiones")
    String whatsapp,

    @Size(max = 255, message = "El enlace de Instagram no puede superar los 255 caracteres")
    @Pattern(regexp = "^https://(www\\.|m\\.)?instagram\\.com(/.*)?$",
             flags = Pattern.Flag.CASE_INSENSITIVE,
             message = "Pegá el enlace completo de Instagram (https://instagram.com/...)")
    String instagram,

    @Size(max = 255, message = "El enlace de Facebook no puede superar los 255 caracteres")
    @Pattern(regexp = "^https://(www\\.|m\\.)?(facebook\\.com|fb\\.com|fb\\.me)(/.*)?$",
             flags = Pattern.Flag.CASE_INSENSITIVE,
             message = "Pegá el enlace completo de Facebook (https://facebook.com/...)")
    String facebook,

    @Size(max = 255, message = "El enlace de YouTube no puede superar los 255 caracteres")
    @Pattern(regexp = "^https://(www\\.|m\\.)?(youtube\\.com|youtu\\.be)(/.*)?$",
             flags = Pattern.Flag.CASE_INSENSITIVE,
             message = "Pegá el enlace completo de YouTube (https://youtube.com/...)")
    String youtube
) {

    public ContactoRequest {
        email = Textos.vacioANull(email);
        telefono = Textos.vacioANull(telefono);
        whatsapp = Textos.vacioANull(whatsapp);
        instagram = Textos.vacioANull(instagram);
        facebook = Textos.vacioANull(facebook);
        youtube = Textos.vacioANull(youtube);
    }
}