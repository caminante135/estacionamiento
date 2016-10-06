package com.example.gerardo.miestacionamiento.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.Gravity;
import android.widget.Toast;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.ui.fragment.PersonalFragment;
import com.example.gerardo.miestacionamiento.util.GlobalFunction;
import com.heinrichreimersoftware.singleinputform.SingleInputFormActivity;
import com.heinrichreimersoftware.singleinputform.steps.Step;
import com.heinrichreimersoftware.singleinputform.steps.TextStep;

import java.util.ArrayList;
import java.util.List;


public class RegistroActivity extends SingleInputFormActivity {

    public static final String DK_RUT="rut";
    public static final String DK_NOMBRE="nombre";
    public static final String DK_AP="ap";
    public static final String DK_AM="am";
    public static final String DK_CORREO="correo";
    public static final String DK_TELEFONO="telefono";
    public static final String DK_CLAVE="clave";

    String tipo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            tipo = extras.getString("tipo");
        }
    }



    @Override
    protected List<Step> onCreateSteps() {
        List<Step> mySteps = new ArrayList<Step>();

        mySteps.add(
                new TextStep.Builder(this,DK_RUT)
                .title("RUT")
                .errorResId(R.string.errorRut)
                .detailsResId(R.string.hintRut)
                .inputType(InputType.TYPE_CLASS_TEXT)
                .validator(new TextStep.Validator() {
                    @Override
                    public boolean validate(String input) {
//                        return Patterns.EMAIL_ADDRESS.matcher(input).matches();
                        return GlobalFunction.isRut(input);
                    }
                })
                .build());

        mySteps.add(new TextStep.Builder(this,DK_NOMBRE)
                .title("Nombre")
                .errorResId(R.string.errorNombre)
                .detailsResId(R.string.hintNombre)
                .inputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME)
                        .validator(new TextStep.Validator() {
                            @Override
                            public boolean validate(String input) {
//                        return Patterns.EMAIL_ADDRESS.matcher(input).matches();
                                return !GlobalFunction.isEmpty(input);
                            }
                        })
                .build());

        mySteps.add(new TextStep.Builder(this,DK_AP)
                .title("Apellido Paterno")
                .errorResId(R.string.errorAP)
                .detailsResId(R.string.hintApelP)
                .inputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME)
                .validator(new TextStep.Validator() {
                    @Override
                    public boolean validate(String input) {
//                        return Patterns.EMAIL_ADDRESS.matcher(input).matches();
                        return !GlobalFunction.isEmpty(input);
                    }
                })
                .build());

        mySteps.add(new TextStep.Builder(this,DK_AM)
                .title("Apellido Materno")
                .errorResId(R.string.errorAM)
                .detailsResId(R.string.hintApelM)
                .inputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME)
                .validator(new TextStep.Validator() {
                    @Override
                    public boolean validate(String input) {
                        return !GlobalFunction.isEmpty(input);
                    }
                })
                .build());

        mySteps.add(new TextStep.Builder(this,DK_CORREO)
                .title("Correo Electrónico")
                .errorResId(R.string.errorCorreo)
                .detailsResId(R.string.hintCorreo)
                .inputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                .validator(new TextStep.Validator() {
                    @Override
                    public boolean validate(String input) {
                        return Patterns.EMAIL_ADDRESS.matcher(input).matches();
                    }
                })
                .build());

        mySteps.add(new TextStep.Builder(this,DK_TELEFONO)
                .title("Teléfono")
                .errorResId(R.string.errorTelefono)
                .detailsResId(R.string.hintTelefono)
                .inputType(InputType.TYPE_CLASS_PHONE)
                .validator(new TextStep.Validator() {
                    @Override
                    public boolean validate(String input) {
                        return !GlobalFunction.isEmpty(input);
                    }
                })
                .build());

        mySteps.add(new TextStep.Builder(this,DK_CLAVE)
                .title("Contraseña")
                .errorResId(R.string.errorClave)
                .detailsResId(R.string.hintClave)
                .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)
                .validator(new TextStep.Validator() {
                    @Override
                    public boolean validate(String input) {
                        return !GlobalFunction.isEmpty(input);
                    }
                })
                .build());

        return mySteps;
    }

    @Override
    protected void onFormFinished(Bundle bundle) {
//        Toast.makeText(RegistroActivity.this, TextStep.text(bundle,DK_NOMBRE), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegistroActivity.this,BaseActivityRegistro.class);
        intent.putExtra("tipo",tipo);
        startActivity(intent);

        finish();

    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_registro);
//        setTitle("Registrar nuevo usuario");
//
//        Bundle extras = getIntent().getExtras();
//        String tipo = "";
//        if (extras != null) {
//            tipo = extras.getString("tipo");
//        }
//        PersonalFragment personalFragment = PersonalFragment.newInstance(tipo);
//
//
////        Bundle args = new Bundle();
////        args.putString("tipo",tipo);
//
//        if (personalFragment!=null){
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
////            personalFragment.setArguments(args);
//            ft.replace(R.id.container, personalFragment);
//            ft.commit();
//        }
//
//
//    }


}
