package com.example.proyectosegundo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DashboardActivity extends AppCompatActivity {

    private TextView tvTitle, tvDescription;
    private ImageView ivImage;
    private Button btnLogout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);

        mAuth = FirebaseAuth.getInstance();
        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        ivImage = findViewById(R.id.ivImage);
        btnLogout = findViewById(R.id.btnLogout);

        // Verificar si el usuario está autenticado
        if (mAuth.getCurrentUser() == null) {
            // Si no hay usuario autenticado, redirigir al LoginActivity
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        // Establecer el listener para el botón de logout
        btnLogout.setOnClickListener(v -> logoutUser());

        // Cargar los datos del dashboard
        loadDashboardData();
    }

    // Cargar los datos del dashboard desde Firebase
    private void loadDashboardData() {
        // Obtener referencia de "recetas" en Firebase Realtime Database
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("recetas");

        // Leer los datos de la receta con ID "1"
        reference.child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Obtener los datos del título, descripción e imagen
                    String title = dataSnapshot.child("titulo").getValue(String.class);
                    String description = dataSnapshot.child("descripcion").getValue(String.class);
                    String imageUrl = dataSnapshot.child("imagen").getValue(String.class);

                    // Actualizar los elementos de la interfaz con los datos
                    tvTitle.setText(title);
                    tvDescription.setText(description);

                    // Usar Picasso para cargar la imagen desde la URL
                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        Picasso.get().load(imageUrl).into(ivImage);
                    } else {
                        // Si no hay URL, puedes establecer una imagen predeterminada
                        //ivImage.setImageResource(R.drawable.placeholder_image); // Reemplaza con tu imagen predeterminada
                    }
                } else {
                    // Si los datos no existen
                    Toast.makeText(DashboardActivity.this, "Datos no disponibles", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Mostrar error si ocurre un fallo al leer los datos
                Toast.makeText(DashboardActivity.this, "Has cerrado sesion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Función para realizar logout
    private void logoutUser() {
        mAuth.signOut();
        // Redirigir al LoginActivity después del logout
        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Finalizar la actividad DashboardActivity para evitar que el usuario vuelva al dashboard
    }
}
