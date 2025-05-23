package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mer.utils.StringToolUtils;
import co.com.claro.mer.utils.enums.DelimitadorEnum;
import co.com.claro.mer.utils.enums.ParametrosMerEnum;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.dao.impl.cm.CmtBasicaMglDaoImpl;
import co.com.claro.mgl.dtos.EstadosOtCmDirDto;
import co.com.claro.mgl.dtos.FiltroConjsultaBasicasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaAuditoriaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoSolicitudMgl;
import co.com.claro.mgl.utils.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CmtBasicaMglManager {

    private static final Logger LOGGER = LogManager.getLogger(CmtBasicaMglManager.class);
    private String HABILITAR_RR;

    public CmtBasicaMglManager() {

    }

    /**
     *
     * @return resulList
     * @throws ApplicationException
     */
    public List<CmtBasicaMgl> findAll()
            throws ApplicationException {
        List<CmtBasicaMgl> resulList;
        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        resulList = cmtBasicaMglDaoImpl.findAll();
        return resulList;
    }

    /**
     *
     * @param basica
     * @return
     * @throws ApplicationException
     */
    public CmtBasicaMgl findByAbreviaAndTipoBasica(CmtBasicaMgl basica)
            throws ApplicationException {
        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        return cmtBasicaMglDaoImpl.findByTipoBasicaAndAbreviatura(basica);
    }

    /**
     *
     * @param tipoBasicaId
     * @return
     * @throws ApplicationException
     */
    public List<CmtBasicaMgl> findByTipoBasica(CmtTipoBasicaMgl tipoBasicaId) throws ApplicationException {
        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        return cmtBasicaMglDaoImpl.findByTipoBasica(tipoBasicaId);
    }

    /**
     *
     * @param tipoBasicaId
     * @return
     * @throws ApplicationException
     */
    public List<CmtBasicaMgl> findByTipoBasicaEstadosCombinado(CmtTipoBasicaMgl tipoBasicaId) throws ApplicationException {
        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        return cmtBasicaMglDaoImpl.findByTipoBasicaEstadosCombinados(tipoBasicaId);
    }

    /**
     * Lista los diferentes tipos por el nombre.
     *
     * @author Antonio Gil
     * @param tipoBasicaName
     * @return lista
     * @throws ApplicationException
     */
    public List<CmtBasicaMgl> findTipoBasicaName(String tipoBasicaName)
            throws ApplicationException {
        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        return cmtBasicaMglDaoImpl.findTipoBasicaName(tipoBasicaName);
    }

    public CmtBasicaMgl create(CmtBasicaMgl cmtBasicaMgl, String mUser, int mPerfil)
            throws ApplicationException {

        cmtBasicaMgl.setAbreviatura(
                (cmtBasicaMgl.getTipoBasicaObj().getNombreTipo().equalsIgnoreCase(Constant.TECNOLOGIA))
                ? cmtBasicaMgl.getCodigoBasica()
                : CmtUtilidadesCM.calcularAbreviatura(cmtBasicaMgl.getNombreBasica())
        );

        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        List<CmtBasicaMgl> listTmpCmtBasicaMgl = cmtBasicaMglDaoImpl
                .findByBasicaCode(cmtBasicaMgl);
        if ((listTmpCmtBasicaMgl == null || listTmpCmtBasicaMgl.isEmpty())) {
            CmtTablasBasicasRRMglManager cmtTablasBasicasRRMglManager = new CmtTablasBasicasRRMglManager();
            if (cmtTablasBasicasRRMglManager.actionCreate(cmtBasicaMgl, mUser)) {
                return cmtBasicaMglDaoImpl.createCm(cmtBasicaMgl, mUser, mPerfil);
            } else {
                throw new ApplicationException("No se pudo crear el registro "
                        + cmtBasicaMgl.getDescripcion()
                        + " en RR, de tipo "
                        + cmtBasicaMgl.getTipoBasicaObj().getDescripcionTipo());
            }

        } else {
            throw new ApplicationException("El codigo basica debe ser unico, "
                    + "ya lo contiene el registro "
                    + listTmpCmtBasicaMgl.get(0).getNombreBasica());
        }
    }

    public CmtBasicaMgl update(CmtBasicaMgl cmtBasicaMgl, String mUser, int mPerfil)
            throws ApplicationException {

        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        CmtTablasBasicasRRMglManager cmtTablasBasicasRRMglManager = new CmtTablasBasicasRRMglManager();
        ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
        HABILITAR_RR = parametrosMglManager.findByAcronimo(
                Constant.HABILITAR_RR)
                .iterator().next().getParValor();

        if (HABILITAR_RR != null) {
            if (HABILITAR_RR.equals("1")) {

                if (cmtBasicaMgl.getTipoBasicaObj().getNombreTipo().equalsIgnoreCase(Constant.TECNOLOGIA)) {

                    if (cmtBasicaMgl.getAbreviatura().length() > 3) {
                        throw new ApplicationException("El campo abreviatura debe tener maximo 3 caracteres");
                    }
                }
                if (cmtTablasBasicasRRMglManager.actionUpdate(cmtBasicaMgl, mUser)) {
                    return cmtBasicaMglDaoImpl.updateCm(cmtBasicaMgl, mUser, mPerfil);
                } else {
                    throw new ApplicationException("No se pudo actualizar el registro "
                            + cmtBasicaMgl.getNombreBasica()
                            + " en RR, de tipo "
                            + cmtBasicaMgl.getTipoBasicaObj().getDescripcionTipo());
                }
            } else {
                return cmtBasicaMglDaoImpl.updateCm(cmtBasicaMgl, mUser, mPerfil);
            }
        } else {
            throw new ApplicationException("No se encuentra configurado el parámetro HABILITAR_RR.");
        }
    }

    public boolean delete(CmtBasicaMgl cmtBasicaMgl, String mUser, int mPerfil)
            throws ApplicationException {
        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();

        CmtTablasBasicasRRMglManager cmtTablasBasicasRRMglManager = new CmtTablasBasicasRRMglManager();
        if (cmtTablasBasicasRRMglManager.actionDelete(cmtBasicaMgl, mUser)) {
            return cmtBasicaMglDaoImpl.deleteCm(cmtBasicaMgl, mUser, mPerfil);

        } else {
            throw new ApplicationException("No se pudo eliminar el registro"
                    + cmtBasicaMgl.getDescripcion()
                    + " en RR, de tipo"
                    + cmtBasicaMgl.getTipoBasicaObj().getDescripcionTipo());
        }

    }

    /**
     *
     * @param cmtBasicaMgl
     * @return
     * @throws ApplicationException
     */
    public CmtBasicaMgl findById(CmtBasicaMgl cmtBasicaMgl)
            throws ApplicationException {
        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        return cmtBasicaMglDaoImpl.find(cmtBasicaMgl.getBasicaId());
    }

    /**
     *
     * @param id
     * @return
     * @throws ApplicationException
     */
    public CmtBasicaMgl findById(BigDecimal id)
            throws ApplicationException {
        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        return cmtBasicaMglDaoImpl.find(id);
    }

    public List<AuditoriaDto> construirAuditoria(CmtBasicaMgl cmtBasicaMgl)
            throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException {
        UtilsCMAuditoria<CmtBasicaMgl, CmtBasicaAuditoriaMgl> utilsCMAuditoria = new UtilsCMAuditoria<>();
        List<AuditoriaDto> listAuditoriaDto = utilsCMAuditoria.construirAuditoria(cmtBasicaMgl);
        return listAuditoriaDto;

    }

    public List<CmtBasicaMgl> findByFiltro(FiltroConjsultaBasicasDto consulta)
            throws ApplicationException {
        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        return cmtBasicaMglDaoImpl.findByFiltro(consulta);
    }

    public String buscarUltimoCodigoNumerico(CmtTipoBasicaMgl tipoBasicaId)
            throws ApplicationException {
        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        return cmtBasicaMglDaoImpl.buscarUltimoCodigoNumerico(tipoBasicaId);

    }

    public CmtBasicaMgl findByNombre(BigDecimal tipoBasica, String nombre) {
        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        return cmtBasicaMglDaoImpl.findByNombre(tipoBasica, nombre);
    }

    public CmtBasicaMgl findByTipoBasicaAndCodigo(BigDecimal tipoBasica,
            String codigoBasica) {
        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        return cmtBasicaMglDaoImpl.findByTipoBasicaAndCodigo(tipoBasica,
                codigoBasica);
    }

    public CmtBasicaMgl findEstratoByLevelGeo(String estratoGeo) throws ApplicationException {
        CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
        String varFindEstrato = "";
        if (estratoGeo == null || estratoGeo.isEmpty()) {
            varFindEstrato = "NG";
        }
        if ((estratoGeo != null && !estratoGeo.isEmpty())
                && estratoGeo.equalsIgnoreCase("0")) {
            varFindEstrato = "NR";
        }
        if ((estratoGeo != null && !estratoGeo.isEmpty())
                && !estratoGeo.equalsIgnoreCase("0")) {
            varFindEstrato = estratoGeo.trim();
        }
        CmtTipoBasicaMgl tipoBasicaMgl = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                co.com.claro.mgl.utils.Constant.TIPO_BASICA_ESTRATO);
        return findByTipoBasicaAndCodigo(tipoBasicaMgl.getTipoBasicaId(),
                varFindEstrato);
    }

    /**
     * Busca si el nombre se encuentra en un grupo de basicas. Permite realizar
     * la busqueda de una basica dentro de un grupo por medio del nombre.
     *
     * @author Johnnatan Ortiz
     * @param nombreBasica Nombre de la basica a buscar
     * @param grupoBasicaList Grupo de basicas para la busqueda
     * @return CmtBasicaMgl Basica encontrada en el grupo
     * @throws ApplicationException
     */
    public CmtBasicaMgl determinarBasicaEnGrupo(String nombreBasica,
            List<CmtBasicaMgl> grupoBasicaList) throws ApplicationException {
        try {
            CmtBasicaMgl result = null;
            for (CmtBasicaMgl b : grupoBasicaList) {
                if (nombreBasica.trim().equals(b.getNombreBasica().trim())) {
                    result = b;
                    break;
                }
            }
            return result;
        } catch (Exception e) {
            LOGGER.error("Error en determinarBasicaEnGrupo. " + e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Buscar la instancia de basica. Se busca una instancia de basica teniendo
     * en cuenta el nombre de la basica y el nombre de tipo de basica.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param codigoBasica nombre de la basica que sa va a buscar.
     * @param identificadorInternoApp nombre del tipo de basica con la que se
     * relaciona la basica.
     * @return una instancia de basica si la encuentra o null en caso contrario.
     * @throws ApplicationException Lanza la excepcion cuando ocurra un error en
     * a ejecucion de la sentencia.
     */
    public CmtBasicaMgl findIdBasica(String codigoBasica, String identificadorInternoApp)
            throws ApplicationException {
        CmtBasicaMglDaoImpl mglDaoImpl = new CmtBasicaMglDaoImpl();
        return mglDaoImpl.findBasica(codigoBasica, identificadorInternoApp);
    }

    /**
     * Buscar la instancia de basica. Se busca una instancia de basica teniendo
     * en cuenta el nombre de la basica y el nombre de tipo de basica.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param nombreBasica nombre de la basica que sa va a buscar.
     * @param nombreTipoBasica nombre del tipo de basica con la que se relaciona
     * la basica.
     * @return una instancia de basica si la encuentra o null en caso contrario.
     * @throws ApplicationException Lanza la excepcion cuando ocurra un error en
     * a ejecucion de la sentencia.
     */
    public CmtBasicaMgl findIdBasica1(String nombreBasica, String nombreTipoBasica)
            throws ApplicationException {
        CmtBasicaMglDaoImpl mglDaoImpl = new CmtBasicaMglDaoImpl();
        return mglDaoImpl.findBasica1(nombreBasica, nombreTipoBasica);
    }

    /**
     * Buscar una basica.Busca una basica por nombre tipo basica y por su
     * codigo.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param codigoBasica Codigo a buscar en tipo basica.
     * @param nombreTipoBasica Nombre a buscar en tipo basica.
     * @return Los datos de la entidad basica.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public CmtBasicaMgl findIdBasicaCodigo(String codigoBasica,
            String nombreTipoBasica)
            throws ApplicationException {
        CmtBasicaMglDaoImpl mglDaoImpl = new CmtBasicaMglDaoImpl();
        return mglDaoImpl.findBasicaCodigo(codigoBasica, nombreTipoBasica);
    }

    /**
     * Busca aquellos registros que este relacionados con el Tipo de Bácica por
     * medio del Id.
     *
     * @author Juan David Hernández
     * @param idTipoProyectoGeneral Id básica a buscar
     * @return List<CmtBasicaMgl> Básica encontrada
     * @throws ApplicationException
     */
    public List<CmtBasicaMgl> findGrupoProyectoBasicaList(BigDecimal idTipoProyectoGeneral)
            throws ApplicationException {
        try {
            CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
            return cmtBasicaMglDaoImpl.findGrupoProyectoBasicaList(idTipoProyectoGeneral);

        } catch (Exception e) {
            LOGGER.error("Error en findGrupoProyectoBasicaList. " + e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Busca aquellos registros que este relacionados con el Tipo de Bácica por
     * medio del Id.
     *
     * @author Juan David Hernández
     * @param idTipoGrupoProyecto Id básica a buscar
     * @return List<CmtBasicaMgl> Básica encontrada
     * @throws ApplicationException
     */
    public List<CmtBasicaMgl> findProyectoBasicaList(BigDecimal idTipoGrupoProyecto)
            throws ApplicationException {
        try {
            CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
            return cmtBasicaMglDaoImpl.findProyectoBasicaList(idTipoGrupoProyecto);
        } catch (ApplicationException e) {
            LOGGER.error("Error en findProyectoBasicaList. " + e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Función que se utiliza para obtener la abreviatura del tipo de solicitud
     *
     * @author Juan David Hernandez
     * @param tipoSolicitud
     * @return String abreviatura del tipo de solicitud
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public String findAbreviaturaTipoTecnologia(String tipoSolicitud)
            throws ApplicationException {
        try {
            CmtTipoBasicaMgl cmtTipoBasicaMgl;
            CmtBasicaMglManager cmtBasicaMglMglManager = new CmtBasicaMglManager();
            CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
            cmtTipoBasicaMgl = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TIPO_SOLICITUD);
            // Obtiene valores de tipo de tecnología
            List<CmtBasicaMgl> tipoSolicitudBasicaList = cmtBasicaMglMglManager.findByTipoBasica(cmtTipoBasicaMgl);
            if (tipoSolicitudBasicaList != null
                    && !tipoSolicitudBasicaList.isEmpty()) {
                for (CmtBasicaMgl cmtBasicaMgl : tipoSolicitudBasicaList) {

                    if (tipoSolicitud.equalsIgnoreCase(cmtBasicaMgl.getCodigoBasica())) {
                        return cmtBasicaMgl.getAbreviatura();
                    }
                }
            }
            return null;
        } catch (ApplicationException e) {
            LOGGER.error("Error en findAbreviaturaTipoTecnologia. " + e.getMessage());
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Función que obtiene el valor del tipo de tecnologia por abreviatura
     *
     * @author Juan David Hernandez
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public CmtBasicaMgl findValorTipoSolicitud(String tipoTecnologiaAbreviatura)
            throws ApplicationException {
        try {

            CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
            CmtTipoBasicaMgl cmtTipoBasicaMgl;
            cmtTipoBasicaMgl = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TECNOLOGIA);

            CmtBasicaMglManager cmtBasicaMglMglManager = new CmtBasicaMglManager();
            // Obtiene valores de tipo de tecnología
            List<CmtBasicaMgl> tipoTecnologiaBasicaList = cmtBasicaMglMglManager.findByTipoBasica(cmtTipoBasicaMgl);
            if (tipoTecnologiaBasicaList != null
                    && !tipoTecnologiaBasicaList.isEmpty()) {
                for (CmtBasicaMgl cmtBasicaMgl : tipoTecnologiaBasicaList) {
                    if (tipoTecnologiaAbreviatura
                            .equalsIgnoreCase(cmtBasicaMgl.getAbreviatura())) {
                        return cmtBasicaMgl;
                    }
                }
            }
            return null;
        } catch (ApplicationException e) {
            LOGGER.error("Error en findValorTipoSolicitud. " + e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Busca el codigo basica por identificador interno de la app
     *
     * @author Lenis Cardenas
     * @param codigoInternoApp codigo interno de la aplicacion
     * @return CmtBasicaMgl basica encontrado
     * @throws ApplicationException
     */
    public CmtBasicaMgl findByCodigoInternoApp(String codigoInternoApp)
            throws ApplicationException {
        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        return cmtBasicaMglDaoImpl.findByCodigoInternoApp(codigoInternoApp);
    }

    /**
     * valbuenayf
     * metodo para listar CmtBasicaMgl por identificador interno de la app
     *
     * @param codigoInternoApp
     * @return
     * @throws ApplicationException
     */
    public List<CmtBasicaMgl> findListCmtBasicaMglByCodigoInternoApp(String codigoInternoApp)
            throws ApplicationException {
        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        return cmtBasicaMglDaoImpl.findListCmtBasicaMglByCodigoInternoApp(codigoInternoApp);
    }

    /**
     * Autor: Victor Bocanegar
     * metodo para obtener el valor extendido de una tipo basica estado hhpp
     *
     * @param basicaMgl
     * @return String
     * @throws ApplicationException
     */
    public String findValorExtendidoEstHhpp(CmtBasicaMgl basicaMgl) {
        String valorExt = "";
        if (basicaMgl != null && basicaMgl.getTipoBasicaObj() != null
                && basicaMgl.getTipoBasicaObj().getListCmtTipoBasicaExtMgl() != null
                && !basicaMgl.getTipoBasicaObj().getListCmtTipoBasicaExtMgl().isEmpty()) {

            for (CmtTipoBasicaExtMgl cmtTipoBasicaExtMgl : basicaMgl.getTipoBasicaObj().getListCmtTipoBasicaExtMgl()) {
                if (cmtTipoBasicaExtMgl != null) {
                    if (cmtTipoBasicaExtMgl.getCampoEntidadAs400()
                            .equals(co.com.claro.mgl.utils.Constant.EQUIVALENTE_RR)) {
                        if (cmtTipoBasicaExtMgl.getListCmtBasicaExtMgl() != null) {
                            for (CmtBasicaExtMgl cmtBasicaExtMgl : cmtTipoBasicaExtMgl.getListCmtBasicaExtMgl()) {
                                if (basicaMgl == cmtBasicaExtMgl.getIdBasicaObj()) {
                                    valorExt = cmtBasicaExtMgl.getValorExtendido();
                                }
                            }
                        }
                    }
                }
            }
        }
        return valorExt;
    }

    public String findCodigoOrExtEstHhpp(CmtTipoBasicaMgl cmtTipoBasicaMgl, String letra, boolean control) {
        String respuesta = "";
        if (cmtTipoBasicaMgl != null && !cmtTipoBasicaMgl.getListCmtTipoBasicaExtMgl().isEmpty()) {
            for (CmtTipoBasicaExtMgl cmtTipoBasicaExtMgl : cmtTipoBasicaMgl.getListCmtTipoBasicaExtMgl()) {
                if (cmtTipoBasicaExtMgl.getCampoEntidadAs400().equals(co.com.claro.mgl.utils.Constant.EQUIVALENTE_RR)) {
                    for (CmtBasicaExtMgl cmtBasicaExtMgl : cmtTipoBasicaExtMgl.getListCmtBasicaExtMgl()) {
                        if (cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase(letra)) {
                            respuesta = (control) ? cmtBasicaExtMgl.getIdBasicaObj().getCodigoBasica()
                                    : cmtBasicaExtMgl.getValorExtendido();
                        }
                    }
                }
            }
        }
        return respuesta;
    }

    public List<CmtBasicaMgl> findByNombreBasica(String nombreBasica) throws ApplicationException {
        List<CmtBasicaMgl> resultList;
        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        resultList = cmtBasicaMglDaoImpl.findByNombreBasica(nombreBasica);
        return resultList;
    }

    /**
     * Busca Basica que contenga el prefijo en el nombre.
     *
     * @author Juan David Hernandez
     * @param prefijo que debe contener el registro
     * @param tipoBasicaid
     * @return List de CmtBasicaMgl encontrados
     * @throws ApplicationException
     */
    public List<CmtBasicaMgl> findByPreFijo(String prefijo, BigDecimal tipoBasicaid)
            throws ApplicationException {
        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        return cmtBasicaMglDaoImpl.findByPreFijo(prefijo, tipoBasicaid);
    }

    /**
     * Busca Basica por el tipo de basica y el codigo de la basica
     *
     * @author Jonathan Peña
     * @param codigo
     * @param tipoBasicaid
     * @return CmtBasicaMgl encontrada
     * @throws ApplicationException
     */
    public CmtBasicaMgl findByBasicaCode(String codigo, BigDecimal tipoBasicaid)
            throws ApplicationException {
        CmtBasicaMgl basica = new CmtBasicaMgl();
        basica.setCodigoBasica(codigo);
        CmtTipoBasicaMgl tipo = new CmtTipoBasicaMgl();
        tipo.setTipoBasicaId(tipoBasicaid);
        basica.setTipoBasicaObj(tipo);
        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        List<CmtBasicaMgl> lista = cmtBasicaMglDaoImpl.findByBasicaCode(basica);
        if (lista != null && !lista.isEmpty()) {
            basica = lista.get(0);
        }
        return basica;
    }

    public List<CmtBasicaMgl> findEstadoResultadoOTRR(List<String> tipoBasicaExt, CmtTipoBasicaMgl tipoBasica)
            throws ApplicationException {
        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        return cmtBasicaMglDaoImpl.findEstadoResultadoOTRR(tipoBasicaExt, tipoBasica);
    }

    /**
     *
     * @param tipoSolicitudListByRol
     * @param tipoBasicaId
     * @return
     * @throws ApplicationException
     */
    public List<CmtBasicaMgl> findByCodigoBasicaList(List<CmtTipoSolicitudMgl> tipoSolicitudListByRol,
            CmtTipoBasicaMgl tipoBasicaId)
            throws ApplicationException {
        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        List<String> codigoBasicaList = new ArrayList<>();
        // se obtiene los codigos basica del tipo de solicitud (cambio dir 0,1,2,3)
        if (tipoSolicitudListByRol != null && !tipoSolicitudListByRol.isEmpty()) {
            tipoSolicitudListByRol.stream()
                    .filter((cmtTipoSolicitudMgl) -> (cmtTipoSolicitudMgl.getTipoSolicitudBasicaId() != null))
                    .forEachOrdered((cmtTipoSolicitudMgl) -> {
                        codigoBasicaList.add(cmtTipoSolicitudMgl.getTipoSolicitudBasicaId().getCodigoBasica());
                    });
        }
        // retorna solo los tipo de solicitud de la basica que su rol le permitió
        return cmtBasicaMglDaoImpl.findByCodigoBasicaList(codigoBasicaList, tipoBasicaId);
    }

    /**
     *
     * @return resulList
     * @throws ApplicationException
     */
    public List<CmtBasicaMgl> findByAllFields(HashMap<String, Object> paramsValidacionGuardar)
            throws ApplicationException {
        List<CmtBasicaMgl> resulList;
        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        resulList = cmtBasicaMglDaoImpl.findByAllFields(paramsValidacionGuardar);
        return resulList;
    }

    /**
     * cardenaslb
     * metodo para listar CmtBasicaMgl por tecnologia
     *
     * @param basicaTecnologia
     * @return
     * @throws ApplicationException
     */
    public List<CmtBasicaMgl> findByTipoBasicaTecno(CmtBasicaMgl basicaTecnologia)
            throws ApplicationException {
        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        return cmtBasicaMglDaoImpl.findByTipoBasicaTecno(basicaTecnologia);
    }

    public List<CmtBasicaMgl> findTecnolosgias()
            throws ApplicationException {
        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        return cmtBasicaMglDaoImpl.findTecnolosgias();
    }

    /**
     *
     * @param tipoBasicaId
     * @return
     * @throws ApplicationException
     */
    public List<CmtBasicaMgl> findByTipoBasica(BigDecimal tipoBasicaId)
            throws ApplicationException {
        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        return cmtBasicaMglDaoImpl.findByTipoBasica(tipoBasicaId);
    }

    public List<EstadosOtCmDirDto> findListEstadosCmDir(CmtTipoBasicaMgl tipoBasicaTipoOtCmId,
            CmtTipoBasicaMgl tipoBasicaTipoOtDirId)
            throws ApplicationException {
        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        return cmtBasicaMglDaoImpl.findListEstadosCmDir(tipoBasicaTipoOtCmId, tipoBasicaTipoOtDirId);
    }

    public List<CmtBasicaMgl> findBasicaListByIdentificadorInternoApp(String identificadorInternoApp)
            throws ApplicationException {
        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        return cmtBasicaMglDaoImpl.findBasicaListByIdentificadorInternoApp(identificadorInternoApp);
    }

    public CmtBasicaMgl findByTipoBasicaAndNombre(CmtTipoBasicaMgl tipoBasicaMgl,
            String nombre) throws ApplicationException {

        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        return cmtBasicaMglDaoImpl.findByTipoBasicaAndNombre(tipoBasicaMgl, nombre);
    }

    /**
     * Busca lista de Basicas por tipo basica y abreviatura
     *
     * @author Victor Bocanegra
     * @param tipoBasica
     * @param abreviatura
     * @return List de CmtBasicaMgl encontrados
     * @throws ApplicationException
     */
    public List<CmtBasicaMgl> findByTipoBAndAbreviatura(BigDecimal tipoBasica, String abreviatura)
            throws ApplicationException {
        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        return cmtBasicaMglDaoImpl.findByTipoBAndAbreviatura(tipoBasica, abreviatura);
    }

    /**
     * Busca los códigos de tecnologías parametrizados por los usuarios.
     *
     * @return {@link List<String>} Retorna la lista de códigos de tecnología
     * parametrizados
     * @throws ApplicationException Excepción de la App
     * @author Gildardo Mora
     */
    public List<String> findCodigosTecnologiasPorValidarEnSolicitud() throws ApplicationException {
        try {
            String codigosTecnologias = ParametrosMerUtil.findValor(
                    ParametrosMerEnum.TECNOLOGIAS_BLOQUEO_SOLICITUD_HHPP.getAcronimo());
            return StringToolUtils.convertStringToList(codigosTecnologias, DelimitadorEnum.PIPE, true);

        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, e);
        }
    }

    /**
     * Busca Basica por codigo de basica e identificadorInternoApp
     *
     * @author Miguel Barrios Hitss
     * @param identificadorInternoApp
     * @param codigoBasica
     * @return CmtBasicaMgl encontrada
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public CmtBasicaMgl findByCodigoInternoAppAndCodigo(String identificadorInternoApp, String codigoBasica) throws ApplicationException {
        try {
            CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
            return cmtBasicaMglDaoImpl.findByCodigoInternoAppAndCodigo(identificadorInternoApp, codigoBasica);

        } catch (Exception e) {
            LOGGER.error("Error en findByCodigoInternoAppAndCodigo. " + e.getMessage(), e);
            throw new ApplicationException(e.getMessage(), e);
        }

    }
}
