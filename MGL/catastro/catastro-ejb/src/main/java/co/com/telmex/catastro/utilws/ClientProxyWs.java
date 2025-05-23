package co.com.telmex.catastro.utilws;

import co.com.telmex.catastro.data.AddressGeodata;
import co.com.telmex.catastro.data.AddressSuggested;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase ClientProxyWs
 *
 * @author Deiver Rovira
 * @version	1.0
 * @version 1.1 - Modificado por: Nodo DTH 2015-09-30
 * @version     1.2 - Modificado por: Nueve nuebas gozonas Inspira 2019-01-31 Carlos Villamil HITSS
 */
public class ClientProxyWs {

    /**
     *
     * @param parametros
     * @param multiorigen
     * @return
     */
    public AddressGeodata createAddressGeoFromList(List<String> parametros, String barrio, boolean multiorigen) {
        AddressGeodata addressGeodata = new AddressGeodata();

        if (parametros != null && parametros.size() > 0) {
            addressGeodata.setDirtrad(parametros.get(0));
            addressGeodata.setBartrad(parametros.get(1));
            addressGeodata.setCoddir(parametros.get(2));
            addressGeodata.setCodencont(parametros.get(3));
            addressGeodata.setFuente(parametros.get(4));
            addressGeodata.setDiralterna(parametros.get(5));
            addressGeodata.setAmbigua(parametros.get(6));
            addressGeodata.setValagreg(parametros.get(7));
            addressGeodata.setValplaca(parametros.get(8));
            addressGeodata.setManzana(parametros.get(9));
            addressGeodata.setBarrio(parametros.get(10));
            addressGeodata.setLocalidad(parametros.get(11));
            addressGeodata.setNivsocio(parametros.get(12));
            addressGeodata.setCx(parametros.get(13));
            addressGeodata.setCy(parametros.get(14));
            addressGeodata.setNodo1(parametros.get(15));
            addressGeodata.setNodo2(parametros.get(16));
            addressGeodata.setNodo3(parametros.get(17));
            addressGeodata.setNodo4(parametros.get(18));
            addressGeodata.setEstrato(parametros.get(18));
            addressGeodata.setActeconomica(parametros.get(19));

            addressGeodata.setNodoDth(parametros.get(20));
            addressGeodata.setNodoMovil(parametros.get(21));
            addressGeodata.setNodoFtth(parametros.get(22));
            addressGeodata.setNodoWifi(parametros.get(23));
            
            addressGeodata.setGeoZonaUnifilar(parametros.get(24));
            addressGeodata.setGeoZonaGponDiseniado(parametros.get(25));
            addressGeodata.setGeoZonaMicroOndas(parametros.get(26));
            addressGeodata.setGeoZona3G(parametros.get(27));
            addressGeodata.setGeoZona4G(parametros.get(28));
            addressGeodata.setGeoZonaCoberturaCavs(parametros.get(29));
            addressGeodata.setGeoZonaCoberturaUltimaMilla(parametros.get(30));
            addressGeodata.setGeoZonaCurrier(parametros.get(31));
            addressGeodata.setGeoZona5G(parametros.get(32));            
            
            

            if (parametros.get(33) != null && parametros.get(33).length() == 8) {
                addressGeodata.setCoddanedpto(parametros.get(33).substring(0, 2));
                addressGeodata.setCoddanemcpio(parametros.get(33).trim());
            }


            addressGeodata.setEstado(parametros.get(34));
            
        }


        return addressGeodata;
    }

    /**
     *
     * @param list
     * @return
     */
    public List<AddressSuggested> createListSuggested(List<String> list) {
        List<AddressSuggested> listAddress = new ArrayList<AddressSuggested>();
        String address = null;
        String neighborhood = null;
        int exist = 0;
        int exist1 = 0;
        int exist2 = 0;
        int length = 0;
        int length_suffix = ConstantWS.SUGGESTED_SUFFIX_1.length();

        for (String address_neighborhood : list) {
            length = address_neighborhood.length();
            if (address_neighborhood != null && length > 1) {
                exist1 = address_neighborhood.indexOf(ConstantWS.SUGGESTED_SUFFIX_1);
                exist2 = address_neighborhood.indexOf(ConstantWS.SUGGESTED_SUFFIX_2);
                if (exist1 > 0) {
                    exist = exist1;
                } else if (exist2 > 0) {
                    exist = exist2;
                }
                if (exist > 0) {
                    address = address_neighborhood.substring(0, exist);
                    neighborhood = address_neighborhood.substring(exist + length_suffix, length);
                    AddressSuggested addressSuggested = new AddressSuggested();
                    addressSuggested.setAddress(address);
                    addressSuggested.setNeighborhood(neighborhood);
                    listAddress.add(addressSuggested);
                }                
            }
            exist = 0;
        }
        return listAddress;
    }
}
