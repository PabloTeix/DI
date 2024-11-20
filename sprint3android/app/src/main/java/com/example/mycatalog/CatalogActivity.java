package com.example.mycatalog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CatalogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalog_activity); // Asegúrate de que el layout sea el correcto

        // Referencia al botón "Navegar al detalle"
        Button buttonNavegarDetalle = findViewById(R.id.button_navegar_detalle);

        // Establecer el OnClickListener
        buttonNavegarDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llamada para iniciar DetailActivity
                Intent intent = new Intent(CatalogActivity.this, DetailActivity.class);
                startActivity(intent);
            }
        });
    }
}