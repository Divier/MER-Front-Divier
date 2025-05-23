package co.com.claro.mgl.rest.dtos;

import java.math.BigDecimal;

/**
 *
 * @author valbuenayf
 */
public class MasivoModificacionDto {

    private BigDecimal idTecnoSubedificio;
    private BigDecimal idSubedificio;
    private String nombreSubedificio;
    private String tipoSubedificio;
    private BigDecimal idCuentamatriz;
    private BigDecimal numeroCuenta;
    private String tipoTecnologia;
    private String regional;
    private String ciudad;
    private String centroPoblado;
    private String idCentroPoblado;
    private String barrio;
    private String direccion;
    private String fechaCreacion;
    private String fechaUltMod;
    private String nombreCuenta;
    private String companiaAdministracion;
    private String companiaAscensor;
    private String administrador;
    private String telefonoUno;
    private String telefonoDos;
    private String tipoProyecto;
    private String origenDatos;
    private String codigoNodo;
    private String estadoTecnologia;
    private String tipoConfiguracion;
    private String alimentacionElectrica;
    private String tipoDistribucion;
    private String tipoCcmm;
    private String companiaConstructora;
    private String blacklistTecnologia;
    private BigDecimal idBlacklistTecnologia;
    private String nuevoDato;
    private String nota;
    private int linea;
    private int numDetallado;
    private boolean procesado;
    private String mensajeSubEdificio;
    private String mensajeTecnologiaSub;
    private String mensajeCuentaMatriz;

    public MasivoModificacionDto() {
    }

    public BigDecimal getIdTecnoSubedificio() {
        return idTecnoSubedificio;
    }

    public void setIdTecnoSubedificio(BigDecimal idTecnoSubedificio) {
        this.idTecnoSubedificio = idTecnoSubedificio;
    }

    public BigDecimal getIdSubedificio() {
        return idSubedificio;
    }

    public void setIdSubedificio(BigDecimal idSubedificio) {
        this.idSubedificio = idSubedificio;
    }

    public BigDecimal getIdCuentamatriz() {
        return idCuentamatriz;
    }

    public void setIdCuentamatriz(BigDecimal idCuentamatriz) {
        this.idCuentamatriz = idCuentamatriz;
    }

    public String getTipoTecnologia() {
        return tipoTecnologia;
    }

    public void setTipoTecnologia(String tipoTecnologia) {
        this.tipoTecnologia = tipoTecnologia;
    }

    public String getRegional() {
        return regional;
    }

    public void setRegional(String regional) {
        this.regional = regional;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCentroPoblado() {
        return centroPoblado;
    }

    public void setCentroPoblado(String centroPoblado) {
        this.centroPoblado = centroPoblado;
    }

    public String getIdCentroPoblado() {
        return idCentroPoblado;
    }

    public void setIdCentroPoblado(String idCentroPoblado) {
        this.idCentroPoblado = idCentroPoblado;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombreCuenta() {
        return nombreCuenta;
    }

    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }

    public String getCompaniaAdministracion() {
        return companiaAdministracion;
    }

    public void setCompaniaAdministracion(String companiaAdministracion) {
        this.companiaAdministracion = companiaAdministracion;
    }

    public String getCompaniaAscensor() {
        return companiaAscensor;
    }

    public void setCompaniaAscensor(String companiaAscensor) {
        this.companiaAscensor = companiaAscensor;
    }

    public String getAdministrador() {
        return administrador;
    }

    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }

    public String getTelefonoUno() {
        return telefonoUno;
    }

    public void setTelefonoUno(String telefonoUno) {
        this.telefonoUno = telefonoUno;
    }

    public String getTelefonoDos() {
        return telefonoDos;
    }

    public void setTelefonoDos(String telefonoDos) {
        this.telefonoDos = telefonoDos;
    }

    public String getTipoProyecto() {
        return tipoProyecto;
    }

    public void setTipoProyecto(String tipoProyecto) {
        this.tipoProyecto = tipoProyecto;
    }

    public String getOrigenDatos() {
        return origenDatos;
    }

    public void setOrigenDatos(String origenDatos) {
        this.origenDatos = origenDatos;
    }

    public String getCodigoNodo() {
        return codigoNodo;
    }

    public void setCodigoNodo(String codigoNodo) {
        this.codigoNodo = codigoNodo;
    }

    public String getEstadoTecnologia() {
        return estadoTecnologia;
    }

    public void setEstadoTecnologia(String estadoTecnologia) {
        this.estadoTecnologia = estadoTecnologia;
    }

    public String getTipoConfiguracion() {
        return tipoConfiguracion;
    }

    public void setTipoConfiguracion(String tipoConfiguracion) {
        this.tipoConfiguracion = tipoConfiguracion;
    }

    public String getAlimentacionElectrica() {
        return alimentacionElectrica;
    }

    public void setAlimentacionElectrica(String alimentacionElectrica) {
        this.alimentacionElectrica = alimentacionElectrica;
    }

    public String getTipoDistribucion() {
        return tipoDistribucion;
    }

    public void setTipoDistribucion(String tipoDistribucion) {
        this.tipoDistribucion = tipoDistribucion;
    }

    public String getNuevoDato() {
        return nuevoDato;
    }

    public void setNuevoDato(String nuevoDato) {
        this.nuevoDato = nuevoDato;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getNumDetallado() {
        return numDetallado;
    }

    public void setNumDetallado(int numDetallado) {
        this.numDetallado = numDetallado;
    }

    public boolean isProcesado() {
        return procesado;
    }

    public void setProcesado(boolean procesado) {
        this.procesado = procesado;
    }

    public String getMensajeSubEdificio() {
        return mensajeSubEdificio;
    }

    public void setMensajeSubEdificio(String mensajeSubEdificio) {
        this.mensajeSubEdificio = mensajeSubEdificio;
    }

    public String getMensajeTecnologiaSub() {
        return mensajeTecnologiaSub;
    }

    public void setMensajeTecnologiaSub(String mensajeTecnologiaSub) {
        this.mensajeTecnologiaSub = mensajeTecnologiaSub;
    }

    public String getMensajeCuentaMatriz() {
        return mensajeCuentaMatriz;
    }

    public void setMensajeCuentaMatriz(String mensajeCuentaMatriz) {
        this.mensajeCuentaMatriz = mensajeCuentaMatriz;
    }

    public BigDecimal getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(BigDecimal numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getNombreSubedificio() {
        return nombreSubedificio;
    }

    public void setNombreSubedificio(String nombreSubedificio) {
        this.nombreSubedificio = nombreSubedificio;
    }

    public String getTipoSubedificio() {
        return tipoSubedificio;
    }

    public void setTipoSubedificio(String tipoSubedificio) {
        this.tipoSubedificio = tipoSubedificio;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFechaUltMod() {
        return fechaUltMod;
    }

    public void setFechaUltMod(String fechaUltMod) {
        this.fechaUltMod = fechaUltMod;
    }

    public String getTipoCcmm() {
        return tipoCcmm;
    }

    public void setTipoCcmm(String tipoCcmm) {
        this.tipoCcmm = tipoCcmm;
    }

    public String getCompaniaConstructora() {
        return companiaConstructora;
    }

    public void setCompaniaConstructora(String companiaConstructora) {
        this.companiaConstructora = companiaConstructora;
    }

    public String getBlacklistTecnologia() {
        return blacklistTecnologia;
    }

    public void setBlacklistTecnologia(String blacklistTecnologia) {
        this.blacklistTecnologia = blacklistTecnologia;
    }

    public BigDecimal getIdBlacklistTecnologia() {
        return idBlacklistTecnologia;
    }

    public void setIdBlacklistTecnologia(BigDecimal idBlacklistTecnologia) {
        this.idBlacklistTecnologia = idBlacklistTecnologia;
    }



}
