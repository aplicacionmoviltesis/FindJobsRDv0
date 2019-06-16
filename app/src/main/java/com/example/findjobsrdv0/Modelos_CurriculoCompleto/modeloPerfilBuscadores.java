package com.example.findjobsrdv0.Modelos_CurriculoCompleto;

import android.net.Uri;

public class modeloPerfilBuscadores {
    String imagenperfilB, nombreperfilB, apellidoperfilB, correoelectronicoperfilB, telefonoperfilB;

    public modeloPerfilBuscadores() {
    }

    public modeloPerfilBuscadores(String imagenperfilB, String nombreperfilB, String apellidoperfilB, String correoelectronicoperfilB, String telefonoperfilB) {
        this.imagenperfilB = imagenperfilB;
        this.nombreperfilB = nombreperfilB;
        this.apellidoperfilB = apellidoperfilB;
        this.correoelectronicoperfilB = correoelectronicoperfilB;
        this.telefonoperfilB = telefonoperfilB;
    }

    public modeloPerfilBuscadores(Uri downloadUri, String nombreperfilB, String apellidoperfilB, String correoelectronicoperfilB, String telefonoperfilB) {
    }

    public String getImagenperfilB() {
        return imagenperfilB;
    }

    public void setImagenperfilB(String imagenperfilB) {
        this.imagenperfilB = imagenperfilB;
    }

    public String getNombreperfilB() {
        return nombreperfilB;
    }

    public void setNombreperfilB(String nombreperfilB) {
        this.nombreperfilB = nombreperfilB;
    }

    public String getApellidoperfilB() {
        return apellidoperfilB;
    }

    public void setApellidoperfilB(String apellidoperfilB) {
        this.apellidoperfilB = apellidoperfilB;
    }

    public String getCorreoelectronicoperfilB() {
        return correoelectronicoperfilB;
    }

    public void setCorreoelectronicoperfilB(String correoelectronicoperfilB) {
        this.correoelectronicoperfilB = correoelectronicoperfilB;
    }

    public String getTelefonoperfilB() {
        return telefonoperfilB;
    }

    public void setTelefonoperfilB(String telefonoperfilB) {
        this.telefonoperfilB = telefonoperfilB;
    }
}
