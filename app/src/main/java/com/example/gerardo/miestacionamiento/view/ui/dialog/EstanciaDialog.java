package com.example.gerardo.miestacionamiento.view.ui.dialog;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.controller.GlobalFunction;
import com.example.gerardo.miestacionamiento.controller.util.GlobalConstant;
import com.example.gerardo.miestacionamiento.view.ui.fragment.ResumenFragment;
import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Gerardo on 17/11/2016.
 */
public class EstanciaDialog extends DialogFragment {

    @Bind(R.id.dialog_estancia_txt_llegada)
    TextView txtLlegada;
    @Bind(R.id.dialog_estancia_txt_salida)
    TextView txtSalida;
    @Bind(R.id.dialog_estancia_btn_cancelar)
    Button btnCancelar;
    @Bind(R.id.dialog_estancia_btn_aceptar)
    Button btnAceptar;

    private static final String TAG_DATETIME_FRAGMENT = "TAG_DATETIME_FRAGMENT";
    SwitchDateTimeDialogFragment dateTimeDialogFragment;
    String rutUsuario;
    int idEstacio;
    int cantH;
    String fechaHoraLlegada, fechaHoraSalida;
    @Bind(R.id.dialog_Estancia_image)
    ImageView imageView;

    public static EstanciaDialog newInstance(String usuario, int est) {
        EstanciaDialog dialog = new EstanciaDialog();
        Bundle b = new Bundle();
        b.putString(GlobalConstant.BUNDLE_RUT_USUARIO, usuario);
        b.putInt(GlobalConstant.BUNDLE_ID_ESTACIO, est);
        dialog.setArguments(b);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dateTimeDialogFragment = (SwitchDateTimeDialogFragment) getActivity()
                .getSupportFragmentManager().findFragmentByTag(TAG_DATETIME_FRAGMENT);

        if (dateTimeDialogFragment == null) {
            dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance(
                    getString(R.string.label_datetime_dialog),
                    getString(R.string.positive_button_datetime_picker),
                    getString(R.string.negative_button_datetime_picker)
            );
        }
        cantH = 0;
        Bundle args = getArguments();
        rutUsuario = args.getString(GlobalConstant.BUNDLE_RUT_USUARIO, "");
        idEstacio = args.getInt(GlobalConstant.BUNDLE_ID_ESTACIO, 0);
        setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_estancia, container, false);
        ButterKnife.bind(this, root);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setClipToOutline(true);
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.dialog_estancia_btn_aceptar)
    public void onClick() {
        if (cantH > 0) {

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_info, ResumenFragment.newInstance(
                            rutUsuario, idEstacio, cantH, fechaHoraLlegada, fechaHoraSalida))
                    .commitAllowingStateLoss();
            dismissAllowingStateLoss();
        } else {
            Toast.makeText(getActivity(), "Necesita de al menos 1 hora de estadía para arrendar el estacionamiento",
                    Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.dialog_estancia_txt_llegada)
    public void definirLlegada() {
        dateTimeDialogFragment.show(getActivity().getSupportFragmentManager(), TAG_DATETIME_FRAGMENT);

        dateTimeDialogFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                String fechaTemp = GlobalFunction.formatDate(date.toString());
                txtLlegada.setText(fechaTemp);
                fechaHoraLlegada = fechaTemp;
                setCantHoras();
            }

            @Override
            public void onNegativeButtonClick(Date date) {

            }
        });

    }

    @OnClick(R.id.dialog_estancia_txt_salida)
    public void definirSalida() {
        dateTimeDialogFragment.show(getActivity().getSupportFragmentManager(), TAG_DATETIME_FRAGMENT);
        if (fechaHoraSalida != null) {

        }
        dateTimeDialogFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                String fechaTemp = GlobalFunction.formatDate(date.toString());
                txtSalida.setText(fechaTemp);
                fechaHoraSalida = fechaTemp;
                setCantHoras();
            }

            @Override
            public void onNegativeButtonClick(Date date) {

            }
        });

    }

    private void setCantHoras() {
        if (fechaHoraLlegada != null && fechaHoraSalida != null) {
            cantH = GlobalFunction.hourBetweenDates(fechaHoraLlegada, fechaHoraSalida);
            if (cantH >= 0) {
//                mCantidadHoras.setText(getString(R.string.cantHoras,cantH));
            } else {
//                mCantidadHoras.setText(getString(R.string.cantHoras,"-"));
            }

        }
    }

    @Override
    public int getTheme() {
        return R.style.DialogAnim;
    }

    @Override
    public void onResume() {
        super.onResume();
        // GlobalFunction.ConvertDpToPx(300)
        getDialog().getWindow().setLayout(GlobalFunction.ConvertDpToPx(300), ViewGroup.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams lp = getDialog().getWindow().getAttributes();
        lp.dimAmount = 0.7f;
        getDialog().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getDialog().getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.bg_dialog));
    }

    @OnClick(R.id.dialog_estancia_btn_cancelar)
    public void cancelar() {
        dismissAllowingStateLoss();
    }
}
