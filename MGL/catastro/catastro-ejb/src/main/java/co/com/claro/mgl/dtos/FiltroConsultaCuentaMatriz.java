/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class FiltroConsultaCuentaMatriz {
    long numRegistros=0;
    List<CmtCuentaMatrizMgl> listaCm;

    public long getNumRegistros() {
        return numRegistros;
    }

    public void setNumRegistros(long numRegistros) {
        this.numRegistros = numRegistros;
    }

    public List<CmtCuentaMatrizMgl> getListaCm() {
        return listaCm;
    }

    public void setListaCm(List<CmtCuentaMatrizMgl> listaCm) {
        this.listaCm = listaCm;
    }
    
    
}
