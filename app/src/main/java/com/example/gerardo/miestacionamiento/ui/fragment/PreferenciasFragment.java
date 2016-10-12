package com.example.gerardo.miestacionamiento.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gerardo.miestacionamiento.R;

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
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.switch_prefs_sesion)
    public void onClick() {
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
        Toast.makeText(getActivity(), "Enviar invitación", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.txt_prefs_about)
    public void about(){
        Toast.makeText(getActivity(), "Sobre Nosotros", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.txt_prefs_delete)
    public void deleteAccount(){
        Toast.makeText(getActivity(), "Eliminar Cuenta", Toast.LENGTH_SHORT).show();
    }
}
