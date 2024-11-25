package com.example.mycatalog;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.detail_activity);

        ImageView imageView = findViewById(R.id.imageView);

// Usamos Glide para cargar una imagen desde un recurso drawable
        Glide.with(this)  // 'this' es el contexto (puede ser 'this' en Activity o 'getContext()' en Fragment)
                .load(R.drawable.imagen1)  // Ruta de la imagen (puede ser una URL o un recurso drawable)
                .transform(new CropCircleTransformation())
                .into(imageView);  // Aquí es donde cargamos la imagen en el ImageView
    }
}