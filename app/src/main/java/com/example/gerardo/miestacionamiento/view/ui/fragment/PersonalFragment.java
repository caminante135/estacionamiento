package com.example.gerardo.miestacionamiento.view.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.controller.util.GlobalConstant;
import com.example.gerardo.miestacionamiento.model.Usuario;
import com.example.gerardo.miestacionamiento.controller.util.GlobalFunction;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment {

    public static final String ARGUMENTO_TIPO = "tipo";
    public static final String ARGUMENTO_PROP = "propietario";
    public static final String ARGUMENTO_ARREN = "arrendatario";





    @Bind(R.id.rb_persona_tipo_prop)
    RadioButton rbTipoProp;
    @Bind(R.id.rb_persona_tipo_arre)
    RadioButton rbTipoArre;
    @Bind(R.id.rg_persona_tipo)
    RadioGroup rgTipo;
    @Bind(R.id.edit_personal_rut)
    TextInputEditText mEditRut;
    @Bind(R.id.edit_personal_nombre)
    TextInputEditText mEditNombre;
    @Bind(R.id.edit_personal_apellido_p)
    TextInputEditText mEditApellidoP;
    @Bind(R.id.edit_personal_apellido_m)
    TextInputEditText mEditApellidoM;
    @Bind(R.id.edit_personal_correo)
    TextInputEditText mEditCorreo;
    @Bind(R.id.edit_personal_telefono)
    TextInputEditText mEditTelefono;
    @Bind(R.id.edit_personal_clave)
    TextInputEditText mEditClave;
    @Bind(R.id.btn_personal_registrar)
    Button btnRegistrar;

    String tipoUsuario;

    public PersonalFragment() {
        // Required empty public constructor
    }

    public static PersonalFragment newInstance() {
        PersonalFragment personalFragment = new PersonalFragment();
//        Bundle b = new Bundle();
//        b.putString(ARGUMENTO_TIPO, tipo);
//        personalFragment.setArguments(b);
        return personalFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Bundle args = getArguments();
//        tipoUsuario = args.getString(ARGUMENTO_TIPO);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_personal, container, false);
        ButterKnife.bind(this, root);
        tipoUsuario = null;



        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


//    public void sendData() {
//        if (tipoUsuario.equals(ARGUMENTO_PROP)) {
//            EstacionamientoFragment fragment = EstacionamientoFragment.newInstance();
//            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.container, fragment);
//            ft.commitAllowingStateLoss();
//        } else {
//            if (tipoUsuario.equals(ARGUMENTO_ARREN)) {
//                VehiculoFragment fragment = VehiculoFragment.newInstance();
//                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.container, fragment);
//                ft.commitAllowingStateLoss();
//            }
//        }
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private boolean validarFormulario(){

        if (mEditRut.getText().toString().trim().equals("")){
            return false;
        }
        if (mEditNombre.getText().toString().trim().equals("")) {
            return false;
        }
        if (mEditApellidoP.getText().toString().trim().equals("")) {
            return false;
        }
        if (mEditApellidoM.getText().toString().trim().equals("")) {
            return false;
        }
        if (mEditCorreo.getText().toString().trim().equals("")){
            return false;
        }
        if (mEditTelefono.getText().toString().trim().equals("")){
            return false;
        }
        if (mEditClave.getText().toString().trim().equals("")){
            return false;
        }
        if (rgTipo.getCheckedRadioButtonId() == -1){
            return false;
        }
        return true;


    }

    @OnClick(R.id.btn_personal_registrar)
    public void onClick() {
        if (validarFormulario()){
            if (GlobalFunction.isRut(mEditRut.getText().toString().trim())){
                if (GlobalFunction.isValidEmail(mEditCorreo.getText().toString().trim())){

                    if (rbTipoProp.isChecked()){
                        changeFragment(ARGUMENTO_PROP);
                    }else{
                        changeFragment(ARGUMENTO_ARREN);
                    }

                }else{
                    Toast.makeText(getActivity(), "Correo inválido", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getActivity(), "RUT inválido", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(getActivity(), "Debe completar todos los campos antes de continuar", Toast.LENGTH_SHORT).show();
        }
    }


    private void changeFragment(String tipo){
        Fragment fragment = null;
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

        if (tipo.equals(ARGUMENTO_PROP)){
            fragment = EstacionamientoFragment.newInstance(setIntentInfo(GlobalConstant.TIPO_DUEÑO));
        }else{
            fragment = VehiculoFragment.newInstance(setIntentInfo(GlobalConstant.TIPO_CLIENTE));
        }

        if (fragment!=null){
            ft.replace(R.id.container,fragment);
            ft.addToBackStack(null);
            ft.commitAllowingStateLoss();
        }


    }

    private String setIntentInfo(int tipo){
        Usuario usuario = new Usuario();
        usuario.setRut(mEditRut.getText().toString().trim());
        usuario.setNombre(mEditNombre.getText().toString().trim());
        usuario.setApellidoPaterno(mEditApellidoP.getText().toString().trim());
        usuario.setApellidoMaterno(mEditApellidoM.getText().toString().trim());
        usuario.setCorreo(mEditCorreo.getText().toString().trim());
        usuario.setTelefono(Integer.parseInt(mEditTelefono.getText().toString().trim()));
        usuario.setContraseña(mEditClave.getText().toString().trim());
        usuario.setTipoUsuario(tipo);

        String json = GlobalFunction.createJSONObject(usuario);
        return json;

    }

}
