package com.example.gerardo.miestacionamiento.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gerardo.miestacionamiento.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Gerardo on 14/10/2016.
 */
public class EstacionamientoAdapter extends RecyclerView.Adapter<EstacionamientoAdapter.EstacionamientoViewHolder> {

    Context context;

    public EstacionamientoAdapter(Context context) {
        this.context = context;
    }

    @Override
    public EstacionamientoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_rv_estacionamiento,parent,false);

        return new EstacionamientoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EstacionamientoViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class EstacionamientoViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.txt_item_est_direccion)
        TextView direccion;
        @Bind(R.id.txt_item_est_estado)
        TextView estado;
        @Bind(R.id.txt_item_est_tamaño)
        TextView tamaño;
        @Bind(R.id.txt_item_est_editar)
        TextView editar;
        @Bind(R.id.txt_item_est_eliminar)
        TextView eliminar;

        public EstacionamientoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


}
