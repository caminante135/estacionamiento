package com.example.gerardo.miestacionamiento.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gerardo.miestacionamiento.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/*
A) drawables with theme attributes

ContextCompat.getDrawable(getActivity(), R.drawable.name);

B) drawables without theme attributes

ResourcesCompat.getDrawable(getResources(), R.drawable.name, null);

EXTRA) drawables with theme attributes from another theme

ResourcesCompat.getDrawable(getResources(), R.drawable.name, anotherTheme);
 */

/**
 * Created by Gerardo on 23/10/2016.
 */
public class TarjetaAdapter extends RecyclerView.Adapter<TarjetaAdapter.TarjetaViewHolder> {

    Context context;

    public TarjetaAdapter(Context context) {
        this.context = context;
    }

    @Override
    public TarjetaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_rv_tarjeta,parent,false);

        return new TarjetaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TarjetaViewHolder holder, int position) {
        Drawable res = null;
        String nombre = "";
        String numero = "";

        switch (position){
            case 0:
                res = ContextCompat.getDrawable(context,R.drawable.ic_mastercard);
                nombre = "MasterCard";
                numero = "5105105105105100";
                break;
            case 1:
                res = ContextCompat.getDrawable(context,R.drawable.ic_visa);
                nombre = "VISA";
                numero = "4012888888881881";
                break;
            case 2:
                res = ContextCompat.getDrawable(context,R.drawable.ic_americanexpress);
                nombre = "American Express";
                numero = "378282246310005";
                break;
            case 3:
                res = ContextCompat.getDrawable(context,R.drawable.ic_discover);
                nombre = "Discover";
                numero = "6011000990139424";
                break;
        }

        holder.setImgTarjeta(res);
        holder.setNombreTarjeta(nombre);
        holder.setNumeroTarjeta(numero);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class TarjetaViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.imgView_item_tarjeta)
        ImageView imgTarjeta;
        @Bind(R.id.txt_item_nombre_tarjeta)
        TextView nombreTarjeta;
        @Bind(R.id.txt_item_num_tarjeta)
        TextView numeroTarjeta;
        @Bind(R.id.btn_item_eliminar_tarjeta)
        Button btnEliminarTarjeta;


        public TarjetaViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setImgTarjeta(Drawable imageResource){
            imgTarjeta.setImageDrawable(imageResource);
        }

        public void setNombreTarjeta(String nombreTarjeta){
            this.nombreTarjeta.setText(nombreTarjeta);
        }

        public void setNumeroTarjeta(String numTarjeta){
            this.numeroTarjeta.setText(numTarjeta);
        }

    }


}
