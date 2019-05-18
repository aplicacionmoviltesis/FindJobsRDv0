package com.example.findjobsrdv0.Vista_recycler_en_los_insert.RecyclerExperienciaLaboral.Modelo;

public class modeloExperienciaLaboral {

   String elCargoOcupado, elFechaEntrada, elTelefono, elFechaSalida, elNombreEmpresa;

    public modeloExperienciaLaboral() {
    }

    public modeloExperienciaLaboral(String elCargoOcupado, String elFechaEntrada, String elTelefono, String elFechaSalida, String elNombreEmpresa) {
        this.elCargoOcupado = elCargoOcupado;
        this.elFechaEntrada = elFechaEntrada;
        this.elTelefono = elTelefono;
        this.elFechaSalida = elFechaSalida;
        this.elNombreEmpresa = elNombreEmpresa;
    }

    public String getElCargoOcupado() {
        return elCargoOcupado;
    }

    public void setElCargoOcupado(String elCargoOcupado) {
        this.elCargoOcupado = elCargoOcupado;
    }

    public String getElFechaEntrada() {
        return elFechaEntrada;
    }

    public void setElFechaEntrada(String elFechaEntrada) {
        this.elFechaEntrada = elFechaEntrada;
    }

    public String getElTelefono() {
        return elTelefono;
    }

    public void setElTelefono(String elTelefono) {
        this.elTelefono = elTelefono;
    }

    public String getElFechaSalida() {
        return elFechaSalida;
    }

    public void setElFechaSalida(String elFechaSalida) {
        this.elFechaSalida = elFechaSalida;
    }

    public String getElNombreEmpresa() {
        return elNombreEmpresa;
    }

    public void setElNombreEmpresa(String elNombreEmpresa) {
        this.elNombreEmpresa = elNombreEmpresa;
    }
}
