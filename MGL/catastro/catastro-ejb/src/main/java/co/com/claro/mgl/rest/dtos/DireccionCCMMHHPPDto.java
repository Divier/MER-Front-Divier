/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Objetivo: Clase que permite mapear la informacion de direcciones
 * Descripcion: Contiene la informacion de direccion sociada a una orden CCMM o HHPP
 *
 * @author Divier Casas
 * @versión 1.0
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DireccionCCMMHHPPDto {
 
    private String direccionId;
    private String subDireccionId;
    private String cuentaMatrizId;
    private String tipoCCMMHHPP;
}
