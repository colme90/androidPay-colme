package com.google.android.gms.samples.inte;

/**
 * Created by colme on 23/04/2017.
 */
public class Resources {

    public static synchronized String getRespuestaMensys() {
        return respuestaMensys;
    }

    public static void setRespuestaMensys(String respuestaMensys) {
        Resources.respuestaMensys = respuestaMensys;
    }

    public static String respuestaMensys = null;

    public static String getRespuestaPaste() {
        return respuestaPaste;
    }

    public static void setRespuestaPaste(String respuestaPaste) {
        Resources.respuestaPaste = respuestaPaste;
    }

    public static String respuestaPaste = "";



}
