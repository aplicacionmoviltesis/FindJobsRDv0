package com.example.findjobsrdv0.Modelo;

public class RegistrarCurriculo {
    String codigoid, nombre, apellido, cedula,email,telefono,celular,provincia,estadocivil,direccion,ocupacion,gradomayor,estadoactual,habilidades;

    public RegistrarCurriculo(String codigoid, String nombre, String apellido, String cedula, String email, String telefono, String celular, String provincia, String estadocivil, String direccion, String ocupacion, String gradomayor, String estadoactual, String habilidades) {
        this.codigoid = codigoid;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.email = email;
        this.telefono = telefono;
        this.celular = celular;
        this.provincia = provincia;
        this.estadocivil = estadocivil;
        this.direccion = direccion;
        this.ocupacion = ocupacion;
        this.gradomayor = gradomayor;
        this.estadoactual = estadoactual;
        this.habilidades = habilidades;
    }

    public String getCodigoid() {
        return codigoid;
    }

    public void setCodigoid(String codigoid) {
        this.codigoid = codigoid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getEstadocivil() {
        return estadocivil;
    }

    public void setEstadocivil(String estadocivil) {
        this.estadocivil = estadocivil;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getGradomayor() {
        return gradomayor;
    }

    public void setGradomayor(String gradomayor) {
        this.gradomayor = gradomayor;
    }

    public String getEstadoactual() {
        return estadoactual;
    }

    public void setEstadoactual(String estadoactual) {
        this.estadoactual = estadoactual;
    }

    public String getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(String habilidades) {
        this.habilidades = habilidades;
    }
}
