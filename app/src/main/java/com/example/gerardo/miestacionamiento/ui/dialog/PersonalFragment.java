package com.example.gerardo.miestacionamiento.ui.dialog;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gerardo.miestacionamiento.R;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment implements VerticalStepperForm {

    private VerticalStepperFormLayout verticalStepperForm;

    public PersonalFragment() {
        // Required empty public constructor
    }

    public static PersonalFragment newInstance(){
        return new PersonalFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_personal, container, false);


        String[] mySteps = getActivity().getResources().getStringArray(R.array.itemsDatosPersonales);;
//        String[] mySubs = {"Ingre su nombre", "Ingresa tu EMAIL", "Ingresa tu numero de telefono"};
        int colorPrimary = ContextCompat.getColor(getActivity().getApplicationContext(), R.color.colorPrimary);
        int colorPrimaryDark = ContextCompat.getColor(getActivity().getApplicationContext(), R.color.colorPrimaryDark);

        verticalStepperForm = (VerticalStepperFormLayout) root.findViewById(R.id.vertical_stepper_form);

        // Setting up and initializing the form
        VerticalStepperFormLayout.Builder.newInstance(verticalStepperForm, mySteps,this , getActivity())
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
        switch (stepNumber){
            case 0:
                view = createNameStep();
                break;
            case 1:
                view = createNameStep();
                break;
            case 2:
                view = createNameStep();
                break;
            case 3:
                view = createNameStep();
                break;
            case 4:
                view = createNameStep();
                break;
            case 5:
                view = createNameStep();
                break;
            case 6:
                view = createNameStep();
                break;
        }
        return view;
    }

    @Override
    public void onStepOpening(int stepNumber) {
        switch (stepNumber){
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
        Toast.makeText(getActivity(), name.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    EditText name;

    private View createNameStep() {
        // Here we generate programmatically the view that will be added by the system to the step content layout
        name = new EditText(getActivity());
        name.setSingleLine(true);
        name.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        name.setHint("Your name");

        return name;
    }


}
