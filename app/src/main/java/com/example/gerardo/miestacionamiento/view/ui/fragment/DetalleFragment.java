package com.example.gerardo.miestacionamiento.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.controller.util.GlobalConstant;
import com.example.gerardo.miestacionamiento.controller.util.GlobalFunction;
import com.example.gerardo.miestacionamiento.model.Estacionamiento;
import com.example.gerardo.miestacionamiento.model.Usuario;
import com.example.gerardo.miestacionamiento.view.ui.MainActivity;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Gerardo on 10/10/2016.
 */
public class DetalleFragment extends Fragment implements OnStreetViewPanoramaReadyCallback {

    StreetViewPanorama mStreetView;
    SupportMapFragment fragmentStreetView;
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
    @Bind(R.id.detalle_scrollView)
    ScrollView mainScrollView;
    @Bind(R.id.transparent_image)
    ImageView transparentImageView;

    String jsonUsuario;
    String jsonEstacionamiento;


    public static DetalleFragment newInstance(LatLng coordenadas, Usuario usuario, Estacionamiento est) {
        DetalleFragment fragment = new DetalleFragment();
        Bundle b = new Bundle();
        b.putDouble("latitud", coordenadas.latitude);
        b.putDouble("longitud", coordenadas.longitude);
        if (usuario!= null){
            b.putString(GlobalConstant.BUNDLE_USUARIO,GlobalFunction.createJSONObject(usuario));
        }
        if (est != null){
            b.putString(GlobalConstant.BUNDLE_ESTACIO,GlobalFunction.createJSONObject(est));
        }
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
        jsonUsuario = args.getString(GlobalConstant.BUNDLE_USUARIO,"");
        jsonEstacionamiento = args.getString(GlobalConstant.BUNDLE_ESTACIO,"");
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
//        View fragmentView = super.onCreateView(layoutInflater, viewGroup, bundle);
        View root = layoutInflater.inflate(R.layout.fragment_detalle, viewGroup, false);
        ButterKnife.bind(this,root);
//        getStreetViewPanoramaAsync(this);
        setContentViews();

        SupportStreetViewPanoramaFragment streetViewPanoramaFragment =
                (SupportStreetViewPanoramaFragment) getChildFragmentManager()
                        .findFragmentById(R.id.street_view);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);

        setTouchEvent();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity.txtToolbar.setText("Detalle Estacionamiento");

    }

    @OnClick(R.id.btn_detalle_solicitar)
    public void solicitar(){
        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null)
                .replace(R.id.frame,EstanciaFragment.newInstance(jsonUsuario,jsonEstacionamiento))
                .commitAllowingStateLoss();
    }

    private void setContentViews(){
        Usuario usuario = new Gson().fromJson(jsonUsuario,Usuario.class);
        Estacionamiento est = new Gson().fromJson(jsonEstacionamiento, Estacionamiento.class);

        if (est!= null){
            mComuna.setText("San Joaquín");
            mDireccion.setText(est.getDireccionEstacionamiento());
            mTamaño.setText("Normal");
            if (est.getNumeroEst()!=0){
                mNumero.setText(String.valueOf(est.getNumeroEst()));
            }else{
                mNumero.setText("N/A");
            }
            if (est.getPisoUbicacion()!=0){
                mPiso.setText(String.valueOf(est.getPisoUbicacion()));
            }else{
                mPiso.setText("N/A");
            }
            if (est.getCamaraVigilancia() == 1){
                mCamaraVigilancia.setText("Sí");
            }else{
                mCamaraVigilancia.setText("No");
            }
            mValorHora.setText(String.valueOf(est.getCostoHora()));
        }
        if (usuario != null){
            mNombre.setText(usuario.getNombre());
            mApellido.setText(getActivity().getResources().getString(R.string.resumenApellido,
                    usuario.getApellidoPaterno(),usuario.getApellidoMaterno()));
            mCorreo.setText(usuario.getCorreo());
        }

    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
//        Estacionamiento est = new Gson().fromJson(jsonEstacionamiento, Estacionamiento.class);
//
//        LatLng latLng = new LatLng(Double.valueOf(est.getLatitud()),Double.valueOf(est.getLongitud()));

        panorama.setPosition(coordenadas);

    }

    private void setTouchEvent(){
        transparentImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        mainScrollView.requestDisallowInterceptTouchEvent(true);
                        // Disable touch on transparent view
                        return false;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        mainScrollView.requestDisallowInterceptTouchEvent(false);
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        mainScrollView.requestDisallowInterceptTouchEvent(true);
                        return false;

                    default:
                        return true;
                }
            }
        });
    }

//    @Override
//    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
//        mStreetView = streetViewPanorama;
//        mStreetView.setPosition(coordenadas);
//
//    }
}
