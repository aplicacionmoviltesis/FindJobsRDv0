package com.example.findjobsrdv0.Modelos_CurriculoCompleto;

import java.io.Serializable;

public class modeloCompararCurriculo implements Serializable {

    public modeloCompararCurriculo() {
    }

    public void setsIdCurriculo(String sIdCurriculo) {
        this.sIdCurriculo = sIdCurriculo;
    }

    public void setsIdBuscadorEmpleo(String sIdBuscadorEmpleo) {
        this.sIdBuscadorEmpleo = sIdBuscadorEmpleo;
    }

    public void setsImagenC(String sImagenC) {
        this.sImagenC = sImagenC;
    }

    public void setsNombreC(String sNombreC) {
        this.sNombreC = sNombreC;
    }

    public void setsApellidoC(String sApellidoC) {
        this.sApellidoC = sApellidoC;
    }

    public void setsCedulaC(String sCedulaC) {
        this.sCedulaC = sCedulaC;
    }

    public void setsEmailC(String sEmailC) {
        this.sEmailC = sEmailC;
    }

    public void setsTelefonoC(String sTelefonoC) {
        this.sTelefonoC = sTelefonoC;
    }

    public void setsCelularC(String sCelularC) {
        this.sCelularC = sCelularC;
    }

    public void setsProvinciaC(String sProvinciaC) {
        this.sProvinciaC = sProvinciaC;
    }

    public void setsEstadoCivilC(String sEstadoCivilC) {
        this.sEstadoCivilC = sEstadoCivilC;
    }

    public void setsDireccionC(String sDireccionC) {
        this.sDireccionC = sDireccionC;
    }

    public void setsOcupacionC(String sOcupacionC) {
        this.sOcupacionC = sOcupacionC;
    }

    public void setsIdiomaC(String sIdiomaC) {
        this.sIdiomaC = sIdiomaC;
    }

    public void setsGradoMayorC(String sGradoMayorC) {
        this.sGradoMayorC = sGradoMayorC;
    }

    public void setsEstadoActualC(String sEstadoActualC) {
        this.sEstadoActualC = sEstadoActualC;
    }

    public void setsSexoC(String sSexoC) {
        this.sSexoC = sSexoC;
    }

    public void setsHabilidadesC(String sHabilidadesC) {
        this.sHabilidadesC = sHabilidadesC;
    }

    public void setsFechaC(String sFechaC) {
        this.sFechaC = sFechaC;
    }

    public void setsAreaC(String sAreaC) {
        this.sAreaC = sAreaC;
    }

    private String sIdCurriculo, sIdBuscadorEmpleo, sImagenC, sNombreC, sApellidoC, sCedulaC, sEmailC, sTelefonoC, sCelularC,
            sProvinciaC, sEstadoCivilC, sDireccionC, sOcupacionC, sIdiomaC, sGradoMayorC,
            sEstadoActualC, sSexoC, sHabilidadesC,sFechaC, sAreaC;

    public modeloCompararCurriculo(String sIdCurriculo, String sIdBuscadorEmpleo, String sImagenC, String sNombreC, String sApellidoC, String sCedulaC, String sEmailC, String sTelefonoC, String sCelularC, String sProvinciaC, String sEstadoCivilC, String sDireccionC, String sOcupacionC, String sIdiomaC, String sGradoMayorC, String sEstadoActualC, String sSexoC, String sHabilidadesC, String sFechaC, String sAreaC) {
        this.sIdCurriculo = sIdCurriculo;
        this.sIdBuscadorEmpleo = sIdBuscadorEmpleo;
        this.sImagenC = sImagenC;
        this.sNombreC = sNombreC;
        this.sApellidoC = sApellidoC;
        this.sCedulaC = sCedulaC;
        this.sEmailC = sEmailC;
        this.sTelefonoC = sTelefonoC;
        this.sCelularC = sCelularC;
        this.sProvinciaC = sProvinciaC;
        this.sEstadoCivilC = sEstadoCivilC;
        this.sDireccionC = sDireccionC;
        this.sOcupacionC = sOcupacionC;
        this.sIdiomaC = sIdiomaC;
        this.sGradoMayorC = sGradoMayorC;
        this.sEstadoActualC = sEstadoActualC;
        this.sSexoC = sSexoC;
        this.sHabilidadesC = sHabilidadesC;
        this.sFechaC = sFechaC;
        this.sAreaC = sAreaC;
    }

    private boolean isChecked = false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }


    public String getsIdCurriculo() {
        return sIdCurriculo;
    }

    public String getsIdBuscadorEmpleo() {
        return sIdBuscadorEmpleo;
    }

    public String getsImagenC() {
        return sImagenC;
    }

    public String getsNombreC() {
        return sNombreC;
    }

    public String getsApellidoC() {
        return sApellidoC;
    }

    public String getsCedulaC() {
        return sCedulaC;
    }

    public String getsEmailC() {
        return sEmailC;
    }

    public String getsTelefonoC() {
        return sTelefonoC;
    }

    public String getsCelularC() {
        return sCelularC;
    }

    public String getsProvinciaC() {
        return sProvinciaC;
    }

    public String getsEstadoCivilC() {
        return sEstadoCivilC;
    }

    public String getsDireccionC() {
        return sDireccionC;
    }

    public String getsOcupacionC() {
        return sOcupacionC;
    }

    public String getsIdiomaC() {
        return sIdiomaC;
    }

    public String getsGradoMayorC() {
        return sGradoMayorC;
    }

    public String getsEstadoActualC() {
        return sEstadoActualC;
    }

    public String getsSexoC() {
        return sSexoC;
    }

    public String getsHabilidadesC() {
        return sHabilidadesC;
    }

    public String getsFechaC() {
        return sFechaC;
    }

    public String getsAreaC() {
        return sAreaC;
    }
}
