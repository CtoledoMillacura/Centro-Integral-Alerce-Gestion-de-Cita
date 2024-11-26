package com.santotomas.centrointegralalerce_gestindecitas.Actividad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.santotomas.centrointegralalerce_gestindecitas.Configuracion.SettingsActivity;
import com.santotomas.centrointegralalerce_gestindecitas.CrearActividadActivity;
import com.santotomas.centrointegralalerce_gestindecitas.MainActivity;
import com.santotomas.centrointegralalerce_gestindecitas.R;
import com.santotomas.centrointegralalerce_gestindecitas.Model.Actividad;
import java.util.ArrayList;

public class ActividadActivity extends AppCompatActivity {

    private ListView listViewActividades;
    private ActividadAdapter actividadAdapter;
    private ArrayList<Actividad> listaActividades;
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad);

        // Título y lista de actividades
        listViewActividades = findViewById(R.id.listViewActividades);
        listaActividades = new ArrayList<>();
        actividadAdapter = new ActividadAdapter(this, listaActividades);
        listViewActividades.setAdapter(actividadAdapter);

        // Dentro de onCreate()
        Button btnCrearActividad = findViewById(R.id.btnCrearActividad);
        btnCrearActividad.setOnClickListener(v -> {
            Intent intent = new Intent(ActividadActivity.this, CrearActividadActivity.class);
            startActivity(intent);
        });

        // Botón para ir al Calendario (MainActivity)
        ImageButton btnCalendar = findViewById(R.id.btn_calendar);
        btnCalendar.setOnClickListener(v -> {
            Intent intent = new Intent(ActividadActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Botón para ir a Configuración (SettingsActivity)
        ImageButton btnSettings = findViewById(R.id.btn_settings);
        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(ActividadActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        // Conexión a Firebase para cargar las actividades
        dbRef = FirebaseDatabase.getInstance().getReference("actividades");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaActividades.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Actividad actividad = snapshot.getValue(Actividad.class);
                    listaActividades.add(actividad);
                }
                actividadAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ActividadActivity.this, "Error al cargar actividades", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
