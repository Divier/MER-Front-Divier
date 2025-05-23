/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Admin
 */
@Getter
@Setter
@Entity
@Table(name = "TEC_NODO", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NodoMgl.findAll", query = "SELECT s FROM NodoMgl s WHERE  s.estadoRegistro = 1"),
    @NamedQuery(name = "NodoMgl.findAllByNodTipoByGpoId", query = "SELECT s FROM NodoMgl s "
            + " WHERE s.nodTipo = :nodTipo AND s.gpoId = :gpoId AND s.estadoRegistro = 1"),
    @NamedQuery(name = "NodoMgl.findAllByNodTipoByGpoIdByNodCodigo", query = "SELECT s FROM NodoMgl s "
            + " WHERE s.nodTipo = :nodTipo AND s.gpoId = :gpoId AND s.nodCodigo = :nodCodigo AND s.estadoRegistro = 1"),
    @NamedQuery(name = "NodoMgl.findCodCityByDivArea", query = "SELECT DISTINCT(n.gpoId) FROM NodoMgl n "
            + " WHERE n.divId = :divId and n.areId = :areId AND n.estadoRegistro = 1"),
    @NamedQuery(name = "NodoMgl.findByCodNodIdCentroPobladoIdTec", query = "SELECT n FROM NodoMgl n "
            + " WHERE UPPER (n.nodCodigo) = :codigo AND n.gpoId = :idCentroPoblado  "
            + " AND n.nodTipo.basicaId = :idTecnologia AND n.estadoRegistro = 1"),
    @NamedQuery(name = "NodoMgl.findByCodNodId", query = "SELECT n FROM NodoMgl n "
            + " WHERE n.nodId = :id  AND n.estadoRegistro = 1"),
    @NamedQuery(name = "NodoMgl.findByCodigoAndComunidadRR", query = "SELECT n FROM NodoMgl n "
            + " WHERE UPPER (n.nodCodigo) = :codigo and n.comId = :comId AND n.estadoRegistro = 1")})
public class NodoMgl implements Serializable, Cloneable {

    private static final Logger LOGGER = LogManager.getLogger(NodoMgl.class);

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "NodoMgl.seq_nodo",
            sequenceName = "MGL_SCHEME.TEC_NODO_SQ", allocationSize = 1
    )
    @GeneratedValue(generator = "NodoMgl.seq_nodo")
    @Column(name = "NODO_ID", nullable = false)
    private BigDecimal nodId;
    @Column(name = "GEOGRAFICO_POLITICO_ID", nullable = false)
    private BigDecimal gpoId;
    @Column(name = "NOMBRE")
    private String nodNombre;
    @Column(name = "CODIGO")
    private String nodCodigo;
    @Column(name = "FECHA_ACTIVACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date nodFechaActivacion;
    @NotNull
    @JoinColumn(name = "UNIDAD_GESTION_BASICA_ID", referencedColumnName = "BASICA_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private CmtBasicaMgl ugeId;
    @NotNull
    @JoinColumn(name = "ZONA_BASICA_ID", referencedColumnName = "BASICA_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private CmtBasicaMgl zonId;
    @NotNull
    @JoinColumn(name = "DISTRITO_BASICA_ID", referencedColumnName = "BASICA_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private CmtBasicaMgl disId;
    @NotNull
    @JoinColumn(name = "DIVISIONAL_BASICA_ID", referencedColumnName = "BASICA_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private CmtBasicaMgl divId;
    @NotNull
    @JoinColumn(name = "AREA_BASICA_ID", referencedColumnName = "BASICA_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private CmtBasicaMgl areId;

    @Column(name = "FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date nodFechaCreacion;
    @Column(name = "USUARIO_CREACION")
    private String nodUsuarioCreacion;
    @Column(name = "FECHA_MODIFICACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date nodFechaModificacion;
    @Column(name = "USUARIO_MODIFICACION")
    private String nodUsuarioModificacion;
    @Column(name = "HEAD_END")
    private String nodHeadEnd;
    @Column(name = "CAMPO_ADICIONAL1")
    private String nodCampoAdicional1;
    @Column(name = "CAMPO_ADICIONAL2")
    private String nodCampoAdicional2;
    @Column(name = "CAMPO_ADICIONAL3")
    private String nodCampoAdicional3;
    @Column(name = "CAMPO_ADICIONAL4")
    private String nodCampoAdicional4;
    @Column(name = "CAMPO_ADICIONAL5")
    private String nodCampoAdicional5;

    @NotNull
    @JoinColumn(name = "TIPO", referencedColumnName = "BASICA_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private CmtBasicaMgl nodTipo;

    @JoinColumn(name = "COM_ID", referencedColumnName = "COMUNIDAD_RR_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private CmtComunidadRr comId;

    @JoinColumn(name = "ESTADO", referencedColumnName = "BASICA_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private CmtBasicaMgl estado;

    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;

    @JoinColumn(name = "ALIADO_ID", referencedColumnName = "BASICA_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private CmtBasicaMgl aliadoId;

    @Column(name = "COSTO_RED")
    private BigDecimal costoRed;
    @Column(name = "LIMITES")
    private String limites;
    @Column(name = "FACTIBILIDAD")
    private String factibilidad;
    
    @Column(name = "PROYECTO")
    private String proyecto;

    @Column(name = "ANCHO_BAN_RETO")
    private String anchoBanda;
    
    @Column(name = "OPERA")
    private String opera;
    
    @Column(name = "OLT")
    private String olt;
    
    @Column(name = "OLT_NODO")
    private String oltNodo;
    
    @Column(name = "OT")
    private String ot;


    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }
    @Transient
    private boolean selected = false;
    @Transient
    private boolean sincronizaRr; //valbuenayf ajuste checkbox 
    @Transient
    private String direccionStr;

    public BigDecimal getNodId() {
        return nodId;
    }

    public void setNodId(BigDecimal nodId) {
        this.nodId = nodId;
    }

    public BigDecimal getGpoId() {
        return gpoId;
    }

    public void setGpoId(BigDecimal gpoId) {
        this.gpoId = gpoId;
    }

    public String getNodNombre() {
        return nodNombre;
    }

    public void setNodNombre(String nodNombre) {
        this.nodNombre = nodNombre;
    }

    public String getNodCodigo() {
        return nodCodigo;
    }

    public void setNodCodigo(String nodCodigo) {
        this.nodCodigo = nodCodigo;
    }

    public Date getNodFechaActivacion() {
        return nodFechaActivacion;
    }

    public void setNodFechaActivacion(Date nodFechaActivacion) {
        this.nodFechaActivacion = nodFechaActivacion;
    }

    public CmtBasicaMgl getUgeId() {
        return ugeId;
    }

    public void setUgeId(CmtBasicaMgl ugeId) {
        this.ugeId = ugeId;
    }

    public CmtBasicaMgl getZonId() {
        return zonId;
    }

    public void setZonId(CmtBasicaMgl zonId) {
        this.zonId = zonId;
    }

    public CmtBasicaMgl getDisId() {
        return disId;
    }

    public void setDisId(CmtBasicaMgl disId) {
        this.disId = disId;
    }

    public CmtBasicaMgl getDivId() {
        return divId;
    }

    public void setDivId(CmtBasicaMgl divId) {
        this.divId = divId;
    }

    public CmtBasicaMgl getAreId() {
        return areId;
    }

    public void setAreId(CmtBasicaMgl areId) {
        this.areId = areId;
    }

    public Date getNodFechaCreacion() {
        return nodFechaCreacion;
    }

    public void setNodFechaCreacion(Date nodFechaCreacion) {
        this.nodFechaCreacion = nodFechaCreacion;
    }

    public String getNodUsuarioCreacion() {
        return nodUsuarioCreacion;
    }

    public void setNodUsuarioCreacion(String nodUsuarioCreacion) {
        this.nodUsuarioCreacion = nodUsuarioCreacion;
    }

    public Date getNodFechaModificacion() {
        return nodFechaModificacion;
    }

    public void setNodFechaModificacion(Date nodFechaModificacion) {
        this.nodFechaModificacion = nodFechaModificacion;
    }

    public String getNodUsuarioModificacion() {
        return nodUsuarioModificacion;
    }

    public void setNodUsuarioModificacion(String nodUsuarioModificacion) {
        this.nodUsuarioModificacion = nodUsuarioModificacion;
    }

    public String getNodHeadEnd() {
        return nodHeadEnd;
    }

    public void setNodHeadEnd(String nodHeadEnd) {
        this.nodHeadEnd = nodHeadEnd;
    }

    public String getNodCampoAdicional1() {
        return nodCampoAdicional1;
    }

    public void setNodCampoAdicional1(String nodCampoAdicional1) {
        this.nodCampoAdicional1 = nodCampoAdicional1;
    }

    public String getNodCampoAdicional2() {
        return nodCampoAdicional2;
    }

    public void setNodCampoAdicional2(String nodCampoAdicional2) {
        this.nodCampoAdicional2 = nodCampoAdicional2;
    }

    public String getNodCampoAdicional3() {
        return nodCampoAdicional3;
    }

    public void setNodCampoAdicional3(String nodCampoAdicional3) {
        this.nodCampoAdicional3 = nodCampoAdicional3;
    }

    public String getNodCampoAdicional4() {
        return nodCampoAdicional4;
    }

    public void setNodCampoAdicional4(String nodCampoAdicional4) {
        this.nodCampoAdicional4 = nodCampoAdicional4;
    }

    public String getNodCampoAdicional5() {
        return nodCampoAdicional5;
    }

    public void setNodCampoAdicional5(String nodCampoAdicional5) {
        this.nodCampoAdicional5 = nodCampoAdicional5;
    }

    public CmtBasicaMgl getNodTipo() {
        return nodTipo;
    }

    public void setNodTipo(CmtBasicaMgl nodTipo) {
        this.nodTipo = nodTipo;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public CmtBasicaMgl getEstado() {
        return estado;
    }

    public void setEstado(CmtBasicaMgl estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "" + nodCodigo;
    }

    public int getPerfilCreacion() {
        return perfilCreacion;
    }

    public void setPerfilCreacion(int perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
    }

    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    public CmtBasicaMgl getAliadoId() {
        return aliadoId;
    }

    public void setAliadoId(CmtBasicaMgl aliadoId) {
        this.aliadoId = aliadoId;
    }

    public String getDireccionStr() {
        return direccionStr;
    }

    public void setDireccionStr(String direccionStr) {
        this.direccionStr = direccionStr;
    }

    public BigDecimal getCostoRed() {
        return costoRed;
    }

    public void setCostoRed(BigDecimal costoRed) {
        this.costoRed = costoRed;
    }

    public String getLimites() {
        return limites;
    }

    public void setLimites(String limites) {
        this.limites = limites;
    }

    @Override
    public NodoMgl clone() throws CloneNotSupportedException {
        NodoMgl obj = null;
        try {
            obj = (NodoMgl) super.clone();
        } catch (CloneNotSupportedException ex) {
			String msg = "Se produjo un error al momento de ejecutar el método '"+
			ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			LOGGER.error(msg);
            throw ex;
        }

        return obj;
    }

    public CmtComunidadRr getComId() {
        return comId;
    }

    public void setComId(CmtComunidadRr comId) {
        this.comId = comId;
    }

    public boolean isSincronizaRr() {
        return sincronizaRr;
    }

    public void setSincronizaRr(boolean sincronizaRr) {
        this.sincronizaRr = sincronizaRr;
    }

    public String getFactibilidad() {
        return factibilidad;
    }

    public void setFactibilidad(String factibilidad) {
        this.factibilidad = factibilidad;
    }
    
     public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    /**
     * get field @Column(name = "ANCHO_BAN_RETO")
     *
     * @return anchoBanda @Column(name = "ANCHO_BAN_RETO")

     */
    public String getAnchoBanda() {
        return this.anchoBanda;
    }

    /**
     * set field @Column(name = "ANCHO_BAN_RETO")
     *
     * @param anchoBanda @Column(name = "ANCHO_BAN_RETO")

     */
    public void setAnchoBanda(String anchoBanda) {
        this.anchoBanda = anchoBanda;
    }
    
}
