package com.horizontesculturales.backend.dto;

import com.horizontesculturales.backend.model.Multimedia;
import com.horizontesculturales.backend.model.TipoMultimedia;

public record MultimediaResponse(
    Long id,
    String url,
    TipoMultimedia tipo
) {

    public static MultimediaResponse desde(Multimedia multimedia) {
        return new MultimediaResponse(multimedia.getId(), multimedia.getUrl(), multimedia.getTipo());
    }
}
