package com.santotomas.centrointegralalerce_gestindecitas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.santotomas.centrointegralalerce_gestindecitas.Activities.ActivitiesActivity;
import com.santotomas.centrointegralalerce_gestindecitas.Configuracion.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Botones inferiores
        Button btnActivities = findViewById(R.id.btn_activities);
        Button btnSettings = findViewById(R.id.btn_settings);
        Button btnCalendar = findViewById(R.id.btn_calendar);

        // Acción para los botones inferiores (no funcionalidad adicional por ahora)
        btnActivities.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ActivitiesActivity.class)));
        btnSettings.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SettingsActivity.class)));
        btnCalendar.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MainActivity.class))); // Aquí agregas la actividad del calendario
    }
}
