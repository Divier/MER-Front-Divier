/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import co.com.claro.mgl.jpa.entities.cm.CmtInfoTecnicaMgl;
import java.util.List;

/**
 *
 * @author cardenaslb
 */
public class FiltroInformacionTecnicaDto {

    long numRegistros = 0;

    List<CmtInfoTecnicaMgl> listainfoTecnica;

    public long getNumRegistros() {
        return numRegistros;
    }

    public void setNumRegistros(long numRegistros) {
        this.numRegistros = numRegistros;
    }

    public List<CmtInfoTecnicaMgl> getListaTablasTipoBasica() {
        return listainfoTecnica;
    }

    public void setListaTablasTipoBasica(List<CmtInfoTecnicaMgl> listaTablasTipoBasica) {
        this.listainfoTecnica = listaTablasTipoBasica;
    }

}
