package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtItemVtMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtItemMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtItemVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

/**
 * Contiene la logiga de negocio relacionada con la clase CmtItemVtMgl.
 *
 * @author alejandro.martine.ext@claro.com.co
 *
 * @versión 1.00.0000
 */
public class CmtItemVtMglManager {
    
    
    private static final Logger LOGGER = LogManager.getLogger(CmtItemVtMglManager.class);

    public List<CmtItemVtMgl> findAll() throws ApplicationException {
        CmtItemVtMglDaoImpl daoImpl = new CmtItemVtMglDaoImpl();
        return daoImpl.findAll();
    }

    /**
     * findItemByVt metodo empleado para consultar empleando el id de la visita
     * tecnica los items de mano de obra o materiales correspondientes a esta.alejandro.martinez.ext@claro.com.co
     *
     *
     * @param vtObj
     * @param idVt
     * @param tipoItem
     * @param versionVt
     * @return
     * @throws ApplicationException
     */
    public List<CmtItemVtMgl> findItemVtByVt(CmtVisitaTecnicaMgl vtObj, String tipoItem) throws ApplicationException {
        CmtItemVtMglDaoImpl daoImpl = new CmtItemVtMglDaoImpl();
        return daoImpl.findItemVtByVt(vtObj, tipoItem);
    }

    /**
     * findByVtItem metodo empleado para la busqueda de un item especifico en
     * una visita tecnica especifica.
     *
     * alejandro.martinez.ext@claro.com.co
     *
     * @param idVt
     * @param idItem
     * @param versionVt
     * @return
     * @throws ApplicationException
     */
    public List<CmtItemVtMgl> findByVtItem(BigDecimal idVt, BigDecimal idItem, int versionVt) throws ApplicationException {
        try {
            CmtItemVtMglDaoImpl daoImpl = new CmtItemVtMglDaoImpl();
            return daoImpl.findByVtItem(idVt, idItem, versionVt);
        } catch (ApplicationException | NoResultException | NonUniqueResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg,  e);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg,  ex);
        }
    }

    /**
     *
     * @param cmtItemVtMgl
     * @param usuario
     * @param perfil
     * @return
     * @throws ApplicationException
     */
    public CmtItemVtMgl createCm(CmtItemVtMgl cmtItemVtMgl, String usuario, int perfil) throws ApplicationException {
        CmtItemVtMglDaoImpl daoImpl = new CmtItemVtMglDaoImpl();
        //asignamos uno a la cantidad
        cmtItemVtMgl.setCantidad(BigDecimal.ONE);
        //Se asigna el costo total del item (cantidad X costo)
        cmtItemVtMgl.setCostoTotal(
                cmtItemVtMgl.getCantidad().multiply(
                cmtItemVtMgl.getVigenciaCostoItemObj().getCosto()));

        cmtItemVtMgl = daoImpl.createCm(cmtItemVtMgl, usuario, perfil);
        //se Actualizan los costos totales
        asignarCostosTotalesVt(cmtItemVtMgl.getVtObj(),
                cmtItemVtMgl.getItemObj().getTipoItem(), usuario, perfil);

        return cmtItemVtMgl;
    }

    public CmtItemVtMgl create(CmtItemVtMgl cmtItemVtMgl) throws ApplicationException {
        CmtItemVtMglDaoImpl daoImpl = new CmtItemVtMglDaoImpl();
        return daoImpl.create(cmtItemVtMgl);
    }

    /**
     *
     * @param cmtItemVtMgl
     * @param usuario
     * @param perfil
     * @return
     * @throws ApplicationException
     */
    public CmtItemVtMgl update(CmtItemVtMgl cmtItemVtMgl, String usuario, int perfil) throws ApplicationException {
        CmtItemVtMgl result;
        CmtItemVtMglDaoImpl daoItemVt = new CmtItemVtMglDaoImpl();
        //Se asigna el costo total del item (cantidad X costo)
        cmtItemVtMgl.setCostoTotal(
                cmtItemVtMgl.getCantidad().multiply(
                cmtItemVtMgl.getVigenciaCostoItemObj().getCosto()));
        //Se guarda item de la Vt
        result = daoItemVt.updateCm(cmtItemVtMgl, usuario, perfil);
        //se Actualizan los costos totales
        asignarCostosTotalesVt(cmtItemVtMgl.getVtObj(),
                cmtItemVtMgl.getItemObj().getTipoItem(), usuario, perfil);
        return result;
    }

    /**
     *
     * @param cmtItemVtMgl
     * @param usuario
     * @param perfil
     * @return
     * @throws ApplicationException
     */
    public boolean delete(CmtItemVtMgl cmtItemVtMgl, String usuario, int perfil) throws ApplicationException {
        CmtItemVtMglDaoImpl daoItemVt = new CmtItemVtMglDaoImpl();
        CmtVisitaTecnicaMglManager managerVt = new CmtVisitaTecnicaMglManager();

        boolean result = daoItemVt.deleteCm(cmtItemVtMgl, usuario, perfil);
        //se Actualizan los costos totales
        if (result) {
            asignarCostosTotalesVt(cmtItemVtMgl.getVtObj(),
                    cmtItemVtMgl.getItemObj().getTipoItem(), usuario, perfil);
        }
        return result;
    }

    public int getCountByVt(CmtVisitaTecnicaMgl vtObj, String tipoItem) throws ApplicationException {
        CmtItemVtMglDaoImpl daoImpl = new CmtItemVtMglDaoImpl();
        return daoImpl.getCountByVt(vtObj, tipoItem);
    }

    public CmtItemVtMgl findItemVtByItem(CmtVisitaTecnicaMgl vtObj, CmtItemMgl idItem) throws ApplicationException {
        CmtItemVtMglDaoImpl daoImpl = new CmtItemVtMglDaoImpl();
        return daoImpl.findItemVtByItem(vtObj, idItem);
    }

    public List<CmtItemVtMgl> findItemByVtPaginado(int paginaSelected,
            int maxResults, CmtVisitaTecnicaMgl vtObj, String tipoItem) throws ApplicationException {
        CmtItemVtMglDaoImpl daoImpl = new CmtItemVtMglDaoImpl();
        int firstResult = 0;
        if (paginaSelected > 1) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        return daoImpl.findItemByVtPaginado(firstResult, maxResults, vtObj, tipoItem);
    }

    /**
     *
     * @param cmtItemVtMgl
     * @return
     * @throws ApplicationException
     */
    public CmtItemVtMgl findById(CmtItemVtMgl cmtItemVtMgl) throws ApplicationException {
        CmtItemVtMglDaoImpl daoImpl = new CmtItemVtMglDaoImpl();
        return daoImpl.find(cmtItemVtMgl.getIdItemVt());
    }

    /**
     * Calcula costo total de Items. Calcula el costo total de los items de una
     * VT por medio del tipo del items
     *
     * @author Johnnatan Ortiz
     * @param vt Visita tecnica
     * @param tipoItem tipo de items
     * @return Costo total de todos los items de la VT
     * @throws ApplicationException
     */
    public BigDecimal getTotalCosto(CmtVisitaTecnicaMgl vt, String tipoItem)
            throws ApplicationException {
        BigDecimal result = BigDecimal.ZERO;
        List<CmtItemVtMgl> itemsVtList = findItemVtByVt(vt, tipoItem);
        if (itemsVtList != null && !itemsVtList.isEmpty()) {

            for (CmtItemVtMgl it : itemsVtList) {
                if (it != null && it.getCostoTotal() != null) {
                    result.add(it.getCostoTotal());
                    BigDecimal sum = result.add(it.getCostoTotal());
                    result = sum;
                }
            }
        }
        return result;
    }

    /**
     * Calcula el total de Items. Calcula el total de los items de una VT por
     * medio del tipo del items
     *
     * @author Johnnatan Ortiz
     * @param vt Visita tecnica
     * @param tipoItem tipo de items
     * @return Total de todos los items de la VT
     * @throws ApplicationException
     */
    public BigDecimal getTotalUnidades(CmtVisitaTecnicaMgl vt, String tipoItem) throws ApplicationException {
        BigDecimal result = BigDecimal.ZERO;
        List<CmtItemVtMgl> itemsVtList = findItemVtByVt(vt, tipoItem);
        if (itemsVtList != null && !itemsVtList.isEmpty()) {

            for (CmtItemVtMgl it : itemsVtList) {
                if (it != null && it.getCantidad() != null) {
                    BigDecimal sum = result.add(it.getCantidad());
                    result = sum;
                }
            }
        }
        return result;
    }

    public boolean asignarCostosTotalesVt(CmtVisitaTecnicaMgl vt,
            String tipoItem, String usuario, int perfil) throws ApplicationException {
        boolean result = false;
        try {
            CmtVisitaTecnicaMglManager managerVt = new CmtVisitaTecnicaMglManager();
            //Calculamos Costos Totales
            BigDecimal costoTotal = getTotalCosto(vt, tipoItem);
            if (tipoItem.trim().equals(Constant.TIPO_ITEM_MANO_OBRA)) {
                vt.setCtoManoObra(costoTotal);
            } else if (tipoItem.trim().equals(Constant.TIPO_ITEM_MATERIAL)) {
                vt.setCtoMaterialesRed(costoTotal);
            } else if (tipoItem.trim().equals(Constant.TIPO_ITEM_MANO_OBRA_DISENO)) {
                vt.setCostoManoObraDiseno(costoTotal);
            } else if (tipoItem.trim().equals(Constant.TIPO_ITEM_MATERIAL_DISENO)) {
                vt.setCostoMaterialesDiseno(costoTotal);
            }
            managerVt.updateCm(vt, usuario, perfil);
            result = true;
        } catch (ApplicationException e) {
             String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
			 LOGGER.error(msg);
			 throw new ApplicationException(msg,  e);
        }
        return result;
    }
}
