package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Clase DireccionAlterna
 * Implementa Serialización.
 *
 * @author 	Deiver Rovira
 * @version	1.0
 */
public class DireccionAlterna implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal diaId;
    private String diaDireccionIgac;
    private Date diaFechaCreacion;
    private String diaUsuarioCreacion;
    private Date diaFechaModificacion;
    private String diaUsuarioModificacion;
    private Direccion direccion;

    /**
     * Constructor
     */
    public DireccionAlterna() {
    }

    /**
     * Constructor con parámetros.
     * @param diaId
     */
    public DireccionAlterna(BigDecimal diaId) {
        this.diaId = diaId;
    }

    /**
     * Obtiene el Id.
     * @return Entero con el Id.
     */
    public BigDecimal getDiaId() {
        return diaId;
    }

    /**
     * Establece el Id.
     * @param diaId Entero con el Id.
     */
    public void setDiaId(BigDecimal diaId) {
        this.diaId = diaId;
    }

    /**
     * Obtiene la dirección IGAC.
     * @return Cadena con la dirección IGAC.
     */
    public String getDiaDireccionIgac() {
        return diaDireccionIgac;
    }

    /**
     * Establece la dirección IGAC.
     * @param diaDireccionIgac Cadena con la dirección IGAC.
     */
    public void setDiaDireccionIgac(String diaDireccionIgac) {
        this.diaDireccionIgac = diaDireccionIgac;
    }

    /**
     * Obtiene la fecha de creación.
     * @return Fecha de creación.
     */
    public Date getDiaFechaCreacion() {
        return diaFechaCreacion;
    }

    /**
     * Establece la fecha de creación.
     * @param diaFechaCreacion Fecha de creación.
     */
    public void setDiaFechaCreacion(Date diaFechaCreacion) {
        this.diaFechaCreacion = diaFechaCreacion;
    }

    /**
     * Obtiene el usuario que realiza la creación.
     * @return Cadena con el usuario que realiza la creación.
     */
    public String getDiaUsuarioCreacion() {
        return diaUsuarioCreacion;
    }

    /**
     * Establece el usuario que realiza la creación.
     * @param diaUsuarioCreacion Cadena con el usuario que realiza la creación.
     */
    public void setDiaUsuarioCreacion(String diaUsuarioCreacion) {
        this.diaUsuarioCreacion = diaUsuarioCreacion;
    }

    /**
     * Obtiene la fecha de modificación.
     * @return Fecha de modificación.
     */
    public Date getDiaFechaModificacion() {
        return diaFechaModificacion;
    }

    /**
     * Establece la fecha de modificación.
     * @param diaFechaModificacion Fecha de modificación.
     */
    public void setDiaFechaModificacion(Date diaFechaModificacion) {
        this.diaFechaModificacion = diaFechaModificacion;
    }

    /**
     * Obtiene el usuario que realiza la modificación.
     * @return Cadena con el usuario que realiza la modificación.
     */
    public String getDiaUsuarioModificacion() {
        return diaUsuarioModificacion;
    }

    /**
     * Establece el usuario que realiza la modificación.
     * @param diaUsuarioModificacion Cadena con el usuario que realiza la modificación.
     */
    public void setDiaUsuarioModificacion(String diaUsuarioModificacion) {
        this.diaUsuarioModificacion = diaUsuarioModificacion;
    }

    /**
     * Obtiene la dirección.
     * @return Objeto Direccion con la información solicitada.
     */
    public Direccion getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección.
     * @param direccion Objeto Direccion con la información solicitada.
     */
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene información para auditoría
     * @return Cadena con información para auditoría.
     */
    public String auditoria() {
        return "DireccionAlterna:" + "diaId=" + diaId
                + ", diaDireccionIgac=" + diaDireccionIgac
                + ", diaUsuarioCreacion=" + diaUsuarioCreacion
                + ", direccion=" + direccion + '.';
    }

    /**
     * Sobre Escritura del método toString()
     * @return Cadena con la información de la auditoría.
     */
    @Override
    public String toString() {
        return "DireccionAlterna:" + "diaId=" + diaId
                + ", diaDireccionIgac=" + diaDireccionIgac
                + ", diaUsuarioCreacion=" + diaUsuarioCreacion
                + ", direccion=" + direccion + '.';
    }

    /**
     * Sobre Escritura del método HashCode()
     * @return Entero con la validación de null.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (diaId != null ? diaId.hashCode() : 0);
        return hash;
    }

    /**
     * Sobre Escritura del método equals()
     * @param object Objeto a comparar.
     * @return Verdadero si el objeto tiene instancia declarada o falso si no existe o es null.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DireccionAlterna)) {
            return false;
        }
        DireccionAlterna other = (DireccionAlterna) object;
        if ((this.diaId == null && other.diaId != null) || (this.diaId != null && !this.diaId.equals(other.diaId))) {
            return false;
        }
        return true;
    }
}
