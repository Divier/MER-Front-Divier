package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.businessmanager.address.DrDireccionManager;
import co.com.claro.mgl.dao.impl.cm.CmtSubEdificiosVtDaoImp;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.error.ApplicationExceptionCM;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificiosVt;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author alejandro.martinez.ext@claro.com.co
 */
public class CmtSubEdificiosVtManager {
    
    CmtSubEdificiosVtDaoImp dao = new CmtSubEdificiosVtDaoImp();
    CmtSubEdificiosVtDaoImp daoSubEdificio = new CmtSubEdificiosVtDaoImp();
    
    private static final Logger LOGGER = LogManager.getLogger(CmtSubEdificiosVtManager.class);
    
    public CmtSubEdificiosVt createCm(CmtSubEdificiosVt subEdificioVt,
            String usuario, int perfil) throws ApplicationException {
            subEdificioVt.setNombreSubEdificio(
                    createNombreubEdificioVt(subEdificioVt));
        CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();
        CmtBasicaMgl estadoSubEdi = basicaMglManager.findByCodigoInternoApp(
                Constant.BASICA_ESTADO_SIN_VISITA_TECNICA);
        CmtSubEdificioMgl subEd = subEdificioVt.mapearCamposCmtSubEdificioMgl(
                subEdificioVt, estadoSubEdi);
        CmtSubEdificioMglManager subEdificioManager = new CmtSubEdificioMglManager();
        
        String nombreFormatoRR = subEdificioManager.obtenerNombreSubEdEstandarRr(subEd);
        if (nombreFormatoRR != null && !nombreFormatoRR.trim().isEmpty()) {
            subEdificioVt.setNombramientoRr(nombreFormatoRR);
        }

        return dao.createCm(subEdificioVt, usuario, perfil);
    }
    
    public CmtSubEdificiosVt update(CmtSubEdificiosVt cmtSubEdificiosVt,
            String usuario, int perfil) throws ApplicationException {
        cmtSubEdificiosVt.setNombreSubEdificio(
                createNombreubEdificioVt(cmtSubEdificiosVt));
        CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();
        CmtBasicaMgl estadoSubEdi = basicaMglManager.findByCodigoInternoApp(
                Constant.BASICA_ESTADO_SIN_VISITA_TECNICA);
        CmtSubEdificioMgl subEd = cmtSubEdificiosVt.mapearCamposCmtSubEdificioMgl(
                cmtSubEdificiosVt, estadoSubEdi);
        CmtSubEdificioMglManager subEdificioManager = new CmtSubEdificioMglManager();
        
        String nombreFormatoRR = subEdificioManager.obtenerNombreSubEdEstandarRr(subEd);
        if (nombreFormatoRR != null && !nombreFormatoRR.trim().isEmpty()) {
             cmtSubEdificiosVt.setNombramientoRr(nombreFormatoRR);
        }
            return dao.updateCm(cmtSubEdificiosVt, usuario, perfil);
    }
    
    public String createNombreubEdificioVt(CmtSubEdificiosVt cmtSubEdificiosVt)
            throws ApplicationException {
        StringBuilder nombreEd = new StringBuilder();
        if (cmtSubEdificiosVt.getTipoNivel1() != null
                && cmtSubEdificiosVt.getTipoNivel1().getNombreBasica() != null
                && !cmtSubEdificiosVt.getTipoNivel1().getNombreBasica().trim().isEmpty()
                && cmtSubEdificiosVt.getValorNivel1() != null
                && !cmtSubEdificiosVt.getValorNivel1().trim().isEmpty()) {
                if (cmtSubEdificiosVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral() != null && 
                        cmtSubEdificiosVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral().getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
                        && cmtSubEdificiosVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral().getEstadoSubEdificioObj().getIdentificadorInternoApp().
                        equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                    nombreEd.append(" ");
                    nombreEd.append(cmtSubEdificiosVt.getTipoNivel1().getNombreBasica().trim());
                    nombreEd.append(" ");
                    nombreEd.append(cmtSubEdificiosVt.getValorNivel1().trim());
            } else {            
                    nombreEd.append(cmtSubEdificiosVt.getValorNivel1().trim());                
            }


        }
        if (cmtSubEdificiosVt.getTipoNivel2() != null
                && cmtSubEdificiosVt.getTipoNivel2().getNombreBasica() != null
                && !cmtSubEdificiosVt.getTipoNivel2().getNombreBasica().trim().isEmpty()
                && cmtSubEdificiosVt.getValorNivel2() != null
                && !cmtSubEdificiosVt.getValorNivel2().trim().isEmpty()) {
            if (cmtSubEdificiosVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral() != null && 
                    cmtSubEdificiosVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral().getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
                    && cmtSubEdificiosVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral().getEstadoSubEdificioObj().getIdentificadorInternoApp().
                    equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                nombreEd.append(" ");
                nombreEd.append(cmtSubEdificiosVt.getTipoNivel2().getNombreBasica().trim());
                nombreEd.append(" ");
                nombreEd.append(cmtSubEdificiosVt.getValorNivel2().trim());
            } else {               
                    nombreEd.append(cmtSubEdificiosVt.getValorNivel2().trim());                
            }
           
        }
        if (cmtSubEdificiosVt.getTipoNivel3() != null
                && cmtSubEdificiosVt.getTipoNivel3().getNombreBasica() != null
                && !cmtSubEdificiosVt.getTipoNivel3().getNombreBasica().trim().isEmpty()
                && cmtSubEdificiosVt.getValorNivel3() != null
                && !cmtSubEdificiosVt.getValorNivel3().trim().isEmpty()) {
            if (cmtSubEdificiosVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral() != null && 
                    cmtSubEdificiosVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral().getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
                    && cmtSubEdificiosVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral().getEstadoSubEdificioObj().getIdentificadorInternoApp().
                    equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                nombreEd.append(" ");
                nombreEd.append(cmtSubEdificiosVt.getTipoNivel3().getNombreBasica().trim());
                nombreEd.append(" ");
                nombreEd.append(cmtSubEdificiosVt.getValorNivel3().trim());
            } else {                
                    nombreEd.append(cmtSubEdificiosVt.getValorNivel3().trim());                
            }
          
        }
        if (cmtSubEdificiosVt.getTipoNivel4() != null
                && cmtSubEdificiosVt.getTipoNivel4().getNombreBasica() != null
                && !cmtSubEdificiosVt.getTipoNivel4().getNombreBasica().trim().isEmpty()
                && cmtSubEdificiosVt.getValorNivel4() != null
                && !cmtSubEdificiosVt.getValorNivel4().trim().isEmpty()) {
            if (cmtSubEdificiosVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral() != null && 
                    cmtSubEdificiosVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral().getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
                    && cmtSubEdificiosVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral().getEstadoSubEdificioObj().getIdentificadorInternoApp().
                    equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                nombreEd.append(" ");
                nombreEd.append(cmtSubEdificiosVt.getTipoNivel4().getNombreBasica().trim());
                nombreEd.append(" ");
                nombreEd.append(cmtSubEdificiosVt.getValorNivel4().trim());
            } else {              
                    nombreEd.append(cmtSubEdificiosVt.getValorNivel4().trim());                
            }
           
        }
        if (cmtSubEdificiosVt.getTipoNivel5() != null
                && cmtSubEdificiosVt.getTipoNivel5().getNombreBasica() != null
                && !cmtSubEdificiosVt.getTipoNivel5().getNombreBasica().trim().isEmpty()
                && cmtSubEdificiosVt.getValorNivel5() != null
                && !cmtSubEdificiosVt.getValorNivel5().trim().isEmpty()) {
            if (cmtSubEdificiosVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral() != null && 
                    cmtSubEdificiosVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral().getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
                    && cmtSubEdificiosVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral().getEstadoSubEdificioObj().getIdentificadorInternoApp().
                    equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                nombreEd.append(" ");
                nombreEd.append(cmtSubEdificiosVt.getTipoNivel5().getNombreBasica().trim());
                nombreEd.append(" ");
                nombreEd.append(cmtSubEdificiosVt.getValorNivel5().trim());
            } else {               
                    nombreEd.append(cmtSubEdificiosVt.getValorNivel5().trim());  
            }
           
        }
        return nombreEd.toString().trim();
    }
    
    public boolean delete(CmtSubEdificiosVt cmtSubEdificiosVt, String usuario, int perfil) throws ApplicationException {
        return dao.deleteCm(cmtSubEdificiosVt, usuario, perfil);
    }
    
    public List<CmtSubEdificiosVt> findByVt(CmtVisitaTecnicaMgl vtObj) throws ApplicationException {
        return dao.findByVt(vtObj);
    }
    
    public CmtSubEdificiosVt findById(BigDecimal idSubEdificiosVt) throws ApplicationException {
        return dao.find(idSubEdificiosVt);
    }
    
    public CmtSubEdificiosVt findByIdSubEdificio(BigDecimal idSubEdificio) throws ApplicationException {
        return dao.findByIdSubEdificio(idSubEdificio);
    }

    /**
     * Cuenta los Sub Edificio creados en una visita tecnica. Permite realizar
     * el conteo de las Compentencias de un mismo SubEdificio.
     *
     * @author alejandro.martine.ext@claro.com.co
     * @param vtObj VisitaTecnica
     * @return numero de Sub Edificios asociadas a una VisitaTecnica
     * @throws ApplicationException
     */
    public int getCountByVt(CmtVisitaTecnicaMgl vtObj) throws ApplicationException {
        CmtSubEdificiosVtDaoImp daoImpl = new CmtSubEdificiosVtDaoImp();
        return daoImpl.getCountByVt(vtObj);
    }

    /**
     * Busca los Sub Edificio creados en una VisitaTecnica. Permite realizar la
     * busqueda de los Sub Edificio de un mismo VisitaTecnica.
     *
     * @author alejandro.martine.ext@claro.com.co
     * @param paginaSelected pagina de la busqueda
     * @param maxResults maximo numero de resultados
     * @param vtObj
     * @return Compentencias asociadas a un SubEdifico
     * @throws ApplicationException
     */
    public List<CmtSubEdificiosVt> findByVtPaginado(int paginaSelected,
            int maxResults, CmtVisitaTecnicaMgl vtObj) throws ApplicationException {
        CmtSubEdificiosVtDaoImp daoImpl = new CmtSubEdificiosVtDaoImp();
        int firstResult = 0;
        if (paginaSelected > 1) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        return daoImpl.findByVtPaginado(firstResult, maxResults, vtObj);
    }

    /**
     * Crea un SubEdificioVt a partir de un SubEdificio.Permite realizar la
 creacion de un SubEdificiVT a partir de un Subedificios.
     *
     * @author Johnnatan Ortiz
     * @param subEdificio SubEdificio a adicionar a la visita tecnica
     * @param visitaTecnica visita tecnica
     * @param usuario
     * @param nodSub nodo del subedificio TecnoSub
     * @param perfil
     * @return SubEdificioVt Creado
     * @throws ApplicationException
     */
    public CmtSubEdificiosVt createSubEdVtfromSubEd(CmtSubEdificioMgl subEdificio,
            CmtVisitaTecnicaMgl visitaTecnica, String usuario, int perfil, NodoMgl nodSub) throws ApplicationException, ApplicationExceptionCM {
        try {
            CmtSubEdificioMglManager subEdificioManager = new CmtSubEdificioMglManager();
            CmtSubEdificioMgl subEdificioMglProcess = subEdificioManager.findById(subEdificio);
            CmtDireccionMglManager direccionManager = new CmtDireccionMglManager();
            CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();
            CmtDireccionMgl cmtdireccionMgl = null;
            for (CmtDireccionMgl d : subEdificioMglProcess.getCmtCuentaMatrizMglObj().getDireccionesList()) {
                if (d.getEstadoRegistro() == 1 && d.getTdiId() == 2) {
                    cmtdireccionMgl = d;
                    break;
                }
            }
            if (cmtdireccionMgl != null) {                
                
                String nombreSubEdi = (!direccionManager.esNombreSubEdificioEstandar(subEdificio.getNombreSubedificio())) ? "" : subEdificio.getNombreSubedificio(); 
                DireccionRRManager direccionRRManager = direccionManager.estandarizarNombreSubEdificioDireccion(cmtdireccionMgl, nombreSubEdi);
                DetalleDireccionEntity detalleDireccionEntity = direccionRRManager.getDireccionEntity();
                CmtSubEdificiosVt subEdificioVt = new CmtSubEdificiosVt();
                subEdificioVt.mapearCamposFromDetalleDireccionEntity(detalleDireccionEntity);
                subEdificioVt.setSubEdificioObj(subEdificio);
                //se cambia esta linea por el nodo de la tecnologia del subedificio
                subEdificioVt.setNodo(nodSub);
                subEdificioVt.setVtObj(visitaTecnica);

           
                //Asignamos los valores de basicas a los niveles 1-4
                subEdificioVt = asignarNivelesSubEdificioVt(subEdificioVt);
                if (subEdificioVt.getSubEdificioObj() != null) {
                    if (subEdificioVt.getSubEdificioObj().getCmtCuentaMatrizMglObj().isUnicoSubEdificioBoolean()) {
                        CmtBasicaMgl basicaMglTorre = basicaMglManager.findByCodigoInternoApp(Constant.BASICA_TORRE);
                        subEdificioVt.setTipoNivel1(basicaMglTorre);
                        subEdificioVt.setValorNivel1(subEdificioVt.getSubEdificioObj().getNombreSubedificio());
                    }
                }
                if (subEdificio.getCmtCuentaMatrizMglObj().getDireccionesList()!= null && subEdificio.getCmtCuentaMatrizMglObj().getDireccionesList().size() > 0) {
                    for (CmtDireccionMgl direccionMgl : subEdificio.getCmtCuentaMatrizMglObj().getDireccionesList()) {
                        if (direccionMgl.getEstadoRegistro() == 1 && direccionMgl.getTdiId() == Constant.ID_TIPO_DIRECCION_PRINCIPAL) {
                            DrDireccionManager ddm = new DrDireccionManager();
                            DrDireccion direccion = new DrDireccion();
                            direccion.obtenerFromCmtDireccionMgl(direccionMgl);
                            DetalleDireccionEntity direccionEntity;
                            direccionEntity = direccion.convertToDetalleDireccionEntity();
                            direccionEntity.setMultiOrigen(subEdificio.getCmtCuentaMatrizMglObj().getMunicipio().getGpoMultiorigen());
                            direccionEntity.setIdDirCatastro(direccionMgl.getDireccionObj().getDirId().toString());
                            direccionEntity.setBarrio(subEdificio.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getBarrio());
                            DireccionRRManager drrm = new DireccionRRManager(direccionEntity);
                            ddm.getDireccion(direccion);
                            String textAddress = ddm.getPricipalDireccion();
                            subEdificioVt.mapearCamposDrDireccion(direccion);
                            subEdificioVt.setCalleRr(drrm.getDireccion().getCalle());
                            subEdificioVt.setUnidadRr(drrm.getDireccion().getNumeroUnidad());
                            break;
                        }
                    }
                }
                return createCm(subEdificioVt, usuario, perfil);
            }
            return null;
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
        
    }
    
    private CmtSubEdificiosVt asignarNivelesSubEdificioVt(
            CmtSubEdificiosVt subEdificioVt) throws ApplicationException {
        try {
            CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
            CmtTipoBasicaMgl cmtTipoBasicaMgl;
            cmtTipoBasicaMgl = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TIPO_SUBEDIFICIO);
            CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();
            List<CmtBasicaMgl> tipoSubEdificioList = basicaMglManager.findByTipoBasica(cmtTipoBasicaMgl);
            
            if (subEdificioVt.getCpTipoNivel1() != null
                    && !subEdificioVt.getCpTipoNivel1().trim().isEmpty()) {
                CmtBasicaMgl tipo = basicaMglManager.determinarBasicaEnGrupo(
                        subEdificioVt.getCpTipoNivel1(), tipoSubEdificioList);
                if (tipo != null && tipo.getBasicaId() != null) {
                    subEdificioVt.setTipoNivel1(tipo);
                }
                if (subEdificioVt.getCpValorNivel1() != null
                        && !subEdificioVt.getCpValorNivel1().trim().isEmpty()) {
                    subEdificioVt.setValorNivel1(subEdificioVt.getCpValorNivel1());
                }
            }
            
            if (subEdificioVt.getCpTipoNivel2() != null
                    && !subEdificioVt.getCpTipoNivel2().trim().isEmpty()) {
                CmtBasicaMgl tipo = basicaMglManager.determinarBasicaEnGrupo(
                        subEdificioVt.getCpTipoNivel2(), tipoSubEdificioList);
                if (tipo != null && tipo.getBasicaId() != null) {
                    subEdificioVt.setTipoNivel2(tipo);
                }
                if (subEdificioVt.getCpValorNivel2() != null
                        && !subEdificioVt.getCpValorNivel2().trim().isEmpty()) {
                    subEdificioVt.setValorNivel2(subEdificioVt.getCpValorNivel2());
                }
            }
            
            if (subEdificioVt.getCpTipoNivel3() != null
                    && !subEdificioVt.getCpTipoNivel3().trim().isEmpty()) {
                CmtBasicaMgl tipo = basicaMglManager.determinarBasicaEnGrupo(
                        subEdificioVt.getCpTipoNivel3(), tipoSubEdificioList);
                if (tipo != null && tipo.getBasicaId() != null) {
                    subEdificioVt.setTipoNivel3(tipo);
                }
                if (subEdificioVt.getCpValorNivel3() != null
                        && !subEdificioVt.getCpValorNivel3().trim().isEmpty()) {
                    subEdificioVt.setValorNivel3(subEdificioVt.getCpValorNivel3());
                }
            }
            
            if (subEdificioVt.getCpTipoNivel4() != null
                    && !subEdificioVt.getCpTipoNivel4().trim().isEmpty()) {
                CmtBasicaMgl tipo = basicaMglManager.determinarBasicaEnGrupo(
                        subEdificioVt.getCpTipoNivel4(), tipoSubEdificioList);
                if (tipo != null && tipo.getBasicaId() != null) {
                    subEdificioVt.setTipoNivel4(tipo);
                }
                if (subEdificioVt.getCpValorNivel4() != null
                        && !subEdificioVt.getCpValorNivel4().trim().isEmpty()) {
                    subEdificioVt.setValorNivel4(subEdificioVt.getCpValorNivel4());
                }
            }
            return subEdificioVt;
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
     /**
     * Busca los Sub Edificio creados en una VisitaTecnica que no tienen orden
     * de acometida
     *
     * @author Victor Bocanegra
     * @param vtObj VisitaTecnica
     * @return List<CmtSubEdificiosVt>
     * @throws ApplicationException
     */
    public List<CmtSubEdificiosVt> findByVtAndAcometida(CmtVisitaTecnicaMgl vtObj) throws ApplicationException {

        return dao.findByVtAndAcometida(vtObj);

    }
    
    /**
     * Busca los Sub Edificio creados en una VisitaTecnica que tienen orden de
     * acometida
     *
     * @author Victor Bocanegra
     * @param idOt orden de acometida
     * @return List<CmtSubEdificiosVt>
     * @throws ApplicationException
     */
    public List<CmtSubEdificiosVt> findByIdOtAcometida(BigDecimal idOt) throws ApplicationException {

        return dao.findByIdOtAcometida(idOt);

    }
}
