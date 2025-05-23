/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import co.com.claro.mgl.jpa.entities.cm.CmtDetalleFlujoMgl;
import java.util.List;

/**
 *
 * @author cardenaslb
 */
public class FiltroConsultaDetalleFlujoDto {
     long numRegistros = 0;

   List<CmtDetalleFlujoMgl> listaCmtEstadoxFlujoMgl;

    public long getNumRegistros() {
        return numRegistros;
    }

    public void setNumRegistros(long numRegistros) {
        this.numRegistros = numRegistros;
    }

    public List<CmtDetalleFlujoMgl> getListaCmtEstadoxFlujoMgl() {
        return listaCmtEstadoxFlujoMgl;
    }

    public void setListaCmtEstadoxFlujoMgl(List<CmtDetalleFlujoMgl> listaCmtEstadoxFlujoMgl) {
        this.listaCmtEstadoxFlujoMgl = listaCmtEstadoxFlujoMgl;
    }
   
   
}
