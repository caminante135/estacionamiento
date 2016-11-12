package com.example.gerardo.miestacionamiento.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.controller.util.GlobalConstant;
import com.example.gerardo.miestacionamiento.model.Estacionamiento;

import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Gerardo on 14/10/2016.
 */
public class EstacionamientoAdapter extends RecyclerView.Adapter<EstacionamientoAdapter.EstacionamientoViewHolder> {

    Context context;
    List<Estacionamiento> estacionamientos;
    public EstacionamientoAdapter(Context context, List<Estacionamiento> listaEst) {
        this.context = context;
        estacionamientos = listaEst;
    }

    @Override
    public EstacionamientoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_rv_estacionamiento,parent,false);

        return new EstacionamientoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EstacionamientoViewHolder holder, int position) {
        Estacionamiento est = estacionamientos.get(position);
        holder.setComuna("San Joaquín");
        holder.setDireccion(est.getDireccionEstacionamiento());
        if (est.getIdEstado() == GlobalConstant.ESTACIONAMIENTO_DISPONIBLE){
            holder.setEstado("Disponible");
            holder.setEstadoDrawable(R.drawable.ic_marker_green);
            holder.setImgEstado(R.drawable.available);
        }else{
            holder.setEstado("No disponible");
            holder.setEstadoDrawable(R.drawable.ic_marker_red);
            holder.setImgEstado(R.drawable.unavailable);
        }
        holder.setValorHora(String.format(new Locale("es","ES"),"$%1$d",est.getCostoHora()));
    }

    @Override
    public int getItemCount() {
        return estacionamientos.size();
    }

    public class EstacionamientoViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.txt_item_est_comuna)
        TextView comuna;
        @Bind(R.id.txt_item_est_direccion)
        TextView direccion;
        @Bind(R.id.txt_item_est_estado)
        TextView estado;
        @Bind(R.id.txt_item_est_valor_hora)
        TextView valorHora;
        @Bind(R.id.txt_item_est_editar)
        TextView editar;
        @Bind(R.id.txt_item_est_eliminar)
        TextView eliminar;
        @Bind(R.id.img_item_est_estado)
        ImageView imgEstado;

        public EstacionamientoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setComuna(String comuna) {
            this.comuna.setText(comuna);
        }

        public void setDireccion(String direccion) {
            this.direccion.setText(direccion);
        }

        public void setEstado(String estado) {
            this.estado.setText(estado);
        }
        public void setEstadoDrawable(int estado) {
            this.estado.setCompoundDrawablesWithIntrinsicBounds(estado,0,0,0);
        }

        public void setValorHora(String valorHora) {
            this.valorHora.setText(valorHora);
        }

        public void setImgEstado(int imgEstado) {
            this.imgEstado.setImageResource(imgEstado);
        }
    }


}
