package co.edu.udea.kplus1.appuntesmobile.model;

public class MateriaUniversidad {
    private Integer id;
    private String codigo;
    private String materia;
    private Integer numeroPensum;

    public MateriaUniversidad(Integer id, String codigo, String materia, Integer numeroPensum) {
        this.id = id;
        this.codigo = codigo;
        this.materia = materia;
        this.numeroPensum = numeroPensum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public Integer getNumeroPensum() {
        return numeroPensum;
    }

    public void setNumeroPensum(Integer numeroPensum) {
        this.numeroPensum = numeroPensum;
    }

    @Override
    public String toString() {
        return materia;
    }
}
