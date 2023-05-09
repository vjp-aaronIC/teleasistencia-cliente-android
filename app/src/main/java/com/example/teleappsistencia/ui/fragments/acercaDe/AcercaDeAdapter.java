package com.example.teleappsistencia.ui.fragments.acercaDe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teleappsistencia.R;
import com.example.teleappsistencia.modelos.Alarma;
import com.example.teleappsistencia.modelos.Desarrollador;
import com.example.teleappsistencia.modelos.Desarrollador_tecnologia;
import com.example.teleappsistencia.modelos.TecnologiaRel;
import com.example.teleappsistencia.ui.fragments.alarma.AlarmaAdapter;
import com.example.teleappsistencia.ui.fragments.alarma.ConsultarAlarmaFragment;
import com.example.teleappsistencia.utilidades.Constantes;
import com.example.teleappsistencia.utilidades.Utilidad;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.time.temporal.ValueRange;
import java.util.List;

public class AcercaDeAdapter extends RecyclerView.Adapter<AcercaDeAdapter.AcercaDeViewHolder> {
    private List<Desarrollador> items;

    private AcercaDeViewHolder acercaDeviewHolder;

    private Desarrollador desarrolladorSeleccionado;

    private OnItemSelectedListener listener;

    private int selectedPosition = RecyclerView.NO_POSITION;

    public interface OnItemSelectedListener {
        void onItemSelected(int position);
    }

    public Desarrollador getDesarrolladorSeleccionado() {
        return desarrolladorSeleccionado;
    }

    public Desarrollador getItemAtPosition(int position) {
        return items.get(position);
    }



    public static class AcercaDeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Context context;

        private TextView textViewNombreDesarrollador;

        private ImageView foto_perfil;

        private ImageView imagen_tecnologia1;

        private ImageView imagen_tecnologia2;

        private ImageView imagen_tecnologia3;

        private Desarrollador desarrollador;

        public AcercaDeViewHolder(View v){
            super(v);
            this.context = v.getContext();
            this.textViewNombreDesarrollador = (TextView) v.findViewById(R.id.textViewNombreDesarrolladorCard);
            this.foto_perfil = (ImageView) v.findViewById(R.id.imagenPerfilDesarrolladorCard);
            this.imagen_tecnologia1 = (ImageView) v.findViewById(R.id.tecnologia_uno);
            this.imagen_tecnologia2 = (ImageView) v.findViewById(R.id.tecnologia_dos);
            this.imagen_tecnologia3 = (ImageView) v.findViewById(R.id.tecnologia_tres);
        }

        @Override
        public void onClick(View view) {

        }

        // pone un desarrollador
        public void setDesarrollador(Desarrollador desarrollador) {
            this.desarrollador = desarrollador;
        }

        public void setOnClickListeners() {
        }

        public void consultarDesarrollador(Desarrollador desarrolladorSeleccionado){
            AppCompatActivity activity = (AppCompatActivity) context;
            Bundle agrs = new Bundle();
            agrs.putSerializable(Constantes.ARG_DESARROLLADOR,desarrolladorSeleccionado);
            ConsultarDesarrolladorFragment consultarDesarrolladorFragment = new ConsultarDesarrolladorFragment();
            consultarDesarrolladorFragment.setArguments(agrs);
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_fragment, consultarDesarrolladorFragment)
                    .addToBackStack(null)
                    .commit();
        }


    }

    public AcercaDeAdapter(List<Desarrollador> items){
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @NonNull
    @Override
    public AcercaDeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        // Pasar los argumentos al Fragment
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_desarrollador_card, viewGroup, false);
        return new AcercaDeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AcercaDeViewHolder viewHolder, int i) {
        viewHolder.setOnClickListeners();

        Desarrollador desarrollador = items.get(i);
        viewHolder.setDesarrollador(desarrollador);

        viewHolder.textViewNombreDesarrollador.setText(desarrollador.getNombre());

        Picasso.get().load(desarrollador.getImagen()).into(viewHolder.foto_perfil);


        //Desarrollador_tecnologia desarrollador_tecnologia = (Desarrollador_tecnologia) Utilidad.getObjeto(desarrollador.getlDesarrollador_tecnologia().get(0), Constantes.DESARROLLADOR_TECNOLOGIA);
        //TecnologiaRel tecnologiaRel = (TecnologiaRel) Utilidad.getObjeto(desarrollador_tecnologia.getId_tecnologia(), Constantes.TECNOLOGIAREL);

        //Picasso.get().load(tecnologiaRel.getImagen()).resize(150, 150).into(viewHolder.imagen_tecnologia);
        for (int j = 0; j < desarrollador.getlDesarrollador_tecnologia().size(); j++) {
            if(desarrollador.getlDesarrollador_tecnologia().get(j) != null){
                Desarrollador_tecnologia desarrollador_tecnologia = (Desarrollador_tecnologia) Utilidad.getObjeto(desarrollador.getlDesarrollador_tecnologia().get(j), Constantes.DESARROLLADOR_TECNOLOGIA);
                TecnologiaRel tecnologiaRel = (TecnologiaRel) Utilidad.getObjeto(desarrollador_tecnologia.getId_tecnologia(), Constantes.TECNOLOGIAREL);
                switch (j){
                    case 0:
                        Picasso.get().load(tecnologiaRel.getImagen()).resize(150, 150).into(viewHolder.imagen_tecnologia1);
                        break;
                    case 1:
                        Picasso.get().load(tecnologiaRel.getImagen()).resize(150, 150).into(viewHolder.imagen_tecnologia2);
                        break;
                    case 2:
                        Picasso.get().load(tecnologiaRel.getImagen()).resize(150, 150).into(viewHolder.imagen_tecnologia3);
                        break;
                }
            }
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Actualizar la posición seleccionada y notificar al adapter
                int previousSelectedPosition = selectedPosition;
                selectedPosition = viewHolder.getAdapterPosition();
                notifyItemChanged(previousSelectedPosition);
                notifyItemChanged(selectedPosition);

                // Llamar al método onItemSelected de OnItemSelectedListener
                if (listener != null) {
                    listener.onItemSelected(selectedPosition);
                }
                desarrolladorSeleccionado= viewHolder.desarrollador;

                viewHolder.consultarDesarrollador(desarrolladorSeleccionado);
            }
        });
    }


}
