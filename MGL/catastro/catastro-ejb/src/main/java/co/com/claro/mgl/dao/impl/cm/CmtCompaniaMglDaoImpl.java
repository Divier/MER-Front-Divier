
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.dtos.FiltroConsultaCompaniasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCompaniaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoCompaniaMgl;
import co.com.claro.mgl.utils.Constant;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class CmtCompaniaMglDaoImpl extends GenericDaoImpl<CmtCompaniaMgl> {

    public List<CmtCompaniaMgl> findAll() throws ApplicationException {
        List<CmtCompaniaMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtCompaniaMgl.findAll");
        resultList = (List<CmtCompaniaMgl>) query.getResultList();
        getEntityManager().clear();
        return resultList;
    }

    public List<CmtCompaniaMgl> findByTipoCompania(CmtTipoCompaniaMgl tipoCompaniaId) throws ApplicationException {
        List<CmtCompaniaMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtCompaniaMgl.findByTipoCompania");
        query.setParameter("tipoCompania", tipoCompaniaId);
        resultList = (List<CmtCompaniaMgl>) query.getResultList();
        getEntityManager().clear();
        return resultList;
    }

    /**
     * Busca las compañias Ascensores, Administracion y Constructoras de acuerdo
     * a la ciudad o centro poblado
     *
     * @author yimy diaz
     * @param municipio
     * @param tipoCompania
     * @return List<CmtCompaniaMgl>
     * @throws ApplicationException
     */
    public List<CmtCompaniaMgl> findByMunicipioByTipeCompany(GeograficoPoliticoMgl municipio,
            CmtTipoCompaniaMgl tipoCompania) throws ApplicationException {
        List<CmtCompaniaMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtCompaniaMgl.findByCiudad");
        query.setParameter("municipio", municipio);
        query.setParameter("tipoCompania", tipoCompania);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<CmtCompaniaMgl>) query.getResultList();
        getEntityManager().clear();
        return resultList;
    }

    public List<CmtCompaniaMgl> findByCoderr(CmtCompaniaMgl Compania) throws ApplicationException {
        List<CmtCompaniaMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtCompaniaMgl.findByCoderr");
        query.setParameter("tipoCompaniaId", Compania.getTipoCompaniaObj().getTipoCompaniaId());
        query.setParameter("codigoRr", Compania.getCodigoRr());
        resultList = (List<CmtCompaniaMgl>) query.getResultList();
        getEntityManager().clear();
        return resultList;
    }

    public List<CmtCompaniaMgl> findByfiltro(FiltroConsultaCompaniasDto filtro, boolean ordenarPorCodigo) throws ApplicationException {
        List<CmtCompaniaMgl> resultList;
        if (filtro.getTipoCompania() == null || filtro.getTipoCompania().equals(BigDecimal.ZERO)) {
            throw new ApplicationException("el filtro de basica debe tener un valor para TipoBasica");
        }
        String queryTipo = "SELECT c FROM CmtCompaniaMgl c WHERE c.estadoRegistro=1 ";
        queryTipo += "AND c.tipoCompaniaObj.tipoCompaniaId= :tipoCompaniaId ";

        if (filtro.getNombre() != null && !filtro.getNombre().isEmpty()) {
            queryTipo += "AND UPPER(c.nombreCompania) LIKE :nombreCompania ";
        }
        if (filtro.getNit() != null && !filtro.getNit().isEmpty()) {
            queryTipo += "AND c.nitCompania LIKE :nitCompania ";
        }

        if (filtro.getDepartamento() != null) {
            queryTipo += "AND c.departamento.gpoId = :departamento ";
        }

        if (filtro.getMunicipio() != null) {
            queryTipo += "AND c.municipio.gpoId = :municipio ";
        }

        if (filtro.getCentroPoblado() != null) {
            queryTipo += "AND c.centroPoblado.gpoId = :centroPoblado ";
        }

        if (filtro.getBarrio() != null && !filtro.getBarrio().isEmpty()) {
            queryTipo += "AND c.barrio LIKE :barrio ";
        }

        if (filtro.getEstado() != null && !filtro.getEstado().isEmpty()) {
            queryTipo += "AND c.activado = :activado ";
        }
        
        if (ordenarPorCodigo) {

            queryTipo += " ORDER BY CAST(c.codigoRr AS NUMERIC) ASC ";
        } else {
            queryTipo += " ORDER BY c.nombreCompania ASC ";
        }

        Query query = entityManager.createQuery(queryTipo);

        query.setParameter("tipoCompaniaId", filtro.getTipoCompania());

        if (filtro.getNombre() != null && !filtro.getNombre().isEmpty()) {
            query.setParameter("nombreCompania", "%" + filtro.getNombre().toUpperCase() + "%");
        }
        if (filtro.getNit() != null && !filtro.getNit().isEmpty()) {
            query.setParameter("nitCompania", filtro.getNit() + "%");
        }
        if (filtro.getDepartamento() != null) {
            query.setParameter("departamento", filtro.getDepartamento());
        }
        if (filtro.getMunicipio() != null) {
            query.setParameter("municipio", filtro.getMunicipio());
        }

        if (filtro.getCentroPoblado() != null) {
            query.setParameter("centroPoblado", filtro.getCentroPoblado());
        }

        if (filtro.getBarrio() != null && !filtro.getBarrio().isEmpty()) {
            query.setParameter("barrio", filtro.getBarrio() + "%");
        }

        if (filtro.getEstado() != null && !filtro.getEstado().isEmpty()) {
            query.setParameter("activado", filtro.getEstado());
        }
        resultList = (List<CmtCompaniaMgl>) query.getResultList();
        getEntityManager().clear();        
        return resultList;
    }
    
   /**
     * *Victor Bocanegra Metodo para conseguir las compañias por los filtros de la
     * tabla y paginado
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
        
        List<CmtCompaniaMgl> resultList;
        if (filtro.getTipoCompania() == null || filtro.getTipoCompania().equals(BigDecimal.ZERO)) {
            throw new ApplicationException("el filtro de basica debe tener un valor para TipoBasica");
        }
        String queryTipo = "SELECT c FROM CmtCompaniaMgl c WHERE c.estadoRegistro=1 ";
        queryTipo += "AND c.tipoCompaniaObj.tipoCompaniaId= :tipoCompaniaId ";

        if (filtro.getNombre() != null && !filtro.getNombre().isEmpty()) {
            queryTipo += "AND UPPER(c.nombreCompania) LIKE :nombreCompania ";
        }
        if (filtro.getNit() != null && !filtro.getNit().isEmpty()) {
            queryTipo += "AND c.nitCompania LIKE :nitCompania ";
        }

        if (filtro.getDepartamento() != null) {
            queryTipo += "AND c.departamento.gpoId = :departamento ";
        }

        if (filtro.getMunicipio() != null) {
            queryTipo += "AND c.municipio.gpoId = :municipio ";
        }

        if (filtro.getCentroPoblado() != null) {
            queryTipo += "AND c.centroPoblado.gpoId = :centroPoblado ";
        }

        if (filtro.getBarrio() != null && !filtro.getBarrio().isEmpty()) {
            queryTipo += "AND c.barrio LIKE :barrio ";
        }

        if (filtro.getEstado() != null && !filtro.getEstado().isEmpty()) {
            queryTipo += "AND c.activado = :activado ";
        }
        
        if (ordenarPorCodigo) {

            queryTipo += " ORDER BY CAST(c.codigoRr AS NUMERIC) ASC ";
        } else {
            queryTipo += " ORDER BY c.nombreCompania ASC ";
        }

        Query query = entityManager.createQuery(queryTipo);

        query.setParameter("tipoCompaniaId", filtro.getTipoCompania());

        if (filtro.getNombre() != null && !filtro.getNombre().isEmpty()) {
            query.setParameter("nombreCompania", "%" + filtro.getNombre().toUpperCase() + "%");
        }
        if (filtro.getNit() != null && !filtro.getNit().isEmpty()) {
            query.setParameter("nitCompania", filtro.getNit() + "%");
        }
        if (filtro.getDepartamento() != null) {
            query.setParameter("departamento", filtro.getDepartamento());
        }
        if (filtro.getMunicipio() != null) {
            query.setParameter("municipio", filtro.getMunicipio());
        }

        if (filtro.getCentroPoblado() != null) {
            query.setParameter("centroPoblado", filtro.getCentroPoblado());
        }

        if (filtro.getBarrio() != null && !filtro.getBarrio().isEmpty()) {
            query.setParameter("barrio", filtro.getBarrio() + "%");
        }

        if (filtro.getEstado() != null && !filtro.getEstado().isEmpty()) {
            query.setParameter("activado", filtro.getEstado());
        }
        
        query.setFirstResult(paginaSelected);
        query.setMaxResults(maxResults);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtCompaniaMgl>) query.getResultList();
        getEntityManager().clear();
        return resultList;
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
               
        Long resultCount;
        
        if (filtro.getTipoCompania() == null || filtro.getTipoCompania().equals(BigDecimal.ZERO)) {
            throw new ApplicationException("el filtro de basica debe tener un valor para TipoBasica");
        }
        String queryTipo = "SELECT COUNT(1) FROM CmtCompaniaMgl c WHERE c.estadoRegistro=1 ";
        queryTipo += "AND c.tipoCompaniaObj.tipoCompaniaId= :tipoCompaniaId ";

        if (filtro.getNombre() != null && !filtro.getNombre().isEmpty()) {
            queryTipo += "AND UPPER(c.nombreCompania) LIKE :nombreCompania ";
        }
        if (filtro.getNit() != null && !filtro.getNit().isEmpty()) {
            queryTipo += "AND c.nitCompania LIKE :nitCompania ";
        }

        if (filtro.getDepartamento() != null) {
            queryTipo += "AND c.departamento.gpoId = :departamento ";
        }

        if (filtro.getMunicipio() != null) {
            queryTipo += "AND c.municipio.gpoId = :municipio ";
        }

        if (filtro.getCentroPoblado() != null) {
            queryTipo += "AND c.centroPoblado.gpoId = :centroPoblado ";
        }

        if (filtro.getBarrio() != null && !filtro.getBarrio().isEmpty()) {
            queryTipo += "AND c.barrio LIKE :barrio ";
        }

        if (filtro.getEstado() != null && !filtro.getEstado().isEmpty()) {
            queryTipo += "AND c.activado = :activado ";
        }
        
        if (ordenarPorCodigo) {

            queryTipo += " ORDER BY CAST(c.codigoRr AS NUMERIC) ASC ";
        } else {
            queryTipo += " ORDER BY c.nombreCompania ASC ";
        }

        Query query = entityManager.createQuery(queryTipo);

        query.setParameter("tipoCompaniaId", filtro.getTipoCompania());

        if (filtro.getNombre() != null && !filtro.getNombre().isEmpty()) {
            query.setParameter("nombreCompania", "%" + filtro.getNombre().toUpperCase() + "%");
        }
        if (filtro.getNit() != null && !filtro.getNit().isEmpty()) {
            query.setParameter("nitCompania", filtro.getNit() + "%");
        }
        if (filtro.getDepartamento() != null) {
            query.setParameter("departamento", filtro.getDepartamento());
        }
        if (filtro.getMunicipio() != null) {
            query.setParameter("municipio", filtro.getMunicipio());
        }

        if (filtro.getCentroPoblado() != null) {
            query.setParameter("centroPoblado", filtro.getCentroPoblado());
        }

        if (filtro.getBarrio() != null && !filtro.getBarrio().isEmpty()) {
            query.setParameter("barrio", filtro.getBarrio() + "%");
        }

        if (filtro.getEstado() != null && !filtro.getEstado().isEmpty()) {
            query.setParameter("activado", filtro.getEstado());
        }
        
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultCount = (Long) query.getSingleResult();
        return resultCount;
        
    }

    
    public String buscarUltimoCodigoNumerico(CmtTipoCompaniaMgl tipoCompaniaMgl) throws ApplicationException {
        List<CmtCompaniaMgl> listCmtBasicaMgl;
        if (tipoCompaniaMgl.getTipoCompaniaId().compareTo(Constant.TIPO_COMPANIA_ID_ADMINISTRACION) == 0
                || tipoCompaniaMgl.getTipoCompaniaId().compareTo(Constant.TIPO_COMPANIA_ADMON_NATURAL) == 0) {
            CmtTipoCompaniaMgl otroTipocompania= new CmtTipoCompaniaMgl();
            otroTipocompania.setTipoCompaniaId(Constant.TIPO_COMPANIA_ID_ADMINISTRACION);
            listCmtBasicaMgl = findByTipoCompania(otroTipocompania);
            otroTipocompania.setTipoCompaniaId(Constant.TIPO_COMPANIA_ADMON_NATURAL);
            listCmtBasicaMgl.addAll(findByTipoCompania(otroTipocompania));
        } else {
            listCmtBasicaMgl = findByTipoCompania(tipoCompaniaMgl);            
        }
        if (listCmtBasicaMgl != null && !listCmtBasicaMgl.isEmpty()) {
            Collections.sort(listCmtBasicaMgl,
                    new Comparator<CmtCompaniaMgl>() {
                @Override
                public int compare(CmtCompaniaMgl f1, CmtCompaniaMgl f2) {
                    return ("0000" + (f1.getCodigoRr()==null?"0":f1.getCodigoRr())).substring(("0000" + (f1.getCodigoRr()==null?"0":f1.getCodigoRr())).length() - 4).
                            compareTo(("0000" + (f2.getCodigoRr()==null?"0":f2.getCodigoRr())).substring(("0000" + (f2.getCodigoRr()==null?"0":f2.getCodigoRr())).length() - 4));
                }
            });
            Integer finalValueInteger;
            if (listCmtBasicaMgl.get(listCmtBasicaMgl.size() - 1).getCodigoRr() == null) {
                finalValueInteger=0;
            } else {
                finalValueInteger = new Integer(listCmtBasicaMgl.get(listCmtBasicaMgl.size() - 1).getCodigoRr());
            }
            finalValueInteger += 1;
            if (finalValueInteger.compareTo(new Integer(9999)) > 0) {
                return "0";
            }
            return finalValueInteger.toString();
        }
        return "1";
    }

}
