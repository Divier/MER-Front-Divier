/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import co.com.claro.mgl.jpa.entities.cm.CmtSubtipoOrdenVtTecMgl;
import java.util.List;

/**
 *
 * @author cardenaslb
 */
public class FiltroConsultaSubTipoOtTecDto {
    
    long numRegistros = 0;

    List<CmtSubtipoOrdenVtTecMgl> listaCmtSubtipoOrdenVtTecMgl;

    public long getNumRegistros() {
        return numRegistros;
    }

    public void setNumRegistros(long numRegistros) {
        this.numRegistros = numRegistros;
    }

    public List<CmtSubtipoOrdenVtTecMgl> getListaCmtSubtipoOrdenVtTecMgl() {
        return listaCmtSubtipoOrdenVtTecMgl;
    }

    public void setListaCmtSubtipoOrdenVtTecMgl(List<CmtSubtipoOrdenVtTecMgl> listaCmtSubtipoOrdenVtTecMgl) {
        this.listaCmtSubtipoOrdenVtTecMgl = listaCmtSubtipoOrdenVtTecMgl;
    }
   
    
   
   
}
