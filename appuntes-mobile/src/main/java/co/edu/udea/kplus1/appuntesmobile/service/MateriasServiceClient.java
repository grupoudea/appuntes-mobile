package co.edu.udea.kplus1.appuntesmobile.service;

import java.util.List;

import co.edu.udea.kplus1.appuntesmobile.model.Materia;
import co.edu.udea.kplus1.appuntesmobile.utils.StandardResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MateriasServiceClient {

    public static final String MATERIAS_BASE = "materias";

    @GET("materias/obtener-materias/{idEstudiante}")
    Call<StandardResponse<List<Materia>>> obtenerMateriasPorEstudiante(@Path("idEstudiante") Integer idEstudiante);

    @GET("materias/filtro-materias")
    Call<StandardResponse<List<Materia>>> filtrarMateriasPorEstudiante(@Query("busqueda") String busqueda, @Query("idEstudiante") Integer idEstudiante);
}
