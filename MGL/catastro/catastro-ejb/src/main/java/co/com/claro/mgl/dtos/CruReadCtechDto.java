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
 * DTO para representar resultado de consulta a PRC_CRU_READ_CTECH
 *
 * @author Johan Gomez
 * @version 1.1, 2024/12/09 Rev gomezjoj
 */
@XmlRootElement
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CruReadCtechDto {
    
    @XmlElement
    private Integer sitesapId;
    @XmlElement
    private String sitio;
    @XmlElement
    private String daneMunicipio;
    @XmlElement
    private String centroPoblado;
    @XmlElement
    private String ubicacionTecnica;
    @XmlElement
    private String daneCp;
    @XmlElement
    private String idSitio;
    @XmlElement
    private Integer ccmm;
    @XmlElement
    private String tipoDeSitio;
    @XmlElement
    private String disponibilidad;
    @XmlElement
    private Date fechaCreacion;
    @XmlElement
    private String usuarioCreacion;
    @XmlElement
    private Date fechaEdicion;
    @XmlElement
    private String usuarioEdicion;
    @XmlElement
    private Integer estadoRegistro;
    
       
}
