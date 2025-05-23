package co.com.claro.mgl.jpa.entities;

import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@Entity
@Table(name = "MGL_GEOGRAFICO_POLITICO", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GeograficoPoliticoMgl.findAll", query = "SELECT g FROM GeograficoPoliticoMgl g where g.estadoRegistro = 1 ORDER BY g.gpoNombre ASC"),
    @NamedQuery(name =  "GeograficoPoliticoMgl.findCentrosPoblados", query = "SELECT g FROM GeograficoPoliticoMgl g WHERE g.gpoTipo = 'CENTRO POBLADO' AND g.geoGpoId = :myGeoGpoId and g.estadoRegistro = 1 ORDER BY g.gpoNombre ASC")
})
public class GeograficoPoliticoMgl implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "GEOGRAFICO_POLITICO_ID", nullable = false)
    @SequenceGenerator(
            name = "GeograficoPoliticoMgl.GeograficoPoliticoMgl",
            sequenceName = "MGL_SCHEME.MGL_GEOGRAFICO_POLITICO_SQ", allocationSize = 1)
    @GeneratedValue(generator = "GeograficoPoliticoMgl.GeograficoPoliticoMgl")
    private BigDecimal gpoId;
    @Column(name = "GEOGRAFICO_GEO_POLITICO_ID")
    private BigDecimal geoGpoId;
    @Column(name = "NOMBRE")
    private String gpoNombre;
    @Column(name = "CODIGO")
    private String gpoCodigo;
    @Column(name = "TIPO", nullable = false)
    private String gpoTipo;
    @Column(name = "MULTIORIGEN")
    private String gpoMultiorigen;
    @Column(name = "COD_TIPO_DIRECCION")
    private String gpoCodTipoDireccion;
    @Column(name = "CODIGODANE")
    private String geoCodigoDane;
    @Column(name = "DEPARTAMENTO_CODIGO_ZIP")
    private String gpoDepartamentoCodigoZip;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Column(name = "FECHA_EDICION")
    @Temporal(TemporalType.DATE)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @ManyToOne(fetch = FetchType.LAZY)    
    @JoinColumn(name = "REGIONAL_TECNICA_BASICA_ID")
    private CmtBasicaMgl regionalTecnicaObj;

    @Column(name = "NUMERO_MANZANA")
    private String numManzana;
    @Column (name = "CORREGIMIENTO")
    private String corregimiento;

    @Transient
    private boolean selected = false;
    @Transient
    private boolean selectedUNI = false;
    @Transient
    private boolean selectedBI = false;
    @Transient
    private boolean selectedDTH = false;
    @Transient
    private String nombreCiudad;
    
    
    private static final Logger LOGGER = LogManager.getLogger(GeograficoPoliticoMgl.class);

    public BigDecimal getGpoId() {
        return gpoId;
    }

    public void setGpoId(BigDecimal gpoId) {
        this.gpoId = gpoId;
    }

    public BigDecimal getGeoGpoId() {
        return geoGpoId;
    }

    public void setGeoGpoId(BigDecimal geoGpoId) {
        this.geoGpoId = geoGpoId;
    }

    public String getGpoNombre() {
        return gpoNombre;
    }

    public void setGpoNombre(String gpoNombre) {
        this.gpoNombre = gpoNombre;
    }

    public String getGpoCodigo() {
        return gpoCodigo;
    }

    public void setGpoCodigo(String gpoCodigo) {
        this.gpoCodigo = gpoCodigo;
    }

    public String getGpoTipo() {
        return gpoTipo;
    }

    public void setGpoTipo(String gpoTipo) {
        this.gpoTipo = gpoTipo;
    }

    public String getGpoMultiorigen() {
        return gpoMultiorigen;
    }

    public void setGpoMultiorigen(String gpoMultiorigen) {
        this.gpoMultiorigen = gpoMultiorigen;
    }

    public String getGpoCodTipoDireccion() {
        return gpoCodTipoDireccion;
    }

    public void setGpoCodTipoDireccion(String gpoCodTipoDireccion) {
        this.gpoCodTipoDireccion = gpoCodTipoDireccion;
    }

    public String getGeoCodigoDane() {
        return geoCodigoDane;
    }

    public void setGeoCodigoDane(String geoCodigoDane) {
        this.geoCodigoDane = geoCodigoDane;
    }

    public String getGpoDepartamentoCodigoZip() {
        return gpoDepartamentoCodigoZip;
    }

    public void setGpoDepartamentoCodigoZip(String gpoDepartamentoCodigoZip) {
        this.gpoDepartamentoCodigoZip = gpoDepartamentoCodigoZip;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelectedUNI() {
        return selectedUNI;
    }

    public void setSelectedUNI(boolean selectedUNI) {
        this.selectedUNI = selectedUNI;
    }

    public boolean isSelectedBI() {
        return selectedBI;
    }

    public void setSelectedBI(boolean selectedBI) {
        this.selectedBI = selectedBI;
    }

    public boolean isSelectedDTH() {
        return selectedDTH;
    }

    public void setSelectedDTH(boolean selectedDTH) {
        this.selectedDTH = selectedDTH;
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


    @Override
    public String toString(){
        return "[("+gpoId.toString().trim()+")"+gpoNombre.trim()+"]";
    }

     /*
     * Obtiene regional tecnica cp
     * getRegionalTecnicaObj()
     * @return CmtBasicaMgl 
     */
    public CmtBasicaMgl getRegionalTecnicaObj() {
        return regionalTecnicaObj;
    }
    /*
     * set regional tecnica cp
     * setRegionalTecnicaObj()
     * @Param String CmtBasicaMgl
     */
    public void setRegionalTecnicaObj(CmtBasicaMgl regionalTecnicaObj) {
        this.regionalTecnicaObj = regionalTecnicaObj;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    public int getPerfilCreacion() {
        return perfilCreacion;
    }

    public void setPerfilCreacion(int perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
    }

    public String getNumManzana() {
        return numManzana;
    }

    public void setNumManzana(String numManzana) {
        this.numManzana = numManzana;
    }

    public String getCorregimiento() {
        return corregimiento;
    }

    public void setCorregimiento(String corregimiento) {
        this.corregimiento = corregimiento;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }
    
    
    

    @Override
    public GeograficoPoliticoMgl clone() throws CloneNotSupportedException {
        GeograficoPoliticoMgl obj = null;
        try {
            obj = (GeograficoPoliticoMgl) super.clone();
        } catch (CloneNotSupportedException ex) {
			String msg = "Se produjo un error al momento de ejecutar el método '"+
			ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			LOGGER.error(msg);
            throw ex;
        }

        return obj;
    }
 
}
