/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtOrdenTrabajoInventarioMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtInventariosTecnologiaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoInventarioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificiosVt;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Manager Inventarios de tecnologias asociados a Orden de Trabajo. Contiene la
 * logica de negocio para el manejo de inventarios de tecnologias asociados a
 * ordenes de trabajo en el repositorio.
 *
 * @author Victor Bocanegra
 * @version 1.00.000
 */
public class CmtOrdenTrabajoInventarioMglManager {

    CmtOrdenTrabajoInventarioMglDaoImpl dao = new CmtOrdenTrabajoInventarioMglDaoImpl();
    private static final Logger LOGGER = LogManager.getLogger(CmtOrdenTrabajoInventarioMglManager.class);

    /**
     * Crea un inventario de tecnologia asociado a una Orden de Trabajo.Permite
 realizar la creacion de un inventario de tecnologia asociado a una Orden
 de Trabajo en el repositorio.
     *
     * @author Victor Bocanegra
     * @param ot Orden de Trabajo a crear en el repositorio
     * @param usuario
     * @param perfil
     * @return Inventario de tecnologia asociado a una Orden de Trabajo
     * @throws ApplicationException
     */
    public CmtOrdenTrabajoInventarioMgl crearOtInv(CmtOrdenTrabajoInventarioMgl otInv, String usuario, int perfil) throws ApplicationException {

        otInv = dao.createCm(otInv, usuario, perfil);
        return otInv;
    }

    /**
     * Crea un inventario de tecnologia asociado a una Orden de Trabajo.Permite
 realizar la creacion de un inventario de tecnologia asociado a una Orden
 de Trabajo en el repositorio.
     *
     * @author Victor Bocanegra
     * @param invTec inventario de tecnologia
     * @param cmtOrdenTrabajoMgl
     * @param idOt
     * @param usuarioCrea
     * @param perfilCrea
     * @param cmtSubEdificiosVt
     * @return Inventario de tecnologia asociado a una Orden de Trabajo
     * @throws ApplicationException
     */
    public CmtOrdenTrabajoInventarioMgl crearOtInvCm(CmtInventariosTecnologiaMgl invTec,
            CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl, String usuarioCrea, int perfilCrea,
            CmtSubEdificiosVt cmtSubEdificiosVt)
            throws ApplicationException {

        CmtOrdenTrabajoInventarioMgl otInv = new CmtOrdenTrabajoInventarioMgl();
        otInv.setCmtOrdenTrabajoMglObj(cmtOrdenTrabajoMgl);
        otInv.setSistemaInventarioId(invTec.getSistemaInventarioId());
        otInv.setTipoInventario(invTec.getTipoInventario());
        otInv.setClaseInventario(invTec.getClaseInventario());
        otInv.setFabricante(invTec.getFabricante());
        otInv.setSerial(invTec.getSerial());
        otInv.setReferencia(invTec.getReferencia());
        otInv.setEstado(invTec.getEstado());
        otInv.setMac(invTec.getMac());
        otInv.setFechaCreacion(new Date());
        otInv.setCmtSubEdificiosVtObj(cmtSubEdificiosVt);
        otInv.setVtId(invTec.getTecnoSubedificioId().getVisitaTecnica().getIdVt().longValue());
        otInv.setInventarioTecObj(invTec);
        char noAction = '3';
        otInv.setActionInvTecOt(noAction);

        otInv = dao.createCm(otInv, usuarioCrea, perfilCrea);
        return otInv;
    }

    /**
     * Crea un inventario de tecnologia asociado a una Orden de Trabajo.Permite
 realizar la creacion de un inventario de tecnologia asociado a una Orden
 de Trabajo en el repositorio con los datos de entrada del formulario.
     *
     * @author Victor Bocanegra
     * @param invTec inventario de tecnologia
     * @param idOt
     * @param cmtOrdenTrabajoInventarioMgl
     * @param tipoInv
     * @param claseInv
     * @param usuarioCrea
     * @param perfilCrea
     * @return Inventario de tecnologia asociado a una Orden de Trabajo
     * @throws ApplicationException
     */
    public CmtOrdenTrabajoInventarioMgl crearOtInvForm(CmtOrdenTrabajoInventarioMgl cmtOrdenTrabajoInventarioMgl, String usuarioCrea, int perfilCrea) throws ApplicationException {
        char Add = '1';
        cmtOrdenTrabajoInventarioMgl.setActionInvTecOt(Add);
        cmtOrdenTrabajoInventarioMgl = dao.createCm(
                cmtOrdenTrabajoInventarioMgl, usuarioCrea, perfilCrea);
        return cmtOrdenTrabajoInventarioMgl;

    }

    /**
     * Busca los inventarios de tecnologias asociados Ordenes de Trabajo
     *
     * @author Victor Bocanegra
     * @param Otid ID de la orden de trabajo
     * @return Lista de Inventarios de tecnologias asociados a una Orden de
     * Trabajo encontradas en el repositorio
     * @throws ApplicationException
     */
    public List<CmtOrdenTrabajoInventarioMgl> findByIdOt(BigDecimal Otid)
            throws ApplicationException {
        return dao.findByIdOt(Otid);
    }

    /**
     * Busca el inventarios de tecnologias por el Id del inventario y estado
     * registro
     *
     * @author Victor Bocanegra
     * @param idOtInvTec ID del inventario
     * @return CmtOrdenTrabajoInventarioMgl encontradas en el repositorio
     * @throws ApplicationException
     */
    public CmtOrdenTrabajoInventarioMgl findByIdOtInvTec(BigDecimal idOtInvTec)
            throws ApplicationException {
        return dao.findByIdOtInvTec(idOtInvTec);
    }

    /**
     * Actualiza un inventario de tecnologia asociado a una Ot
     *
     * @author Victor Bocanegra
     * @param action
     * @param cmtOrdenTrabajoInventarioMgl
     * @param usuarioEdi
     * @param perfilEdi
     * @throws ApplicationException
     */
    public void updateOtInv(char action, CmtOrdenTrabajoInventarioMgl cmtOrdenTrabajoInventarioMgl, String usuarioEdi, int perfilEdi) throws ApplicationException {

        cmtOrdenTrabajoInventarioMgl.setActionInvTecOt(action);
        cmtOrdenTrabajoInventarioMgl.setFechaEdicion(new Date());

        dao.updateCm(cmtOrdenTrabajoInventarioMgl, usuarioEdi, perfilEdi);


    }

    /**
     * Borra un inventario de tecnologia asociado a una Ot
     *
     * @author Victor Bocanegra
     * @param cmtOrdenTrabajoInventarioMgl
     * @param usuarioEdi
     * @param perfilEdi
     * @throws ApplicationException
     */
    public void deleteOtInv(CmtOrdenTrabajoInventarioMgl cmtOrdenTrabajoInventarioMgl, String usuarioEdi, int perfilEdi) throws ApplicationException {

        dao.deleteCm(cmtOrdenTrabajoInventarioMgl, usuarioEdi, perfilEdi);


    }

    /**
     * Retorna la lista de inventarios de tecnologias asociados a una OT
     *
     * @author Victor Bocanegra
     * @param cmtOrdenTrabajoMgl
     * @param usuarioVt
     * @param perfilVt
     * @return 
     * @throws ApplicationException
     */
    public List<CmtOrdenTrabajoInventarioMgl> cargarInfo(CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl,
            String usuarioVt, int perfilVt) throws ApplicationException {

        List<CmtOrdenTrabajoInventarioMgl> lsCmtOrdenTrabajoInventarioMgls = null;
        CmtOrdenTrabajoInventarioMglManager cmtOrdenTrabajoInventarioMglManager =
                new CmtOrdenTrabajoInventarioMglManager();

        List<CmtInventariosTecnologiaMgl> lsCmtInventariosTecnologiaMgls;
        CmtInventarioTecnologiaMglManager cmtInventarioTecnologiaMglManager =
                new CmtInventarioTecnologiaMglManager();

        try {

            if (cmtOrdenTrabajoMgl != null) {
                //Consulta si hay inventarios de tecnologias en CMT_ORDENTRABAJO_INVENTARIO
                lsCmtOrdenTrabajoInventarioMgls = cmtOrdenTrabajoInventarioMglManager.findByIdOt(
                        cmtOrdenTrabajoMgl.getIdOt());
                for (CmtOrdenTrabajoInventarioMgl cmtOrdenTrabajoInventarioMgl : lsCmtOrdenTrabajoInventarioMgls) {
                    cmtOrdenTrabajoInventarioMgl.getCmtSubEdificiosVtObj();
                }
                if (lsCmtOrdenTrabajoInventarioMgls == null || lsCmtOrdenTrabajoInventarioMgls.isEmpty()) {
                    //Consulta si hay inventarios de tecnologias en CMT_INVENTARIOS_TECNOLOGIAS
                    lsCmtInventariosTecnologiaMgls = cmtInventarioTecnologiaMglManager.
                            findInvTecnoByOtTec(cmtOrdenTrabajoMgl.getIdOt(), cmtOrdenTrabajoMgl.
                            getBasicaIdTecnologia().getBasicaId());

                    if (!(lsCmtInventariosTecnologiaMgls == null || lsCmtInventariosTecnologiaMgls.isEmpty())){
                        //Inserto registros en CMT_ORDENTRABAJO_INVENTARIO       
                        for (CmtInventariosTecnologiaMgl cmtInventariosTecnologiaMgl
                                : lsCmtInventariosTecnologiaMgls) {
                            CmtSubEdificiosVtManager cmtSubEdificiosVtManager = new CmtSubEdificiosVtManager();

                            CmtSubEdificiosVt cmtSubEdificiosVt = cmtSubEdificiosVtManager.findByIdSubEdificio(
                                    cmtInventariosTecnologiaMgl.getTecnoSubedificioId().
                                    getSubedificioId().getSubEdificioId());
                            CmtOrdenTrabajoInventarioMgl cmtOrdenTrabajoInventarioMgl =
                                    cmtOrdenTrabajoInventarioMglManager.crearOtInvCm(
                                    cmtInventariosTecnologiaMgl, cmtOrdenTrabajoMgl,
                                    usuarioVt, perfilVt, cmtSubEdificiosVt);
                            lsCmtOrdenTrabajoInventarioMgls =
                                    new ArrayList<CmtOrdenTrabajoInventarioMgl>();
                            if (cmtOrdenTrabajoInventarioMgl.getOrdentrabajoInventarioId() != null) {
                                lsCmtOrdenTrabajoInventarioMgls.add(cmtOrdenTrabajoInventarioMgl);
                            } else {
                                String msn = "No se pudo almacenar el registro en CMT_ORDENTRABAJO_INVENTARIO";
                                LOGGER.error(msn);
                                throw new ApplicationException(msn);
                            }

                        }
                    }
                }

            }
        } catch (ApplicationException ex) {
            String msg = "Error al momento de cargar la información. EX000 '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
           throw new ApplicationException("Error al momento de cargar la información. EX000: " + ex.getMessage(), ex);
        }

        return lsCmtOrdenTrabajoInventarioMgls;

    }
}
