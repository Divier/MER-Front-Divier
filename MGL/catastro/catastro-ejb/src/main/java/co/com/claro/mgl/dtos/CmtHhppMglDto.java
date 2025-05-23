package co.com.claro.mgl.dtos;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Dto mapeo Hhpp swervicio web
 *
 * @author carlos.villa.ext@claro.com.co
 *
 * @versión 1.00.0000
 */
@XmlRootElement(name = "hhppMglRequest")
public class CmtHhppMglDto implements Serializable {

    @XmlElement
    private String hhppRepositorioId;
    @XmlElement
    private String direccionRepositorioId;
    @XmlElement
    private String nodoRrId;
    @XmlElement
    private String tipoHhppRepositorioId;
    @XmlElement
    private String tipoConexionRepositorioId;
    @XmlElement
    private String tipoRedRepositorioId;
    @XmlElement
    private String usuario;
    @XmlElement
    private String estadoHhppRepositorioId;
    @XmlElement
    private String idHhppRr;
    @XmlElement
    private String calleRr;
    @XmlElement
    private String placaRr;
    @XmlElement
    private String apartamentoRr;
    @XmlElement
    private String comunidadRr;
    @XmlElement
    private String divisionRr;
    @XmlElement
    private String estadoUnidadRr;
    @XmlElement
    private String vendedorRr;
    @XmlElement
    private String codigoPostalRr;
    @XmlElement
    private String tipoAcometidaRr;
    @XmlElement
    private String ultimaUbicacionNodoRr;
    @XmlElement
    private String headEndRr;
    @XmlElement
    private String nombreEdificioRr;
    @XmlElement
    private String tipoUnidadRr;
    @XmlElement
    private String tipoCblAcometidaRr;
    @XmlElement
    private String fechaAuditRr;
    @XmlElement
    private String notasAdd1HhppRr;
    @XmlElement
    private String notasAdd2HhppRr;
    @XmlElement
    private String notasAdd3HhppRr;
    @XmlElement
    private String notasAdd4HhppRr;
    @XmlElement
    private String subEdificioIdRepositorio;

    public String getHhppRepositorioId() {
        return hhppRepositorioId;
    }

    public void setHhppRepositorioId(String hhppRepositorioId) {
        this.hhppRepositorioId = hhppRepositorioId;
    }

    public String getDireccionRepositorioId() {
        return direccionRepositorioId;
    }

    public void setDireccionRepositorioId(String direccionRepositorioId) {
        this.direccionRepositorioId = direccionRepositorioId;
    }

    public String getNodoRrId() {
        return nodoRrId;
    }

    public void setNodoRrId(String nodoRrId) {
        this.nodoRrId = nodoRrId;
    }

    public String getTipoHhppRepositorioId() {
        return tipoHhppRepositorioId;
    }

    public void setTipoHhppRepositorioId(String tipoHhppRepositorioId) {
        this.tipoHhppRepositorioId = tipoHhppRepositorioId;
    }

    public String getTipoConexionRepositorioId() {
        return tipoConexionRepositorioId;
    }

    public void setTipoConexionRepositorioId(String tipoConexionRepositorioId) {
        this.tipoConexionRepositorioId = tipoConexionRepositorioId;
    }

    public String getTipoRedRepositorioId() {
        return tipoRedRepositorioId;
    }

    public void setTipoRedRepositorioId(String tipoRedRepositorioId) {
        this.tipoRedRepositorioId = tipoRedRepositorioId;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEstadoHhppRepositorioId() {
        return estadoHhppRepositorioId;
    }

    public void setEstadoHhppRepositorioId(String estadoHhppRepositorioId) {
        this.estadoHhppRepositorioId = estadoHhppRepositorioId;
    }

    public String getIdHhppRr() {
        return idHhppRr;
    }

    public void setIdHhppRr(String idHhppRr) {
        this.idHhppRr = idHhppRr;
    }

    public String getCalleRr() {
        return calleRr;
    }

    public void setCalleRr(String calleRr) {
        this.calleRr = calleRr;
    }

    public String getPlacaRr() {
        return placaRr;
    }

    public void setPlacaRr(String placaRr) {
        this.placaRr = placaRr;
    }

    public String getApartamentoRr() {
        return apartamentoRr;
    }

    public void setApartamentoRr(String apartamentoRr) {
        this.apartamentoRr = apartamentoRr;
    }

    public String getComunidadRr() {
        return comunidadRr;
    }

    public void setComunidadRr(String comunidadRr) {
        this.comunidadRr = comunidadRr;
    }

    public String getDivisionRr() {
        return divisionRr;
    }

    public void setDivisionRr(String divisionRr) {
        this.divisionRr = divisionRr;
    }

    public String getEstadoUnidadRr() {
        return estadoUnidadRr;
    }

    public void setEstadoUnidadRr(String estadoUnidadRr) {
        this.estadoUnidadRr = estadoUnidadRr;
    }

    public String getVendedorRr() {
        return vendedorRr;
    }

    public void setVendedorRr(String vendedorRr) {
        this.vendedorRr = vendedorRr;
    }

    public String getCodigoPostalRr() {
        return codigoPostalRr;
    }

    public void setCodigoPostalRr(String codigoPostalRr) {
        this.codigoPostalRr = codigoPostalRr;
    }

    public String getTipoAcometidaRr() {
        return tipoAcometidaRr;
    }

    public void setTipoAcometidaRr(String tipoAcometidaRr) {
        this.tipoAcometidaRr = tipoAcometidaRr;
    }

    public String getUltimaUbicacionNodoRr() {
        return ultimaUbicacionNodoRr;
    }

    public void setUltimaUbicacionNodoRr(String ultimaUbicacionNodoRr) {
        this.ultimaUbicacionNodoRr = ultimaUbicacionNodoRr;
    }

    public String getHeadEndRr() {
        return headEndRr;
    }

    public void setHeadEndRr(String headEndRr) {
        this.headEndRr = headEndRr;
    }

    public String getNombreEdificioRr() {
        return nombreEdificioRr;
    }

    public void setNombreEdificioRr(String nombreEdificioRr) {
        this.nombreEdificioRr = nombreEdificioRr;
    }

    public String getTipoUnidadRr() {
        return tipoUnidadRr;
    }

    public void setTipoUnidadRr(String tipoUnidadRr) {
        this.tipoUnidadRr = tipoUnidadRr;
    }

    public String getTipoCblAcometidaRr() {
        return tipoCblAcometidaRr;
    }

    public void setTipoCblAcometidaRr(String TipoCblAcometidaRr) {
        this.tipoCblAcometidaRr = TipoCblAcometidaRr;
    }

    public String getFechaAuditRr() {
        return fechaAuditRr;
    }

    public void setFechaAuditRr(String fechaAuditRr) {
        this.fechaAuditRr = fechaAuditRr;
    }

    public String getNotasAdd1HhppRr() {
        return notasAdd1HhppRr;
    }

    public void setNotasAdd1HhppRr(String notasAdd1HhppRr) {
        this.notasAdd1HhppRr = notasAdd1HhppRr;
    }

    public String getNotasAdd2HhppRr() {
        return notasAdd2HhppRr;
    }

    public void setNotasAdd2HhppRr(String notasAdd2HhppRr) {
        this.notasAdd2HhppRr = notasAdd2HhppRr;
    }

    public String getNotasAdd3HhppRr() {
        return notasAdd3HhppRr;
    }

    public void setNotasAdd3HhppRr(String notasAdd3HhppRr) {
        this.notasAdd3HhppRr = notasAdd3HhppRr;
    }

    public String getNotasAdd4HhppRr() {
        return notasAdd4HhppRr;
    }

    public void setNotasAdd4HhppRr(String notasAdd4HhppRr) {
        this.notasAdd4HhppRr = notasAdd4HhppRr;
    }

    public String getSubEdificioIdRepositorio() {
        return subEdificioIdRepositorio;
    }

    public void setSubEdificioIdRepositorio(String subEdificioIdRepositorio) {
        this.subEdificioIdRepositorio = subEdificioIdRepositorio;
    }

    
    
}
