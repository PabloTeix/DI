package com.example.proyectosegundo.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.proyectosegundo.R;
import com.example.proyectosegundo.models.Recipe;
import com.example.proyectosegundo.repositories.UserRepository;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailFragment extends Fragment {

    private TextView tvTitle, tvDescription;
    private ImageView ivImage, ivFavorite;
    private UserRepository userRepository;
    private String elementId;
    private boolean isFavorite;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout
        View view = inflater.inflate(R.layout.activity_detail, container, false);

        // Inicialización de vistas
        tvTitle = view.findViewById(R.id.tvTitle);
        tvDescription = view.findViewById(R.id.tvDescription);
        ivImage = view.findViewById(R.id.ivImage);
        ivFavorite = view.findViewById(R.id.fab_favorito);

        userRepository = new UserRepository();

        // Recibir los datos desde el fragmento anterior
        if (getArguments() != null) {
            String title = getArguments().getString("title");
            String description = getArguments().getString("description");
            String imageUrl = getArguments().getString("imageUrl");
            elementId = getArguments().getString("elementId", ""); // Valor por defecto vacío si no está presente

            // Establecer los valores en la interfaz
            tvTitle.setText(title != null ? title : "Título no disponible");
            tvDescription.setText(description != null ? description : "Descripción no disponible");

            // Usar Picasso para cargar la imagen
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get().load(imageUrl).into(ivImage);
            }

            // Verificar si el elemento está en los favoritos
            userRepository.getFavoritos(new UserRepository.RecipeCallback() {
                @Override
                public void onSuccess(List<Recipe> recipeList) {
                    List<String> favoritos = new ArrayList<>();
                    for (Recipe recipe : recipeList) {
                        favoritos.add(recipe.getId());
                    }

                    // Verificar si el item es favorito
                    isFavorite = favoritos.contains(elementId);

                    // Actualizar el icono de favorito
                    updateFavoriteIcon();
                }

                @Override
                public void onFailure(String errorMessage) {
                    Toast.makeText(getActivity(), "Error al obtener los favoritos: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            });

            // Configurar el clic del botón de favorito
            ivFavorite.setOnClickListener(v -> {
                if (isFavorite) {
                    removeFromFavorites();
                } else {
                    addToFavorites();
                }
            });
        }

        return view;
    }

    private void addToFavorites() {
        userRepository.addToFavoritos(elementId, task -> {
            if (task.isSuccessful()) {
                isFavorite = true;
                updateFavoriteIcon();
                Toast.makeText(getActivity(), "Elemento agregado a favoritos", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Error al agregar a favoritos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeFromFavorites() {
        userRepository.removeFromFavoritos(elementId, task -> {
            if (task.isSuccessful()) {
                isFavorite = false;
                updateFavoriteIcon();
                Toast.makeText(getActivity(), "Elemento eliminado de favoritos", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Error al eliminar de favoritos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateFavoriteIcon() {
        if (isFavorite) {
            ivFavorite.setImageResource(R.drawable.ic_favorito_lleno); // Corazón lleno
        } else {
            ivFavorite.setImageResource(R.drawable.ic_favorito_vacio); // Corazón vacío
        }
    }
}
