package com.example.gerardo.miestacionamiento.view.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.controller.util.GlobalConstant;
import com.example.gerardo.miestacionamiento.controller.util.GlobalFunction;
import com.example.gerardo.miestacionamiento.controller.util.RunnableArgs;
import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AnimacionLoadinActivity extends AppCompatActivity {

    @Bind(R.id.circle_loading_view)
    AnimatedCircleLoadingView animatedCircleLoadingView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animacion_loadin);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        animatedCircleLoadingView.startIndeterminate();
        animatedCircleLoadingView.stopOk();

        Bundle extras = getIntent().getExtras();
        final String correo = extras.getString(GlobalConstant.PREFS_CORREO,"");
        final String clave = extras.getString(GlobalConstant.PREFS_CLAVE,"");

        final RunnableArgs runnableArgs = new RunnableArgs(){
            @Override
            public void run() {
                if (this.getResponse() == GlobalConstant.RESPONSE_LOGIN_CORRECT){

                    startActivity(new Intent(AnimacionLoadinActivity.this,MainActivity.class));
                }else{
                    if (this.getResponse() == GlobalConstant.RESPONSE_LOGIN_INCORRECT){


                        Toast.makeText(AnimacionLoadinActivity.this, "Usuario y/o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }else{
                        if (this.getResponse() == GlobalConstant.RESPONSE_CONNECTION_ERROR){

                            Toast.makeText(AnimacionLoadinActivity.this, "Problemas al conectar, reintentelo en unos minutos", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        };


        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GlobalFunction.loginConnect(AnimacionLoadinActivity.this,correo,clave,runnableArgs);
            }
        },6000);



    }
}
