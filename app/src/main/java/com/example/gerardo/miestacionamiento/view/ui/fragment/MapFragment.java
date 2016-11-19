package com.example.gerardo.miestacionamiento.view.ui.fragment;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.controller.util.GlobalConstant;
import com.example.gerardo.miestacionamiento.controller.GlobalFunction;
import com.example.gerardo.miestacionamiento.controller.util.RunnableArgs;
import com.example.gerardo.miestacionamiento.model.Estacionamiento;
import com.example.gerardo.miestacionamiento.model.Usuario;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.InfoWindowAdapter
        , GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMapClickListener, TextWatcher {

    @Bind(R.id.btn_buscar_mapfragment)
    ImageButton btnBuscar;
    @Bind(R.id.edit_direccion_mapfragment)
    EditText editDireccion;
    @Bind(R.id.btn_borrar_mapfragment)
    ImageButton btnBorrar;


    GoogleMap mGoogleMap;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private static Map<Marker, Estacionamiento> marcadores;
    LatLng coordenadasSave;
    CameraPosition cameraPosition;

    private static final int LOCATION_REQUEST_CODE = 1;



    public MapFragment() {
        // Required empty public constructor
    }

    ProgressDialog dialog;

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, root);
        prefs = getActivity().getSharedPreferences(GlobalConstant.PREFS_NAME, Context.MODE_PRIVATE);

        if (marcadores == null) {
            marcadores = new HashMap<>();
        }

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        supportMapFragment.getMapAsync(this);

        editDireccion.addTextChangedListener(this);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            Double lat = Double.valueOf(prefs.getString(GlobalConstant.PREFS_LATITUD, ""));
            Double lon = Double.valueOf(prefs.getString(GlobalConstant.PREFS_LONGITUD, ""));
            coordenadasSave = new LatLng(lat, lon);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            Log.e("EXCEPTION_MAP", e.getMessage());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        editor = prefs.edit();
        editor.putString(GlobalConstant.PREFS_LATITUD, String.valueOf(mGoogleMap.getCameraPosition().target.latitude));
        editor.putString(GlobalConstant.PREFS_LONGITUD, String.valueOf(mGoogleMap.getCameraPosition().target.longitude));
        editor.apply();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setOnInfoWindowClickListener(this);
        mGoogleMap.setInfoWindowAdapter(this);
        mGoogleMap.setOnMapClickListener(this);

        UiSettings settings = mGoogleMap.getUiSettings();
        settings.setZoomControlsEnabled(true);
        settings.setCompassEnabled(false);
        settings.setMyLocationButtonEnabled(true);
        settings.setMapToolbarEnabled(false);

        mGoogleMap.setPadding(0, GlobalFunction.ConvertDpToPx(60), 0, 0);

        setPermisosLocation();

        callWS();
        Location myLocation = null;
        if (GlobalFunction.isGpsActive(getActivity())) {

            if (ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, true);
            myLocation = locationManager.getLastKnownLocation(provider);
        }
        LatLng green = null;
        if (myLocation != null) {
            green = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        } else {
            green = new LatLng(-33.500316, -70.616127);
        }
//        LatLng red = new LatLng(-33.500593, -70.616803);
//        mGoogleMap.addMarker(new MarkerOptions()
//                .position(green)
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.greenmark))
//                .title("Estacionamiento 1"));
//        mGoogleMap.addMarker(new MarkerOptions()
//                .position(red)
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.redmark))
//                .title("Estacionamiento 2"));

        if (coordenadasSave == null) {
            cameraPosition = CameraPosition.builder()
                    .target(green)
                    .zoom(15)
                    .build();
        } else {
            cameraPosition = CameraPosition.builder()
                    .target(coordenadasSave)
                    .zoom(15)
                    .build();
        }


        dialog.dismiss();
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(20));
        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }


    //EVENTO CLICK DE LA VENTANA DE INFORMACION
    @Override
    public void onInfoWindowClick(Marker marker) {
        Estacionamiento est = marcadores.get(marker);
        Usuario user = null;
        if (est != null) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            Estacionamiento estApoyo = realm.where(Estacionamiento.class).equalTo("idEstacionamiento", est.getIdEstacionamiento()).findFirst();
            user = realm.where(Usuario.class).equalTo("rutUsuario", estApoyo.getRutUsuario()).findFirst();
            realm.commitTransaction();
        }

        DetalleFragment fragment = DetalleFragment.newInstance(marker.getPosition(), user, est);

        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, fragment).commit();
    }

    @Override
    public void onMapClick(LatLng latLng) {
//        mGoogleMap.addMarker(new MarkerOptions()
//                .position(latLng)
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.greenmark))
//                .title("Marker Dinámico"));
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.googlemap_info_window, null);
        v.setLayoutParams(new LinearLayout.LayoutParams(750, ViewGroup.LayoutParams.WRAP_CONTENT));

        setViewInfoContents(v, marker);

        return v;
    }

    private void setViewInfoContents(View view, Marker marker) {
        TextView txtComuna = (TextView) view.findViewById(R.id.info_window_comuna);
        TextView txtDireccion = (TextView) view.findViewById(R.id.info_window_direccion);
        TextView txtTamaño = (TextView) view.findViewById(R.id.info_window_tamaño);
        TextView txtEstado = (TextView) view.findViewById(R.id.info_window_estado);
        TextView txtValorHora = (TextView) view.findViewById(R.id.info_window_valor_hora);
        TextView txtComentarios = (TextView) view.findViewById(R.id.info_window_comentarios);

        Estacionamiento est = marcadores.get(marker);

        try {
            txtComuna.setText("San Joaquín");
            txtDireccion.setText(est.getDireccionEstacionamiento());
            if (est.getIdEstado() == GlobalConstant.ESTACIONAMIENTO_DISPONIBLE) {
                txtEstado.setText("Disponible");
                txtEstado.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
            } else {
                txtEstado.setText("No Disponible");
                txtEstado.setTextColor(getActivity().getResources().getColor(R.color.noDisponible));
            }
            txtTamaño.setText(getActivity().getResources().getString(R.string.tamañoEst, "Normal"));
            txtValorHora.setText(getActivity().getResources().getString(R.string.valorHora, est.getCostoHora()));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    private void setPermisosLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mGoogleMap.setMyLocationEnabled(true);
            if (!GlobalFunction.isGpsActive(getActivity())) {
                requestGPS();
            }
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Mostrar diálogo explicativo
            } else {
                // Solicitar permiso
                ActivityCompat.requestPermissions(
                        getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }
        }
    }

    private void crearMarksMap(List<Estacionamiento> datos, @Nullable ProgressDialog dialog) {

        int drawable = 0;
        for (int i = 0; i < datos.size(); i++) {
            Estacionamiento estacionamiento = datos.get(i);
            if (estacionamiento.getIdEstado() == GlobalConstant.ESTACIONAMIENTO_DISPONIBLE) {
                drawable = R.drawable.greenmark;
            } else {
                drawable = R.drawable.redmark;
            }
            LatLng latLng = new LatLng(Double.valueOf(estacionamiento.getLatitud()), Double.valueOf(estacionamiento.getLongitud()));

            Marker marker = mGoogleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(drawable)));

            marcadores.put(marker, estacionamiento);
        }

        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

    }

    private void callWS() {
        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Cargando Estacionamientos");
        dialog.setMessage("Espere un momento...");
        dialog.setCancelable(false);
        dialog.show();
        final RunnableArgs runnableArgs = new RunnableArgs() {
            @Override
            public void run() {
                if (this.getResponse() == GlobalConstant.RESPONSE_LOGIN_CORRECT) {
//                    crearMarksMap(GlobalFunction.convertToObjectGetEstacionamientos(prefs.getString(GlobalConstant.PREFS_JSON_GET_EST, "")), dialog);
                    Realm realm = Realm.getDefaultInstance();
                    RealmResults<Estacionamiento> estacionamientoses = realm.where(Estacionamiento.class)
                            .findAll();
                    crearMarksMap(estacionamientoses, dialog);
                } else {
                    dialog.dismiss();
                }
            }
        };
        GlobalFunction.getEstacionamientos(getActivity(), runnableArgs);
    }

    private void requestGPS() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());
        alertDialogBuilder
                .setMessage("GPS esta desactivado. ¿Deseas encenderlo?")
                .setCancelable(false)
                .setPositiveButton("Activar GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                getActivity().startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            // ¿Permisos asignados?
            if (permissions.length > 0 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                Toast.makeText(getActivity(), "Error de permisos", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (!editable.toString().equals("")){
            if (editable.toString().length()==1){
                btnBuscar.setVisibility(View.VISIBLE);
                btnBorrar.setVisibility(View.VISIBLE);
            }
//            Animation fadeIn = new AlphaAnimation(0,1);
//            fadeIn.setInterpolator(new DecelerateInterpolator());
//            fadeIn.setDuration(1000);
//
//            btnBorrar.animate()
//                    .alpha(0.1f)
//                    .setDuration(500);
//            btnBuscar.setAnimation(fadeIn);
//            btnBorrar.setAnimation(fadeIn);
//            btnBuscar.animate()
//                    .alpha(0.1f)
//                    .setDuration(500)
//                    .setListener(new Animator.AnimatorListener() {
//                @Override
//                public void onAnimationStart(Animator animator) {
//
//                }
//
//                @Override
//                public void onAnimationEnd(Animator animator) {
//                    btnBuscar.setVisibility(View.VISIBLE);
//                    btnBorrar.setVisibility(View.VISIBLE);
//                }
//
//                @Override
//                public void onAnimationCancel(Animator animator) {
//
//                }
//
//                @Override
//                public void onAnimationRepeat(Animator animator) {
//
//                }
//            });
//            Animation fadeIn = new AlphaAnimation(0, 1);
//            fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
//            fadeIn.setDuration(1000);
//
//            Animation fadeOut = new AlphaAnimation(1, 0);
//            fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
//            fadeOut.setStartOffset(1000);
//            fadeOut.setDuration(1000);
//
//            AnimationSet animation = new AnimationSet(false); //change to false
//            animation.addAnimation(fadeIn);
//            animation.addAnimation(fadeOut);
//
//            btnBuscar.setAnimation(animation);
//            btnBuscar.startAnimation(animation);

        }else{
            btnBuscar.setVisibility(View.GONE);
            btnBorrar.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btn_buscar_mapfragment)
    public void buscarDireccion(){
        LatLng coordenadas = GlobalFunction.getCoordinatesFromAddress(getActivity(),editDireccion.getText().toString());
        GlobalFunction.hideKeyboardFrom(getActivity(),editDireccion);
        if (coordenadas != null){
            cameraPosition = CameraPosition.builder()
                    .target(coordenadas)
                    .zoom(15)
                    .build();
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }else{
            Toast.makeText(getActivity(), "Dirección no encontrada", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_borrar_mapfragment)
    public void borrarTexto(){
        if (editDireccion.getText().toString().length() > 0){
            editDireccion.setText("");
            editDireccion.clearFocus();
        }
    }

}
