package com.example.findjobsrdv0.Modelos_CurriculoCompleto;

public class PerfilBuscador {
    String sImagenPerfilB, sNombrePerfilB, sApellidoperfilB, sEmailPerfilB, sTelefonoPerfilB;

    public PerfilBuscador() {
    }

    public PerfilBuscador(String sImagenPerfilB, String sNombrePerfilB, String sApellidoperfilB, String correoelectronicoperfilB, String sTelefonoPerfilB) {
        this.sImagenPerfilB = sImagenPerfilB;
        this.sNombrePerfilB = sNombrePerfilB;
        this.sApellidoperfilB = sApellidoperfilB;
        this.sEmailPerfilB = correoelectronicoperfilB;
        this.sTelefonoPerfilB = sTelefonoPerfilB;
    }

    public String getImagenperfilB() {
        return sImagenPerfilB;
    }

    public void setImagenperfilB(String imagenperfilB) {
        this.sImagenPerfilB = imagenperfilB;
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

    public String getCorreoelectronicoperfilB() {
        return sEmailPerfilB;
    }

    public void setCorreoelectronicoperfilB(String correoelectronicoperfilB) {
        this.sEmailPerfilB = correoelectronicoperfilB;
    }

    public String getsTelefonoPerfilB() {
        return sTelefonoPerfilB;
    }

    public void setsTelefonoPerfilB(String sTelefonoPerfilB) {
        this.sTelefonoPerfilB = sTelefonoPerfilB;
    }
}
