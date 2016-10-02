package com.example.gerardo.miestacionamiento.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Gerardo on 01/10/2016.
 */
public final class GlobalFunction {



    //Funcion para convertir unidades DP a Pixeles (PX)
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean isEmpty(String value){
        if (value.equals("") || value == null){
            return true;
        }else{
            return false;
        }
    }

}
