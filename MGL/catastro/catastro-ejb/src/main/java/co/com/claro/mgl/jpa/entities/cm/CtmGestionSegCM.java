/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity de la entidad CMT_GESTION_SEG_CM. Permite manejar
 * los datos de admionistracion de la cuenta matriz
 *
 * @author Carlos Andres Caicedo
 * @versión 1.00.000
 */

@Entity
@Table(name = "CMT_GESTION_SEG_CM", schema = "MGL_SCHEME")
@XmlRootElement
public class CtmGestionSegCM implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CtmGestionSegCM.CtmGestionSegCMSq",
            sequenceName = "MGL_SCHEME.CMT_GESTION_SEG_CM_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CtmGestionSegCM.CtmGestionSegCMSq")
    @Column(name = "ID", nullable = false)
    private BigDecimal id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUENTAMATRIZ_ID")
    private CmtCuentaMatrizMgl cmtCuentaMatrizMgl;
    @Column(name = "CERRADURA_ELECT", nullable = true, length = 1)
    private String cerraduraElect;
    @Column(name = "TIPO_CERRAD_ELECT", nullable = true, length = 200)
    private BigDecimal tipoCerradElect;
    @Column(name = "SERIAL", nullable = true, length = 200)
    private String serial;
    @Column(name = "FABRICANTE", nullable = true, length = 200)
    private String fabricante;
    @Column(name = "PROP_SITIO_NOMBRE", nullable = true, length = 200)
    private String propSitioNombre;
    @Column(name = "PROP_SITIO_CELULAR", nullable = true, length = 200)
    private String propSitioCelular;
    @Column(name = "USUARIO_ACTUALIZA", nullable = false, length = 200)
    private String usuarioActualiza;
    @Column(name = "NOMBRE_JEFE_ZONA", nullable = true, length = 200)
    private String nombreJefeZona;
    @Column(name = "CELULAR_JEFE_ZONA", nullable = true, length = 200)
    private String celularJefeZona;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public CmtCuentaMatrizMgl getCmtCuentaMatrizMgl() {
        return cmtCuentaMatrizMgl;
    }

    public void setCmtCuentaMatrizMgl(CmtCuentaMatrizMgl cmtCuentaMatrizMgl) {
        this.cmtCuentaMatrizMgl = cmtCuentaMatrizMgl;
    }


    public String getCerraduraElect() {
        return cerraduraElect;
    }

    public void setCerraduraElect(String cerraduraElect) {
        this.cerraduraElect = cerraduraElect;
    }

    public BigDecimal getTipoCerradElect() {
        return tipoCerradElect;
    }

    public void setTipoCerradElect(BigDecimal tipoCerradElect) {
        this.tipoCerradElect = tipoCerradElect;
    }



    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public void setPropSitioNombre(String propSitioNombre) {
        this.propSitioNombre = propSitioNombre;
    }
    public String getPropSitioNombre() {
        return propSitioNombre;
    }

    public String getPropSitioCelular() {
        return propSitioCelular;
    }

    public void setPropSitioCedula(String propSitioCelular) {
        this.propSitioCelular = propSitioCelular;
    }

    public String getUsuarioActualiza() {
        return usuarioActualiza;
    }

    public void setUsuarioActualiza(String usuarioActualiza) {
        this.usuarioActualiza = usuarioActualiza;
    }

    @Override
    public String toString() {
        return "CtmGestionSegCM{" + "id=" + id + ", cmtCuentaMatrizMgl=" + cmtCuentaMatrizMgl + ", cerraduraElect=" + cerraduraElect + ", tipoCerradElect=" + tipoCerradElect + ", serial=" + serial + ", fabricante=" + fabricante + ", propSitioNombre=" + propSitioNombre + ", propSitioCelular=" + propSitioCelular + ", usuarioActualiza=" + usuarioActualiza + ", nombreJefeZona=" + nombreJefeZona + ", celularJefeZona=" + celularJefeZona + '}';
    }

    
    
}
