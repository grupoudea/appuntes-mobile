package co.edu.udea.kplus1.appuntesmobile.utils;

import android.content.Context;
import android.content.SharedPreferences;

import co.edu.udea.kplus1.appuntesmobile.database.UsuarioPersistence;

public class UsuarioManager {
    private static UsuarioManager instance;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NOMBRE_USUARIO = "nombreUsuario";
    private static final String PREF_ID_USUARIO = "idUsuario";
    private static final String PREF_CONTRASENA = "contrasena";
    private static final String PREF_ID_ESTUDIANTE = "idEstudianteFk";
    private static final String PREF_NOMBRE_ESTUDIANTE = "nombreEstudiante";

    private UsuarioManager(Context context) {
        sharedPreferences = context.getApplicationContext()
                .getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
    }

    public static synchronized UsuarioManager getInstance(Context context) {
        if (instance == null) {
            instance = new UsuarioManager(context);
        }
        return instance;
    }

    public void saveUserCredentials(UsuarioPersistence usuarioPersistence) {
        sharedPreferences.edit()
                .putString(PREF_NOMBRE_USUARIO, usuarioPersistence.getNombreUsuario())
                .putString(PREF_CONTRASENA, usuarioPersistence.getPassword())
                .putString(PREF_NOMBRE_ESTUDIANTE, usuarioPersistence.getNombreEstudiante())
                .putLong(PREF_ID_USUARIO, usuarioPersistence.getId())
                .putLong(PREF_ID_ESTUDIANTE, usuarioPersistence.getIdEstudianteFk())
                .apply();
    }

    public void clearUserCredentials() {
        sharedPreferences.edit()
                .remove(PREF_NOMBRE_USUARIO)
                .remove(PREF_CONTRASENA)
                .remove(PREF_NOMBRE_ESTUDIANTE)
                .remove(PREF_ID_USUARIO)
                .remove(PREF_ID_ESTUDIANTE)
                .apply();
    }

    public UsuarioPersistence obtenerUsuarioLogueado() {
        UsuarioPersistence usuarioPersistence = new UsuarioPersistence();
        usuarioPersistence.setId(sharedPreferences.getLong(PREF_ID_USUARIO, 0));
        usuarioPersistence.setIdEstudianteFk(sharedPreferences.getLong(PREF_ID_ESTUDIANTE, 0));
        usuarioPersistence.setNombreEstudiante(sharedPreferences.getString(PREF_NOMBRE_ESTUDIANTE, ""));
        usuarioPersistence.setNombreUsuario(sharedPreferences.getString(PREF_NOMBRE_USUARIO, ""));
        usuarioPersistence.setPassword(sharedPreferences.getString(PREF_CONTRASENA, ""));
        return usuarioPersistence;
    }

    public String getUsername() {
        return sharedPreferences.getString(PREF_NOMBRE_USUARIO, "");
    }

    public String getPassword() {
        return sharedPreferences.getString(PREF_CONTRASENA, "");
    }
}
