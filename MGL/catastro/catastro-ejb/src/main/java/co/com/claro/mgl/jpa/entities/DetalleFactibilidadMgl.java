/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import lombok.NoArgsConstructor;
import org.primefaces.model.map.Polygon;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author bocanegravm
 */
@Entity
@Table(name = "MGL_DETALLE_FACTIBILIDAD", schema = "MGL_SCHEME")
@XmlRootElement
@NoArgsConstructor
public class DetalleFactibilidadMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "DetalleFactibilidadMgl.DetalleFactibilidadMglSq",
            sequenceName = "MGL_SCHEME.MGL_DETALLE_FACTIBILIDAD_SQ", allocationSize = 1)
    @GeneratedValue(generator = "DetalleFactibilidadMgl.DetalleFactibilidadMglSq")
    @Column(name = "FACTIBILIDAD_DETALLE_ID", nullable = false)
    private BigDecimal factibilidadDetalleId;   
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACTIBILIDAD_ID", referencedColumnName = "FACTIBILIDAD_ID")
    private FactibilidadMgl factibilidadMglObj;

    @Column(name = "NOMBRE_TECNOLOGIA")
    private String nombreTecnologia;

    @Column(name = "SDS")
    private String sds;

    @Column(name = "CODIGO_NODO")
    private String codigoNodo;

    @Column(name = "CLASE_FACTIBILIDAD")
    private String claseFactibilidad;

    @Column(name = "NOMBRE_ARRENDATARIO")
    private String nombreArrendatario;

    @Column(name = "ESTADO_TECNOLOGIA")
    private String estadoTecnologia;
    
    @Column(name = "ESTADO_NODO")
    private String estadoNodo;
    
    @Column(name = "ES_HHPP")
    private String esHhpp;
    
    @Column(name = "TIEMPO_ULTIMA_MILLA")
    private int tiempoUltimaLilla;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SLA_EJECUCION_ID", referencedColumnName = "SLA_EJECUCION_ID")
    private SlaEjecucionMgl slaEjecucionMglObj;
    
    @Column(name = "COLOR_TEC")
    private String colorTecno;
    
    @Column(name = "USUARIO")
    private String usuario;
    
    @Column(name = "DISTANCIA_APROXIMADA")
    private int distanciaNodoApro;
    
    @Column(name = "PROYECTO")
    private String proyecto;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NODO_APROXIMADO", referencedColumnName = "NODO_ID")
    private NodoMgl nodoMglAproximado;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NODO_BACKUP", referencedColumnName = "NODO_ID")
    private NodoMgl nodoMglBackup;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ARRENDATARIO_ID", referencedColumnName = "ARRENDATARIO_ID")
    private ArrendatarioMgl arrendatarioMgl;
    
    @Column(name = "DETALLESLA")
    private String detallesSlas;

    /*Brief 98062  */
    @Column(name = "NODO_SITI_DATA")
    private String nodoSitiData;
    /*Cierre brief 98062*/
    
    @Column(name = "TIPO_SERVICIO")
    private String tipoServicio;
    
    @Column(name = "TIPO_SOLICITUD")
    private String tipoSolicitud;
    

    @Transient
    private boolean isChecked;
    
    @Transient
    private Polygon poligono;
    
    @Transient
    private boolean calculateNodeDistanceWs;

    /*  ---- Getters and Setters ----*/

    public BigDecimal getFactibilidadDetalleId() {
        return factibilidadDetalleId;
    }

    public void setFactibilidadDetalleId(BigDecimal factibilidadDetalleId) {
        this.factibilidadDetalleId = factibilidadDetalleId;
    }

    public FactibilidadMgl getFactibilidadMglObj() {
        return factibilidadMglObj;
    }

    public void setFactibilidadMglObj(FactibilidadMgl factibilidadMglObj) {
        this.factibilidadMglObj = factibilidadMglObj;
    }

    public String getNombreTecnologia() {
        return nombreTecnologia;
    }

    public void setNombreTecnologia(String nombreTecnologia) {
        this.nombreTecnologia = nombreTecnologia;
    }

    public String getSds() {
        return sds;
    }

    public void setSds(String sds) {
        this.sds = sds;
    }

    public String getCodigoNodo() {
        return codigoNodo;
    }

    public void setCodigoNodo(String codigoNodo) {
        this.codigoNodo = codigoNodo;
    }

    public String getClaseFactibilidad() {
        return claseFactibilidad;
    }

    public void setClaseFactibilidad(String claseFactibilidad) {
        this.claseFactibilidad = claseFactibilidad;
    }

    public String getNombreArrendatario() {
        return nombreArrendatario;
    }

    public void setNombreArrendatario(String nombreArrendatario) {
        this.nombreArrendatario = nombreArrendatario;
    }

    public String getEstadoTecnologia() {
        return estadoTecnologia;
    }

    public void setEstadoTecnologia(String estadoTecnologia) {
        this.estadoTecnologia = estadoTecnologia;
    }

    public String getEstadoNodo() {
        return estadoNodo;
    }

    public void setEstadoNodo(String estadoNodo) {
        this.estadoNodo = estadoNodo;
    }

    public String getEsHhpp() {
        return esHhpp;
    }

    public void setEsHhpp(String esHhpp) {
        this.esHhpp = esHhpp;
    }

    public boolean isIsChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public Polygon getPoligono() {
        return poligono;
    }

    public void setPoligono(Polygon poligono) {
        this.poligono = poligono;
    }

    public int getTiempoUltimaLilla() {
        return tiempoUltimaLilla;
    }

    public void setTiempoUltimaLilla(int tiempoUltimaLilla) {
        this.tiempoUltimaLilla = tiempoUltimaLilla;
    }

    public SlaEjecucionMgl getSlaEjecucionMglObj() {
        return slaEjecucionMglObj;
    }

    public void setSlaEjecucionMglObj(SlaEjecucionMgl slaEjecucionMglObj) {
        this.slaEjecucionMglObj = slaEjecucionMglObj;
    }

    public String getColorTecno() {
        return colorTecno;
    }

    public void setColorTecno(String colorTecno) {
        this.colorTecno = colorTecno;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getDistanciaNodoApro() {
        return distanciaNodoApro;
    }

    public void setDistanciaNodoApro(int distanciaNodoApro) {
        this.distanciaNodoApro = distanciaNodoApro;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public NodoMgl getNodoMglAproximado() {
        return nodoMglAproximado;
    }

    public void setNodoMglAproximado(NodoMgl nodoMglAproximado) {
        this.nodoMglAproximado = nodoMglAproximado;
    }

    public void setNodoMglAproximado(String nodoMglAproximado) {
        NodoMgl nodoMgl = new NodoMgl();
        nodoMgl.setNodCodigo(nodoMglAproximado);
        this.nodoMglAproximado = nodoMgl;
    }

    public NodoMgl getNodoMglBackup() {
        return nodoMglBackup;
    }

    public void setNodoMglBackup(NodoMgl nodoMglBackup) {
        this.nodoMglBackup = nodoMglBackup;
    }

    public ArrendatarioMgl getArrendatarioMgl() {
        return arrendatarioMgl;
    }

    public void setArrendatarioMgl(ArrendatarioMgl arrendatarioMgl) {
        this.arrendatarioMgl = arrendatarioMgl;
    }

    public String getDetallesSlas() {
        return detallesSlas;
    }

    public void setDetallesSlas(String detallesSlas) {
        this.detallesSlas = detallesSlas;
    }

    /* Brief 98062 */
    public String getNodoSitiData() {
        return nodoSitiData;
    }

    public void setNodoSitiData(String nodoSitiData) {
        this.nodoSitiData = nodoSitiData;
    }

    /* Cierre Brief 98062 */

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public boolean isCalculateNodeDistanceWs() {
        return calculateNodeDistanceWs;
    }

    public void setCalculateNodeDistanceWs(boolean calculateNodeDistanceWs) {
        this.calculateNodeDistanceWs = calculateNodeDistanceWs;
    }
}
