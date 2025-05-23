package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Clase SolicitudConsulta
 * Implementa Serialización.
 *
 * @author 	Ana María.
 * @version	1.0
 */
public class SolicitudConsulta implements Serializable {

    private BigDecimal identificador = null;
    private BigDecimal nivsocio = null;
    private String direccionm = null;
    private BigDecimal estrato = null;
    private String actividadec = null;

    /**
     * Obtiene las Actividades
     * @return Cadena con las actividades.
     */
    public String getActividadec() {
        return actividadec;
    }

    /**
     * Establece las actividades.
     * @param actividadec Cadena con las actividades.
     */
    public void setActividadec(String actividadec) {
        this.actividadec = actividadec;
    }

    /** 
     * Obtiene el estrato
     * @return Cadena con el estrato.
     */
    public BigDecimal getEstrato() {
        return estrato;
    }

    /**
     * Establece el estrato.
     * @param estrato Entero con el estrato.
     */
    public void setEstrato(BigDecimal estrato) {
        this.estrato = estrato;
    }

    /**
     * Obtiene la dirección. 
     * @return Cadena con la dirección.
     */
    public String getDireccionm() {
        return direccionm;
    }

    /**
     * Establece la dirección.
     * @param direccionm Cadena con la dirección.
     */
    public void setDireccionm(String direccionm) {
        this.direccionm = direccionm;
    }

    /**
     * Obtiene el nivel socio económico.
     * @return Entero con el nivel socio económico.
     */
    public BigDecimal getNivsocio() {
        return nivsocio;
    }

    /**
     * Establece el nivel socio económico.
     * @param nivsocio Entero con el nivel socio económico.
     */
    public void setNivsocio(BigDecimal nivsocio) {
        this.nivsocio = nivsocio;
    }

    /**
     * Obtiene el identificador.
     * @return Entero con el identificador.
     */
    public BigDecimal getIdentificador() {
        return identificador;
    }

    /**
     * Establece con el identificador.
     * @param identificador Entero con el identificador.
     */
    public void setIdentificador(BigDecimal identificador) {
        this.identificador = identificador;
    }

    /**
     * Cadena con la configuración para auditoría de carga.
     * @return Cadena con los campos que contiene la clase.
     */
    public String auditoria() {
        return "Direccion:" + "estrato=" + estrato + ", identificador=" + identificador
                + ", nivsocio=" + nivsocio
                + ", direccionm=" + direccionm + ", actividadec=" + actividadec + '.';
    }
}
