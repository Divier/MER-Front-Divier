package co.com.claro.mgl.facade.cm;

import co.com.claro.cmas400.ejb.respons.ResponseManttoEdificioList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoSubEdificiosList;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import com.sun.jersey.api.client.UniformInterfaceException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface CmtSubEdificioMglFacadeLocal extends BaseFacadeLocal<CmtSubEdificioMgl> {

    List<CmtSubEdificioMgl> findSubEdificioByCuentaMatriz(
            CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException;

    CmtSubEdificioMgl findById(BigDecimal id) throws ApplicationException;

    void setUser(String mUser, int mPerfil) throws ApplicationException;

    List<AuditoriaDto> construirAuditoria(
            CmtSubEdificioMgl cmtSubEdificioMgl)
            throws NoSuchMethodException, 
            IllegalAccessException, InvocationTargetException;

    CmtSubEdificioMgl create(CmtSubEdificioMgl t, 
            boolean crearSoloEncabezado) throws ApplicationException;
    
    /**
     * Crea un subedificio en MGL
     *
     * @author Victor Bocanegra
     * @param t CmtSubEdificioMgl
     * @return SubEdificio creado en MGL
     * @throws ApplicationException
     */
    CmtSubEdificioMgl createCm(CmtSubEdificioMgl t)
            throws ApplicationException;

    CmtSubEdificioMgl update(CmtSubEdificioMgl t, boolean actualizarHhpp, boolean cambioNombreSubedificioPropiaDir) throws ApplicationException;

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
    List<CmtSubEdificioMgl> findSubEdifByCmAndEstado(
            CmtCuentaMatrizMgl cuentaMatriz,
            CmtBasicaMgl estado) throws ApplicationException;

    /**
     * Busca los SubEdificios asociados a una CM en el estadorequerido de la OT. 
     * Permite realizar la busqueda de los Subedificios asociados a una Cuenta Matriz 
     * y que se encuentren en estado que acepta el tipo de OT.
     * @author Johnnatan Ortiz
     * @param cmtOrdenTrabajoMgl Cuenta Matriz
     * @return SubEdificios asociados a una Cuenta Matriz que se encuentran en
     * el estado especificado
     * @throws ApplicationException
     */
    List<CmtSubEdificioMgl> findSubEdifByCmSinVt(
            CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl) throws ApplicationException;
    
     boolean updateSubEdificioNumPisos(
            BigDecimal subEdificioId, int numPisos) throws ApplicationException;
    /**
     * Perimte eliminar un subedificio desde la visita tecnica, el cual no tenga 
     * ningun registro relacionado ni yna visita tecnica iniciada.
     * @author Carlos Leonardo villamil.
     * @param cmtSubEdificioMgl
     * @throws ApplicationException
     */
     public void deleteSubEdificioOnProcesVt(CmtSubEdificioMgl cmtSubEdificioMgl) 
             throws ApplicationException;
     
     /**
     * Buscar un sub edificio general de una cuenta matriz especifica
     * 
     * @author rodriguezluim
     * @param cuentaMatriz Condicional para buscar el subedificio
     * @return Un obnbjeto de tipo CmtSubEdificioMgl
     * @throws ApplicationException 
     */
     public CmtSubEdificioMgl findSubEdificioGeneralByCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException;

    /**
     * Borra un subedificio en MGL
     *
     * @author Victor Bocanegra
     * @param t CmtSubEdificioMgl
     * @return 
     * @throws ApplicationException
     */
     public boolean deleteCm(CmtSubEdificioMgl t) 
             throws ApplicationException;
     /**
      * consulta el numero de SubEdificios asociados con la cuenta matriz 
      * parametro de entrada el ID de la cuenta matriz relacionada con el subedificio
      * devuelve el entero de cantidad de registros que existen.
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
      **/
    Long countSubEdificiosCuentaMatriz(BigDecimal id_cuenta_matriz) throws ApplicationException;
    
        /**
     * Busca lista de sub edificios por nodo especifico
     *
     * @author bocanegra vm
     * @param nodo
     * @return List<CmtSubEdificioMgl>
     * @throws ApplicationException
     */
     List<CmtSubEdificioMgl> findSubEdificioByNodo(NodoMgl nodo) throws ApplicationException; 
     
         /**
     * Buscar un sub edificio general de una cuenta matriz especifica
     *
     * @author Bocanegra vm
     * @param cuentaMatriz Condicional para buscar el subedificio
     * @return Un obnbjeto de tipo CmtSubEdificioMgl
     * @throws ApplicationException
     */
     CmtSubEdificioMgl findSubEdificioGeneralByCuentaMatrizEliminado(CmtCuentaMatrizMgl cuentaMatriz)
            throws ApplicationException;
     
     /**
     * Consulta el nombre del subedificio en RR
     *
     * @author Bocanegra vm
     * @param subEdificioMgl que se consulta
     * @return String con el nombre del subedicicio
     * @throws ApplicationException
     * @throws java.io.IOException
     */
      String nombreSubedificioRR(CmtSubEdificioMgl subEdificioMgl)
            throws UniformInterfaceException, IOException, ApplicationException;
      
      
      CmtSubEdificioMgl updateSinRr(CmtSubEdificioMgl cmtSubEdificioMgl, String usuario, int perfil)
            throws ApplicationException; 
      
      /**
     * Consulta del subedificio en RR
     *
     * @author Bocanegra vm
     * @param subEdificioMgl que se consulta
     * @return ResponseManttoSubEdificiosList con el nombre del subedicicio
     * @throws ApplicationException
     * @throws java.io.IOException
     */
    ResponseManttoSubEdificiosList consultaSubedificioRR(CmtSubEdificioMgl subEdificioMgl)
            throws UniformInterfaceException, IOException, ApplicationException;
    
        /**
     * Consulta de unico edificio en RR
     *
     * @author Bocanegra vm
     * @param cuenta que se consulta
     * @return ResponseManttoEdificioList con el nombre del subedicicio
     * @throws ApplicationException
     * @throws java.io.IOException
     */
    ResponseManttoEdificioList consultaUnicoSubedificioRR(CmtCuentaMatrizMgl cuenta)
            throws UniformInterfaceException, IOException, ApplicationException; 
    
     /**
     * Realiza actualizacion de  un subedificio en RR y MGL
     *
     * @author Victor Bocanegra
     * @param t CmtSubEdificioMgl
     * @return SubEdificio actualizado en RR y  MGL
     * @throws ApplicationException
     */
    CmtSubEdificioMgl updateSubedificio(CmtSubEdificioMgl t) throws ApplicationException;
    
    public void updateCompania(CmtSubEdificioMgl t, String usuario, int perfil) throws ApplicationException;
    
}
