package com.example.gerardo.miestacionamiento.view.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.controller.util.GlobalConstant;
import com.example.gerardo.miestacionamiento.controller.GlobalFunction;
import com.example.gerardo.miestacionamiento.model.Estacionamiento;
import com.example.gerardo.miestacionamiento.model.Usuario;
import com.example.gerardo.miestacionamiento.view.ui.InfoActivity;
import com.example.gerardo.miestacionamiento.view.ui.MainActivity;
import com.example.gerardo.miestacionamiento.view.ui.dialog.PagoDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

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
    @Bind(R.id.txt_resumen_direccion_comuna)
    TextView txtDireccionComuna;
    @Bind(R.id.txt_resumen_nombres_apellidos)
    TextView txtnombreApellido;
    @Bind(R.id.txt_resumen_correo)
    TextView txtCorreo;
    @Bind(R.id.txt_resumen_fecha_inicio)
    TextView txtFechaInicio;
    @Bind(R.id.txt_resumen_fecha_termino)
    TextView txtFechaTermino;
    @Bind(R.id.txt_resumen_total_horas)
    TextView txtTotalHoras;
    @Bind(R.id.txt_resumen_monto_hora)
    TextView txtMontoHora;
    @Bind(R.id.txt_resumen_total_costo)
    TextView txtTotalCosto;


    String rutUsuario,fechaInicio,fechaTermino;
    int idEstacio;
    int cantHoras;

    public ResumenFragment() {
        // Required empty public constructor
    }

    public static ResumenFragment newInstance(String usuario, Integer est, int cant, String fechaInicio, String fechaTermino) {
        ResumenFragment fragment = new ResumenFragment();
        Bundle b = new Bundle();
        if (usuario != null) {
            b.putString(GlobalConstant.BUNDLE_RUT_USUARIO, usuario);
        }
        if (est != null) {
            b.putInt(GlobalConstant.BUNDLE_ID_ESTACIO, est);
        }
        b.putInt(GlobalConstant.BUNDLE_CANT_HORAS, cant);
        b.putString(GlobalConstant.BUNDLE_FECHA_INICIO,fechaInicio);
        b.putString(GlobalConstant.BUNDLE_FECHA_TERMINO,fechaTermino);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        rutUsuario = args.getString(GlobalConstant.BUNDLE_RUT_USUARIO, "");
        idEstacio = args.getInt(GlobalConstant.BUNDLE_ID_ESTACIO, 0);
        cantHoras = args.getInt(GlobalConstant.BUNDLE_CANT_HORAS, 0);
        fechaInicio = args.getString(GlobalConstant.BUNDLE_FECHA_INICIO,"");
        fechaTermino = args.getString(GlobalConstant.BUNDLE_FECHA_TERMINO,"");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_resumen, container, false);
        ButterKnife.bind(this, root);
        setContentsView();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Resumen del Arriendo");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
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
        if (mCheckTerminos.isChecked()) {
            PagoDialog dialog = PagoDialog.newInstance(rutUsuario,cantHoras,fechaInicio,fechaTermino,idEstacio);
            dialog.show(getActivity().getSupportFragmentManager(), "fragment");
        } else {
            Toast.makeText(getActivity(), "Debes aceptar los Términos y Condiciones", Toast.LENGTH_SHORT).show();
        }
    }

    private void setContentsView() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Usuario usuario = realm.where(Usuario.class).equalTo("rutUsuario",rutUsuario).findFirst();
        Estacionamiento est = realm.where(Estacionamiento.class).equalTo("idEstacionamiento",idEstacio).findFirst();
        realm.commitTransaction();

        if (est != null){
            txtDireccionComuna.setText(est.getDireccionEstacionamiento()+", "+GlobalFunction.getComunaNombrebyID(est.getIdComuna()));
            txtMontoHora.setText("$"+est.getCostoHora());
            int costo = est.getCostoHora()*cantHoras;
            txtTotalCosto.setText(getActivity().getResources().getString(R.string.resumenTotalCosto,costo));
        }
        if (usuario!=null){
            txtnombreApellido.setText(usuario.getNombre()+" "+usuario.getApellidoPaterno()+" "+usuario.getApellidoMaterno());
            txtCorreo.setText(usuario.getCorreo());

        }
        txtFechaInicio.setText(getActivity().getResources().getString(R.string.resumenFechaInicio,fechaInicio));
        txtFechaTermino.setText(getActivity().getResources().getString(R.string.resumenFechaTermino,fechaTermino));
        txtTotalHoras.setText(getActivity().getResources().getString(R.string.resumenTotalHoras,cantHoras));


    }

    @OnClick(R.id.btn_resumen_cancelar)
    public void volver(){
        getActivity().getSupportFragmentManager().popBackStackImmediate();
    }
}
