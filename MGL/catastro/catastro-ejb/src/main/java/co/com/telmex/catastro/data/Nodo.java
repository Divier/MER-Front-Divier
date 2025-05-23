package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Clase Nodo
 * Implementa Serialización.
 *
 * @author 	Nataly Orozco Torres
 * @version	1.0
 */
public class Nodo implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal nodId;
    private String nodNombre;
    private String nodCodigo;
    private Date nodFechaActivacion;
    private String nodHeadend;
    private Zona zona;
    private UnidadGestion unidadGestion;
    private GeograficoPolitico geograficoPolitico;
    private Divisional divisional;
    private Distrito distrito;
    private Area area;
    private Comunidad comunidad;
    private String nodCampoAdicional1;
    private String nodCampoAdicional2;
    private String nodCampoAdicional3;
    private String nodCampoAdicional4;
    private String nodCampoAdicional5;
    private BigDecimal nodTipo;
    private Date nodFechaCreacion;
    private String nodUsuarioCreacion;
    private Date nodFechaModificacion;
    private String nodUsuarioModificacion;

    /**
     * Constructor.
     */
    public Nodo() {
    }

    /**
     * Constructor con parámetros.
     * @param nodId Entero con el Id del nodo.
     */
    public Nodo(BigDecimal nodId) {
        this.nodId = nodId;
    }

    /**
     * Obtiene el Id del nodo
     * @return Entero con el Id del nodo.
     */
    public BigDecimal getNodId() {
        return nodId;
    }

    /**
     * Establece el Id del nodo.
     * @param nodId Entero con el Id del nodo.
     */
    public void setNodId(BigDecimal nodId) {
        this.nodId = nodId;
    }

    /**
     * Obtiene el nombre del nodo.
     * @return Cadena con el nombre del nodo.
     */
    public String getNodNombre() {
        return nodNombre;
    }

    /**
     * Establece el nombre del nodo.
     * @param nodNombre Cadena con el nombre del nodo.
     */
    public void setNodNombre(String nodNombre) {
        this.nodNombre = nodNombre;
    }

    /**
     * Obtiene el código del nodo.
     * @return Cadena con el código del nodo.
     */
    public String getNodCodigo() {
        return nodCodigo;
    }

    /**
     * Establece el código del nodo.
     * @param nodCodigo Cadena con el código del nodo.
     */
    public void setNodCodigo(String nodCodigo) {
        this.nodCodigo = nodCodigo;
    }

    /**
     * Obtiene la Fecha de activación del nodo. 
     * @return Fecha de activación del nodo.
     */
    public Date getNodFechaActivacion() {
        return nodFechaActivacion;
    }

    /**
     * Establece la fecha de activación del nodo.
     * @param nodFechaActivacion Fecha de activación del nodo.
     */
    public void setNodFechaActivacion(Date nodFechaActivacion) {
        this.nodFechaActivacion = nodFechaActivacion;
    }

    /**
     * Obtiene el Head End del nodo.
     * @return Cadena con el Head End del nodo.
     */
    public String getNodHeadend() {
        return nodHeadend;
    }

    /**
     * Establece el Head End del nodo.
     * @param nodHeadend Cadena con el Head End del nodo.
     */
    public void setNodHeadend(String nodHeadend) {
        this.nodHeadend = nodHeadend;
    }

    /**
     * Obtiene la fecha de creación del nodo.
     * @return Fecha de creación del nodo.
     */
    public Date getNodFechaCreacion() {
        return nodFechaCreacion;
    }

    /**
     * Establece la fecha de creación del nodo.
     * @param nodFechaCreacion Fecha de creación del nodo.
     */
    public void setNodFechaCreacion(Date nodFechaCreacion) {
        this.nodFechaCreacion = nodFechaCreacion;
    }

    /**
     * Obtiene El campo Adicional 1
     * @return Cadena con el campo adicional 1.
     */
    public String getNodCampoAdicional1() {
        return nodCampoAdicional1;
    }

    /**
     * Establece el campo adicional 1.
     * @param nodCampoAdicional1 Cadena con el campo adicional 1.
     */
    public void setNodCampoAdicional1(String nodCampoAdicional1) {
        this.nodCampoAdicional1 = nodCampoAdicional1;
    }

    /**
     * Obtiene el campo adicional 2. 
     * @return Cadena con el campo adicional 2.
     */
    public String getNodCampoAdicional2() {
        return nodCampoAdicional2;
    }

    /**
     * Establece el campo adicional 2.
     * @param nodCampoAdicional2 Cadena con el campo adicional 2.
     */
    public void setNodCampoAdicional2(String nodCampoAdicional2) {
        this.nodCampoAdicional2 = nodCampoAdicional2;
    }

    /**
     * Obtiene el campo adicional 3.
     * @return Cadena con el campo adicional 3.
     */
    public String getNodCampoAdicional3() {
        return nodCampoAdicional3;
    }

    /**
     * Establece el campo adicional 3.
     * @param nodCampoAdicional3 Cadena con el campo adicional 3.
     */
    public void setNodCampoAdicional3(String nodCampoAdicional3) {
        this.nodCampoAdicional3 = nodCampoAdicional3;
    }

    /**
     * Obtiene el campo adicional 4.
     * @return Cadena con el nombre adicional 4.
     */
    public String getNodCampoAdicional4() {
        return nodCampoAdicional4;
    }

    /**
     * Establece el campo adicional 4.
     * @param nodCampoAdicional4 Cadena con el campo adicional 4.
     */
    public void setNodCampoAdicional4(String nodCampoAdicional4) {
        this.nodCampoAdicional4 = nodCampoAdicional4;
    }

    /**
     * Obtiene el campo adicional 5.
     * @return Cadena con el campo adicional 5.
     */
    public String getNodCampoAdicional5() {
        return nodCampoAdicional5;
    }

    /**
     * Establece el campo adicional 5.
     * @param nodCampoAdicional5 Cadena con el campo adicional 5.
     */
    public void setNodCampoAdicional5(String nodCampoAdicional5) {
        this.nodCampoAdicional5 = nodCampoAdicional5;
    }

    public BigDecimal getNodTipo() {
        return nodTipo;
    }

    public void setNodTipo(BigDecimal nodTipo) {
        this.nodTipo = nodTipo;
    }


    /**
     * Obtiene el nombre del usuario que realiza la creación del nodo.
     * @return Cadena con el nombre del usuario que realiza la creación.
     */
    public String getNodUsuarioCreacion() {
        return nodUsuarioCreacion;
    }

    /**
     * Establece el nombre de usuario que realiza la creación.
     * @param nodUsuarioCreacion Cadena con el nombre de usuario que realiza la creación.
     */
    public void setNodUsuarioCreacion(String nodUsuarioCreacion) {
        this.nodUsuarioCreacion = nodUsuarioCreacion;
    }

    /**
     * Obtiene la fecha en la que se realiza la modificación
     * @return Fecha en que se realiza la modificación
     */
    public Date getNodFechaModificacion() {
        return nodFechaModificacion;
    }

    /**
     * Establece la fecha de modificación
     * @param nodFechaModificacion Fecha de modificación
     */
    public void setNodFechaModificacion(Date nodFechaModificacion) {
        this.nodFechaModificacion = nodFechaModificacion;
    }

    /**
     * Obtiene el usuario que realiza la modificación.
     * @return Cadena con el usuario que realiza la modificación.
     */
    public String getNodUsuarioModificacion() {
        return nodUsuarioModificacion;
    }

    /**
     * Establece el usuario que realiza la modificación.
     * @param nodUsuarioModificacion Cadena con el usuario que realiza la modificación.
     */
    public void setNodUsuarioModificacion(String nodUsuarioModificacion) {
        this.nodUsuarioModificacion = nodUsuarioModificacion;
    }

    /**
     * Obtiene la zona.
     * @return Objeto Zona con la información.
     */
    public Zona getZona() {
        return zona;
    }

    /**
     * Establece la Zona.
     * @param zona Objeto Zona con la información.
     */
    public void setZona(Zona zona) {
        this.zona = zona;
    }

    /**
     * Obtiene la unidad de gestión.
     * @return Objeto UnidadGestion con la información.
     */
    public UnidadGestion getUnidadGestion() {
        return unidadGestion;
    }

    /**
     * Establece la UnidadGestion
     * @param unidadGestion Objeto UnidadGestion.
     */
    public void setUnidadGestion(UnidadGestion unidadGestion) {
        this.unidadGestion = unidadGestion;
    }

    /**
     * Obtiene el GeograficoPolitico
     * @return Objeto GeograficoPolitico con la información.
     */
    public GeograficoPolitico getGeograficoPolitico() {
        return geograficoPolitico;
    }

    /**
     * Establece el GeograficoPolitico
     * @param geograficoPolitico Objeto GeograficoPolitico con la información
     */
    public void setGeograficoPolitico(GeograficoPolitico geograficoPolitico) {
        this.geograficoPolitico = geograficoPolitico;
    }

    /**
     * Obtiene el Divisional
     * @return Objeto Divisional con la información.
     */
    public Divisional getDivisional() {
        return divisional;
    }

    /**
     * Establece el Divisional.
     * @param divisional Objeto Divisional con la información.
     */
    public void setDivisional(Divisional divisional) {
        this.divisional = divisional;
    }

    /**
     * Obtiene el Distrito
     * @return Objeto Distrito con la información.
     */
    public Distrito getDistrito() {
        return distrito;
    }

    /**
     * Establece el Distrito
     * @param distrito Objeto Distrito con la información.
     */
    public void setDistrito(Distrito distrito) {
        this.distrito = distrito;
    }

    /**
     * Obtiene el Area
     * @return Objeto Area con la información.
     */
    public Area getArea() {
        return area;
    }

    /**
     * Establece el Area.
     * @param area Objeto Area con la información.
     */
    public void setArea(Area area) {
        this.area = area;
    }

    /**
     * Obtiene la comunidad.
     * @return Objeto Comunidad con la información.
     */
    public Comunidad getComunidad() {
        return comunidad;
    }

    /**
     * Establece la Comunidad 
     * @param comunidad Objeto Comunidad con la información.
     */
    public void setComunidad(Comunidad comunidad) {
        this.comunidad = comunidad;
    }

    /**
     * Obtiene la cadena de información para Auditoría.
     * @return Cadena de información de auditoría.
     */
    public String auditoria() {
        return "Nodo:" + "nodId=" + nodId + ", nodNombre=" + nodNombre
                + ", nodCodigo=" + nodCodigo
                + ", nodFechaActivacion=" + nodFechaActivacion
                + ", nodHeadend=" + nodHeadend + ", zona=" + zona
                + ", unidadGestion=" + unidadGestion
                + ", geograficoPolitico=" + geograficoPolitico
                + ", distrito=" + distrito + ", area=" + area
                + ", comunidad=" + comunidad + ", nodTipo=" + nodTipo + '.';
    }

    /**
     * Sobre Escritura del método toString()
     * @return Cadena con la información de la auditoría.
     */
    @Override
    public String toString() {
        return "Nodo:" + "nodId=" + nodId + ", nodNombre=" + nodNombre
                + ", nodCodigo=" + nodCodigo
                + ", nodFechaActivacion=" + nodFechaActivacion
                + ", nodHeadend=" + nodHeadend + ", zona=" + zona
                + ", unidadGestion=" + unidadGestion
                + ", geograficoPolitico=" + geograficoPolitico
                + ", distrito=" + distrito + ", area=" + area
                + ", comunidad=" + comunidad + ", nodTipo=" + nodTipo + '.';
    }
}