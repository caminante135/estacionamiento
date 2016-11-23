package com.example.gerardo.miestacionamiento.view.ui;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.model.Estacionamiento;
import com.example.gerardo.miestacionamiento.view.ui.fragment.DetalleFragment;
import com.example.gerardo.miestacionamiento.view.ui.fragment.MapFragment;
import com.example.gerardo.miestacionamiento.view.ui.fragment.MiCuentaFragment;
import com.example.gerardo.miestacionamiento.view.ui.fragment.PreferenciasFragment;
import com.example.gerardo.miestacionamiento.controller.util.GlobalConstant;
import com.example.gerardo.miestacionamiento.controller.GlobalFunction;
import com.google.android.gms.maps.GoogleMap;

import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    GoogleMap mGoogleMap;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;


    @Bind(R.id.image_header)
    ImageView imageHeader;
    @Bind(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    Menu menu;
    Fragment fragment = null;
    @Bind(R.id.image_circle_header)
    CircleImageView imageCircleHeader;
    @Bind(R.id.txt_header_nombre)
    TextView txtHeaderNombre;
    @Bind(R.id.txt_header_tipo)
    TextView txtHeaderTipo;

    public static TextView txtToolbar;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    BroadcastReceiver mMessageReceiver;
    static String ns = Context.NOTIFICATION_SERVICE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        txtToolbar = (TextView) toolbar.findViewById(R.id.txt_toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        displayView(R.id.nav_home);
        navView.setNavigationItemSelectedListener(this);


        ImageView iv = (ImageView) navView.getHeaderView(0).findViewById(R.id.navHeader_image);


        iv.setImageBitmap(setImageBlurEffect(R.drawable.logo_last));
        imageHeader.setImageBitmap(setImageBlurEffect(R.drawable.logo_last));

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }

        //PREFERENCIAS
        prefs = getSharedPreferences(GlobalConstant.PREFS_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();

        //SETEO DATOS HEADER DEL MENU IZQUIERDO
        String nombre = prefs.getString(GlobalConstant.PREFS_NOMBRE, "");
        String apellido = prefs.getString(GlobalConstant.PREFS_APELLIDO_P, "");
        String rut = prefs.getString(GlobalConstant.PREFS_RUT,"");

        int cantV = GlobalFunction.calcularSizeArray(prefs.getString(GlobalConstant.PREFS_JSON_VEHICULOS,""));

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        List<Estacionamiento> estacionamientos = realm.where(Estacionamiento.class).equalTo("rutUsuario",rut).findAll();
//        List<Vehiculo> vehiculos = realm.where(Vehiculo.class).equalTo("rutUsuario",rut).findAll();
        realm.commitTransaction();

        setDatosDrawerAndHeader(nombre, apellido, prefs.getInt(GlobalConstant.PREFS_IDROL,4), cantV, estacionamientos.size());

        disableCollapse();

        mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                long[] s = { 0, 100, 10, 500, 10, 100, 0, 500, 10, 100, 10, 500 };
                vibrator.vibrate(s, -1);
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("name"));

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            boolean isNot = extras.getBoolean("notificacion");
            if (isNot){
                String direccion = extras.getString("direccion");
                crearNotificacion(direccion);
            }
        }

    }

    private void crearNotificacion(String direccion) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.logoappsintitulo);
        NotificationManager manager = (NotificationManager) getSystemService(ns);
        Notification noti = new Notification.Builder(this)
                .setContentTitle("Tu periodo de arriendo comenzó")
                .setContentText(String.format("Dirección: %s",direccion))
                .setSmallIcon(R.drawable.logoappsintitulo)
                .setLargeIcon(bitmap)
                .build();
        manager.notify(1,noti);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        syncFrags();
//        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame);
//        if (fragment instanceof MapFragment){
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.frame, fragment);
//            ft.commit();
//        }

    }

    private void syncFrags() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame);
        if (fragment instanceof MiCuentaFragment) {
            enableCollapse();
        } else {
            disableCollapse();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
//        hideOption(R.id.action_estacionamiento);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
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
        String title = null;

        switch (viewId) {
            case R.id.nav_home:
                if (!(fragment instanceof MapFragment)) {
                    fragment = new MapFragment().newInstance();
                    title = "Inicio";
                    disableCollapse();
                }
                break;
            case R.id.nav_profile:
                fragment = new MiCuentaFragment();
                title = "Mi Cuenta";
                enableCollapse();
                break;
            case R.id.nav_historial:
                title = "Historial";
                disableCollapse();
                break;
            case R.id.nav_prefs:
                fragment = new PreferenciasFragment();
                title = "Preferencias";
                disableCollapse();
                break;
            case R.id.nav_logout:
                AlertDialog.Builder builder = GlobalFunction.crearDialogYesNot(this, "Salir", "¿Desea salir de Mi Estacionamiento?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deletePrefs();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                break;
            default:
                fragment = null;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame, fragment);
//            ft.addToBackStack(null);
            ft.commit();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            txtToolbar.setText(title);
        }
        drawerLayout.closeDrawers();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displayView(item.getItemId());
        return true;
    }

    private void hideOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(false);
        invalidateOptionsMenu();
    }

    public void disableCollapse() {
        imageHeader.setVisibility(View.GONE);
        imageCircleHeader.setVisibility(View.GONE);
        txtHeaderNombre.setVisibility(View.GONE);
        txtHeaderTipo.setVisibility(View.GONE);
        collapsingToolbarLayout.setTitleEnabled(false);

    }

    public void enableCollapse() {
        imageHeader.setVisibility(View.VISIBLE);
        imageCircleHeader.setVisibility(View.VISIBLE);
        txtHeaderNombre.setVisibility(View.VISIBLE);
        txtHeaderTipo.setVisibility(View.VISIBLE);
        collapsingToolbarLayout.setTitleEnabled(true);
    }

    public Bitmap setImageBlurEffect(int drawable) {
        Bitmap image = BitmapFactory.decodeResource(getResources(),
                drawable);

        Bitmap blur = GlobalFunction.blurRenderScript(this, image, 25);

        return blur;
    }

    @Override
    protected void onStop() {
        deletePrefs();
        super.onStop();
    }

    private void deletePrefs() {
        editor.remove(GlobalConstant.PREFS_LATITUD);
        editor.remove(GlobalConstant.PREFS_LONGITUD);
        editor.putBoolean(GlobalConstant.PREFS_AUTOLOGIN,false);
        editor.apply();
    }

    private void setDatosDrawerAndHeader(String nombre, String apellido, int tipo, int cantVeh, int cantEst) {
        TextView txtNombre, txtTipo, txtCv, txtcE,txtNombreHeader,txtTipoHeader;

        View header = navView.getHeaderView(0);

        txtNombre = (TextView) header.findViewById(R.id.txt_drawer_nombre);
        txtTipo = (TextView) header.findViewById(R.id.txt_drawer_tipo);
        txtCv = (TextView) header.findViewById(R.id.txt_drawer_vehiculos);
        txtcE = (TextView) header.findViewById(R.id.txt_Drawer_estacionamientos);

        txtNombreHeader = (TextView) collapsingToolbarLayout.findViewById(R.id.txt_header_nombre);
        txtTipoHeader = (TextView) collapsingToolbarLayout.findViewById(R.id.txt_header_tipo);

        String tipoUsuario="";
        switch (tipo){
            case GlobalConstant.TIPO_DUEÑO:
                tipoUsuario = "Dueño";
                break;
            case GlobalConstant.TIPO_CLIENTE:
                tipoUsuario = "Cliente";
                break;
            case GlobalConstant.TIPO_ADMINISTRADOR:
                tipoUsuario = "Administrador";
                break;
            case GlobalConstant.TIPO_CONSULTOR:
                tipoUsuario = "Consultor";
        }

        txtNombre.setText(String.format("%s %s", nombre, apellido));
        txtTipo.setText(tipoUsuario);
        txtCv.setText(String.format(new Locale("es", "ES"), "Vehículos: %d", cantVeh));
        txtcE.setText(String.format(new Locale("es", "ES"), "Estacionamientos: %d", cantEst));

        txtNombreHeader.setText(String.format("%s %s", nombre, apellido));
        txtTipoHeader.setText(tipoUsuario);
    }

}
