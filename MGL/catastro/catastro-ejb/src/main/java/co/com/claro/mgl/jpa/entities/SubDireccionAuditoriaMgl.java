/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juan David Hernandez
 */
@Entity
@Table(name = "MGL_SUB_DIRECCION$AUD", schema = "MGL_SCHEME")
@XmlRootElement
public class SubDireccionAuditoriaMgl implements Serializable  {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)  
    @Column(name = "SUB_DIRECCION_$AUD_ID", nullable = false)
    private BigDecimal sdiAuditoriaId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUB_DIRECCION_ID", nullable = false)
    private SubDireccionMgl subDireccionId;
  
    @Column(name = "DIRECCION_ID")
    private BigDecimal dirId;
    
    @Column(name = "FORMATO_IGAC")
    private String sdiFormatoIgac;
    
    @Column(name = "SERVINFORMACION")
    private String sdiServinformacion;
    
    @Column(name = "ESTRATO")
    private BigDecimal sdiEstrato;
    
    @Column(name = "NIVEL_SOCIOECONO")
    private BigDecimal sdiNivelSocioecono;
    
    @Column(name = "ACTIVIDAD_ECONO")
    private BigDecimal sdiActividadEcono;
    
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    
    @Column(name = "COMENTARIO_SOCIOECONOMICO")
    private String sdiComentarioSocioeconomico;
    
    @Column(name = "NODOUNO")
    private String sdiNodouno;
    
    @Column(name = "NODODOS")
    private String sdiNododos;
    
    @Column(name = "NODOTRES")
    private String sdiNodotres;
    
    @Column(name = "NODODTH")
    private String sdiNodoDth;
    
    @Column(name = "NODOMOVIL")
    private String sdiNodoMovil;
    
    @Column(name = "NODOFTTH")
    private String sdiNodoFtth;
    
    @Column(name = "NODOFTTX")
    private String sdiNodoFttx;
    
    @Column(name = "NODOWIFI")
    private String sdiNodoWifi;
    
    @Column(name = "ZONA_UNIFILAR")
    private String geoZonaUnifilar;
    @Column(name = "ZONA_GPON_DISENIADO")
    private String geoZonaGponDiseniado;
    @Column(name = "ZONA_MICRO_ONDAS")
    private String geoZonaMicroOndas;
    @Column(name = "ZONA_3G")
    private String geoZona3G;
    @Column(name = "ZONA_4G")
    private String geoZona4G;
    @Column(name = "ZONA_COBERTURA_CAVS")
    private String geoZonaCoberturaCavs;
    @Column(name = "ZONA_COBERTURA_ULTIMA_MILLA")
    private String geoZonaCoberturaUltimaMilla;
    @Column(name = "ZONA_CURRIER")
    private String geoZonaCurrier;
    @Column(name = "ZONA_5G")
    private String geoZona5G;        
    @Column(name = "CONFIABILIDAD")
    private BigDecimal sdirConfiabilidad;
    
    
    @Column(name = "FECHA_EDICION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    
    @Column(name = "FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    
    @Column(name = "AUD_ACTION",length = 3)
    private String accionAuditoria;
    
    @Column(name = "AUD_TIMESTAMP")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaAuditoria;
    
    @Column(name = "AUD_USER",length = 30)
    private String usuarioAuditoria;
    
    @Column(name = "SESSION_ID")
    private BigDecimal sesionAuditoria; 
    
    @Transient
    private CmtDireccionDetalladaMgl direccionDetallada;
    
    @Transient
    private String direccionTextoCompleta;

    public BigDecimal getSdiAuditoriaId() {
        return sdiAuditoriaId;
    }

    public void setSdiAuditoriaId(BigDecimal sdiAuditoriaId) {
        this.sdiAuditoriaId = sdiAuditoriaId;
    }

    public String getAccionAuditoria() {
        return accionAuditoria;
    }

    public void setAccionAuditoria(String accionAuditoria) {
        this.accionAuditoria = accionAuditoria;
    }

    public Date getFechaAuditoria() {
        return fechaAuditoria;
    }

    public void setFechaAuditoria(Date fechaAuditoria) {
        this.fechaAuditoria = fechaAuditoria;
    }

    public String getUsuarioAuditoria() {
        return usuarioAuditoria;
    }

    public void setUsuarioAuditoria(String usuarioAuditoria) {
        this.usuarioAuditoria = usuarioAuditoria;
    }

    public BigDecimal getSesionAuditoria() {
        return sesionAuditoria;
    }

    public void setSesionAuditoria(BigDecimal sesionAuditoria) {
        this.sesionAuditoria = sesionAuditoria;
    }

    public String getSdiFormatoIgac() {
        return sdiFormatoIgac;
    }

    public void setSdiFormatoIgac(String sdiFormatoIgac) {
        this.sdiFormatoIgac = sdiFormatoIgac;
    }  

    public String getSdiNodouno() {
        return sdiNodouno;
    }

    public void setSdiNodouno(String sdiNodouno) {
        this.sdiNodouno = sdiNodouno;
    }

    public String getSdiNododos() {
        return sdiNododos;
    }

    public void setSdiNododos(String sdiNododos) {
        this.sdiNododos = sdiNododos;
    }

    public String getSdiNodotres() {
        return sdiNodotres;
    }

    public void setSdiNodotres(String sdiNodotres) {
        this.sdiNodotres = sdiNodotres;
    }

    public String getSdiServinformacion() {
        return sdiServinformacion;
    }

    public void setSdiServinformacion(String sdiServinformacion) {
        this.sdiServinformacion = sdiServinformacion;
    }

    public BigDecimal getSdiEstrato() {
        return sdiEstrato;
    }

    public void setSdiEstrato(BigDecimal sdiEstrato) {
        this.sdiEstrato = sdiEstrato;
    }

    public BigDecimal getSdiNivelSocioecono() {
        return sdiNivelSocioecono;
    }

    public void setSdiNivelSocioecono(BigDecimal sdiNivelSocioecono) {
        this.sdiNivelSocioecono = sdiNivelSocioecono;
    }

    public BigDecimal getSdiActividadEcono() {
        return sdiActividadEcono;
    }

    public void setSdiActividadEcono(BigDecimal sdiActividadEcono) {
        this.sdiActividadEcono = sdiActividadEcono;
    }

    public String getSdiComentarioSocioeconomico() {
        return sdiComentarioSocioeconomico;
    }

    public void setSdiComentarioSocioeconomico(String sdiComentarioSocioeconomico) {
        this.sdiComentarioSocioeconomico = sdiComentarioSocioeconomico;
    }

    /**
     * Get sdiNodoDth
     *
     * @return String sdiNodoDth
     */
    public String getSdiNodoDth() {
        return sdiNodoDth;
    }

    /**
     * Set sdiNodoDth
     *
     * @param sdiNodoDth String
     */
    public void setSdiNodoDth(String sdiNodoDth) {
        this.sdiNodoDth = sdiNodoDth;
    }

    public String getSdiNodoMovil() {
        return sdiNodoMovil;
    }

    public void setSdiNodoMovil(String sdiNodoMovil) {
        this.sdiNodoMovil = sdiNodoMovil;
    }

    public String getSdiNodoFtth() {
        return sdiNodoFtth;
    }

    public void setSdiNodoFtth(String sdiNodoFtth) {
        this.sdiNodoFtth = sdiNodoFtth;
    }

    public String getSdiNodoFttx() {
        return sdiNodoFttx;
    }

    public void setSdiNodoFttx(String sdiNodoFttx) {
        this.sdiNodoFttx = sdiNodoFttx;
    }

    public String getSdiNodoWifi() {
        return sdiNodoWifi;
    }

    public void setSdiNodoWifi(String sdiNodoWifi) {
        this.sdiNodoWifi = sdiNodoWifi;
    }

   
    @Override
    public String toString() {
        return sdiFormatoIgac;
    }

    public String getDireccionTextoCompleta() {
        return direccionTextoCompleta;
    }

    public void setDireccionTextoCompleta(String direccionTextoCompleta) {
        this.direccionTextoCompleta = direccionTextoCompleta;
    }  

    public BigDecimal getDirId() {
        return dirId;
    }

    public void setDirId(BigDecimal dirId) {
        this.dirId = dirId;
    }

    public SubDireccionMgl getSubDireccionId() {
        return subDireccionId;
    }

    public void setSubDireccionId(SubDireccionMgl subDireccionId) {
        this.subDireccionId = subDireccionId;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    public String getUsuarioEdicion() {
        return usuarioEdicion;
    }

    public void setUsuarioEdicion(String usuarioEdicion) {
        this.usuarioEdicion = usuarioEdicion;
    }

    //Se utiliza este GET para la auditoria del object
    public BigDecimal getSdiId() {
        return subDireccionId.getSdiId();
    }

    public CmtDireccionDetalladaMgl getDireccionDetallada() {
        return direccionDetallada;
    }

    public void setDireccionDetallada(CmtDireccionDetalladaMgl direccionDetallada) {
        this.direccionDetallada = direccionDetallada;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

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
    
    public BigDecimal getSdirConfiabilidad() {
        return sdirConfiabilidad;
    }

    public void setSdirConfiabilidad(BigDecimal sdirConfiabilidad) {
        this.sdirConfiabilidad = sdirConfiabilidad;
    }


    
}
