package com.example.findjobsrdv0.GeneralesApp;

public class Areas {

    public Areas() {
    }

    String sIdArea;
    String sNombreArea;
    String sDescripcionArea;
    String sImagenArea;
    String sSubAreas;

    public Areas(String sIdArea, String sNombreArea, String sDescripcionArea, String sImagenArea, String sSubAreas) {
        this.sIdArea = sIdArea;
        this.sNombreArea = sNombreArea;
        this.sDescripcionArea = sDescripcionArea;
        this.sImagenArea = sImagenArea;
        this.sSubAreas = sSubAreas;
    }

    public String getsIdArea() {
        return sIdArea;
    }

    public void setsIdArea(String sIdArea) {
        this.sIdArea = sIdArea;
    }

    public String getsNombreArea() {
        return sNombreArea;
    }

    public void setsNombreArea(String sNombreArea) {
        this.sNombreArea = sNombreArea;
    }

    public String getsDescripcionArea() {
        return sDescripcionArea;
    }

    public void setsDescripcionArea(String sDescripcionArea) {
        this.sDescripcionArea = sDescripcionArea;
    }

    public String getsImagenArea() {
        return sImagenArea;
    }

    public void setsImagenArea(String sImagenArea) {
        this.sImagenArea = sImagenArea;
    }

    public String getsSubAreas() {
        return sSubAreas;
    }

    public void setsSubAreas(String sSubAreas) {
        this.sSubAreas = sSubAreas;
    }


}
