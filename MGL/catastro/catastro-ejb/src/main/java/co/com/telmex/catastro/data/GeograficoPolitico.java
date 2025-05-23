package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Clase GeograficoPolitico
 * Implementa Serialización.
 *
 * @author 	Deiver Rovira
 * @version	1.0
 */
public class GeograficoPolitico implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal gpoId;
    private String gpoNombre;
    private String gpoCodigoDane;
    private String gpoTipo;
    private String gpoMultiorigen;
    private String gpoCodTipoDireccion;
    private String gpoCodigo;
    private Date gpoFechaCreacion;
    private String gpoUsuarioCreacion;
    private Date gpoFechaModificacion;
    private String gpoUsuarioModificacion;
    private List<GeograficoPolitico> geograficoPoliticoList;
    private GeograficoPolitico geograficoPolitico;
    private List<GeograficoPolitico> geograficoPoliticoList1;

    /**
     * Constructor.
     */
    public GeograficoPolitico() {
    }

    /**
     * Constructor con parámetros.
     * @param gpoId Entero con el Id.
     */
    public GeograficoPolitico(BigDecimal gpoId) {
        this.gpoId = gpoId;
    }

    /**
     * Constructor con Id y Tipo
     * @param gpoId Entero con el Id.
     * @param gpoTipo Cadena con el tipo.
     */
    public GeograficoPolitico(BigDecimal gpoId, String gpoTipo) {
        this.gpoId = gpoId;
        this.gpoTipo = gpoTipo;
    }

    /**
     * Obtiene el Id.
     * @return Entero con el Id.
     */
    public BigDecimal getGpoId() {
        return gpoId;
    }

    /**
     * Establece el Id.
     * @param gpoId Entero con el Id.
     */
    public void setGpoId(BigDecimal gpoId) {
        this.gpoId = gpoId;
    }

    /**
     * Obtiene el nombre.
     * @return Cadena con el nombre.
     */
    public String getGpoNombre() {
        return gpoNombre;
    }

    /**
     * Establece el nombre.
     * @param gpoNombre Cadena con el nombre.
     */
    public void setGpoNombre(String gpoNombre) {
        this.gpoNombre = gpoNombre;
    }

    /**
     * Obtiene el código DANE.
     * @return Cadena con el código DANE.
     */
    public String getGpoCodigoDane() {
        return gpoCodigoDane;
    }

    /**
     * Establece el Código DANE.
     * @param gpoCodigoDane Cadena con el código DANE
     */
    public void setGpoCodigoDane(String gpoCodigoDane) {
        this.gpoCodigoDane = gpoCodigoDane;
    }

    /**
     * Obtiene el Tipo.
     * @return Cadena con el tipo.
     */
    public String getGpoTipo() {
        return gpoTipo;
    }

    /**
     * Establece el tipo.
     * @param gpoTipo Cadena con el Tipo.
     */
    public void setGpoTipo(String gpoTipo) {
        this.gpoTipo = gpoTipo;
    }

    /**
     * Obtiene el código del tipo de dirección
     * @return Cadena con el código del tipo de dirección.
     */
    public String getGpoCodTipoDireccion() {
        return gpoCodTipoDireccion;
    }

    /**
     * Establece el código del tipo de dirección
     * @param gpoCodTipoDireccion Cadena con el código del tipo de dirección.
     */
    public void setGpoCodTipoDireccion(String gpoCodTipoDireccion) {
        this.gpoCodTipoDireccion = gpoCodTipoDireccion;
    }

    /**
     * Obtiene el Multiorigen.
     * @return Cadena con el Multiorigen.
     */
    public String getGpoMultiorigen() {
        return gpoMultiorigen;
    }

    /**
     * Establece el valor del Multiorigen.
     * @param gpoMultiorigen Cadena con el valor de Multiorigen.
     */
    public void setGpoMultiorigen(String gpoMultiorigen) {
        this.gpoMultiorigen = gpoMultiorigen;
    }

    /**
     * Obtiene el Código.
     * @return Cadena con el código.
     */
    public String getGpoCodigo() {
        return gpoCodigo;
    }

    /**
     * Establece el código.
     * @param gpoCodigo 
     */
    public void setGpoCodigo(String gpoCodigo) {
        this.gpoCodigo = gpoCodigo;
    }

    /**
     * Obtiene la fecha de creación.
     * @return Fecha de creación.
     */
    public Date getGpoFechaCreacion() {
        return gpoFechaCreacion;
    }

    /**
     * Establece la fecha de creación
     * @param gpoFechaCreacion Fecha de creación.
     */
    public void setGpoFechaCreacion(Date gpoFechaCreacion) {
        this.gpoFechaCreacion = gpoFechaCreacion;
    }

    /**
     * Obtiene el usuario que realiza la creación.
     * @return Cadena con el usuario que realiza la creación.
     */
    public String getGpoUsuarioCreacion() {
        return gpoUsuarioCreacion;
    }

    /**
     * Establece el usuario de creación.
     * @param gpoUsuarioCreacion Cadena con el usuario que realiza la creación.
     */
    public void setGpoUsuarioCreacion(String gpoUsuarioCreacion) {
        this.gpoUsuarioCreacion = gpoUsuarioCreacion;
    }

    /**
     * Obtiene la fecha de modificación.
     * @return Fecha de modificación.
     */
    public Date getGpoFechaModificacion() {
        return gpoFechaModificacion;
    }

    /**
     * Establece la fecha de modificación
     * @param gpoFechaModificacion Fecha de modificación.
     */
    public void setGpoFechaModificacion(Date gpoFechaModificacion) {
        this.gpoFechaModificacion = gpoFechaModificacion;
    }

    /**
     * Obtiene el usuario que realiza la modificación.
     * @return Cadena con el usuario que realiza la modificación.
     */
    public String getGpoUsuarioModificacion() {
        return gpoUsuarioModificacion;
    }

    /**
     * Establece el usuario que realiza la modificación.
     * @param gpoUsuarioModificacion Cadena con el usuario que realiza la modificación.
     */
    public void setGpoUsuarioModificacion(String gpoUsuarioModificacion) {
        this.gpoUsuarioModificacion = gpoUsuarioModificacion;
    }

    /**
     * Obtiene la lista de geográfico político.
     * @return Lista de objetos GeograficoPolitico
     */
    public List<GeograficoPolitico> getGeograficoPoliticoList() {
        return geograficoPoliticoList;
    }

    /**
     * Establece la lista de geográfico político.
     * @param geograficoPoliticoList Lista de objetos GeograficoPolitico
     */
    public void setGeograficoPoliticoList(List<GeograficoPolitico> geograficoPoliticoList) {
        this.geograficoPoliticoList = geograficoPoliticoList;
    }

    /**
     * Obtiene el GeograficoPolitico
     * @return Objeto GeograficoPolitico.
     */
    public GeograficoPolitico getGeograficoPolitico() {
        return geograficoPolitico;
    }

    /**
     * Establece el GeograficoPolitico
     * @param geograficoPolitico Objeto GeograficoPolitico
     */
    public void setGeograficoPolitico(GeograficoPolitico geograficoPolitico) {
        this.geograficoPolitico = geograficoPolitico;
    }

    /**
     * Obtiene la Lista de Geográfico Político
     * @return Lista de objetos GeograficoPolitico
     */
    public List<GeograficoPolitico> getGeograficoPoliticoList1() {
        return geograficoPoliticoList1;
    }

    /**
     * Establece la Lista de Geográfico Político
     * @param geograficoPoliticoList1 Lista de objetos GeograficoPolitico
     */
    public void setGeograficoPoliticoList1(List<GeograficoPolitico> geograficoPoliticoList1) {
        this.geograficoPoliticoList1 = geograficoPoliticoList1;
    }

    /**
     * Obtiene información para auditoría
     * @return Cadena con información para auditoría.
     */
    public String auditoria() {
        String auditoria = "GeograficoPolitico:" + "gpoId=" + gpoId + ", gpoNombre=" + gpoNombre
                + ", gpoCodigoDane=" + gpoCodigoDane + ", gpoTipo=" + gpoTipo
                + ", gpoMultiorigen=" + gpoMultiorigen
                + ", gpoCodTipoDireccion=" + gpoCodTipoDireccion
                + ", gpoCodigo=" + gpoCodigo + ", gpoFechaCreacion=" + gpoFechaCreacion
                + ", gpoUsuarioCreacion=" + gpoUsuarioCreacion
                + ", gpoFechaModificacion=" + gpoFechaModificacion
                + ", gpoUsuarioModificacion=" + gpoUsuarioModificacion;
        if (geograficoPoliticoList != null) {
            auditoria += ", geograficoPoliticoList con " + geograficoPoliticoList.size() + "elementos,";
        }
        if (geograficoPolitico != null) {
            auditoria += ", geograficoPolitico=" + geograficoPolitico.getGpoId();
        }
        if (geograficoPoliticoList1 != null) {
            auditoria += ", geograficoPoliticoList1 con " + geograficoPoliticoList1.size() + " elementos,";
        }
        return auditoria;
    }

    /**
     * Sobre Escritura del método toString()
     * @return Cadena con la información de la auditoría.
     */
    @Override
    public String toString() {
        String resultado = "GeograficoPolitico:" + "gpoId=" + gpoId + ", gpoNombre=" + gpoNombre
                + ", gpoCodigoDane=" + gpoCodigoDane + ", gpoTipo=" + gpoTipo
                + ", gpoMultiorigen=" + gpoMultiorigen
                + ", gpoCodTipoDireccion=" + gpoCodTipoDireccion
                + ", gpoCodigo=" + gpoCodigo + ", gpoFechaCreacion=" + gpoFechaCreacion
                + ", gpoUsuarioCreacion=" + gpoUsuarioCreacion
                + ", gpoFechaModificacion=" + gpoFechaModificacion
                + ", gpoUsuarioModificacion=" + gpoUsuarioModificacion;
        if (geograficoPoliticoList != null) {
            resultado += ", geograficoPoliticoList con " + geograficoPoliticoList.size() + "elementos,";
        }
        if (geograficoPolitico != null) {
            resultado += ", geograficoPolitico=" + geograficoPolitico.getGpoId();
        }
        if (geograficoPoliticoList1 != null) {
            resultado += ", geograficoPoliticoList1 con " + geograficoPoliticoList1.size() + " elementos,";
        }
        return resultado;
    }
}
