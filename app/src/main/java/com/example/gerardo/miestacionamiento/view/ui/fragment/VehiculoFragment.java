package com.example.gerardo.miestacionamiento.view.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.controller.util.GlobalConstant;
import com.example.gerardo.miestacionamiento.controller.GlobalFunction;
import com.example.gerardo.miestacionamiento.model.Marca;
import com.example.gerardo.miestacionamiento.model.Vehiculo;
import com.example.gerardo.miestacionamiento.view.ui.dialog.DialogWebPay;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.Sort;

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
    Realm realm;

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

        realm = Realm.getDefaultInstance();
        setAutoCompleteMarca();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Nuevo Vehículo");
    }


    @OnClick(R.id.btN_vehiculo_siguiente)
    public void sendData() {
        if (editPatente.getText().toString().length()==0 ||editMarca.getText().toString().length()==0 ||
                editModelo.getText().toString().length()==0 ||editColor.getText().toString().length()==0){
            Toast.makeText(getActivity(), "Debes completar todos los campos antes de enviar", Toast.LENGTH_SHORT).show();

        }else{
            DialogWebPay fragment = DialogWebPay.newInstance(jsonUsuario, setIntentInfo(), null,null);
            fragment.show(getActivity().getSupportFragmentManager(), "webpayFragment");
        }

    }


    private String setIntentInfo() {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPatente(editPatente.getText().toString().trim());
        vehiculo.setIdMarca(String.valueOf(1));
        vehiculo.setTipoVehiculo((int) spnTipo.getSelectedItemId()+1);
        vehiculo.setRutPropietario("170992210");
        try {
            JSONObject usuarioObject = new JSONObject(jsonUsuario);
            vehiculo.setRutUsuario(usuarioObject.getString("rutUsuario"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = GlobalFunction.createJSONObject(vehiculo);
        return json;

    }

    private void setAutoCompleteMarca(){
        final List<Marca> marcas = realm.where(Marca.class).findAllSorted("nombre", Sort.ASCENDING);
        final String[] nombreMarcas = new String[marcas.size()];
        for (int i = 0; i < marcas.size(); i++) {
            nombreMarcas[i] = marcas.get(i).getNombre();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, nombreMarcas);
        editMarca.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}





