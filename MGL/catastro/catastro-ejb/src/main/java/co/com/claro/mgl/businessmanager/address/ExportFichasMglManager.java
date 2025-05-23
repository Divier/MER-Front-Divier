package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.ExportFichasMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ExportFichasMgl;
import java.math.BigDecimal;
import java.util.List;

public class ExportFichasMglManager {
    
    ExportFichasMglDaoImpl daoImpl = new ExportFichasMglDaoImpl();
    
     public ExportFichasMgl crearRegistroExportFichas(ExportFichasMgl historico) throws ApplicationException {
         return daoImpl.create(historico);
     }
     
     public List<ExportFichasMgl> findListadoHistoricosFicha(ExportFichasMgl filtro , int page , int maxResults) throws ApplicationException{
         int firstResult = 0;
         if (page > 1) {
             firstResult = (maxResults * (page - 1));
         }
         return daoImpl.findListadoHistoricosFicha(filtro, firstResult, maxResults);
     }
     
     public List<ExportFichasMgl> findHistoricoFichaPorIdPreficha(BigDecimal idPreficha) throws ApplicationException{
         return daoImpl.findHistoricoFichaPorIdPreficha(idPreficha);
     }
     
     public int countListadoHistoricosFicha(ExportFichasMgl filtro)throws ApplicationException{
          return daoImpl.countListadoHistoricosFicha(filtro);
     }
    
}
