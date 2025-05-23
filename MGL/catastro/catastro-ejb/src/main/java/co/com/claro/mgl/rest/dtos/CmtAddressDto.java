/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.rest.dtos;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author valbuenayf
 */
@XmlRootElement(name = "address")
@XmlAccessorType(XmlAccessType.FIELD)
public class CmtAddressDto {

    @XmlElement
    private String addressId;
    @XmlElement
    private CmtCityDto city;
    @XmlElement
    private String igacAddress;
    @XmlElement
    private Integer adressReliability;
    @XmlElement
    private String latitudeCoordinate;
    @XmlElement
    private String lengthCoordinate;
    @XmlElement
    private CmtDireccionTabuladaDto splitAddres;
    @XmlElement
    private List<CmtCoverDto> listCover;
    @XmlElement
    private List<String> listCarrierCover;
    @XmlElement
    private String stratum;
    @XmlElement
    private String mensajeDireccionAntigua;
    @XmlElement
    private List<CmtHhppDto> listHhpps;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public CmtCityDto getCity() {
        return city;
    }

    public void setCity(CmtCityDto city) {
        this.city = city;
    }

    public String getIgacAddress() {
        return igacAddress;
    }

    public void setIgacAddress(String igacAddress) {
        this.igacAddress = igacAddress;
    }

    public Integer getAdressReliability() {
        return adressReliability;
    }

    public void setAdressReliability(Integer adressReliability) {
        this.adressReliability = adressReliability;
    }

    public String getLatitudeCoordinate() {
        return latitudeCoordinate;
    }

    public void setLatitudeCoordinate(String latitudeCoordinate) {
        this.latitudeCoordinate = latitudeCoordinate;
    }

    public String getLengthCoordinate() {
        return lengthCoordinate;
    }

    public void setLengthCoordinate(String lengthCoordinate) {
        this.lengthCoordinate = lengthCoordinate;
    }

    public CmtDireccionTabuladaDto getSplitAddres() {
        return splitAddres;
    }

    public void setSplitAddres(CmtDireccionTabuladaDto splitAddres) {
        this.splitAddres = splitAddres;
    }

    public List<CmtCoverDto> getListCover() {
        return listCover;
    }

    public void setListCover(List<CmtCoverDto> listCover) {
        this.listCover = listCover;
    }

    public List<String> getListCarrierCover() {
        return listCarrierCover;
    }

    public void setListCarrierCover(List<String> listCarrierCover) {
        this.listCarrierCover = listCarrierCover;
    }

    public String getStratum() {
        return stratum;
    }

    public void setStratum(String stratum) {
        this.stratum = stratum;
    }

    public List<CmtHhppDto> getListHhpps() {
        return listHhpps;
    }

    public void setListHhpps(List<CmtHhppDto> listHhpps) {
        this.listHhpps = listHhpps;
    }

    public String getMensajeDireccionAntigua() {
        return mensajeDireccionAntigua;
    }

    public void setMensajeDireccionAntigua(String mensajeDireccionAntigua) {
        this.mensajeDireccionAntigua = mensajeDireccionAntigua;
    }
    
    
}
