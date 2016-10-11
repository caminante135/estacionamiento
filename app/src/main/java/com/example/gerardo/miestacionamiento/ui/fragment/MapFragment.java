package com.example.gerardo.miestacionamiento.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gerardo.miestacionamiento.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
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
                                ,GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMapClickListener{

    GoogleMap mGoogleMap;


    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance(){
        MapFragment fragment = new MapFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);

        getMapAsync(this);

        return root;
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

        CameraPosition cameraPosition = CameraPosition.builder()
                .target(green)
                .zoom(15)
                .build();



        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }




    //EVENTO CLICK DE LA VENTANA DE INFORMACION
    @Override
    public void onInfoWindowClick(Marker marker) {
        StreetViewFragment fragment = StreetViewFragment.newInstance(marker.getPosition());

        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame,fragment).commit();
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
        View v = getActivity().getLayoutInflater().inflate(R.layout.googlemap_info_window,null);

        v.setLayoutParams(new LinearLayout.LayoutParams(850, ViewGroup.LayoutParams.WRAP_CONTENT));


        return v;
    }
}
