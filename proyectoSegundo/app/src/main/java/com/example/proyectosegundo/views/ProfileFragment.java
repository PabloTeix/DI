package com.example.proyectosegundo.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.proyectosegundo.R;
import com.example.proyectosegundo.models.User;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {
    private EditText currentPasswordEditText, newPasswordEditText;
    private Switch darkModeSwitch;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflamos el layout del fragmento
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Inicializamos las vistas
        currentPasswordEditText = view.findViewById(R.id.currentPasswordEditText);
        newPasswordEditText = view.findViewById(R.id.newPasswordEditText);
        darkModeSwitch = view.findViewById(R.id.darkModeSwitch);

        // Cargar la configuración actual del modo oscuro desde SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
        darkModeSwitch.setChecked(prefs.getBoolean("darkMode", false));

        // Listener para el Switch del modo oscuro
        darkModeSwitch.setOnCheckedChangeListener((compoundButton, checked) -> toggleDarkMode(checked));

        // Listener para el botón de cambio de contraseña
        view.findViewById(R.id.changePasswordButton).setOnClickListener(v -> changePassword());

        return view;
    }

    private void changePassword() {
        // Obtenemos las contraseñas ingresadas por el usuario
        String currentPass = currentPasswordEditText.getText().toString();
        String newPass = newPasswordEditText.getText().toString();

        // Validación básica de entradas
        if (currentPass.isEmpty() || newPass.isEmpty()) {
            Toast.makeText(getContext(), "Por favor ingrese ambas contraseñas.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Código para cambiar la contraseña con Firebase
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            // Reautenticación antes de cambiar la contraseña
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPass);
            user.reauthenticate(credential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    // Cambiar la contraseña
                    user.updatePassword(newPass).addOnCompleteListener(updateTask -> {
                        if (updateTask.isSuccessful()) {
                            Toast.makeText(getContext(), "Contraseña cambiada con éxito.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Error al cambiar la contraseña.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "La contraseña actual es incorrecta.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    // Método para cambiar entre modo oscuro y claro
    private void toggleDarkMode(boolean enableDarkMode) {
        SharedPreferences prefs = requireActivity().getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("darkMode", enableDarkMode).apply();

        // Cambiar el tema de la aplicación
        AppCompatDelegate.setDefaultNightMode(enableDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        // Recrear la actividad para que los cambios tomen efecto
        requireActivity().recreate();
    }
}

