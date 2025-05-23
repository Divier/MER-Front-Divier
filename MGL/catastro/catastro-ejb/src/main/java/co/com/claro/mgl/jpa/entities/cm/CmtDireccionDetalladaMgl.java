/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

import co.com.claro.mgl.dtos.CmtDireccionDetalladaMglDto;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.FactibilidadMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.utils.EntityUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author valbuenayf
 */
@Entity
@Table(name = "MGL_DIRECCION_DETALLADA", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtDireccionDetalladaMgl.findAll", query = "SELECT c FROM CmtDireccionDetalladaMgl c")
    ,
    @NamedQuery(name = "CmtDireccionDetalladaMgl.findByDireccionDetalladaId", query = "SELECT c FROM CmtDireccionDetalladaMgl c WHERE c.direccionDetalladaId = :direccionDetalladaId and c.estadoRegistro = 1")
    ,
    @NamedQuery(name = "CmtDireccionDetalladaMgl.findByDireccionDetalladaIdCiudad", query = "SELECT c FROM CmtDireccionDetalladaMgl c WHERE c.direccion.ubicacion.gpoIdObj.gpoId = :idCiudad and c.direccionTexto like :direccionTexto and c.estadoRegistro = 1")
    ,
    @NamedQuery(name = "CmtDireccionDetalladaMgl.findByDireccionIx", query = "SELECT c FROM CmtDireccionDetalladaMgl c WHERE c.direccionIx = :direccionIx and c.estadoRegistro = 1")
    ,
     @NamedQuery(name = "CmtDireccionDetalladaMgl.findByDireccionSubDireccion", query = "SELECT c FROM CmtDireccionDetalladaMgl c WHERE c.direccion = :direccion and c.subDireccion = :subDireccion AND c.estadoRegistro = 1")
})
public class CmtDireccionDetalladaMgl implements Serializable, Comparator<CmtDireccionDetalladaMgl>, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtDireccionDetalladaMgl.cmtDireccionDetalladaSq",
            sequenceName = "MGL_SCHEME.MGL_DIRECCION_DETALLADA_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtDireccionDetalladaMgl.cmtDireccionDetalladaSq")
    @NotNull
    @Column(name = "DIRECCION_DETALLADA_ID")
    private BigDecimal direccionDetalladaId;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "DIRECCION_IX")
    private String direccionIx;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "ID_TIPO_DIRECCION")
    private String idTipoDireccion;

    @Size(max = 1)
    @Column(name = "DIR_PRINC_ALT")
    private String dirPrincAlt;

    @Size(max = 200)
    @Column(name = "BARRIO")
    private String barrio;

    @Size(max = 200)
    @Column(name = "TIPO_VIA_PRINCIPAL")
    private String tipoViaPrincipal;

    @Size(max = 200)
    @Column(name = "NUM_VIA_PRINCIPAL")
    private String numViaPrincipal;

    @Size(max = 200)
    @Column(name = "LT_VIA_PRINCIPAL")
    private String ltViaPrincipal;

    @Size(max = 200)
    @Column(name = "NL_POST_VIA_P")
    private String nlPostViaP;

    @Size(max = 200)
    @Column(name = "BIS_VIA_PRINCIPAL")
    private String bisViaPrincipal;

    @Size(max = 200)
    @Column(name = "CUAD_VIA_PRINCIPAL")
    private String cuadViaPrincipal;

    @Size(max = 200)
    @Column(name = "TIPO_VIA_GENERADORA")
    private String tipoViaGeneradora;

    @Size(max = 200)
    @Column(name = "NUM_VIA_GENERADORA")
    private String numViaGeneradora;

    @Size(max = 200)
    @Column(name = "LT_VIA_GENERADORA")
    private String ltViaGeneradora;

    @Size(max = 200)
    @Column(name = "NL_POST_VIA_G")
    private String nlPostViaG;

    @Size(max = 200)
    @Column(name = "BIS_VIA_GENERADORA")
    private String bisViaGeneradora;

    @Size(max = 200)
    @Column(name = "CUAD_VIA_GENERADORA")
    private String cuadViaGeneradora;

    @Size(max = 200)
    @Column(name = "PLACA_DIRECCION")
    private String placaDireccion;

    @Size(max = 200)
    @Column(name = "CP_TIPO_NIVEL1")
    private String cpTipoNivel1;

    @Size(max = 200)
    @Column(name = "CP_TIPO_NIVEL2")
    private String cpTipoNivel2;

    @Size(max = 200)
    @Column(name = "CP_TIPO_NIVEL3")
    private String cpTipoNivel3;

    @Size(max = 200)
    @Column(name = "CP_TIPO_NIVEL4")
    private String cpTipoNivel4;

    @Size(max = 200)
    @Column(name = "CP_TIPO_NIVEL5")
    private String cpTipoNivel5;

    @Size(max = 200)
    @Column(name = "CP_TIPO_NIVEL6")
    private String cpTipoNivel6;

    @Size(max = 200)
    @Column(name = "CP_VALOR_NIVEL1")
    private String cpValorNivel1;

    @Size(max = 200)
    @Column(name = "CP_VALOR_NIVEL2")
    private String cpValorNivel2;

    @Size(max = 200)
    @Column(name = "CP_VALOR_NIVEL3")
    private String cpValorNivel3;

    @Size(max = 200)
    @Column(name = "CP_VALOR_NIVEL4")
    private String cpValorNivel4;

    @Size(max = 200)
    @Column(name = "CP_VALOR_NIVEL5")
    private String cpValorNivel5;

    @Size(max = 200)
    @Column(name = "CP_VALOR_NIVEL6")
    private String cpValorNivel6;

    @Size(max = 200)
    @Column(name = "MZ_TIPO_NIVEL1")
    private String mzTipoNivel1;

    @Size(max = 200)
    @Column(name = "MZ_TIPO_NIVEL2")
    private String mzTipoNivel2;

    @Size(max = 200)
    @Column(name = "MZ_TIPO_NIVEL3")
    private String mzTipoNivel3;

    @Size(max = 200)
    @Column(name = "MZ_TIPO_NIVEL4")
    private String mzTipoNivel4;

    @Size(max = 200)
    @Column(name = "MZ_TIPO_NIVEL5")
    private String mzTipoNivel5;

    @Size(max = 200)
    @Column(name = "MZ_VALOR_NIVEL1")
    private String mzValorNivel1;

    @Size(max = 200)
    @Column(name = "MZ_VALOR_NIVEL2")
    private String mzValorNivel2;

    @Size(max = 200)
    @Column(name = "MZ_VALOR_NIVEL3")
    private String mzValorNivel3;

    @Size(max = 200)
    @Column(name = "MZ_VALOR_NIVEL4")
    private String mzValorNivel4;

    @Size(max = 200)
    @Column(name = "MZ_VALOR_NIVEL5")
    private String mzValorNivel5;

    @Size(max = 100)
    @Column(name = "ID_DIR_CATASTRO")
    private String idDirCatastro;

    @Size(max = 200)
    @Column(name = "MZ_TIPO_NIVEL6")
    private String mzTipoNivel6;

    @Size(max = 200)
    @Column(name = "MZ_VALOR_NIVEL6")
    private String mzValorNivel6;

    @Size(max = 200)
    @Column(name = "IT_TIPO_PLACA")
    private String itTipoPlaca;

    @Size(max = 200)
    @Column(name = "IT_VALOR_PLACA")
    private String itValorPlaca;

    @Size(max = 1)
    @Column(name = "ESTADO_DIR_GEO")
    private String estadoDirGeo;

    @Size(max = 20)
    @Column(name = "LETRA3G")
    private String letra3G;

    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;

    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @Size(max = 20)
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;

    @Column(name = "PERFIL_CREACION")
    private Long perfilCreacion;

    @Column(name = "FECHA_EDICION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEdicion;

    @Size(max = 20)
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;

    @Size(max = 15)
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;

    @Size(max = 200)
    @Column(name = "DIRECCION_TEXTO")
    private String direccionTexto;

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "SUB_DIRECCION_ID", nullable = true)
    private SubDireccionMgl subDireccion;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DIRECCION_ID")
    private DireccionMgl direccion;

    @OneToMany(mappedBy = "cmtDireccionDetallada", fetch = FetchType.LAZY)
    private List<CmtDireccionMgl> cmtDireccion;

    @ManyToOne
    @JoinColumn(name = "DIRECCION_DETALLADA_ID", referencedColumnName = "DIRECCION_DETALLADA_ID", insertable=false, updatable=false)
    private FactibilidadMgl factibilidadMgl;
    
    @Column(name = "INDICACIONES")
    private String indicaciones;

    @Transient
    private boolean selected = false;
    @Transient
    private String departamento;
    @Transient
    private String ciudad;
    @Transient
    private boolean hhppExistente;
    @Transient
    private boolean isNodoCertificado;
    @Transient
    private String numeroCuentaMatriz; 
    @Transient
    private HhppMgl hhppMgl; 
    @Transient
    private String nombreSubedificio;
    @Transient
    private transient BigDecimal direccionId;
    @Transient
    private transient BigDecimal subdireccionId;
    @Transient
    private transient BigDecimal idFactibilidad;
    
    

    public CmtDireccionDetalladaMgl() {
    }

    public CmtDireccionDetalladaMgl(BigDecimal direccionDetalladaId) {
        this.direccionDetalladaId = direccionDetalladaId;
    }

    public CmtDireccionDetalladaMgl(BigDecimal direccionDetalladaId, String direccionIx, String idTipoDireccion) {
        this.direccionDetalladaId = direccionDetalladaId;
        this.direccionIx = direccionIx;
        this.idTipoDireccion = idTipoDireccion;
    }
    
    
    /**
     * Realiza la conversi&oacute;n de Entidad a Data Transfer Object.
     * @return Data Transfer Object asociado al presente objeto.
     */
    @Transient
    public CmtDireccionDetalladaMglDto convertirADto() {
        CmtDireccionDetalladaMglDto cmtDireccionDetalladaMglDto = new CmtDireccionDetalladaMglDto();
        
        EntityUtils<CmtDireccionDetalladaMgl, CmtDireccionDetalladaMglDto> entityUtils = new EntityUtils(cmtDireccionDetalladaMglDto);
        cmtDireccionDetalladaMglDto = entityUtils.convertirEntidadADto(this);
        
        return (cmtDireccionDetalladaMglDto);
    }
    

    public BigDecimal getDireccionDetalladaId() {
        return direccionDetalladaId;
    }

    public void setDireccionDetalladaId(BigDecimal direccionDetalladaId) {
        this.direccionDetalladaId = direccionDetalladaId;
    }

    public String getDireccionIx() {
        return direccionIx;
    }

    public void setDireccionIx(String direccionIx) {
        this.direccionIx = direccionIx;
    }

    public String getIdTipoDireccion() {
        return idTipoDireccion;
    }

    public void setIdTipoDireccion(String idTipoDireccion) {
        this.idTipoDireccion = idTipoDireccion;
    }

    public String getDirPrincAlt() {
        return dirPrincAlt;
    }

    public void setDirPrincAlt(String dirPrincAlt) {
        this.dirPrincAlt = dirPrincAlt;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getTipoViaPrincipal() {
        return tipoViaPrincipal;
    }

    public void setTipoViaPrincipal(String tipoViaPrincipal) {
        this.tipoViaPrincipal = tipoViaPrincipal;
    }

    public String getNumViaPrincipal() {
        return numViaPrincipal;
    }

    public void setNumViaPrincipal(String numViaPrincipal) {
        this.numViaPrincipal = numViaPrincipal;
    }

    public String getLtViaPrincipal() {
        return ltViaPrincipal;
    }

    public void setLtViaPrincipal(String ltViaPrincipal) {
        this.ltViaPrincipal = ltViaPrincipal;
    }

    public String getNlPostViaP() {
        return nlPostViaP;
    }

    public void setNlPostViaP(String nlPostViaP) {
        this.nlPostViaP = nlPostViaP;
    }

    public String getBisViaPrincipal() {
        return bisViaPrincipal;
    }

    public void setBisViaPrincipal(String bisViaPrincipal) {
        this.bisViaPrincipal = bisViaPrincipal;
    }

    public String getCuadViaPrincipal() {
        return cuadViaPrincipal;
    }

    public void setCuadViaPrincipal(String cuadViaPrincipal) {
        this.cuadViaPrincipal = cuadViaPrincipal;
    }

    public String getTipoViaGeneradora() {
        return tipoViaGeneradora;
    }

    public void setTipoViaGeneradora(String tipoViaGeneradora) {
        this.tipoViaGeneradora = tipoViaGeneradora;
    }

    public String getNumViaGeneradora() {
        return numViaGeneradora;
    }

    public void setNumViaGeneradora(String numViaGeneradora) {
        this.numViaGeneradora = numViaGeneradora;
    }

    public String getLtViaGeneradora() {
        return ltViaGeneradora;
    }

    public void setLtViaGeneradora(String ltViaGeneradora) {
        this.ltViaGeneradora = ltViaGeneradora;
    }

    public String getNlPostViaG() {
        return nlPostViaG;
    }

    public void setNlPostViaG(String nlPostViaG) {
        this.nlPostViaG = nlPostViaG;
    }

    public String getBisViaGeneradora() {
        return bisViaGeneradora;
    }

    public void setBisViaGeneradora(String bisViaGeneradora) {
        this.bisViaGeneradora = bisViaGeneradora;
    }

    public String getCuadViaGeneradora() {
        return cuadViaGeneradora;
    }

    public void setCuadViaGeneradora(String cuadViaGeneradora) {
        this.cuadViaGeneradora = cuadViaGeneradora;
    }

    public String getPlacaDireccion() {
        return placaDireccion;
    }

    public void setPlacaDireccion(String placaDireccion) {
        this.placaDireccion = placaDireccion;
    }

    public String getCpTipoNivel1() {
        return cpTipoNivel1;
    }

    public void setCpTipoNivel1(String cpTipoNivel1) {
        this.cpTipoNivel1 = cpTipoNivel1;
    }

    public String getCpTipoNivel2() {
        return cpTipoNivel2;
    }

    public void setCpTipoNivel2(String cpTipoNivel2) {
        this.cpTipoNivel2 = cpTipoNivel2;
    }

    public String getCpTipoNivel3() {
        return cpTipoNivel3;
    }

    public void setCpTipoNivel3(String cpTipoNivel3) {
        this.cpTipoNivel3 = cpTipoNivel3;
    }

    public String getCpTipoNivel4() {
        return cpTipoNivel4;
    }

    public void setCpTipoNivel4(String cpTipoNivel4) {
        this.cpTipoNivel4 = cpTipoNivel4;
    }

    public String getCpTipoNivel5() {
        return cpTipoNivel5;
    }

    public void setCpTipoNivel5(String cpTipoNivel5) {
        this.cpTipoNivel5 = cpTipoNivel5;
    }

    public String getCpTipoNivel6() {
        return cpTipoNivel6;
    }

    public void setCpTipoNivel6(String cpTipoNivel6) {
        this.cpTipoNivel6 = cpTipoNivel6;
    }

    public String getCpValorNivel1() {
        return cpValorNivel1;
    }

    public void setCpValorNivel1(String cpValorNivel1) {
        this.cpValorNivel1 = cpValorNivel1;
    }

    public String getCpValorNivel2() {
        return cpValorNivel2;
    }

    public void setCpValorNivel2(String cpValorNivel2) {
        this.cpValorNivel2 = cpValorNivel2;
    }

    public String getCpValorNivel3() {
        return cpValorNivel3;
    }

    public void setCpValorNivel3(String cpValorNivel3) {
        this.cpValorNivel3 = cpValorNivel3;
    }

    public String getCpValorNivel4() {
        return cpValorNivel4;
    }

    public void setCpValorNivel4(String cpValorNivel4) {
        this.cpValorNivel4 = cpValorNivel4;
    }

    public String getCpValorNivel5() {
        return cpValorNivel5;
    }

    public void setCpValorNivel5(String cpValorNivel5) {
        this.cpValorNivel5 = cpValorNivel5;
    }

    public String getCpValorNivel6() {
        return cpValorNivel6;
    }

    public void setCpValorNivel6(String cpValorNivel6) {
        this.cpValorNivel6 = cpValorNivel6;
    }

    public String getMzTipoNivel1() {
        return mzTipoNivel1;
    }

    public void setMzTipoNivel1(String mzTipoNivel1) {
        this.mzTipoNivel1 = mzTipoNivel1;
    }

    public String getMzTipoNivel2() {
        return mzTipoNivel2;
    }

    public void setMzTipoNivel2(String mzTipoNivel2) {
        this.mzTipoNivel2 = mzTipoNivel2;
    }

    public String getMzTipoNivel3() {
        return mzTipoNivel3;
    }

    public void setMzTipoNivel3(String mzTipoNivel3) {
        this.mzTipoNivel3 = mzTipoNivel3;
    }

    public String getMzTipoNivel4() {
        return mzTipoNivel4;
    }

    public void setMzTipoNivel4(String mzTipoNivel4) {
        this.mzTipoNivel4 = mzTipoNivel4;
    }

    public String getMzTipoNivel5() {
        return mzTipoNivel5;
    }

    public void setMzTipoNivel5(String mzTipoNivel5) {
        this.mzTipoNivel5 = mzTipoNivel5;
    }

    public String getMzValorNivel1() {
        return mzValorNivel1;
    }

    public void setMzValorNivel1(String mzValorNivel1) {
        this.mzValorNivel1 = mzValorNivel1;
    }

    public String getMzValorNivel2() {
        return mzValorNivel2;
    }

    public void setMzValorNivel2(String mzValorNivel2) {
        this.mzValorNivel2 = mzValorNivel2;
    }

    public String getMzValorNivel3() {
        return mzValorNivel3;
    }

    public void setMzValorNivel3(String mzValorNivel3) {
        this.mzValorNivel3 = mzValorNivel3;
    }

    public String getMzValorNivel4() {
        return mzValorNivel4;
    }

    public void setMzValorNivel4(String mzValorNivel4) {
        this.mzValorNivel4 = mzValorNivel4;
    }

    public String getMzValorNivel5() {
        return mzValorNivel5;
    }

    public void setMzValorNivel5(String mzValorNivel5) {
        this.mzValorNivel5 = mzValorNivel5;
    }

    public String getIdDirCatastro() {
        return idDirCatastro;
    }

    public void setIdDirCatastro(String idDirCatastro) {
        this.idDirCatastro = idDirCatastro;
    }

    public String getMzTipoNivel6() {
        return mzTipoNivel6;
    }

    public void setMzTipoNivel6(String mzTipoNivel6) {
        this.mzTipoNivel6 = mzTipoNivel6;
    }

    public String getMzValorNivel6() {
        return mzValorNivel6;
    }

    public void setMzValorNivel6(String mzValorNivel6) {
        this.mzValorNivel6 = mzValorNivel6;
    }

    public String getItTipoPlaca() {
        return itTipoPlaca;
    }

    public void setItTipoPlaca(String itTipoPlaca) {
        this.itTipoPlaca = itTipoPlaca;
    }

    public String getItValorPlaca() {
        return itValorPlaca;
    }

    public void setItValorPlaca(String itValorPlaca) {
        this.itValorPlaca = itValorPlaca;
    }

    public String getEstadoDirGeo() {
        return estadoDirGeo;
    }

    public void setEstadoDirGeo(String estadoDirGeo) {
        this.estadoDirGeo = estadoDirGeo;
    }

    public String getLetra3G() {
        return letra3G;
    }

    public void setLetra3G(String letra3G) {
        this.letra3G = letra3G;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
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

    public Long getPerfilCreacion() {
        return perfilCreacion;
    }

    public void setPerfilCreacion(Long perfilCreacion) {
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

    public String getDireccionTexto() {
        return direccionTexto;
    }

    public void setDireccionTexto(String direccionTexto) {
        this.direccionTexto = direccionTexto;
    }

    public SubDireccionMgl getSubDireccion() {
        return subDireccion;
    }

    public void setSubDireccion(SubDireccionMgl subDireccion) {
        this.subDireccion = subDireccion;
    }

    public DireccionMgl getDireccion() {
        return direccion;
    }

    public void setDireccion(DireccionMgl direccion) {
        this.direccion = direccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (direccionDetalladaId != null ? direccionDetalladaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CmtDireccionDetalladaMgl)) {
            return false;
        }
        CmtDireccionDetalladaMgl other = (CmtDireccionDetalladaMgl) object;
        if ((this.direccionDetalladaId == null && other.direccionDetalladaId != null) || (this.direccionDetalladaId != null && !this.direccionDetalladaId.equals(other.direccionDetalladaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datos.CmtDireccionDetalladaMgl[ direccionDetalladaId=" + direccionDetalladaId + " ]";
    }

    @Override
    public CmtDireccionDetalladaMgl clone() throws CloneNotSupportedException {
        return (CmtDireccionDetalladaMgl) super.clone();
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public boolean isHhppExistente() {
        return hhppExistente;
    }

    public void setHhppExistente(boolean hhppExistente) {
        this.hhppExistente = hhppExistente;
    }

    public boolean isIsNodoCertificado() {
        return isNodoCertificado;
    }

    public void setIsNodoCertificado(boolean isNodoCertificado) {
        this.isNodoCertificado = isNodoCertificado;
    }
    
    public String getNumeroCuentaMatriz() {
        return numeroCuentaMatriz;
    }

    public void setNumeroCuentaMatriz(String numeroCuentaMatriz) {
        this.numeroCuentaMatriz = numeroCuentaMatriz;
    }

    public HhppMgl getHhppMgl() {
        return hhppMgl;
    }

    public void setHhppMgl(HhppMgl hhppMgl) {
        this.hhppMgl = hhppMgl;
    }

    @Override
    public int compare(CmtDireccionDetalladaMgl o1, CmtDireccionDetalladaMgl o2) {
        if (o1.getDireccionDetalladaId() == null) {
            return (o2.getDireccionDetalladaId() == null) ? 0 : -1;
        }
        if (o2.getDireccionDetalladaId() == null) {
            return 1;
        }
        return o1.getDireccionDetalladaId().compareTo(o2.getDireccionDetalladaId());
    }

    public String getNombreSubedificio() {
        return nombreSubedificio;
    }

    public void setNombreSubedificio(String nombreSubedificio) {
        this.nombreSubedificio = nombreSubedificio;
    }
    
    /**
     * Obtiene el identificador de la Direcci&oacute;n.
     * @return direccion.dirId
     */
    public BigDecimal getDireccionId() {
        if (direccion != null) {
            this.direccionId = this.direccion.getDirId();
        }
        return direccionId;
    }
    
    /**
     * Obtiene el identificador de la SubDirecci&oacute;n.
     * @return subDireccion.sdiId
     */
    public BigDecimal getSubdireccionId() {
        if (subDireccion != null) {
            this.subdireccionId = this.subDireccion.getSdiId();
        }
        return subdireccionId;
    }

    public BigDecimal getIdFactibilidad() {
        return idFactibilidad;
    }

    public void setIdFactibilidad(BigDecimal idFactibilidad) {
        this.idFactibilidad = idFactibilidad;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public List<CmtDireccionMgl> getCmtDireccion() {
        return cmtDireccion;
    }

    public void setCmtDireccion(List<CmtDireccionMgl> cmtDireccion) {
        this.cmtDireccion = cmtDireccion;
    }

    public FactibilidadMgl getFactibilidadMgl() {
        return factibilidadMgl;
    }

    public void setFactibilidadMgl(FactibilidadMgl factibilidadMgl) {
        this.factibilidadMgl = factibilidadMgl;
    }
}
