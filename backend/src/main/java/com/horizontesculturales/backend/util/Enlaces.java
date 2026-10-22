package com.horizontesculturales.backend.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public final class Enlaces {

    private static final List<String> DOMINIOS_DE_VIDEO =
        List.of("youtube.com", "youtu.be", "vimeo.com", "instagram.com");

    private Enlaces() {
    }

    public static boolean esHttps(String url) {
        URI uri = leer(url);
        return uri != null && "https".equalsIgnoreCase(uri.getScheme()) && uri.getHost() != null;
    }

    public static boolean esDeVideoPermitido(String url) {
        if (!esHttps(url)) {
            return false;
        }
        String host = leer(url).getHost().toLowerCase();
        return DOMINIOS_DE_VIDEO.stream()
            .anyMatch(dominio -> host.equals(dominio) || host.endsWith("." + dominio));
    }

    private static URI leer(String url) {
        try {
            return new URI(url);
        } catch (URISyntaxException | NullPointerException e) {
            return null;
        }
    }
}