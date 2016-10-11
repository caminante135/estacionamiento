package com.example.gerardo.miestacionamiento.ui;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.ui.fragment.MapFragment;
import com.example.gerardo.miestacionamiento.util.GlobalFunction;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {

    GoogleMap mGoogleMap;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        displayView(R.id.nav_home);
        navView.setNavigationItemSelectedListener(this);


        ImageView iv = (ImageView) navView.getHeaderView(0).findViewById(R.id.navHeader_image);

        Bitmap image = BitmapFactory.decodeResource(getResources(),
                R.drawable.logo_last);
        Bitmap blur = GlobalFunction.blurRenderScript(this,image,25);

        iv.setImageBitmap(blur);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        hideOption(R.id.action_estacionamiento);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_estacionamiento:
                break;
            case R.id.action_vehiculo:
                break;
            case R.id.action_tarjeta:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void displayView(int viewId) {
        Fragment fragment = null;
        String title = null;

        switch (viewId) {
            case R.id.nav_home:
                fragment = new MapFragment().newInstance();
                title = "Inicio";
                break;
            case R.id.nav_profile:
                break;
            case R.id.nav_historial:
                break;
            case R.id.nav_prefs:
                break;
            case R.id.nav_logout:
                AlertDialog.Builder builder = GlobalFunction.crearDialogYesNot(this,"Salir","¿Desea salir de Mi Estacionamiento?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame, fragment);
            ft.commit();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(title);
        }
        drawerLayout.closeDrawers();
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displayView(item.getItemId());
        return true;
    }

    private void hideOption(int id)
    {
        MenuItem item = menu.findItem(id);
        item.setVisible(false);
        invalidateOptionsMenu();
    }

}
