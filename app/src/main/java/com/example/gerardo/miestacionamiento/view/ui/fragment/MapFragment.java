package com.example.gerardo.miestacionamiento.view.ui.fragment;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.controller.util.GlobalConstant;
import com.example.gerardo.miestacionamiento.controller.util.GlobalFunction;
import com.example.gerardo.miestacionamiento.controller.util.RunnableArgs;
import com.example.gerardo.miestacionamiento.model.Estacionamiento;
import com.example.gerardo.miestacionamiento.model.ResponseAllEstacionamientos;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.InfoWindowAdapter
        , GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMapClickListener {

    GoogleMap mGoogleMap;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Map<Marker,Estacionamiento> marcadores;
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
        View root = super.onCreateView(inflater, container, savedInstanceState);
        prefs = getActivity().getSharedPreferences(GlobalConstant.PREFS_NAME, Context.MODE_PRIVATE);
        marcadores = new HashMap<>();

        getMapAsync(this);



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

        setPermisosLocation();

        callWS();


        LatLng green = new LatLng(-33.500316, -70.616127);
//        LatLng red = new LatLng(-33.500593, -70.616803);
//        mGoogleMap.addMarker(new MarkerOptions()
//                .position(green)
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.greenmark))
//                .title("Estacionamiento 1"));
//        mGoogleMap.addMarker(new MarkerOptions()
//                .position(red)
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.redmark))
//                .title("Estacionamiento 2"));

        if (coordenadasSave == null){
            cameraPosition = CameraPosition.builder()
                    .target(green)
                    .zoom(15)
                    .build();
        }else{
            cameraPosition = CameraPosition.builder()
                    .target(coordenadasSave)
                    .zoom(15)
                    .build();
        }


        dialog.dismiss();
        mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }


    //EVENTO CLICK DE LA VENTANA DE INFORMACION
    @Override
    public void onInfoWindowClick(Marker marker) {
        Estacionamiento est = marcadores.get(marker);
        if (est != null){
            Usuario user = GlobalFunction.getUsuarioByIDEstacionamiento(getActivity(),est.getIdEstacionamiento());
            Log.d("FUNCIONO?",user.getNombre());
        }

        DetalleFragment fragment = DetalleFragment.newInstance(marker.getPosition());

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

        setViewInfoContents(v,marker);

        return v;
    }

    private void setViewInfoContents(View view,Marker marker){
        TextView txtComuna = (TextView) view.findViewById(R.id.info_window_comuna);
        TextView txtDireccion = (TextView) view.findViewById(R.id.info_window_direccion);
        TextView txtTamaño = (TextView) view.findViewById(R.id.info_window_tamaño);
        TextView txtEstado = (TextView) view.findViewById(R.id.info_window_estado);
        TextView txtValorHora = (TextView) view.findViewById(R.id.info_window_valor_hora);
        TextView txtComentarios = (TextView) view.findViewById(R.id.info_window_comentarios);

        Estacionamiento est = marcadores.get(marker);

        try{
            txtComuna.setText("San Joaquín");
            txtDireccion.setText(est.getDireccionEstacionamiento());
            if (est.getIdEstado() == GlobalConstant.ESTACIONAMIENTO_DISPONIBLE){
                txtEstado.setText("Disponible");
                txtEstado.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
            }else{
                txtEstado.setText("No Disponible");
                txtEstado.setTextColor(getActivity().getResources().getColor(R.color.noDisponible));
            }
            txtTamaño.setText(getActivity().getResources().getString(R.string.tamañoEst,"Normal"));
            txtValorHora.setText(getActivity().getResources().getString(R.string.valorHora,est.getCostoHora()));
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }
    private void setPermisosLocation(){
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mGoogleMap.setMyLocationEnabled(true);
            if (!GlobalFunction.isGpsActive(getActivity())){
                requestGPS();
            }
        }else {
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
    private void crearMarksMap(List<ResponseAllEstacionamientos> datos,@Nullable ProgressDialog dialog){

        int drawable = 0;
        for (int i = 0; i < datos.size(); i++) {
            ResponseAllEstacionamientos res = datos.get(i);

            for (int j = 0; j < res.getEstacionamientos().size(); j++) {
                Estacionamiento est = res.getEstacionamientos().get(j);

                if (est.getIdEstado() == GlobalConstant.ESTACIONAMIENTO_DISPONIBLE) {
                    drawable = R.drawable.greenmark;
                } else {
                    drawable = R.drawable.redmark;
                }

                LatLng latLng = new LatLng(Double.valueOf(est.getLatitud()), Double.valueOf(est.getLongitud()));

                Marker marker = mGoogleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .icon(BitmapDescriptorFactory.fromResource(drawable)));

                marcadores.put(marker, est);
            }

        }

        if (dialog!=null){
            if (dialog.isShowing()){
                dialog.dismiss();
            }
        }

    }
    private void callWS(){
        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Cargando Estacionamientos");
        dialog.setMessage("Espere un momento...");
        dialog.setCancelable(false);
        dialog.show();
        final RunnableArgs runnableArgs = new RunnableArgs(){
            @Override
            public void run() {
                if (this.getResponse() == GlobalConstant.RESPONSE_LOGIN_CORRECT){
                    crearMarksMap(GlobalFunction.convertToObjectGetEstacionamientos(prefs.getString(GlobalConstant.PREFS_JSON_GET_EST,"")),dialog);
                }else{
                    dialog.dismiss();
                }
            }
        };
        GlobalFunction.getEstacionamientos(getActivity(),runnableArgs);
    }
    private void requestGPS(){
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
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
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

}
