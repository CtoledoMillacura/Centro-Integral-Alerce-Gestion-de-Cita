package com.santotomas.centrointegralalerce_gestindecitas;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.santotomas.centrointegralalerce_gestindecitas.Model.Actividad;
import com.santotomas.centrointegralalerce_gestindecitas.Model.Lugar;
import com.santotomas.centrointegralalerce_gestindecitas.Model.Oferente;
import com.santotomas.centrointegralalerce_gestindecitas.Model.Proyecto;
import com.santotomas.centrointegralalerce_gestindecitas.Model.SocioComunitario;
import com.santotomas.centrointegralalerce_gestindecitas.Model.TipoActividad;

import java.util.ArrayList;
import java.util.Calendar;

public class CrearActividadActivity extends AppCompatActivity {

    private EditText editTextNombre, editTextPeriodicidad, editTextFecha, editTextHora;
    private Spinner spinnerLugar, spinnerOferente, spinnerProyecto, spinnerSocioComunitario, spinnerTipoActividad;
    private Button btnGuardar;

    private ArrayList<String> lugares = new ArrayList<>();
    private ArrayList<String> oferentes = new ArrayList<>();
    private ArrayList<String> proyectos = new ArrayList<>();
    private ArrayList<String> sociosComunitarios = new ArrayList<>();
    private ArrayList<String> tiposActividad = new ArrayList<>();

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_actividad);

        // Referencias de los componentes
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextPeriodicidad = findViewById(R.id.editTextPeriodicidad);
        editTextFecha = findViewById(R.id.editTextFecha);
        editTextHora = findViewById(R.id.editTextHora);
        spinnerLugar = findViewById(R.id.spinnerLugar);
        spinnerOferente = findViewById(R.id.spinnerOferente);
        spinnerProyecto = findViewById(R.id.spinnerProyecto);
        spinnerSocioComunitario = findViewById(R.id.spinnerSocioComunitario);
        spinnerTipoActividad = findViewById(R.id.spinnerTipoActividad);
        btnGuardar = findViewById(R.id.btnGuardar);

        // Configurar el campo de fecha
        editTextFecha.setOnClickListener(v -> mostrarDatePicker());

        // Configurar el campo de hora
        editTextHora.setOnClickListener(v -> mostrarTimePicker());

        // Inicializar Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Cargar datos para los Spinners
        cargarLugares();
        cargarOferentes();
        cargarProyectos();
        cargarSociosComunitarios();
        cargarTiposActividad();

        // Configurar el botón de guardar
        btnGuardar.setOnClickListener(v -> guardarActividad());
    }

    private void mostrarDatePicker() {
        // Obtener la fecha actual
        final Calendar calendar = Calendar.getInstance();
        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        // Mostrar el DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            // Formatear la fecha seleccionada
            String fechaSeleccionada = dayOfMonth + "/" + (month + 1) + "/" + year;
            editTextFecha.setText(fechaSeleccionada);
        }, anio, mes, dia);

        datePickerDialog.show();
    }

    private void mostrarTimePicker() {
        // Obtener la hora actual
        final Calendar calendar = Calendar.getInstance();
        int horaActual = calendar.get(Calendar.HOUR_OF_DAY);
        int minutoActual = calendar.get(Calendar.MINUTE);

        // Mostrar el TimePickerDialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            // Formatear la hora seleccionada
            String horaSeleccionada = String.format("%02d:%02d", hourOfDay, minute);
            editTextHora.setText(horaSeleccionada);
        }, horaActual, minutoActual, true);

        timePickerDialog.show();
    }

    private void cargarLugares() {
        databaseReference.child("lugares").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                lugares.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Lugar lugar = data.getValue(Lugar.class);
                    if (lugar != null) {
                        lugares.add(lugar.getNombre());
                    }
                }
                spinnerLugar.setAdapter(new ArrayAdapter<>(CrearActividadActivity.this, android.R.layout.simple_spinner_dropdown_item, lugares));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(CrearActividadActivity.this, "Error al cargar lugares", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarOferentes() {
        databaseReference.child("oferentes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                oferentes.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Oferente oferente = data.getValue(Oferente.class);
                    if (oferente != null) {
                        oferentes.add(oferente.getNombre());
                    }
                }
                spinnerOferente.setAdapter(new ArrayAdapter<>(CrearActividadActivity.this, android.R.layout.simple_spinner_dropdown_item, oferentes));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(CrearActividadActivity.this, "Error al cargar oferentes", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarProyectos() {
        databaseReference.child("proyectos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                proyectos.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Proyecto proyecto = data.getValue(Proyecto.class);
                    if (proyecto != null) {
                        proyectos.add(proyecto.getNombre());
                    }
                }
                spinnerProyecto.setAdapter(new ArrayAdapter<>(CrearActividadActivity.this, android.R.layout.simple_spinner_dropdown_item, proyectos));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(CrearActividadActivity.this, "Error al cargar proyectos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarSociosComunitarios() {
        databaseReference.child("SociosComunitarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                sociosComunitarios.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    SocioComunitario socio = data.getValue(SocioComunitario.class);
                    if (socio != null) {
                        sociosComunitarios.add(socio.getNombre());
                    }
                }
                spinnerSocioComunitario.setAdapter(new ArrayAdapter<>(CrearActividadActivity.this, android.R.layout.simple_spinner_dropdown_item, sociosComunitarios));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(CrearActividadActivity.this, "Error al cargar socios comunitarios", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarTiposActividad() {
        databaseReference.child("tipos_actividad").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                tiposActividad.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    TipoActividad tipo = data.getValue(TipoActividad.class);
                    if (tipo != null) {
                        tiposActividad.add(tipo.getNombre());
                    }
                }
                spinnerTipoActividad.setAdapter(new ArrayAdapter<>(CrearActividadActivity.this, android.R.layout.simple_spinner_dropdown_item, tiposActividad));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(CrearActividadActivity.this, "Error al cargar tipos de actividad", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void guardarActividad() {
        String nombre = editTextNombre.getText().toString();
        String periodicidad = editTextPeriodicidad.getText().toString();
        String fecha = editTextFecha.getText().toString();
        String hora = editTextHora.getText().toString();

        String lugarSeleccionado = spinnerLugar.getSelectedItem().toString();
        String oferenteSeleccionado = spinnerOferente.getSelectedItem().toString();
        String proyectoSeleccionado = spinnerProyecto.getSelectedItem().toString();
        String socioSeleccionado = spinnerSocioComunitario.getSelectedItem().toString();
        String tipoSeleccionado = spinnerTipoActividad.getSelectedItem().toString();

        if (nombre.isEmpty() || fecha.isEmpty() || hora.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = databaseReference.child("actividades").push().getKey();
        Actividad actividad = new Actividad(id, nombre, periodicidad, fecha, hora, lugarSeleccionado, oferenteSeleccionado, proyectoSeleccionado, socioSeleccionado, tipoSeleccionado);

        databaseReference.child("actividades").child(id).setValue(actividad).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Actividad guardada exitosamente", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al guardar la actividad", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
