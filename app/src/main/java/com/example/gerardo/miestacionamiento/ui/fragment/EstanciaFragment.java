package com.example.gerardo.miestacionamiento.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.util.GlobalFunction;
import com.google.android.gms.maps.model.LatLng;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class EstanciaFragment extends Fragment {

    private static final String TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT";

    @Bind(R.id.btn_estancia_definir_llegada)
    Button mBtnDefinirLlegada;
    @Bind(R.id.txt_estancia_fecha_llegada)
    TextView mFechaLlegada;
    @Bind(R.id.btn_estancia_definir_salida)
    Button mBtnDefinirSalida;
    @Bind(R.id.txt_estancia_fecha_salida)
    TextView mFechaSalida;
    @Bind(R.id.txt_estancia_cantidad)
    TextView mCantidadHoras;
    @Bind(R.id.btn_estancia_aceptar)
    Button mBtnAceptar;

    SwitchDateTimeDialogFragment dateTimeDialogFragment;

    String fechaHoraLlegada, fechaHoraSalida;

    public EstanciaFragment() {
        // Required empty public constructor
    }

    public static EstanciaFragment newInstance() {
        EstanciaFragment fragment = new EstanciaFragment();
//        Bundle b = new Bundle();
//        b.putDouble("latitud", coordenadas.latitude);
//        b.putDouble("longitud", coordenadas.longitude);
//        fragment.setArguments(b);
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dateTimeDialogFragment = (SwitchDateTimeDialogFragment) getActivity()
                .getSupportFragmentManager().findFragmentByTag(TAG_DATETIME_FRAGMENT);

        if(dateTimeDialogFragment == null) {
            dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance(
                    getString(R.string.label_datetime_dialog),
                    getString(R.string.positive_button_datetime_picker),
                    getString(R.string.negative_button_datetime_picker)
            );
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_estancia, container, false);


        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn_estancia_aceptar)
    public void onClick() {
    }

    @OnClick(R.id.btn_estancia_definir_llegada)
    public void definirLlegada() {
        dateTimeDialogFragment.show(getActivity().getSupportFragmentManager(),TAG_DATETIME_FRAGMENT);

        dateTimeDialogFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                String fechaTemp = GlobalFunction.formatDate(date.toString());
                mFechaLlegada.setText(fechaTemp);
                fechaHoraLlegada = fechaTemp;
                setCantHoras();
            }

            @Override
            public void onNegativeButtonClick(Date date) {

            }
        });

    }

    @OnClick(R.id.btn_estancia_definir_salida)
    public void definirSalida() {
        dateTimeDialogFragment.show(getActivity().getSupportFragmentManager(),TAG_DATETIME_FRAGMENT);
        if (fechaHoraSalida != null){

        }
        dateTimeDialogFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                String fechaTemp = GlobalFunction.formatDate(date.toString());
                mFechaSalida.setText(fechaTemp);
                fechaHoraSalida = fechaTemp;
                setCantHoras();
            }

            @Override
            public void onNegativeButtonClick(Date date) {

            }
        });

    }

    private void setCantHoras(){
        if (fechaHoraLlegada != null && fechaHoraSalida != null){
            int cantH = GlobalFunction.hourBetweenDates(fechaHoraLlegada,fechaHoraSalida);
            if (cantH >= 0){
                mCantidadHoras.setText(getString(R.string.cantHoras,cantH));
            }else{
                mCantidadHoras.setText(getString(R.string.cantHoras,"-"));
            }

        }
    }

}
