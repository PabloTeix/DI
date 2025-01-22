package com.example.proyectosegundo.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.proyectosegundo.models.Recipe;
import com.example.proyectosegundo.repositories.DashboardRepository;

import java.util.List;

public class DashboardViewModel extends AndroidViewModel {

    private DashboardRepository repository;
    private MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public DashboardViewModel(Application application) {
        super(application);
        repository = new DashboardRepository();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipes;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void loadRecipes() {
        repository.loadRecipes(new DashboardRepository.RecipeCallback() {
            @Override
            public void onSuccess(List<Recipe> recipeList) {
                recipes.setValue(recipeList);
            }

            @Override
            public void onFailure(String errorMessage) {
                DashboardViewModel.this.errorMessage.setValue(errorMessage);
            }
        });
    }
}

