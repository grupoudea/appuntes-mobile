package co.edu.udea.kplus1.appuntesmobile.viewModel;

import java.util.List;

import co.edu.udea.kplus1.appuntesmobile.model.Materia;

public class ConsultarMateriasResult {
    private boolean isLoading;
    private List<Materia> materias;
    private String errorMessage;

    public ConsultarMateriasResult(boolean isLoading, List<Materia> materias, String errorMessage) {
        this.isLoading = isLoading;
        this.materias = materias;
        this.errorMessage = errorMessage;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
