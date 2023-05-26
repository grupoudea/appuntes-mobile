package co.edu.udea.kplus1.appuntesmobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import co.edu.udea.kplus1.appuntesmobile.database.UsuarioDaoService;
import co.edu.udea.kplus1.appuntesmobile.database.UsuarioDataBase;
import co.edu.udea.kplus1.appuntesmobile.database.UsuarioPersistence;
import co.edu.udea.kplus1.appuntesmobile.model.Usuario;
import co.edu.udea.kplus1.appuntesmobile.restclient.RestApiClient;
import co.edu.udea.kplus1.appuntesmobile.service.UsuariosServiceClient;
import co.edu.udea.kplus1.appuntesmobile.utils.StandardResponse;
import co.edu.udea.kplus1.appuntesmobile.utils.UsuarioManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private UsuarioDataBase usuarioDataBase;
    private UsuarioDaoService usuarioDaoService;

    private UsuarioPersistence usuarioPersistence;

    private UsuarioManager usuarioManager;

    private  EditText usuario;
    private EditText contrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuarioManager = UsuarioManager.getInstance(getApplicationContext());
        usuarioPersistence = usuarioManager.obtenerUsuarioLogueado();

        initEvents();
    }

    public void initEvents() {
        usuario = findViewById(R.id.et_usuario);
        contrasena = findViewById(R.id.et_contrasena);

        if (usuarioPersistence.getNombreUsuario() != null && !usuarioPersistence.getNombreUsuario().isEmpty()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            LoginActivity.this.startActivity(intent);
        }
    }

    public void iniciarSesionOnClick(View view) {
        final String usuarioIngresado = usuario.getText().toString().trim();
        final String contrasenaIngresada = contrasena.getText().toString().trim();

        if(camposVacios(usuarioIngresado, contrasenaIngresada)) {
            iniciarSesion(usuarioIngresado, contrasenaIngresada);
        }
    }

    public void iniciarSesion(final String usuarioIngresado, final String contrasenaIngresada){
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(usuarioIngresado);
        usuario.setPassword(contrasenaIngresada);

        Call<StandardResponse<Usuario>> usuarioAutenticado = RestApiClient.getClient()
                        .create(UsuariosServiceClient.class).iniciarSesion(usuario);

        usuarioAutenticado.enqueue(new Callback<StandardResponse<Usuario>>() {
            @Override
            public void onResponse(Call<StandardResponse<Usuario>> call, Response<StandardResponse<Usuario>> response) {
                validarOnResponse(call, response);
            }

            @Override
            public void onFailure(Call<StandardResponse<Usuario>> call, Throwable t) {
                Toast.makeText(LoginActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }

    public  void validarOnResponse(Call<StandardResponse<Usuario>> call,
                                   Response<StandardResponse<Usuario>> response){

        if (String.valueOf(response.code()).equals("200")) {
            Usuario usuarioLogueado = response.body().getBody();
            UsuarioPersistence usuarioPersistence = new UsuarioPersistence();

            usuarioPersistence.setId(usuarioLogueado.getId());
            usuarioPersistence.setNombreUsuario(usuarioLogueado.getNombreUsuario());
            usuarioPersistence.setPassword(usuarioLogueado.getPassword());
            usuarioPersistence.setIdEstudianteFk(usuarioLogueado.getIdEstudianteFk());
            usuarioPersistence.setNombreEstudiante(usuarioLogueado.getNombreUsuario());
            usuarioManager.saveUserCredentials(usuarioPersistence);

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            LoginActivity.this.startActivity(intent);

        }else if(String.valueOf(response.code()).equals("404")){
            Toast.makeText(LoginActivity.this,"Usuario no existe",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(LoginActivity.this,"Usuario o contraseña incorrecto",Toast.LENGTH_LONG).show();
        }
    }

    private boolean camposVacios(String usuario, String contrasena){
        if (usuario.isEmpty()){
            Toast.makeText(this, "Usuario es requerido",Toast.LENGTH_LONG).show();
            return false;
        }
        if (contrasena.isEmpty()){
            Toast.makeText(this, "Contraseña es requerida",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;

    }
}