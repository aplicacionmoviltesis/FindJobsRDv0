package com.example.findjobsrdv0;

public class Empleadores {

    String sNombreEmpleador;
    String sRncEmpleador;
    String sPaginaWebEmpleador;
    String sTelefonoEmpleador;
    String sDireccionEmpleador;
    String sCorreoEmpleador;
    //String sIdEmpleador;
    String sImagenEmpleador;

    public Empleadores() {
    }

    public Empleadores(String sNombreEmpleador, String sRncEmpleador, String sPaginaWebEmpleador, String sTelefonoEmpleador, String sDireccionEmpleador, String sCorreoEmpleador, String sImagenEmpleador) {
        this.sNombreEmpleador = sNombreEmpleador;
        this.sRncEmpleador = sRncEmpleador;
        this.sPaginaWebEmpleador = sPaginaWebEmpleador;
        this.sTelefonoEmpleador = sTelefonoEmpleador;
        this.sDireccionEmpleador = sDireccionEmpleador;
        this.sCorreoEmpleador = sCorreoEmpleador;
        //this.sIdEmpleador = sIdEmpleador;
        this.sImagenEmpleador = sImagenEmpleador;
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
/*
    public String getsIdEmpleador() {
        return sIdEmpleador;
    }

    public void setsIdEmpleador(String sIdEmpleador) {
        this.sIdEmpleador = sIdEmpleador;
    }*/

    public String getsImagenEmpleador() {
        return sImagenEmpleador;
    }

    public void setsImagenEmpleador(String sImagenEmpleador) {
        this.sImagenEmpleador = sImagenEmpleador;
    }




}