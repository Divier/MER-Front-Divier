package co.com.telmex.catastro.utilws;

import co.com.telmex.catastro.services.util.UrlProvGeo;

/**
 * Clase ConstantWS
 *
 * @author 	Deiver Rovira.
 * @version	1.0 - Modificado por: Direcciones fase I Carlos Villamil 2012-12-11
 * @version     1.2 - Modificado por: Nodo DTH 2015-09-30
 * @version     1.3 - Modificado por: Nueve nuebas gozonas Inspira 2019-01-31 Carlos Villamil HITSS
 */
public class ConstantWS {

    /**
     * 
     */
    public static String ENDPOINT = "http://"+UrlProvGeo.getUrlProvGeo()+"/WsSitidataStandar/wsSitidataStandar";
    /**
     * 
     */
    public static final String USER = "administrador";
    /**
     * 
     */
    public static final String PWD = "telmex2012";
    /*------------------------------------- TAG REQUEST enriquecerXmlLotes ------------------------------------------------*/
    /**
     * 
     */
    public static final String ADR_ONY_LABEL = "wss";
    /**
     * 
     */
    public static final String ADR_XMLNS = UrlProvGeo.getUrlProvGeo()+"/WsSitidataStandar/wsSitidataStandar";
    /**
     * 
     */
    public static final String ADR_TAG_LOTES = "enrichXmlBatch";
    /**
     * 
     */
    public static final String ADR_TAG_XMLENVIO = "xmlSend";
    /**
     * 
     */
    public static final String ADR_TAG_USUARIO = "user";
    /**
     * 
     */
    public static final String ADR_TAG_CLAVE = "password";
    /**
     * 
     */
    public static final String ADR_TAG_RETURN = "return";
    /*------------------------------------- TAG RESPONSE enriquecerXmlLotes------------------------------------------------*/
    /**
     * 
     */
    public static String IDENTIFICADOR = "id";
    /**
     * 
     */
    public static String DIRTRAD = "addresstran";
    /**
     * 
     */
    public static String BARTRAD = "districttrad";
    /**
     * 
     */
    public static String CODDIR = "codaddress";
    /**
     * 
     */
    public static String CODENCONT = "codefound";
    /**
     * 
     */
    public static String FUENTE = "source";
    /**
     * 
     */
    public static String DIRALTERNA = "addresschoice";
    /**
     * 
     */
    public static String AMBIGUA = "ambiguity";
    /**
     * 
     */
    public static String VALAGREG = "valcomplement";
    /**
     * 
     */
    public static String VALPLACA = "valaddress";
    /**
     * 
     */
    public static String MANZANA = "block";
    /**
     * 
     */
    public static String BARRIO = "district";
    /**
     * 
     */
    public static String LOCALIDAD = "locality";
    /**
     * 
     */
    public static String NIVSOCIO = "levelsocioeconomic";
    /**
     * 
     */
    public static String CX = "longitude";
    /**
     * 
     */
    public static String CY = "latitude";
    /**
     * 
     */
    public static String ESTRATO = "lse";
    /**
     * 
     */
    public static String ACTECONOMICA = "ecoactivity";
    /**
     * 
     */
    public static String NODO1 = "node1";
    /**
     * 
     */
    public static String NODO2 = "node2";
    /**
     * 
     */
    public static String NODO3 = "node3";
    public static String NODO4 = "node4";

    /**
     * Constante Nodo DTH
     */
    public static String NODO_DTH = "DTH";
    /**
     * Constante Nodo DTH
     */
    public static String NODO_MOVIL = "MOVIL";
    /**
     * Constante Nodo DTH
     */
    public static String NODO_FTTH = "FTTH";
    /**
     * Constante Nodo DTH
     */
    public static String NODO_WIFI = "WIFI";
    /**
     * 
     */
   //Inicio cambio version 1.3 
    /**
     * Constante Nodo UNIFILIAR
     */
    public static String ZONA_UNIFILIAR = "UNIFILIAR";
    /**
     * Constante Nodo GPONDISENIADO
     */
    public static String ZONA_GPONDISENIADO = "GPONDISENIADO";
    /**
     * Constante Nodo MICROONDAS
     */
    public static String ZONA_MICROONDAS = "MICROONDAS";
    /**
     * Constante Nodo 3G
     */
    public static String ZONA_3G= "3G";
    /**
     * Constante Nodo 3G
     */
    public static String ZONA_TRESG= "tresG";
    /**
     * Constante Nodo 4G
     */
    public static String ZONA_4G = "4G";
    /**
     * Constante Nodo 4G
     */
    public static String ZONA_CUATROG = "cuatroG";
    /**
     * Constante Nodo COBERTURACAVS
     */
    public static String ZONA_COBERTURACAVS = "COBERTURACAVS";
    /**
     * Constante Nodo COBERTURAULTIMAMILLA
     */
    public static String ZONA_COBERTURAULTIMAMILLA = "COBERTURAULTIMAMILLA";
    /**
     * Constante Nodo CURRIER
     */
    public static String ZONA_CURRIER = "CURRIER";
    /**
     * Constante Nodo 5G
     */
    public static String ZONA_5G = "5G" ;   
    /**
     * Constante Nodo 5G
     */
    public static String ZONA_CINCOG = "cincoG" ;   
   //fin cambio version 1.3     
    /**
     * Constante Nodo codestate
     */
    public static String CODDANEDPTO = "codestate";
    /**
     * 
     */
    public static String CODDANEMCPIO = "codecity";
    /**
     * 
     */
    public static String ESTADO = "status";
    /**
     * 
     */
    public static String MENSAJE = "message";
    /**
     * 
     */
    public static String ZONA1 = "zone1";
    //INICIO Direcciones face I Carlos Vilamil 2012-12-11
    /**
     * 
     */
    public static String ZIPCODE = "zipcode";
    /**
     * 
     */
    public static String SOCIOECONOMICLEVELCOMMENT = "socioeconomiclevelcomment";
    //FIN Direcciones face I Carlos Vilamil 2012-12-11
    /*------------------------------------- TAG REQUEST enriquecerAsistido ------------------------------------------------*/
    /**
     * 
     */
    public static final String SUGGESTED_ONY_LABEL = "wss";
    /**
     * 
     */
    public static final String SUGGESTED_XMLNS = UrlProvGeo.getUrlProvGeo()+"/WsSitidataStandar/wsSitidataStandar";
    /**
     * 
     */
    public static final String SUGGESTED_TAG_METHOD = "enrichAssist";
    /**
     * 
     */
    public static final String SUGGESTED_TAG_ADDRESS = "address";
    /**
     * 
     */
    public static final String SUGGESTED_TAG_CITY = "city";
    /**
     * 
     */
    public static final String SUGGESTED_TAG_NEIGHBORHOOD = "district";
    /**
     * 
     */
    public static final String SUGGESTED_SUFFIX_1 = " BR ";
    /**
     * 
     */
    public static final String SUGGESTED_SUFFIX_2 = "CR";
    /**
     * 
     */
    public static final String SUGGESTED_TAG_ITEM = "item";
    /**
     * 
     */
    public static final String SUGGESTED_TAG_STATE = "state";
    /*--------------------------------------  TAG REQUEST enriquecer -------------------------------------------------------------*/
    /**
     * 
     */
    public static final String ENRICH_ONY_LABEL = "wss";
    /**
     * 
     */
    public static final String ENRICH_XMLNS = UrlProvGeo.getUrlProvGeo()+"/WsSitidataStandar/wsSitidataStandar";
    /**
     * 
     */
    public static final String ENRICH_TAG_METHOD = "enrich";
    /**
     * 
     */
    public static final String ENRICH_TAG_ADDRESS = "address";
    /**
     * 
     */
    public static final String ENRICH_TAG_CITY = "city";
    /**
     * 
     */
    public static final String ENRICH_TAG_NEIGHBORHOOD = "district";
    /**
     * 
     */
    public static final String ENRICH_USUARIO = "user";
    /**
     * 
     */
    public static final String ENRICH_CLAVE = "password";
    /**
     * 
     */
    public static final String ENRICH_TAG_ITEM = "item";
    /**
     * 
     */
    public static final String ENRICH_TAG_STATE = "state";
}
