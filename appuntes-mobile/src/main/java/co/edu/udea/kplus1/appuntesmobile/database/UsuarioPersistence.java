package co.edu.udea.kplus1.appuntesmobile.database;



public class UsuarioPersistence {

    private Long id;
    private String nombreUsuario;
    private String password;

    private Long idEstudianteFk;

    private String nombreEstudiante;

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

    public Long getIdEstudianteFk() {
        return idEstudianteFk;
    }

    public void setIdEstudianteFk(Long idEstudianteFk) {
        this.idEstudianteFk = idEstudianteFk;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    @Override
    public String toString() {
        return "UsuarioPersistence{" +
                "id=" + id +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", password='" + password + '\'' +
                ", idEstudianteFk=" + idEstudianteFk +
                ", nombreEstudiante='" + nombreEstudiante + '\'' +
                '}';
    }
}
