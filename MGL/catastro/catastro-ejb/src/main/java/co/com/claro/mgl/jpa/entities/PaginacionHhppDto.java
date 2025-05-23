/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import co.com.claro.mgl.dtos.CmtPestaniaHhppDetalleDto;
import java.util.List;

/**
 *
 * @author cardenaslb
 */
public class PaginacionHhppDto {
     private long numPaginas;
    List<CmtPestaniaHhppDetalleDto> listaPestaniaHhppDetalleDto;

    public long getNumPaginas() {
        return numPaginas;
    }

    public void setNumPaginas(long numPaginas) {
        this.numPaginas = numPaginas;
    }

    public List<CmtPestaniaHhppDetalleDto> getListaPestaniaHhppDetalleDto() {
        return listaPestaniaHhppDetalleDto;
    }

    public void setListaPestaniaHhppDetalleDto(List<CmtPestaniaHhppDetalleDto> listaPestaniaHhppDetalleDto) {
        this.listaPestaniaHhppDetalleDto = listaPestaniaHhppDetalleDto;
    }


     
    
    
}
