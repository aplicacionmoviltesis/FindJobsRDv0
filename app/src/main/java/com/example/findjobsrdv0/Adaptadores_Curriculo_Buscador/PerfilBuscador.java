package com.example.findjobsrdv0.Adaptadores_Curriculo_Buscador;

public class PerfilBuscador {
    String sImagenPerfilB, sIdBuscador, sNombrePerfilB,
            sApellidoperfilB, sEmailPerfilB, sTelefonoPerfilB;

    public PerfilBuscador() {
    }

    public PerfilBuscador(String sImagenPerfilB, String sIdBuscador, String sNombrePerfilB, String sApellidoperfilB, String sEmailPerfilB, String sTelefonoPerfilB) {
        this.sImagenPerfilB = sImagenPerfilB;
        this.sIdBuscador = sIdBuscador;
        this.sNombrePerfilB = sNombrePerfilB;
        this.sApellidoperfilB = sApellidoperfilB;
        this.sEmailPerfilB = sEmailPerfilB;
        this.sTelefonoPerfilB = sTelefonoPerfilB;
    }

    public String getsImagenPerfilB() {
        return sImagenPerfilB;
    }

    public void setsImagenPerfilB(String sImagenPerfilB) {
        this.sImagenPerfilB = sImagenPerfilB;
    }

    public String getsIdBuscador() {
        return sIdBuscador;
    }

    public void setsIdBuscador(String sIdBuscador) {
        this.sIdBuscador = sIdBuscador;
    }

    public String getsNombrePerfilB() {
        return sNombrePerfilB;
    }

    public void setsNombrePerfilB(String sNombrePerfilB) {
        this.sNombrePerfilB = sNombrePerfilB;
    }

    public String getsApellidoperfilB() {
        return sApellidoperfilB;
    }

    public void setsApellidoperfilB(String sApellidoperfilB) {
        this.sApellidoperfilB = sApellidoperfilB;
    }

    public String getsEmailPerfilB() {
        return sEmailPerfilB;
    }

    public void setsEmailPerfilB(String sEmailPerfilB) {
        this.sEmailPerfilB = sEmailPerfilB;
    }

    public String getsTelefonoPerfilB() {
        return sTelefonoPerfilB;
    }

    public void setsTelefonoPerfilB(String sTelefonoPerfilB) {
        this.sTelefonoPerfilB = sTelefonoPerfilB;
    }
}
