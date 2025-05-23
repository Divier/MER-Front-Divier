/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
    @Entity
@Table(name = "MGL_DIRECCION", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DireccionMgl.findAll", query = "SELECT s FROM DireccionMgl s"),
    @NamedQuery(name = "DireccionMgl.findByDirId", query = "SELECT a FROM DireccionMgl a where a.dirId = :dirId")})
public class DireccionMgl implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "DireccionMgl.direccionseq",
            sequenceName = "MGL_SCHEME.MGL_DIRECCION_SQ", allocationSize = 1)
    @GeneratedValue(generator = "DireccionMgl.direccionseq")
    @Column(name = "DIRECCION_ID", nullable = false)
    private BigDecimal dirId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPO_DIRECCION_ID")
    private TipoDireccionMgl tipoDirObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UBICACION_ID")
    private UbicacionMgl ubicacion;
    @Column(name = "FORMATO_IGAC")
    private String dirFormatoIgac;
    @Column(name = "SERV_INFORMACION")
    private String dirServinformacion;
    @Column(name = "NO_STANDAR")
    private String dirNostandar;
    @Column(name = "ORIGEN")
    private String dirOrigen;
    @Column(name = "CONFIABILIDAD")
    private BigDecimal dirConfiabilidad;
    @Column(name = "ESTRATO")
    private BigDecimal dirEstrato;
    @Column(name = "NIVEL_SOCIOECONOMICO")
    private BigDecimal dirNivelSocioecono;
    @Column(name = "LOCALIDAD")
    private String dirLocalidad;
    @Column(name = "MANZANA_CATASTRAL")
    private String dirManzanaCatastral;
    @Column(name = "MANZANA")
    private String dirManzana;
    @Column(name = "FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Column(name = "FECHA_EDICION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    @Column(name = "REVISAR_DIRECCION")
    private String dirRevisar;
    @Column(name = "ACTIVIDAD_ECONOMICA")
    private String dirActividadEcono;
    @Column(name = "COMENTARIO_SOCIOECONOMICO")
    private String dirComentarioSocioeconomico;
    @Column(name = "NODO_UNO")
    private String dirNodouno;
    @Column(name = "NODO_DOS")
    private String dirNododos;
    @Column(name = "NODO_TRES")
    private String dirNodotres;
    @Column(name = "NODO_DTH")
    private String dirNodoDth;
    @Column(name = "NODO_MOVIL")
    private String dirNodoMovil;
    @Column(name = "NODO_FTTH")
    private String dirNodoFtth;
    @Column(name = "NODOFTTX")
    private String dirNodoFttx;
    @Column(name = "NODO_WIFI")
    private String dirNodoWifi;
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
    
    
    
    @Transient
    private boolean seleccionado;
    @Transient
    private boolean selected = false;
    
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "direccion")
    private CmtDireccionDetalladaMgl direccionDetallada;
    @Transient
    private String direccionTextoCompleta;
    
    @OneToMany(mappedBy = "direccionId", fetch = FetchType.LAZY)
    List<DireccionAuditoriaMgl> listAuditoria;
    
    /**
     * @return the dirId
     */
    public BigDecimal getDirId() {
        return dirId;
    }

    /**
     * @param dirId the dirId to set
     */
    public void setDirId(BigDecimal dirId) {
        this.dirId = dirId;
    }

    public TipoDireccionMgl getTipoDirObj() {
        return tipoDirObj;
    }

    public void setTipoDirObj(TipoDireccionMgl tipoDirObj) {
        this.tipoDirObj = tipoDirObj;
    }

   
    /**
     * @return the ubiId
     */


    /**
     * @return the dirFormatoIgac
     */
    public String getDirFormatoIgac() {
        return dirFormatoIgac;
    }

    /**
     * @param dirFormatoIgac the dirFormatoIgac to set
     */
    public void setDirFormatoIgac(String dirFormatoIgac) {
        this.dirFormatoIgac = dirFormatoIgac;
    }

    /**
     * @return the dirServinformacion
     */
    public String getDirServinformacion() {
        return dirServinformacion;
    }

    /**
     * @param dirServinformacion the dirServinformacion to set
     */
    public void setDirServinformacion(String dirServinformacion) {
        this.dirServinformacion = dirServinformacion;
    }

    /**
     * @return the dirNostandar
     */
    public String getDirNostandar() {
        return dirNostandar;
    }

    /**
     * @param dirNostandar the dirNostandar to set
     */
    public void setDirNostandar(String dirNostandar) {
        this.dirNostandar = dirNostandar;
    }

    /**
     * @return the dirOrigen
     */
    public String getDirOrigen() {
        return dirOrigen;
    }

    /**
     * @param dirOrigen the dirOrigen to set
     */
    public void setDirOrigen(String dirOrigen) {
        this.dirOrigen = dirOrigen;
    }

    /**
     * @return the dirConfiabilidad
     */
    public BigDecimal getDirConfiabilidad() {
        return dirConfiabilidad;
    }

    /**
     * @param dirConfiabilidad the dirConfiabilidad to set
     */
    public void setDirConfiabilidad(BigDecimal dirConfiabilidad) {
        this.dirConfiabilidad = dirConfiabilidad;
    }

    /**
     * @return the dirEstrato
     */
    public BigDecimal getDirEstrato() {
        return dirEstrato;
    }

    /**
     * @param dirEstrato the dirEstrato to set
     */
    public void setDirEstrato(BigDecimal dirEstrato) {
        this.dirEstrato = dirEstrato;
    }

    /**
     * @return the dirNivelSocioecono
     */
    public BigDecimal getDirNivelSocioecono() {
        return dirNivelSocioecono;
    }

    /**
     * @param dirNivelSocioecono the dirNivelSocioecono to set
     */
    public void setDirNivelSocioecono(BigDecimal dirNivelSocioecono) {
        this.dirNivelSocioecono = dirNivelSocioecono;
    }

    /**
     * @return the dirLocalidad
     */
    public String getDirLocalidad() {
        return dirLocalidad;
    }

    /**
     * @param dirLocalidad the dirLocalidad to set
     */
    public void setDirLocalidad(String dirLocalidad) {
        this.dirLocalidad = dirLocalidad;
    }

    /**
     * @return the dirManzanaCatastral
     */
    public String getDirManzanaCatastral() {
        return dirManzanaCatastral;
    }

    /**
     * @param dirManzanaCatastral the dirManzanaCatastral to set
     */
    public void setDirManzanaCatastral(String dirManzanaCatastral) {
        this.dirManzanaCatastral = dirManzanaCatastral;
    }

    /**
     * @return the dirManzana
     */
    public String getDirManzana() {
        return dirManzana;
    }

    /**
     * @param dirManzana the dirManzana to set
     */
    public void setDirManzana(String dirManzana) {
        this.dirManzana = dirManzana;
    }

       /**
     * @return the dirRevisar
     */
    public String getDirRevisar() {
        return dirRevisar;
    }

    /**
     * @param dirRevisar the dirRevisar to set
     */
    public void setDirRevisar(String dirRevisar) {
        this.dirRevisar = dirRevisar;
    }

    /**
     * @return the dirActividadEcono
     */
    public String getDirActividadEcono() {
        return dirActividadEcono;
    }

    /**
     * @param dirActividadEcono the dirActividadEcono to set
     */
    public void setDirActividadEcono(String dirActividadEcono) {
        this.dirActividadEcono = dirActividadEcono;
    }

    /**
     * @return the dirComentarioSocioeconomico
     */
    public String getDirComentarioSocioeconomico() {
        return dirComentarioSocioeconomico;
    }

    /**
     * @param dirComentarioSocioeconomico the dirComentarioSocioeconomico to set
     */
    public void setDirComentarioSocioeconomico(String dirComentarioSocioeconomico) {
        this.dirComentarioSocioeconomico = dirComentarioSocioeconomico;
    }

    /**
     * @return the dirNodouno
     */
    public String getDirNodouno() {
        return dirNodouno;
    }

    /**
     * @param dirNodouno the dirNodouno to set
     */
    public void setDirNodouno(String dirNodouno) {
        this.dirNodouno = dirNodouno;
    }

    /**
     * @return the dirNododos
     */
    public String getDirNododos() {
        return dirNododos;
    }

    /**
     * @param dirNododos the dirNododos to set
     */
    public void setDirNododos(String dirNododos) {
        this.dirNododos = dirNododos;
    }

    /**
     * @return the dirNodotres
     */
    public String getDirNodotres() {
        return dirNodotres;
    }

    /**
     * @param dirNodotres the dirNodotres to set
     */
    public void setDirNodotres(String dirNodotres) {
        this.dirNodotres = dirNodotres;
    }

    /**
     * @return the seleccionado
     */
    public boolean isSeleccionado() {
        return seleccionado;
    }

    /**
     * @param seleccionado the seleccionado to set
     */
    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    /**
     * Get nodoDTH
     * 
     * @return String dirNodoDth
     */
    public String getDirNodoDth() {
        return dirNodoDth;
    }

    /*
     * Set nodoDTH
     * 
     * @param String dirNodoDth
     */
    public void setDirNodoDth(String dirNodoDth) {
        this.dirNodoDth = dirNodoDth;
    }

    public String getDirNodoMovil() {
        return dirNodoMovil;
    }

    public void setDirNodoMovil(String dirNodoMovil) {
        this.dirNodoMovil = dirNodoMovil;
    }

    public String getDirNodoFtth() {
        return dirNodoFtth;
    }

    public void setDirNodoFtth(String dirNodoFtth) {
        this.dirNodoFtth = dirNodoFtth;
    }

    public String getDirNodoWifi() {
        return dirNodoWifi;
    }

    public void setDirNodoWifi(String dirNodoWifi) {
        this.dirNodoWifi = dirNodoWifi;
    }

    @Override
    public String toString() {
        return dirNostandar;
    }

    /**
     * @return the ubicacion
     */
    public UbicacionMgl getUbicacion() {
        return ubicacion;
    }

    /**
     * @param ubicacion the ubicacion to set
     */
    public void setUbicacion(UbicacionMgl ubicacion) {
        this.ubicacion = ubicacion;
    }

    public CmtDireccionDetalladaMgl getDireccionDetallada() {
        return direccionDetallada;
    }

    public void setDireccionDetallada(CmtDireccionDetalladaMgl direccionDetallada) {
        this.direccionDetallada = direccionDetallada;
    }

    public String getDireccionTextoCompleta() {
        return direccionTextoCompleta;
    }

    public void setDireccionTextoCompleta(String direccionTextoCompleta) {
        this.direccionTextoCompleta = direccionTextoCompleta;
    }   
    
    
    @Override
    public DireccionMgl clone() throws CloneNotSupportedException {
        return (DireccionMgl) super.clone();
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public List<DireccionAuditoriaMgl> getListAuditoria() {
        return listAuditoria;
    }

    public void setListAuditoria(List<DireccionAuditoriaMgl> listAuditoria) {
        this.listAuditoria = listAuditoria;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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
    public String getDirNodoFttx() {
        return dirNodoFttx;
    }
    public void setDirNodoFttx(String dirNodoFttx) {
        this.dirNodoFttx = dirNodoFttx;
    }
}
