package co.com.telmex.catastro.services.util;

import co.com.telmex.catastro.data.Marcas;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase ConsultaSolRedModificacion
 * implementa Serialización
 *
 * @author 	Deiver Rovira.
 * @version	1.0
 */
public class ConsultaSolRedModificacion implements Serializable {

    private BigDecimal id_SolRed;
    private BigDecimal id_detSol;
    private BigDecimal id_nodo;
    private BigDecimal id_hhpp;
    private String dso_apto;
    private String dso_dir;
    private BigDecimal dso_estrato;
    private String dso_dir_sta;
    private String dso_nombre;
    private String dso_fuente;
    private String dso_dir_alt;
    private String dso_dir_alt_sta;
    private String dso_estado_hhpp;
    private String dsoEstadoHhpp;
    private String geo_localidad;
    private String geo_barrio;
    private String geo_manzana;
    private String comunidadFromHhpp;
    private String comunidadFromNodo;
    private String cod_nodo;
    private String dso_nd1_geo;
    private String dso_nd2_geo;
    private String dso_nd3_geo;
    private String tipoHhppFromHhpp;
    private String tipoNodoFromNodo;
    private String idGpoFromNodo;
    private String mensaje;
    private List<Marcas> listaMarcas = new ArrayList<Marcas>();
    private String tipoacometida;
    private String tipoconexion;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (this.id_detSol != null ? this.id_detSol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConsultaSolRedModificacion other = (ConsultaSolRedModificacion) obj;
        if (this.id_detSol != other.id_detSol && (this.id_detSol == null || !this.id_detSol.equals(other.id_detSol))) {
            return false;
        }
        return true;
    }

    /**
     * 
     * @return
     */
    public String getDso_apto() {
        return dso_apto;
    }

    /**
     * 
     * @param dso_apto
     */
    public void setDso_apto(String dso_apto) {
        this.dso_apto = dso_apto;
    }

    /**
     * 
     * @return
     */
    public String getDso_dir() {
        return dso_dir;
    }

    /**
     * 
     * @param dso_dir
     */
    public void setDso_dir(String dso_dir) {
        this.dso_dir = dso_dir;
    }

    /**
     * 
     * @return
     */
    public String getDso_dir_alt() {
        return dso_dir_alt;
    }

    /**
     * 
     * @param dso_dir_alt
     */
    public void setDso_dir_alt(String dso_dir_alt) {
        this.dso_dir_alt = dso_dir_alt;
    }

    /**
     * 
     * @return
     */
    public String getDso_dir_alt_sta() {
        return dso_dir_alt_sta;
    }

    /**
     * 
     * @param dso_dir_alt_sta
     */
    public void setDso_dir_alt_sta(String dso_dir_alt_sta) {
        this.dso_dir_alt_sta = dso_dir_alt_sta;
    }

    /**
     * 
     * @return
     */
    public String getDso_dir_sta() {
        return dso_dir_sta;
    }

    /**
     * 
     * @param dso_dir_sta
     */
    public void setDso_dir_sta(String dso_dir_sta) {
        this.dso_dir_sta = dso_dir_sta;
    }

    /**
     * 
     * @return
     */
    public String getDso_fuente() {
        return dso_fuente;
    }

    /**
     * 
     * @param dso_fuente
     */
    public void setDso_fuente(String dso_fuente) {
        this.dso_fuente = dso_fuente;
    }

    /**
     * 
     * @return
     */
    public String getDso_nombre() {
        return dso_nombre;
    }

    /**
     * 
     * @param dso_nombre
     */
    public void setDso_nombre(String dso_nombre) {
        this.dso_nombre = dso_nombre;
    }

    /**
     * 
     * @return
     */
    public String getGeo_barrio() {
        return geo_barrio;
    }

    /**
     * 
     * @param geo_barrio
     */
    public void setGeo_barrio(String geo_barrio) {
        this.geo_barrio = geo_barrio;
    }

    /**
     * 
     * @return
     */
    public String getGeo_localidad() {
        return geo_localidad;
    }

    /**
     * 
     * @param geo_localidad
     */
    public void setGeo_localidad(String geo_localidad) {
        this.geo_localidad = geo_localidad;
    }

    /**
     * 
     * @return
     */
    public String getGeo_manzana() {
        return geo_manzana;
    }

    /**
     * 
     * @param geo_manzana
     */
    public void setGeo_manzana(String geo_manzana) {
        this.geo_manzana = geo_manzana;
    }

    /**
     * 
     * @return
     */
    public BigDecimal getId_SolRed() {
        return id_SolRed;
    }

    /**
     * 
     * @param id_SolRed
     */
    public void setId_SolRed(BigDecimal id_SolRed) {
        this.id_SolRed = id_SolRed;
    }

    /**
     * 
     * @return
     */
    public BigDecimal getId_detSol() {
        return id_detSol;
    }

    /**
     * 
     * @param id_detSol
     */
    public void setId_detSol(BigDecimal id_detSol) {
        this.id_detSol = id_detSol;
    }

    /**
     * 
     * @return
     */
    public BigDecimal getId_nodo() {
        return id_nodo;
    }

    /**
     * 
     * @param id_nodo
     */
    public void setId_nodo(BigDecimal id_nodo) {
        this.id_nodo = id_nodo;
    }

    /**
     * 
     * @return
     */
    public BigDecimal getId_hhpp() {
        return id_hhpp;
    }

    /**
     * 
     * @param id_hhpp
     */
    public void setId_hhpp(BigDecimal id_hhpp) {
        this.id_hhpp = id_hhpp;
    }

    /**
     * 
     * @return
     */
    public String getDso_estado_hhpp() {
        return dso_estado_hhpp;
    }

    /**
     * 
     * @param dso_estado_hhpp
     */
    public void setDso_estado_hhpp(String dso_estado_hhpp) {
        this.dso_estado_hhpp = dso_estado_hhpp;
    }

    /**
     * 
     * @return
     */
    public BigDecimal getDso_estrato() {
        return dso_estrato;
    }

    /**
     * 
     * @param dso_estrato
     */
    public void setDso_estrato(BigDecimal dso_estrato) {
        this.dso_estrato = dso_estrato;
    }

    /**
     * 
     * @return
     */
    public String getComunidadFromHhpp() {
        return comunidadFromHhpp;
    }

    /**
     * 
     * @param comunidadFromHhpp
     */
    public void setComunidadFromHhpp(String comunidadFromHhpp) {
        this.comunidadFromHhpp = comunidadFromHhpp;
    }

    /**
     * 
     * @return
     */
    public String getComunidadFromNodo() {
        return comunidadFromNodo;
    }

    /**
     * 
     * @param comunidadFromNodo
     */
    public void setComunidadFromNodo(String comunidadFromNodo) {
        this.comunidadFromNodo = comunidadFromNodo;
    }

    /**
     * 
     * @return
     */
    public String getCod_nodo() {
        return cod_nodo;
    }

    /**
     * 
     * @param cod_nodo
     */
    public void setCod_nodo(String cod_nodo) {
        this.cod_nodo = cod_nodo;
    }

    /**
     * 
     * @return
     */
    public String getDsoEstadoHhpp() {
        return dsoEstadoHhpp;
    }

    /**
     * 
     * @param dsoEstadoHhpp
     */
    public void setDsoEstadoHhpp(String dsoEstadoHhpp) {
        this.dsoEstadoHhpp = dsoEstadoHhpp;
    }

    /**
     * 
     * @return
     */
    public String getTipoHhppFromHhpp() {
        return tipoHhppFromHhpp;
    }

    /**
     * 
     * @param tipoHhppFromHhpp
     */
    public void setTipoHhppFromHhpp(String tipoHhppFromHhpp) {
        this.tipoHhppFromHhpp = tipoHhppFromHhpp;
    }

    /**
     * 
     * @return
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * 
     * @param mensaje
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * 
     * @return
     */
    public List<Marcas> getListaMarcas() {
        return listaMarcas;
    }

    /**
     * 
     * @param listaMarcas
     */
    public void setListaMarcas(List<Marcas> listaMarcas) {
        this.listaMarcas = listaMarcas;
    }

    /**
     * 
     * @return
     */
    public String getDso_nd1_geo() {
        return dso_nd1_geo;
    }

    /**
     * 
     * @param dso_nd1_geo
     */
    public void setDso_nd1_geo(String dso_nd1_geo) {
        this.dso_nd1_geo = dso_nd1_geo;
    }

    /**
     * 
     * @return
     */
    public String getDso_nd2_geo() {
        return dso_nd2_geo;
    }

    /**
     * 
     * @param dso_nd2_geo
     */
    public void setDso_nd2_geo(String dso_nd2_geo) {
        this.dso_nd2_geo = dso_nd2_geo;
    }

    /**
     * 
     * @return
     */
    public String getDso_nd3_geo() {
        return dso_nd3_geo;
    }

    /**
     * 
     * @param dso_nd3_geo
     */
    public void setDso_nd3_geo(String dso_nd3_geo) {
        this.dso_nd3_geo = dso_nd3_geo;
    }

    /**
     * 
     * @return
     */
    public String getTipoNodoFromNodo() {
        return tipoNodoFromNodo;
    }

    /**
     * 
     * @param tipoNodoFromNodo
     */
    public void setTipoNodoFromNodo(String tipoNodoFromNodo) {
        this.tipoNodoFromNodo = tipoNodoFromNodo;
    }

    /**
     * 
     * @return
     */
    public String getIdGpoFromNodo() {
        return idGpoFromNodo;
    }

    /**
     * 
     * @param idGpoFromNodo
     */
    public void setIdGpoFromNodo(String idGpoFromNodo) {
        this.idGpoFromNodo = idGpoFromNodo;
    }

    /**
     * @return the tipoacometida
     */
    public String getTipoacometida() {
        return tipoacometida;
    }

    /**
     * @param tipoacometida the tipoacometida to set
     */
    public void setTipoacometida(String tipoacometida) {
        this.tipoacometida = tipoacometida;
    }

    /**
     * @return the tipoconexion
     */
    public String getTipoconexion() {
        return tipoconexion;
    }

    /**
     * @param tipoconexion the tipoconexion to set
     */
    public void setTipoconexion(String tipoconexion) {
        this.tipoconexion = tipoconexion;
    }
}