package com.example.gerardo.miestacionamiento.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.ui.dialog.PagarDialog;
import com.example.gerardo.miestacionamiento.util.GlobalFunction;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResumenFragment extends Fragment {


    @Bind(R.id.checkbox_resumen_terminos)
    CheckBox mCheckTerminos;
    @Bind(R.id.txt_resumen_terminos)
    TextView mTxtTerminos;
    @Bind(R.id.btn_resumen_cancelar)
    Button btnResumenCancelar;
    @Bind(R.id.btn_resumen_aceptar)
    Button mBtnAceptar;

    public ResumenFragment() {
        // Required empty public constructor
    }

    public static ResumenFragment newInstance() {
        ResumenFragment fragment = new ResumenFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_resumen, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.txt_resumen_terminos)
    public void mostrarDialogoTerminosCondiciones() {
        AlertDialog.Builder builder = GlobalFunction.crearDialogYesNot(getActivity(),
                "Términos y Condiciones", getString(R.string.terminosCondiciones));

        AlertDialog dialog = builder.create();
        dialog.setCancelable(true);

        dialog.show();
    }

    @OnClick(R.id.btn_resumen_aceptar)
    public void onClick() {
        if (mCheckTerminos.isChecked()){
            PagarDialog dialog = PagarDialog.newInstance();
            dialog.show(getActivity().getSupportFragmentManager(),"fragment");
        }else{
            Toast.makeText(getActivity(), "Debes aceptar los Términos y Condiciones", Toast.LENGTH_SHORT).show();
        }
    }
}
