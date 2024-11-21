package com.santotomas.centrointegralalerce_gestindecitas.Configuracion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.santotomas.centrointegralalerce_gestindecitas.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Establecer el contenido de la actividad con el archivo XML
        setContentView(R.layout.activity_settings);

        // Encuentra los botones por su ID
        Button lugaresButton = findViewById(R.id.btn_lugares);
        Button proyectosButton = findViewById(R.id.btn_proyectos);
        Button sociosComunitariosButton = findViewById(R.id.btn_socios_comunitarios);
        Button tipoActividadButton = findViewById(R.id.btn_tipo_actividad);

        // Configurar los OnClickListeners para navegar a las actividades correspondientes
        lugaresButton.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, LugaresActivity.class);
            startActivity(intent);
        });

        proyectosButton.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, ProyectosActivity.class);
            startActivity(intent);
        });

        sociosComunitariosButton.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, SociosComunitariosActivity.class);
            startActivity(intent);
        });

        tipoActividadButton.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, TiposDeActividadActivity.class);
            startActivity(intent);
        });
    }
}
