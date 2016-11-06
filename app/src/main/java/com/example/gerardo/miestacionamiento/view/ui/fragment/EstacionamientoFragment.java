package com.example.gerardo.miestacionamiento.view.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.gerardo.miestacionamiento.model.Estacionamiento;
import com.example.gerardo.miestacionamiento.model.Usuario;
import com.example.gerardo.miestacionamiento.view.ui.dialog.DialogWebPay;
import com.example.gerardo.miestacionamiento.controller.util.GlobalConstant;
import com.example.gerardo.miestacionamiento.controller.util.GlobalFunction;
import com.example.gerardo.miestacionamiento.R;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

/**
 * A simple {@link Fragment} subclass.
 */
public class EstacionamientoFragment extends Fragment implements VerticalStepperForm {


    VerticalStepperFormLayout verticalStepperForm;
    ViewGroup.LayoutParams lp;

    EditText mDireccion;
    EditText mComuna;
    EditText mPiso;
    RadioGroup mTipoResidencia;
    RadioGroup mCamaraVigilancia;
    EditText mNumEstacionamiento;
    EditText mValorHora;

    String jsonUsuario;

    public EstacionamientoFragment() {
        // Required empty public constructor
    }

    public static EstacionamientoFragment newInstance(String jsonUsuario) {
        EstacionamientoFragment fragment = new EstacionamientoFragment();
        Bundle b = new Bundle();
        b.putString(GlobalConstant.BUNDLE_USUARIO,jsonUsuario);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        jsonUsuario = args.getString(GlobalConstant.BUNDLE_USUARIO,"");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_estacionamiento, container, false);

        String[] mySteps = getActivity().getResources().getStringArray(R.array.itemsDatosEstacionamiento);

//        String[] mySubs = {"Ingre su nombre", "Ingresa tu EMAIL", "Ingresa tu numero de telefono"};
        int colorPrimary = ContextCompat.getColor(getActivity().getApplicationContext(), R.color.colorPrimary);
        int colorPrimaryDark = ContextCompat.getColor(getActivity().getApplicationContext(), R.color.colorPrimaryDark);

        verticalStepperForm = (VerticalStepperFormLayout) root.findViewById(R.id.vertical_stepper_form_estacionamiento);
        lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, GlobalFunction.ConvertDpToPx(35));

        // Setting up and initializing the form
        VerticalStepperFormLayout.Builder.newInstance(verticalStepperForm, mySteps, this, getActivity())
                .primaryColor(colorPrimary)
                .primaryDarkColor(colorPrimaryDark)
                .materialDesignInDisabledSteps(true)
                .showVerticalLineWhenStepsAreCollapsed(true)
//                .stepsSubtitles(mySubs)
                .displayBottomNavigation(true) // It is true by default, so in this case this line is not necessary
                .init();


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Nuevo Estacionamiento");
    }

    @Override
    public View createStepContentView(int stepNumber) {
        View view = null;
        switch (stepNumber) {
            case 0:
                view = crearViewComuna();
                break;
            case 1:
                view = crearViewDireccion();
                break;
            case 2:
                view = crearViewTipo();
                break;
            case 3:
                view = crearViewNumEst();
                break;
            case 4:
                view = crearViewCamaraVigilancia();
                break;
            case 5:
                view = crearviewPiso();
                break;
            case 6:
                view = crearViewValorHora();
                break;
        }
        return view;
    }

    @Override
    public void onStepOpening(int stepNumber) {
        switch (stepNumber) {
            case 0:
                verticalStepperForm.setActiveStepAsCompleted();
                break;
            case 1:
                verticalStepperForm.setActiveStepAsCompleted();
                break;
            case 2:
                verticalStepperForm.setActiveStepAsCompleted();
                break;
            case 3:
                verticalStepperForm.setActiveStepAsCompleted();
                break;
            case 4:
                verticalStepperForm.setActiveStepAsCompleted();
                break;
            case 5:
                verticalStepperForm.setActiveStepAsCompleted();
                break;
            case 6:
                verticalStepperForm.setStepAsCompleted(6);
                break;
        }
    }

    @Override
    public void sendData() {
        DialogWebPay fragment = DialogWebPay.newInstance(jsonUsuario,null,null);
        fragment.show(getActivity().getSupportFragmentManager(),"webpayFragment");
    }
//
//    private String setIntentInfo(int tipo){
//        Estacionamiento est = new Estacionamiento();
//        est.setIdComuna(mEditRut.getText().toString().trim());
//        usuario.setNombre(mEditNombre.getText().toString().trim());
//        usuario.setApellidoPaterno(mEditApellidoP.getText().toString().trim());
//        usuario.setApellidoMaterno(mEditApellidoM.getText().toString().trim());
//        usuario.setCorreo(mEditCorreo.getText().toString().trim());
//        usuario.setTelefono(Integer.parseInt(mEditTelefono.getText().toString().trim()));
//        usuario.setContraseña(mEditClave.getText().toString().trim());
//        usuario.setTipoUsuario(tipo);
//
//        String json = GlobalFunction.createJSONObject(usuario);
//        return json;
//
//    }

    private View crearViewComuna() {
        mComuna = new EditText(getActivity());
        mComuna.setSingleLine(true);
        mComuna.setLayoutParams(lp);
        mComuna.setHint(getActivity().getResources().getString(R.string.hintComuna));
        mComuna.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.whiteHint));
        mComuna.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryText));
        mComuna.setPadding(GlobalFunction.ConvertDpToPx(10), 0, GlobalFunction.ConvertDpToPx(10), 0);
        mComuna.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_text_background));
        return mComuna;
    }
    private View crearViewDireccion() {
        mDireccion = new EditText(getActivity());
        mDireccion.setSingleLine(true);
        mDireccion.setLayoutParams(lp);
        mDireccion.setHint(getActivity().getResources().getString(R.string.hintDireccion));
        mDireccion.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.whiteHint));
        mDireccion.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryText));
        mDireccion.setPadding(GlobalFunction.ConvertDpToPx(10), 0, GlobalFunction.ConvertDpToPx(10), 0);
        mDireccion.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_text_background));
        return mDireccion;
    }
    private View crearviewPiso() {
        mPiso = new EditText(getActivity());
        mPiso.setSingleLine(true);
        mPiso.setLayoutParams(lp);
        mPiso.setHint(getActivity().getResources().getString(R.string.hintPiso));
        mPiso.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.whiteHint));
        mPiso.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryText));
        mPiso.setPadding(GlobalFunction.ConvertDpToPx(10), 0, GlobalFunction.ConvertDpToPx(10), 0);
        mPiso.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_text_background));
        mPiso.setInputType(InputType.TYPE_CLASS_NUMBER);
        return mPiso;
    }
    private View crearViewTipo() {
        RadioButton[] radioButtons = new RadioButton[2];
        mTipoResidencia = new RadioGroup(getActivity());
        mTipoResidencia.setOrientation(RadioGroup.HORIZONTAL);

        String[] texts = {"Casa", "Departamento"};

        for (int i = 0; i < 2; i++) {
            radioButtons[i] = new RadioButton(getActivity());
            radioButtons[i].setText(texts[i]);
            mTipoResidencia.addView(radioButtons[i]);
        }

        return mTipoResidencia;

    }
    private View crearViewCamaraVigilancia() {
        RadioButton[] radioButtons = new RadioButton[2];
        mCamaraVigilancia = new RadioGroup(getActivity());
        mCamaraVigilancia.setOrientation(RadioGroup.HORIZONTAL);

        String[] texts = {"Si", "No"};

        for (int i = 0; i < 2; i++) {
            radioButtons[i] = new RadioButton(getActivity());
            radioButtons[i].setText(texts[i]);
            mCamaraVigilancia.addView(radioButtons[i]);
        }

        return mCamaraVigilancia;

    }
    private View crearViewNumEst() {
        mNumEstacionamiento = new EditText(getActivity());
        mNumEstacionamiento.setSingleLine(true);
        mNumEstacionamiento.setLayoutParams(lp);
        mNumEstacionamiento.setHint(getActivity().getResources().getString(R.string.hintNumeroEst));
        mNumEstacionamiento.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.whiteHint));
        mNumEstacionamiento.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryText));
        mNumEstacionamiento.setPadding(GlobalFunction.ConvertDpToPx(10), 0, GlobalFunction.ConvertDpToPx(10), 0);
        mNumEstacionamiento.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_text_background));
        mNumEstacionamiento.setInputType(InputType.TYPE_CLASS_NUMBER);
        return mNumEstacionamiento;
    }
    private View crearViewValorHora() {
        mValorHora = new EditText(getActivity());
        mValorHora.setSingleLine(true);
        mValorHora.setLayoutParams(lp);
        mValorHora.setHint(getActivity().getResources().getString(R.string.hintValorHora));
        mValorHora.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.whiteHint));
        mValorHora.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryText));
        mValorHora.setPadding(GlobalFunction.ConvertDpToPx(10), 0, GlobalFunction.ConvertDpToPx(10), 0);
        mValorHora.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_text_background));
        mValorHora.setInputType(InputType.TYPE_CLASS_NUMBER);
        return mValorHora;
    }


}
