/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.dao.impl.cm.CmtComunidadRrDaoImpl;
import co.com.claro.mgl.dtos.CmtFiltroConsultaComunidadDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

/**
 *
 * @author valbuenayf
 */
public class ComunidadRrManager {

    private static final Logger LOGGER = LogManager.getLogger(ComunidadRrManager.class);
    private CmtComunidadRrDaoImpl comunidadRrDaoImpl = new CmtComunidadRrDaoImpl();
    private String HABILITAR_RR;
    private ParametrosMglManager parametrosMglManager = new ParametrosMglManager();

    public ComunidadRrManager() {
        try {
            HABILITAR_RR = parametrosMglManager.findByAcronimo(
                    Constant.HABILITAR_RR)
                    .iterator().next().getParValor();
        } catch (ApplicationException e) {
            LOGGER.error("Error al llamar parametro " + e.getMessage());
        }
    }

    /**
     * valbuenayf Metodo para la comunidad y su regional
     *
     * @param idCiudad
     * @param codigoTecnologia
     * @return
     */
    public CmtComunidadRr findComunidadRegional(BigDecimal idCiudad, String codigoTecnologia) {
        CmtComunidadRr comunidadRr;
        CmtComunidadRrDaoImpl comunidadRrDaoImpl;
        try {
            comunidadRrDaoImpl = new CmtComunidadRrDaoImpl();
            comunidadRr = comunidadRrDaoImpl.findComunidadRegional(idCiudad, codigoTecnologia);
        } catch (Exception e) {
            LOGGER.error("Error findComunidadRegional de CmtComunidadRrManager " + e.getMessage());
            return null;
        }
        return comunidadRr;
    }

    /**
     * Metodo para trar la comunidad por el codigo RR
     *
     * @param codigoRR
     * @return CmtComunidadRr
     */
    public CmtComunidadRr findComunidadByCodigo(String codigoRR) {

        CmtComunidadRr comunidadRr;
        CmtComunidadRrDaoImpl comunidadRrDaoImpl;
        try {
            comunidadRrDaoImpl = new CmtComunidadRrDaoImpl();
            comunidadRr = comunidadRrDaoImpl.findComunidadByCodigo(codigoRR);
        } catch (Exception e) {
            LOGGER.error("Error findComunidadRegional de CmtComunidadRrManager " + e.getMessage());
            return null;
        }
        return comunidadRr;
    }

    /**
     * Lenis Cardenas Metodo para trar la comunidad por el codigo de la
     * comunidad RR
     *
     * @param comunidadRR
     * @return CmtComunidadRr
     */
    public CmtComunidadRr findComunidadByComunidad(String comunidadRR) {

        CmtComunidadRr comunidadRr;
        CmtComunidadRrDaoImpl comunidadRrDaoImpl;
        try {
            comunidadRrDaoImpl = new CmtComunidadRrDaoImpl();
            comunidadRr = comunidadRrDaoImpl.findComunidadByCodigo(comunidadRR);
        } catch (Exception e) {
            LOGGER.error("Error findComunidadRegional de CmtComunidadRrManager " + e.getMessage());
            return null;
        }
        return comunidadRr;
    }

    public PaginacionDto<CmtComunidadRr> findAllPaginado(int paginaSelected,
            int maxResults, CmtFiltroConsultaComunidadDto consulta) throws ApplicationException {

        PaginacionDto<CmtComunidadRr> resultado = new PaginacionDto<CmtComunidadRr>();
        int firstResult = 0;
        if (paginaSelected > 0) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        if (consulta != null) {
            resultado.setNumPaginas(comunidadRrDaoImpl.countByComFiltro(consulta));
            resultado.setListResultado(comunidadRrDaoImpl.findByFiltro(firstResult, maxResults, consulta));
        } else {
            consulta = new CmtFiltroConsultaComunidadDto();
            resultado.setNumPaginas(comunidadRrDaoImpl.countByComFiltro(consulta));
            resultado.setListResultado(comunidadRrDaoImpl.findByFiltro(firstResult, maxResults, consulta));
        }
        return resultado;
    }

    public CmtComunidadRr create(CmtComunidadRr comunidadRr, String usuario, int id) throws ApplicationException {
        CmtComunidadRr comunidadRrResult = null;
        LOGGER.error("Creando Comunidad:" + comunidadRr.getCodigoRr());
        CmtTablasBasicasSincronizacionRRMglManager basicasSincronizacionRRMglManager
                = new CmtTablasBasicasSincronizacionRRMglManager();
       
            if (HABILITAR_RR.equals("1")) {
                if (findComunidadByComunidad(comunidadRr.getCodigoRr()) == null) {
                    if (basicasSincronizacionRRMglManager.crearComunidadRr(comunidadRr, usuario)) {
                        comunidadRrResult = comunidadRrDaoImpl.create(comunidadRr);
                    }
                } else {
                    throw new ApplicationException("El codigo de la comunidad "
                            + comunidadRr.getCodigoRr()+ " Ya existe en MGL");
                }
            } else {
                if (findComunidadByComunidad(comunidadRr.getCodigoRr()) == null) {
                    comunidadRrResult = comunidadRrDaoImpl.create(comunidadRr);
                } else {
                    throw new ApplicationException("El codigo de la comunidad "
                            + comunidadRr.getCodigoRr() + " Ya existe en MGL");
                }
            }
      
        return comunidadRrResult;
    }

    public CmtComunidadRr update(CmtComunidadRr comunidadRr, String usuario, int id) throws ApplicationException {
        CmtComunidadRr comunidadRrResult = null;
        LOGGER.error("Actualizando Comunidad:" + comunidadRr.getCodigoRr());
        CmtTablasBasicasSincronizacionRRMglManager basicasSincronizacionRRMglManager
                = new CmtTablasBasicasSincronizacionRRMglManager();
        if (basicasSincronizacionRRMglManager.actualizarComunidadRr(comunidadRr, usuario)) {
            comunidadRrResult = comunidadRrDaoImpl.update(comunidadRr);
        } else {
            throw new ApplicationException("No se pudo actualizar el registro "
                    + comunidadRr.getNombreComunidad());
        }
        return comunidadRrResult;
    }

    public boolean delete(CmtComunidadRr comunidadRr, String usuario, int perfil)
            throws ApplicationException {
        LOGGER.error("Eliminando Comunidad:" + comunidadRr.getCodigoRr());
        boolean eliminado = false;
        CmtTablasBasicasSincronizacionRRMglManager basicasSincronizacionRRMglManager
                = new CmtTablasBasicasSincronizacionRRMglManager();
        comunidadRr.setEstadoRegistro(0);
        comunidadRr.setUsuarioEdicion(usuario);
        comunidadRr.setPerfilEdicion(String.valueOf(perfil));

        if (HABILITAR_RR.equals("1")) {
            if (basicasSincronizacionRRMglManager.eliminarComunidadRr(comunidadRr, usuario)) {
                comunidadRrDaoImpl.update(comunidadRr);
                eliminado = true;
                comunidadRr.setComunidadRrId(new BigDecimal(0));
            }

        } else {
            comunidadRrDaoImpl.update(comunidadRr);
            eliminado = true;
            comunidadRr.setComunidadRrId(new BigDecimal(0));
        }
        return eliminado;
    }
}
