package com.example.gerardo.miestacionamiento.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gerardo.miestacionamiento.R;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Gerardo on 10/10/2016.
 */
public class StreetViewFragment extends SupportStreetViewPanoramaFragment implements OnStreetViewPanoramaReadyCallback {

    StreetViewPanorama mStreetView;
    LatLng coordenadas;

    public static StreetViewFragment newInstance(LatLng coordenadas) {
        StreetViewFragment fragment = new StreetViewFragment();
        Bundle b = new Bundle();
        b.putDouble("latitud",coordenadas.latitude);
        b.putDouble("longitud",coordenadas.longitude);
        fragment.setArguments(b);
        return fragment;

    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle args = getArguments();
        Double la = args.getDouble("latitud");
        Double lo = args.getDouble("longitud");
        coordenadas = new LatLng(la,lo);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View fragmentView = super.onCreateView(layoutInflater, viewGroup, bundle);
        getStreetViewPanoramaAsync(this);
        return fragmentView;
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        mStreetView = streetViewPanorama;
        mStreetView.setPosition(coordenadas);
        Log.d("STREETVIEW",coordenadas.toString());
    }
}
