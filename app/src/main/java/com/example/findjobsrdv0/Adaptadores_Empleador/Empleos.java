package com.example.findjobsrdv0.Adaptadores_Empleador;

import java.util.HashMap;
import java.util.Map;

public class Empleos {

    public Empleos() {
    }


    String sIDEmpleo,sNombreEmpleoE, sNombreEmpresaE,sProvinciaE,sDireccionE, sTelefonoE,sPaginaWebE,sEmailE,sSalarioE,sOtrosDatosE,
            sHorarioE,sFechaPublicacionE, sMostrarIdiomaE,sAreaE, sFormacionAcademicaE, sAnosExperienciaE,sSexoRequeridoE,sRangoE,sJornadaE,sCantidadVacantesE,
            sTipoContratoE,sEstadoEmpleoE, sEstadoAdminE,sImagenEmpleoE, sIdEmpleadorE;

    public Empleos(String sIDEmpleo, String sNombreEmpleoE, String sNombreEmpresaE, String sDireccionE, String sProvinciaE,
                   String sTelefonoE, String sPaginaWebE, String sEmailE, String sSalarioE, String sOtrosDatosE,
                   String sHorarioE, String sFechaPublicacionE, String sMostrarIdiomaE, String sAreaE, String sAnosExperienciaE, String sFormacionAcademicaE,
                   String sSexoRequeridoE, String sRangoE, String sJornadaE, String sCantidadVacantesE,
                   String sTipoContratoE, String sEstadoEmpleoE, String sEstadoAdminE, String sImagenEmpleoE, String sIdEmpleadorE) {

        this.sIDEmpleo = sIDEmpleo;
        this.sNombreEmpleoE = sNombreEmpleoE;
        this.sNombreEmpresaE = sNombreEmpresaE;
        this.sProvinciaE = sProvinciaE;
        this.sDireccionE = sDireccionE;
        this.sTelefonoE = sTelefonoE;
        this.sPaginaWebE = sPaginaWebE;
        this.sEmailE = sEmailE;
        this.sSalarioE = sSalarioE;
        this.sOtrosDatosE = sOtrosDatosE;
        this.sHorarioE = sHorarioE;
        this.sFechaPublicacionE = sFechaPublicacionE;
        this.sMostrarIdiomaE = sMostrarIdiomaE;
        this.sAreaE = sAreaE;
        this.sFormacionAcademicaE = sFormacionAcademicaE;
        this.sAnosExperienciaE = sAnosExperienciaE;
        this.sSexoRequeridoE = sSexoRequeridoE;
        this.sRangoE = sRangoE;
        this.sJornadaE = sJornadaE;
        this.sCantidadVacantesE = sCantidadVacantesE;
        this.sTipoContratoE = sTipoContratoE;
        this.sEstadoEmpleoE = sEstadoEmpleoE;
        this.sEstadoAdminE = sEstadoAdminE;
        this.sImagenEmpleoE = sImagenEmpleoE;
        this.sIdEmpleadorE = sIdEmpleadorE;

    }

    public String getsIdEmpleadorE() {
        return sIdEmpleadorE;
    }

    public String getsIDEmpleo() {
        return sIDEmpleo;
    }

    public String getsNombreEmpleoE() {
        return sNombreEmpleoE;
    }

    public String getsNombreEmpresaE() {
        return sNombreEmpresaE;
    }

    public String getsProvinciaE() {
        return sProvinciaE;
    }
    public String getsDireccionE() {
        return sDireccionE;
    }

    public String getsTelefonoE() {
        return sTelefonoE;
    }

    public String getsPaginaWebE() {
        return sPaginaWebE;
    }

    public String getsEmailE() {
        return sEmailE;
    }

    public String getsSalarioE() {
        return sSalarioE;
    }

    public String getsOtrosDatosE() {
        return sOtrosDatosE;
    }

    public String getsHorarioE() {
        return sHorarioE;
    }

    public String getsFechaPublicacionE() {
        return sFechaPublicacionE;
    }

    public String getsMostrarIdiomaE() {
        return sMostrarIdiomaE;
    }

    public String getsAreaE() {
        return sAreaE;
    }

    public String getsSexoRequeridoE() {
        return sSexoRequeridoE;
    }

    public String getsRangoE() {
        return sRangoE;
    }

    public String getsJornadaE() {
        return sJornadaE;
    }

    public String getsCantidadVacantesE() {
        return sCantidadVacantesE;
    }

    public String getsTipoContratoE() {
        return sTipoContratoE;
    }

    public String getsEstadoEmpleoE() {
        return sEstadoEmpleoE;
    }

    public String getsEstadoAdminE() {
        return sEstadoAdminE;
    }
    public String getsImagenEmpleoE() {
        return sImagenEmpleoE;
}


    public String getsFormacionAcademicaE() {
        return sFormacionAcademicaE;
    }
    public String getsAnosExperienciaE() {
        return sAnosExperienciaE;
    }
}
