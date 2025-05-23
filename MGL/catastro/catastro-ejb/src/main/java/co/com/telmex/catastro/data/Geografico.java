package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Clase Geografico
 * Implementa Serialización.
 *
 * @author 	Deiver Rovira
 * @version	1.0
 */
public class Geografico implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal geoId;
    private String geoNombre;
    private Date geoFechaCreacion;
    private String geoUsuarioCreacion;
    private Date geoFechaModificacion;
    private String geoUsuarioModificacion;
    private TipoGeografico tipoGeografico;
    private GeograficoPolitico geograficoPolitico;
    private List<Geografico> geograficoList;
    private Geografico geografico;

    /**
     * Constructor.
     */
    public Geografico() {
    }

    /**
     * Contructor con parámetros.
     * @param geoId
     */
    public Geografico(BigDecimal geoId) {
        this.geoId = geoId;
    }

    /**
     * Obtiene el Id.
     * @return Entero con el Id.
     */
    public BigDecimal getGeoId() {
        return geoId;
    }

    /**
     * Establece el Id.
     * @param geoId Entero con el Id.
     */
    public void setGeoId(BigDecimal geoId) {
        this.geoId = geoId;
    }

    /**
     * Obtiene el nombre.
     * @return Cadena con el nombre
     */
    public String getGeoNombre() {
        return geoNombre;
    }

    /**
     * Establece el nombre.
     * @param geoNombre Cadena con el nombre.
     */
    public void setGeoNombre(String geoNombre) {
        this.geoNombre = geoNombre;
    }

    /**
     * Obtiene la fecha de creación
     * @return Fecha de creación.
     */
    public Date getGeoFechaCreacion() {
        return geoFechaCreacion;
    }

    /**
     * Establece la fecha de creación.
     * @param geoFechaCreacion Fecha de creación.
     */
    public void setGeoFechaCreacion(Date geoFechaCreacion) {
        this.geoFechaCreacion = geoFechaCreacion;
    }

    /**
     * Obtiene el usuario que realiza la creación.
     * @return Cadena con el usuario que realiza la creación.
     */
    public String getGeoUsuarioCreacion() {
        return geoUsuarioCreacion;
    }

    /**
     * Establece el usuario que realiza la creación.
     * @param geoUsuarioCreacion Cadena con el usuario que realiza la creación.
     */
    public void setGeoUsuarioCreacion(String geoUsuarioCreacion) {
        this.geoUsuarioCreacion = geoUsuarioCreacion;
    }

    /**
     * Obtiene la fecha de modificación.
     * @return Fecha de modificación
     */
    public Date getGeoFechaModificacion() {
        return geoFechaModificacion;
    }

    /**
     * Establece la fecha de modificación.
     * @param geoFechaModificacion Fecha de modificación
     */
    public void setGeoFechaModificacion(Date geoFechaModificacion) {
        this.geoFechaModificacion = geoFechaModificacion;
    }

    /**
     * Obtiene el usuario que realiza la modificación.
     * @return Cadena con el usuario que realiza la modificación.
     */
    public String getGeoUsuarioModificacion() {
        return geoUsuarioModificacion;
    }

    /**
     * Establece el usuario que realiza modificación.
     * @param geoUsuarioModificacion Cadena con el usuario que realiza la modificación.
     */
    public void setGeoUsuarioModificacion(String geoUsuarioModificacion) {
        this.geoUsuarioModificacion = geoUsuarioModificacion;
    }

    /**
     * Obtiene el tipo Geográfico.
     * @return Objeto de TipoGeografico con la información.
     */
    public TipoGeografico getTipoGeografico() {
        return tipoGeografico;
    }

    /**
     * Establece el tipo Geográfico
     * @param tipoGeografico Objeto TipoGeografico con la información.
     */
    public void setTipoGeografico(TipoGeografico tipoGeografico) {
        this.tipoGeografico = tipoGeografico;
    }

    /**
     * Obtiene el geográfico político.
     * @return Objeto GeograficoPolitico con la información.
     */
    public GeograficoPolitico getGeograficoPolitico() {
        return geograficoPolitico;
    }

    /**
     * Establece el objeto geográfico político.
     * @param geograficoPolitico Objeto GeograficoPolitico con la información.
     */
    public void setGeograficoPolitico(GeograficoPolitico geograficoPolitico) {
        this.geograficoPolitico = geograficoPolitico;
    }

    /**
     * Obtiene una lista de geográfico.
     * @return Lista de objetos Geografico.
     */
    public List<Geografico> getGeograficoList() {
        return geograficoList;
    }

    /**
     * Establece la lista de geográficos.
     * @param geograficoList Lista de Objetos Geografico.
     */
    public void setGeograficoList(List<Geografico> geograficoList) {
        this.geograficoList = geograficoList;
    }

    /**
     * Obtiene el geográfico.
     * @return Objeto geográfico con la información.
     */
    public Geografico getGeografico() {
        return geografico;
    }

    /**
     * Establece el geográfico.
     * @param geografico Objeto geográfico con la información.
     */
    public void setGeografico(Geografico geografico) {
        this.geografico = geografico;
    }

    /**
     * Obtiene información para auditoría
     * @return Cadena con información para auditoría.
     */
    public String auditoria() {
        return "Geografico:" + "geoId=" + geoId + ", geoNombre=" + geoNombre
                + ", tipoGeografico=" + tipoGeografico
                + ", geograficoPolitico=" + geograficoPolitico
                + ", geograficoList=" + geograficoList
                + ", geografico=" + geografico + '.';
    }

    /**
     * Sobre Escritura del método toString()
     * @return Cadena con la información de la auditoría.
     */
    @Override
    public String toString() {
        return "Geografico:" + "geoId=" + geoId + ", geoNombre=" + geoNombre
                + ", tipoGeografico=" + tipoGeografico
                + ", geograficoPolitico=" + geograficoPolitico
                + ", geograficoList=" + geograficoList
                + ", geografico=" + geografico + '.';
    }
}
