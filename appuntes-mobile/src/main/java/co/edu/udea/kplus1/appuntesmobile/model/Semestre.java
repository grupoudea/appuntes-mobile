package co.edu.udea.kplus1.appuntesmobile.model;


import java.time.LocalDate;

public class Semestre {
    private Integer id;
    private Integer studentId;
    private Estudiante student;
    private String semesterName;
    private Integer semesterLevel;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean active;
    private Double finalGrade;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Estudiante getStudent() {
        return student;
    }

    public void setStudent(Estudiante student) {
        this.student = student;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public Integer getSemesterLevel() {
        return semesterLevel;
    }

    public void setSemesterLevel(Integer semesterLevel) {
        this.semesterLevel = semesterLevel;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Double getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(Double finalGrade) {
        this.finalGrade = finalGrade;
    }
}
