package co.edu.udea.kplus1.appuntesmobile.model;


public class MateriaPensum {
    private Integer id;
    private String name;
    private Integer levelPensum;

    public MateriaPensum(String name, Integer levelPensum) {
        this.name = name;
        this.levelPensum = levelPensum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevelPensum() {
        return levelPensum;
    }

    public void setLevelPensum(Integer levelPensum) {
        this.levelPensum = levelPensum;
    }
}
