/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.manager;

/**
 *
 * @author Admin
 */
public class ConfiguracionColumnas {

    private String nomColumna;
    private String numCaracteres;
    private String tipoColumna;

    public ConfiguracionColumnas(String nomColumna, String numCaracteres, String tipoColumna) {
        this.nomColumna = nomColumna;
        this.numCaracteres = numCaracteres;
        this.tipoColumna = tipoColumna;
    }

    public String getNomColumna() {
        return nomColumna;
    }

    public void setNomColumna(String nomColumna) {
        this.nomColumna = nomColumna;
    }

    public String getNumCaracteres() {
        return numCaracteres;
    }

    public void setNumCaracteres(String numCaracteres) {
        this.numCaracteres = numCaracteres;
    }

    public String getTipoColumna() {
        return tipoColumna;
    }

    public void setTipoColumna(String tipoColumna) {
        this.tipoColumna = tipoColumna;
    }
}
