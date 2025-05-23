package co.com.claro.mgl.rest.dtos;

import co.com.claro.mgl.dtos.CmtDefaultBasicResponse;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jrodriguez
 */
@XmlRootElement(name = "nodeMgl")
public class NodoMglDto extends CmtDefaultBasicResponse {

    @XmlElement
    private String nombre;
    @XmlElement
    private String codigo;
    @XmlElement
    private String fechaActivacion;
    @XmlElement
    private String fechaCreacion;
    @XmlElement
    private String usuarioCreacion;
    @XmlElement
    private String fechaModificacion;
    @XmlElement
    private String usuarioModificacion;
    @XmlElement
    private String headEnd;
    @XmlElement
    private String campo1;
    @XmlElement
    private String campo2;
    @XmlElement
    private String campo3;
    @XmlElement
    private String campo4;
    @XmlElement
    private String campo5;
    @XmlElement
    private int estadoRegistro;
    @XmlElement
    private int perfilCreacion;
    @XmlElement
    private int perfilEdicion;

    @XmlElement
    private GeograficoPoliticoDto geograficoPolitico = new GeograficoPoliticoDto();

    @XmlElement
    private CmtComunidadRrDto cmtComunidad = new CmtComunidadRrDto();

    @XmlElement
    private CmtBasicaMglDto area = new CmtBasicaMglDto();

    @XmlElement
    private CmtBasicaMglDto unidadGestion = new CmtBasicaMglDto();

    @XmlElement
    private CmtBasicaMglDto zona = new CmtBasicaMglDto();

    @XmlElement
    private CmtBasicaMglDto divisional = new CmtBasicaMglDto();

    @XmlElement
    private CmtBasicaMglDto distrito = new CmtBasicaMglDto();

    @XmlElement
    private CmtBasicaMglDto estado = new CmtBasicaMglDto();

    @XmlElement
    private CmtBasicaMglDto aliado = new CmtBasicaMglDto();

    @XmlElement
    private BigDecimal costoRed;
    
    @XmlElement
    private String limites;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getFechaActivacion() {
        return fechaActivacion;
    }

    public void setFechaActivacion(String fechaActivacion) {
        this.fechaActivacion = fechaActivacion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public String getHeadEnd() {
        return headEnd;
    }

    public void setHeadEnd(String headEnd) {
        this.headEnd = headEnd;
    }

    public String getCampo1() {
        return campo1;
    }

    public void setCampo1(String campo1) {
        this.campo1 = campo1;
    }

    public String getCampo2() {
        return campo2;
    }

    public void setCampo2(String campo2) {
        this.campo2 = campo2;
    }

    public String getCampo3() {
        return campo3;
    }

    public void setCampo3(String campo3) {
        this.campo3 = campo3;
    }

    public String getCampo4() {
        return campo4;
    }

    public void setCampo4(String campo4) {
        this.campo4 = campo4;
    }

    public String getCampo5() {
        return campo5;
    }

    public void setCampo5(String campo5) {
        this.campo5 = campo5;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public int getPerfilCreacion() {
        return perfilCreacion;
    }

    public void setPerfilCreacion(int perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
    }

    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    public GeograficoPoliticoDto getGeograficoPolitico() {
        return geograficoPolitico;
    }

    public void setGeograficoPolitico(GeograficoPoliticoDto geograficoPolitico) {
        this.geograficoPolitico = geograficoPolitico;
    }

    public CmtComunidadRrDto getCmtComunidad() {
        return cmtComunidad;
    }

    public void setCmtComunidad(CmtComunidadRrDto cmtComunidad) {
        this.cmtComunidad = cmtComunidad;
    }

    public CmtBasicaMglDto getArea() {
        return area;
    }

    public void setArea(CmtBasicaMglDto area) {
        this.area = area;
    }

    public CmtBasicaMglDto getUnidadGestion() {
        return unidadGestion;
    }

    public void setUnidadGestion(CmtBasicaMglDto unidadGestion) {
        this.unidadGestion = unidadGestion;
    }

    public CmtBasicaMglDto getZona() {
        return zona;
    }

    public void setZona(CmtBasicaMglDto zona) {
        this.zona = zona;
    }

    public CmtBasicaMglDto getDivisional() {
        return divisional;
    }

    public void setDivisional(CmtBasicaMglDto divisional) {
        this.divisional = divisional;
    }

    public CmtBasicaMglDto getDistrito() {
        return distrito;
    }

    public void setDistrito(CmtBasicaMglDto distrito) {
        this.distrito = distrito;
    }

    public CmtBasicaMglDto getEstado() {
        return estado;
    }

    public void setEstado(CmtBasicaMglDto estado) {
        this.estado = estado;
    }

    public CmtBasicaMglDto getAliado() {
        return aliado;
    }

    public void setAliado(CmtBasicaMglDto aliado) {
        this.aliado = aliado;
    }

    public BigDecimal getCostoRed() {
        return costoRed;
    }

    public void setCostoRed(BigDecimal costoRed) {
        this.costoRed = costoRed;
    }

    public String getLimites() {
        return limites;
    }

    public void setLimites(String limites) {
        this.limites = limites;
    }

    
    
}
