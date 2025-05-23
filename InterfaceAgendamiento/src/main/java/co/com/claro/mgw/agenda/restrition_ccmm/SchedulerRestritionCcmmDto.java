/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgw.agenda.restrition_ccmm;


/**
 *
 * @author bocanegravm
 */
public class SchedulerRestritionCcmmDto {
    
    
    private String  dia;
    private String  tipoRestriccion;
    private String  horaMinutoInicial;
    private String  horaMinutoFinal;
    private String  descripcionRestricion;

    
    
    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }
    
    public String getTipoRestriccion() {
        return tipoRestriccion;
    }

    public void setTipoRestriccion(String tipoRestriccion) {
        this.tipoRestriccion = tipoRestriccion;
    }

    public String getHoraMinutoInicial() {
        return horaMinutoInicial;
    }

    public void setHoraMinutoInicial(String horaMinutoInicial) {
        this.horaMinutoInicial = horaMinutoInicial;
    }

    public String getHoraMinutoFinal() {
        return horaMinutoFinal;
    }

    public void setHoraMinutoFinal(String horaMinutoFinal) {
        this.horaMinutoFinal = horaMinutoFinal;
    }

    public String getDescripcionRestricion() {
        return descripcionRestricion;
    }

    public void setDescripcionRestricion(String descripcionRestricion) {
        this.descripcionRestricion = descripcionRestricion;
    }

}
