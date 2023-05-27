package co.edu.udea.kplus1.appuntesmobile.service;

import co.edu.udea.kplus1.appuntesmobile.model.Materia;
import co.edu.udea.kplus1.appuntesmobile.model.Usuario;
import co.edu.udea.kplus1.appuntesmobile.utils.StandardResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UsuariosServiceClient {

    String USUARIOS_BASE = "usuarios";


    @POST(USUARIOS_BASE + "/iniciar-sesion")
    Call<StandardResponse<Usuario>> iniciarSesion(@Body Usuario usuario);
}
