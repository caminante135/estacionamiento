package com.example.gerardo.miestacionamiento.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.controller.GlobalFunction;
import com.example.gerardo.miestacionamiento.controller.util.GlobalConstant;
import com.example.gerardo.miestacionamiento.model.Transaccion;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Gerardo on 27/11/2016.
 */
public class TransaccionAdapter extends RecyclerView.Adapter<TransaccionAdapter.TransaccionViewHolder> {

    Context context;
    List<Transaccion> transaccions;

    public TransaccionAdapter(Context context, List<Transaccion> transaccions) {
        this.context = context;
        this.transaccions = transaccions;
    }

    @Override
    public TransaccionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_rv_historial, parent, false);

        return new TransaccionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TransaccionViewHolder holder, int position) {
        Transaccion transaccion = transaccions.get(position);

        holder.setDireccion(GlobalFunction.getdireccionById(transaccion.getArriendo().getIdEstacionamiento()));
        holder.setFechaIni(transaccion.getArriendo().getFechaInicio());
        holder.setFechaFin(transaccion.getArriendo().getFechaTermino());
        holder.setCosto((transaccion.getArriendo().getCostoHora()*transaccion.getArriendo().getHorasArrendadas()));

        switch (transaccion.getArriendo().getIdEstado()){
            case 1:
                holder.setImageEstado(R.drawable.ic_price);
                holder.setEstado("Pagado");
                break;
            case 2:
                holder.setImageEstado(R.drawable.clock_logo);
                holder.setEstado("En arriendo");
                break;
            case 3:
                holder.setImageEstado(R.drawable.available);
                holder.setEstado("Arriendo Finalizado");
                break;
            default:
                holder.setImageEstado(R.drawable.available);
                holder.setEstado("Pagado");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return transaccions.size();
    }

    public class TransaccionViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.historial_rv_direccion)
        TextView historialDireccion;
        @Bind(R.id.historial_rv_fechaIni)
        TextView historialFechaIni;
        @Bind(R.id.historial_rv_fechaFin)
        TextView historialFechaFin;
        @Bind(R.id.historial_rv_costo)
        TextView historialCosto;
        @Bind(R.id.historial_rv_imageEstado)
        ImageView historialImageEstado;
        @Bind(R.id.historial_rv_txtEstado)
        TextView historialEstado;

        public TransaccionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setDireccion(String direccion){
            historialDireccion.setText(direccion);
        }
        public void setFechaIni(String fechaIni){
            historialFechaIni.setText(fechaIni);
        }
        public void setFechaFin(String fechaFin){
            historialFechaFin.setText(fechaFin);
        }
        public void setCosto(Integer costo){
            historialCosto.setText("$"+costo);
        }
        public void setImageEstado(int resource){
            historialImageEstado.setImageResource(resource);
        }
        public void setEstado(String estado){
            historialEstado.setText(estado);
        }
    }


}
