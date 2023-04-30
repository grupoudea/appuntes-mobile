package co.edu.udea.kplus1.appuntesmobile.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MateriasServiceClient {

    public static final String MATERIAS_BASE = "materias";

    @GET(MATERIAS_BASE+"/obtener-materia/{idEstudiante}")
    Call<?> obtenerMateriasPorEstudiante(@Path("idEstudiante") Integer idEstudiante);
}
