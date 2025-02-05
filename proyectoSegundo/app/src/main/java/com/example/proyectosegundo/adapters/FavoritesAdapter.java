package com.example.proyectosegundo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectosegundo.R;
import com.example.proyectosegundo.models.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder> {

    private List<Recipe> favoritos;
    private OnItemClickListener onItemClickListener;

    // Interfaz para manejar el clic
    public interface OnItemClickListener {
        void onItemClick(Recipe favorito);
    }

    public FavoritesAdapter(List<Recipe> favoritos, OnItemClickListener onItemClickListener) {
        this.favoritos = favoritos;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, int position) {
        Recipe favorito = favoritos.get(position);

        // Configurar los datos de cada receta en el adaptador
        holder.tvTitle.setText(favorito.getTitulo());
        holder.tvDescription.setText(favorito.getDescripcion());
        Picasso.get().load(favorito.getImagen()).into(holder.ivImage);

        // Configurar el clic en cada ítem
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(favorito);
            }
        });
    }


    @Override
    public int getItemCount() {
        return favoritos != null ? favoritos.size() : 0;
    }

    public void updateData(List<Recipe> nuevosFavoritos) {
        this.favoritos = nuevosFavoritos;
        notifyDataSetChanged(); // Notifica que los datos han cambiado y actualiza la vista
    }


    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription;
        ImageView ivImage;

        public FavoriteViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivImage = itemView.findViewById(R.id.ivImage);

        }
    }
}
