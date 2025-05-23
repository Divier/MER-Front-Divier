/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import co.com.claro.mgl.jpa.entities.cm.CmtCoberturaEntregaMgl;
import java.util.List;

/**
 *
 * @author villamilc
 */
public class CmtFiltroCoberturasDto {
    long numRegistros = 0;

   List<CmtCoberturaEntregaMgl> listaCmtCoberturaEntregaMgl;

    public long getNumRegistros() {
        return numRegistros;
    }

    public void setNumRegistros(long numRegistros) {
        this.numRegistros = numRegistros;
    }

    public List<CmtCoberturaEntregaMgl> getListaCmtCoberturaEntregaMgl() {
        return listaCmtCoberturaEntregaMgl;
    }

    public void setListaCmtCoberturaEntregaMgl(List<CmtCoberturaEntregaMgl> listaCmtCoberturaEntregaMgl) {
        this.listaCmtCoberturaEntregaMgl = listaCmtCoberturaEntregaMgl;
    }
}
