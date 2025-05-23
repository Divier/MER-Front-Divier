/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author cardenaslb
 */
public class CmtReporteDetalladoDto {

    //Datos De la solicitud
    private BigDecimal solicitudCmId;
    private String nombreTipoSolicitud;
    private String nombreEstadoSolicitud;
    private Date fechaIniCreacionSol;
    private Date fechaFinCreacionSol;
    //Datos De la solicitud
    //Informacion cuenta matriz
    private String departamentoGpo;
    private String ciudad;
    private String centroPobladoGpo;
    private String direccionCm;
    private String barrio;
    private BigDecimal cuentaMatrizId;
    private BigDecimal cuentaMatrizIdRR;
    private String cuentaMatrizNombre;
    private String tipoEdificio;
    private int cantidadTorres;
    private int cantidadHhpp;
    private String constructora;
    private String contactoConstructora;
    private String administracion;
    private String contactoAdministracion;
    private String ascensores;
    private String contactoAscensores;
    private String telefonoPorteria1;
    private String telefonoPorteria2;
    private Date fechaEntrega;
    //Informacion cuenta matriz 
    //Datos Solicitante
    private String origenSolicitud;
    private String areaSolictud;
    private String asesor;
    private String correoAsesor;
    private String telefonoSolicitante;
    private String copiaA;
    private String usuarioSolicitudNombre;
    private String correoUsuarioSolicitante;
    private String notasSolicitante;
    //Datos Solicitante
    //Datos General Gestion
    private String tempSolicitud;
    private String tempGestion;
    private String resultadoGestion;
    private String respuestaActual;
    private String usuarioGestionNombre;
    private Date fechaGestion;
    private Date fechaInicioGestion;
    //Datos  General Gestion
    //Datos Creacion CM
    //Geo
    private String dirTraducida;
    private String dirAntigua;
    private String confiabilidad;
    private String confiabilidadComplemento;
    private String fuente;
    private String barrioGeo;
    private String nivelSoGeo;
    private String nodoBi;
    private String nodoUni1;
    private String nodoUni2;
    private String nodoDth;
    private String nodoMovil;
    private String nodoFtth;
    private String nodoWifi;
    private String estado;
    private String cx;
    private String cy;
    //Geo
    //Gestion Cm
    private String nivelSocGes;
    private String tecnologiaSolGes;
    private String tecnoGestionDTH;
    private String tecnoGestionFTTH;
    private String tecnoGestionFOG;
    private String tecnoGestionFOU;
    private String tecnoGestionBID;
    private String tecnoGestionUNI;
    private String tecnoGestionLTE;
    private String tecnoGestionMOV;
    //Gestion Cm
    //Datos Creacion CM
    //Datos Modificacion CM
    private short modSubedificios;
    private short modDireccion;
    private short modDatosCm;
    private short modCobertura;
    
    private String cuentaMatrizNombreDespues;
    private String tipoEdificioDespues;
    private String telefonoPorteria1Despues;
    private String telefonoPorteria2Despues; 
    private String administracionDespues;
    private String contactoAdministracionDespues;
    private String ascensoresDespues;
    private String subedificiosAntes;
    private String subedificiosDespues;
    private String direccionCmAntes;
    private String direccionCmDespues;
    private String coberturaCmAntes;
    private String coberturaCmDespues;
   
    
    private String resultGestionModDatosCm;
    private String resultGestionModDir;
    private String resultGestionModSubEdi;
    private String resultGestionModCobertura;
    private Date fechaInicioGestionCobertura;
    private Date fechaGestionCobertura;
    private String usuarioGestionCobertura;
    private int cantidadSubedicifiosMod;
    //Datos Modificacion CM
    //Datos Visita Tecnica
    private String acondicionamientoVT;
    private String tipoSegmentoVT;
    private String tecnologiaVt;
    private Date fechaProgramacionVT;
    private int cantidadTorresVt;
    private String administradorVT;
    private String celAdministradorVT;
    private String correoAdministradorVT;
    private String telAdministradorVT;
    private String nombreContactoVt;
    private String telContactoVt;
    //Datos Visita Tecnica
    //Datos creacion/modificacion Hhpp
    private int cantidadHhppMod;
    private String hhppCmModificados;
    private int cantidadHhppCre;
    private String hhppCmCreados;
    
    private Date fechaInicioGestionHhpp;
    private Date fechaGestionHhpp;
   
    //Datos creacion/modificacion Hhpp
   
    public BigDecimal getSolicitudCmId() {
        return solicitudCmId;
    }

    public void setSolicitudCmId(BigDecimal solicitudCmId) {
        this.solicitudCmId = solicitudCmId;
    }

    public String getNombreTipoSolicitud() {
        return nombreTipoSolicitud;
    }

    public void setNombreTipoSolicitud(String nombreTipoSolicitud) {
        this.nombreTipoSolicitud = nombreTipoSolicitud;
    }

    public String getNombreEstadoSolicitud() {
        return nombreEstadoSolicitud;
    }

    public void setNombreEstadoSolicitud(String nombreEstadoSolicitud) {
        this.nombreEstadoSolicitud = nombreEstadoSolicitud;
    }

    public Date getFechaIniCreacionSol() {
        return fechaIniCreacionSol;
    }

    public void setFechaIniCreacionSol(Date fechaIniCreacionSol) {
        this.fechaIniCreacionSol = fechaIniCreacionSol;
    }

    public Date getFechaFinCreacionSol() {
        return fechaFinCreacionSol;
    }

    public void setFechaFinCreacionSol(Date fechaFinCreacionSol) {
        this.fechaFinCreacionSol = fechaFinCreacionSol;
    }

    public String getDepartamentoGpo() {
        return departamentoGpo;
    }

    public void setDepartamentoGpo(String departamentoGpo) {
        this.departamentoGpo = departamentoGpo;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCentroPobladoGpo() {
        return centroPobladoGpo;
    }

    public void setCentroPobladoGpo(String centroPobladoGpo) {
        this.centroPobladoGpo = centroPobladoGpo;
    }

    public String getDireccionCm() {
        return direccionCm;
    }

    public void setDireccionCm(String direccionCm) {
        this.direccionCm = direccionCm;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public BigDecimal getCuentaMatrizId() {
        return cuentaMatrizId;
    }

    public void setCuentaMatrizId(BigDecimal cuentaMatrizId) {
        this.cuentaMatrizId = cuentaMatrizId;
    }

    public BigDecimal getCuentaMatrizIdRR() {
        return cuentaMatrizIdRR;
    }

    public void setCuentaMatrizIdRR(BigDecimal cuentaMatrizIdRR) {
        this.cuentaMatrizIdRR = cuentaMatrizIdRR;
    }

    public String getCuentaMatrizNombre() {
        return cuentaMatrizNombre;
    }

    public void setCuentaMatrizNombre(String cuentaMatrizNombre) {
        this.cuentaMatrizNombre = cuentaMatrizNombre;
    }

    public String getTipoEdificio() {
        return tipoEdificio;
    }

    public void setTipoEdificio(String tipoEdificio) {
        this.tipoEdificio = tipoEdificio;
    }

    public int getCantidadTorres() {
        return cantidadTorres;
    }

    public void setCantidadTorres(int cantidadTorres) {
        this.cantidadTorres = cantidadTorres;
    }

    public int getCantidadHhpp() {
        return cantidadHhpp;
    }

    public void setCantidadHhpp(int cantidadHhpp) {
        this.cantidadHhpp = cantidadHhpp;
    }

    public String getConstructora() {
        return constructora;
    }

    public void setConstructora(String constructora) {
        this.constructora = constructora;
    }

    public String getContactoConstructora() {
        return contactoConstructora;
    }

    public void setContactoConstructora(String contactoConstructora) {
        this.contactoConstructora = contactoConstructora;
    }

    public String getAdministracion() {
        return administracion;
    }

    public void setAdministracion(String administracion) {
        this.administracion = administracion;
    }

    public String getContactoAdministracion() {
        return contactoAdministracion;
    }

    public void setContactoAdministracion(String contactoAdministracion) {
        this.contactoAdministracion = contactoAdministracion;
    }

    public String getAscensores() {
        return ascensores;
    }

    public void setAscensores(String ascensores) {
        this.ascensores = ascensores;
    }

    public String getContactoAscensores() {
        return contactoAscensores;
    }

    public void setContactoAscensores(String contactoAscensores) {
        this.contactoAscensores = contactoAscensores;
    }

    public String getTelefonoPorteria1() {
        return telefonoPorteria1;
    }

    public void setTelefonoPorteria1(String telefonoPorteria1) {
        this.telefonoPorteria1 = telefonoPorteria1;
    }

    public String getTelefonoPorteria2() {
        return telefonoPorteria2;
    }

    public void setTelefonoPorteria2(String telefonoPorteria2) {
        this.telefonoPorteria2 = telefonoPorteria2;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
    
    public String getOrigenSolicitud() {
        return origenSolicitud;
    }

    public void setOrigenSolicitud(String origenSolicitud) {
        this.origenSolicitud = origenSolicitud;
    }

    public String getAreaSolictud() {
        return areaSolictud;
    }

    public void setAreaSolictud(String areaSolictud) {
        this.areaSolictud = areaSolictud;
    }

    public String getAsesor() {
        return asesor;
    }

    public void setAsesor(String asesor) {
        this.asesor = asesor;
    }

    public String getCorreoAsesor() {
        return correoAsesor;
    }

    public void setCorreoAsesor(String correoAsesor) {
        this.correoAsesor = correoAsesor;
    }

    public String getTelefonoSolicitante() {
        return telefonoSolicitante;
    }

    public void setTelefonoSolicitante(String telefonoSolicitante) {
        this.telefonoSolicitante = telefonoSolicitante;
    }

    public String getCopiaA() {
        return copiaA;
    }

    public void setCopiaA(String copiaA) {
        this.copiaA = copiaA;
    }

    public String getUsuarioSolicitudNombre() {
        return usuarioSolicitudNombre;
    }

    public void setUsuarioSolicitudNombre(String usuarioSolicitudNombre) {
        this.usuarioSolicitudNombre = usuarioSolicitudNombre;
    }

    public String getCorreoUsuarioSolicitante() {
        return correoUsuarioSolicitante;
    }

    public void setCorreoUsuarioSolicitante(String correoUsuarioSolicitante) {
        this.correoUsuarioSolicitante = correoUsuarioSolicitante;
    }

    public String getNotasSolicitante() {
        return notasSolicitante;
    }

    public void setNotasSolicitante(String notasSolicitante) {
        this.notasSolicitante = notasSolicitante;
    }

    public String getTempSolicitud() {
        return tempSolicitud;
    }

    public void setTempSolicitud(String tempSolicitud) {
        this.tempSolicitud = tempSolicitud;
    }

    public String getTempGestion() {
        return tempGestion;
    }

    public void setTempGestion(String tempGestion) {
        this.tempGestion = tempGestion;
    }

    public String getResultadoGestion() {
        return resultadoGestion;
    }

    public void setResultadoGestion(String resultadoGestion) {
        this.resultadoGestion = resultadoGestion;
    }

    public String getRespuestaActual() {
        return respuestaActual;
    }

    public void setRespuestaActual(String respuestaActual) {
        this.respuestaActual = respuestaActual;
    }

    public String getUsuarioGestionNombre() {
        return usuarioGestionNombre;
    }

    public void setUsuarioGestionNombre(String usuarioGestionNombre) {
        this.usuarioGestionNombre = usuarioGestionNombre;
    }

    public String getDirTraducida() {
        return dirTraducida;
    }

    public void setDirTraducida(String dirTraducida) {
        this.dirTraducida = dirTraducida;
    }

    public String getDirAntigua() {
        return dirAntigua;
    }

    public void setDirAntigua(String dirAntigua) {
        this.dirAntigua = dirAntigua;
    }

    public String getConfiabilidad() {
        return confiabilidad;
    }

    public void setConfiabilidad(String confiabilidad) {
        this.confiabilidad = confiabilidad;
    }

    public String getConfiabilidadComplemento() {
        return confiabilidadComplemento;
    }

    public void setConfiabilidadComplemento(String confiabilidadComplemento) {
        this.confiabilidadComplemento = confiabilidadComplemento;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    public String getBarrioGeo() {
        return barrioGeo;
    }

    public void setBarrioGeo(String barrioGeo) {
        this.barrioGeo = barrioGeo;
    }

    public String getNivelSoGeo() {
        return nivelSoGeo;
    }

    public void setNivelSoGeo(String nivelSoGeo) {
        this.nivelSoGeo = nivelSoGeo;
    }

    public String getNodoBi() {
        return nodoBi;
    }

    public void setNodoBi(String nodoBi) {
        this.nodoBi = nodoBi;
    }

    public String getNodoUni1() {
        return nodoUni1;
    }

    public void setNodoUni1(String nodoUni1) {
        this.nodoUni1 = nodoUni1;
    }

    public String getNodoUni2() {
        return nodoUni2;
    }

    public void setNodoUni2(String nodoUni2) {
        this.nodoUni2 = nodoUni2;
    }

    public String getNodoDth() {
        return nodoDth;
    }

    public void setNodoDth(String nodoDth) {
        this.nodoDth = nodoDth;
    }

    public String getNodoMovil() {
        return nodoMovil;
    }

    public void setNodoMovil(String nodoMovil) {
        this.nodoMovil = nodoMovil;
    }

    public String getNodoFtth() {
        return nodoFtth;
    }

    public void setNodoFtth(String nodoFtth) {
        this.nodoFtth = nodoFtth;
    }

    public String getNodoWifi() {
        return nodoWifi;
    }

    public void setNodoWifi(String nodoWifi) {
        this.nodoWifi = nodoWifi;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCx() {
        return cx;
    }

    public void setCx(String cx) {
        this.cx = cx;
    }

    public String getCy() {
        return cy;
    }

    public void setCy(String cy) {
        this.cy = cy;
    }

    public String getNivelSocGes() {
        return nivelSocGes;
    }

    public void setNivelSocGes(String nivelSocGes) {
        this.nivelSocGes = nivelSocGes;
    }

    public String getTecnologiaSolGes() {
        return tecnologiaSolGes;
    }

    public void setTecnologiaSolGes(String tecnologiaSolGes) {
        this.tecnologiaSolGes = tecnologiaSolGes;
    }

    public String getTecnoGestionDTH() {
        return tecnoGestionDTH;
    }

    public void setTecnoGestionDTH(String tecnoGestionDTH) {
        this.tecnoGestionDTH = tecnoGestionDTH;
    }

    public String getTecnoGestionFTTH() {
        return tecnoGestionFTTH;
    }

    public void setTecnoGestionFTTH(String tecnoGestionFTTH) {
        this.tecnoGestionFTTH = tecnoGestionFTTH;
    }

    public String getTecnoGestionFOG() {
        return tecnoGestionFOG;
    }

    public void setTecnoGestionFOG(String tecnoGestionFOG) {
        this.tecnoGestionFOG = tecnoGestionFOG;
    }

    public String getTecnoGestionFOU() {
        return tecnoGestionFOU;
    }

    public void setTecnoGestionFOU(String tecnoGestionFOU) {
        this.tecnoGestionFOU = tecnoGestionFOU;
    }

    public String getTecnoGestionBID() {
        return tecnoGestionBID;
    }

    public void setTecnoGestionBID(String tecnoGestionBID) {
        this.tecnoGestionBID = tecnoGestionBID;
    }

    public String getTecnoGestionUNI() {
        return tecnoGestionUNI;
    }

    public void setTecnoGestionUNI(String tecnoGestionUNI) {
        this.tecnoGestionUNI = tecnoGestionUNI;
    }

    public String getTecnoGestionLTE() {
        return tecnoGestionLTE;
    }

    public void setTecnoGestionLTE(String tecnoGestionLTE) {
        this.tecnoGestionLTE = tecnoGestionLTE;
    }

    public String getTecnoGestionMOV() {
        return tecnoGestionMOV;
    }

    public void setTecnoGestionMOV(String tecnoGestionMOV) {
        this.tecnoGestionMOV = tecnoGestionMOV;
    }

    public short getModSubedificios() {
        return modSubedificios;
    }

    public void setModSubedificios(short modSubedificios) {
        this.modSubedificios = modSubedificios;
    }

    public short getModDireccion() {
        return modDireccion;
    }

    public void setModDireccion(short modDireccion) {
        this.modDireccion = modDireccion;
    }

    public short getModDatosCm() {
        return modDatosCm;
    }

    public void setModDatosCm(short modDatosCm) {
        this.modDatosCm = modDatosCm;
    }

    public short getModCobertura() {
        return modCobertura;
    }

    public void setModCobertura(short modCobertura) {
        this.modCobertura = modCobertura;
    }

    public String getResultGestionModDatosCm() {
        return resultGestionModDatosCm;
    }

    public void setResultGestionModDatosCm(String resultGestionModDatosCm) {
        this.resultGestionModDatosCm = resultGestionModDatosCm;
    }

    public String getResultGestionModDir() {
        return resultGestionModDir;
    }

    public void setResultGestionModDir(String resultGestionModDir) {
        this.resultGestionModDir = resultGestionModDir;
    }

    public String getResultGestionModSubEdi() {
        return resultGestionModSubEdi;
    }

    public void setResultGestionModSubEdi(String resultGestionModSubEdi) {
        this.resultGestionModSubEdi = resultGestionModSubEdi;
    }

    public String getResultGestionModCobertura() {
        return resultGestionModCobertura;
    }

    public void setResultGestionModCobertura(String resultGestionModCobertura) {
        this.resultGestionModCobertura = resultGestionModCobertura;
    }

    public Date getFechaGestion() {
        return fechaGestion;
    }

    public void setFechaGestion(Date fechaGestion) {
        this.fechaGestion = fechaGestion;
    }

    public Date getFechaInicioGestion() {
        return fechaInicioGestion;
    }

    public void setFechaInicioGestion(Date fechaInicioGestion) {
        this.fechaInicioGestion = fechaInicioGestion;
    }

    public Date getFechaInicioGestionCobertura() {
        return fechaInicioGestionCobertura;
    }

    public void setFechaInicioGestionCobertura(Date fechaInicioGestionCobertura) {
        this.fechaInicioGestionCobertura = fechaInicioGestionCobertura;
    }

    public Date getFechaGestionCobertura() {
        return fechaGestionCobertura;
    }

    public void setFechaGestionCobertura(Date fechaGestionCobertura) {
        this.fechaGestionCobertura = fechaGestionCobertura;
    }

    public String getUsuarioGestionCobertura() {
        return usuarioGestionCobertura;
    }

    public void setUsuarioGestionCobertura(String usuarioGestionCobertura) {
        this.usuarioGestionCobertura = usuarioGestionCobertura;
    }

    public int getCantidadSubedicifiosMod() {
        return cantidadSubedicifiosMod;
    }

    public void setCantidadSubedicifiosMod(int cantidadSubedicifiosMod) {
        this.cantidadSubedicifiosMod = cantidadSubedicifiosMod;
    }

    public String getAcondicionamientoVT() {
        return acondicionamientoVT;
    }

    public void setAcondicionamientoVT(String acondicionamientoVT) {
        this.acondicionamientoVT = acondicionamientoVT;
    }

    public String getTipoSegmentoVT() {
        return tipoSegmentoVT;
    }

    public void setTipoSegmentoVT(String tipoSegmentoVT) {
        this.tipoSegmentoVT = tipoSegmentoVT;
    }

    public String getTecnologiaVt() {
        return tecnologiaVt;
    }

    public void setTecnologiaVt(String tecnologiaVt) {
        this.tecnologiaVt = tecnologiaVt;
    }

    public Date getFechaProgramacionVT() {
        return fechaProgramacionVT;
    }

    public void setFechaProgramacionVT(Date fechaProgramacionVT) {
        this.fechaProgramacionVT = fechaProgramacionVT;
    }

    public int getCantidadTorresVt() {
        return cantidadTorresVt;
    }

    public void setCantidadTorresVt(int cantidadTorresVt) {
        this.cantidadTorresVt = cantidadTorresVt;
    }

    public String getAdministradorVT() {
        return administradorVT;
    }

    public void setAdministradorVT(String administradorVT) {
        this.administradorVT = administradorVT;
    }

    public String getCelAdministradorVT() {
        return celAdministradorVT;
    }

    public void setCelAdministradorVT(String celAdministradorVT) {
        this.celAdministradorVT = celAdministradorVT;
    }

    public String getCorreoAdministradorVT() {
        return correoAdministradorVT;
    }

    public void setCorreoAdministradorVT(String correoAdministradorVT) {
        this.correoAdministradorVT = correoAdministradorVT;
    }

    public String getTelAdministradorVT() {
        return telAdministradorVT;
    }

    public void setTelAdministradorVT(String telAdministradorVT) {
        this.telAdministradorVT = telAdministradorVT;
    }

    public String getNombreContactoVt() {
        return nombreContactoVt;
    }

    public void setNombreContactoVt(String nombreContactoVt) {
        this.nombreContactoVt = nombreContactoVt;
    }

    public String getTelContactoVt() {
        return telContactoVt;
    }

    public void setTelContactoVt(String telContactoVt) {
        this.telContactoVt = telContactoVt;
    }

    public int getCantidadHhppMod() {
        return cantidadHhppMod;
    }

    public void setCantidadHhppMod(int cantidadHhppMod) {
        this.cantidadHhppMod = cantidadHhppMod;
    }

    public int getCantidadHhppCre() {
        return cantidadHhppCre;
    }

    public void setCantidadHhppCre(int cantidadHhppCre) {
        this.cantidadHhppCre = cantidadHhppCre;
    }

    public Date getFechaInicioGestionHhpp() {
        return fechaInicioGestionHhpp;
    }

    public void setFechaInicioGestionHhpp(Date fechaInicioGestionHhpp) {
        this.fechaInicioGestionHhpp = fechaInicioGestionHhpp;
    }

    public Date getFechaGestionHhpp() {
        return fechaGestionHhpp;
    }

    public void setFechaGestionHhpp(Date fechaGestionHhpp) {
        this.fechaGestionHhpp = fechaGestionHhpp;
    }

    public String getCuentaMatrizNombreDespues() {
        return cuentaMatrizNombreDespues;
    }

    public void setCuentaMatrizNombreDespues(String cuentaMatrizNombreDespues) {
        this.cuentaMatrizNombreDespues = cuentaMatrizNombreDespues;
    }

    public String getTipoEdificioDespues() {
        return tipoEdificioDespues;
    }

    public void setTipoEdificioDespues(String tipoEdificioDespues) {
        this.tipoEdificioDespues = tipoEdificioDespues;
    }

    public String getTelefonoPorteria1Despues() {
        return telefonoPorteria1Despues;
    }

    public void setTelefonoPorteria1Despues(String telefonoPorteria1Despues) {
        this.telefonoPorteria1Despues = telefonoPorteria1Despues;
    }

    public String getTelefonoPorteria2Despues() {
        return telefonoPorteria2Despues;
    }

    public void setTelefonoPorteria2Despues(String telefonoPorteria2Despues) {
        this.telefonoPorteria2Despues = telefonoPorteria2Despues;
    }

    public String getAdministracionDespues() {
        return administracionDespues;
    }

    public void setAdministracionDespues(String administracionDespues) {
        this.administracionDespues = administracionDespues;
    }

    public String getContactoAdministracionDespues() {
        return contactoAdministracionDespues;
    }

    public void setContactoAdministracionDespues(String contactoAdministracionDespues) {
        this.contactoAdministracionDespues = contactoAdministracionDespues;
    }

    public String getAscensoresDespues() {
        return ascensoresDespues;
    }

    public void setAscensoresDespues(String ascensoresDespues) {
        this.ascensoresDespues = ascensoresDespues;
    }

    public String getSubedificiosAntes() {
        return subedificiosAntes;
    }

    public void setSubedificiosAntes(String subedificiosAntes) {
        this.subedificiosAntes = subedificiosAntes;
    }

    public String getSubedificiosDespues() {
        return subedificiosDespues;
    }

    public void setSubedificiosDespues(String subedificiosDespues) {
        this.subedificiosDespues = subedificiosDespues;
    }

    public String getDireccionCmDespues() {
        return direccionCmDespues;
    }

    public void setDireccionCmDespues(String direccionCmDespues) {
        this.direccionCmDespues = direccionCmDespues;
    }

    public String getDireccionCmAntes() {
        return direccionCmAntes;
    }

    public void setDireccionCmAntes(String direccionCmAntes) {
        this.direccionCmAntes = direccionCmAntes;
    }

    public String getCoberturaCmAntes() {
        return coberturaCmAntes;
    }

    public void setCoberturaCmAntes(String coberturaCmAntes) {
        this.coberturaCmAntes = coberturaCmAntes;
    }

    public String getCoberturaCmDespues() {
        return coberturaCmDespues;
    }

    public void setCoberturaCmDespues(String coberturaCmDespues) {
        this.coberturaCmDespues = coberturaCmDespues;
    }

    public String getHhppCmModificados() {
        return hhppCmModificados;
    }

    public void setHhppCmModificados(String hhppCmModificados) {
        this.hhppCmModificados = hhppCmModificados;
    }

    public String getHhppCmCreados() {
        return hhppCmCreados;
    }

    public void setHhppCmCreados(String hhppCmCreados) {
        this.hhppCmCreados = hhppCmCreados;
    }    
}
