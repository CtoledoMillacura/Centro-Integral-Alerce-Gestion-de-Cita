package com.santotomas.centrointegralalerce_gestindecitas.Configuracion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.santotomas.centrointegralalerce_gestindecitas.Model.Oferente;
import com.santotomas.centrointegralalerce_gestindecitas.R;

import java.util.ArrayList;

public class OferentesAdapter extends RecyclerView.Adapter<OferentesAdapter.ViewHolder> {

    private ArrayList<Oferente> listaOferentes;
    private Context context;

    public OferentesAdapter(ArrayList<Oferente> listaOferentes, Context context) {
        this.listaOferentes = listaOferentes;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_oferente, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Oferente oferente = listaOferentes.get(position);
        holder.nombre.setText(oferente.getNombre());
        holder.docenteResponsable.setText(oferente.getDocenteResponsable());
        holder.carrera.setText(oferente.getCarrera());

        holder.btnEditar.setOnClickListener(view -> {
            if (context instanceof OferentesActivity) {
                ((OferentesActivity) context).editarOferente(oferente);
            }
        });

        holder.btnEliminar.setOnClickListener(view -> {
            if (context instanceof OferentesActivity) {
                ((OferentesActivity) context).eliminarOferente(oferente);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaOferentes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, docenteResponsable, carrera;
        Button btnEditar, btnEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.oferente_nombre);
            docenteResponsable = itemView.findViewById(R.id.oferente_docente_responsable);
            carrera = itemView.findViewById(R.id.oferente_carrera);
            btnEditar = itemView.findViewById(R.id.btn_editar_oferente);
            btnEliminar = itemView.findViewById(R.id.btn_eliminar_oferente);
        }
    }
}
