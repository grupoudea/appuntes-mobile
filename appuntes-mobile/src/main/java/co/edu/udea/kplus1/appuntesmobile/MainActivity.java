package co.edu.udea.kplus1.appuntesmobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import co.edu.udea.kplus1.appuntesmobile.databinding.ActivityMainBinding;
import co.edu.udea.kplus1.appuntesmobile.utils.UsuarioManager;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.toString();
    private UsuarioManager usuarioManager;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        usuarioManager = UsuarioManager.getInstance(getApplicationContext());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        setupAppBar();
    }

    public void onClickCerrarSesion(MenuItem menuItem) {
        Log.i(TAG, "El usuario está cerrando sesión");
        usuarioManager.clearUserCredentials();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        MainActivity.this.startActivity(intent);
        finishAffinity();
    }

    private void setupAppBar() {
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.materiasFragment, R.id.materiasPublicasFragment)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}