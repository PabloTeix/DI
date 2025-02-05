package com.example.proyectosegundo.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectosegundo.R;
import com.example.proyectosegundo.adapters.RecipeAdapter;
import com.example.proyectosegundo.models.Recipe;
import com.example.proyectosegundo.viewmodels.DashboardViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private FirebaseAuth mAuth;
    private Button btnLogout;
    private DashboardViewModel dashboardViewModel;
    private Button btnListaFavoritos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);

        // Inicialización de vistas
        recyclerView = findViewById(R.id.recyclerView);
        btnLogout = findViewById(R.id.btnLogout);
        btnListaFavoritos = findViewById(R.id.btnListaFavoritos);
        Button themeButton = findViewById(R.id.themeButton); // Botón para cambiar el tema
        mAuth = FirebaseAuth.getInstance();

        // Configuración del RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar adaptador vacío
        recipeAdapter = new RecipeAdapter(this, null);
        recyclerView.setAdapter(recipeAdapter);

        // Usamos ViewModelProvider para obtener el ViewModel
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        // Observar el ViewModel para obtener los cambios en la lista de recetas
        dashboardViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                recipeAdapter.setRecipes(recipes);  // Actualizamos la lista de recetas
            }
        });

        // Cargar las recetas desde Firebase
        dashboardViewModel.loadRecipes();

        // Configurar el botón de Logout
        btnLogout.setOnClickListener(v -> logoutUser());

        // Configurar el botón de ListaFavoritos
        btnListaFavoritos.setOnClickListener(v -> lista_favoritos());

        // Configurar el botón de cambio de tema
        SharedPreferences sharedPref = getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
        boolean darkMode = sharedPref.getBoolean("darkMode", false);

        // Establecer el tema inicial según la preferencia guardada
        if (darkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // Acción al hacer clic en el botón para alternar el tema
        themeButton.setOnClickListener(v -> {
            // Recuperar el estado actual del modo oscuro
            boolean isDarkMode = sharedPref.getBoolean("darkMode", false);

            // Cambiar el estado y guardarlo
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("darkMode", !isDarkMode); // Cambiar el valor
            editor.apply();

            // Aplicar el nuevo tema
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); // Modo claro
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); // Modo oscuro
            }

            recreate(); // Recrear la actividad para aplicar el nuevo tema
        });

        // Configurar el click en cada item del RecyclerView
        recipeAdapter.setOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Recipe recipe) {
                // Crear el Intent para abrir DetailActivity
                Intent intent = new Intent(DashboardActivity.this, DetailActivity.class);

                // Pasar los datos del elemento seleccionado
                intent.putExtra("title", recipe.getTitulo());
                intent.putExtra("description", recipe.getDescripcion());
                intent.putExtra("imageUrl", recipe.getImagen());
                intent.putExtra("elementId", recipe.getId());

                // Iniciar la actividad DetailActivity
                startActivity(intent);
            }
        });
    }

    // Método para cerrar sesión
    private void logoutUser() {
        mAuth.signOut();
        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    // Método para ver la lista de favoritos
    public void lista_favoritos() {
        Intent intent = new Intent(DashboardActivity.this, FavouritesActivity.class);
        startActivity(intent);
        finish();
    }
}





