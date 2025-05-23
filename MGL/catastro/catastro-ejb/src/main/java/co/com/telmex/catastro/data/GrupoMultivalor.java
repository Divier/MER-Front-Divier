package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Clase GrupoMultivalor
 * Implementa Serialización.
 *
 * @author 	Deiver Rovira
 * @version	1.0
 */
public class GrupoMultivalor implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal gmuId;
    private String gmuNombre;
    private List<Multivalor> multivalor;

    /**
     * Constructor.
     */
    public GrupoMultivalor() {
    }

    /**
     * Constructor con parámetros.
     * @param gmuId
     */
    public GrupoMultivalor(BigDecimal gmuId) {
        this.gmuId = gmuId;
    }

    /**
     * Obtiene el Id.
     * @return Entero con el Id.
     */
    public BigDecimal getGmuId() {
        return gmuId;
    }

    /**
     * Establece el Id.
     * @param gmuId Entero con el Id.
     */
    public void setGmuId(BigDecimal gmuId) {
        this.gmuId = gmuId;
    }

    /**
     * Obtiene el nombre.
     * @return Cadena con el nombre.
     */
    public String getGmuNombre() {
        return gmuNombre;
    }

    /**
     * Establece el nombre.
     * @param gmuNombre Cadena con el nombre.
     */
    public void setGmuNombre(String gmuNombre) {
        this.gmuNombre = gmuNombre;
    }

    /**
     * Obtiene la lista de multivalor.
     * @return Lista de objeto Multivalor.
     */
    public List<Multivalor> getMultivalor() {
        return multivalor;
    }

    /**
     * Establece la lista multivalor.
     * @param multivalor Lista de objeto Multivalor.
     */
    public void setMultivalor(List<Multivalor> multivalor) {
        this.multivalor = multivalor;
    }

    /**
     * Sobre Escritura del método equals()
     * @param object Objeto a comparar.
     * @return Verdadero si el objeto tiene instancia declarada o falso si no existe o es null.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GrupoMultivalor)) {
            return false;
        }
        GrupoMultivalor other = (GrupoMultivalor) object;
        if ((this.gmuId == null && other.gmuId != null) || (this.gmuId != null && !this.gmuId.equals(other.gmuId))) {
            return false;
        }
        return true;
    }

    /**
     * Obtiene información para auditoría
     * @return Cadena con información para auditoría.
     */
    public String auditoria() {
        return "GrupoMultivalor:" + "gmuId=" + gmuId + ", gmuNombre=" + gmuNombre + '.';
    }

    /**
     * Sobre Escritura del método toString()
     * @return Cadena con la información de la auditoría.
     */
    @Override
    public String toString() {
        return "GrupoMultivalor:" + "gmuId=" + gmuId + ", gmuNombre=" + gmuNombre + '.';
    }

    /**
     * Sobre Escritura del método HashCode()
     * @return Entero con la validación de null.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gmuId != null ? gmuId.hashCode() : 0);
        return hash;
    }
}
