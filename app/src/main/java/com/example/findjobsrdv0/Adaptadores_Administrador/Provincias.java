package com.example.findjobsrdv0.Adaptadores_Administrador;

public class Provincias {

    public Provincias() {
    }

    private String sIdProvincia;
    private String sNombreProvincia;
    private String sDescripcionProvincia;
    private String sDivisionTerritorialProvincia;
    private String sPoblacionProvincia;
    private String sImagenProvincia;
    private String sIdUserAdminProvincia;
    private String sEconomiaProvincia;
    private String sClimaProvincia;
    private String sAtractivosProvincia;

    public Provincias(String sIdProvincia, String sNombreProvincia, String sDescripcionProvincia,String sDivisionTerritorialProvincia, String sPoblacionProvincia, String sImagenProvincia,String sIdUserAdminProvincia,
                      String sEconomiaProvincia,String sClimaProvincia,String sAtractivosProvincia) {
        this.sIdProvincia = sIdProvincia;
        this.sNombreProvincia = sNombreProvincia;
        this.sDescripcionProvincia = sDescripcionProvincia;
        this.sPoblacionProvincia = sPoblacionProvincia;
        this.sImagenProvincia = sImagenProvincia;
        this.sEconomiaProvincia = sEconomiaProvincia;
        this.sClimaProvincia = sClimaProvincia;
        this.sAtractivosProvincia = sAtractivosProvincia;
        this.sIdUserAdminProvincia = sIdUserAdminProvincia;
        this.sDivisionTerritorialProvincia = sDivisionTerritorialProvincia;

    }
    public String getsIdUserAdminProvincia() {
        return sIdUserAdminProvincia;
    }

    public void setsIdUserAdminProvincia(String sIdUserAdminProvincia) {
        this.sIdUserAdminProvincia = sIdUserAdminProvincia;
    }

    public String getsEconomiaProvincia() {
        return sEconomiaProvincia;
    }

    public void setsEconomiaProvincia(String sEconomiaProvincia) {
        this.sEconomiaProvincia = sEconomiaProvincia;
    }

    public String getsClimaProvincia() {
        return sClimaProvincia;
    }

    public void setsClimaProvincia(String sClimaProvincia) {
        this.sClimaProvincia = sClimaProvincia;
    }

    public String getsAtractivosProvincia() {
        return sAtractivosProvincia;
    }

    public void setsAtractivosProvincia(String sAtractivosProvincia) {
        this.sAtractivosProvincia = sAtractivosProvincia;
    }

    public String getsIdProvincia() {
        return sIdProvincia;
    }

    public void setsIdProvincia(String sIdProvincias) {
        this.sIdProvincia = sIdProvincias;
    }

    public String getsNombreProvincia() {
        return sNombreProvincia;
    }

    public void setsNombreProvincia(String sNombreProvincia) {
        this.sNombreProvincia = sNombreProvincia;
    }

    public String getsDescripcionProvincia() {
        return sDescripcionProvincia;
    }

    public void setsDescripcionProvincia(String sDescripcionProvincia) {
        this.sDescripcionProvincia = sDescripcionProvincia;
    }

    public String getsPoblacionProvincia() {
        return sPoblacionProvincia;
    }

    public void setsPoblacionProvincia(String sPoblacionProvincia) {
        this.sPoblacionProvincia = sPoblacionProvincia;
    }

    public String getsImagenProvincia() {
        return sImagenProvincia;
    }

    public void setsImagenProvincia(String sImagenProvincia) {
        this.sImagenProvincia = sImagenProvincia;
    }

    public String getsDivisionTerritorialProvincia() {
        return sDivisionTerritorialProvincia;
    }

    public void setsDivisionTerritorialProvincia(String sDivisionTerritorialProvincia) {
        this.sDivisionTerritorialProvincia = sDivisionTerritorialProvincia;
    }
}
