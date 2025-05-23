/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.client.https.ConstansClient;
import co.com.claro.mgl.client.https.ConsumoGenerico;
import co.com.claro.mgl.dao.impl.cm.ArrendatarioMglDaoImpl;
import co.com.claro.mgl.dtos.CmtFiltroConsultaArrendatarioDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ArrendatarioAuditoriaMgl;
import co.com.claro.mgl.jpa.entities.ArrendatarioMgl;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtExtendidaTecnologiaMgl;
import co.com.claro.mgl.utils.UtilsCMAuditoria;
import co.com.claro.ofscCapacity.activityBookingOptions.GetActivityBookingResponses;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author bocanegravm
 */
public class ArrendatarioMglManager {

    /**
     * Autor: victor bocanegra Metodo para consultar todos los arrendatarios
     * activos de la BD
     *
     * @return resulList
     * @throws ApplicationException
     */
    public List<ArrendatarioMgl> findAll() throws ApplicationException {
        List<ArrendatarioMgl> resulList;
        ArrendatarioMglDaoImpl dao = new ArrendatarioMglDaoImpl();
        resulList = dao.findAll();
        return resulList;
    }

    /**
     * Autor: victor bocanegra Metodo para crear un arrendatario en la BD
     *
     * @param arrendatarioMgl arrendatario a crear
     * @param mUser usuario que crea el registro
     * @param mPerfil perfil del usuario que crea el registro
     * @return ArrendatarioMgl
     * @throws ApplicationException
     */
    public ArrendatarioMgl create(ArrendatarioMgl arrendatarioMgl, String mUser, int mPerfil)
            throws ApplicationException {

        ArrendatarioMglDaoImpl dao = new ArrendatarioMglDaoImpl();
        return dao.createCm(arrendatarioMgl, mUser, mPerfil);

    }

    /**
     * Autor: victor bocanegra Metodo para actualizar un arrendatario en la BD
     *
     * @param arrendatarioMgl arrendatario a modificar
     * @param mUser usuario que modifica el registro
     * @param mPerfil perfil del usuario que modifica el registro
     * @return ArrendatarioMgl
     * @throws ApplicationException
     */
    public ArrendatarioMgl update(ArrendatarioMgl arrendatarioMgl, String mUser, int mPerfil)
            throws ApplicationException {

        ArrendatarioMglDaoImpl dao = new ArrendatarioMglDaoImpl();
        return dao.updateCm(arrendatarioMgl, mUser, mPerfil);

    }

    /**
     * Autor: victor bocanegra Metodo para borrar logicamente un arrendatario en
     * la BD
     *
     * @param arrendatarioMgl arrendatario a borrar
     * @param mUser usuario que borra el registro
     * @param mPerfil perfil del usuario que borra el registro
     * @return boolean
     * @throws ApplicationException
     */
    public boolean delete(ArrendatarioMgl arrendatarioMgl, String mUser, int mPerfil)
            throws ApplicationException {

        ArrendatarioMglDaoImpl dao = new ArrendatarioMglDaoImpl();
        return dao.deleteCm(arrendatarioMgl, mUser, mPerfil);

    }
    
     /**
     * Autor: victor bocanegra Metodo consultar la auditoria de un registro
     *
     * @param arrendatarioMgl arrendatario a consultar
     * @return List<AuditoriaDto>
     * @throws ApplicationException
     */
    public List<AuditoriaDto> construirAuditoria(ArrendatarioMgl arrendatarioMgl)
            throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException {
        UtilsCMAuditoria<ArrendatarioMgl, ArrendatarioAuditoriaMgl> utilsCMAuditoria
                = new UtilsCMAuditoria<>();
        List<AuditoriaDto> listAuditoriaDto 
                = utilsCMAuditoria.construirAuditoria(arrendatarioMgl);
        return listAuditoriaDto;

    }
    
    /**
     * Autor: victor bocanegra Metodo para consultar el 
     * arrendatario por centro poblado
     *
     * @return List<ArrendatarioMgl>
     * @throws ApplicationException
     */
    public List<ArrendatarioMgl> findArrendatariosByCentro
        (GeograficoPoliticoMgl centro) throws ApplicationException {

        ArrendatarioMglDaoImpl dao = new ArrendatarioMglDaoImpl();
        return dao.findArrendatariosByCentro(centro);
    }
    
    /**
     * Autor: victor bocanegra Metodo para consultar cuadrantes
     * @param nodo
     * @return GetActivityBookingResponses
     * @throws ApplicationException
     */
    public GetActivityBookingResponses consultarCuadrante(NodoMgl nodo)
            throws ApplicationException {

        GetActivityBookingResponses responses = null;

        try {

            String BASE_URI_REST_GET_ZONAS;
            ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
            CmtExtendidaTecnologiaManager extendidaTecnologiaMglManager = new CmtExtendidaTecnologiaManager();

            BASE_URI_REST_GET_ZONAS = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_REST_GET_ZONAS)
                    .iterator().next().getParValor();
            
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String fechaDia =  df.format(new Date());
            String nombreRed = "";
            String codNodo = "";
            String comNodo = "";
            
            
            CmtExtendidaTecnologiaMgl mglExtendidaTecnologia = 
                    extendidaTecnologiaMglManager.findBytipoTecnologiaObj(nodo.getNodTipo());

            if (mglExtendidaTecnologia != null
                    && mglExtendidaTecnologia.getIdExtTec() != null) {
                if (mglExtendidaTecnologia.getNomTecnologiaOFSC() != null) {
                    nombreRed = mglExtendidaTecnologia.getNomTecnologiaOFSC().getAbreviatura();
                }
            }
            
            if (nodo.getNodId() != null) {
                codNodo = nodo.getNodCodigo();
                comNodo = nodo.getComId() != null ? nodo.getComId().getCodigoRr() : "";
            }

            URL url = new URL(BASE_URI_REST_GET_ZONAS
                    + "dates="+fechaDia+"&determineAreaByWorkZone=true"
                            + "&determineCategory=true&XA_Red="+nombreRed+""
                            + "&XA_Idcity="+comNodo+"&Node="+codNodo+""
                            + "&activityType=INT&estimateDuration=false"
                            + "&estimateTravelTime=false&XA_WorkOrderSubtype=INBAS");
            
            System.err.println(url.toString());

            String userPwdAutorization = parametrosMglManager.findByAcronimo(ConstansClient.CLAVE_AUTORIZATION)
                    .iterator().next().getParValor();
            String comAutorization = "Basic" + " " + userPwdAutorization;

            ConsumoGenerico consumoGenerico = new ConsumoGenerico(ConstansClient.TYPE_METHOD, url,
                    comAutorization, null, null, null);

            responses = consumoGenerico.consumirServicioZonas();

        } catch (ApplicationException | MalformedURLException ex) {
            throw new ApplicationException(ex.getMessage());
        }
        return responses;

    }
    
    /**
     * Autor: victor bocanegra Metodo para consultar un arrendatario por
     * centro-ciudad y depto
     *
     * @param centro
     * @param ciudad
     * @param dpto
     * @return ArrendatarioMgl
     * @throws ApplicationException
     */
    public ArrendatarioMgl findArrendatariosByCentroAndCityAndDpto(GeograficoPoliticoMgl centro, GeograficoPoliticoMgl ciudad,
            GeograficoPoliticoMgl dpto) throws ApplicationException {

        ArrendatarioMglDaoImpl dao = new ArrendatarioMglDaoImpl();
        return dao.findArrendatariosByCentroAndCityAndDpto(centro, ciudad, dpto);
    }
    
    /**
     * Autor: victor bocanegra Metodo para consultar un arrendatario por centro-
     * nombre de arrendatario y cuadrante
     *
     * @param centro
     * @param nombreArrenda
     * @param cuadrante
     * @return ArrendatarioMgl
     * @throws ApplicationException
     */
    public ArrendatarioMgl findByCentroAndNombreArrendaAndCuadrante
        (GeograficoPoliticoMgl centro, String nombreArrenda,
            String cuadrante) throws ApplicationException {

        ArrendatarioMglDaoImpl dao = new ArrendatarioMglDaoImpl();
        return dao.findByCentroAndNombreArrendaAndCuadrante(centro, nombreArrenda, cuadrante);
    }
        
    /**
     * Autor: victor bocanegra Metodo para consultar un arrendatario por Id
     *
     * @param id
     * @return ArrendatarioMgl
     * @throws ApplicationException
     */
    public ArrendatarioMgl findArrendatariosById(BigDecimal id) 
            throws ApplicationException {

        ArrendatarioMglDaoImpl dao = new ArrendatarioMglDaoImpl();
        return dao.find(id);
    }
    
    /**
     * Autor: Jeniffer Corredor
     * Método para consultar los Arrendatarios según filtros de consulta
     *
     * @param filtro
     * @return ArrendatarioMgl
     * @throws ApplicationException
     */
    public List<ArrendatarioMgl> findArrendatariosByFiltro(CmtFiltroConsultaArrendatarioDto filtro) 
            throws ApplicationException {
                ArrendatarioMglDaoImpl dao = new ArrendatarioMglDaoImpl();
        return dao.findArrendatariosByFiltro(filtro);
    }
    

}
