/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad para consulta del Master Nap <i>TEC_LOG_NAP_MASTER</i>.
 *
 * @author duartey
 */
@Entity
@Table(name = "TEC_LOG_NAP_MASTER", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LogActualizaMaster.findAll", query = "SELECT n FROM LogActualizaMaster n"),
    @NamedQuery(name = "LogActualizaMaster.findLogToFechaRegistro", query = "SELECT n FROM LogActualizaMaster n where n.fechaRegistro between :fechaInicial and :fechaFinal  ORDER BY n.idNap DESC "),
    @NamedQuery(name = "LogActualizaMaster.findLogToTroncal", query = "SELECT n FROM LogActualizaMaster n where n.troncal = :troncal ORDER BY n.idNap DESC "),
    @NamedQuery(name = "LogActualizaMaster.findLogGroupByTroncal", query = "SELECT n.troncal FROM LogActualizaMaster n GROUP BY n.troncal"),
    @NamedQuery(name = "LogActualizaMaster.findLogToTipoTecnologia", query = "SELECT n FROM LogActualizaMaster n where n.tipoTecnologia = :tipoTecnologia ORDER BY n.idNap DESC "),
    @NamedQuery(name = "LogActualizaMaster.findLogGroupByTipoTec", query = "SELECT n.tipoTecnologia FROM LogActualizaMaster n GROUP BY n.tipoTecnologia"),
    @NamedQuery(name = "LogActualizaMaster.findLogToLikeNombreArchivo", query = "SELECT n FROM LogActualizaMaster n where n.nombreArchivo LIKE :nombreArchivo ORDER BY n.idNap DESC "),
    @NamedQuery(name = "LogActualizaMaster.findLogToDepartamento", query = "SELECT n FROM LogActualizaMaster n where n.departamento = :departamento ORDER BY n.idNap DESC "),
    @NamedQuery(name = "LogActualizaMaster.findLogGroupByDepartamento", query = "SELECT n.departamento FROM LogActualizaMaster n GROUP BY n.departamento"),
    @NamedQuery(name = "LogActualizaMaster.findLogToCiudad", query = "SELECT n FROM LogActualizaMaster n where n.ciudad = :ciudad ORDER BY n.idNap DESC "),
    @NamedQuery(name = "LogActualizaMaster.findLogGroupByCiudad", query = "SELECT n.ciudad FROM LogActualizaMaster n GROUP BY n.ciudad"),
    @NamedQuery(name = "LogActualizaMaster.findLogToCentroPoblado", query = "SELECT n FROM LogActualizaMaster n where n.centroPoblado = :centroPoblado ORDER BY n.idNap DESC "),
    @NamedQuery(name = "LogActualizaMaster.findLogGroupByCentroPoblado", query = "SELECT n.centroPoblado FROM LogActualizaMaster n GROUP BY n.centroPoblado"),
    @NamedQuery(name = "LogActualizaMaster.findLogToLikeUsuarioCreacion", query = "SELECT n FROM LogActualizaMaster n where n.usuarioCreacion LIKE :usuarioCreacion ORDER BY n.idNap DESC ")
})
public class LogActualizaMaster implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "LogActualizaMaster.TEC_PREFICHA_SQ",
            sequenceName = "MGL_SCHEME.TEC_PREFICHA_SQ", allocationSize = 1)
    @GeneratedValue(generator = "LogActualizaMaster.TEC_PREFICHA_SQ")
    @Column(name = "ID_NAP_MASTER", nullable = false)
    private BigDecimal idNap;
    @Column(name = "TRONCAL", nullable = false, length = 20)
    private String troncal;
    @Column(name = "TIPO_TECNOLOGIA", nullable = true, length = 100)
    private String tipoTecnologia;
    @Column(name = "NOMBRE_ARCHIVO", nullable = false, length = 100)
    private String nombreArchivo;
    @Column(name = "DEPARTAMENTO", nullable = true, length = 50)
    private String departamento;
    @Column(name = "CIUDAD", nullable = true, length = 50)
    private String ciudad;
    @Column(name = "CENTRO_POBLADO", nullable = true, length = 50)
    private String centroPoblado;
    @Column(name = "USUARIO_CREACION", nullable = false, length = 20)
    private String usuarioCreacion;
    @Column(name = "ACTIVIDAD", nullable = false, length = 20)
    private String actividad;
    @Column(name = "TOTAL_REGISTROS", nullable = false)
    private BigDecimal totalRegistros;
    @Column(name = "REG_PROCESADOS", nullable = true)
    private BigDecimal registrosProcesados;
    @Column(name = "REG_NO_PROCESADOS", nullable = true)
    private BigDecimal registrosNoProcesados;
    @Column(name = "FECHA_REGISTRO", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaRegistro;



    public LogActualizaMaster() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public BigDecimal getIdNap() {
        return idNap;
    }

    public String getTroncal() {
        return troncal;
    }

    public String getTipoTecnologia() {
        return tipoTecnologia;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public String getDepartamento() {
        return departamento;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getCentroPoblado() {
        return centroPoblado;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public String getActividad() {
        return actividad;
    }

    public BigDecimal getTotalRegistros() {
        return totalRegistros;
    }

    public BigDecimal getRegistrosProcesados() {
        return registrosProcesados;
    }

    public BigDecimal getRegistrosNoProcesados() {
        return registrosNoProcesados;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setIdNap(BigDecimal idNap) {
        this.idNap = idNap;
    }

    public void setTroncal(String troncal) {
        this.troncal = troncal;
    }

    public void setTipoTecnologia(String tipoTecnologia) {
        this.tipoTecnologia = tipoTecnologia;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setCentroPoblado(String centroPoblado) {
        this.centroPoblado = centroPoblado;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public void setTotalRegistros(BigDecimal totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    public void setRegistrosProcesados(BigDecimal registrosProcesados) {
        this.registrosProcesados = registrosProcesados;
    }

    public void setRegistrosNoProcesados(BigDecimal registrosNoProcesados) {
        this.registrosNoProcesados = registrosNoProcesados;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return (LogActualizaMaster)super.clone();
    }
    

}
