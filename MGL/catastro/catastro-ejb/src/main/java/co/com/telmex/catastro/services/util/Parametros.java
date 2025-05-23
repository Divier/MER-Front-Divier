package co.com.telmex.catastro.services.util;

/**
 * Clase Parametros
 *
 * @author 	Deiver Rovira.
 * @version	1.0
 */
public class Parametros {

    private String acronimo;
    private String valor;
    private String descripcion;

    /**
     * 
     * @return
     */
    public String getAcronimo() {
        return acronimo;
    }

    /**
     * 
     * @param acronimo
     */
    public void setAcronimo(String acronimo) {
        this.acronimo = acronimo;
    }

    /**
     * 
     * @return
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * 
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * 
     * @return
     */
    public String getValor() {
        return valor;
    }

    /**
     * 
     * @param valor
     */
    public void setValor(String valor) {
        this.valor = valor;
    }
}
