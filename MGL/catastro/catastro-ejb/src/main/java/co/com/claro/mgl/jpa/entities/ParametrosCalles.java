/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Parzifal de León
 */
@Entity
@Table(name = "MGL_PARAMETROS_CALLES", schema = "MGL_SCHEME")
@XmlRootElement
public class ParametrosCalles implements Serializable, Cloneable{
    
    private static final Logger LOGGER = LogManager.getLogger(ParametrosCalles.class);
    @Id
    @Column(name = "PARAMETROS_CALLES_ID", nullable = false)
    private String idParametro;
    @Column(name="DESCRIPCION")
    private String descripcion;
    @Column(name="ID_TIPO")
    private String idTipo;
    
    @Transient
    private boolean selected = false;

    public String getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(String idParametro) {
        this.idParametro = idParametro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(String idTipo) {
        this.idTipo = idTipo;
    }   

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    @Override
    public ParametrosCalles clone() throws CloneNotSupportedException {
        ParametrosCalles obj = null;
        try {
            obj = (ParametrosCalles) super.clone();
        } catch (CloneNotSupportedException ex) {
			String msg = "Se produjo un error al momento de ejecutar el método '"+
			ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			LOGGER.error(msg);
            throw ex;
        }

        return obj;
    }
  
}
