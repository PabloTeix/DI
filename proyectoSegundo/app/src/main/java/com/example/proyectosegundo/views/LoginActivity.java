package com.example.proyectosegundo.views;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectosegundo.R;
import com.example.proyectosegundo.viewmodels.LoginViewModel;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmailLogin, etPasswordLogin;
    private Button btnLogin, btnRegisterRedirect;
    private ProgressBar progressBar;
    private LoginViewModel loginViewModel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        etEmailLogin = findViewById(R.id.etEmailLogin);
        etPasswordLogin = findViewById(R.id.etPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegisterRedirect = findViewById(R.id.btnRegisterRedirect);
        progressBar = findViewById(R.id.progressBar);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // Observar el estado del inicio de sesión
        loginViewModel.getStatusMessage().observe(this, message -> {
            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
        });

        loginViewModel.getIsLoggingIn().observe(this, isLoggingIn -> {
            if (isLoggingIn) {
                progressBar.setVisibility(ProgressBar.VISIBLE);
                btnLogin.setEnabled(false);
            } else {
                progressBar.setVisibility(ProgressBar.GONE);
                btnLogin.setEnabled(true);
            }
        });

        loginViewModel.getLoggedInUser().observe(this, user -> {
            if (user != null) {
                // Si el login es exitoso, redirigir al DashboardActivity
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish(); // Finalizar LoginActivity para evitar que el usuario regrese con el botón de atrás
            }
        });

        // Manejar el clic del botón de login
        btnLogin.setOnClickListener(v -> {
            String email = etEmailLogin.getText().toString().trim();
            String password = etPasswordLogin.getText().toString().trim();

            // Usar el ViewModel para iniciar sesión
            loginViewModel.loginUser(email, password);
        });

        // Redirigir a la actividad de registro
        btnRegisterRedirect.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}

