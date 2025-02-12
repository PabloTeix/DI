package com.example.proyectosegundo.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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

public class DashboardFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private FirebaseAuth mAuth;
    private Button btnLogout;
    private DashboardViewModel dashboardViewModel;
    private Button btnListaFavoritos;

    public DashboardFragment() {
        // Constructor vacío necesario
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.dashboard_activity, container, false);

        // Inicialización de vistas
        recyclerView = view.findViewById(R.id.dashboardRecyclerView);
        //btnLogout = view.findViewById(R.id.btnLogout);
        //btnListaFavoritos = view.findViewById(R.id.btnListaFavoritos);
        //Button themeButton = view.findViewById(R.id.themeButton); // Botón para cambiar el tema
        mAuth = FirebaseAuth.getInstance();

        // Configuración del RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializar adaptador vacío
        recipeAdapter = new RecipeAdapter(getContext(), null);
        recyclerView.setAdapter(recipeAdapter);

        // Usamos ViewModelProvider para obtener el ViewModel
        dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);

        // Observar el ViewModel para obtener los cambios en la lista de recetas
        dashboardViewModel.getRecipes().observe(getViewLifecycleOwner(), new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                recipeAdapter.setRecipes(recipes);  // Actualizamos la lista de recetas
            }
        });

        // Cargar las recetas desde Firebase
        dashboardViewModel.loadRecipes();

        // Configurar el botón de Logout
        //btnLogout.setOnClickListener(v -> logoutUser());

        // Configurar el botón de ListaFavoritos
        //btnListaFavoritos.setOnClickListener(v -> lista_favoritos());

        // Configurar el botón de cambio de tema
        //SharedPreferences sharedPref = getContext().getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
        //boolean darkMode = sharedPref.getBoolean("darkMode", false);

        // Establecer el tema inicial según la preferencia guardada
        //if (darkMode) {
          //  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        //} else {
          //  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //}

        // Acción al hacer clic en el botón para alternar el tema
        //themeButton.setOnClickListener(v -> {
            // Recuperar el estado actual del modo oscuro
            //boolean isDarkMode = sharedPref.getBoolean("darkMode", false);

            // Cambiar el estado y guardarlo
           /* SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean("darkMode", !isDarkMode); // Cambiar el valor
            editor.apply();

            // Aplicar el nuevo tema
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); // Modo claro
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); // Modo oscuro
            }

            getActivity().recreate(); // Recrear la actividad para aplicar el nuevo tema
        }); */

        // Configurar el click en cada item del RecyclerView
        recipeAdapter.setOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Recipe recipe) {
                // Crear el Intent para abrir DetailFragment
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                DetailFragment detailFragment = new DetailFragment();

                // Pasar los datos del elemento seleccionado
                Bundle bundle = new Bundle();
                bundle.putString("title", recipe.getTitulo());
                bundle.putString("description", recipe.getDescripcion());
                bundle.putString("imageUrl", recipe.getImagen());
                bundle.putString("elementId", recipe.getId());

                detailFragment.setArguments(bundle);
                transaction.replace(R.id.fragmentContainer, detailFragment); // El contenedor de fragmentos de tu actividad
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
/*
    // Método para cerrar sesión
    private void logoutUser() {
        mAuth.signOut();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish(); // Este es correcto aquí ya que estamos en una actividad y no en un fragmento
    }

    // Método para ver la lista de favoritos
    public void lista_favoritos() {
        // Cambiar a FavouritesFragment sin usar finish()
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        FavouritesFragment favouritesFragment = new FavouritesFragment();
        transaction.replace(R.id.fragmentContainer, favouritesFragment); // El contenedor de fragmentos de tu actividad
        transaction.addToBackStack(null); // Para que el usuario pueda retroceder
        transaction.commit();
    } */
}







