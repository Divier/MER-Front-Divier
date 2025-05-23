/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@Entity
@Table(name = "MGL_RESULT_MOD_FACT_DIR", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ResultModFacDirMgl.findBytoken",
            query = "SELECT r FROM ResultModFacDirMgl r WHERE r.tokenConsulta = :tokenConsulta "),
    @NamedQuery(name = "ResultModFacDirMgl.findDirByToken",
            query = "SELECT r FROM ResultModFacDirMgl r WHERE r.tokenConsulta = :tokenConsulta ")
})
public class ResultModFacDirMgl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "ResultModFacDirMgl.ResultModFacDirMglSq",
            sequenceName = "MGL_SCHEME.MGL_RESULT_MOD_FACT_DIR_SQ",
            allocationSize = 1)
    @GeneratedValue(generator = "ResultModFacDirMgl.ResultModFacDirMglSq")
    @Column(name = "RESULT_MOD_FACT_DIR_ID")
    private BigDecimal resultModFactDirId;

    @Column(name = "TOKEN_CONSULTA")
    private String tokenConsulta;

    @Column(name = "DIRECCION_DETALLADA_ID")
    private BigDecimal direccionDetallada;

    public BigDecimal getResultModFactDirId() {
        return resultModFactDirId;
    }

    public void setResultModFactDirId(BigDecimal resultModFactDirId) {
        this.resultModFactDirId = resultModFactDirId;
    }

    public String getTokenConsulta() {
        return tokenConsulta;
    }

    public void setTokenConsulta(String tokenConsulta) {
        this.tokenConsulta = tokenConsulta;
    }

    public BigDecimal getDireccionDetallada() {
        return direccionDetallada;
    }

    public void setDireccionDetallada(BigDecimal direccionDetallada) {
        this.direccionDetallada = direccionDetallada;
    }

}
