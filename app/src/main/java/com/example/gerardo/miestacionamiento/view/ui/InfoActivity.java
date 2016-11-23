package com.example.gerardo.miestacionamiento.view.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.controller.util.GlobalConstant;
import com.example.gerardo.miestacionamiento.view.ui.fragment.DetalleFragment;
import com.example.gerardo.miestacionamiento.view.ui.fragment.PersonalFragment;
import com.google.android.gms.maps.model.LatLng;

public class InfoActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Bundle extras = getIntent().getExtras();
        String rut = "";
        Integer idEsta = null;
        LatLng latLng = null;
        if (extras != null){
            rut = extras.getString(GlobalConstant.BUNDLE_RUT_USUARIO);
            idEsta = extras.getInt(GlobalConstant.BUNDLE_ID_ESTACIO);
            latLng = new LatLng(extras.getDouble(GlobalConstant.PREFS_LATITUD),extras.getDouble(GlobalConstant.PREFS_LONGITUD));
        }

        DetalleFragment fragment = DetalleFragment.newInstance(latLng,rut,idEsta);

        if (fragment!=null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container_info, fragment);
            ft.commit();
        }



    }


}
