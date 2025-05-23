package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ExportFichasMgl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.persistence.Query;


public class ExportFichasMglDaoImpl extends GenericDaoImpl<ExportFichasMgl> {
    
    private static final Logger LOGGER = LogManager.getLogger(ExportFichasMglDaoImpl.class);
    
    public List<ExportFichasMgl> findListadoHistoricosFicha(ExportFichasMgl filtro, int firstResult,int maxResults) throws ApplicationException{
        try {
            StringBuilder consulta = new StringBuilder("SELECT h FROM ExportFichasMgl h WHERE h.prefichaId IS NOT NULL ");
            if(filtro.getNodo() != null && filtro.getNodo().getNodId() != null && filtro.getNodo().getNodId().intValue() != 0){
                consulta.append("AND h.nodo.nodId = :idNodo ");
            }
            if(filtro.getTipoTecnologiaId() != null && filtro.getTipoTecnologiaId().getBasicaId() != null && filtro.getTipoTecnologiaId().getBasicaId().intValue() != 0){
                consulta.append("AND h.tipoTecnologiaId.basicaId = :idTecnologia ");
            }
            if(filtro.getDepartamento() != null && filtro.getDepartamento().getGpoId() != null && filtro.getDepartamento().getGpoId().intValue() != 0){
                consulta.append("AND h.departamento.gpoId = :departamento ");
            }
            if(filtro.getCiudad() != null && filtro.getCiudad().getGpoId() != null && filtro.getCiudad().getGpoId().intValue() != 0){
                consulta.append("AND h.ciudad.gpoId = :ciudad ");
            }
            if(filtro.getCentroPoblado() != null && filtro.getCentroPoblado().getGpoId() != null && filtro.getCentroPoblado().getGpoId().intValue() != 0){
               consulta.append("AND h.centroPoblado.gpoId = :centroPoblado ");
            }
            if(filtro.getFechaCreacion() != null){
                consulta.append("AND h.fechaCreacion BETWEEN  :fechaCreacionI AND :fechaCreacionF " );
            }
            if(filtro.getNombreArchivo() != null && !filtro.getNombreArchivo().isEmpty()){
                consulta.append("AND h.nombreArchivo LIKE :nombreArchivo ");
            }
            if(filtro.getUsuarioCrea() != null && !filtro.getUsuarioCrea().isEmpty()){
                consulta.append("AND h.usuarioCrea LIKE :usuarioCrea ");
            }
            consulta.append("ORDER BY h.nodo.nodCodigo ASC,h.fechaCreacion DESC");
            Query query = entityManager.createQuery(consulta.toString());
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);
            if(filtro.getNodo() != null && filtro.getNodo().getNodId() != null && filtro.getNodo().getNodId().intValue() != 0){
                query.setParameter("idNodo", filtro.getNodo().getNodId());
            }
            if(filtro.getTipoTecnologiaId() != null && filtro.getTipoTecnologiaId().getBasicaId() != null && filtro.getTipoTecnologiaId().getBasicaId().intValue() != 0){
                query.setParameter("idTecnologia", filtro.getTipoTecnologiaId().getBasicaId());
            }
            if(filtro.getDepartamento() != null && filtro.getDepartamento().getGpoId() != null && filtro.getDepartamento().getGpoId().intValue() != 0){
                query.setParameter("departamento", filtro.getDepartamento().getGpoId());
            }
            if(filtro.getCiudad() != null && filtro.getCiudad().getGpoId() != null && filtro.getCiudad().getGpoId().intValue() != 0){
                query.setParameter("ciudad", filtro.getCiudad().getGpoId());
            }
            if(filtro.getCentroPoblado() != null && filtro.getCentroPoblado().getGpoId() != null && filtro.getCentroPoblado().getGpoId().intValue() != 0){
               query.setParameter("centroPoblado", filtro.getCentroPoblado().getGpoId());
            }
            if(filtro.getFechaCreacion() != null){
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat formatoFin = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                query.setParameter("fechaCreacionI", filtro.getFechaCreacion());
                String fechaFin = formato.format(filtro.getFechaCreacion());
                fechaFin = fechaFin +" 23:59:59";
                query.setParameter("fechaCreacionF", formatoFin.parse(fechaFin));
            }
            if(filtro.getNombreArchivo() != null && !filtro.getNombreArchivo().isEmpty()){
                query.setParameter("nombreArchivo", "%"+filtro.getNombreArchivo()+"%");
            }
            if(filtro.getUsuarioCrea() != null && !filtro.getUsuarioCrea().isEmpty()){
                query.setParameter("usuarioCrea", "%"+filtro.getUsuarioCrea()+"%");
            }
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error consultando listado historicos fichas. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    public List<ExportFichasMgl> findHistoricoFichaPorIdPreficha(BigDecimal idPreficha) throws ApplicationException{
        try {
            Query query = entityManager.createQuery("SELECT h FROM ExportFichasMgl h WHERE h.prefichaId.prfId = :idPreficha");
            query.setParameter("idPreficha", idPreficha);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error consultando listado historicos fichas por id preficha. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    
   public int countListadoHistoricosFicha(ExportFichasMgl filtro)throws ApplicationException{
        try {
            StringBuilder consulta = new StringBuilder("SELECT COUNT(h) FROM ExportFichasMgl h WHERE h.prefichaId IS NOT NULL ");
            if(filtro.getNodo() != null && filtro.getNodo().getNodId() != null && filtro.getNodo().getNodId().intValue() != 0){
                consulta.append("AND h.nodo.nodId = :idNodo ");
            }
            if(filtro.getTipoTecnologiaId() != null && filtro.getTipoTecnologiaId().getBasicaId() != null && filtro.getTipoTecnologiaId().getBasicaId().intValue() != 0){
                consulta.append("AND h.tipoTecnologiaId.basicaId = :idTecnologia ");
            }
            if(filtro.getDepartamento() != null && filtro.getDepartamento().getGpoId() != null && filtro.getDepartamento().getGpoId().intValue() != 0){
                consulta.append("AND h.departamento.gpoId = :departamento ");
            }
            if(filtro.getCiudad() != null && filtro.getCiudad().getGpoId() != null && filtro.getCiudad().getGpoId().intValue() != 0){
                consulta.append("AND h.ciudad.gpoId = :ciudad ");
            }
            if(filtro.getCentroPoblado() != null && filtro.getCentroPoblado().getGpoId() != null && filtro.getCentroPoblado().getGpoId().intValue() != 0){
               consulta.append("AND h.centroPoblado.gpoId = :centroPoblado ");
            }
            if(filtro.getFechaCreacion() != null){
                consulta.append("AND h.fechaCreacion BETWEEN  :fechaCreacionI AND :fechaCreacionF " );
            }
            if(filtro.getNombreArchivo() != null && !filtro.getNombreArchivo().isEmpty()){
                consulta.append("AND h.nombreArchivo LIKE :nombreArchivo ");
            }
            if(filtro.getUsuarioCrea() != null && !filtro.getUsuarioCrea().isEmpty()){
                consulta.append("AND h.usuarioCrea LIKE :usuarioCrea ");
            }
            consulta.append("ORDER BY h.fechaCreacion DESC ");
            Query query = entityManager.createQuery(consulta.toString());
            if(filtro.getNodo() != null && filtro.getNodo().getNodId() != null && filtro.getNodo().getNodId().intValue() != 0){
                query.setParameter("idNodo", filtro.getNodo().getNodId());
            }
            if(filtro.getTipoTecnologiaId() != null && filtro.getTipoTecnologiaId().getBasicaId() != null && filtro.getTipoTecnologiaId().getBasicaId().intValue() != 0){
                query.setParameter("idTecnologia", filtro.getTipoTecnologiaId().getBasicaId());
            }
            if(filtro.getDepartamento() != null && filtro.getDepartamento().getGpoId() != null && filtro.getDepartamento().getGpoId().intValue() != 0){
                query.setParameter("departamento", filtro.getDepartamento().getGpoId());
            }
            if(filtro.getCiudad() != null && filtro.getCiudad().getGpoId() != null && filtro.getCiudad().getGpoId().intValue() != 0){
                query.setParameter("ciudad", filtro.getCiudad().getGpoId());
            }
            if(filtro.getCentroPoblado() != null && filtro.getCentroPoblado().getGpoId() != null && filtro.getCentroPoblado().getGpoId().intValue() != 0){
               query.setParameter("centroPoblado", filtro.getCentroPoblado().getGpoId());
            }
            if(filtro.getFechaCreacion() != null){
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat formatoFin = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                query.setParameter("fechaCreacionI", filtro.getFechaCreacion());
                String fechaFin = formato.format(filtro.getFechaCreacion());
                fechaFin = fechaFin +" 23:59:59";
                query.setParameter("fechaCreacionF", formatoFin.parse(fechaFin));
            }
            if(filtro.getNombreArchivo() != null && !filtro.getNombreArchivo().isEmpty()){
                query.setParameter("nombreArchivo", "%"+filtro.getNombreArchivo()+"%");
            }
            if(filtro.getUsuarioCrea() != null && !filtro.getUsuarioCrea().isEmpty()){
                query.setParameter("usuarioCrea", "%"+filtro.getUsuarioCrea()+"%");
            }
            return query.getSingleResult() != null ? ((Long)query.getSingleResult()).intValue() : 0;
        } catch (Exception e) {
            LOGGER.error("Error contando historicos fichas. ", e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
}
