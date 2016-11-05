package com.example.gerardo.miestacionamiento.view.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.gerardo.miestacionamiento.R;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.model.LatLng;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Gerardo on 10/10/2016.
 */
public class DetalleFragment extends Fragment {

    StreetViewPanorama mStreetView;
    LatLng coordenadas;

    @Bind(R.id.txt_detalle_comuna)
    TextView mComuna;
    @Bind(R.id.txt_detalle_direccion)
    TextView mDireccion;
    @Bind(R.id.txt_detalle_tamaño)
    TextView mTamaño;
    @Bind(R.id.txt_detalle_numero)
    TextView mNumero;
    @Bind(R.id.txt_detalle_piso)
    TextView mPiso;
    @Bind(R.id.txt_detalle_camara_vigilancia)
    TextView mCamaraVigilancia;
    @Bind(R.id.txt_detalle_valor_hora)
    TextView mValorHora;
    @Bind(R.id.txt_detalle_nombre)
    TextView mNombre;
    @Bind(R.id.txt_detalle_apellido)
    TextView mApellido;
    @Bind(R.id.txt_detalle_correo)
    TextView mCorreo;
    @Bind(R.id.rating_detalle_valoracion)
    RatingBar mRatingBar;
    @Bind(R.id.btn_detalle_solicitar)
    TextView mBtnSolicitar;


    public static DetalleFragment newInstance(LatLng coordenadas) {
        DetalleFragment fragment = new DetalleFragment();
        Bundle b = new Bundle();
        b.putDouble("latitud", coordenadas.latitude);
        b.putDouble("longitud", coordenadas.longitude);
        fragment.setArguments(b);
        return fragment;

    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle args = getArguments();
        Double la = args.getDouble("latitud");
        Double lo = args.getDouble("longitud");
        coordenadas = new LatLng(la, lo);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
//        View fragmentView = super.onCreateView(layoutInflater, viewGroup, bundle);
        View root = layoutInflater.inflate(R.layout.fragment_detalle, viewGroup, false);
        ButterKnife.bind(this,root);
//        getStreetViewPanoramaAsync(this);
        return root;
    }

    @OnClick(R.id.btn_detalle_solicitar)
    public void solicitar(){
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null)
                .replace(R.id.frame,EstanciaFragment.newInstance())
                .commitAllowingStateLoss();
    }

//    @Override
//    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
//        mStreetView = streetViewPanorama;
//        mStreetView.setPosition(coordenadas);
//
//    }
}
