package com.example.findjobsrdv0.Adaptadores_Curriculo_Buscador;

public class Referencias {

    private String sIdReferencia, sIdCurriculorRef, sNombrePersonaRef, sCargoOcupadoRef, sInstitucionRef, sTelefonoRef;

    public Referencias() {
    }

    public Referencias(String sIdReferencia, String sIdCurriculorRef, String sNombrePersonaRef, String sCargoOcupadoRef, String sInstitucionRef, String sTelefonoRef) {
        this.sIdReferencia = sIdReferencia;
        this.sIdCurriculorRef = sIdCurriculorRef;
        this.sNombrePersonaRef = sNombrePersonaRef;
        this.sCargoOcupadoRef = sCargoOcupadoRef;
        this.sInstitucionRef = sInstitucionRef;
        this.sTelefonoRef = sTelefonoRef;
    }

    public String getsIdReferencia() {
        return sIdReferencia;
    }

    public void setsIdReferencia(String sIdReferencia) {
        this.sIdReferencia = sIdReferencia;
    }

    public String getsIdCurriculorRef() {
        return sIdCurriculorRef;
    }

    public void setsIdCurriculorRef(String sIdCurriculorRef) {
        this.sIdCurriculorRef = sIdCurriculorRef;
    }

    public String getsNombrePersonaRef() {
        return sNombrePersonaRef;
    }

    public void setsNombrePersonaRef(String sNombrePersonaRef) {
        this.sNombrePersonaRef = sNombrePersonaRef;
    }

    public String getsCargoOcupadoRef() {
        return sCargoOcupadoRef;
    }

    public void setsCargoOcupadoRef(String sCargoOcupadoRef) {
        this.sCargoOcupadoRef = sCargoOcupadoRef;
    }

    public String getsInstitucionRef() {
        return sInstitucionRef;
    }

    public void setsInstitucionRef(String sInstitucionRef) {
        this.sInstitucionRef = sInstitucionRef;
    }

    public String getsTelefonoRef() {
        return sTelefonoRef;
    }

    public void setsTelefonoRef(String sTelefonoRef) {
        this.sTelefonoRef = sTelefonoRef;
    }
}