package com.example.findjobsrdv0.Adaptadores_Administrador;

public class Areas {

    public Areas() {
    }

    private String sIdArea;
    private String sNombreArea;
    private String sDescripcionArea;
    private String sImagenArea;
    private String sSubAreas;
    private String sIdUserAdminArea;

    public Areas(String sIdArea, String sNombreArea, String sDescripcionArea, String sImagenArea, String sSubAreas, String sIdUserAdminArea) {
        this.sIdArea = sIdArea;
        this.sNombreArea = sNombreArea;
        this.sDescripcionArea = sDescripcionArea;
        this.sImagenArea = sImagenArea;
        this.sSubAreas = sSubAreas;
        this.sIdUserAdminArea = sIdUserAdminArea;
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

    public String getsIdUserAdminArea() {
        return sIdUserAdminArea;
    }

    public void setsIdUserAdminArea(String sIdUserAdminArea) {
        this.sIdUserAdminArea = sIdUserAdminArea;
    }
}
