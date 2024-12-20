package com.santotomas.centrointegralalerce_gestindecitas.Configuracion;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.santotomas.centrointegralalerce_gestindecitas.Model.Proyecto;
import com.santotomas.centrointegralalerce_gestindecitas.R;

import java.util.ArrayList;
import java.util.UUID;

public class ProyectosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProyectosAdapter adapter;
    private ArrayList<Proyecto> listaProyectos;
    private FloatingActionButton fabCrear;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proyectos);

        recyclerView = findViewById(R.id.recycler_proyectos);
        fabCrear = findViewById(R.id.fab_crear_proyecto);

        listaProyectos = new ArrayList<>();
        adapter = new ProyectosAdapter(listaProyectos, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("proyectos");

        // Agregar el listener para escuchar cambios en tiempo real
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Limpiar la lista antes de agregar los nuevos datos
                listaProyectos.clear();

                // Recorrer los datos obtenidos de Firebase
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Proyecto proyecto = snapshot.getValue(Proyecto.class);
                    listaProyectos.add(proyecto);
                }

                // Notificar al adaptador que los datos han cambiado
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar errores
                Toast.makeText(ProyectosActivity.this, "Error al cargar los proyectos", Toast.LENGTH_SHORT).show();
            }
        });

        // Evento para crear un nuevo proyecto
        fabCrear.setOnClickListener(view -> mostrarDialogoCrear());
    }

    private void mostrarDialogoCrear() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_crear_proyecto, null);
        builder.setView(view);

        EditText inputNombre = view.findViewById(R.id.input_nombre_proyecto);

        builder.setPositiveButton("Crear", (dialogInterface, i) -> {
            String nombre = inputNombre.getText().toString().trim();
            if (TextUtils.isEmpty(nombre)) {
                Toast.makeText(this, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show();
                return;
            }

            String id = UUID.randomUUID().toString();
            Proyecto proyecto = new Proyecto(id, nombre);

            // Guardar en Firebase Realtime Database
            databaseReference.child(id).setValue(proyecto)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Proyecto creado", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Error al crear el proyecto", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        builder.setNegativeButton("Cancelar", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.create().show();
    }

    public void editarProyecto(Proyecto proyecto) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_crear_proyecto, null);
        builder.setView(view);

        EditText inputNombre = view.findViewById(R.id.input_nombre_proyecto);
        inputNombre.setText(proyecto.getNombre());

        builder.setPositiveButton("Guardar", (dialogInterface, i) -> {
            String nombre = inputNombre.getText().toString().trim();
            if (TextUtils.isEmpty(nombre)) {
                Toast.makeText(this, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show();
                return;
            }

            proyecto.setNombre(nombre);

            // Actualizar en Firebase
            databaseReference.child(proyecto.getId()).setValue(proyecto)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Proyecto actualizado", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Error al actualizar el proyecto", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        builder.setNegativeButton("Cancelar", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.create().show();
    }

    public void eliminarProyecto(Proyecto proyecto) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Estás seguro de que deseas eliminar este proyecto?")
                .setPositiveButton("Sí", (dialogInterface, i) -> {
                    // Eliminar de Firebase
                    databaseReference.child(proyecto.getId()).removeValue()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(this, "Proyecto eliminado", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(this, "Error al eliminar el proyecto", Toast.LENGTH_SHORT).show();
                                }
                            });
                })
                .setNegativeButton("No", null)
                .create()
                .show();
    }
}
