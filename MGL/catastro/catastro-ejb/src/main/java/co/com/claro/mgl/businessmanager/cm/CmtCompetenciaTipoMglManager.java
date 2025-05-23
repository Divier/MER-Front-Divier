/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtCompetenciaTipoMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtCompetenciaTipoMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 * Manager Competencia Tipo. Contiene la logica de negocio de competencia Tipos
 * en el repositorio.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 */
public class CmtCompetenciaTipoMglManager {

    /**
     * Busca los todos los tipos Competencias. Permite realizar la busqueda de
     * los tipos de Compentencias activos en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @return Tipo Compentencias activas
     * @throws ApplicationException
     */
    public List<CmtCompetenciaTipoMgl> findAllItemsActive() throws ApplicationException {
        CmtCompetenciaTipoMglDaoImpl daoImpl = new CmtCompetenciaTipoMglDaoImpl();
        return daoImpl.findAllActiveItems();
    }

    /**
     * Busca las Competencias Tipos. Permite realizar la busqueda de las
     * Compentencias Tipos Activas Paginado el resultado.
     *
     * @author Johnnatan Ortiz
     * @param paginaSelected pagina de la busqueda
     * @param maxResults maximo numero de resultados
     * @return Compentencias asociadas a un SubEdifico
     * @throws ApplicationException
     */
    public List<CmtCompetenciaTipoMgl> findAllActiveItemsPaginado(int paginaSelected,
            int maxResults) throws ApplicationException {
        CmtCompetenciaTipoMglDaoImpl daoImpl = new CmtCompetenciaTipoMglDaoImpl();
        int firstResult = 0;

        firstResult = (maxResults * (paginaSelected - 1));
//        }
        return daoImpl.findAllActiveItemsPaginado(firstResult, maxResults);
    }

    /**
     * Cuenta las Competencias Tipo. Permite realizar el conteo de las
     * Compentencias Tipos Activas.
     *
     * @author Johnnatan Ortiz
     * @return numero de Compentencias Tipos activas
     * @throws ApplicationException
     */
    public int getCountAllActiveItems() throws ApplicationException {
        CmtCompetenciaTipoMglDaoImpl daoImpl = new CmtCompetenciaTipoMglDaoImpl();
        return daoImpl.getCountAllActiveItems();
    }

    /**
     * Crea una Competencia Tipo.Permite crear una Compentencia Tipo en el
 repositorio.
     *
     * @author Johnnatan Ortiz
     * @param competenciaTipoMgl competencia tipo a crear
     * @param usuario
     * @param perfil
     * @return competencia tipo creada
     * @throws ApplicationException
     */
    public CmtCompetenciaTipoMgl create(
            CmtCompetenciaTipoMgl competenciaTipoMgl,
            String usuario, int perfil)
            throws ApplicationException {
        CmtCompetenciaTipoMgl result;

        CmtTablasBasicasRRMglManager basicasRRMglManager =
                new CmtTablasBasicasRRMglManager();
        if (basicasRRMglManager.competenciaAdd(competenciaTipoMgl, usuario)) {
            CmtCompetenciaTipoMglDaoImpl daoImpl =
                    new CmtCompetenciaTipoMglDaoImpl();
            result = daoImpl.createCm(competenciaTipoMgl, usuario, perfil);
        } else {
            throw new ApplicationException("No Fue posible crear la competecia");
        }

        return result;
    }

    /**
     * Actualizar una Competencia Tipo.Permite actualizar una Compentencia Tipo
 en el repositorio dejando el registro estado = 0.
     *
     * @author Johnnatan Ortiz
     * @param competenciaTipoMgl competencia tipo a crear
     * @param usuario
     * @param perfil
     * @return competencia tipo creada
     * @throws ApplicationException
     */
    public boolean delete(CmtCompetenciaTipoMgl competenciaTipoMgl,
            String usuario, int perfil) throws ApplicationException {
        boolean result = false;

        CmtTablasBasicasRRMglManager basicasRRMglManager =
                new CmtTablasBasicasRRMglManager();
        if (basicasRRMglManager.competenciaDelete(competenciaTipoMgl, usuario)) {
            CmtCompetenciaTipoMglDaoImpl daoImpl =
                    new CmtCompetenciaTipoMglDaoImpl();
            result = daoImpl.deleteCm(competenciaTipoMgl, usuario, perfil);
        } else {
            throw new ApplicationException("No Fue posible crear la competecia");
        }
        return result;
    }

    /**
     * Actualiza una Competencia Tipo.Permite actualizar una Compentencia Tipo
 en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @param competenciaTipoMgl competencia tipo a crear
     * @param usuario
     * @param perfil
     * @return competencia tipo creada
     * @throws ApplicationException
     */
    public CmtCompetenciaTipoMgl update(
            CmtCompetenciaTipoMgl competenciaTipoMgl,
            String usuario, int perfil)
            throws ApplicationException {

        CmtCompetenciaTipoMgl result;
        CmtTablasBasicasRRMglManager basicasRRMglManager =
                new CmtTablasBasicasRRMglManager();
        if (basicasRRMglManager.competenciaUpdate(competenciaTipoMgl, usuario)) {
            CmtCompetenciaTipoMglDaoImpl daoImpl =
                    new CmtCompetenciaTipoMglDaoImpl();
            result = daoImpl.updateCm(competenciaTipoMgl, usuario, perfil);
        } else {
            throw new ApplicationException("No Fue posible crear la competecia");
        }
        return result;
    }

    /**
     * Actualiza una Competencia Tipo.Permite actualizar una Compentencia Tipo
 en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @return competencia tipo creada
     * @throws ApplicationException
     */
    public String calcularCodigoRr() throws ApplicationException {
        CmtCompetenciaTipoMglDaoImpl daoImpl = new CmtCompetenciaTipoMglDaoImpl();
        String maxCodRr = daoImpl.getMaxCodigoRr();
        Integer codCalculo = new Integer("1");
        if (maxCodRr != null && !maxCodRr.trim().isEmpty()) {
            codCalculo = Integer.parseInt(maxCodRr) + 1;
        }
        return codCalculo.toString();
    }

    public CmtCompetenciaTipoMgl findById(BigDecimal idCompetenciaTipo) throws ApplicationException {
        CmtCompetenciaTipoMglDaoImpl daoImpl = new CmtCompetenciaTipoMglDaoImpl();
        return daoImpl.find(idCompetenciaTipo);
    }
}
