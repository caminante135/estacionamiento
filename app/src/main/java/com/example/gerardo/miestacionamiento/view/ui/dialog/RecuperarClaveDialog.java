package com.example.gerardo.miestacionamiento.view.ui.dialog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.controller.GlobalFunction;
import com.example.gerardo.miestacionamiento.controller.util.GlobalConstant;
import com.example.gerardo.miestacionamiento.controller.util.RunnableArgs;
import com.example.gerardo.miestacionamiento.view.ui.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Gerardo on 30/11/2016.
 */
public class RecuperarClaveDialog extends DialogFragment {


    @Bind(R.id.dialog_recuperar_correo)
    EditText editCorreo;
    @Bind(R.id.dialog_recuperar_btn)
    Button dialogRecuperarBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_recuperar_clave, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        ButterKnife.bind(this, root);



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(GlobalFunction.ConvertDpToPx(300), GlobalFunction.ConvertDpToPx(250));
        ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(getActivity(),R.color.dialog_transparecy));
        getDialog().getWindow().setBackgroundDrawable(colorDrawable);
    }

    @OnClick(R.id.dialog_recuperar_btn)
    public void onClick() {
        if (editCorreo.getText().toString().trim().length()!=0){
            final ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setCancelable(false);
            dialog.setTitle("Cargando");
            dialog.setMessage("Reestableciendo su contraseña");
            dialog.show();
            final RunnableArgs runnableArgs = new RunnableArgs() {
                @Override
                public void run() {
                    dialog.dismiss();
                    if (this.getResponseBoolean()) {
                        Toast.makeText(getActivity(), "Nueva contraseña enviada a tu correo", Toast.LENGTH_SHORT).show();
                        dismiss();
                    } else {
                        Toast.makeText(getActivity(), "No se pudo reestablecer la contraseña", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                }
            };
            GlobalFunction.recuperarContraseña(editCorreo.getText().toString(),runnableArgs);
        }else{
            Toast.makeText(getActivity(), "Ingrese un correo válido antes de reestablecer la contraseña", Toast.LENGTH_SHORT).show();
        }
    }
}
