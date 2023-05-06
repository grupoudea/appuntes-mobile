package co.edu.udea.kplus1.appuntesmobile.service;

import java.util.List;

import co.edu.udea.kplus1.appuntesmobile.model.Materia;
import co.edu.udea.kplus1.appuntesmobile.utils.StandardResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MateriasServiceClient {

    public static final String MATERIAS_BASE = "materias";

    @GET(MATERIAS_BASE + "/obtener-materias/{idEstudiante}")
    Call<StandardResponse<List<Materia>>> obtenerMateriasPorEstudiante(@Path("idEstudiante") Integer idEstudiante);

    @GET(MATERIAS_BASE + "/filtro-materias")
    Call<StandardResponse<List<Materia>>> filtrarMateriasPorEstudiante(@Query("busqueda") String busqueda, @Query("idEstudiante") Integer idEstudiante);

    @POST(MATERIAS_BASE + "/guardar-materia")
    Call<StandardResponse<Materia>> guardarMateria(@Body Materia materia);
}
