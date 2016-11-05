package com.example.gerardo.miestacionamiento.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.controller.util.EnumCardType;
import com.example.gerardo.miestacionamiento.model.Tarjeta;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
    List<Tarjeta> tarjetas;

    public TarjetaAdapter(Context context, String tarjetasArray) {
        this.context = context;

        this.tarjetas = convertJsonArrayList(tarjetasArray);
    }

    @Override
    public TarjetaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_rv_tarjeta,parent,false);

        return new TarjetaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TarjetaViewHolder holder, int position) {
        Tarjeta currentTarjeta = tarjetas.get(position);

        Drawable res = null;
        String nombre = "";


        switch (EnumCardType.detect(currentTarjeta.getNumeroTarjeta())){
            case MASTERCARD:
                res = ContextCompat.getDrawable(context,R.drawable.ic_mastercard);
                nombre = "MasterCard";

                break;
            case VISA:
                res = ContextCompat.getDrawable(context,R.drawable.ic_visa);
                nombre = "VISA";

                break;
            case AMERICAN_EXPRESS:
                res = ContextCompat.getDrawable(context,R.drawable.ic_americanexpress);
                nombre = "American Express";

                break;
            case UNKNOWN:
                res = ContextCompat.getDrawable(context,R.drawable.ic_discover);
                nombre = "Unknow";

                break;
        }
        holder.setImgTarjeta(res);
        holder.setNombreTarjeta(nombre);
        holder.setNumeroTarjeta(currentTarjeta.getNumeroTarjeta());
    }

    @Override
    public int getItemCount() {
        return tarjetas.size();
    }

    private List<Tarjeta> convertJsonArrayList(String array){
        Type type = new TypeToken<List<Tarjeta>>(){}.getType();
        List<Tarjeta> tarjetaList = new Gson().fromJson(array,type);
        return tarjetaList;
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
