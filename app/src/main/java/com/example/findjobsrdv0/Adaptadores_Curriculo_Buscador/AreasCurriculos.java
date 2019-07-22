package com.example.findjobsrdv0.Adaptadores_Curriculo_Buscador;

public class AreasCurriculos {

    private String sIdAreaCurriculo, sIdCurriculo , sAreaPrincipalCurr, sAreaSecundariaCurr, sAreaTerciaria;

    public AreasCurriculos() {
    }

    public AreasCurriculos(String sIdAreaCurriculo, String sIdCurriculo, String sAreaPrincipalCurr, String sAreaSecundariaCurr, String sAreaTerciaria) {
        this.sIdAreaCurriculo = sIdAreaCurriculo;
        this.sIdCurriculo = sIdCurriculo;
        this.sAreaPrincipalCurr = sAreaPrincipalCurr;
        this.sAreaSecundariaCurr = sAreaSecundariaCurr;
        this.sAreaTerciaria = sAreaTerciaria;
    }

    public String getsIdAreaCurriculo() {
        return sIdAreaCurriculo;
    }

    public void setsIdAreaCurriculo(String sIdAreaCurriculo) {
        this.sIdAreaCurriculo = sIdAreaCurriculo;
    }

    public String getsIdCurriculo() {
        return sIdCurriculo;
    }

    public void setsIdCurriculo(String sIdCurriculo) {
        this.sIdCurriculo = sIdCurriculo;
    }

    public String getsAreaPrincipalCurr() {
        return sAreaPrincipalCurr;
    }

    public void setsAreaPrincipalCurr(String sAreaPrincipalCurr) {
        this.sAreaPrincipalCurr = sAreaPrincipalCurr;
    }

    public String getsAreaSecundariaCurr() {
        return sAreaSecundariaCurr;
    }

    public void setsAreaSecundariaCurr(String sAreaSecundariaCurr) {
        this.sAreaSecundariaCurr = sAreaSecundariaCurr;
    }

    public String getsAreaTerciaria() {
        return sAreaTerciaria;
    }

    public void setsAreaTerciaria(String sAreaTerciaria) {
        this.sAreaTerciaria = sAreaTerciaria;
    }
}
