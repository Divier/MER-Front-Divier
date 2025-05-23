/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import java.util.List;

/**
 *
 * @author cardenaslb
 */
public class FiltroReporteSolicitudCMDto {

    int numRegistros = 0;

    List<CmtSolicitudCmMgl> listaSolicitudCM;
    List<CmtReporteGeneralDto> listaCmtReporteGeneralDto;
     List<CmtReporteDetalladoDto> listaCmtReporteDetalladoDto;

    public int getNumRegistros() {
        return numRegistros;
    }

    public void setNumRegistros(int numRegistros) {
        this.numRegistros = numRegistros;
    }

    public List<CmtSolicitudCmMgl> getListaSolicitudCM() {
        return listaSolicitudCM;
    }

    public void setListaSolicitudCM(List<CmtSolicitudCmMgl> listaSolicitudCM) {
        this.listaSolicitudCM = listaSolicitudCM;
    }

    public List<CmtReporteGeneralDto> getListaCmtReporteGeneralDto() {
        return listaCmtReporteGeneralDto;
    }

    public void setListaCmtReporteGeneralDto(List<CmtReporteGeneralDto> listaCmtReporteGeneralDto) {
        this.listaCmtReporteGeneralDto = listaCmtReporteGeneralDto;
    }

    public List<CmtReporteDetalladoDto> getListaCmtReporteDetalladoDto() {
        return listaCmtReporteDetalladoDto;
    }

    public void setListaCmtReporteDetalladoDto(List<CmtReporteDetalladoDto> listaCmtReporteDetalladoDto) {
        this.listaCmtReporteDetalladoDto = listaCmtReporteDetalladoDto;
    }

}
