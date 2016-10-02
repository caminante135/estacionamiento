package com.example.gerardo.miestacionamiento.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gerardo.miestacionamiento.ui.dialog.DialogEscogerTipoUsuario;
import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.util.GlobalConstant;
import com.example.gerardo.miestacionamiento.util.GlobalFunction;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.edit_usuario)
    TextInputEditText editUsuario;
    @Bind(R.id.edit_password)
    TextInputEditText editPassword;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.txt_Registrar)
    TextView txtRegistrar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

    }

    @OnClick(R.id.btn_login)
    public void loguear() {
        if (!GlobalFunction.isEmpty(editUsuario.getText().toString().trim()) &&
                !GlobalFunction.isEmpty(editPassword.getText().toString().trim())){

//            SharedPreferences prefs = this.getSharedPreferences(GlobalConstant.PREFS_NAME, Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = prefs.edit();
//            editor.putString(GlobalConstant.PREFS_USER,editUsuario.getText().toString().trim());
//            editor.putString(GlobalConstant.PREFS_PASS,editPassword.getText().toString().trim());
//            editor.apply();
            startActivity(new Intent(this,MainActivity.class));
        }else{
            Toast.makeText(LoginActivity.this, "Debe rellenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.txt_Registrar)
    public void registrar() {
        new DialogEscogerTipoUsuario(this).show();
    }

}
