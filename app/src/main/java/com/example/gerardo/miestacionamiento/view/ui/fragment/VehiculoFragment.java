package com.example.gerardo.miestacionamiento.view.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.controller.util.GlobalConstant;
import com.example.gerardo.miestacionamiento.controller.util.GlobalFunction;
import com.example.gerardo.miestacionamiento.model.Vehiculo;
import com.example.gerardo.miestacionamiento.view.ui.MainActivity;
import com.example.gerardo.miestacionamiento.view.ui.dialog.DialogWebPay;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class VehiculoFragment extends Fragment {

    public static final String ARGUMENTO_TIPO = "tipo";
    public static final String ARGUMENTO_PROP = "propietario";
    public static final String ARGUMENTO_ARREN = "arrendatario";


    @Bind(R.id.edit_vehiculo_patente)
    TextInputEditText editPatente;
    @Bind(R.id.edit_vehiculo_marca)
    AutoCompleteTextView editMarca;
    @Bind(R.id.edit_vehiculo_modelo)
    AutoCompleteTextView editModelo;
    @Bind(R.id.edit_vehiculo_color)
    AutoCompleteTextView editColor;
    @Bind(R.id.spn_vehiculo_tipo)
    Spinner spnTipo;
    @Bind(R.id.btN_vehiculo_siguiente)
    Button btnSiguiente;

    String jsonUsuario = null;


    public VehiculoFragment() {
        // Required empty public constructor
    }

    public static VehiculoFragment newInstance(String jsonUsuario) {
        VehiculoFragment vehiculoFragment = new VehiculoFragment();
        Bundle b = new Bundle();
        b.putString(GlobalConstant.BUNDLE_USUARIO, jsonUsuario);
        vehiculoFragment.setArguments(b);
        return vehiculoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        jsonUsuario = args.getString(GlobalConstant.BUNDLE_USUARIO);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_vehiculo, container, false);


        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Nuevo Vehículo");
    }


    @OnClick(R.id.btN_vehiculo_siguiente)
    public void sendData() {
        DialogWebPay fragment = DialogWebPay.newInstance(jsonUsuario, setIntentInfo(), null);
        fragment.show(getActivity().getSupportFragmentManager(), "webpayFragment");
    }


    private String setIntentInfo() {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPatente(editPatente.getText().toString().trim());
        vehiculo.setMarca(editMarca.getText().toString().trim());
        vehiculo.setModelo(editModelo.getText().toString().trim());
        vehiculo.setColor(editColor.getText().toString().trim());
        vehiculo.setTipoVehiculo((int) spnTipo.getSelectedItemId()+1);

        String json = GlobalFunction.createJSONObject(vehiculo);
        return json;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}





