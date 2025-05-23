/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Dto para recibir datos de SP de tabla TEC_PREFICHA_XLS_NEW
 *
 * @author Johan Gomez
 * @version 1.1, 2023/07/18 Rev gomezjoj
 */
@XmlRootElement
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConsultaPrefichaXlsNewDto {
    @XmlElement
    private String prefichaXlsId;
    @XmlElement
    private String trazaId;
    @XmlElement
    private String direccion;
    @XmlElement
    private String codigoDane;
    @XmlElement
    private String barrio;        
    @XmlElement
    private String fechaCreacion;
    @XmlElement
    private String usuaCreacion;
    @XmlElement
    private String estadoRegistro;
    @XmlElement
    private String fechaEdicion;
    @XmlElement
    private String usuaEdicion;
    
    
}
