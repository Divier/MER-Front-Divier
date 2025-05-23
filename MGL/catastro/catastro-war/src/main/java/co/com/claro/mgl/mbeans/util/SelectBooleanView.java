
package co.com.claro.mgl.mbeans.util;

import javax.faces.bean.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Jimmy García Ospina - Oracle
 * @use    Clase que permite asignar y obtener valores para elementos de tipo ToggleSwitch de PrimeFaces
 * @date   16-09-2021
 */
@Named
@RequestScoped
public class SelectBooleanView {
    
    private boolean bolAplicaHHPP=false;

    public boolean isBolAplicaHHPP() {
        return bolAplicaHHPP;
    }

    public void setBolAplicaHHPP(boolean bolAplicaHHPP) {
        this.bolAplicaHHPP = bolAplicaHHPP;
    }
    
    
    
}
