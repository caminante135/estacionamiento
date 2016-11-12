package com.example.gerardo.miestacionamiento.view.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.controller.util.GlobalConstant;
import com.example.gerardo.miestacionamiento.controller.util.GlobalFunction;
import com.example.gerardo.miestacionamiento.view.ui.MainActivity;
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


    @Bind(R.id.txt_estancia_fecha_llegada)
    TextView mFechaLlegada;
    @Bind(R.id.txt_estancia_fecha_salida)
    TextView mFechaSalida;
    @Bind(R.id.txt_estancia_cantidad)
    TextView mCantidadHoras;
    @Bind(R.id.btn_estancia_aceptar)
    Button mBtnAceptar;

    SwitchDateTimeDialogFragment dateTimeDialogFragment;
    String rutUsuario;
    int idEstacio;
    int cantH;
    String fechaHoraLlegada, fechaHoraSalida;

    public EstanciaFragment() {
        // Required empty public constructor
    }

    public static EstanciaFragment newInstance(String usuario, int est) {
        EstanciaFragment fragment = new EstanciaFragment();
        Bundle b = new Bundle();
//        b.putDouble("latitud", coordenadas.latitude);
//        b.putDouble("longitud", coordenadas.longitude);
        b.putString(GlobalConstant.BUNDLE_RUT_USUARIO,usuario);
        b.putInt(GlobalConstant.BUNDLE_ID_ESTACIO,est);
        fragment.setArguments(b);
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
        cantH = 0;
        Bundle args = getArguments();
        rutUsuario = args.getString(GlobalConstant.BUNDLE_RUT_USUARIO,"");
        idEstacio = args.getInt(GlobalConstant.BUNDLE_ID_ESTACIO,0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_estancia, container, false);


        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity.txtToolbar.setText("Tiempo de Estancia");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn_estancia_aceptar)
    public void onClick() {
        if (cantH > 0){
            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null)
                    .replace(R.id.frame,ResumenFragment.newInstance(
                            rutUsuario, idEstacio,cantH, fechaHoraLlegada,fechaHoraSalida))
                    .commitAllowingStateLoss();
        }else{
            Toast.makeText(getActivity(), "Necesita de al menos 1 hora de estadía para arrendar el estacionamiento",
                    Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.txt_estancia_fecha_llegada)
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

    @OnClick(R.id.txt_estancia_fecha_salida)
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
            cantH = GlobalFunction.hourBetweenDates(fechaHoraLlegada,fechaHoraSalida);
            if (cantH >= 0){
                mCantidadHoras.setText(getString(R.string.cantHoras,cantH));
            }else{
                mCantidadHoras.setText(getString(R.string.cantHoras,"-"));
            }

        }
    }

}
