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
import com.santotomas.centrointegralalerce_gestindecitas.Model.Oferente;
import com.santotomas.centrointegralalerce_gestindecitas.R;

import java.util.ArrayList;
import java.util.UUID;

public class OferentesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OferentesAdapter adapter;
    private ArrayList<Oferente> listaOferentes;
    private FloatingActionButton fabCrear;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oferentes);

        recyclerView = findViewById(R.id.recycler_oferentes);
        fabCrear = findViewById(R.id.fab_crear_oferente);

        listaOferentes = new ArrayList<>();
        adapter = new OferentesAdapter(listaOferentes, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Inicializamos la referencia de la base de datos
        databaseReference = FirebaseDatabase.getInstance().getReference("oferentes");

        // Agregar el listener para escuchar cambios en tiempo real
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Limpiar la lista antes de agregar los nuevos datos
                listaOferentes.clear();

                // Recorrer los datos obtenidos de Firebase
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Oferente oferente = snapshot.getValue(Oferente.class);
                    listaOferentes.add(oferente);
                }

                // Notificar al adaptador que los datos han cambiado
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar errores
                Toast.makeText(OferentesActivity.this, "Error al cargar los oferentes", Toast.LENGTH_SHORT).show();
            }
        });

        // Evento para crear un nuevo oferente
        fabCrear.setOnClickListener(view -> mostrarDialogoCrear());
    }

    private void mostrarDialogoCrear() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_crear_oferente, null);
        builder.setView(view);

        EditText inputNombre = view.findViewById(R.id.input_nombre_oferente);
        EditText inputDocenteResponsable = view.findViewById(R.id.input_docente_responsable);
        EditText inputCarrera = view.findViewById(R.id.input_carrera);

        builder.setPositiveButton("Crear", (dialogInterface, i) -> {
            String nombre = inputNombre.getText().toString().trim();
            String docenteResponsable = inputDocenteResponsable.getText().toString().trim();
            String carrera = inputCarrera.getText().toString().trim();

            if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(docenteResponsable) || TextUtils.isEmpty(carrera)) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            String id = UUID.randomUUID().toString();
            Oferente oferente = new Oferente(id, nombre, docenteResponsable, carrera);

            // Guardar en Firebase Realtime Database
            databaseReference.child(id).setValue(oferente)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Oferente creado", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Error al crear el oferente", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        builder.setNegativeButton("Cancelar", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.create().show();
    }

    public void editarOferente(Oferente oferente) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_crear_oferente, null);
        builder.setView(view);

        EditText inputNombre = view.findViewById(R.id.input_nombre_oferente);
        EditText inputDocenteResponsable = view.findViewById(R.id.input_docente_responsable);
        EditText inputCarrera = view.findViewById(R.id.input_carrera);

        inputNombre.setText(oferente.getNombre());
        inputDocenteResponsable.setText(oferente.getDocenteResponsable());
        inputCarrera.setText(oferente.getCarrera());

        builder.setPositiveButton("Guardar", (dialogInterface, i) -> {
            String nombre = inputNombre.getText().toString().trim();
            String docenteResponsable = inputDocenteResponsable.getText().toString().trim();
            String carrera = inputCarrera.getText().toString().trim();

            if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(docenteResponsable) || TextUtils.isEmpty(carrera)) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            oferente.setNombre(nombre);
            oferente.setDocenteResponsable(docenteResponsable);
            oferente.setCarrera(carrera);

            // Actualizar en Firebase
            databaseReference.child(oferente.getId()).setValue(oferente)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Oferente actualizado", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Error al actualizar el oferente", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        builder.setNegativeButton("Cancelar", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.create().show();
    }

    public void eliminarOferente(Oferente oferente) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Estás seguro de que deseas eliminar este oferente?")
                .setPositiveButton("Sí", (dialogInterface, i) -> {
                    // Eliminar de Firebase
                    databaseReference.child(oferente.getId()).removeValue()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(this, "Oferente eliminado", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(this, "Error al eliminar el oferente", Toast.LENGTH_SHORT).show();
                                }
                            });
                })
                .setNegativeButton("No", null)
                .create()
                .show();
    }
}
