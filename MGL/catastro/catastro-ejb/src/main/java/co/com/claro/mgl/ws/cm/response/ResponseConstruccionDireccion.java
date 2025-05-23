package co.com.claro.mgl.ws.cm.response;

import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.telmex.catastro.data.AddressSuggested;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ortizjaf
 */
@XmlRootElement
public class ResponseConstruccionDireccion {
    
    

    @XmlElement
    private ResponseMesaje responseMesaje;
    @XmlElement
    private DrDireccion drDireccion;
    @XmlElement
    private List<AddressSuggested> barrioList;
    @XmlElement
    private String direccionStr;
    @XmlElement
    private String direccionRespuestaGeo;
    
    @XmlTransient
    private String nombreDpto;
    @XmlTransient
    private String nombreCity;
    @XmlTransient
    private String nombreCentro;
    @XmlTransient
    private CmtDireccionDetalladaMgl detalladaMgl;

    public ResponseMesaje getResponseMesaje() {
        return responseMesaje;
    }

    public void setResponseMesaje(ResponseMesaje responseMesaje) {
        this.responseMesaje = responseMesaje;
    }

    public DrDireccion getDrDireccion() {
        return drDireccion;
    }

    public void setDrDireccion(DrDireccion drDireccion) {
        this.drDireccion = drDireccion;
    }    

    public List<AddressSuggested> getBarrioList() {
        return barrioList;
    }

    public void setBarrioList(List<AddressSuggested> barrioList) {
        this.barrioList = barrioList;
    }

    public String getDireccionStr() {
        return direccionStr;
    }

    public void setDireccionStr(String direccionStr) {
        this.direccionStr = direccionStr;
    }

    public String getDireccionRespuestaGeo() {
        return direccionRespuestaGeo;
    }

    public void setDireccionRespuestaGeo(String direccionRespuestaGeo) {
        this.direccionRespuestaGeo = direccionRespuestaGeo;
    }

    public String getNombreDpto() {
        return nombreDpto;
    }

    public void setNombreDpto(String nombreDpto) {
        this.nombreDpto = nombreDpto;
    }

    public String getNombreCity() {
        return nombreCity;
    }

    public void setNombreCity(String nombreCity) {
        this.nombreCity = nombreCity;
    }

    public String getNombreCentro() {
        return nombreCentro;
    }

    public void setNombreCentro(String nombreCentro) {
        this.nombreCentro = nombreCentro;
    }

    public CmtDireccionDetalladaMgl getDetalladaMgl() {
        return detalladaMgl;
    }

    public void setDetalladaMgl(CmtDireccionDetalladaMgl detalladaMgl) {
        this.detalladaMgl = detalladaMgl;
    }
    
}
