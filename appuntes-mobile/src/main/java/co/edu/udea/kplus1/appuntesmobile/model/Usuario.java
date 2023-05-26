package co.edu.udea.kplus1.appuntesmobile.model;

public class Usuario {

    private Long id;
    private String nombreUsuario;
    private String password;
    private boolean estado;
    private Long idEstudianteFk;
    private Estudiante estudiante;

    public Usuario() {
    }

    public Usuario(Long id, String nombreUsuario, String password, boolean estado, Long idEstudianteFk, Estudiante estudiante) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.estado = estado;
        this.idEstudianteFk = idEstudianteFk;
        this.estudiante = estudiante;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Long getIdEstudianteFk() {
        return idEstudianteFk;
    }

    public void setIdEstudianteFk(Long idEstudianteFk) {
        this.idEstudianteFk = idEstudianteFk;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
}
