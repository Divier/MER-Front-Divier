/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author cardenaslb
 */


@Entity
@Table(name = "RAD_DETALLADA_HHPP", schema = "MGL_RADIOGRAFIA")

public class CmRadDetalladaHhpp implements Serializable {
    
    @Id
    @Basic
    @Column(name = "RAD_DETALLADA_HHPP_ID")
    @SequenceGenerator(name = "CmRadDetalladaHhpp.CmRadDetalladaHhppSq", schema = "MGL_RADIOGRAFIA",
            sequenceName = "CmRadDetalladaHhppSq.RAD_DETALLADA_HHPP_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmRadDetalladaHhpp.CmRadDetalladaHhppSq")
    private BigDecimal id;
    @Column(name = "INFO_GENERAL_NODO")
    private String infoGeneralNodo;
    @Basic
    @Column(name = "TIPO_NODO")
    private String tipoNodo;
    @Basic
    @Column(name = "ESTADO_NODO")
    private String estadoNodo;
    @Basic
    @Column(name = "MUNICIPIO")
    private String municipio;
    @Basic
    @Column(name = "CENTRO_POBLADO")
    private String centroPoblado;
    @Basic
    @Column(name = "NOMBRE_DIVISIONAL")
    private String nombreDivisional;
    @Basic
    @Column(name = "NOMBRE_AREA")
    private String nombreArea;
    @Basic
    @Column(name = "NOMBRE_ZONA")
    private String nombreZona;
    @Basic
    @Column(name = "NOMBRE_DISTRITO")
    private String nombreistrito;
    @Basic
    @Column(name = "NOMBRE_UNI_GESTION")
    private String nombreUniGestion;
    @Basic
    @Column(name = "CODIGO")
    private String codigo;
    @Basic
    @Column(name = "COM_ID")
    private BigDecimal comid;
    @Basic
    @Column(name = "NUMERO_CUENTA")
    private BigDecimal numeroCuenta;
    @Basic
    @Column(name = "NOMBRE_CUENTA")
    private String nombreCuenta;
    @Basic
    @Column(name = "FORMATO_IGAC")
    private String formatoIgac;
    @Basic
    @Column(name = "ESTRATO")
    private BigDecimal estrato;
    @Basic
    @Column(name = "PLACA")
    private String placa;
    @Basic
    @Column(name = "ESTADO_CCMM_RR")
    private BigDecimal estadoCcmmRR;
    @Basic
    @Column(name = "DESCRIPCION_TIPO_CCMM")
    private String descripcionTipoCcmm;
    @Basic
    @Column(name = "INDICADOR_MULTIEDIFICIO")
    private String indicadorMultiedificio;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUBEDIFICIO_ID", referencedColumnName = "SUBEDIFICIO_ID")
    private BigDecimal subEdificio;
    @Basic
    @Column(name = "NOMBRE_SUBEDIFICIO")
    private String nombreSubedificio;
    @Basic
    @Column(name = "ESTADO_TEC_SUB_EDIFICIO")
    private BigDecimal estadoTecSubEdificio;
    @Basic
    @Column(name = "ESTADO_SUBEDIFICIO")
    private String estadoSubEdificio;
    @Basic
    @Column(name = "ACTIVADO")
    private String activado;
    @Basic
    @Column(name = "EXCEPTION_RADIOGRAFIA")
    private String exceptionRadiografia;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TECNOLOGIA_HABILITADA_ID", referencedColumnName = "TECNOLOGIA_HABILITADA_ID")
    private BigDecimal tecnologiaHabilitadaId;
    @Basic
    @Column(name = "CP_VALOR_NIVEL6")
    private String cpValorNivel6;
    @Basic
    @Column(name = "CODIGO_RR_TB_REGIONAL")
    private String codigoRRTbRegional;
    @Basic
    @Column(name = "CODIGO_RR_TB_COMUNIDAD")
    private String codigoRRTbComunidad;
    @Basic
    @Column(name = "CUENTA_ASOCIADA")
    private BigDecimal cuentaAsociada;
    @Basic
    @Column(name = "ESTADO_ID")
    private BigDecimal estadoId;
    @Basic
    @Column(name = "INDICADOR_HHPP")
    private BigDecimal estadoHhpp;
    @Basic
    @Column(name = "HOGAR_TELEVISION")
    private BigDecimal hogarTelevision;
    @Basic
    @Column(name = "HOGAR_INTERNET")
    private BigDecimal hogarInternet;
    @Basic
    @Column(name = "HOGAR_VOZ")
    private BigDecimal hogarVoz;
    @Basic
    @Column(name = "HOGAR_TEL_INT")
    private BigDecimal hogarTelInt;
    @Basic
    @Column(name = "HOGAR_TEL_VOZ")
    private BigDecimal hogarTelVoz;
    @Basic
    @Column(name = "HOGAR_INT_VOZ")
    private BigDecimal hogarIntVoz;
    @Basic
    @Column(name = "HOGAR_TEL_INT_VOZ")
    private BigDecimal hogarTelIntVoz;
    @Basic
    @Column(name = "RENTA_MENSUAL")
    private BigDecimal rentaMensual;
    @Basic
    @Column(name = "INDICADOR_OT_PENDIENTE")
    private String indicadorOtPendiente;
    @Basic
    @Column(name = "NUMERO_OT_PENDIENTE")
    private BigDecimal numeroOtPendiente;
    @Basic
    @Column(name = "TARIFA_OT_PENDIENTE")
    private BigDecimal tarifaOtPendiente;
    @Basic
    @Column(name = "ESTADO_OT_PENDIENTE")
    private BigDecimal estadoOtPendiente;
    @Basic
    @Column(name = "CARGO_FIJO_INTERNET")
    private BigDecimal cargoFijoInternet;
    @Basic
    @Column(name = "FECHA_ACTIVACION")
    @Temporal(TemporalType.DATE)
    private Date fechaActivacion;
    @Basic
    @Column(name = "TIPO_CLIENTE")
    private String tipoCliente;
    @Basic
    @Column(name = "FECHA_OT_INS_DESINS")
    @Temporal(TemporalType.DATE)
    private Date fechaOtInsDesin;
    @Basic
    @Column(name = "NUMERO_OT_INS_DESINS")
    private BigDecimal numeroOtInsDesins;
    @Basic
    @Column(name = "USUARIO_OT_INS_DESINS")
    private BigDecimal usuarioOtInsDesins;
    @Basic
    @Column(name = "DIVISIONAL_BASICA_ID")
    private BigDecimal divisionalBasicaId;
    @Basic
    @Column(name = "AREA_BASICA_ID")
    private BigDecimal areaBasicaId;
    @Basic
    @Column(name = "ZONA_BASICA_ID")
    private BigDecimal zonaBasicaId;
    @Basic
    @Column(name = "DISTRITO_BASICA_ID")
    private BigDecimal distritoBasicaId;
    @Basic
    @Column(name = "UNIDAD_GESTION_BASICA_ID")
    private BigDecimal unidadGestionBasicaId;
    @Basic
    @Column(name = "FECHA_CREACION_HHHPP")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacionHhpp;
    @Basic
    @Column(name = "USUARIO_CREACION_HHPP")
    private BigDecimal usuarioCreacionHhpp;
    @Basic
    @Column(name = "TARIFA_RR_HOGAR")
    private BigDecimal tarifaRRHogar;
    @Basic
    @Column(name = "DIRECCION")
    private String direccion;
    @Basic
    @Column(name = "ESTADO_CUENTA_SUBS_ACT")
    private String estadoCuentaSubsAct;
    @Basic
    @Column(name = "FECHA_INSCRIPCION_SUBS_ACT")
    @Temporal(TemporalType.DATE)
    private Date fechaInscripcionSubsAct;
    @Basic
    @Column(name = "CLASS_SUBS_ACT")
    private String claseSubsAct;
    @Basic
    @Column(name = "PROBLEMA_SUBSCRIPTOR_ACT")
    private String problemaSuscritorAct;
    @Basic
    @Column(name = "EDAD_MORA_SUBS_ACT")
    private String edadMoraSubsAct;
    @Basic
    @Column(name = "ESTADO_MORA_SUBS_ACT")
    private String estadoMoraSubsAct;
    @Basic
    @Column(name = "VALOR_ULTIMO_AJUSTE_SUBS_ACT")
    private BigDecimal valorUltimoAjusteSubsAct;
    @Basic
    @Column(name = "SALDO_MORA_SUBS_ACT")
    private BigDecimal saldoMoraSubsAct;
    @Basic
    @Column(name = "TELEFONO1_SUBS_ACT")
    private String telefono1SubsAct;
    @Basic
    @Column(name = "TELEFONO2_SUBS_ACT")
    private String telefono2SubsAct;
    @Basic
    @Column(name = "ESTADO_CUENTA_SUBS_ANT")
    private String estadoCuentaSubsAnt;
    @Basic
    @Column(name = "BILLING_ACCOUNT_SUBS_ANT")
    private BigDecimal billingAccountSubsAnt;
    @Basic
    @Column(name = "FECHA_INSCIPCION_SUBS_ANT")
    @Temporal(TemporalType.DATE)
    private String fechaInscripcionSubsAnt;
    @Basic
    @Column(name = "CLASS_SUBS_ANT")
    private String classSubsAnt;
    @Basic
    @Column(name = "PROBLEMA_SUBSCRIPTOR_ANT")
    private String problemaSubscriptorAnt;
    @Basic
    @Column(name = "EDAD_MORA_SUBS_ANT")
    private String edadMoraSubsAnt;
    @Basic
    @Column(name = "ESTADO_MORA_SUSB_ANT")
    private String estadoMoraSubsAnt;
    @Basic
    @Column(name = "VALOR_ULTIMO_AJUSTE_SUSB_ANT")
    private String valorUltimoAjusteSusbAnt;
    @Basic
    @Column(name = "SALDO_MORA_SUBS_ANT")
    private String saldoMoraSubsAnt;
    @Basic
    @Column(name = "TELEFONO1_SUBS_ANT")
    private String telefono1SubsAnt;
    @Basic
    @Column(name = "TELEFONO2_SUBS_ANT")
    private String telefono2SubsAnt;
    @Basic
    @Column(name = "ESTADO_REGISTRO")
    private String estadoRegistro;
    @Basic
    @Column(name = "FECHA_CREACION")
     @Temporal(TemporalType.DATE)
    private Date fechaCraecion;
    @Basic
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Basic
    @Column(name = "PERFIL_CREACION")
    private String perfilCreacion;
    @Basic
    @Column(name = "FECHA_EDICION")
    @Temporal(TemporalType.DATE)
    private Date fechaEdicion;
    @Basic
    @Column(name = "PERFIL_EDICION")
    private String perfilEdicion;
    @Basic
    @Column(name = "RELACION_RAD_RR")
    private BigDecimal relacionRadRR;
                                               
    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getInfoGeneralNodo() {
        return infoGeneralNodo;
    }

    public void setInfoGeneralNodo(String infoGeneralNodo) {
        this.infoGeneralNodo = infoGeneralNodo;
    }

    public String getTipoNodo() {
        return tipoNodo;
    }

    public void setTipoNodo(String tipoNodo) {
        this.tipoNodo = tipoNodo;
    }

    public String getEstadoNodo() {
        return estadoNodo;
    }

    public void setEstadoNodo(String estadoNodo) {
        this.estadoNodo = estadoNodo;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getCentroPoblado() {
        return centroPoblado;
    }

    public void setCentroPoblado(String centroPoblado) {
        this.centroPoblado = centroPoblado;
    }

    public String getNombreDivisional() {
        return nombreDivisional;
    }

    public void setNombreDivisional(String nombreDivisional) {
        this.nombreDivisional = nombreDivisional;
    }

    public String getNombreArea() {
        return nombreArea;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }

    public String getNombreZona() {
        return nombreZona;
    }

    public void setNombreZona(String nombreZona) {
        this.nombreZona = nombreZona;
    }

    public String getNombreistrito() {
        return nombreistrito;
    }

    public void setNombreistrito(String nombreistrito) {
        this.nombreistrito = nombreistrito;
    }

    public String getNombreUniGestion() {
        return nombreUniGestion;
    }

    public void setNombreUniGestion(String nombreUniGestion) {
        this.nombreUniGestion = nombreUniGestion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public BigDecimal getComid() {
        return comid;
    }

    public void setComid(BigDecimal comid) {
        this.comid = comid;
    }

    public BigDecimal getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(BigDecimal numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getNombreCuenta() {
        return nombreCuenta;
    }

    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }

    public String getFormatoIgac() {
        return formatoIgac;
    }

    public void setFormatoIgac(String formatoIgac) {
        this.formatoIgac = formatoIgac;
    }

    public BigDecimal getEstrato() {
        return estrato;
    }

    public void setEstrato(BigDecimal estrato) {
        this.estrato = estrato;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public BigDecimal getEstadoCcmmRR() {
        return estadoCcmmRR;
    }

    public void setEstadoCcmmRR(BigDecimal estadoCcmmRR) {
        this.estadoCcmmRR = estadoCcmmRR;
    }

    public String getDescripcionTipoCcmm() {
        return descripcionTipoCcmm;
    }

    public void setDescripcionTipoCcmm(String descripcionTipoCcmm) {
        this.descripcionTipoCcmm = descripcionTipoCcmm;
    }

    public String getIndicadorMultiedificio() {
        return indicadorMultiedificio;
    }

    public void setIndicadorMultiedificio(String indicadorMultiedificio) {
        this.indicadorMultiedificio = indicadorMultiedificio;
    }

 

    public String getNombreSubedificio() {
        return nombreSubedificio;
    }

    public void setNombreSubedificio(String nombreSubedificio) {
        this.nombreSubedificio = nombreSubedificio;
    }

    public BigDecimal getEstadoTecSubEdificio() {
        return estadoTecSubEdificio;
    }

    public void setEstadoTecSubEdificio(BigDecimal estadoTecSubEdificio) {
        this.estadoTecSubEdificio = estadoTecSubEdificio;
    }

    public String getEstadoSubEdificio() {
        return estadoSubEdificio;
    }

    public void setEstadoSubEdificio(String estadoSubEdificio) {
        this.estadoSubEdificio = estadoSubEdificio;
    }

    public String getActivado() {
        return activado;
    }

    public void setActivado(String activado) {
        this.activado = activado;
    }

    public String getExceptionRadiografia() {
        return exceptionRadiografia;
    }

    public void setExceptionRadiografia(String exceptionRadiografia) {
        this.exceptionRadiografia = exceptionRadiografia;
    }


    public String getCpValorNivel6() {
        return cpValorNivel6;
    }

    public void setCpValorNivel6(String cpValorNivel6) {
        this.cpValorNivel6 = cpValorNivel6;
    }

    public String getCodigoRRTbRegional() {
        return codigoRRTbRegional;
    }

    public void setCodigoRRTbRegional(String codigoRRTbRegional) {
        this.codigoRRTbRegional = codigoRRTbRegional;
    }

    public String getCodigoRRTbComunidad() {
        return codigoRRTbComunidad;
    }

    public void setCodigoRRTbComunidad(String codigoRRTbComunidad) {
        this.codigoRRTbComunidad = codigoRRTbComunidad;
    }

    public BigDecimal getCuentaAsociada() {
        return cuentaAsociada;
    }

    public void setCuentaAsociada(BigDecimal cuentaAsociada) {
        this.cuentaAsociada = cuentaAsociada;
    }

    public BigDecimal getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(BigDecimal estadoId) {
        this.estadoId = estadoId;
    }

    public BigDecimal getEstadoHhpp() {
        return estadoHhpp;
    }

    public void setEstadoHhpp(BigDecimal estadoHhpp) {
        this.estadoHhpp = estadoHhpp;
    }

    public BigDecimal getHogarTelevision() {
        return hogarTelevision;
    }

    public void setHogarTelevision(BigDecimal hogarTelevision) {
        this.hogarTelevision = hogarTelevision;
    }

    public BigDecimal getHogarInternet() {
        return hogarInternet;
    }

    public void setHogarInternet(BigDecimal hogarInternet) {
        this.hogarInternet = hogarInternet;
    }

    public BigDecimal getHogarVoz() {
        return hogarVoz;
    }

    public void setHogarVoz(BigDecimal hogarVoz) {
        this.hogarVoz = hogarVoz;
    }

    public BigDecimal getHogarTelInt() {
        return hogarTelInt;
    }

    public void setHogarTelInt(BigDecimal hogarTelInt) {
        this.hogarTelInt = hogarTelInt;
    }

    public BigDecimal getHogarTelVoz() {
        return hogarTelVoz;
    }

    public void setHogarTelVoz(BigDecimal hogarTelVoz) {
        this.hogarTelVoz = hogarTelVoz;
    }

    public BigDecimal getHogarIntVoz() {
        return hogarIntVoz;
    }

    public void setHogarIntVoz(BigDecimal hogarIntVoz) {
        this.hogarIntVoz = hogarIntVoz;
    }

    public BigDecimal getHogarTelIntVoz() {
        return hogarTelIntVoz;
    }

    public void setHogarTelIntVoz(BigDecimal hogarTelIntVoz) {
        this.hogarTelIntVoz = hogarTelIntVoz;
    }

    public BigDecimal getRentaMensual() {
        return rentaMensual;
    }

    public void setRentaMensual(BigDecimal rentaMensual) {
        this.rentaMensual = rentaMensual;
    }

    public String getIndicadorOtPendiente() {
        return indicadorOtPendiente;
    }

    public void setIndicadorOtPendiente(String indicadorOtPendiente) {
        this.indicadorOtPendiente = indicadorOtPendiente;
    }

    public BigDecimal getNumeroOtPendiente() {
        return numeroOtPendiente;
    }

    public void setNumeroOtPendiente(BigDecimal numeroOtPendiente) {
        this.numeroOtPendiente = numeroOtPendiente;
    }

    public BigDecimal getTarifaOtPendiente() {
        return tarifaOtPendiente;
    }

    public void setTarifaOtPendiente(BigDecimal tarifaOtPendiente) {
        this.tarifaOtPendiente = tarifaOtPendiente;
    }

    public BigDecimal getEstadoOtPendiente() {
        return estadoOtPendiente;
    }

    public void setEstadoOtPendiente(BigDecimal estadoOtPendiente) {
        this.estadoOtPendiente = estadoOtPendiente;
    }

    public BigDecimal getCargoFijoInternet() {
        return cargoFijoInternet;
    }

    public void setCargoFijoInternet(BigDecimal cargoFijoInternet) {
        this.cargoFijoInternet = cargoFijoInternet;
    }

    public Date getFechaActivacion() {
        return fechaActivacion;
    }

    public void setFechaActivacion(Date fechaActivacion) {
        this.fechaActivacion = fechaActivacion;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public Date getFechaOtInsDesin() {
        return fechaOtInsDesin;
    }

    public void setFechaOtInsDesin(Date fechaOtInsDesin) {
        this.fechaOtInsDesin = fechaOtInsDesin;
    }

    public BigDecimal getNumeroOtInsDesins() {
        return numeroOtInsDesins;
    }

    public void setNumeroOtInsDesins(BigDecimal numeroOtInsDesins) {
        this.numeroOtInsDesins = numeroOtInsDesins;
    }

    public BigDecimal getUsuarioOtInsDesins() {
        return usuarioOtInsDesins;
    }

    public void setUsuarioOtInsDesins(BigDecimal usuarioOtInsDesins) {
        this.usuarioOtInsDesins = usuarioOtInsDesins;
    }

    public BigDecimal getDivisionalBasicaId() {
        return divisionalBasicaId;
    }

    public void setDivisionalBasicaId(BigDecimal divisionalBasicaId) {
        this.divisionalBasicaId = divisionalBasicaId;
    }

    public BigDecimal getAreaBasicaId() {
        return areaBasicaId;
    }

    public void setAreaBasicaId(BigDecimal areaBasicaId) {
        this.areaBasicaId = areaBasicaId;
    }

    public BigDecimal getZonaBasicaId() {
        return zonaBasicaId;
    }

    public void setZonaBasicaId(BigDecimal zonaBasicaId) {
        this.zonaBasicaId = zonaBasicaId;
    }

    public BigDecimal getDistritoBasicaId() {
        return distritoBasicaId;
    }

    public void setDistritoBasicaId(BigDecimal distritoBasicaId) {
        this.distritoBasicaId = distritoBasicaId;
    }

    public BigDecimal getUnidadGestionBasicaId() {
        return unidadGestionBasicaId;
    }

    public void setUnidadGestionBasicaId(BigDecimal unidadGestionBasicaId) {
        this.unidadGestionBasicaId = unidadGestionBasicaId;
    }

    public Date getFechaCreacionHhpp() {
        return fechaCreacionHhpp;
    }

    public void setFechaCreacionHhpp(Date fechaCreacionHhpp) {
        this.fechaCreacionHhpp = fechaCreacionHhpp;
    }

    public BigDecimal getUsuarioCreacionHhpp() {
        return usuarioCreacionHhpp;
    }

    public void setUsuarioCreacionHhpp(BigDecimal usuarioCreacionHhpp) {
        this.usuarioCreacionHhpp = usuarioCreacionHhpp;
    }

    public BigDecimal getTarifaRRHogar() {
        return tarifaRRHogar;
    }

    public void setTarifaRRHogar(BigDecimal tarifaRRHogar) {
        this.tarifaRRHogar = tarifaRRHogar;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstadoCuentaSubsAct() {
        return estadoCuentaSubsAct;
    }

    public void setEstadoCuentaSubsAct(String estadoCuentaSubsAct) {
        this.estadoCuentaSubsAct = estadoCuentaSubsAct;
    }

    public Date getFechaInscripcionSubsAct() {
        return fechaInscripcionSubsAct;
    }

    public void setFechaInscripcionSubsAct(Date fechaInscripcionSubsAct) {
        this.fechaInscripcionSubsAct = fechaInscripcionSubsAct;
    }

    public String getClaseSubsAct() {
        return claseSubsAct;
    }

    public void setClaseSubsAct(String claseSubsAct) {
        this.claseSubsAct = claseSubsAct;
    }

    public String getProblemaSuscritorAct() {
        return problemaSuscritorAct;
    }

    public void setProblemaSuscritorAct(String problemaSuscritorAct) {
        this.problemaSuscritorAct = problemaSuscritorAct;
    }

    public String getEdadMoraSubsAct() {
        return edadMoraSubsAct;
    }

    public void setEdadMoraSubsAct(String edadMoraSubsAct) {
        this.edadMoraSubsAct = edadMoraSubsAct;
    }

    public String getEstadoMoraSubsAct() {
        return estadoMoraSubsAct;
    }

    public void setEstadoMoraSubsAct(String estadoMoraSubsAct) {
        this.estadoMoraSubsAct = estadoMoraSubsAct;
    }

    public BigDecimal getValorUltimoAjusteSubsAct() {
        return valorUltimoAjusteSubsAct;
    }

    public void setValorUltimoAjusteSubsAct(BigDecimal valorUltimoAjusteSubsAct) {
        this.valorUltimoAjusteSubsAct = valorUltimoAjusteSubsAct;
    }

    public BigDecimal getSaldoMoraSubsAct() {
        return saldoMoraSubsAct;
    }

    public void setSaldoMoraSubsAct(BigDecimal saldoMoraSubsAct) {
        this.saldoMoraSubsAct = saldoMoraSubsAct;
    }

    public String getTelefono1SubsAct() {
        return telefono1SubsAct;
    }

    public void setTelefono1SubsAct(String telefono1SubsAct) {
        this.telefono1SubsAct = telefono1SubsAct;
    }

    public String getTelefono2SubsAct() {
        return telefono2SubsAct;
    }

    public void setTelefono2SubsAct(String telefono2SubsAct) {
        this.telefono2SubsAct = telefono2SubsAct;
    }

    public String getEstadoCuentaSubsAnt() {
        return estadoCuentaSubsAnt;
    }

    public void setEstadoCuentaSubsAnt(String estadoCuentaSubsAnt) {
        this.estadoCuentaSubsAnt = estadoCuentaSubsAnt;
    }

    public BigDecimal getBillingAccountSubsAnt() {
        return billingAccountSubsAnt;
    }

    public void setBillingAccountSubsAnt(BigDecimal billingAccountSubsAnt) {
        this.billingAccountSubsAnt = billingAccountSubsAnt;
    }

    public String getFechaInscripcionSubsAnt() {
        return fechaInscripcionSubsAnt;
    }

    public void setFechaInscripcionSubsAnt(String fechaInscripcionSubsAnt) {
        this.fechaInscripcionSubsAnt = fechaInscripcionSubsAnt;
    }

    public String getClassSubsAnt() {
        return classSubsAnt;
    }

    public void setClassSubsAnt(String classSubsAnt) {
        this.classSubsAnt = classSubsAnt;
    }

    public String getProblemaSubscriptorAnt() {
        return problemaSubscriptorAnt;
    }

    public void setProblemaSubscriptorAnt(String problemaSubscriptorAnt) {
        this.problemaSubscriptorAnt = problemaSubscriptorAnt;
    }

    public String getEdadMoraSubsAnt() {
        return edadMoraSubsAnt;
    }

    public void setEdadMoraSubsAnt(String edadMoraSubsAnt) {
        this.edadMoraSubsAnt = edadMoraSubsAnt;
    }

    public String getEstadoMoraSubsAnt() {
        return estadoMoraSubsAnt;
    }

    public void setEstadoMoraSubsAnt(String estadoMoraSubsAnt) {
        this.estadoMoraSubsAnt = estadoMoraSubsAnt;
    }

    public String getValorUltimoAjusteSusbAnt() {
        return valorUltimoAjusteSusbAnt;
    }

    public void setValorUltimoAjusteSusbAnt(String valorUltimoAjusteSusbAnt) {
        this.valorUltimoAjusteSusbAnt = valorUltimoAjusteSusbAnt;
    }

    public String getSaldoMoraSubsAnt() {
        return saldoMoraSubsAnt;
    }

    public void setSaldoMoraSubsAnt(String saldoMoraSubsAnt) {
        this.saldoMoraSubsAnt = saldoMoraSubsAnt;
    }

    public String getTelefono1SubsAnt() {
        return telefono1SubsAnt;
    }

    public void setTelefono1SubsAnt(String telefono1SubsAnt) {
        this.telefono1SubsAnt = telefono1SubsAnt;
    }

    public String getTelefono2SubsAnt() {
        return telefono2SubsAnt;
    }

    public void setTelefono2SubsAnt(String telefono2SubsAnt) {
        this.telefono2SubsAnt = telefono2SubsAnt;
    }

    public String getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(String estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public Date getFechaCraecion() {
        return fechaCraecion;
    }

    public void setFechaCraecion(Date fechaCraecion) {
        this.fechaCraecion = fechaCraecion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getPerfilCreacion() {
        return perfilCreacion;
    }

    public void setPerfilCreacion(String perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
    }

    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    public String getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(String perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    public BigDecimal getRelacionRadRR() {
        return relacionRadRR;
    }

    public void setRelacionRadRR(BigDecimal relacionRadRR) {
        this.relacionRadRR = relacionRadRR;
    }

    public BigDecimal getSubEdificio() {
        return subEdificio;
    }

    public void setSubEdificio(BigDecimal subEdificio) {
        this.subEdificio = subEdificio;
    }

    public BigDecimal getTecnologiaHabilitadaId() {
        return tecnologiaHabilitadaId;
    }

    public void setTecnologiaHabilitadaId(BigDecimal tecnologiaHabilitadaId) {
        this.tecnologiaHabilitadaId = tecnologiaHabilitadaId;
    }
}
