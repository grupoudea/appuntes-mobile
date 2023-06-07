package co.edu.udea.kplus1.appuntesmobile.service;

import java.util.List;

import co.edu.udea.kplus1.appuntesmobile.model.Materia;
import co.edu.udea.kplus1.appuntesmobile.model.MateriaUniversidad;
import co.edu.udea.kplus1.appuntesmobile.utils.StandardResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MateriasServiceClient {

    String MATERIAS_BASE = "materias";

    @GET(MATERIAS_BASE + "/filtro-materias")
    Call<StandardResponse<List<Materia>>> filtrarMateriasPorEstudiante(@Query("busqueda") String busqueda, @Query("idEstudiante") Integer idEstudiante);

    @GET(MATERIAS_BASE + "/filtro-materias")
    Call<StandardResponse<List<Materia>>> filtrarMateriasPorEstudiantePublico(@Query("busqueda") String busqueda, @Query("idEstudiante") Integer idEstudiante);

    @GET(MATERIAS_BASE + "/filtro-materias-universidad")
    Call<StandardResponse<List<MateriaUniversidad>>> buscarMateriasUniversidad(@Query("materia") String materia);

    @POST(MATERIAS_BASE + "/guardar-materia")
    Call<StandardResponse<Materia>> guardarMateria(@Body Materia materia);

    @PUT(MATERIAS_BASE + "/editar-materia")
    Call<StandardResponse<Materia>> editarMateria(@Body Materia materia);

    @DELETE(MATERIAS_BASE + "/eliminar-materia-con-apuntes/{idMateria}")
    Call<StandardResponse<Void>> eliminarMateria(@Path("idMateria") Integer idMateria);
}
