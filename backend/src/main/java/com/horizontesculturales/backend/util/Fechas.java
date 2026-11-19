package com.horizontesculturales.backend.util;

import java.time.LocalDate;
import java.time.ZoneId;

public final class Fechas {

    private static final ZoneId ZONA = ZoneId.of("America/Argentina/Buenos_Aires");

    private Fechas() {
    }

    public static LocalDate hoy() {
        return LocalDate.now(ZONA);
    }
}