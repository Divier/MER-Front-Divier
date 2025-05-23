/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity de la pagina de seguridad. Permite manejar   
 * la auditoria de los cambios sobre las cuentas matrices
 * @author Carlos Andres Caicedo
 * @versión 1.00.000
 */
@Entity
@Table(name = "CMT_GESTION_SEG_CM_$AUD", schema = "MGL_SCHEME")
@XmlRootElement
public class CtmGestionSegCMAuditoria implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private CtmGestionSegCMAuditoriaPK  ctmGestionSegCMAuditoriaPK;
    @Column(name = "TIPO_OPERACION", nullable = false, length = 1)
    private String tipoOperacion;
    @Column(name = "FECHA_OPERACION", nullable = false, length = 6)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaOperacion;
    @Column(name = "USUARIO", nullable = false, length = 50)
    private String usuario;
    @Column(name = "VALOR_ANTIGUO", nullable = true, length = 4000)
    private String valorAntiguo;
    @Column(name = "VALOR_NUEVO", nullable = true, length = 4000)
    private String valorNuevo;
    @Column(name = "ID_GESTION_SEG_CM", nullable = false, length = 15)
    private BigDecimal idGestionSegCM;

    public CtmGestionSegCMAuditoriaPK getCtmGestionSegCMAuditoriaPK() {
        return ctmGestionSegCMAuditoriaPK;
    }

    public void setCtmGestionSegCMAuditoriaPK(CtmGestionSegCMAuditoriaPK ctmGestionSegCMAuditoriaPK) {
        this.ctmGestionSegCMAuditoriaPK = ctmGestionSegCMAuditoriaPK;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public Date getFechaOperacion() {
        return fechaOperacion;
    }

    public void setFechaOperacion(Date fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getValorAntiguo() {
        return valorAntiguo;
    }

    public void setValorAntiguo(String valorAntiguo) {
        this.valorAntiguo = valorAntiguo;
    }

    public String getValorNuevo() {
        return valorNuevo;
    }

    public void setValorNuevo(String valorNuevo) {
        this.valorNuevo = valorNuevo;
    }

    public BigDecimal getIdGestionSegCM() {
        return idGestionSegCM;
    }

    public void setIdGestionSegCM(BigDecimal idGestionSegCM) {
        this.idGestionSegCM = idGestionSegCM;
    }

    @Override
    public String toString() {
        return "CtmGestionSegCMAuditoria{" + "ctmGestionSegCMAuditoriaPK=" + ctmGestionSegCMAuditoriaPK + ", tipoOperacion=" + tipoOperacion + ", fechaOperacion=" + fechaOperacion + ", usuario=" + usuario + ", valorAntiguo=" + valorAntiguo + ", valorNuevo=" + valorNuevo + ", idGestionSegCM=" + idGestionSegCM + '}';
    }
  

}
