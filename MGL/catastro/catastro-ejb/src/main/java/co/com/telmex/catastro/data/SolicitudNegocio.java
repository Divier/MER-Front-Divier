package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Clase SolicitudNegocio
 * Implementa Serialización.
 *
 * @author 	Nombre del autor.
 * @version	1.0
 */
public class SolicitudNegocio implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal sonId;
    private String sonMotivo;
    private String sonNomSolicitante;
    private String sonContacto;
    private String sonTelContacto;
    private Date sonFechaIngreso;
    private Date sonFechaSolicitud;
    private String sonTiempohoras;
    private String sonEstado;
    private String sonTipoSolucion;
    private String sonResGestion;
    private String sonCorregirHhpp;
    private String sonCambioNodo;
    private String sonNuevoProducto;
    private String sonEstratoAntiguo;
    private String sonEstratoNuevo;
    private String sonObservaciones;
    private String sonFormatoIgac;
    private String sonServinformacion;
    private String sonNostandar;
    private String sonTipoSolicitud;
    private String sonEstrato;
    private String sonNivSocioeconomico;
    private String sonZipcode;
    private String sonDiralterna;
    private String sonLocalidad;
    private String sonMz;
    private String sonLongitud;
    private String sonLatitud;
    private String sonLadomz;
    private String sonHeadEnd;
    private String sonCodSolicitante;
    private String sonEmailSolicitante;
    private String sonCelSolicitante;
    private Date sonFechaFinalizacion;
    private String sonEstadoSol;
    private String sonEstadoUni;
    private String sonUsuGestion;
    private String sonCampoa1;
    private String sonCampoa2;
    private String sonCampoa3;
    private String sonCampoa4;
    private String sonCampoa5;
    private String sonDirExiste;
    private String sonDirValidada;
    private String sonAreaSolicitante;
    private String sonNodoUsuario;
    private String sonPlaca;
    private String sonComplemento;
    private BigDecimal sonConfiabilidad;
    private String sonManzanaCatastral;
    private String sonActividadEconomica;
    private String sonFuente;
    private Hhpp hhpp;
    private TipoHhpp tipoHhpp;
    private Marcas marcas;
    private TipoHhppRed tipoHhppRed;
    private TipoHhppConexion tipoHhppConexion;
    private Nodo nodo;
    private GeograficoPolitico geograficoPolitico;
    private Geografico geografico;
    private Date sonFechaCreacion;
    private String sonUsuarioCreacion;
    private Date sonFechaModificacion;
    private String sonUsuarioModificacion;
    private String sonArchivoGeneradoRr;

    /**
     * Constructor
     */
    public SolicitudNegocio() {
    }

    /**
     * Constructor con parámetros.
     * @param sonId Entero Id de la solicitud.
     */
    public SolicitudNegocio(BigDecimal sonId) {
        this.sonId = sonId;
    }

    /**
     * Constructor con parámetros.
     * @param sonId Entero Id de la solicitud.
     * @param sonObservaciones Observaciones de la solicitud.
     */
    public SolicitudNegocio(BigDecimal sonId, String sonObservaciones) {
        this.sonId = sonId;
        this.sonObservaciones = sonObservaciones;
    }

    /**
     * Obtiene el Id de la solicitud.
     * @return Entero con el Id de la solicitud.
     */
    public BigDecimal getSonId() {
        return sonId;
    }

    /**
     * Establece el Id de la solicitud.
     * @param sonId Entero con el Id de la solicitud.
     */
    public void setSonId(BigDecimal sonId) {
        this.sonId = sonId;
    }

    /**
     * Obtiene el Motivo de la solicitud.
     * @return Cadena con el motivo de la solicitud.
     */
    public String getSonMotivo() {
        return sonMotivo;
    }

    /**
     * Establece el motivo de la solicitud.
     * @param sonMotivo Cadena con el motivo de la solicitud.
     */
    public void setSonMotivo(String sonMotivo) {
        this.sonMotivo = sonMotivo;
    }

    /**
     * Obtiene el nombre del solicitante.
     * @return Cadena con el nombre del solicitante.
     */
    public String getSonNomSolicitante() {
        return sonNomSolicitante;
    }

    /**
     * Establece el nombre del solicitante.
     * @param sonNomSolicitante Cadena con el nombre del solicitante.
     */
    public void setSonNomSolicitante(String sonNomSolicitante) {
        this.sonNomSolicitante = sonNomSolicitante;
    }

    /**
     * Obtiene el nombre del contacto.
     * @return Cadena con el nombre del contacto.
     */
    public String getSonContacto() {
        return sonContacto;
    }

    /**
     * Establece el nombre del contacto.
     * @param sonContacto Cadena con el nombre del contacto.
     */
    public void setSonContacto(String sonContacto) {
        this.sonContacto = sonContacto;
    }

    /**
     * Obtiene el teléfono del contacto.
     * @return Cadena con el número del teléfono de contacto.
     */
    public String getSonTelContacto() {
        return sonTelContacto;
    }

    /**
     * Establece el número telefónico del contacto.
     * @param sonTelContacto Cadena con el número telefónico del contacto.
     */
    public void setSonTelContacto(String sonTelContacto) {
        this.sonTelContacto = sonTelContacto;
    }

    /**
     * Obtiene la fecha de ingreso de la solicitud.
     * @return Fecha de ingreso de la solicitud.
     */
    public Date getSonFechaIngreso() {
        return sonFechaIngreso;
    }

    /**
     * Establece la fecha de ingreso de la solicitud.
     * @param sonFechaIngreso Fecha de ingreso de la solicitud.
     */
    public void setSonFechaIngreso(Date sonFechaIngreso) {
        this.sonFechaIngreso = sonFechaIngreso;
    }

    /**
     * Obtiene la fecha correspondiente a la solicitud.
     * @return Fecha de solicitud.
     */
    public Date getSonFechaSolicitud() {
        return sonFechaSolicitud;
    }

    /**
     * Establece la fecha de la solicitud.
     * @param sonFechaSolicitud Fecha de la solicitud.
     */
    public void setSonFechaSolicitud(Date sonFechaSolicitud) {
        this.sonFechaSolicitud = sonFechaSolicitud;
    }

    /**
     * Obtiene el tiempo en horas.
     * @return Cadena con el tiempo en horas.
     */
    public String getSonTiempohoras() {
        return sonTiempohoras;
    }

    /**
     * Establece el tiempo en horas.
     * @param sonTiempohoras Cadena con el tiempo en horas.
     */
    public void setSonTiempohoras(String sonTiempohoras) {
        this.sonTiempohoras = sonTiempohoras;
    }

    /**
     * Obtiene el estado de la solicitud.
     * @return Cadena con el estado de la solicitud.
     */
    public String getSonEstado() {
        return sonEstado;
    }

    /**
     * Establece el estado de la solicitud
     * @param sonEstado Cadena con el estado de la solicitud.
     */
    public void setSonEstado(String sonEstado) {
        this.sonEstado = sonEstado;
    }

    /**
     * Obtiene el tipo de solución
     * @return Cadena con el tipo de solución.
     */
    public String getSonTipoSolucion() {
        return sonTipoSolucion;
    }

    /**
     * Establece el tipo de solución.
     * @param sonTipoSolucion Cadena con el tipo de solución.
     */
    public void setSonTipoSolucion(String sonTipoSolucion) {
        this.sonTipoSolucion = sonTipoSolucion;
    }

    /**
     * Obtiene el resultado de la gestión.
     * @return Cadena con el resultado de la gestión.
     */
    public String getSonResGestion() {
        return sonResGestion;
    }

    /**
     * Establece el resultado de la gestión.
     * @param sonResGestion Cadena con el resultado de la gestión.
     */
    public void setSonResGestion(String sonResGestion) {
        this.sonResGestion = sonResGestion;
    }

    /**
     * Obtiene si se debe corregir el hhpp.
     * @return Cadena con el corregir hhpp.
     */
    public String getSonCorregirHhpp() {
        return sonCorregirHhpp;
    }

    /**
     * Establece si se debe corregir hhpp.
     * @param sonCorregirHhpp Cadena con el corregirhhpp.
     */
    public void setSonCorregirHhpp(String sonCorregirHhpp) {
        this.sonCorregirHhpp = sonCorregirHhpp;
    }

    /**
     * Obtiene si se debe cambiar de nodo.
     * @return Cadena con el cambiar nodo.
     */
    public String getSonCambioNodo() {
        return sonCambioNodo;
    }

    /**
     * Establece si se debe cambiar nodo.
     * @param sonCambioNodo Cadena con el cambio nodo.
     */
    public void setSonCambioNodo(String sonCambioNodo) {
        this.sonCambioNodo = sonCambioNodo;
    }

    /**
     * Obtiene el Nuevo Producto.
     * @return Cadena con el Nuevo Producto.
     */
    public String getSonNuevoProducto() {
        return sonNuevoProducto;
    }

    /**
     * Establece el nuevo producto.
     * @param sonNuevoProducto Cadena con el nuevo producto.
     */
    public void setSonNuevoProducto(String sonNuevoProducto) {
        this.sonNuevoProducto = sonNuevoProducto;
    }

    /**
     * Obtiene el estrato antiguo
     * @return Cadena con el estrato antiguo.
     */
    public String getSonEstratoAntiguo() {
        return sonEstratoAntiguo;
    }

    /**
     * Establece el estrato antiguo.
     * @param sonEstratoAntiguo Cadena con el estrato antiguo.
     */
    public void setSonEstratoAntiguo(String sonEstratoAntiguo) {
        this.sonEstratoAntiguo = sonEstratoAntiguo;
    }

    /**
     * Obtiene el estrato nuevo.
     * @return Cadena con el estrato nuevo.
     */
    public String getSonEstratoNuevo() {
        return sonEstratoNuevo;
    }

    /**
     * Establece el estrato nuevo.
     * @param sonEstratoNuevo Cadena con el estrato nuevo.
     */
    public void setSonEstratoNuevo(String sonEstratoNuevo) {
        this.sonEstratoNuevo = sonEstratoNuevo;
    }

    /**
     * Obtiene la fecha de creación.
     * @return Fecha de creación.
     */
    public Date getSonFechaCreacion() {
        return sonFechaCreacion;
    }

    /**
     * Establece la fecha de creación. 
     * @param sonFechaCreacion Fecha de creación.
     */
    public void setSonFechaCreacion(Date sonFechaCreacion) {
        this.sonFechaCreacion = sonFechaCreacion;
    }

    /**
     * Obtiene el usuario que crea la solicitud. 
     * @return Cadena con el usuario que crea la solicitud.
     */
    public String getSonUsuarioCreacion() {
        return sonUsuarioCreacion;
    }

    /**
     * Establece el usuario que crea la solicitud.
     * @param sonUsuarioCreacion Cadena con el usuario que crea la solicitud.
     */
    public void setSonUsuarioCreacion(String sonUsuarioCreacion) {
        this.sonUsuarioCreacion = sonUsuarioCreacion;
    }

    /**
     * Obtiene la fecha de modificación.
     * @return Fecha de modificación.
     */
    public Date getSonFechaModificacion() {
        return sonFechaModificacion;
    }

    /**
     * Establece la fecha de modificación
     * @param sonFechaModificacion Fecha de modificación.
     */
    public void setSonFechaModificacion(Date sonFechaModificacion) {
        this.sonFechaModificacion = sonFechaModificacion;
    }

    /**
     * Obtiene el usuario que realiza la modificación.
     * @return Cadena con el usuario que realiza la modificación.
     */
    public String getSonUsuarioModificacion() {
        return sonUsuarioModificacion;
    }

    /**
     * Establece el usuario que realiza la modificación. 
     * @param sonUsuarioModificacion Cadena con el usuario que realiza la modificación.
     */
    public void setSonUsuarioModificacion(String sonUsuarioModificacion) {
        this.sonUsuarioModificacion = sonUsuarioModificacion;
    }

    /**
     * Obtiene las observaciones.
     * @return Cadena con las observaciones.
     */
    public String getSonObservaciones() {
        return sonObservaciones;
    }

    /**
     * Establece las observaciones.
     * @param sonObservaciones Cadena con las observaciones.
     */
    public void setSonObservaciones(String sonObservaciones) {
        this.sonObservaciones = sonObservaciones;
    }

    /**
     * Obtiene la dirección en formato IGAC.
     * @return Cadena con la dirección en formato IGAC.
     */
    public String getSonFormatoIgac() {
        return sonFormatoIgac;
    }

    /**
     * Establece la dirección en formato IGAC.
     * @param sonFormatoIgac Cadena con la dirección en formato IGAC.
     */
    public void setSonFormatoIgac(String sonFormatoIgac) {
        this.sonFormatoIgac = sonFormatoIgac;
    }

    /**
     * Obtiene la cadena de información de ServiInformación.
     * @return Cadena con información de ServiInformación.
     */
    public String getSonServinformacion() {
        return sonServinformacion;
    }

    /**
     * Establece el valor del campo ServiInformación
     * @param sonServinformacion Cadena con información de ServiInformación.
     */
    public void setSonServinformacion(String sonServinformacion) {
        this.sonServinformacion = sonServinformacion;
    }

    /**
     * Obtiene el valor de la dirección ingresada por el usuario.
     * @return Cadena con la dirección ingresada.
     */
    public String getSonNostandar() {
        return sonNostandar;
    }

    /**
     * Establece el valor de la dirección no estandarizada.
     * @param sonNostandar Cadena con la dirección.
     */
    public void setSonNostandar(String sonNostandar) {
        this.sonNostandar = sonNostandar;
    }

    /**
     * Obtiene el tipo de solicitud.
     * @return Cadena con el tipo de solicitud.
     */
    public String getSonTipoSolicitud() {
        return sonTipoSolicitud;
    }

    /**
     * Establece el tipo de solicitud.
     * @param sonTipoSolicitud Cadena con el tipo de solicitud.
     */
    public void setSonTipoSolicitud(String sonTipoSolicitud) {
        this.sonTipoSolicitud = sonTipoSolicitud;
    }

    /**
     * Obtiene el estrato.
     * @return Cadena con el valor del estrato.
     */
    public String getSonEstrato() {
        return sonEstrato;
    }

    /**
     * Establece el estrato.
     * @param sonEstrato Cadena con el valor del estrato.
     */
    public void setSonEstrato(String sonEstrato) {
        this.sonEstrato = sonEstrato;
    }

    /**
     * Obtiene el nivel socio económico.
     * @return Cadena con el valor del nivel socio económico.
     */
    public String getSonNivSocioeconomico() {
        return sonNivSocioeconomico;
    }

    /**
     * Establece el valor del nivel socio económico.
     * @param sonNivSocioeconomico Cadena con el valor del nivel socio económico.
     */
    public void setSonNivSocioeconomico(String sonNivSocioeconomico) {
        this.sonNivSocioeconomico = sonNivSocioeconomico;
    }

    /**
     * Obtiene el código Zip.
     * @return Cadena con el código zip.
     */
    public String getSonZipcode() {
        return sonZipcode;
    }

    /**
     * Establece el codigo zip.
     * @param sonZipcode Cadena con el codigo zip.
     */
    public void setSonZipcode(String sonZipcode) {
        this.sonZipcode = sonZipcode;
    }

    /**
     * Obtiene la dirección alterna.
     * @return Cadena con la dirección alterna.
     */
    public String getSonDiralterna() {
        return sonDiralterna;
    }

    /**
     * Establece la dirección alterna.
     * @param sonDiralterna Cadena con la dirección alterna.
     */
    public void setSonDiralterna(String sonDiralterna) {
        this.sonDiralterna = sonDiralterna;
    }

    /**
     * Obtiene la localidad.
     * @return Cadena con la localidad.
     */
    public String getSonLocalidad() {
        return sonLocalidad;
    }

    /**
     * Establece la localidad.
     * @param sonLocalidad Cadena con la localidad.
     */
    public void setSonLocalidad(String sonLocalidad) {
        this.sonLocalidad = sonLocalidad;
    }

    /**
     * Obtiene la Mz.
     * @return Cadena con la Mz.
     */
    public String getSonMz() {
        return sonMz;
    }

    /**
     * Establece la Mz.
     * @param sonMz Cadena con la Mz.
     */
    public void setSonMz(String sonMz) {
        this.sonMz = sonMz;
    }

    /**
     * Obtiene la longitud.
     * @return Cadena con la longitud.
     */
    public String getSonLongitud() {
        return sonLongitud;
    }

    /**
     * Establece la longitud.
     * @param sonLongitud Cadena con la longitud.
     */
    public void setSonLongitud(String sonLongitud) {
        this.sonLongitud = sonLongitud;
    }

    /**
     * Obtiene la latitud.
     * @return Cadena con la latitud.
     */
    public String getSonLatitud() {
        return sonLatitud;
    }

    /**
     * Establece la latitud.
     * @param sonLatitud Cadena con la latitud.
     */
    public void setSonLatitud(String sonLatitud) {
        this.sonLatitud = sonLatitud;
    }

    /**
     * Obtiene el lado Mz.
     * @return Cadena con el lado Mz.
     */
    public String getSonLadomz() {
        return sonLadomz;
    }

    /**
     * Establece el lado Mz.
     * @param sonLadomz Cadena con el lado Mz.
     */
    public void setSonLadomz(String sonLadomz) {
        this.sonLadomz = sonLadomz;
    }

    /**
     * Obtiene el HeadEnd.
     * @return Cadena con el HeadEnd.
     */
    public String getSonHeadEnd() {
        return sonHeadEnd;
    }

    /**
     * Establece el HeadEnd.
     * @param sonHeadEnd Cadena con el HeadEnd.
     */
    public void setSonHeadEnd(String sonHeadEnd) {
        this.sonHeadEnd = sonHeadEnd;
    }

    /**
     * Obtiene el código del solicitante.
     * @return Cadena con el código del solicitante.
     */
    public String getSonCodSolicitante() {
        return sonCodSolicitante;
    }

    /**
     * Establece el código del solicitante.
     * @param sonCodSolicitante Cadena con el código del solicitante.
     */
    public void setSonCodSolicitante(String sonCodSolicitante) {
        this.sonCodSolicitante = sonCodSolicitante;
    }

    /**
     * Obtiene el Email del solicitante
     * @return Cadena con el Email del solicitante.
     */
    public String getSonEmailSolicitante() {
        return sonEmailSolicitante;
    }

    /**
     * Establece el Email del solicitante.
     * @param sonEmailSolicitante Cadena con el Email del solicitante.
     */
    public void setSonEmailSolicitante(String sonEmailSolicitante) {
        this.sonEmailSolicitante = sonEmailSolicitante;
    }

    /**
     * Obtiene el numero celular del solicitante.
     * @return Cadena con el numero celular del solicitante.
     */
    public String getSonCelSolicitante() {
        return sonCelSolicitante;
    }

    /**
     * Establece el numero del solicitante.
     * @param sonCelSolicitante Cadena con el numero celular del solicitante.
     */
    public void setSonCelSolicitante(String sonCelSolicitante) {
        this.sonCelSolicitante = sonCelSolicitante;
    }

    /**
     * Obtiene la fecha de finalización
     * @return Fecha de finalización
     */
    public Date getSonFechaFinalizacion() {
        return sonFechaFinalizacion;
    }

    /**
     * Establece la fecha de Finalización
     * @param sonFechaFinalizacion Fecha de finalización.
     */
    public void setSonFechaFinalizacion(Date sonFechaFinalizacion) {
        this.sonFechaFinalizacion = sonFechaFinalizacion;
    }

    /**
     * Obtiene el EstadoSol
     * @return Cadena con el EstadoSol.
     */
    public String getSonEstadoSol() {
        return sonEstadoSol;
    }

    /**
     * Establece el estadoSol
     * @param sonEstadoSol Cadena con el EstadoSol.
     */
    public void setSonEstadoSol(String sonEstadoSol) {
        this.sonEstadoSol = sonEstadoSol;
    }

    /**
     * Obtiene el estado Uni.
     * @return Cadena con el estado Uni.
     */
    public String getSonEstadoUni() {
        return sonEstadoUni;
    }

    /**
     * Establece el estado Uni.
     * @param sonEstadoUni Cadena con el estado Uni.
     */
    public void setSonEstadoUni(String sonEstadoUni) {
        this.sonEstadoUni = sonEstadoUni;
    }

    /**
     * Obtiene el Son Usuario Gestión.
     * @return Cadena con el Son Usuario Gestión.
     */
    public String getSonUsuGestion() {
        return sonUsuGestion;
    }

    /**
     * Establece el Son Usuario Gestión.
     * @param sonUsuGestion Cadena con el Son Usuario Gestión.
     */
    public void setSonUsuGestion(String sonUsuGestion) {
        this.sonUsuGestion = sonUsuGestion;
    }

    /**
     * Obtiene el campo A1
     * @return Cadena con el campo A1
     */
    public String getSonCampoa1() {
        return sonCampoa1;
    }

    /**
     * Establece el Campo A1
     * @param sonCampoa1 Cadena con el campo A1
     */
    public void setSonCampoa1(String sonCampoa1) {
        this.sonCampoa1 = sonCampoa1;
    }

    /**
     * Obtiene el campo A2
     * @return Cadena con el campo A2
     */
    public String getSonCampoa2() {
        return sonCampoa2;
    }

    /**
     * Establece el Campo A2
     * @param sonCampoa2 Cadena con el campo A2.
     */
    public void setSonCampoa2(String sonCampoa2) {
        this.sonCampoa2 = sonCampoa2;
    }

    /**
     * Obtiene el Campo A3
     * @return Cadena con el campo A3
     */
    public String getSonCampoa3() {
        return sonCampoa3;
    }

    /**
     * Establece el Campo A3
     * @param sonCampoa3 Cadena con el Campo A3
     */
    public void setSonCampoa3(String sonCampoa3) {
        this.sonCampoa3 = sonCampoa3;
    }

    /**
     * Obtiene el Campo A4
     * @return Cadena con el campo A4
     */
    public String getSonCampoa4() {
        return sonCampoa4;
    }

    /**
     * Establece el Campo A4
     * @param sonCampoa4 Cadena con el campo A4
     */
    public void setSonCampoa4(String sonCampoa4) {
        this.sonCampoa4 = sonCampoa4;
    }

    /**
     * Obtiene el Campo A5
     * @return Cadena con el campo A5
     */
    public String getSonCampoa5() {
        return sonCampoa5;
    }

    /**
     * Establece el Campo A5
     * @param sonCampoa5 Cadena con el campo A5
     */
    public void setSonCampoa5(String sonCampoa5) {
        this.sonCampoa5 = sonCampoa5;
    }

    /**
     * Obtiene el complemento 
     * @return Cadena con el complemento.
     */
    public String getSonComplemento() {
        return sonComplemento;
    }

    /**
     * Establece el complemento.
     * @param sonComplemento Cadena con el complemento.
     */
    public void setSonComplemento(String sonComplemento) {
        this.sonComplemento = sonComplemento;
    }

    /**
     * Obtiene la placa
     * @return Cadena con la placa.
     */
    public String getSonPlaca() {
        return sonPlaca;
    }

    /**
     * Establece la placa.
     * @param sonPlaca Cadena con la placa.
     */
    public void setSonPlaca(String sonPlaca) {
        this.sonPlaca = sonPlaca;
    }

    /**
     * Obtiene si la dirección existe.
     * @return Cadena con la dirección existe.
     */
    public String getSonDirExiste() {
        return sonDirExiste;
    }

    /**
     * Establece si la dirección existe.
     * @param sonDirExiste Cadena con la dirección existe.
     */
    public void setSonDirExiste(String sonDirExiste) {
        this.sonDirExiste = sonDirExiste;
    }

    /**
     * Obtiene la dirección validada.
     * @return Cadena con la dirección validada.
     */
    public String getSonDirValidada() {
        return sonDirValidada;
    }

    /**
     * Establece la dirección validada.
     * @param sonDirValidada Cadena con la dirección validada.
     */
    public void setSonDirValidada(String sonDirValidada) {
        this.sonDirValidada = sonDirValidada;
    }

    /**
     * Obtiene el área solicitante.
     * @return Cadena con el área solicitante.
     */
    public String getSonAreaSolicitante() {
        return sonAreaSolicitante;
    }

    /**
     * Establece el área solicitante.
     * @param sonAreaSolicitante Cadena con el área solicitante.
     */
    public void setSonAreaSolicitante(String sonAreaSolicitante) {
        this.sonAreaSolicitante = sonAreaSolicitante;
    }

    /**
     * Obtiene el nodo del usuario
     * @return Cadena con el nodo del usuario.
     */
    public String getSonNodoUsuario() {
        return sonNodoUsuario;
    }

    /**
     * Establece el nodo del usuario.
     * @param sonNodoUsuario Cadena con el nodo del usuario.
     */
    public void setSonNodoUsuario(String sonNodoUsuario) {
        this.sonNodoUsuario = sonNodoUsuario;
    }

    /**
     * Obtiene el hhpp
     * @return Objeto HHPP con la información obtenida.
     */
    public Hhpp getHhpp() {
        return hhpp;
    }

    /**
     * Establece el hhpp.
     * @param hhpp Objeto hhpp con la información a cargar.
     */
    public void setHhpp(Hhpp hhpp) {
        this.hhpp = hhpp;
    }

    /**
     * Obtiene el tipo Hhpp
     * @return Objeto TipoHhpp con la información recuperada.
     */
    public TipoHhpp getTipoHhpp() {
        return tipoHhpp;
    }

    /**
     * Establece el tipo hhpp.
     * @param tipoHhpp Objeto tipoHhpp con la información solicitada.
     */
    public void setTipoHhpp(TipoHhpp tipoHhpp) {
        this.tipoHhpp = tipoHhpp;
    }

    /**
     * Obtiene las Marcas.
     * @return Objeto Marcas con la información solicitada.
     */
    public Marcas getMarcas() {
        return marcas;
    }

    /**
     * Establece las marcas.
     * @param marcas Objeto marcas con la información solicitada.
     */
    public void setMarcas(Marcas marcas) {
        this.marcas = marcas;
    }

    /**
     * Obtiene en tipo de red hhpp.
     * @return Objeto TipoHhppRed con la información solicitada.
     */
    public TipoHhppRed getTipoHhppRed() {
        return tipoHhppRed;
    }

    /**
     * Establece el tipo de red Hhpp.
     * @param tipoHhppRed Objeto tipoHhppRed con la información solicitada.
     */
    public void setTipoHhppRed(TipoHhppRed tipoHhppRed) {
        this.tipoHhppRed = tipoHhppRed;
    }

    /**
     * Obtiene el tipo de conexión hhpp.
     * @return Objeto tipoHhppConexion con la información solicitada.
     */
    public TipoHhppConexion getTipoHhppConexion() {
        return tipoHhppConexion;
    }

    /**
     * Establece el tipoHhppconexion
     * @param tipoHhppConexion Objeto TipoHhppConexion con la información solicitada.
     */
    public void setTipoHhppConexion(TipoHhppConexion tipoHhppConexion) {
        this.tipoHhppConexion = tipoHhppConexion;
    }

    /**
     * Obtiene el nodo.
     * @return Objeto Nodo con la información solicitada.
     */
    public Nodo getNodo() {
        return nodo;
    }

    /**
     * Establece el nodo.
     * @param nodo Objeto Nodo con la información solicitada.
     */
    public void setNodo(Nodo nodo) {
        this.nodo = nodo;
    }

    /**
     * Obtiene el GeograficoPolitico
     * @return Objeto GeograficoPolitico con la información solicitada.
     */
    public GeograficoPolitico getGeograficoPolitico() {
        return geograficoPolitico;
    }

    /**
     * Establece el GeograficoPolitico
     * @param geograficoPolitico Objeto GeograficoPolitico con la información solicitada.
     */
    public void setGeograficoPolitico(GeograficoPolitico geograficoPolitico) {
        this.geograficoPolitico = geograficoPolitico;
    }

    /**
     * Obtiene el Geografico
     * @return Objeto Geografico con la información solicitada.
     */
    public Geografico getGeografico() {
        return geografico;
    }

    /**
     * Establece el Geografico  
     * @param geografico Objeto Geografico con la información solicitada.
     */
    public void setGeografico(Geografico geografico) {
        this.geografico = geografico;
    }

    /**
     * Obtiene la cadena con información del archivo generado RR.
     * @return Cadena con la información del archivo generado.
     */
    public String getSonArchivoGeneradoRr() {
        return sonArchivoGeneradoRr;
    }

    /**
     * Establece la información del archivo generado RR.
     * @param sonArchivoGeneradoRr Cadena con la información del archivo generado.
     */
    public void setSonArchivoGeneradoRr(String sonArchivoGeneradoRr) {
        this.sonArchivoGeneradoRr = sonArchivoGeneradoRr;
    }

    /**
     * Obtiene la actividad económica.
     * @return Cadena con la actividad económica.
     */
    public String getSonActividadEconomica() {
        return sonActividadEconomica;
    }

    /**
     * Establece la actividad económica.
     * @param sonActividadEconomica Cadena con la actividad económica.
     */
    public void setSonActividadEconomica(String sonActividadEconomica) {
        this.sonActividadEconomica = sonActividadEconomica;
    }

    /**
     * Obtiene en nivel de confiabilidad.
     * @return Entero con el nivel de confiabilidad.
     */
    public BigDecimal getSonConfiabilidad() {
        return sonConfiabilidad;
    }

    /**
     * Establece el nivel de confiabilidad.
     * @param sonConfiabilidad Entero con el nivel de confiabilidad.
     */
    public void setSonConfiabilidad(BigDecimal sonConfiabilidad) {
        this.sonConfiabilidad = sonConfiabilidad;
    }

    /**
     * Obtiene la fuente de información.
     * @return Cadena con el nombre de la fuente.
     */
    public String getSonFuente() {
        return sonFuente;
    }

    /**
     * Establece la fuente de información.
     * @param sonFuente Cadena con la fuente de información.
     */
    public void setSonFuente(String sonFuente) {
        this.sonFuente = sonFuente;
    }

    /**
     * Obtiene la Manzana Catastal.
     * @return Cadena con la manzana catastral.
     */
    public String getSonManzanaCatastral() {
        return sonManzanaCatastral;
    }

    /**
     * Establece la manzana catastral.
     * @param sonManzanaCatastral Cadena con la manzana catastral.
     */
    public void setSonManzanaCatastral(String sonManzanaCatastral) {
        this.sonManzanaCatastral = sonManzanaCatastral;
    }

    /**
     * Cadena con la configuración de la clase.
     * @return Cadena con los campos que contiene la clase.
     */
    public String auditoria() {
        String auditoria = "SolicitudNegocio{" + "sonId=" + sonId
                + ", sonMotivo=" + sonMotivo
                + ", sonNomSolicitante=" + sonNomSolicitante
                + ", sonContacto=" + sonContacto
                + ", sonTelContacto=" + sonTelContacto
                + ", sonFechaIngreso=" + sonFechaIngreso
                + ", sonFechaSolicitud=" + sonFechaSolicitud
                + ", sonTiempohoras=" + sonTiempohoras
                + ", sonEstado=" + sonEstado
                + ", sonTipoSolucion=" + sonTipoSolucion
                + ", sonResGestion=" + sonResGestion
                + ", sonCorregirHhpp=" + sonCorregirHhpp
                + ", sonCambioNodo=" + sonCambioNodo
                + ", sonNuevoProducto=" + sonNuevoProducto
                + ", sonEstratoAntiguo=" + sonEstratoAntiguo
                + ", sonEstratoNuevo=" + sonEstratoNuevo
                + ", sonObservaciones=" + sonObservaciones
                + ", sonFormatoIgac=" + sonFormatoIgac
                + ", sonServinformacion=" + sonServinformacion
                + ", sonNostandar=" + sonNostandar
                + ", sonTipoSolicitud=" + sonTipoSolicitud
                + ", sonEstrato=" + sonEstrato
                + ", sonNivSocioeconomico=" + sonNivSocioeconomico
                + ", sonZipcode=" + sonZipcode
                + ", sonDiralterna=" + sonDiralterna
                + ", sonLocalidad=" + sonLocalidad
                + ", sonMz=" + sonMz + ", sonLongitud=" + sonLongitud
                + ", sonLatitud=" + sonLatitud + ", sonLadomz=" + sonLadomz
                + ", sonHeadEnd=" + sonHeadEnd
                + ", sonCodSolicitante=" + sonCodSolicitante
                + ", sonEmailSolicitante=" + sonEmailSolicitante
                + ", sonCelSolicitante=" + sonCelSolicitante
                + ", sonFechaFinalizacion=" + sonFechaFinalizacion
                + ", sonEstadoSol=" + sonEstadoSol
                + ", sonEstadoUni=" + sonEstadoUni
                + ", sonUsuGestion=" + sonUsuGestion
                + ", sonCampoa1=" + sonCampoa1
                + ", sonCampoa2=" + sonCampoa2
                + ", sonCampoa3=" + sonCampoa3
                + ", sonCampoa4=" + sonCampoa4
                + ", sonCampoa5=" + sonCampoa5
                + ", sonDirExiste=" + sonDirExiste
                + ", sonDirValidada=" + sonDirValidada
                + ", sonAreaSolicitante=" + sonAreaSolicitante
                + ", sonNodoUsuario=" + sonNodoUsuario
                + ", sonPlaca=" + sonPlaca
                + ", sonComplemento=" + sonComplemento
                + ", sonConfiabilidad=" + sonConfiabilidad
                + ", sonManzanaCatastral=" + sonManzanaCatastral
                + ", sonActividadEconomica=" + sonActividadEconomica
                + ", sonFuente=" + sonFuente;
        if (hhpp != null) {
            auditoria = auditoria + ", hhpp=" + hhpp.getHhppId();
        }
        if (tipoHhpp != null) {
            auditoria = auditoria + ", tipoHhpp=" + tipoHhpp.getThhId();
        }
        if (marcas != null) {
            auditoria = auditoria + ", marcas=" + marcas.getMarId();
        }
        if (tipoHhppRed != null) {
            auditoria = auditoria + ", tipoHhppRed=" + tipoHhppRed.getThrId();
        }
        if (tipoHhppConexion != null) {
            auditoria = auditoria + ", tipoHhppConexion=" + tipoHhppConexion.getThcId();
        }
        if (nodo != null) {
            auditoria = auditoria + ", nodo=" + nodo.getNodId();
        }
        if (geograficoPolitico != null) {
            auditoria = auditoria + ", geograficoPolitico=" + geograficoPolitico.getGpoId();
        }
        if (geografico != null) {
            auditoria = auditoria + ", geografico=" + geografico.getGeoId();
        }
        auditoria = auditoria + ", sonFechaCreacion=" + sonFechaCreacion
                + ", sonUsuarioCreacion=" + sonUsuarioCreacion
                + ", sonFechaModificacion=" + sonFechaModificacion
                + ", sonUsuarioModificacion=" + sonUsuarioModificacion
                + ", sonArchivoGeneradoRr=" + sonArchivoGeneradoRr + '.';
        return auditoria;
    }

    @Override
    /**
     * Sobre Escritura del método toString()
     * @return Cadena con los campos que contiene la clase.
     */
    public String toString() {
        String resultado = "SolicitudNegocio{" + "sonId=" + sonId
                + ", sonMotivo=" + sonMotivo
                + ", sonNomSolicitante=" + sonNomSolicitante
                + ", sonContacto=" + sonContacto
                + ", sonTelContacto=" + sonTelContacto
                + ", sonFechaIngreso=" + sonFechaIngreso
                + ", sonFechaSolicitud=" + sonFechaSolicitud
                + ", sonTiempohoras=" + sonTiempohoras
                + ", sonEstado=" + sonEstado
                + ", sonTipoSolucion=" + sonTipoSolucion
                + ", sonResGestion=" + sonResGestion
                + ", sonCorregirHhpp=" + sonCorregirHhpp
                + ", sonCambioNodo=" + sonCambioNodo
                + ", sonNuevoProducto=" + sonNuevoProducto
                + ", sonEstratoAntiguo=" + sonEstratoAntiguo
                + ", sonEstratoNuevo=" + sonEstratoNuevo
                + ", sonObservaciones=" + sonObservaciones
                + ", sonFormatoIgac=" + sonFormatoIgac
                + ", sonServinformacion=" + sonServinformacion
                + ", sonNostandar=" + sonNostandar
                + ", sonTipoSolicitud=" + sonTipoSolicitud
                + ", sonEstrato=" + sonEstrato
                + ", sonNivSocioeconomico=" + sonNivSocioeconomico
                + ", sonZipcode=" + sonZipcode
                + ", sonDiralterna=" + sonDiralterna
                + ", sonLocalidad=" + sonLocalidad
                + ", sonMz=" + sonMz + ", sonLongitud=" + sonLongitud
                + ", sonLatitud=" + sonLatitud + ", sonLadomz=" + sonLadomz
                + ", sonHeadEnd=" + sonHeadEnd
                + ", sonCodSolicitante=" + sonCodSolicitante
                + ", sonEmailSolicitante=" + sonEmailSolicitante
                + ", sonCelSolicitante=" + sonCelSolicitante
                + ", sonFechaFinalizacion=" + sonFechaFinalizacion
                + ", sonEstadoSol=" + sonEstadoSol
                + ", sonEstadoUni=" + sonEstadoUni
                + ", sonUsuGestion=" + sonUsuGestion
                + ", sonCampoa1=" + sonCampoa1
                + ", sonCampoa2=" + sonCampoa2
                + ", sonCampoa3=" + sonCampoa3
                + ", sonCampoa4=" + sonCampoa4
                + ", sonCampoa5=" + sonCampoa5
                + ", sonDirExiste=" + sonDirExiste
                + ", sonDirValidada=" + sonDirValidada
                + ", sonAreaSolicitante=" + sonAreaSolicitante
                + ", sonNodoUsuario=" + sonNodoUsuario
                + ", sonPlaca=" + sonPlaca
                + ", sonComplemento=" + sonComplemento
                + ", sonConfiabilidad=" + sonConfiabilidad
                + ", sonManzanaCatastral=" + sonManzanaCatastral
                + ", sonActividadEconomica=" + sonActividadEconomica
                + ", sonFuente=" + sonFuente;
        if (hhpp != null) {
            resultado = resultado + ", hhpp=" + hhpp.getHhppId();
        }
        if (tipoHhpp != null) {
            resultado = resultado + ", tipoHhpp=" + tipoHhpp.getThhId();
        }
        if (marcas != null) {
            resultado = resultado + ", marcas=" + marcas.getMarId();
        }
        if (tipoHhppRed != null) {
            resultado = resultado + ", tipoHhppRed=" + tipoHhppRed.getThrId();
        }
        if (tipoHhppConexion != null) {
            resultado = resultado + ", tipoHhppConexion=" + tipoHhppConexion.getThcId();
        }
        if (nodo != null) {
            resultado = resultado + ", nodo=" + nodo.getNodId();
        }
        if (geograficoPolitico != null) {
            resultado = resultado + ", geograficoPolitico=" + geograficoPolitico.getGpoId();
        }
        if (geografico != null) {
            resultado = resultado + ", geografico=" + geografico.getGeoId();
        }


        resultado = resultado + ", sonFechaCreacion=" + sonFechaCreacion
                + ", sonUsuarioCreacion=" + sonUsuarioCreacion
                + ", sonFechaModificacion=" + sonFechaModificacion
                + ", sonUsuarioModificacion=" + sonUsuarioModificacion
                + ", sonArchivoGeneradoRr=" + sonArchivoGeneradoRr + '.';
        return resultado;
    }
}