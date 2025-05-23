/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author camargomf
 */
@XmlRootElement
public class RequestDataInformacionAdicionalSubEdificio {

    @XmlElement
    private String nombreUsuario = "";
    @XmlElement
    private String codigoCuenta = "";
    @XmlElement
    private String codigoSubEdificio = "";
    @XmlElement
    private String descripcionSubedificio = "";
    @XmlElement
    private String nombreEdificio = "";
    @XmlElement
    private String nombreCalle = "";
    @XmlElement
    private String codigoOrigenDatos = "";
    @XmlElement
    private String descripcionOrigenDatos = "";
    @XmlElement
    private String codigoTipoProyecto = "";
    @XmlElement
    private String nombreTipoProyecto = "";
    @XmlElement
    private String codigoConstructor = "";
    @XmlElement
    private String nombreConstructor = "";
    @XmlElement
    private String fechaEntrega = "";
    @XmlElement
    private String codigoEstado = "";
    @XmlElement
    private String descripcionEstado = "";
    @XmlElement
    private String codigoAsesor = "";
    @XmlElement
    private String nombreAsesor = "";
    @XmlElement
    private String codigoEspecialista = "";
    @XmlElement
    private String nombreEspecialista = "";
    @XmlElement
    private String codigoSupervisor = "";
    @XmlElement
    private String nombreSupervisor = "";
    @XmlElement
    private String codigoTecnico = "";
    @XmlElement
    private String nombreTecnico = "";
    @XmlElement
    private String vt = "";
    @XmlElement
    private String fechaVT = "";
    @XmlElement
    private String costoVT = "";
    @XmlElement
    private String reDiseno = "";
    @XmlElement
    private String fechaReporteDiseno = "";
    @XmlElement
    private String fechaRespuestaDiseno = "";
    @XmlElement
    private String cierre = "";
    @XmlElement
    private String fechaRecibidoCierre = "";
    @XmlElement
    private String meta = "";
    @XmlElement
    private String fechaInicioEjecucion = "";
    @XmlElement
    private String fechaFinEjecucion = "";
    @XmlElement
    private String costoEjecucion = "";
    @XmlElement
    private String rePlanos = "";
    @XmlElement
    private String fechaSolicitudPlanos = "";
    @XmlElement
    private String fechaEntregaPlanos = "";
    @XmlElement
    private String conectRCorriente = "";
    @XmlElement
    private String fechaSolicitudConectRCorriente = "";
    @XmlElement
    private String fechaEntregaConectRCorriente = "";
    @XmlElement
    private String codigoMotivo = "";
    @XmlElement
    private String descripcionMotivo = "";

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCodigoCuenta(){
      return codigoCuenta;
    }

    public void setCodigoCuenta(String codigoCuenta){
      this.codigoCuenta = codigoCuenta;
    }

    public String getCodigoSubEdificio(){
      return codigoSubEdificio;
    }

    public void setCodigoSubEdificio(String codigoSubEdificio){
      this.codigoSubEdificio = codigoSubEdificio;
    }

    public String getDescripcionSubedificio(){
      return descripcionSubedificio;
    }

    public void setDescripcionSubedificio(String descripcionSubedificio){
      this.descripcionSubedificio = descripcionSubedificio;
    }

    public String getNombreEdificio(){
      return nombreEdificio;
    }

    public void setNombreEdificio(String nombreEdificio){
      this.nombreEdificio = nombreEdificio;
    }

    public String getNombreCalle(){
      return nombreCalle;
    }

    public void setNombreCalle(String nombreCalle){
      this.nombreCalle = nombreCalle;
    }

    public String getCodigoOrigenDatos(){
      return codigoOrigenDatos;
    }

    public void setCodigoOrigenDatos(String codigoOrigenDatos){
      this.codigoOrigenDatos = codigoOrigenDatos;
    }

    public String getDescripcionOrigenDatos(){
      return descripcionOrigenDatos;
    }

    public void setDescripcionOrigenDatos(String descripcionOrigenDatos){
      this.descripcionOrigenDatos = descripcionOrigenDatos;
    }

    public String getCodigoTipoProyecto(){
      return codigoTipoProyecto;
    }

    public void setCodigoTipoProyecto(String codigoTipoProyecto){
      this.codigoTipoProyecto = codigoTipoProyecto;
    }

    public String getNombreTipoProyecto(){
      return nombreTipoProyecto;
    }

    public void setNombreTipoProyecto(String nombreTipoProyecto){
      this.nombreTipoProyecto = nombreTipoProyecto;
    }

    public String getCodigoConstructor(){
      return codigoConstructor;
    }

    public void setCodigoConstructor(String codigoConstructor){
      this.codigoConstructor = codigoConstructor;
    }

    public String getNombreConstructor(){
      return nombreConstructor;
    }

    public void setNombreConstructor(String nombreConstructor){
      this.nombreConstructor = nombreConstructor;
    }

    public String getFechaEntrega(){
      return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega){
      this.fechaEntrega = fechaEntrega;
    }

    public String getCodigoEstado(){
      return codigoEstado;
    }

    public void setCodigoEstado(String codigoEstado){
      this.codigoEstado = codigoEstado;
    }

    public String getDescripcionEstado(){
      return descripcionEstado;
    }

    public void setDescripcionEstado(String descripcionEstado){
      this.descripcionEstado = descripcionEstado;
    }

    public String getCodigoAsesor(){
      return codigoAsesor;
    }

    public void setCodigoAsesor(String codigoAsesor){
      this.codigoAsesor = codigoAsesor;
    }

    public String getNombreAsesor(){
      return nombreAsesor;
    }

    public void setNombreAsesor(String nombreAsesor){
      this.nombreAsesor = nombreAsesor;
    }

    public String getCodigoEspecialista(){
      return codigoEspecialista;
    }

    public void setCodigoEspecialista(String codigoEspecialista){
      this.codigoEspecialista = codigoEspecialista;
    }

    public String getNombreEspecialista(){
      return nombreEspecialista;
    }

    public void setNombreEspecialista(String nombreEspecialista){
      this.nombreEspecialista = nombreEspecialista;
    }

    public String getCodigoSupervisor(){
      return codigoSupervisor;
    }

    public void setCodigoSupervisor(String codigoSupervisor){
      this.codigoSupervisor = codigoSupervisor;
    }

    public String getNombreSupervisor(){
      return nombreSupervisor;
    }

    public void setNombreSupervisor(String nombreSupervisor){
      this.nombreSupervisor = nombreSupervisor;
    }

    public String getCodigoTecnico(){
      return codigoTecnico;
    }

    public void setCodigoTecnico(String codigoTecnico){
      this.codigoTecnico = codigoTecnico;
    }

    public String getNombreTecnico(){
      return nombreTecnico;
    }

    public void setNombreTecnico(String nombreTecnico){
      this.nombreTecnico = nombreTecnico;
    }

    public String getVt() {
        return vt;
    }

    public void setVt(String vt) {
        this.vt = vt;
    }

    public String getFechaVT(){
      return fechaVT;
    }

    public void setFechaVT(String fechaVT){
      this.fechaVT = fechaVT;
    }

    public String getCostoVT(){
      return costoVT;
    }

    public void setCostoVT(String costoVT){
      this.costoVT = costoVT;
    }

    public String getReDiseno(){
      return reDiseno;
    }

    public void setReDiseno(String reDiseno){
      this.reDiseno = reDiseno;
    }

    public String getFechaReporteDiseno(){
      return fechaReporteDiseno;
    }

    public void setFechaReporteDiseno(String fechaReporteDiseno){
      this.fechaReporteDiseno = fechaReporteDiseno;
    }

    public String getFechaRespuestaDiseno(){
      return fechaRespuestaDiseno;
    }

    public void setFechaRespuestaDiseno(String fechaRespuestaDiseno){
      this.fechaRespuestaDiseno = fechaRespuestaDiseno;
    }

    public String getCierre(){
      return cierre;
    }

    public void setCierre(String cierre){
      this.cierre = cierre;
    }

    public String getFechaRecibidoCierre(){
      return fechaRecibidoCierre;
    }

    public void setFechaRecibidoCierre(String fechaRecibidoCierre){
      this.fechaRecibidoCierre = fechaRecibidoCierre;
    }

    public String getMeta(){
      return meta;
    }

    public void setMeta(String meta){
      this.meta = meta;
    }

    public String getFechaInicioEjecucion(){
      return fechaInicioEjecucion;
    }

    public void setFechaInicioEjecucion(String fechaInicioEjecucion){
      this.fechaInicioEjecucion = fechaInicioEjecucion;
    }

    public String getFechaFinEjecucion(){
      return fechaFinEjecucion;
    }

    public void setFechaFinEjecucion(String fechaFinEjecucion){
      this.fechaFinEjecucion = fechaFinEjecucion;
    }

    public String getCostoEjecucion(){
      return costoEjecucion;
    }

    public void setCostoEjecucion(String costoEjecucion){
      this.costoEjecucion = costoEjecucion;
    }

    public String getRePlanos(){
      return rePlanos;
    }

    public void setRePlanos(String rePlanos){
      this.rePlanos = rePlanos;
    }

    public String getFechaSolicitudPlanos(){
      return fechaSolicitudPlanos;
    }

    public void setFechaSolicitudPlanos(String fechaSolicitudPlanos){
      this.fechaSolicitudPlanos = fechaSolicitudPlanos;
    }

    public String getFechaEntregaPlanos(){
      return fechaEntregaPlanos;
    }

    public void setFechaEntregaPlanos(String fechaEntregaPlanos){
      this.fechaEntregaPlanos = fechaEntregaPlanos;
    }

    public String getConectRCorriente(){
      return conectRCorriente;
    }

    public void setConectRCorriente(String conectRCorriente){
      this.conectRCorriente = conectRCorriente;
    }

    public String getFechaSolicitudConectRCorriente(){
      return fechaSolicitudConectRCorriente;
    }

    public void setFechaSolicitudConectRCorriente(String fechaSolicitudConectRCorriente){
      this.fechaSolicitudConectRCorriente = fechaSolicitudConectRCorriente;
    }

    public String getFechaEntregaConectRCorriente(){
      return fechaEntregaConectRCorriente;
    }

    public void setFechaEntregaConectRCorriente(String fechaEntregaConectRCorriente){
      this.fechaEntregaConectRCorriente = fechaEntregaConectRCorriente;
    }

    public String getCodigoMotivo(){
      return codigoMotivo;
    }

    public void setCodigoMotivo(String codigoMotivo){
      this.codigoMotivo = codigoMotivo;
    }

    public String getDescripcionMotivo(){
      return descripcionMotivo;
    }

    public void setDescripcionMotivo(String descripcionMotivo){
      this.descripcionMotivo = descripcionMotivo;
    }


}
