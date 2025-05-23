/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

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
 * @author Admin
 */
@Entity
@Table(name = "CMT_SUBEDIFICIOS_SOLICITUD", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtSubedificiosSolicitudMgl.findAll", query = "SELECT c FROM CmtSubedificiosSolicitudMgl c ")})
public class CmtSubedificiosSolicitudMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtSubedificiosSolicitudMgl.CmtSubedificiosSolicitudMglSeq",
            sequenceName = "MGL_SCHEME.CMT_SUBEDIF_SOLICITUD_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtSubedificiosSolicitudMgl.CmtSubedificiosSolicitudMglSeq")
    @Column(name = "ID_SUBEDIFICIOS_SOL", nullable = false)
    private BigDecimal idSubedificiosSol;
    @Column(name = "ID_MULTI_SOL")
    private BigDecimal idMultiSol;
    @Column(name = "ID_SOLICITUD_CM")
    private BigDecimal idSolicitudCm;
    @Column(name = "ID_COMP_CONSTRUCTORA")
    private BigDecimal idCompConstructora;
    @Column(name = "NOM_SUBEDIFICIO")
    private String nomSubedificio;

    public BigDecimal getIdSubedificiosSol() {
        return idSubedificiosSol;
    }

    public void setIdSubedificiosSol(BigDecimal idSubedificiosSol) {
        this.idSubedificiosSol = idSubedificiosSol;
    }

    public BigDecimal getIdMultiSol() {
        return idMultiSol;
    }

    public void setIdMultiSol(BigDecimal idMultiSol) {
        this.idMultiSol = idMultiSol;
    }

    public BigDecimal getIdSolicitudCm() {
        return idSolicitudCm;
    }

    public void setIdSolicitudCm(BigDecimal idSolicitudCm) {
        this.idSolicitudCm = idSolicitudCm;
    }

    public BigDecimal getIdCompConstructora() {
        return idCompConstructora;
    }

    public void setIdCompConstructora(BigDecimal idCompConstructora) {
        this.idCompConstructora = idCompConstructora;
    }

    public String getNomSubedificio() {
        return nomSubedificio;
    }

    public void setNomSubedificio(String nomSubedificio) {
        this.nomSubedificio = nomSubedificio;
    }
}
