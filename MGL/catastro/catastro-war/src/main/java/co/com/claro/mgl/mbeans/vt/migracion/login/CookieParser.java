/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.vt.migracion.login;

import com.sun.org.apache.xerces.internal.parsers.SAXParser;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 *
 */
public class CookieParser {

  private XMLReader parser;
  private CookieData data;
  private static final Logger LOGGER = LogManager.getLogger(CookieParser.class);

  //TODO No se parsena todos los roles solo toma los primeros segun orden  
  public CookieParser(String XMLText) throws IOException, SAXException {
    LOGGER.info("cadena a xml a convertir --->" + XMLText);
    Map<String, String> mapData = new HashMap<String, String>();
    
    parser = new SAXParser();
    
    parser.setContentHandler(new CookieContentHandler(mapData));
    InputSource input = new InputSource(new StringReader(XMLText));
    parser.parse(input);
    parseData(mapData);
    LOGGER.info(" cadena ya en el map al finalizar el metodo"
            + " CookieParser con los roles" + mapData.get("ROLES"));
  }
  @SuppressWarnings("unchecked")
  private void parseData(Map<String, String> mapData) {

    ArrayList list;
    setData(new CookieData());
    LOGGER.info("MAPA DE ROLES ------>" + mapData.get("ROLES"));
    getData().setAliado(mapData.get("ALIADO"));

    if (mapData.get("ALIADOS") != null) {
      list = new ArrayList(Arrays.asList(mapData.get("ALIADOS").split("/")));
      getData().setAliados(list);
    }

    if (mapData.get("CIUDADES") != null) {
      list = new ArrayList(Arrays.asList(mapData.get("CIUDADES").split("/")));
      getData().setCiudades(list);
    }

    getData().setIdUsuario(mapData.get("IDUSUARIO"));
    getData().setNombre(mapData.get("NOMBRE"));

    if (mapData.get("PARAMETROS") != null) {
      list = new ArrayList(Arrays.asList(mapData.get("PARAMETROS").split("/")));
      getData().setParametros(list);
    }

    getData().setPerfil(Integer.parseInt(mapData.get("PERFIL")));

    if (mapData.get("ROLES") != null) {
      list = new ArrayList(Arrays.asList(mapData.get("ROLES").split("/")));
      getData().setRoles(list);
    }

    if (mapData.get("TIPOSTRABAJO") != null) {
      list = new ArrayList(Arrays.asList(mapData.get("TIPOSTRABAJO").split("/")));
      getData().setTiposTrabajo(list);
    }

    getData().setUsuario(mapData.get("USUARIO"));
  }

  public CookieData getData() {
    return data;
  }

  public void setData(CookieData data) {
    this.data = data;
  }
}

class CookieContentHandler implements ContentHandler {

  private Locator locator;
  private String currentElement;
  private Map<String, String> data;
  private static final Logger logger = LogManager.getLogger(CookieContentHandler.class.getName());

  public CookieContentHandler(Map<String, String> data) {
    this.data = data;
  }

  @Override
  public void setDocumentLocator(Locator locator) {
    logger.info("------------------------Entro a la clase cookieContentHandler en el metodo setDocumentLocator-------");
    logger.info("variable PublicId: " + locator.getPublicId());
    logger.info("variable SystemId: " + locator.getSystemId());
    logger.info("variable column number: " + locator.getColumnNumber());
    logger.info("variable line number: " + locator.getLineNumber());
    this.locator = locator;

  }

  @Override
  public void startDocument() throws SAXException {
  }

  @Override
  public void endDocument() throws SAXException {
  }

  @Override
  public void startPrefixMapping(String prefix, String uri) throws SAXException {
    logger.info("------------------------Entro a la clase cookieContentHandler en el metodo startPrefixMapping-------");
    logger.info("variable prefix: " + prefix);
    logger.info("variable uri: " + uri);
  }

  @Override
  public void endPrefixMapping(String prefix) throws SAXException {

    logger.info("------------------------Entro a la clase cookieContentHandler en el metodo endPrefixMapping-------");
    logger.info("variable prefix: " + prefix);

  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
    logger.info("------------------------Entro a la clase cookieContentHandler en el metodo startElement-------");
    logger.info("variable uri: " + uri);
    logger.info("variable localName: " + localName);
    logger.info("variable qName: " + qName);
    logger.info("variable Length: " + atts.getLength());
    currentElement = localName;

  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    logger.info("------------------------Entro a la clase cookieContentHandler en el metodo endElement-------");
    logger.info("variable uri: " + uri);
    logger.info("variable localName: " + localName);
    logger.info("variable qName: " + qName);
  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {

    String s = new String(ch, start, length);
    String temp = "";
    if (data.get(currentElement) == null) {
      data.put(currentElement, s);
    } else {
      temp = data.get(currentElement);
      temp += s;
      data.remove(currentElement);
      data.put(currentElement, temp);
    }

    logger.info("------------------------Entro a la clase cookieContentHandler en el metodo characters-------");
    logger.info("variable star: " + start);
    logger.info("variable length " + length);
    logger.info("datos de elmentos vector caracteres " + new String(ch));
    logger.info("variable current element: " + currentElement);
    logger.info("mapa de datos de la variable " + currentElement + " : " + data.get(currentElement));
  }

  @Override
  public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
  }

  @Override
  public void processingInstruction(String target, String data) throws SAXException {
  }

  @Override
  public void skippedEntity(String name) throws SAXException {
  }


}
