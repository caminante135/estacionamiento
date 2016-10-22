package com.example.gerardo.miestacionamiento.ui.fragment;


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

import com.example.gerardo.miestacionamiento.util.GlobalConstant;
import com.example.gerardo.miestacionamiento.util.GlobalFunction;
import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.ui.dialog.DialogWebPay;



import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

/**
 * A simple {@link Fragment} subclass.
 */
public class VehiculoFragment extends Fragment implements VerticalStepperForm  {

    public static final String ARGUMENTO_TIPO = "tipo";
    public static final String ARGUMENTO_PROP = "propietario";
    public static final String ARGUMENTO_ARREN = "arrendatario";

    VerticalStepperFormLayout verticalStepperForm;
    ViewGroup.LayoutParams lp;

    EditText mPatente;
    EditText mMarca;
    EditText mModelo;
    EditText mColor;
    EditText mLargo;


    String jsonUsuario = null;

    public VehiculoFragment() {
        // Required empty public constructor
    }

    public static VehiculoFragment newInstance(String jsonUsuario) {
        VehiculoFragment vehiculoFragment = new VehiculoFragment();
        Bundle b = new Bundle();
        b.putString(GlobalConstant.BUNDLE_USUARIO,jsonUsuario);
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

        String[] mySteps = getActivity().getResources().getStringArray(R.array.itemsDatosVehiculo);
        String[] mySubs = {"", "", "", "", "Largo aproximado en metros"};
        int colorPrimary = ContextCompat.getColor(getActivity().getApplicationContext(), R.color.colorPrimary);
        int colorPrimaryDark = ContextCompat.getColor(getActivity().getApplicationContext(), R.color.colorPrimaryDark);

        verticalStepperForm = (VerticalStepperFormLayout) root.findViewById(R.id.vertical_stepper_form_vehiculo);
        lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, GlobalFunction.ConvertDpToPx(35));

        // Setting up and initializing the form
        VerticalStepperFormLayout.Builder.newInstance(verticalStepperForm, mySteps, this, getActivity())
                .primaryColor(colorPrimary)
                .primaryDarkColor(colorPrimaryDark)
                .materialDesignInDisabledSteps(true)
                .showVerticalLineWhenStepsAreCollapsed(true)
                .stepsSubtitles(mySubs)
                .displayBottomNavigation(true) // It is true by default, so in this case this line is not necessary
                .init();



        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Nuevo Vehículo");
    }

    @Override
    public View createStepContentView(int stepNumber) {
        View view = null;
        switch (stepNumber) {
            case 0:
                view = crearViewPatente();
                break;
            case 1:
                view = crearViewMarca();
                break;
            case 2:
                view = crearViewModelo();
                break;
            case 3:
                view = crearViewColor();
                break;
            case 4:
                view = crearViewLargo();
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
                verticalStepperForm.setStepAsCompleted(4);
                break;
        }
    }

    @Override
    public void sendData() {
        DialogWebPay fragment = new DialogWebPay();
        fragment.show(getActivity().getSupportFragmentManager(),"webpayFragment");
    }




    private View crearViewPatente() {
        mPatente = new EditText(getActivity());
        mPatente.setSingleLine(true);
        mPatente.setLayoutParams(lp);
        mPatente.setHint(getActivity().getResources().getString(R.string.hintPatente));
        mPatente.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.whiteHint));
        mPatente.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryText));
        mPatente.setPadding(GlobalFunction.ConvertDpToPx(10), 0, GlobalFunction.ConvertDpToPx(10), 0);
        mPatente.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_text_background));
        return mPatente;

    }
    private View crearViewMarca() {
        mMarca = new EditText(getActivity());
        mMarca.setSingleLine(true);
        mMarca.setLayoutParams(lp);
        mMarca.setHint(getActivity().getResources().getString(R.string.hintMarca));
        mMarca.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.whiteHint));
        mMarca.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryText));
        mMarca.setPadding(GlobalFunction.ConvertDpToPx(10), 0, GlobalFunction.ConvertDpToPx(10), 0);
        mMarca.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_text_background));
        return mMarca;
    }
    private View crearViewModelo() {
        mModelo = new EditText(getActivity());
        mModelo.setSingleLine(true);
        mModelo.setLayoutParams(lp);
        mModelo.setHint(getActivity().getResources().getString(R.string.hintModelo));
        mModelo.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.whiteHint));
        mModelo.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryText));
        mModelo.setPadding(GlobalFunction.ConvertDpToPx(10), 0, GlobalFunction.ConvertDpToPx(10), 0);
        mModelo.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_text_background));
        return mModelo;
    }
    private View crearViewColor() {
        mColor = new EditText(getActivity());
        mColor.setSingleLine(true);
        mColor.setLayoutParams(lp);
        mColor.setHint(getActivity().getResources().getString(R.string.hintColor));
        mColor.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.whiteHint));
        mColor.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryText));
        mColor.setPadding(GlobalFunction.ConvertDpToPx(10), 0, GlobalFunction.ConvertDpToPx(10), 0);
        mColor.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_text_background));
        return mColor;
    }
    private View crearViewLargo() {
        mLargo = new EditText(getActivity());
        mLargo.setSingleLine(true);
        mLargo.setLayoutParams(lp);
        mLargo.setHint(getActivity().getResources().getString(R.string.hintLargo));
        mLargo.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.whiteHint));
        mLargo.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryText));
        mLargo.setPadding(GlobalFunction.ConvertDpToPx(10), 0, GlobalFunction.ConvertDpToPx(10), 0);
        mLargo.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.edit_text_background));
        mLargo.setInputType(InputType.TYPE_CLASS_NUMBER);
        return mLargo;
    }

}
