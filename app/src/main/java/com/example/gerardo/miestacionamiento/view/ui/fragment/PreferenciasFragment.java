package com.example.gerardo.miestacionamiento.view.ui.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.controller.GlobalFunction;
import com.example.gerardo.miestacionamiento.controller.util.GlobalConstant;
import com.example.gerardo.miestacionamiento.view.ui.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreferenciasFragment extends Fragment {


    @Bind(R.id.switch_prefs_sesion)
    SwitchCompat switchSesion;
    @Bind(R.id.txt_prefs_report)
    TextView txtReport;
    @Bind(R.id.txt_prefs_share)
    TextView txtShare;
    @Bind(R.id.txt_prefs_about)
    TextView txtAbout;
    @Bind(R.id.txt_prefs_delete)
    TextView txtDelete;


    public PreferenciasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_preferencias, container, false);
        ButterKnife.bind(this, root);
        handleSwitchEvent();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void handleSwitchEvent(){
        switchSesion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(getActivity(), "Activado", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Desactivado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @OnClick(R.id.txt_prefs_report)
    public void reportar(){
        Toast.makeText(getActivity(), "Reportar un problema", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.txt_prefs_share)
    public void compartir(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent
                .putExtra(Intent.EXTRA_TEXT,
                        "Hola, te invito a probar la app Estacionate! es gratis, encuentrala en este enlace http://bit.ly/2gyKzMS");
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Invitación a Mi Estacionamiento");
        sendIntent.setType("text/plain");
//        sendIntent.setPackage("com.facebook.orca");
        try {
            startActivity(sendIntent);
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(),"Por favór instale la applicación", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.txt_prefs_about)
    public void about(){
        AlertDialog.Builder builder = GlobalFunction.crearDialogYesNot(getActivity(),
                "Sobre Nosotros", getString(R.string.sobreNosotros));

        AlertDialog dialog = builder.create();
        dialog.setCancelable(true);

        dialog.show();
    }

    @OnClick(R.id.txt_prefs_delete)
    public void deleteAccount(){
        AlertDialog.Builder builder = GlobalFunction.crearDialogYesNot(getActivity(), "Eliminar", "Nos apena que quieras eliminar tu cuenta de Estacionate! ¿Realmente quieres borrarla?");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences prefs;
                SharedPreferences.Editor editor;
                prefs = getActivity().getSharedPreferences(GlobalConstant.PREFS_NAME, Context.MODE_PRIVATE);
                editor = prefs.edit();
                editor.putBoolean(GlobalConstant.PREFS_AUTOLOGIN,false);
                editor.remove(GlobalConstant.PREFS_CORREO);
                editor.remove(GlobalConstant.PREFS_CLAVE);
                editor.apply();
                Toast.makeText(getActivity(), "Cuenta eliminada :(", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
