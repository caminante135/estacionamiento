package com.example.gerardo.miestacionamiento.ui.fragment;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.adapter.EstacionamientoAdapter;
import com.example.gerardo.miestacionamiento.model.Usuario;
import com.example.gerardo.miestacionamiento.util.GlobalFunction;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    EstacionamientoAdapter adapter;

    public MiCuentaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mi_cuenta, container, false);
        ButterKnife.bind(this, root);
        txtCorreo.setSelected(true);

        adapter = new EstacionamientoAdapter(getActivity());
        recyclerViewEstacionamiento.setAdapter(adapter);

        setDatos();

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
                InputType.TYPE_TEXT_VARIATION_PASSWORD)){
            txtClave.setInputType(InputType.TYPE_CLASS_TEXT);
            txtClave.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_eye_off,0);
        }else{
            txtClave.setInputType(InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
            txtClave.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_eye,0);
        }
    }

    private void setDatos(){
        Usuario usuario = GlobalFunction.currentUsuario(getActivity());

        if (usuario != null){

            txtRut.setText(usuario.getRut());
            txtTelefono.setText(String.valueOf(usuario.getTelefono()));
            txtCorreo.setText(usuario.getCorreo());
            txtClave.setText(usuario.getContraseña());
        }

    }

}
