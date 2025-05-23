package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * Clase CargueGeografico
 * Representa La información cargada correspondiente al espacio geográfico.
 * implementa Serialización
 *
 * @author 	Jose Luis Caicedo G.
 * @version	1.0
 */
public class CargueGeografico implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal sonId;
    private String sonCuentaMatriz;
    private String sonMotivo;
    private String sonRespuesta;
    private String sonNomSolicitante;
    private String sonContacto;
    private String sonTelContacto;
    private Date sonFechaIngreso;
    private Date sonFechaSolicitud;
    private BigInteger sonTiempohoras;
    private String sonEstado;
    private String sonTipoSolucion;
    private String sonResGestion;
    private String sonCorregirHhpp;
    private String sonCambioNodo;
    private String sonNuevoProducto;
    private String sonEstratoAntiguo;
    private String sonEstratoNuevo;
    private Date sonFechaCreacion;
    private String sonUsuarioCreacion;
    private Date sonFechaModificacion;
    private String sonUsuarioModificacion;
    private String sonObservaciones;
    private String sonFormatoIgac;
    private String sonServinformacion;
    private String sonNostandar;
    private String sonTipoSolicitud;
    private String sonEstrato;
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
    private TipoHhpp tipoHhpp;
    private TipoMarcas tipoMarcas;
    private TipoHhppRed tipoHhppRed;
    private TipoHhppConexion tipoHhppConexion;
    private Nodo nodo;
    private GeograficoPolitico geograficoPolitico;
    private Geografico geografico;

    /**
     * 
     */
    public CargueGeografico() {
    }

    /**
     * 
     * @param sonId
     */
    public CargueGeografico(BigDecimal sonId) {
        this.sonId = sonId;
    }

    /**
     * 
     * @param sonId
     * @param sonObservaciones
     */
    public CargueGeografico(BigDecimal sonId, String sonObservaciones) {
        this.sonId = sonId;
        this.sonObservaciones = sonObservaciones;
    }

    /**
     * 
     * @return
     */
    public BigDecimal getSonId() {
        return sonId;
    }

    /**
     * 
     * @param sonId
     */
    public void setSonId(BigDecimal sonId) {
        this.sonId = sonId;
    }

    /**
     * 
     * @return
     */
    public String getSonCuentaMatriz() {
        return sonCuentaMatriz;
    }

    /**
     * 
     * @param sonCuentaMatriz
     */
    public void setSonCuentaMatriz(String sonCuentaMatriz) {
        this.sonCuentaMatriz = sonCuentaMatriz;
    }

    /**
     * 
     * @return
     */
    public String getSonMotivo() {
        return sonMotivo;
    }

    /**
     * 
     * @param sonMotivo
     */
    public void setSonMotivo(String sonMotivo) {
        this.sonMotivo = sonMotivo;
    }

    /**
     * 
     * @return
     */
    public String getSonRespuesta() {
        return sonRespuesta;
    }

    /**
     * 
     * @param sonRespuesta
     */
    public void setSonRespuesta(String sonRespuesta) {
        this.sonRespuesta = sonRespuesta;
    }

    /**
     * 
     * @return
     */
    public String getSonNomSolicitante() {
        return sonNomSolicitante;
    }

    /**
     * 
     * @param sonNomSolicitante
     */
    public void setSonNomSolicitante(String sonNomSolicitante) {
        this.sonNomSolicitante = sonNomSolicitante;
    }

    /**
     * 
     * @return
     */
    public String getSonContacto() {
        return sonContacto;
    }

    /**
     * 
     * @param sonContacto
     */
    public void setSonContacto(String sonContacto) {
        this.sonContacto = sonContacto;
    }

    /**
     * 
     * @return
     */
    public String getSonTelContacto() {
        return sonTelContacto;
    }

    /**
     * 
     * @param sonTelContacto
     */
    public void setSonTelContacto(String sonTelContacto) {
        this.sonTelContacto = sonTelContacto;
    }

    /**
     * 
     * @return
     */
    public Date getSonFechaIngreso() {
        return sonFechaIngreso;
    }

    /**
     * 
     * @param sonFechaIngreso
     */
    public void setSonFechaIngreso(Date sonFechaIngreso) {
        this.sonFechaIngreso = sonFechaIngreso;
    }

    /**
     * 
     * @return
     */
    public Date getSonFechaSolicitud() {
        return sonFechaSolicitud;
    }

    /**
     * 
     * @param sonFechaSolicitud
     */
    public void setSonFechaSolicitud(Date sonFechaSolicitud) {
        this.sonFechaSolicitud = sonFechaSolicitud;
    }

    /**
     * 
     * @return
     */
    public BigInteger getSonTiempohoras() {
        return sonTiempohoras;
    }

    /**
     * 
     * @param sonTiempohoras
     */
    public void setSonTiempohoras(BigInteger sonTiempohoras) {
        this.sonTiempohoras = sonTiempohoras;
    }

    /**
     * 
     * @return
     */
    public String getSonEstado() {
        return sonEstado;
    }

    /**
     * 
     * @param sonEstado
     */
    public void setSonEstado(String sonEstado) {
        this.sonEstado = sonEstado;
    }

    /**
     * 
     * @return
     */
    public String getSonTipoSolucion() {
        return sonTipoSolucion;
    }

    /**
     * 
     * @param sonTipoSolucion
     */
    public void setSonTipoSolucion(String sonTipoSolucion) {
        this.sonTipoSolucion = sonTipoSolucion;
    }

    /**
     * 
     * @return
     */
    public String getSonResGestion() {
        return sonResGestion;
    }

    /**
     * 
     * @param sonResGestion
     */
    public void setSonResGestion(String sonResGestion) {
        this.sonResGestion = sonResGestion;
    }

    /**
     * 
     * @return
     */
    public String getSonCorregirHhpp() {
        return sonCorregirHhpp;
    }

    /**
     * 
     * @param sonCorregirHhpp
     */
    public void setSonCorregirHhpp(String sonCorregirHhpp) {
        this.sonCorregirHhpp = sonCorregirHhpp;
    }

    /**
     * 
     * @return
     */
    public String getSonCambioNodo() {
        return sonCambioNodo;
    }

    /**
     * 
     * @param sonCambioNodo
     */
    public void setSonCambioNodo(String sonCambioNodo) {
        this.sonCambioNodo = sonCambioNodo;
    }

    /**
     * 
     * @return
     */
    public String getSonNuevoProducto() {
        return sonNuevoProducto;
    }

    /**
     * 
     * @param sonNuevoProducto
     */
    public void setSonNuevoProducto(String sonNuevoProducto) {
        this.sonNuevoProducto = sonNuevoProducto;
    }

    /**
     * 
     * @return
     */
    public String getSonEstratoAntiguo() {
        return sonEstratoAntiguo;
    }

    /**
     * 
     * @param sonEstratoAntiguo
     */
    public void setSonEstratoAntiguo(String sonEstratoAntiguo) {
        this.sonEstratoAntiguo = sonEstratoAntiguo;
    }

    /**
     * 
     * @return
     */
    public String getSonEstratoNuevo() {
        return sonEstratoNuevo;
    }

    /**
     * 
     * @param sonEstratoNuevo
     */
    public void setSonEstratoNuevo(String sonEstratoNuevo) {
        this.sonEstratoNuevo = sonEstratoNuevo;
    }

    /**
     * 
     * @return
     */
    public Date getSonFechaCreacion() {
        return sonFechaCreacion;
    }

    /**
     * 
     * @param sonFechaCreacion
     */
    public void setSonFechaCreacion(Date sonFechaCreacion) {
        this.sonFechaCreacion = sonFechaCreacion;
    }

    /**
     * 
     * @return
     */
    public String getSonUsuarioCreacion() {
        return sonUsuarioCreacion;
    }

    /**
     * 
     * @param sonUsuarioCreacion
     */
    public void setSonUsuarioCreacion(String sonUsuarioCreacion) {
        this.sonUsuarioCreacion = sonUsuarioCreacion;
    }

    /**
     * 
     * @return
     */
    public Date getSonFechaModificacion() {
        return sonFechaModificacion;
    }

    /**
     * 
     * @param sonFechaModificacion
     */
    public void setSonFechaModificacion(Date sonFechaModificacion) {
        this.sonFechaModificacion = sonFechaModificacion;
    }

    /**
     * 
     * @return
     */
    public String getSonUsuarioModificacion() {
        return sonUsuarioModificacion;
    }

    /**
     * 
     * @param sonUsuarioModificacion
     */
    public void setSonUsuarioModificacion(String sonUsuarioModificacion) {
        this.sonUsuarioModificacion = sonUsuarioModificacion;
    }

    /**
     * 
     * @return
     */
    public String getSonObservaciones() {
        return sonObservaciones;
    }

    /**
     * 
     * @param sonObservaciones
     */
    public void setSonObservaciones(String sonObservaciones) {
        this.sonObservaciones = sonObservaciones;
    }

    /**
     * 
     * @return
     */
    public String getSonFormatoIgac() {
        return sonFormatoIgac;
    }

    /**
     * 
     * @param sonFormatoIgac
     */
    public void setSonFormatoIgac(String sonFormatoIgac) {
        this.sonFormatoIgac = sonFormatoIgac;
    }

    /**
     * 
     * @return
     */
    public String getSonServinformacion() {
        return sonServinformacion;
    }

    /**
     * 
     * @param sonServinformacion
     */
    public void setSonServinformacion(String sonServinformacion) {
        this.sonServinformacion = sonServinformacion;
    }

    /**
     * 
     * @return
     */
    public String getSonNostandar() {
        return sonNostandar;
    }

    /**
     * 
     * @param sonNostandar
     */
    public void setSonNostandar(String sonNostandar) {
        this.sonNostandar = sonNostandar;
    }

    /**
     * 
     * @return
     */
    public String getSonTipoSolicitud() {
        return sonTipoSolicitud;
    }

    /**
     * 
     * @param sonTipoSolicitud
     */
    public void setSonTipoSolicitud(String sonTipoSolicitud) {
        this.sonTipoSolicitud = sonTipoSolicitud;
    }

    /**
     * 
     * @return
     */
    public String getSonEstrato() {
        return sonEstrato;
    }

    /**
     * 
     * @param sonEstrato
     */
    public void setSonEstrato(String sonEstrato) {
        this.sonEstrato = sonEstrato;
    }

    /**
     * 
     * @return
     */
    public String getSonZipcode() {
        return sonZipcode;
    }

    /**
     * 
     * @param sonZipcode
     */
    public void setSonZipcode(String sonZipcode) {
        this.sonZipcode = sonZipcode;
    }

    /**
     * 
     * @return
     */
    public String getSonDiralterna() {
        return sonDiralterna;
    }

    /**
     * 
     * @param sonDiralterna
     */
    public void setSonDiralterna(String sonDiralterna) {
        this.sonDiralterna = sonDiralterna;
    }

    /**
     * 
     * @return
     */
    public String getSonLocalidad() {
        return sonLocalidad;
    }

    /**
     * 
     * @param sonLocalidad
     */
    public void setSonLocalidad(String sonLocalidad) {
        this.sonLocalidad = sonLocalidad;
    }

    /**
     * 
     * @return
     */
    public String getSonMz() {
        return sonMz;
    }

    /**
     * 
     * @param sonMz
     */
    public void setSonMz(String sonMz) {
        this.sonMz = sonMz;
    }

    /**
     * 
     * @return
     */
    public String getSonLongitud() {
        return sonLongitud;
    }

    /**
     * 
     * @param sonLongitud
     */
    public void setSonLongitud(String sonLongitud) {
        this.sonLongitud = sonLongitud;
    }

    /**
     * 
     * @return
     */
    public String getSonLatitud() {
        return sonLatitud;
    }

    /**
     * 
     * @param sonLatitud
     */
    public void setSonLatitud(String sonLatitud) {
        this.sonLatitud = sonLatitud;
    }

    /**
     * 
     * @return
     */
    public String getSonLadomz() {
        return sonLadomz;
    }

    /**
     * 
     * @param sonLadomz
     */
    public void setSonLadomz(String sonLadomz) {
        this.sonLadomz = sonLadomz;
    }

    /**
     * 
     * @return
     */
    public String getSonHeadEnd() {
        return sonHeadEnd;
    }

    /**
     * 
     * @param sonHeadEnd
     */
    public void setSonHeadEnd(String sonHeadEnd) {
        this.sonHeadEnd = sonHeadEnd;
    }

    /**
     * 
     * @return
     */
    public String getSonCodSolicitante() {
        return sonCodSolicitante;
    }

    /**
     * 
     * @param sonCodSolicitante
     */
    public void setSonCodSolicitante(String sonCodSolicitante) {
        this.sonCodSolicitante = sonCodSolicitante;
    }

    /**
     * 
     * @return
     */
    public String getSonEmailSolicitante() {
        return sonEmailSolicitante;
    }

    /**
     * 
     * @param sonEmailSolicitante
     */
    public void setSonEmailSolicitante(String sonEmailSolicitante) {
        this.sonEmailSolicitante = sonEmailSolicitante;
    }

    /**
     * 
     * @return
     */
    public String getSonCelSolicitante() {
        return sonCelSolicitante;
    }

    /**
     * 
     * @param sonCelSolicitante
     */
    public void setSonCelSolicitante(String sonCelSolicitante) {
        this.sonCelSolicitante = sonCelSolicitante;
    }

    /**
     * 
     * @return
     */
    public Date getSonFechaFinalizacion() {
        return sonFechaFinalizacion;
    }

    /**
     * 
     * @param sonFechaFinalizacion
     */
    public void setSonFechaFinalizacion(Date sonFechaFinalizacion) {
        this.sonFechaFinalizacion = sonFechaFinalizacion;
    }

    /**
     * 
     * @return
     */
    public String getSonEstadoSol() {
        return sonEstadoSol;
    }

    /**
     * 
     * @param sonEstadoSol
     */
    public void setSonEstadoSol(String sonEstadoSol) {
        this.sonEstadoSol = sonEstadoSol;
    }

    /**
     * 
     * @return
     */
    public String getSonEstadoUni() {
        return sonEstadoUni;
    }

    /**
     * 
     * @param sonEstadoUni
     */
    public void setSonEstadoUni(String sonEstadoUni) {
        this.sonEstadoUni = sonEstadoUni;
    }

    /**
     * 
     * @return
     */
    public String getSonUsuGestion() {
        return sonUsuGestion;
    }

    /**
     * 
     * @param sonUsuGestion
     */
    public void setSonUsuGestion(String sonUsuGestion) {
        this.sonUsuGestion = sonUsuGestion;
    }

    /**
     * 
     * @return
     */
    public String getSonCampoa1() {
        return sonCampoa1;
    }

    /**
     * 
     * @param sonCampoa1
     */
    public void setSonCampoa1(String sonCampoa1) {
        this.sonCampoa1 = sonCampoa1;
    }

    /**
     * 
     * @return
     */
    public String getSonCampoa2() {
        return sonCampoa2;
    }

    /**
     * 
     * @param sonCampoa2
     */
    public void setSonCampoa2(String sonCampoa2) {
        this.sonCampoa2 = sonCampoa2;
    }

    /**
     * 
     * @return
     */
    public String getSonCampoa3() {
        return sonCampoa3;
    }

    /**
     * 
     * @param sonCampoa3
     */
    public void setSonCampoa3(String sonCampoa3) {
        this.sonCampoa3 = sonCampoa3;
    }

    /**
     * 
     * @return
     */
    public String getSonCampoa4() {
        return sonCampoa4;
    }

    /**
     * 
     * @param sonCampoa4
     */
    public void setSonCampoa4(String sonCampoa4) {
        this.sonCampoa4 = sonCampoa4;
    }

    /**
     * 
     * @return
     */
    public String getSonCampoa5() {
        return sonCampoa5;
    }

    /**
     * 
     * @param sonCampoa5
     */
    public void setSonCampoa5(String sonCampoa5) {
        this.sonCampoa5 = sonCampoa5;
    }

    /**
     * 
     * @return
     */
    public TipoHhpp getTipoHhpp() {
        return tipoHhpp;
    }

    /**
     * 
     * @param tipoHhpp
     */
    public void setTipoHhpp(TipoHhpp tipoHhpp) {
        this.tipoHhpp = tipoHhpp;
    }

    /**
     * 
     * @return
     */
    public TipoMarcas getTipoMarcas() {
        return tipoMarcas;
    }

    /**
     * 
     * @param tipoMarcas
     */
    public void setTipoMarcas(TipoMarcas tipoMarcas) {
        this.tipoMarcas = tipoMarcas;
    }

    /**
     * 
     * @return
     */
    public TipoHhppRed getTipoHhppRed() {
        return tipoHhppRed;
    }

    /**
     * 
     * @param tipoHhppRed
     */
    public void setTipoHhppRed(TipoHhppRed tipoHhppRed) {
        this.tipoHhppRed = tipoHhppRed;
    }

    /**
     * 
     * @return
     */
    public TipoHhppConexion getTipoHhppConexion() {
        return tipoHhppConexion;
    }

    /**
     * 
     * @param tipoHhppConexion
     */
    public void setTipoHhppConexion(TipoHhppConexion tipoHhppConexion) {
        this.tipoHhppConexion = tipoHhppConexion;
    }

    /**
     * 
     * @return
     */
    public Nodo getNodo() {
        return nodo;
    }

    /**
     * 
     * @param nodo
     */
    public void setNodo(Nodo nodo) {
        this.nodo = nodo;
    }

    /**
     * 
     * @return
     */
    public GeograficoPolitico getGeograficoPolitico() {
        return geograficoPolitico;
    }

    /**
     * 
     * @param geograficoPolitico
     */
    public void setGeograficoPolitico(GeograficoPolitico geograficoPolitico) {
        this.geograficoPolitico = geograficoPolitico;
    }

    /**
     * 
     * @return
     */
    public Geografico getGeografico() {
        return geografico;
    }

    /**
     * 
     * @param geografico
     */
    public void setGeografico(Geografico geografico) {
        this.geografico = geografico;
    }

    @Override
    public String toString() {
        return "SolicitudNegocio{" + "sonId=" + sonId + ", sonCuentaMatriz=" + sonCuentaMatriz
                + ", sonMotivo=" + sonMotivo + ", sonFormatoIgac=" + sonFormatoIgac
                + ", sonServinformacion=" + sonServinformacion + ", sonRespuesta=" + sonRespuesta
                + ", sonNostandar=" + sonNostandar + ", sonNomSolicitante=" + sonNomSolicitante
                + ", sonContacto=" + sonContacto + ", sonTelContacto=" + sonTelContacto
                + ", sonFechaIngreso=" + sonFechaIngreso + ", sonFechaSolicitud=" + sonFechaSolicitud
                + ", sonTiempohoras=" + sonTiempohoras + ", sonEstado=" + sonEstado
                + ", sonTipoSolucion=" + sonTipoSolucion + ", sonResGestion=" + sonResGestion
                + ", sonCorregirHhpp=" + sonCorregirHhpp + ", sonCambioNodo=" + sonCambioNodo
                + ", sonNuevoProducto=" + sonNuevoProducto + ", sonEstratoAntiguo=" + sonEstratoAntiguo
                + ", sonEstratoNuevo=" + sonEstratoNuevo + ", sonObservaciones=" + sonObservaciones
                + ", sonFechaCreacion=" + sonFechaCreacion + ", sonUsuarioCreacion=" + sonUsuarioCreacion
                + ", sonFechaModificacion=" + sonFechaModificacion
                + ", sonUsuarioModificacion=" + sonUsuarioModificacion + ", tipoHhpp=" + tipoHhpp
                + ", nodo=" + nodo + ", geograficoPolitico=" + geograficoPolitico + ", geografico=" + geografico + '}';
    }
}
