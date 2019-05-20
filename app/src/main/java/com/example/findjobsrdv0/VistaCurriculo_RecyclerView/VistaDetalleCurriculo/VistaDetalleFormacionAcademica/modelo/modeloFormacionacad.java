package com.example.findjobsrdv0.VistaCurriculo_RecyclerView.VistaDetalleCurriculo.VistaDetalleFormacionAcademica.modelo;

public class modeloFormacionacad {
    String carrerac, nivelprimarioc, nivelsecundarioc, nivelsuperiorc;

    public modeloFormacionacad() {
    }

    public modeloFormacionacad(String carrerac, String nivelprimarioc, String nivelsecundarioc, String nivelsuperiorc) {
        this.carrerac = carrerac;
        this.nivelprimarioc = nivelprimarioc;
        this.nivelsecundarioc = nivelsecundarioc;
        this.nivelsuperiorc = nivelsuperiorc;
    }

    public String getCarrerac() {
        return carrerac;
    }

    public void setCarrerac(String carrerac) {
        this.carrerac = carrerac;
    }

    public String getNivelprimarioc() {
        return nivelprimarioc;
    }

    public void setNivelprimarioc(String nivelprimarioc) {
        this.nivelprimarioc = nivelprimarioc;
    }

    public String getNivelsecundarioc() {
        return nivelsecundarioc;
    }

    public void setNivelsecundarioc(String nivelsecundarioc) {
        this.nivelsecundarioc = nivelsecundarioc;
    }

    public String getNivelsuperiorc() {
        return nivelsuperiorc;
    }

    public void setNivelsuperiorc(String nivelsuperiorc) {
        this.nivelsuperiorc = nivelsuperiorc;
    }
}
