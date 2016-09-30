package com.example.gerardo.miestacionamiento.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.ui.RegistroActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Gerardo on 29/09/2016.
 */
public class DialogEscogerTipoUsuario extends Dialog {


    @Bind(R.id.btn_propietario)
    ImageButton btnPropietario;
    @Bind(R.id.btn_arrendatario)
    ImageButton btnArrendatario;

    public DialogEscogerTipoUsuario(Context context, int themeResId) {
        super(context, themeResId);
    }

    public DialogEscogerTipoUsuario(Context context) {
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

    @OnClick(R.id.btn_propietario)
    public void onClick() {
        getContext().startActivity(new Intent(getContext(), RegistroActivity.class));
    }

    @OnClick(R.id.btn_propietario)
    public void onClick2() {
        getContext().startActivity(new Intent(getContext(), RegistroActivity.class));
    }
}
