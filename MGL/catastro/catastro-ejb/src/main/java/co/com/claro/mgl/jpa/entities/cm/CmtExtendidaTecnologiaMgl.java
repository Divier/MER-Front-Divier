/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

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
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@Entity
@Table(name = "CMT_EXTENDIDA_TECNOLOGIA" , schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtExtendidaTecnologiaMgl.findAll", query = "SELECT c FROM CmtExtendidaTecnologiaMgl c"),
    @NamedQuery(name = "CmtExtendidaTecnologiaMgl.findBytipoTecnologiaObj",
            query = "SELECT c FROM CmtExtendidaTecnologiaMgl c WHERE c.tipoTecnologiaObj = :tipoTecnologiaObj  "
            + "AND c.estadoRegistro = :estadoRegistro")})
public class CmtExtendidaTecnologiaMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtExtendidaTecnologiaMgl.CmtExtendidaTecnologiaSq",
            sequenceName = "MGL_SCHEME.CMT_EXTENDIDA_TECNOLOGIA_SQ",
            allocationSize = 1)
    @GeneratedValue(generator = "CmtExtendidaTecnologiaMgl.CmtExtendidaTecnologiaSq")
    @Column(name = "ID_EXTENDIDA_TECNOLOGIA")
    private BigDecimal idExtTec;
    @Size(min = 1, max = 255)
    @Column(name = "TIPOS_VIVIENDA")
    private String tiposVivienda;
    @Column(name = "REQ_SUBEDIFICIOS", columnDefinition = "default 0")
    private int reqSubedificios;
    @Column(name = "REQ_SOPORTE", columnDefinition = "default 0")
    private int reqSoporte;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPO_TECNOLOGIA", referencedColumnName = "BASICA_ID", nullable = false)
    private CmtBasicaMgl tipoTecnologiaObj;
    @NotNull
    @Column(name = "ESTADO_REGISTRO", columnDefinition = "default 1")
    private int estadoRegistro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "FECHA_EDICION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Size(min = 1, max = 20)
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    @Basic(optional = false)
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_NOMBRE_TEC_OFSC", referencedColumnName = "BASICA_ID", nullable = false)
    private CmtBasicaMgl nomTecnologiaOFSC;
    
    @Size(min = 1, max = 20)
    @Column(name = "LEGADOS")
    private String legados;

    /**
     * @return the idExtTec
     */
    public BigDecimal getIdExtTec() {
        return idExtTec;
    }

    /**
     * @param idExtTec the idExtTec to set
     */
    public void setIdExtTec(BigDecimal idExtTec) {
        this.idExtTec = idExtTec;
    }

    /**
     * @return the tiposVivienda
     */
    public String getTiposVivienda() {
        return tiposVivienda;
    }

    /**
     * @param tiposVivienda the tiposVivienda to set
     */
    public void setTiposVivienda(String tiposVivienda) {
        this.tiposVivienda = tiposVivienda;
    }

    /**
     * @return the reqSubedificios
     */
    public int getReqSubedificios() {
        return reqSubedificios;
    }

    /**
     * @param reqSubedificios the reqSubedificios to set
     */
    public void setReqSubedificios(int reqSubedificios) {
        this.reqSubedificios = reqSubedificios;
    }

    /**
     * @return the reqSoporte
     */
    public int getReqSoporte() {
        return reqSoporte;
    }

    /**
     * @param reqSoporte the reqSoporte to set
     */
    public void setReqSoporte(int reqSoporte) {
        this.reqSoporte = reqSoporte;
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
     * @return the tipoTecnologiaObj
     */
    public CmtBasicaMgl getTipoTecnologiaObj() {
        return tipoTecnologiaObj;
    }

    /**
     * @param tipoTecnologiaObj the tipoTecnologiaObj to set
     */
    public void setTipoTecnologiaObj(CmtBasicaMgl tipoTecnologiaObj) {
        this.tipoTecnologiaObj = tipoTecnologiaObj;
    }

    public CmtBasicaMgl getNomTecnologiaOFSC() {
        return nomTecnologiaOFSC;
    }

    public void setNomTecnologiaOFSC(CmtBasicaMgl nomTecnologiaOFSC) {
        this.nomTecnologiaOFSC = nomTecnologiaOFSC;
    }

    public String getLegados() {
        return legados;
    }

    public void setLegados(String legados) {
        this.legados = legados;
    }
      
    
}
