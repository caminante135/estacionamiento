package com.example.gerardo.miestacionamiento.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.gerardo.miestacionamiento.model.Usuario;
import com.google.gson.Gson;

import org.joda.time.DateTime;
import org.joda.time.Hours;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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

    public static boolean isEmpty(String value){
        if (value.equals("") || value == null || value.length()==0){
            return true;
        }else{
            return false;
        }
    }

    //VALIDADOR DE RUT CHILENO
    public static boolean isRut(String rut) {
        boolean validacion = false;
        try {
            rut =  rut.toUpperCase();
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
    public static Bitmap blurRenderScript(Context context,Bitmap smallBitmap, int radius) {
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
    public static AlertDialog.Builder crearDialogYesNot(Context context,String titulo, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(titulo);
        builder.setMessage(message);

        return builder;
    }

    public static Usuario currentUsuario(Context context){
        SharedPreferences prefs = context.getSharedPreferences(GlobalConstant.PREFS_NAME,Context.MODE_PRIVATE);

        Usuario usuario = new Usuario();

        usuario.setRut(prefs.getString(GlobalConstant.PREFS_RUT,""));
        usuario.setNombre(prefs.getString(GlobalConstant.PREFS_NOMBRE,""));
        usuario.setApellidoPaterno(prefs.getString(GlobalConstant.PREFS_APELLIDO_P,""));
        usuario.setApellidoMaterno(prefs.getString(GlobalConstant.PREFS_APELLIDO_M,""));
        usuario.setCorreo(prefs.getString(GlobalConstant.PREFS_CORREO,""));
        usuario.setTelefono(prefs.getInt(GlobalConstant.PREFS_TELEFONO,0));
        usuario.setContraseña(prefs.getString(GlobalConstant.PREFS_CLAVE,""));

        return usuario;
    }

    //Wed Oct 19 08:27:41 GMT-03:00 2016
    public static String formatDate(String prevDate){
        String outputPattern = "dd/MM/yyyy HH:mm";
        String inputPattern = "EEE MMM dd hh:mm:ss z yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.US);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

//        inputFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        Date date;
        String str = "";

        try {

            date = inputFormat.parse(prevDate);
            str = outputFormat.format(date);

        } catch (ParseException e) {
            Log.d("ERROR_FORMAT",e.toString());
            e.printStackTrace();
        }

        return str;

    }

    //CALCULO LA CANTIDAD DE HORAS ENTRE 2 FECHAS
    public static int hourBetweenDates(String date1, String date2){
        String format = "dd/MM/yyyy HH:mm";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date fechaLlegada = null;
        Date fechaSalida = null;

        try {
            fechaLlegada = dateFormat.parse(date1);
            fechaSalida = dateFormat.parse(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Hours hours = null;

        if (fechaLlegada != null && fechaSalida != null){
            Calendar calendarLlegada = Calendar.getInstance();
            calendarLlegada.setTime(fechaLlegada);
            DateTime dateTimeLlegada = new DateTime(calendarLlegada.get(Calendar.YEAR),
                    calendarLlegada.get(Calendar.MONTH)+1,
                    calendarLlegada.get(Calendar.DAY_OF_MONTH),
                    calendarLlegada.get(Calendar.HOUR_OF_DAY),
                    calendarLlegada.get(Calendar.MINUTE)
            );


            Calendar calendarSalida = Calendar.getInstance();
            calendarSalida.setTime(fechaSalida);
            DateTime dateTimeSalida = new DateTime(calendarSalida.get(Calendar.YEAR),
                    calendarSalida.get(Calendar.MONTH)+1,
                    calendarSalida.get(Calendar.DAY_OF_MONTH),
                    calendarSalida.get(Calendar.HOUR_OF_DAY),
                    calendarSalida.get(Calendar.MINUTE)
            );

            hours = Hours.hoursBetween(dateTimeLlegada,dateTimeSalida);

        }



        return hours.getHours();
    }

    //CREAR JSON A PARTIR DE UN OBJETO
    public static String createJSONObject(Object object){
        Gson gson = new Gson();

        String json = gson.toJson(object);

        return json;
    }


}
