package com.example.gerardo.miestacionamiento.ui.fragment;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.gerardo.miestacionamiento.GlobalFunction;
import com.example.gerardo.miestacionamiento.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment implements VerticalStepperForm {

    public static final String ARGUMENTO_TIPO = "tipo";
    public static final String ARGUMENTO_PROP = "propietario";
    public static final String ARGUMENTO_ARREN = "arrendatario";

    VerticalStepperFormLayout verticalStepperForm;
    ViewGroup.LayoutParams lp;

    EditText mRut;
    EditText mNombre;
    EditText mApellidoP;
    EditText mApellidoM;
    EditText mCorreo;
    EditText mTelefono;
    EditText mContraseña;

    String tipoUsuario=null;

    public PersonalFragment() {
        // Required empty public constructor
    }

    public static PersonalFragment newInstance(String tipo) {
        PersonalFragment personalFragment = new PersonalFragment();
        Bundle b = new Bundle();
        b.putString(ARGUMENTO_TIPO,tipo);
        personalFragment.setArguments(b);
        return personalFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        tipoUsuario = args.getString(ARGUMENTO_TIPO);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_personal, container, false);

        String[] mySteps = getActivity().getResources().getStringArray(R.array.itemsDatosPersonales);

//        String[] mySubs = {"Ingre su nombre", "Ingresa tu EMAIL", "Ingresa tu numero de telefono"};
        int colorPrimary = ContextCompat.getColor(getActivity().getApplicationContext(), R.color.colorPrimary);
        int colorPrimaryDark = ContextCompat.getColor(getActivity().getApplicationContext(), R.color.colorPrimaryDark);

        verticalStepperForm = (VerticalStepperFormLayout) root.findViewById(R.id.vertical_stepper_form);
        lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,GlobalFunction.dpToPx(35));

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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public View createStepContentView(int stepNumber) {
        View view = null;
        switch (stepNumber) {
            case 0:
                view = crearViewRut();
                break;
            case 1:
                view = crearViewNombre();
                break;
            case 2:
                view = crearViewAP();
                break;
            case 3:
                view = crearViewAM();
                break;
            case 4:
                view = crearViewCorreo();
                break;
            case 5:
                view = crearViewTelefono();
                break;
            case 6:
                view = crearViewContraseña();
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
        if (tipoUsuario.equals(ARGUMENTO_PROP)){
            EstacionamientoFragment fragment = EstacionamientoFragment.newInstance();
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container,fragment);
            ft.commitAllowingStateLoss();
        }else{
            if (tipoUsuario.equals(ARGUMENTO_ARREN)){
                VehiculoFragment fragment = VehiculoFragment.newInstance(tipoUsuario);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container,fragment);
                ft.commitAllowingStateLoss();
            }
        }
    }



    private View crearViewRut() {
        mRut = new EditText(getActivity());
        mRut.setSingleLine(true);
        mRut.setLayoutParams(lp);
        mRut.setHint(getActivity().getResources().getString(R.string.hintRut));
        mRut.setHintTextColor(ContextCompat.getColor(getActivity(),R.color.whiteHint));
        mRut.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorPrimaryText));
        mRut.setPadding(GlobalFunction.dpToPx(10),0,GlobalFunction.dpToPx(10),0);
        mRut.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.edit_text_background));
        return mRut;
    }
    private View crearViewNombre() {
        mNombre = new EditText(getActivity());
        mNombre.setSingleLine(true);
        mNombre.setLayoutParams(lp);
        mNombre.setHint(getActivity().getResources().getString(R.string.hintNombre));
        mNombre.setHintTextColor(ContextCompat.getColor(getActivity(),R.color.whiteHint));
        mNombre.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorPrimaryText));
        mNombre.setPadding(GlobalFunction.dpToPx(10),0,GlobalFunction.dpToPx(10),0);
        mNombre.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.edit_text_background));
        mNombre.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        return mNombre;
    }
    private View crearViewAP() {
        mApellidoP = new EditText(getActivity());
        mApellidoP.setSingleLine(true);
        mApellidoP.setLayoutParams(lp);
        mApellidoP.setHint(getActivity().getResources().getString(R.string.hintApelP));
        mApellidoP.setHintTextColor(ContextCompat.getColor(getActivity(),R.color.whiteHint));
        mApellidoP.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorPrimaryText));
        mApellidoP.setPadding(GlobalFunction.dpToPx(10),0,GlobalFunction.dpToPx(10),0);
        mApellidoP.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.edit_text_background));
        return mApellidoP;
    }
    private View crearViewAM() {
        mApellidoM = new EditText(getActivity());
        mApellidoM.setSingleLine(true);
        mApellidoM.setLayoutParams(lp);
        mApellidoM.setHint(getActivity().getResources().getString(R.string.hintApelM));
        mApellidoM.setHintTextColor(ContextCompat.getColor(getActivity(),R.color.whiteHint));
        mApellidoM.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorPrimaryText));
        mApellidoM.setPadding(GlobalFunction.dpToPx(10),0,GlobalFunction.dpToPx(10),0);
        mApellidoM.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.edit_text_background));
        return mApellidoM;
    }
    private View crearViewCorreo() {
        mCorreo = new EditText(getActivity());
        mCorreo.setSingleLine(true);
        mCorreo.setLayoutParams(lp);
        mCorreo.setHint(getActivity().getResources().getString(R.string.hintCorreo));
        mCorreo.setHintTextColor(ContextCompat.getColor(getActivity(),R.color.whiteHint));
        mCorreo.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorPrimaryText));
        mCorreo.setPadding(GlobalFunction.dpToPx(10),0,GlobalFunction.dpToPx(10),0);
        mCorreo.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.edit_text_background));
        mCorreo.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        return mCorreo;
    }
    private View crearViewTelefono() {
        mTelefono = new EditText(getActivity());
        mTelefono.setSingleLine(true);
        mTelefono.setLayoutParams(lp);
        mTelefono.setHint(getActivity().getResources().getString(R.string.hintTelefono));
        mTelefono.setHintTextColor(ContextCompat.getColor(getActivity(),R.color.whiteHint));
        mTelefono.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorPrimaryText));
        mTelefono.setPadding(GlobalFunction.dpToPx(10),0,GlobalFunction.dpToPx(10),0);
        mTelefono.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.edit_text_background));
        mTelefono.setInputType(InputType.TYPE_CLASS_NUMBER);
        return mTelefono;
    }
    private View crearViewContraseña() {
        mContraseña = new EditText(getActivity());
        mContraseña.setSingleLine(true);
        mContraseña.setLayoutParams(lp);
        mContraseña.setHint(getActivity().getResources().getString(R.string.hintClave));
        mContraseña.setHintTextColor(ContextCompat.getColor(getActivity(),R.color.whiteHint));
        mContraseña.setTextColor(ContextCompat.getColor(getActivity(),R.color.colorPrimaryText));
        mContraseña.setPadding(GlobalFunction.dpToPx(10),0,GlobalFunction.dpToPx(10),0);
        mContraseña.setTypeface(Typeface.DEFAULT);
        mContraseña.setTransformationMethod(new PasswordTransformationMethod());
        mContraseña.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.edit_text_background));
        mContraseña.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        return mContraseña;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
