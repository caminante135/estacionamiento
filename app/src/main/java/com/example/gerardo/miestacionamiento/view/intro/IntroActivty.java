package com.example.gerardo.miestacionamiento.view.intro;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.WindowManager;

import com.example.gerardo.miestacionamiento.R;
import com.example.gerardo.miestacionamiento.view.ui.LoginActivity;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

/**
 * Created by Gerardo on 13/11/2016.
 */
public class IntroActivty extends AppIntro {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        //PRIMER SLIDE
        addSlide(AppIntroFragment.newInstance("¿Buscas un Estacionamiento?",
                "Con Estacionate! podrás fácilmente buscar y arrendar el estacionamientos que necesitas",
                R.drawable.logoappsintitulo,
                ContextCompat.getColor(this,R.color.quintoSlideIntro),
                ContextCompat.getColor(this,R.color.introTitleColor),
                ContextCompat.getColor(this,R.color.white)));

        //SEGUNDO SLIDE
        addSlide(AppIntroFragment.newInstance("Busca un Estacionamiento",
                "En el mapa encontrarás todos los estacionamientos que estén registrados en la aplicación y mediante el icono sabrás si está o no disponible",
                R.drawable.imagen_buscar,
                ContextCompat.getColor(this,R.color.secondSlideIntro),
                ContextCompat.getColor(this,R.color.introTitleColor),
                ContextCompat.getColor(this,R.color.white)));

        //TERCER SLIDE
        addSlide(AppIntroFragment.newInstance("Ojo en los detalles",
                "Debes fijarte en los detalles del estacionamiento como el precio por hora, la disponibilidad y la ubicación de este",
                R.drawable.imagen_etalles,
                ContextCompat.getColor(this,R.color.tercerSlideIntro),
                ContextCompat.getColor(this,R.color.introTitleColor),
                ContextCompat.getColor(this,R.color.white)));

        //CUARTO SLIDE
        addSlide(AppIntroFragment.newInstance("Tiempo de Estancia",
                "Determina el tiempo en que harás uso del estacionamiento, puedes programarlo incluso con ¡dias de anticipación!",
                R.drawable.imagen_tiempo,
                ContextCompat.getColor(this,R.color.cuartoSlideIntro),
                ContextCompat.getColor(this,R.color.introTitleColor),
                ContextCompat.getColor(this,R.color.white)));

        //QUINTO SLIDE
        addSlide(AppIntroFragment.newInstance("Paga de la manera más fácil",
                "Paga acorde a la cantidad de horas que hayas seleccionado y de la manera más fácil solo indicando la tarjeta de crédito que deseas utilizar",
                R.drawable.imagen_pagar,
                ContextCompat.getColor(this,R.color.quintoSlideIntro),
                ContextCompat.getColor(this,R.color.introTitleColor),
                ContextCompat.getColor(this,R.color.white)));

        //CUARTO SLIDE
        addSlide(AppIntroFragment.newInstance("Ve y estaciónate!",
                "Ahora solo debes ir hacia el estacionamente y ocuparlo hasta la hora que hayas fijado, ¡Disfrútalo!",
                R.drawable.imagen_estacionamientos,
                ContextCompat.getColor(this,R.color.sextoSlideIntro),
                ContextCompat.getColor(this,R.color.introTitleColor),
                ContextCompat.getColor(this,R.color.white)));

        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);
        setDepthAnimation();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

}
