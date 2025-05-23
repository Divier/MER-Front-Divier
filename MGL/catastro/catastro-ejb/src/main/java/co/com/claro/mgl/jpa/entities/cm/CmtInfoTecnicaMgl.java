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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "CMT_INFO_TECNICA", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtInfoTecnicaMgl.findAll", query = "SELECT n FROM CmtInfoTecnicaMgl n "),
    @NamedQuery(name = "CmtInfoTecnicaMgl.findBySubEdificio", query = "SELECT n FROM CmtInfoTecnicaMgl n WHERE n.idSubedificio.subEdificioId = :subEdificio AND n.estadoRegistro = 1 "),
    @NamedQuery(name = "CmtInfoTecnicaMgl.getCountBySubEdificio", query = "SELECT COUNT(DISTINCT c) FROM CmtInfoTecnicaMgl c  WHERE c.idSubedificio.subEdificioId = :subEdificio AND c.estadoRegistro = 1")})

public class CmtInfoTecnicaMgl
        implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtInfoTecnicaMgl.cmtInfoTecnicaSq",
            sequenceName = "MGL_SCHEME.CMT_INFO_TECNICA_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtInfoTecnicaMgl.cmtInfoTecnicaSq")
    @Column(name = "ID", nullable = false)
    private BigDecimal id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SUBEDIFICIO")
    private CmtSubEdificioMgl idSubedificio;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_INFO_NIVEL_1")
    private CmtBasicaMgl basicaIdInfoN1;
      @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_INFO_NIVEL_2")
    private CmtBasicaMgl basicaIdInfoN2;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_INFO_NIVEL_3")
    private CmtBasicaMgl basicaIdInfoN3;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_INFO_NIVEL_4")
    private CmtBasicaMgl basicaIdInfoN4;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TECNOLOGIA_SUB_ID")
    private CmtTecnologiaSubMgl tecnologiaSubMglObj;
    @Column(name = "FECHACREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "USUARIOCREACION", nullable = true, length = 200)
    private String usuarioCreacion;
    @Column(name = "PERFILCREACION")
    private int perfilCreacion;
    @Column(name = "FECHAEDICION", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Column(name = "USUARIOEDICION", nullable = true, length = 200)
    private String usuarioEdicion;
    @Column(name = "PERFILEDICION")
    private int perfilEdicion;
    @NotNull
    @Column(name = "ESTADOREGISTRO")
    private int estadoRegistro;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public CmtSubEdificioMgl getIdSubedificio() {
        return idSubedificio;
    }

    public void setIdSubedificio(CmtSubEdificioMgl idSubedificio) {
        this.idSubedificio = idSubedificio;
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

    public int getPerfilCreacion() {
        return perfilCreacion;
    }

    public void setPerfilCreacion(int perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
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

    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public CmtBasicaMgl getBasicaIdInfoN1() {
        return basicaIdInfoN1;
    }

    public void setBasicaIdInfoN1(CmtBasicaMgl basicaIdInfoN1) {
        this.basicaIdInfoN1 = basicaIdInfoN1;
    }

    public CmtBasicaMgl getBasicaIdInfoN2() {
        return basicaIdInfoN2;
    }

    public void setBasicaIdInfoN2(CmtBasicaMgl basicaIdInfoN2) {
        this.basicaIdInfoN2 = basicaIdInfoN2;
    }

    public CmtBasicaMgl getBasicaIdInfoN3() {
        return basicaIdInfoN3;
    }

    public void setBasicaIdInfoN3(CmtBasicaMgl basicaIdInfoN3) {
        this.basicaIdInfoN3 = basicaIdInfoN3;
    }

    public CmtBasicaMgl getBasicaIdInfoN4() {
        return basicaIdInfoN4;
    }

    public void setBasicaIdInfoN4(CmtBasicaMgl basicaIdInfoN4) {
        this.basicaIdInfoN4 = basicaIdInfoN4;
    }

    public CmtTecnologiaSubMgl getTecnologiaSubMglObj() {
        return tecnologiaSubMglObj;
    }

    public void setTecnologiaSubMglObj(CmtTecnologiaSubMgl tecnologiaSubMglObj) {
        this.tecnologiaSubMglObj = tecnologiaSubMglObj;
    }

  

}
