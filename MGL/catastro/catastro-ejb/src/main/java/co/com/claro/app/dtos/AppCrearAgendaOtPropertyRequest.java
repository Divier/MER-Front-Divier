package co.com.claro.app.dtos;

import java.io.Serializable;

/**
 * Objetivo: Clase que permite el mapeo clave valor
 * Descripcion: Mapeo con etiqueta de label y valor que se requieren para realizar agendamientos
 * 
 * @author Divier Casas
 * @versión 1.0
 */
public class AppCrearAgendaOtPropertyRequest implements Serializable {

    private String label;
    private String value;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
