package com.horizontesculturales.backend.security;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class IntentosLogin {

    static final int MAXIMO_FALLOS = 5;
    static final Duration VENTANA = Duration.ofMinutes(15);
    static final Duration BLOQUEO = Duration.ofMinutes(15);
    private static final int LIMITE_PARA_LIMPIAR = 1000;
    private static final int LARGO_MAXIMO_CLAVE = 150;

    private record Registro(int fallos, Instant ultimoFallo, Instant bloqueadoHasta) { }

    private final Map<String, Registro> registros = new ConcurrentHashMap<>();
    private final Clock reloj;

    public IntentosLogin() {
        this(Clock.systemUTC());
    }

    // Para poder probar la clase con un reloj falso.
    IntentosLogin(Clock reloj) {
        this.reloj = reloj;
    }

    public long segundosRestantes(String email) {
        Registro registro = registros.get(clave(email));
        if (registro == null || registro.bloqueadoHasta() == null) {
            return 0;
        }
        long restantes = Duration.between(reloj.instant(), registro.bloqueadoHasta()).getSeconds();
        return Math.max(restantes, 0);
    }

    public void registrarFallo(String email) {
        Instant ahora = reloj.instant();
        if (registros.size() > LIMITE_PARA_LIMPIAR) {
            registros.values().removeIf(registro -> vencido(registro, ahora));
        }
        registros.compute(clave(email), (clave, anterior) -> {
            int fallos = 1;
            if (anterior != null && ahora.isBefore(anterior.ultimoFallo().plus(VENTANA))) {
                fallos = anterior.fallos() + 1;
            }
            Instant bloqueadoHasta = fallos >= MAXIMO_FALLOS ? ahora.plus(BLOQUEO) : null;
            return new Registro(fallos, ahora, bloqueadoHasta);
        });
    }

    public void registrarExito(String email) {
        registros.remove(clave(email));
    }

    private static boolean vencido(Registro registro, Instant ahora) {
        Duration duracion = VENTANA.compareTo(BLOQUEO) > 0 ? VENTANA : BLOQUEO;
        return ahora.isAfter(registro.ultimoFallo().plus(duracion));
    }

    private static String clave(String email) {
        if (email == null) {
            return "";
        }
        String limpio = email.trim().toLowerCase();
        return limpio.length() > LARGO_MAXIMO_CLAVE ? limpio.substring(0, LARGO_MAXIMO_CLAVE) : limpio;
    }
}