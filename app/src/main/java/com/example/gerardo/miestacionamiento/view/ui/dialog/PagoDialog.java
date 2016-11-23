package com.example.gerardo.miestacionamiento.view.ui.dialog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.controller.GlobalFunction;
import com.example.gerardo.miestacionamiento.controller.util.GlobalConstant;
import com.example.gerardo.miestacionamiento.model.Estacionamiento;
import com.example.gerardo.miestacionamiento.model.Tarjeta;
import com.example.gerardo.miestacionamiento.view.ui.MainActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Created by Gerardo on 21/10/2016.
 */
public class PagoDialog extends DialogFragment {


    @Bind(R.id.spn_pagar_tarjetas)
    Spinner mSpnTarjetas;
    @Bind(R.id.btn_cerrar_dialog_paagar)
    ImageButton btnCerrar;
    @Bind(R.id.btn_dialog_pagar)
    Button pagar;

    String rutUsuario;
    @Bind(R.id.btn_dialog_test)
    Button btnTest;

    public static PagoDialog newInstance(String rutUsuario) {
        PagoDialog dialog = new PagoDialog();
        Bundle b = new Bundle();
        b.putString(GlobalConstant.BUNDLE_RUT_USUARIO, rutUsuario);
        dialog.setArguments(b);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        rutUsuario = args.getString(GlobalConstant.BUNDLE_RUT_USUARIO, "");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_pagar, container, false);
        ButterKnife.bind(this, root);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        setSpinner();

        return root;
    }

    private String getDireccionByRut(String rut){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Estacionamiento est = realm.where(Estacionamiento.class).equalTo("rutUsuario",rut).findFirst();
        realm.commitTransaction();

        return est.getDireccionEstacionamiento();
    }

    private void setSpinner() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        List<Tarjeta> tarjetas = realm.where(Tarjeta.class).equalTo("rutUsuario", rutUsuario).findAll();
        realm.commitTransaction();
        String[] medios = new String[tarjetas.size()];
        for (int i = 0; i < tarjetas.size(); i++) {
            String nombreTarjeta = "";
            int tipoTarjeta = tarjetas.get(i).getTipoTarjeta();
            if (tipoTarjeta == GlobalConstant.TARJETA_TIPO_MC) {
                nombreTarjeta = "MasterCard";
            } else {
                if (tipoTarjeta == GlobalConstant.TARJETA_TIPO_VISA) {
                    nombreTarjeta = "VISA";
                } else {
                    if (tipoTarjeta == GlobalConstant.TARJETA_TIPO_AE) {
                        nombreTarjeta = "American Express";
                    }
                }

            }
            medios[i] = nombreTarjeta;
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_text, medios);
        mSpnTarjetas.setAdapter(adapter);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);

    }

    @Override
    public void onResume() {
        super.onResume();
//        getDialog().getWindow().setEnterTransition();
        getDialog().getWindow().setLayout(GlobalFunction.ConvertDpToPx(300), GlobalFunction.ConvertDpToPx(250));
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn_cerrar_dialog_paagar)
    public void cerrar() {
        dismissAllowingStateLoss();
    }

    @OnClick(R.id.btn_dialog_pagar)
    public void pagar() {


        new CountDownTimer(10000, 1000) {

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                Toast.makeText(getActivity(), "ARRIENDO TERMINADO", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }

    @OnClick(R.id.btn_dialog_test)
    public void test(){
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setCancelable(false);
        dialog.setTitle("Validando Pago");
        dialog.setMessage("Espere un momento...");
        dialog.show();
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
            dialog.dismiss();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("notificacion",true);
                intent.putExtra("direccion",getDireccionByRut(rutUsuario));
                startActivity(intent);
            }
        },3000);
    }

}
