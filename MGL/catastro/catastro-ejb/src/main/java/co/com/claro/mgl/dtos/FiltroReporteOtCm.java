/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import java.util.List;

/**
 *
 * @author cardenaslb
 */
public class FiltroReporteOtCm {
    long numRegistros = 0;
    List<ReporteOtCMDto> listaCm;

    public long getNumRegistros() {
        return numRegistros;
    }

    public void setNumRegistros(long numRegistros) {
        this.numRegistros = numRegistros;
    }

    public List<ReporteOtCMDto> getListaCm() {
        return listaCm;
    }

    public void setListaCm(List<ReporteOtCMDto> listaCm) {
        this.listaCm = listaCm;
    }
    
    
    
    
    
    
}
