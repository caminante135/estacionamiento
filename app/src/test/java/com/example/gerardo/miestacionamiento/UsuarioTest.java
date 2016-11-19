package com.example.gerardo.miestacionamiento;

import android.content.Context;
import android.test.mock.MockContext;

import com.example.gerardo.miestacionamiento.model.Usuario;
import com.example.gerardo.miestacionamiento.controller.GlobalFunction;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Created by Gerardo on 21/10/2016.
 */
public class UsuarioTest  {

    //VALIDO QUE EXISTA EL CONTEXTO
    @Test
    public void testContexto(){
        Context context = new MockContext();
        assertNotNull(context);

    }

    //VALIDA QUE ME DEVUELVA CORRECTAMENTE LOS DATOS DEL ULTIMO USUARIO QUE INGRESO A LA APP
    @Test
    public void testUltimoUsuario(){
        Usuario user = GlobalFunction.currentUsuario(new MockContext());
        assertNotNull(user);

    }

}
