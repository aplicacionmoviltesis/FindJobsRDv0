package com.example.findjobsrdv0.Administradores;

public class Administrador {



    public Administrador() {
    }

    private String sIdAdmin;
    private String sNombreAdmin;
    private String sApellidoAdmin;
    private String sEmailAdmin;
    private String sTelefonAdmin;

    public Administrador(String sIdAdmin, String sNombreAdmin, String sApellidoAdmin, String sEmailAdmin, String sTelefonAdmin) {
        this.sIdAdmin = sIdAdmin;
        this.sNombreAdmin = sNombreAdmin;
        this.sApellidoAdmin = sApellidoAdmin;
        this.sEmailAdmin = sEmailAdmin;
        this.sTelefonAdmin = sTelefonAdmin;
    }



    public String getsIdAdmin() {
        return sIdAdmin;
    }

    public void setsIdAdmin(String sIdAdmin) {
        this.sIdAdmin = sIdAdmin;
    }

    public String getsNombreAdmin() {
        return sNombreAdmin;
    }

    public void setsNombreAdmin(String sNombreAdmin) {
        this.sNombreAdmin = sNombreAdmin;
    }

    public String getsApellidoAdmin() {
        return sApellidoAdmin;
    }

    public void setsApellidoAdmin(String sApellidoAdmin) {
        this.sApellidoAdmin = sApellidoAdmin;
    }

    public String getsEmailAdmin() {
        return sEmailAdmin;
    }

    public void setsEmailAdmin(String sEmailAdmin) {
        this.sEmailAdmin = sEmailAdmin;
    }

    public String getsTelefonAdmin() {
        return sTelefonAdmin;
    }

    public void setsTelefonAdmin(String sTelefonAdmin) {
        this.sTelefonAdmin = sTelefonAdmin;
    }



}
