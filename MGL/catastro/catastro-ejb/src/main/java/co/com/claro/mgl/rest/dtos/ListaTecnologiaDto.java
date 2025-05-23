
package co.com.claro.mgl.rest.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 *  Clase que contiene las propiedades de cada tecnologia para salida a la operacion consultaFactibilidad
 *
 * @author Yasser Leon
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "listaTecnologiaDto", propOrder = {
        "codigoTecnologia",
        "estado",
        "nodo",
        "estadoNodo",
        "factibilidad",
        "distanciaOffNet",
        "nodoBakup",
        "distanciaBackup",
        "sds",
        "sdsBackup",
        "tiempoAproximadoInstalacion",
        "arrendatarios"
})
public class ListaTecnologiaDto {

    private String codigoTecnologia;
    private String estado;
    private String nodo;
    private String estadoNodo;
    private String factibilidad;
    private Integer distanciaOffNet;
    private String nodoBakup;
    private Integer distanciaBackup;
    private String sds;
    private String sdsBackup;
    private String tiempoAproximadoInstalacion;
    protected ArrendatariosDto arrendatarios;

    /**
     * Gets the value of the codigoTecnologia property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCodigoTecnologia() {
        return codigoTecnologia;
    }

    /**
     * Sets the value of the codigoTecnologia property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCodigoTecnologia(String value) {
        this.codigoTecnologia = value;
    }

    /**
     * Gets the value of the estado property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Sets the value of the estado property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setEstado(String value) {
        this.estado = value;
    }

    /**
     * Gets the value of the nodo property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getNodo() {
        return nodo;
    }

    /**
     * Sets the value of the nodo property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNodo(String value) {
        this.nodo = value;
    }

    /**
     * Gets the value of the estadoNodo property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getEstadoNodo() {
        return estadoNodo;
    }

    /**
     * Sets the value of the estadoNodo property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setEstadoNodo(String value) {
        this.estadoNodo = value;
    }

    /**
     * Gets the value of the factibilidad property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getFactibilidad() {
        return factibilidad;
    }

    /**
     * Sets the value of the factibilidad property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setFactibilidad(String value) {
        this.factibilidad = value;
    }

    /**
     * Gets the value of the distanciaOffNet property.
     *
     * @return possible object is
     * {@link Integer }
     */
    public Integer getDistanciaOffNet() {
        return distanciaOffNet;
    }

    /**
     * Sets the value of the distanciaOffNet property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setDistanciaOffNet(Integer value) {
        this.distanciaOffNet = value;
    }

    /**
     * Gets the value of the nodoBakup property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getNodoBakup() {
        return nodoBakup;
    }

    /**
     * Sets the value of the nodoBakup property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNodoBakup(String value) {
        this.nodoBakup = value;
    }

    /**
     * Gets the value of the distanciaBackup property.
     *
     * @return possible object is
     * {@link Integer }
     */
    public Integer getDistanciaBackup() {
        return distanciaBackup;
    }

    /**
     * Sets the value of the distanciaBackup property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setDistanciaBackup(Integer value) {
        this.distanciaBackup = value;
    }

    /**
     * Gets the value of the sds property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getSds() {
        return sds;
    }

    /**
     * Sets the value of the sds property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSds(String value) {
        this.sds = value;
    }

    /**
     * Gets the value of the sdsBackup property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getSdsBackup() {
        return sdsBackup;
    }

    /**
     * Sets the value of the sdsBackup property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSdsBackup(String value) {
        this.sdsBackup = value;
    }

    /**
     * Gets the value of the tiempoAproximadoInstalacion property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getTiempoAproximadoInstalacion() {
        return tiempoAproximadoInstalacion;
    }

    /**
     * Sets the value of the tiempoAproximadoInstalacion property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTiempoAproximadoInstalacion(String value) {
        this.tiempoAproximadoInstalacion = value;
    }

    /**
     * Gets the value of the arrendatarios property.
     *
     * @return possible object is
     * {@link ArrendatariosDto }
     */
    public ArrendatariosDto getArrendatarios() {
        return arrendatarios;
    }

    /**
     * Sets the value of the arrendatarios property.
     *
     * @param value allowed object is
     *              {@link ArrendatariosDto }
     */
    public void setArrendatarios(ArrendatariosDto value) {
        this.arrendatarios = value;
    }

}
