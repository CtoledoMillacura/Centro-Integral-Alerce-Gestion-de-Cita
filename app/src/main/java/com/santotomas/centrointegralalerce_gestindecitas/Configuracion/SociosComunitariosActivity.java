package com.santotomas.centrointegralalerce_gestindecitas.Configuracion;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.santotomas.centrointegralalerce_gestindecitas.Model.SocioComunitario;
import com.santotomas.centrointegralalerce_gestindecitas.R;

import java.util.ArrayList;

public class SociosComunitariosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SociosComunitariosAdapter adapter;
    private ArrayList<SocioComunitario> listaSocios;
    private FloatingActionButton fabCrear;

    private DatabaseReference databaseReference; // Referencia a Firebase Database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socios_comunitarios);

        recyclerView = findViewById(R.id.recycler_socios_comunitarios);
        fabCrear = findViewById(R.id.fab_crear_socio_comunitario);

        listaSocios = new ArrayList<>();
        adapter = new SociosComunitariosAdapter(listaSocios, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Inicializa Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("SociosComunitarios");

        // Carga los datos desde Firebase
        cargarDatosFirebase();

        fabCrear.setOnClickListener(view -> mostrarDialogoCrear());
    }

    private void cargarDatosFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaSocios.clear(); // Limpia la lista local antes de agregar los datos actualizados
                for (DataSnapshot socioSnapshot : snapshot.getChildren()) {
                    SocioComunitario socio = socioSnapshot.getValue(SocioComunitario.class);
                    if (socio != null) {
                        listaSocios.add(socio);
                    }
                }
                adapter.notifyDataSetChanged(); // Actualiza el RecyclerView
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SociosComunitariosActivity.this, "Error al cargar datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarDialogoCrear() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_crear_socio_comunitario, null);
        builder.setView(view);

        EditText inputNombre = view.findViewById(R.id.input_nombre_socio_comunitario);

        builder.setPositiveButton("Crear", (dialogInterface, i) -> {
            String nombre = inputNombre.getText().toString().trim();
            if (TextUtils.isEmpty(nombre)) {
                Toast.makeText(this, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show();
                return;
            }

            String id = databaseReference.push().getKey(); // Genera un ID único desde Firebase
            if (id != null) {
                SocioComunitario socio = new SocioComunitario(id, nombre);
                databaseReference.child(id).setValue(socio) // Guarda en Firebase
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(this, "Socio creado", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "Error al crear socio", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        builder.setNegativeButton("Cancelar", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.create().show();
    }

    public void editarSocio(SocioComunitario socio) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_crear_socio_comunitario, null);
        builder.setView(view);

        EditText inputNombre = view.findViewById(R.id.input_nombre_socio_comunitario);
        inputNombre.setText(socio.getNombre());

        builder.setPositiveButton("Guardar", (dialogInterface, i) -> {
            String nombre = inputNombre.getText().toString().trim();
            if (TextUtils.isEmpty(nombre)) {
                Toast.makeText(this, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show();
                return;
            }

            socio.setNombre(nombre);
            databaseReference.child(socio.getId()).setValue(socio) // Actualiza en Firebase
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Socio actualizado", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Error al actualizar socio", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        builder.setNegativeButton("Cancelar", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.create().show();
    }

    public void eliminarSocio(SocioComunitario socio) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Estás seguro de que deseas eliminar este socio?")
                .setPositiveButton("Sí", (dialogInterface, i) -> {
                    databaseReference.child(socio.getId()).removeValue() // Elimina de Firebase
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(this, "Socio eliminado", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(this, "Error al eliminar socio", Toast.LENGTH_SHORT).show();
                                }
                            });
                })
                .setNegativeButton("No", null)
                .create()
                .show();
    }
}
