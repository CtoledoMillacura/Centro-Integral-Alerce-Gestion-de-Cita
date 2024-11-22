package com.santotomas.centrointegralalerce_gestindecitas.Configuracion;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.santotomas.centrointegralalerce_gestindecitas.Model.Lugar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Context;
import com.santotomas.centrointegralalerce_gestindecitas.R;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class LugarAdapter extends RecyclerView.Adapter<LugarAdapter.LugarViewHolder> {

    @SuppressLint("RestrictedApi")
    private LugaresActivity context;
    private List<Lugar> lugarList;

    public LugarAdapter(LugaresActivity context, List<Lugar> lugarList) {
        this.context = context;
        this.lugarList = lugarList;
    }

    @NonNull
    @Override
    public LugarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lugar, parent, false);
        return new LugarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LugarViewHolder holder, int position) {
        Lugar lugar = lugarList.get(position);
        holder.nombre.setText(lugar.getNombre());
        holder.cupo.setText(lugar.getCupo() != null ? "Cupo: " + lugar.getCupo() : "Sin cupo");

        // Botón de editar
        holder.editarButton.setOnClickListener(view -> {
            // Llamar al método para mostrar el diálogo de edición
            mostrarDialogoEditarLugar(lugar);
        });

        // Botón de eliminar
        holder.eliminarButton.setOnClickListener(view -> {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("lugares");
            databaseReference.child(lugar.getId()).removeValue().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Lugar eliminado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void mostrarDialogoEditarLugar(Lugar lugar) {
        // Crear el cuadro de diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Editar Lugar");

        // Inflar el diseño personalizado para el cuadro
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_crear_lugar, null);
        builder.setView(view);

        // Referencias a los campos de entrada
        EditText inputNombre = view.findViewById(R.id.input_nombre_lugar);
        EditText inputCupo = view.findViewById(R.id.input_cupo_lugar);

        // Prellenar los campos con los datos existentes
        inputNombre.setText(lugar.getNombre());
        inputCupo.setText(lugar.getCupo() != null ? String.valueOf(lugar.getCupo()) : "");

        // Configurar los botones del cuadro de diálogo
        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String nuevoNombre = inputNombre.getText().toString().trim();
            String nuevoCupoTexto = inputCupo.getText().toString().trim();

            // Validar los campos
            if (nuevoNombre.isEmpty()) {
                Toast.makeText(context, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show();
                return;
            }

            Integer nuevoCupo = nuevoCupoTexto.isEmpty() ? null : Integer.parseInt(nuevoCupoTexto);

            // Actualizar los datos en Firebase
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("lugares");
            lugar.setNombre(nuevoNombre);
            lugar.setCupo(nuevoCupo);

            databaseReference.child(lugar.getId()).setValue(lugar).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Lugar actualizado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error al actualizar el lugar", Toast.LENGTH_SHORT).show();
                }
            });
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        // Mostrar el cuadro de diálogo
        builder.create().show();
    }


    @Override
    public int getItemCount() {
        return lugarList.size();
    }

    public static class LugarViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, cupo;
        Button editarButton, eliminarButton;

        public LugarViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.lugar_nombre);
            cupo = itemView.findViewById(R.id.lugar_cupo);
            editarButton = itemView.findViewById(R.id.editar_button);
            eliminarButton = itemView.findViewById(R.id.eliminar_button);
        }
    }
}