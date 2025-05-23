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
@Table(name = "CMT_TP_MULTIEDIFICIO_SOLICITUD", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtTpMultiedificioSolicitudMgl.findAll", query = "SELECT c FROM CmtTpMultiedificioSolicitudMgl c ")})
public class CmtTpMultiedificioSolicitudMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtTpMultiedificioSolicitudMgl.CmtTpMultiedificioSolicitudMglSeq",
            sequenceName = "GESTIONNEW.CMT_TP_MULTIEDIF_SOLI_SEQ ", allocationSize = 1)
    @GeneratedValue(generator = "CmtTpMultiedificioSolicitudMgl.CmtTpMultiedificioSolicitudMglSeq")
    @Column(name = "ID_MULTI_SOL", nullable = false)
    private BigDecimal idMultiSol;
    @Column(name = "ID_SOLICITUD_CM")
    private BigDecimal idSolicitudCm;
    @Column(name = "TIPO_SUBEDIF")
    private String tipoSubedif;
    @Column(name = "RANGO_INI")
    private String rangoIni;
    @Column(name = "RANGO_FIN")
    private String rangoFin;
    @Column(name = "NUM_SUBEDIFICIO")
    private BigDecimal numSubedificio;

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

    public String getTipoSubedif() {
        return tipoSubedif;
    }

    public void setTipoSubedif(String tipoSubedif) {
        this.tipoSubedif = tipoSubedif;
    }

    public String getRangoIni() {
        return rangoIni;
    }

    public void setRangoIni(String rangoIni) {
        this.rangoIni = rangoIni;
    }

    public String getRangoFin() {
        return rangoFin;
    }

    public void setRangoFin(String rangoFin) {
        this.rangoFin = rangoFin;
    }

    public BigDecimal getNumSubedificio() {
        return numSubedificio;
    }

    public void setNumSubedificio(BigDecimal numSubedificio) {
        this.numSubedificio = numSubedificio;
    }
}
