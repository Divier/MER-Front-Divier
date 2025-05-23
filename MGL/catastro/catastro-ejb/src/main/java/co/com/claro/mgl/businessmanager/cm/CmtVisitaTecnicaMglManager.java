package co.com.claro.mgl.businessmanager.cm;

import co.claro.wcc.client.gestor.ClientGestorDocumental;
import co.claro.wcc.schema.schemadocument.DocumentType;
import co.claro.wcc.schema.schemadocument.FieldType;
import co.claro.wcc.schema.schemadocument.FileType;
import co.claro.wcc.schema.schemaoperations.ActionStatusEnumType;
import co.claro.wcc.schema.schemaoperations.FileRequestType;
import co.claro.wcc.schema.schemaoperations.RequestType;
import co.claro.wcc.schema.schemaoperations.ResponseType;
import co.claro.wcc.services.search.searchcuentasmatrices.SearchCuentasMatricesFault;
import co.claro.wcc.services.upload.uploadcuentasmatrices.UploadCuentasMatricesFault;
import co.com.claro.mgl.businessmanager.address.DireccionMglManager;
import co.com.claro.mgl.businessmanager.address.HhppMglManager;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.businessmanager.address.SubDireccionMglManager;
import co.com.claro.mgl.dao.impl.cm.CmtVisitaTecnicaMglDaoImpl;
import co.com.claro.mgl.dtos.CmtSubEdificioDto;
import co.com.claro.mgl.dtos.ResponseCreateHhppDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtArchivosVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCompaniaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoxFlujoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtHhppVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtInfoTecnicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificiosVt;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtVisitaTecnicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.services.georeferencia.AddressEJB;
import co.com.telmex.catastro.utilws.ResponseMessage;
import com.jlcg.db.exept.ExceptionDB;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.model.file.UploadedFile;

/**
 * Contiene la logiga de negocio relacionada con la clase CmtVisitaTecnicaMgl.
 *
 * @author alejandro.martine.ext@claro.com.co
 *
 * @versión 1.0
 */
public class CmtVisitaTecnicaMglManager {

    private static final Logger LOGGER = LogManager.getLogger(CmtVisitaTecnicaMglManager.class);
    private CmtSubEdificioMglManager cmtSubEdificioMglManager = new CmtSubEdificioMglManager();
    private DireccionMglManager direccionMglManager = new DireccionMglManager();
    private SubDireccionMglManager subDireccionMglManager = new SubDireccionMglManager();
    private CmtHhppVtMglManager cmtHhppVtMglManager = new CmtHhppVtMglManager();
    private CmtDireccionMglManager cmtDireccionMglManager = new CmtDireccionMglManager();
    private String HABILITAR_RR;
    private ParametrosMglManager parametrosMglManager = new ParametrosMglManager();

    public CmtVisitaTecnicaMglManager() {
        try {
            HABILITAR_RR = parametrosMglManager.findByAcronimo(
                    Constant.HABILITAR_RR)
                    .iterator().next().getParValor();
        } catch (ApplicationException e) {
            LOGGER.error("Error al llamar parametro " + e.getMessage());
        }
    }

    public List<CmtVisitaTecnicaMgl> findAll() throws ApplicationException {
        CmtVisitaTecnicaMglDaoImpl daoImpl = new CmtVisitaTecnicaMglDaoImpl();
        return daoImpl.findAll();
    }

    public List<CmtVisitaTecnicaMgl> findByIdOt(CmtOrdenTrabajoMgl idOt) throws ApplicationException {
        CmtVisitaTecnicaMglDaoImpl daoImpl = new CmtVisitaTecnicaMglDaoImpl();
        return daoImpl.findByIdOt(idOt);
    }

    public List<CmtVisitaTecnicaMgl> findByIdVt(BigDecimal idVt) throws ApplicationException {
        CmtVisitaTecnicaMglDaoImpl daoImpl = new CmtVisitaTecnicaMglDaoImpl();
        return daoImpl.findByIdVt(idVt);
    }

    /**
     * Inserta los registros en la tabla
     *
     * @param t
     * @param usuario
     * @param perfil
     * @return Notificacion de insercion del registro
     * @throws ApplicationException
     */
    public CmtVisitaTecnicaMgl createCm(CmtVisitaTecnicaMgl cmtVisitaTecnicaMgl, String usuario, int perfil)
            throws ApplicationException {
        try {

            CmtVisitaTecnicaMglDaoImpl daoImpl = new CmtVisitaTecnicaMglDaoImpl();
            if (!(cmtVisitaTecnicaMgl.getAdministracionObj() != null
                    && cmtVisitaTecnicaMgl.getAdministracionObj().getCompaniaId() != null
                    && cmtVisitaTecnicaMgl.getAdministracionObj().getCompaniaId().compareTo(BigDecimal.ZERO) != 0)) {

                cmtVisitaTecnicaMgl.setAdministracionObj(
                        cmtVisitaTecnicaMgl.getOtObj().getCmObj().getSubEdificioGeneral().
                                getCompaniaAdministracionObj());
            }
            if (!(cmtVisitaTecnicaMgl.getCompaniaConstructora() != null
                    && cmtVisitaTecnicaMgl.getCompaniaConstructora().getCompaniaId() != null
                    && cmtVisitaTecnicaMgl.getCompaniaConstructora().getCompaniaId().compareTo(BigDecimal.ZERO) != 0)) {
                cmtVisitaTecnicaMgl.setCompaniaConstructora(cmtVisitaTecnicaMgl.getOtObj().
                        getCmObj().getSubEdificioGeneral().
                        getCompaniaConstructoraObj());
            }
            if (!(cmtVisitaTecnicaMgl.getOrigenDatos() != null
                    && cmtVisitaTecnicaMgl.getOrigenDatos().getBasicaId() != null
                    && cmtVisitaTecnicaMgl.getOrigenDatos().getBasicaId().compareTo(BigDecimal.ZERO) != 0)) {
                cmtVisitaTecnicaMgl.setOrigenDatos(cmtVisitaTecnicaMgl.getOtObj()
                        .getCmObj().getSubEdificioGeneral().
                        getOrigenDatosObj());
            }

            if (!(cmtVisitaTecnicaMgl.getTipoProyecto() != null
                    && cmtVisitaTecnicaMgl.getTipoProyecto().getBasicaId() != null
                    && cmtVisitaTecnicaMgl.getTipoProyecto().getBasicaId().compareTo(BigDecimal.ZERO) != 0)) {
                cmtVisitaTecnicaMgl.setTipoProyecto(cmtVisitaTecnicaMgl.getOtObj().
                        getCmObj().getSubEdificioGeneral().
                        getTipoProyectoObj());
            }

            if (cmtVisitaTecnicaMgl.getTipoPtoIniAcometObj() != null
                    && (cmtVisitaTecnicaMgl.getTipoPtoIniAcometObj().getBasicaId() == null
                    || cmtVisitaTecnicaMgl.getTipoPtoIniAcometObj().getBasicaId().compareTo(BigDecimal.ZERO) == 0)) {
                cmtVisitaTecnicaMgl.setTipoPtoIniAcometObj(null);
            }

            if (cmtVisitaTecnicaMgl.getCanalizacionIntObj() != null
                    && (cmtVisitaTecnicaMgl.getCanalizacionIntObj().getBasicaId() == null
                    || cmtVisitaTecnicaMgl.getCanalizacionIntObj().getBasicaId().compareTo(BigDecimal.ZERO) == 0)) {
                cmtVisitaTecnicaMgl.setCanalizacionIntObj(null);
            }

            if (cmtVisitaTecnicaMgl.getTipoConfDistribObj() != null
                    && (cmtVisitaTecnicaMgl.getTipoConfDistribObj().getBasicaId() == null
                    || cmtVisitaTecnicaMgl.getTipoConfDistribObj().getBasicaId().compareTo(BigDecimal.ZERO) == 0)) {
                cmtVisitaTecnicaMgl.setTipoConfDistribObj(null);
            }

            if (cmtVisitaTecnicaMgl.getAlimentElectObj() != null
                    && (cmtVisitaTecnicaMgl.getAlimentElectObj().getBasicaId() == null
                    || cmtVisitaTecnicaMgl.getAlimentElectObj().getBasicaId().compareTo(BigDecimal.ZERO) == 0)) {
                cmtVisitaTecnicaMgl.setAlimentElectObj(null);
            }

            if (cmtVisitaTecnicaMgl.getTipoDistribucionObj() != null
                    && (cmtVisitaTecnicaMgl.getTipoDistribucionObj().getBasicaId() == null
                    || cmtVisitaTecnicaMgl.getTipoDistribucionObj().getBasicaId().compareTo(BigDecimal.ZERO) == 0)) {
                cmtVisitaTecnicaMgl.setTipoDistribucionObj(null);
            }

            if (cmtVisitaTecnicaMgl.getManejoAscensoresObj() != null
                    && (cmtVisitaTecnicaMgl.getManejoAscensoresObj().getBasicaId() == null
                    || cmtVisitaTecnicaMgl.getManejoAscensoresObj().getBasicaId().compareTo(BigDecimal.ZERO) == 0)) {
                cmtVisitaTecnicaMgl.setManejoAscensoresObj(null);
            }

            cmtVisitaTecnicaMgl = daoImpl.createCm(cmtVisitaTecnicaMgl, usuario, perfil);

            if (!(cmtVisitaTecnicaMgl.getAdministracionObj() != null
                    && cmtVisitaTecnicaMgl.getAdministracionObj().getCompaniaId() != null
                    && cmtVisitaTecnicaMgl.getAdministracionObj().getCompaniaId().compareTo(BigDecimal.ZERO) != 0)) {

                cmtVisitaTecnicaMgl.setAdministracionObj(new CmtCompaniaMgl());
            }
            //Asignamos la compañia constructora si cambio
            if (!(cmtVisitaTecnicaMgl.getCompaniaConstructora() != null
                    && cmtVisitaTecnicaMgl.getCompaniaConstructora().getCompaniaId() != null
                    && cmtVisitaTecnicaMgl.getCompaniaConstructora().getCompaniaId().compareTo(BigDecimal.ZERO) != 0)) {
                cmtVisitaTecnicaMgl.setCompaniaConstructora(new CmtCompaniaMgl());
            }
            //Asignamos el origen de datos si cambio
            if (!(cmtVisitaTecnicaMgl.getOrigenDatos() != null
                    && cmtVisitaTecnicaMgl.getOrigenDatos().getBasicaId() != null
                    && cmtVisitaTecnicaMgl.getOrigenDatos().getBasicaId().compareTo(BigDecimal.ZERO) != 0)) {
                cmtVisitaTecnicaMgl.setOrigenDatos(new CmtBasicaMgl());
            }

            //Asignamos el tipo de proyecto si cambio
            if (!(cmtVisitaTecnicaMgl.getTipoProyecto() != null
                    && cmtVisitaTecnicaMgl.getTipoProyecto().getBasicaId() != null
                    && cmtVisitaTecnicaMgl.getTipoProyecto().getBasicaId().compareTo(BigDecimal.ZERO) != 0)) {
                cmtVisitaTecnicaMgl.setTipoProyecto(new CmtBasicaMgl());
            }
            return cmtVisitaTecnicaMgl;

        } catch (ApplicationException ex) {
            String msg = "Ocurrio un error actualizando la VT '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Ocurrio un error actualizando la VT"
                    + ex.getMessage());
        }
    }

    /**
     * Edita los campos del registro en la tabla
     *
     * @param usuario
     * @param perfil
     * @param cmtVisitaTecnicaMgl
     * @return Notificacion de actualizacion del registro
     * @throws ApplicationException
     */
    public CmtVisitaTecnicaMgl updateCm(CmtVisitaTecnicaMgl cmtVisitaTecnicaMgl,
            String usuario, int perfil) throws ApplicationException {
        try {

            CmtVisitaTecnicaMglDaoImpl daoImpl = new CmtVisitaTecnicaMglDaoImpl();
            
            if (cmtVisitaTecnicaMgl.getOtObj() != null 
                        && cmtVisitaTecnicaMgl.getOtObj().getCmObj() != null 
                        && cmtVisitaTecnicaMgl.getOtObj().getCmObj().getSubEdificioGeneral() != null) {
            
                if (!(cmtVisitaTecnicaMgl.getAdministracionObj() != null
                        && cmtVisitaTecnicaMgl.getAdministracionObj().getCompaniaId() != null
                        && cmtVisitaTecnicaMgl.getAdministracionObj().getCompaniaId().compareTo(BigDecimal.ZERO) != 0)) {

                    cmtVisitaTecnicaMgl.setAdministracionObj(
                            cmtVisitaTecnicaMgl.getOtObj().getCmObj().getSubEdificioGeneral().
                                    getCompaniaAdministracionObj());
                }
                if (!(cmtVisitaTecnicaMgl.getCompaniaConstructora() != null
                        && cmtVisitaTecnicaMgl.getCompaniaConstructora().getCompaniaId() != null
                        && cmtVisitaTecnicaMgl.getCompaniaConstructora().getCompaniaId().compareTo(BigDecimal.ZERO) != 0)) {
                    cmtVisitaTecnicaMgl.setCompaniaConstructora(cmtVisitaTecnicaMgl.getOtObj().
                            getCmObj().getSubEdificioGeneral().
                            getCompaniaConstructoraObj());
                }
                if (!(cmtVisitaTecnicaMgl.getOrigenDatos() != null
                        && cmtVisitaTecnicaMgl.getOrigenDatos().getBasicaId() != null
                        && cmtVisitaTecnicaMgl.getOrigenDatos().getBasicaId().compareTo(BigDecimal.ZERO) != 0)) {
                    cmtVisitaTecnicaMgl.setOrigenDatos(cmtVisitaTecnicaMgl.getOtObj()
                            .getCmObj().getSubEdificioGeneral().
                            getOrigenDatosObj());
                }

                if (!(cmtVisitaTecnicaMgl.getTipoProyecto() != null
                        && cmtVisitaTecnicaMgl.getTipoProyecto().getBasicaId() != null
                        && cmtVisitaTecnicaMgl.getTipoProyecto().getBasicaId().compareTo(BigDecimal.ZERO) != 0)) {
                    cmtVisitaTecnicaMgl.setTipoProyecto(cmtVisitaTecnicaMgl.getOtObj().
                            getCmObj().getSubEdificioGeneral().
                            getTipoProyectoObj());
                }
            }

            if (cmtVisitaTecnicaMgl.getTipoPtoIniAcometObj() != null
                    && (cmtVisitaTecnicaMgl.getTipoPtoIniAcometObj().getBasicaId() == null
                    || cmtVisitaTecnicaMgl.getTipoPtoIniAcometObj().getBasicaId().compareTo(BigDecimal.ZERO) == 0)) {
                cmtVisitaTecnicaMgl.setTipoPtoIniAcometObj(null);
            }

            if (cmtVisitaTecnicaMgl.getCanalizacionIntObj() != null
                    && (cmtVisitaTecnicaMgl.getCanalizacionIntObj().getBasicaId() == null
                    || cmtVisitaTecnicaMgl.getCanalizacionIntObj().getBasicaId().compareTo(BigDecimal.ZERO) == 0)) {
                cmtVisitaTecnicaMgl.setCanalizacionIntObj(null);
            }

            if (cmtVisitaTecnicaMgl.getTipoConfDistribObj() != null
                    && (cmtVisitaTecnicaMgl.getTipoConfDistribObj().getBasicaId() == null
                    || cmtVisitaTecnicaMgl.getTipoConfDistribObj().getBasicaId().compareTo(BigDecimal.ZERO) == 0)) {
                cmtVisitaTecnicaMgl.setTipoConfDistribObj(null);
            }

            if (cmtVisitaTecnicaMgl.getAlimentElectObj() != null
                    && (cmtVisitaTecnicaMgl.getAlimentElectObj().getBasicaId() == null
                    || cmtVisitaTecnicaMgl.getAlimentElectObj().getBasicaId().compareTo(BigDecimal.ZERO) == 0)) {
                cmtVisitaTecnicaMgl.setAlimentElectObj(null);
            }

            if (cmtVisitaTecnicaMgl.getTipoDistribucionObj() != null
                    && (cmtVisitaTecnicaMgl.getTipoDistribucionObj().getBasicaId() == null
                    || cmtVisitaTecnicaMgl.getTipoDistribucionObj().getBasicaId().compareTo(BigDecimal.ZERO) == 0)) {
                cmtVisitaTecnicaMgl.setTipoDistribucionObj(null);
            }

            if (cmtVisitaTecnicaMgl.getManejoAscensoresObj() != null
                    && (cmtVisitaTecnicaMgl.getManejoAscensoresObj().getBasicaId() == null
                    || cmtVisitaTecnicaMgl.getManejoAscensoresObj().getBasicaId().compareTo(BigDecimal.ZERO) == 0)) {
                cmtVisitaTecnicaMgl.setManejoAscensoresObj(null);
            }

            cmtVisitaTecnicaMgl = daoImpl.updateCm(cmtVisitaTecnicaMgl, usuario, perfil);

            if (!(cmtVisitaTecnicaMgl.getAdministracionObj() != null
                    && cmtVisitaTecnicaMgl.getAdministracionObj().getCompaniaId() != null
                    && cmtVisitaTecnicaMgl.getAdministracionObj().getCompaniaId().compareTo(BigDecimal.ZERO) != 0)) {

                cmtVisitaTecnicaMgl.setAdministracionObj(new CmtCompaniaMgl());
            }
            //Asignamos la compañia constructora si cambio
            if (!(cmtVisitaTecnicaMgl.getCompaniaConstructora() != null
                    && cmtVisitaTecnicaMgl.getCompaniaConstructora().getCompaniaId() != null
                    && cmtVisitaTecnicaMgl.getCompaniaConstructora().getCompaniaId().compareTo(BigDecimal.ZERO) != 0)) {
                cmtVisitaTecnicaMgl.setCompaniaConstructora(new CmtCompaniaMgl());
            }
            //Asignamos el origen de datos si cambio
            if (!(cmtVisitaTecnicaMgl.getOrigenDatos() != null
                    && cmtVisitaTecnicaMgl.getOrigenDatos().getBasicaId() != null
                    && cmtVisitaTecnicaMgl.getOrigenDatos().getBasicaId().compareTo(BigDecimal.ZERO) != 0)) {
                cmtVisitaTecnicaMgl.setOrigenDatos(new CmtBasicaMgl());
            }

            //Asignamos el tipo de proyecto si cambio
            if (!(cmtVisitaTecnicaMgl.getTipoProyecto() != null
                    && cmtVisitaTecnicaMgl.getTipoProyecto().getBasicaId() != null
                    && cmtVisitaTecnicaMgl.getTipoProyecto().getBasicaId().compareTo(BigDecimal.ZERO) != 0)) {
                cmtVisitaTecnicaMgl.setTipoProyecto(new CmtBasicaMgl());
            }
            return cmtVisitaTecnicaMgl;
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Ocurrio un error actualizando la VT" + e.getMessage());
        }
    }

    /**
     * Elimina los registros en la tabla
     *
     * @param t
     * @param usuario
     * @param perfil
     * @return Norificacion de eliminacion del registro
     * @throws ApplicationException
     */
    public boolean deleteCm(CmtVisitaTecnicaMgl cmtVisitaTecnicaMgl, String usuario, int perfil) throws ApplicationException {
        CmtVisitaTecnicaMglDaoImpl daoImpl = new CmtVisitaTecnicaMglDaoImpl();
        return daoImpl.deleteCm(cmtVisitaTecnicaMgl, usuario, perfil);
    }

    public CmtVisitaTecnicaMgl findVtbyIdOt(BigDecimal idOt) throws ApplicationException {
        try {
            CmtVisitaTecnicaMglDaoImpl daoImpl = new CmtVisitaTecnicaMglDaoImpl();
            return daoImpl.findVtbyIdOt(idOt);
        } catch (ApplicationException ex) {
            if (ex.getCause() instanceof NoResultException) {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                LOGGER.error(msg);
                return null;
            } else {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                LOGGER.error(msg);
                throw new ApplicationException(ex.getMessage(), ex);
            }
        }
    }

    public CmtVisitaTecnicaMgl findById(CmtVisitaTecnicaMgl cmtVisitaTecnicaMgl) throws ApplicationException {
        CmtVisitaTecnicaMglDaoImpl cmtVisitaTecnicaMglDaoImpl = new CmtVisitaTecnicaMglDaoImpl();

        //valbuenayf inicio ajuste lista nodo
        CmtVisitaTecnicaMgl visitaTecnicaMgl = cmtVisitaTecnicaMglDaoImpl.findByIdVt(cmtVisitaTecnicaMgl);

        if (visitaTecnicaMgl != null && visitaTecnicaMgl.getEstadoVisitaTecnica() != null 
                && visitaTecnicaMgl.getEstadoVisitaTecnica().compareTo(BigDecimal.ONE) == 0
                && visitaTecnicaMgl.getEstadoRegistro() == 1 && visitaTecnicaMgl.getListCmtSubEdificiosVt() != null
                && !visitaTecnicaMgl.getListCmtSubEdificiosVt().isEmpty()) {
            List<CmtSubEdificioDto> listaSubEdificio = new ArrayList<>();

            for (CmtSubEdificiosVt v : visitaTecnicaMgl.getListCmtSubEdificiosVt()) {
                if (v.getEstadoRegistro() == 1) {
                    CmtSubEdificioDto subEdiDto = new CmtSubEdificioDto();
                    subEdiDto.setNombre(v.getNombreSubEdificio());
                    if (v.getNodo() != null) {
                        subEdiDto.setCodigoNodo(v.getNodo().getNodCodigo());
                    } else {
                        subEdiDto.setCodigoNodo("");
                    }
                    subEdiDto.setPiso(String.valueOf(v.getNumeroPisosCasas()));
                    listaSubEdificio.add(subEdiDto);
                }
            }
            if (!listaSubEdificio.isEmpty()) {
                visitaTecnicaMgl.setListaSubEdificio(listaSubEdificio);
            }
        }
        //valbuenayf fin ajuste lista nodo

        return visitaTecnicaMgl;
    }

    public ResponseCreateHhppDto consolidacionSubEdiVt(List<CmtSubEdificiosVt> subEdificiosVtList, String usuario, int perfil)
            throws ApplicationException, ExceptionDB, IOException, ApplicationException {
        //variable que activa y desactiva RR
        boolean habilitarRR = false;
        ResponseCreateHhppDto responseCreateHhppDto = new ResponseCreateHhppDto();
        ParametrosMglManager parametrosMglManager2 = new ParametrosMglManager();
        ParametrosMgl parametroHabilitarRR = parametrosMglManager2.findParametroMgl(co.com.claro.mgl.utils.Constant.HABILITAR_RR);
        if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
            habilitarRR = true;
        }
        if (subEdificiosVtList == null || subEdificiosVtList.isEmpty()) {
            throw new ApplicationException("Se debe agregar la torre unica del la CCMM,"
                    .concat(" o las torres a procesar en la VT"));
        }
        if (habilitarRR == true) {
            List<HhppMgl> listaHhpp;
            List<String> listaProcesados = new ArrayList<>();
            CmtCuentaMatrizMgl cmtCuentaMatrizMgl = subEdificiosVtList.get(0).getVtObj().getOtObj().getCmObj();
            CmtSubEdificioMgl cmtSubEdificioMgl =cmtCuentaMatrizMgl.getSubEdificioGeneral();
            HhppMglManager hhppManager = new HhppMglManager();
            // Lista de hhpp salaventas campamentos de torre 1 para rr
            listaHhpp = hhppManager.findHhppBySubEdificioId(cmtSubEdificioMgl.getSubEdificioId());
            // se validan que ya esten actualizados con la neuva direccion
            for (HhppMgl hhppSC : listaHhpp) {
                if (hhppSC.getHhppSCProcesado() == null) {

                    listaProcesados.add("1");

                } else {
                    if (hhppSC.getHhppSCProcesado().equals("1")) {
                        continue;
                    }
                }
            }
            // extraer el nombre del subedificio y separar tipoNivel y valor 
            for (CmtSubEdificiosVt cmtSubEdificiosVt : subEdificiosVtList) {
                String nombreSubEdificio = " ";
                String[] nombreSubEdificioSep = cmtSubEdificiosVt.getNombreSubEdificio().split(" ");
                String nombreValor = "";
                for (int i = 0; i < nombreSubEdificioSep.length; i++) {
                    if (i != 0) {
                        nombreValor += nombreSubEdificioSep[i] + " ";
                    }
                }
                nombreSubEdificio = nombreSubEdificioSep[0] + " " + nombreValor;
                // si la torre o subedificio que posee hhpp de salaventa o campamento 
                if (cmtSubEdificiosVt.getNombreSubEdificio().contains(nombreSubEdificioSep[0] + " " + "1")) {
                    if (!listaProcesados.isEmpty() || listaProcesados.contains("1")) {
                        if (!listaHhpp.isEmpty()) {
                            // metodo para cambiar la direccion de los hhpp salaventas y complementos en el caso 
                            // de subedificio con su propia direccion
                            cambioDireccionHhppSalYCamp(cmtSubEdificiosVt, usuario, cmtCuentaMatrizMgl);

                        }
                    }
                }
//                }
            }
        }

        for (CmtSubEdificiosVt subEdiVt : subEdificiosVtList) {
            CmtDireccionMgl dirSubEdi = null;
            AddressEJB addressEJB = new AddressEJB();
            AddressRequest addressRequest = new AddressRequest();
            DireccionMgl direccionSEMgl = new DireccionMgl();
            SubDireccionMgl subDireccionSEMgl = new SubDireccionMgl();
            String direccionSE;
            CmtEstadoxFlujoMglManager estadoxFlujoMglManager = new CmtEstadoxFlujoMglManager();
            // se calcula el estado del subedificio en funcion al tipoFlujo, Estado Interno y tecnologia
            CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl = estadoxFlujoMglManager.findByTipoFlujoAndEstadoInt(
                    subEdiVt.getVtObj().getOtObj().getTipoOtObj().getTipoFlujoOt(), subEdiVt.getVtObj().getOtObj().getEstadoInternoObj(), subEdiVt.getVtObj().getOtObj().getBasicaIdTecnologia());

            CmtSubEdificioMgl subEdiMgl = subEdiVt.mapearCamposCmtSubEdificioMgl(subEdiVt,
                    cmtEstadoxFlujoMgl.getCambiaCmEstadoObj());
            
            if (subEdiVt.getSubEdificioObj() != null) {
                subEdiMgl.setSubEdificioId(subEdiVt.getSubEdificioObj().getSubEdificioId());
            }
            // se crean la tecnologia del subedificiio
            CmtTecnologiaSubMgl tecnologiaSub = crearRegistroTecnologiaSub(subEdiVt, usuario, perfil, subEdiMgl);
            // validacion cuando la ccmm este presente FO
            List<CmtTecnologiaSubMgl> listatecnologiaSubMgls;
            List<CmtTecnologiaSubMgl> listTecnologiasEdificio;
            List<String> listaBasicaTec = new ArrayList<>();
            CmtTecnologiaSubMglManager cmtTecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
            CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
            listatecnologiaSubMgls = cmtTecnologiaSubMglManager.findTecnoSubBySubEdi(subEdiVt.getVtObj().getOtObj().getCmObj().getSubEdificioGeneral());
            listTecnologiasEdificio = cmtTecnologiaSubMglManager.findTecnoSubBySubEdi(subEdiVt.getSubEdificioObj());
            for (CmtTecnologiaSubMgl cmtTecnologiaSubMgl : listatecnologiaSubMgls) {
                listaBasicaTec.add(cmtTecnologiaSubMgl.getBasicaIdTecnologias().getIdentificadorInternoApp());
            }
            //Proceso para multiedificio
            if (!subEdiVt.getVtObj().getOtObj().getCmObj().isUnicoSubEdificioBoolean()) {
                 // valida si tiene direccion el subedificio
                if (!(subEdiVt.getDireccionSubEdificio() == null || subEdiVt.getDireccionSubEdificio().isEmpty())) {
                    dirSubEdi = subEdiVt.mapearCamposCmtDireccion(subEdiVt);
                    dirSubEdi.setSubEdificioObj(subEdiMgl);
                    dirSubEdi.setCuentaMatrizObj(subEdiMgl.getCuentaMatrizObj());
                    if (!subEdiVt.isHasOwnAddress()) {
                        direccionSE = subEdiVt.getDireccionSubEdificio();
                        DrDireccion nuevadireccion = new DrDireccion();
                        nuevadireccion.obtenerFromCmtDireccionMgl(dirSubEdi);
                        nuevadireccion.setBarrio(subEdiMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getBarrio());
                        addressRequest.setAddress(direccionSE);
                        addressRequest.setCity(subEdiVt.getVtObj().getOtObj().getCmObj().getMunicipio().getGpoNombre());
                        addressRequest.setCodDaneVt(subEdiVt.getVtObj().getOtObj().getCmObj().getCentroPoblado().getGeoCodigoDane());
                        addressRequest.setNeighborhood(subEdiVt.getVtObj().getOtObj().getCmObj().getDireccionPrincipal().getBarrio());
                        addressRequest.setState(subEdiVt.getVtObj().getOtObj().getCmObj().getDepartamento().getGpoNombre());
                        //se georeferencia la direccion de la cuenta matriz en DrDireccion
                        ResponseMessage responseMessage = addressEJB.createAddress(addressRequest, usuario, "CM", "true", nuevadireccion);
                        char tipoDir = responseMessage.getIdaddress().charAt(0);
                        if ("D".equals(Character.toString(tipoDir)) || "d".equals(Character.toString(tipoDir))) {
                            direccionSEMgl.setDirId(new BigDecimal(responseMessage.getIdaddress().replace("d", "").replace("D", "")));
                            direccionSEMgl = direccionMglManager.findById(direccionSEMgl);
                            dirSubEdi.setDireccionObj(direccionSEMgl);
                        } else if ("S".equals(Character.toString(tipoDir)) || "s".equals(Character.toString(tipoDir))) {
                            subDireccionSEMgl.setSdiId(new BigDecimal(responseMessage.getIdaddress().replace("s", "").replace("S", "")));
                            subDireccionSEMgl = subDireccionMglManager.findById(subDireccionSEMgl.getSdiId());
                            dirSubEdi.setDireccionId(subDireccionSEMgl.getDirId());
                        }
                        dirSubEdi.setComentario("Direccion creada por Gestion de Visita Tecnica");
                    }
                }
                boolean isCreate = subEdiMgl.getSubEdificioId() == null;
               
                if (isCreate) {
                    //se crean los subedificios nuevos en la pestaña multiedificios  en RR y MGL
                     subEdiMgl.setNodoObj(subEdiMgl.getCmtCuentaMatrizMglObj().getSubEdificioGeneral().getNodoObj());
                    // subedificios creados durante la visitatecnica
                    if (subEdiVt.getListTecnologiaHabilitada() != null) {
                        subEdiMgl.setUnidadesEstimadas(subEdiVt.getListTecnologiaHabilitada().size());
                    }
                    // ajuste crear torres nuevas con su propia direccion  para asociar cm en rr

                    if (subEdiVt.getDireccionSubEdificio() != null && !subEdiVt.getDireccionSubEdificio().isEmpty()) {
                        // edificios con su propia direccion y multiorigen
                        if (subEdiVt.getVtObj().getOtObj().getCmObj().getCentroPoblado().getGpoMultiorigen().equals("1")) {
                            // multiorigen se le agrega el barrio

                            subEdiMgl.setNombreEntSubedificio(Constant.PREFIJO_SUBEDIFICIO_ENTRADA + subEdiVt.getCalleRr() + " " + subEdiVt.getUnidadRr());
                            subEdiMgl = cmtSubEdificioMglManager.create(subEdiMgl, usuario, perfil, false);
                        } else {
                            // edificios con su propia direccion intraducibles 
                            if (subEdiVt.getIdTipoDireccion().equalsIgnoreCase("IT")) {
                                subEdiMgl.setNombreEntSubedificio(Constant.PREFIJO_SUBEDIFICIO_ENTRADA + subEdiVt.getCalleRr() + " " + subEdiVt.getUnidadRr());
                            } else {
                                subEdiMgl.setNombreEntSubedificio(Constant.PREFIJO_SUBEDIFICIO_ENTRADA + subEdiVt.getCalleRr() + " " + subEdiVt.getUnidadRr());
                            }

                            subEdiMgl = cmtSubEdificioMglManager.create(subEdiMgl, usuario, perfil, false);
                        }

                    } else {
                        // escenario normal
                        subEdiMgl = cmtSubEdificioMglManager.create(subEdiMgl, usuario, perfil, false);
                    }

                    if (dirSubEdi != null) {
                        dirSubEdi.setCuentaMatrizObj(subEdiMgl.getCmtCuentaMatrizMglObj());
                        dirSubEdi.setSubEdificioObj(subEdiMgl);
                        //JDHT
                        if (dirSubEdi.getCuentaMatrizObj() != null
                                && dirSubEdi.getCuentaMatrizObj().getDireccionPrincipal() != null
                                && dirSubEdi.getCuentaMatrizObj().getDireccionPrincipal().getBarrio() != null
                                && !dirSubEdi.getCuentaMatrizObj().getDireccionPrincipal().getBarrio().isEmpty()){
                            dirSubEdi.setBarrio(dirSubEdi.getCuentaMatrizObj().getDireccionPrincipal().getBarrio());
                        }
                        if (dirSubEdi.getDireccionId() == null) {
                            dirSubEdi.setTdiId(3);
                            dirSubEdi = cmtDireccionMglManager.create(dirSubEdi, usuario, perfil);
                            subEdiMgl.getListDireccionesMgl().add(dirSubEdi);
                        }
                    }
                    CmtSubEdificioMgl subEdificioMglGeneral = subEdiMgl.getCuentaMatrizObj().getSubEdificioGeneral();
                    subEdificioMglGeneral.setUnidadesEstimadas(contarUnidadesACrearByOt(subEdiVt.getVtObj().getOtObj()));
                    cmtSubEdificioMglManager.update(
                            subEdificioMglGeneral, usuario, perfil, false, false);
                } else {
                      subEdiMgl.setNodoObj(subEdiMgl.getCmtCuentaMatrizMglObj().getSubEdificioGeneral().getNodoObj());
                    // edificios creados con la cuenta matriz
                    if (subEdiVt.getListTecnologiaHabilitada() != null) {
                        subEdiMgl.setUnidadesEstimadas(subEdiVt.getListTecnologiaHabilitada().size());
                    }
                    // ajuste actualizar torres con su propia direccion  para asociar cm en rr
                    if (subEdiVt.getDireccionSubEdificio() != null && !subEdiVt.getDireccionSubEdificio().isEmpty()) {
                        if (subEdiVt.getSubEdificioObj() != null
                                && subEdiVt.getSubEdificioObj().getCmtCuentaMatrizMglObj() != null
                                && subEdiVt.getSubEdificioObj().getCmtCuentaMatrizMglObj().getCentroPoblado() != null
                                && subEdiVt.getSubEdificioObj().getCmtCuentaMatrizMglObj().getCentroPoblado().getGpoMultiorigen() != null
                                && subEdiVt.getSubEdificioObj().getCmtCuentaMatrizMglObj().getCentroPoblado().getGpoMultiorigen().equals("1")) {
                            subEdiMgl.setNombreEntSubedificio(Constant.PREFIJO_SUBEDIFICIO_ENTRADA + subEdiVt.getCalleRr() + " " + subEdiVt.getUnidadRr());
                            subEdiMgl = cmtSubEdificioMglManager.update(
                                    subEdiMgl, usuario, perfil, false, false);
                        } else {
                            // edificios con su propia direccion intraducibles 
                            if (subEdiVt.getIdTipoDireccion() != null
                                    && subEdiVt.getIdTipoDireccion().equalsIgnoreCase("IT")) {
                                subEdiMgl.setNombreEntSubedificio(Constant.PREFIJO_SUBEDIFICIO_ENTRADA + subEdiVt.getCalleRr() + " " + subEdiVt.getUnidadRr());
                            } else {
                                subEdiMgl.setNombreEntSubedificio(Constant.PREFIJO_SUBEDIFICIO_ENTRADA + subEdiVt.getCalleRr() + " " + subEdiVt.getUnidadRr());
                            }
                            subEdiMgl = cmtSubEdificioMglManager.update(
                                    subEdiMgl, usuario, perfil, false, false);
                        }
                    } else {

                        subEdiMgl = cmtSubEdificioMglManager.update(
                                subEdiMgl, usuario, perfil, false, false);
                    }

                    if (dirSubEdi != null) {
                        dirSubEdi.setCuentaMatrizObj(subEdiMgl.getCmtCuentaMatrizMglObj());
                        dirSubEdi.setSubEdificioObj(subEdiMgl);
                        if (dirSubEdi.getDireccionId() == null) {
                            dirSubEdi.setTdiId(3);
                            dirSubEdi = cmtDireccionMglManager.create(dirSubEdi, usuario, perfil);
                            subEdiMgl.getListDireccionesMgl().add(dirSubEdi);
                        }

                    }
                    CmtSubEdificioMgl subEdificioMglGeneral = subEdiMgl.getCuentaMatrizObj().getSubEdificioGeneral();
                    subEdificioMglGeneral.setUnidadesEstimadas(contarUnidadesACrearByOt(subEdiVt.getVtObj().getOtObj()));
                    cmtSubEdificioMglManager.update(
                            subEdificioMglGeneral, usuario, perfil, false, false);
                }

                CmtSubEdificiosVtManager subEdificiosVtManager
                        = new CmtSubEdificiosVtManager();
                subEdiVt = subEdificiosVtManager.findById(subEdiVt.getIdEdificioVt());
                subEdiVt.setCodigoRr(subEdiMgl.getCodigoRr());
                subEdiVt.setSubEdificioObj(subEdiMgl);
                subEdiVt = subEdificiosVtManager.update(subEdiVt, usuario, perfil);

            } else {
                // unico Edificio
                // se actualiza en RR con el estado combinado cuando hay tecnologia FO
                    if (listaBasicaTec.contains(Constant.RED_FO)) {
                    CmtBasicaMgl basicaMgl = new CmtBasicaMgl();
                    CmtTipoBasicaMgl cmtTipoBasicaMgl;
                    cmtTipoBasicaMgl = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                            co.com.claro.mgl.utils.Constant.TIPO_BASICA_ESTADO_CUENTA_MATRIZ);
                    basicaMgl = cmtTipoBasicaMgl.
                            findBasicaByCampoEntidadAs400ExtendidoAndValorExtendido(Constant.RED_FO, "Y");
                    subEdiMgl.setEstadoSubEdificioObj(basicaMgl);
                }
                // actualiza en la tabla Subedificio
                subEdiMgl.setUnidadesEstimadas(subEdiVt.getListTecnologiaHabilitada().size());
                if (subEdiVt.getSubEdificioObj() != null && subEdiVt.getSubEdificioObj().getCmtCuentaMatrizMglObj() != null && subEdiVt.getSubEdificioObj().getCmtCuentaMatrizMglObj().getCentroPoblado() != null && subEdiVt.getSubEdificioObj().getCmtCuentaMatrizMglObj().getCentroPoblado().getGpoMultiorigen() != null
                        && subEdiVt.getSubEdificioObj().getCmtCuentaMatrizMglObj().getCentroPoblado().getGpoMultiorigen().equals("1")) {
                    if (subEdiVt.getDireccionSubEdificio() != null && !subEdiVt.getDireccionSubEdificio().isEmpty()) {
                        subEdiMgl.setNombreEntSubedificio(Constant.PREFIJO_SUBEDIFICIO_ENTRADA + subEdiVt.getCalleRr() + " " + subEdiVt.getUnidadRr());
                        subEdiMgl = cmtSubEdificioMglManager.update(
                                subEdiMgl, usuario, perfil, false, false);
                    } else {

                        subEdiMgl = cmtSubEdificioMglManager.update(
                                subEdiMgl, usuario, perfil, false, false);
                    }

                } else {
                    subEdiMgl = cmtSubEdificioMglManager.update(
                            subEdiMgl, usuario, perfil, false, false);
                }

            }

            String tipoSolStr = "Visita Tecnica";
            String observaconStr = "Creacion HHPP Visita Tecnica";
            String razonStr = "HHPP CREADO CM";
            List<CmtHhppVtMgl> listHhppBySubEdificio = cmtHhppVtMglManager.findBySubEdiVt(subEdiVt);

            //creacion de los hhpp del subedificio
            for (CmtHhppVtMgl tecnologiaHabilitada : listHhppBySubEdificio) {
                if (tecnologiaHabilitada != null && tecnologiaHabilitada.getProcesado() == 0) {
                    try {
                        HhppMglManager hhppMglManager = new HhppMglManager();
                        boolean resultProcessHhppRr = true;
                        responseCreateHhppDto = hhppMglManager.crearHhppRRAndRepoFromCcMm(tecnologiaHabilitada.getOpcionNivel5(),
                                tecnologiaHabilitada.getValorNivel5(),
                                tecnologiaHabilitada.getOpcionNivel6(),
                                tecnologiaHabilitada.getValorNivel6(),
                                subEdiMgl, usuario,
                                subEdiVt.getVtObj().getIdVt().toString(),
                                tipoSolStr, observaconStr, razonStr,
                                tecnologiaSub, tecnologiaHabilitada, perfil);
                        resultProcessHhppRr = responseCreateHhppDto.isCreacionExitosa();
                        if (resultProcessHhppRr) {
                            tecnologiaHabilitada.setProcesado(1);
                            CmtHhppVtMglManager tecnologiaHabilitadaManager = new CmtHhppVtMglManager();
                            tecnologiaHabilitada.setMensajeCreacion("Creado");
                            tecnologiaHabilitada.setMensajeProceso("OK");
                            tecnologiaHabilitadaManager.updateCmt(tecnologiaHabilitada, usuario, perfil);
                            tecnologiaHabilitada.setVariableRR(false);
                        } else {
                            tecnologiaHabilitada.setProcesado(0);
                            if (responseCreateHhppDto.getValidationMessages() != null && !responseCreateHhppDto.getValidationMessages().isEmpty()) {
                                tecnologiaHabilitada.setMensajeCreacion(responseCreateHhppDto.getValidationMessages().get(0));
                            }
                            CmtHhppVtMglManager tecnologiaHabilitadaManager = new CmtHhppVtMglManager();
                            tecnologiaHabilitadaManager.updateCmt(tecnologiaHabilitada, usuario, perfil);
                        }

                    } catch (ApplicationException er) {
                        String msg = "No se proceso el HHPP:".concat(tecnologiaHabilitada.getOpcionNivel5() == null ? "" : tecnologiaHabilitada.getOpcionNivel5())
                                .concat(": ".concat(tecnologiaHabilitada.getOpcionNivel6() == null ? "" : tecnologiaHabilitada.getOpcionNivel6()));
                        if (er.getMessage() != null) {
                            msg = msg.concat("::").concat(er.getMessage());
                        }
                        tecnologiaHabilitada.setMensajeProceso(msg);
                        LOGGER.error("Error al tratar de crear HHPP en procesar RR VT", er);
                    }
                }
            }
            crearInfoTecnica(listTecnologiasEdificio, usuario, perfil);
        }

        CmtTecnologiaSubMglManager cmtTecnologiaSubMglManager = new CmtTecnologiaSubMglManager();

        if (!subEdificiosVtList.isEmpty()) {
            CmtVisitaTecnicaMgl cmtVisitaTecnicaMgl = subEdificiosVtList.get(0).getVtObj();
            cmtTecnologiaSubMglManager.guardarMetaSubEdificios(cmtVisitaTecnicaMgl, usuario, perfil);
            cmtTecnologiaSubMglManager.guardarFechaHabilitacionSubEdificios(cmtVisitaTecnicaMgl, usuario, perfil);
            cmtTecnologiaSubMglManager.guardarTiempoRecuperacionSubEdificios(cmtVisitaTecnicaMgl, usuario, perfil);//villamil Inspira, deuda tecnica
            CmtInventarioTecnologiaMglManager cmtInventarioTecnologiaMglManager = new CmtInventarioTecnologiaMglManager();
            cmtInventarioTecnologiaMglManager.actualizarInvTecCm(cmtVisitaTecnicaMgl.getOtObj(), usuario, perfil);
        }
        return responseCreateHhppDto;
    }

    public int contarUnidadesACrearByOt(CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl) {
        int contadorHhppVt = 0;
        CmtVisitaTecnicaMgl cmtVisitaTecnicaMgl = cmtOrdenTrabajoMgl.getVtActivaFromOt();
        if (cmtVisitaTecnicaMgl.getListCmtSubEdificiosVt() != null) {
            for (CmtSubEdificiosVt edificiosVt : cmtVisitaTecnicaMgl.getListCmtSubEdificiosVt()) {
                if (edificiosVt.getEstadoRegistro() == 1) {
                    for (CmtHhppVtMgl tecnologiaHabilitada : edificiosVt.getListTecnologiaHabilitada()) {
                        if (tecnologiaHabilitada.getEstadoRegistro() == 1) {
                            contadorHhppVt++;
                        }
                    }
                }
            }
        }
        return contadorHhppVt;
    }

 public CmtTecnologiaSubMgl crearRegistroTecnologiaSub(CmtSubEdificiosVt subEdiVt,
            String usuario, int perfil, CmtSubEdificioMgl subEdiMgl) throws ApplicationException {
     CmtTecnologiaSubMglManager tecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
     CmtBasicaMgl basicaMglTipoFlujo = subEdiVt.getVtObj().getOtObj().getTipoOtObj().getTipoFlujoOt();
     CmtEstadoxFlujoMglManager estadoxFlujoMglManager = new CmtEstadoxFlujoMglManager();
     CmtTecnologiaSubMgl tecnologiaSubMglEdifGeneral = null;
     CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl = estadoxFlujoMglManager.findByTipoFlujoAndEstadoInt(
             basicaMglTipoFlujo, subEdiVt.getVtObj().getOtObj().getEstadoInternoObj(),
             subEdiVt.getVtObj().getOtObj().getBasicaIdTecnologia());

     List<CmtTecnologiaSubMgl> tecnologiasSub
             = tecnologiaSubMglManager.findTecnoSubBySubEdiTec(
                     subEdiVt.getSubEdificioObj(),
                     subEdiVt.getVtObj().getOtObj().getBasicaIdTecnologia());
        CmtTecnologiaSubMgl tecnologiaSubMgl;
        CmtVisitaTecnicaMgl visita = subEdiVt.getVtObj();
        if (tecnologiasSub == null || tecnologiasSub.isEmpty()) {
            tecnologiaSubMgl
                    = tecnologiaSubMglManager.generarTecnologiaSub(subEdiMgl,
                            visita, subEdiVt.getVtObj().getOtObj(), subEdiVt);
            tecnologiaSubMgl.setCostoVt(BigDecimal.ZERO);
            tecnologiaSubMgl.setCostoVisitaTecnica(BigDecimal.ZERO);
            tecnologiaSubMgl.setSubedificioId(subEdiMgl);
        } else {
            tecnologiaSubMgl = tecnologiasSub.get(0);
                    //Verificamos si no cambia estado
            if (cmtEstadoxFlujoMgl != null && cmtEstadoxFlujoMgl.getCambiaCmEstadoObj() != null
                    && cmtEstadoxFlujoMgl.getCambiaCmEstadoObj().getBasicaId() != null) {
                tecnologiaSubMgl.setBasicaIdEstadosTec(cmtEstadoxFlujoMgl.getCambiaCmEstadoObj());
            }
            if (subEdiVt.getNodo() != null) {
                tecnologiaSubMgl.setNodoId(subEdiVt.getNodo());
            }
            tecnologiaSubMgl.setVisitaTecnica(visita);
            tecnologiaSubMgl.setOrdenTrabajo(visita.getOtObj());
            tecnologiaSubMgl.setFlagVisitaTecnica("Y");
            tecnologiaSubMgl.setFechaVisitaTecnica(visita.getFechaEntregaEdificio());
        }

        tecnologiaSubMgl.setTipoConfDistribObj(visita.getTipoConfDistribObj());
        tecnologiaSubMgl.setAlimentElectObj(visita.getAlimentElectObj());
        tecnologiaSubMgl.setTipoDistribucionObj(visita.getTipoDistribucionObj());
        tecnologiaSubMgl.setManejoAscensoresObj(visita.getManejoAscensoresObj());
        tecnologiaSubMgl.setVoltaje(visita.getVoltaje() != null
                ? visita.getVoltaje() : "NA");
        tecnologiaSubMgl.setOtroTipoDistribucion(visita.getOtroTipoDistribucion() != null
                ? visita.getOtroTipoDistribucion() : "NA");
        tecnologiaSubMgl.setTapsExternos(visita.getTapsExternos() != null
                ? visita.getTapsExternos() : "NA");
        tecnologiaSubMgl.setMarca(visita.getMarca() != null
                ? visita.getMarca() : "NA");
        tecnologiaSubMgl.setTelefono(visita.getTelefono() != null ? visita.getTelefono() : "0000000");
        tecnologiaSubMgl.setAcondicionamientos(visita.getAcondicionamientos() != null ? visita.getAcondicionamientos() : "NA");
       
         
          
        // actualizacion del edificio general para unico edificio o multiedificio
        List<CmtTecnologiaSubMgl> tecnologiasSubEdifGeneral= null;
        
        if(subEdiVt.getSubEdificioObj() != null && 
                subEdiVt.getSubEdificioObj().getCmtCuentaMatrizMglObj() != null ){
           tecnologiasSubEdifGeneral
             = tecnologiaSubMglManager.findTecnoSubBySubEdiTec(
                     subEdiVt.getSubEdificioObj().getCmtCuentaMatrizMglObj().getSubEdificioGeneral(),
                     subEdiVt.getVtObj().getOtObj().getBasicaIdTecnologia());       
        }
     
          //si la tecnologia viene de la creacion de la cm             
        if (tecnologiasSubEdifGeneral != null && !tecnologiasSubEdifGeneral.isEmpty()) {
            tecnologiaSubMglEdifGeneral = tecnologiasSubEdifGeneral.get(0);
            CmtTecnologiaSubMglManager tecnoEdifGeneralManager = new CmtTecnologiaSubMglManager();
             CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
              CmtBasicaMgl basicaTipoEdificio
                        = cmtBasicaMglManager.findByCodigoInternoApp(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO);
            if (( subEdiVt.getSubEdificioObj().getCmtCuentaMatrizMglObj().getSubEdificioGeneral().getEstadoSubEdificioObj().getBasicaId().compareTo(basicaTipoEdificio.getBasicaId())) == 0) {
                tecnologiaSubMglEdifGeneral.setBasicaIdEstadosTec(basicaTipoEdificio);
                tecnoEdifGeneralManager.actualizar(tecnologiaSubMglEdifGeneral, usuario, perfil);
            } else {
                if (cmtEstadoxFlujoMgl != null && cmtEstadoxFlujoMgl.getCambiaCmEstadoObj() != null) {
                    tecnologiaSubMglEdifGeneral.setBasicaIdEstadosTec(cmtEstadoxFlujoMgl.getCambiaCmEstadoObj());
                } else {
                    tecnologiaSubMglEdifGeneral.setBasicaIdEstadosTec(null);
                }
                tecnoEdifGeneralManager.actualizar(tecnologiaSubMglEdifGeneral, usuario, perfil);
            }
        } else {
            //si la tecnologia no viene con la creacion de la cm
            CmtTecnologiaSubMglManager tecnoEdifGeneralManager = new CmtTecnologiaSubMglManager();
            CmtTecnologiaSubMgl cmtTecnologiaSubMgl = new CmtTecnologiaSubMgl();
            CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
            CmtBasicaMgl basicaTipoEdificio
                        = cmtBasicaMglManager.findByCodigoInternoApp(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO);
            // tecnologia edificio general multiedificio
            if (subEdiVt.getSubEdificioObj() != null
                    && subEdiVt.getSubEdificioObj().getCmtCuentaMatrizMglObj() != null
                    && subEdiVt.getSubEdificioObj().getCmtCuentaMatrizMglObj().getSubEdificioGeneral() != null
                    && subEdiVt.getSubEdificioObj().getCmtCuentaMatrizMglObj().getSubEdificioGeneral().getEstadoSubEdificioObj() != null
                    && subEdiVt.getSubEdificioObj().getCmtCuentaMatrizMglObj().getSubEdificioGeneral().getEstadoSubEdificioObj().getIdentificadorInternoApp() != null
                    && subEdiVt.getSubEdificioObj().getCmtCuentaMatrizMglObj().getSubEdificioGeneral().getEstadoSubEdificioObj().getIdentificadorInternoApp().equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                cmtTecnologiaSubMgl.setBasicaIdEstadosTec(basicaTipoEdificio);
                if(subEdiVt.getNodo()!= null){
                    cmtTecnologiaSubMgl.setNodoId(subEdiVt.getNodo());
                }
                cmtTecnologiaSubMgl.setBasicaIdTecnologias(subEdiVt.getVtObj().getOtObj().getBasicaIdTecnologia());
                cmtTecnologiaSubMgl.setSubedificioId(subEdiVt.getSubEdificioObj().getCmtCuentaMatrizMglObj().getSubEdificioGeneral());
                tecnoEdifGeneralManager.crear(cmtTecnologiaSubMgl, usuario, perfil);
            } 
        }
        if (tecnologiaSubMgl.getTecnoSubedificioId() != null) {
            return tecnologiaSubMglManager.actualizar(tecnologiaSubMgl, usuario, perfil);
        } else {
            return tecnologiaSubMglManager.crear(tecnologiaSubMgl, usuario, perfil);
        }
    }

    public boolean consolidarHhppFromSubEdVt(List<CmtHhppVtMgl> listHhppBySubEdificio) {
        return false;
    }

    /**
     * Buscar Visitas tecnicas activas de una derminada OT
     *
     * @param ordenTrabajo orden de trabajo como condicional de la busqueda
     * @return Retorna el objeto activo de una determinada ot Visitas tegnicas
     * @throws ApplicationException
     */
    public CmtVisitaTecnicaMgl findVTActiveByIdOt(CmtOrdenTrabajoMgl ordenTrabajo) throws ApplicationException {
        CmtVisitaTecnicaMglDaoImpl daoImpl = new CmtVisitaTecnicaMglDaoImpl();
        return daoImpl.findVTActiveByIdOt(ordenTrabajo);
    }

    /**
     * Actualiza los costos de acometidas totales de los subedificios
     *
     * @param subEdificiosVtList lista de subedificios
     * @param vtAcometida acometida con los costos reales
     * @param usuario en sesion que realiza la operacion
     * @param perfil del usuario
     * @return List<Boolean>
     * @throws ApplicationException
     */
    public List<Boolean> actualizarCostosAcometidaCcmm(List<CmtSubEdificiosVt> subEdificiosVtList,
            CmtVisitaTecnicaMgl vtAcometida, String usuario, int perfil)
            throws ApplicationException {

        CmtTecnologiaSubMglManager cmtTecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
        List<Boolean> respuestas = new ArrayList<>();

        if (subEdificiosVtList != null && !subEdificiosVtList.isEmpty()) {
            CmtVisitaTecnicaMgl cmtVisitaTecnicaMgl = subEdificiosVtList.get(0).getVtObj();
            try {
                respuestas.add(cmtTecnologiaSubMglManager.actualizarCostosAcometidasTecnologiaSubEdificioGeneral(
                        cmtVisitaTecnicaMgl.getOtObj().getCmObj(),
                        cmtVisitaTecnicaMgl.getOtObj().getBasicaIdTecnologia(),
                        vtAcometida,
                        usuario, perfil));
            } catch (ApplicationException e) {
                LOGGER.error(e.getMessage());
                throw new ApplicationException("Ocurrio un error actualizando los costos en la cuenta matriz: " + e.getMessage());
            }
            try {
                respuestas.add(cmtTecnologiaSubMglManager.actualizarCostosAcometidaSubEdificios(subEdificiosVtList, vtAcometida, usuario, perfil));
            } catch (ApplicationException e) {
                LOGGER.error(e.getMessage());
                throw new ApplicationException("Ocurrio un error actualizando los costos de cada sub edificio: " + e.getMessage());
            }
        }
        return respuestas;
    }

    /**
     * Metodo para cargar un documento de VT al UCM Autor: Victor Bocanegra
     *
     * @param visitaTecnicaMgl a la cual se le van adjuntar la imagen
     * @param archivo archivo que se va a cargar al UCM
     * @param usuario que realiza la operacion
     * @param perfil del usuario que realiza la operacion
     * @param origen
     * @return boolean
     * @throws java.net.MalformedURLException
     * @throws ApplicationException Excepcion lanzada por la carga del archivo
     * @throws java.io.FileNotFoundException
     */
    public boolean cargarArchivoVTxUCM(CmtVisitaTecnicaMgl visitaTecnicaMgl,
            UploadedFile archivo, String usuario, int perfil, String origen)
            throws MalformedURLException, FileNotFoundException, ApplicationException, 
            UploadCuentasMatricesFault, SearchCuentasMatricesFault {

        boolean respuesta = false;
        CmtArchivosVtMglManager archivosVtMglManager = new CmtArchivosVtMglManager();
        CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();
        int maximo;
        CmtCuentaMatrizMgl cuentaMatrizMgl = visitaTecnicaMgl.getOtObj().getCmObj();

        try {

            URL url;
            ParametrosMglManager parametros = new ParametrosMglManager();
            ParametrosMgl paramUser = parametros.findByAcronimoName(Constant.USER_AUTENTICACION_GESTOR_DOCUMENTAL);
            String user = "";
            if (paramUser != null) {
                user = paramUser.getParValor();
            }

            ParametrosMgl paramPass = parametros.findByAcronimoName(Constant.PASS_AUTENTICACION_GESTOR_DOCUMENTAL);
            String pass = "";
            if (paramPass != null) {
                pass = paramPass.getParValor();
            }

            ParametrosMgl param = parametros.findByAcronimoName(Constant.PROPERTY_URL_WS_UCM_UPLOAD_CCMM);
            String ruta = "";
            if (param != null) {
                ruta = param.getParValor();
            }

            String tipoDocumental = "";
            CmtBasicaMgl basicaTipoDoc = basicaMglManager.
                    findByCodigoInternoApp(Constant.TIPO_DOCUMENTAL_CUENTA_MATRIZ);

            if (basicaTipoDoc != null) {
                tipoDocumental = basicaTipoDoc.getNombreBasica();
            }

            String empresa = "";
            ParametrosMgl param2 = parametros.findByAcronimoName(Constant.PROPERTY_UCM_TIPO_EMPRESA);
            if (param2 != null) {
                empresa = param2.getParValor();
            }

            url = new URL(null, ruta, new sun.net.www.protocol.http.Handler());

            ClientGestorDocumental cliente = new ClientGestorDocumental(user, pass);

            FileRequestType request = new FileRequestType();
            DocumentType documentType = new DocumentType();
            DocumentType documentTypeBuscar = new DocumentType();

            FileType fielFileType = new FileType();

            FieldType field1 = new FieldType();
            field1.setName("xdEmpresa");
            field1.setValue(empresa);
            documentType.getField().add(field1);
            documentTypeBuscar.getField().add(field1);

            FieldType field2 = new FieldType();
            field2.setName("xdTipoDocumental");
            field2.setValue(tipoDocumental);
            documentType.getField().add(field2);
            documentTypeBuscar.getField().add(field2);

            FieldType field3 = new FieldType();
            field3.setName("xdNumeroCtaMatriz");
            field3.setValue(cuentaMatrizMgl.getCuentaMatrizId().toString());
            documentType.getField().add(field3);
            documentTypeBuscar.getField().add(field3);

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();

            FieldType field4 = new FieldType();
            field4.setName("xdFechaDocumento");
            field4.setValue(dateFormat.format(date));
            documentType.getField().add(field4);
            documentTypeBuscar.getField().add(field4);

           
            maximo = archivosVtMglManager.selectMaximoSecXVt(visitaTecnicaMgl, origen);

            String numUnico = visitaTecnicaMgl.getIdVt().toString() + String.valueOf(maximo) + origen;

            if (maximo == 0) {
                maximo++;
            } else {
                maximo++;
            }


            FieldType field5 = new FieldType();
            field5.setName("xdIdProceso");
            field5.setValue(numUnico);
            documentType.getField().add(field5);
            documentTypeBuscar.getField().add(field5);
            
            FieldType field6 = new FieldType();
            field6.setName("xdDireccionPredio");
            if (cuentaMatrizMgl.getDireccionPrincipal() != null && cuentaMatrizMgl.getDireccionPrincipal().getDireccionObj() != null) {
                field6.setValue(cuentaMatrizMgl.getDireccionPrincipal().getDireccionObj().getDirFormatoIgac());
            }else{
                 field6.setValue("Sin Direccion");
            }
            
            documentType.getField().add(field6);
            documentTypeBuscar.getField().add(field6);
            
            FieldType field7 = new FieldType();
            field7.setName("xdPredio");
            field7.setValue(cuentaMatrizMgl.getNombreCuenta());
            documentType.getField().add(field7);
            documentTypeBuscar.getField().add(field7);
            
            FieldType field8 = new FieldType();
            field8.setName("xdIdOrden");
            field8.setValue(visitaTecnicaMgl.getOtObj().getIdOt().toString());
            documentType.getField().add(field8);
            documentTypeBuscar.getField().add(field8);
            
            FieldType field9 = new FieldType();
            field9.setName("xdNumeroIdentificacion");
            field9.setValue(cuentaMatrizMgl.getNumeroCuenta().toString());
            documentType.getField().add(field9);
            documentTypeBuscar.getField().add(field9);
            
            fielFileType.setName(archivo.getFileName());
            fielFileType.setContent(archivo.getContent());

            request.setDocument(documentType);
            request.setFile(fielFileType);

            ResponseType response = cliente.insert(request, url);
            ActionStatusEnumType status = response.getActionStatus();

            String urlArchivo = null;

            if (status.value().equalsIgnoreCase("success")) {

                //Consultamos el archivo subido al gestor
                ParametrosMgl paramUrl = parametros.findByAcronimoName(Constant.PROPERTY_URL_WS_UCM_SEARCH_CCMM);
                String rutaBuscar = "";
                if (paramUrl != null) {
                    rutaBuscar = paramUrl.getParValor();
                }

                URL urlBuscar = new URL(null, rutaBuscar, new sun.net.www.protocol.http.Handler());
                RequestType requestBuscar = new RequestType();
                requestBuscar.setDocument(documentTypeBuscar);

                ResponseType responseBuscar = cliente.find(requestBuscar, urlBuscar);
                ActionStatusEnumType statusBuscar = responseBuscar.getActionStatus();

                if (statusBuscar.value().equalsIgnoreCase("success")) {

                    for (DocumentType documentType1 : responseBuscar.getDocument()) {
                        if (documentType1.getField().size() > 0) {

                            for (FieldType atributos : documentType1.getField()) {
                                if (atributos.getName().equalsIgnoreCase("xdURLDocumento")) {
                                    urlArchivo = atributos.getValue();
                                }
                            }

                        }

                    }
                }

                if (urlArchivo != null && !urlArchivo.isEmpty()) {
                    CmtArchivosVtMgl cmtArchivosVtMgl = new CmtArchivosVtMgl();
                    cmtArchivosVtMgl.setVisitaTecnicaMglobj(visitaTecnicaMgl);
                    cmtArchivosVtMgl.setOrdenTrabajoMglobj(visitaTecnicaMgl.getOtObj());
                    cmtArchivosVtMgl.setRutaArchivo(urlArchivo);
                    cmtArchivosVtMgl.setNombreArchivo(archivo.getFileName());
                    cmtArchivosVtMgl.setSecArchivo(maximo);
                    cmtArchivosVtMgl.setOrigenArchivos(origen);
                    cmtArchivosVtMgl = archivosVtMglManager.create(cmtArchivosVtMgl, usuario, perfil);

                    respuesta = cmtArchivosVtMgl.getIdArchivosVt() != null;

                } else {
                    LOGGER.info("Empiezan los 10 segundos");
                    esperar(10);

                    responseBuscar = cliente.find(requestBuscar, urlBuscar);
                    ActionStatusEnumType statusBuscar2 = responseBuscar.getActionStatus();

                    if (statusBuscar2.value().equalsIgnoreCase("success")) {

                        for (DocumentType documentType1 : responseBuscar.getDocument()) {
                            if (documentType1.getField().size() > 0) {

                                for (FieldType atributos : documentType1.getField()) {
                                    if (atributos.getName().equalsIgnoreCase("xdURLDocumento")) {
                                        urlArchivo = atributos.getValue();
                                    }
                                }

                            }

                        }
                    }
                    if (urlArchivo != null && !urlArchivo.isEmpty()) {
                        CmtArchivosVtMgl cmtArchivosVtMgl = new CmtArchivosVtMgl();
                        cmtArchivosVtMgl.setVisitaTecnicaMglobj(visitaTecnicaMgl);
                        cmtArchivosVtMgl.setOrdenTrabajoMglobj(visitaTecnicaMgl.getOtObj());
                        cmtArchivosVtMgl.setRutaArchivo(urlArchivo);
                        cmtArchivosVtMgl.setNombreArchivo(archivo.getFileName());
                        cmtArchivosVtMgl.setSecArchivo(maximo);
                        cmtArchivosVtMgl.setOrigenArchivos(origen);
                        cmtArchivosVtMgl = archivosVtMglManager.create(cmtArchivosVtMgl, usuario, perfil);

                        respuesta = cmtArchivosVtMgl.getIdArchivosVt() != null;
                    } else {
                        respuesta = false;
                    }
                }
            } else {
                respuesta = false;
            }

        } catch (ApplicationException | IOException  ex) {
            String msg = "Error al momento de cargar el archivo. EX000 '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, ex);
        }
        return respuesta;
        
        
    }

    public void esperar(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
        }
    }

    public CmtTecnologiaSubMgl crearRegistroTecnologiaSubXAcometida(CmtSubEdificiosVt subEdiVt,
            String usuario, int perfil,
            CmtOrdenTrabajoMgl otAcometida) throws ApplicationException {

        CmtTecnologiaSubMglManager tecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
        CmtBasicaMgl basicaMglTipoFlujo = otAcometida.getTipoOtObj().getTipoFlujoOt();
        CmtEstadoxFlujoMglManager estadoxFlujoMglManager = new CmtEstadoxFlujoMglManager();
        CmtTecnologiaSubMgl tecnologiaSubMglEdifGeneral;
        CmtEstadoxFlujoMgl cmtEstadoxFlujoMgl = estadoxFlujoMglManager.findByTipoFlujoAndEstadoInt(
                basicaMglTipoFlujo, otAcometida.getEstadoInternoObj(),
                otAcometida.getBasicaIdTecnologia());
        List<CmtTecnologiaSubMgl> tecnologiasSub
                = tecnologiaSubMglManager.findTecnoSubBySubEdiTec(
                        subEdiVt.getSubEdificioObj(),
                        subEdiVt.getVtObj().getOtObj().getBasicaIdTecnologia());
        CmtTecnologiaSubMgl tecnologiaSubMgl;
       
        CmtVisitaTecnicaMgl visita = subEdiVt.getVtObj();
        if (tecnologiasSub == null || tecnologiasSub.isEmpty()) {
            tecnologiaSubMgl
                    = tecnologiaSubMglManager.generarTecnologiaSub(subEdiVt.getSubEdificioObj(),
                            visita, subEdiVt.getVtObj().getOtObj(), subEdiVt);
            tecnologiaSubMgl.setCostoVt(BigDecimal.ZERO);
            tecnologiaSubMgl.setCostoVisitaTecnica(BigDecimal.ZERO);
        } else {
            tecnologiaSubMgl = tecnologiasSub.get(0);
            if (cmtEstadoxFlujoMgl != null && cmtEstadoxFlujoMgl.getCambiaCmEstadoObj() != null && cmtEstadoxFlujoMgl.getCambiaCmEstadoObj().getBasicaId() != null) {
                tecnologiaSubMgl.setBasicaIdEstadosTec(cmtEstadoxFlujoMgl.getCambiaCmEstadoObj());
            }
            if (subEdiVt.getNodo() != null) {
                tecnologiaSubMgl.setNodoId(subEdiVt.getNodo());
            }
            tecnologiaSubMgl.setVisitaTecnica(visita);
            tecnologiaSubMgl.setOrdenTrabajo(visita.getOtObj());
            tecnologiaSubMgl.setFlagVisitaTecnica("Y");
            tecnologiaSubMgl.setFechaVisitaTecnica(visita.getFechaEntregaEdificio());
        }

        tecnologiaSubMgl.setTipoConfDistribObj(visita.getTipoConfDistribObj());
        tecnologiaSubMgl.setAlimentElectObj(visita.getAlimentElectObj());
        tecnologiaSubMgl.setTipoDistribucionObj(visita.getTipoDistribucionObj());
        tecnologiaSubMgl.setManejoAscensoresObj(visita.getManejoAscensoresObj());
        tecnologiaSubMgl.setVoltaje(visita.getVoltaje() != null
                ? visita.getVoltaje() : "NA");
        tecnologiaSubMgl.setOtroTipoDistribucion(visita.getOtroTipoDistribucion() != null
                ? visita.getOtroTipoDistribucion() : "NA");
        tecnologiaSubMgl.setTapsExternos(visita.getTapsExternos() != null
                ? visita.getTapsExternos() : "NA");
        tecnologiaSubMgl.setMarca(visita.getMarca() != null
                ? visita.getMarca() : "NA");
        tecnologiaSubMgl.setTelefono(visita.getTelefono() != null ? visita.getTelefono() : "0000000");
        tecnologiaSubMgl.setAcondicionamientos(visita.getAcondicionamientos() != null ? visita.getAcondicionamientos() : "NA");
          
        // actualizacion del edificio general para unico edificio o multiedificio
       List<CmtTecnologiaSubMgl> tecnologiasSubEdifGeneral
             = tecnologiaSubMglManager.findTecnoSubBySubEdiTec(
                     subEdiVt.getSubEdificioObj().getCmtCuentaMatrizMglObj().getSubEdificioGeneral(),
                     subEdiVt.getVtObj().getOtObj().getBasicaIdTecnologia());
          //si la tecnologia viene de la creacion de la cm             
        if (!tecnologiasSubEdifGeneral.isEmpty()) {
            tecnologiaSubMglEdifGeneral = tecnologiasSubEdifGeneral.get(0);
            CmtTecnologiaSubMglManager tecnoEdifGeneralManager = new CmtTecnologiaSubMglManager();
             CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
              CmtBasicaMgl basicaTipoEdificio
                        = cmtBasicaMglManager.findByCodigoInternoApp(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO);
            if (( subEdiVt.getSubEdificioObj().getCmtCuentaMatrizMglObj().getSubEdificioGeneral().getEstadoSubEdificioObj().getBasicaId().compareTo(basicaTipoEdificio.getBasicaId())) == 0) {
                tecnologiaSubMglEdifGeneral.setBasicaIdEstadosTec(basicaTipoEdificio);
                tecnoEdifGeneralManager.actualizar(tecnologiaSubMglEdifGeneral, usuario, perfil);
            } else {
                if(cmtEstadoxFlujoMgl != null && cmtEstadoxFlujoMgl.getCambiaCmEstadoObj() != null && cmtEstadoxFlujoMgl.getCambiaCmEstadoObj().getBasicaId() != null){
                tecnologiaSubMglEdifGeneral.setBasicaIdEstadosTec(cmtEstadoxFlujoMgl.getCambiaCmEstadoObj());
                }
                 tecnoEdifGeneralManager.actualizar(tecnologiaSubMglEdifGeneral, usuario, perfil);
            }
        } else {
            //si la tecnologia no viene con la creacion de la cm
            CmtTecnologiaSubMglManager tecnoEdifGeneralManager = new CmtTecnologiaSubMglManager();
            CmtTecnologiaSubMgl cmtTecnologiaSubMgl = new CmtTecnologiaSubMgl();
            CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
            CmtBasicaMgl basicaTipoEdificio
                        = cmtBasicaMglManager.findByCodigoInternoApp(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO);
            // tecnologia edificio general multiedificio
            if (subEdiVt != null && subEdiVt.getSubEdificioObj() != null
                    && subEdiVt.getSubEdificioObj().getCmtCuentaMatrizMglObj() != null
                    && subEdiVt.getSubEdificioObj().getCmtCuentaMatrizMglObj().getSubEdificioGeneral() != null
                    && subEdiVt.getSubEdificioObj().getCmtCuentaMatrizMglObj().getSubEdificioGeneral().getEstadoSubEdificioObj() != null
                    && subEdiVt.getSubEdificioObj().getCmtCuentaMatrizMglObj().getSubEdificioGeneral().getEstadoSubEdificioObj().getIdentificadorInternoApp() != null
                    && subEdiVt.getSubEdificioObj().getCmtCuentaMatrizMglObj().getSubEdificioGeneral().getEstadoSubEdificioObj().getIdentificadorInternoApp().equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                cmtTecnologiaSubMgl.setBasicaIdEstadosTec(basicaTipoEdificio);
                cmtTecnologiaSubMgl.setBasicaIdTecnologias(subEdiVt.getVtObj().getOtObj().getBasicaIdTecnologia());
                cmtTecnologiaSubMgl.setSubedificioId(subEdiVt.getSubEdificioObj().getCmtCuentaMatrizMglObj().getSubEdificioGeneral());
                tecnoEdifGeneralManager.crear(cmtTecnologiaSubMgl, usuario, perfil);
            } 
        }
        
        if (tecnologiaSubMgl.getTecnoSubedificioId() != null) {
          
            return tecnologiaSubMglManager.actualizar(tecnologiaSubMgl, usuario, perfil);
        } else {
            return tecnologiaSubMglManager.crear(tecnologiaSubMgl, usuario, perfil);
        }
    }

    public CmtVisitaTecnicaMgl findVtById(BigDecimal idvisitaTecnicaMgl) throws ApplicationException {
        CmtVisitaTecnicaMgl cmtVisitaTecnicaMgl;
        CmtVisitaTecnicaMglDaoImpl daoImpl = new CmtVisitaTecnicaMglDaoImpl();
        cmtVisitaTecnicaMgl = daoImpl.findVtById(idvisitaTecnicaMgl);
        return cmtVisitaTecnicaMgl;
    }

   public boolean cambioDireccionHhppSalYCamp(CmtSubEdificiosVt subEdificiosVt, String usuario, CmtCuentaMatrizMgl cmtCuentaMatrizMgl) throws ApplicationException, ApplicationException {
        boolean respExitosa = false;
        List<HhppMgl> listhhppMglSalyCamp = null;
        HhppMglManager hhppMglManagerCM = new HhppMglManager();
        CmtTecnologiaSubMglManager tecnologiaSubCM = new CmtTecnologiaSubMglManager();
        DireccionRRManager dirRRManager = new DireccionRRManager(true);
        CmtSubEdificioMgl cmtSubEdificioMgl = cmtSubEdificioMglManager.findSubEdificioGeneralByCuentaMatriz(cmtCuentaMatrizMgl);
        List<CmtTecnologiaSubMgl> listCmtTecnologiaSubMgl = tecnologiaSubCM.findTecnoSubBySubEdi(cmtSubEdificioMgl);
        if(!listCmtTecnologiaSubMgl.isEmpty()){
        for (CmtTecnologiaSubMgl cmtTecnologiaSubMgl :listCmtTecnologiaSubMgl) {
            if (cmtTecnologiaSubMgl.getBasicaIdEstadosTec() != null) {
                if (cmtSubEdificioMgl.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
                        && cmtSubEdificioMgl.getEstadoSubEdificioObj().getIdentificadorInternoApp().equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                    listhhppMglSalyCamp = hhppMglManagerCM.findBy_SubEdifi_TecSubEdifi(cmtSubEdificioMgl.getSubEdificioId(), cmtTecnologiaSubMgl.getTecnoSubedificioId());
                }
                String nombreCalleNew = "";
                if (subEdificiosVt.getDireccionSubEdificio() != null) {
                    nombreCalleNew = cmtCuentaMatrizMgl.getDireccionPrincipal().getCalleRr() + " EN " + subEdificiosVt.getCalleRr() + " " + subEdificiosVt.getUnidadRr();
                    if (nombreCalleNew.length() > 50) {
                        nombreCalleNew = nombreCalleNew.substring(0, 49);
                    }
                } else {
                    if (nombreCalleNew.length() > 50) {
                        nombreCalleNew = nombreCalleNew.substring(0, 49);
                    } else {
                        nombreCalleNew = cmtCuentaMatrizMgl.getDireccionPrincipal().getCalleRr().toUpperCase() + " " + subEdificiosVt.getNombramientoRr().toUpperCase();
                    }
                }

                if (listhhppMglSalyCamp != null && !listhhppMglSalyCamp.isEmpty()) {
                    dirRRManager.cambiarDirHHPPRR_CM(listhhppMglSalyCamp.get(0).getNodId().getComId().getCodigoRr(), listhhppMglSalyCamp.get(0).getNodId().getComId().getRegionalRr().getCodigoRr(),
                            listhhppMglSalyCamp.get(0).getHhpPlaca(), listhhppMglSalyCamp.get(0).getHhpCalle(), listhhppMglSalyCamp.get(0).getHhpApart(),
                            listhhppMglSalyCamp.get(0).getNodId().getComId().getCodigoRr(), listhhppMglSalyCamp.get(0).getNodId().getComId().getRegionalRr().getCodigoRr(),
                            listhhppMglSalyCamp.get(0).getHhpPlaca(), nombreCalleNew, listhhppMglSalyCamp.get(0).getHhpApart(), "HHPP CREADO CM", usuario, "Visita Tecnica",
                            "campoUno", cmtCuentaMatrizMgl.getDireccionPrincipal().getCodTipoDir(), listhhppMglSalyCamp.get(0).getNodId().getComId());
                    listhhppMglSalyCamp.get(0).setHhppSCProcesado("1");
                    hhppMglManagerCM.update(listhhppMglSalyCamp.get(0));
                    LOGGER.error("HHPP CREADO CM " + listhhppMglSalyCamp.get(0).getHhpApart());
                    dirRRManager.cambiarDirHHPPRR_CM(listhhppMglSalyCamp.get(1).getNodId().getComId().getCodigoRr(), listhhppMglSalyCamp.get(1).getNodId().getComId().getRegionalRr().getCodigoRr(),
                            listhhppMglSalyCamp.get(1).getHhpPlaca(), listhhppMglSalyCamp.get(1).getHhpCalle(), listhhppMglSalyCamp.get(1).getHhpApart(),
                            listhhppMglSalyCamp.get(1).getNodId().getComId().getCodigoRr(), listhhppMglSalyCamp.get(1).getNodId().getComId().getRegionalRr().getCodigoRr(),
                            listhhppMglSalyCamp.get(1).getHhpPlaca(), nombreCalleNew, listhhppMglSalyCamp.get(1).getHhpApart(), "HHPP CREADO CM", usuario, "Visita Tecnica",
                            "campoUno", cmtCuentaMatrizMgl.getDireccionPrincipal().getCodTipoDir(), listhhppMglSalyCamp.get(1).getNodId().getComId());
                    listhhppMglSalyCamp.get(1).setHhppSCProcesado("1");
                    hhppMglManagerCM.update(listhhppMglSalyCamp.get(1));
                    respExitosa = true;
                    LOGGER.error("HHPP CREADO CM " + listhhppMglSalyCamp.get(1).getHhpApart());
                }

            }

        }
        }
        return respExitosa;
    }

    public CmtVisitaTecnicaMgl update(CmtVisitaTecnicaMgl cmtVisitaTecnicaMgl,
            String usuario, int perfil) throws ApplicationException {
        return updateCm(cmtVisitaTecnicaMgl, usuario, perfil);

    }
    
    public void crearInfoTecnica(List<CmtTecnologiaSubMgl> listTecnologias, String user, int perfil)
            throws ApplicationException {

        CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();
        CmtInfoTecnicaMglManager infoTecnicaMglManager = new CmtInfoTecnicaMglManager();
        CmtBasicaMgl cmtBasicaMglInfoN1Nodo = basicaMglManager.findByCodigoInternoApp(Constant.NIVEL_UNO_NODO);
        CmtBasicaMgl cmtBasicaMglInfoN1Nap = basicaMglManager.findByCodigoInternoApp(Constant.NIVEL_UNO_NAP);
        CmtInfoTecnicaMgl cmtInfoTecni;

        if (listTecnologias != null
                && !listTecnologias.isEmpty()) {

            for (CmtTecnologiaSubMgl tecnoSub : listTecnologias) {

                if (tecnoSub != null
                        && tecnoSub.getTecnoSubedificioId() != null) {

                    cmtInfoTecni = infoTecnicaMglManager.
                            findBySubEdificioIdAndTecnoSub(tecnoSub.getSubedificioId(), tecnoSub);

                    if (cmtInfoTecni == null) {
                        
                        cmtInfoTecni = new CmtInfoTecnicaMgl();
                        cmtInfoTecni.setIdSubedificio(tecnoSub.getSubedificioId());

                        if (tecnoSub.getBasicaIdTecnologias().getIdentificadorInternoApp().equalsIgnoreCase("@B_FOG")) {
                            cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1Nap);
                        } else if (tecnoSub.getBasicaIdTecnologias().getIdentificadorInternoApp().equalsIgnoreCase("@B_FOU")) {
                            cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1Nap);
                        } else if (tecnoSub.getBasicaIdTecnologias().getIdentificadorInternoApp().equalsIgnoreCase("@B_HOST")) {
                            cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1Nodo);
                        } else if (tecnoSub.getBasicaIdTecnologias().getIdentificadorInternoApp().equalsIgnoreCase("@B_BI")) {
                            cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1Nodo);
                        } else if (tecnoSub.getBasicaIdTecnologias().getIdentificadorInternoApp().equalsIgnoreCase("@B_DTH")) {
                            cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1Nodo);
                        } else if (tecnoSub.getBasicaIdTecnologias().getIdentificadorInternoApp().equalsIgnoreCase("@B_FTH")) {
                            cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1Nodo);
                        } else if (tecnoSub.getBasicaIdTecnologias().getIdentificadorInternoApp().equalsIgnoreCase("@B_UNI")) {
                            cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1Nodo);
                        } else if (tecnoSub.getBasicaIdTecnologias().getIdentificadorInternoApp().equalsIgnoreCase("@B_FOR")) {
                            cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1Nap);
                        }
                        
                        if (cmtInfoTecni != null && cmtInfoTecni.getBasicaIdInfoN1() != null) {
                            cmtInfoTecni.setFechaCreacion(new Date());
                            cmtInfoTecni.setEstadoRegistro(1);
                            cmtInfoTecni.setPerfilEdicion(0);
                            cmtInfoTecni.setTecnologiaSubMglObj(tecnoSub);
                            cmtInfoTecni = infoTecnicaMglManager.create(cmtInfoTecni, user, perfil);                           
                        }
                    }

                }

            }
        }
    }

}
