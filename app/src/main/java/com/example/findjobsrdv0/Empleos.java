package com.example.findjobsrdv0;

import java.util.HashMap;
import java.util.Map;

public class Empleos {

    public Empleos() {
    }


    String sIDEmpleo,sNombreEmpleoE, sNombreEmpresaE,sProvinciaE,sDireccionE, sTelefonoE,sPaginaWebE,sEmailE,sSalarioE,sOtrosDatosE,
            sHorarioE,sFechaPublicacionE, sMostrarIdioma,sAreaE,sFormacionAcademica,sAnosExperienciaDE,sSexoRequeridoE,sRangoE,sJornadaE,sCantidadVacantesE,
            sTipoContratoE,sEstadoEmpleoE,sPersonasAplicaronE,sImagenEmpleoE, sIdEmpleadorE;

    public Empleos(String sIDEmpleo, String sNombreEmpleoE, String sNombreEmpresaE, String sDireccionE,String sProvinciaE,
                   String sTelefonoE, String sPaginaWebE, String sEmailE, String sSalarioE, String sOtrosDatosE,
                   String sHorarioE, String sFechaPublicacionE, String sMostrarIdioma, String sAreaE,String sAnosExperienciaDE,String sFormacionAcademica,
                   String sSexoRequeridoE, String sRangoE, String sJornadaE, String sCantidadVacantesE,
                   String sTipoContratoE, String sEstadoEmpleoE, String sPersonasAplicaronE,String sImagenEmpleoE, String sIdEmpleadorE) {

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
        this.sMostrarIdioma = sMostrarIdioma;
        this.sAreaE = sAreaE;
        this.sFormacionAcademica = sFormacionAcademica;
        this.sAnosExperienciaDE = sAnosExperienciaDE;
        this.sSexoRequeridoE = sSexoRequeridoE;
        this.sRangoE = sRangoE;
        this.sJornadaE = sJornadaE;
        this.sCantidadVacantesE = sCantidadVacantesE;
        this.sTipoContratoE = sTipoContratoE;
        this.sEstadoEmpleoE = sEstadoEmpleoE;
        this.sPersonasAplicaronE = sPersonasAplicaronE;
        this.sImagenEmpleoE = sImagenEmpleoE;
        this.sIdEmpleadorE = sIdEmpleadorE;

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

    public String getsMostrarIdioma() {
        return sMostrarIdioma;
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

    public String getsPersonasAplicaronE() {
        return sPersonasAplicaronE;
    }
    public String getsImagenEmpleoE() {
        return sImagenEmpleoE;
}

    public String getsIdEmpleador() {
        return sIdEmpleadorE;
    }
    public String getsFormacionAcademica() {
        return sFormacionAcademica;
    }
    public String getsAnosExperienciaDE() {
        return sAnosExperienciaDE;
    }


    public Map<String, Object> toMap(){

        HashMap<String,Object> ListEmpleoss= new HashMap<>();
        ListEmpleoss.put("FOTO_EMPLEO",sImagenEmpleoE);//-------------------------------------resolver, antes de
        ListEmpleoss.put("sIDEmpleo",sIDEmpleo);
        ListEmpleoss.put("sNombreEmpleoE",sNombreEmpleoE);
        ListEmpleoss.put("sNombreEmpresaE",sNombreEmpresaE);
        ListEmpleoss.put("sProvinciaE",sProvinciaE);
        ListEmpleoss.put("sDireccionE",sDireccionE);
        ListEmpleoss.put("sTelefonoE",sTelefonoE);
        ListEmpleoss.put("sPaginaWebE",sPaginaWebE);
        ListEmpleoss.put("sEmailE",sEmailE);
        ListEmpleoss.put("sSalarioE",sSalarioE);
        ListEmpleoss.put("sOtrosDatosE",sOtrosDatosE);
        ListEmpleoss.put("sHorarioE",sHorarioE);
        ListEmpleoss.put("sMostrarIdioma",sMostrarIdioma);
        ListEmpleoss.put("EXPERIENCIA",sAreaE);//-------------------------------------resolver, antes de
        ListEmpleoss.put("ESTADO",sSexoRequeridoE);//-------------------------------------resolver, antes de
        ListEmpleoss.put("DIRECCION",sRangoE);//-------------------------------------resolver, antes de
        ListEmpleoss.put("sJornadaE",sJornadaE);
        ListEmpleoss.put("sCantidadVacantesE",sCantidadVacantesE);
        ListEmpleoss.put("sTipoContratoE",sTipoContratoE);
        ListEmpleoss.put("sFechaPublicacionE",sFechaPublicacionE);
        ListEmpleoss.put("sPersonasAplicaronE",sPersonasAplicaronE);
        ListEmpleoss.put("sEstadoEmpleoE",sEstadoEmpleoE);
        ListEmpleoss.put("sIdEmpleadorE",sIdEmpleadorE);




        return ListEmpleoss;
    }


}
