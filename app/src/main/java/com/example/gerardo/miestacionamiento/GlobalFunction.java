package com.example.gerardo.miestacionamiento;

import android.content.res.Resources;

/**
 * Created by Gerardo on 01/10/2016.
 */
public final class GlobalFunction {



    //Funcion para convertir unidades DP a Pixeles (PX)
    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }


}
