package com.example.proyectosegundo.repositories;

import com.example.proyectosegundo.models.Recipe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DashboardRepository {

    private DatabaseReference mDatabase;

    public DashboardRepository() {
        mDatabase = FirebaseDatabase.getInstance().getReference("recetas");
    }

    // Método para cargar las recetas desde Firebase
    public void loadRecipes(final RecipeCallback callback) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Recipe> recipeList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Recipe recipe = snapshot.getValue(Recipe.class);
                    if (recipe != null) {
                        recipe.setId(snapshot.getKey());  // Asignar el ID de la receta
                        recipeList.add(recipe);
                    }
                }
                callback.onSuccess(recipeList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure(databaseError.getMessage());
            }
        });
    }

    public interface RecipeCallback {
        void onSuccess(List<Recipe> recipeList);
        void onFailure(String errorMessage);
    }
}
