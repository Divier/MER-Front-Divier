package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtTipoBasicaMglManager;
import co.com.claro.mgl.dtos.FiltroConsultaTipoBasicaDto;
import co.com.claro.mgl.dtos.FiltroTablaTipoBasicaDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class CmtTipoBasicaMglFacade implements CmtTipoBasicaMglFacadeLocal {

    private String user = "";
    private int perfil = 0;

    @Override
    public List<CmtTipoBasicaMgl> findAll() throws ApplicationException {
        CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
        return cmtTipoBasicaMglManager.findAll();
    }

    @Override
    public CmtTipoBasicaMgl create(CmtTipoBasicaMgl t) throws ApplicationException {
        CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
        return cmtTipoBasicaMglManager.create(t);
    }

    @Override
    public CmtTipoBasicaMgl update(CmtTipoBasicaMgl t) throws ApplicationException {
        CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
        return cmtTipoBasicaMglManager.update(t, user, perfil);
    }

    @Override
    public boolean delete(CmtTipoBasicaMgl t) throws ApplicationException {
        CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
        return cmtTipoBasicaMglManager.delete(t);
    }

    @Override
    public CmtTipoBasicaMgl findById(CmtTipoBasicaMgl idTipoBasica) throws ApplicationException {
        CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
        return cmtTipoBasicaMglManager.findById(idTipoBasica);
    }


    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil Nopueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }

    @Override
    public FiltroConsultaTipoBasicaDto findTablasTipoBasica(FiltroTablaTipoBasicaDto filtro,
            boolean contar, int firstResult, int maxResults) throws ApplicationException {
        CmtTipoBasicaMglManager cmtCuentMatrizMglManager = new CmtTipoBasicaMglManager();
        return cmtCuentMatrizMglManager.getTablasTipoBasicaSearch(filtro, contar, firstResult, maxResults);
    }

    @Override
    public List<AuditoriaDto> construirAuditoria(CmtTipoBasicaMgl cmtTipoBasicaMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (cmtTipoBasicaMgl != null) {
            CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
            return cmtTipoBasicaMglManager.construirAuditoria(cmtTipoBasicaMgl);
        } else {
            return null;
        }
    }

    /**
     * {@inheritDoc }
     *
     * @return {@link List}&lt;{@link CmtTipoBasicaMgl}> listado de tipos
     * encontrados
     * @throws ApplicationException Excepci&oacute;n lanzada en la consulta
     */
    @Override
    public List<CmtTipoBasicaMgl> encontrarTiposEliminacionCM() throws ApplicationException {
        CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
        return cmtTipoBasicaMglManager.encontrarTiposEliminacionCM();
    }

    /**
     * Busca lista de CmtTipoBasicaMgl en el repositorio
     *
     * @author Victor Bocanegra
     * @param complemento
     * @return List<CmtTipoBasicaMgl> encontrada en el repositorio
     * @throws ApplicationException
     *
     *
     */
    @Override
    public List<CmtTipoBasicaMgl> findByComplemento(String complemento) throws ApplicationException {

        CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
        return cmtTipoBasicaMglManager.findByComplemento(complemento);

    }

    /**
     * Busca toda la lista de CmtTipoBasicaMgl en el repositorio sin las que
     * tiene informacion adicional
     *
     * @author Victor Bocanegra
     * @param complemento
     * @return List<CmtTipoBasicaMgl> encontrada en el repositorio
     * @throws ApplicationException
     *
     *
     */
    @Override
    public List<CmtTipoBasicaMgl> findAllSinInfoAdi(String complemento) throws ApplicationException {

        CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
        return cmtTipoBasicaMglManager.findAllSinInfoAdi(complemento);
    }

    @Override
    public CmtTipoBasicaMgl findByNombreTipoBasica(String tipoBasica)
            throws ApplicationException {
        CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
        return cmtTipoBasicaMglManager.findNombreTipoBasica(tipoBasica);
    }

    /**
     * Busca el tipo basica por identificador interno de la app
     *
     * @author Carlos Villamil Hitss
     * @param codigoInternoApp codigo interno de la aplicacion
     * @return CmtTipoBasicaMgl tipo basica encontrado
     * @throws ApplicationException
     */
    @Override
    public CmtTipoBasicaMgl findByCodigoInternoApp(String codigoInternoApp)
            throws ApplicationException {
        CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
        return cmtTipoBasicaMglManager.findByCodigoInternoApp(codigoInternoApp);
    }

     @Override
    public CmtTipoBasicaMgl findByTipoBasicaId(BigDecimal id) throws ApplicationException {
        CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
        return cmtTipoBasicaMglManager.findByTipoBasicaId(id);
    }
     
    /**
     * Busca el maximo codigo
     *
     * @author Victor Bocanegra
     * @return String
     * @throws ApplicationException
     */
    @Override
    public String selectMaxCodigo() throws ApplicationException {
        CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
        return cmtTipoBasicaMglManager.selectMaxCodigo();
    }

    @Override
    public List<CmtTipoBasicaMgl> findByAllFields(HashMap<String, Object> parametros) throws ApplicationException {
        CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
        return cmtTipoBasicaMglManager.findByAllFields(parametros);
    }
}
