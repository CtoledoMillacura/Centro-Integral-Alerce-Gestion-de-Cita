package com.santotomas.centrointegralalerce_gestindecitas.Configuracion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.santotomas.centrointegralalerce_gestindecitas.Actividad.ActividadActivity;
import com.santotomas.centrointegralalerce_gestindecitas.LoginActivity;
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
            Button oferentesButton = findViewById(R.id.btn_oferentes); // Nuevo botón para Oferentes
            Button salirButton = findViewById(R.id.btn_salir);
            ImageButton actividadesButton = findViewById(R.id.btn_actividades);
            ImageButton calendarioButton = findViewById(R.id.btn_calendario);
            ImageButton configuracionButton = findViewById(R.id.btn_configuracion);

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
                Intent intent = new Intent(SettingsActivity.this, TiposActividadActivity.class);
                startActivity(intent);
            });

            // Configurar OnClickListener para navegar a OferentesActivity
            oferentesButton.setOnClickListener(v -> {
                Intent intent = new Intent(SettingsActivity.this, OferentesActivity.class); // Nueva actividad
                startActivity(intent);
            });

            // Configurar botón de cerrar sesión
            salirButton.setOnClickListener(v -> {
                // Eliminar la sesión de SharedPreferences
                SharedPreferences preferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isLoggedIn", false); // Marcar como no logueado
                editor.apply();

                // Redirigir al LoginActivity
                Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Finalizar esta actividad (opcional)
            });

            // Configuración de botones de navegación
            actividadesButton.setOnClickListener(v -> {
                Intent intent = new Intent(SettingsActivity.this, ActividadActivity.class);
                startActivity(intent);
            });

            calendarioButton.setOnClickListener(v -> {
                Intent intent = new Intent(SettingsActivity.this, LugarAdapter.MainActivity.class);
                startActivity(intent);
            });
        }
    }


