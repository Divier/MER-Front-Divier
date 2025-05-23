/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.cmas400.ejb.request.RequestDataConstructorasMgl;
import co.com.claro.cmas400.ejb.respons.ResponseConstructorasList;
import co.com.claro.cmas400.ejb.respons.ResponseDataConstructoras;
import co.com.claro.mgl.dao.impl.cm.CmtCompaniaMglDaoImpl;
import co.com.claro.mgl.dtos.FiltroConsultaCompaniasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCompaniaAuditoriaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCompaniaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoCompaniaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.CmtUtilidadesCM;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.UtilsCMAuditoria;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CmtCompaniaMglManager {
    
    private static final Logger LOGGER = LogManager.getLogger(CmtCompaniaMglManager.class);

    public List<CmtCompaniaMgl> findAll() throws ApplicationException {
        List<CmtCompaniaMgl> resulList;
        CmtCompaniaMglDaoImpl cmtCompaniaMglDaoImpl = new CmtCompaniaMglDaoImpl();
        resulList = cmtCompaniaMglDaoImpl.findAll();
        return resulList;
    }

    public List<CmtCompaniaMgl> findByTipoCompania(CmtTipoCompaniaMgl tipoCompania) throws ApplicationException {
        CmtCompaniaMglDaoImpl cmtCompaniaMglDaoImpl = new CmtCompaniaMglDaoImpl();
        return cmtCompaniaMglDaoImpl.findByTipoCompania(tipoCompania);
    }

    public CmtCompaniaMgl create(CmtCompaniaMgl cmtCompaniaMgl, String usuario, int perfil) throws ApplicationException {
        CmtCompaniaMglDaoImpl cmtCompaniaMglDaoImpl = new CmtCompaniaMglDaoImpl();
        CmtTablasBasicasRRMglManager cmtTablasBasicasRRMglManager = new CmtTablasBasicasRRMglManager();
        cmtCompaniaMgl.setAbreviatura(CmtUtilidadesCM.calcularAbreviatura(cmtCompaniaMgl.getNombreCompania()));
        List<CmtCompaniaMgl> listCmtCompaniaMgl = cmtCompaniaMglDaoImpl.findByCoderr(cmtCompaniaMgl);
        if (listCmtCompaniaMgl == null || listCmtCompaniaMgl.isEmpty()) {
            if (cmtTablasBasicasRRMglManager.companiaCreate(cmtCompaniaMgl,usuario)) {
                return cmtCompaniaMglDaoImpl.createCm(cmtCompaniaMgl, usuario, perfil);
            } else {
                throw new ApplicationException("No se pudo crear el registro "
                        + cmtCompaniaMgl.getNombreCompania()
                        + " en RR, de tipo "
                        + cmtCompaniaMgl.getTipoCompaniaObj().getNombreTipo());
            }
        } else {
            throw new ApplicationException("El codigo digitado ya existe para la compañia: " + listCmtCompaniaMgl.get(0).getNombreCompania());
        }
    }

    public CmtCompaniaMgl update(CmtCompaniaMgl cmtCompaniaMgl, String usuario, int perfil) throws ApplicationException {
        CmtCompaniaMglDaoImpl cmtCompaniaMglDaoImpl = new CmtCompaniaMglDaoImpl();
        CmtTablasBasicasRRMglManager cmtTablasBasicasRRMglManager = new CmtTablasBasicasRRMglManager();
        if (cmtTablasBasicasRRMglManager.companiaUpdate(cmtCompaniaMgl,usuario)) {
            return cmtCompaniaMglDaoImpl.updateCm(cmtCompaniaMgl, usuario, perfil);
        } else {
            throw new ApplicationException("No se pudo crear el registro "
                    + cmtCompaniaMgl.getNombreCompania()
                    + " en RR, de tipo "
                    + cmtCompaniaMgl.getTipoCompaniaObj().getNombreTipo());
        }
    }

    public boolean delete(CmtCompaniaMgl cmtCompaniaMgl, String usuario, int perfil) throws ApplicationException {
        CmtCompaniaMglDaoImpl cmtCompaniaMglDaoImpl = new CmtCompaniaMglDaoImpl();
        CmtTablasBasicasRRMglManager cmtTablasBasicasRRMglManager = new CmtTablasBasicasRRMglManager();
        if (cmtTablasBasicasRRMglManager.companiaDelete(cmtCompaniaMgl,usuario)) {
            return cmtCompaniaMglDaoImpl.deleteCm(cmtCompaniaMgl, usuario, perfil);
        } else {
            throw new ApplicationException("No se pudo crear el registro "
                    + cmtCompaniaMgl.getNombreCompania()
                    + " en RR, de tipo "
                    + cmtCompaniaMgl.getTipoCompaniaObj().getNombreTipo());
        }
    }

    public CmtCompaniaMgl findById(CmtCompaniaMgl cmtCompaniaMgl) throws ApplicationException {
        CmtCompaniaMglDaoImpl cmtCompaniaMglDaoImpl = new CmtCompaniaMglDaoImpl();
        return cmtCompaniaMglDaoImpl.find(cmtCompaniaMgl.getCompaniaId());
    }

    public CmtCompaniaMgl findById(BigDecimal id) throws ApplicationException {
        CmtCompaniaMglDaoImpl cmtCompaniaMglDaoImpl = new CmtCompaniaMglDaoImpl();
        return cmtCompaniaMglDaoImpl.find(id);
    }

    public List<CmtCompaniaMgl> findByMunicipioByTipeCompany(GeograficoPoliticoMgl municipio,
            CmtTipoCompaniaMgl tipoCompania) throws ApplicationException {
        CmtCompaniaMglDaoImpl cmtCompaniaMglDaoImpl = new CmtCompaniaMglDaoImpl();
        //TODO: Fase 2 Proyecto implementar compañias para varias ciudades.
        return cmtCompaniaMglDaoImpl.findByTipoCompania(tipoCompania);
    }

    public List<CmtCompaniaMgl> findByfiltro(FiltroConsultaCompaniasDto filtro, boolean ordenarPorCodigo) throws ApplicationException {
        CmtCompaniaMglDaoImpl cmtCompaniaMglDaoImpl = new CmtCompaniaMglDaoImpl();
        return cmtCompaniaMglDaoImpl.findByfiltro(filtro,ordenarPorCodigo);
    }
    
    /**
     * *Victor Bocanegra Metodo para conseguir las compañias por los filtros de
     * la tabla y paginado
     *
     * @param paginaSelected
     * @param maxResults
     * @param filtro
     * @param ordenarPorCodigo
     * @return List<CmtCompaniaMgl>
     * @throws ApplicationException
     */
    public List<CmtCompaniaMgl> findByfiltroAndPaginado(int paginaSelected,
            int maxResults, FiltroConsultaCompaniasDto filtro, boolean ordenarPorCodigo)
            throws ApplicationException {

        CmtCompaniaMglDaoImpl cmtCompaniaMglDaoImpl = new CmtCompaniaMglDaoImpl();

        int firstResult = 0;
        if (paginaSelected > 0) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        return cmtCompaniaMglDaoImpl.findByfiltroAndPaginado(firstResult, maxResults, filtro, ordenarPorCodigo);
    }
    
    /**
     * *Victor Bocanegra Metodo para contar las compañias por los filtros de la
     * tabla
     *
     * @param filtro
     * @param ordenarPorCodigo
     * @return Long
     * @throws ApplicationException
     */
    public Long countByCompaFiltro(FiltroConsultaCompaniasDto filtro, boolean ordenarPorCodigo)
            throws ApplicationException {
        CmtCompaniaMglDaoImpl cmtCompaniaMglDaoImpl = new CmtCompaniaMglDaoImpl();
        return cmtCompaniaMglDaoImpl.countByCompaFiltro(filtro, ordenarPorCodigo);
    }

    public List<AuditoriaDto> construirAuditoria(CmtCompaniaMgl cmtCompaniaMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        UtilsCMAuditoria<CmtCompaniaMgl, CmtCompaniaAuditoriaMgl> utilsCMAuditoria = new UtilsCMAuditoria<>();
        List<AuditoriaDto> listAuditoriaDto = utilsCMAuditoria.construirAuditoria(cmtCompaniaMgl);
        return listAuditoriaDto;

    }

    public String buscarUltimoCodigoNumerico(CmtTipoCompaniaMgl tipoCompaniaMgl) throws ApplicationException {
                CmtCompaniaMglDaoImpl cmtCompaniaMglDaoImpl = new CmtCompaniaMglDaoImpl();
                return cmtCompaniaMglDaoImpl.buscarUltimoCodigoNumerico(tipoCompaniaMgl);
    }
    
    /**
     * Autor: Victor Bocanegra Metodo encargado de consultar los registros de
     * constructoras en la tabla CMT_COMPANIAS.
     *
     * @param request
     * @return ResponseConstructorasList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public ResponseConstructorasList constructorasQueryMgl(
            RequestDataConstructorasMgl request) throws ApplicationException {

        CmtCompaniaMglDaoImpl dao = new CmtCompaniaMglDaoImpl();
        ResponseConstructorasList responseConstructorasList = new ResponseConstructorasList();
        List<CmtCompaniaMgl> constructoras;
        CmtCompaniaMgl constructoraUnica;
        List<ResponseDataConstructoras> lstConstructorases = new ArrayList<>();

        try {


            if (request.getConstructoraId() == null
                    || request.getConstructoraId().isEmpty()) {

                //Consulta todas las constructoras

                CmtTipoCompaniaMglManager manager = new CmtTipoCompaniaMglManager();
                CmtTipoCompaniaMgl tipoCompaniaMgl = manager.findByNombreTipo(Constant.TIPO_COMPANIA_CONSTRUCTORA);

                if (tipoCompaniaMgl != null) {

                    constructoras = dao.findByTipoCompania(tipoCompaniaMgl);

                    if (constructoras.size() > 0) {

                        for (CmtCompaniaMgl constructora : constructoras) {
                            ResponseDataConstructoras responseDataConstructoras = new ResponseDataConstructoras();
                            responseDataConstructoras.setCodigo(constructora.getCodigoRr());
                            responseDataConstructoras.setCorreoElectronico(constructora.getEmail());
                            responseDataConstructoras.setDescripcion(constructora.getNombreCompania());
                            responseDataConstructoras.setDireccion(constructora.getDireccion());
                            responseDataConstructoras.setNombreContacto(constructora.getNombreContacto());
                            responseDataConstructoras.setObservacion1(constructora.getJustificacion());
                            responseDataConstructoras.setObservacion2(null);
                            responseDataConstructoras.setObservacion3(null);
                            responseDataConstructoras.setObservacion4(null);
                            responseDataConstructoras.setTelefono1(constructora.getTelefonos());
                            responseDataConstructoras.setTelefono2(constructora.getTelefono2());
                            lstConstructorases.add(responseDataConstructoras);

                        }
                        responseConstructorasList.setListConstructoras(lstConstructorases);
                        responseConstructorasList.setResultado("I");
                        responseConstructorasList.setMensaje("Consulta Exitosa");
                    } else {
                        responseConstructorasList.setListConstructoras(lstConstructorases);
                        responseConstructorasList.setResultado("I");
                        responseConstructorasList.setMensaje("No hay constructoras configuradas en MGL");
                    }
                }

            } else {
                //consulte por el id ingresado
                BigDecimal idConstructora = new BigDecimal(request.getConstructoraId());
                constructoraUnica = dao.find(idConstructora);
                if (constructoraUnica != null) {
                    ResponseDataConstructoras responseDataConstructoras = new ResponseDataConstructoras();
                    responseDataConstructoras.setCodigo(constructoraUnica.getCodigoRr());
                    responseDataConstructoras.setCorreoElectronico(constructoraUnica.getEmail());
                    responseDataConstructoras.setDescripcion(constructoraUnica.getNombreCompania());
                    responseDataConstructoras.setDireccion(constructoraUnica.getDireccion());
                    responseDataConstructoras.setNombreContacto(constructoraUnica.getNombreContacto());
                    responseDataConstructoras.setObservacion1(constructoraUnica.getJustificacion());
                    responseDataConstructoras.setObservacion2(null);
                    responseDataConstructoras.setObservacion3(null);
                    responseDataConstructoras.setObservacion4(null);
                    responseDataConstructoras.setTelefono1(constructoraUnica.getTelefonos());
                    responseDataConstructoras.setTelefono2(constructoraUnica.getTelefono2());
                    lstConstructorases.add(responseDataConstructoras);
                }
                responseConstructorasList.setListConstructoras(lstConstructorases);
                responseConstructorasList.setResultado("I");
                responseConstructorasList.setMensaje("Consulta Exitosa");

            }
        } catch (NumberFormatException ex) {
            
            responseConstructorasList.setListConstructoras(lstConstructorases);
            responseConstructorasList.setResultado("E");
            responseConstructorasList.setMensaje("Solo se permite valores Numericos");
			String msg = "Solo se permite valores Numericos, '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			LOGGER.error(msg);
			throw new ApplicationException(msg,  ex);
        } catch (ApplicationException ex) {

            responseConstructorasList.setListConstructoras(lstConstructorases);
            responseConstructorasList.setResultado("E");
            responseConstructorasList.setMensaje(ex.getMessage());
            String msg = "Se produjo un error al momento de ejecutar el método, '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
			LOGGER.error(msg);
			throw new ApplicationException(msg,  ex);
        }
        return responseConstructorasList;

    }
}
