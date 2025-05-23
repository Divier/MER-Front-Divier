/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm.componente;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class Horas implements Serializable {

    private static final long serialVersionUID = 7526472295622776147L;
    private final int indice;
    private final String hora;
    private final String valor;

    public int getIndice() {
        return indice;
    }

    public String getHora() {
        return hora;
    }

    public String getValor() {
        return valor;
    }

    public Horas(int indice, String hora, String valor) {
        this.indice = indice;
        this.hora = hora;
        this.valor = valor;
    }
}
