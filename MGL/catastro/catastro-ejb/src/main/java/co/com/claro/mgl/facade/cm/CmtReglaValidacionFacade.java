package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtReglaValidacionManager;
import co.com.claro.mgl.dtos.CmtReglaValidacionDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtReglaValidacion;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoValidacionMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Facade Regla Validacion. Expone la logica de negocio para el manejo de Reglas
 * de Validacion en el repositorio.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 */
@Stateless
public class CmtReglaValidacionFacade
        extends BaseCmFacade<CmtReglaValidacion>
        implements CmtReglaValidacionFacadeLocal {
    
    @Override
    public List<CmtReglaValidacionDto> findByFiltroPaginadoDto(
            int paginaSelected,
            int maxResults,
            CmtBasicaMgl proyecto)
            throws ApplicationException {
        CmtReglaValidacionManager reglaValidacionManager =
                new CmtReglaValidacionManager();
        return reglaValidacionManager.findByFiltroPaginadoDto(
                paginaSelected, maxResults, proyecto);
    }

    @Override
    public int countByFiltroPaginado(CmtBasicaMgl proyecto)
            throws ApplicationException {
        CmtReglaValidacionManager reglaValidacionManager =
                new CmtReglaValidacionManager();
        return reglaValidacionManager.countByFiltroPaginado(proyecto);
    }

    @Override
    public CmtReglaValidacionDto addValidacionToRegla(CmtReglaValidacion regla,
            CmtTipoValidacionMgl validacionToAdd,
            String valorValidacion) throws ApplicationException {
        CmtReglaValidacionManager reglaValidacionManager =
                new CmtReglaValidacionManager();
        return reglaValidacionManager.addValidacionToRegla(
                regla,
                validacionToAdd,
                valorValidacion,
                super.getUser(),
                super.getPerfil());
    }
    
    @Override
    public CmtReglaValidacionDto addValidacionToReglaDto(
            CmtReglaValidacionDto regla, 
            CmtTipoValidacionMgl validacionToAdd, 
            String valorValidacion) throws ApplicationException {
        CmtReglaValidacionManager reglaValidacionManager =
                new CmtReglaValidacionManager();
        return reglaValidacionManager.addValidacionToReglaDto(
                regla,
                validacionToAdd,
                valorValidacion);
    }
    
    @Override
    public CmtReglaValidacionDto createReglaFromDto(
            CmtReglaValidacionDto reglaDto)
            throws ApplicationException{
        
        CmtReglaValidacionManager reglaValidacionManager =
                new CmtReglaValidacionManager();
        return reglaValidacionManager.createReglaFromDto(
                reglaDto, getUser(), getPerfil());
    }
    
    @Override
    public CmtReglaValidacionDto updateReglaFromDto(
            CmtReglaValidacionDto reglaDtoToUpdate)
            throws ApplicationException {
        CmtReglaValidacionManager reglaValidacionManager =
                new CmtReglaValidacionManager();
        return reglaValidacionManager.updateReglaFromDto(
                reglaDtoToUpdate, getUser(), getPerfil());
    }
    
    @Override
    public boolean deleteRegla(
            CmtReglaValidacionDto reglaDtoToDelete) 
            throws ApplicationException {
        CmtReglaValidacionManager reglaValidacionManager =
                new CmtReglaValidacionManager();
        return reglaValidacionManager.deleteRegla(
                reglaDtoToDelete, getUser(), getPerfil());
    }

    @Override
    public List<CmtReglaValidacionDto> validarTiposValidacionReglas
        (List<CmtReglaValidacionDto> reglas) {
        CmtReglaValidacionManager reglaValidacionManager = new CmtReglaValidacionManager();
        return reglaValidacionManager.validarTiposValidacionReglas(reglas);
    }
    
    @Override
    public Boolean validarEliminacionTipoValidacion(BigDecimal id) throws ApplicationException {
        CmtReglaValidacionManager reglaValidacionManager = new CmtReglaValidacionManager();
        return reglaValidacionManager.validarEliminacionTipoValidacion(id);
    }
}
