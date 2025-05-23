/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

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

/**
 *
 * @author cardenaslb
 */
public interface CmtInventarioTecnologiaFacadeLocal {

    public CmtInventariosTecnologiaMgl create(CmtInventariosTecnologiaMgl cmtInventariosTecnologiaMgl) throws ApplicationException;

    public CmtInventariosTecnologiaMgl update(CmtInventariosTecnologiaMgl cmtInventariosTecnologiaMgl) throws ApplicationException;

    public List<CmtInventariosTecnologiaMgl> findAll() throws ApplicationException;

    public List<CmtInventariosTecnologiaMgl> findBySubEdif(CmtSubEdificioMgl cmtSubEdificioMgl) throws ApplicationException;

    public List<CmtInventariosTecnologiaMgl> findByTecSub(CmtTecnologiaSubMgl cmtTecnologiaSubMgl) throws ApplicationException;

    public List<CmtInventariosTecnologiaMgl> findInvTecnoByOtTec(BigDecimal OtId, BigDecimal tecnologia) throws ApplicationException;

    public void setUser(String mUser, int mPerfil) throws ApplicationException;

    public boolean delete(CmtInventariosTecnologiaMgl cmtInventariosTecnologiaMgl) throws ApplicationException;

    public boolean deleteInvTec(CmtInventariosTecnologiaMgl cmtInventariosTecnologiaMgl,
            String usuarioDel, int perfilDel) throws ApplicationException;

    public PaginacionDto<CmtInventariosTecnologiaMgl> findBySubOrCM(int paginaSelected, int maxResults, CmtSubEdificioMgl cmtSubEdificioMgl,
            FiltroConsultaInventarioTecDto filtroConsultaHhppDto, Constant.FIND_HHPP_BY findBy) throws ApplicationException;

    public CmtInventariosTecnologiaMgl findByInventarioTecId(Long InvTecId) throws ApplicationException;

    public CmtInventariosTecnologiaMgl createInvTec(CmtOrdenTrabajoInventarioMgl cmtOrdenTrabajoInventarioMgl, CmtTecnologiaSubMgl cmtTecnologiaSubMgl, String usuarioCrea, int perfilCrea) throws ApplicationException;

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
            String usuarioVt, int perfilVt) throws ApplicationException;
}
