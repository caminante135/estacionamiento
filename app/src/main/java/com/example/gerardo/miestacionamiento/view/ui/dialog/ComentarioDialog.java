package com.example.gerardo.miestacionamiento.view.ui.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.controller.GlobalFunction;
import com.example.gerardo.miestacionamiento.controller.util.GlobalConstant;
import com.example.gerardo.miestacionamiento.controller.util.RunnableArgs;
import com.example.gerardo.miestacionamiento.model.Evaluacion;
import com.example.gerardo.miestacionamiento.view.ui.MainActivity;

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

    String rutCalificador;
    String rutEvaluado;
    Integer idEstacionamiento;


    public static ComentarioDialog newInstance(String rutEvaluado, Integer idEst){
        ComentarioDialog fragment = new ComentarioDialog();
        Bundle bundle = new Bundle();
        bundle.putString("rutEvaluado",rutEvaluado);
        bundle.putInt("idEst",idEst);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
        Bundle args = getArguments();
        rutEvaluado = args.getString("rutEvaluado");
        idEstacionamiento = args.getInt("idEst");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_comentario, container, false);
        ButterKnife.bind(this, root);

        prefs = getActivity().getSharedPreferences(GlobalConstant.PREFS_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
        rutCalificador = prefs.getString(GlobalConstant.PREFS_RUT,"");
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
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setCancelable(false);
        dialog.setTitle("Enviando comentario");
        dialog.setMessage("Espere un momento");

        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setComentario(editComentario.getText().toString());
        evaluacion.setCalificacion((int) rbComentario.getRating());
        evaluacion.setRutEvaluado(rutEvaluado);
        evaluacion.setRutCalificador(rutCalificador);
        evaluacion.setIdEstacionamiento(idEstacionamiento);

        final RunnableArgs runnableArgs = new RunnableArgs() {
            @Override
            public void run() {
                dialog.dismiss();
                if (this.getResponseBoolean()) {
                    Toast.makeText(getActivity(), "Su evaluacion ha sido enviada con éxito", Toast.LENGTH_SHORT).show();
                    GlobalFunction.cargarEvaluaciones();
                    startActivity(new Intent(getActivity(),MainActivity.class));
                } else {
                    Toast.makeText(getActivity(), "No se pudo enviar su evaluación", Toast.LENGTH_SHORT).show();
                }
            }
        };

        if (rbComentario.getRating()==0){
            Toast.makeText(getActivity(), "Califíque su estadía antes de enviar", Toast.LENGTH_SHORT).show();
        }else{
            if (editComentario.getText().toString().trim().length()==0){
                Toast.makeText(getActivity(), "Escriba un comentario antes de enviar", Toast.LENGTH_SHORT).show();
            }else{
                dialog.show();
                GlobalFunction.insertcomentario(evaluacion, runnableArgs);
            }
        }

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
