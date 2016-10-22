package com.example.gerardo.miestacionamiento;

import com.example.gerardo.miestacionamiento.util.GlobalFunction;

import junit.framework.TestCase;

/**
 * Created by Gerardo on 20/10/2016.
 */
public class FuncionesTest extends TestCase {

    //VALIDA LA FUNCION QUE COMPRUEBA EL FORMATO DEL EMAIL
    public void testFuncionValidarEmail(){
        String email = "gerardo@.cl";

        boolean resultado = GlobalFunction.isValidEmail(email);

        assertFalse("Resultado Email",resultado);

    }

    //VALIDA LA FUNCION QUE COMPRUEBA EL FORMATO DEL RUT
    public void testFuncionValidarRUT(){
        String rut = "189537883";

        boolean resultado = GlobalFunction.isRut(rut);

        //SE ESPERA UN TRUE
        assertEquals("Resultado RUT",true,resultado);

    }




}
