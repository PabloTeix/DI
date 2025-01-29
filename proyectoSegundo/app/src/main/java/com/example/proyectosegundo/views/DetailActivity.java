package com.example.proyectosegundo.views;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectosegundo.R;
import com.example.proyectosegundo.repositories.UserRepository;
import com.squareup.picasso.Picasso;

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
            userRepository.getFavoritos(task -> {
                if (task.isSuccessful()) {
                    List<String> favoritos = (List<String>) task.getResult().getValue();
                    isFavorite = favoritos != null && favoritos.contains(elementId);
                    updateFavoriteIcon(); // Actualizar icono según el estado de favorito
                } else {
                    Toast.makeText(this, "Error al obtener los favoritos", Toast.LENGTH_SHORT).show();
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


