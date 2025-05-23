/*
 * Entidad AVISO PROGRAMADO creada para almacenar la informacion para cuando se 
 * cambie el estado de un HHPP o CCMM genere una peticion a TCRM informando el 
 * estado
 */
package co.com.claro.mgl.dtos;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rodriguezluim
 */
@XmlRootElement(name = "cmtAvisoProgramadoDto")
public class CmtAvisoProgramadoDto extends CmtDefaultBasicResquest {

    @XmlElement
    private String idCasoTcrm;
    @XmlElement
    private BigDecimal idTecnologiaSubEdificio;
    @XmlElement
    private BigDecimal idHhpp;
    @XmlElement
    private String tecnologia;

    public String getIdCasoTcrm() {
        return idCasoTcrm;
    }

    public void setIdCasoTcrm(String idCasoTcrm) {
        this.idCasoTcrm = idCasoTcrm;
    }

    public BigDecimal getIdTecnologiaSubEdificio() {
        return idTecnologiaSubEdificio;
    }

    public void setIdTecnologiaSubEdificio(BigDecimal idTecnologiaSubEdificio) {
        this.idTecnologiaSubEdificio = idTecnologiaSubEdificio;
    }

    public BigDecimal getIdHhpp() {
        return idHhpp;
    }

    public void setIdHhpp(BigDecimal idHhpp) {
        this.idHhpp = idHhpp;
    }

    public String getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(String tecnologia) {
        this.tecnologia = tecnologia;
    }
}
