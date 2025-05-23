package co.com.claro.mgl.jpa.entities.cm;

import co.com.claro.mgl.jpa.entities.NodoMgl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "MGL_BASICA", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtBasicaMgl.findByBasicaId", query = "SELECT c FROM CmtBasicaMgl c WHERE c.basicaId = :codigoBasica  and  c.estadoRegistro=1"),
    @NamedQuery(name = "CmtBasicaMgl.findAll", query = "SELECT c FROM CmtBasicaMgl c WHERE c.estadoRegistro=1"),
    @NamedQuery(name = "CmtBasicaMgl.findByTipoBasicaEstado1", query = "SELECT c FROM CmtBasicaMgl c WHERE c.tipoBasicaObj = :tipoBasicaObj AND c.estadoRegistro=1 AND c.estadoRegistro=1 ORDER BY c.nombreBasica ASC"),
    @NamedQuery(name = "CmtBasicaMgl.findByTipoBasica", query = "SELECT c FROM CmtBasicaMgl c WHERE c.tipoBasicaObj = :tipoBasicaObj AND c.estadoRegistro=1 AND c.activado='Y' ORDER BY c.nombreBasica ASC"),
    @NamedQuery(name = "CmtBasicaMgl.findByTipoBasicaByOrderRR", query = "SELECT c FROM CmtBasicaMgl c WHERE c.tipoBasicaObj = :tipoBasicaObj AND c.estadoRegistro=1 ORDER BY c.nombreBasica ASC"),
     @NamedQuery(name = "CmtBasicaMgl.findByCodigoBasicaList", query = "SELECT c FROM CmtBasicaMgl c WHERE c.tipoBasicaObj = :tipoBasicaObj AND c.estadoRegistro=1 AND c.activado='Y' AND c.codigoBasica IN :codigoBasicaList ORDER BY c.nombreBasica ASC"),
    @NamedQuery(name = "CmtBasicaMgl.findByTipoBasicaId", query = "SELECT c FROM CmtBasicaMgl c WHERE c.tipoBasicaObj.tipoBasicaId = :tipoBasicaId AND c.estadoRegistro=1 AND c.activado='Y' ORDER BY c.nombreBasica ASC"),
    @NamedQuery(name = "CmtBasicaMgl.findByTipoBasicaAndAbreviatura", query = "SELECT c FROM CmtBasicaMgl c WHERE c.tipoBasicaObj.tipoBasicaId = :tipoBasicaid and c.abreviatura= :abreviatura and c.estadoRegistro=1 "),
    @NamedQuery(name = "CmtBasicaMgl.findBynombre", query = "SELECT c FROM CmtBasicaMgl c WHERE c.tipoBasicaObj.tipoBasicaId = :tipoBasica and c.estadoRegistro=1 AND c.nombreBasica = :nombre"),
    @NamedQuery(name = "CmtBasicaMgl.findByTipoBasicaAndLikeNombre", query = "SELECT c FROM CmtBasicaMgl c WHERE c.tipoBasicaObj.tipoBasicaId = :tipoBasica and c.estadoRegistro=1 AND c.nombreBasica LIKE :nombre"),
    @NamedQuery(name = "CmtBasicaMgl.findByTipoBasicaAndCodigo", query = "SELECT c FROM CmtBasicaMgl c WHERE c.tipoBasicaObj.tipoBasicaId = :tipoBasica and c.estadoRegistro=1 AND c.codigoBasica = :codigoBasica"),
    @NamedQuery(name = "CmtBasicaMgl.findByBasicaCode", query = "SELECT c FROM CmtBasicaMgl c WHERE c.codigoBasica = :codigoBasica and c.estadoRegistro=1 and c.tipoBasicaObj.tipoBasicaId = :tipoBasicaId"),
    @NamedQuery(name = "CmtBasicaMgl.findGrupoProyectoBasicaList", query = "SELECT c FROM CmtBasicaMgl c WHERE c.tipoBasicaObj.tipoBasicaId = :idGrupoProyectoBasica and c.estadoRegistro =:estado "),
    @NamedQuery(name = "CmtBasicaMgl.findProyectoBasicaList", query = "SELECT c FROM CmtBasicaMgl c WHERE c.tipoBasicaObj.tipoBasicaId = :idProyectoBasica and c.estadoRegistro=:estado "),
    @NamedQuery(name = "CmtBasicaMgl.findBynombreBasicaTipo",
            query = "SELECT ba FROM CmtBasicaMgl ba INNER JOIN  ba.tipoBasicaObj c "
            + "WHERE UPPER(c.identificadorInternoApp) LIKE :identificadorInternoApp "
            + "AND UPPER(ba.abreviatura) LIKE :codigoBasica "
            + "AND ba.estadoRegistro=1 ORDER BY ba.basicaId DESC"),
    @NamedQuery(name = "CmtBasicaMgl.findBynombreBasicaTipoCode",
            query = "SELECT ba FROM CmtBasicaMgl ba INNER JOIN  ba.tipoBasicaObj c "
            + "WHERE UPPER(c.nombreTipo) LIKE :nombreTipoBasica "
            + "AND UPPER(ba.codigoBasica) LIKE :codigoBasica "
            + "AND ba.estadoRegistro=1 ORDER BY ba.basicaId DESC"),
    @NamedQuery(name = "CmtBasicaMgl.findProyectoBasicaList", query = "SELECT c FROM CmtBasicaMgl c WHERE c.tipoBasicaObj.tipoBasicaId = :idProyectoBasica and c.estadoRegistro=:estado "),
    @NamedQuery(name = "CmtBasicaMgl.findByCodigoInternoApp",query = "SELECT c FROM CmtBasicaMgl c WHERE c.identificadorInternoApp = :codigoInternoApp AND c.estadoRegistro=1 "),
    @NamedQuery(name = "CmtBasicaMgl.findByCodigoInternoAppAndCodigo",query = "SELECT c FROM CmtBasicaMgl c WHERE c.identificadorInternoApp = :codigoInternoApp AND c.codigoBasica = :codigoBasica AND c.estadoRegistro=1 "),
    @NamedQuery(name = "CmtBasicaMgl.findByPrefijo",query = "SELECT c FROM CmtBasicaMgl c WHERE c.nombreBasica LIKE :prefijo AND c.tipoBasicaObj.tipoBasicaId =:tipoBasicaId AND c.estadoRegistro = 1 "),
    @NamedQuery(name = "CmtBasicaMgl.findByNombreLike", query = "SELECT c FROM CmtBasicaMgl c "
             + "WHERE c.estadoRegistro = 1 AND UPPER(c.nombreBasica) LIKE :nombreBasica")
})
public class CmtBasicaMgl implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtBasicaMgl.CmtBasicaMglSeq",
            sequenceName = "MGL_SCHEME.MGL_BASICA_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtBasicaMgl.CmtBasicaMglSeq")
    @Column(name = "BASICA_ID", nullable = false)
    private BigDecimal basicaId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPO_BASICA_ID")
    private CmtTipoBasicaMgl tipoBasicaObj;
    @NotNull
    @Column(name = "CODIGO_BASICA", length = 15)
    private String codigoBasica;
    @Basic(optional = false)
    @Column(name = "ABREVIATURA")
    private String abreviatura;
    @Basic(optional = false)
    @Column(name = "NOMBRE_BASICA", nullable = true, length = 100)
    private String nombreBasica;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION", nullable = true, length = 500)
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @NotNull
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @NotNull
    @Column(name = "USUARIO_CREACION", nullable = true, length = 200)
    private String usuarioCreacion;
    @NotNull
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "FECHA_EDICION", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION", nullable = true, length = 200)
    private String usuarioEdicion;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @OneToMany(mappedBy = "basicaObj", fetch = FetchType.LAZY)
    private List<CmtBasicaAuditoriaMgl> listAuditoria;
    @Column(name = "ACTIVADO")
    private String activado;
    @Column(name = "JUSTIFICACION")
    private String justificacion;
    @OneToMany(mappedBy = "idBasicaObj", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CmtBasicaExtMgl> listCmtBasicaExtMgl;
    @NotNull
    @Column(name = "IDENTIFICADOR_INTERNO_APP")
    private String identificadorInternoApp;
    @Transient
    private boolean activacion;
    @Transient
    private BigDecimal idEstadosxTec;
    @Transient
    private List<CmtBasicaMgl> moduloSelected;
    @Transient
    private List<CmtBasicaMgl> modulo;
    @Transient
    private boolean nodoTecnologia;
    //valbuenayf inicio ajuste checkbox 
    @Transient
    private String nodoCobertura;
    @Transient
    private String nodoGestion;
    @Transient
    private boolean sincronizaRr;
    //valbuenayf fin ajuste checkbox 
    @Transient
    private String nodoTecnologiaBasica;
    @Transient 
    private List<NodoMgl> nodoCercanoList;
    @Transient 
    private String nodoCercano;
    @Transient 
    private boolean tecnologiaProcesada;
    @Transient 
    private NodoMgl nodoMgl;
    @Transient 
    private String napCercana;
    @Transient 
    private String nap;
    @Transient
    private boolean napFTTH;
    
 
    
    public List<CmtBasicaMgl> remover(List<CmtBasicaMgl> lista) {
        int i = 0;

        for (CmtBasicaMgl bas : lista) {
            if (bas.basicaId.compareTo(this.basicaId) == 0) {
                lista.remove(i);
                return lista;
            }
            i++;
        }
        return lista;
    }

    /**
     * @return the basicaId
     */
    public BigDecimal getBasicaId() {
        return basicaId;
    }

    /**
     * @param basicaId the basicaId to set
     */
    public void setBasicaId(BigDecimal basicaId) {
        this.basicaId = basicaId;
    }

    /**
     * @return the tipoBasicaObj
     */
    public CmtTipoBasicaMgl getTipoBasicaObj() {
        return tipoBasicaObj;
    }

    /**
     * @param tipoBasicaObj the tipoBasicaObj to set
     */
    public void setTipoBasicaObj(CmtTipoBasicaMgl tipoBasicaObj) {
        this.tipoBasicaObj = tipoBasicaObj;
    }

    public String getCodigoBasica() {
        return codigoBasica;
    }

    public void setCodigoBasica(String codigoBasica) {
        this.codigoBasica = codigoBasica;
    }

    /**
     * @return the abreviatura
     */
    public String getAbreviatura() {
        return abreviatura;
    }

    /**
     * @param abreviatura the abreviatura to set
     */
    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    /**
     * @return the nombreBasica
     */
    public String getNombreBasica() {
        return nombreBasica;
    }

    /**
     * @param nombreBasica the nombreBasica to set
     */
    public void setNombreBasica(String nombreBasica) {
        this.nombreBasica = nombreBasica;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /*
     * @return the estadoRegistro
     */
    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    /**
     * @param estadoRegistro the estadoRegistro to set
     */
    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    /**
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the usuarioCreacion
     */
    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    /**
     * @param usuarioCreacion the usuarioCreacion to set
     */
    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    /**
     * @return the perfilCreacion
     */
    public int getPerfilCreacion() {
        return perfilCreacion;
    }

    /**
     * @param perfilCreacion the perfilCreacion to set
     */
    public void setPerfilCreacion(int perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
    }

    /**
     * @return the fechaEdicion
     */
    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    /**
     * @param fechaEdicion the fechaEdicion to set
     */
    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    /**
     * @return the usuarioEdicion
     */
    public String getUsuarioEdicion() {
        return usuarioEdicion;
    }

    /**
     * @param usuarioEdicion the usuarioEdicion to set
     */
    public void setUsuarioEdicion(String usuarioEdicion) {
        this.usuarioEdicion = usuarioEdicion;
    }

    /**
     * @return the perfilEdicion
     */
    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    /**
     * @param perfilEdicion the perfilEdicion to set
     */
    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    public List<CmtBasicaAuditoriaMgl> getListAuditoria() {
        return listAuditoria;
    }

    public void setListAuditoria(List<CmtBasicaAuditoriaMgl> listAuditoria) {
        this.listAuditoria = listAuditoria;
    }

    public String getActivado() {
        return activado;
    }

    public void setActivado(String eliminado) {
        this.activado = eliminado;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    @Override
    public String toString() {
        return "[" + codigoBasica + "]->" + nombreBasica;
    }

    @Override
    public CmtBasicaMgl clone() throws CloneNotSupportedException {
        return (CmtBasicaMgl) super.clone();
    }

    public boolean getActivacion() {
        return activacion;
    }

    public void setActivacion(boolean activacion) {
        this.activacion = activacion;
    }

    public BigDecimal getIdEstadosxTec() {
        return idEstadosxTec;
    }

    public void setIdEstadosxTec(BigDecimal idEstadosxTec) {
        this.idEstadosxTec = idEstadosxTec;
    }

    public int compareTo(CmtBasicaMgl o) {
        int valor = getCodigoBasica().compareTo(o.getCodigoBasica());

        return (valor != 0 ? valor : getCodigoBasica().compareTo(o.getCodigoBasica()));
    }

    public List<CmtBasicaMgl> getModulo() {
        return modulo;
    }

    public void setModulo(List<CmtBasicaMgl> modulo) {
        this.modulo = modulo;
    }

    public List<CmtBasicaMgl> getModuloSelected() {

        return moduloSelected;
    }

    public void setModuloSelected(List<CmtBasicaMgl> moduloSelected) {

        this.moduloSelected = moduloSelected;
    }

    public List<CmtBasicaExtMgl> getListCmtBasicaExtMgl() {
        return listCmtBasicaExtMgl;
    }

    public void setListCmtBasicaExtMgl(List<CmtBasicaExtMgl> listCmtBasicaExtMgl) {
        this.listCmtBasicaExtMgl = listCmtBasicaExtMgl;
    }

    public String getIdentificadorInternoApp() {
        return identificadorInternoApp;
    }

    public void setIdentificadorInternoApp(String identificadorInternoApp) {
        this.identificadorInternoApp = identificadorInternoApp;
    }

    public boolean isNodoTecnologia() {
        return nodoTecnologia;
    }

    public void setNodoTecnologia(boolean nodoTecnologia) {
        this.nodoTecnologia = nodoTecnologia;
    }

    public String getNodoCobertura() {
        return nodoCobertura;
    }

    public void setNodoCobertura(String nodoCobertura) {
        this.nodoCobertura = nodoCobertura;
    }

    public String getNodoGestion() {
        return nodoGestion;
    }

    public void setNodoGestion(String nodoGestion) {
        this.nodoGestion = nodoGestion;
    }

    public boolean isSincronizaRr() {
        return sincronizaRr;
    }

    public void setSincronizaRr(boolean sincronizaRr) {
        this.sincronizaRr = sincronizaRr;
    }

    public String getNodoTecnologiaBasica() {
        return nodoTecnologiaBasica;
    }

    public void setNodoTecnologiaBasica(String nodoTecnologiaBasica) {
        this.nodoTecnologiaBasica = nodoTecnologiaBasica;
    }

    public List<NodoMgl> getNodoCercanoList() {
        return nodoCercanoList;
    }

    public void setNodoCercanoList(List<NodoMgl> nodoCercanoList) {
        this.nodoCercanoList = nodoCercanoList;
    }

    public String getNodoCercano() {
        return nodoCercano;
    }

    public void setNodoCercano(String nodoCercano) {
        this.nodoCercano = nodoCercano;
    }

    public boolean isTecnologiaProcesada() {
        return tecnologiaProcesada;
    }

    public void setTecnologiaProcesada(boolean tecnologiaProcesada) {
        this.tecnologiaProcesada = tecnologiaProcesada;
    }

    public NodoMgl getNodoMgl() {
        return nodoMgl;
    }

    public void setNodoMgl(NodoMgl nodoMgl) {
        this.nodoMgl = nodoMgl;
    }

    public String getNapCercana() {
        return napCercana;
    }

    public void setNapCercana(String napCercana) {
        this.napCercana = napCercana;
    }

    public String getNap() {
        return nap;
    }

    public void setNap(String nap) {
        this.nap = nap;
    }

    public boolean isNapFTTH() {
        return napFTTH;
    }

    public void setNapFTTH(boolean napFTTH) {
        this.napFTTH = napFTTH;
    }


}
