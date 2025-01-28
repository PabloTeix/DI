package com.example.proyectosegundo.repositories;

import com.example.proyectosegundo.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class UserRepository {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public UserRepository() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("usuarios");
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

                            // Guardar los datos en Firebase Realtime Database
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

    // Método para obtener los favoritos del usuario
    public void getFavoritos(final OnCompleteListener<DataSnapshot> listener) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            mDatabase.child(user.getUid()).child("favoritos")
                    .get() // Recuperamos los favoritos del usuario desde Firebase
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<String> favoritos = (List<String>) task.getResult().getValue();
                            listener.onComplete(task); // Devolver la lista de favoritos
                        } else {
                            listener.onComplete(task); // En caso de error
                        }
                    });
        }
    }

    // Método para agregar un elemento a los favoritos
    public void addToFavoritos(String elementId, final OnCompleteListener<Void> listener) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            mDatabase.child(user.getUid()).child("favoritos").child(elementId).setValue(true)
                    .addOnCompleteListener(listener); // Guardar el elemento en los favoritos
        }
    }

    // Método para eliminar un elemento de los favoritos
    public void removeFromFavoritos(String elementId, final OnCompleteListener<Void> listener) {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            mDatabase.child(user.getUid()).child("favoritos").child(elementId).removeValue()
                    .addOnCompleteListener(listener); // Eliminar el elemento de los favoritos
        }
    }
}





