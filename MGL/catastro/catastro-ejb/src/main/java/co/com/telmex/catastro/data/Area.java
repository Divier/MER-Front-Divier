package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Clase Area
 * Representa un espacio con nombre y id.
 * implementa Serialización
 *
 * @author 	Jose Luis Caicedo G.
 * @version	1.0 
 */
public class Area implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal areId;
    private String areNombre;
    private String areUsuarioCreacion;
    private String areUsuarioModificacion;
    private Date areFechaModificacion;
    private Date areFechaCreacion;

    /**
     * 
     */
    public Area() {
    }

    /**
     * 
     * @param areId
     */
    public Area(BigDecimal areId) {
        this.areId = areId;
    }

    /**
     * 
     * @return
     */
    public BigDecimal getAreId() {
        return areId;
    }

    /**
     * 
     * @param areId
     */
    public void setAreId(BigDecimal areId) {
        this.areId = areId;
    }

    /**
     * 
     * @return
     */
    public String getAreNombre() {
        return areNombre;
    }

    /**
     * 
     * @param areNombre
     */
    public void setAreNombre(String areNombre) {
        this.areNombre = areNombre;
    }

    /**
     * 
     * @return
     */
    public String getAreUsuarioCreacion() {
        return areUsuarioCreacion;
    }

    /**
     * 
     * @param areUsuarioCreacion
     */
    public void setAreUsuarioCreacion(String areUsuarioCreacion) {
        this.areUsuarioCreacion = areUsuarioCreacion;
    }

    /**
     * 
     * @return
     */
    public String getAreUsuarioModificacion() {
        return areUsuarioModificacion;
    }

    /**
     * 
     * @param areUsuarioModificacion
     */
    public void setAreUsuarioModificacion(String areUsuarioModificacion) {
        this.areUsuarioModificacion = areUsuarioModificacion;
    }

    /**
     * 
     * @return
     */
    public Date getAreFechaModificacion() {
        return areFechaModificacion;
    }

    /**
     * 
     * @param areFechaModificacion
     */
    public void setAreFechaModificacion(Date areFechaModificacion) {
        this.areFechaModificacion = areFechaModificacion;
    }

    /**
     * 
     * @return
     */
    public Date getAreFechaCreacion() {
        return areFechaCreacion;
    }

    /**
     * 
     * @param areFechaCreacion
     */
    public void setAreFechaCreacion(Date areFechaCreacion) {
        this.areFechaCreacion = areFechaCreacion;
    }

    /**
     * 
     * @return
     */
    public String auditoria() {
        return "Area:" + "areId=" + areId + ", areNombre=" + areNombre + '.';
    }

    @Override
    public String toString() {
        return "Area:" + "areId=" + areId + ", areNombre=" + areNombre + '.';
    }
}
