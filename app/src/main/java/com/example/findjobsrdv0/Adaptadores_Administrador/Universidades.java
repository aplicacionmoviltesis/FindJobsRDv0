package com.example.findjobsrdv0.Adaptadores_Administrador;

public class Universidades {

    public Universidades() {
    }

    private String sIdUniversidad;
    private String sNombreUniversidad;
    private String sUbicacionUniversidad;
    private String sImagenUniversidad;
    private String sIdUserAdminUniversidad;
    private String sTelefonoUniversidad;
    private String sPaginaWebUniversidad;
    private String sDireccionUniversidad;

    public Universidades(String sIdUniversidad, String sNombreUniversidad, String sUbicacionUniversidad, String sImagenUniversidad, String sDireccionUniversidad, String sTelefonoUniversidad, String sPaginaWebUniversidad, String sIdUserAdminUniversidad) {
        this.sIdUniversidad = sIdUniversidad;
        this.sNombreUniversidad = sNombreUniversidad;
        this.sUbicacionUniversidad = sUbicacionUniversidad;
        this.sImagenUniversidad = sImagenUniversidad;
        this.sDireccionUniversidad = sDireccionUniversidad;
        this.sTelefonoUniversidad = sTelefonoUniversidad;
        this.sPaginaWebUniversidad = sPaginaWebUniversidad;
        this.sIdUserAdminUniversidad = sIdUserAdminUniversidad;

    }

    public String getsIdUniversidad() {
        return sIdUniversidad;
    }

    public void setsIdUniversidad(String sIdUniversidad) {
        this.sIdUniversidad = sIdUniversidad;
    }

    public String getsNombreUniversidad() {
        return sNombreUniversidad;
    }

    public void setsNombreUniversidad(String sNombreUniversidad) {
        this.sNombreUniversidad = sNombreUniversidad;
    }

    public String getsUbicacionUniversidad() {
        return sUbicacionUniversidad;
    }

    public void setsUbicacionUniversidad(String sUbicacionUniversidad) {
        this.sUbicacionUniversidad = sUbicacionUniversidad;
    }

    public String getsImagenUniversidad() {
        return sImagenUniversidad;
    }

    public void setsImagenUniversidad(String sImagenUniversidad) {
        this.sImagenUniversidad = sImagenUniversidad;
    }

    public String getsTelefonoUniversidad() {
        return sTelefonoUniversidad;
    }

    public void setsTelefonoUniversidad(String sTelefonoUniversidad) {
        this.sTelefonoUniversidad = sTelefonoUniversidad;
    }

    public String getsPaginaWebUniversidad() {
        return sPaginaWebUniversidad;
    }

    public void setsPaginaWebUniversidad(String sPaginaWebUniversidad) {
        this.sPaginaWebUniversidad = sPaginaWebUniversidad;
    }

    public String getsDireccionUniversidad() {
        return sDireccionUniversidad;
    }

    public void setsDireccionUniversidad(String sDireccionUniversidad) {
        this.sDireccionUniversidad = sDireccionUniversidad;
    }

    public String getsIdUserAdminUniversidad() {
        return sIdUserAdminUniversidad;
    }

    public void setsIdUserAdminUniversidad(String sIdUserAdminUniversidad) {
        this.sIdUserAdminUniversidad = sIdUserAdminUniversidad;
    }
}
