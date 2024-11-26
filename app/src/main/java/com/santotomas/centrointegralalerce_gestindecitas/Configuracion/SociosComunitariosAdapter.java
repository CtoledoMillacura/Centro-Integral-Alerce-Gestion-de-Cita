package com.santotomas.centrointegralalerce_gestindecitas.Configuracion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.santotomas.centrointegralalerce_gestindecitas.Model.SocioComunitario;
import com.santotomas.centrointegralalerce_gestindecitas.R;

import java.util.ArrayList;

public class SociosComunitariosAdapter extends RecyclerView.Adapter<SociosComunitariosAdapter.ViewHolder> {

    private ArrayList<SocioComunitario> listaSocios;
    private Context context;

    public SociosComunitariosAdapter(ArrayList<SocioComunitario> listaSocios, Context context) {
        this.listaSocios = listaSocios;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_socio_comunitario, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SocioComunitario socio = listaSocios.get(position);
        holder.nombre.setText(socio.getNombre());

        holder.btnEditar.setOnClickListener(view -> {
            if (context instanceof SociosComunitariosActivity) {
                ((SociosComunitariosActivity) context).editarSocio(socio);
            }
        });

        holder.btnEliminar.setOnClickListener(view -> {
            if (context instanceof SociosComunitariosActivity) {
                ((SociosComunitariosActivity) context).eliminarSocio(socio);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaSocios.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;
        Button btnEditar, btnEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.socio_comunitario_nombre);
            btnEditar = itemView.findViewById(R.id.btn_editar);
            btnEliminar = itemView.findViewById(R.id.btn_eliminar);
        }
    }
}
