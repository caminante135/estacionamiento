package com.example.gerardo.miestacionamiento.view.ui.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
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
import com.example.gerardo.miestacionamiento.controller.util.RunnableArgs;
import com.example.gerardo.miestacionamiento.model.Arriendo;
import com.example.gerardo.miestacionamiento.model.Estacionamiento;
import com.example.gerardo.miestacionamiento.model.FullTransaccionArriendo;
import com.example.gerardo.miestacionamiento.model.Tarjeta;
import com.example.gerardo.miestacionamiento.model.Transaccion;
import com.example.gerardo.miestacionamiento.view.ui.MainActivity;
import com.example.gerardo.miestacionamiento.view.ui.WebPayActivity;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    @Bind(R.id.btn_dialog_test)
    Button btnTest;

    String rutUsuario;
    int cantHoras;
    String fechaInicio;
    String fechaTermino;
    int idEstacionamiento;

    String rutArrendador;

    SharedPreferences prefs;

    public static PagoDialog newInstance(String rutUsuario, int cantHoras, String fechaInicio, String fechaTermino, Integer idEst) {
        PagoDialog dialog = new PagoDialog();
        Bundle b = new Bundle();
        b.putString(GlobalConstant.BUNDLE_RUT_USUARIO, rutUsuario);
        b.putInt("cantHoras", cantHoras);
        b.putString("fechaIni", fechaInicio);
        b.putString("fechaTer", fechaTermino);
        b.putInt("idEst",idEst);
        dialog.setArguments(b);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        rutUsuario = args.getString(GlobalConstant.BUNDLE_RUT_USUARIO, "");
        cantHoras = args.getInt("cantHoras", 0);
        fechaInicio = args.getString("fechaIni", "");
        fechaTermino = args.getString("fechaTer", "");
        idEstacionamiento = args.getInt("idEst",0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_pagar, container, false);
        ButterKnife.bind(this, root);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        prefs = getActivity().getSharedPreferences(GlobalConstant.PREFS_NAME, Context.MODE_PRIVATE);
        rutArrendador = prefs.getString(GlobalConstant.PREFS_RUT, "");
        setSpinner();

        return root;
    }

    private String getDireccionByRut(String rut) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Estacionamiento est = realm.where(Estacionamiento.class).equalTo("idEstacionamiento", idEstacionamiento).findFirst();
        realm.commitTransaction();

        return est.getDireccionEstacionamiento();
    }


    private Integer getCostoHora(String rut) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Estacionamiento est = realm.where(Estacionamiento.class).equalTo("idEstacionamiento", idEstacionamiento).findFirst();
        realm.commitTransaction();

        return est.getCostoHora();
    }

    private void setSpinner() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        List<Tarjeta> tarjetas = realm.where(Tarjeta.class).equalTo("rutUsuario", rutArrendador).findAll();
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
//        final ProgressDialog dialog = new ProgressDialog(getActivity());
//        dialog.setCancelable(false);
//        dialog.setTitle("Validando Pago");
//        dialog.setMessage("Espere un momento...");
//        dialog.show();
//        Handler mHandler = new Handler();
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                dialog.dismiss();
//                Intent intent = new Intent(getActivity(), WebPayActivity.class);
//                intent.putExtra("notificacion", true);
//                intent.putExtra("direccion", getDireccionByRut(rutUsuario));
//                startActivity(intent);
//            }
//        }, 3000);
        generarClaseAEnviar();
        Intent intent = new Intent(getActivity(), WebPayActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_dialog_test)
    public void test() {

        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setCancelable(false);
        dialog.setTitle("Validando Pago");
        dialog.setMessage("Espere un momento...");
        dialog.show();

        final RunnableArgs runnableArgs = new RunnableArgs() {
            @Override
            public void run() {
                dialog.dismiss();
                if (this.getResponse() == GlobalConstant.RESPONSE_LOGIN_CORRECT) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra("notificacion", true);
                    intent.putExtra("direccion", getDireccionByRut(rutUsuario));
                    intent.putExtra("idEst",idEstacionamiento);
                    intent.putExtra("rutUser",rutUsuario);
                    startActivity(intent);
                } else {
                    if (this.getResponse() == GlobalConstant.RESPONSE_LOGIN_INCORRECT) {
                        Toast.makeText(getActivity(), "El pago no se pudo realizar", Toast.LENGTH_SHORT).show();
                    } else {
                        if (this.getResponse() == GlobalConstant.RESPONSE_CONNECTION_ERROR) {

                            Toast.makeText(getActivity(), "Problemas al conectar, reintentelo en unos minutos", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        };
        GlobalFunction.generarTransaccion(getActivity(), generarClaseAEnviar(), runnableArgs);
//        Handler mHandler = new Handler();
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                dialog.dismiss();
//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                intent.putExtra("notificacion", true);
//                intent.putExtra("direccion", getDireccionByRut(rutUsuario));
//                startActivity(intent);
//            }
//        }, 3000);
    }

    private FullTransaccionArriendo generarClaseAEnviar() {
        Arriendo arriendo = new Arriendo();
        arriendo.setIdArriendo(JSONObject.NULL);
        arriendo.setHorasArrendadas(cantHoras);
        arriendo.setIdVehiculo(81);
        arriendo.setIdEstacionamiento(idEstacionamiento);
        arriendo.setIdTipoArriendo(1);
        arriendo.setIdEstado(2);
        arriendo.setCostoHora(getCostoHora(rutUsuario));
        arriendo.setFechaInicio(GlobalFunction.formatDateArriendo(fechaInicio));
        arriendo.setFechaTermino(GlobalFunction.formatDateArriendo(fechaTermino));

        Transaccion transaccion = new Transaccion();
        transaccion.setIdTransaccion(null);
        transaccion.setFechaTransaccion(currentDate());
        transaccion.setIdEstado(1);
        transaccion.setRutUsuarioUsuario(rutArrendador);
        transaccion.setRutUsuarioPRopietario(rutUsuario);
        //VER QUE ONDA
        transaccion.setIdEstado(1);
        transaccion.setIdArriendo(null);

        FullTransaccionArriendo full = new FullTransaccionArriendo();
        full.setArriendo(arriendo);
        full.setTransaccion(transaccion);

        return full;

    }

    private String currentDate() {
        Date date = new Date();
        String fecha = null;
        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
        fecha = curFormater.format(date);

        return fecha;

    }


}
