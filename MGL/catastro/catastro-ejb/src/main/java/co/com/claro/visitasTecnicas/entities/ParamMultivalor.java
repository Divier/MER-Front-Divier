package co.com.claro.visitasTecnicas.entities;

import java.io.Serializable;

/**
 * Clase ParamMultivalor
 * @author 	Diego Barrera
 * @version     version 1.2
 * @date        2013/09/12
 */
public class ParamMultivalor implements Serializable, Comparable<ParamMultivalor> {
    
    private String idParametro = "";
    private String descripcion = "";
    private String idTipo = "";
    
    
    /**
     * Realiza la comparaci&oacute;n a trav&eacute;s de la Descripci&oacute;n.
     * 
     * @param p Par&aacute;metro a comparar.
     * @return Resultado de la comparaci&oacute;n de las Descripciones.
     * @see java.lang.Comparable#compareTo(java.lang.Object) 
     */
    @Override
    public int compareTo(ParamMultivalor p) {
        if (getDescripcion() == null || p.getDescripcion() == null) {
            return 0;
        }
        return getDescripcion().compareTo(p.getDescripcion());
    }
    
    
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
    
    
}
