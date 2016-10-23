package com.example.gerardo.miestacionamiento.ui.dialog;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.util.GlobalFunction;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Gerardo on 21/10/2016.
 */
public class PagoDialog extends DialogFragment {


    @Bind(R.id.spn_pagar_tarjetas)
    Spinner mSpnTarjetas;

    public static PagoDialog newInstance() {
        PagoDialog dialog = new PagoDialog();
        return dialog;
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

    private void setSpinner() {
        String[] medios = {"MasterCard", "American Express", "VISA", "pobree"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_text, medios);
        mSpnTarjetas.setAdapter(adapter);
//        ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(getActivity(), R.layout.spinner_text, years );
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
//        mSpnTarjetas.setAdapter(langAdapter);
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
}
