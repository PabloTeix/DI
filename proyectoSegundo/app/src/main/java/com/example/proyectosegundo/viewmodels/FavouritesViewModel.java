package com.example.proyectosegundo.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.proyectosegundo.models.Recipe;
import com.example.proyectosegundo.repositories.DashboardRepository;
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
        userRepository.getFavoritos(new UserRepository.RecipeCallback() {
            @Override
            public void onSuccess(List<Recipe> recipeList) {
                favoritosLiveData.setValue(recipeList);
            }

            @Override
            public void onFailure(String errorMessage) {
                FavouritesViewModel.this.errorMessage.setValue(errorMessage);
            }
        });
    }

}



