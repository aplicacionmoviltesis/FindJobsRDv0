package com.example.findjobsrdv0.Administradores;

public class Universidades {

    public Universidades() {
    }

    private String sIdUniversidad;
    private String sNombreUniversidad;
    private String sUbicacionUniversidad;
    private String sImagenUniversidad;

    public Universidades(String sIdUniversidad, String sNombreUniversidad, String sUbicacionUniversidad, String sImagenUniversidad) {
        this.sIdUniversidad = sIdUniversidad;
        this.sNombreUniversidad = sNombreUniversidad;
        this.sUbicacionUniversidad = sUbicacionUniversidad;
        this.sImagenUniversidad = sImagenUniversidad;
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
}
