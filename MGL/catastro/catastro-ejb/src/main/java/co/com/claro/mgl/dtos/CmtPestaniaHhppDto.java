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
public class CmtPestaniaHhppDto {
    
    List<CmtPestaniaHhppDetalleDto> listaPestaniaHhppDet;
    long numPag;

  
    public List<CmtPestaniaHhppDetalleDto> getListaPestaniaHhppDet() {
        return listaPestaniaHhppDet;
    }

    public void setListaPestaniaHhppDet(List<CmtPestaniaHhppDetalleDto> listaPestaniaHhppDet) {
        this.listaPestaniaHhppDet = listaPestaniaHhppDet;
    }

    public long getNumPag() {
        return numPag;
    }

    public void setNumPag(long numPag) {
        this.numPag = numPag;
    }
    
}
