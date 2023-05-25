package co.edu.udea.kplus1.appuntesmobile.model;

import android.graphics.Bitmap;

import java.time.LocalDateTime;
import java.util.Date;

public class Apunte {

    private Integer id;
    private Integer idGrupoApunte;
    private GrupoApunte grupoApunte;
    private String fechaCreacion;
    private String titulo;
    private String contenido;
    private String tipoContenido;

    public Apunte(Integer idGrupoApunte, String contenido) {
        this.idGrupoApunte = idGrupoApunte;
        this.contenido = contenido;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdGrupoApunte() {
        return idGrupoApunte;
    }

    public void setIdGrupoApunte(Integer idGrupoApunte) {
        this.idGrupoApunte = idGrupoApunte;
    }

    public GrupoApunte getGrupoApunte() {
        return grupoApunte;
    }

    public void setGrupoApunte(GrupoApunte grupoApunte) {
        this.grupoApunte = grupoApunte;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getTipoContenido() {
        return tipoContenido;
    }

    public void setTipoContenido(String tipoContenido) {
        this.tipoContenido = tipoContenido;
    }
}


