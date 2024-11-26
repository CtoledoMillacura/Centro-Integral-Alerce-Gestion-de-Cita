package com.santotomas.centrointegralalerce_gestindecitas.Configuracion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.santotomas.centrointegralalerce_gestindecitas.Model.TipoActividad;
import com.santotomas.centrointegralalerce_gestindecitas.R;

import java.util.List;

public class TipoActividadAdapter extends RecyclerView.Adapter<TipoActividadAdapter.TipoActividadViewHolder> {

    private TiposActividadActivity context;
    private List<TipoActividad> tipoActividadList;

    public TipoActividadAdapter(TiposActividadActivity context, List<TipoActividad> tipoActividadList) {
        this.context = context;
        this.tipoActividadList = tipoActividadList;
    }

    @Override
    public TipoActividadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tipo_actividad, parent, false);
        return new TipoActividadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TipoActividadViewHolder holder, int position) {
        TipoActividad tipoActividad = tipoActividadList.get(position);
        holder.nombre.setText(tipoActividad.getNombre());
        holder.descripcion.setText(tipoActividad.getDescripcion());

        // Configurar el botón de editar
        holder.btnEditar.setOnClickListener(view -> context.editarTipoActividad(tipoActividad));

        // Configurar el botón de eliminar con confirmación
        holder.btnEliminar.setOnClickListener(view -> {
            // Crear un cuadro de diálogo de confirmación
            new AlertDialog.Builder(context)
                    .setTitle("Confirmar Eliminación")
                    .setMessage("¿Estás seguro de que deseas eliminar este tipo de actividad?")
                    .setPositiveButton("Sí", (dialog, which) -> context.eliminarTipoActividad(tipoActividad))
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return tipoActividadList.size();
    }

    public static class TipoActividadViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, descripcion;
        Button btnEditar, btnEliminar;

        public TipoActividadViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.tipo_actividad_nombre);
            descripcion = itemView.findViewById(R.id.tipo_actividad_descripcion);
            btnEditar = itemView.findViewById(R.id.btn_editar);
            btnEliminar = itemView.findViewById(R.id.btn_eliminar);
        }
    }
}
