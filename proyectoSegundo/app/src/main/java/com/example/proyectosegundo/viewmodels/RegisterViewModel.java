package com.example.proyectosegundo.viewmodels;

import android.app.Application;
import android.text.TextUtils;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.proyectosegundo.repositories.UserRepository;
import com.google.firebase.auth.AuthResult;

public class RegisterViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private MutableLiveData<String> statusMessage;
    private MutableLiveData<Boolean> isRegistering;

    public RegisterViewModel(Application application) {
        super(application);
        userRepository = new UserRepository();
        statusMessage = new MutableLiveData<>();
        isRegistering = new MutableLiveData<>(false);
    }

    public LiveData<String> getStatusMessage() {
        return statusMessage;
    }

    public LiveData<Boolean> getIsRegistering() {
        return isRegistering;
    }

    // Validar y registrar al usuario
    public void registerUser(String email, String password, String confirmPassword, String fullName, String phone, String address) {
        // Validar datos
        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(confirmPassword) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(address)) {
            statusMessage.setValue("Por favor, complete todos los campos.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            statusMessage.setValue("Las contraseñas no coinciden.");
            return;
        }

        // Cambiar estado de registro en curso
        isRegistering.setValue(true);

        // Llamar al repositorio para registrar al usuario
        userRepository.registerUser(email, password, fullName, phone, address, task -> {
            if (task.isSuccessful()) {
                statusMessage.setValue("Registro exitoso");
            } else {
                statusMessage.setValue("Error en el registro: " + task.getException().getMessage());
            }
            isRegistering.setValue(false); // Terminar estado de registro
        });
    }
}



