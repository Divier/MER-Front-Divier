/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.MultivalorDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.GrupoMultivalor;
import co.com.claro.mgl.jpa.entities.Multivalor;
import java.math.BigDecimal;
import java.util.List;

/**
 * Manager Multivalor. Clase que maneja la logica de negocio tabla multivalor.
 *
 * @author Carlos Leonardo Villamil
 * @versión 1.00.000
 */
public class MultivalorManager {

    private static final String POLITICAS_VETO = "POLITICAS_VETO";
    private static final String TIPO_RED = "TIPO_RED";
    private static final String TIPO_VETO = "TIPO_VETO";

    public List<Multivalor> loadPoliticasVeto() throws ApplicationException {

        MultivalorDaoImpl multivalorDaoImpl = new MultivalorDaoImpl();

        GrupoMultivalor grupoMultivalor = new GrupoMultivalor();
        List<Multivalor> multivalorList;

        GrupoMultivalorManager grupoMultivalorManager = new GrupoMultivalorManager();
        grupoMultivalor = grupoMultivalorManager.fingByGmuNombre(POLITICAS_VETO);

        multivalorList = multivalorDaoImpl.findByIdGmu(grupoMultivalor.getGmuId());

        return multivalorList;
    }

    public List<Multivalor> loadTipoRed() throws ApplicationException {

        MultivalorDaoImpl multivalorDaoImpl = new MultivalorDaoImpl();

        GrupoMultivalor grupoMultivalor = new GrupoMultivalor();
        List<Multivalor> multivalorList;

        GrupoMultivalorManager grupoMultivalorManager = new GrupoMultivalorManager();
        grupoMultivalor = grupoMultivalorManager.fingByGmuNombre(TIPO_RED);

        multivalorList = multivalorDaoImpl.findByIdGmu(grupoMultivalor.getGmuId());

        return multivalorList;
    }

    public List<Multivalor> loadTipoVeto() throws ApplicationException {

        MultivalorDaoImpl multivalorDaoImpl = new MultivalorDaoImpl();

        List<Multivalor> multivalorList;

        GrupoMultivalorManager grupoMultivalorManager = new GrupoMultivalorManager();
        GrupoMultivalor grupoMultivalor = grupoMultivalorManager.fingByGmuNombre(TIPO_VETO);

        multivalorList = multivalorDaoImpl.findByIdGmu(grupoMultivalor.getGmuId());

        return multivalorList;
    }

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
    public Multivalor findByIdGmuAndmulValor(BigDecimal idGmu, String mulValor)
            throws ApplicationException {
        MultivalorDaoImpl multivalorDaoImpl = new MultivalorDaoImpl();
        return multivalorDaoImpl.findByIdGmuAndmulValor(idGmu, mulValor);
    }

    public List<Multivalor> loadSocioEconomico(BigDecimal idSocioE) throws ApplicationException {
        MultivalorDaoImpl multivalorDaoImpl = new MultivalorDaoImpl();
        List<Multivalor> multivalorList;
        multivalorList = multivalorDaoImpl.findByIdGmu(idSocioE);
        return multivalorList;
    }
}
