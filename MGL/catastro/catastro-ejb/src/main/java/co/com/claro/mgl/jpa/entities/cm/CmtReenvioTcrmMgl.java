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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author valbuenayf
 */
@Entity
@Table(name = "CMT_REENVIO_TCRM", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtReenvioTcrmMgl.findAll", query = "SELECT r FROM CmtReenvioTcrmMgl r")
})
public class CmtReenvioTcrmMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtReenvioTcrmMgl.cmtReenvioTcrmSq",
            sequenceName = "MGL_SCHEME.CMT_REENVIO_TCRM_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtReenvioTcrmMgl.cmtReenvioTcrmSq")
    @NotNull
    @Column(name = "REENVIO_TCRM_ID")
    private BigDecimal reenvioTcrmId;
    @Size(max = 4000)
    @Column(name = "MENSAJE")
    private String mensaje;
    @Size(max = 4000)
    @Column(name = "RESPUESTA")
    private String respuesta;
    @Column(name = "CONTADOR")
    private int contador;

    public BigDecimal getReenvioTcrmId() {
        return reenvioTcrmId;
    }

    public void setReenvioTcrmId(BigDecimal reenvioTcrmId) {
        this.reenvioTcrmId = reenvioTcrmId;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }
}
