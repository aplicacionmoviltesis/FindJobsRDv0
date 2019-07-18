package com.example.findjobsrdv0.Clases_EmpleoCompleto;

import android.widget.ImageView;

public class Empleadores {

    String sNombreEmpleador;
    String sRncEmpleador;
    String sPaginaWebEmpleador;
    String sTelefonoEmpleador;
    String sDireccionEmpleador;
    String sCorreoEmpleador;
    String sDescripcionEmpleador;
    String sProvinciaEmpleador;
    String sIdEmpleador;
    String sImagenEmpleador;
    Boolean sVerificacionEmpleador;

    public Empleadores() {
    }



    public Empleadores(String sNombreEmpleador, String sRncEmpleador, String sPaginaWebEmpleador, String sTelefonoEmpleador, String sDireccionEmpleador, String sCorreoEmpleador, String sImagenEmpleador, boolean sVerificacionEmpleador, String sIdEmpleador, String sDescripcionEmpleador, String sProvinciaEmpleador) {
        this.sNombreEmpleador = sNombreEmpleador;
        this.sRncEmpleador = sRncEmpleador;
        this.sPaginaWebEmpleador = sPaginaWebEmpleador;
        this.sTelefonoEmpleador = sTelefonoEmpleador;
        this.sDireccionEmpleador = sDireccionEmpleador;
        this.sCorreoEmpleador = sCorreoEmpleador;
        this.sVerificacionEmpleador = sVerificacionEmpleador;
        this.sImagenEmpleador = sImagenEmpleador;
        this.sIdEmpleador = sIdEmpleador;
        this.sProvinciaEmpleador = sProvinciaEmpleador;
        this.sDescripcionEmpleador = sDescripcionEmpleador;

    }

    public String getsProvinciaEmpleador() {
        return sProvinciaEmpleador;
    }

    public void setsProvinciaEmpleador(String sProvinciaEmpleador) {
        this.sProvinciaEmpleador = sProvinciaEmpleador;
    }

    public String getsDescripcionEmpleador() {
        return sDescripcionEmpleador;
    }

    public void setsDescripcionEmpleador(String sDescripcionEmpleador) {
        this.sDescripcionEmpleador = sDescripcionEmpleador;
    }


    public String getsIdEmpleador() {
        return sIdEmpleador;
    }

    public void setsIdEmpleador(String sIdEmpleador) {
        this.sIdEmpleador = sIdEmpleador;
    }

    public String getsNombreEmpleador() {
        return sNombreEmpleador;
    }

    public void setsNombreEmpleador(String sNombreEmpleador) {
        this.sNombreEmpleador = sNombreEmpleador;
    }

    public String getsRncEmpleador() {
        return sRncEmpleador;
    }

    public void setsRncEmpleador(String sRncEmpleador) {
        this.sRncEmpleador = sRncEmpleador;
    }

    public String getsPaginaWebEmpleador() {
        return sPaginaWebEmpleador;
    }

    public void setsPaginaWebEmpleador(String sPaginaWebEmpleador) {
        this.sPaginaWebEmpleador = sPaginaWebEmpleador;
    }

    public String getsTelefonoEmpleador() {
        return sTelefonoEmpleador;
    }

    public void setsTelefonoEmpleador(String sTelefonoEmpleador) {
        this.sTelefonoEmpleador = sTelefonoEmpleador;
    }

    public String getsDireccionEmpleador() {
        return sDireccionEmpleador;
    }

    public void setsDireccionEmpleador(String sDireccionEmpleador) {
        this.sDireccionEmpleador = sDireccionEmpleador;
    }

    public String getsCorreoEmpleador() {
        return sCorreoEmpleador;
    }

    public void setsCorreoEmpleador(String sCorreoEmpleador) {
        this.sCorreoEmpleador = sCorreoEmpleador;
    }

    public boolean getsVerificacionEmpleador() {
        return sVerificacionEmpleador;
    }

    public boolean setsVerificacion(boolean sVerificacion) {
        this.sVerificacionEmpleador = sVerificacion;
        return sVerificacion;
    }

    public String getsImagenEmpleador() {
        return sImagenEmpleador;
    }

    public void setsImagenEmpleador(String sImagenEmpleador) {
        this.sImagenEmpleador = sImagenEmpleador;
    }




}
