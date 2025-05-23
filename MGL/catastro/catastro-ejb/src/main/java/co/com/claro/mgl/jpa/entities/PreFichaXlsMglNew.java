/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.telmex.catastro.data.AddressService;
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
 * Entity TEC_PREFICHA_XLS_NEW
 * 
 * @author Miguel Barrios Hitss
 * @version 1.1 Rev Miguel Barrios Hitss
 */

@Entity
@Table(name = "TEC_PREFICHA_XLS_NEW", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PreFichaXlsMglNew.findByFaseAndIdPf", query = "SELECT p FROM PreFichaXlsMglNew p where p.prfId = :prfId and p.fase = :fase"),
    @NamedQuery(name = "PreFichaXlsMglNew.findByFaseAndIdPfAndPestana", query = "SELECT p FROM PreFichaXlsMglNew p "
            + "where p.prfId = :prfId and p.fase = :fase AND p.pestana = :tab AND p.estadoRegistro = 1" )})
public class PreFichaXlsMglNew implements Serializable {
   private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
        name="PreFichaXlsMglNew.preFichaXlsNewSeq",
        sequenceName="MGL_SCHEME.TEC_PREFICHA_XLS_NEW_SQ", allocationSize = 1
    )
    @GeneratedValue(generator="PreFichaXlsMglNew.preFichaXlsNewSeq" )
    @Column(name = "PREFICHA_XLS_ID", nullable = false)
    private BigDecimal id;
    @Column(name = "PREFICHA_XLS_TRAZABILIDAD_ID", nullable = true)
    private BigDecimal idPfx;
    @Column(name = "PREFICHA_ID", nullable = false)
    private BigDecimal prfId;
    @Column(name = "NOMBRE_CONJUNTO", nullable = true, length = 200)
    private String nombreConj;
    @Column(name = "VIA_PRINCIPAL", nullable = true, length = 200)
    private String viaPrincipal;
    @Column(name = "PLACA", nullable = true, length = 20)
    private String placa;
    @Column(name = "TOTAL_TEC_HABILITADA", nullable = true)
    private BigDecimal totalHHPP;
    @Column(name = "APARTAMENTOS", nullable = true)
    private BigDecimal aptos;
    @Column(name = "LOCALES", nullable = true)
    private BigDecimal locales;
    @Column(name = "OFICINAS", nullable = true)
    private BigDecimal oficinas;
    @Column(name = "PISOS", nullable = true)
    private BigDecimal pisos;
    @Column(name = "BARRIO", nullable = true, length = 200)
    private String barrio;
    @Column(name = "DISTRIBUCION", nullable = true, length = 200)
    private String distribucion;
    @Column(name = "OBSERVACIONES", nullable = true, length = 20)
    private String observaciones;
    @Column(name = "FASE_PREFICHA", nullable = true, length = 200)
    private String fase;
    @Column(name = "CLASIFICACION", nullable = true, length = 200)
    private String clasificacion;    
    @OneToOne(mappedBy="preFichaXlsMgl")
    private PreFichaGeorreferenciaMglNew fichaGeorreferenciaMgl;    
    @OneToMany(fetch=FetchType.LAZY)
    @JoinColumn(name = "PREFICHA_XLS_ID", referencedColumnName = "PREFICHA_XLS_ID")
    private List<PreFichaHHPPDetalleMglNew> detalleHHPPList;
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION", nullable = false)
    private String usuarioCreacion;
    @Column(name = "FECHA_EDICION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION", nullable = false)
    private String usuarioEdicion;
    @Column(name = "ESTADO_REGISTRO", nullable = false)
    private int estadoRegistro;
    @Column(name = "ID_TIPO_DIRECCION", nullable = true)
    private String idTipoDireccion;
    @Column(name = "NODO", nullable = true, length = 10)
    private String nodo;
    @Column(name = "REGISTRO_VALIDO", nullable = true)
    private int registroValido;
    @Column(name = "PESTANA", nullable = true, length = 200)
    private String pestana;
    @Column(name = "AMP", nullable = true, length = 24)
    private String amp;
    @Column(name = "DIRECCION", nullable = true, length = 255)
    private String direccion;
    @Column(name = "CODIGO_DANE", nullable = true, length = 255)
    private String codigoDane;
    @Column (name = "PISO_USO", nullable = false)
    private int pisoUso;
    @Column (name = "UNIDAD_USO", nullable = false)
    private int unidadUso;
    @Column(name = "NUEVO_APTO", nullable = true, length = 25)
    private String nuevoApto;
    @Column (name = "ACT_UNIDAD", nullable = false)
    private int actUnidad;
    
    @Transient
    private boolean selected = false;
    @Transient
    private boolean modified = false;
    @Transient
    private List<String> direccionStrList;    
    @Transient
    private List<String> direccionCCMMStrList;    
    @Transient
    private List<PreFichaGeorreferenciaMglNew> georreferenciaList;
    @Transient
    private List<String> hhppList;
    @Transient
    private List<DrDireccion> drDireccionList;
    @Transient    
    private AddressService responseGeo;
    @Transient
    private List<String> listClasificacion;
    @Transient
    private String observacionFicha;    
    @Transient 
    private CmtCuentaMatrizMgl cuentaAsociada;
    @Transient
    private List<HhppMgl> hhppAsociados;
    @Transient
    private List<PreFichaHHPPDetalleMglNew> hhppDetalleList;
    @Transient
    private boolean buttonOk;
    @Transient
    private boolean buttonError;    
    @Transient
    private DrDireccion direccionTabulada;
    @Transient
    private boolean geoDrDireccionNull = false;    
    @Transient
    private boolean geoDrDireccionNotNull = false;
    @Transient
    private boolean unidadActualizada = false;  
    @Transient
    private String nuevaDist;    
    @Transient
    private String calleG;   
    @Transient
    private String placaG;   
    @Transient
    private boolean actualizaCasa = false;
        
    public List<HhppMgl> getHhppAsociados() {
        return hhppAsociados;
    }

    public void setHhppAsociados(List<HhppMgl> hhppAsociados) {
        this.hhppAsociados = hhppAsociados;
    }

    public CmtCuentaMatrizMgl getCuentaAsociada() {
        return cuentaAsociada;
    }

    public void setCuentaAsociada(CmtCuentaMatrizMgl cuentaAsociada) {
        this.cuentaAsociada = cuentaAsociada;
    }

    public AddressService getResponseGeo() {
        return responseGeo;
    }

    public void setResponseGeo(AddressService responseGeo) {
        this.responseGeo = responseGeo;
    }
    
    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal pfxId) {
        this.id = pfxId;
    }

    public BigDecimal getIdPfx() {
        return idPfx;
    }

    public void setIdPfx(BigDecimal idPfx) {
        this.idPfx = idPfx;
    }

    public BigDecimal getPrfId() {
        return prfId;
    }

    public void setPrfId(BigDecimal prfId) {
        this.prfId = prfId;
    }

    public String getNombreConj() {
        return nombreConj;
    }

    public void setNombreConj(String pfxNombreConj) {
        this.nombreConj = pfxNombreConj;
    }

    public String getViaPrincipal() {
        return viaPrincipal;
    }

    public void setViaPrincipal(String pfxViaPrincipal) {
        this.viaPrincipal = pfxViaPrincipal;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String pfxPlaca) {
        this.placa = pfxPlaca;
    }

    public BigDecimal getTotalHHPP() {
        return totalHHPP;
    }

    public void setTotalHHPP(BigDecimal pfxTotalHHPP) {
        this.totalHHPP = pfxTotalHHPP;
    }

    public BigDecimal getAptos() {
        return aptos;
    }

    public void setAptos(BigDecimal pfxAptos) {
        this.aptos = pfxAptos;
    }

    public BigDecimal getLocales() {
        return locales;
    }

    public void setLocales(BigDecimal pfxLocales) {
        this.locales = pfxLocales;
    }

    public BigDecimal getOficinas() {
        return oficinas;
    }

    public void setOficinas(BigDecimal pfxOficinas) {
        this.oficinas = pfxOficinas;
    }

    public BigDecimal getPisos() {
        return pisos;
    }

    public void setPisos(BigDecimal pfxPisos) {
        this.pisos = pfxPisos;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String pfxBarrio) {
        this.barrio = pfxBarrio;
    }

    public String getDistribucion() {
        return distribucion;
    }

    public void setDistribucion(String pfxDistribucion) {
        this.distribucion = pfxDistribucion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String pfxObservaciones) {
        this.observaciones = pfxObservaciones;
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String pfxFase) {
        this.fase = pfxFase;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String pfxClasificacion) {
        this.clasificacion = pfxClasificacion;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public String getModifiedStr() {
        return String.valueOf(modified);
    }

    public List<String> getDireccionStrList() {
        return direccionStrList;
    }

    public void setDireccionStrList(List<String> direccionStrList) {
        this.direccionStrList = direccionStrList;
    }

    public PreFichaGeorreferenciaMglNew getFichaGeorreferenciaMgl() {
        return fichaGeorreferenciaMgl;
    }

    public void setFichaGeorreferenciaMgl(PreFichaGeorreferenciaMglNew fichaGeorreferenciaMgl) {
        this.fichaGeorreferenciaMgl = fichaGeorreferenciaMgl;
    }      

    public List<PreFichaHHPPDetalleMglNew> getDetalleHHPPList() {
        return detalleHHPPList;
    }

    public void setDetalleHHPPList(List<PreFichaHHPPDetalleMglNew> detalleHHPPList) {
        this.detalleHHPPList = detalleHHPPList;
    }

    public List<PreFichaGeorreferenciaMglNew> getGeorreferenciaList() {
        return georreferenciaList;
    }

    public void setGeorreferenciaList(List<PreFichaGeorreferenciaMglNew> georreferenciaList) {
        this.georreferenciaList = georreferenciaList;
    }

    public List<String> getHhppList() {
        return hhppList;
    }

    public void setHhppList(List<String> hhppList) {
        this.hhppList = hhppList;
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

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public List<DrDireccion> getDrDireccionList() {
        return drDireccionList;
    }

    public void setDrDireccionList(List<DrDireccion> drDireccionList) {
        this.drDireccionList = drDireccionList;
    }

    public String getIdTipoDireccion() {
        return idTipoDireccion;
    }

    public void setIdTipoDireccion(String idTipoDireccion) {
        this.idTipoDireccion = idTipoDireccion;
    }

    public String getNodo() {
        return nodo;
    }

    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    public List<String> getListClasificacion() {
        return listClasificacion;
    }

    public void setListClasificacion(List<String> listClasificacion) {
        this.listClasificacion = listClasificacion;
    }

    /**
     * @return the registroValido
     */
    public int getRegistroValido() {
        return registroValido;
    }

    /**
     * @param registroValido the registroValido to set
     */
    public void setRegistroValido(int registroValido) {
        this.registroValido = registroValido;
    }

    public String getObservacionFicha() {
        return observacionFicha;
    }

    public void setObservacionFicha(String observacionFicha) {
        this.observacionFicha = observacionFicha;
    }

    public String getPestana() {
        return pestana;
    }

    public void setPestana(String pestana) {
        this.pestana = pestana;
    }

    public String getAmp() {
        return amp;
    }

    public void setAmp(String amp) {
        this.amp = amp;
    }
    
    public List<PreFichaHHPPDetalleMglNew> getHhppDetalleList() {
        return hhppDetalleList;
    }

    public void setHhppDetalleList(List<PreFichaHHPPDetalleMglNew> hhppDetalleList) {
        this.hhppDetalleList = hhppDetalleList;
    }

    /**
     * @return the direccionCCMMStrList
     */
    public List<String> getDireccionCCMMStrList() {
        return direccionCCMMStrList;
    }

    /**
     * @param direccionCCMMStrList the direccionCCMMStrList to set
     */
    public void setDireccionCCMMStrList(List<String> direccionCCMMStrList) {
        this.direccionCCMMStrList = direccionCCMMStrList;
    }

    public boolean isButtonOk() {
        return buttonOk;
    }

    public void setButtonOk(boolean buttonOk) {
        this.buttonOk = buttonOk;
    }

    public boolean isButtonError() {
        return buttonError;
    }

    public void setButtonError(boolean buttonError) {
        this.buttonError = buttonError;
    }

    public DrDireccion getDireccionTabulada() {
        return direccionTabulada;
    }

    public void setDireccionTabulada(DrDireccion direccionTabulada) {
        this.direccionTabulada = direccionTabulada;
    }

    public boolean isGeoDrDireccionNull() {
        return geoDrDireccionNull;
    }

    public void setGeoDrDireccionNull(boolean geoDrDireccionNull) {
        this.geoDrDireccionNull = geoDrDireccionNull;
    }

    public boolean isGeoDrDireccionNotNull() {
        return geoDrDireccionNotNull;
    }

    public void setGeoDrDireccionNotNull(boolean geoDrDireccionNotNull) {
        this.geoDrDireccionNotNull = geoDrDireccionNotNull;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodigoDane() {
        return codigoDane;
    }

    public void setCodigoDane(String codigoDane) {
        this.codigoDane = codigoDane;
    }
    
    public String getCalleG () {
        return calleG;
    }
    
    public void setCalleG(String calleG) {
        this.calleG = calleG;
    }
    
    public String getNuevaDist () {
        return nuevaDist;
    }
    
    public void setNuevaDist(String nuevaDist) {
        this.nuevaDist = nuevaDist;
    }
    
    public boolean getUnidadActualizada() {
        return unidadActualizada;
    }
    
    public void setUnidadActualizada(boolean unidadActualizada) {
        this.unidadActualizada = unidadActualizada;
    }
    
    public String getPlacaG () {
        return placaG;
    }
    
    public void setPlacaG(String placaG) {
        this.placaG = placaG;
    }
    
    public boolean getActualizaCasa() {
        return actualizaCasa;
    }
    
    public void setActualizaCasa(boolean actualizaCasa) {
        this.actualizaCasa = actualizaCasa;
    }

    public int getPisoUso() {
        return pisoUso;
    }

    public void setPisoUso(int pisoUso) {
        this.pisoUso = pisoUso;
    }

    public String getNuevoApto() {
        return nuevoApto;
    }

    public void setNuevoApto(String nuevoApto) {
        this.nuevoApto = nuevoApto;
    }

    public int getActUnidad() {
        return actUnidad;
    }

    public void setActUnidad(int actUnidad) {
        this.actUnidad = actUnidad;
    }

    public int getUnidadUso() {
        return unidadUso;
    }

    public void setUnidadUso(int unidadUso) {
        this.unidadUso = unidadUso;
    }    
}
