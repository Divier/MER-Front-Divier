package co.com.claro.mgl.jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad para consulta del Detalle Nap <i>TEC_LOG_NAP_DETALLE</i>. 
 *
 * @author duartey
 */
@Entity
@Table(name = "TEC_LOG_NAP_DETALLE", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LogActualizaDetalle.findAll", query = "SELECT l FROM LogActualizaDetalle l"),
    @NamedQuery(name = "LogActualizaDetalle.findLogMaster", query = "SELECT l FROM LogActualizaDetalle l WHERE l.idMaster.idNap = :idNap"),
    @NamedQuery(name = "LogActualizaDetalle.findLogMasterByEstado", query = "SELECT l FROM LogActualizaDetalle l WHERE l.idMaster.idNap = :idNap AND l.estadoTransaccion = :estadoTransaccion")
})
public class LogActualizaDetalle implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @SequenceGenerator(
          name = "LogActualizaDetalle.TEC_LOG_NAP_DETALLE_SQ",
          sequenceName = "MGL_SCHEME.TEC_LOG_NAP_DETALLE_SQ", allocationSize = 1)
    @GeneratedValue(generator = "LogActualizaDetalle.TEC_LOG_NAP_DETALLE_SQ")
    @Column(name = "ID_NAP_DETALLE", nullable = false)
    private BigDecimal idNapDetalle;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MASTER", nullable = false)
    private LogActualizaMaster idMaster;
    @Column(name = "HANDLE", nullable = false, length = 7)
    private String handle;
    @Column(name = "BLOCKNAME", nullable = false, length = 15)
    private String blockname;
    @Column(name = "CABLE", nullable = true, length = 15)
    private String cable;
    @Column(name = "HILOS", nullable = true, length = 15)
    private String hilos;
    @Column(name = "LIBRES", nullable = true, length = 15)
    private String libres;
    @Column(name = "ESTADO", nullable = true, length = 20)
    private String estado;
    @Column(name = "DIST", nullable = true, length = 20)
    private String dist;
    @Column(name = "NUMERO", nullable = true, length = 20)
    private String numero;
    @Column(name = "DIRECCION", nullable = false, length = 50)
    private String dirreccion;
    @Column(name = "SP_SEC", nullable = true, length = 20)
    private String spSec;
    @Column(name = "LONGITUD", nullable = true, length = 20)
    private String longitud;
    @Column(name = "UTILIZADO", nullable = true, length = 20)
    private String utilizado;
    @Column(name = "CORD_X", nullable = true, length = 20)
    private String cordX;
    @Column(name = "CORD_Y", nullable = true, length = 20)
    private String cordY;
    @Column(name = "NPCD", nullable = true, length = 20)
    private String npcd;
    @Column(name = "NOMBRE_CONJ", nullable = true, length = 100)
    private String nombreConjunto;
    @Column(name = "NOMBRECALL", nullable = false, length = 100)
    private String nombreCall;
    @Column(name = "PLACAUNIDA", nullable = false, length = 100)
    private String placaUnida;
    @Column(name = "NUM_CASAS", nullable = false, length = 100)
    private String numCasas;
    @Column(name = "APTOS", nullable = false, length = 100)
    private String aptos;
    @Column(name = "LOCALES", nullable = false, length = 100)
    private String locales;
    @Column(name = "PISOS", nullable = false, length = 100)
    private String pisos;
    @Column(name = "BARRIO", nullable = false, length = 100)
    private String barrio;
    @Column(name = "INTERIOR", nullable = true, length = 100)
    private String interior;
    @Column(name = "NOMBRECALLANT2", nullable = true, length = 100)
    private String nombreCallAnt2;
    @Column(name = "PLACAUNIDAANT2", nullable = true, length = 100)
    private String placaUnidaAnt2;
    @Column(name = "NOMBRECALLANT3", nullable = true, length = 100)
    private String nombreCallAnt3;
    @Column(name = "PLACAUNIDAANT3", nullable = true, length = 100)
    private String placaUnidaAnt3;
    @Column(name = "DISTRIBUCION", nullable = true, length = 100)
    private String distribucion;
    @Column(name = "AMP", nullable = true, length = 100)
    private String amp;
    @Column(name = "CORDX", nullable = true, length = 100)
    private String cordx;
    @Column(name = "CORDY", nullable = true, length = 100)
    private String cordy;
    @Column(name = "TRONCAL", nullable = false, length = 20)
    private String troncal;
    @Column(name = "NAP", nullable = false, length = 10)
    private String nap;
    @Column(name = "ESTADO_TRANSACCION", nullable = false, length = 25)
    private String estadoTransaccion;
    @Column(name = "FECHA")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "DETALLE", nullable = true, length = 2000)
    private String detalle;

    public BigDecimal getIdNapDetalle() {
        return idNapDetalle;
    }

    public void setIdNapDetalle(BigDecimal idNapDetalle) {
        this.idNapDetalle = idNapDetalle;
    }

    public LogActualizaMaster getIdMaster() {
        return idMaster;
    }

    public void setIdMaster(LogActualizaMaster idMaster) {
        this.idMaster = idMaster;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getBlockname() {
        return blockname;
    }

    public void setBlockname(String blockname) {
        this.blockname = blockname;
    }

    public String getCable() {
        return cable;
    }

    public void setCable(String cable) {
        this.cable = cable;
    }

    public String getHilos() {
        return hilos;
    }

    public void setHilos(String hilos) {
        this.hilos = hilos;
    }

    public String getLibres() {
        return libres;
    }

    public void setLibres(String libres) {
        this.libres = libres;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDirreccion() {
        return dirreccion;
    }

    public void setDirreccion(String dirreccion) {
        this.dirreccion = dirreccion;
    }

    public String getSpSec() {
        return spSec;
    }

    public void setSpSec(String spSec) {
        this.spSec = spSec;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getUtilizado() {
        return utilizado;
    }

    public void setUtilizado(String utilizado) {
        this.utilizado = utilizado;
    }

    public String getCordX() {
        return cordX;
    }

    public void setCordX(String cordX) {
        this.cordX = cordX;
    }

    public String getCordY() {
        return cordY;
    }

    public void setCordY(String cordY) {
        this.cordY = cordY;
    }

    public String getNpcd() {
        return npcd;
    }

    public void setNpcd(String npcd) {
        this.npcd = npcd;
    }

    public String getNombreConjunto() {
        return nombreConjunto;
    }

    public void setNombreConjunto(String nombreConjunto) {
        this.nombreConjunto = nombreConjunto;
    }

    public String getNombreCall() {
        return nombreCall;
    }

    public void setNombreCall(String nombreCall) {
        this.nombreCall = nombreCall;
    }

    public String getPlacaUnida() {
        return placaUnida;
    }

    public void setPlacaUnida(String placaUnida) {
        this.placaUnida = placaUnida;
    }

    public String getNumCasas() {
        return numCasas;
    }

    public void setNumCasas(String numCasas) {
        this.numCasas = numCasas;
    }

    public String getAptos() {
        return aptos;
    }

    public void setAptos(String aptos) {
        this.aptos = aptos;
    }

    public String getLocales() {
        return locales;
    }

    public void setLocales(String locales) {
        this.locales = locales;
    }

    public String getPisos() {
        return pisos;
    }

    public void setPisos(String pisos) {
        this.pisos = pisos;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getInterior() {
        return interior;
    }

    public void setInterior(String interior) {
        this.interior = interior;
    }

    public String getNombreCallAnt2() {
        return nombreCallAnt2;
    }

    public void setNombreCallAnt2(String nombreCallAnt2) {
        this.nombreCallAnt2 = nombreCallAnt2;
    }

    public String getPlacaUnidaAnt2() {
        return placaUnidaAnt2;
    }

    public void setPlacaUnidaAnt2(String placaUnidaAnt2) {
        this.placaUnidaAnt2 = placaUnidaAnt2;
    }

    public String getNombreCallAnt3() {
        return nombreCallAnt3;
    }

    public void setNombreCallAnt3(String nombreCallAnt3) {
        this.nombreCallAnt3 = nombreCallAnt3;
    }

    public String getPlacaUnidaAnt3() {
        return placaUnidaAnt3;
    }

    public void setPlacaUnidaAnt3(String placaUnidaAnt3) {
        this.placaUnidaAnt3 = placaUnidaAnt3;
    }

    public String getDistribucion() {
        return distribucion;
    }

    public void setDistribucion(String distribucion) {
        this.distribucion = distribucion;
    }

    public String getAmp() {
        return amp;
    }

    public void setAmp(String amp) {
        this.amp = amp;
    }

    public String getCordx() {
        return cordx;
    }

    public void setCordx(String cordx) {
        this.cordx = cordx;
    }

    public String getCordy() {
        return cordy;
    }

    public void setCordy(String cordy) {
        this.cordy = cordy;
    }

    public String getTroncal() {
        return troncal;
    }

    public void setTroncal(String troncal) {
        this.troncal = troncal;
    }

    public String getNap() {
        return nap;
    }

    public void setNap(String nap) {
        this.nap = nap;
    }

    public String getEstadoTransaccion() {
        return estadoTransaccion;
    }

    public void setEstadoTransaccion(String estadoTransaccion) {
        this.estadoTransaccion = estadoTransaccion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
    
    
}
