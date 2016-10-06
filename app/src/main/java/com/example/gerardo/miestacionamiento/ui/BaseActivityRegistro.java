package com.example.gerardo.miestacionamiento.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.ui.fragment.EstacionamientoFragment;
import com.example.gerardo.miestacionamiento.ui.fragment.PersonalFragment;
import com.example.gerardo.miestacionamiento.ui.fragment.VehiculoFragment;

public class BaseActivityRegistro extends AppCompatActivity {

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
        Fragment fragment = null;

        if (tipo != null){
            if (tipo.equals("propietario")){
                fragment = EstacionamientoFragment.newInstance();
            }else
                if (tipo.equals("arrendatario")){
                    fragment = VehiculoFragment.newInstance();
                }
        }

//        Bundle args = new Bundle();
//        args.putString("tipo",tipo);

        if (fragment!=null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            personalFragment.setArguments(args);
            ft.setCustomAnimations(R.anim.slide_in_left,R.anim.slide_in_left);
            ft.replace(R.id.container, fragment);
            ft.commit();
        }






    }
}
