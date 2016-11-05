package com.example.gerardo.miestacionamiento.view.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.view.ui.RegistroActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Gerardo on 29/09/2016.
 */
public class DialogTipoUsuario extends Dialog {


    @Bind(R.id.btn_propietario)
    ImageButton btnPropietario;
    @Bind(R.id.btn_arrendatario)
    ImageButton btnArrendatario;

    public DialogTipoUsuario(Context context, int themeResId) {
        super(context, themeResId);
    }

    public DialogTipoUsuario(Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureDialog();
    }

    private void configureDialog() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);
        setContentView(R.layout.dialog_choose);

        ButterKnife.bind(this);


        //Ajusto el tamaño horizontal del dialog
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = getWindow();
        lp.copyFrom(window.getAttributes());
        //Le seteo el tamaño
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);


    }

    @OnClick({R.id.btn_propietario, R.id.btn_arrendatario})
    public void onClick(View view) {
        Intent intent = new Intent(getContext(),RegistroActivity.class);
        switch (view.getId()) {
            case R.id.btn_propietario:
                intent.putExtra("tipo","propietario");
                getContext().startActivity(intent);
                break;
            case R.id.btn_arrendatario:
                intent.putExtra("tipo","arrendatario");
                getContext().startActivity(intent);
                break;
        }
    }
}
