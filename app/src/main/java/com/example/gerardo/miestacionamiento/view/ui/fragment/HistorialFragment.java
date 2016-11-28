package com.example.gerardo.miestacionamiento.view.ui.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.controller.rest.ApiAdapter;
import com.example.gerardo.miestacionamiento.controller.util.GlobalConstant;
import com.example.gerardo.miestacionamiento.model.Transaccion;
import com.example.gerardo.miestacionamiento.view.adapter.TransaccionAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistorialFragment extends Fragment {


    @Bind(R.id.historial_recyclerview)
    RecyclerView recyclerView;
    @Bind(R.id.historial_nocontent)
    TextView txtVacio;

    TransaccionAdapter adapter;

    public HistorialFragment() {
        // Required empty public constructor
    }

    SharedPreferences prefs;
    String rutUsuario;
    public static HistorialFragment newInstance() {
        HistorialFragment fragment = new HistorialFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_historial, container, false);
        ButterKnife.bind(this, root);
        prefs = getActivity().getSharedPreferences(GlobalConstant.PREFS_NAME, Context.MODE_PRIVATE);
        rutUsuario = prefs.getString(GlobalConstant.PREFS_RUT,"");
        getTransacciones();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void getTransacciones(){
        if (!rutUsuario.equals("")){
            Transaccion t = new Transaccion();
            t.setRutUsuarioUsuario(rutUsuario);
            Call<Transaccion.TransaccionArriendo> retroCall = ApiAdapter.getApiService().selectTransaccionArriendoByRut(t);
            retroCall.enqueue(new Callback<Transaccion.TransaccionArriendo>() {
                @Override
                public void onResponse(Call<Transaccion.TransaccionArriendo> call, Response<Transaccion.TransaccionArriendo> response) {
                    if (response.body().getMsg().toLowerCase().equals("true")){
                        if (response.body().getTransaccions().size()>0){
                            adapter = new TransaccionAdapter(getActivity(),response.body().getTransaccions());
                            recyclerView.setAdapter(adapter);
                        }else{
                            txtVacio.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }

                    }else{
                        txtVacio.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<Transaccion.TransaccionArriendo> call, Throwable t) {
                    txtVacio.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            });
        }else{
            txtVacio.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }
}
