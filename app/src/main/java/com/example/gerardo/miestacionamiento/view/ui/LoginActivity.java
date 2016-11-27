package com.example.gerardo.miestacionamiento.view.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.controller.util.GlobalConstant;
import com.example.gerardo.miestacionamiento.controller.GlobalFunction;
import com.example.gerardo.miestacionamiento.controller.util.RunnableArgs;
import com.example.gerardo.miestacionamiento.view.intro.IntroActivty;

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

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window w = getWindow(); // in Activity's onCreate() for instance
//            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }

        prefs = getSharedPreferences(GlobalConstant.PREFS_NAME, MODE_PRIVATE);
        editor = prefs.edit();
        if (prefs.getBoolean(GlobalConstant.PREFS_AUTOLOGIN, false)) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
        editUsuario.setText(prefs.getString(GlobalConstant.PREFS_CORREO, ""));
        editPassword.setText(prefs.getString(GlobalConstant.PREFS_CLAVE, ""));

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isFirstStart = prefs.getBoolean(GlobalConstant.PREFS_FIRST_TIME, true);

                if (isFirstStart) {
                    editor.putBoolean(GlobalConstant.PREFS_FIRST_TIME, false);
                    editor.apply();
                    startActivity(new Intent(LoginActivity.this, IntroActivty.class));
                }
            }
        });
        thread.start();
        //CARGO LAS COMUNAS, Marcas, Modelos y Evaluaciones
        GlobalFunction.cargarComunas(this);
        GlobalFunction.cargarMarcasVehiculo(this);
        GlobalFunction.cargarModelosVehiculo(this);
        GlobalFunction.cargarEvaluaciones();
    }

    @OnClick(R.id.btn_login)
    public void loguear() {
        if (!GlobalFunction.isEmpty(editUsuario.getText().toString().trim()) &&
                !GlobalFunction.isEmpty(editPassword.getText().toString().trim())) {

//            SharedPreferences prefs = this.getSharedPreferences(GlobalConstant.PREFS_NAME, Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = prefs.edit();
//            editor.putString(GlobalConstant.PREFS_USER,editUsuario.getText().toString().trim());
//            editor.putString(GlobalConstant.PREFS_PASS,editPassword.getText().toString().trim());
//            editor.apply();

            final ProgressDialog dialog = new ProgressDialog(this, R.style.ProgressDialog);
            dialog.setTitle("Login");
            dialog.setMessage("Cargando...");
            dialog.setCancelable(false);
            dialog.show();


            String email = editUsuario.getText().toString().trim();
            String pass = editPassword.getText().toString().trim();

            final RunnableArgs runnableArgs = new RunnableArgs() {
                @Override
                public void run() {
                    dialog.dismiss();
                    if (this.getResponse() == GlobalConstant.RESPONSE_LOGIN_CORRECT) {

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        if (this.getResponse() == GlobalConstant.RESPONSE_LOGIN_INCORRECT) {


                            Toast.makeText(LoginActivity.this, "Usuario y/o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                        } else {
                            if (this.getResponse() == GlobalConstant.RESPONSE_CONNECTION_ERROR) {

                                Toast.makeText(LoginActivity.this, "Problemas al conectar, reintentelo en unos minutos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            };
            GlobalFunction.loginConnect(LoginActivity.this, email, pass, runnableArgs);
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));

        } else {
            Toast.makeText(LoginActivity.this, "Debe ingresar todos los campos solicitados", Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick(R.id.txt_Registrar)
    public void registrar() {
        startActivity(new Intent(this, RegistroActivity.class));
    }

}
