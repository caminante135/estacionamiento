package com.example.gerardo.miestacionamiento.view.ui.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.controller.util.GlobalConstant;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.InfoWindowAdapter
        , GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMapClickListener {

    GoogleMap mGoogleMap;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    LatLng coordenadasSave;
    CameraPosition cameraPosition;


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

        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Cargando");
        dialog.setMessage("Cargando los estacionamientos");
        dialog.setCancelable(false);
        dialog.show();
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

        LatLng green = new LatLng(-33.500316, -70.616127);
        LatLng red = new LatLng(-33.500593, -70.616803);
        mGoogleMap.addMarker(new MarkerOptions()
                .position(green)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.greenmark))
                .title("Estacionamiento 1"));
        mGoogleMap.addMarker(new MarkerOptions()
                .position(red)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.redmark))
                .title("Estacionamiento 2"));

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
        DetalleFragment fragment = DetalleFragment.newInstance(marker.getPosition());

        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame, fragment).commit();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mGoogleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.greenmark))
                .title("Marker Dinámico"));
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.googlemap_info_window, null);

        v.setLayoutParams(new LinearLayout.LayoutParams(850, ViewGroup.LayoutParams.WRAP_CONTENT));

        return v;
    }
}