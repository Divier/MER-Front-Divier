package co.com.telmex.catastro.data;

import java.io.Serializable;

/**
 * Clase Privilegios
 * Implementa Serialización.
 *
 * @author 	Ana María
 * @version	1.0
 */
public class Privilegios implements Serializable {

    private static final long serialVersionUID = 1L;
    private String atrNombre = null;
    private String atrVisible = null;
    private String atrEstilo = null;

    /**
     * Obtiene el estilo.
     * @return Cadena con el estilo.
     */
    public String getAtrEstilo() {
        return atrEstilo;
    }

    /**
     * Establece el estilo.
     * @param atrEstilo Cadena con el estilo.
     */
    public void setAtrEstilo(String atrEstilo) {
        this.atrEstilo = atrEstilo;
    }

    /**
     * Obtiene el nombre del estilo.
     * @return Cadena con el nombre del estilo.
     */
    public String getAtrNombre() {
        return atrNombre;
    }

    /**
     * Establece el nombre del estilo.
     * @param atrNombre Cadena con el nombre del estilo.
     */
    public void setAtrNombre(String atrNombre) {
        this.atrNombre = atrNombre;
    }

    /**
     * Obtiene si es visible.
     * @return Cadena con el estado de visibilidad.
     */
    public String getAtrVisible() {
        return atrVisible;
    }

    /**
     * Establece si es visible.
     * @param atrVisible Cadena con el estado de visibilidad. 
     */
    public void setAtrVisible(String atrVisible) {
        this.atrVisible = atrVisible;
    }
}
