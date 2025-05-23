package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Clase Direccion Implementa Serialización.
 *
 * @author Deiver Rovira
 * @version	1.0
 * @version	1.1 - Modificado por: Direcciones face I Carlos Vilamil 2013-05-11
 * @version     1.2 - Modificado por: Nodo DTH 2015-09-30
 * @version     1.3 - Modificado por: Nodo DTH 2015-09-30
 * @version     1.4 - Geozonas 9 Inspira    2019-02-01
 */
public class Direccion implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal dirId;
    private String dirFormatoIgac;
    private String dirServinformacion;
    private String dirNostandar;
    private String dirOrigen;
    private BigDecimal dirConfiabilidad;
    private BigDecimal dirEstrato;
    private BigDecimal dirNivelSocioecono;
    private String dirManzanaCatastral;
    private String dirManzana;
    private String dirRevisar;
    private String dirActividadEconomica;
    private Date dirFechaCreacion;
    private String dirUsuarioCreacion;
    private Date dirFechaModificacion;
    private String dirUsuarioModificacion;
    private String dirComentarioNivelSocioEconomico;//Modificado fase 1 direcciones carlos villamil2012-12-14
    private List<DireccionAlterna> direccionAlternaList;
    private List<SubDireccion> subDireccionList;
    private List<Hhpp> hhppList;
    private Ubicacion ubicacion;
    private TipoDireccion tipoDireccion;
    private String nodoUno; //Modificado fase 1 direcciones carlos villamil 2013-12-23 V 1.1
    private String nodoDos; //Modificado fase 1 direcciones carlos villamil 2013-12-23 V 1.1
    private String nodoTres; //Modificado fase 1 direcciones carlos villamil 2013-12-23 V 1.1
    private String nodoCuatro;
    /**
     * Nodo DTH
     */
    private String nodoDth;
    private String nodoMovil;
    private String nodoFtth;
    private String nodoWifi;
    
//Inicio cambio version 1.4
    private String geoZonaUnifilar = null;
    private String geoZonaGponDiseniado = null;
    private String geoZonaMicroOndas = null;
    private String  geoZona3G= null;
    private String  geoZona4G= null;
    private String  geoZonaCoberturaCavs= null;
    private String  geoZonaCoberturaUltimaMilla= null;
    private String  geoZonaCurrier= null;
    private String  geoZona5G= null;
//fin cambio version 1.4    
    
    // <editor-fold defaultstate="collapsed" desc="Modificacion Direcciones face I Johnnatan Ortiz 2013-09-30">
    private String cx;
    private String cy;
    // </editor-fold>

    /**
     * Constructor.
     */
    public Direccion() {
    }

    /**
     * Constructor con parámetros.
     *
     * @param dirId
     */
    public Direccion(BigDecimal dirId) {
        this.dirId = dirId;
    }

    /**
     * Obtiene el Id.
     *
     * @return Entero con el Id.
     */
    public BigDecimal getDirId() {
        return dirId;
    }

    /**
     * Establece el Id.
     *
     * @param dirId Entero con el Id.
     */
    public void setDirId(BigDecimal dirId) {
        this.dirId = dirId;
    }

    /**
     * Obtiene la dirección con el formato IGAC.
     *
     * @return Cadena con el formato IGAC.
     */
    public String getDirFormatoIgac() {
        return dirFormatoIgac;
    }

    /**
     * Establece la dirección IGAC
     *
     * @param dirFormatoIgac Cadena con la dirección IGAC.
     */
    public void setDirFormatoIgac(String dirFormatoIgac) {
        this.dirFormatoIgac = dirFormatoIgac;
    }

    /**
     * Obtiene la dirección según Servinformación.
     *
     * @return Cadena con la dirección según serviinformación
     */
    public String getDirServinformacion() {
        return dirServinformacion;
    }

    /**
     * Establece la dirección según Servinformación.
     *
     * @param dirServinformacion Cadena con la dirección.
     */
    public void setDirServinformacion(String dirServinformacion) {
        this.dirServinformacion = dirServinformacion;
    }

    /**
     * Obtiene la dirección No estandarizada.
     *
     * @return Cadena con la dirección no estandarizada,
     */
    public String getDirNostandar() {
        return dirNostandar;
    }

    /**
     * Establece la dirección no estandarizada.
     *
     * @param dirNostandar Cadena con la dirección no estandarizada.
     */
    public void setDirNostandar(String dirNostandar) {
        this.dirNostandar = dirNostandar;
    }

    /**
     * Obtiene el origen.
     *
     * @return Cadena con el origen.
     */
    public String getDirOrigen() {
        return dirOrigen;
    }

    /**
     * Establece el origen.
     *
     * @param dirOrigen Cadena con el origen.
     */
    public void setDirOrigen(String dirOrigen) {
        this.dirOrigen = dirOrigen;
    }

    /**
     * Obtiene el nivel de confiabilidad.
     *
     * @return Entero con el nivel de confiabilidad.
     */
    public BigDecimal getDirConfiabilidad() {
        return dirConfiabilidad;
    }

    /**
     * Establece el nivel de confiabilidad.
     *
     * @param dirConfiabilidad Entero con el nivel de confiabilidad.
     */
    public void setDirConfiabilidad(BigDecimal dirConfiabilidad) {
        this.dirConfiabilidad = dirConfiabilidad;
    }

    /**
     * Obtiene el estato.
     *
     * @return Entero con el estrato.
     */
    public BigDecimal getDirEstrato() {
        return dirEstrato;
    }

    /**
     * Establece el estrato.
     *
     * @param dirEstrato Entero con el estrato.
     */
    public void setDirEstrato(BigDecimal dirEstrato) {
        this.dirEstrato = dirEstrato;
    }

    /**
     * Obtiene el nivel socio económico.
     *
     * @return Entero con el nivel socio económico.
     */
    public BigDecimal getDirNivelSocioecono() {
        return dirNivelSocioecono;
    }

    /**
     * Establece el nivel socio económico.
     *
     * @param dirNivelSocioecono Entero con el nivel socio económico.
     */
    public void setDirNivelSocioecono(BigDecimal dirNivelSocioecono) {
        this.dirNivelSocioecono = dirNivelSocioecono;
    }

    /**
     * Obtiene la manzana catastral.
     *
     * @return Cadena con la manzana catastral.
     */
    public String getDirManzanaCatastral() {
        return dirManzanaCatastral;
    }

    /**
     * Establece la manzana catastral.
     *
     * @param dirManzanaCatastral Cadena con la manzana catastral.
     */
    public void setDirManzanaCatastral(String dirManzanaCatastral) {
        this.dirManzanaCatastral = dirManzanaCatastral;
    }

    /**
     * Obtiene la manzana.
     *
     * @return Cadena con la manzana.
     */
    public String getDirManzana() {
        return dirManzana;
    }

    /**
     * Establece la manzana.
     *
     * @param dirManzana Cadena con la manzana.
     */
    public void setDirManzana(String dirManzana) {
        this.dirManzana = dirManzana;
    }

    /**
     * Obtiene la fecha de creación.
     *
     * @return Fecha de creación.
     */
    public Date getDirFechaCreacion() {
        return dirFechaCreacion;
    }

    /**
     * Establece la fecha de creación.
     *
     * @param dirFechaCreacion Fecha de creación.
     */
    public void setDirFechaCreacion(Date dirFechaCreacion) {
        this.dirFechaCreacion = dirFechaCreacion;
    }

    /**
     * Obtiene el usuario que realiza la creación.
     *
     * @return Cadena con el usuario que realiza la creación.
     */
    public String getDirUsuarioCreacion() {
        return dirUsuarioCreacion;
    }

    /**
     * Establece el usuario que realiza la creación.
     *
     * @param dirUsuarioCreacion Cadena con el usuario que realiza la creación.
     */
    public void setDirUsuarioCreacion(String dirUsuarioCreacion) {
        this.dirUsuarioCreacion = dirUsuarioCreacion;
    }

    /**
     * Obtiene la fecha de modificación.
     *
     * @return Fecha de modificación.
     */
    public Date getDirFechaModificacion() {
        return dirFechaModificacion;
    }

    /**
     * Establece la fecha de modificación.
     *
     * @param dirFechaModificacion Fecha de modificación.
     */
    public void setDirFechaModificacion(Date dirFechaModificacion) {
        this.dirFechaModificacion = dirFechaModificacion;
    }

    /**
     * Obtiene el usuario que realiza la modificación.
     *
     * @return Cadena con el usuario que realiza la modificación
     */
    public String getDirUsuarioModificacion() {
        return dirUsuarioModificacion;
    }

    /**
     * Establece el usuario que realiza la modificación.
     *
     * @param dirUsuarioModificacion Cadena con el usuario que realiza la
     * modificación.
     */
    public void setDirUsuarioModificacion(String dirUsuarioModificacion) {
        this.dirUsuarioModificacion = dirUsuarioModificacion;
    }

    /**
     * Obtiene si se debe verificar la dirección.
     *
     * @return Cadena con información de verificación de dirección.
     */
    public String getDirRevisar() {
        return dirRevisar;
    }

    /**
     * Establece si se debe verificar la dirección.
     *
     * @param dirRevisar Cadena con información de verificación de dirección.
     */
    public void setDirRevisar(String dirRevisar) {
        this.dirRevisar = dirRevisar;
    }

    /**
     * Obtiene una lista de direcciones alternas.
     *
     * @return Lista de objetos DireccionAlterna
     */
    public List<DireccionAlterna> getDireccionAlternaList() {
        return direccionAlternaList;
    }

    /**
     * Establece la lista de direcciones alternas.
     *
     * @param direccionAlternaList Lista de objetos DireccionAlterna
     */
    public void setDireccionAlternaList(List<DireccionAlterna> direccionAlternaList) {
        this.direccionAlternaList = direccionAlternaList;
    }

    /**
     * Obtiene una lista de subdirecciones.
     *
     * @return Lista de objetos SubDireccion
     */
    public List<SubDireccion> getSubDireccionList() {
        return subDireccionList;
    }

    /**
     * Establece una lista de subdirecciones.
     *
     * @param subDireccionList Lista de objetos SubDireccion
     */
    public void setSubDireccionList(List<SubDireccion> subDireccionList) {
        this.subDireccionList = subDireccionList;
    }

    /**
     * Obtiene una lista de hhpp.
     *
     * @return Lista de objetos Hhpp
     */
    public List<Hhpp> getHhppList() {
        return hhppList;
    }

    /**
     * Establece la lista de hhpp.
     *
     * @param hhppList Lista de objetos Hhpp
     */
    public void setHhppList(List<Hhpp> hhppList) {
        this.hhppList = hhppList;
    }

    /**
     * Obtiene la ubicación.
     *
     * @return Objeto Ubicacion con la información solicitada.
     */
    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    /**
     * Establece la ubicación.
     *
     * @param ubicacion Objeto Ubicacion con la información solicitada.
     */
    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    /**
     * Obtiene el tipo de dirección.
     *
     * @return Objeto TipoDireccion con la información solicitada.
     */
    public TipoDireccion getTipoDireccion() {
        return tipoDireccion;
    }

    /**
     * Establece el tipo dirección.
     *
     * @param tipoDireccion Objeto TipoDireccion con la información solicitada.
     */
    public void setTipoDireccion(TipoDireccion tipoDireccion) {
        this.tipoDireccion = tipoDireccion;
    }

    /**
     * Obtiene la actividad Económica.
     *
     * @return Cadena con la actividad económica.
     */
    public String getDirActividadEconomica() {
        return dirActividadEconomica;
    }

    /**
     * Establece la actividad económica.
     *
     * @param dirActividadEconomica Cadena con la actividad económica.
     */
    public void setDirActividadEconomica(String dirActividadEconomica) {
        this.dirActividadEconomica = dirActividadEconomica;
    }

//Inicio Modificado fase 1 direcciones carlos villamil2012-12-14
    /**
     * Obtiene el comentario del nivel socio económico.
     *
     * @return Cadena con el comentario del nivel socio económico.
     */
    public String getDirComentarioNivelSocioEconomico() {
        return dirComentarioNivelSocioEconomico;
    }

    /**
     * Establece el comentario del nivel socio económico.
     *
     * @param dirComentarioNivelSocioEconomico Cadena con el comentario del
     * nivel socio económico.
     */
    public void setDirComentarioNivelSocioEconomico(String dirComentarioNivelSocioEconomico) {
        this.dirComentarioNivelSocioEconomico = dirComentarioNivelSocioEconomico;
    }
//Inicio Modificado fase 1 direcciones carlos villamil2012-12-14

    /**
     * Obtiene información para auditoría
     *
     * @return Cadena con información para auditoría.
     */
    public String auditoria() {
        String auditoria = "Direccion:"
                + "dirId=" + dirId
                + ", dirFormatoIgac=" + dirFormatoIgac
                + ", dirServinformacion=" + dirServinformacion
                + ", dirNostandar=" + dirNostandar
                + ", dirOrigen=" + dirOrigen
                + ", dirConfiabilidad=" + dirConfiabilidad
                + ", dirEstrato=" + dirEstrato
                + ", dirNivelSocioecono=" + dirNivelSocioecono
                + ", dir_comentario_socioeconomico=" + dirComentarioNivelSocioEconomico
                + ", dirManzanaCatastral=" + dirManzanaCatastral
                + ", dirManzana=" + dirManzana
                + ", dirRevisar=" + dirRevisar
                + ", dirActividadEconomica=" + dirActividadEconomica;
        if (ubicacion != null) {
            auditoria = auditoria + ", ubicacionId=" + ubicacion.getUbiId().toString();
        }
        if (tipoDireccion != null) {
            auditoria = auditoria + ", tipoDireccion=" + tipoDireccion.getTdiId() + '.';
        }
        return auditoria;
    }

    /**
     * Sobre Escritura del método toString()
     *
     * @return Cadena con la información de la auditoría.
     */
    @Override
    public String toString() {
        return "Direccion:" + "dirId=" + dirId
                + ", dirFormatoIgac=" + dirFormatoIgac
                + ", dirServinformacion=" + dirServinformacion
                + ", dirNostandar=" + dirNostandar
                + ", dirOrigen=" + dirOrigen
                + ", dirConfiabilidad=" + dirConfiabilidad
                + ", dirEstrato=" + dirEstrato
                + ", dirNivelSocioecono=" + dirNivelSocioecono
                + ", dirManzanaCatastral=" + dirManzanaCatastral
                + ", dirManzana=" + dirManzana
                + ", dirRevisar=" + dirRevisar
                + ", dirActividadEconomica=" + dirActividadEconomica
                + ", ubicacionId=" + ubicacion.getUbiId().toString()
                + ", dirComentarioNivelSocioEconomico=" + dirComentarioNivelSocioEconomico +//Modificado fase 1 direcciones carlos villamil2012-12-14
                ", tipoDireccion=" + tipoDireccion.getTdiId().toString() + '.';
    }

    /**
     * @return the nodoUno v 1.1
     */
    public String getNodoUno() {
        return nodoUno;
    }

    /**
     * @param nodoUno the nodoUno to set v 1.1
     */
    public void setNodoUno(String nodoUno) {
        this.nodoUno = nodoUno;
    }

    /**
     * @return the nodoDos v 1.1
     */
    public String getNodoDos() {
        return nodoDos;
    }

    /**
     * @param nodoDos the nodoDos to set v 1.1
     */
    public void setNodoDos(String nodoDos) {
        this.nodoDos = nodoDos;
    }

    /**
     * @return the nodoTres v 1.1
     */
    public String getNodoTres() {
        return nodoTres;
    }

    /**
     * @param nodoTres the nodoTres to set v 1.1
     */
    public void setNodoTres(String nodoTres) {
        this.nodoTres = nodoTres;
    }

    public String getNodoCuatro() {
        return nodoCuatro;
    }

    /**
     * @param nodoCuatro the nodoTres to set v 1.1
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

    // <editor-fold defaultstate="collapsed" desc="Modificacion Direcciones face I Johnnatan Ortiz 2013-09-30">
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

    // </editor-fold>
    //Inicio cambio version 1.4
    public String getGeoZonaUnifilar() {
        return geoZonaUnifilar;
    }

    public void setGeoZonaUnifilar(String geoZonaUnifilar) {
        this.geoZonaUnifilar = geoZonaUnifilar;
    }

    public String getGeoZonaGponDiseniado() {
        return geoZonaGponDiseniado;
    }

    public void setGeoZonaGponDiseniado(String geoZonaGponDiseniado) {
        this.geoZonaGponDiseniado = geoZonaGponDiseniado;
    }

    public String getGeoZonaMicroOndas() {
        return geoZonaMicroOndas;
    }

    public void setGeoZonaMicroOndas(String geoZonaMicroOndas) {
        this.geoZonaMicroOndas = geoZonaMicroOndas;
    }

    public String getGeoZona3G() {
        return geoZona3G;
    }

    public void setGeoZona3G(String geoZona3G) {
        this.geoZona3G = geoZona3G;
    }

    public String getGeoZona4G() {
        return geoZona4G;
    }

    public void setGeoZona4G(String geoZona4G) {
        this.geoZona4G = geoZona4G;
    }

    public String getGeoZonaCoberturaCavs() {
        return geoZonaCoberturaCavs;
    }

    public void setGeoZonaCoberturaCavs(String geoZonaCoberturaCavs) {
        this.geoZonaCoberturaCavs = geoZonaCoberturaCavs;
    }

    public String getGeoZonaCoberturaUltimaMilla() {
        return geoZonaCoberturaUltimaMilla;
    }

    public void setGeoZonaCoberturaUltimaMilla(String geoZonaCoberturaUltimaMilla) {
        this.geoZonaCoberturaUltimaMilla = geoZonaCoberturaUltimaMilla;
    }

    public String getGeoZonaCurrier() {
        return geoZonaCurrier;
    }

    public void setGeoZonaCurrier(String geoZonaCurrier) {
        this.geoZonaCurrier = geoZonaCurrier;
    }

    public String getGeoZona5G() {
        return geoZona5G;
    }

    public void setGeoZona5G(String geoZona5G) {
        this.geoZona5G = geoZona5G;
    }
    //Fin cambio version 1.4
    
}
