/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import co.com.claro.mgl.businessmanager.cm.CmtAvisoProgramadoMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTiempoSolicitudMgl;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@Entity
@Table(name = "TEC_SOL_TEC_HABILITADA", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Solicitud.findAll", query = "SELECT m FROM Solicitud m"),
    @NamedQuery(name = "Solicitud.findByCasoTcrm", query = "SELECT m FROM Solicitud m where m.casoTcrmId = :casoTcrmId"),
    @NamedQuery(name = "Solicitud.findBySolicitudes", query = "SELECT s FROM Solicitud s where s.direccionDetallada.direccionDetalladaId = :direccionDetalladaId and UPPER (s.estado)  IN ('PENDIENTE','VERIFICADO')")
})
public class Solicitud implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(
            name = "Solicitud.TEC_SOL_TEC_HABILITADA_SQ",
            sequenceName = "MGL_SCHEME.TEC_SOL_TEC_HABILITADA_SQ", allocationSize = 1)
    @GeneratedValue(generator = "Solicitud.TEC_SOL_TEC_HABILITADA_SQ")
    @Column(name = "SOL_TEC_HABILITADA_ID", nullable = false)
    private BigDecimal idSolicitud;
    @Column(name = "CUENTAMATRIZ")
    private String cuentaMatriz;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "TELEFONO")
    private String telefono;
    @Column(name = "DIRECCION1")
    private String direccion1;
    @Column(name = "TELEFONO1")
    private String telefono1;
    @Column(name = "CONTACTO")
    private String contacto;
    @Column(name = "TELCONTACTO")
    private String telContacto;
    @Column(name = "CONTACTO1")
    private String contacto1;
    @Column(name = "TELCONTACTO1")
    private String telContacto1;
    @Column(name = "SOLICITANTE")
    private String solicitante;
    @Column(name = "CORREO")
    private String correo;
    @Column(name = "MOVIL")
    private String movil;
    @Column(name = "SUPERVISOR")
    private String supervisor;
    @Column(name = "MOTIVO")
    private String motivo;
    @Column(name = "RESPUESTA")
    private String respuesta;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "TIPO")
    private String tipo;
    @Column(name = "FECHAINGRESO")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaIngreso;
    @Column(name = "FECHAMODIFICACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Column(name = "FECHACANCELACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCancelacion;
    @Column(name = "NODO")
    private String nodo;
    @Column(name = "CIUDAD")
    private String ciudad;
    @Column(name = "REGIONAL")
    private String regional;
    @Column(name = "TIPOVIVIENDA")
    private String tipoVivienda;
    @Column(name = "NUMPUERTA")
    private String numPuerta;
    @Column(name = "TELSOLICITANTE")
    private String telSolicitante;
    @Column(name = "EDIFICIO")
    private String edificio;
    @Column(name = "TORRES")
    private BigDecimal torres;
    @Column(name = "CANTIDADHHPP")
    private BigDecimal cantidadHHPP;
    @Column(name = "BARRIO")
    private String barrio;
    @Column(name = "SUCURSAL")
    private String sucursal;
    @Column(name = "DIRECCION_ANTIGUA_IGAC")
    private String direccionAntiguaIgac;
    @Column(name = "TIPOSOL")
    private String tipoSol;
    @Column(name = "USUARIO")
    private String usuario;
    @Column(name = "RGESTION")
    private String rptGestion;
    @Column(name = "COMPLEMENTO")
    private String complemento;
    @Column(name = "CAMBIO_NODO")
    private String cambioNodo;
    @Column(name = "NOMBRE_NUEVOEDIFICIO")
    private String nombreNuevoEdificio;
    @Column(name = "CUENTASUSCRIPTOR")
    private String cuentaSuscriptor;
    @Column(name = "ESTRATO_ANTIGUO")
    private String estratoAntiguo;
    @Column(name = "ESTRATO_NUEVO")
    private String estratoNuevo;
    @Column(name = "CORREGIR_HHPPP")
    private String corregirHHPP;
    @Column(name = "NUEVO_PRODUCTO")
    private String nuevoProducto;
    @Column(name = "ID_SOLICITANTE")
    private BigDecimal idSolicitante;
    @Column(name = "CUENTA")
    private String cuenta;
    @Column(name = "TIPOVENTA")
    private String tipoVenta;
    @Column(name = "CODIGOVERIFICACION")
    private String codigoVerificacion;
    @Column(name = "HOUSE_NUMBER")
    private String houseNumber;
    @Column(name = "STREET_NAME")
    private String streetName;
    @Column(name = "APARTMENT_NUMBER")
    private String aparmentNumber;
    @Column(name = "CAMBIO_DIR")
    private String cambioDir;
    @Column(name = "DISPONIBILIDAD_GESTION")
    private String disponibilidadGestion;
    @Column(name = "USUARIO_VERIFICADOR")
    private String usuarioVerificador;
    @OneToOne(mappedBy = "solicitudObj")
    private CmtTiempoSolicitudMgl tiempoSolicitudMgl;
    @Column(name = "ESTADO_TECNOLOGIA_HABILITADA")
    private String estadoHhpp;
    @Column(name = "ESTADO_TEC_HAB_NUEVO")
    private String estadoHhppNuevo;
    @JoinColumn(name = "TECNOLOGIA_HABILITADA_ID")
    @OneToOne
    private HhppMgl hhppMgl;
    @Column(name = "CASO_TCRM_ID")
    private String casoTcrmId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DIRECCION_DETALLADA_ID", referencedColumnName = "DIRECCION_DETALLADA_ID")    
    private CmtDireccionDetalladaMgl direccionDetallada;
    @Column(name = "RESULTADO_VERIFICACIONCE")
    private String resultadoVerificacionCamEstrato;
    @Column(name = "TIPO_DOC_SOPORTE_CE")
    private String tipoDocSoporteCamEstarto;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TECNOLOGIA", referencedColumnName = "BASICA_ID")
    private CmtBasicaMgl tecnologiaId;
    @Column(name = "CENTRO_POBLADO_ID")
    private BigDecimal centroPobladoId;
    @Column(name = "CP_TIPO_NIVEL_5", nullable = true)
    private String cpTipoNivel5 ;
    @Column(name = "CP_TIPO_NIVEL_6", nullable = true)
    private String cpTipoNivel6 ;
    @Column(name = "CP_VALOR_NIVEL_5", nullable = true)
    private String cpValorNivel5;
    @Column(name = "CP_VALOR_NIVEL_6", nullable = true)
    private String cpValorNivel6;
    @Column(name = "DOC_SOPORTE_CE")
    private String docSoporteCamEstarto;
    @Column(name = "DIR_RESPUESTA_GEO_AV")
    private String direccionRespuestaGeo;
    @Column(name = "TIPO_TECNOLOGIA_HAB_ID")
    private String tipoTecHabilitadaId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DETALLADA_ORIGEN_SOLICITUD_ID", referencedColumnName = "DIRECCION_DETALLADA_ID")    
    private CmtDireccionDetalladaMgl direccionDetalladaOrigenSolicitud;
    @Column(name = "ID_HIST_FACT", nullable = true)
    private BigDecimal idFactibilidad;
    @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "ID_BASICA_DIVI", referencedColumnName = "BASICA_ID")
    private CmtBasicaMgl idBasicaDivi;
    @Column(name = "COORD_CY", nullable = true)
    private String coordY;
    @Column(name = "COORD_CX", nullable = true)
    private String coordX;
    @Column(name = "CORREO_COPIA", nullable = true)
    private String correoCopia;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TEC_HAB_GESTIONADO", referencedColumnName = "TECNOLOGIA_HABILITADA_ID")    
    private HhppMgl idHhppGestionado;
    @NotNull
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @Column(name = "USUARIO_GESTIONADOR")
    private String usuarioGestionador;
    @Transient
    private String tipoSolicitudStr;
    @Transient
    private String colorAlerta;
    @Transient
    private String tipoAccionSolicitudStr;
    @Transient
    private String tipoHhpp;
    @Transient
    private CmtCuentaMatrizMgl cmtCuentaMatrizMglSol;
    @Transient
    private DrDireccion direccionOrigenSolicitud;
    @Transient
    private List<MarcasMgl> agregarMarcasHhppList;
    @Column(name = "CCMM")
    private String ccmm;
    @Column(name = "TIPO_GESTION")
    private String tipoGestion;
    @Column(name = "TIPO_HHPP")
    private String tipoHHPP;
    @Column(name = "SOLICITUD_OT")
    private String solicitudOT;
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @Column(name = "TIPO_DOCUMENTO")
    private String tipoDoc;
    @Column(name = "NUMERO_DOCUMENTO_CLIENTE")
    private String numDocCli;
    @Column(name = "ID_USUARIO_GESTION")
    private BigDecimal idUsuarioGestion;
    /*Brief 57762 Traslado HHPP Bloqueado*/
    @Getter
    @Setter
    @Column(name = "CUENTA_CLIENTE_TRASLADAR")
    private String numeroCuentaClienteTrasladar;

    @Transient
    @Getter
    @Setter
    private HhppMgl hhppReal;
    
    @Transient
    private boolean creacionAutomatica;

    @Column(name = "TIPO_DE_SOLICITUD")
    private String tipoDeSolicitud;
    
    @Column(name = "TIPO_DE_SERVICIO")					  
    private String tipoDeServicio;
								 
    @Column(name = "TIPO_DE_VALIDACION")
    private String tipoDeValidacion;
    
    @Column(name = "BW")
    private String bw;							
			
    @Column(name = "TIPO_DE_SITIO")
    private String tipoDeSitio;
    
    /* ----------------------------------------------------------------*/
    
    public boolean isCreacionAutomatica() {
        return creacionAutomatica;
    }

    public void setCreacionAutomatica(boolean creacionAutomatica) {
        this.creacionAutomatica = creacionAutomatica;
    }
    
    public String getIdCasoTcrm() {
        return casoTcrmId;
    }

    public void setIdCasoTcrm(String idCasoTcrm) {
        this.casoTcrmId = idCasoTcrm;
    }

    public BigDecimal getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(BigDecimal idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getCuentaMatriz() {
        return cuentaMatriz;
    }

    public void setCuentaMatriz(String cuentaMatriz) {
        this.cuentaMatriz = cuentaMatriz;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion1() {
        return direccion1;
    }

    public void setDireccion1(String direccion1) {
        this.direccion1 = direccion1;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTelContacto() {
        return telContacto;
    }

    public void setTelContacto(String telContacto) {
        this.telContacto = telContacto;
    }

    public String getContacto1() {
        return contacto1;
    }

    public void setContacto1(String contacto1) {
        this.contacto1 = contacto1;
    }

    public String getTelContacto1() {
        return telContacto1;
    }

    public void setTelContacto1(String telContacto1) {
        this.telContacto1 = telContacto1;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Date getFechaCancelacion() {
        return fechaCancelacion;
    }

    public void setFechaCancelacion(Date fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    public String getNodo() {
        return nodo;
    }

    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getRegional() {
        return regional;
    }

    public void setRegional(String regional) {
        this.regional = regional;
    }

    public String getTipoVivienda() {
        return tipoVivienda;
    }

    public void setTipoVivienda(String tipoVivienda) {
        this.tipoVivienda = tipoVivienda;
    }

    public String getNumPuerta() {
        return numPuerta;
    }

    public void setNumPuerta(String numPuerta) {
        this.numPuerta = numPuerta;
    }

    public String getTelSolicitante() {
        return telSolicitante;
    }

    public void setTelSolicitante(String telSolicitante) {
        this.telSolicitante = telSolicitante;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public BigDecimal getTorres() {
        return torres;
    }

    public void setTorres(BigDecimal torres) {
        this.torres = torres;
    }

    public BigDecimal getCantidadHHPP() {
        return cantidadHHPP;
    }

    public void setCantidadHHPP(BigDecimal cantidadHHPP) {
        this.cantidadHHPP = cantidadHHPP;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getDireccionAntiguaIgac() {
        return direccionAntiguaIgac;
    }

    public void setDireccionAntiguaIgac(String direccionAntiguaIgac) {
        this.direccionAntiguaIgac = direccionAntiguaIgac;
    }   
    
    public String getTipoSol() {
        return tipoSol;
    }

    public void setTipoSol(String tipoSol) {
        this.tipoSol = tipoSol;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getRptGestion() {
        return rptGestion;
    }

    public void setRptGestion(String rptGestion) {
        this.rptGestion = rptGestion;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCambioNodo() {
        return cambioNodo;
    }

    public void setCambioNodo(String cambioNodo) {
        this.cambioNodo = cambioNodo;
    }

    public String getNombreNuevoEdificio() {
        return nombreNuevoEdificio;
    }

    public void setNombreNuevoEdificio(String nombreNuevoEdificio) {
        this.nombreNuevoEdificio = nombreNuevoEdificio;
    }

    public String getCuentaSuscriptor() {
        return cuentaSuscriptor;
    }

    public void setCuentaSuscriptor(String cuentaSuscriptor) {
        this.cuentaSuscriptor = cuentaSuscriptor;
    }

    public String getEstratoAntiguo() {
        return estratoAntiguo;
    }

    public void setEstratoAntiguo(String estratoAntiguo) {
        this.estratoAntiguo = estratoAntiguo;
    }

    public String getEstratoNuevo() {
        return estratoNuevo;
    }

    public void setEstratoNuevo(String estratoNuevo) {
        this.estratoNuevo = estratoNuevo;
    }

    public String getCorregirHHPP() {
        return corregirHHPP;
    }

    public void setCorregirHHPP(String corregirHHPP) {
        this.corregirHHPP = corregirHHPP;
    }

    public String getNuevoProducto() {
        return nuevoProducto;
    }

    public void setNuevoProducto(String nuevoProducto) {
        this.nuevoProducto = nuevoProducto;
    }

    public BigDecimal getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(BigDecimal idSolicitante) {
        this.idSolicitante = idSolicitante;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getTipoVenta() {
        return tipoVenta;
    }

    public void setTipoVenta(String tipoVenta) {
        this.tipoVenta = tipoVenta;
    }

    public String getCodigoVerificacion() {
        return codigoVerificacion;
    }

    public void setCodigoVerificacion(String codigoVerificacion) {
        this.codigoVerificacion = codigoVerificacion;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getAparmentNumber() {
        return aparmentNumber;
    }

    public void setAparmentNumber(String aparmentNumber) {
        this.aparmentNumber = aparmentNumber;
    }

    public String getCambioDir() {
        return cambioDir;
    }

    public void setCambioDir(String cambioDir) {
        this.cambioDir = cambioDir;
    }

    public String getDisponibilidadGestion() {
        return disponibilidadGestion;
    }

    public void setDisponibilidadGestion(String disponibilidadGestion) {
        this.disponibilidadGestion = disponibilidadGestion;
    }

    public String getUsuarioVerificador() {
        return usuarioVerificador;
    }

    public void setUsuarioVerificador(String usuarioVerificador) {
        this.usuarioVerificador = usuarioVerificador;
    }

    public String getEstadoHhpp() {
        return estadoHhpp;
    }

    public void setEstadoHhpp(String estadoHhpp) {
        this.estadoHhpp = estadoHhpp;
    }

    public String getEstadoHhppNuevo() {
        return estadoHhppNuevo;
    }

    public void setEstadoHhppNuevo(String estadoHhppNuevo) {
        this.estadoHhppNuevo = estadoHhppNuevo;
    }

    public String getTipoSolicitudStr() {
        tipoSolicitudStr = "";
        if (tipo != null && !tipo.trim().isEmpty()) {
            if (tipo.equalsIgnoreCase("VTCASA")) {
                tipoSolicitudStr = "VERIFICACION DE CASAS";
            } else if (tipo.equalsIgnoreCase("CREACION HHPP UNIDI")) {
                tipoSolicitudStr = "CREACION DE HHPP UNIDIRECCIONAL";
            }
        }
        return tipoSolicitudStr;
    }

    public String getTiempoEjecuion() {
        String result = "";
        if (this.fechaIngreso != null && this.fechaCancelacion != null) {
            long diffDate = fechaCancelacion.getTime() - fechaIngreso.getTime();
            //Diferencia de las Fechas en Minutos
            long diffMinutes = Math.abs(diffDate / (60 * 1000));
            long minutes = diffMinutes % 60;
            long hours = Math.abs(diffDate / (60 * 60 * 1000));

            String hoursStr = (hours < 10 ? "0" + String.valueOf(hours)
                    : String.valueOf(hours));
            String minutesStr = (minutes < 10 ? "0" + String.valueOf(minutes)
                    : String.valueOf(minutes));

            result = hoursStr + ":" + minutesStr;
        }
        return result;
    }

    public String getColorAlerta() {
        return colorAlerta;
    }

    public void setColorAlerta(String colorAlerta) {
        this.colorAlerta = colorAlerta;
    }

    public String getTiempo(Date dateInicio, Date dateFin) {
        String result = "00:00:00";
        if (dateInicio != null) {
            Date fechaG = new Date();
            if (dateFin != null) {
                fechaG = dateFin;
            }
            long diffDate = fechaG.getTime() - dateInicio.getTime();
            //Diferencia de las Fechas en Segundos
            long diffSeconds = Math.abs(diffDate / 1000);
            //Diferencia de las Fechas en Minutos
            long diffMinutes = Math.abs(diffDate / (60 * 1000));
            long seconds = diffSeconds % 60;
            long minutes = diffMinutes % 60;
            long hours = Math.abs(diffDate / (60 * 60 * 1000));

            String hoursStr = (hours < 10 ? "0" + String.valueOf(hours) : String.valueOf(hours));
            String minutesStr = (minutes < 10 ? "0" + String.valueOf(minutes) : String.valueOf(minutes));
            String secondsStr = (seconds < 10 ? "0" + String.valueOf(seconds) : String.valueOf(seconds));

            result = hoursStr + ":" + minutesStr + ":" + secondsStr;
        }
        return result;
    }

    public CmtTiempoSolicitudMgl getTiempoSolicitudMgl() {
        return tiempoSolicitudMgl;
    }

    public void setTiempoSolicitudMgl(CmtTiempoSolicitudMgl tiempoSolicitudMgl) {
        this.tiempoSolicitudMgl = tiempoSolicitudMgl;
    }
    
    public HhppMgl getHhppMgl() {
        return hhppMgl;
    }

    public void setHhppMgl(HhppMgl hhppMgl) {
        this.hhppMgl = hhppMgl;
    }

    public String getCasoTcrmId() {
        return casoTcrmId;
    }

    public void setCasoTcrmId(String casoTcrmId) {
        this.casoTcrmId = casoTcrmId;
    }

    public CmtDireccionDetalladaMgl getDireccionDetallada() {
        return direccionDetallada;
    }

    public void setDireccionDetallada(CmtDireccionDetalladaMgl direccionDetallada) {
        this.direccionDetallada = direccionDetallada;
    }

    public String getResultadoVerificacionCamEstrato() {
        return resultadoVerificacionCamEstrato;
    }

    public void setResultadoVerificacionCamEstrato(String resultadoVerificacionCamEstrato) {
        this.resultadoVerificacionCamEstrato = resultadoVerificacionCamEstrato;
    }

    public String getTipoDocSoporteCamEstarto() {
        return tipoDocSoporteCamEstarto;
    }

    public void setTipoDocSoporteCamEstarto(String tipoDocSoporteCamEstarto) {
        this.tipoDocSoporteCamEstarto = tipoDocSoporteCamEstarto;
    }

    public CmtBasicaMgl getTecnologiaId() {
        return tecnologiaId;
    }

    public void setTecnologiaId(CmtBasicaMgl tecnologiaId) {
        this.tecnologiaId = tecnologiaId;
    }

    public BigDecimal getCentroPobladoId() {
        return centroPobladoId;
    }

    public void setCentroPobladoId(BigDecimal centroPobladoId) {
        this.centroPobladoId = centroPobladoId;
    }

    public String getTipoAccionSolicitudStr() {
        return tipoAccionSolicitudStr;
    }

    public void setTipoAccionSolicitudStr(String tipoAccionSolicitudStr) {
        this.tipoAccionSolicitudStr = tipoAccionSolicitudStr;
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

    public String getDocSoporteCamEstarto() {
        return docSoporteCamEstarto;
    }

    public void setDocSoporteCamEstarto(String docSoporteCamEstarto) {
        this.docSoporteCamEstarto = docSoporteCamEstarto;
    }

    @PrePersist
    public void prePersist() throws ApplicationException {
        if (this.direccion != null
                && !this.direccion.trim().isEmpty()
                && this.direccion.length() > 20) {
            this.direccion = this.direccion.substring(0, 20);
        }
    }
    
    @PostUpdate
    private void notificarCambioEstado() {
        CmtAvisoProgramadoMglManager capmm = new CmtAvisoProgramadoMglManager();
        capmm.verificarCambio(this);
    }
    
    public String getTipoHhpp() {
        return tipoHhpp;
    }

    public void setTipoHhpp(String tipoHhpp) {
        this.tipoHhpp = tipoHhpp;
    }

    public CmtCuentaMatrizMgl getCmtCuentaMatrizMglSol() {
        return cmtCuentaMatrizMglSol;
    }

    public void setCmtCuentaMatrizMglSol(CmtCuentaMatrizMgl cmtCuentaMatrizMglSol) {
        this.cmtCuentaMatrizMglSol = cmtCuentaMatrizMglSol;
    }

    public DrDireccion getDireccionOrigenSolicitud() {
        return direccionOrigenSolicitud;
    }

    public void setDireccionOrigenSolicitud(DrDireccion direccionOrigenSolicitud) {
        this.direccionOrigenSolicitud = direccionOrigenSolicitud;
    }

    public CmtDireccionDetalladaMgl getDireccionDetalladaOrigenSolicitud() {
        return direccionDetalladaOrigenSolicitud;
    }

    public void setDireccionDetalladaOrigenSolicitud(CmtDireccionDetalladaMgl direccionDetalladaOrigenSolicitud) {
        this.direccionDetalladaOrigenSolicitud = direccionDetalladaOrigenSolicitud;
    }
   
    public String getTipoTecHabilitadaId() {
        return tipoTecHabilitadaId;
    }

    public void setTipoTecHabilitadaId(String tipoTecHabilitadaId) {
        this.tipoTecHabilitadaId = tipoTecHabilitadaId;
    }

    public String getDireccionRespuestaGeo() {
        return direccionRespuestaGeo;
    }

    public void setDireccionRespuestaGeo(String direccionRespuestaGeo) {
        this.direccionRespuestaGeo = direccionRespuestaGeo;
    }

    public List<MarcasMgl> getAgregarMarcasHhppList() {
        return agregarMarcasHhppList;
    }

    public void setAgregarMarcasHhppList(List<MarcasMgl> agregarMarcasHhppList) {
        this.agregarMarcasHhppList = agregarMarcasHhppList;
    }

    public BigDecimal getIdFactibilidad() {
        return idFactibilidad;
    }

    public void setIdFactibilidad(BigDecimal idFactibilidad) {
        this.idFactibilidad = idFactibilidad;
    }

    public CmtBasicaMgl getIdBasicaDivi() {
        return idBasicaDivi;
    }

    public void setIdBasicaDivi(CmtBasicaMgl idBasicaDivi) {
        this.idBasicaDivi = idBasicaDivi;
    }


    public String getCoordY() {
        return coordY;
    }

    public void setCoordY(String coordY) {
        this.coordY = coordY;
    }

    public String getCoordX() {
        return coordX;
    }

    public void setCoordX(String coordX) {
        this.coordX = coordX;
    }

    public String getCorreoCopia() {
        return correoCopia;
    }

    public void setCorreoCopia(String correoCopia) {
        this.correoCopia = correoCopia;
    }

    public HhppMgl getIdHhppGestionado() {
        return idHhppGestionado;
    }

    public void setIdHhppGestionado(HhppMgl idHhppGestionado) {
        this.idHhppGestionado = idHhppGestionado;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public String getUsuarioGestionador() {
        return usuarioGestionador;
    }

    public void setUsuarioGestionador(String usuarioGestionador) {
        this.usuarioGestionador = usuarioGestionador;
    }

    /**
     * get field @Column(name = "CCMM")
     *
     * @return ccmm @Column(name = "CCMM")

     */
    public String getCcmm() {
        return this.ccmm;
    }

    /**
     * set field @Column(name = "CCMM")
     *
     * @param ccmm @Column(name = "CCMM")

     */
    public void setCcmm(String ccmm) {
        this.ccmm = ccmm;
    }

    /**
     * get field @Column(name = "TIPO_GESTION")
     *
     * @return tipoGestion @Column(name = "TIPO_GESTION")

     */
    public String getTipoGestion() {
        return this.tipoGestion;
    }

    /**
     * set field @Column(name = "TIPO_GESTION")
     *
     * @param tipoGestion @Column(name = "TIPO_GESTION")

     */
    public void setTipoGestion(String tipoGestion) {
        this.tipoGestion = tipoGestion;
    }

    /**
     * get field @Column(name = "TIPO_HHPP")
     *
     * @return tipoHHPP @Column(name = "TIPO_HHPP")

     */
    public String getTipoHHPP() {
        return this.tipoHHPP;
    }

    /**
     * set field @Column(name = "TIPO_HHPP")
     *
     * @param tipoHHPP @Column(name = "TIPO_HHPP")

     */
    public void setTipoHHPP(String tipoHHPP) {
        this.tipoHHPP = tipoHHPP;
    }

    /**
     * get field @Column(name = "SOLICITUD_OT")
     *
     * @return solicitudOT @Column(name = "SOLICITUD_OT")

     */
    public String getSolicitudOT() {
        return this.solicitudOT;
    }

    /**
     * set field @Column(name = "SOLICITUD_OT")
     *
     * @param solicitudOT @Column(name = "SOLICITUD_OT")

     */
    public void setSolicitudOT(String solicitudOT) {
        this.solicitudOT = solicitudOT;
    }

    /**
     * get field @Column(name = "OBSERVACIONES")
     *
     * @return observaciones @Column(name = "OBSERVACIONES")

     */
    public String getObservaciones() {
        return this.observaciones;
    }

    /**
     * set field @Column(name = "OBSERVACIONES")
     *
     * @param observaciones @Column(name = "OBSERVACIONES")

     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getNumDocCliente() {
        return numDocCli;
    }

    public void setNumDocCliente(String numDocCli) {
        this.numDocCli = numDocCli;
    }

    /**
     * get field @Column(name = "ID_USUARIO_GESTION")
     *
     * @return idUsuarioGestion @Column(name = "ID_USUARIO_GESTION")

     */
    public BigDecimal getIdUsuarioGestion() {
        return this.idUsuarioGestion;
    }

    /**
     * set field @Column(name = "ID_USUARIO_GESTION")
     *
     * @param idUsuarioGestion @Column(name = "ID_USUARIO_GESTION")

     */
    public void setIdUsuarioGestion(BigDecimal idUsuarioGestion) {
        this.idUsuarioGestion = idUsuarioGestion;
    }

    /**
     * @return the tipoDeSolicitud
     */
    public String getTipoDeSolicitud() {
        return tipoDeSolicitud;
    }

    /**
     * @param tipoDeSolicitud the tipoDeSolicitud to set
     */
    public void setTipoDeSolicitud(String tipoDeSolicitud) {
        this.tipoDeSolicitud = tipoDeSolicitud;
    }

    /**
     * @return the tipoDeServicio
     */
    public String getTipoDeServicio() {
        return tipoDeServicio;
    }

    /**
     * @param tipoDeServicio the tipoDeServicio to set
     */
    public void setTipoDeServicio(String tipoDeServicio) {
        this.tipoDeServicio = tipoDeServicio;
    }

    /**
     * @return the tipoDeValidacion
     */
    public String getTipoDeValidacion() {
        return tipoDeValidacion;
    }

    /**
     * @param tipoDeValidacion the tipoDeValidacion to set
     */
    public void setTipoDeValidacion(String tipoDeValidacion) {
        this.tipoDeValidacion = tipoDeValidacion;
    }

    /**
     * @return the bw
     */
    public String getBw() {
        return bw;
    }

    /**
     * @param bw the bw to set
     */
    public void setBw(String bw) {
        this.bw = bw;
    }

    /**
     * @return the tipoDeSitio
     */
    public String getTipoDeSitio() {
        return tipoDeSitio;
    }

    /**
     * @param tipoDeSitio the tipoDeSitio to set
     */
    public void setTipoDeSitio(String tipoDeSitio) {
        this.tipoDeSitio = tipoDeSitio;
    }
}
