package com.example.gerardo.miestacionamiento.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.controller.GlobalFunction;
import com.example.gerardo.miestacionamiento.model.Evaluacion;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Gerardo on 26/11/2016.
 */
public class EvaluacionAdapter extends RecyclerView.Adapter<EvaluacionAdapter.EvaluacionViewHolder> {

    Context context;
    List<Evaluacion> comentarios;


    public EvaluacionAdapter(Context context, List<Evaluacion> comentarios) {
        this.context = context;
        this.comentarios = comentarios;
    }

    @Override
    public EvaluacionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_rv_comentarios, parent, false);

        return new EvaluacionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EvaluacionViewHolder holder, int position) {
        Evaluacion currentEvaluacion = comentarios.get(position);
        holder.setRatingBar(currentEvaluacion.getCalificacion());
        holder.setComentario(currentEvaluacion.getComentario());
        holder.setRutEvaluador(currentEvaluacion.getRutCalificador());
    }

    @Override
    public int getItemCount() {
        return comentarios.size();
    }

    class EvaluacionViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.comentario_calificacion)
        RatingBar rbCalificacion;
        @Bind(R.id.comentario_mensaje)
        TextView txtComentario;
        @Bind(R.id.comentario_rut)
        TextView txtRutEvaluador;

        public EvaluacionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setRatingBar(int calificacion){
            rbCalificacion.setRating(calificacion);
        }

        public void setComentario(String mensaje){
            txtComentario.setText(mensaje);
        }

        public void setRutEvaluador(String rut){
            txtRutEvaluador.setText(GlobalFunction.formatRutEvaluacion(rut));
        }
    }

}
