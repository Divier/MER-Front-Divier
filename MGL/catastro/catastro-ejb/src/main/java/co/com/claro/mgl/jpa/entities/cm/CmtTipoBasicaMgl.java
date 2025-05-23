package co.com.claro.mgl.jpa.entities.cm;

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
@Table(name = "MGL_TIPO_BASICA", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtTipoBasicaMgl.findAll", query = "SELECT c FROM CmtTipoBasicaMgl c order by C.nombreTipo ASC"),
    @NamedQuery(name = "CmtTipoBasicaMgl.findNombre", query = "SELECT bs FROM CmtTipoBasicaMgl bs WHERE bs.nombreTipo=:nombreTipo"),
    @NamedQuery(name = "CmtTipoBasicaMgl.findByTipoBasicaId", query = "SELECT c FROM CmtTipoBasicaMgl c WHERE c.tipoBasicaId = :tipoBasicaId AND "
            + "c.estadoRegistro=1 ORDER BY c.nombreTipo ASC"),
    @NamedQuery(name = "CmtTipoBasicaMgl.findByCodigoInternoApp", query = "SELECT c FROM CmtTipoBasicaMgl c WHERE c.identificadorInternoApp = :codigoInternoApp AND c.estadoRegistro=1"),
    @NamedQuery(name = "CmtTipoBasicaMgl.findByComplemento", query = "SELECT c FROM CmtTipoBasicaMgl c WHERE c.complemento = :complemento "
            + "AND c.estadoRegistro=1 ORDER BY c.nombreTipo ASC"),
    @NamedQuery(name = "CmtTipoBasicaMgl.findAllSinInfoAdi", query = "SELECT c FROM CmtTipoBasicaMgl c WHERE  c.estadoRegistro=1   "
            + "AND (c.complemento is  null OR c.complemento <> :complemento) ORDER BY c.nombreTipo ASC"),
    @NamedQuery(name = "CmtTipoBasicaMgl.selectMaxCodigo", query = "SELECT MAX(c.codigoTipo) FROM CmtTipoBasicaMgl c"
    )
})
public class CmtTipoBasicaMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtTipoBasicaMgl.CmtTipoBasicaMglSq",
            sequenceName = "MGL_SCHEME.MGL_TIPO_BASICA_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtTipoBasicaMgl.CmtTipoBasicaMglSq")
    @Column(name = "TIPO_BASICA_ID", nullable = false)
    private BigDecimal tipoBasicaId;
    @Column(name = "NOMBRE_TIPO", nullable = false, length = 200)
    private String nombreTipo;
    @Column(name = "CODIGO_TIPO", nullable = false, length = 20)
    private String codigoTipo;
    @Column(name = "DESCRIPCION_TIPO", nullable = false, length = 1000)
    private String descripcionTipo;
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
    @NotNull
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @Column(name = "LONGITUD_CODIGO")
    private int longitudCodigo;
    @Column(name = "TIPO_DATO_CODIGO", length = 2)
    private String tipoDatoCodigo;
    @Column(name = "INICIAL_ABREVIATURA", length = 1)
    private String inicialesAbreviatura;
    @Column(name = "JUSTIFICACION", nullable = false, length = 1000)
    private String justificacion;
    @Column(name = "COMPLEMENTO")
    private String complemento;
    @Column(name = "MODULO")
    private String modulo;
    @Transient
    private String moduloVista;
    @OneToMany(mappedBy = "idTipoBasica", fetch = FetchType.LAZY)
    private List<CmtTipoBasicaExtMgl> listCmtTipoBasicaExtMgl;
    @OneToMany(mappedBy = "tipoBasicaObj", fetch = FetchType.LAZY)
    private List<CmtTipoBasicaAuditoriaMgl> listAuditoria;
    @Column(name = "IDENTIFICADOR_INTERNO_APP")
    private String identificadorInternoApp;

    /**
     * @return the tipoBasicaId
     */
    public BigDecimal getTipoBasicaId() {
        return tipoBasicaId;
    }

    /**
     * @param tipoBasicaId the tipoBasicaId to set
     */
    public void setTipoBasicaId(BigDecimal tipoBasicaId) {
        this.tipoBasicaId = tipoBasicaId;
    }

    /**
     * @return the nombreTipo
     */
    public String getNombreTipo() {
        return nombreTipo;
    }

    /**
     * @param nombreTipo the nombreTipo to set
     */
    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    /**
     * @return the codigoTipo
     */
    public String getCodigoTipo() {
        return codigoTipo;
    }

    /**
     * @param codigoTipo the codigoTipo to set
     */
    public void setCodigoTipo(String codigoTipo) {
        this.codigoTipo = codigoTipo;
    }

    /**
     * @return the descripcionTipo
     */
    public String getDescripcionTipo() {
        return descripcionTipo;
    }

    /**
     * @param descripcionTipo the descripcionTipo to set
     */
    public void setDescripcionTipo(String descripcionTipo) {
        this.descripcionTipo = descripcionTipo;
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

    public int getLongitudCodigo() {
        return longitudCodigo;
    }

    public void setLongitudCodigo(int longitudCodigo) {
        this.longitudCodigo = longitudCodigo;
    }

    public String getTipoDatoCodigo() {
        return tipoDatoCodigo;
    }

    public void setTipoDatoCodigo(String tipoDatoCodigo) {
        this.tipoDatoCodigo = tipoDatoCodigo;
    }

    public String getInicialesAbreviatura() {
        return inicialesAbreviatura;
    }

    public void setInicialesAbreviatura(String inicialesAbreviatura) {
        this.inicialesAbreviatura = inicialesAbreviatura;
    }

    @Override
    public String toString() {
        return "[(" + this.tipoBasicaId.toString() + ")" + this.getNombreTipo() + "]";
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getModulo() {

        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getModuloVista() {
        return moduloVista;
    }

    public void setModuloVista(String moduloVista) {
        this.moduloVista = moduloVista;
    }

    public List<CmtTipoBasicaExtMgl> getListCmtTipoBasicaExtMgl() {
        return listCmtTipoBasicaExtMgl;
    }

    public void setListCmtTipoBasicaExtMgl(List<CmtTipoBasicaExtMgl> listCmtTipoBasicaExtMgl) {
        this.listCmtTipoBasicaExtMgl = listCmtTipoBasicaExtMgl;
    }

    public List<CmtTipoBasicaAuditoriaMgl> getListAuditoria() {
        return listAuditoria;
    }

    public void setListAuditoria(List<CmtTipoBasicaAuditoriaMgl> listAuditoria) {
        this.listAuditoria = listAuditoria;
    }

    public String getIdentificadorInternoApp() {
        return identificadorInternoApp;
    }

    public void setIdentificadorInternoApp(String identificadorInternoApp) {
        this.identificadorInternoApp = identificadorInternoApp;
    }

    public CmtBasicaMgl findBasicaByCampoEntidadAs400ExtendidoAndValorExtendido(
            String CampoEntidadAs400,String ValorExtendido) {
        if (listCmtTipoBasicaExtMgl != null && !listCmtTipoBasicaExtMgl.isEmpty()) {
            for (CmtTipoBasicaExtMgl tipoBasicaExtMgl : listCmtTipoBasicaExtMgl) {
                if (tipoBasicaExtMgl.getCampoEntidadAs400().
                        equalsIgnoreCase(CampoEntidadAs400)) {
                    if(tipoBasicaExtMgl.getListCmtBasicaExtMgl()!=null &&
                            !tipoBasicaExtMgl.getListCmtBasicaExtMgl().isEmpty()){
                        for(CmtBasicaExtMgl basicaExtMgl :
                                tipoBasicaExtMgl.getListCmtBasicaExtMgl()){
                            if(tipoBasicaExtMgl.getIdTipoBasicaExt().compareTo(
                                    basicaExtMgl.getIdTipoBasicaExt().getIdTipoBasicaExt())==0 
                                    && basicaExtMgl.getValorExtendido().equals(ValorExtendido)){
                                return basicaExtMgl.getIdBasicaObj();
                            }
                        }                        
                    }
                }
            }
        }
        return  null;
    }

}
