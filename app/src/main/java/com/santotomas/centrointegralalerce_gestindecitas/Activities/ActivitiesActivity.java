package com.santotomas.centrointegralalerce_gestindecitas.Activities;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.santotomas.centrointegralalerce_gestindecitas.R;

public class ActivitiesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Establecer el contenido de la actividad con el archivo XML
        setContentView(R.layout.activity_activities);

        // Puedes agregar aquí la lógica para los elementos del XML si es necesario
        Button exampleButton = findViewById(R.id.btn_example);
        exampleButton.setOnClickListener(v -> {
            // Acción cuando se presiona el botón
        });
    }
}
