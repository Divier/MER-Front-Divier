package co.com.claro.mer.dtos.sp.cursors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase que contiene los atributos de OpcionesCarguesDto
 * @version 1.0, 01/09/2021
 * @author Manuel Hernández
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpcionesCarguesDto {

    private String nombreCargue;
    private int acciones;
}
