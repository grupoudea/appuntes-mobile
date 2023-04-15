package co.edu.udea.kplus1.appuntesmobile.model;

public class Materia {
    private Integer id;
    private Integer subjectPensumId;
    private MateriaPensum subjectPensum;
    private Integer semesterId;
    private Semestre semester;
    private Integer credits;
    private String teacher;
    private String status;
    private Double finalGrade;

    public Materia() {
    }

    public Materia(Integer subjectPensumId, MateriaPensum subjectPensum, Integer semesterId, Integer credits, String teacher, Double finalGrade) {
        this.subjectPensumId = subjectPensumId;
        this.subjectPensum = subjectPensum;
        this.semesterId = semesterId;
        this.credits = credits;
        this.teacher = teacher;
        this.finalGrade = finalGrade;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubjectPensumId() {
        return subjectPensumId;
    }

    public void setSubjectPensumId(Integer subjectPensumId) {
        this.subjectPensumId = subjectPensumId;
    }

    public MateriaPensum getSubjectPensum() {
        return subjectPensum;
    }

    public void setSubjectPensum(MateriaPensum subjectPensum) {
        this.subjectPensum = subjectPensum;
    }

    public Integer getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Integer semesterId) {
        this.semesterId = semesterId;
    }

    public Semestre getSemester() {
        return semester;
    }

    public void setSemester(Semestre semester) {
        this.semester = semester;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(Double finalGrade) {
        this.finalGrade = finalGrade;
    }
}
