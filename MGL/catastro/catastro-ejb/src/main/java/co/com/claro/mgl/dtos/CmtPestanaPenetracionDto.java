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
public class CmtPestanaPenetracionDto {

    long numRegistros = 0;
    
    private List<CmtPenetracionCMDto> listaPenetracionTecnologias;

    public long getNumRegistros() {
        return numRegistros;
    }

    public void setNumRegistros(long numRegistros) {
        this.numRegistros = numRegistros;
    }

    public List<CmtPenetracionCMDto> getListaPenetracionTecnologias() {
        return listaPenetracionTecnologias;
    }

    public void setListaPenetracionTecnologias(List<CmtPenetracionCMDto> listaPenetracionTecnologias) {
        this.listaPenetracionTecnologias = listaPenetracionTecnologias;
    }


   
    
    

}
