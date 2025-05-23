/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import java.util.List;

/**
 *
 * @author cardenaslb
 */
public class FiltroConsultaTipoBasicaDto {

    long numRegistros = 0;

   List<CmtTipoBasicaMgl> listaTablasTipoBasica;

    public long getNumRegistros() {
        return numRegistros;
    }

    public void setNumRegistros(long numRegistros) {
        this.numRegistros = numRegistros;
    }

    public List<CmtTipoBasicaMgl> getListaTablasTipoBasica() {
        return listaTablasTipoBasica;
    }

    public void setListaTablasTipoBasica(List<CmtTipoBasicaMgl> listaTablasTipoBasica) {
        this.listaTablasTipoBasica = listaTablasTipoBasica;
    }
   
   
}
