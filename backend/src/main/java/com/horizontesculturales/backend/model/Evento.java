package com.horizontesculturales.backend.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "eventos")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(columnDefinition = "TEXT")
    private String cronica;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false, length = 255)
    private String lugar;

    @Column(nullable = false)
    private boolean destacado;

    @Column(nullable = false, length = 150)
    private String titulo;
    
    @Column(nullable = false, length = 50)
    private String horario;

    @ManyToOne
    @JoinColumn(name = "categoria_evento_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CategoriaEvento categoriaEvento;

    @ManyToMany
    @JoinTable(
        name = "evento_expositores",
        joinColumns = @JoinColumn(name = "evento_id"),
        inverseJoinColumns = @JoinColumn(name = "persona_id")
    )
    private Set<Persona> expositores = new HashSet<>();

    public Evento() {
    }

    public Evento(String titulo, String descripcion, LocalDate fecha, String horario, String lugar, boolean destacado, CategoriaEvento categoriaEvento) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.horario = horario;
        this.lugar = lugar;
        this.destacado = destacado;
        this.categoriaEvento = categoriaEvento;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getCronica() {
        return cronica;
    }
    public void setCronica(String cronica) {
        this.cronica = cronica;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public String getLugar() {
        return lugar;
    }
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
    public boolean isDestacado() {
        return destacado;
    }
    public void setDestacado(boolean destacado) {
        this.destacado = destacado;
    }
    public CategoriaEvento getCategoriaEvento() {
        return categoriaEvento;
    }
    public void setCategoriaEvento(CategoriaEvento categoriaEvento) {
        this.categoriaEvento = categoriaEvento;
    }
    public Set<Persona> getExpositores() {
        return expositores;
    }
    public void setExpositores(Set<Persona> expositores) {
        this.expositores = expositores;
    }
    public String getHorario() {
        return horario;
    }
    public void setHorario(String horario) {
        this.horario = horario;
    }
}