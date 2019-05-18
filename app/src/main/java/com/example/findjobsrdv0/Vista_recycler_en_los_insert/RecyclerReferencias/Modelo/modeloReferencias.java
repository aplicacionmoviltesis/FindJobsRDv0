package com.example.findjobsrdv0.Vista_recycler_en_los_insert.RecyclerReferencias.Modelo;

public class modeloReferencias {
    String rCargoOcupadoC,  rInstitucionC, rNombreC, rTelefonoC;

    public modeloReferencias() {
    }

    public modeloReferencias(String rCargoOcupadoC, String rInstitucionC, String rNombreC, String rTelefonoC) {
        this.rCargoOcupadoC = rCargoOcupadoC;
        this.rInstitucionC = rInstitucionC;
        this.rNombreC = rNombreC;
        this.rTelefonoC = rTelefonoC;
    }

    public String getrCargoOcupadoC() {
        return rCargoOcupadoC;
    }

    public void setrCargoOcupadoC(String rCargoOcupadoC) {
        this.rCargoOcupadoC = rCargoOcupadoC;
    }

    public String getrInstitucionC() {
        return rInstitucionC;
    }

    public void setrInstitucionC(String rInstitucionC) {
        this.rInstitucionC = rInstitucionC;
    }

    public String getrNombreC() {
        return rNombreC;
    }

    public void setrNombreC(String rNombreC) {
        this.rNombreC = rNombreC;
    }

    public String getrTelefonoC() {
        return rTelefonoC;
    }

    public void setrTelefonoC(String rTelefonoC) {
        this.rTelefonoC = rTelefonoC;
    }
}
