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
import android.widget.Toast;

import com.alburivan.slickform.FieldsType;
import com.alburivan.slickform.FormField;
import com.alburivan.slickform.SlickForm;
import com.alburivan.slickform.interfaces.IOnCustomValidation;
import com.alburivan.slickform.interfaces.IOnProcessChange;
import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.util.GlobalFunction;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment {

    public static final String ARGUMENTO_TIPO = "tipo";
    public static final String ARGUMENTO_PROP = "propietario";
    public static final String ARGUMENTO_ARREN = "arrendatario";

    VerticalStepperFormLayout verticalStepperForm;
    ViewGroup.LayoutParams lp;

    FormField mRut;
    FormField mNombre;
    FormField mApellidoP;
    FormField mApellidoM;
    FormField mCorreo;
    FormField mTelefono;
    FormField mContraseña;

    String tipoUsuario = null;
    @Bind(R.id.slick_form_personal)
    SlickForm mSlickForm;

    public PersonalFragment() {
        // Required empty public constructor
    }

    public static PersonalFragment newInstance(String tipo) {
        PersonalFragment personalFragment = new PersonalFragment();
        Bundle b = new Bundle();
        b.putString(ARGUMENTO_TIPO, tipo);
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
        ButterKnife.bind(this, root);

        String[] mySteps = getActivity().getResources().getStringArray(R.array.itemsDatosPersonales);

//        String[] mySubs = {"Ingre su nombre", "Ingresa tu EMAIL", "Ingresa tu numero de telefono"};
        int colorPrimary = ContextCompat.getColor(getActivity().getApplicationContext(), R.color.colorPrimary);
        int colorPrimaryDark = ContextCompat.getColor(getActivity().getApplicationContext(), R.color.colorPrimaryDark);

        verticalStepperForm = (VerticalStepperFormLayout) root.findViewById(R.id.vertical_stepper_form);
        lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, GlobalFunction.dpToPx(35));

        // Setting up and initializing the form
//        VerticalStepperFormLayout.Builder.newInstance(verticalStepperForm, mySteps, this, getActivity())
//                .primaryColor(colorPrimary)
//                .primaryDarkColor(colorPrimaryDark)
//                .materialDesignInDisabledSteps(true)
//                .showVerticalLineWhenStepsAreCollapsed(true)
////                .stepsSubtitles(mySubs)
//                .displayBottomNavigation(true) // It is true by default, so in this case this line is not necessary
//                .init();

        buildSlickForm();

        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }



    public void sendData() {
        if (tipoUsuario.equals(ARGUMENTO_PROP)) {
            EstacionamientoFragment fragment = EstacionamientoFragment.newInstance();
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, fragment);
            ft.commitAllowingStateLoss();
        } else {
            if (tipoUsuario.equals(ARGUMENTO_ARREN)) {
                VehiculoFragment fragment = VehiculoFragment.newInstance();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, fragment);
                ft.commitAllowingStateLoss();
            }
        }
    }

    private IOnCustomValidation crearValidation(){
        IOnCustomValidation val = new IOnCustomValidation() {
            @Override
            public boolean withCustomValidation(FormField formField) {
                if (GlobalFunction.isEmpty(formField.getInputFieldText())){
                    return false;
                }else{
                    return true;
                }
            }
        };
        return val;
    }

    private FormField crearViewRut() {
        mRut = new FormField(getActivity())
                .withType(FieldsType.TEXT)
                .withHint(getActivity().getResources().getString(R.string.hintRut))
                .withLabel("Siguiente")
                .withCustomValidation(new IOnCustomValidation() {
                    @Override
                    public boolean withCustomValidation(FormField formField) {
                        if (GlobalFunction.isEmpty(formField.getInputFieldText()) ||
                                !GlobalFunction.isRut(formField.getInputFieldText())){
                            return false;
                        }else{
                            return true;
                        }
                    }
                })
                ;
//        mRut.setSingleLine(true);
//        mRut.setLayoutParams(lp);
//        mRut.setHint(getActivity().getResources().getString(R.string.hintRut));
//        mRut.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.whiteHint));
//        mRut.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryText));
//        mRut.setPadding(GlobalFunction.dpToPx(10), 0, GlobalFunction.dpToPx(10), 0);
//        mRut.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_text_background));
        return mRut;
    }
    private FormField crearViewNombre() {
        mNombre = new FormField(getActivity())
                .withType(FieldsType.TEXT)
                .withHint(getActivity().getResources().getString(R.string.hintNombre))
                .withLabel("Siguiente")
                .withCustomValidation(crearValidation());
        return mNombre;
    }
    private FormField crearViewAP() {
        mApellidoP = new FormField(getActivity())
                .withType(FieldsType.TEXT)
                .withHint(getActivity().getResources().getString(R.string.hintApelP))
                .withLabel("Siguiente")
                .withCustomValidation(crearValidation());
//        mApellidoP.setSingleLine(true);
//        mApellidoP.setLayoutParams(lp);
//        mApellidoP.setHint(getActivity().getResources().getString(R.string.hintApelP));
//        mApellidoP.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.whiteHint));
//        mApellidoP.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryText));
//        mApellidoP.setPadding(GlobalFunction.dpToPx(10), 0, GlobalFunction.dpToPx(10), 0);
//        mApellidoP.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_text_background));
        return mApellidoP;
    }
    private FormField crearViewAM() {
        mApellidoM = new FormField(getActivity())
                .withType(FieldsType.TEXT)
                .withHint(getActivity().getResources().getString(R.string.hintApelM))
                .withLabel("Siguiente")
                .withCustomValidation(crearValidation());
        return mApellidoM;
    }
    private FormField crearViewCorreo() {
        mCorreo = new FormField(getActivity())
                .withType(FieldsType.EMAIL)
                .withHint(getActivity().getResources().getString(R.string.hintCorreo))
                .withLabel("Siguiente")
                .withCustomValidation(new IOnCustomValidation() {
                    @Override
                    public boolean withCustomValidation(FormField formField) {
                        if (!GlobalFunction.isEmpty(formField.getInputFieldText())
                                && GlobalFunction.isValidEmail(formField.getInputFieldText())){
                            return true;
                        }else{
                            return false;
                        }
                    }
                });
//        mCorreo.setSingleLine(true);
//        mCorreo.setLayoutParams(lp);
//        mCorreo.setHint(getActivity().getResources().getString(R.string.hintCorreo));
//        mCorreo.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.whiteHint));
//        mCorreo.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryText));
//        mCorreo.setPadding(GlobalFunction.dpToPx(10), 0, GlobalFunction.dpToPx(10), 0);
//        mCorreo.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_text_background));
//        mCorreo.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        return mCorreo;
    }
    private FormField crearViewTelefono() {
        mTelefono = new FormField(getActivity())
                .withType(FieldsType.NUMERIC)
                .withHint(getActivity().getResources().getString(R.string.hintTelefono))
                .withLabel("Siguiente")
                .withCustomValidation(crearValidation());
//
        return mTelefono;
    }
    private FormField crearViewContraseña() {
        mContraseña = new FormField(getActivity())
                .withType(FieldsType.PASSWORD)
                .withHint(getActivity().getResources().getString(R.string.hintClave))
                .withLabel("Siguiente")
                .withCustomValidation(crearValidation());
        return mContraseña;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    private void buildSlickForm(){
        mSlickForm
                .withField(crearViewRut())
                .withField(crearViewNombre())
                .withField(crearViewAP())
                .withField(crearViewAM())
                .withField(crearViewCorreo())
                .withField(crearViewTelefono())
                .withField(crearViewContraseña())
                .setOnProcessChangeListener(new IOnProcessChange() {
                    @Override
                    public boolean workInBackground(List<FormField> list) {
                        Toast.makeText(getActivity(), "Rut: "+list.get(0).getInputFieldText()+" AP: "+list.get(1)
                                .getInputFieldText(), Toast.LENGTH_SHORT).show();
                        return true;
                    }

                    @Override
                    public void workFinished() {
//                        EstacionamientoFragment fragment = EstacionamientoFragment.newInstance();
//                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                        ft.replace(R.id.container, fragment);
//                        ft.commitAllowingStateLoss();
                    }
                })
                .withProcessingLabel("Guardando")
                .ready();
    }

}
