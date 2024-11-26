package com.santotomas.centrointegralalerce_gestindecitas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.santotomas.centrointegralalerce_gestindecitas.Actividad.ActividadActivity;
import com.santotomas.centrointegralalerce_gestindecitas.Configuracion.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Botones inferiores
        ImageButton btnActivities = findViewById(R.id.btn_activities);
        ImageButton btnSettings = findViewById(R.id.btn_settings);

        // Acción para los botones inferiores
        btnActivities.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ActividadActivity.class)));
        btnSettings.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SettingsActivity.class)));
    }
}
