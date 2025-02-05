package com.example.proyectosegundo.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectosegundo.R;
import com.example.proyectosegundo.models.Recipe;
import com.example.proyectosegundo.repositories.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private TextView tvTitle, tvDescription;
    private ImageView ivImage, ivFavorite;
    private UserRepository userRepository;
    private String elementId;
    private boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Inicialización de vistas
        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        ivImage = findViewById(R.id.ivImage);
        ivFavorite = findViewById(R.id.fab_favorito); // El FloatingActionButton como botón de favoritos

        userRepository = new UserRepository();

        // Recibir los datos del Intent
        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra("title");
            String description = intent.getStringExtra("description");
            String imageUrl = intent.getStringExtra("imageUrl");
            elementId = intent.getStringExtra("elementId"); // El ID único del elemento



            // Establecer los valores en la interfaz
            tvTitle.setText(title);
            tvDescription.setText(description);

            // Usar Picasso para cargar la imagen desde la URL
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get().load(imageUrl).into(ivImage);
            }

            // Verificar si el elemento ya está en los favoritos
            userRepository.getFavoritos(new UserRepository.RecipeCallback() {
                @Override
                public void onSuccess(List<Recipe> recipeList) {
                    // Crear una lista con los IDs de los favoritos
                    List<String> favoritos = new ArrayList<>();
                    for (Recipe recipe : recipeList) {
                        String recipeId = recipe.getId();
                        Log.d("DetailActivity", "ID de la receta: " + recipeId);
                        favoritos.add(recipeId);
                    }


                    // Verificamos si el elemento es favorito
                    isFavorite = favoritos.contains(elementId);

                    // Actualizamos el ícono según el estado
                    updateFavoriteIcon();
                }

                @Override
                public void onFailure(String errorMessage) {
                    // En caso de error al obtener los favoritos
                    Toast.makeText(DetailActivity.this, "Error al obtener los favoritos: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            });


            // Configurar el clic del botón flotante
            ivFavorite.setOnClickListener(v -> {
                if (isFavorite) {
                    // Eliminar de favoritos
                    removeFromFavorites();
                } else {
                    // Agregar a favoritos
                    addToFavorites();
                }
            });
        }
    }

    // Método para agregar a favoritos
    private void addToFavorites() {
        userRepository.addToFavoritos(elementId, task -> {
            if (task.isSuccessful()) {
                isFavorite = true;
                updateFavoriteIcon(); // Actualizar el icono después de agregar
                Toast.makeText(DetailActivity.this, "Elemento agregado a favoritos", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DetailActivity.this, "Error al agregar a favoritos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para eliminar de favoritos
    private void removeFromFavorites() {
        userRepository.removeFromFavoritos(elementId, task -> {
            if (task.isSuccessful()) {
                isFavorite = false;
                updateFavoriteIcon(); // Actualizar el icono después de eliminar
                Toast.makeText(DetailActivity.this, "Elemento eliminado de favoritos", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DetailActivity.this, "Error al eliminar de favoritos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Actualiza el ícono del botón flotante según si el elemento es favorito
    private void updateFavoriteIcon() {
        if (isFavorite) {
            ivFavorite.setImageResource(R.drawable.ic_favorito_lleno); // Corazón lleno
        } else {
            ivFavorite.setImageResource(R.drawable.ic_favorito_vacio); // Corazón vacío
        }
    }
}





