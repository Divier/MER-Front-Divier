/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.dtos.FiltroConsultaInventarioTecDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtInventariosTecnologiaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author cardenaslb
 */
public class CmtInventarioTecnologiaMglDaoImpl extends GenericDaoImpl<CmtInventariosTecnologiaMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtInventarioTecnologiaMglDaoImpl.class);

    public List<CmtInventariosTecnologiaMgl> findAll() throws ApplicationException {
        try {
            List<CmtInventariosTecnologiaMgl> resulList;
            Query query = entityManager.createNamedQuery("CmtInventariosTecnologiaMgl.findAll");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resulList = (List<CmtInventariosTecnologiaMgl>) query.getResultList();
            return resulList;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }

    public List<CmtInventariosTecnologiaMgl> findBySubEdiVt(CmtSubEdificioMgl cmtSubEdificioMgl) throws ApplicationException {
        try {
            List<CmtInventariosTecnologiaMgl> resulList;
            Query query = entityManager.createNamedQuery("CmtHhppVtMgl.findBySubEdiVt");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (cmtSubEdificioMgl != null) {
                query.setParameter("subEdificioVtObj", cmtSubEdificioMgl);
            }
            resulList = (List<CmtInventariosTecnologiaMgl>) query.getResultList();
            return resulList;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }

    public Long countByCm(CmtCuentaMatrizMgl cmtCuentaMatrizMgl,
            FiltroConsultaInventarioTecDto filtroConsultaInventarioTecDto)
            throws ApplicationException {
        Long resultCount = 0L;
        String queryString;
        queryString = "SELECT COUNT(1) FROM CmtInventariosTecnologiaMgl h WHERE h.tecnoSubedificioId.subedificioId.cuentaMatrizObj = :cuentaMatrizObj ";

        if (cmtCuentaMatrizMgl == null
                || cmtCuentaMatrizMgl.getCuentaMatrizId() == null
                || cmtCuentaMatrizMgl.getCuentaMatrizId().equals(BigDecimal.ZERO)) {
            throw new ApplicationException("El filtro cuenta matriz es obligatorio");
        }
        if (filtroConsultaInventarioTecDto.getTipoInv() != null
                && !filtroConsultaInventarioTecDto.getTipoInv().isEmpty()) {
            queryString += " AND  h.tipoInventario LIKE :tipoInv ";
        }
        if (filtroConsultaInventarioTecDto.getClaseInv() != null
                && !filtroConsultaInventarioTecDto.getClaseInv().isEmpty()) {
            queryString += " AND  h.claseInventario LIKE :claseInv ";
        }
        if (filtroConsultaInventarioTecDto.getFabricante() != null
                && !filtroConsultaInventarioTecDto.getFabricante().isEmpty()) {
            queryString += " AND  h.fabricante LIKE :fabricante ";
        }
        if (filtroConsultaInventarioTecDto.getSerial() != null
                && !filtroConsultaInventarioTecDto.getSerial().isEmpty()) {
            queryString += " AND  h.serial = :serial ";
        }
        if (filtroConsultaInventarioTecDto.getReferencia() != null
                && !filtroConsultaInventarioTecDto.getReferencia().isEmpty()) {
            queryString += " AND  h.referencia LIKE :referencia ";
        }
        if (filtroConsultaInventarioTecDto.getMac() != null
                && !filtroConsultaInventarioTecDto.getMac().isEmpty()) {
            queryString += " AND  h.mac LIKE :mac ";
        }
        if (filtroConsultaInventarioTecDto.getEstado() != 0) {
            queryString += " AND  h.estado  = :estado ";
        }
        if (filtroConsultaInventarioTecDto.getTorre() != null && !filtroConsultaInventarioTecDto.getTorre().isEmpty()) {
            queryString += " AND  h.tecnoSubedificioId.subedificioId.nombreSubedificio  LIKE :torre ";
        }
        if (filtroConsultaInventarioTecDto.getTecnologia() != null && !filtroConsultaInventarioTecDto.getTecnologia().isEmpty()) {
            queryString += " AND  h.tecnoSubedificioId.basicaIdTecnologias.nombreBasica  LIKE :tecnologia ";
        }

        Query query = entityManager.createQuery(queryString);

        query.setParameter("cuentaMatrizObj", cmtCuentaMatrizMgl);

        if (filtroConsultaInventarioTecDto.getTipoInv() != null
                && !filtroConsultaInventarioTecDto.getTipoInv().isEmpty()) {
            query.setParameter("tipoInv", "%" + filtroConsultaInventarioTecDto.getTipoInv() + "%");
        }
        if (filtroConsultaInventarioTecDto.getClaseInv() != null
                && !filtroConsultaInventarioTecDto.getClaseInv().isEmpty()) {
            query.setParameter("claseInv", "%" + filtroConsultaInventarioTecDto.getClaseInv() + "%");
        }
        if (filtroConsultaInventarioTecDto.getFabricante() != null
                && !filtroConsultaInventarioTecDto.getFabricante().isEmpty()) {
            query.setParameter("fabricante", "%" + filtroConsultaInventarioTecDto.getFabricante() + "%");
        }
        if (filtroConsultaInventarioTecDto.getSerial() != null
                && !filtroConsultaInventarioTecDto.getSerial().isEmpty()) {
            query.setParameter("serial", "%" + filtroConsultaInventarioTecDto.getSerial() + "%");
        }
        if (filtroConsultaInventarioTecDto.getReferencia() != null
                && !filtroConsultaInventarioTecDto.getReferencia().isEmpty()) {
            query.setParameter("referencia", "%" + filtroConsultaInventarioTecDto.getReferencia() + "%");
        }
        if (filtroConsultaInventarioTecDto.getMac() != null
                && !filtroConsultaInventarioTecDto.getMac().isEmpty()) {
            query.setParameter("mac", "%" + filtroConsultaInventarioTecDto.getMac() + "%");
        }
        if (filtroConsultaInventarioTecDto.getEstado() != 0) {
            query.setParameter("estado", filtroConsultaInventarioTecDto.getEstado());
        }
        if (filtroConsultaInventarioTecDto.getTorre() != null
                && !filtroConsultaInventarioTecDto.getTorre().isEmpty()) {
            query.setParameter("torre", "%" + filtroConsultaInventarioTecDto.getMac() + "%");
        }
        if (filtroConsultaInventarioTecDto.getTecnologia() != null
                && !filtroConsultaInventarioTecDto.getTecnologia().isEmpty()) {
            query.setParameter("tecnologia", "%" + filtroConsultaInventarioTecDto.getTecnologia() + "%");
        }

        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultCount = (Long) query.getSingleResult();
        return resultCount;
    }

    public List<CmtInventariosTecnologiaMgl> findPaginacion(int firstResult,
            int maxResults, CmtCuentaMatrizMgl cmtCuentaMatrizMgl,
            FiltroConsultaInventarioTecDto filtroConsultaInventarioTecDto)
            throws ApplicationException {
        List<CmtInventariosTecnologiaMgl> resultList;
        String queryString;
        queryString = "SELECT h FROM CmtInventariosTecnologiaMgl h WHERE h.tecnoSubedificioId.subedificioId.cuentaMatrizObj = :cuentaMatrizObj ";

        if (cmtCuentaMatrizMgl == null
                || cmtCuentaMatrizMgl.getCuentaMatrizId() == null
                || cmtCuentaMatrizMgl.getCuentaMatrizId().equals(BigDecimal.ZERO)) {
            throw new ApplicationException("El filtro cuenta matriz es obligatorio");
        }
        if (filtroConsultaInventarioTecDto.getTipoInv() != null
                && !filtroConsultaInventarioTecDto.getTipoInv().isEmpty()) {
            queryString += " AND  h.tipoInventario LIKE :tipoInv ";
        }
        if (filtroConsultaInventarioTecDto.getClaseInv() != null
                && !filtroConsultaInventarioTecDto.getClaseInv().isEmpty()) {
            queryString += " AND  h.claseInventario LIKE :claseInv ";
        }
        if (filtroConsultaInventarioTecDto.getFabricante() != null
                && !filtroConsultaInventarioTecDto.getFabricante().isEmpty()) {
            queryString += " AND  h.fabricante LIKE :fabricante ";
        }
        if (filtroConsultaInventarioTecDto.getSerial() != null
                && !filtroConsultaInventarioTecDto.getSerial().isEmpty()) {
            queryString += " AND  h.serial = :serial ";
        }
        if (filtroConsultaInventarioTecDto.getReferencia() != null
                && !filtroConsultaInventarioTecDto.getReferencia().isEmpty()) {
            queryString += " AND  h.referencia LIKE :referencia ";
        }
        if (filtroConsultaInventarioTecDto.getMac() != null
                && !filtroConsultaInventarioTecDto.getMac().isEmpty()) {
            queryString += " AND  h.mac LIKE :mac ";
        }
        if (filtroConsultaInventarioTecDto.getEstado() != 0) {
            queryString += " AND  h.estado  = :estado ";
        }
        if (filtroConsultaInventarioTecDto.getTorre() != null && !filtroConsultaInventarioTecDto.getTorre().isEmpty()) {
            queryString += " AND  h.tecnoSubedificioId.subedificioId.nombreSubedificio  LIKE :torre ";
        }
        if (filtroConsultaInventarioTecDto.getTecnologia() != null && !filtroConsultaInventarioTecDto.getTecnologia().isEmpty()) {
            queryString += " AND  h.tecnoSubedificioId.basicaIdTecnologias.nombreBasica  LIKE :tecnologia ";
        }

        Query query = entityManager.createQuery(queryString);

        query.setParameter("cuentaMatrizObj", cmtCuentaMatrizMgl);
        if (filtroConsultaInventarioTecDto.getTipoInv() != null
                && !filtroConsultaInventarioTecDto.getTipoInv().isEmpty()) {
            query.setParameter("tipoInv", "%" + filtroConsultaInventarioTecDto.getTipoInv() + "%");
        }
        if (filtroConsultaInventarioTecDto.getClaseInv() != null
                && !filtroConsultaInventarioTecDto.getClaseInv().isEmpty()) {
            query.setParameter("claseInv", "%" + filtroConsultaInventarioTecDto.getClaseInv() + "%");
        }
        if (filtroConsultaInventarioTecDto.getFabricante() != null
                && !filtroConsultaInventarioTecDto.getFabricante().isEmpty()) {
            query.setParameter("fabricante", "%" + filtroConsultaInventarioTecDto.getFabricante() + "%");
        }
        if (filtroConsultaInventarioTecDto.getSerial() != null
                && !filtroConsultaInventarioTecDto.getSerial().isEmpty()) {
            query.setParameter("serial", "%" + filtroConsultaInventarioTecDto.getSerial() + "%");
        }
        if (filtroConsultaInventarioTecDto.getReferencia() != null
                && !filtroConsultaInventarioTecDto.getReferencia().isEmpty()) {
            query.setParameter("referencia", "%" + filtroConsultaInventarioTecDto.getReferencia() + "%");
        }
        if (filtroConsultaInventarioTecDto.getMac() != null
                && !filtroConsultaInventarioTecDto.getMac().isEmpty()) {
            query.setParameter("mac", "%" + filtroConsultaInventarioTecDto.getMac() + "%");
        }
        if (filtroConsultaInventarioTecDto.getEstado() != 0) {
            query.setParameter("estado", filtroConsultaInventarioTecDto.getEstado());
        }

        if (filtroConsultaInventarioTecDto.getTorre() != null
                && !filtroConsultaInventarioTecDto.getTorre().isEmpty()) {
            query.setParameter("torre", "%" + filtroConsultaInventarioTecDto.getTorre() + "%");
        }

        if (filtroConsultaInventarioTecDto.getTecnologia() != null
                && !filtroConsultaInventarioTecDto.getTecnologia().isEmpty()) {
            query.setParameter("tecnologia", "%" + filtroConsultaInventarioTecDto.getTecnologia() + "%");
        }
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtInventariosTecnologiaMgl>) query.getResultList();
        return resultList;
    }

    public Long countBySubCm(CmtSubEdificioMgl cmtSubEdificioMgl,
            FiltroConsultaInventarioTecDto filtroConsultaInventarioTecDto)
            throws ApplicationException {
        Long resultCount = 0L;
        String queryString;
        queryString = "SELECT COUNT(1) FROM CmtInventariosTecnologiaMgl h WHERE h.tecnoSubedificioId.subedificioId = :subEdificioObj ";

        if (cmtSubEdificioMgl == null
                || cmtSubEdificioMgl.getSubEdificioId() == null
                || cmtSubEdificioMgl.getSubEdificioId().equals(BigDecimal.ZERO)) {
            throw new ApplicationException("El filtro Sub Edificio es obligatorio");
        }
        if (filtroConsultaInventarioTecDto.getTipoInv() != null
                && !filtroConsultaInventarioTecDto.getTipoInv().isEmpty()) {
            queryString += " AND  h.tipoInventario LIKE :tipoInv ";
        }
        if (filtroConsultaInventarioTecDto.getClaseInv() != null
                && !filtroConsultaInventarioTecDto.getClaseInv().isEmpty()) {
            queryString += " AND  h.claseInventario LIKE :claseInv ";
        }
        if (filtroConsultaInventarioTecDto.getFabricante() != null
                && !filtroConsultaInventarioTecDto.getFabricante().isEmpty()) {
            queryString += " AND  h.fabricante LIKE :fabricante ";
        }
        if (filtroConsultaInventarioTecDto.getSerial() != null
                && !filtroConsultaInventarioTecDto.getSerial().isEmpty()) {
            queryString += " AND  h.serial = :serial ";
        }
        if (filtroConsultaInventarioTecDto.getReferencia() != null
                && !filtroConsultaInventarioTecDto.getReferencia().isEmpty()) {
            queryString += " AND  h.referencia LIKE :referencia ";
        }
        if (filtroConsultaInventarioTecDto.getMac() != null
                && !filtroConsultaInventarioTecDto.getMac().isEmpty()) {
            queryString += " AND  h.mac LIKE :mac ";
        }
        if (filtroConsultaInventarioTecDto.getEstado() != 0) {
            queryString += " AND  h.estado  = :estado ";
        }
        if (filtroConsultaInventarioTecDto.getTorre() != null && !filtroConsultaInventarioTecDto.getTorre().isEmpty()) {
            queryString += " AND  h.tecnoSubedificioId.subedificioId.nombreSubedificio  LIKE :torre ";
        }
        if (filtroConsultaInventarioTecDto.getTecnologia() != null && !filtroConsultaInventarioTecDto.getTecnologia().isEmpty()) {
            queryString += " AND  h.tecnoSubedificioId.basicaIdTecnologias.nombreBasica  LIKE :tecnologia ";
        }

        Query query = entityManager.createQuery(queryString);

        query.setParameter("subEdificioObj", cmtSubEdificioMgl);
        if (filtroConsultaInventarioTecDto.getTipoInv() != null
                && !filtroConsultaInventarioTecDto.getTipoInv().isEmpty()) {
            query.setParameter("tipoInv", "%" + filtroConsultaInventarioTecDto.getTipoInv() + "%");
        }
        if (filtroConsultaInventarioTecDto.getClaseInv() != null
                && !filtroConsultaInventarioTecDto.getClaseInv().isEmpty()) {
            query.setParameter("claseInv", "%" + filtroConsultaInventarioTecDto.getClaseInv() + "%");
        }
        if (filtroConsultaInventarioTecDto.getFabricante() != null
                && !filtroConsultaInventarioTecDto.getFabricante().isEmpty()) {
            query.setParameter("fabricante", "%" + filtroConsultaInventarioTecDto.getFabricante() + "%");
        }
        if (filtroConsultaInventarioTecDto.getSerial() != null
                && !filtroConsultaInventarioTecDto.getSerial().isEmpty()) {
            query.setParameter("serial", "%" + filtroConsultaInventarioTecDto.getSerial() + "%");
        }
        if (filtroConsultaInventarioTecDto.getReferencia() != null
                && !filtroConsultaInventarioTecDto.getReferencia().isEmpty()) {
            query.setParameter("referencia", "%" + filtroConsultaInventarioTecDto.getReferencia() + "%");
        }
        if (filtroConsultaInventarioTecDto.getMac() != null
                && !filtroConsultaInventarioTecDto.getMac().isEmpty()) {
            query.setParameter("mac", "%" + filtroConsultaInventarioTecDto.getMac() + "%");
        }
        if (filtroConsultaInventarioTecDto.getEstado() != 0) {
            query.setParameter("estado", filtroConsultaInventarioTecDto.getEstado());
        }

        if (filtroConsultaInventarioTecDto.getTorre() != null
                && !filtroConsultaInventarioTecDto.getTorre().isEmpty()) {
            query.setParameter("torre", "%" + filtroConsultaInventarioTecDto.getTorre() + "%");
        }
        if (filtroConsultaInventarioTecDto.getTecnologia() != null
                && !filtroConsultaInventarioTecDto.getTecnologia().isEmpty()) {
            query.setParameter("tecnologia", "%" + filtroConsultaInventarioTecDto.getTecnologia() + "%");
        }
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultCount = (Long) query.getSingleResult();
        return resultCount;
    }

    public List<CmtInventariosTecnologiaMgl> findPaginacionSub(int firstResult,
            int maxResults, CmtSubEdificioMgl cmtSubEdificioMgl,
            FiltroConsultaInventarioTecDto filtroConsultaInventarioTecDto)
            throws ApplicationException {
        List<CmtInventariosTecnologiaMgl> resultList;
        String queryString;
        queryString = "SELECT h FROM CmtInventariosTecnologiaMgl h WHERE h.tecnoSubedificioId.subedificioId= :subEdificioObj ";

        if (cmtSubEdificioMgl == null
                || cmtSubEdificioMgl.getSubEdificioId() == null
                || cmtSubEdificioMgl.getSubEdificioId().equals(BigDecimal.ZERO)) {
            throw new ApplicationException("El filtro Sub Edificio es obligatorio");
        }
        if (filtroConsultaInventarioTecDto.getTipoInv() != null
                && !filtroConsultaInventarioTecDto.getTipoInv().isEmpty()) {
            queryString += " AND  h.tipoInventario LIKE :tipoInv ";
        }
        if (filtroConsultaInventarioTecDto.getClaseInv() != null
                && !filtroConsultaInventarioTecDto.getClaseInv().isEmpty()) {
            queryString += " AND  h.claseInventario LIKE :claseInv ";
        }
        if (filtroConsultaInventarioTecDto.getFabricante() != null
                && !filtroConsultaInventarioTecDto.getFabricante().isEmpty()) {
            queryString += " AND  h.fabricante LIKE :fabricante ";
        }
        if (filtroConsultaInventarioTecDto.getSerial() != null
                && !filtroConsultaInventarioTecDto.getSerial().isEmpty()) {
            queryString += " AND  h.serial = :serial ";
        }
        if (filtroConsultaInventarioTecDto.getReferencia() != null
                && !filtroConsultaInventarioTecDto.getReferencia().isEmpty()) {
            queryString += " AND  h.referencia LIKE :referencia ";
        }
        if (filtroConsultaInventarioTecDto.getMac() != null
                && !filtroConsultaInventarioTecDto.getMac().isEmpty()) {
            queryString += " AND  h.mac LIKE :mac ";
        }
        if (filtroConsultaInventarioTecDto.getEstado() != 0) {
            queryString += " AND  h.estado  = :estado ";
        }
        if (filtroConsultaInventarioTecDto.getTorre() != null && !filtroConsultaInventarioTecDto.getTorre().isEmpty()) {
            queryString += " AND  h.tecnoSubedificioId.subedificioId.nombreSubedificio  LIKE :torre ";
        }
        if (filtroConsultaInventarioTecDto.getTecnologia() != null && !filtroConsultaInventarioTecDto.getTecnologia().isEmpty()) {
            queryString += " AND  h.tecnoSubedificioId.basicaIdTecnologias.nombreBasica  LIKE :tecnologia ";
        }

        queryString += " ORDER BY h.tecnoSubedificioId.subedificioId.subEdificioId asc ";

        Query query = entityManager.createQuery(queryString);

        query.setParameter("subEdificioObj", cmtSubEdificioMgl);
        if (filtroConsultaInventarioTecDto.getTipoInv() != null
                && !filtroConsultaInventarioTecDto.getTipoInv().isEmpty()) {
            query.setParameter("tipoInv", "%" + filtroConsultaInventarioTecDto.getTipoInv() + "%");
        }
        if (filtroConsultaInventarioTecDto.getClaseInv() != null
                && !filtroConsultaInventarioTecDto.getClaseInv().isEmpty()) {
            query.setParameter("claseInv", "%" + filtroConsultaInventarioTecDto.getClaseInv() + "%");
        }
        if (filtroConsultaInventarioTecDto.getFabricante() != null
                && !filtroConsultaInventarioTecDto.getFabricante().isEmpty()) {
            query.setParameter("fabricante", "%" + filtroConsultaInventarioTecDto.getFabricante() + "%");
        }
        if (filtroConsultaInventarioTecDto.getSerial() != null
                && !filtroConsultaInventarioTecDto.getSerial().isEmpty()) {
            query.setParameter("serial", "%" + filtroConsultaInventarioTecDto.getSerial() + "%");
        }
        if (filtroConsultaInventarioTecDto.getReferencia() != null
                && !filtroConsultaInventarioTecDto.getReferencia().isEmpty()) {
            query.setParameter("referencia", "%" + filtroConsultaInventarioTecDto.getReferencia() + "%");
        }
        if (filtroConsultaInventarioTecDto.getMac() != null
                && !filtroConsultaInventarioTecDto.getMac().isEmpty()) {
            query.setParameter("mac", "%" + filtroConsultaInventarioTecDto.getMac() + "%");
        }
        if (filtroConsultaInventarioTecDto.getEstado() != 0) {
            query.setParameter("estado", filtroConsultaInventarioTecDto.getEstado());
        }

        if (filtroConsultaInventarioTecDto.getTorre() != null
                && !filtroConsultaInventarioTecDto.getTorre().isEmpty()) {
            query.setParameter("torre", "%" + filtroConsultaInventarioTecDto.getTorre() + "%");
        }
        if (filtroConsultaInventarioTecDto.getTecnologia() != null
                && !filtroConsultaInventarioTecDto.getTecnologia().isEmpty()) {
            query.setParameter("tecnologia", "%" + filtroConsultaInventarioTecDto.getTecnologia() + "%");
        }

        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtInventariosTecnologiaMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtInventariosTecnologiaMgl> findByTecSub(CmtTecnologiaSubMgl cmtTecnologiaSubMgl) throws ApplicationException {
        try {
            List<CmtInventariosTecnologiaMgl> resulList;
            Query query = entityManager.createNamedQuery("CmtInventariosTecnologiaMgl.findByTecnoSubedificioId");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (cmtTecnologiaSubMgl != null) {
                query.setParameter("tecnoSubedificioId", cmtTecnologiaSubMgl);
            }
            resulList = (List<CmtInventariosTecnologiaMgl>) query.getResultList();
            return resulList;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
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

        try {
            List<CmtInventariosTecnologiaMgl> resultList;
            String q = " SELECT invTec FROM CmtInventariosTecnologiaMgl invTec, CmtTecnologiaSubMgl tecSub, "
                    + " CmtSubEdificiosVt subVt, CmtVisitaTecnicaMgl vt, CmtOrdenTrabajoMgl ot"
                    + " WHERE   tecSub.tecnoSubedificioId = invTec.tecnoSubedificioId.tecnoSubedificioId "
                    + " AND  subVt.subEdificioObj.subEdificioId = tecSub.subedificioId.subEdificioId "
                    + " AND  vt.idVt = subVt.vtObj.idVt "
                    + " AND  ot.idOt = vt.otObj.idOt "
                    + " AND  ot.idOt = :idOt "
                    + " AND  tecSub.basicaIdTecnologias.basicaId  = :basicaId "
                    + " AND  invTec.estadoRegistro <> 0 "
                    + " AND  vt.estadoVisitaTecnica=1 ";

            Query query = entityManager.createQuery(q);
            query.setParameter("idOt", OtId);
            query.setParameter("basicaId", tecnologia);

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<CmtInventariosTecnologiaMgl> rows = query.getResultList();
            resultList = new ArrayList<CmtInventariosTecnologiaMgl>();
            for (CmtInventariosTecnologiaMgl row : rows) {
                CmtInventariosTecnologiaMgl cmtInventariosTecnologiaMgl = new CmtInventariosTecnologiaMgl();
                cmtInventariosTecnologiaMgl.setInventarioTecnologiaId(row.getInventarioTecnologiaId());
                cmtInventariosTecnologiaMgl.setTecnoSubedificioId(row.getTecnoSubedificioId());
                cmtInventariosTecnologiaMgl.setSistemaInventarioId(row.getSistemaInventarioId());
                cmtInventariosTecnologiaMgl.setTipoInventario(row.getTipoInventario());
                cmtInventariosTecnologiaMgl.setClaseInventario(row.getClaseInventario());
                cmtInventariosTecnologiaMgl.setFabricante(row.getFabricante());
                cmtInventariosTecnologiaMgl.setSerial(row.getSerial());
                cmtInventariosTecnologiaMgl.setReferencia(row.getReferencia());
                cmtInventariosTecnologiaMgl.setMac(row.getMac());
                cmtInventariosTecnologiaMgl.setFechaCreacion(row.getFechaCreacion());
                cmtInventariosTecnologiaMgl.setUsuarioCreacion(row.getUsuarioCreacion());
                cmtInventariosTecnologiaMgl.setPerfilCreacion(row.getPerfilCreacion());
                cmtInventariosTecnologiaMgl.setFechaEdicion(row.getFechaEdicion());
                cmtInventariosTecnologiaMgl.setUsuarioEdicion(row.getUsuarioEdicion());
                cmtInventariosTecnologiaMgl.setPerfilEdicion(row.getPerfilEdicion());
                cmtInventariosTecnologiaMgl.setEstadoRegistro(row.getEstadoRegistro());

                resultList.add(cmtInventariosTecnologiaMgl);

            }
            return resultList;

        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
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
        try {
            CmtInventariosTecnologiaMgl resulList;
            Query query = entityManager.createNamedQuery("CmtInventariosTecnologiaMgl.findByInventarioTecnologiaId");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (InvTecId != null) {
                query.setParameter("inventarioTecnologiaId", InvTecId);
            }
            resulList = (CmtInventariosTecnologiaMgl) query.getSingleResult();
            return resulList;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }
}
