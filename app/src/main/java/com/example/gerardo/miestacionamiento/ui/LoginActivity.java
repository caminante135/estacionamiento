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

import com.example.gerardo.miestacionamiento.ResponseRetro;
import com.example.gerardo.miestacionamiento.rest.ApiAdapter;
import com.example.gerardo.miestacionamiento.ui.dialog.DialogEscogerTipoUsuario;
import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.util.GlobalConstant;
import com.example.gerardo.miestacionamiento.util.GlobalFunction;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

            //LLAMADA AL WEB SERVICE
            Call<ResponseRetro> g = ApiAdapter.getApiService().login(editUsuario.getText().toString(),editPassword.getText().toString());
            g.enqueue(new Callback<ResponseRetro>() {
                @Override
                public void onResponse(Call<ResponseRetro> call, Response<ResponseRetro> response) {
                    if (response.body().getMensaje().equals("true")){
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    }else{
                        Toast.makeText(LoginActivity.this, "Datos invalidos", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseRetro> call, Throwable t) {

                }
            });




//
        }else{
            Toast.makeText(LoginActivity.this, "Debe rellenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.txt_Registrar)
    public void registrar() {
        new DialogEscogerTipoUsuario(this).show();
    }

}
