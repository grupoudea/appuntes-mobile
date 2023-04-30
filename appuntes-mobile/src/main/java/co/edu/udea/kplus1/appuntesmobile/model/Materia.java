package co.edu.udea.kplus1.appuntesmobile.model;

public class Materia {

    private Integer id;
    private Integer idMateriaFk;
    private MateriaUniversidad materiaUniversidad;
    private Integer idEstudianteFk;
    private Estudiante estudiante;
    private Integer creditos;
    private String profesor;

    public Materia(Integer id, Integer idMateriaFk, MateriaUniversidad materiaUniversidad, Integer idEstudianteFk, Estudiante estudiante, Integer creditos, String profesor) {
        this.id = id;
        this.idMateriaFk = idMateriaFk;
        this.materiaUniversidad = materiaUniversidad;
        this.idEstudianteFk = idEstudianteFk;
        this.estudiante = estudiante;
        this.creditos = creditos;
        this.profesor = profesor;
    }

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

    public MateriaUniversidad getMateriaUniversidad() {
        return materiaUniversidad;
    }

    public void setMateriaUniversidad(MateriaUniversidad materiaUniversidad) {
        this.materiaUniversidad = materiaUniversidad;
    }

    public Integer getIdEstudianteFk() {
        return idEstudianteFk;
    }

    public void setIdEstudianteFk(Integer idEstudianteFk) {
        this.idEstudianteFk = idEstudianteFk;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }
}
