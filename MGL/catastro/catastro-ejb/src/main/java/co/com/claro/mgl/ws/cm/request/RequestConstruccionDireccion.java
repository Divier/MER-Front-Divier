package co.com.claro.mgl.ws.cm.request;

import co.com.claro.mgl.jpa.entities.DrDireccion;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ortizjaf
 */
@XmlRootElement
public class RequestConstruccionDireccion implements Cloneable{
    
    @XmlElement
    private DrDireccion drDireccion;
    @XmlElement
    private String direccionStr;
    @XmlElement
    private String comunidad;
    @XmlElement
    private String barrio;
    @XmlElement
    private String tipoAdicion;//P-pincipal,C-Complemento,A-apartamento,N-Nada
    @XmlElement
    private String tipoNivel;
    @XmlElement
    private String valorNivel;
    @XmlElement
    private String idUsuario;

    private boolean isFichas;

    private boolean dirConstruccionCorrecta;

    private BigDecimal idPrefichaXls;

    public DrDireccion getDrDireccion() {
        return drDireccion;
    }

    public void setDrDireccion(DrDireccion drDireccion) {
        this.drDireccion = drDireccion;
    }

    public String getDireccionStr() {
        return direccionStr;
    }

    public void setDireccionStr(String direccionStr) {
        this.direccionStr = direccionStr;
    }

    public String getComunidad() {
        return comunidad;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getTipoAdicion() {
        return tipoAdicion;
    }

    public void setTipoAdicion(String tipoAdicion) {
        this.tipoAdicion = tipoAdicion;
    }

    public String getTipoNivel() {
        return tipoNivel;
    }

    public void setTipoNivel(String tipoNivel) {
        this.tipoNivel = tipoNivel;
    }

    public String getValorNivel() {
        return valorNivel;
    }

    public void setValorNivel(String valorNivel) {
        this.valorNivel = valorNivel;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public boolean isIsFichas() {
        return isFichas;
    }

    public void setIsFichas(boolean isFichas) {
        this.isFichas = isFichas;
    }
    
    @Override
    public RequestConstruccionDireccion clone() throws CloneNotSupportedException {
        return (RequestConstruccionDireccion) super.clone();
    }

    public boolean isDirConstruccionCorrecta() {
        return dirConstruccionCorrecta;
    }

    public void setDirConstruccionCorrecta(boolean dirConstruccionCorrecta) {
        this.dirConstruccionCorrecta = dirConstruccionCorrecta;
    }

    public BigDecimal getIdPrefichaXls() {
        return idPrefichaXls;
    }

    public void setIdPrefichaXls(BigDecimal idPrefichaXls) {
        this.idPrefichaXls = idPrefichaXls;
    }


    
}
