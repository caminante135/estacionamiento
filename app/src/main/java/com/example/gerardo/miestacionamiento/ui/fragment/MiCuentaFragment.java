package com.example.gerardo.miestacionamiento.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gerardo.miestacionamiento.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MiCuentaFragment extends Fragment {


    @Bind(R.id.txt_micuenta_rut)
    TextView txtRut;
    @Bind(R.id.txt_micuenta_telefono)
    TextView txtTelefono;
    @Bind(R.id.txt_micuenta_correo)
    TextView txtCorreo;
    @Bind(R.id.txt_micuenta_clave)
    TextView txtClave;
    @Bind(R.id.recyclerView_micuenta_estacionamiento)
    RecyclerView recyclerViewEstacionamiento;
    @Bind(R.id.recyclerView_micuenta_vehiculo)
    RecyclerView recyclerViewVehiculo;
    @Bind(R.id.recyclerView_micuenta_tarjeta)
    RecyclerView recyclerViewTarjeta;

    public MiCuentaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mi_cuenta, container, false);
        ButterKnife.bind(this, root);
        txtCorreo.setSelected(true);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
