package co.com.claro.mgl.jpa.entities.cm;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author camargomf
 */
@Entity
@Table(name = "CMT_NOTAS$AUD", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "CmtNotasAuditoriaMgl.findAll", query = "SELECT c FROM CmtNotasAuditoriaMgl c")})
public class CmtNotasAuditoriaMgl implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)   
    @NotNull
    @Column(name = "NOTASAUD_ID", nullable = false)
    private BigDecimal notasaudId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NOTAS_ID", nullable = false)
    private CmtNotasMgl notasId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPO_NOTA")
    private CmtBasicaMgl tipoNotaObj;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUBEDIFICIO_ID")
    private CmtSubEdificioMgl subEdificioObj;
    @Column(name = "DESCRIPCION", nullable = true, length = 200)
    private String descripcion;
    @Column(name = "NOTA", nullable = true, length = 4000)
    private String nota;
    @Column(name = "NOMBRE_ARCHIVO", nullable = true, length = 200)
    private String nombreArchivo;
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
    
    @Column(name = "AUD_ACTION",length = 3)
    private String accionAuditoria;
    @Column(name = "AUD_TIMESTAMP")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaAuditoria;
    @Column(name = "AUD_USER",length = 30)
    private String usuarioAuditoria;
    @Column(name = "SESSION_ID")
    private BigDecimal sesionAuditoria;

    public BigDecimal getNotasaudId(){
      return notasaudId;
    }

    public void setNotasaudId(BigDecimal notasaudId){
      this.notasaudId = notasaudId;
    }

    public CmtNotasMgl getNotasId(){
      return notasId;
    }

    public void setNotasId(CmtNotasMgl notasId){
      this.notasId = notasId;
    }

    public CmtBasicaMgl getTipoNotaObj(){
      return tipoNotaObj;
    }

    public void setTipoNotaObj(CmtBasicaMgl tipoNotaObj){
      this.tipoNotaObj = tipoNotaObj;
    }

    public CmtSubEdificioMgl getSubEdificioObj(){
      return subEdificioObj;
    }

    public void setSubEdificioObj(CmtSubEdificioMgl subEdificioObj){
      this.subEdificioObj = subEdificioObj;
    }

    public String getDescripcion(){
      return descripcion;
    }

    public void setDescripcion(String descripcion){
      this.descripcion = descripcion;
    }

    public String getNota(){
      return nota;
    }

    public void setNota(String nota){
      this.nota = nota;
    }

    public String getNombreArchivo(){
      return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo){
      this.nombreArchivo = nombreArchivo;
    }

    public Date getFechaCreacion(){
      return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion){
      this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioCreacion(){
      return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion){
      this.usuarioCreacion = usuarioCreacion;
    }

    public int getPerfilCreacion(){
      return perfilCreacion;
    }

    public void setPerfilCreacion(int perfilCreacion){
      this.perfilCreacion = perfilCreacion;
    }

    public Date getFechaEdicion(){
      return fechaEdicion;
    }

    public void setFechaEdicion(Date fechaEdicion){
      this.fechaEdicion = fechaEdicion;
    }

    public String getUsuarioEdicion(){
      return usuarioEdicion;
    }

    public void setUsuarioEdicion(String usuarioEdicion){
      this.usuarioEdicion = usuarioEdicion;
    }

    public int getPerfilEdicion(){
      return perfilEdicion;
    }

    public void setPerfilEdicion(int perfilEdicion){
      this.perfilEdicion = perfilEdicion;
    }

    public int getEstadoRegistro(){
      return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro){
      this.estadoRegistro = estadoRegistro;
    }

    public String getAccionAuditoria(){
      return accionAuditoria;
    }

    public void setAccionAuditoria(String accionAuditoria){
      this.accionAuditoria = accionAuditoria;
    }

    public Date getFechaAuditoria(){
      return fechaAuditoria;
    }

    public void setFechaAuditoria(Date fechaAuditoria){
      this.fechaAuditoria = fechaAuditoria;
    }

    public String getUsuarioAuditoria(){
      return usuarioAuditoria;
    }

    public void setUsuarioAuditoria(String usuarioAuditoria){
      this.usuarioAuditoria = usuarioAuditoria;
    }

    public BigDecimal getSesionAuditoria(){
      return sesionAuditoria;
    }

    public void setSesionAuditoria(BigDecimal sesionAuditoria){
      this.sesionAuditoria = sesionAuditoria;
    }
    
    
}
