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
import com.santotomas.centrointegralalerce_gestindecitas.Model.TipoActividad;
import com.santotomas.centrointegralalerce_gestindecitas.R;

import java.util.ArrayList;

public class TiposActividadActivity extends AppCompatActivity {

    private RecyclerView recyclerTiposActividad;
    private TipoActividadAdapter tipoActividadAdapter;
    private ArrayList<TipoActividad> tipoActividadList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipos_actividad);

        // Inicializar RecyclerView
        recyclerTiposActividad = findViewById(R.id.recycler_tipos_actividad);
        recyclerTiposActividad.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar la lista y el adaptador
        tipoActividadList = new ArrayList<>();
        tipoActividadAdapter = new TipoActividadAdapter(this, tipoActividadList);
        recyclerTiposActividad.setAdapter(tipoActividadAdapter);

        // Referencia a Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("tipos_actividad");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                tipoActividadList.clear();
                for (DataSnapshot tipoSnapshot : snapshot.getChildren()) {
                    TipoActividad tipoActividad = tipoSnapshot.getValue(TipoActividad.class);
                    if (tipoActividad != null) tipoActividadList.add(tipoActividad);
                }
                tipoActividadAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(TiposActividadActivity.this, "Error al cargar tipos de actividad", Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton fabCrearTipo = findViewById(R.id.fab_crear_tipo_actividad);
        fabCrearTipo.setOnClickListener(view -> mostrarDialogoCrearTipoActividad());
    }

    private void mostrarDialogoCrearTipoActividad() {
        // Crear el cuadro de diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Crear Nuevo Tipo de Actividad");

        // Inflar un diseño personalizado para el cuadro
        View view = getLayoutInflater().inflate(R.layout.dialog_crear_tipo_actividad, null);
        builder.setView(view);

        // Obtener las referencias a los campos de entrada
        EditText inputNombre = view.findViewById(R.id.input_nombre_tipo_actividad);
        EditText inputDescripcion = view.findViewById(R.id.input_descripcion_tipo_actividad);

        // Configurar los botones del cuadro de diálogo
        builder.setPositiveButton("Crear", (dialog, which) -> {
            String nombre = inputNombre.getText().toString().trim();
            String descripcion = inputDescripcion.getText().toString().trim();

            // Validar los campos
            if (nombre.isEmpty() || descripcion.isEmpty()) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            // Guardar el tipo de actividad en Firebase
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("tipos_actividad");
            String tipoId = databaseReference.push().getKey(); // Generar un ID único

            TipoActividad tipoActividad = new TipoActividad(tipoId, nombre, descripcion);
            databaseReference.child(tipoId).setValue(tipoActividad).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Tipo de actividad creado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Error al crear el tipo de actividad", Toast.LENGTH_SHORT).show();
                }
            });
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        // Mostrar el cuadro de diálogo
        builder.create().show();
    }

    public void editarTipoActividad(TipoActividad tipoActividad) {
        // Crear el cuadro de diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editar Tipo de Actividad");

        // Inflar un diseño personalizado para el cuadro
        View view = getLayoutInflater().inflate(R.layout.dialog_crear_tipo_actividad, null);
        builder.setView(view);

        // Obtener las referencias a los campos de entrada
        EditText inputNombre = view.findViewById(R.id.input_nombre_tipo_actividad);
        EditText inputDescripcion = view.findViewById(R.id.input_descripcion_tipo_actividad);

        // Rellenar los campos con los datos existentes del tipo de actividad
        inputNombre.setText(tipoActividad.getNombre());
        inputDescripcion.setText(tipoActividad.getDescripcion());

        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String nombre = inputNombre.getText().toString().trim();
            String descripcion = inputDescripcion.getText().toString().trim();

            // Validar los campos
            if (nombre.isEmpty() || descripcion.isEmpty()) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            // Actualizar el tipo de actividad en Firebase
            DatabaseReference tipoRef = FirebaseDatabase.getInstance().getReference("tipos_actividad").child(tipoActividad.getId());
            tipoActividad.setNombre(nombre);
            tipoActividad.setDescripcion(descripcion);

            tipoRef.setValue(tipoActividad).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Tipo de actividad actualizado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Error al actualizar el tipo de actividad", Toast.LENGTH_SHORT).show();
                }
            });
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        // Mostrar el cuadro de diálogo
        builder.create().show();
    }

    public void eliminarTipoActividad(TipoActividad tipoActividad) {
        DatabaseReference tipoRef = FirebaseDatabase.getInstance().getReference("tipos_actividad").child(tipoActividad.getId());
        tipoRef.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Tipo de actividad eliminado exitosamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al eliminar el tipo de actividad", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
