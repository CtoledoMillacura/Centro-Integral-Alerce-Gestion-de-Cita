package com.santotomas.centrointegralalerce_gestindecitas.Actividad;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.santotomas.centrointegralalerce_gestindecitas.Model.Actividad;
import com.santotomas.centrointegralalerce_gestindecitas.R;

import java.util.ArrayList;

public class ActividadAdapter extends ArrayAdapter<Actividad> {

    private Context context;
    private ArrayList<Actividad> actividades;

    public ActividadAdapter(Context context, ArrayList<Actividad> actividades) {
        super(context, R.layout.item_actividad, actividades); // Cambié el 0 por R.layout.item_actividad
        this.context = context;
        this.actividades = actividades;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_actividad, parent, false);
        }

        // Obtener la actividad actual
        Actividad actividad = actividades.get(position);

        // Referencias a los elementos de la vista
        TextView textViewNombre = convertView.findViewById(R.id.textViewNombre);
        TextView textViewFecha = convertView.findViewById(R.id.textViewFecha);
        TextView textViewHora = convertView.findViewById(R.id.textViewHora);
        TextView textViewLugar = convertView.findViewById(R.id.textViewLugar);

        // Establecer los valores de la actividad
        textViewNombre.setText(actividad.getNombre());
        textViewFecha.setText("Fecha: " + actividad.getFecha());
        textViewHora.setText("Hora: " + actividad.getHora());
        textViewLugar.setText("Lugar: " + actividad.getIdLugar());

        // Lógica para ver todos los datos de la actividad al hacer clic en la vista
        convertView.setOnClickListener(v -> {
            mostrarDetallesActividad(actividad);
        });

        return convertView;
    }

    // Método para mostrar todos los detalles de la actividad
    private void mostrarDetallesActividad(Actividad actividad) {
        String detalles = "Nombre: " + actividad.getNombre() +
                "\nFecha: " + actividad.getFecha() +
                "\nHora: " + actividad.getHora() +
                "\nLugar: " + actividad.getIdLugar() +
                "\nOferente: " + actividad.getIdOferente() +
                "\nProyecto: " + actividad.getIdProyecto() +
                "\nSocio Comunitario: " + actividad.getIdSocioComunitario() +
                "\nTipo de Actividad: " + actividad.getIdTipoActividad() +
                "\nPeriodicidad: " + actividad.getPeriodicidad();

        // Crear un cuadro de diálogo para mostrar los detalles
        new AlertDialog.Builder(context)
                .setTitle("Detalles de la Actividad")
                .setMessage(detalles)
                .setCancelable(true)
                .setPositiveButton("Cerrar", null)
                .show();
    }
}
