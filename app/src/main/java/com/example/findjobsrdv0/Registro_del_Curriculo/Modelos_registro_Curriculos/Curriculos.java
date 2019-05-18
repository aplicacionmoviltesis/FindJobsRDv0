package com.example.findjobsrdv0.Registro_del_Curriculo.Modelos_registro_Curriculos;

import java.util.HashMap;
import java.util.Map;

public class Curriculos {
    String cCodigoId, cIdBuscador, nombre, apellido, cedula, email, telefono, celular, provincia, estadoCivil, direccion, ocupacion, idioma, gradomayor, estadoactual, habilidades, fecha;

    public Curriculos() {
    }

    public Curriculos(String cCodigoId, String cIdBuscador, String nombre, String apellido, String cedula, String email, String telefono, String celular, String provincia, String estadoCivil, String direccion, String ocupacion, String idioma, String gradomayor, String estadoactual, String habilidades, String fecha) {
        this.cCodigoId = cCodigoId;
        this.cIdBuscador = cIdBuscador;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.email = email;
        this.telefono = telefono;
        this.celular = celular;
        this.provincia = provincia;
        this.estadoCivil = estadoCivil;
        this.direccion = direccion;
        this.ocupacion = ocupacion;
        this.idioma = idioma;
        this.gradomayor = gradomayor;
        this.estadoactual = estadoactual;
        this.habilidades = habilidades;
        this.fecha = fecha;
    }

    public String getcCodigoId() {
        return cCodigoId;
    }

    public void setcCodigoId(String cCodigoId) {
        this.cCodigoId = cCodigoId;
    }

    public String getcIdBuscador() {
        return cIdBuscador;
    }

    public void setcIdBuscador(String cIdBuscador) {
        this.cIdBuscador = cIdBuscador;
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

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
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

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Map<String, Object> CurrtoMap(){

        HashMap<String,Object> ListCurriculo= new HashMap<>();
        ListCurriculo.put("codigoId",cCodigoId);
        ListCurriculo.put("nombre",nombre);
        ListCurriculo.put("apellido",apellido);
        ListCurriculo.put("cedula",cedula);
        ListCurriculo.put("email",email);
        ListCurriculo.put("telefono",telefono);
        ListCurriculo.put("celular",celular);
        ListCurriculo.put("provincia",provincia);
        ListCurriculo.put("estadocivil",estadoCivil);
        ListCurriculo.put("direccion",direccion);
        ListCurriculo.put("ocupacion",ocupacion);
        ListCurriculo.put("idioma",idioma);
        ListCurriculo.put("gradomayor",gradomayor);
        ListCurriculo.put("estadoactual",estadoactual);
        ListCurriculo.put("habilidades",habilidades);
        ListCurriculo.put("fecha",fecha);


        return ListCurriculo;
    }

}
