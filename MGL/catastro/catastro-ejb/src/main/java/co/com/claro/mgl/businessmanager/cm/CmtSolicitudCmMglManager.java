package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.businessmanager.address.*;
import co.com.claro.mgl.businessmanager.ptlus.UsuariosServicesManager;
import co.com.claro.mgl.dao.impl.cm.CmtCompaniaMglDaoImpl;
import co.com.claro.mgl.dao.impl.cm.CmtSolicitudCmMglDaoImpl;
import co.com.claro.mgl.dtos.CmtParamentrosComplejosDto;
import co.com.claro.mgl.dtos.CmtReporteDetalladoDto;
import co.com.claro.mgl.dtos.FiltroReporteSolicitudCMDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.*;
import co.com.claro.mgl.jpa.entities.cm.*;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.CmtUtilidadesCM;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.MailSender;
import co.com.claro.visitasTecnicas.business.ConsultaEspecificaManager;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.claro.visitasTecnicas.entities.HhppResponseRR;
import co.com.telmex.catastro.data.AddressGeodata;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.data.SubDireccion;
import co.com.telmex.catastro.services.georeferencia.AddressEJBRemote;
import co.com.telmex.catastro.services.seguridad.AuditoriaEJB;
import co.com.telmex.catastro.services.util.DireccionUtil;
import co.com.telmex.catastro.utilws.ResponseMessage;
import com.jlcg.db.AccessData;
import com.jlcg.db.exept.ExceptionDB;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 *
 * @author Admin
 */
public class CmtSolicitudCmMglManager {

    private static final Logger LOGGER = LogManager.getLogger(CmtSolicitudCmMglManager.class);
    private static final String PERMITE_CREA_NFI = "PERMITE_CREA_NFI";
    private static final String PERMITE_CREA_NOD_OFF = "PERMITE_CREA_NOD_OFF";
    private static final String BLACK_LIST_DFAULT = "BLACK_LIST_DFAULT";
    private static final String BLACK_LIST_POR_ORIGEN = "BLACK_LIST_POR_ORIGEN";

    /**
     * Cuenta las Solicitudes asociadas a una CM. Permite realizar el conteo de
     * las Solicitudes asociadas a una Cuenta Matriz en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @param cuentaMatriz ID de la Cuenta Matriz
     * @return Solicitudes encontradas en el repositorio asociadas a una Cuenta
     *         Matriz
     * @throws ApplicationException
     */
    public int getCountByCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException {
        CmtSolicitudCmMglDaoImpl daoImpl = new CmtSolicitudCmMglDaoImpl();
        return daoImpl.getCountByCuentaMatriz(cuentaMatriz);
    }

    /**
     * Busca las Solicitudes asociadas a una CM.Permite realizar la busqueda de
     * las Solicitudes asociadas a una Cuenta Matriz en el repositorio
     * realizando paginacion de los resultados.
     *
     * @author Johnnatan Ortiz
     * @param cuentaMatriz ID de la Cuenta Matriz
     * @return Solicitudes encontradas en el repositorio asociadas a una Cuenta
     *         Matriz
     * @throws ApplicationException
     */
    public List<CmtSolicitudCmMgl> findByCuentaMatrizPaginado(
            CmtCuentaMatrizMgl cuentaMatriz)
            throws ApplicationException {
        CmtSolicitudCmMglDaoImpl daoImpl = new CmtSolicitudCmMglDaoImpl();
        return daoImpl.findByCuentaMatrizPaginado(cuentaMatriz);
    }

    public List<CmtSolicitudCmMgl> findAll() throws ApplicationException {
        List<CmtSolicitudCmMgl> resulList;
        CmtSolicitudCmMglDaoImpl CmtSolicitudCmMglDaoImpl = new CmtSolicitudCmMglDaoImpl();
        resulList = CmtSolicitudCmMglDaoImpl.findAll();
        return resulList;
    }

    public int validaLongitudDireccion(CmtSolicitudCmMgl sol, CmtDireccionSolicitudMgl cdsm)
            throws ApplicationException {
        int TotalLongitudConTorres = 0;
        if (sol.getTipoSolicitudObj().getTipoSolicitudId().compareTo(Constant.TIPO_SOLICITUD_CREACION_CM) == 0) {
            if (cdsm != null) {
                if (sol.getOrigenSolicitud().getIdentificadorInternoApp().equals(
                        Constant.BASICA_ORIGEN_DE_SOLICTUD_CONSTRUCTORA)) {
                    if (sol.getCantidadTorres() != null && sol.getCantidadTorres().compareTo(BigDecimal.ZERO) != 0) {
                        TotalLongitudConTorres = cdsm.getCalleRr().length() + " Torre 123".length();
                    }
                }
            }
        }
        return TotalLongitudConTorres;
    }

    public CmtSolicitudCmMgl create(CmtSolicitudCmMgl sol, String usuario, int perfil) throws ApplicationException {
        CmtSolicitudCmMglDaoImpl CmtSolicitudCmMglDaoImpl = new CmtSolicitudCmMglDaoImpl();
        CmtSolicitudCmMgl solicitud = CmtSolicitudCmMglDaoImpl.createCm(sol, usuario, perfil);
        try {
            enviarCorreoCreacion(sol);
        } catch (ApplicationException e) {
            LOGGER.error("Error al momento de enviar el correo: " + e.getMessage(), e);
        }
        return solicitud;
    }

    public CmtSolicitudCmMgl update(CmtSolicitudCmMgl solicitudCm, String usuario, int perfil)
            throws ApplicationException {
        CmtSolicitudCmMglDaoImpl CmtSolicitudCmMglDaoImpl = new CmtSolicitudCmMglDaoImpl();
        CmtNotasMgl cmtNotasMglCmCrea = new CmtNotasMgl();

        boolean habilitarRR = false;
        ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
        ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);
        if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
            habilitarRR = true;
        }

        solicitudCm = CmtSolicitudCmMglDaoImpl.updateCm(solicitudCm, usuario, perfil);

        if (solicitudCm.getCuentaMatrizObj() != null
                && solicitudCm.getCuentaMatrizObj().getSubEdificioGeneral() != null) {
            cmtNotasMglCmCrea.setSubEdificioObj(solicitudCm.getCuentaMatrizObj().getSubEdificioGeneral());
            cmtNotasMglCmCrea.setDescripcion("CORREO ASESOR");
            if (solicitudCm.getCorreoAsesor() != null && !solicitudCm.getCorreoAsesor().isEmpty()) {
                cmtNotasMglCmCrea.setNota("CORREO: " + solicitudCm.getCorreoAsesor());
            }
            cmtNotasMglCmCrea.setFechaCreacion(new Date());

            if (solicitudCm.getCuentaMatrizObj().getNumeroCuenta().compareTo(Constant.NUMERO_CM_MGL) != 0
                    && habilitarRR) {
                CmtCuentaMatrizRRMglManager cmtCuentaMatrizRRMglManager = new CmtCuentaMatrizRRMglManager();
                cmtCuentaMatrizRRMglManager.notasCuentaMatrizAdd(cmtNotasMglCmCrea, usuario);
            }

        }
        return solicitudCm;
    }

    public void validarCreacionCM(CmtSolicitudCmMgl solicitudCm, NodoMgl nodoMgl)
            throws ApplicationException {
        ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
        List<CmtParamentrosComplejosDto> complejosDtosList;
        boolean isValido = false;
        if (nodoMgl.getNodCodigo().toUpperCase().contains("NFI")) {
            complejosDtosList = parametrosMglManager.findComplejo(PERMITE_CREA_NFI);
            if (complejosDtosList != null) {
                String valueTipoOrigen = solicitudCm.getOrigenSolicitud().getAbreviatura();
                for (CmtParamentrosComplejosDto val : complejosDtosList) {
                    if (val.getValue().equalsIgnoreCase(valueTipoOrigen)) {
                        isValido = true;
                    }
                }
                if (!isValido) {
                    throw new ApplicationException("No se puede crear la cuenta "
                            + " matriz de Origen solicitud " + solicitudCm.getOrigenSolicitud().getNombreBasica()
                            + " sobre un nodo NFI;");
                }
            } else {
                throw new ApplicationException("No se encontro la configuracion "
                        + "para validar creacion  sobre NFI(PERMITE_CREA_NFI);");
            }
        }
        CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();
        CmtBasicaMgl inactivo = basicaMglManager
                .findByCodigoInternoApp(Constant.BASICA_TIPO_ESTADO_NODO_NO_CERTIFICADO);
        if (nodoMgl.getEstado().getBasicaId() == inactivo.getBasicaId()) {
            if (!nodoMgl.getNodCodigo().toUpperCase().contains("NFI")) {
                complejosDtosList = parametrosMglManager.findComplejo(PERMITE_CREA_NOD_OFF);
                isValido = false;
                if (complejosDtosList != null) {
                    String valueTipoOrigen = solicitudCm.getOrigenSolicitud().getAbreviatura();
                    for (CmtParamentrosComplejosDto val : complejosDtosList) {
                        if (val.getValue().equalsIgnoreCase(valueTipoOrigen)) {
                            isValido = true;
                        }
                    }
                    if (!isValido) {
                        throw new ApplicationException("No se puede crear la "
                                + "cuenta matriz de Origen solicitud "
                                + solicitudCm.getOrigenSolicitud().getNombreBasica()
                                + " sobre un nodo no certificado;");
                    }
                } else {
                    throw new ApplicationException("No se encontro la "
                            + "configuracion para validar creacion  "
                            + "sobre nodos apagados(PERMITE_CREA_NOD_OFF);");
                }
            }
        }
    }

    public CmtSolicitudCmMgl updateSolicitudCreaCM(CmtSolicitudCmMgl solicitudCm,
            AddressService addressServiceGestion,
            Map<CmtBasicaMgl, NodoMgl> datosGestion, NodoMgl nodoXdefecto,
            String usuario, int perfil,
            boolean isFichaNodos) throws ApplicationException, ApplicationException {
        CmtCuentaMatrizMglManager cuentaMatrizMglManager = new CmtCuentaMatrizMglManager();
        CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();
        SubDireccionMglManager subDireccionMglManager = new SubDireccionMglManager();
        DireccionMglManager direccionMglManager = new DireccionMglManager();
        CmtDireccionMglManager cmtDireccionMglManager = new CmtDireccionMglManager();
        CmtSubEdificioMglManager cmtSubEdificioMglManager = new CmtSubEdificioMglManager();
        CmtTecnologiaSubMglManager cmtTecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
        try {
            // Valida si RR se encuentra encendido o apagado para realizar las operaciones
            // en RR
            boolean habilitarRR = false;
            ParametrosMglManager parametrosManager = new ParametrosMglManager();
            ParametrosMgl parametroHabilitarRR = parametrosManager.findParametroMgl(Constant.HABILITAR_RR);
            if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
                habilitarRR = true;
            }

            CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
            boolean hasTorres = false;
            boolean creaTecnoSub;
            boolean sincronizaRR = false;
            ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
            List<CmtParamentrosComplejosDto> complejosDtosList;
            CmtSubEdificioMgl edificioMglAsociarHhpp;
            AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();
            CmtSolicitudCmMglDaoImpl CmtSolicitudCmMglDaoImpl = new CmtSolicitudCmMglDaoImpl();
            CmtBlackListMglManager blackListMglManager = new CmtBlackListMglManager();
            String comunidad = "000";
            String division = "000";
            String headEnd = "00";
            NodoMgl nodoUnico = new NodoMgl();
            CmtCompaniaMglDaoImpl cmtCompaniaMglDaoImpl = new CmtCompaniaMglDaoImpl();
            if (solicitudCm.getTipoSolicitudObj().getTipoSolicitudId()
                    .compareTo(Constant.TIPO_SOLICITUD_CREACION_CM) == 0
                    && solicitudCm.getEstadoSolicitudObj().getBasicaId().compareTo(basicaMglManager
                            .findByCodigoInternoApp(Constant.BASICA_ESTADO_SOLICITUD_FINALIZADO).getBasicaId()) == 0
                    && !solicitudCm.getResultGestion().getTipoBasicaObj().getIdentificadorInternoApp()
                            .equals(Constant.TIPO_BASICA_ACCION_RECHAZAR_CUENTA_MATRIZ)) {
                for (Map.Entry<CmtBasicaMgl, NodoMgl> n : datosGestion.entrySet()) {
                    validarCreacionCM(solicitudCm, n.getValue());
                    if (n.getKey().getListCmtBasicaExtMgl().size() > 0) {
                        for (CmtBasicaExtMgl cmtBasicaExtMgl : n.getKey().getListCmtBasicaExtMgl()) {
                            if (habilitarRR) {
                                if (cmtBasicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400()
                                        .equalsIgnoreCase(Constant.SINCRONIZA_RR)
                                        && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")
                                        && n.getKey().isNodoTecnologia()) {
                                    CmtComunidadRr comunidadRrr = n.getValue().getComId();
                                    headEnd = n.getValue().getNodHeadEnd();
                                    if (comunidadRrr != null) {
                                        comunidad = comunidadRrr.getCodigoRr();
                                        division = comunidadRrr.getRegionalRr().getCodigoRr();
                                        sincronizaRR = true;
                                        nodoUnico = n.getValue();
                                    }
                                }
                            }
                        }
                    }
                }
                cuentaMatrizMglManager = new CmtCuentaMatrizMglManager();
                CmtCuentaMatrizMgl cmtCuentaMatrizMgl = new CmtCuentaMatrizMgl();
                cmtCuentaMatrizMgl.setCentroPoblado(solicitudCm.getCentroPobladoGpo());
                cmtCuentaMatrizMgl.setComunidad(comunidad);
                cmtCuentaMatrizMgl.setDepartamento(solicitudCm.getDepartamentoGpo());
                cmtCuentaMatrizMgl.setDivision(division);
                cmtCuentaMatrizMgl.setMunicipio(solicitudCm.getCiudadGpo());
                cmtCuentaMatrizMgl.setNombreCuenta(
                        solicitudCm.getListCmtSolicitudSubEdificioMgl().get(0).getNombreSubedificio());
                cmtCuentaMatrizMgl.setEstadoRegistro(1);
                cmtCuentaMatrizMgl.setNumeroCuenta(BigDecimal.ZERO);
                if (isFichaNodos) {
                    cmtCuentaMatrizMgl.setOrigenFicha(Constant.PLAN_DE_EXPANSION_NACIONAL);
                }
                cmtCuentaMatrizMgl = cuentaMatrizMglManager.createCm(cmtCuentaMatrizMgl, usuario, perfil);
                solicitudCm.setCuentaMatrizObj(cmtCuentaMatrizMgl);
                CmtDireccionMgl cmtDireccionMgl;
                cmtDireccionMgl = solicitudCm.getListCmtDireccionesMgl().get(0).mapearCamposCmtDireccionMgl();
                cmtDireccionMgl.setCuentaMatrizObj(cmtCuentaMatrizMgl);
                cmtDireccionMgl.setTdiId(2);
                cmtDireccionMgl.setEstadoRegistro(1);
                DrDireccion drDireccion = solicitudCm.getListCmtDireccionesMgl().get(0).getCamposDrDireccion();
                DetalleDireccionEntity direccionEntity = drDireccion.convertToDetalleDireccionEntity();
                direccionEntity.setMultiOrigen(solicitudCm.getCentroPobladoGpo().getGpoMultiorigen());
                if (isFichaNodos) {
                    cmtDireccionMgl.setCalleRr(solicitudCm.getListCmtDireccionesMgl().get(0).getCalleRr());
                    cmtDireccionMgl.setUnidadRr(solicitudCm.getListCmtDireccionesMgl().get(0).getUnidadRr());
                } else {
                    DireccionRRManager drrm = new DireccionRRManager(direccionEntity, "", null);
                    String calleRRCorreg;
                    calleRRCorreg = drrm.getDireccion().getCalle();

                    if (cmtDireccionMgl.getCuentaMatrizObj() != null) {
                        GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
                        GeograficoPoliticoMgl centroPobladoCompleto = new GeograficoPoliticoMgl();
                        centroPobladoCompleto = geograficoPoliticoManager.findGeoPoliticoById(
                                cmtDireccionMgl.getCuentaMatrizObj().getCentroPoblado().getGpoId());

                        if (centroPobladoCompleto != null) {
                            if (centroPobladoCompleto.getCorregimiento() != null
                                    && !centroPobladoCompleto.getCorregimiento().isEmpty()
                                    && centroPobladoCompleto.getCorregimiento().equalsIgnoreCase("Y")) {

                                calleRRCorreg += " ";
                                String[] corregimiento = centroPobladoCompleto.getGpoNombre().split(" ");
                                for (int i = 0; i < corregimiento.length; i++) {
                                    calleRRCorreg += corregimiento[i];
                                    // JDHT
                                    cmtDireccionMgl.getCuentaMatrizObj().setCorregimientoAplicado(true);
                                }

                                if (calleRRCorreg.trim().length() > 50) {
                                    throw new ApplicationException(
                                            "Campo Calle ha superado el número máximo de caracteres en formato RR ["
                                                    + calleRRCorreg + "] al añadir el corregimiento.");
                                }
                            }
                        }
                    }

                    cmtDireccionMgl.setCalleRr(calleRRCorreg);
                    cmtDireccionMgl.setUnidadRr(drrm.getDireccion().getNumeroUnidad());
                }
                cmtDireccionMgl.setComentario("CREACION");
                AddressRequest addressRequest = new AddressRequest();
                DrDireccionManager direccionManager = new DrDireccionManager();
                String direccion = direccionManager.getDireccion(drDireccion);
                addressRequest.setAddress(direccion);
                addressRequest.setCity(solicitudCm.getCentroPobladoGpo().getGpoNombre());
                addressRequest.setCodDaneVt(solicitudCm.getCentroPobladoGpo().getGeoCodigoDane());
                addressRequest.setLevel("C");

                if (solicitudCm.getCentroPobladoGpo().getGpoMultiorigen().equalsIgnoreCase("1")
                        && cmtDireccionMgl.getCodTipoDir() != null
                        && cmtDireccionMgl.getCodTipoDir().equalsIgnoreCase("CK")) {
                    if (cmtDireccionMgl.getBarrio() != null && !cmtDireccionMgl.getBarrio().isEmpty()) {
                        addressRequest.setNeighborhood(cmtDireccionMgl.getBarrio());
                    }
                }

                addressRequest.setState(solicitudCm.getDepartamentoGpo().getGpoNombre());
                ResponseMessage responseMessage;
                if (isFichaNodos) {
                    // Metodo Create Address para fichas nodos pasando los datos ya georeferenciados
                    responseMessage = addressEJBRemote.createAddressFichas(addressServiceGestion.getRespuestaGeo(),
                            new AddressGeodata(), addressRequest, usuario, "CM-MGL", "true", drDireccion);
                } else {
                    responseMessage = addressEJBRemote.createAddress(addressRequest, usuario, "CM-MGL", "true",
                            drDireccion);
                    if (ResponseMessage.MESSAGE_ERROR_DIR_YA_EXISTE_EN_MALLA_ANTIGUA
                            .equalsIgnoreCase(responseMessage.getMessageText())) {
                        CmtValidadorDireccionesManager direccionesManager = new CmtValidadorDireccionesManager();
                        boolean isCalleCarrera = cmtDireccionMgl.getCodTipoDir().equalsIgnoreCase("CK");
                        direccionesManager.actualizarDireccionMayaNueva(addressRequest.getAddress(),
                                solicitudCm.getCiudadGpo(), cmtDireccionMgl.getBarrio(), isCalleCarrera);
                    }
                }

                if (responseMessage.getIdaddress() != null) {
                    if ("ERROR".equalsIgnoreCase(responseMessage.getMessageType())) {
                        if (!responseMessage.getIdaddress().toUpperCase().contains("D")) {
                            throw new ApplicationException(" " + responseMessage.getMessageText());
                        }
                    }
                    if (responseMessage.getIdaddress().toUpperCase().equalsIgnoreCase("")) {
                        throw new ApplicationException(
                                "Hubo un error al crear la direccion, Verifique con el area de HHPP");
                    }
                    if (responseMessage.getIdaddress().toUpperCase().contains("S")) {
                        throw new ApplicationException(
                                "La direccion se entendio como subdireccion, no es validada, Verifique con el area de HHPP");
                    }
                } else {
                    LOGGER.error(
                            "No fue entregado el Id Address a partir de la respuesta del servicio: " + responseMessage);
                    throw new ApplicationException(
                            "No fue entregado el Id Address a partir de la respuesta del servicio de creación de dirección GEO: "
                                    + responseMessage);

                }
                DireccionMgl direccionMgl = new DireccionMgl();
                if (responseMessage.getIdaddress() != null) {
                    direccionMgl
                            .setDirId(new BigDecimal(responseMessage.getIdaddress().replace("d", "").replace("D", "")));
                }
                cmtDireccionMgl.setDireccionObj(direccionMgl);
                if (responseMessage.getNuevaDireccionDetallada() != null) {
                    if (responseMessage.getNuevaDireccionDetallada() != null
                            && responseMessage.getNuevaDireccionDetallada().getDireccion() != null
                            && responseMessage.getNuevaDireccionDetallada().getDireccion().getDirEstrato() != null) {
                        cmtDireccionMgl.setEstrato(
                                responseMessage.getNuevaDireccionDetallada().getDireccion().getDirEstrato().intValue());
                    } else {
                        BigDecimal estrato = Constant.ESTRATO_DIRECCION_NG;
                        if (estrato != null) {
                            cmtDireccionMgl.setEstrato(estrato.intValue());
                        }
                    }
                } else {
                    LOGGER.error("No se encontró nueva dirección detallada para la dirección: "
                            + responseMessage.getAddress());
                    throw new ApplicationException("No se encontró nueva dirección detallada para la dirección: "
                            + responseMessage.getAddress());

                }
                cmtDireccionMglManager.create(cmtDireccionMgl, usuario, perfil);
                cmtCuentaMatrizMgl.getDireccionesList().add(cmtDireccionMgl);
                cmtCuentaMatrizMgl.setDireccionesList(new ArrayList<>());
                cmtCuentaMatrizMgl.getDireccionesList().add(cmtDireccionMgl);
                CmtSubEdificioMgl cmtSubEdificioMgl = new CmtSubEdificioMgl();

                CmtSolicitudSubEdificioMgl cmtSubedificiosSolicitudMglTemp = solicitudCm
                        .getListCmtSolicitudSubEdificioMgl().get(0);
                cmtSubEdificioMgl.setAdministrador(cmtSubedificiosSolicitudMglTemp.getAdministrador());
                cmtSubEdificioMgl.setCmtCuentaMatrizMglObj(cmtCuentaMatrizMgl);
                if (cmtSubedificiosSolicitudMglTemp.getCompaniaConstructoraObj() == null && solicitudCm
                        .getOrigenSolicitud().getBasicaId().compareTo(basicaMglManager.findByCodigoInternoApp(
                                Constant.BASICA_TIPO_SOLICITUD_ACOMETIDA).getBasicaId()) == 0) {
                    CmtCompaniaMgl cmtCompaniaMgl = cmtCompaniaMglDaoImpl.find(new BigDecimal("1294"));
                    cmtSubEdificioMgl.setCompaniaConstructoraObj(cmtCompaniaMgl);
                } else {
                    cmtSubEdificioMgl
                            .setCompaniaConstructoraObj(cmtSubedificiosSolicitudMglTemp.getCompaniaConstructoraObj());
                }
                cmtSubEdificioMgl
                        .setCompaniaAdministracionObj(cmtSubedificiosSolicitudMglTemp.getCompaniaAdministracionObj());
                cmtSubEdificioMgl.setCompaniaAscensorObj(cmtSubedificiosSolicitudMglTemp.getCompaniaAscensorObj());
                cmtSubEdificioMgl.setCuentaMatrizObj(cmtCuentaMatrizMgl);
                cmtSubEdificioMgl.setDireccion(direccion);
                cmtSubEdificioMgl.setTelefonoPorteria(cmtSubedificiosSolicitudMglTemp.getTelefonoPorteria());
                cmtSubEdificioMgl.setTelefonoPorteria2(cmtSubedificiosSolicitudMglTemp.getTelefonoPorteria2());
                cmtSubEdificioMgl.setDireccionAntigua(
                        addressServiceGestion != null && addressServiceGestion.getAlternateaddress() != null
                                ? addressServiceGestion.getAlternateaddress()
                                : "");
                cmtSubEdificioMgl.setFechaEntregaEdificio(cmtSubedificiosSolicitudMglTemp.getFechaEntregaEdificio());
                cmtSubEdificioMgl.setCambioestado("N");
                if (solicitudCm.isCasaaEdificio()) {
                    CmtBasicaMgl basicaMgl = new CmtBasicaMgl();
                    basicaMgl.setAbreviatura("ECM28");
                    basicaMgl.setBasicaId(basicaMglManager.findByCodigoInternoApp(
                            Constant.BASICA_ESTADO_SIN_VISITA_TECNICA).getBasicaId());
                    basicaMgl = basicaMglManager.findById(basicaMgl);
                    cmtSubEdificioMgl.setEstadoSubEdificioObj(basicaMgl);
                    creaTecnoSub = true;
                    cmtSubEdificioMgl.getCuentaMatrizObj().setUnicoEdif("SI");
                } else {
                    if (solicitudCm.getOrigenSolicitud() != null
                            && solicitudCm.getOrigenSolicitud().getIdentificadorInternoApp()
                                    .equals(Constant.BASICA_ORIGEN_DE_SOLICTUD_CONSTRUCTORA)) {
                        if (solicitudCm.getCantidadTorres() == null
                                || solicitudCm.getCantidadTorres().compareTo(BigDecimal.ZERO) == 0) {
                            CmtBasicaMgl basicaMgl = new CmtBasicaMgl();
                            basicaMgl.setBasicaId(basicaMglManager.findByCodigoInternoApp(
                                    Constant.BASICA_ESTADO_SIN_VISITA_TECNICA).getBasicaId());
                            basicaMgl = basicaMglManager.findById(basicaMgl);
                            cmtSubEdificioMgl.setEstadoSubEdificioObj(basicaMgl);
                            creaTecnoSub = true;
                            cmtSubEdificioMgl.getCuentaMatrizObj().setUnicoEdif("SI");
                        } else {
                            CmtBasicaMgl basicaMgl = new CmtBasicaMgl();
                            basicaMgl.setBasicaId(basicaMglManager.findByCodigoInternoApp(
                                    Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO).getBasicaId());
                            basicaMgl = basicaMglManager.findById(basicaMgl);
                            cmtSubEdificioMgl.setEstadoSubEdificioObj(basicaMgl);
                            creaTecnoSub = true;
                            cmtSubEdificioMgl.getCuentaMatrizObj().setUnicoEdif("NO");
                        }
                    } else {
                        CmtBasicaMgl basicaMgl = new CmtBasicaMgl();
                        basicaMgl.setBasicaId(basicaMglManager.findByCodigoInternoApp(
                                Constant.BASICA_ESTADO_SIN_VISITA_TECNICA).getBasicaId());
                        basicaMgl = basicaMglManager.findById(basicaMgl);
                        cmtSubEdificioMgl.setEstadoSubEdificioObj(basicaMgl);
                        cmtSubEdificioMgl.getCuentaMatrizObj().setUnicoEdif("SI");
                        creaTecnoSub = true;
                    }
                }
                cmtSubEdificioMgl.setEstrato(cmtSubedificiosSolicitudMglTemp.getEstratoSubEdificioObj());
                cmtSubEdificioMgl.setHeadEnd(headEnd);
                cmtSubEdificioMgl.setNombreSubedificio(cmtSubedificiosSolicitudMglTemp.getNombreSubedificio());
                cmtSubEdificioMgl.setOrigenDatosObj(null);
                cmtSubEdificioMgl.setTipoEdificioObj(cmtSubedificiosSolicitudMglTemp.getTipoEdificioObj());
                cmtSubEdificioMgl.setTipoLoc("ND");
                CmtBasicaMgl basicaMglOrigendatos = new CmtBasicaMgl();
                if (isFichaNodos) {
                    basicaMglOrigendatos.setBasicaId(basicaMglManager.findByCodigoInternoApp(
                            Constant.BASICA_ORIGEN_DATOS_NODO_NUEVO).getBasicaId());
                } else {
                    basicaMglOrigendatos.setBasicaId(basicaMglManager.findByCodigoInternoApp(
                            Constant.BASICA_ORIGEN_DATOS_BARRIDO_CALLES).getBasicaId());
                }
                basicaMglOrigendatos = basicaMglManager.findById(basicaMglOrigendatos);
                cmtSubEdificioMgl.setOrigenDatosObj(basicaMglOrigendatos);
                cmtSubEdificioMgl.setPlanos("N");
                cmtSubEdificioMgl.setReDiseno("N");
                cmtSubEdificioMgl.setVisitaTecnica("N");
                cmtSubEdificioMgl.setCierre("N");
                cmtSubEdificioMgl.setConexionCorriente("N");
                cmtSubEdificioMgl.setUnidadesEstimadas(cmtSubedificiosSolicitudMglTemp.getUnidades());
                cmtSubEdificioMgl.setCodigoRr("0000");
                if (sincronizaRR) {
                    cmtSubEdificioMgl.setNodoObj(nodoUnico);
                } else {
                    cmtSubEdificioMgl.setNodoObj(nodoXdefecto);
                }
                cmtSubEdificioMgl.setEstadoRegistro(1);

                CmtBasicaMgl basicaMglTipoProyecto = new CmtBasicaMgl();
                if (solicitudCm.getOrigenSolicitud().getIdentificadorInternoApp().equals(
                        Constant.BASICA_ORIGEN_DE_SOLICTUD_CONSTRUCTORA)) {
                    basicaMglTipoProyecto.setBasicaId(basicaMglManager
                            .findByCodigoInternoApp(Constant.BASICA_TIPO_PROYECTO_CONSTRUCTORARECIDENCIAL)
                            .getBasicaId());
                } else {
                    if (isFichaNodos) {
                        basicaMglTipoProyecto.setBasicaId(basicaMglManager.findByCodigoInternoApp(
                                Constant.BASICA_TIPO_PROYECTO_NODO_NUEVO).getBasicaId());
                    } else {
                        basicaMglTipoProyecto.setBasicaId(basicaMglManager.findByCodigoInternoApp(
                                Constant.BASICA_TIPO_PROYECTO_ACOMETIDA_NORMAL).getBasicaId());
                    }
                }
                basicaMglTipoProyecto = basicaMglManager.findById(basicaMglTipoProyecto);
                cmtSubEdificioMgl.setTipoProyectoObj(basicaMglTipoProyecto);
                cmtSubEdificioMgl = cmtSubEdificioMglManager.create(cmtSubEdificioMgl, usuario, perfil);

                if (creaTecnoSub) {
                    cmtTecnologiaSubMglManager.crearTecnSubXGestion(datosGestion, cmtSubEdificioMgl, usuario, perfil);
                }
                complejosDtosList = parametrosMglManager.findComplejo(BLACK_LIST_DFAULT);
                if (complejosDtosList != null && !complejosDtosList.isEmpty()) {
                    for (CmtParamentrosComplejosDto val : complejosDtosList) {
                        CmtBasicaMgl basicaMglBlacList = new CmtBasicaMgl();
                        basicaMglBlacList.setAbreviatura(val.getValue());
                        CmtTipoBasicaMgl tipoBasicaMglBlaklist = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                                Constant.TIPO_BASICA_BLACK_LIST_CM);
                        basicaMglBlacList.setTipoBasicaObj(tipoBasicaMglBlaklist);
                        basicaMglBlacList = basicaMglManager.findByAbreviaAndTipoBasica(basicaMglBlacList);
                        if (basicaMglBlacList != null) {
                            CmtBlackListMgl blackListMgl = new CmtBlackListMgl();
                            blackListMgl.setBlackListObj(basicaMglBlacList);
                            blackListMgl.setDetalle("Creacion CM");
                            blackListMgl.setEstadoRegistro(1);
                            blackListMgl.setSubEdificioObj(cmtSubEdificioMgl);
                            blackListMglManager.createWithAutRR(blackListMgl, usuario, perfil);
                            cmtSubEdificioMgl.getListCmtBlackListMgl().add(blackListMgl);
                        }
                    }
                }
                complejosDtosList = parametrosMglManager.findComplejo(BLACK_LIST_POR_ORIGEN);
                if (complejosDtosList != null && !complejosDtosList.isEmpty()) {
                    for (CmtParamentrosComplejosDto val : complejosDtosList) {
                        if (val.getKey().trim().equalsIgnoreCase(
                                solicitudCm.getOrigenSolicitud().getAbreviatura().trim())) {
                            CmtBasicaMgl basicaMglBlacList = new CmtBasicaMgl();
                            basicaMglBlacList.setAbreviatura(val.getValue());
                            CmtTipoBasicaMgl tipoBasicaMglBlaklist = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                                    Constant.TIPO_BASICA_BLACK_LIST_CM);
                            basicaMglBlacList.setTipoBasicaObj(tipoBasicaMglBlaklist);
                            basicaMglBlacList = basicaMglManager.findByAbreviaAndTipoBasica(basicaMglBlacList);
                            if (basicaMglBlacList != null) {
                                CmtBlackListMgl blackListMgl = new CmtBlackListMgl();
                                blackListMgl.setBlackListObj(basicaMglBlacList);
                                blackListMgl.setDetalle("Creacion CM");
                                blackListMgl.setEstadoRegistro(1);
                                blackListMgl.setSubEdificioObj(cmtSubEdificioMgl);
                                blackListMglManager.createWithAutRR(blackListMgl, usuario, perfil);
                                cmtSubEdificioMgl.getListCmtBlackListMgl().add(blackListMgl);
                            }
                        }
                    }
                }
                CmtBasicaMgl basicaMglProducto = new CmtBasicaMgl();
                basicaMglProducto.setBasicaId(
                        basicaMglManager.findByCodigoInternoApp(Constant.BASICA_TIPO_PRODUCTO_MULTIPLAY).getBasicaId());
                basicaMglProducto = basicaMglManager.findById(basicaMglProducto);
                cmtSubEdificioMgl.setProductoObj(basicaMglProducto);
                cmtCuentaMatrizMgl.setListCmtSubEdificioMgl(new ArrayList<>());
                cmtCuentaMatrizMgl.getListCmtSubEdificioMgl().add(cmtSubEdificioMgl);
                edificioMglAsociarHhpp = cmtSubEdificioMgl;
                if (solicitudCm.getCantidadTorres() != null
                        && solicitudCm.getCantidadTorres().compareTo(BigDecimal.ZERO) > 0
                        && cmtSubedificiosSolicitudMglTemp.getCompaniaConstructoraObj() != null
                        && cmtSubedificiosSolicitudMglTemp.getCompaniaConstructoraObj().getCompaniaId() != null
                        && solicitudCm.getOrigenSolicitud().getIdentificadorInternoApp()
                                .equals(Constant.BASICA_ORIGEN_DE_SOLICTUD_CONSTRUCTORA)) {
                    CmtBasicaMgl basicaMgl = new CmtBasicaMgl();
                    basicaMgl.setBasicaId(basicaMglManager.findByCodigoInternoApp(
                            Constant.BASICA_ESTADO_SIN_VISITA_TECNICA).getBasicaId());
                    basicaMgl = basicaMglManager.findById(basicaMgl);
                    int CantidadTorresAGenerar = solicitudCm.getCantidadTorres().intValue();
                    for (int ContadorTorres = 1; ContadorTorres <= CantidadTorresAGenerar; ContadorTorres++) {
                        cmtSubEdificioMgl = new CmtSubEdificioMgl();
                        cmtSubEdificioMgl.setAdministrador(cmtSubedificiosSolicitudMglTemp.getAdministrador());
                        cmtSubEdificioMgl.setCmtCuentaMatrizMglObj(cmtCuentaMatrizMgl);
                        cmtSubEdificioMgl.setCompaniaAdministracionObj(
                                cmtSubedificiosSolicitudMglTemp.getCompaniaAdministracionObj());
                        cmtSubEdificioMgl
                                .setCompaniaAscensorObj(cmtSubedificiosSolicitudMglTemp.getCompaniaAscensorObj());
                        cmtSubEdificioMgl.setCompaniaConstructoraObj(
                                cmtSubedificiosSolicitudMglTemp.getCompaniaConstructoraObj());
                        cmtSubEdificioMgl.setTelefonoPorteria(cmtSubedificiosSolicitudMglTemp.getTelefonoPorteria());
                        cmtSubEdificioMgl
                                .setFechaEntregaEdificio(cmtSubedificiosSolicitudMglTemp.getFechaEntregaEdificio());
                        cmtSubEdificioMgl.setCuentaMatrizObj(cmtCuentaMatrizMgl);
                        cmtSubEdificioMgl.setDireccion("");
                        cmtSubEdificioMgl.setDireccionAntigua("");
                        cmtSubEdificioMgl.setEstadoRegistro(1);
                        cmtSubEdificioMgl.setEstadoSubEdificioObj(basicaMgl);
                        cmtSubEdificioMgl.setOrigenDatosObj(basicaMglOrigendatos);
                        cmtSubEdificioMgl.setTipoProyectoObj(basicaMglTipoProyecto);
                        cmtSubEdificioMgl.setEstrato(cmtSubedificiosSolicitudMglTemp.getEstratoSubEdificioObj());
                        cmtSubEdificioMgl.setHeadEnd(headEnd);
                        cmtSubEdificioMgl.setNombreSubedificio("TORRE " + ContadorTorres);
                        cmtSubEdificioMgl.setOrigenDatosObj(null);
                        cmtSubEdificioMgl.setTipoEdificioObj(cmtSubedificiosSolicitudMglTemp.getTipoEdificioObj());
                        cmtSubEdificioMgl.setTipoLoc("ND");
                        cmtSubEdificioMgl.setPlanos("N");
                        cmtSubEdificioMgl.setReDiseno("N");
                        cmtSubEdificioMgl.setVisitaTecnica("N");
                        cmtSubEdificioMgl.setCierre("N");
                        cmtSubEdificioMgl.setConexionCorriente("N");
                        cmtSubEdificioMgl.setUnidadesEstimadas(cmtSubedificiosSolicitudMglTemp.getUnidades());
                        cmtSubEdificioMgl.setCodigoRr("0000");
                        if (sincronizaRR) {
                            cmtSubEdificioMgl.setNodoObj(nodoUnico);
                        } else {
                            cmtSubEdificioMgl.setNodoObj(nodoXdefecto);
                        }
                        cmtSubEdificioMgl.setProductoObj(basicaMglProducto);
                        cmtSubEdificioMglManager.create(cmtSubEdificioMgl, usuario, perfil);
                        cmtSubEdificioMgl.setEstadoRegistro(1);
                        cmtCuentaMatrizMgl.getListCmtSubEdificioMgl().add(cmtSubEdificioMgl);
                        cmtSubEdificioMgl.getCuentaMatrizObj().setUnicoEdif("NO");
                        cmtTecnologiaSubMglManager.crearTecnSubXGestion(datosGestion, cmtSubEdificioMgl, usuario,
                                perfil);

                        hasTorres = true;
                    }
                }
                List<CmtUnidadesPreviasMgl> previasMglList = solicitudCm.getListUnidadesPreviasCm();
                if (previasMglList != null && !previasMglList.isEmpty()) {
                    for (CmtUnidadesPreviasMgl cupm : previasMglList) {
                        HhppMglManager hhppMglManager = new HhppMglManager();
                        List<HhppMgl> lsthhppMgl = null;
                        if (cupm.getCmtDireccionDetalladaMgl() != null
                                && cupm.getCmtDireccionDetalladaMgl().getDireccion() != null) {
                            BigDecimal dirId = cupm.getCmtDireccionDetalladaMgl().getDireccion().getDirId();
                            BigDecimal subDirId = cupm.getCmtDireccionDetalladaMgl().getSubDireccion() != null
                                    ? cupm.getCmtDireccionDetalladaMgl().getSubDireccion().getSdiId()
                                    : null;
                            lsthhppMgl = hhppMglManager.findHhppByDirIdSubDirId(dirId, subDirId);
                        }
                        if (lsthhppMgl != null && !lsthhppMgl.isEmpty()) {
                            for (HhppMgl hhppMgl : lsthhppMgl) {
                                String nivel5tipo = (cupm.getNivel5TipoNuevo() == null
                                        || cupm.getNivel5TipoNuevo().isEmpty()) ? "" : cupm.getNivel5TipoNuevo();
                                String nivel5valor = (cupm.getNivel5ValorNuevo() == null
                                        || cupm.getNivel5ValorNuevo().isEmpty()) ? "" : cupm.getNivel5ValorNuevo();
                                String nivel6tipo = (cupm.getNivel6TipoNuevo() == null
                                        || cupm.getNivel6TipoNuevo().isEmpty()) ? "" : cupm.getNivel6TipoNuevo();
                                String nivel6valor = (cupm.getNivel6ValorNuevo() == null
                                        || cupm.getNivel6ValorNuevo().isEmpty()) ? "" : cupm.getNivel6ValorNuevo();
                                DrDireccion dirCambioSub = drDireccion.clone();
                                dirCambioSub.setCpTipoNivel5(nivel5tipo);
                                dirCambioSub.setCpTipoNivel6(nivel6tipo);
                                dirCambioSub.setCpValorNivel5(nivel5valor);
                                dirCambioSub.setCpValorNivel6(nivel6valor);
                                String torreUno = "";
                                String direccionHhpp = direccion + " " + torreUno + " " + nivel5tipo + " " + nivel5valor
                                        + " " + nivel6tipo + " " + nivel6valor;
                                addressRequest.setAddress(direccionHhpp);
                                addressRequest.setCity(solicitudCm.getCentroPobladoGpo().getGpoNombre());
                                addressRequest.setCodDaneVt(solicitudCm.getCentroPobladoGpo().getGeoCodigoDane());
                                addressRequest.setLevel("C");
                                if (solicitudCm.getCentroPobladoGpo().getGpoMultiorigen().equalsIgnoreCase("1")
                                        && cmtDireccionMgl.getCodTipoDir().equalsIgnoreCase("CK")) {
                                    addressRequest.setNeighborhood(cmtDireccionMgl.getBarrio());
                                }
                                addressRequest.setState(solicitudCm.getDepartamentoGpo().getGpoNombre());
                                if (isFichaNodos) {
                                    responseMessage = addressEJBRemote.createAddressFichas(
                                            addressServiceGestion.getRespuestaGeo(), new AddressGeodata(),
                                            addressRequest, usuario, "CM-MGL", "true", dirCambioSub);
                                } else {
                                    responseMessage = addressEJBRemote.createAddress(addressRequest, usuario, "CM-MGL",
                                            "true", dirCambioSub);
                                }
                                if (responseMessage.getMessageType().equalsIgnoreCase("ERROR")) {
                                    if (!(responseMessage.getIdaddress().toUpperCase().contains("D")
                                            || responseMessage.getIdaddress().toUpperCase().contains("S"))) {
                                        throw new ApplicationException(" " + responseMessage.getMessageText());
                                    }
                                }
                                BigDecimal direccionId;
                                BigDecimal subDireccionId;
                                if (responseMessage.getIdaddress().toUpperCase().contains("D")) {
                                    DireccionMgl direccionMglCreada = new DireccionMgl();
                                    direccionId = new BigDecimal(
                                            responseMessage.getIdaddress().replace("d", "").replace("D", ""));
                                    direccionMglCreada.setDirId(direccionId);
                                    direccionMglCreada = direccionMglManager.findById(direccionMglCreada);
                                    hhppMgl.setDireccionObj(direccionMglCreada);
                                } else {
                                    DireccionMgl direccionMglCreada = new DireccionMgl();
                                    subDireccionId = new BigDecimal(
                                            responseMessage.getIdaddress().replace("s", "").replace("S", ""));
                                    SubDireccionMgl subDireccionMgl = subDireccionMglManager.findById(subDireccionId);
                                    direccionMglCreada.setDirId(subDireccionMgl.getDirId());
                                    hhppMgl.setDireccionObj(direccionMglCreada);
                                    hhppMgl.setSubDireccionObj(subDireccionMgl);
                                }
                                CmtBasicaMgl tecnologia = null;
                                if (sincronizaRR && habilitarRR) {
                                    hhppMgl.setNodId(nodoUnico);
                                    tecnologia = nodoUnico.getNodTipo();
                                } else {
                                    for (Map.Entry<CmtBasicaMgl, NodoMgl> n : datosGestion.entrySet()) {
                                        hhppMgl.setNodId((hhppMgl.getNodId().getNodTipo().getNombreBasica()
                                                .equalsIgnoreCase(n.getKey().getNombreBasica())) ? n.getValue()
                                                        : nodoXdefecto);
                                        tecnologia = (hhppMgl.getNodId().getNodTipo().getNombreBasica()
                                                .equalsIgnoreCase(n.getKey().getNombreBasica())) ? n.getKey()
                                                        : nodoXdefecto.getNodTipo();
                                    }
                                }
                                if (solicitudCm.getCantidadTorres() != null
                                        && (solicitudCm.getCantidadTorres().compareTo(BigDecimal.ZERO) != 0)) {
                                    direccionEntity.setCptiponivel1("TORRE");
                                    direccionEntity.setCpvalornivel1("1");
                                }
                                direccionEntity.setCptiponivel1("");
                                direccionEntity.setCpvalornivel1("");
                                direccionEntity.setCptiponivel5(cupm.getNivel5TipoNuevo());
                                direccionEntity.setCpvalornivel5(cupm.getNivel5ValorNuevo());
                                direccionEntity.setCptiponivel6(cupm.getNivel6TipoNuevo());
                                direccionEntity.setCpvalornivel6(cupm.getNivel6ValorNuevo());
                                DireccionRRManager direccionRRManagerHhpp = new DireccionRRManager(direccionEntity, "",
                                        solicitudCm.getCentroPobladoGpo());
                                hhppMgl.setHhpApart(direccionRRManagerHhpp.getDireccion().getNumeroApartamento());
                                hhppMgl.setHhppSubEdificioObj(edificioMglAsociarHhpp);
                                CmtTecnologiaSubMgl cmtTecnologiaSubMgl = null;
                                if (tecnologia != null) {
                                    cmtTecnologiaSubMgl = cmtTecnologiaSubMglManager
                                            .findBySubEdificioTecnologia(edificioMglAsociarHhpp, tecnologia);
                                }
                                if (cmtTecnologiaSubMgl != null
                                        && cmtTecnologiaSubMgl.getTecnoSubedificioId() != null) {
                                    hhppMgl.setCmtTecnologiaSubId(cmtTecnologiaSubMgl);
                                }
                                hhppMgl.setSuscriptor("NA");
                                hhppMglManager.update(hhppMgl);
                                comunidad = hhppMgl.getNodId().getComId().getCodigoRr();
                                division = hhppMgl.getNodId().getComId().getRegionalRr().getCodigoRr();

                                if (sincronizaRR && habilitarRR && hhppMgl.getHhpIdrR() != null) {
                                    // Consultamos el hhpp real de rr
                                    DireccionRRManager manager = new DireccionRRManager(true);

                                    String comunidadOrg;
                                    String divisionOrg;
                                    String placaOrg;
                                    String calleOrg;
                                    String apartamentoOrg;

                                    HhppResponseRR responseHhppRR = manager.getHhppByIdRR(hhppMgl.getHhpIdrR());
                                    if (responseHhppRR.getTipoMensaje() != null
                                            && responseHhppRR.getTipoMensaje().equalsIgnoreCase("I")) {
                                        comunidadOrg = responseHhppRR.getComunidad();
                                        divisionOrg = responseHhppRR.getDivision();
                                        calleOrg = responseHhppRR.getStreet();
                                        placaOrg = responseHhppRR.getHouse();
                                        apartamentoOrg = responseHhppRR.getApartamento();
                                        comunidad = responseHhppRR.getComunidad();
                                        division = responseHhppRR.getDivision();
                                        direccionRRManagerHhpp.cambiarDirHHPPRRCm(comunidadOrg,
                                                divisionOrg,
                                                placaOrg,
                                                calleOrg,
                                                apartamentoOrg,
                                                comunidad,
                                                division,
                                                direccionRRManagerHhpp.getDireccion().getNumeroUnidad(),
                                                direccionRRManagerHhpp.getDireccion().getCalle(),
                                                direccionRRManagerHhpp.getDireccion().getNumeroApartamento(),
                                                solicitudCm.getSolicitudCmId().toString(),
                                                usuario,
                                                solicitudCm.getTipoSolicitudObj().getNombreTipo(),
                                                addressServiceGestion != null
                                                        ? addressServiceGestion.getAlternateaddress()
                                                        : "",
                                                solicitudCm.getListCmtDireccionesMgl().get(0).getCodTipoDir(),
                                                responseMessage.getIdaddress());

                                    } else {
                                        LOGGER.error(
                                                "Ocurrio un error consultando la data del hhpp en RR por tal motivo"
                                                        + " no se hace cambio de dirección al HHPP");
                                    }
                                }
                            }
                        }
                    }
                }
                solicitudCm = registrarHhpp(solicitudCm, direccionEntity, cmtDireccionMgl,
                        hasTorres, usuario, addressServiceGestion, datosGestion, perfil,
                        sincronizaRR, addressRequest, addressEJBRemote, isFichaNodos);
                CmtBasicaMgl estadoTecnologiaRR = actualizarEstadosCMRR(solicitudCm.getCuentaMatrizObj(), usuario,
                        perfil);
                if (estadoTecnologiaRR == null) {
                    if (solicitudCm.getCuentaMatrizObj() != null
                            && solicitudCm.getCuentaMatrizObj().getSubEdificioGeneral() != null
                            && solicitudCm.getCuentaMatrizObj().getSubEdificioGeneral().getEstadoSubEdificioObj()
                                    .getNombreBasica() != null) {
                        String estadoTecnologia = solicitudCm.getCuentaMatrizObj().getSubEdificioGeneral()
                                .getEstadoSubEdificioObj().getNombreBasica().trim();
                        String mensaje = "NO se encontró regla de sincronizacion de estado combinado para RR, se actualizara el estado de la CM a: "
                                + estadoTecnologia + " ";
                        solicitudCm.setEstadoTecnoRR(mensaje);
                    }
                } else {
                    String mensaje = "Se encontró una regla de sincronizacion con RR de estado combinado,"
                            + " se actualizara el estado de la CM a: " + estadoTecnologiaRR.getNombreBasica()
                            + " y se enviara a RR ";
                    solicitudCm.setEstadoTecnoRR(mensaje);
                }
            }
            return CmtSolicitudCmMglDaoImpl.updateCm(solicitudCm, usuario, perfil);
        } catch (ApplicationException | ExceptionDB | CloneNotSupportedException ex) {
            List<CmtSubEdificioMgl> SubEEraseList = null;
            List<CmtDireccionMgl> direccionEraseList = null;
            if (solicitudCm != null && solicitudCm.getCuentaMatrizObj() != null) {
                SubEEraseList = solicitudCm.getCuentaMatrizObj().getListCmtSubEdificioMglActivos();
                direccionEraseList = solicitudCm.getCuentaMatrizObj().getDireccionesList();
            }

            if (SubEEraseList != null && !SubEEraseList.isEmpty()) {
                for (CmtSubEdificioMgl edificioMgl : SubEEraseList) {
                    cmtSubEdificioMglManager.deleteSinRr(edificioMgl, usuario, perfil);
                    List<CmtTecnologiaSubMgl> listaTecnoABorrar = null;
                    listaTecnoABorrar = cmtTecnologiaSubMglManager.findTecnoSubBySubEdi(edificioMgl);
                    for (CmtTecnologiaSubMgl lista : listaTecnoABorrar) {
                        cmtTecnologiaSubMglManager.deleteSubEdificioTecnologia(lista, usuario, perfil);
                    }
                }
            }
            if (direccionEraseList != null) {
                for (CmtDireccionMgl direccionE : direccionEraseList) {
                    cmtDireccionMglManager.deleteSinRr(direccionE, usuario, perfil);
                }
            }
            boolean deleteSinRr = false;
            if (solicitudCm != null && solicitudCm.getCuentaMatrizObj() != null) {
                deleteSinRr = cuentaMatrizMglManager.deleteSinRr(solicitudCm.getCuentaMatrizObj(), usuario, perfil);
            }

            if (deleteSinRr) {
                LOGGER.info("MATRIZ BORRADA ROLLBACK");
            }
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg, ex);
            throw new ApplicationException("MATRIZ BORRADA ROLLBACK " + ex.getMessage(), ex);
        }
    }

    public boolean delete(CmtSolicitudCmMgl CmtSolicitudCmMgl) throws ApplicationException {
        CmtSolicitudCmMglDaoImpl cmtSolicitudCmMglDaoImpl = new CmtSolicitudCmMglDaoImpl();
        return cmtSolicitudCmMglDaoImpl.delete(CmtSolicitudCmMgl);
    }

    public CmtSolicitudCmMgl findById(CmtSolicitudCmMgl CmtSolicitudCmMgl) throws ApplicationException {
        CmtSolicitudCmMglDaoImpl cmtSolicitudCmMglDaoImpl = new CmtSolicitudCmMglDaoImpl();
        return cmtSolicitudCmMglDaoImpl.find(CmtSolicitudCmMgl.getSolicitudCmId());
    }

    public CmtSolicitudCmMgl findById(BigDecimal id) throws ApplicationException {
        CmtSolicitudCmMglDaoImpl cmtSolicitudCmMglDaoImpl = new CmtSolicitudCmMglDaoImpl();
        return cmtSolicitudCmMglDaoImpl.find(id);
    }

    public String cargarParametrosCorreoCM(CmtSolicitudCmMgl cmtSolicitudCmMgl, String proceso)
            throws ApplicationException {
        try {
            final String GESTION = "GESTION";
            ParametrosMglManager parametrosManager = new ParametrosMglManager();
            List<CmtParamentrosComplejosDto> tipoSolicitudLis = parametrosManager
                    .findComplejo(Constant.GRUPO_TIPO_SOLICITUD_CM_PARAM);

            BigDecimal tipoSolicitudId = Optional.ofNullable(cmtSolicitudCmMgl)
                    .map(CmtSolicitudCmMgl::getTipoSolicitudObj)
                    .map(CmtTipoSolicitudMgl::getTipoSolicitudId)
                    .orElse(null);

            String tipoSolicitudStr = tipoSolicitudLis.stream()
                    .filter(tipo -> StringUtils.isNotBlank(tipo.getValue()))
                    .filter(tipo -> Objects.nonNull(tipoSolicitudId)
                            && tipoSolicitudId.compareTo(new BigDecimal(tipo.getValue())) == 0)
                    .map(CmtParamentrosComplejosDto::getKey)
                    .findFirst().orElseThrow(
                            () -> new ApplicationException("Error: cargarParametrosCorreoCM: datos no encontrados"));

            BinaryOperator<String> respuesta = (gestion, crea) -> {
                if (proceso.equalsIgnoreCase(GESTION)) {
                    return gestion;
                }

                if (proceso.equalsIgnoreCase("SOLICITUD_CM")) {
                    return crea;
                }

                return null;
            };

            Map<String, String> parametrosCorreoMap = new HashMap<>();
            parametrosCorreoMap.put(Constant.ID_TIPO_SOLICITUD_CREA_CM,
                    respuesta.apply(Constant.ID_CORREO_GESTION_SOLICITUD_CREA_CM,
                            Constant.ID_CORREO_CREA_SOLICITUD_CREA_CM));
            parametrosCorreoMap.put(Constant.ID_TIPO_SOLICITUD_CREA_HHPP_CM,
                    respuesta.apply(Constant.ID_CORREO_GESTION_SOLICITUD_CREA_HHPP_CM,
                            Constant.ID_CORREO_CREA_SOLICITUD_CREA_HHPP_CM));
            parametrosCorreoMap.put(Constant.ID_TIPO_SOLICITUD_MOD_CM,
                    respuesta.apply(Constant.ID_CORREO_GESTION_SOLICITUD_MOD_CM,
                            Constant.ID_CORREO_CREA_SOLICITUD_MOD_CM));
            parametrosCorreoMap.put(Constant.ID_TIPO_SOLICITUD_VT,
                    respuesta.apply(Constant.ID_CORREO_GESTION_SOLICITUD_VT, Constant.ID_CORREO_CREA_SOLICITUD_VT));
            parametrosCorreoMap.put(Constant.ID_TIPO_SOLICITUD_ELIMINA_CM,
                    respuesta.apply(Constant.ID_CORREO_GESTION_SOLICITUD_ELIMINA_CM,
                            Constant.ID_CORREO_CREA_SOLICITUD_ELIMINA_CM));
            parametrosCorreoMap.put(Constant.ID_TIPO_SOLICITUD_TRASLADO_HHPP_BLOQUEADO_CM,
                    respuesta.apply(Constant.ID_CORREO_CREA_SOLICITUD_CREA_HHPP_CM,
                            Constant.ID_CORREO_GESTION_SOLICITUD_CREA_HHPP_CM));
            String parametroCorreo = parametrosCorreoMap.getOrDefault(tipoSolicitudStr, null);

            if (Objects.isNull(parametroCorreo)) {
                throw new ApplicationException("Error: cargarParametrosCorreoCM: datos no encontrados");
            }

            return parametroCorreo;

        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método: '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg, e);
            throw new ApplicationException(
                    "Error: cargarParametrosCorreoCM: enviar el correo. EX000: " + e.getMessage());
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg, e);
            throw new ApplicationException(
                    "Error: cargarParametrosCorreoCM: enviar el correo. EX000: " + e.getMessage());
        }
    }

    /**
     * envia cporreo confiramdo la creacion de la solicitud de creacion de la
     * cuenta matriz por el id de la solicitud
     *
     * @param cmtSolicitudCmMgl Solicitud.
     * @throws ApplicationException
     */
    public void enviarCorreoCreacion(CmtSolicitudCmMgl cmtSolicitudCmMgl) throws ApplicationException {
        try {
            if (Objects.isNull(cmtSolicitudCmMgl)) {
                String msgError = "El objeto cmtSolicitudCmMgl recibido es nulo: "
                        + ClassUtils.getCurrentMethodName(this.getClass());
                LOGGER.error(msgError);
                return;
            }
            String asunto_correo = "Solicitudes CM " + cmtSolicitudCmMgl.getTipoSolicitudObj().getNombreTipo() +
                    " su numero de Solicitud es :" + cmtSolicitudCmMgl.getSolicitudCmId();
            ParametrosMglManager datosCorreo = new ParametrosMglManager();
            ParametrosMgl paramtroBodyMsn = datosCorreo
                    .findByAcronimoName(cargarParametrosCorreoCM(cmtSolicitudCmMgl, "SOLICITUD_CM"));
            if (paramtroBodyMsn != null && paramtroBodyMsn.getParValor() != null
                    && !paramtroBodyMsn.getParValor().isEmpty()) {
                // si el correo copia esta en null se sete "" para evitar null pointer
                if (cmtSolicitudCmMgl.getCorreoCopiaSolicitud() == null) {
                    cmtSolicitudCmMgl.setCorreoCopiaSolicitud("");
                }
                MailSender.send(cmtSolicitudCmMgl.getCorreoAsesor(),
                        cmtSolicitudCmMgl.getCorreoCopiaSolicitud(),
                        "",
                        asunto_correo,
                        cargaDatosCorreoCM(cmtSolicitudCmMgl, paramtroBodyMsn.getParValor().trim()));
            }
        } catch (ApplicationException e) {
            throw new ApplicationException("Error al momento de enviar el correo. EX000: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ApplicationException("Error al momento de enviar el correo. EX000: " + e.getMessage(), e);
        }
    }

    /**
     * envía correo confirmando la realización de la solicitud de creación de la
     * cuenta matriz por el ID de la solicitud
     *
     * @param cmtSolicitudCmMgl {@link CmtSolicitudCmMgl}
     */
    public void enviarCorreoGestion(CmtSolicitudCmMgl cmtSolicitudCmMgl) {
        try {
            Objects.requireNonNull(cmtSolicitudCmMgl,
                    "El objeto cmtSolicitudCmMgl recibido es nulo. "
                            + ClassUtils.getCurrentMethodName(this.getClass()));

            String asunto_correo = "Gestion CM " + cmtSolicitudCmMgl.getTipoSolicitudObj().getNombreTipo() +
                    " su número de Solicitud es :" + cmtSolicitudCmMgl.getSolicitudCmId();

            ParametrosMglManager datosCorreo = new ParametrosMglManager();
            ParametrosMgl paramtroBodyMsn = datosCorreo
                    .findByAcronimoName(cargarParametrosCorreoCM(cmtSolicitudCmMgl, "GESTION"));
            if (paramtroBodyMsn != null && paramtroBodyMsn.getParValor() != null
                    && !paramtroBodyMsn.getParValor().isEmpty()) {
                MailSender.send(cmtSolicitudCmMgl.getCorreoAsesor(),
                        cmtSolicitudCmMgl.getCorreoCopiaSolicitud(),
                        "",
                        asunto_correo,
                        cargaDatosCorreoCM(cmtSolicitudCmMgl, paramtroBodyMsn.getParValor().trim()));
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error al momento de enviar el correo. EX000: " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Error al momento de enviar el correo. EX000: " + e.getMessage(), e);
        }
    }

    public StringBuffer cargaDatosCorreoCM(CmtSolicitudCmMgl cmtSolicitudCmMgl, String bodyMsn)
            throws ApplicationException {
        StringBuffer msnStr = new StringBuffer("");
        if (bodyMsn != null) {
            try {
                if (cmtSolicitudCmMgl != null) {
                    if (bodyMsn.contains("numSolicitud") && cmtSolicitudCmMgl.getSolicitudCmId() != null) {
                        msnStr.append("\nNumero solicitud: ").append(cmtSolicitudCmMgl.getSolicitudCmId().toString())
                                .append("</br>");
                    }
                    if (bodyMsn.contains("tipoSolicitud") && cmtSolicitudCmMgl.getTipoSolicitudObj() != null &&
                            cmtSolicitudCmMgl.getTipoSolicitudObj().getNombreTipo() != null) {
                        msnStr.append("\nTipo de solicitud: ")
                                .append(StringUtils
                                        .stripAccents(cmtSolicitudCmMgl.getTipoSolicitudObj().getNombreTipo()))
                                .append("</br>");
                    }
                    if (bodyMsn.contains("tecSolicitud") && cmtSolicitudCmMgl.getBasicaIdTecnologia() != null &&
                            cmtSolicitudCmMgl.getBasicaIdTecnologia().getNombreBasica() != null) {
                        msnStr.append("\nTecnologia solicitud: ")
                                .append(cmtSolicitudCmMgl.getBasicaIdTecnologia().getNombreBasica()).append("</br>");
                    }
                    if (cmtSolicitudCmMgl.getCuentaMatrizObj() != null) {
                        CmtCuentaMatrizMgl cuenta = cmtSolicitudCmMgl.getCuentaMatrizObj();
                        if (bodyMsn.contains("numCMmgl") &&
                                cuenta.getCuentaMatrizId() != null) {
                            msnStr.append("\nCuenta Matriz mgl: ").append(cuenta.getCuentaMatrizId()).append("</br>");
                        }
                        if (bodyMsn.contains("numCMrr") &&
                                cuenta.getNumeroCuenta() != null) {
                            msnStr.append("\nNumero Cuenta Matiz rr: ").append(cuenta.getNumeroCuenta())
                                    .append("</br>");
                        }
                        if (bodyMsn.contains("nomCM") &&
                                cuenta.getNombreCuenta() != null) {
                            msnStr.append("\nNombre Cuenta Matriz: ").append(cuenta.getNombreCuenta()).append("</br>");
                        }
                        if (bodyMsn.contains("dirPrincipal") &&
                                cuenta.getDireccionPrincipal() != null &&
                                cuenta.getDireccionPrincipal().getDireccionObj() != null &&
                                cuenta.getDireccionPrincipal().getDireccionObj().getDireccionDetallada() != null) {
                            if (cuenta.getDireccionPrincipal().getDireccionObj().getDireccionDetallada()
                                    .getDireccionTexto() != null) {
                                msnStr.append("\nDireccion principal: ").append(cuenta.getDireccionPrincipal()
                                        .getDireccionObj().getDireccionDetallada().getDireccionTexto()).append("</br>");
                            }
                            if (bodyMsn.contains("estrato")
                                    && cuenta.getDireccionPrincipal().getDireccionObj().getDirEstrato() != null) {
                                msnStr.append("\nEstrato: ")
                                        .append(cuenta.getDireccionPrincipal().getDireccionObj().getDirEstrato())
                                        .append("</br>");
                            }
                        }
                        if (bodyMsn.contains("dirAlternas") && cuenta.getDireccionAlternaList() != null) {
                            StringBuffer da = new StringBuffer();
                            for (CmtDireccionMgl dirTemp : cuenta.getDireccionAlternaList()) {
                                if (dirTemp != null && dirTemp.getDireccionObj() != null &&
                                        dirTemp.getDireccionObj().getDireccionDetallada() != null &&
                                        dirTemp.getDireccionObj().getDireccionDetallada().getDireccionTexto() != null) {
                                    da.append(dirTemp.getDireccionObj().getDireccionDetallada().getDireccionTexto())
                                            .append("</br>");
                                }
                            }
                            msnStr.append("\nDirecciones Alternas: ").append(da);
                        }
                    }
                    if (bodyMsn.contains("departamento") && cmtSolicitudCmMgl.getDepartamentoGpo() != null &&
                            cmtSolicitudCmMgl.getDepartamentoGpo().getGpoNombre() != null) {
                        msnStr.append("\nDepartamento: ").append(cmtSolicitudCmMgl.getDepartamentoGpo().getGpoNombre())
                                .append("</br>");
                    }
                    if (bodyMsn.contains("ciudad") && cmtSolicitudCmMgl.getCiudadGpo() != null &&
                            cmtSolicitudCmMgl.getCiudadGpo().getGpoNombre() != null) {
                        msnStr.append("\nCiudad: ")
                                .append(StringUtils.stripAccents(cmtSolicitudCmMgl.getCiudadGpo().getGpoNombre()))
                                .append("</br>");
                    }
                    if (bodyMsn.contains("centrPobla") && cmtSolicitudCmMgl.getCentroPobladoGpo() != null &&
                            cmtSolicitudCmMgl.getCentroPobladoGpo().getGpoNombre() != null) {
                        msnStr.append("\nCentro poblado: ")
                                .append(StringUtils
                                        .stripAccents(cmtSolicitudCmMgl.getCentroPobladoGpo().getGpoNombre()))
                                .append("</br>");
                    }

                    if (bodyMsn.contains("estadoSolicit") && cmtSolicitudCmMgl.getEstadoSolicitudObj() != null &&
                            cmtSolicitudCmMgl.getEstadoSolicitudObj().getNombreBasica() != null) {
                        msnStr.append("\nEstado solicitud: ")
                                .append(cmtSolicitudCmMgl.getEstadoSolicitudObj().getNombreBasica()).append("</br>");
                    }
                    if (bodyMsn.contains("respActual") && cmtSolicitudCmMgl.getRespuestaActual() != null) {
                        msnStr.append("\nRespuesta actual: ").append(cmtSolicitudCmMgl.getRespuestaActual())
                                .append("</br>");
                    }
                    if (bodyMsn.contains("resulGestion") && cmtSolicitudCmMgl.getResultadoGestion() != null) {
                        msnStr.append("\nResultado gestion: ").append(cmtSolicitudCmMgl.getResultadoGestion())
                                .append("</br>");
                    }
                    UsuariosManager usuMan = new UsuariosManager();
                    if (bodyMsn.contains("usuarioGestiona") &&
                            cmtSolicitudCmMgl.getUsuarioGestionId() != null) {
                        Usuarios u = usuMan.findUsuarioById(cmtSolicitudCmMgl.getUsuarioGestionId());
                        if (u != null) {
                            msnStr.append("\nUsuario gestionador: ").append(u.getNombre()).append("</br>");
                        }
                    }
                    if (bodyMsn.contains("usuarioSolicita") &&
                            cmtSolicitudCmMgl.getUsuarioSolicitudId() != null) {
                        Usuarios u = usuMan.findUsuarioById(cmtSolicitudCmMgl.getUsuarioSolicitudId());
                        if (u != null) {
                            msnStr.append("\nUsuario solicitante: ").append(u.getNombre()).append("</br>");
                        }
                    }
                }

                /* solo hhpp */
                if (bodyMsn.contains("tipoModificacion")) {
                    msnStr.append("\nTipo modificacion: ").append("</br>");
                }

                return msnStr;
            } catch (ApplicationException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '"
                        + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg, e);
                throw new ApplicationException("Error: cargaDatosCorreo: enviar el correo. EX000: " + e.getMessage(),
                        e);
            } catch (Exception e) {
                String msg = "Se produjo un error al momento de ejecutar el método '"
                        + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg, e);
                throw new ApplicationException("Error: cargaDatosCorreo: enviar el correo. EX000: " + e.getMessage(),
                        e);
            }
        } else {
            return null;
        }
    }

    private List<BigDecimal> getEstadosPendientesId() {
        List<BigDecimal> estadSolicitudList = new ArrayList<>();
        try {
            CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();

            estadSolicitudList.add(basicaMglManager.findByCodigoInternoApp(
                    Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE).getBasicaId());
            estadSolicitudList.add(basicaMglManager.findByCodigoInternoApp(
                    Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE_COBERTURA).getBasicaId());
            estadSolicitudList.add(basicaMglManager.findByCodigoInternoApp(
                    Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE_HHPP).getBasicaId());
            estadSolicitudList.add(basicaMglManager.findByCodigoInternoApp(
                    Constant.BASICA_ESTADO_SOLICITUD_PENDIENTE_ESCALADO_ACO).getBasicaId());

        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg, ex);
        }
        return estadSolicitudList;
    }

    /**
     * Cuenta las Solicitudes por filtro.Permite realizar el conteo de las
     * solicitudes por los parametros de comunidad, division, segmento y lista
     * de tipos de solicitudes.
     *
     * @author Johnnatan Ortiz
     * @param division          division de las solicitudes
     * @param comunidad         comunidad de las solicitudes
     * @param segmento          segmento de las solicitudes
     * @param llamada
     * @param tipoSolicitudList lista de los tipos de solicitudes a consultar
     * @return Compentencias asociadas a un SubEdifico
     * @throws ApplicationException
     */
    public int getCountPendientesByFiltroParaGestion(String division,
            String comunidad,
            CmtBasicaMgl segmento,
            List<CmtTipoSolicitudMgl> tipoSolicitudList,
            boolean llamada) throws ApplicationException {

        List<BigDecimal> tipoSolicitudIdList = null;
        if (tipoSolicitudList != null && !tipoSolicitudList.isEmpty()) {
            tipoSolicitudIdList = new ArrayList<>();
            for (CmtTipoSolicitudMgl ts : tipoSolicitudList) {
                tipoSolicitudIdList.add(ts.getTipoSolicitudId());
            }
        }
        CmtSolicitudCmMglDaoImpl daoImpl = new CmtSolicitudCmMglDaoImpl();
        List<BigDecimal> estadSolicitudList = getEstadosPendientesId();
        return daoImpl.getCountByFiltroParaGestion(division, comunidad,
                segmento, tipoSolicitudIdList, estadSolicitudList, llamada);
    }

    /**
     * Obtiene las Solicitudes por filtro.Permite realizar el conteo de las
     * solicitudes por los parametros de comunidad, division, segmento y lista
     * de tipos de solicitudes.
     *
     * @author Johnnatan Ortiz
     * @param paginaSelected    pagina de la busqueda
     * @param maxResults        maximo numero de resultados
     * @param division          division de las solicitudes
     * @param comunidad         comunidad de las solicitudes
     * @param segmento          segmento de las solicitudes
     * @param llamada
     * @param tipoSolicitudList lista de los tipos de solicitudes a consultar
     * @param ordenMayorMenor
     * @return Compentencias asociadas a un SubEdifico
     * @throws ApplicationException
     */
    public List<CmtSolicitudCmMgl> findPendientesByFiltroParaGestionPaginacion(int paginaSelected,
            int maxResults,
            String division,
            String comunidad,
            CmtBasicaMgl segmento,
            List<CmtTipoSolicitudMgl> tipoSolicitudList,
            boolean llamada, boolean ordenMayorMenor) throws ApplicationException {

        int firstResult = 0;
        if (paginaSelected > 1) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        List<BigDecimal> tipoSolicitudIdList = null;
        if (tipoSolicitudList != null && !tipoSolicitudList.isEmpty()) {
            tipoSolicitudIdList = new ArrayList<>();
            for (CmtTipoSolicitudMgl ts : tipoSolicitudList) {
                tipoSolicitudIdList.add(ts.getTipoSolicitudId());
            }
        }
        CmtSolicitudCmMglDaoImpl daoImpl = new CmtSolicitudCmMglDaoImpl();
        List<BigDecimal> estadSolicitudList = getEstadosPendientesId();
        return daoImpl.findByFiltroParaGestionPaginacion(firstResult, maxResults,
                division, comunidad,
                segmento, tipoSolicitudIdList, estadSolicitudList, llamada, ordenMayorMenor);
    }

    /**
     * Cuenta las Solicitudes creadas el dia de la fecha actual. Permite
     * realizar el conteo de las solicitudes por tipos y creadas en la fecha
     * actual.
     *
     * @author Johnnatan Ortiz
     * @param tipoSolicitudList lista de los tipos de solicitudes a consultar
     * @return solicitudes
     * @throws ApplicationException
     */
    public int getCountSolicitudCreateDay(List<CmtTipoSolicitudMgl> tipoSolicitudList) throws ApplicationException {
        CmtSolicitudCmMglDaoImpl daoImpl = new CmtSolicitudCmMglDaoImpl();
        List<BigDecimal> tipoSolicitudIdList = null;
        if (tipoSolicitudList != null && !tipoSolicitudList.isEmpty()) {
            tipoSolicitudIdList = new ArrayList<>();
            for (CmtTipoSolicitudMgl ts : tipoSolicitudList) {
                tipoSolicitudIdList.add(ts.getTipoSolicitudId());
            }
        }
        return daoImpl.getCountSolicitudCreateDay(tipoSolicitudIdList);
    }

    /**
     * Cuenta las Solicitudes gestionadas el dia de la fecha actual. Permite
     * realizar el conteo de las solicitudes por tipos y gestionadas en la fecha
     * actual.
     *
     * @author Johnnatan Ortiz
     * @param tipoSolicitudList lista de los tipos de solicitudes a consultar
     * @return solicitudes
     * @throws ApplicationException
     */
    public int getCountSolicitudGestionadaDay(List<CmtTipoSolicitudMgl> tipoSolicitudList) throws ApplicationException {
        CmtSolicitudCmMglDaoImpl daoImpl = new CmtSolicitudCmMglDaoImpl();
        List<BigDecimal> tipoSolicitudIdList = null;
        if (tipoSolicitudList != null && !tipoSolicitudList.isEmpty()) {
            tipoSolicitudIdList = new ArrayList<>();
            for (CmtTipoSolicitudMgl ts : tipoSolicitudList) {
                tipoSolicitudIdList.add(ts.getTipoSolicitudId());
            }
        }
        return daoImpl.getCountSolicitudGestionadaDay(tipoSolicitudIdList);
    }

    /**
     * Cuenta las Solicitudes activas el dia de la fecha actual. Permite
     * realizar el conteo de las solicitudes por tipos y creadas en la fecha
     * actual.
     *
     * @author Johnnatan Ortiz
     * @param tipoSolicitudList lista de los tipos de solicitudes a consultar
     * @return solicitudes
     * @throws ApplicationException
     */
    public int getCountAllSolicitudActiveDay(List<CmtTipoSolicitudMgl> tipoSolicitudList) throws ApplicationException {
        CmtSolicitudCmMglDaoImpl daoImpl = new CmtSolicitudCmMglDaoImpl();
        List<BigDecimal> tipoSolicitudIdList = null;
        if (tipoSolicitudList != null && !tipoSolicitudList.isEmpty()) {
            tipoSolicitudIdList = new ArrayList<>();
            for (CmtTipoSolicitudMgl ts : tipoSolicitudList) {
                tipoSolicitudIdList.add(ts.getTipoSolicitudId());
            }
        }
        List<BigDecimal> estadSolicitudList = getEstadosPendientesId();
        return daoImpl.getCountAllSolicitudActiveDay(tipoSolicitudIdList, estadSolicitudList);
    }

    /**
     * Cuenta las Solicitudes por vencer en la fecha actual. Permite realizar el
     * conteo de las solicitudes por tipos y creadas en la fecha actual.
     *
     * @author Johnnatan Ortiz
     * @param tipoSolicitudList lista de los tipos de solicitudes a consultar
     * @return solicitudes
     * @throws ApplicationException
     */
    public int getCountAllSolicitudPorVencerDay(List<CmtTipoSolicitudMgl> tipoSolicitudList)
            throws ApplicationException {
        CmtSolicitudCmMglDaoImpl daoImpl = new CmtSolicitudCmMglDaoImpl();
        List<BigDecimal> tipoSolicitudIdList = null;
        if (tipoSolicitudList != null && !tipoSolicitudList.isEmpty()) {
            tipoSolicitudIdList = new ArrayList<>();
            for (CmtTipoSolicitudMgl ts : tipoSolicitudList) {
                tipoSolicitudIdList.add(ts.getTipoSolicitudId());
            }
        }
        List<BigDecimal> estadSolicitudList = getEstadosPendientesId();
        return daoImpl.getCountAllSolicitudPorVencerDay(tipoSolicitudIdList, estadSolicitudList);
    }

    /**
     * Bloquea o Desbloquea una solicitud. Permite realizar el bloqueo o
     * desbloqueo de una solicitud en el repositorio para la gestion.
     *
     * @author Johnnatan Ortiz
     * @param solicitudCm solicitud a bloquear o desbloquear
     * @param bloqueo     si es bloqueo o no
     * @param usuario     usuario que realiza el bloqueo-desbloque
     * @param perfil      perfil
     * @return solicitud
     * @throws ApplicationException
     */
    public CmtSolicitudCmMgl bloquearDesbloquearSolicitud(CmtSolicitudCmMgl solicitudCm,
            boolean bloqueo, String usuario, int perfil) throws ApplicationException {
        CmtSolicitudCmMglDaoImpl daoImpl = new CmtSolicitudCmMglDaoImpl();
        String disponiblidadGestion = (bloqueo ? "0" : "1");
        solicitudCm = findById(solicitudCm);
        solicitudCm.setDisponibilidadGestion(disponiblidadGestion);
        return daoImpl.updateCm(solicitudCm, usuario, perfil);
    }

    /**
     * Cuenta las Solicitudes vencidas en la fecha actual. Permite realizar el
     * conteo de las solicitudes por tipos y creadas en la fecha actual.
     *
     * @author Johnnatan Ortiz
     * @param tipoSolicitudList lista de los tipos de solicitudes a consultar
     * @return solicitudes
     * @throws ApplicationException
     */
    public int getCountAllSolicitudVencidasDay(List<CmtTipoSolicitudMgl> tipoSolicitudList)
            throws ApplicationException {
        CmtSolicitudCmMglDaoImpl daoImpl = new CmtSolicitudCmMglDaoImpl();
        List<BigDecimal> tipoSolicitudIdList = null;
        if (tipoSolicitudList != null && !tipoSolicitudList.isEmpty()) {
            tipoSolicitudIdList = new ArrayList<>();
            for (CmtTipoSolicitudMgl ts : tipoSolicitudList) {
                tipoSolicitudIdList.add(ts.getTipoSolicitudId());
            }
        }
        List<BigDecimal> estadSolicitudList = getEstadosPendientesId();
        return daoImpl.getCountAllSolicitudVencidasDay(tipoSolicitudIdList, estadSolicitudList);
    }

    /**
     * Gestiona una solicitud VT. Permite realizar la gestion de una solicitud
     * de VT.
     *
     * @author Johnnatan Ortiz
     * @param solicitudCm solicitud VT a gestionar
     * @param usuario     usuario gestionar
     * @param perfil      perfil gestionar
     * @return solicitud gestionada
     * @throws ApplicationException
     */
    public CmtSolicitudCmMgl gestionSolicitudVt(CmtSolicitudCmMgl solicitudCm, String usuario, int perfil)
            throws ApplicationException {
        CmtSolicitudCmMgl result;
        CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();
        if (solicitudCm.getResultGestion() != null
                && solicitudCm.getResultGestion().getBasicaId() != null) {
            solicitudCm.setResultGestion(basicaMglManager.findById(solicitudCm.getResultGestion()));
            if (solicitudCm.getResultGestion().getIdentificadorInternoApp().equals(
                    Constant.BASICA_RPT_GEST_POSPONER_VISITA_TECNICA)) {
                BigDecimal numLlamadas = solicitudCm.getNumeroIntentosLlamada();
                if (numLlamadas == null) {
                    numLlamadas = BigDecimal.ONE;
                } else {
                    numLlamadas.add(BigDecimal.ONE);
                }
                solicitudCm.setNumeroIntentosLlamada(numLlamadas);
                solicitudCm.setFechaGestion(null);
            } else {
                CmtBasicaMgl estadoFinalizadoSolicitud = new CmtBasicaMgl();
                estadoFinalizadoSolicitud.setBasicaId(basicaMglManager.findByCodigoInternoApp(
                        Constant.BASICA_ESTADO_SOLICITUD_FINALIZADO).getBasicaId());
                solicitudCm.setEstadoSolicitudObj(estadoFinalizadoSolicitud);
            }
        }
        if (solicitudCm.getResultGestion().getIdentificadorInternoApp().equals(
                Constant.BASICA_GEST_CREAR_VISITA_TECNICA)) {
            CmtOrdenTrabajoMgl ordenTrabajo = new CmtOrdenTrabajoMgl();
            ordenTrabajo.setCmObj(solicitudCm.getCuentaMatrizObj());
            ordenTrabajo.setSegmento(solicitudCm.getTipoSegmento());
            ordenTrabajo.setFechaCreacion(new Date());
            ordenTrabajo.setSolicitud(solicitudCm);
            CmtTipoOtMglManager tipoOrdenTrabajoManager = new CmtTipoOtMglManager();
            // subtipoOt
            CmtSubTipoOtVtTecMglManager subTipoOt = new CmtSubTipoOtVtTecMglManager();
            CmtSubtipoOrdenVtTecMgl subtipoOt = subTipoOt.findSubTipoxTecnologia(solicitudCm.getBasicaIdTecnologia());
            ordenTrabajo.setTipoOtObj(subtipoOt.getTipoFlujoOtObj());
            CmtTipoOtMgl tipoOtMgl = tipoOrdenTrabajoManager.findById(subtipoOt.getTipoFlujoOtObj().getIdTipoOt());
            // tipo de trabajo
            CmtBasicaMgl flujoOt = basicaMglManager.findById(tipoOtMgl.getBasicaIdTipoOt());
            ordenTrabajo.setBasicaIdTipoTrabajo(subtipoOt.getTipoFlujoOtObj().getBasicaIdTipoOt());
            ordenTrabajo.setBasicaIdTecnologia(solicitudCm.getBasicaIdTecnologia());
            // Asignamos el usario de la creacion
            UsuariosServicesManager usuariosManager = UsuariosServicesManager.getInstance();
            UsuariosServicesDTO u = usuariosManager.consultaInfoUserPorUsuario(usuario);

            if (u != null && u.getCedula() != null) {
                ordenTrabajo.setUsuarioCreacionId(new BigDecimal(u.getCedula()));
            }

            CmtBasicaMgl flujoTipoOtMgl = tipoOtMgl.getTipoFlujoOt();
            CmtEstadoxFlujoMglManager managerEstadoxFlujo = new CmtEstadoxFlujoMglManager();
            CmtEstadoxFlujoMgl estadoInicialFlujo = managerEstadoxFlujo.getEstadoInicialFlujo(flujoTipoOtMgl,
                    solicitudCm.getBasicaIdTecnologia());
            ordenTrabajo.setEstadoInternoObj(estadoInicialFlujo.getEstadoInternoObj());
            ordenTrabajo.setObservacion(solicitudCm.getRespuestaActual());
            ordenTrabajo.setFechaProgramacion(solicitudCm.getFechaProgramcionVt());

            CmtOrdenTrabajoMglManager ordenTrabajoManager = new CmtOrdenTrabajoMglManager();
            ordenTrabajoManager.crearOt(ordenTrabajo, usuario, perfil);
        }
        result = update(solicitudCm, usuario, perfil);
        return result;
    }

    public List<AuditoriaDto> construirAuditoria(CmtSolicitudCmMgl cmtSolicitudCmMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return new ArrayList<>();
    }

    private AddressEJBRemote lookupaddressEJBBean() {
        try {
            Context c = new InitialContext();
            return (AddressEJBRemote) c
                    .lookup("addressEJB#co.com.telmex.catastro.services.georeferencia.AddressEJBRemote");
        } catch (NamingException ne) {
            LOGGER.error(ne);
            throw new RuntimeException(ne);
        }
    }

    /**
     * Guarda la gestion de HHPP
     *
     * @author Antonio Gil
     * @param solicitudCm solicitud VT a gestionar
     * @param usuario     usuario gestionar
     * @param perfil      perfil gestionar
     * @return solicitud actualizada
     * @throws ApplicationException
     */
    public CmtSolicitudCmMgl gestionSolicitudHhpp(CmtSolicitudCmMgl solicitudCm, String usuario, int perfil)
            throws ApplicationException {
        CmtSolicitudCmMgl result;
        CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
        solicitudCm.setFechaGestionHhpp(new Date());
        solicitudCm.setTempGestion(solicitudCm.getTiempoGestionHHPPSolicitud());
        CmtBasicaMgl estadoFinalizadoSolicitud = new CmtBasicaMgl();
        estadoFinalizadoSolicitud.setBasicaId(cmtBasicaMglManager.findByCodigoInternoApp(
                Constant.BASICA_ESTADO_SOLICITUD_FINALIZADO).getBasicaId());
        solicitudCm.setEstadoSolicitudObj(estadoFinalizadoSolicitud);
        result = update(solicitudCm, usuario, perfil);
        return result;
    }

    /**
     * M&eacute;todo para contar las solicitudes de eliminacion existentes en la
     * base de datos
     *
     * @param cuentaMatrizId Identificador de la cuetna matriz asociada a la
     *                       solicitud
     * @return {@link Integer} cantidad de registros existentes en la base de
     *         datos
     * @throws ApplicationException
     */
    public Integer cantidadSolicitudesEliminacionPorCuentaMatriz(BigDecimal cuentaMatrizId)
            throws ApplicationException {
        CmtSolicitudCmMglDaoImpl daoImpl = new CmtSolicitudCmMglDaoImpl();
        return daoImpl.cantidadSolicitudesEliminacionPorCuentaMatriz(cuentaMatrizId);
    }

    /**
     * Obtiene reporte detallado de solicitudes
     *
     * @author Lenis Cardenas
     * @param tipoReporte
     * @param cmtTipoSolicitudMgl
     * @param fechaInicio
     * @param fechaFin
     * @param estado
     * @param usuario
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    public void getSolicitudesDetalladoSolicitudes(String tipoReporte, CmtTipoSolicitudMgl cmtTipoSolicitudMgl,
            Date fechaInicio, Date fechaFin, BigDecimal estado, String usuario) throws ApplicationException {
        Thread thread = new Thread(new CmtRunableReporteDetalladoManager(tipoReporte, cmtTipoSolicitudMgl, fechaInicio,
                fechaFin, estado, usuario));
        thread.start();
    }

    /**
     * Obtiene el conteo de registros del reporte detallado de solicitudes
     *
     * @author Lenis Cardenas
     * @param tipoReporte
     * @param cmtTipoSolicitudMgl
     * @param fechaInicio
     * @param fechaFin
     * @param estado
     * @param usuario
     * @param page
     * @param numeroRegistros
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    public int generarReporteDetalladoContar(String tipoReporte, CmtTipoSolicitudMgl cmtTipoSolicitudMgl,
            Date fechaInicio, Date fechaFin, BigDecimal estado, long page, int numeroRegistros, String usuario)
            throws ApplicationException {
        CmtSolicitudCmMglDaoImpl CmtSolicitudCmMglDaoImpl = new CmtSolicitudCmMglDaoImpl();
        return CmtSolicitudCmMglDaoImpl.getReporteDetalladoSolicitudesContar(tipoReporte, cmtTipoSolicitudMgl,
                fechaInicio, fechaFin, estado);
    }

    /**
     * Obtiene lista de reporte detallado de solicitudes
     *
     * @author Lenis Cardenas
     * @param tipoReporte
     * @param cmtTipoSolicitudMgl
     * @param fechaInicio
     * @param fechaFin
     * @param estado
     * @param inicio
     * @param usuario
     * @param fin
     * @param procesados
     * @param regProcesar
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     *
     */
    public List<CmtReporteDetalladoDto> getSolicitudesSearch(String tipoReporte,
            CmtTipoSolicitudMgl cmtTipoSolicitudMgl, Date fechaInicio, Date fechaFin,
            BigDecimal estado, int inicio, int fin, String usuario,
            int procesados, int regProcesar) throws ApplicationException {
        CmtSolicitudCmMglDaoImpl cmtSolicitudCmMglDaoImpl = new CmtSolicitudCmMglDaoImpl();
        return cmtSolicitudCmMglDaoImpl.getSolicitudesSearch(tipoReporte, cmtTipoSolicitudMgl, fechaInicio, fechaFin,
                estado, inicio, fin, procesados, regProcesar);
    }

    public CmtSolicitudCmMgl registrarHhpp(CmtSolicitudCmMgl solicitudCm,
            DetalleDireccionEntity direccionEntity, CmtDireccionMgl cmtDireccionMgl,
            boolean hasTorres, String usuario, AddressService addressServiceGestion,
            Map<CmtBasicaMgl, NodoMgl> datosGestion, int perfil, boolean sincronizaRR,
            AddressRequest addressRequest, AddressEJBRemote addressEJBRemote, boolean isFichaNodos)
            throws ApplicationException {

        CmtSubEdificioMglManager cmtSubEdificioMglManager = new CmtSubEdificioMglManager();
        CmtSubEdificioMgl cmtSubEdificioMglGral;
        CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();
        CmtBasicaMgl basicaMgl = new CmtBasicaMgl();
        basicaMgl.setBasicaId(basicaMglManager.findByCodigoInternoApp(
                Constant.BASICA_ESTADO_SIN_VISITA_TECNICA).getBasicaId());
        cmtSubEdificioMglGral = cmtSubEdificioMglManager
                .findSubEdificioGeneralByCuentaMatriz(solicitudCm.getCuentaMatrizObj());
        CmtDireccionMglManager cmtDireccionMglManager = new CmtDireccionMglManager();
        CmtTablasBasicasRRMglManager cmtTablasBasicasRRMglManager = new CmtTablasBasicasRRMglManager();
        CmtCuentaMatrizMglManager cuentaMatrizMglManager = new CmtCuentaMatrizMglManager();
        CmtTecnologiaSubMglManager cmtTecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
        CmtTecnologiaSubMgl cmtTecnologiaSubMgl;
        int control = 0;
        SubDireccionMgl subCampamento = null;
        SubDireccionMgl subSalaventa = null;
        boolean habilitarRR = false;
        ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
        ParametrosMgl parametroHabilitarRR = parametrosMglManager
                .findParametroMgl(co.com.claro.mgl.utils.Constant.HABILITAR_RR);
        if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
            habilitarRR = true;
        }
        try {
            for (Map.Entry<CmtBasicaMgl, NodoMgl> n : datosGestion.entrySet()) {
                sincronizaRR = n.getKey().isNodoTecnologia(); // valbuenayf ajuste checkbox
                if (habilitarRR) {
                    if (sincronizaRR) {
                        try {
                            boolean siCreaEdificioRr = cmtTablasBasicasRRMglManager.edificioAdd(
                                    n.getValue(),
                                    addressServiceGestion,
                                    solicitudCm.getCuentaMatrizObj(),
                                    usuario,
                                    isFichaNodos);
                            if (!siCreaEdificioRr) {
                                throw new ApplicationException(
                                        "Error creando edificio en registrar HHPP de cuenta matriz RR");
                            }
                        } catch (ApplicationException ae) {
                            try {
                                if (sincronizaRR) {
                                    if (!solicitudCm.getCuentaMatrizObj().getNumeroCuenta().equals(0)) {
                                        cmtTablasBasicasRRMglManager.edificioDelete(solicitudCm.getCuentaMatrizObj(),
                                                usuario);
                                    }
                                }
                            } catch (ApplicationException ae1) {
                                String msg = "Se produjo un error al momento de ejecutar el método '"
                                        + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ae1.getMessage();
                                LOGGER.error(msg, ae1);
                            }

                            List<CmtSubEdificioMgl> SubEEraseList = solicitudCm.getCuentaMatrizObj()
                                    .getListCmtSubEdificioMglActivos();
                            List<CmtDireccionMgl> direccionEraseList = solicitudCm.getCuentaMatrizObj()
                                    .getDireccionesList();
                            if (SubEEraseList != null) {
                                for (CmtSubEdificioMgl edificioMgl : SubEEraseList) {
                                    cmtSubEdificioMglManager.deleteSinRr(edificioMgl, usuario, perfil);
                                    CmtTecnologiaSubMgl cmtTecnologiaSubMglBorra;
                                    cmtTecnologiaSubMglBorra = cmtTecnologiaSubMglManager
                                            .findBySubEdificioTecnologia(edificioMgl, n.getKey());
                                    if (cmtTecnologiaSubMglBorra != null
                                            && cmtTecnologiaSubMglBorra.getTecnoSubedificioId() != null) { // valbuenayf
                                                                                                           // ajuste
                                                                                                           // checkbox
                                        cmtTecnologiaSubMglManager.deleteSubEdificioTecnologia(cmtTecnologiaSubMglBorra,
                                                usuario, perfil);
                                    }
                                }
                            }
                            if (direccionEraseList != null) {
                                for (CmtDireccionMgl direccionE : direccionEraseList) {
                                    cmtDireccionMglManager.deleteSinRr(direccionE, usuario, perfil);
                                }
                            }

                            if (solicitudCm.getCuentaMatrizObj() != null) {
                                cuentaMatrizMglManager.deleteSinRr(solicitudCm.getCuentaMatrizObj(), usuario, perfil);
                            }
                            throw new ApplicationException(ae);
                        }
                        cuentaMatrizMglManager.updateSinRr(solicitudCm.getCuentaMatrizObj(), usuario, perfil);
                        List<CmtSubEdificioMgl> SubEUpdateList = solicitudCm.getCuentaMatrizObj()
                                .getListCmtSubEdificioMglActivos();
                        if (SubEUpdateList != null) {
                            for (CmtSubEdificioMgl edificioMgl : SubEUpdateList) {
                                cmtSubEdificioMglManager.updateSinRr(edificioMgl, usuario, perfil);
                            }
                        }
                    }
                }
                if (solicitudCm.getOrigenSolicitud().getIdentificadorInternoApp().equals(
                        Constant.BASICA_ORIGEN_DE_SOLICTUD_CONSTRUCTORA)) {
                    direccionEntity.setCptiponivel5(null);
                    direccionEntity.setCpvalornivel5(null);
                    direccionEntity.setCptiponivel6(null);
                    direccionEntity.setCpvalornivel6(null);
                    NodoMgl nodoSolicitud = n.getValue();
                    String carpeta;
                    if (nodoSolicitud.getNodTipo().getIdentificadorInternoApp().equals(Constant.HFC_BID)
                            || nodoSolicitud.getNodTipo().getIdentificadorInternoApp().equals(Constant.DTH)) {
                        carpeta = "VERIFICACION_CASAS";
                    } else {
                        carpeta = "VERIFICACION_CASAS";
                    }
                    String tmpEstrato;
                    if (cmtSubEdificioMglGral.getEstrato() != null
                            && cmtSubEdificioMglGral.getEstrato().getNombreBasica().contains("ESTRATO")) {
                        tmpEstrato = cmtSubEdificioMglGral.getEstrato().getNombreBasica().replace("ESTRATO", "").trim();
                    } else {
                        tmpEstrato = cmtSubEdificioMglGral.getEstrato().getNombreBasica();
                    }
                    if (control == 0) {
                        DrDireccion drDireccion_complemento_campa = solicitudCm.getListCmtDireccionesMgl().get(0)
                                .getCamposDrDireccion();
                        drDireccion_complemento_campa.setCpTipoNivel5("OTROS");
                        drDireccion_complemento_campa.setCpValorNivel5("CAMPAMENTO");
                        drDireccion_complemento_campa.setCpTipoNivel1("TORRE");
                        drDireccion_complemento_campa.setCpValorNivel1("1");
                        drDireccion_complemento_campa
                                .setBarrio(solicitudCm.getListCmtDireccionesMgl().get(0).getBarrio());
                        ResponseMessage responseMessage = addressEJBRemote.createAddress(addressRequest, usuario,
                                "CM-MGL", "true", drDireccion_complemento_campa);
                        subCampamento = new SubDireccionMgl();
                        // se asigna al hhpp los id recien creados en el createAddress correspondientes
                        // a la dirrección creada.
                        if (responseMessage != null && responseMessage.getNuevaDireccionDetallada() != null) {
                            subCampamento = responseMessage.getNuevaDireccionDetallada().getSubDireccion();
                            direccionEntity.setIdDirCatastro("d" + responseMessage.getNuevaDireccionDetallada()
                                    .getDireccion().getDirId().toString());
                        }
                    }
                    DireccionRRManager direccionRRManagerHhpp = new DireccionRRManager(direccionEntity);
                    if (hasTorres) {
                        direccionRRManagerHhpp.getDireccion()
                                .setCalle(direccionRRManagerHhpp.getDireccion().getCalle()); // + " TORRE 1"
                    }

                    direccionRRManagerHhpp.getDireccion().setNumeroApartamento("CAMPAMENTO");
                    // TODO: Enviar valor del id Centro Poblado de creación del Hhpp
                    direccionRRManagerHhpp.registrarHHPP_CM(
                            n.getValue().getNodCodigo(),
                            n.getValue().getNodCodigo(),
                            usuario,
                            carpeta,
                            "MGL_CM",
                            tmpEstrato,
                            false,
                            solicitudCm.getSolicitudCmId().toString(),
                            solicitudCm.getOrigenSolicitud().getNombreBasica(),
                            n.getValue().getComId(),
                            "CREACI0N CM",
                            true,
                            usuario,
                            solicitudCm.getResultGestion().getNombreBasica(),
                            solicitudCm.getUsuarioGestionId().toString(),
                            solicitudCm.getAsesor(), solicitudCm.getTelefonoAsesor(),
                            addressServiceGestion.getAlternateaddress(), n.getValue().getGpoId(), sincronizaRR);
                    BigDecimal idCampmentoHhpp = new BigDecimal(direccionRRManagerHhpp.getDireccion().getIdHhpp());
                    if (control == 0) {
                        DrDireccion drDireccion_complemento_salVenta = solicitudCm.getListCmtDireccionesMgl().get(0)
                                .getCamposDrDireccion();
                        drDireccion_complemento_salVenta.setCpTipoNivel5("OTROS");
                        drDireccion_complemento_salVenta.setCpValorNivel5("SALAVENTAS");//
                        drDireccion_complemento_salVenta.setCpTipoNivel1("TORRE");
                        drDireccion_complemento_salVenta.setCpValorNivel1("1");
                        ResponseMessage responseMessage = addressEJBRemote.createAddress(addressRequest, usuario,
                                "CM-MGL", "true", drDireccion_complemento_salVenta);
                        subSalaventa = new SubDireccionMgl();
                        if (responseMessage != null && responseMessage.getNuevaDireccionDetallada() != null) {
                            subSalaventa = responseMessage.getNuevaDireccionDetallada().getSubDireccion();
                            direccionEntity.setIdDirCatastro("d" + responseMessage.getNuevaDireccionDetallada()
                                    .getDireccion().getDirId().toString());
                        }
                    }
                    direccionRRManagerHhpp.getDireccion().setNumeroApartamento("SALAVENTAS");
                    direccionRRManagerHhpp.registrarHHPP_CM(
                            n.getValue().getNodCodigo(),
                            n.getValue().getNodCodigo(),
                            usuario,
                            carpeta,
                            "MGL_CM",
                            tmpEstrato,
                            false,
                            solicitudCm.getSolicitudCmId().toString(),
                            solicitudCm.getOrigenSolicitud().getNombreBasica(),
                            n.getValue().getComId(),
                            "CREACI0N CM",
                            true,
                            usuario,
                            solicitudCm.getResultGestion().getNombreBasica(),
                            solicitudCm.getUsuarioGestionId().toString(),
                            solicitudCm.getAsesor(), solicitudCm.getTelefonoAsesor(),
                            addressServiceGestion.getAlternateaddress(), n.getValue().getGpoId(), sincronizaRR);
                    BigDecimal idSalaventaHhpp = new BigDecimal(direccionRRManagerHhpp.getDireccion().getIdHhpp());
                    HhppMglManager hhppMglManager = new HhppMglManager();
                    HhppMgl hhppMglCampamento = hhppMglManager.findById(idCampmentoHhpp);
                    HhppMgl hhppMglSalaVenta = hhppMglManager.findById(idSalaventaHhpp);
                    hhppMglCampamento.setHhppSubEdificioObj(cmtSubEdificioMglGral);
                    hhppMglCampamento.setSubDireccionObj(subCampamento);
                    hhppMglSalaVenta.setHhppSubEdificioObj(cmtSubEdificioMglGral);
                    hhppMglSalaVenta.setSubDireccionObj(subSalaventa);
                    cmtTecnologiaSubMgl = cmtTecnologiaSubMglManager.findBySubEdificioTecnologia(cmtSubEdificioMglGral,
                            n.getKey());
                    if (cmtTecnologiaSubMgl != null && cmtTecnologiaSubMgl.getTecnoSubedificioId() != null) {
                        hhppMglCampamento.setCmtTecnologiaSubId(cmtTecnologiaSubMgl);
                        hhppMglSalaVenta.setCmtTecnologiaSubId(cmtTecnologiaSubMgl);
                    }

                    try {
                        hhppMglManager.update(hhppMglSalaVenta);
                        hhppMglManager.update(hhppMglCampamento);
                    } catch (ApplicationException e) {
                        String msg = "Se produjo un error al momento de ejecutar el método '"
                                + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                        LOGGER.error(msg);
                        hhppMglManager.update(hhppMglSalaVenta);
                        hhppMglManager.update(hhppMglCampamento);
                    }
                    control++;
                }
            }
            return solicitudCm;
        } catch (ApplicationException | ExceptionDB ex) {
            try {
                if (sincronizaRR) {
                    if (!solicitudCm.getCuentaMatrizObj().getNumeroCuenta().equals(0)) {
                        cmtTablasBasicasRRMglManager.edificioDelete(solicitudCm.getCuentaMatrizObj(), usuario);
                    }
                }
            } catch (ApplicationException ae1) {
                String msg = "Se produjo un error al momento de ejecutar el método '"
                        + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                LOGGER.error(msg, ex);
            }
            List<CmtSubEdificioMgl> SubEEraseList = solicitudCm.getCuentaMatrizObj().getListCmtSubEdificioMglActivos();
            List<CmtDireccionMgl> direccionEraseList = solicitudCm.getCuentaMatrizObj().getDireccionesList();
            if (SubEEraseList != null) {
                for (Map.Entry<CmtBasicaMgl, NodoMgl> n : datosGestion.entrySet()) {
                    for (CmtSubEdificioMgl edificioMgl : SubEEraseList) {
                        cmtSubEdificioMglManager.deleteSinRr(edificioMgl, usuario, perfil);
                        CmtTecnologiaSubMgl cmtTecnologiaSubMglBorra;
                        cmtTecnologiaSubMglBorra = cmtTecnologiaSubMglManager.findBySubEdificioTecnologia(edificioMgl,
                                n.getKey());
                        if (cmtTecnologiaSubMglBorra != null
                                && cmtTecnologiaSubMglBorra.getTecnoSubedificioId() != null) {
                            cmtTecnologiaSubMglManager.deleteSubEdificioTecnologia(cmtTecnologiaSubMglBorra, usuario,
                                    perfil);
                        }
                    }
                }
            }
            if (direccionEraseList != null) {
                List<HhppMgl> lstHhppMgl;
                HhppMglManager hhppMglManager = new HhppMglManager();
                for (CmtDireccionMgl direccionE : direccionEraseList) {
                    cmtDireccionMglManager.deleteSinRr(direccionE, usuario, perfil);
                    lstHhppMgl = hhppMglManager.findHhppDireccion(direccionE.getDireccionId());
                    if (lstHhppMgl.size() > 0) {
                        for (HhppMgl hhppMgl : lstHhppMgl) {
                            hhppMglManager.deleteCm(hhppMgl, usuario, perfil);
                        }
                    }

                }
            }

            if (solicitudCm.getCuentaMatrizObj() != null) {
                cuentaMatrizMglManager.deleteSinRr(solicitudCm.getCuentaMatrizObj(), usuario, perfil);
            }

            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg, ex);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }

    public SubDireccionMgl guardarSubdireccion(DireccionMgl direccionMgl,
            String sufijo, String usuario, String nombreFuncionalidad) {
        SubDireccionMglManager subDireccionManager;
        SubDireccionMgl subDir = new SubDireccionMgl();
        AccessData adb = null;
        try {
            String idDireccion = direccionMgl.getDirId().toString();
            String formatoIgac = direccionMgl.getDirFormatoIgac() + " " + sufijo;
            String servinformacion = direccionMgl.getDirServinformacion() + sufijo;
            String comSocioEconomico = null;
            if (direccionMgl.getDirNivelSocioecono() != null) {
                comSocioEconomico = direccionMgl.getDirNivelSocioecono().toString();
            }
            BigDecimal estrato = new BigDecimal(-1);
            String nodoUno = direccionMgl.getDirNodouno();
            String nodoDos = direccionMgl.getDirNododos();
            String nodoTres = direccionMgl.getDirNodotres();
            String nodoDth = direccionMgl.getDirNodoDth();
            String nodoMovil = direccionMgl.getDirNodoMovil();
            String nodoFtth = direccionMgl.getDirNodoFtth();
            String nodoWifi = direccionMgl.getDirNodoWifi();
            if (direccionMgl.getDirEstrato() != null) {
                estrato = direccionMgl.getDirEstrato();
            }
            BigDecimal nivSocioEco = null;
            if (direccionMgl.getDirNivelSocioecono() != null) {
                nivSocioEco = direccionMgl.getDirNivelSocioecono();
            }
            BigDecimal actividadEco = null;
            if (direccionMgl.getDirActividadEcono() != null && !direccionMgl.getDirActividadEcono().trim().isEmpty()
                    && !direccionMgl.getDirActividadEcono().equals("NA")
                    && direccionMgl.getDirActividadEcono().matches("^[0-9]")) {
                actividadEco = new BigDecimal(direccionMgl.getDirActividadEcono());
            }
            String user = usuario;
            subDir.setDirId(new BigDecimal(idDireccion));
            subDir.setSdiFormatoIgac(formatoIgac);
            subDir.setSdiServinformacion(servinformacion);
            subDir.setSdiEstrato(estrato);
            subDir.setSdiNivelSocioecono(nivSocioEco);
            subDir.setSdiActividadEcono(actividadEco);
            subDir.setUsuarioCreacion(user);
            subDir.setSdiComentarioSocioeconomico(comSocioEconomico);
            subDir.setSdiNodouno(nodoUno);
            subDir.setSdiNododos(nodoDos);
            subDir.setSdiNodotres(nodoTres);
            subDir.setSdiNodoDth(nodoDth);
            subDir.setSdiNodoMovil(nodoMovil);
            subDir.setSdiNodoFtth(nodoFtth);
            subDir.setSdiNodoWifi(nodoWifi);
            subDireccionManager = new SubDireccionMglManager();
            subDir = subDireccionManager.create(subDir);
            if (subDir.getSdiId() != null) {
                ConsultaEspecificaManager manager = new ConsultaEspecificaManager();
                AuditoriaEJB audi = new AuditoriaEJB();
                SubDireccion subDirAuditoria = manager.querySubAddressOnRepositoryById(subDir.getSdiId());
                audi.auditar(nombreFuncionalidad, co.com.telmex.catastro.services.util.Constant.SUB_DIRECCION,
                        subDirAuditoria.getSdiUsuarioCreacion(), co.com.telmex.catastro.services.util.Constant.INSERT,
                        subDirAuditoria.auditoria(), adb);
            }
            DireccionUtil.closeConnection(adb);
        } catch (ApplicationException e) {
            LOGGER.error("Error al momento de guardar la subdirección. EX000: " + e.getMessage(), e);
            DireccionUtil.closeConnection(adb);
        }
        return subDir;
    }

    public boolean validarExisteSolCMTipo(CmtCuentaMatrizMgl cmtCuentaMatrizMgl, CmtSolicitudCmMgl t)
            throws ApplicationException {
        CmtSolicitudCmMglDaoImpl CmtSolicitudCmMglDaoImpl = new CmtSolicitudCmMglDaoImpl();
        return CmtSolicitudCmMglDaoImpl.findByIdCMTipoSol(cmtCuentaMatrizMgl, t.getOrigenSolicitud().getBasicaId());
    }

    /**
     * Consulta solicitud por cuenta matriz y tipo de solicitud
     *
     * @author Victor Bocanegra
     * @param cuentaMatrizId      cuenta matriz a consultar
     * @param cmtTipoSolicitudMgl tipo de solicitud a consultar
     * @param estadoSol
     * @return CmtSolicitudCmMgl
     * @throws ApplicationException
     */
    public CmtSolicitudCmMgl findBySolCMTipoSol(CmtCuentaMatrizMgl cuentaMatrizId,
            CmtTipoSolicitudMgl cmtTipoSolicitudMgl, CmtBasicaMgl estadoSol) throws ApplicationException {
        CmtSolicitudCmMglDaoImpl CmtSolicitudCmMglDaoImpl = new CmtSolicitudCmMglDaoImpl();
        return CmtSolicitudCmMglDaoImpl.findBySolCMTipoSol(cuentaMatrizId, cmtTipoSolicitudMgl, estadoSol);
    }

    public void esperar(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg, ex);
        }
    }

    /**
     * Consulta listado de solicitudes agrupadas
     *
     * @author Victor Bocanegra
     * @param tipoReporte         tipo de reporte
     * @param cmtTipoSolicitudMgl tipo de solicitud a consultar
     * @param fechaInicio         fecha inicio rango
     * @param fechaFin            fecha fin rango
     * @param estado              de la solicitud.
     * @param paginaSelected
     * @param maxResults
     * @return List<CmtReporteGeneralDto>
     * @throws ApplicationException
     */
    public FiltroReporteSolicitudCMDto getReporteGeneralSolicitudesSearchFinalCon(String tipoReporte,
            BigDecimal cmtTipoSolicitudMgl, Date fechaInicio, Date fechaFin, BigDecimal estado,
            int paginaSelected, int maxResults)
            throws ApplicationException {

        int firstResult = 0;
        if (paginaSelected > 1) {
            firstResult = (maxResults * (paginaSelected - 1));
        }

        CmtSolicitudCmMglDaoImpl daoImpl = new CmtSolicitudCmMglDaoImpl();
        return daoImpl.getReporteGeneralSolicitudesSearchFinalCon(tipoReporte, cmtTipoSolicitudMgl, fechaInicio,
                fechaFin, estado, firstResult, maxResults);
    }

    public CmtSolicitudCmMgl updateCm(CmtSolicitudCmMgl solicitudCm, String usuario, int perfil)
            throws ApplicationException {

        CmtSolicitudCmMglDaoImpl daoImpl = new CmtSolicitudCmMglDaoImpl();
        return daoImpl.updateCm(solicitudCm, usuario, perfil);
    }

    /**
     * Obtiene una Solicitud por rol
     *
     * @author Victor Bocanegra
     * @param tipoSolicitudList lista de los tipos de solicitudes a consultar
     * @param solicitudId       id de la solicitud a consultar
     * @return CmtSolicitudCmMgl
     * @throws ApplicationException
     */
    public CmtSolicitudCmMgl findBySolicitudPorPermisos(
            List<CmtTipoSolicitudMgl> tipoSolicitudList,
            BigDecimal solicitudId) throws ApplicationException {

        List<BigDecimal> tipoSolicitudIdList = null;
        if (tipoSolicitudList != null && !tipoSolicitudList.isEmpty()) {
            tipoSolicitudIdList = new ArrayList<>();
            for (CmtTipoSolicitudMgl ts : tipoSolicitudList) {
                tipoSolicitudIdList.add(ts.getTipoSolicitudId());
            }
        }
        CmtSolicitudCmMglDaoImpl daoImpl = new CmtSolicitudCmMglDaoImpl();
        List<BigDecimal> estadSolicitudList = getEstadosPendientesId();
        return daoImpl.findBySolicitudPorPermisos(tipoSolicitudIdList, estadSolicitudList, solicitudId);

    }

    public void estadoInicialCM(CmtSolicitudCmMgl cmtSolicitudCmMgl, CmtSubEdificioMgl cmtSubEdificioMgl) {
        try {
            CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();
            if (cmtSolicitudCmMgl.getBasicaIdTecnologia().getBasicaId()
                    .compareTo(basicaMglManager.findByCodigoInternoApp(
                            Constant.DTH).getBasicaId()) == 0) {
                CmtBasicaMgl basicaMgl = new CmtBasicaMgl();
                CmtTipoBasicaMgl tipoBasica = new CmtTipoBasicaMgl();
                tipoBasica.setTipoBasicaId(new BigDecimal(20));
                basicaMgl.setTipoBasicaObj(tipoBasica);
                basicaMgl = basicaMglManager.findByCodigoInternoApp(
                        Constant.ESTADO_DTH);
                cmtSubEdificioMgl.setEstadoSubEdificioObj(basicaMgl);
            } else if (cmtSolicitudCmMgl.getBasicaIdTecnologia().getBasicaId()
                    .compareTo(basicaMglManager.findByCodigoInternoApp(
                            Constant.HFC_BID).getBasicaId()) == 0) {
                CmtBasicaMgl basicaMgl = new CmtBasicaMgl();
                CmtTipoBasicaMgl tipoBasica = new CmtTipoBasicaMgl();
                tipoBasica.setTipoBasicaId(new BigDecimal(20));
                basicaMgl.setTipoBasicaObj(tipoBasica);
                basicaMgl = basicaMglManager.findByCodigoInternoApp(
                        Constant.ESTADO_HFC_BID);
                cmtSubEdificioMgl.setEstadoSubEdificioObj(basicaMgl);

            } else if (cmtSolicitudCmMgl.getBasicaIdTecnologia().getBasicaId()
                    .compareTo(basicaMglManager.findByCodigoInternoApp(
                            Constant.HFC_UNI).getBasicaId()) == 0) {
                CmtBasicaMgl basicaMgl = new CmtBasicaMgl();
                CmtTipoBasicaMgl tipoBasica = new CmtTipoBasicaMgl();
                tipoBasica.setTipoBasicaId(new BigDecimal(20));
                basicaMgl.setTipoBasicaObj(tipoBasica);
                basicaMgl = basicaMglManager.findByCodigoInternoApp(
                        Constant.ESTADO_HFC_UNI);
                cmtSubEdificioMgl.setEstadoSubEdificioObj(basicaMgl);
            } else if (cmtSolicitudCmMgl.getBasicaIdTecnologia().getBasicaId()
                    .compareTo(basicaMglManager.findByCodigoInternoApp(
                            Constant.FIBRA_FTTTH).getBasicaId()) == 0) {
                CmtBasicaMgl basicaMgl = new CmtBasicaMgl();
                CmtTipoBasicaMgl tipoBasica = new CmtTipoBasicaMgl();
                tipoBasica.setTipoBasicaId(new BigDecimal(20));
                basicaMgl.setTipoBasicaObj(tipoBasica);
                basicaMgl = basicaMglManager.findByCodigoInternoApp(
                        Constant.ESTADO_FIBRA_FTTTH);
                cmtSubEdificioMgl.setEstadoSubEdificioObj(basicaMgl);
            } else if (cmtSolicitudCmMgl.getBasicaIdTecnologia().getBasicaId()
                    .compareTo(basicaMglManager.findByCodigoInternoApp(
                            Constant.FIBRA_OP_GPON).getBasicaId()) == 0) {
                CmtBasicaMgl basicaMgl = new CmtBasicaMgl();
                CmtTipoBasicaMgl tipoBasica = new CmtTipoBasicaMgl();
                tipoBasica.setTipoBasicaId(new BigDecimal(20));
                basicaMgl.setTipoBasicaObj(tipoBasica);
                basicaMgl = basicaMglManager.findByCodigoInternoApp(
                        Constant.ESTADO_FIBRA_OP_GPON);
                cmtSubEdificioMgl.setEstadoSubEdificioObj(basicaMgl);
            } else if (cmtSolicitudCmMgl.getBasicaIdTecnologia().getBasicaId()
                    .compareTo(basicaMglManager.findByCodigoInternoApp(
                            Constant.FIBRA_OP_UNI).getBasicaId()) == 0) {
                // ESTADO_FIBRA_OP_GPON
                CmtBasicaMgl basicaMgl = new CmtBasicaMgl();
                CmtTipoBasicaMgl tipoBasica = new CmtTipoBasicaMgl();
                tipoBasica.setTipoBasicaId(new BigDecimal(20));
                basicaMgl.setTipoBasicaObj(tipoBasica);
                basicaMgl = basicaMglManager.findByCodigoInternoApp(
                        Constant.ESTADO_FIBRA_OP_GPON);
                cmtSubEdificioMgl.setEstadoSubEdificioObj(basicaMgl);
            } else {
                CmtBasicaMgl basicaMgl = new CmtBasicaMgl();
                basicaMgl.setBasicaId(basicaMglManager.findByCodigoInternoApp(
                        Constant.BASICA_ESTADO_SIN_VISITA_TECNICA).getBasicaId());
                basicaMgl = basicaMglManager.findById(basicaMgl);
                cmtSubEdificioMgl.setEstadoSubEdificioObj(basicaMgl);
            }
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg, ex);
        }
    }

    public CmtBasicaMgl actualizarEstadosCMRR(CmtCuentaMatrizMgl cmtCuentaMatrizMgl, String usuario, int perfil) {
        CmtBasicaMgl estadoCombinado = null;
        try {
            CmtUtilidadesCM utilidadesCM = new CmtUtilidadesCM();
            CmtTecnologiaSubMglManager tecSubManager = new CmtTecnologiaSubMglManager();
            CmtSubEdificioMglManager subEdificioMglManager = new CmtSubEdificioMglManager();
            CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();
            List<CmtTecnologiaSubMgl> listaBasicaTec = tecSubManager.findTecnoSubBySubEdi(cmtCuentaMatrizMgl.getSubEdificioGeneral());
            CmtBasicaMgl basicaMglEstMult = new CmtBasicaMgl();
            basicaMglEstMult.setBasicaId(basicaMglManager.findByCodigoInternoApp(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO).getBasicaId());
            basicaMglEstMult = basicaMglManager.findById(basicaMglEstMult);

            // cargar el id de los estados de las tecnologias e
            for (CmtSubEdificioMgl cmtSubEdif : cmtCuentaMatrizMgl.getListCmtSubEdificioMglActivos()) {
                if (basicaMglEstMult.getIdentificadorInternoApp() != null
                        && !basicaMglEstMult.getIdentificadorInternoApp().equals(
                                cmtSubEdif.getEstadoSubEdificioObj().getIdentificadorInternoApp())) {
                    if (cmtSubEdif.getEstadoRegistro() == 1) {
                        CmtSubEdificioMgl cmtSubEdificioMgl = cmtSubEdif;
                        List<CmtTecnologiaSubMgl> lstCmtTecnologiaSubMgls = tecSubManager
                                .findTecnoSubBySubEdi(cmtSubEdificioMgl);
                        List<CmtBasicaMgl> estadosTecnologias = null;
                        List<BigDecimal> estados = new ArrayList<>();
                        if (lstCmtTecnologiaSubMgls != null && !lstCmtTecnologiaSubMgls.isEmpty()) {
                            estadosTecnologias = new ArrayList<>();
                            for (CmtTecnologiaSubMgl tecnologiaSubMgl : lstCmtTecnologiaSubMgls) {
                                if (tecnologiaSubMgl.getBasicaIdEstadosTec() != null) {
                                    estadosTecnologias.add(tecnologiaSubMgl.getBasicaIdEstadosTec());
                                }
                            }
                        }
                        // cargar el id de los estados de las tecnologias e
                        for (CmtTecnologiaSubMgl tecnologiaSubMgl : lstCmtTecnologiaSubMgls) {
                            if (tecnologiaSubMgl.getBasicaIdEstadosTec() != null) {
                                estados.add(tecnologiaSubMgl.getBasicaIdEstadosTec().getBasicaId());
                            }
                        }
                        // // quitar ids repetidos
                        List<BigDecimal> nombreSinDuplicados = estados.stream().distinct().collect(Collectors.toList());
                        if (estadosTecnologias != null && !estadosTecnologias.isEmpty()) {
                            estadoCombinado = utilidadesCM.obtenerEstadoCmRrByReglaEstado(lstCmtTecnologiaSubMgls);
                            if (estadoCombinado != null) {
                                cmtSubEdificioMgl.setEstadoSubEdificioObj(estadoCombinado);
                                subEdificioMglManager.update(cmtSubEdificioMgl, usuario, perfil, false, false);
                            }
                        }
                    }
                }
            }
        } catch (ApplicationException ex) {
            String msgError = "Ocurrió un error en " + ClassUtils.getCurrentMethodName(this.getClass())
                    + ex.getMessage();
            LOGGER.error(msgError, ex);
        }
        return estadoCombinado;
    }

    public List<CmtSolicitudCmMgl> findSolicitudCMHhppEnCurso(BigDecimal basicaIdTecnologia, BigDecimal subEdificioId,
            String cpTipoNivel5, String cpTipoNivel6, String cpValorNivel5, String cpValorNivel6)
            throws ApplicationException {
        CmtSolicitudCmMglDaoImpl CmtSolicitudCmMglDaoImpl = new CmtSolicitudCmMglDaoImpl();
        return CmtSolicitudCmMglDaoImpl.findSolicitudCMHhppEnCurso(basicaIdTecnologia, subEdificioId, cpTipoNivel5,
                cpTipoNivel6, cpValorNivel5, cpValorNivel6);

    }
}
