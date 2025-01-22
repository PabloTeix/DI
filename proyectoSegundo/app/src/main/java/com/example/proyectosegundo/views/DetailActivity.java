package com.example.proyectosegundo.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectosegundo.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView tvTitle, tvDescription;
    private ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Referencias a los elementos de la interfaz
        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        ivImage = findViewById(R.id.ivImage);

        // Recibir los datos del Intent
        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra("title");
            String description = intent.getStringExtra("description");
            String imageUrl = intent.getStringExtra("imageUrl");

            // Establecer los valores en la interfaz
            tvTitle.setText(title);
            tvDescription.setText(description);

            // Usar Picasso para cargar la imagen desde la URL
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get().load(imageUrl).into(ivImage);
            }
        }
    }
}

