/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@Entity
@Table(name = "MGL_FACTIBILIDAD", schema = "MGL_SCHEME")
@XmlRootElement
@NoArgsConstructor
public class FactibilidadMgl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(
            name = "FactibilidadMgl.FactibilidadMglSq",
            sequenceName = "MGL_SCHEME.MGL_FACTIBILIDAD_SQ", allocationSize = 1)
    @GeneratedValue(generator = "FactibilidadMgl.FactibilidadMglSq")
    @Column(name = "FACTIBILIDAD_ID", nullable = false)
    private BigDecimal factibilidadId;

    @Column(name = "DIRECCION_DETALLADA_ID", nullable = false)
    private BigDecimal direccionDetalladaId;

    @Column(name = "FECHA_CREACION", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @Column(name = "FECHA_VENCIMIENTO", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaVencimiento;
    
    @Column(name = "USUARIO")
    private String usuario;
    
    @Column(name = "NOMBRE_ARCHIVO_MASIVO")
    private String nombreArchivo;

    @OneToMany(mappedBy = "factibilidadMgl", fetch = FetchType.LAZY)
    private List<CmtDireccionDetalladaMgl> cmtDireccion;

    public BigDecimal getFactibilidadId() {
        return factibilidadId;
    }

    public void setFactibilidadId(BigDecimal factibilidadId) {
        this.factibilidadId = factibilidadId;
    }

    public BigDecimal getDireccionDetalladaId() {
        return direccionDetalladaId;
    }

    public void setDireccionDetalladaId(BigDecimal direccionDetalladaId) {
        this.direccionDetalladaId = direccionDetalladaId;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public List<CmtDireccionDetalladaMgl> getCmtDireccion() {
        return cmtDireccion;
    }

    public void setCmtDireccion(List<CmtDireccionDetalladaMgl> cmtDireccion) {
        this.cmtDireccion = cmtDireccion;
    }

}
