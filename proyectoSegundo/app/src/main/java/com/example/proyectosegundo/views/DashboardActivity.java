package com.example.proyectosegundo.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);

        recyclerView = findViewById(R.id.recyclerView);
        btnLogout = findViewById(R.id.btnLogout);
        mAuth = FirebaseAuth.getInstance();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recipeAdapter = new RecipeAdapter(this, null);
        recyclerView.setAdapter(recipeAdapter);

        // Usamos ViewModelProvider para obtener el ViewModel
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        // Observar el ViewModel para obtener los cambios en la lista de recetas
        dashboardViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                recipeAdapter = new RecipeAdapter(DashboardActivity.this, recipes);
                recyclerView.setAdapter(recipeAdapter);
            }
        });

        // Cargar las recetas desde Firebase
        dashboardViewModel.loadRecipes();

        // Configurar el botón de Logout
        btnLogout.setOnClickListener(v -> logoutUser());

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

                // Iniciar la actividad DetailActivity
                startActivity(intent);
            }
        });
    }

    private void logoutUser() {
        mAuth.signOut();
        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}



