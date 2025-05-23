package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.ExportFichasMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ExportFichasMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;


@Stateless
public class ExportFichasMglFacade implements ExportFichasMglFacadeLocal{

    @Override
    public ExportFichasMgl crearRegistroExportFichas(ExportFichasMgl historico) throws ApplicationException {
        ExportFichasMglManager exportFichasMglManager = new ExportFichasMglManager();
        return exportFichasMglManager.crearRegistroExportFichas(historico);
    }

    @Override
    public List<ExportFichasMgl> findListadoHistoricosFicha(ExportFichasMgl filtro , int page , int maxResults)throws ApplicationException {
       ExportFichasMglManager exportFichasMglManager = new ExportFichasMglManager();
       return exportFichasMglManager.findListadoHistoricosFicha(filtro , page , maxResults);
    }
    
    @Override
    public List<ExportFichasMgl> findHistoricoFichaPorIdPreficha(BigDecimal idPreficha) throws ApplicationException{
       ExportFichasMglManager exportFichasMglManager = new ExportFichasMglManager();
       return exportFichasMglManager.findHistoricoFichaPorIdPreficha(idPreficha);
    }
    
    @Override
    public int countListadoHistoricosFicha(ExportFichasMgl filtro)throws ApplicationException{
        ExportFichasMglManager exportFichasMglManager = new ExportFichasMglManager();
        return exportFichasMglManager.countListadoHistoricosFicha(filtro);
    }
}
