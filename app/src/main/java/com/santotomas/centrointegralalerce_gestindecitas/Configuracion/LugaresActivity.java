package com.santotomas.centrointegralalerce_gestindecitas.Configuracion;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.santotomas.centrointegralalerce_gestindecitas.Model.Lugar;
import com.santotomas.centrointegralalerce_gestindecitas.R;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class LugaresActivity extends AppCompatActivity {

    private RecyclerView recyclerLugares;
    private LugarAdapter lugarAdapter;
    private ArrayList<Lugar> lugarList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugares);

        // Inicializar RecyclerView
        recyclerLugares = findViewById(R.id.recycler_lugares);
        recyclerLugares.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar la lista y el adaptador
        lugarList = new ArrayList<>();
        lugarAdapter = new LugarAdapter(this, lugarList);
        recyclerLugares.setAdapter(lugarAdapter);

        // Referencia a Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("lugares");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lugarList.clear();
                for (DataSnapshot lugarSnapshot : snapshot.getChildren()) {
                    Lugar lugar = lugarSnapshot.getValue(Lugar.class);
                    if (lugar != null) lugarList.add(lugar);
                }
                lugarAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LugaresActivity.this, "Error al cargar lugares", Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton fabCrearLugar = findViewById(R.id.fab_crear_lugar);
        fabCrearLugar.setOnClickListener(view -> mostrarDialogoCrearLugar());

    }

    private void mostrarDialogoCrearLugar() {
        // Crear el cuadro de diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Crear Nuevo Lugar");

        // Inflar un diseño personalizado para el cuadro
        View view = getLayoutInflater().inflate(R.layout.dialog_crear_lugar, null);
        builder.setView(view);

        // Obtener las referencias a los campos de entrada
        EditText inputNombre = view.findViewById(R.id.input_nombre_lugar);
        EditText inputCupo = view.findViewById(R.id.input_cupo_lugar);

        // Configurar los botones del cuadro de diálogo
        builder.setPositiveButton("Crear", (dialog, which) -> {
            String nombre = inputNombre.getText().toString().trim();
            String cupoTexto = inputCupo.getText().toString().trim();

            // Validar los campos
            if (nombre.isEmpty()) {
                Toast.makeText(this, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show();
                return;
            }

            Integer cupo = cupoTexto.isEmpty() ? null : Integer.parseInt(cupoTexto);

            // Guardar el lugar en Firebase
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("lugares");
            String lugarId = databaseReference.push().getKey(); // Generar un ID único

            Lugar lugar = new Lugar(lugarId, nombre, cupo);
            databaseReference.child(lugarId).setValue(lugar).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Lugar creado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Error al crear el lugar", Toast.LENGTH_SHORT).show();
                }
            });
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        // Mostrar el cuadro de diálogo
        builder.create().show();
    }

}