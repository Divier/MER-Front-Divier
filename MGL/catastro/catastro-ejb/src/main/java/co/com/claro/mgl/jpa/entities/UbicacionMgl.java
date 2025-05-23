/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "MGL_UBICACION", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UbicacionMgl.findAll", query = "SELECT s FROM UbicacionMgl s")})
public class UbicacionMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "UbicacionMgl.UbicacionMglSeq",
            sequenceName = "MGL_SCHEME.MGL_UBICACION_SQ", allocationSize = 1)
    @GeneratedValue(generator = "UbicacionMgl.UbicacionMglSeq")
    @Column(name = "UBICACION_ID", nullable = false)
    private BigDecimal ubiId;

    @ManyToOne
    @JoinColumn(name = "GEOGRAFICO_POLITICO_ID", nullable = true)
    private GeograficoPoliticoMgl gpoIdObj;
    @Column(name = "TIPO_UBICACION_ID", nullable = true)
    private BigDecimal tubId;

    @ManyToOne
    @JoinColumn(name = "GEOGRAFICO_ID", nullable = true)
    private GeograficoMgl geoIdObj;
    @Column(name = "NOMBRE")
    private String ubiNombre;
    @Column(name = "LATITUD", nullable = true, length = 15)
    private String ubiLatitud;
    @Column(name = "LONGITUD", nullable = true, length = 15)
    private String ubiLongitud;
    //JDHT
    @Column(name = "LATITUD_NUM", nullable = true, length = 15)
    private BigDecimal ubiLatitudNum;
    @Column(name = "LONGITUD_NUM", nullable = true, length = 15)
    private BigDecimal ubiLongitudNum;
    @Column(name = "ESTADO_RED", nullable = true, length = 30)
    private String ubiEstadoRed;
    @Column(name = "FECHA_CREACION", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date ubiFechaCreacion;
    @Column(name = "USUARIO_CREACION", nullable = true, length = 50)
    private String ubiUsuarioCreacion;
    @Column(name = "FECHA_MODIFICACION", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date ubiFechaModificacion;
    @Column(name = "USUARIO_MODIFICACION", nullable = true, length = 50)
    private String ubiUsuarioModificacion;
    @Column(name = "CUENTA_MATRIZ", nullable = true, length = 10)
    private String ubiCuentaMatriz;
    @Column(name = "ZONA_DIVIPOLA", nullable = true, length = 3)
    private String ubiZonaDiviPola;
    @Column(name = "ZONA_DISTRITO_CODIGO_ZIP", nullable = true, length = 4)
    private String ubiZonaDistritoCodigoZip;


    public BigDecimal getUbiId() {
        return ubiId;
    }

    public void setUbiId(BigDecimal ubiId) {
        this.ubiId = ubiId;
    }



    public GeograficoPoliticoMgl getGpoIdObj() {
        return gpoIdObj;
    }

    public void setGpoIdObj(GeograficoPoliticoMgl gpoIdObj) {
        this.gpoIdObj = gpoIdObj;
    }

    public BigDecimal getTubId() {
        return tubId;
    }

    public void setTubId(BigDecimal tubId) {
        this.tubId = tubId;
    }



    public GeograficoMgl getGeoIdObj() {
        return geoIdObj;
    }

    public void setGeoIdObj(GeograficoMgl geoIdObj) {
        this.geoIdObj = geoIdObj;
    }

    public String getUbiNombre() {
        return ubiNombre;
    }

    public void setUbiNombre(String ubiNombre) {
        this.ubiNombre = ubiNombre;
    }

    public String getUbiLatitud() {
        return ubiLatitud;
    }

    public void setUbiLatitud(String ubiLatitud) {
        this.ubiLatitud = ubiLatitud;
    }

    public String getUbiLongitud() {
        return ubiLongitud;
    }

    public void setUbiLongitud(String ubiLongitud) {
        this.ubiLongitud = ubiLongitud;
    }

    public String getUbiEstadoRed() {
        return ubiEstadoRed;
    }

    public void setUbiEstadoRed(String ubiEstadoRed) {
        this.ubiEstadoRed = ubiEstadoRed;
    }

    public Date getUbiFechaCreacion() {
        return ubiFechaCreacion;
    }

    public void setUbiFechaCreacion(Date ubiFechaCreacion) {
        this.ubiFechaCreacion = ubiFechaCreacion;
    }

    public String getUbiUsuarioCreacion() {
        return ubiUsuarioCreacion;
    }

    public void setUbiUsuarioCreacion(String ubiUsuarioCreacion) {
        this.ubiUsuarioCreacion = ubiUsuarioCreacion;
    }

    public Date getUbiFechaModificacion() {
        return ubiFechaModificacion;
    }

    public void setUbiFechaModificacion(Date ubiFechaModificacion) {
        this.ubiFechaModificacion = ubiFechaModificacion;
    }

    public String getUbiUsuarioModificacion() {
        return ubiUsuarioModificacion;
    }

    public void setUbiUsuarioModificacion(String ubiUsuarioModificacion) {
        this.ubiUsuarioModificacion = ubiUsuarioModificacion;
    }

    public String getUbiCuentaMatriz() {
        return ubiCuentaMatriz;
    }

    public void setUbiCuentaMatriz(String ubiCuentaMatriz) {
        this.ubiCuentaMatriz = ubiCuentaMatriz;
    }

    public String getUbiZonaDiviPola() {
        return ubiZonaDiviPola;
    }

    public void setUbiZonaDiviPola(String ubiZonaDiviPola) {
        this.ubiZonaDiviPola = ubiZonaDiviPola;
    }

    public String getUbiZonaDistritoCodigoZip() {
        return ubiZonaDistritoCodigoZip;
    }

    public void setUbiZonaDistritoCodigoZip(String ubiZonaDistritoCodigoZip) {
        this.ubiZonaDistritoCodigoZip = ubiZonaDistritoCodigoZip;
    }

    public BigDecimal getUbiLatitudNum() {
        return ubiLatitudNum;
    }

    public void setUbiLatitudNum(BigDecimal ubiLatitudNum) {
        this.ubiLatitudNum = ubiLatitudNum;
    }

    public BigDecimal getUbiLongitudNum() {
        return ubiLongitudNum;
    }

    public void setUbiLongitudNum(BigDecimal ubiLongitudNum) {
        this.ubiLongitudNum = ubiLongitudNum;
    }  

    @Override
    public String toString() {
        return "co.com.claro.mgl.jpa.entities.UbicacionMgl[ id=" + ubiId + ", UBI_NOMBRE = " + ubiNombre + "  ]";
    }
    
}
