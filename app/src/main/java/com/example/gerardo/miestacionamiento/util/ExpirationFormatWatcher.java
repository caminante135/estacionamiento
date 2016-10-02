package com.example.gerardo.miestacionamiento.util;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

/**
 * Created by Gerardo on 02/10/2016.
 */
public class ExpirationFormatWatcher implements TextWatcher {

    private static final String forwardSlash = " / ";


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 2) {
            s.append(forwardSlash);
//            s.insert(s.length(), forwardSlash);

        }
    }
}