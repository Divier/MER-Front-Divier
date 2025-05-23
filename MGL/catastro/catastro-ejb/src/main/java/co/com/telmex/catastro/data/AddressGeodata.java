package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Representa los parámetros recibidos de WebService
 * Implementa Serialización
 *
 * @author 	Jose Luis Caicedo G. (* Modificado por: Direcciones fase I Carlos Vilamil 2012-12-11)
 * @version	1.0
 * @version     1.1 - Modificado por: Nodo DTH 2015-09-30
 * @version     1.2 - Modificado por: Nueve nuebas gozonas Inspira 2019-01-31 Carlos Villamil HITSS
 */
@XmlRootElement(name="addressGeodata")
public class AddressGeodata implements Serializable {

    @XmlElement
    public String test;
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
    private String Valplaca = null;
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
//TODO: Se agregan los nuevos campos
    @XmlElement
    private String estrato = null;
    @XmlElement
    private String nodo1 = null;
    @XmlElement
    private String nodo2 = null;
    @XmlElement
    private String nodo3 = null;
    //DTH

    //FTTX
    @XmlElement
    private String nodo4 = null;
    private String nodoDth = null;
    @XmlElement
    private String nodoMovil = null;
    @XmlElement
    private String nodoFtth = null;
    @XmlElement
    private String nodoWifi = null;
//Inicio cambio version 1.2
    @XmlElement
    private String geoZonaUnifilar = null;
    @XmlElement
    private String geoZonaGponDiseniado = null;
    @XmlElement
    private String geoZonaMicroOndas = null;
    @XmlElement
    private String  geoZona3G= null;
    @XmlElement
    private String  geoZona4G= null;
    @XmlElement
    private String  geoZonaCoberturaCavs= null;
    @XmlElement
    private String  geoZonaCoberturaUltimaMilla= null;
    @XmlElement
    private String  geoZonaCurrier= null;
    @XmlElement
    private String  geoZona5G= null;

    private String manzana = null;
    @XmlElement
    private String coddanedpto = null;
    @XmlElement
    private String coddanemcpio = null;
    @XmlElement
    private String acteconomica = null;
//End
    @XmlElement
    private String estado = null;
    @XmlElement
    private String mensaje = null;
    @XmlElement
    private String zona = null;
    @XmlElement
    private String traslate = null;
    @XmlElement
    private String existe = null;
    @XmlElement
    private String categoria = null;
    @XmlElement
    private String confiability = null;
    @XmlElement
    private String recomendacion = null;
    @XmlElement
    private List<AddressAggregated> addressAgregate;
//Incio Cambios Face I Carlos Villamil 2012-12-11
    @XmlElement
    private String zipCode = null;
    @XmlElement
    private String comentEconomicLevel = null;
//FIN Cambios Face I Carlos Villamil 2012-12-11

    /**
     *
     * @return
     */
    
  
    public String getConfiability() {
        return confiability;
    }

    /**
     *
     * @param confiability
     */
    public void setConfiability(String confiability) {
        this.confiability = confiability;
    }

    /**
     *
     * @return
     */
    public List<AddressAggregated> getAddressAggregated() {
        return addressAgregate;
    }

    /**
     *
     * @param addressAgregate
     */
    public void setAddressAggregated(List<AddressAggregated> addressAgregate) {
        this.addressAgregate = addressAgregate;
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
    public String getExiste() {
        return existe;
    }

    /**
     *
     * @param existe
     */
    public void setExiste(String existe) {
        this.existe = existe;
    }

    /**
     *
     * @return
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     *
     * @param categoria
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     *
     * @return
     */
    public String getRecomendacion() {
        return recomendacion;
    }

    /**
     *
     * @param recomendacion
     */
    public void setRecomendacion(String recomendacion) {
        this.recomendacion = recomendacion;
    }

    /**
     *
     * @return
     */
    public String getValplaca() {
        return Valplaca;
    }

    /**
     *
     * @param Valplaca
     */
    public void setValplaca(String Valplaca) {
        this.Valplaca = Valplaca;
    }

    /**
     *
     * @return
     */
    public String getAmbigua() {
        return ambigua;
    }

    /**
     *
     * @param ambigua
     */
    public void setAmbigua(String ambigua) {
        this.ambigua = ambigua;
    }

    /**
     *
     * @return
     */
    public String getBarrio() {
        return barrio;
    }

    /**
     *
     * @param barrio
     */
    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    /**
     *
     * @return
     */
    public String getBartrad() {
        return bartrad;
    }

    /**
     *
     * @param bartrad
     */
    public void setBartrad(String bartrad) {
        this.bartrad = bartrad;
    }

    /**
     *
     * @return
     */
    public String getCoddir() {
        return coddir;
    }

    /**
     *
     * @param coddir
     */
    public void setCoddir(String coddir) {
        this.coddir = coddir;
    }

    /**
     *
     * @return
     */
    public String getCodencont() {
        return codencont;
    }

    /**
     *
     * @param codencont
     */
    public void setCodencont(String codencont) {
        this.codencont = codencont;
    }

    /**
     *
     * @return
     */
    public String getCx() {
        return cx;
    }

    /**
     *
     * @param cx
     */
    public void setCx(String cx) {
        this.cx = cx;
    }

    /**
     *
     * @return
     */
    public String getCy() {
        return cy;
    }

    /**
     *
     * @param cy
     */
    public void setCy(String cy) {
        this.cy = cy;
    }

    /**
     *
     * @return
     */
    public String getDiralterna() {
        return diralterna;
    }

    /**
     *
     * @param diralterna
     */
    public void setDiralterna(String diralterna) {
        this.diralterna = diralterna;
    }

    /**
     *
     * @return
     */
    public String getDirtrad() {
        return dirtrad;
    }

    /**
     *
     * @param dirtrad
     */
    public void setDirtrad(String dirtrad) {
        this.dirtrad = dirtrad;
    }

    /**
     *
     * @return
     */
    public String getEstado() {
        return estado;
    }

    /**
     *
     * @param estado
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     *
     * @return
     */
    public String getFuente() {
        return fuente;
    }

    /**
     *
     * @param fuente
     */
    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    /**
     *
     * @return
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     *
     * @param identificador
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     *
     * @return
     */
    public String getLocalidad() {
        return localidad;
    }

    /**
     *
     * @param localidad
     */
    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    /**
     *
     * @return
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     *
     * @param mensaje
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     *
     * @return
     */
    public String getNivsocio() {
        return nivsocio;
    }

    /**
     *
     * @param nivsocio
     */
    public void setNivsocio(String nivsocio) {
        this.nivsocio = nivsocio;
    }

    /**
     *
     * @return
     */
    public String getValagreg() {
        return valagreg;
    }

    /**
     *
     * @param valagreg
     */
    public void setValagreg(String valagreg) {
        this.valagreg = valagreg;
    }

    /**
     *
     * @return
     */
    public String getZona() {
        return zona;
    }

    /**
     *
     * @param zona
     */
    public void setZona(String zona) {
        this.zona = zona;
    }

    /**
     *
     * @return
     */
    public String getEstrato() {
        return estrato;
    }

    /**
     *
     * @param estrato
     */
    public void setEstrato(String estrato) {
        this.estrato = estrato;
    }

    /**
     *
     * @return
     */
    public String getNodo1() {
        return nodo1;
    }

    /**
     *
     * @param nodo1
     */
    public void setNodo1(String nodo1) {
        this.nodo1 = nodo1;
    }

    /**
     *
     * @return
     */
    public String getNodo2() {
        return nodo2;
    }

    /**
     *
     * @param nodo2
     */
    public void setNodo2(String nodo2) {
        this.nodo2 = nodo2;
    }

    /**
     *
     * @return
     */
    public String getNodo3() {
        return nodo3;
    }

    /**
     *
     * @param nodo3
     */
    public void setNodo3(String nodo3) {
        this.nodo3 = nodo3;
    }


    public String getNodo4() {
        return nodo4;
    }

    /**
     *
     * @param nodo4
     */
    public void setNodo4(String nodo4) {
        this.nodo4 = nodo4;
    }

    /**
     * Get atributo NodoDth
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
     *
     * @return
     */
    public String getActeconomica() {
        return acteconomica;
    }

    /**
     *
     * @param acteconomica
     */
    public void setActeconomica(String acteconomica) {
        this.acteconomica = acteconomica;
    }

    /**
     *
     * @return
     */
    public String getCoddanedpto() {
        return coddanedpto;
    }

    /**
     *
     * @param coddanedpto
     */
    public void setCoddanedpto(String coddanedpto) {
        this.coddanedpto = coddanedpto;
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
    public String getManzana() {
        return manzana;
    }

    /**
     *
     * @param manzana
     */
    public void setManzana(String manzana) {
        this.manzana = manzana;
    }

//Incio Cambios Face I Carlos Villamil 2012-12-11
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
        if (zipCode == null) {
            return "0000";
        }
        if (zipCode.equals("")) {
            return "0000";
        }
        if (zipCode.length() > 6) {
            return "0000";
        }
        return zipCode.substring(4);
    }

    /**
     *
     * @return
     */
    public String getZipCodeState() {
        if (zipCode == null) {
            return "00";
        }
        if (zipCode.equals("")) {
            return "00";
        }
        if (zipCode.length() > 2) {
            return "00";
        }
        return zipCode.substring(0, 2);
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

//Inicio cambio version 1.2
    /**
     *
     * @return
     */    
    public String getGeoZonaUnifilar() {
        return geoZonaUnifilar;
    }

    public void setGeoZonaUnifilar(String geoZonaUnifilar) {
        this.geoZonaUnifilar = geoZonaUnifilar;
    }
    /**
     *
     * @return
     */
    public String getGeoZonaGponDiseniado() {
        return geoZonaGponDiseniado;
    }
    /**
     *
     * @param geoZonaGponDiseniado
     */
    public void setGeoZonaGponDiseniado(String geoZonaGponDiseniado) {
        this.geoZonaGponDiseniado = geoZonaGponDiseniado;
    }
    /**
     *
     * @return
     */
    public String getGeoZonaMicroOndas() {
        return geoZonaMicroOndas;
    }
    /**
     *
     * @param geoZonaMicroOndas
     */
    public void setGeoZonaMicroOndas(String geoZonaMicroOndas) {
        this.geoZonaMicroOndas = geoZonaMicroOndas;
    }
    /**
     *
     * @return
     */
    public String getGeoZona3G() {
        return geoZona3G;
    }
    /**
     *
     * @param geoZona3G
     */
    public void setGeoZona3G(String geoZona3G) {
        this.geoZona3G = geoZona3G;
    }
    /**
     *
     * @return
     */
    public String getGeoZona4G() {
        return geoZona4G;
    }
    /**
     *
     * @param geoZona4G
     */
    public void setGeoZona4G(String geoZona4G) {
        this.geoZona4G = geoZona4G;
    }
    /**
     *
     * @return
     */
    public String getGeoZonaCoberturaCavs() {
        return geoZonaCoberturaCavs;
    }
    /**
     *
     * @param geoZonaCoberturaCavs
     */
    public void setGeoZonaCoberturaCavs(String geoZonaCoberturaCavs) {
        this.geoZonaCoberturaCavs = geoZonaCoberturaCavs;
    }
    /**
     *
     * @return
     */
    public String getGeoZonaCoberturaUltimaMilla() {
        return geoZonaCoberturaUltimaMilla;
    }
    /**
     *
     * @param geoZonaCoberturaUltimaMilla
     */
    public void setGeoZonaCoberturaUltimaMilla(String geoZonaCoberturaUltimaMilla) {
        this.geoZonaCoberturaUltimaMilla = geoZonaCoberturaUltimaMilla;
    }
    /**
     *
     * @return
     */
    public String getGeoZonaCurrier() {
        return geoZonaCurrier;
    }
    /**
     *
     * @param geoZonaCurrier
     */
    public void setGeoZonaCurrier(String geoZonaCurrier) {
        this.geoZonaCurrier = geoZonaCurrier;
    }
    /**
     *
     * @return
     */
    public String getGeoZona5G() {
        return geoZona5G;
    }
    /**
     *
     * @param geoZona5G
     */
    public void setGeoZona5G(String geoZona5G) {
        this.geoZona5G = geoZona5G;
    }

//fin cambio version 1.2
    
    
   public static AddressGeodata instanciaVacia(){
       AddressGeodata geo = new AddressGeodata();
        geo.setIdentificador("");    
        geo.setDirtrad("");    
        geo.setBartrad("");    
        geo.setCoddir("");    
        geo.setCodencont("");    
        geo.setFuente("");    
        geo.setDiralterna("");    
        geo.setAmbigua("");    
        geo.setValagreg("");    
        geo.setValplaca("");    
        geo.setBarrio("");    
        geo.setLocalidad("");    
        geo.setNivsocio("");    
        geo.setCx("");    
        geo.setCy("");    
        geo.setEstrato("");    
        geo.setNodo1("");    
        geo.setNodo2("");    
        geo.setNodo3("");    //DTH
        geo.setNodo4("");    //FTTX
        geo.setNodoDth("");
        geo.setNodoMovil("");    
        geo.setNodoFtth("");    
        geo.setNodoWifi("");    
        geo.setGeoZonaUnifilar("");    
        geo.setGeoZonaGponDiseniado("");    
        geo.setGeoZonaMicroOndas("");    
        geo.setGeoZona3G("");    
        geo.setGeoZona4G("");    
        geo.setGeoZonaCoberturaCavs("");    
        geo.setGeoZonaCoberturaUltimaMilla("");    
        geo.setGeoZonaCurrier("");    
        geo.setGeoZona5G("");
        geo.setManzana("");    
        geo.setCoddanedpto("");    
        geo.setCoddanemcpio("");    
        geo.setActeconomica("");    
        geo.setEstado("");    
        geo.setMensaje("");    
        geo.setZona("");    
        geo.setTraslate("");    
        geo.setExiste("");    
        geo.setCategoria("");    
        geo.setConfiability("");    
        geo.setRecomendacion("");    
        geo.setZipCode("");    
        geo.setComentEconomicLevel("");
       return geo;
   }
    
}