/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import co.com.claro.mgl.jpa.entities.cm.CmtTipoSolicitudMgl;
import java.util.List;

/**
 *
 * @author cardenaslb
 */
public class FiltroConsultaSlaSolicitudDto {
      long numRegistros = 0;

   List<CmtTipoSolicitudMgl> listaCmtTipoSolicitud;

    public long getNumRegistros() {
        return numRegistros;
    }

    public void setNumRegistros(long numRegistros) {
        this.numRegistros = numRegistros;
    }

    public List<CmtTipoSolicitudMgl> getListaCmtTipoSolicitud() {
        return listaCmtTipoSolicitud;
    }

    public void setListaCmtTipoSolicitud(List<CmtTipoSolicitudMgl> listaCmtTipoSolicitud) {
        this.listaCmtTipoSolicitud = listaCmtTipoSolicitud;
    }
   
   
}
