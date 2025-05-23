package co.com.telmex.catastro.utilws;

import co.com.telmex.catastro.data.AddressGeodata;
import co.com.telmex.catastro.data.AddressRequest;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * Clase ConvertXML
 *
 * @author 	Jose Luis Caicedo Gonzalez
 * @version	1.0 - Modificado por: Direcciones fase I Carlos Villamil 2012-12-11
 * @version     1.1 - Modificado por: Nodo DTH 2015-09-30
 * @version     1.2 - Modificado por: Nueve nuebas gozonas Inspira 2019-01-31 Carlos Villamil HITSS
*/
public class ConvertXML {

    /**
     * 
     * @param list
     * @return
     */
    public String createXMLRequest(List<AddressRequest> list) {
        StringBuffer xmldoc = new StringBuffer();
        xmldoc.append("<rows>");
        for (AddressRequest addressRequest : list) {
            xmldoc.append("<row>");
            xmldoc.append("<id>");
            xmldoc.append(addressRequest.getId());
            xmldoc.append("</id>");
            xmldoc.append("<state>");
            xmldoc.append(addressRequest.getState());
            xmldoc.append("</state>");
            xmldoc.append("<city>");
            xmldoc.append(addressRequest.getCity());
            xmldoc.append("</city>");
            xmldoc.append("<address>");
            xmldoc.append(addressRequest.getAddress());
            xmldoc.append("</address>");
            xmldoc.append("<district>");
            xmldoc.append(addressRequest.getNeighborhood());
            xmldoc.append("</district>");
            xmldoc.append("</row>");
        }
        xmldoc.append("</rows>");
        return xmldoc.toString();
    }

    /**
     * 
     * @param addressRequest
     * @return
     */
    public String createXMLRequest(AddressRequest addressRequest) {
        StringBuffer xmldoc = new StringBuffer();
        xmldoc.append("<?xml version='1.0' encoding='UTF-8'?>");
        xmldoc.append("<rows>");
        xmldoc.append("<row>");
        xmldoc.append("<id>");
        xmldoc.append(addressRequest.getId());
        xmldoc.append("</id>");
        xmldoc.append("<state>");
        xmldoc.append(addressRequest.getState());
        xmldoc.append("</state>");
        xmldoc.append("<city>");
        xmldoc.append(addressRequest.getCity());
        xmldoc.append("</city>");
        xmldoc.append("<address>");
        xmldoc.append(addressRequest.getAddress());
        xmldoc.append("</address>");
        xmldoc.append("<district>");
        xmldoc.append(addressRequest.getNeighborhood());
        xmldoc.append("</district>");
        xmldoc.append("</row>");
        xmldoc.append("</rows>");
        return xmldoc.toString();
    }
    
    public String formatResponseBatchToXml(String response){
		
        String responseXml = response;
        
        responseXml = responseXml.replaceAll("><", ">\n<");
	responseXml = responseXml.replaceAll(">\n</", "></");
	responseXml = responseXml.replaceAll("3G", "tresG");
	responseXml = responseXml.replaceAll("4G", "cuatroG");
	responseXml = responseXml.replaceAll("5G", "cincoG");
	
        return responseXml;
                
    }
	
    /**
     * 
     * @param xmlString
     * @return
     * @throws IOException
     * @throws JDOMException
     */
    public AddressGeodata transformToAddressGeodata(String xmlString) throws IOException, JDOMException {
        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(new StringReader(xmlString));
        return createAddressGeodata(document);
    }

    /**
     * 
     * @param xmlString
     * @return
     * @throws IOException
     * @throws JDOMException
     */
    public List<AddressGeodata> transformToListAddressGeodata(String xmlString) throws IOException, JDOMException {
        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(new StringReader(xmlString));
        return createListAddressGeodata(document);
    }
    
    /**
     * 
     * @param document
     * @return 
     */
    private AddressGeodata createAddressGeodata(Document document) {
        AddressGeodata addressGeodata = new AddressGeodata();
        Element nodeAddress = null;
        Element node = null;
        String name = null;
        String value = null;

        Element root = document.getRootElement();
        List list = root.getChildren();
        Iterator iterator = list.iterator();
        if (iterator.hasNext()) {
            nodeAddress = (Element) iterator.next();
            List addressValues = nodeAddress.getChildren();
            Iterator iterator2 = addressValues.iterator();
            while (iterator2.hasNext()) {
                node = (Element) iterator2.next();
                name = node.getName();
                value = node.getText();
                addAttributeAddressGeodata(addressGeodata, name, value);
            }
        }

        return addressGeodata;
    }

    /**
     * 
     * @param document
     * @return 
     */
    private List<AddressGeodata> createListAddressGeodata(Document document) {
        List<AddressGeodata> listaAddressGeodata = new ArrayList<AddressGeodata>();
        Element nodeAddress = null;
        Element node = null;
        String name = null;
        String value = null;

        Element root = document.getRootElement();
        List list = root.getChildren();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            AddressGeodata addressGeodata = new AddressGeodata();
            nodeAddress = (Element) iterator.next();
            List addressValues = nodeAddress.getChildren();
            Iterator iterator2 = addressValues.iterator();
            while (iterator2.hasNext()) {
                node = (Element) iterator2.next();
                name = node.getName();
                value = node.getText();
                if (name != null) {
                    name = name.trim();
                }
                if (value != null) {
                    value = value.trim();
                }
                addAttributeAddressGeodata(addressGeodata, name, value);
            }
            listaAddressGeodata.add(addressGeodata);
        }
        return listaAddressGeodata;
    }

    /**
     * 
     * @param addressGeodata
     * @param name
     * @param value 
     */
    private void addAttributeAddressGeodata(AddressGeodata addressGeodata, String name, String value) {
        if (ConstantWS.IDENTIFICADOR.equals(name)) {
            addressGeodata.setIdentificador(value);
        } else if (ConstantWS.DIRTRAD.equals(name)) {
            addressGeodata.setDirtrad(value);
        } else if (ConstantWS.BARTRAD.equals(name)) {
            addressGeodata.setBartrad(value);
        } else if (ConstantWS.CODDIR.equals(name)) {
            addressGeodata.setCoddir(value);
        } else if (ConstantWS.CODENCONT.equals(name)) {
            addressGeodata.setCodencont(value);
        } else if (ConstantWS.FUENTE.equals(name)) {
            addressGeodata.setFuente(value);
        } else if (ConstantWS.DIRALTERNA.equals(name)) {
            addressGeodata.setDiralterna(value);
        } else if (ConstantWS.AMBIGUA.equals(name)) {
            addressGeodata.setAmbigua(value);
        } else if (ConstantWS.VALAGREG.equals(name)) {
            addressGeodata.setValagreg(value);
        } else if (ConstantWS.VALPLACA.equals(name)) {
            addressGeodata.setValplaca(value);
        } else if (ConstantWS.MANZANA.equals(name)) {
            addressGeodata.setManzana(value);
        } else if (ConstantWS.BARRIO.equals(name)) {
            addressGeodata.setBarrio(value);
        } else if (ConstantWS.LOCALIDAD.equals(name)) {
            addressGeodata.setLocalidad(value);
        } else if (ConstantWS.NIVSOCIO.equals(name)) {
            addressGeodata.setNivsocio(value);
        } else if (ConstantWS.CX.equals(name)) {
            addressGeodata.setCx(value);
        } else if (ConstantWS.CY.equals(name)) {
            addressGeodata.setCy(value);
        } else if (ConstantWS.ESTRATO.equals(name)) {
            addressGeodata.setEstrato(value);
        } else if (ConstantWS.ACTECONOMICA.equals(name)) {
            addressGeodata.setActeconomica(value);
        } else if (ConstantWS.NODO1.equals(name)) {
            addressGeodata.setNodo1(value);
        } else if (ConstantWS.NODO2.equals(name)) {
            addressGeodata.setNodo2(value);
        } else if (ConstantWS.NODO3.equals(name)) {
            addressGeodata.setNodo3(value);
        } else if (ConstantWS.NODO_DTH.equals(name)) {
            addressGeodata.setNodoDth(value);
        }else if (ConstantWS.NODO_MOVIL.equals(name)) {
            addressGeodata.setNodoMovil(value);
        }else if (ConstantWS.NODO_FTTH.equals(name)) {
            addressGeodata.setNodoFtth(value);
        }else if (ConstantWS.NODO_WIFI.equals(name)) {
            addressGeodata.setNodoWifi(value);
        } else if (ConstantWS.CODDANEDPTO.equals(name)) {
            addressGeodata.setCoddanedpto(value);
        } else if (ConstantWS.CODDANEMCPIO.equals(name)) {
            addressGeodata.setCoddanemcpio(value + "000");//Borrar +"000"// Direcciones face I Carlos Vilamil 2012-12-11
        } else if (ConstantWS.ZONA1.equals(name)) {
            addressGeodata.setZona(value);
        } else if (ConstantWS.ESTADO.equals(name)) {
            addressGeodata.setEstado(value);
        } else if (ConstantWS.MENSAJE.equals(name)) {
            addressGeodata.setMensaje(value);
        } 
        //Inicio cambio version 1.2 
         else if (ConstantWS.ZONA_CURRIER.equals(name)) {
            addressGeodata.setGeoZonaCurrier(value);
        }else if (ConstantWS.ZONA_5G.equals(name)) {
            addressGeodata.setGeoZona5G(value);
        }else if (ConstantWS.ZONA_CINCOG.equals(name)) {
            addressGeodata.setGeoZona5G(value);
        }else if (ConstantWS.ZONA_GPONDISENIADO.equals(name)) {
            addressGeodata.setGeoZonaGponDiseniado(value);
        }else if (ConstantWS.ZONA_MICROONDAS.equals(name)) {
            addressGeodata.setGeoZonaMicroOndas(value);
        }else if (ConstantWS.ZONA_UNIFILIAR.equals(name)) {
            addressGeodata.setGeoZonaUnifilar(value);
        }else if (ConstantWS.ZONA_3G.equals(name)) {
            addressGeodata.setGeoZona3G(value);
        }else if (ConstantWS.ZONA_TRESG.equals(name)) {
            addressGeodata.setGeoZona3G(value);
        }else if (ConstantWS.ZONA_4G.equals(name)) {
            addressGeodata.setGeoZona4G(value);
        }else if (ConstantWS.ZONA_CUATROG.equals(name)) {
            addressGeodata.setGeoZona4G(value);
        }else if (ConstantWS.ZONA_COBERTURACAVS.equals(name)) {
            addressGeodata.setGeoZonaCoberturaCavs(value);
        }else if (ConstantWS.ZONA_COBERTURAULTIMAMILLA.equals(name)) {
            addressGeodata.setGeoZonaCoberturaUltimaMilla(value);
        }
        //Fin cambio version 1.2 
        
        
        //INICIO Direcciones face I Carlos Vilamil 2012-12-11            
        else if (ConstantWS.SOCIOECONOMICLEVELCOMMENT.equals(name)) {
            addressGeodata.setComentEconomicLevel(value);
        } else if (ConstantWS.ZIPCODE.equals(name)) {
            addressGeodata.setZipCode(value);
        }
        
        
        
        
        addressGeodata.setComentEconomicLevel("Comentario nivel Socioeconomico Borrar en codigo");//borrar para que funcione con servinformacion
        addressGeodata.setZipCode("Codigo SIP Borrar en codigo");
    }
}
