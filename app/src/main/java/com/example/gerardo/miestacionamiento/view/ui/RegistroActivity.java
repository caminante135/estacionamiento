package com.example.gerardo.miestacionamiento.view.ui;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.view.ui.fragment.PersonalFragment;


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
