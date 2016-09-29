package com.example.gerardo.miestacionamiento;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import butterknife.Bind;
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
    }
}
