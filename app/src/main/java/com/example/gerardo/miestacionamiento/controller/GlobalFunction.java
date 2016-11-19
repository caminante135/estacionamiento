package com.example.gerardo.miestacionamiento.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.gerardo.miestacionamiento.controller.util.GlobalConstant;
import com.example.gerardo.miestacionamiento.controller.util.RunnableArgs;
import com.example.gerardo.miestacionamiento.model.Comuna;
import com.example.gerardo.miestacionamiento.model.Estacionamiento;
import com.example.gerardo.miestacionamiento.model.ListaEstacionamientosRealm;
import com.example.gerardo.miestacionamiento.model.Marca;
import com.example.gerardo.miestacionamiento.model.Modelo;
import com.example.gerardo.miestacionamiento.model.ResponseAllEstacionamientos;
import com.example.gerardo.miestacionamiento.model.ResponseLogin;
import com.example.gerardo.miestacionamiento.model.Tarjeta;
import com.example.gerardo.miestacionamiento.model.Usuario;
import com.example.gerardo.miestacionamiento.controller.rest.ApiAdapter;
import com.example.gerardo.miestacionamiento.model.Vehiculo;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmList;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Gerardo on 01/10/2016.
 */
public final class GlobalFunction {


    //Funcion para convertir unidades DP a Pixeles (PX)
    public static int ConvertDpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    //ESCONDE EL TECLADO
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean isEmpty(String value) {
        if (value.equals("") || value == null || value.length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    //VALIDADOR DE RUT CHILENO
    public static boolean isRut(String rut) {
        boolean validacion = false;
        try {
            rut = rut.toUpperCase();
            rut = rut.replace(".", "");
            rut = rut.replace("-", "");
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));

            char dv = rut.charAt(rut.length() - 1);

            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                validacion = true;
            }

        } catch (java.lang.NumberFormatException e) {
        } catch (Exception e) {
        }
        return validacion;
    }

    //VALIDAR DE EMAIL
    public static boolean isValidEmail(String target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    //Añadir efecto blur al imageView
    @SuppressLint("NewApi")
    public static Bitmap blurRenderScript(Context context, Bitmap smallBitmap, int radius) {
        try {
            smallBitmap = RGB565toARGB888(smallBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bitmap bitmap = Bitmap.createBitmap(
                smallBitmap.getWidth(), smallBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);

        RenderScript renderScript = RenderScript.create(context);

        Allocation blurInput = Allocation.createFromBitmap(renderScript, smallBitmap);
        Allocation blurOutput = Allocation.createFromBitmap(renderScript, bitmap);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript,
                Element.U8_4(renderScript));
        blur.setInput(blurInput);
        blur.setRadius(radius); // radius must be 0 < r <= 25
        blur.forEach(blurOutput);

        blurOutput.copyTo(bitmap);
        renderScript.destroy();

        return bitmap;

    }

    private static Bitmap RGB565toARGB888(Bitmap img) throws Exception {
        int numPixels = img.getWidth() * img.getHeight();
        int[] pixels = new int[numPixels];

        //Get JPEG pixels.  Each int is the color values for one pixel.
        img.getPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        //Create a Bitmap of the appropriate format.
        Bitmap result = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.ARGB_8888);

        //Set RGB pixels.
        result.setPixels(pixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());
        return result;
    }

    //Crear dialog Si / No
    public static AlertDialog.Builder crearDialogYesNot(Context context, String titulo, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(titulo);
        builder.setMessage(message);

        return builder;
    }

    public static Usuario currentUsuario(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(GlobalConstant.PREFS_NAME, Context.MODE_PRIVATE);

        Usuario usuario = new Usuario();

        usuario.setRut(prefs.getString(GlobalConstant.PREFS_RUT, ""));
        usuario.setNombre(prefs.getString(GlobalConstant.PREFS_NOMBRE, ""));
        usuario.setApellidoPaterno(prefs.getString(GlobalConstant.PREFS_APELLIDO_P, ""));
        usuario.setApellidoMaterno(prefs.getString(GlobalConstant.PREFS_APELLIDO_M, ""));
        usuario.setCorreo(prefs.getString(GlobalConstant.PREFS_CORREO, ""));
        usuario.setTelefono(prefs.getInt(GlobalConstant.PREFS_TELEFONO, 0));
        usuario.setContraseña(prefs.getString(GlobalConstant.PREFS_CLAVE, ""));

        return usuario;
    }

    public static boolean isGpsActive(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return false;
        }
        return true;
    }

    //Wed Oct 19 08:27:41 GMT-03:00 2016
    public static String formatDate(String prevDate) {
//        String outputPattern = "dd/MM/yyyy HH:mm";
        String outputPattern = "HH:mm EEEE', ' dd 'de' MMMM";
        String inputPattern = "EEE MMM dd hh:mm:ss z yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.US);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern, new Locale("es","CL"));

//        inputFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        Date date;
        String str = "";

        try {

            date = inputFormat.parse(prevDate);
            str = outputFormat.format(date);

        } catch (ParseException e) {
            Log.d("ERROR_FORMAT", e.toString());
            e.printStackTrace();
        }

        return str;

    }

    //CALCULO LA CANTIDAD DE HORAS ENTRE 2 FECHAS
    public static int hourBetweenDates(String date1, String date2) {
//        String format = "dd/MM/yyyy HH:mm";
        String format = "HH:mm EEEE', ' dd 'de' MMMM";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format,new Locale("es","CL"));
        Date fechaLlegada = null;
        Date fechaSalida = null;

        try {
            fechaLlegada = dateFormat.parse(date1);
            fechaSalida = dateFormat.parse(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Hours hours = null;

        if (fechaLlegada != null && fechaSalida != null) {
            Calendar calendarLlegada = Calendar.getInstance();
            calendarLlegada.setTime(fechaLlegada);
            DateTime dateTimeLlegada = new DateTime(calendarLlegada.get(Calendar.YEAR),
                    calendarLlegada.get(Calendar.MONTH) + 1,
                    calendarLlegada.get(Calendar.DAY_OF_MONTH),
                    calendarLlegada.get(Calendar.HOUR_OF_DAY),
                    calendarLlegada.get(Calendar.MINUTE)
            );


            Calendar calendarSalida = Calendar.getInstance();
            calendarSalida.setTime(fechaSalida);
            DateTime dateTimeSalida = new DateTime(calendarSalida.get(Calendar.YEAR),
                    calendarSalida.get(Calendar.MONTH) + 1,
                    calendarSalida.get(Calendar.DAY_OF_MONTH),
                    calendarSalida.get(Calendar.HOUR_OF_DAY),
                    calendarSalida.get(Calendar.MINUTE)
            );

            hours = Hours.hoursBetween(dateTimeLlegada, dateTimeSalida);

        }


        return hours.getHours();
    }

    //CREAR JSON A PARTIR DE UN OBJETO
    public static String createJSONObject(Object object) {
        Gson gson = new Gson();

        String json = gson.toJson(object);

        return json;
    }

    public static void cargarComunas(final Context context) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                List<Comuna> comunas = realm.where(Comuna.class).findAll();
                if (comunas.size() == 0) {
                    realm.createOrUpdateAllFromJson(Comuna.class, loadJSONFromAsset("jsonComunas.json", context));
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.d("cargacomunas", "CARGARON BIEN LAS COMUNAS");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.d("cargacomunas", "FALLO LA CARGA");
            }
        });
    }

    public static void cargarMarcasVehiculo(Context context) {
        Call<ResponseBody> retroCall = ApiAdapter.getApiService().getMarcasVehiculo();

        retroCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                Realm realm = Realm.getDefaultInstance();
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            JSONArray jsonArray = jsonObject.getJSONArray("listaMarcaVehiculo");
                            realm.createOrUpdateAllFromJson(Marca.class, jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException io) {
                            io.printStackTrace();
                        }
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Log.d("cargaMarcas", "CARGARON BIEN LAS MARCAS");
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        error.printStackTrace();
                    }
                });

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public static void cargarModelosVehiculo(Context context) {
        Call<ResponseBody> retroCall = ApiAdapter.getApiService().getModeloVehiculo();

        retroCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                Realm realm = Realm.getDefaultInstance();
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            JSONArray jsonArray = jsonObject.getJSONArray("listaModelos");
                            realm.createOrUpdateAllFromJson(Modelo.class, jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Log.d("cargaModelos", "CARGARON BIEN LOS MODELOS");
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        error.printStackTrace();
                    }
                });

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    //CARGAR JSON DSDE LOS ASSETS
    public static String loadJSONFromAsset(String jsonFileName, Context context) {
        String json = null;
        try {

            InputStream is = context.getAssets().open(jsonFileName);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    public static LatLng getCoordinatesFromAddress(Context context, String addres) {
        LatLng coordenadas;
        Geocoder geocoder = new Geocoder(context, new Locale("es", "CL"));

        try {
            List<Address> coor = geocoder.getFromLocationName(addres, 1);
            if (coor == null) {
                return null;
            }
            coordenadas = new LatLng(coor.get(0).getLatitude(), coor.get(0).getLongitude());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        return coordenadas;
    }


    //LLAMADA AL SERVICIODEL LOGIN
    public static void loginConnect(final Context context, String email, String pass, final RunnableArgs block) {

        Usuario usuario = new Usuario(email, pass);

        //LLAMADA AL WEB SERVICE
        Call<ResponseLogin> retroCall = ApiAdapter.getApiService().login(usuario);

        retroCall.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.body().getMensaje().equals("true")) {

                    Usuario usuario = response.body().getUsuario();
                    List<Estacionamiento> estacionamientos = response.body().getEstacionamientos();
                    List<Vehiculo> vehiculos = response.body().getVehiculos();
                    List<Tarjeta> tarjetas = response.body().getTarjetas();

                    Gson gson = new Gson();
                    String jTarjeta = gson.toJson(response.body().getTarjetas());

                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    realm.createOrUpdateAllFromJson(Tarjeta.class, jTarjeta);
                    realm.commitTransaction();


                    saveInfo(context, usuario, estacionamientos, vehiculos, tarjetas);
                    if (block != null) {
                        block.setResponse(GlobalConstant.RESPONSE_LOGIN_CORRECT);
                        block.run();
                    }

                } else {
                    if (block != null) {
                        block.setResponse(GlobalConstant.RESPONSE_LOGIN_INCORRECT);
                        block.run();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                if (block != null) {
                    block.setResponse(GlobalConstant.RESPONSE_CONNECTION_ERROR);
                    block.run();
                }
            }
        });

    }

    //LLAMADA AL SERVICIO GET ESTACIONAMIENTOS
    public static void getEstacionamientos(final Context context, final RunnableArgs block) {
        Call<List<ResponseAllEstacionamientos>> retroCall = ApiAdapter.getApiService().getEstacionamientos();

        retroCall.enqueue(new Callback<List<ResponseAllEstacionamientos>>() {
            @Override
            public void onResponse(Call<List<ResponseAllEstacionamientos>> call, final Response<List<ResponseAllEstacionamientos>> response) {
//                Log.d("RESPONSE",response.body().getUsuario().getRut());
                convertToJsonGetEstacionamientos(context, response.body());


                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        Gson gson = new Gson();
                        for (int i = 0; i < response.body().size(); i++) {

                            try {
                                String json = gson.toJson(response.body().get(i).getUsuario());
                                JSONObject jsonObject = new JSONObject(json);
                                realm.createOrUpdateObjectFromJson(Usuario.class, jsonObject);

                                String jsonEst = gson.toJson(response.body().get(i).getEstacionamientos());
                                realm.createOrUpdateAllFromJson(Estacionamiento.class, jsonEst);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }


                    }
                });

                if (block != null) {
                    block.setResponse(GlobalConstant.RESPONSE_LOGIN_CORRECT);
                    block.run();
                }
            }

            @Override
            public void onFailure(Call<List<ResponseAllEstacionamientos>> call, Throwable t) {
                if (block != null) {
                    block.setResponse(GlobalConstant.RESPONSE_CONNECTION_ERROR);
                    block.run();
                }
            }
        });

    }

    //GUARDA LA INFO DEL USUARIO, Y LA DE ESTACIONAMIENTOS,VEHICULOS, TARJETAS EN FORMATO JSON
    private static void saveInfo(Context context, Usuario usuario, List<Estacionamiento> estacionamientos,
                                 List<Vehiculo> vehiculos, List<Tarjeta> tarjetas) {
        SharedPreferences prefs = context.getSharedPreferences(GlobalConstant.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        //USUARIO
        editor.putString(GlobalConstant.PREFS_RUT, usuario.getRut());

        //AUTO LOGIN
        editor.putBoolean(GlobalConstant.PREFS_AUTOLOGIN, true);

        Gson gson = new Gson();

        //JSON VEHICULOS
        if (vehiculos != null) {
            String jsonVeh = gson.toJson(vehiculos);
            editor.putString(GlobalConstant.PREFS_JSON_VEHICULOS, jsonVeh);
        }
        editor.apply();
    }

    private static void convertToJsonGetEstacionamientos(Context context, List<ResponseAllEstacionamientos> datos) {
        Gson gson = new Gson();
        SharedPreferences prefs = context.getSharedPreferences(GlobalConstant.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        String json = gson.toJson(datos);

        editor.putString(GlobalConstant.PREFS_JSON_GET_EST, json);
        editor.commit();
    }


    //CALCULA EL TAMAÑO DE UN JSONARRAY
    public static int calcularSizeArray(String array) {
        int cantidad = 0;
        try {
            JSONArray jsonArray = new JSONArray(array);
            cantidad = jsonArray.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cantidad;
    }

    public static int getComunaIDbyNombre(String nombre) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        Comuna comuna = realm.where(Comuna.class).equalTo("nombreComuna", nombre).findFirst();
        realm.commitTransaction();
        return comuna.getIdComuna();
    }

    public static String getComunaNombrebyID(int id) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        Comuna comuna = realm.where(Comuna.class).equalTo("idComuna", id).findFirst();
        realm.commitTransaction();
        return comuna.getNombreComuna();
    }

}
