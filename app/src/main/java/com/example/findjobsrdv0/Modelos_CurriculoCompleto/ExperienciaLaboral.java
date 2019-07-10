package com.example.findjobsrdv0.Modelos_CurriculoCompleto;

import java.util.HashMap;
import java.util.Map;

public class ExperienciaLaboral {
    private String sIdExperienciaLaboral, sIdCurriculoExpLab, sNombreEmpresaExpLab, sCargoOcupadoExpLab, sTelefonoExpLab, sFechaEntradaExpLab,
            sFechaSalidaExpLab;

    public ExperienciaLaboral() {
    }

    public ExperienciaLaboral(String sIdExperienciaLaboral, String sIdCurriculoExpLab, String sNombreEmpresaExpLab, String sCargoOcupadoExpLab, String sTelefonoExpLab, String sFechaEntradaExpLab, String sFechaSalidaExpLab) {
        this.sIdExperienciaLaboral = sIdExperienciaLaboral;
        this.sIdCurriculoExpLab = sIdCurriculoExpLab;
        this.sNombreEmpresaExpLab = sNombreEmpresaExpLab;
        this.sCargoOcupadoExpLab = sCargoOcupadoExpLab;
        this.sTelefonoExpLab = sTelefonoExpLab;
        this.sFechaEntradaExpLab = sFechaEntradaExpLab;
        this.sFechaSalidaExpLab = sFechaSalidaExpLab;
    }

    public String getsIdExperienciaLaboral() {
        return sIdExperienciaLaboral;
    }

    public void setsIdExperienciaLaboral(String sIdExperienciaLaboral) {
        this.sIdExperienciaLaboral = sIdExperienciaLaboral;
    }

    public String getsIdCurriculoExpLab() {
        return sIdCurriculoExpLab;
    }

    public void setsIdCurriculoExpLab(String sIdCurriculoExpLab) {
        this.sIdCurriculoExpLab = sIdCurriculoExpLab;
    }

    public String getsNombreEmpresaExpLab() {
        return sNombreEmpresaExpLab;
    }

    public void setsNombreEmpresaExpLab(String sNombreEmpresaExpLab) {
        this.sNombreEmpresaExpLab = sNombreEmpresaExpLab;
    }

    public String getsCargoOcupadoExpLab() {
        return sCargoOcupadoExpLab;
    }

    public void setsCargoOcupadoExpLab(String sCargoOcupadoExpLab) {
        this.sCargoOcupadoExpLab = sCargoOcupadoExpLab;
    }

    public String getsTelefonoExpLab() {
        return sTelefonoExpLab;
    }

    public void setsTelefonoExpLab(String sTelefonoExpLab) {
        this.sTelefonoExpLab = sTelefonoExpLab;
    }

    public String getsFechaEntradaExpLab() {
        return sFechaEntradaExpLab;
    }

    public void setsFechaEntradaExpLab(String sFechaEntradaExpLab) {
        this.sFechaEntradaExpLab = sFechaEntradaExpLab;
    }

    public String getsFechaSalidaExpLab() {
        return sFechaSalidaExpLab;
    }

    public void setsFechaSalidaExpLab(String sFechaSalidaExpLab) {
        this.sFechaSalidaExpLab = sFechaSalidaExpLab;
    }

    public Map<String, Object> ExpLab(){

        HashMap<String,Object> ListExpLab= new HashMap<>();
        ListExpLab.put("Nombre_Empresa", sNombreEmpresaExpLab );
        ListExpLab.put( "Cargo_Ocupado", sCargoOcupadoExpLab );
        ListExpLab.put("Telefono", sTelefonoExpLab );
        ListExpLab.put("Fecha_Entrada", sFechaEntradaExpLab );
        ListExpLab.put("Fecha_Salida", sFechaSalidaExpLab );

        return ListExpLab;
    }
}
