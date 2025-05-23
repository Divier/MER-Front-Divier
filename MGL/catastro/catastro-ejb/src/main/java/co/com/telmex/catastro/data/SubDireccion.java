package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Clase SubDireccion.
 * Implementa Serialización.
 *
 * @author 	Deiver. Modificado Carlos Villamil Fase I direcciones 2012-12-18
 * @version	1.0
 * @version	1.1 - Modificado por: Direcciones face I Carlos Vilamil 2013-05-11
 * @version     1.2 - Modificado por: Nodo DTH 2015-09-30
 */
public class SubDireccion implements Serializable {

    private BigDecimal sdiId;
    private String sdiFormatoIgac;
    private String sdiCodigoPostal;
    private Date sdiFechaCreacion;
    private String sdiUsuarioCreacion;
    private Date sdiFechaModificacion;
    private String sdiUsuarioModificacion;
    private String sdiServinformacion;
    private Direccion direccion;
    private BigDecimal sdiEstrato;
    private BigDecimal sdiNivelSocioecono;
    private BigDecimal sdiActividadEcono;
    private String sdiComentarioNivelSocioeconomico;
    private String nodoUno;//Modificado fase 1 direcciones carlos villamil 2013-12-23 V 1.1
    private String nodoDos;//Modificado fase 1 direcciones carlos villamil 2013-12-23 V 1.1
    private String nodoTres;//Modificado fase 1 direcciones carlos villamil 2013-12-23 V 1.1
    /**
     * Nodo DTH
     */
    private String nodoDth;
    private String nodoMovil;
    private String nodoFtth;
    private String nodoFttx;
    private String nodoWifi;
//Inicio cambio version 1.4
    private String geoZonaUnifilar = null;
    private String geoZonaGponDiseniado = null;
    private String geoZonaMicroOndas = null;
    private String  geoZona3G= null;
    private String  geoZona4G= null;
    private String  geoZonaCoberturaCavs= null;
    private String  geoZonaCoberturaUltimaMilla= null;
    private String  geoZonaCurrier= null;
    private String  geoZona5G= null;
//fin cambio version 1.4
    private BigDecimal sdiConfiabilidad = null;
    
    // <editor-fold defaultstate="collapsed" desc="Modificacion Direcciones face I Johnnatan Ortiz 2013-09-30">
    private String cx;
    private String cy;
    // </editor-fold>
    /**
     * Constructor
     */
    public SubDireccion() {
    }

    /**
     * Constructor con parámetros.
     * @param sdiId Entero con el valor del Id de la SubDireccion.
     */
    public SubDireccion(BigDecimal sdiId) {
        this.sdiId = sdiId;
    }

    /**
     * Obtiene el valor del Id de la SubDireccion.
     * @return Entero con el valor del Id de la SubDireccion.
     */
    public BigDecimal getSdiId() {
        return sdiId;
    }

    /**
     * Establece el valor del Id de la SubDireccion.
     * @param sdiId Entero con el valor del Id de la SubDireccion.
     */
    public void setSdiId(BigDecimal sdiId) {
        this.sdiId = sdiId;
    }

    /**
     * Obtiene el valor de la subdirección en formato IGAC 
     * @return Cadena con el valor de la subdirección en formato IGAC
     */
    public String getSdiFormatoIgac() {
        return sdiFormatoIgac;
    }

    /**
     * Establece el valor de la subdirección en formato IGAC
     * @param sdiFormatoIgac Cadena con el valor de la subdirección en formato IGAC.
     */
    public void setSdiFormatoIgac(String sdiFormatoIgac) {
        this.sdiFormatoIgac = sdiFormatoIgac;
    }

    /**
     * Obtiene el valor del código postal de la subdirección.
     * @return Cadena con el valor del código postal de la subdirección.
     */
    public String getSdiCodigoPostal() {
        return sdiCodigoPostal;
    }

    /**
     * Establece el valor del código postal de la subdirección.
     * @param sdiCodigoPostal Cadena con el valor del código postal de la subdirección.
     */
    public void setSdiCodigoPostal(String sdiCodigoPostal) {
        this.sdiCodigoPostal = sdiCodigoPostal;
    }

    /**
     * Obtiene la fecha de creación de la subdirección.
     * @return Fecha de creación de la subdirección.
     */
    public Date getSdiFechaCreacion() {
        return sdiFechaCreacion;
    }

    /**
     * Establece la fecha de creación de la subdirección.
     * @param sdiFechaCreacion Fecha de creación de la subdirección.
     */
    public void setSdiFechaCreacion(Date sdiFechaCreacion) {
        this.sdiFechaCreacion = sdiFechaCreacion;
    }

    /**
     * Obtiene el usuario que crea la subdirección.
     * @return Cadena con el usuario que crea la subdirección.
     */
    public String getSdiUsuarioCreacion() {
        return sdiUsuarioCreacion;
    }

    /**
     * Establece el usuario que crea la subdirección.
     * @param sdiUsuarioCreacion Cadena con el usuario que crea la subdirección.
     */
    public void setSdiUsuarioCreacion(String sdiUsuarioCreacion) {
        this.sdiUsuarioCreacion = sdiUsuarioCreacion;
    }

    /**
     * Obtiene la fecha en la que se modifica la subdirección.
     * @return Fecha en la que se modifica la subdirección.
     */
    public Date getSdiFechaModificacion() {
        return sdiFechaModificacion;
    }

    /**
     * Establece la fecha de modificación de la subdirección.
     * @param sdiFechaModificacion Fecha de modificación de la subdirección.
     */
    public void setSdiFechaModificacion(Date sdiFechaModificacion) {
        this.sdiFechaModificacion = sdiFechaModificacion;
    }

    /**
     * Obtiene el usuario que modifica la subdirección.
     * @return Cadena con el usuario que modifica la subdirección.
     */
    public String getSdiUsuarioModificacion() {
        return sdiUsuarioModificacion;
    }

    /**
     * Establece el usuario que modifica la subdirección.
     * @param sdiUsuarioModificacion Cadena con el usuario que modifica la subdirección.
     */
    public void setSdiUsuarioModificacion(String sdiUsuarioModificacion) {
        this.sdiUsuarioModificacion = sdiUsuarioModificacion;
    }

    /**
     * Obtiene el código de subdirección de Servinformación.
     * @return Cadena con el código de subdirección de Servinformación.
     */
    public String getSdiServinformacion() {
        return sdiServinformacion;
    }

    /**
     * Establece el código de subdirección de Servinformación.
     * @param sdiServinformacion Cadena con el código de subdirección de Servinformación.
     */
    public void setSdiServinformacion(String sdiServinformacion) {
        this.sdiServinformacion = sdiServinformacion;
    }

    /**
     * Obtiene el valor de dirección de la subdirección.
     * @return Clase Direccion con el resultado de la consulta.
     */
    public Direccion getDireccion() {
        return direccion;
    }

    /**
     * Establece el valor de la clase Direccion resultado de la consulta.
     * @param direccion Clase Direccion con el resultado de la consulta.
     */
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el estrato de la subdirección.
     * @return Entero con el valor de estrato de la subdirección.
     */
    public BigDecimal getSdiEstrato() {
        return sdiEstrato;
    }

    /**
     * Establece el valor del estrato de la subdirección.
     * @param sdiEstrato Entero con el valor del estrato de la subdirección.
     */
    public void setSdiEstrato(BigDecimal sdiEstrato) {
        this.sdiEstrato = sdiEstrato;
    }

    /**
     * Obtiene el valor del nivel socio económico de la subdirección.
     * @return Entero con el valor del nivel socio económico de la subdirección.
     */
    public BigDecimal getSdiNivelSocioecono() {
        return sdiNivelSocioecono;
    }

    /**
     * Establece el valor del nivel socio económico de la subdirección.
     * @param sdiNivelSocioecono Entero con el valor del nivel socio económico de la subdirección.
     */
    public void setSdiNivelSocioecono(BigDecimal sdiNivelSocioecono) {
        this.sdiNivelSocioecono = sdiNivelSocioecono;
    }

    /**
     * Obtiene el valor de la actividad económica de la subdirección.
     * @return Entero con el valor de la actividad económica de la subdirección.
     */
    public BigDecimal getSdiActividadEcono() {
        return sdiActividadEcono;
    }

    /**
     * Establece el valor de la actividad económica de la subdirección.
     * @param sdiActividadEcono Entero con el valor de la actividad económica de la subdirección.
     */
    public void setSdiActividadEcono(BigDecimal sdiActividadEcono) {
        this.sdiActividadEcono = sdiActividadEcono;
    }

    /**
     * Obtiene información para el control de la subdirección en uso.
     * @return Cadena con información del Subdirección. (Id, Formato Igac, código postal, fecha de creación, usuario creación, fecha modificación, usuario modificación,  código servinformación, estrato, nivel socio económico, Actividad económica)
     */
    public String auditoria() {
        String auditoria = "SubDireccion:" + "sdiId=" + sdiId
                + ", sdiFormatoIgac=" + sdiFormatoIgac
                + ", sdiCodigoPostal=" + sdiCodigoPostal
                + ", sdiFechaCreacion=" + sdiFechaCreacion
                + ", sdiUsuarioCreacion=" + sdiUsuarioCreacion
                + ", sdiFechaModificacion=" + sdiFechaModificacion
                + ", sdiUsuarioModificacion=" + sdiUsuarioModificacion
                + ", sdiServinformacion=" + sdiServinformacion
                + ", sdiEstrato=" + sdiEstrato
                + ", sdiNivelSocioEcono=" + sdiNivelSocioecono
                + ", sdiActividadEconomica=" + sdiActividadEcono;
        if (direccion != null) {
            auditoria = auditoria + ", direccion=" + direccion.getDirId() + '.';
        } else {
            auditoria = auditoria + '.';
        }
        return auditoria;
    }

    /**
     * Sobre Escritura del método toString().
     * @return Cadena con información del Subdirección. (Id, Formato Igac, código postal, fecha de creación, usuario creación, fecha modificación, usuario modificación,  código servinformación, estrato, nivel socio económico, Actividad económica)
     */
    @Override
    public String toString() {
        return "SubDireccion:" + "sdiId=" + sdiId
                + ", sdiFormatoIgac=" + sdiFormatoIgac
                + ", sdiCodigoPostal=" + sdiCodigoPostal
                + ", sdiCodigoDireccion=" + sdiServinformacion
                + ", sdiEstrato=" + sdiEstrato
                + ", sdiNivelSocioEcono=" + sdiNivelSocioecono
                + ", sdiActividadEconomica=" + sdiActividadEcono
                + ", direccion=" + direccion.getDirId() + '.';
    }

    //inicio Modificado Carlos Villamil Fase I direcciones 2012-12-18
    /**
     * Obtiene los comentarios del nivel socio económico de la subdirección.
     * @return Cadena con los comentarios del nivel socio económico de la subdirección.
     */
    public String getSdiComentarioNivelSocioeconomico() {
        return sdiComentarioNivelSocioeconomico;
    }

    /**
     * Establece los comentarios del nivel socio económico de la subdirección.
     * @param sdiComentarioNivelSocioeconomico Cadena con los comentarios del nivel socio económico de la subdirección.
     */
    public void setSdiComentarioNivelSocioeconomico(String sdiComentarioNivelSocioeconomico) {
        this.sdiComentarioNivelSocioeconomico = sdiComentarioNivelSocioeconomico;
    }
//Fin Modificado Carlos Villamil Fase I direcciones 2012-12-18    

    /**
     * @return the nodoUno V 1.1
     */
    public String getNodoUno() {
        return nodoUno;
    }

    /**
     * @param nodoUno the nodoUno to set V 1.1
     */
    public void setNodoUno(String nodoUno) {
        this.nodoUno = nodoUno;
    }

    /**
     * @return the nodoDos V 1.1
     */
    public String getNodoDos() {
        return nodoDos;
    }

    /**
     * @param nodoDos the nodoDos to set V 1.1
     */
    public void setNodoDos(String nodoDos) {
        this.nodoDos = nodoDos;
    }

    /**
     * @return the nodoTres V 1.1
     */
    public String getNodoTres() {
        return nodoTres;
    }

    /**
     * @param nodoTres the nodoTres to set V 1.1
     */
    public void setNodoTres(String nodoTres) {
        this.nodoTres = nodoTres;
    }
    
    /**
     * Get atributo nodoDth
     * 
     * @return 
     */
    public String getNodoDth() {
        return nodoDth;
    }
    
    /**
     *  Set atributo nodoDth
     * 
     * @param nodoDth 
     */
    public void setNodoDth(String nodoDth) {
        this.nodoDth = nodoDth;
    }

    public String getNodoMovil() {
        return nodoMovil;
    }

    public void setNodoMovil(String nodoMovil) {
        this.nodoMovil = nodoMovil;
    }

    public String getNodoFtth() {
        return nodoFtth;
    }

    public void setNodoFtth(String nodoFtth) {
        this.nodoFtth = nodoFtth;
    }

    public String getNodoWifi() {
        return nodoWifi;
    }

    public void setNodoWifi(String nodoWifi) {
        this.nodoWifi = nodoWifi;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Modificacion Direcciones face I Johnnatan Ortiz 2013-09-30">
    public String getCx() {
        return cx;
    }

    public void setCx(String cx) {
        this.cx = cx;
    }

    public String getCy() {
        return cy;
    }

    public void setCy(String cy) {
        this.cy = cy;
    }

    // </editor-fold>
    public String getGeoZonaUnifilar() {
        return geoZonaUnifilar;
    }

    public void setGeoZonaUnifilar(String geoZonaUnifilar) {
        this.geoZonaUnifilar = geoZonaUnifilar;
    }

    public String getGeoZonaGponDiseniado() {
        return geoZonaGponDiseniado;
    }

    public void setGeoZonaGponDiseniado(String geoZonaGponDiseniado) {
        this.geoZonaGponDiseniado = geoZonaGponDiseniado;
    }

    public String getGeoZonaMicroOndas() {
        return geoZonaMicroOndas;
    }

    public void setGeoZonaMicroOndas(String geoZonaMicroOndas) {
        this.geoZonaMicroOndas = geoZonaMicroOndas;
    }

    public String getGeoZona3G() {
        return geoZona3G;
    }

    public void setGeoZona3G(String geoZona3G) {
        this.geoZona3G = geoZona3G;
    }

    public String getGeoZona4G() {
        return geoZona4G;
    }

    public void setGeoZona4G(String geoZona4G) {
        this.geoZona4G = geoZona4G;
    }

    public String getGeoZonaCoberturaCavs() {
        return geoZonaCoberturaCavs;
    }

    public void setGeoZonaCoberturaCavs(String geoZonaCoberturaCavs) {
        this.geoZonaCoberturaCavs = geoZonaCoberturaCavs;
    }

    public String getGeoZonaCoberturaUltimaMilla() {
        return geoZonaCoberturaUltimaMilla;
    }

    public void setGeoZonaCoberturaUltimaMilla(String geoZonaCoberturaUltimaMilla) {
        this.geoZonaCoberturaUltimaMilla = geoZonaCoberturaUltimaMilla;
    }

    public String getGeoZonaCurrier() {
        return geoZonaCurrier;
    }

    public void setGeoZonaCurrier(String geoZonaCurrier) {
        this.geoZonaCurrier = geoZonaCurrier;
    }

    public String getGeoZona5G() {
        return geoZona5G;
    }

    public void setGeoZona5G(String geoZona5G) {
        this.geoZona5G = geoZona5G;
    }

    public BigDecimal getSdiConfiabilidad() {
        return sdiConfiabilidad;
    }

    public void setSdiConfiabilidad(BigDecimal sdiConfiabilidad) {
        this.sdiConfiabilidad = sdiConfiabilidad;
    }

    public String getNodoFttx() {
        return nodoFttx;
    }

    public void setNodoFttx(String nodoFttx) {
        this.nodoFttx = nodoFttx;
    }
}