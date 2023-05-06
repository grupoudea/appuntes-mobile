package co.edu.udea.kplus1.appuntesmobile.model;


import java.io.Serializable;

public class GrupoApunte implements Serializable {
    private Integer id;
    private Integer idMateriaFk;
    private Materia materia;
    private String fechaCreacion;
    private String nombre;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdMateriaFk() {
        return idMateriaFk;
    }

    public void setIdMateriaFk(Integer idMateriaFk) {
        this.idMateriaFk = idMateriaFk;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
