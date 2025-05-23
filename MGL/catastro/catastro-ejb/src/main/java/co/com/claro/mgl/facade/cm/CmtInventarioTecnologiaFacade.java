/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtInventarioTecnologiaMglManager;
import co.com.claro.mgl.dtos.CmtResutadoPasaInventariosDto;
import co.com.claro.mgl.dtos.FiltroConsultaInventarioTecDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.cm.CmtInventariosTecnologiaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoInventarioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import co.com.claro.mgl.utils.Constant;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author cardenaslb
 */
@Stateless
public class CmtInventarioTecnologiaFacade implements CmtInventarioTecnologiaFacadeLocal {

    private String user = "";
    private int perfil = 0;

    @Override
    public CmtInventariosTecnologiaMgl create(CmtInventariosTecnologiaMgl cmtInventariosTecnologiaMgl) throws ApplicationException {
        CmtInventarioTecnologiaMglManager manager = new CmtInventarioTecnologiaMglManager();
        return manager.create(cmtInventariosTecnologiaMgl, user, perfil);
    }

    @Override
    public CmtInventariosTecnologiaMgl update(CmtInventariosTecnologiaMgl cmtInventariosTecnologiaMgl) throws ApplicationException {
        CmtInventarioTecnologiaMglManager manager = new CmtInventarioTecnologiaMglManager();
        return manager.update(cmtInventariosTecnologiaMgl, user, perfil);
    }

    @Override
    public List<CmtInventariosTecnologiaMgl> findAll() throws ApplicationException {
        CmtInventarioTecnologiaMglManager manager = new CmtInventarioTecnologiaMglManager();
        return manager.findAll();
    }

    @Override
    public List<CmtInventariosTecnologiaMgl> findBySubEdif(CmtSubEdificioMgl cmtSubEdificioMgl) throws ApplicationException {
        CmtInventarioTecnologiaMglManager manager = new CmtInventarioTecnologiaMglManager();
        return manager.findBySubEdif(cmtSubEdificioMgl);
    }

    @Override
    public List<CmtInventariosTecnologiaMgl> findByTecSub(CmtTecnologiaSubMgl cmtTecnologiaSubMgl) throws ApplicationException {
        CmtInventarioTecnologiaMglManager manager = new CmtInventarioTecnologiaMglManager();
        return manager.findByTecSub(cmtTecnologiaSubMgl);
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
    @Override
    public List<CmtInventariosTecnologiaMgl> findInvTecnoByOtTec(BigDecimal OtId, BigDecimal tecnologia)
            throws ApplicationException {
        CmtInventarioTecnologiaMglManager manager = new CmtInventarioTecnologiaMglManager();
        return manager.findInvTecnoByOtTec(OtId, tecnologia);
    }

    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil Nopueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }

    @Override
    public boolean delete(CmtInventariosTecnologiaMgl cmtInventariosTecnologiaMgl) throws ApplicationException {
        CmtInventarioTecnologiaMglManager manager = new CmtInventarioTecnologiaMglManager();
        return manager.delete(cmtInventariosTecnologiaMgl, user, perfil);
    }

    @Override
    public PaginacionDto<CmtInventariosTecnologiaMgl> findBySubOrCM(int paginaSelected, int maxResults, CmtSubEdificioMgl cmtSubEdificioMgl,
            FiltroConsultaInventarioTecDto filtroConsultaInventarioTecDto, Constant.FIND_HHPP_BY findBy) throws ApplicationException {
        CmtInventarioTecnologiaMglManager manager = new CmtInventarioTecnologiaMglManager();
        return manager.findBySubOrCM(paginaSelected, maxResults, cmtSubEdificioMgl, filtroConsultaInventarioTecDto, findBy);

    }

    /**
     * Busca una tecnologias de inventario por Id del inventario de tecnologia.
     *
     * @author Victor Bocanegra
     * @param InvTecId Id inventario
     * @return un inventario Tecnologia.
     * @throws ApplicationException
     */
    @Override
    public CmtInventariosTecnologiaMgl findByInventarioTecId(Long InvTecId) throws ApplicationException {

        CmtInventarioTecnologiaMglManager manager = new CmtInventarioTecnologiaMglManager();
        return manager.findByInventarioTecId(InvTecId);

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
    @Override
    public CmtInventariosTecnologiaMgl createInvTec(CmtOrdenTrabajoInventarioMgl cmtOrdenTrabajoInventarioMgl, CmtTecnologiaSubMgl cmtTecnologiaSubMgl, String usuarioCrea, int perfilCrea) throws ApplicationException {

        CmtInventarioTecnologiaMglManager manager = new CmtInventarioTecnologiaMglManager();
        return manager.createInvTec(cmtOrdenTrabajoInventarioMgl, cmtTecnologiaSubMgl, usuarioCrea, perfilCrea);
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
    @Override
    public boolean deleteInvTec(CmtInventariosTecnologiaMgl cmtInventariosTecnologiaMgl,
            String usuarioDel, int perfilDel) throws ApplicationException {

        CmtInventarioTecnologiaMglManager manager = new CmtInventarioTecnologiaMglManager();
        return manager.deleteInvTec(cmtInventariosTecnologiaMgl, usuarioDel, perfilDel);

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
    @Override
    public CmtResutadoPasaInventariosDto actualizarInvTecCm(CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl,
            String usuarioVt, int perfilVt) throws ApplicationException {

        CmtInventarioTecnologiaMglManager manager = new CmtInventarioTecnologiaMglManager();
        return manager.actualizarInvTecCm(cmtOrdenTrabajoMgl, usuarioVt, perfilVt);
    }
}
