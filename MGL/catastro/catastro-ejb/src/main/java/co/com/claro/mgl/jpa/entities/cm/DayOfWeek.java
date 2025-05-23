/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

/**
 *
 * @author Admin
 */
public enum DayOfWeek {

    LUNES("Lunes"),
    MARTES("Martes"),
    MIERCOLES("Miercoles"),
    JUEVES("Jueves"),
    VIERNES("Viernes"),
    SABADO("Sabado"),
    DOMINGO("Domingo");
    private final String dia;

    DayOfWeek(String diaSemana) {
        this.dia = diaSemana;
    }

    public String getDia() {
        return this.dia;
    }
}
