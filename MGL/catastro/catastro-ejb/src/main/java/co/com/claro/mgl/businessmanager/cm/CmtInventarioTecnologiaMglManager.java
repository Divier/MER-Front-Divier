package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtInventarioTecnologiaMglDaoImpl;
import co.com.claro.mgl.dtos.CmtResutadoPasaInventariosDto;
import co.com.claro.mgl.dtos.FiltroConsultaInventarioTecDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.cm.CmtInventariosTecnologiaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoInventarioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author cardenaslb
 */
public class CmtInventarioTecnologiaMglManager {

    CmtInventarioTecnologiaMglDaoImpl dao = new CmtInventarioTecnologiaMglDaoImpl();
    private static final Logger LOGGER = LogManager.getLogger(CmtInventarioTecnologiaMglManager.class);

    public CmtInventariosTecnologiaMgl create(CmtInventariosTecnologiaMgl cmtInventariosTecnologiaMgl, String usuario, int perfil) throws ApplicationException {
        cmtInventariosTecnologiaMgl = dao.createCm(cmtInventariosTecnologiaMgl, usuario, perfil);
        return cmtInventariosTecnologiaMgl;
    }

    /**
     * Crea un inventario de tecnologia en el repositorio
     *
     * @author Victor Bocanegra
     * @param cmtOrdenTrabajoInventarioMgl
     * @param cmtTecnologiaSubMgl
     * @param usuarioCrea
     * @param perfilCrea
     * @return inventarios Tecnologia
     * @throws ApplicationException
     */
    public CmtInventariosTecnologiaMgl createInvTec(CmtOrdenTrabajoInventarioMgl cmtOrdenTrabajoInventarioMgl,
            CmtTecnologiaSubMgl cmtTecnologiaSubMgl, String usuarioCrea, int perfilCrea) throws ApplicationException {

        CmtInventariosTecnologiaMgl cmtInventariosTecnologiaMgl = new CmtInventariosTecnologiaMgl();
        cmtInventariosTecnologiaMgl.setTecnoSubedificioId(cmtTecnologiaSubMgl);
        cmtInventariosTecnologiaMgl.setSistemaInventarioId(cmtOrdenTrabajoInventarioMgl.getSistemaInventarioId());
        cmtInventariosTecnologiaMgl.setTipoInventario(cmtOrdenTrabajoInventarioMgl.getTipoInventario());
        cmtInventariosTecnologiaMgl.setClaseInventario(cmtOrdenTrabajoInventarioMgl.getClaseInventario());
        cmtInventariosTecnologiaMgl.setFabricante(cmtOrdenTrabajoInventarioMgl.getFabricante());
        cmtInventariosTecnologiaMgl.setSerial(cmtOrdenTrabajoInventarioMgl.getSerial());
        cmtInventariosTecnologiaMgl.setReferencia(cmtOrdenTrabajoInventarioMgl.getReferencia());
        cmtInventariosTecnologiaMgl.setEstado(cmtOrdenTrabajoInventarioMgl.getEstado());
        cmtInventariosTecnologiaMgl.setMac(cmtOrdenTrabajoInventarioMgl.getMac());
        cmtInventariosTecnologiaMgl.setFechaCreacion(new Date());

        cmtInventariosTecnologiaMgl = dao.createCm(cmtInventariosTecnologiaMgl, usuarioCrea, perfilCrea);
        return cmtInventariosTecnologiaMgl;
    }

    public CmtInventariosTecnologiaMgl update(CmtInventariosTecnologiaMgl cmtHhppVtObj, String usuario, int perfil) throws ApplicationException {
        cmtHhppVtObj = dao.updateCm(cmtHhppVtObj, usuario, perfil);
        return cmtHhppVtObj;
    }

    public boolean delete(CmtInventariosTecnologiaMgl cmtHhppVtObj, String usuario, int perfil) throws ApplicationException {
        return dao.deleteCm(cmtHhppVtObj, usuario, perfil);
    }

    public List<CmtInventariosTecnologiaMgl> findAll() throws ApplicationException {
        return dao.findAll();
    }

    public List<CmtInventariosTecnologiaMgl> findBySubEdif(CmtSubEdificioMgl cmtSubEdificioMgl) throws ApplicationException {
        return dao.findBySubEdiVt(cmtSubEdificioMgl);
    }

    public List<CmtInventariosTecnologiaMgl> findByTecSub(CmtTecnologiaSubMgl cmtTecnologiaSubMgl) throws ApplicationException {
        return dao.findByTecSub(cmtTecnologiaSubMgl);
    }

    /**
     * Busca las tecnologias asociadas a un subedificio por tecnologia.
     *
     * @author Victor Bocanegra
     * @param OtId orden de trabajo
     * @param tecnologia Tecnologia de la OT
     * @return inventarios Tecnologias asociadas a una ot.
     * @throws ApplicationException
     */
    public List<CmtInventariosTecnologiaMgl> findInvTecnoByOtTec(BigDecimal OtId, BigDecimal tecnologia)
            throws ApplicationException {
        return dao.findInvTecnoByOtTec(OtId, tecnologia);
    }

    /**
     * Busca una tecnologias de inventario por Id del inventario de tecnologia.
     *
     * @author Victor Bocanegra
     * @param InvTecId Id inventario
     * @return un inventario Tecnologia.
     * @throws ApplicationException
     */
    public CmtInventariosTecnologiaMgl findByInventarioTecId(Long InvTecId) throws ApplicationException {
        return dao.findByInventarioTecId(InvTecId);
    }

    public PaginacionDto<CmtInventariosTecnologiaMgl> findBySubOrCM(int paginaSelected,
            int maxResults, CmtSubEdificioMgl cmtSubEdificioMgl,
            FiltroConsultaInventarioTecDto filtroConsultaInventarioTecDto,
            Constant.FIND_HHPP_BY findBy) throws ApplicationException {
        PaginacionDto<CmtInventariosTecnologiaMgl> resultado = new PaginacionDto<CmtInventariosTecnologiaMgl>();
        int firstResult = 0;
        if (paginaSelected > 0) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        switch (findBy) {
            case CUENTA_MATRIZ:
                resultado.setNumPaginas(dao.countByCm(
                        cmtSubEdificioMgl.getCmtCuentaMatrizMglObj(),
                        filtroConsultaInventarioTecDto));
                resultado.setListResultado(dao.findPaginacion(
                        firstResult, maxResults,
                        cmtSubEdificioMgl.getCmtCuentaMatrizMglObj(),
                        filtroConsultaInventarioTecDto));
                break;
            case SUB_EDIFICIO:
                resultado.setNumPaginas(dao.countBySubCm(
                        cmtSubEdificioMgl, filtroConsultaInventarioTecDto));
                resultado.setListResultado(dao.findPaginacionSub(
                        firstResult, maxResults,
                        cmtSubEdificioMgl, filtroConsultaInventarioTecDto));
                break;
            case CUENTA_MATRIZ_SOLO_CONTAR:
                resultado.setNumPaginas(dao.countByCm(
                        cmtSubEdificioMgl.getCmtCuentaMatrizMglObj(),
                        filtroConsultaInventarioTecDto));
                break;
            case SUB_EDIFICIO_SOLO_CONTAR:
                resultado.setNumPaginas(dao.countBySubCm(
                        cmtSubEdificioMgl, filtroConsultaInventarioTecDto));
                break;
        }
        return resultado;
    }

    /**
     * Borrado logico de una tecnologias de inventario
     *
     * @author Victor Bocanegra
     * @param cmtInventariosTecnologiaMgl
     * @param usuarioDel
     * @param perfilDel
     * @return un inventario Tecnologia.
     * @throws ApplicationException
     */
    public boolean deleteInvTec(CmtInventariosTecnologiaMgl cmtInventariosTecnologiaMgl,
            String usuarioDel, int perfilDel) throws ApplicationException {
        return dao.deleteCm(cmtInventariosTecnologiaMgl, usuarioDel, perfilDel);
    }

    /**
     * Adiciona inventario en inventario tecnologia sub deacuerdo al inventario
     * agregado en OT
     *
     * @author Victor Bocanegra
     * @param cmtOrdenTrabajoInventarioMgl
     * @param usuarioVt
     * @param perfilVt
     * @throws ApplicationException
     */
    public void addInventarioSubedificioTecnologia(CmtOrdenTrabajoInventarioMgl cmtOrdenTrabajoInventarioMgl,
            String usuarioVt, int perfilVt) throws ApplicationException {
        CmtInventarioTecnologiaMglManager cmtInventarioTecnologiaMglManager = new CmtInventarioTecnologiaMglManager();
        CmtTecnologiaSubMglManager cmtTecnologiaSubMglManager = new CmtTecnologiaSubMglManager();

        List<CmtTecnologiaSubMgl> cmtTecnologiaSubMglList = cmtTecnologiaSubMglManager.findTecnoSubBySubEdiTec(
                cmtOrdenTrabajoInventarioMgl.getCmtSubEdificiosVtObj().getSubEdificioObj(),
                cmtOrdenTrabajoInventarioMgl.getCmtOrdenTrabajoMglObj().getBasicaIdTecnologia());
        CmtTecnologiaSubMgl cmtTecnologiaSubMgl;
        if (!(cmtTecnologiaSubMglList != null && !cmtTecnologiaSubMglList.isEmpty())) {
            cmtTecnologiaSubMgl = cmtTecnologiaSubMglManager.crearTecSub(
                    cmtOrdenTrabajoInventarioMgl.getCmtSubEdificiosVtObj(),
                    cmtOrdenTrabajoInventarioMgl.getCmtOrdenTrabajoMglObj(),
                    new BigDecimal(cmtOrdenTrabajoInventarioMgl.getVtId()),
                    usuarioVt, perfilVt);
        } else {
            cmtTecnologiaSubMgl = cmtTecnologiaSubMglList.get(0);
        }
        cmtInventarioTecnologiaMglManager.createInvTec(cmtOrdenTrabajoInventarioMgl, cmtTecnologiaSubMgl, usuarioVt, perfilVt);
    }

    /**
     * elimina inventario en inventario tecnologia sub deacuerdo al inventario
     * agregado en OT
     *
     * @author Victor Bocanegra
     * @param cmtOrdenTrabajoInventarioMgl
     * @param usuarioVt
     * @param perfilVt
     * @throws ApplicationException
     */
    public void delInventarioSubedificioTecnologia(CmtOrdenTrabajoInventarioMgl cmtOrdenTrabajoInventarioMgl,
            String usuarioVt, int perfilVt) throws ApplicationException {
        CmtInventarioTecnologiaMglManager cmtInventarioTecnologiaMglManager = new CmtInventarioTecnologiaMglManager();
        cmtInventarioTecnologiaMglManager.delete(cmtOrdenTrabajoInventarioMgl.getInventarioTecObj(), usuarioVt, perfilVt);
    }

    /**
     * Actualizacion Inventario Tecnologias
     *
     * @author Victor Bocanegra
     * @param cmtOrdenTrabajoMgl
     * @param usuarioVt
     * @param perfilVt
     * @return un inventario Tecnologia.
     * @throws ApplicationException
     */
    public CmtResutadoPasaInventariosDto actualizarInvTecCm(CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl,
            String usuarioVt, int perfilVt) throws ApplicationException {

        CmtResutadoPasaInventariosDto cmtResutadoPasaInventariosDto = new CmtResutadoPasaInventariosDto();
        try {
            for (CmtOrdenTrabajoInventarioMgl cmtOrdenTrabajoInventarioMgl : cmtOrdenTrabajoMgl.getInventarioListActivos()) {
                int accion =
                        new Integer(String.valueOf(cmtOrdenTrabajoInventarioMgl.getActionInvTecOt()));
                switch (accion) {
                    case Constant.ACCION_INVENTARIO_OT_ADD:
                        addInventarioSubedificioTecnologia(cmtOrdenTrabajoInventarioMgl, usuarioVt, perfilVt);
                        cmtResutadoPasaInventariosDto.setRegistrosAdd(cmtResutadoPasaInventariosDto.getRegistrosAdd() + 1);
                        break;
                    case Constant.ACCION_INVENTARIO_OT_DEL:
                        addInventarioSubedificioTecnologia(cmtOrdenTrabajoInventarioMgl, usuarioVt, perfilVt);
                        cmtResutadoPasaInventariosDto.setRegistrodDel(cmtResutadoPasaInventariosDto.getRegistrodDel() + 1);
                        break;
                    case Constant.ACCION_INVENTARIO_OT_NOA:
                        break;
                    default:
                        break;
                }
            }
            return cmtResutadoPasaInventariosDto;
        } catch (ApplicationException ex) {
            String msg = "Error al momento de actualizar el Inventario. EX000 '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg,  ex);
        }
    }

}
