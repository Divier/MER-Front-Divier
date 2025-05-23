package co.com.claro.mgl.ws.cm.request;

import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.visitasTecnicas.entities.CityEntity;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase para mapear la petición en el proceso de solicitudes de escalamiento de HHPP
 *
 * @author José René Miranda de la O
 * @version 1.0, 2022/05/25
 */
@XmlRootElement
public class RequestCreaSolicitudEscalamientoHHPP implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@XmlElement
    private String tipoSolicitud;
    @XmlElement
    private String departamento;
    @XmlElement
    private BigDecimal ciudad;
    @XmlElement
    private BigDecimal centroPoblado;
    @XmlElement
    private String area;
    @XmlElement
    private String tipoTecnologia;
    @XmlElement
    private String tipoDireccion;
    @XmlElement
    private String direccion;
    @XmlElement
    private String complemento;
    @XmlElement
    private String apartamento;
    @XmlElement
    private String fechaInicioSolicitud;
    @XmlElement
    private Date fechaFinSolicitud;
    @XmlElement
    private String ccmm;
    @XmlElement
    private String solicitudOT;
    @XmlElement
    private String tipoGestion;
    @XmlElement
    private String observaciones;
    @XmlElement
    private String solicitante;
    @XmlElement
    private String telefono;
    @XmlElement
    private String correo;
    @XmlElement
    private List<File> adjuntos;
	@XmlElement
	private String idUsuario;
	@XmlElement
	private String usuarioVT;
	@XmlElement
	private int perfillVT;
	@XmlElement
	private String tipoHHPP;
	@XmlElement
	private DrDireccion drDireccion;
	@XmlElement
	private CityEntity cityEntity;
	@XmlElement
	private CmtBasicaMgl cmtBasicaMgl;
	@XmlElement
	private String tipoSolicitudStr;

	/**
	 * @return the tipoSolicitud
	 */
	public String getTipoSolicitud() {
		return tipoSolicitud;
	}
	/**
	 * @param tipoSolicitud the tipoSolicitud to set
	 */
	public void setTipoSolicitud(String tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}
	/**
	 * @return the departamento
	 */
	public String getDepartamento() {
		return departamento;
	}
	/**
	 * @param departamento the departamento to set
	 */
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	/**
	 * @return the ciudad
	 */
	public BigDecimal getCiudad() {
		return ciudad;
	}
	/**
	 * @param ciudad the ciudad to set
	 */
	public void setCiudad(BigDecimal ciudad) {
		this.ciudad = ciudad;
	}
	/**
	 * @return the centroPoblado
	 */
	public BigDecimal getCentroPoblado() {
		return centroPoblado;
	}
	/**
	 * @param centroPoblado the centroPoblado to set
	 */
	public void setCentroPoblado(BigDecimal centroPoblado) {
		this.centroPoblado = centroPoblado;
	}
	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}
	/**
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}
	/**
	 * @return the tipoTecnologia
	 */
	public String getTipoTecnologia() {
		return tipoTecnologia;
	}
	/**
	 * @param tipoTecnologia the tipoTecnologia to set
	 */
	public void setTipoTecnologia(String tipoTecnologia) {
		this.tipoTecnologia = tipoTecnologia;
	}
	/**
	 * @return the tipoDireccion
	 */
	public String getTipoDireccion() {
		return tipoDireccion;
	}
	/**
	 * @param tipoDireccion the tipoDireccion to set
	 */
	public void setTipoDireccion(String tipoDireccion) {
		this.tipoDireccion = tipoDireccion;
	}
	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}
	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	/**
	 * @return the complemento
	 */
	public String getComplemento() {
		return complemento;
	}
	/**
	 * @param complemento the complemento to set
	 */
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	/**
	 * @return the apartamento
	 */
	public String getApartamento() {
		return apartamento;
	}
	/**
	 * @param apartamento the apartamento to set
	 */
	public void setApartamento(String apartamento) {
		this.apartamento = apartamento;
	}
	/**
	 * @return the fechaInicioSolicitud
	 */
	public String getFechaInicioSolicitud() {
		return fechaInicioSolicitud;
	}
	/**
	 * @param fechaInicioSolicitud the fechaInicioSolicitud to set
	 */
	public void setFechaInicioSolicitud(String fechaInicioSolicitud) {
		this.fechaInicioSolicitud = fechaInicioSolicitud;
	}
	/**
	 * @return the fechaFinSolicitud
	 */
	public Date getFechaFinSolicitud() {
		return fechaFinSolicitud;
	}
	/**
	 * @param fechaFinSolicitud the fechaFinSolicitud to set
	 */
	public void setFechaFinSolicitud(Date fechaFinSolicitud) {
		this.fechaFinSolicitud = fechaFinSolicitud;
	}
	/**
	 * @return the ccmm
	 */
	public String getCcmm() {
		return ccmm;
	}
	/**
	 * @param ccmm the ccmm to set
	 */
	public void setCcmm(String ccmm) {
		this.ccmm = ccmm;
	}
	/**
	 * @return the solicitudOT
	 */
	public String getSolicitudOT() {
		return solicitudOT;
	}
	/**
	 * @param solicitudOT the solicitudOT to set
	 */
	public void setSolicitudOT(String solicitudOT) {
		this.solicitudOT = solicitudOT;
	}
	/**
	 * @return the tipoGestion
	 */
	public String getTipoGestion() {
		return tipoGestion;
	}
	/**
	 * @param tipoGestion the tipoGestion to set
	 */
	public void setTipoGestion(String tipoGestion) {
		this.tipoGestion = tipoGestion;
	}
	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}
	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	/**
	 * @return the solicitante
	 */
	public String getSolicitante() {
		return solicitante;
	}
	/**
	 * @param solicitante the solicitante to set
	 */
	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}
	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}
	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	/**
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}
	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	/**
	 * @return the adjuntos
	 */
	public List<File> getAdjuntos() {
		return adjuntos;
	}
	/**
	 * @param adjuntos the adjuntos to set
	 */
	public void setAdjuntos(List<File> adjuntos) {
		this.adjuntos = adjuntos;
	}

	/**
	 * get field @XmlElement
	 *
	 * @return usuarioVT @XmlElement

	 */
	public String getUsuarioVT() {
		return this.usuarioVT;
	}

	/**
	 * set field @XmlElement
	 *
	 * @param usuarioVT @XmlElement

	 */
	public void setUsuarioVT(String usuarioVT) {
		this.usuarioVT = usuarioVT;
	}

	/**
	 * get field @XmlElement
	 *
	 * @return perfillVT @XmlElement

	 */
	public int getPerfillVT() {
		return this.perfillVT;
	}

	/**
	 * set field @XmlElement
	 *
	 * @param perfillVT @XmlElement

	 */
	public void setPerfillVT(int perfillVT) {
		this.perfillVT = perfillVT;
	}

	/**
	 * get field @XmlElement
	 *
	 * @return idUsuario @XmlElement

	 */
	public String getIdUsuario() {
		return this.idUsuario;
	}

	/**
	 * set field @XmlElement
	 *
	 * @param idUsuario @XmlElement

	 */
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * get field @XmlElement
	 *
	 * @return tipoHHPP @XmlElement

	 */
	public String getTipoHHPP() {
		return this.tipoHHPP;
	}

	/**
	 * set field @XmlElement
	 *
	 * @param tipoHHPP @XmlElement

	 */
	public void setTipoHHPP(String tipoHHPP) {
		this.tipoHHPP = tipoHHPP;
	}

	/**
	 * get field @XmlElement
	 *
	 * @return drDireccion @XmlElement

	 */
	public DrDireccion getDrDireccion() {
		return this.drDireccion;
	}

	/**
	 * set field @XmlElement
	 *
	 * @param drDireccion @XmlElement

	 */
	public void setDrDireccion(DrDireccion drDireccion) {
		this.drDireccion = drDireccion;
	}

	/**
	 * get field @XmlElement
	 *
	 * @return cityEntity @XmlElement

	 */
	public CityEntity getCityEntity() {
		return this.cityEntity;
	}

	/**
	 * set field @XmlElement
	 *
	 * @param cityEntity @XmlElement

	 */
	public void setCityEntity(CityEntity cityEntity) {
		this.cityEntity = cityEntity;
	}

	/**
	 * get field @XmlElement
	 *
	 * @return cmtBasicaMgl @XmlElement

	 */
	public CmtBasicaMgl getCmtBasicaMgl() {
		return this.cmtBasicaMgl;
	}

	/**
	 * set field @XmlElement
	 *
	 * @param cmtBasicaMgl @XmlElement

	 */
	public void setCmtBasicaMgl(CmtBasicaMgl cmtBasicaMgl) {
		this.cmtBasicaMgl = cmtBasicaMgl;
	}

	/**
	 * get field @XmlElement
	 *
	 * @return tipoSolicitudStr @XmlElement

	 */
	public String getTipoSolicitudStr() {
		return this.tipoSolicitudStr;
	}

	/**
	 * set field @XmlElement
	 *
	 * @param tipoSolicitudStr @XmlElement

	 */
	public void setTipoSolicitudStr(String tipoSolicitudStr) {
		this.tipoSolicitudStr = tipoSolicitudStr;
	}
}
