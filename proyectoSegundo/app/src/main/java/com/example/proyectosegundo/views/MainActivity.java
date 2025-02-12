package com.example.proyectosegundo.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.example.proyectosegundo.R;
import com.example.proyectosegundo.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configurar el binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Configurar el listener para los ítems del NavigationView
        binding.navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_dashboard) {
                openFragment(new DashboardFragment());
            } else if (item.getItemId() == R.id.nav_favourites) {
                openFragment(new FavouritesFragment());
            } else if (item.getItemId() == R.id.nav_profile) {
                openFragment(new ProfileFragment());
            } else if (item.getItemId() == R.id.nav_logout) {
                logoutUser();
            }

            binding.drawerLayout.closeDrawers(); // Cerrar el Drawer
            return true; // Retornar true para indicar que se manejó el click correctamente
        });

        // Inicializar con el fragmento por defecto (Dashboard)
        if (savedInstanceState == null) {
            openFragment(new DashboardFragment());
        }
    }

    // Método para abrir el fragmento correspondiente
    private void openFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment) // Reemplazar el contenedor de fragmentos
                .commit();
    }

    // Método para cerrar sesión
    private void logoutUser() {
        FirebaseAuth.getInstance().signOut(); // Cerrar sesión con Firebase
        Intent intent = new Intent(this, LoginActivity.class); // Redirigir al Login
        startActivity(intent);
        finish();
    }
}



