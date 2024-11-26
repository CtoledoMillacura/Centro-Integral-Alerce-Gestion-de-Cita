package com.santotomas.centrointegralalerce_gestindecitas.Configuracion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.santotomas.centrointegralalerce_gestindecitas.Model.Proyecto;
import com.santotomas.centrointegralalerce_gestindecitas.R;

import java.util.ArrayList;

public class ProyectosAdapter extends RecyclerView.Adapter<ProyectosAdapter.ViewHolder> {

    private ArrayList<Proyecto> listaProyectos;
    private Context context;

    public ProyectosAdapter(ArrayList<Proyecto> listaProyectos, Context context) {
        this.listaProyectos = listaProyectos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_proyecto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Proyecto proyecto = listaProyectos.get(position);
        holder.nombre.setText(proyecto.getNombre());

        holder.btnEditar.setOnClickListener(view -> {
            if (context instanceof ProyectosActivity) {
                ((ProyectosActivity) context).editarProyecto(proyecto);
            }
        });

        holder.btnEliminar.setOnClickListener(view -> {
            if (context instanceof ProyectosActivity) {
                ((ProyectosActivity) context).eliminarProyecto(proyecto);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaProyectos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        Button btnEditar, btnEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.proyecto_nombre);
            btnEditar = itemView.findViewById(R.id.btn_editar);
            btnEliminar = itemView.findViewById(R.id.btn_eliminar);
        }
    }
}
