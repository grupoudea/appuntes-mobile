package co.edu.udea.kplus1.appuntesmobile.service;

import java.util.List;

import co.edu.udea.kplus1.appuntesmobile.model.Apunte;
import co.edu.udea.kplus1.appuntesmobile.model.GrupoApunte;

import co.edu.udea.kplus1.appuntesmobile.utils.StandardResponse;
import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import retrofit2.http.DELETE;

import retrofit2.http.Path;

import retrofit2.http.Query;

public interface ApuntesServiceClient {

    public static final String APUNTES_BASE = "apuntes";

    @GET(APUNTES_BASE + "/filtro-grupos-apuntes")
    Call<StandardResponse<List<GrupoApunte>>> filtrarGrupoApuntesPorMateria(@Query("busqueda") String busqueda,
                                                                            @Query("idMateria") Integer idMateria,
                                                                            @Query("idEstudiante") Integer idEstudiante);

    @POST(APUNTES_BASE + "/guardar-apunte")
    Call<StandardResponse<Apunte>> guardarApunte(@Body Apunte apunte);

    @GET(APUNTES_BASE + "/filtro-grupo-apuntes")
    Call<StandardResponse<List<GrupoApunte>>> buscarGrupoApunte(@Query("grupoApunte") String grupoApunte);



    @DELETE(APUNTES_BASE + "/eliminar-grupos-apuntes/{idGrupoApuntes}")
    Call<StandardResponse<Void>> eliminarGruposApuntes(@Path("idGrupoApuntes") Integer idGrupoApuntes);

}
