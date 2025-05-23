package co.com.claro.mgl.facade.cm;

/**
 *
 * @author Admin
 */
import co.com.claro.cmas400.ejb.respons.ResponseManttoEdificioList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoSubEdificiosList;
import co.com.claro.mgl.businessmanager.cm.CmtSubEdificioMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.utils.ClassUtils;
import com.sun.jersey.api.client.UniformInterfaceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class CmtSubEdificioMglFacade implements CmtSubEdificioMglFacadeLocal {
    
    private static final Logger LOGGER = LogManager.getLogger(CmtSubEdificioMglFacade.class);
    
    private String user = "";
    private int perfil = 0;

    @Override
    public List<CmtSubEdificioMgl> findAll() throws ApplicationException {
        CmtSubEdificioMglManager cmtSubEdificioMglMglManager = new CmtSubEdificioMglManager();
        return cmtSubEdificioMglMglManager.findAll();
    }

    @Override
    public List<CmtSubEdificioMgl> findSubEdificioByCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException {
        CmtSubEdificioMglManager cmtSubEdificioMglMglManager = new CmtSubEdificioMglManager();
        return cmtSubEdificioMglMglManager.findSubEdificioByCuentaMatriz(cuentaMatriz);
    }

    @Override
    public CmtSubEdificioMgl create(CmtSubEdificioMgl t, boolean crearSoloEncabezado) throws ApplicationException {
        CmtSubEdificioMglManager cmtSubEdificioMglMglManager = new CmtSubEdificioMglManager();
        return cmtSubEdificioMglMglManager.create(t, user, perfil, crearSoloEncabezado);
    }
    
    /**
     * Crea un subedificio en MGL
     *
     * @author Victor Bocanegra
     * @param t CmtSubEdificioMgl
     * @return SubEdificio creado en MGL
     * @throws ApplicationException
     */
    @Override
    public CmtSubEdificioMgl createCm(CmtSubEdificioMgl t) throws ApplicationException {
        CmtSubEdificioMglManager cmtSubEdificioMglMglManager = new CmtSubEdificioMglManager();
        return cmtSubEdificioMglMglManager.create(t, user, perfil);
    }

    @Override
    public CmtSubEdificioMgl update(CmtSubEdificioMgl t, boolean actualizarHhpp, boolean cambioNombreSubedificioPropiaDir) throws ApplicationException {
        CmtSubEdificioMglManager cmtSubEdificioMglMglManager = new CmtSubEdificioMglManager();
        try {
            return cmtSubEdificioMglMglManager.update(t, user, perfil, actualizarHhpp, 
                    cambioNombreSubedificioPropiaDir );
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
        return null;
    }

    @Override
    public boolean delete(CmtSubEdificioMgl t) throws ApplicationException {
        CmtSubEdificioMglManager cmtSubEdificioMglMglManager = new CmtSubEdificioMglManager();
        return cmtSubEdificioMglMglManager.delete(t, user, perfil);
    }
    
     /**
     * Borrado logico de  un subedificio en MGL
     *
     * @author Victor Bocanegra
     * @param t CmtSubEdificioMgl
     * @return 
     * @throws ApplicationException
     */
    @Override
    public boolean deleteCm(CmtSubEdificioMgl t) throws ApplicationException {
        CmtSubEdificioMglManager cmtSubEdificioMglMglManager = new CmtSubEdificioMglManager();
        return cmtSubEdificioMglMglManager.deleteCm(t, user, perfil);
    }


    @Override
    public CmtSubEdificioMgl findById(BigDecimal id) throws ApplicationException {
        CmtSubEdificioMglManager cmtSubEdificioMglMglManager = new CmtSubEdificioMglManager();
        return cmtSubEdificioMglMglManager.findById(id);
    }

    @Override
    public CmtSubEdificioMgl findById(CmtSubEdificioMgl sqlData) throws ApplicationException {
        CmtSubEdificioMglManager cmtSubEdificioMglMglManager = new CmtSubEdificioMglManager();
        return cmtSubEdificioMglMglManager.findById(sqlData);
    }

    @Override
    public List<AuditoriaDto> construirAuditoria(CmtSubEdificioMgl cmtSubEdificioMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (cmtSubEdificioMgl != null) {
            CmtSubEdificioMglManager cmtOrdenTrabajoMglManager = new CmtSubEdificioMglManager();
            return cmtOrdenTrabajoMglManager.construirAuditoria(cmtSubEdificioMgl);
        } else {
            return null;

        }
    }

    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser == null || mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil Nopueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }

    @Override
    public CmtSubEdificioMgl create(CmtSubEdificioMgl t) throws ApplicationException {
       CmtSubEdificioMglManager cmtSubEdificioMglMglManager = new CmtSubEdificioMglManager();
        return cmtSubEdificioMglMglManager.create(t);
    }

    /**
     * Busca los SubEdificios asociados a una CM y en un estado especifico.
     * Permite realizar la busqueda de los Subedificios asociados a una Cuenta
     * Matriz y que se encuentren en un estado especifico en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @param cuentaMatriz Cuenta Matriz
     * @param estado Estado de los SubEdificios
     * @return SubEdificios asociados a una Cuenta Matriz que se encuentran en
     * el estado especificado
     * @throws ApplicationException
     */
    @Override
    public List<CmtSubEdificioMgl> findSubEdifByCmAndEstado(
            CmtCuentaMatrizMgl cuentaMatriz,
            CmtBasicaMgl estado) throws ApplicationException {
        CmtSubEdificioMglManager manager = new CmtSubEdificioMglManager();
        return manager.findSubEdifByCmAndEstado(cuentaMatriz, estado);
    }

    /**
     * Busca los SubEdificios asociados a una CM en el estadorequerido de la OT.
     * Permite realizar la busqueda de los Subedificios asociados a una Cuenta
     * Matriz y que se encuentren en estado que acepta el tipo de OT.
     *
     * @author Johnnatan Ortiz
     * @param cmtOrdenTrabajoMgl Cuenta Matriz
     * @return SubEdificios asociados a una Cuenta Matriz que se encuentran en
     * el estado especificado
     * @throws ApplicationException
     */
    @Override
    public List<CmtSubEdificioMgl> findSubEdifByCmSinVt(
            CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl) throws ApplicationException {
        CmtSubEdificioMglManager manager = new CmtSubEdificioMglManager();
        return manager.findSubEdifByCmSinVt(cmtOrdenTrabajoMgl);
    }

    @Override
    public boolean updateSubEdificioNumPisos(
            BigDecimal cmtSubEdificioMgl, int numPisos) throws ApplicationException {
        CmtSubEdificioMglManager manager = new CmtSubEdificioMglManager();
        return manager.updateSubEdificioNumPisos(cmtSubEdificioMgl, numPisos);
    }

    /**
     * Perimte eliminar un subedificio desde la visita tecnica, el cual no tenga
     * ningun registro relacionado ni yna visita tecnica iniciada.
     *
     * @author Carlos Leonardo villamil.
     * @param cmtSubEdificioMgl
     * @throws ApplicationException
     */
    @Override
    public void deleteSubEdificioOnProcesVt(CmtSubEdificioMgl cmtSubEdificioMgl)
            throws ApplicationException {
        CmtSubEdificioMglManager manager = new CmtSubEdificioMglManager();
        manager.deleteSubEdificioOnProcesVt(cmtSubEdificioMgl, user, perfil);
    }

    /**
     * Buscar un sub edificio general de una cuenta matriz especifica
     *
     * @author rodriguezluim
     * @param cuentaMatriz Condicional para buscar el subedificio
     * @return Un obnbjeto de tipo CmtSubEdificioMgl
     * @throws ApplicationException
     */
    @Override
    public CmtSubEdificioMgl findSubEdificioGeneralByCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException {
        CmtSubEdificioMglManager manager = new CmtSubEdificioMglManager();
        return manager.findSubEdificioGeneralByCuentaMatriz(cuentaMatriz);
    }

    @Override
    public Long countSubEdificiosCuentaMatriz(BigDecimal id_cuenta_matriz) throws ApplicationException{
        CmtSubEdificioMglManager manager = new CmtSubEdificioMglManager();
        return manager.countSubEdificiosCuentaMatriz(id_cuenta_matriz);
    }
    
    /**
     * Busca lista de sub edificios por nodo especifico
     *
     * @author bocanegra vm
     * @param nodo
     * @return List<CmtSubEdificioMgl>
     * @throws ApplicationException
     */
    @Override
    public List<CmtSubEdificioMgl> findSubEdificioByNodo(NodoMgl nodo) throws ApplicationException {

        CmtSubEdificioMglManager manager = new CmtSubEdificioMglManager();
        return manager.findSubEdificioByNodo(nodo);
    }
    
        
    /**
     * Buscar un sub edificio general de una cuenta matriz especifica
     *
     * @author Bocanegra vm
     * @param cuentaMatriz Condicional para buscar el subedificio
     * @return Un obnbjeto de tipo CmtSubEdificioMgl
     * @throws ApplicationException
     */
    @Override
    public CmtSubEdificioMgl findSubEdificioGeneralByCuentaMatrizEliminado(CmtCuentaMatrizMgl cuentaMatriz)
            throws ApplicationException {

        CmtSubEdificioMglManager manager = new CmtSubEdificioMglManager();
        return manager.findSubEdificioGeneralByCuentaMatrizEliminado(cuentaMatriz);
    }
    
    /**
     * Consulta el nombre del subedificio en RR
     *
     * @author Bocanegra vm
     * @param subEdificioMgl que se consulta
     * @return String con el nombre del subedicicio
     * @throws ApplicationException
     * @throws java.io.IOException
     */
    @Override
    public String nombreSubedificioRR(CmtSubEdificioMgl subEdificioMgl)
            throws UniformInterfaceException, IOException, ApplicationException {
        
        CmtSubEdificioMglManager manager = new CmtSubEdificioMglManager();
        return manager.nombreSubedificioRR(subEdificioMgl);
    }
    
    @Override
    public CmtSubEdificioMgl updateSinRr(CmtSubEdificioMgl cmtSubEdificioMgl, String usuario, int perfil)
            throws ApplicationException {

        CmtSubEdificioMglManager manager = new CmtSubEdificioMglManager();
        return manager.updateSinRr(cmtSubEdificioMgl, usuario, perfil);

    }

    @Override
    public CmtSubEdificioMgl update(CmtSubEdificioMgl t) throws ApplicationException {
        CmtSubEdificioMglManager cmtSubEdificioMglMglManager = new CmtSubEdificioMglManager();
        try {
            return cmtSubEdificioMglMglManager.update(t, user, perfil,false,false);
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
        return null;
    }
    
    /**
     * Consulta del subedificio en RR
     *
     * @author Bocanegra vm
     * @param subEdificioMgl que se consulta
     * @return ResponseManttoSubEdificiosList con el nombre del subedicicio
     * @throws ApplicationException
     * @throws java.io.IOException
     */
    @Override
    public ResponseManttoSubEdificiosList consultaSubedificioRR(CmtSubEdificioMgl subEdificioMgl)
            throws UniformInterfaceException, IOException, ApplicationException {

        CmtSubEdificioMglManager manager = new CmtSubEdificioMglManager();
        return manager.consultaSubedificioRR(subEdificioMgl);

    }
  
    /**
     * Consulta de unico edificio en RR
     *
     * @author Bocanegra vm
     * @param cuenta que se consulta
     * @return ResponseManttoEdificioList con el nombre del subedicicio
     * @throws ApplicationException
     * @throws java.io.IOException
     */
    @Override
    public ResponseManttoEdificioList consultaUnicoSubedificioRR(CmtCuentaMatrizMgl cuenta)
            throws UniformInterfaceException, IOException, ApplicationException {

        CmtSubEdificioMglManager manager = new CmtSubEdificioMglManager();
        return manager.consultaUnicoSubedificioRR(cuenta);
    }
    
    /**
     * Realiza actualizacion de un subedificio en RR y MGL
     *
     * @author Victor Bocanegra
     * @param t CmtSubEdificioMgl
     * @return SubEdificio actualizado en RR y MGL
     * @throws ApplicationException
     */
    @Override
    public CmtSubEdificioMgl updateSubedificio(CmtSubEdificioMgl t) throws ApplicationException {
        CmtSubEdificioMglManager cmtSubEdificioMglMglManager = new CmtSubEdificioMglManager();
        try {
            return cmtSubEdificioMglMglManager.update(t, user, perfil,false,false);
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
           throw new ApplicationException(msg);
        }
    }
    
       @Override
    public void updateCompania(CmtSubEdificioMgl t, String usuario, int perfil) throws ApplicationException {
        CmtSubEdificioMglManager cmtSubEdificioMglMglManager = new CmtSubEdificioMglManager();
        try {
            cmtSubEdificioMglMglManager.updateCompania(t, usuario, perfil);
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
           throw new ApplicationException(msg);
        }
    }
}
