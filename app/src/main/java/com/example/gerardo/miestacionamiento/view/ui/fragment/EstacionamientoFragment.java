package com.example.gerardo.miestacionamiento.view.ui.fragment;


import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.controller.util.GlobalConstant;
import com.example.gerardo.miestacionamiento.controller.util.GlobalFunction;
import com.example.gerardo.miestacionamiento.model.Comuna;
import com.example.gerardo.miestacionamiento.model.Estacionamiento;
import com.example.gerardo.miestacionamiento.view.ui.dialog.DialogWebPay;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * A simple {@link Fragment} subclass.
 */
public class EstacionamientoFragment extends Fragment {


    @Bind(R.id.spn_estacionamiento_comuna)
    Spinner spnComuna;
    @Bind(R.id.edit_estacionamiento_direccion)
    TextInputEditText editDireccion;
    @Bind(R.id.rb_estacionamiento_tipo_casa)
    RadioButton rbCasa;
    @Bind(R.id.rb_estacionamiento_tipo_dpto)
    RadioButton rbDpto;
    @Bind(R.id.rg_estacionamiento_tipo)
    RadioGroup rgTipo;
    @Bind(R.id.edit_estacionamiento_numero)
    TextInputEditText editNumero;
    @Bind(R.id.rb_estacionamiento_camara_si)
    RadioButton rbCamaraSi;
    @Bind(R.id.rb_estacionamiento_camara_no)
    RadioButton rbCamaraNo;
    @Bind(R.id.rg_estacionamiento_camara)
    RadioGroup rgCamara;
    @Bind(R.id.edit_estacionamiento_piso)
    TextInputEditText editPiso;
    @Bind(R.id.edit_estacionamiento_valor_hora)
    TextInputEditText editValorHora;
    @Bind(R.id.btn_estacionamiento_siguiente)
    Button btnSiguiente;

    String jsonUsuario;

    public EstacionamientoFragment() {
        // Required empty public constructor
    }

    public static EstacionamientoFragment newInstance(String jsonUsuario) {
        EstacionamientoFragment fragment = new EstacionamientoFragment();
        Bundle b = new Bundle();
        b.putString(GlobalConstant.BUNDLE_USUARIO, jsonUsuario);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        jsonUsuario = args.getString(GlobalConstant.BUNDLE_USUARIO, "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_estacionamiento, container, false);
        ButterKnife.bind(this, root);

        setSpinner();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Nuevo Estacionamiento");
    }

//
    private String setIntentInfo( LatLng coordenadas){
        Estacionamiento est = new Estacionamiento();
        est.setAltura(2.0);
        est.setLargo(6.0);
        est.setAncho(3.0);
        if (!editPiso.getText().toString().equals("")){
            est.setPisoUbicacion(Integer.parseInt(editPiso.getText().toString()));
        }else{
            est.setPisoUbicacion(0);
        }
        if (!editNumero.getText().toString().equals("")){
            est.setNumeroEst(Integer.parseInt(editNumero.getText().toString()));
        }else{
            est.setNumeroEst(0);
        }
        est.setIdEstado(1);
        if (rbCamaraSi.isChecked()){
            est.setCamaraVigilancia(1);
        }else{
            est.setCamaraVigilancia(0);
        }
        est.setTipoEstacionamiento(1);
        est.setDireccionEstacionamiento(editDireccion.getText().toString());
        est.setIdComuna(GlobalFunction.getComunaIDbyNombre(spnComuna.getSelectedItem().toString()));
        est.setCostoHora(Integer.parseInt(editValorHora.getText().toString()));
        est.setLatitud(String.valueOf(coordenadas.latitude));
        est.setLongitud(String.valueOf(coordenadas.longitude));

        try {
            JSONObject usuarioObject = new JSONObject(jsonUsuario);
            est.setRutUsuario(usuarioObject.getString("rutUsuario"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = GlobalFunction.createJSONObject(est);
        return json;

    }

    private void setSpinner() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<Comuna> comunas = realm.where(Comuna.class).findAllSorted("nombreComuna", Sort.ASCENDING);
        realm.commitTransaction();
        String[] nombres = new String[comunas.size()];

        for (int i = 0; i < comunas.size(); i++) {
            nombres[i] = comunas.get(i).getNombreComuna();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_text, nombres);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spnComuna.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private boolean validarCampos() {
        if (rgTipo.getCheckedRadioButtonId() == -1) {
            return false;
        }
        if (editDireccion.getText().toString().equals("")) {
            return false;
        }
        if (editValorHora.getText().toString().equals("")) {
            return false;
        }
        if (rgCamara.getCheckedRadioButtonId() == -1) {
            return false;
        }
        return true;
    }

    @OnClick(R.id.btn_estacionamiento_siguiente)
    public void onClick() {
        if (validarCampos()) {
            LatLng latLng = GlobalFunction.getCoordinatesFromAddress(getActivity(),String.format(new Locale("es", "CL"), "%1$s, %2$s",
                    editDireccion.getText().toString().trim(),
                    spnComuna.getSelectedItem().toString()));
            if (latLng != null) {
                String jsonEst = setIntentInfo(latLng);
                DialogWebPay fragment = DialogWebPay.newInstance(jsonUsuario, null, jsonEst);
                fragment.show(getActivity().getSupportFragmentManager(), "webpayFragment");
            }else{
                Toast.makeText(getActivity(), "La dirección no se pudo encontrar", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getActivity(), "Debe completar todos los campos obligatorios antes de continuar", Toast.LENGTH_SHORT).show();
        }


    }
}
