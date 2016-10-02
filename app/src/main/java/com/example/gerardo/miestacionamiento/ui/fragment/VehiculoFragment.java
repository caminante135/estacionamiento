package com.example.gerardo.miestacionamiento.ui.fragment;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gerardo.miestacionamiento.R;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class VehiculoFragment extends Fragment {


    public VehiculoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_vehiculo, container, false);
        return root;
    }

}
