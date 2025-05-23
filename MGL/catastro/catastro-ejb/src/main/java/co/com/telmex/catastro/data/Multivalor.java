package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Clase Multivalor
 * Implementa Serialización.
 *
 * @author 	Nataly Orozco Torres
 * @version	1.0
 */
public class Multivalor implements Serializable {

    private static final long serialVersionUID = 1L;
    //TODO validar porque esta clase tien 2 ID...
    private BigDecimal id = null;
    private BigDecimal mulId;
    private String mulValor;
    private String descripcion;
    private GrupoMultivalor grupoMultivalor;

    /**
     * Constructor.
     */
    public Multivalor() {
    }

    /**
     * Constructor con parámetros.
     * @param mulId
     */
    public Multivalor(BigDecimal mulId) {
        this.mulId = mulId;
    }

    /**
     * Obtiene el valor del Id.
     * @return Entero con el valor del Id
     */
    public BigDecimal getId() {
        return id;
    }

    /**
     * Establece el Id.
     * @param id Entero con el valor del Id.
     */
    public void setId(BigDecimal id) {
        this.id = id;
    }

    /**
     * Obtiene el valor MultiId.
     * @return Entero con el Valor MultiId.
     */
    public BigDecimal getMulId() {
        return mulId;
    }

    /**
     * Establece el valor MultiId.
     * @param mulId Entero con el valor MultiId.
     */
    public void setMulId(BigDecimal mulId) {
        this.mulId = mulId;
    }

    /**
     * Obtiene el valor.
     * @return Cadena con el valor.
     */
    public String getMulValor() {
        return mulValor;
    }

    /**
     * Establece el valor.
     * @param mulValor Cadena con el valor.
     */
    public void setMulValor(String mulValor) {
        this.mulValor = mulValor;
    }

    /**
     * Obtiene la descripción
     * @return Cadena con la descripción.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción
     * @param descripcion Cadena con la descripción.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el Grupo Multivalor
     * @return Objeto GrupoMultivalor con la información.
     */
    public GrupoMultivalor getGrupoMultivalor() {
        return grupoMultivalor;
    }

    /**
     * Establece le grupoMultivalor.
     * @param grupoMultivalor Objeto grupoMultivalor con la información.
     */
    public void setGrupoMultivalor(GrupoMultivalor grupoMultivalor) {
        this.grupoMultivalor = grupoMultivalor;
    }

    /**
     * Sobre Escritura del método hashCode()
     * @return Hash con el valor del MulId o 0 si es null.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mulId != null ? mulId.hashCode() : 0);
        return hash;
    }

    /**
     * Sobre Escritura del método equals()
     * @param object
     * @return Verdadero si existe la instancia, False Si no existe o es null.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Multivalor)) {
            return false;
        }
        Multivalor other = (Multivalor) object;
        if ((this.mulId == null && other.mulId != null) || (this.mulId != null && !this.mulId.equals(other.mulId))) {
            return false;
        }
        return true;
    }

    /**
     * Obtiene la información para auditoría.
     * @return Cadena con la información de la auditoría.
     */
    public String auditoria() {
        return "Multivalor:" + "id=" + id + ", mulId=" + mulId
                + ", mulValor=" + mulValor + ", descripcion=" + descripcion
                + ", grupoMultivalor=" + grupoMultivalor + '.';
    }

    /**
     * Sobre Escritura del método toString()
     * @return Cadena con la información de la auditoría.
     */
    @Override
    public String toString() {
        return "Multivalor:" + "id=" + id + ", mulId=" + mulId
                + ", mulValor=" + mulValor + ", descripcion=" + descripcion
                + ", grupoMultivalor=" + grupoMultivalor + '.';
    }
}