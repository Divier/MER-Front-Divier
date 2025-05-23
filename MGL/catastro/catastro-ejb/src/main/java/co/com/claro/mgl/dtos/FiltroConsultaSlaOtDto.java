/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import co.com.claro.mgl.jpa.entities.cm.CmtTipoOtMgl;
import java.util.List;

/**
 *
 * @author cardenaslb
 */
public class FiltroConsultaSlaOtDto {

    long numRegistros = 0;

    List<CmtTipoOtMgl> listaCmtTipoOtMgl;

    public long getNumRegistros() {
        return numRegistros;
    }

    public void setNumRegistros(long numRegistros) {
        this.numRegistros = numRegistros;
    }

    public List<CmtTipoOtMgl> getListaCmtTipoOtMgl() {
        return listaCmtTipoOtMgl;
    }

    public void setListaCmtTipoOtMgl(List<CmtTipoOtMgl> listaCmtTipoOtMgl) {
        this.listaCmtTipoOtMgl = listaCmtTipoOtMgl;
    }

}
