package com.example.gerardo.miestacionamiento.view.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.devmarvel.creditcardentry.library.CreditCard;
import com.devmarvel.creditcardentry.library.CreditCardForm;
import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.controller.rest.ApiAdapter;
import com.example.gerardo.miestacionamiento.controller.rest.ApiConstants;
import com.example.gerardo.miestacionamiento.controller.util.ExpirationFormatWatcher;
import com.example.gerardo.miestacionamiento.controller.util.GlobalConstant;
import com.example.gerardo.miestacionamiento.model.Estacionamiento;
import com.example.gerardo.miestacionamiento.model.RegistroFullUsuario;
import com.example.gerardo.miestacionamiento.model.Tarjeta;
import com.example.gerardo.miestacionamiento.model.Usuario;
import com.example.gerardo.miestacionamiento.model.Vehiculo;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Gerardo on 02/10/2016.
 */
public class DialogWebPay extends DialogFragment {

    @Bind(R.id.edit_wp_ccform)
    CreditCardForm mCcform;
    @Bind(R.id.edit_wp_expiracion)
    EditText mExpiracion;
    @Bind(R.id.edit_wp_cvc)
    EditText mCvc;
    @Bind(R.id.edit_wp_nombre)
    EditText mNombre;
    @Bind(R.id.btn_wp_cancelar)
    Button btnCancelar;
    @Bind(R.id.btn_wp_registrar)
    Button btnRegistrar;

    String jsonUsuario;
    String jsonVehiculo;
    String jsonEstacionamiento;

    public DialogWebPay() {
    }

    public static DialogWebPay newInstance(String jsonUsuario, @Nullable String jsonVehiculo,
                                           @Nullable String jsonEstacionamiento){
        DialogWebPay dialogWebPay = new DialogWebPay();
        Bundle b = new Bundle();
        b.putString(GlobalConstant.BUNDLE_USUARIO, jsonUsuario);
        if (jsonVehiculo != null){
            b.putString(GlobalConstant.BUNDLE_VEHICULO, jsonVehiculo);
        }
        if (jsonEstacionamiento != null){
            b.putString(GlobalConstant.BUNDLE_ESTACIO, jsonEstacionamiento);
        }

        dialogWebPay.setArguments(b);

        return dialogWebPay;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        jsonUsuario = args.getString(GlobalConstant.BUNDLE_USUARIO,"");
        jsonVehiculo = args.getString(GlobalConstant.BUNDLE_VEHICULO,"");
        jsonEstacionamiento = args.getString(GlobalConstant.BUNDLE_ESTACIO,"");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_webpay, container, false);

        ButterKnife.bind(this, root);

        mExpiracion.addTextChangedListener(new ExpirationFormatWatcher());


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.btn_wp_cancelar, R.id.btn_wp_registrar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_wp_cancelar:
                dismissAllowingStateLoss();
                break;
            case R.id.btn_wp_registrar:
                CreditCard card = mCcform.getCreditCard();
                Gson gson = new Gson();
                Usuario usuario = gson.fromJson(jsonUsuario, Usuario.class);
                usuario.setEstado(1);
                Estacionamiento estacionamiento = null;
                Vehiculo vehiculo = null;
//                if (!jsonEstacionamiento.equals("")){
//                    estacionamiento = gson.fromJson(jsonEstacionamiento, Estacionamiento.class);
//                }
//                if (!jsonVehiculo.equals("")){
//                    vehiculo = gson.fromJson(jsonVehiculo, Vehiculo.class);
//                }
                Tarjeta tarjeta = new Tarjeta();
                tarjeta.setNumeroTarjeta(card.getCardNumber().trim());
                tarjeta.setTipoTarjeta(getTipoTarjeta(card.getCardType().toString()));
                tarjeta.setFechaExpiracion(mExpiracion.getText().toString().trim());
                tarjeta.setCodigoVerificacion(Integer.parseInt(mCvc.getText().toString()));
                tarjeta.setRutUsuario(usuario.getRut());

                RegistroFullUsuario r = new RegistroFullUsuario();
                r.setUsuario(usuario);
                r.setVehiculo(vehiculo);
                r.setEstacionamiento(estacionamiento);
                r.setTarjeta(tarjeta);

                Call<RegistroFullUsuario.ResponseRegistroFull> response = ApiAdapter.getApiService().registrarFullUsuario(r);
                response.enqueue(new Callback<RegistroFullUsuario.ResponseRegistroFull>() {
                    @Override
                    public void onResponse(Call<RegistroFullUsuario.ResponseRegistroFull> call, Response<RegistroFullUsuario.ResponseRegistroFull> response) {

                    }

                    @Override
                    public void onFailure(Call<RegistroFullUsuario.ResponseRegistroFull> call, Throwable t) {

                    }
                });

//                Toast.makeText(getActivity(), "Tipo: "+card.getCardType() + " Numero: "+ card.getCardNumber(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private int getTipoTarjeta(String tipoTarjeta){
        switch (tipoTarjeta){
            case "MasterCard":
                return GlobalConstant.TARJETA_TIPO_MC;

            case "VISA":
                return GlobalConstant.TARJETA_TIPO_VISA;

            case "American Express":
                return GlobalConstant.TARJETA_TIPO_AE;

            default:
                return GlobalConstant.TARJETA_TIPO_BEBITO;

        }
    }

}
