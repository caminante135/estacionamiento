package com.example.gerardo.miestacionamiento.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gerardo.miestacionamiento.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MiCuentaFragment extends Fragment {


    public MiCuentaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mi_cuenta, container, false);
    }

}
