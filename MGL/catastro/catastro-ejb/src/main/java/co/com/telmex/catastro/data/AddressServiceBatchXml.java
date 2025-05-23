/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.telmex.catastro.data;

import co.com.claro.mgl.dtos.AddressGeoDirDataDto;
import java.io.Serializable;
import java.util.List;


/**
 * Clase AddressService Representa el estado de servicio de la dirección
 * solicitada. implementa Serialización
 *
 * @author Johan Gomez
 * @version 1.1, 2023/07/19 Rev gomezjoj
 */
public class AddressServiceBatchXml  implements Serializable {
    
    private String idaddress = null;
    private String address = null;
    private String neighborhood = null;
    private String category = null;
    private String leveleconomic = null;
    private String appletstandar = null;
    private String chagenumber = null;
    private String alternateaddress = null;
    private String levellive = null;
    private String qualifiers = null;
    private String activityeconomic = null;
    private String recommendations = null;
    private String exist = null;
    private String traslate = null;
    private List<AddressSuggested> addressSuggested = null;
    private List<AddressAggregated> addressAggregated = null;

    private String coddanemcpio = null;
    private String zipCode = null;
    private String comentEconomicLevel = null;

    private String nodoUno = null;
    private String nodoDos = null;
    private String nodoTres = null;

    private String nodoCuatro = null;

    /**
     * Nodo DTH
     */
    private String nodoDth = null;
    private String nodoMovil = null;
    private String nodoFtth = null;
    private String nodoWifi = null;
    //JDHT
    private String nodoZonaUnifilar = null;
    private String nodoZona3G = null;
    private String nodoZona4G = null;
    private String nodoZona5G = null;
    private String nodoZonaCoberturaCavs = null;
    private String nodoZonaCoberturaUltimaMilla = null;
    private String nodoZonaGponDiseniado = null;
    private String nodoZonaMicroOndas = null;
    private String nodoZonaCurrier = null;
    
    
    
    private String estratoDef = null;
    private String source;//AddressGeodata.fuente
    private String barrioTraducido;//AddressGeodata.bartrad
    private String addressCode;// sin complemento
    private String addressCodeFound;// sin complemento
    private String locality;//AddressGeodata.localidad
    private String cx;//AddressGeodata.cx -->Cambiar por los de ubicacion
    private String cy;//AddressGeodata.cy -->Cambiar por los de ubicacion
    private String state;//AddressGeodata.estado
    private String zipCodeDistrict;
    private String zipCodeState;
    
    private String reliability;
    private String ambigua;
    private String reliabilityPlaca;
    
    private AddressGeoDirDataDto respuestaGeo;


    /**
     *
     * @return
     */
    public String getExist() {
        return exist;
    }

    /**
     *
     * @param exist
     */
    public void setExist(String exist) {
        this.exist = exist;
    }

    /**
     *
     * @return
     */
    public String getTraslate() {
        return traslate;
    }

    /**
     *
     * @param traslate
     */
    public void setTraslate(String traslate) {
        this.traslate = traslate;
    }

    /**
     *
     * @return
     */
    public List<AddressSuggested> getAddressSuggested() {
        return addressSuggested;
    }

    /**
     *
     * @param addressSuggested
     */
    public void setAddressSuggested(List<AddressSuggested> addressSuggested) {
        this.addressSuggested = addressSuggested;
    }

    /**
     *
     * @return
     */
    public String getRecommendations() {
        return recommendations;
    }

    /**
     *
     * @param recommendations
     */
    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    /**
     *
     * @return
     */
    public String getActivityeconomic() {
        return activityeconomic;
    }

    /**
     *
     * @param activityeconomic
     */
    public void setActivityeconomic(String activityeconomic) {
        this.activityeconomic = activityeconomic;
    }

    /**
     *
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return
     */
    public String getAlternateaddress() {
        return alternateaddress;
    }

    /**
     *
     * @param alternateaddress
     */
    public void setAlternateaddress(String alternateaddress) {
        this.alternateaddress = alternateaddress;
    }

    /**
     *
     * @return
     */
    public String getAppletstandar() {
        return appletstandar;
    }

    /**
     *
     * @param appletstandar
     */
    public void setAppletstandar(String appletstandar) {
        this.appletstandar = appletstandar;
    }

    /**
     *
     * @return
     */
    public String getCategory() {
        return category;
    }

    /**
     *
     * @param category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     *
     * @return
     */
    public String getChagenumber() {
        return chagenumber;
    }

    /**
     *
     * @param chagenumber
     */
    public void setChagenumber(String chagenumber) {
        this.chagenumber = chagenumber;
    }

    /**
     *
     * @return
     */
    public String getIdaddress() {
        return idaddress;
    }

    /**
     *
     * @param idaddress
     */
    public void setIdaddress(String idaddress) {
        this.idaddress = idaddress;
    }

    /**
     *
     * @return
     */
    public String getLeveleconomic() {
        return leveleconomic;
    }

    /**
     *
     * @param leveleconomic
     */
    public void setLeveleconomic(String leveleconomic) {
        this.leveleconomic = leveleconomic;
    }

    /**
     *
     * @return
     */
    public String getLevellive() {
        return levellive;
    }

    /**
     *
     * @param levellive
     */
    public void setLevellive(String levellive) {
        this.levellive = levellive;
    }

    /**
     *
     * @return
     */
    public String getNeighborhood() {
        return neighborhood;
    }

    /**
     *
     * @param neighborhood
     */
    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    /**
     *
     * @return
     */
    public String getQualifiers() {
        return qualifiers;
    }

    /**
     *
     * @param qualifiers
     */
    public void setQualifiers(String qualifiers) {
        this.qualifiers = qualifiers;
    }


    /**
     *
     * @return
     */
    public List<AddressAggregated> getAddressAggregated() {
        return addressAggregated;
    }

    /**
     *
     * @param addressAggregated
     */
    public void setAddressAggregated(List<AddressAggregated> addressAggregated) {
        this.addressAggregated = addressAggregated;
    }

    /**
     *
     * @return
     */
    public String getCoddanemcpio() {
        return coddanemcpio;
    }

    /**
     *
     * @param coddanemcpio
     */
    public void setCoddanemcpio(String coddanemcpio) {
        this.coddanemcpio = coddanemcpio;
    }

    /**
     *
     * @return
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     *
     * @param zipCode
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     *
     * @return
     */
    public String getComentEconomicLevel() {
        return comentEconomicLevel;
    }

    /**
     *
     * @param comentEconomicLevel
     */
    public void setComentEconomicLevel(String comentEconomicLevel) {
        this.comentEconomicLevel = comentEconomicLevel;
    }

    /**
     *
     * @return
     */

    public String getZipCodeDistrict() {
        if (zipCode == null
                || zipCode.trim().isEmpty()
                || zipCode.length() > 6) {
            zipCodeDistrict = "0000";
        } else {
            zipCodeDistrict = zipCode.substring(4);
        }
        return zipCodeDistrict;
    }

    public void setZipCodeDistrict(String zipCodeDistrict) {
        this.zipCodeDistrict = zipCodeDistrict;
    }

    /**
     *
     * @return
     */

    public String getZipCodeState() {
        if (zipCode == null
                || zipCode.trim().isEmpty()
                || zipCode.length() > 2) {
            zipCodeState = "00";
        } else {
            zipCodeState = zipCode.substring(0, 2);
        }
        return zipCodeState;
    }

    public void setZipCodeState(String zipCodeState) {
        this.zipCodeState = zipCodeState;
    }

    /**
     *
     * @return
     */
    public String getDanePopulatedArea() {
        if (coddanemcpio == null) {
            return "000";
        }
        if (coddanemcpio.equals("")) {
            return "000";
        }
        if (coddanemcpio.length() > 8) {
            return "000";
        }
        return coddanemcpio.substring(5);
    }

    /**
     *
     * @return
     */
    public String getDaneCity() {
        if (coddanemcpio == null) {
            return "00000";
        }
        if (coddanemcpio.equals("")) {
            return "00000";
        }
        if (coddanemcpio.length() > 5) {
            return "00000";
        }
        return coddanemcpio.substring(0, 4);
    }

//FIN Cambios Face I Carlos Villamil 2012-12-11    
    @Override
    public String toString() {
        return "AddressService{" + "identificador=" +respuestaGeo.getIdentificador() + "idaddress=" + idaddress + ", address=" + address + ", neighborhood=" + neighborhood
                + ", category=" + category + ", leveleconomic=" + leveleconomic + ", appletstandar=" + appletstandar
                + ", chagenumber=" + chagenumber + ", alternateaddress=" + alternateaddress + ", levellive=" + levellive
                + ", qualifiers=" + qualifiers + ", activityeconomic=" + activityeconomic + ", recommendations=" + recommendations
                + ", exist=" + exist + ", traslate=" + traslate + ", addressSuggested=" + addressSuggested + ", addressggregated=" + addressAggregated + '}';
    }

    /**
     * @return the nodoUno ver. 1.1
     */
    public String getNodoUno() {
        return nodoUno;
    }

    /**
     * @param nodoUno the nodoUno to set ver. 1.1
     */
    public void setNodoUno(String nodoUno) {
        this.nodoUno = nodoUno;
    }

    /**
     * @return the nodoDos ver. 1.1
     */
    public String getNodoDos() {
        return nodoDos;
    }

    /**
     * @param nodoDos the nodoDos to set ver. 1.1
     */
    public void setNodoDos(String nodoDos) {
        this.nodoDos = nodoDos;
    }

    /**
     * @return the nodoTres ver. 1.1
     */
    public String getNodoTres() {
        return nodoTres;
    }

    /**
     * @param nodoTres the nodoTres to set ver. 1.1
     */
    public void setNodoTres(String nodoTres) {
        this.nodoTres = nodoTres;
    }


    public String getNodoCuatro() {
        return nodoCuatro;
    }

    /**
     * @param nodoCuatro the nodoUno to set ver. 1.1
     */
    public void setNodoCuatro(String nodoCuatro) {
        this.nodoCuatro = nodoCuatro;
    }

    /**
     * Get atributo nodoDth
     *
     * @return String nodoDth
     */
    public String getNodoDth() {
        return nodoDth;
    }

    /**
     * Set atributo nodoDth
     *
     * @param nodoDth
     */
    public void setNodoDth(String nodoDth) {
        this.nodoDth = nodoDth;
    }

    /**
     * @return the estratoDef Cambios Face I Carlos Villamil 2013-05-22 ver. 1.1
     */
    public String getEstratoDef() {
        return estratoDef;
    }

    /**
     * @param estratoDef the estratoDef to set Cambios Face I Carlos Villamil
     * 2013-05-22 ver. 1.1
     */
    public void setEstratoDef(String estratoDef) {
        this.estratoDef = estratoDef;
    }

    // <editor-fold defaultstate="collapsed" desc="Modificacion Direcciones face I Johnnatan Ortiz 2013-09-30">
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getBarrioTraducido() {
        return barrioTraducido;
    }

    public void setBarrioTraducido(String barrioTraducido) {
        this.barrioTraducido = barrioTraducido;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public String getAddressCodeFound() {
        return addressCodeFound;
    }

    public void setAddressCodeFound(String addressCodeFound) {
        this.addressCodeFound = addressCodeFound;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCx() {
        return cx;
    }

    public void setCx(String cx) {
        this.cx = cx;
    }

    public String getCy() {
        return cy;
    }

    public void setCy(String cy) {
        this.cy = cy;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReliability() {
        return reliability;
    }

    public void setReliability(String reliability) {
        this.reliability = reliability;
    }

    // </editor-fold>    
    public String getNodoMovil() {
        return nodoMovil;
    }

    public void setNodoMovil(String nodoMovil) {
        this.nodoMovil = nodoMovil;
    }

    public String getNodoFtth() {
        return nodoFtth;
    }

    public void setNodoFtth(String nodoFtth) {
        this.nodoFtth = nodoFtth;
    }

    public String getNodoWifi() {
        return nodoWifi;
    }

    public void setNodoWifi(String nodoWifi) {
        this.nodoWifi = nodoWifi;
    }

    public String getAmbigua() {
        return ambigua;
    }

    public void setAmbigua(String ambigua) {
        this.ambigua = ambigua;
    }

    public String getReliabilityPlaca() {
        return reliabilityPlaca;
    }

    public void setReliabilityPlaca(String reliabilityPlaca) {
        this.reliabilityPlaca = reliabilityPlaca;
    }

    public AddressGeoDirDataDto getRespuestaGeo() {
        return respuestaGeo;
    }

    public void setRespuestaGeo(AddressGeoDirDataDto respuestaGeo) {
        this.respuestaGeo = respuestaGeo;
    }

    public String getNodoZonaUnifilar() {
        return nodoZonaUnifilar;
    }

    public void setNodoZonaUnifilar(String nodoZonaUnifilar) {
        this.nodoZonaUnifilar = nodoZonaUnifilar;
    }

    public String getNodoZona3G() {
        return nodoZona3G;
    }

    public void setNodoZona3G(String nodoZona3G) {
        this.nodoZona3G = nodoZona3G;
    }

    public String getNodoZona4G() {
        return nodoZona4G;
    }

    public void setNodoZona4G(String nodoZona4G) {
        this.nodoZona4G = nodoZona4G;
    }

    public String getNodoZona5G() {
        return nodoZona5G;
    }

    public void setNodoZona5G(String nodoZona5G) {
        this.nodoZona5G = nodoZona5G;
    }

    public String getNodoZonaCoberturaCavs() {
        return nodoZonaCoberturaCavs;
    }

    public void setNodoZonaCoberturaCavs(String nodoZonaCoberturaCavs) {
        this.nodoZonaCoberturaCavs = nodoZonaCoberturaCavs;
    }

    public String getNodoZonaCoberturaUltimaMilla() {
        return nodoZonaCoberturaUltimaMilla;
    }

    public void setNodoZonaCoberturaUltimaMilla(String nodoZonaCoberturaUltimaMilla) {
        this.nodoZonaCoberturaUltimaMilla = nodoZonaCoberturaUltimaMilla;
    }

    public String getNodoZonaGponDiseniado() {
        return nodoZonaGponDiseniado;
    }

    public void setNodoZonaGponDiseniado(String nodoZonaGponDiseniado) {
        this.nodoZonaGponDiseniado = nodoZonaGponDiseniado;
    }

    public String getNodoZonaMicroOndas() {
        return nodoZonaMicroOndas;
    }

    public void setNodoZonaMicroOndas(String nodoZonaMicroOndas) {
        this.nodoZonaMicroOndas = nodoZonaMicroOndas;
    }

    public String getNodoZonaCurrier() {
        return nodoZonaCurrier;
    }

    public void setNodoZonaCurrier(String nodoZonaCurrier) {
        this.nodoZonaCurrier = nodoZonaCurrier;
    }

}
