package com.example.proyectosegundo.repositories;

import android.util.Log;

import com.example.proyectosegundo.models.Recipe;
import com.example.proyectosegundo.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private DatabaseReference mRecetas;

    public UserRepository() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mRecetas = FirebaseDatabase.getInstance().getReference("recetas");
    }

    // Método para registrar un nuevo usuario
    public void registerUser(String email, String password, String fullName, String phone, String address, final OnCompleteListener<AuthResult> listener) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Crear un objeto User con los datos adicionales
                            User newUser = new User(fullName, email, phone, address, null);

                            // Guardar los datos del usuario en Firebase Realtime Database
                            mDatabase.child(user.getUid()).setValue(newUser)
                                    .addOnCompleteListener(dbTask -> {
                                        listener.onComplete(task); // Llamamos al listener del método original
                                    });
                        }
                    } else {
                        listener.onComplete(task); // Llamar al listener en caso de error
                    }
                });
    }

    // Método para iniciar sesión
    public void loginUser(String email, String password, final OnCompleteListener<AuthResult> listener) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(listener);  // Devolver el resultado de la autenticación
    }

    // Método para cerrar sesión
    public void logoutUser() {
        mAuth.signOut();
    }

    // Método para obtener el usuario actual
    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    // Método para obtener los favoritos de un usuario
    public void getFavoritos(final RecipeCallback callback) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // Primero obtenemos los IDs de los favoritos
            Log.d("UserRepository", "Usuario autenticado: " + user.getUid());
            mDatabase.child(user.getUid()).child("favoritos")
                    .get() // Recuperamos los favoritos del usuario desde Firebase
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            // Extraemos los favoritos del DataSnapshot
                            List<String> favoritosId = new ArrayList<>();
                            DataSnapshot snapshot = task.getResult();

                            // Llenamos la lista con los IDs de las recetas favoritas
                            for (DataSnapshot favoritoSnapshot : snapshot.getChildren()) {
                                String recipeId = favoritoSnapshot.getKey(); // Usamos el key del snapshot (elementId)
                                if (recipeId != null) {
                                    favoritosId.add(recipeId);
                                }
                            }

                            // Ahora, obtenemos las recetas completas usando los IDs
                            List<Recipe> favoritosList = new ArrayList<>();
                            for (String recipeId : favoritosId) {
                                getRecipeById(recipeId, new RecipeCallback() {
                                    @Override
                                    public void onSuccess(List<Recipe> recipeList) {
                                        favoritosList.addAll(recipeList); // Agregamos la receta a la lista de favoritos
                                        if (favoritosList.size() == favoritosId.size()) {
                                            // Si ya hemos agregado todas las recetas, llamamos al callback
                                            callback.onSuccess(favoritosList);
                                        }
                                    }

                                    @Override
                                    public void onFailure(String errorMessage) {
                                        Log.e("Error", "Error al obtener receta con ID: " + recipeId);
                                        callback.onFailure(errorMessage); // Pasamos el mensaje de error al callback
                                    }
                                });
                            }
                        } else {
                            Log.e("Error", "Error al obtener los favoritos: " + task.getException());
                            callback.onFailure("Error al obtener los favoritos");
                        }
                    });
        } else {
            callback.onFailure("Usuario no autenticado");
        }
    }

    // Método para obtener una receta por su ID
    private void getRecipeById(String recipeId, final RecipeCallback callback) {
        mRecetas.child(recipeId)  // Accedemos a la receta usando su ID
                .get()  // Obtenemos los datos de la receta desde Firebase
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        DataSnapshot recipeSnapshot = task.getResult();
                        // Suponemos que Recipe es una clase con estos atributos
                        Recipe recipe = recipeSnapshot.getValue(Recipe.class);

                        if (recipe != null) {
                            // Si la receta fue encontrada, la agregamos a una lista de recetas
                            List<Recipe> favoritosList = new ArrayList<>();
                            favoritosList.add(recipe); // Agregamos la receta a la lista

                            // Ahora, llamamos al callback con la lista de recetas
                            callback.onSuccess(favoritosList);
                        } else {
                            // Si no se encuentra la receta, pasamos un mensaje de error
                            callback.onFailure("Receta no encontrada");
                        }
                    } else {
                        // Si ocurre un error al obtener la receta, pasamos un mensaje de error
                        callback.onFailure("Error al obtener la receta");
                    }
                });
    }

    // Método para agregar un elemento a los favoritos
    public void addToFavoritos(String elementId, final OnCompleteListener<Void> listener) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // Usamos el ID de la receta como clave para agregarla a los favoritos
            mDatabase.child(user.getUid()).child("favoritos").child(elementId).setValue(elementId)
                    .addOnCompleteListener(listener); // Guardamos el ID de la receta
        } else {
            listener.onComplete(null); // Si no hay usuario autenticado
        }
    }

    // Método para eliminar un elemento de los favoritos
    public void removeFromFavoritos(String elementId, final OnCompleteListener<Void> listener) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // Eliminamos el ID de la receta de los favoritos
            mDatabase.child(user.getUid()).child("favoritos").child(elementId).removeValue()
                    .addOnCompleteListener(listener); // Eliminar el ID de la receta
        } else {
            listener.onComplete(null); // Si no hay usuario autenticado
        }
    }

    // Interfaz de callback para manejar el resultado de obtener recetas
    public interface RecipeCallback {
        void onSuccess(List<Recipe> recipeList);
        void onFailure(String errorMessage);
    }
}








