/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.claro.mgl.jpa.entities.cm;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Pk de la entidad CtmGestionSegCMAuditoriaPK. Permite manejar   
 * la llave primaria para la entidad auditoria de cuentas matrices
 * @author Carlos Andres Caicedo
 * @versión 1.00.000
 */
@Embeddable
public class CtmGestionSegCMAuditoriaPK implements Serializable {

    @Column(name = "ID_AUDITORIA", nullable = false, length = 15)
    private BigDecimal idAuditoria;

    @Column(name = "CUENTAMATRIZ_ID", nullable = false, length = 15)
    private BigDecimal cuentaMatrizId;

    @Column(name = "NOMBRE_COLUMNA", nullable = true, length = 50)
    private String nombreColumna;

    public BigDecimal getIdAuditoria() {
        return idAuditoria;
    }

    public void setIdAuditoria(BigDecimal idAuditoria) {
        this.idAuditoria = idAuditoria;
    }

    public BigDecimal getCuentaMatrizId() {
        return cuentaMatrizId;
    }

    public void setCuentaMatrizId(BigDecimal cuentaMatrizId) {
        this.cuentaMatrizId = cuentaMatrizId;
    }

    public String getNombreColumna() {
        return nombreColumna;
    }

    public void setNombreColumna(String nombreColumna) {
        this.nombreColumna = nombreColumna;
    }

}
