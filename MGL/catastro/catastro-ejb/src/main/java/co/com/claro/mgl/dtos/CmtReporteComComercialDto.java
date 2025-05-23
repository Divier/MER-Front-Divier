/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import java.util.List;

/**
 *
 * @author cardenaslb
 */
public class CmtReporteComComercialDto {
     long numRegistros = 0;
    
    private List<CuentaMatrizCompComercialDto> listaCuentaMatrizCompComercialDto;

    public long getNumRegistros() {
        return numRegistros;
    }

    public void setNumRegistros(long numRegistros) {
        this.numRegistros = numRegistros;
    }

    public List<CuentaMatrizCompComercialDto> getListaCuentaMatrizCompComercialDto() {
        return listaCuentaMatrizCompComercialDto;
    }

    public void setListaCuentaMatrizCompComercialDto(List<CuentaMatrizCompComercialDto> listaCuentaMatrizCompComercialDto) {
        this.listaCuentaMatrizCompComercialDto = listaCuentaMatrizCompComercialDto;
    }

    
}
