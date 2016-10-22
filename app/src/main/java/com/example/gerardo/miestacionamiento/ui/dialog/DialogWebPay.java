package com.example.gerardo.miestacionamiento.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.devmarvel.creditcardentry.library.CreditCard;
import com.devmarvel.creditcardentry.library.CreditCardForm;
import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.util.ExpirationFormatWatcher;
import com.example.gerardo.miestacionamiento.util.GlobalConstant;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
//                Toast.makeText(getActivity(), "Tipo: "+card.getCardType() + " Numero: "+ card.getCardNumber(), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
