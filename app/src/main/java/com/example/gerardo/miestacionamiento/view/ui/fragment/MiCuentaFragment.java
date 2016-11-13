package com.example.gerardo.miestacionamiento.view.ui.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.controller.util.GlobalConstant;
import com.example.gerardo.miestacionamiento.controller.util.GlobalFunction;
import com.example.gerardo.miestacionamiento.model.Estacionamiento;
import com.example.gerardo.miestacionamiento.model.Tarjeta;
import com.example.gerardo.miestacionamiento.model.Usuario;
import com.example.gerardo.miestacionamiento.model.Vehiculo;
import com.example.gerardo.miestacionamiento.view.adapter.EstacionamientoAdapter;
import com.example.gerardo.miestacionamiento.view.adapter.TarjetaAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

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
    @Bind(R.id.micuenta_layout_estacionamiento)
    LinearLayout layoutEst;
    @Bind(R.id.micuenta_layout_vehiculo)
    LinearLayout layoutVeh;
    @Bind(R.id.micuenta_layout_tarjeta)
    LinearLayout layoutTar;


    EstacionamientoAdapter adapterEstacionamiento;
    TarjetaAdapter adapterTarjeta;

    public MiCuentaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mi_cuenta, container, false);
        ButterKnife.bind(this, root);
        txtCorreo.setSelected(true);

        setDatos();
        setRecyclers();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.txt_micuenta_clave)
    public void textVisible() {
        if (txtClave.getInputType() == (InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
            txtClave.setInputType(InputType.TYPE_CLASS_TEXT);
            txtClave.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye_off, 0);
        } else {
            txtClave.setInputType(InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
            txtClave.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_eye, 0);
        }
    }

    private void setDatos() {
        Usuario usuario = GlobalFunction.currentUsuario(getActivity());

        if (usuario != null) {
            txtRut.setText(usuario.getRut());
            txtTelefono.setText(String.valueOf(usuario.getTelefono()));
            txtCorreo.setText(usuario.getCorreo());
            txtClave.setText(usuario.getContraseña());
        }

    }

    private void setRecyclers() {
        SharedPreferences prefs = getActivity().getSharedPreferences(GlobalConstant.PREFS_NAME, Context.MODE_PRIVATE);
        String rut = prefs.getString(GlobalConstant.PREFS_RUT,"");
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        List<Estacionamiento> estacionamientos = realm.where(Estacionamiento.class).equalTo("rutUsuario",rut).findAll();
        List<Tarjeta> tarjetas = realm.where(Tarjeta.class).equalTo("rutUsuario",rut).findAll();
        realm.commitTransaction();
        if (prefs.getString(GlobalConstant.PREFS_JSON_VEHICULOS, "").equals("[]")) {
            layoutVeh.setVisibility(View.GONE);
        } else {
            Log.d("VEHICULO", prefs.getString(GlobalConstant.PREFS_JSON_VEHICULOS, ""));
        }
        if (prefs.getString(GlobalConstant.PREFS_JSON_ESTACIONAMIENTOS, "").equals("[]")) {
            layoutEst.setVisibility(View.GONE);
        } else {
            adapterEstacionamiento = new EstacionamientoAdapter(getActivity(),estacionamientos);
            if (estacionamientos.size()==1){
                ViewGroup.LayoutParams params=recyclerViewEstacionamiento.getLayoutParams();
                params.height= ViewGroup.LayoutParams.WRAP_CONTENT;
            }
            recyclerViewEstacionamiento.setAdapter(adapterEstacionamiento);
        }

        if (prefs.getString(GlobalConstant.PREFS_JSON_TARJETAS, "").equals("[]")) {
            layoutTar.setVisibility(View.GONE);
        } else {
            adapterTarjeta = new TarjetaAdapter(getActivity(),tarjetas);
            if (tarjetas.size()==1){
                ViewGroup.LayoutParams params=recyclerViewTarjeta.getLayoutParams();
                params.height= ViewGroup.LayoutParams.WRAP_CONTENT;
            }
            recyclerViewTarjeta.setAdapter(adapterTarjeta);
        }


    }

}
