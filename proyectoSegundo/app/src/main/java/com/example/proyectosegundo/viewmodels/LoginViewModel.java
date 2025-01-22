package com.example.proyectosegundo.viewmodels;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.proyectosegundo.repositories.UserRepository;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LoginViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private MutableLiveData<String> statusMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoggingIn = new MutableLiveData<>(false);
    private MutableLiveData<FirebaseUser> loggedInUser = new MutableLiveData<>();

    public LoginViewModel(Application application) {
        super(application);
        userRepository = new UserRepository();
    }

    public LiveData<String> getStatusMessage() {
        return statusMessage;
    }

    public LiveData<Boolean> getIsLoggingIn() {
        return isLoggingIn;
    }

    public LiveData<FirebaseUser> getLoggedInUser() {
        return loggedInUser;
    }

    public void loginUser(String email, String password) {
        // Validación de campos vacíos
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            statusMessage.setValue("Por favor, ingresa tu email y contraseña.");
            return;
        }

        isLoggingIn.setValue(true); // Iniciar el proceso de login

        // Llamar al UserRepository para realizar el login
        userRepository.loginUser(email, password, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                isLoggingIn.setValue(false); // Finalizar el proceso de login

                if (task.isSuccessful()) {
                    // El inicio de sesión fue exitoso
                    FirebaseUser user = userRepository.getCurrentUser();
                    statusMessage.setValue("Inicio de sesión exitoso.");
                    loggedInUser.setValue(user); // Notificar que el usuario ha iniciado sesión
                } else {
                    // Si hubo un error en el login
                    statusMessage.setValue("Error en el inicio de sesión: " + task.getException().getMessage());
                }
            }
        });
    }
}

