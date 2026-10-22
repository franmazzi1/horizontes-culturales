package com.horizontesculturales.backend.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "multimedia")
public class Multimedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TipoMultimedia tipo;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Evento evento;

    @ManyToOne
    @JoinColumn(name = "novedad_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Novedad novedad;

    @ManyToOne
    @JoinColumn(name = "taller_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Taller taller;

    @ManyToOne
    @JoinColumn(name = "persona_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Persona persona;

    public Multimedia() {
    }

    public Multimedia(String url, TipoMultimedia tipo) {
        this.url = url;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public TipoMultimedia getTipo() {
        return tipo;
    }
    public void setTipo(TipoMultimedia tipo) {
        this.tipo = tipo;
    }
    public Evento getEvento() {
        return evento;
    }
    public void setEvento(Evento evento) {
        this.evento = evento;
    }
    public Novedad getNovedad() {
        return novedad;
    }
    public void setNovedad(Novedad novedad) {
        this.novedad = novedad;
    }
    public Taller getTaller() {
        return taller;
    }
    public void setTaller(Taller taller) {
        this.taller = taller;
    }
    public Persona getPersona() {
        return persona;
    }
    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}