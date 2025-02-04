package com.example.proyectosegundo.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.proyectosegundo.models.Recipe;
import com.example.proyectosegundo.repositories.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class FavouritesViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private MutableLiveData<List<Recipe>> favoritosLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public FavouritesViewModel(Application application) {
        super(application);
        userRepository = new UserRepository();
    }

    public MutableLiveData<List<Recipe>> getFavoritos() {
        return favoritosLiveData;  // Asegúrate de que devuelva la lista de favoritos
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void loadFavoritos() {
        userRepository.getFavoritos(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    List<Recipe> favoritos = new ArrayList<>();
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Recipe favorito = snapshot.getValue(Recipe.class);
                            if (favorito != null) {
                                favoritos.add(favorito);
                            }
                        }
                        favoritosLiveData.setValue(favoritos); // Establece los favoritos en el LiveData
                    } else {
                        favoritosLiveData.setValue(new ArrayList<>()); // No hay favoritos
                    }
                } else {
                    errorMessage.setValue("Error al cargar los favoritos"); // Error al obtener datos
                }
            }
        });
    }
}



