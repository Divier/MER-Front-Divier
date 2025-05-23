package co.com.claro.mgl.ws.cm.response;

import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Juan David Hernandez
 */
public class ResponseGeograficoPolitico implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    private BigDecimal gpoId;

    private BigDecimal geoGpoId;

    private String gpoNombre;

    private String gpoCodigo;

    private String gpoTipo;

    private String gpoMultiorigen;

    private String gpoCodTipoDireccion;

    private String geoCodigoDane;

    private String gpoDepartamentoCodigoZip;

    @Temporal(TemporalType.DATE)
    private Date gpoFechaCreacion;

    private String gpoUsuarioCreacion;

    @Temporal(TemporalType.DATE)
    private Date gpoFechaModificacion;

    private String gpoUsuarioModificacion;

    private int perfilEdicion;

    private int perfilCreacion;

    private int estadoRegistro;

    private CmtBasicaMgl regionalTecnicaObj;

    private String numManzana;
  
    private String corregimiento;    
    
    @Transient
    private boolean selected = false;
    @Transient
    private boolean selectedUNI = false;
    @Transient
    private boolean selectedBI = false;
    @Transient
    private boolean selectedDTH = false;


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

    public Date getGpoFechaCreacion() {
        return gpoFechaCreacion;
    }

    public void setGpoFechaCreacion(Date gpoFechaCreacion) {
        this.gpoFechaCreacion = gpoFechaCreacion;
    }

    public String getGpoUsuarioCreacion() {
        return gpoUsuarioCreacion;
    }

    public void setGpoUsuarioCreacion(String gpoUsuarioCreacion) {
        this.gpoUsuarioCreacion = gpoUsuarioCreacion;
    }

    public Date getGpoFechaModificacion() {
        return gpoFechaModificacion;
    }

    public void setGpoFechaModificacion(Date gpoFechaModificacion) {
        this.gpoFechaModificacion = gpoFechaModificacion;
    }

    public String getGpoUsuarioModificacion() {
        return gpoUsuarioModificacion;
    }

    public void setGpoUsuarioModificacion(String gpoUsuarioModificacion) {
        this.gpoUsuarioModificacion = gpoUsuarioModificacion;
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
    

    
    
}
