package com.example.proyectosegundo.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectosegundo.R;
import com.example.proyectosegundo.adapters.FavoritesAdapter;
import com.example.proyectosegundo.models.Recipe;
import com.example.proyectosegundo.viewmodels.FavouritesViewModel;

import java.util.Collections;
import java.util.List;

public class FavouritesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FavoritesAdapter adapter;
    private FavouritesViewModel favouritesViewModel;

    private Button btnRetroceder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourites_activity);

        recyclerView = findViewById(R.id.recyclerView_Favoritos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnRetroceder = findViewById(R.id.btnRetrocederFavoritos);

        // Obtener el ViewModel
        favouritesViewModel = new ViewModelProvider(this).get(FavouritesViewModel.class);

        // Inicializar el adaptador con una lista vacía y el OnItemClickListener
        adapter = new FavoritesAdapter(Collections.emptyList(), new FavoritesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Recipe favorito) {
                // Crear el Intent para abrir DetailActivity
                Intent intent = new Intent(FavouritesActivity.this, DetailActivity.class);

                // Pasar los datos del favorito (en este caso, solo un String, pero puedes pasar más)
                intent.putExtra("title", favorito.getTitulo());
                intent.putExtra("description", favorito.getDescripcion());
                intent.putExtra("imageUrl", favorito.getImagen());
                intent.putExtra("elementId",favorito.getId());

                // Iniciar la actividad DetailActivity
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        // Observar los cambios en la lista de favoritos
        favouritesViewModel.getFavoritos().observe(this, favoritosList -> {
            if (favoritosList != null && !favoritosList.isEmpty()) {
                // Actualizar el adaptador con la lista de favoritos
                adapter.updateData(favoritosList); // Método para actualizar la lista en el adaptador
            } else {
                Toast.makeText(FavouritesActivity.this, "No tienes favoritos", Toast.LENGTH_SHORT).show();
            }
        });

        // Observar los mensajes de error
        favouritesViewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(FavouritesActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        // Cargar los favoritos
        favouritesViewModel.loadFavoritos();

        // Configurar el botón de retroceder
        btnRetroceder.setOnClickListener(v -> retroceder());
    }

    public void retroceder() {
        Intent intent = new Intent(FavouritesActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}




