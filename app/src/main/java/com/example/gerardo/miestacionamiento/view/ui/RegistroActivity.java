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

        PersonalFragment personalFragment = PersonalFragment.newInstance();

        if (personalFragment!=null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, personalFragment);
            ft.commit();
        }


    }


}
