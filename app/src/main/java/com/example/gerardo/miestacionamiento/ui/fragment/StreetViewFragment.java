package com.example.gerardo.miestacionamiento.ui.fragment;

import android.os.Bundle;
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

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

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
        mStreetView.setPosition(new LatLng(-33.4997293, -70.6164077));
    }
}
