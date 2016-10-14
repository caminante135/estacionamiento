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


public class RegistroActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        setTitle("Registrar nuevo usuario");

        Bundle extras = getIntent().getExtras();
        String tipo = "";
        if (extras != null) {
            tipo = extras.getString("tipo");
        }
        PersonalFragment personalFragment = PersonalFragment.newInstance();


//        Bundle args = new Bundle();
//        args.putString("tipo",tipo);

        if (personalFragment!=null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            personalFragment.setArguments(args);
            ft.replace(R.id.container, personalFragment);
            ft.commit();
        }


    }


}
