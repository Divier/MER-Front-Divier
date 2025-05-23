/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.Multivalor;
import java.math.BigDecimal;
import java.util.List;

/**
 * objetivo de la clase. 
 * Clase de tipo interface bean de session de la tabla multivalor.
 *
 * @author Carlos Leonardo Villamil
 * @versión 1.00.000
 */
public interface MultivalorFacadeLocal extends BaseFacadeLocal<Multivalor> {

    List<Multivalor> loadPoliticasVeto() throws ApplicationException;

    List<Multivalor> loadTipoRed() throws ApplicationException;

    List<Multivalor> loadTipoVeto() throws ApplicationException;

    List<Multivalor> loadSocioEconomico(BigDecimal idSocioE) throws ApplicationException;

    /**
     * Descripción del objetivo del método.Busca un codigo de grupo y valor en
        la tabla multivalor.
     *
     * @author Carlos Leonardo Villamil
     * @param idGmu Grupo multivalor.
     * @param mulValor key de usuario de la tabla .
     * @return Multivalor Descripcion de la tabla multivalor.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    Multivalor findByIdGmuAndmulValor(
            BigDecimal idGmu, String mulValor)
            throws ApplicationException;
}
