package com.example.gerardo.miestacionamiento.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.controller.GlobalFunction;
import com.example.gerardo.miestacionamiento.controller.util.GlobalConstant;
import com.example.gerardo.miestacionamiento.model.Arriendo;
import com.example.gerardo.miestacionamiento.model.Transaccion;

import java.util.Date;
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
        holder.setFechaIni(transaccion.getArriendoTo().getFechaInicio());
        holder.setFechaFin(transaccion.getArriendoTo().getFechaTermino());
        holder.setCosto((transaccion.getArriendo().getCostoHora()*transaccion.getArriendo().getHorasArrendadas()));

        switch (transaccion.getArriendo().getIdEstado()){
            case 1:
                holder.setImageEstado(R.drawable.ic_price);
                holder.setScaleType(ImageView.ScaleType.FIT_CENTER);
                holder.setEstado("Pagado");
                holder.setTextTitle("Arriendo Pagado");
                break;
            case 2:
                holder.setImageEstado(R.drawable.clock_logo);
                holder.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                holder.setColorBackgroundTitle(ContextCompat.getColor(context,R.color.backClockLogo));
                holder.setEstado("En arriendo");
                holder.setTextTitle("En Arriendo");
                break;
            case 3:
                holder.setImageEstado(R.drawable.available);
                holder.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                holder.setColorBackgroundTitle(ContextCompat.getColor(context,R.color.backAvailableLogo));
                holder.setEstado("Arriendo Finalizado");
                holder.setTextTitle("Arriendo Finalizado");
                break;
            default:
                holder.setImageEstado(R.drawable.available);
                holder.setScaleType(ImageView.ScaleType.FIT_CENTER);
                holder.setEstado("Pagado");
                holder.setTextTitle("Arriendo Pagado");
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
        @Bind(R.id.historialtitle)
        TextView historialTitle;

        public TransaccionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }

//        public void setDrawablasSize(){
//            historialFechaIni.getViewTreeObserver()
//                    .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                        @Override
//                        public void onGlobalLayout() {
//                            Drawable fechaIni = ContextCompat.getDrawable(context,R.drawable.ic_calendar_green);
//                            fechaIni.setBounds(0,0,70,70);
//                            historialFechaIni.setCompoundDrawables(fechaIni,null,null,null);
////                            historialFechaIni.removeOnLayoutChangeListener(this);
//                        }
//                    });
//        }

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
        public void setScaleType(ImageView.ScaleType type){
            historialImageEstado.setScaleType(type);
        }
        public void setEstado(String estado){
            historialEstado.setText(estado);
        }

        public void setColorBackgroundTitle(int color){
            historialTitle.setBackgroundColor(color);
        }

        public void setTextTitle(String title){
            historialTitle.setText(title);
        }
    }


}
