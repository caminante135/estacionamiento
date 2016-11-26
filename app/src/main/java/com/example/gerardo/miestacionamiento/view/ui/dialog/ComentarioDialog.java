package com.example.gerardo.miestacionamiento.view.ui.dialog;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.controller.GlobalFunction;
import com.example.gerardo.miestacionamiento.controller.util.GlobalConstant;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Gerardo on 25/11/2016.
 */
public class ComentarioDialog extends DialogFragment {

    @Bind(R.id.dialog_comentario_rb)
    RatingBar rbComentario;
    @Bind(R.id.dialog_comentario_editText)
    EditText editComentario;
    @Bind(R.id.dialog_comentario_btnCancelar)
    Button btnCancelar;
    @Bind(R.id.dialog_comentario_btnComentar)
    Button btnComentar;


    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_comentario, container, false);
        ButterKnife.bind(this, root);

        prefs = getActivity().getSharedPreferences(GlobalConstant.PREFS_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(GlobalFunction.ConvertDpToPx(350),ViewGroup.LayoutParams.WRAP_CONTENT );

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.dialog_comentario_btnComentar)
    public void onClick() {
        editor.putBoolean("dejarComentario", false);
        editor.apply();
    }


    @OnClick(R.id.dialog_comentario_btnCancelar)
    public void cancelar() {
        editor.putBoolean("dejarComentario", false);
        editor.apply();
        dismissAllowingStateLoss();
    }
}
