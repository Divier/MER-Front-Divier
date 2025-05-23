/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Clase para guardar la relacion de la factibilidad con el codigo de consulta
 *
 * @author Yasser Leon
 */
@Entity
@Table(name = "MGL_VISOR_FACTIBILIDAD", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "VisorFactibilidad.findIdFactibilidadByCodigo", query = "SELECT vf.visorFactibilidadId FROM VisorFactibilidad vf where vf.codigo = :codigo")})
public class VisorFactibilidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "VISORFACTIBILIDAD_ID", nullable = false)
    private BigDecimal visorFactibilidadId;

    @Column(name = "CODIGO", nullable = false)
    private String codigo;

    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    public BigDecimal getVisorFactibilidadId() {
        return visorFactibilidadId;
    }

    public void setVisorFactibilidadId(BigDecimal visorFactibilidadId) {
        this.visorFactibilidadId = visorFactibilidadId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
