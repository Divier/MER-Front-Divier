/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.direcciones;

import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.mbeans.components.LazyDataTableModel;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author jrodriguez
 */

@SessionScoped
@ManagedBean(name = "geograficoPoliticoModel")
public class GeograficoPoliticoModel extends LazyDataTableModel<GeograficoPoliticoMgl> implements Serializable {

    private String message;

    public GeograficoPoliticoModel() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
