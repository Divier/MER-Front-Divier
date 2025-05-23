/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import java.util.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para representar resultado inicial de consulta a TEC_PREFICHA_XLS_NEW
 *
 * @author Johan Gomez
 * @version 1.1, 2023/09/05 Rev gomezjoj
 */
@XmlRootElement
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConsultaPrefichaXlsNewAllDto {
    @XmlElement
    private Integer prefichaXlsId;
    @XmlElement
    private Integer trazaId;
    @XmlElement
    private Integer prefichaId;
    @XmlElement
    private String conjunto;
    @XmlElement
    private String via;
    @XmlElement
    private String placa;
    @XmlElement
    private Integer tecHabilitada;
    @XmlElement
    private Integer aptos;
    @XmlElement
    private Integer locales;
    @XmlElement
    private Integer oficinas;
    @XmlElement
    private Integer pisos;
    @XmlElement
    private String barrio;
    @XmlElement
    private String distribucion;
    @XmlElement
    private String observaciones;
    @XmlElement
    private String fase;
    @XmlElement
    private String clasificacion;
    @XmlElement
    private Date fechaCreacion;
    @XmlElement
    private String usuaCreacion;
    @XmlElement
    private Date fechaEdicion;
    @XmlElement
    private String usuaEdicion;
    @XmlElement
    private Integer estadoRegistro;
    @XmlElement
    private String tipoDireccion;
    @XmlElement
    private String nodo;
    @XmlElement
    private Integer registroValido;
    @XmlElement
    private String pestana;
    @XmlElement
    private String amp;
    @XmlElement
    private String direccion;
    @XmlElement
    private String codigoDane;
    
}
