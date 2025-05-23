/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad correspondiente a la tabla CMT_HHPP_VT
 *
 * alejandro.martinez.ext@claro.com.co
 *
 * @versión 1.00.0000
 */
@Entity
@Table(name = "CMT_TEC_HABILITADA_VT", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtHhppVtMgl.findAll", query = "SELECT c FROM CmtHhppVtMgl c"),
    @NamedQuery(name = "CmtHhppVtMgl.findBySubEdiVt", query = "SELECT c FROM CmtHhppVtMgl c WHERE c.subEdificioVtObj = :subEdificioVtObj AND c.estadoRegistro = 1"),
    @NamedQuery(name = "CmtHhppVtMgl.findByVt", query = "SELECT c FROM CmtHhppVtMgl c WHERE c.subEdificioVtObj.vtObj = :vtObj AND c.estadoRegistro = 1"),
    @NamedQuery(name = "CmtHhppVtMgl.findByIdDrDireccion", query = "SELECT c FROM CmtHhppVtMgl c WHERE c.idrDireccionCasa = :drDireccion AND c.estadoRegistro = 1"),
    @NamedQuery(name = "CmtHhppVtMgl.findBySubEdiVtAndPiso", query = "SELECT c FROM CmtHhppVtMgl c WHERE c.subEdificioVtObj = :subEdificioVtObj AND c.piso = :piso AND c.estadoRegistro = 1")
})
public class CmtHhppVtMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "CmtHhppVtMgl.CmtTecnologiaHabilitadaVtMglSq",
            sequenceName = "MGL_SCHEME.CMT_TEC_HABILITADA_VT_SQ",
            allocationSize = 1)
    @GeneratedValue(generator = "CmtHhppVtMgl.CmtTecnologiaHabilitadaVtMglSq")
    @Column(name = "ID_TECNOLOGIA_HABILITADA_VT", nullable = false)
    private BigDecimal idHhppVt;
    @Column(name = "OPCION_NIVEL5", nullable = true, length = 30)
    private String opcionNivel5;
    @Column(name = "VALOR_NIVEL5", nullable = true, length = 100)
    private String valorNivel5;
    @Column(name = "OPCION_NIVEL6", nullable = true, length = 30)
    private String opcionNivel6;
    @Column(name = "VALOR_NIVEL6", nullable = true, length = 100)
    private String valorNivel6;
    @JoinColumn(name = "SUBEDIFICIO_VT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private CmtSubEdificiosVt subEdificioVtObj;
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION", nullable = true, length = 200)
    private String usuarioCreacion;
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "FECHA_EDICION", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION", nullable = true, length = 200)
    private String usuarioEdicion;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @Column(name = "CALLE_RR")
    private String calleRr;
    @Column(name = "UNIDAD_RR")
    private String unidadRr;
    @Column(name = "ID_DRDIRECCION_CASA")
    private BigDecimal idrDireccionCasa;
    @NotNull
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @NotNull
    @Column(name = "PROCESADO")
    private int procesado;
    @Column(name = "MENSAJECREACION")
    private String mensajeCreacion;
    @Transient
    private String mensajeProceso;
    @Transient
    private String direccionValidada;
    @Column(name = "NOMBRE_VALIDO_RR")
    private String nombreValidoRR;
    @Column(name = "PISO")
    private int piso;
    @Column(name = "TIPO_TECNOLOGIA_HAB_VT_ID", nullable = false)
    private String thhVtId;
    
    @Transient
    private boolean variableRR;
    @Transient
    private boolean limiteExcedido;
    @Transient
    private String thhVtIdSeelected;

    public DetalleDireccionEntity mapearDirtecHabilitada(CmtHhppVtMgl hhppVt)
            throws ApplicationException {
        DetalleDireccionEntity direccionEntity = new DetalleDireccionEntity();

        direccionEntity.setBarrio(hhppVt.getSubEdificioVtObj().getBarrio());
        direccionEntity.setBisviageneradora(hhppVt.getSubEdificioVtObj().getBisViaGeneradora());
        direccionEntity.setBisviaprincipal(hhppVt.getSubEdificioVtObj().getBisViaPrincipal());
        direccionEntity.setCptiponivel1(hhppVt.getSubEdificioVtObj().getCpTipoNivel1());
        direccionEntity.setCptiponivel2(hhppVt.getSubEdificioVtObj().getCpTipoNivel2());
        direccionEntity.setCptiponivel3(hhppVt.getSubEdificioVtObj().getCpTipoNivel3());
        direccionEntity.setCptiponivel4(hhppVt.getSubEdificioVtObj().getCpTipoNivel4());
        direccionEntity.setCptiponivel5(hhppVt.getOpcionNivel5());
        direccionEntity.setCptiponivel6(hhppVt.getOpcionNivel6());
        direccionEntity.setCpvalornivel1(hhppVt.getSubEdificioVtObj().getCpValorNivel1());
        direccionEntity.setCpvalornivel2(hhppVt.getSubEdificioVtObj().getCpValorNivel2());
        direccionEntity.setCpvalornivel3(hhppVt.getSubEdificioVtObj().getCpValorNivel3());
        direccionEntity.setCpvalornivel4(hhppVt.getSubEdificioVtObj().getCpValorNivel4());
        direccionEntity.setCpvalornivel5(hhppVt.getValorNivel5());
        direccionEntity.setCpvalornivel6(hhppVt.getValorNivel6());
        direccionEntity.setCuadviageneradora(hhppVt.getSubEdificioVtObj().getCuadViaGeneradora());
        direccionEntity.setCuadviaprincipal(hhppVt.getSubEdificioVtObj().getCuadViaPrincipal());
        direccionEntity.setDescTipoDir(hhppVt.getSubEdificioVtObj().getIdTipoDireccion());
        direccionEntity.setDirprincalt(hhppVt.getSubEdificioVtObj().getDireccionSubEdificio());
        direccionEntity.setEstadoDir(hhppVt.getSubEdificioVtObj().getEstadoDirGeo());
        direccionEntity.setEstrato(hhppVt.getSubEdificioVtObj().getVtObj().getOtObj().getCmObj().getSubEdificioGeneral().getEstrato().getNombreBasica());
        if (direccionEntity.getEstrato().contains("ESTRATO")) {
            direccionEntity.setEstrato(direccionEntity.getEstrato().replace("ESTRATO", "").trim());
        }
        direccionEntity.setIdtipodireccion(hhppVt.getSubEdificioVtObj().getIdTipoDireccion());
        direccionEntity.setItValorPlaca(hhppVt.getSubEdificioVtObj().getItTipoPlaca());
        direccionEntity.setLtviageneradora(hhppVt.getSubEdificioVtObj().getLtViaGeneradora());
        direccionEntity.setLtviaprincipal(hhppVt.getSubEdificioVtObj().getLtViaPrincipal());
        direccionEntity.setMultiOrigen(hhppVt.getSubEdificioVtObj().getVtObj().getOtObj().getCmObj().getMunicipio().getGpoMultiorigen());
        direccionEntity.setMztiponivel1(hhppVt.getSubEdificioVtObj().getMzTipoNivel1());
        direccionEntity.setMztiponivel2(hhppVt.getSubEdificioVtObj().getMzTipoNivel2());
        direccionEntity.setMztiponivel3(hhppVt.getSubEdificioVtObj().getMzTipoNivel3());
        direccionEntity.setMztiponivel4(hhppVt.getSubEdificioVtObj().getMzTipoNivel4());
        direccionEntity.setMztiponivel5(hhppVt.getSubEdificioVtObj().getMzTipoNivel5());
        direccionEntity.setMztiponivel6(hhppVt.getSubEdificioVtObj().getMzTipoNivel6());
        direccionEntity.setMzvalornivel1(hhppVt.getSubEdificioVtObj().getMzValorNivel1());
        direccionEntity.setMzvalornivel2(hhppVt.getSubEdificioVtObj().getMzValorNivel2());
        direccionEntity.setMzvalornivel3(hhppVt.getSubEdificioVtObj().getMzValorNivel3());
        direccionEntity.setMzvalornivel4(hhppVt.getSubEdificioVtObj().getMzValorNivel4());
        direccionEntity.setMzvalornivel5(hhppVt.getSubEdificioVtObj().getMzValorNivel5());
        direccionEntity.setMzvalornivel6(hhppVt.getSubEdificioVtObj().getMzValorNivel6());
        direccionEntity.setNlpostviag(hhppVt.getSubEdificioVtObj().getNlPostViaG());
        direccionEntity.setNlpostviap(hhppVt.getSubEdificioVtObj().getNlPostViaP());
        direccionEntity.setNumviageneradora(hhppVt.getSubEdificioVtObj().getNumViaGeneradora());
        direccionEntity.setNumviaprincipal(hhppVt.getSubEdificioVtObj().getNumViaPrincipal());
        direccionEntity.setPlacadireccion(hhppVt.getSubEdificioVtObj().getPlacaDireccion());
        direccionEntity.setTipoviageneradora(hhppVt.getSubEdificioVtObj().getTipoViaGeneradora());
        direccionEntity.setTipoviaprincipal(hhppVt.getSubEdificioVtObj().getTipoViaPrincipal());

        return direccionEntity;
    }

    public String getNombreHhpp() {
        StringBuffer nombreHhpp = new StringBuffer();
        if (opcionNivel5 != null && !opcionNivel5.trim().isEmpty()) {
            nombreHhpp.append(opcionNivel5);
            nombreHhpp.append("");
        }
        if (valorNivel5 != null && !valorNivel5.trim().isEmpty()) {
            nombreHhpp.append(valorNivel5);
            nombreHhpp.append("");
        }
        if (opcionNivel6 != null && !opcionNivel6.trim().isEmpty()) {
            nombreHhpp.append(opcionNivel6);
            nombreHhpp.append("");
        }
        if (valorNivel6 != null && !valorNivel6.trim().isEmpty()) {
            nombreHhpp.append(valorNivel6);
            nombreHhpp.append("");
        }

        return nombreHhpp.toString().trim();
    }

    /**
     * @return the idHhppVt
     */
    public BigDecimal getIdHhppVt() {
        return idHhppVt;
    }

    /**
     * @param idHhppVt the idTecnologiaHabilitadaVt to set
     */
    public void setIdHhppVt(BigDecimal idHhppVt) {
        this.idHhppVt = idHhppVt;
    }

    /**
     * @return the opcionNivel5
     */
    public String getOpcionNivel5() {
        return opcionNivel5;
    }

    /**
     * @param opcionNivel5 the opcionNivel5 to set
     */
    public void setOpcionNivel5(String opcionNivel5) {
        this.opcionNivel5 = opcionNivel5;
    }

    /**
     * @return the valorNivel5
     */
    public String getValorNivel5() {
        return valorNivel5;
    }

    /**
     * @param valorNivel5 the valorNivel5 to set
     */
    public void setValorNivel5(String valorNivel5) {
        this.valorNivel5 = valorNivel5;
    }

    /**
     * @return the opcionNivel6
     */
    public String getOpcionNivel6() {
        return opcionNivel6;
    }

    /**
     * @param opcionNivel6 the opcionNivel6 to set
     */
    public void setOpcionNivel6(String opcionNivel6) {
        this.opcionNivel6 = opcionNivel6;
    }

    /**
     * @return the valorNivel6
     */
    public String getValorNivel6() {
        return valorNivel6;
    }

    /**
     * @param valorNivel6 the valorNivel6 to set
     */
    public void setValorNivel6(String valorNivel6) {
        this.valorNivel6 = valorNivel6;
    }

    /**
     * @return the subEdificioVtObj
     */
    public CmtSubEdificiosVt getSubEdificioVtObj() {
        return subEdificioVtObj;
    }

    /**
     * @param subEdificioVtObj the subEdificioVtObj to set
     */
    public void setSubEdificioVtObj(CmtSubEdificiosVt subEdificioVtObj) {
        this.subEdificioVtObj = subEdificioVtObj;
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

    /**
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
     * @return the procesado
     */
    public int getProcesado() {
        return procesado;
    }

    /**
     * @param procesado the estadoRegistro to set
     */
    public void setProcesado(int procesado) {
        this.procesado = procesado;
    }

    /**
     * @return 
     */
    public String getMensajeProceso() {
        return mensajeProceso;
    }

    /**
     * @param mensajeProceso the estadoRegistro to set
     */
    public void setMensajeProceso(String mensajeProceso) {
        this.mensajeProceso = mensajeProceso;
    }

    public String getDireccionValidada() {
        return direccionValidada;
    }

    public void setDireccionValidada(String direccionValidada) {
        this.direccionValidada = direccionValidada;
    }

    public String getCalleRr() {
        return calleRr;
    }

    public void setCalleRr(String calleRr) {
        this.calleRr = calleRr;
    }

    public String getUnidadRr() {
        return unidadRr;
    }

    public void setUnidadRr(String unidadRr) {
        this.unidadRr = unidadRr;
    }


    public String getNombreValidoRR() {
        return nombreValidoRR;
    }

    public void setNombreValidoRR(String nombreValidoRR) {
        this.nombreValidoRR = nombreValidoRR;
    }

    public boolean isVariableRR() {
        return variableRR;
    }

    public void setVariableRR(boolean variableRR) {
        this.variableRR = variableRR;
    }

    public int compareTo(CmtHhppVtMgl o) {
        int valor = getValorNivel5().compareTo(o.getValorNivel5());

        return (valor != 0 ? valor : getValorNivel5().compareTo(o.getValorNivel5()));
    }

    public boolean isLimiteExcedido() {
        return limiteExcedido;
    }

    public void setLimiteExcedido(boolean limiteExcedido) {
        this.limiteExcedido = limiteExcedido;
    }


    public String getMensajeCreacion() {
        return mensajeCreacion;
    }

    public void setMensajeCreacion(String mensajeCreacion) {
        this.mensajeCreacion = mensajeCreacion;
    }

    public BigDecimal getIdrDireccionCasa() {
        return idrDireccionCasa;
    }

    public void setIdrDireccionCasa(BigDecimal idrDireccionCasa) {
        this.idrDireccionCasa = idrDireccionCasa;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public String getThhVtId() {
        return thhVtId;
    }

    public void setThhVtId(String thhVtId) {
        this.thhVtId = thhVtId;
    }

    public String getThhVtIdSeelected() {
        return thhVtIdSeelected;
    }

    public void setThhVtIdSeelected(String thhVtIdSeelected) {
        this.thhVtIdSeelected = thhVtIdSeelected;
    }



}
