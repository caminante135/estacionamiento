package com.example.gerardo.miestacionamiento.view.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.controller.util.GlobalConstant;
import com.example.gerardo.miestacionamiento.view.ui.fragment.DetalleFragment;
import com.example.gerardo.miestacionamiento.view.ui.fragment.PersonalFragment;
import com.google.android.gms.maps.model.LatLng;

public class InfoActivity extends AppCompatActivity {

    Toolbar toolbar;
    private static final String KHIPU_URI = "KHIPU-URI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        boolean yaPago = getSharedPreferences(KHIPU_URI, Context.MODE_PRIVATE).getBoolean("yaPago", false);
        if (yaPago){
            SharedPreferences.Editor editor = getSharedPreferences(KHIPU_URI, Context.MODE_PRIVATE).edit();
            editor.putBoolean("yaPago",false);
            editor.apply();
            String direccion = getSharedPreferences(KHIPU_URI, Context.MODE_PRIVATE).getString("direccionKhipu", "");
            Integer idEstacionamiento = getSharedPreferences(KHIPU_URI, Context.MODE_PRIVATE).getInt("idEstKhipu", 0);
            String rutUsuario = getSharedPreferences(KHIPU_URI, Context.MODE_PRIVATE).getString("rutUserKhipu", "");
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("direccion",direccion);
            intent.putExtra("idEst",idEstacionamiento);
            intent.putExtra("rutUser",rutUsuario);
            intent.putExtra("notificacion", true);
            startActivity(intent);
        }else{
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

    @Override
    public void onStart() {
        super.onStart();
        Uri data = getIntent().getData();

        if (data != null && data.getScheme().equals("khipuinstalled")) {

            //SE BUSCA LA URI DE KHIPU QUE ESTABA POR EJECUTARSE CUANDO SE ENVIO AL USUARIO A INSTALAR
            String storedKhipuUri = getSharedPreferences(KHIPU_URI, Context.MODE_PRIVATE).getString(KHIPU_URI, "");
            if (!storedKhipuUri.equals("")) {
                SharedPreferences.Editor editor = getSharedPreferences(KHIPU_URI, Context.MODE_PRIVATE).edit();
                editor.remove(KHIPU_URI);
                editor.commit();
                Intent khipuIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(storedKhipuUri));
                startActivity(khipuIntent);
            }
        } else if (data != null && data.getScheme().equals("khipudemo")) {

            CharSequence text = "";
            if ("cancel.khipu.com".equals(data.getAuthority())) {
                text = "El usuario rechazó el pago";
            } else if ("return.khipu.com".equals(data.getAuthority())) {
                text = "El pago se está verificando";
            }
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(getApplicationContext(), text, duration);
            toast.show();
        }
    }


}
