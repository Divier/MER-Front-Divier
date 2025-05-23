/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO que representa el response al procedimiento para realizar CRUD a tabla CMT_COM_TECHNICALSITESAP.
 *
 * @author Johan Gomez
 * @version 1.0, 2024/12/10
 */
@Getter
@Setter
@NoArgsConstructor
public class CmtTecSiteSapRespDto {
 
    private Integer codigo;

    private String resultado;
    
    private List<CruReadCtechDto> listaReadCtechDto;
}
