/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
/** 
 * Representa los parámetros recibidos de WebService
 *
 * @author gomezjoj
 * @version 19/07/2023
 */
@XmlRootElement
@Getter
@Setter
@AllArgsConstructor
public class AddressGeoDirDataDto implements Serializable {
    @XmlElement
    private String identificador = null;
    @XmlElement
    private String dirtrad = null;
    @XmlElement
    private String bartrad = null;
    @XmlElement
    private String coddir = null;
    @XmlElement
    private String codencont = null;
    @XmlElement
    private String fuente = null;
    @XmlElement
    private String diralterna = null;
    @XmlElement
    private String ambigua = null;
    @XmlElement
    private String valagreg = null;
    @XmlElement
    private String manzana = null;
    @XmlElement
    private String barrio = null;
    @XmlElement
    private String localidad = null;
    @XmlElement
    private String nivsocio = null;
    @XmlElement
    private String cx = null;
    @XmlElement
    private String cy = null;
    @XmlElement
    private String nodo1 = null;
    @XmlElement
    private String nodo2 = null;
    @XmlElement
    private String nodo3 = null;
    @XmlElement
    private String estrato = null;
    @XmlElement
    private String acteconomica = null;
    @XmlElement
    private String nodoDth = null;
    @XmlElement
    private String nodoMovil = null;
    @XmlElement
    private String nodoFtth = null;
    @XmlElement
    private String nodoWifi = null;
    @XmlElement
    private String geoZonaUnifilar = null;
    @XmlElement
    private String geoZonaGponDiseniado = null;
    @XmlElement
    private String geoZonaMicroOndas = null;
    @XmlElement
    private String geoZona3G = null;
    @XmlElement
    private String geoZona4G = null;
    @XmlElement
    private String geoZonaCoberturaCavs = null;
    @XmlElement
    private String geoZonaCoberturaUltimaMilla = null;
    @XmlElement
    private String geoZonaCurrier = null;
    @XmlElement
    private String geoZona5G = null;
    @XmlElement
    private String coddanemcpio = null;
    @XmlElement
    private String estado = null;
    @XmlElement
    private String mensaje = null;
    @XmlElement
    private String valplaca = null;
    @XmlElement
    private String zipCode = null;
    @XmlElement
    private String comentEconomicLevel = null;
    @XmlElement
    private String nodo4 = null;
}
