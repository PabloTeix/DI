package com.example.proyectosegundo.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectosegundo.R;
import com.example.proyectosegundo.adapters.FavoritesAdapter;
import com.example.proyectosegundo.models.Recipe;
import com.example.proyectosegundo.viewmodels.FavouritesViewModel;

import java.util.Collections;

public class FavouritesFragment extends Fragment {

    private RecyclerView recyclerView;
    private FavoritesAdapter adapter;
    private FavouritesViewModel favouritesViewModel;

    public FavouritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        View view = inflater.inflate(R.layout.favourites_activity, container, false);

        recyclerView = view.findViewById(R.id.recyclerView_Favoritos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Obtener el ViewModel
        favouritesViewModel = new FavouritesViewModel(getActivity().getApplication());

        // Inicializar el adaptador con una lista vacía y el OnItemClickListener
        adapter = new FavoritesAdapter(Collections.emptyList(), new FavoritesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Recipe favorito) {
                // Crear el Bundle con los datos que deseas pasar
                Bundle bundle = new Bundle();
                bundle.putString("title", favorito.getTitulo());
                bundle.putString("description", favorito.getDescripcion());
                bundle.putString("imageUrl", favorito.getImagen());
                bundle.putString("elementId", favorito.getId());

                // Crear una instancia del DetailFragment
                DetailFragment detailFragment = new DetailFragment();
                detailFragment.setArguments(bundle);

                // Realizar la transacción de fragmento
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, detailFragment) // Aquí debes asegurarte de que R.id.fragmentContainer sea el contenedor de tus fragments
                        .addToBackStack(null)  // Esto es para poder volver al fragment anterior
                        .commit();
            }
        });

        // Configurar el RecyclerView con el adaptador
        recyclerView.setAdapter(adapter);

        // Observar los cambios en la lista de favoritos
        favouritesViewModel.getFavoritos().observe(getViewLifecycleOwner(), favoritosList -> {
            if (favoritosList != null && !favoritosList.isEmpty()) {
                Log.d("FavouritesFragment", "Favoritos cargados: " + favoritosList.size());
                adapter.updateData(favoritosList); // Actualizamos el adaptador con la lista de favoritos
            } else {
                Toast.makeText(getActivity(), "No tienes favoritos", Toast.LENGTH_SHORT).show();
            }
        });

        // Cargar los favoritos (esto debería estar dentro del ViewModel)
        favouritesViewModel.loadFavoritos();

        return view;
    }
}

