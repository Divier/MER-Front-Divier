/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.ejb.mgl.address.bussines;

import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.telmex.catastro.services.wservicelocal.WsSitidataStandar;
import co.com.telmex.catastro.services.wservicelocal.WsSitidataStandar_Service;
import co.com.telmex.catastro.utilws.ConstantWS;
import co.com.claro.ejb.mgl.address.dto.CmtAddressToCreateProces;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author villamilc
 */
public class CmtGeoreferenciadorManagerMgl {
    
    private static final Logger LOGGER = LogManager.getLogger(CmtGeoreferenciadorManagerMgl.class);


    private String createXMLRequest(List<CmtAddressToCreateProces> list) throws ApplicationException {
        GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
        StringBuilder xmldoc = new StringBuilder();
        xmldoc.append("<?xml version='1.0' encoding='UTF-8'?>");
        xmldoc.append("<rows>");
        for (CmtAddressToCreateProces cmtAddressToCreateProces : list) {
            if(cmtAddressToCreateProces.isNoProcess()){
                continue;
            }
            GeograficoPoliticoMgl geograficoPoliticoMglCiudad = geograficoPoliticoManager.findById(cmtAddressToCreateProces.getGeograficoPoliticoMgl().getGeoGpoId());
            GeograficoPoliticoMgl geograficoPoliticoMglDepartamento = geograficoPoliticoManager.findById(geograficoPoliticoMglCiudad.getGeoGpoId());
            xmldoc.append("<row>");
            xmldoc.append("<id>");
            xmldoc.append(String.valueOf(cmtAddressToCreateProces.getItemId()));
            xmldoc.append("</id>");
            xmldoc.append("<state>");
            xmldoc.append(geograficoPoliticoMglDepartamento.getGpoNombre());
            xmldoc.append("</state>");
            xmldoc.append("<city>");
            xmldoc.append(cmtAddressToCreateProces.getGeograficoPoliticoMgl().getGpoNombre());
            xmldoc.append("</city>");
            xmldoc.append("<address>");
            xmldoc.append(cmtAddressToCreateProces.getSplitAddressMglDto().getDireccionTexto());
            xmldoc.append("</address>");
            xmldoc.append("<district>");
            xmldoc.append(cmtAddressToCreateProces.getSplitAddressMglDto().getBarrio());
            xmldoc.append("</district>");
            xmldoc.append("</row>");
        }
        xmldoc.append("</rows>");
        return xmldoc.toString();
    }

    public List<CmtAddressToCreateProces> getGeoToAddressList(
            List<CmtAddressToCreateProces> cmtAddressToCreateProcesList) throws ApplicationException {
        WsSitidataStandar_Service developService = new WsSitidataStandar_Service();
        WsSitidataStandar sd = developService.getWsSitidataStandarPort();

        String requestXml = createXMLRequest(cmtAddressToCreateProcesList);
        String responseXML = sd.enrichXmlBatch(requestXml, ConstantWS.USER, ConstantWS.PWD);
        getListObjectFormXml(cmtAddressToCreateProcesList, responseXML);
        return new ArrayList<>();
    }
    
    private void getListObjectFormXml(
            List<CmtAddressToCreateProces> cmtAddressToCreateProcesList, 
            String responseXML) throws ApplicationException {
            HashMap<String,CmtAddressToCreateProces> hashMap=new HashMap<>();
            for(CmtAddressToCreateProces catcp:cmtAddressToCreateProcesList){
                hashMap.put(String.valueOf(catcp.getItemId()), catcp);
            }
        try {        
            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(new StringReader(responseXML));
            Element rootNode=document.getRootElement();
            List<Element> listAddress =rootNode.getChildren();
            for(Element Address:listAddress){
                Element eId =Address.getChild("id");
                String itemId=eId.getValue();
                hashMap.get(itemId).getAddressGeodata().setActeconomica(Address.getChild(ConstantWS.ACTECONOMICA).getValue());
                hashMap.get(itemId).getAddressGeodata().setAmbigua(Address.getChild(ConstantWS.AMBIGUA).getValue());
                hashMap.get(itemId).getAddressGeodata().setBarrio(Address.getChild(ConstantWS.BARRIO).getValue());
                hashMap.get(itemId).getAddressGeodata().setBartrad(Address.getChild(ConstantWS.BARTRAD).getValue());
                hashMap.get(itemId).getAddressGeodata().setCoddanedpto(Address.getChild(ConstantWS.CODDANEDPTO).getValue());
                hashMap.get(itemId).getAddressGeodata().setCoddanemcpio(Address.getChild(ConstantWS.CODDANEMCPIO).getValue());
                hashMap.get(itemId).getAddressGeodata().setCoddir(Address.getChild(ConstantWS.CODDIR).getValue());
                hashMap.get(itemId).getAddressGeodata().setCodencont(Address.getChild(ConstantWS.CODENCONT).getValue());
                hashMap.get(itemId).getAddressGeodata().setCx(Address.getChild(ConstantWS.CX).getValue());
                hashMap.get(itemId).getAddressGeodata().setCy(Address.getChild(ConstantWS.CY).getValue());
                hashMap.get(itemId).getAddressGeodata().setDiralterna(Address.getChild(ConstantWS.DIRALTERNA).getValue());
                hashMap.get(itemId).getAddressGeodata().setDirtrad(Address.getChild(ConstantWS.DIRTRAD).getValue());
                hashMap.get(itemId).getAddressGeodata().setEstado(Address.getChild(ConstantWS.ESTADO).getValue());
                hashMap.get(itemId).getAddressGeodata().setEstrato(Address.getChild(ConstantWS.ESTRATO).getValue());
                hashMap.get(itemId).getAddressGeodata().setFuente(Address.getChild(ConstantWS.FUENTE).getValue());
                hashMap.get(itemId).getAddressGeodata().setLocalidad(Address.getChild(ConstantWS.LOCALIDAD).getValue());
                hashMap.get(itemId).getAddressGeodata().setManzana(Address.getChild(ConstantWS.MANZANA).getValue());
                hashMap.get(itemId).getAddressGeodata().setMensaje(Address.getChild(ConstantWS.MENSAJE).getValue());
                hashMap.get(itemId).getAddressGeodata().setNivsocio(Address.getChild(ConstantWS.NIVSOCIO).getValue());
                hashMap.get(itemId).getAddressGeodata().setNodo1(Address.getChild(ConstantWS.NODO1).getValue());
                hashMap.get(itemId).getAddressGeodata().setNodo2(Address.getChild(ConstantWS.NODO2).getValue());
                hashMap.get(itemId).getAddressGeodata().setNodo3(Address.getChild(ConstantWS.NODO3).getValue());
                hashMap.get(itemId).getAddressGeodata().setNodo4(Address.getChild(ConstantWS.NODO4).getValue());
                hashMap.get(itemId).getAddressGeodata().setNodoDth(Address.getChild(ConstantWS.NODO_DTH).getValue());
                hashMap.get(itemId).getAddressGeodata().setNodoFtth(Address.getChild(ConstantWS.NODO_MOVIL).getValue());
                hashMap.get(itemId).getAddressGeodata().setNodoMovil(Address.getChild(ConstantWS.NODO_FTTH).getValue());
                hashMap.get(itemId).getAddressGeodata().setNodoWifi(Address.getChild(ConstantWS.NODO_WIFI).getValue());
                hashMap.get(itemId).getAddressGeodata().setValagreg(Address.getChild(ConstantWS.VALAGREG).getValue());
                hashMap.get(itemId).getAddressGeodata().setValplaca(Address.getChild(ConstantWS.VALPLACA).getValue());
                hashMap.get(itemId).getAddressGeodata().setZipCode(Address.getChild(ConstantWS.ZIPCODE).getValue());
                hashMap.get(itemId).getAddressGeodata().setZona(Address.getChild(ConstantWS.ZONA1).getValue());
            }        
        } catch (JDOMException | IOException e) {
            LOGGER.error("Error en getListObjectFormXml. ".concat(e.getMessage()), e);   
           throw new ApplicationException(e);
        }
    }
}
