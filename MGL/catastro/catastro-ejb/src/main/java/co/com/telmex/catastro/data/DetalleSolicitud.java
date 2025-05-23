package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Clase DetalleSolicitud
 * Implementa Serialización.
 *
 * @author 	Deiver Rovira
 * @version	1.0
 */
public class DetalleSolicitud implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal detalleSolicitudId;
    private SolicitudRed solicitudRed;
    private String tipoSolictud;
    private String nombre;
    private String calle;
    private String placa;
    private String DireccionAPlaca;
    private String DireccionAComplemento;
    private String DireccionSta;
    private String nodo;
    private String fuenteuente;
    private String direccionAltSta;
    private String localidad;
    private String barrio;
    private String barrioGeo;
    private String manzana;
    private BigDecimal nivelSocioeconomico;
    private String servinformacion;
    private String zipcode;
    private String longitud;
    private String latitud;
    private String existe;
    private String validadar;
    private BigDecimal confiabilidad;
    private String manzanaCatastral;
    private String actividadEconomica;
    private String tipoHhpp;
    private String estadoHHPP;
    private BigDecimal tipoRedHHPP;
    private BigDecimal tipoConexionHHPP;
    private Date fechaCreacion;
    private String usuarioCreacion;
    private Date fechaModificacion;
    private String usuarioModificacion;
    private String numeroUnidadRR;
    private String calleRR;
    private String numeroAoartamentoRR;
    private String nombreTipoRedHHPP;
    private BigDecimal idNodo;
    private String nombreTipoConexionHHPP;
    private String nombreTipoHhpp;
    private BigDecimal idGeoUltimo;
    private String errorProceso="";
    private String estadoProceso="";
    private String DireccionEstandarizadaComplemnto="";
    private String codDireccionAComplemnto="";
    /**
     * Constructor
     */
    public DetalleSolicitud() {
    }

    /**
     * @return the detalleSolicitudId
     */
    public BigDecimal getDetalleSolicitudId() {
        return detalleSolicitudId;
    }

    /**
     * @param detalleSolicitudId the detalleSolicitudId to set
     */
    public void setDetalleSolicitudId(BigDecimal detalleSolicitudId) {
        this.detalleSolicitudId = detalleSolicitudId;
    }

    /**
     * @return the solicitudRed
     */
    public SolicitudRed getSolicitudRed() {
        return solicitudRed;
    }

    /**
     * @param solicitudRed the solicitudRed to set
     */
    public void setSolicitudRed(SolicitudRed solicitudRed) {
        this.solicitudRed = solicitudRed;
    }

    /**
     * @return the tipoSolictud
     */
    public String getTipoSolictud() {
        return tipoSolictud;
    }

    /**
     * @param tipoSolictud the tipoSolictud to set
     */
    public void setTipoSolictud(String tipoSolictud) {
        this.tipoSolictud = tipoSolictud;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the calle
     */
    public String getCalle() {
        return calle;
    }

    /**
     * @param calle the calle to set
     */
    public void setCalle(String calle) {
        this.calle = calle;
    }

    /**
     * @return the placa
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * @param placa the placa to set
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * @return the DireccionSta
     */
    public String getDireccionSta() {
        return DireccionSta;
    }

    /**
     * @param DireccionSta the DireccionSta to set
     */
    public void setDireccionSta(String DireccionSta) {
        this.DireccionSta = DireccionSta;
    }

    /**
     * @return the nodo
     */
    public String getNodo() {
        return nodo;
    }

    /**
     * @param nodo the nodo to set
     */
    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    /**
     * @return the fuenteuente
     */
    public String getFuenteuente() {
        return fuenteuente;
    }

    /**
     * @param fuenteuente the fuenteuente to set
     */
    public void setFuenteuente(String fuenteuente) {
        this.fuenteuente = fuenteuente;
    }

    /**
     * @return the direccionAltSta
     */
    public String getDireccionAltSta() {
        return direccionAltSta;
    }

    /**
     * @param direccionAltSta the direccionAltSta to set
     */
    public void setDireccionAltSta(String direccionAltSta) {
        this.direccionAltSta = direccionAltSta;
    }

    /**
     * @return the localidad
     */
    public String getLocalidad() {
        return localidad;
    }

    /**
     * @param localidad the localidad to set
     */
    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    /**
     * @return the barrio
     */
    public String getBarrio() {
        return barrio;
    }

    /**
     * @param barrio the barrio to set
     */
    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    /**
     * @return the manzana
     */
    public String getManzana() {
        return manzana;
    }

    /**
     * @param manzana the manzana to set
     */
    public void setManzana(String manzana) {
        this.manzana = manzana;
    }

    /**
     * @return the nivelSocioeconomico
     */
    public BigDecimal getNivelSocioeconomico() {
        return nivelSocioeconomico;
    }

    /**
     * @param nivelSocioeconomico the nivelSocioeconomico to set
     */
    public void setNivelSocioeconomico(BigDecimal nivelSocioeconomico) {
        this.nivelSocioeconomico = nivelSocioeconomico;
    }

    /**
     * @return the servinformacion
     */
    public String getServinformacion() {
        return servinformacion;
    }

    /**
     * @param servinformacion the servinformacion to set
     */
    public void setServinformacion(String servinformacion) {
        this.servinformacion = servinformacion;
    }

    /**
     * @return the zipcode
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * @param zipcode the zipcode to set
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    /**
     * @return the longitud
     */
    public String getLongitud() {
        return longitud;
    }

    /**
     * @param longitud the longitud to set
     */
    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    /**
     * @return the latitud
     */
    public String getLatitud() {
        return latitud;
    }

    /**
     * @param latitud the latitud to set
     */
    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    /**
     * @return the existe
     */
    public String getExiste() {
        return existe;
    }

    /**
     * @param existe the existe to set
     */
    public void setExiste(String existe) {
        this.existe = existe;
    }

    /**
     * @return the validadar
     */
    public String getValidadar() {
        return validadar;
    }

    /**
     * @param validadar the validadar to set
     */
    public void setValidadar(String validadar) {
        this.validadar = validadar;
    }

    /**
     * @return the confiabilidad
     */
    public BigDecimal getConfiabilidad() {
        return confiabilidad;
    }

    /**
     * @param confiabilidad the confiabilidad to set
     */
    public void setConfiabilidad(BigDecimal confiabilidad) {
        this.confiabilidad = confiabilidad;
    }

    /**
     * @return the manzanaCatastral
     */
    public String getManzanaCatastral() {
        return manzanaCatastral;
    }

    /**
     * @param manzanaCatastral the manzanaCatastral to set
     */
    public void setManzanaCatastral(String manzanaCatastral) {
        this.manzanaCatastral = manzanaCatastral;
    }

    /**
     * @return the actividadEconomica
     */
    public String getActividadEconomica() {
        return actividadEconomica;
    }

    /**
     * @param actividadEconomica the actividadEconomica to set
     */
    public void setActividadEconomica(String actividadEconomica) {
        this.actividadEconomica = actividadEconomica;
    }

    /**
     * @return the tipoHhpp
     */
    public String getTipoHhpp() {
        return tipoHhpp;
    }

    /**
     * @param tipoHhpp the tipoHhpp to set
     */
    public void setTipoHhpp(String tipoHhpp) {
        this.tipoHhpp = tipoHhpp;
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
     * @return the fechaModificacion
     */
    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    /**
     * @param fechaModificacion the fechaModificacion to set
     */
    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    /**
     * @return the usuarioModificacion
     */
    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    /**
     * @param usuarioModificacion the usuarioModificacion to set
     */
    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    /**
     * @return the DireccionAPlaca
     */
    public String getDireccionAPlaca() {
        return DireccionAPlaca;
    }

    /**
     * @param DireccionAPlaca the DireccionAPlaca to set
     */
    public void setDireccionAPlaca(String DireccionAPlaca) {
        this.DireccionAPlaca = DireccionAPlaca;
    }

    /**
     * @return the DireccionAComplemento
     */
    public String getDireccionAComplemento() {
        return DireccionAComplemento;
    }

    /**
     * @param DireccionAComplemento the DireccionAComplemento to set
     */
    public void setDireccionAComplemento(String DireccionAComplemento) {
        this.DireccionAComplemento = DireccionAComplemento;
    }

    /**
     * @return the barrioGeo
     */
    public String getBarrioGeo() {
        return barrioGeo;
    }

    /**
     * @param barrioGeo the barrioGeo to set
     */
    public void setBarrioGeo(String barrioGeo) {
        this.barrioGeo = barrioGeo;
    }

    /**
     * @return the estadoHHPP
     */
    public String getEstadoHHPP() {
        return estadoHHPP;
    }

    /**
     * @param estadoHHPP the estadoHHPP to set
     */
    public void setEstadoHHPP(String estadoHHPP) {
        this.estadoHHPP = estadoHHPP;
    }

    /**
     * @return the numeroUnidadRR
     */
    public String getNumeroUnidadRR() {
        return numeroUnidadRR;
    }

    /**
     * @param numeroUnidadRR the numeroUnidadRR to set
     */
    public void setNumeroUnidadRR(String numeroUnidadRR) {
        this.numeroUnidadRR = numeroUnidadRR;
    }

    /**
     * @return the calleRR
     */
    public String getCalleRR() {
        return calleRR;
    }

    /**
     * @param calleRR the calleRR to set
     */
    public void setCalleRR(String calleRR) {
        this.calleRR = calleRR;
    }

    /**
     * @return the numeroAoartamentoRR
     */
    public String getNumeroAoartamentoRR() {
        return numeroAoartamentoRR;
    }

    /**
     * @param numeroAoartamentoRR the numeroAoartamentoRR to set
     */
    public void setNumeroAoartamentoRR(String numeroAoartamentoRR) {
        this.numeroAoartamentoRR = numeroAoartamentoRR;
    }

    /**
     * @return the tipoRedHHPP
     */
    public BigDecimal getTipoRedHHPP() {
        return tipoRedHHPP;
    }

    /**
     * @param tipoRedHHPP the tipoRedHHPP to set
     */
    public void setTipoRedHHPP(BigDecimal tipoRedHHPP) {
        this.tipoRedHHPP = tipoRedHHPP;
    }

    /**
     * @return the tipoConexionHHPP
     */
    public BigDecimal getTipoConexionHHPP() {
        return tipoConexionHHPP;
    }

    /**
     * @param tipoConexionHHPP the tipoConexionHHPP to set
     */
    public void setTipoConexionHHPP(BigDecimal tipoConexionHHPP) {
        this.tipoConexionHHPP = tipoConexionHHPP;
    }
    @Override
    public String toString(){
            return "DetalleSolicitud={detalleSolicitudId = " + detalleSolicitudId + " \n" +
            "solicitudRed = " + solicitudRed +  " \n" +
            "tipoSolictud = " + tipoSolictud +  " \n" +
            "nombre = " + nombre +  " \n" +
            "calle = " + calle +  " \n" +
            "placa = " + placa +  " \n" +
            "DireccionAPlaca = " + DireccionAPlaca +  " \n" +
            "DireccionAComplemento = " + DireccionAComplemento +  " \n" +
            "DireccionSta = " + DireccionSta +  " \n" +
            "nodo = " + nodo +  " \n" +
            "fuenteuente = " + fuenteuente +  " \n" +
            "direccionAltSta = " + direccionAltSta +  " \n" +
            "localidad = " + localidad +  " \n" +
            "barrio = " + barrio +  " \n" +
            "barrioGeo = " + barrioGeo +  " \n" +
            "manzana = " + manzana +  " \n" +
            "nivelSocioeconomico = " + nivelSocioeconomico +  " \n" +
            "servinformacion = " + servinformacion +  " \n" +
            "zipcode = " + zipcode +  " \n" +
            "longitud = " + longitud +  " \n" +
            "latitud = " + latitud +  " \n" +
            "existe = " + existe +  " \n" +
            "validadar = " + validadar +  " \n" +
            "confiabilidad = " + confiabilidad +  " \n" +
            "manzanaCatastral = " + manzanaCatastral +  " \n" +
            "actividadEconomica = " + actividadEconomica +  " \n" +
            "tipoHhpp = " + tipoHhpp +  " \n" +
            "estadoHHPP = " + estadoHHPP +  " \n" +
            "tipoRedHHPP = " + tipoRedHHPP +  " \n" +
            "tipoConexionHHPP = " + tipoConexionHHPP +  " \n" +
            "fechaCreacion = " + fechaCreacion +  " \n" +
            "usuarioCreacion = " + usuarioCreacion +  " \n" +
            "fechaModificacion = " + fechaModificacion +  " \n" +
            "usuarioModificacion = " + usuarioModificacion +  " \n" +
            "numeroUnidadRR = " + numeroUnidadRR +  " \n" +
            "calleRR = " + calleRR +  " \n" +
            "numeroAoartamentoRR = " + numeroAoartamentoRR+"}"; 
    }

    /**
     * @return the nombreTipoRedHHPP
     */
    public String getNombreTipoRedHHPP() {
        return nombreTipoRedHHPP;
    }

    /**
     * @param nombreTipoRedHHPP the nombreTipoRedHHPP to set
     */
    public void setNombreTipoRedHHPP(String nombreTipoRedHHPP) {
        this.nombreTipoRedHHPP = nombreTipoRedHHPP;
    }

    /**
     * @return the idNodo
     */
    public BigDecimal getIdNodo() {
        return idNodo;
    }

    /**
     * @param idNodo the idNodo to set
     */
    public void setIdNodo(BigDecimal idNodo) {
        this.idNodo = idNodo;
    }

    /**
     * @return the nombreTipoConexionHHPP
     */
    public String getNombreTipoConexionHHPP() {
        return nombreTipoConexionHHPP;
    }

    /**
     * @param nombreTipoConexionHHPP the nombreTipoConexionHHPP to set
     */
    public void setNombreTipoConexionHHPP(String nombreTipoConexionHHPP) {
        this.nombreTipoConexionHHPP = nombreTipoConexionHHPP;
    }

    /**
     * @return the nombreTipoHhpp
     */
    public String getNombreTipoHhpp() {
        return nombreTipoHhpp;
    }

    /**
     * @param nombreTipoHhpp the nombreTipoHhpp to set
     */
    public void setNombreTipoHhpp(String nombreTipoHhpp) {
        this.nombreTipoHhpp = nombreTipoHhpp;
    }

    /**
     * @return the idGeoUltimo
     */
    public BigDecimal getIdGeoUltimo() {
        return idGeoUltimo;
    }

    /**
     * @param idGeoUltimo the idGeoUltimo to set
     */
    public void setIdGeoUltimo(BigDecimal idGeoUltimo) {
        this.idGeoUltimo = idGeoUltimo;
    }


    /**
     * @return the errorProceso
     */
    public String getErrorProceso() {
        return errorProceso;
    }

    /**
     * @param errorProceso the errorProceso to set
     */
    public void setErrorProceso(String errorProceso) {
        this.errorProceso = errorProceso;
    }

    /**
     * @return the estadoProceso
     */
    public String getEstadoProceso() {
        return estadoProceso;
    }

    /**
     * @param estadoProceso the estadoProceso to set
     */
    public void setEstadoProceso(String estadoProceso) {
        this.estadoProceso = estadoProceso;
    }

    /**
     * @return the DireccionEstandarizadaComplemnto
     */
    public String getDireccionEstandarizadaComplemnto() {
        return DireccionEstandarizadaComplemnto;
    }

    /**
     * @param DireccionEstandarizadaComplemnto the DireccionEstandarizadaComplemnto to set
     */
    public void setDireccionEstandarizadaComplemnto(String DireccionEstandarizadaComplemnto) {
        this.DireccionEstandarizadaComplemnto = DireccionEstandarizadaComplemnto;
    }

    /**
     * @return the codDireccionAComplemnto
     */
    public String getCodDireccionAComplemnto() {
        return codDireccionAComplemnto;
    }

    /**
     * @param codDireccionAComplemnto the codDireccionAComplemnto to set
     */
    public void setCodDireccionAComplemnto(String codDireccionAComplemnto) {
        this.codDireccionAComplemnto = codDireccionAComplemnto;
    }
}
