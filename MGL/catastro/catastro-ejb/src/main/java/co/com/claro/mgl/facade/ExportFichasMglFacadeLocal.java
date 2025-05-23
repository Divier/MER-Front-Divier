package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ExportFichasMgl;
import java.math.BigDecimal;
import java.util.List;

public interface ExportFichasMglFacadeLocal {
    
    public ExportFichasMgl crearRegistroExportFichas(ExportFichasMgl historico) throws ApplicationException;
    public List<ExportFichasMgl> findListadoHistoricosFicha(ExportFichasMgl filtro , int page , int filasPagina)throws ApplicationException;
    public List<ExportFichasMgl> findHistoricoFichaPorIdPreficha(BigDecimal idPreficha) throws ApplicationException;
    public int countListadoHistoricosFicha(ExportFichasMgl filtro)throws ApplicationException;
}
