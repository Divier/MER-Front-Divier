package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.cmas400.ejb.request.RequestDataManttoEdificio;
import co.com.claro.cmas400.ejb.request.RequestDataManttoSubEdificios;
import co.com.claro.cmas400.ejb.respons.ResponseDataManttoEdificio;
import co.com.claro.cmas400.ejb.respons.ResponseDataManttoSubEdificios;
import co.com.claro.cmas400.ejb.respons.ResponseManttoEdificioList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoSubEdificiosList;
import co.com.claro.mgl.businessmanager.address.HhppMglManager;
import co.com.claro.mgl.businessmanager.address.NodoMglManager;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.dtos.CmtMasivoCuentaMatrizDtoMgl;
import co.com.claro.mgl.ejb.wsclient.rest.cm.EnumeratorServiceName;
import co.com.claro.mgl.ejb.wsclient.rest.cm.RestClientCuentasMatrices;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBlackListMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCompaniaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoCompaniaMgl;
import co.com.claro.mgl.rest.dtos.MasivoModificacionDto;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import com.sun.jersey.api.client.UniformInterfaceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author valbuenayf
 */
public class CmtRunableMasivoCuentaMatrizManager extends Observable implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(CmtRunableMasivoCuentaMatrizManager.class);
    private String usuario;
    private int perfil;
    private Integer tipoModificacion;
    private List<MasivoModificacionDto> listaModificacion;
    private HhppMglManager hhppMglManager;
    private CmtTecnologiaSubMglManager tecnologiaSubManager;
    private CmtCuentaMatrizMglManager cuentaMatrizManager;
    private CmtSubEdificioMglManager subEdificioManager;
    private CmtBasicaMglManager basicaManager;
    private NodoMglManager nodoMglManager;
    private CmtCompaniaMglManager companiaManager;
    private List<CmtBasicaMgl> listBasicaTecnologia;
    private List<CmtBasicaMgl> listBasicaTipoCcmm;
    private List<CmtBasicaMgl> listBasicaBlackListTec;
    private List<CmtBasicaMgl> listTipoProyecto;
    private List<CmtBasicaMgl> listOrigenDatos;
    private List<CmtBasicaMgl> listConfiguracion;
    private List<CmtBasicaMgl> listAlimentacion;
    private List<CmtBasicaMgl> listDistribucion;
    private List<CmtBasicaMgl> listEstado;
    private List<CmtCompaniaMgl> listAdmin;
    private List<CmtCompaniaMgl> listAscensor;
    private List<CmtCompaniaMgl> listConstructoras;
    private String nombreArchivo;
    ParametrosMglManager parametrosMglManager;
    RestClientCuentasMatrices restClientCuentasMatrices;
    String BASE_URI;

    

    public CmtRunableMasivoCuentaMatrizManager(){

    }

    public CmtRunableMasivoCuentaMatrizManager(Observer observer, List<MasivoModificacionDto> listaModificacion, String usuario, Integer tipoModificacion,
            int perfil, String nombreArchivo) {
        this.listaModificacion = listaModificacion;
        this.usuario = usuario;
        this.perfil = perfil;
        this.tipoModificacion = tipoModificacion;
        this.nombreArchivo = nombreArchivo;
        tecnologiaSubManager = new CmtTecnologiaSubMglManager();
        cuentaMatrizManager = new CmtCuentaMatrizMglManager();
        subEdificioManager = new CmtSubEdificioMglManager();
        basicaManager = new CmtBasicaMglManager();
        companiaManager = new CmtCompaniaMglManager();
        nodoMglManager = new NodoMglManager();
        hhppMglManager = new HhppMglManager();
        
        // managed bean modificacion masivo
        this.addObserver(observer);
    }

    @Override
    public void run() {
        List<MasivoModificacionDto> cmMgls = new ArrayList<MasivoModificacionDto>();
        cargarListas();
        boolean error;
        boolean errorUno = false;
        boolean errorDos = false;
        boolean errorTres = false;
        int count = 0;
        try {
            CmtMasivoCuentaMatrizDtoMgl.cleanBeforeStart();
            CmtMasivoCuentaMatrizDtoMgl.startProcess(usuario);
        } catch (ApplicationException ex) {
            LOGGER.error("error en run de CmtRunableReporteCuentaMatrizManager" + ex);
        }

        CmtMasivoCuentaMatrizDtoMgl.setNumeroRegistrosAProcesar(this.listaModificacion.size());
        CmtMasivoCuentaMatrizDtoMgl.setNombreArchivo(this.nombreArchivo);
        try {
            parametrosMglManager = new ParametrosMglManager();
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
            restClientCuentasMatrices = new RestClientCuentasMatrices(BASE_URI);
            boolean habilitarRR = false;
 
            ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(co.com.claro.mgl.utils.Constant.HABILITAR_RR);
            if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
                habilitarRR = true;
            }
            //Inicio modificacion general
            if (this.tipoModificacion == 1) {
                List<BigDecimal> listIdCuentaMatriz = new ArrayList<BigDecimal>();
                for (MasivoModificacionDto m : this.listaModificacion) {
                    try{
                        error = false;
                        errorUno = false;
                        errorDos = false;
                        errorTres = false;
                        StringBuilder errorSubEdificio = new StringBuilder();
                        StringBuilder errorTecnologiaSub = new StringBuilder();
                        StringBuilder errorCuentaMatriz = new StringBuilder();
                        // Inicio cambio atributos subedificio
                        boolean actualizarSubEdi = true;
                        CmtCompaniaMgl adminitracion = null;
                        CmtCompaniaMgl ascensor = null;
                        CmtBasicaMgl proyecto = null;
                        CmtBasicaMgl datos = null;
                        CmtBasicaMgl tecnologia;
                        
                        CmtBasicaMgl tipoCcmm = null;
                        CmtBasicaMgl blacklistTec = null;
                        BigDecimal idBlacklistTec=null;
                        CmtCompaniaMgl constructora = null;
                        int controlBlack=0;
                        
                        tecnologia = buscarBasica(m.getTipoTecnologia(), listBasicaTecnologia);
                        boolean actualizarRr = false;
                        if (tecnologia != null) {
                            if (tecnologia.getListCmtBasicaExtMgl() != null
                                    && !tecnologia.getListCmtBasicaExtMgl().isEmpty()) {
                                for (CmtBasicaExtMgl b : tecnologia.getListCmtBasicaExtMgl()) {
                                    if (b.getIdTipoBasicaExt() != null) {
                                        if (b.getIdTipoBasicaExt().getCampoEntidadAs400().equals(Constant.SINCRONIZA_RR)
                                                && b.getValorExtendido().equals("Y")) {
                                            actualizarRr = true;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        boolean actualizarCuentaMatriz = true;
                        CmtCuentaMatrizMgl cuentaMatriz = cuentaMatrizManager.findByIdCM(m.getIdCuentamatriz());
                        if ( cuentaMatriz != null ){
                            if (m.getNombreCuenta().isEmpty()){
                                actualizarCuentaMatriz = false;
                                errorTres = true;
                                errorCuentaMatriz.append("Nombre de la cuenta matriz no puede ser vacia; id cuenta matriz :").append(m.getIdCuentamatriz());
                            }
                        }else{    
                            actualizarCuentaMatriz = false;
                            errorTres = true;
                            errorCuentaMatriz.append("Cuenta matriz no existe id cuenta matriz mgl: ").append(m.getIdCuentamatriz());
                        }
                        CmtSubEdificioMgl subEdificio = subEdificioManager.findById(m.getIdSubedificio());
                        //verifico si hay algun cambio en los datos; si todos son iguales no actualiza datos
                        if (subEdificio != null) {
                            if (m.getCompaniaAdministracion() != null && !m.getCompaniaAdministracion().trim().isEmpty()) {
                                adminitracion = buscarCompania(m.getCompaniaAdministracion(), listAdmin);
                                if (adminitracion == null) {
                                    actualizarSubEdi = false;
                                    errorUno = true;
                                    errorSubEdificio.append(Constant.MSN_ERROR_COMPANIA_ADMIN).append(m.getCompaniaAdministracion());
                                }
                            }else{
                                LOGGER.error("Compañia administracion es vacio");
                            }
                            if (m.getCompaniaAscensor() != null && !m.getCompaniaAscensor().trim().isEmpty()) {
                                ascensor = buscarCompania(m.getCompaniaAscensor(), listAscensor);
                                if (ascensor == null) {
                                    actualizarSubEdi = false;
                                    errorUno = true;
                                    errorSubEdificio.append(Constant.MSN_ERROR_COMPANIA_ASCENSOR).append(m.getCompaniaAscensor());
                                }
                            }else{
                               LOGGER.error("Compañia ascensor es vacio");  
                            }
                            if (m.getTipoProyecto() != null && !m.getTipoProyecto().trim().isEmpty()) {
                                proyecto = buscarBasica(m.getTipoProyecto(), listTipoProyecto);
                                if (proyecto == null) {
                                    actualizarSubEdi = false;
                                    errorUno = true;
                                    errorSubEdificio.append(Constant.MSN_ERROR_TIPO_PROYECTO).append(m.getTipoProyecto());
                                }
                            }else{
                              LOGGER.error("Tipo Proyecto es vacio");
                            }
                            if (m.getOrigenDatos() != null && !m.getOrigenDatos().isEmpty()) {
                                datos = buscarBasica(m.getOrigenDatos(), listOrigenDatos);
                                if (datos == null) {
                                    actualizarSubEdi = false;
                                    errorUno = true;
                                    errorSubEdificio.append(Constant.MSN_ERROR_ORIGEN_DATOS).append(m.getOrigenDatos());
                                }
                            }else{
                              LOGGER.error("Origen Datos es vacio");
                            }
                             if (m.getCompaniaConstructora() != null && !m.getCompaniaConstructora().trim().isEmpty()) {
                                constructora = buscarCompania(m.getCompaniaConstructora(), listConstructoras);
                                if (constructora == null) {
                                    actualizarSubEdi = false;
                                    errorUno = true;
                                    errorSubEdificio.append(Constant.MSN_ERROR_COMPANIA_CONSTRUCTORA).append(m.getCompaniaConstructora());
                                }
                            }else{
                               LOGGER.error("Compañia constructora es vacio");  
                            }
                            if (m.getTipoCcmm() != null && !m.getTipoCcmm().trim().isEmpty()) {
                                tipoCcmm = buscarBasica(m.getTipoCcmm(), listBasicaTipoCcmm);
                                if (tipoCcmm == null) {
                                    actualizarSubEdi = false;
                                    errorDos = true;
                                    errorSubEdificio.append(Constant.MSN_ERROR_TIPO_CCMM).append(m.getTipoCcmm());
                                }
                            }else{
                               LOGGER.error("Tipo Ccmm es vacio");  
                            } 
                             if (m.getBlacklistTecnologia() != null && !m.getBlacklistTecnologia().trim().isEmpty()) {
                                blacklistTec = buscarBasica(m.getBlacklistTecnologia(), listBasicaBlackListTec);

                                if (blacklistTec == null) {
                                    actualizarSubEdi = false;
                                    errorDos = true;
                                    errorSubEdificio.append(Constant.MSN_ERROR_BLACKLIST_TECNOLOGIA).append(m.getBlacklistTecnologia());
                                }else {
                                     if (m.getIdBlacklistTecnologia() != null) {
                                         //Actualiza el blacklist
                                         controlBlack = 1;
                                         idBlacklistTec = m.getIdBlacklistTecnologia();
                                     } else {
                                         //valida y crea el blacklist  
                                         controlBlack = 3;
                                     }
                                 }
                            }else {
                                LOGGER.error("Blacklist tecnologia es vacio");
                                if (m.getIdBlacklistTecnologia() != null) {
                                    //Actualizan en blanco
                                    idBlacklistTec = m.getIdBlacklistTecnologia();
                                    controlBlack=2;
                                }
                            }
                        } else {
                            actualizarSubEdi = false;
                            errorUno = true;
                            errorSubEdificio.append("SubEdificion no encontrado: ").append(m.getIdSubedificio());
                        }
                                               
                        boolean actualizarTecnologiaSub = true;
                        NodoMgl nodo = null;
                        CmtBasicaMgl estado = null;
                        CmtBasicaMgl configuracion = null;
                        CmtBasicaMgl alimentacion = null;
                        CmtBasicaMgl distribucion = null;
                        CmtTecnologiaSubMgl tecnologiaSub = tecnologiaSubManager.findIdTecnoSub(m.getIdTecnoSubedificio());
                        if (tecnologiaSub != null) {
                            if (m.getCodigoNodo() != null && !m.getCodigoNodo().trim().isEmpty()) {
                                if (tecnologia == null) {
                                    actualizarTecnologiaSub = false;
                                    errorDos = true;
                                    errorTecnologiaSub.append(Constant.MSN_ERROR_TIPO_TECNOLOGIA).append(m.getEstadoTecnologia());
                                } else {
                                    nodo = buscarNodo(m.getCodigoNodo(), new BigDecimal(m.getIdCentroPoblado()), tecnologia.getBasicaId());
                                }
                                if (nodo == null) {
                                    actualizarTecnologiaSub = false;
                                    errorDos = true;
                                    errorTecnologiaSub.append(Constant.MSN_ERROR_CODIGO_NODO).append(m.getCodigoNodo());
                                }
                            }else{
                                 LOGGER.error("Codigo nodo es vacio");
                            }
                            if (m.getEstadoTecnologia() != null && !m.getEstadoTecnologia().trim().isEmpty()) {
                                estado = buscarBasica(m.getEstadoTecnologia(), listEstado);
                                if (estado == null) {
                                    actualizarTecnologiaSub = false;
                                    errorDos = true;
                                    errorTecnologiaSub.append(Constant.MSN_ERROR_ESTADO_TECNOLOGIA).append(m.getEstadoTecnologia());
                                }
                            }else{
                               LOGGER.error("Estado tecnologia  es vacio");  
                            }
                            if (m.getTipoConfiguracion() != null && !m.getTipoConfiguracion().trim().isEmpty()) {
                                configuracion = buscarBasica(m.getTipoConfiguracion(), listConfiguracion);
                                if (configuracion == null) {
                                    actualizarTecnologiaSub = false;
                                    errorDos = true;
                                    errorTecnologiaSub.append(Constant.MSN_ERROR_CONFIGURACION).append(m.getTipoConfiguracion());
                                }
                            }else{
                               LOGGER.error("Tipo configuracion  es vacio");  
                            }
                            if (m.getAlimentacionElectrica() != null && !m.getAlimentacionElectrica().trim().isEmpty()) {
                                alimentacion = buscarBasica(m.getAlimentacionElectrica(), listAlimentacion);
                                if (alimentacion == null) {
                                    actualizarTecnologiaSub = false;
                                    errorDos = true;
                                    errorTecnologiaSub.append(Constant.MSN_ERROR_ALIMENTACION_ELECTRICA).append(m.getAlimentacionElectrica());
                                }
                            }else{
                                LOGGER.error("Alimentacion electrica es vacio");  
                            }
                            if (m.getTipoDistribucion() != null && !m.getTipoDistribucion().isEmpty()) {
                                distribucion = buscarBasica(m.getTipoDistribucion(), listDistribucion);
                                if (distribucion == null) {
                                    actualizarTecnologiaSub = false;
                                    errorDos = true;
                                    errorTecnologiaSub.append(Constant.MSN_ERROR_DISTRIBUCION).append(m.getTipoDistribucion());
                                }
                            }else{
                                LOGGER.error("Tipo distribucion  es vacio");  
                            }
                        } else {
                            actualizarTecnologiaSub = false;
                            errorDos = true;
                            errorTecnologiaSub.append("Codigo de Tecnologia Sub-Edificio no encontrado: ").append(m.getIdTecnoSubedificio());
                        }
                        boolean actSubEdificio = false;
                        boolean actTecSubEdificio = false;
                        // Fin cambio atributos tecnologia_sub
                        // Inicio cambio atributos cuenta matriz
                        // se valida que la cuenta matriz ya se cambio el nombre
                        boolean actCuentMatriz_Nombre = false;
                        boolean actSubEdificio_CompaAdministra = false;
                        boolean actSubEdificio_CompaAcensores = false;
                        boolean actSubEdificio_Administrador = false;
                        boolean actSubEdificio_Telefono1 = false;
                        boolean actSubEdificio_Telefono2 = false;
                        boolean actSubEdificio_TipoProyecto = false;
                        boolean actSubEdificio_OrigenDatos = false;
                        
                        boolean actSubEdificio_CompaConstructora = false;
                        boolean actSubEdificio_TipoCcmm = false;
                        boolean actSubEdificio_BlacklistTec= false;
                        
                        if (actualizarSubEdi && actualizarTecnologiaSub && actualizarCuentaMatriz) {
                            if (subEdificio != null) {
                                //  cuenta matriz
                                if (subEdificio.getNombreSubedificio() == null) {
                                    if (m.getNombreCuenta() != null && !m.getNombreCuenta().isEmpty()) {
                                        actCuentMatriz_Nombre = true;
                                    }
                                } else {
                                    actCuentMatriz_Nombre = (subEdificio.getNombreSubedificio().compareTo(m.getNombreCuenta()) != 0
                                            && subEdificio.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null
                                            && subEdificio.getEstadoSubEdificioObj().getIdentificadorInternoApp().equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)||isUnicoEdificio(m.getIdCuentamatriz()));
                                }
                                //  sub-edificio

                                if (subEdificio.getCompaniaAdministracionObj() == null) {
                                    actSubEdificio_CompaAdministra = true;
                                } else {
                                    if (adminitracion != null) {
                                        actSubEdificio_CompaAdministra = subEdificio.getCompaniaAdministracionObj().getCompaniaId().compareTo(adminitracion.getCompaniaId()) != 0;
                                    } else {
                                        actSubEdificio_CompaAdministra = true;
                                    }

                                }

                                if (subEdificio.getCompaniaAscensorObj() == null) {
                                    actSubEdificio_CompaAcensores = true;
                                } else {
                                    if (ascensor != null) {
                                        actSubEdificio_CompaAcensores = subEdificio.getCompaniaAscensorObj().getCompaniaId().compareTo(ascensor.getCompaniaId()) != 0;
                                    } else {
                                        actSubEdificio_CompaAcensores = true;
                                    }
                                }
                                
                                if (subEdificio.getAdministrador() == null) {
                                    if (m.getAdministrador() != null && !m.getAdministrador().isEmpty()) {
                                        actSubEdificio_Administrador = true;
                                    }
                                } else {
                                    if (m.getAdministrador() != null && !m.getAdministrador().isEmpty()) {
                                        actSubEdificio_Administrador = subEdificio.getAdministrador().compareToIgnoreCase(m.getAdministrador()) != 0;
                                    } else {
                                        actSubEdificio_Administrador = true;
                                    }

                                }

                                if (subEdificio.getTelefonoPorteria() == null) {
                                    if (m.getTelefonoUno() != null && !m.getTelefonoUno().isEmpty()) {
                                        actSubEdificio_Telefono1 = true;
                                    }
                                } else {
                                    if (m.getTelefonoUno() != null && !m.getTelefonoUno().isEmpty()) {
                                        actSubEdificio_Telefono1 = subEdificio.getTelefonoPorteria().compareToIgnoreCase(m.getTelefonoUno()) != 0;
                                    } else {
                                        actSubEdificio_Telefono1 = true;
                                    }
                                }

                                if (subEdificio.getTelefonoPorteria2() == null) {
                                    if (m.getTelefonoDos() != null && !m.getTelefonoDos().isEmpty()) {
                                        actSubEdificio_Telefono2 = true;
                                    }
                                } else {
                                    if (m.getTelefonoDos() != null && !m.getTelefonoDos().isEmpty()) {
                                        actSubEdificio_Telefono2 = subEdificio.getTelefonoPorteria2().compareToIgnoreCase(m.getTelefonoDos()) != 0;
                                    } else {
                                        actSubEdificio_Telefono2 = true;
                                    }

                                }

                                if (subEdificio.getTipoProyectoObj() == null) {
                                    actSubEdificio_TipoProyecto = true;
                                } else {
                                    if (proyecto != null) {
                                        actSubEdificio_TipoProyecto = subEdificio.getTipoProyectoObj().getBasicaId().compareTo(proyecto.getBasicaId()) != 0;
                                    } else {
                                        actSubEdificio_TipoProyecto = true;
                                    }

                                }

                                if (subEdificio.getOrigenDatosObj() == null) {
                                    actSubEdificio_OrigenDatos = true;
                                } else {
                                    if (datos != null) {
                                        actSubEdificio_OrigenDatos = subEdificio.getOrigenDatosObj().getBasicaId().compareTo(datos.getBasicaId()) != 0;
                                    } else {
                                        actSubEdificio_OrigenDatos = true;
                                    }
                                }
                                
                                if (subEdificio.getCompaniaConstructoraObj() == null) {
                                    actSubEdificio_CompaConstructora = true;
                                } else {
                                    if (constructora != null) {
                                        actSubEdificio_CompaConstructora = subEdificio.getCompaniaConstructoraObj().getCompaniaId().compareTo(constructora.getCompaniaId()) != 0;
                                    } else {
                                        actSubEdificio_CompaConstructora = true;
                                    }
                                }

                                if (subEdificio.getTipoEdificioObj() == null) {
                                        actSubEdificio_TipoCcmm = true;
                                    } else {
                                        if (tipoCcmm != null) {
                                            actSubEdificio_TipoCcmm = subEdificio.getTipoEdificioObj().getBasicaId().compareTo(tipoCcmm.getBasicaId()) != 0;
                                        } else {
                                            actSubEdificio_TipoCcmm = true;
                                        }
                                    }

                                 if (subEdificio.getListCmtBlackListMgl() == null ||
                                         subEdificio.getListCmtBlackListMgl().isEmpty()) {
                                        actSubEdificio_BlacklistTec = true;
                                    } else {
                                        if (blacklistTec != null) {
                                            boolean control = false;
                                            for (CmtBlackListMgl l : subEdificio.getListCmtBlackListMgl()) {
                                                if (l.getBlackListObj().getBasicaId().compareTo(blacklistTec.getBasicaId()) == 0) {
                                                    control = true;
                                                }
                                            }
                                            actSubEdificio_BlacklistTec = !control;
                                        } else {
                                            //Eliminan el blacklist
                                            actSubEdificio_BlacklistTec = true;
                                        }
                                    }
                            }  

                            //  tecnologia sub-edificio
                            boolean actTecSubEdificio_Nodo = false;
                            boolean actTecSubEdificio_EstadoTecnolo = false;
                            boolean actTecSubEdificio_TipoConfDistri = false;
                            boolean actTecSubEdificio_AlimenElectric = false;
                            boolean actTecSubEdificio_TipoDistri = false;
                            
                            if (tecnologiaSub != null) {
                                if (tecnologiaSub.getNodoId() == null) {
                                    actTecSubEdificio_Nodo = true;
                                } else {
                                    if (nodo != null) {
                                        actTecSubEdificio_Nodo = tecnologiaSub.getNodoId().getNodId().compareTo(nodo.getNodId()) != 0;
                                    } else {
                                        actTecSubEdificio_Nodo = true;
                                    }

                                }
                                
                                if (tecnologiaSub.getBasicaIdEstadosTec() == null) {
                                    actTecSubEdificio_EstadoTecnolo = true;
                                } else {
                                    if (estado != null) {
                                        actTecSubEdificio_EstadoTecnolo = tecnologiaSub.getBasicaIdEstadosTec().getBasicaId().compareTo(estado.getBasicaId()) != 0;
                                    } else {
                                        actTecSubEdificio_EstadoTecnolo = true;
                                    }

                                }
                               
                                if (tecnologiaSub.getTipoConfDistribObj() == null) {
                                    actTecSubEdificio_TipoConfDistri = true;
                                } else {
                                    if (configuracion != null) {
                                        actTecSubEdificio_TipoConfDistri = tecnologiaSub.getTipoConfDistribObj().getBasicaId().compareTo(configuracion.getBasicaId()) != 0;
                                    } else {
                                        actTecSubEdificio_TipoConfDistri = true;
                                    }
                                }

                                if (tecnologiaSub.getAlimentElectObj() == null) {
                                    actTecSubEdificio_AlimenElectric = true;
                                } else {
                                    if (alimentacion != null) {
                                        actTecSubEdificio_AlimenElectric = tecnologiaSub.getAlimentElectObj().getBasicaId().compareTo(alimentacion.getBasicaId()) != 0;
                                    } else {
                                        actTecSubEdificio_AlimenElectric = true;
                                    }
                                }
                                
                                if (tecnologiaSub.getTipoDistribucionObj() == null) {
                                    actTecSubEdificio_TipoDistri = true;
                                } else {
                                    if (distribucion != null) {
                                        actTecSubEdificio_TipoDistri = tecnologiaSub.getTipoDistribucionObj().getBasicaId().compareTo(distribucion.getBasicaId()) != 0;
                                    } else {
                                        actTecSubEdificio_TipoDistri = true;
                                    }
                                }
                            }

                            actSubEdificio = actSubEdificio_CompaAdministra || actSubEdificio_CompaAcensores || actSubEdificio_Administrador
                                    || actSubEdificio_Telefono1 || actSubEdificio_Telefono2 || actSubEdificio_TipoProyecto || actSubEdificio_OrigenDatos
                                    || actCuentMatriz_Nombre;
                            actTecSubEdificio = actTecSubEdificio_Nodo || actTecSubEdificio_EstadoTecnolo || actTecSubEdificio_TipoConfDistri
                                    || actTecSubEdificio_AlimenElectric || actTecSubEdificio_TipoDistri;
                            if (actCuentMatriz_Nombre) {
                                if (!listIdCuentaMatriz.contains(m.getIdCuentamatriz())) {
                                    listIdCuentaMatriz.add(m.getIdCuentamatriz());
                                    String respuestaCuenta = actualizarCuentaMatriz(m.getIdCuentamatriz(), m.getNombreCuenta());
                                    if (respuestaCuenta != null) {
                                        errorTres = true;
                                        errorCuentaMatriz.append("Error Actualizando Cuenta Matriz: ").
                                                append(m.getNombreCuenta()).append(" con Id: ").
                                                append(m.getIdCuentamatriz());
                                        actCuentMatriz_Nombre = false;
                                    }
                                }
                            }
                            if (actTecSubEdificio) {
                                boolean modTecSubEdificio = false;
                                if (tecnologiaSub != null) {
                                    if (actTecSubEdificio_EstadoTecnolo) {
                                        tecnologiaSub.setBasicaIdEstadosTec(estado);
                                        modTecSubEdificio = true;
                                    }
                                    if (actTecSubEdificio_TipoConfDistri) {
                                        tecnologiaSub.setTipoConfDistribObj(configuracion);
                                        modTecSubEdificio = true;
                                    }
                                    if (actTecSubEdificio_AlimenElectric) {
                                        tecnologiaSub.setAlimentElectObj(alimentacion);
                                        modTecSubEdificio = true;
                                    }
                                    if (actTecSubEdificio_TipoDistri) {
                                        tecnologiaSub.setTipoDistribucionObj(distribucion);
                                        modTecSubEdificio = true;
                                    }
                                    if (actTecSubEdificio_Nodo) {
                                        boolean actualizaNodoSubEdificio = false;
                                        //Asigno el  nodo al subedificio 
                                        if (subEdificio != null) {
                                            if (habilitarRR) {
                                                //Consulto si son la misma tecnologia en MGL y en RR
                                                CmtCuentaMatrizMgl cuenta = cuentaMatrizManager.findByIdCM(m.getIdCuentamatriz());
                                                if (cuenta.isUnicoSubEdificioBoolean()) {
                                                    RequestDataManttoEdificio request = new RequestDataManttoEdificio();
                                                    request.setCodigoEdificio(cuenta.getNumeroCuenta().toString());

                                                    ResponseManttoEdificioList response
                                                            = restClientCuentasMatrices.callWebService(
                                                                    EnumeratorServiceName.CM_MANTTO_EDIFICIO_QUERY,
                                                                    ResponseManttoEdificioList.class,
                                                                    request);

                                                    if (response.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                                                        LOGGER.error(response.getMensaje());
                                                    } else {
                                                        List<ResponseDataManttoEdificio> lstDataManttoEdificios
                                                                = response.getListManttoEdificios();

                                                        if (lstDataManttoEdificios.size() > 0) {
                                                            ResponseDataManttoEdificio manttoEdificio = lstDataManttoEdificios.get(0);
                                                            String codigoNodo = manttoEdificio.getNodo().trim();
                                                            if (codigoNodo != null && !codigoNodo.isEmpty()) {
                                                                NodoMgl nodoMgl = nodoMglManager.findByCodigo(codigoNodo);
                                                                if (nodoMgl != null) {
                                                                    if (nodoMgl.getNodTipo().getBasicaId().
                                                                            compareTo(tecnologiaSub.getBasicaIdTecnologias().getBasicaId()) == 0) {
                                                                        //Si son las mismas tecnologias 
                                                                        subEdificio.setNodoObj(nodo);
                                                                        actualizaNodoSubEdificio = true;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    //es multiedificio  
                                                    RequestDataManttoSubEdificios request = new RequestDataManttoSubEdificios();
                                                    request.setCodigoCuenta(cuenta.getNumeroCuenta().toString());
                                                    ResponseManttoSubEdificiosList response
                                                            = restClientCuentasMatrices.callWebService(
                                                                    EnumeratorServiceName.CM_MANTTO_SUBEDIFICIOS_QUERY,
                                                                    ResponseManttoSubEdificiosList.class,
                                                                    request);
                                                    if (response.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                                                        LOGGER.error(response.getMensaje());
                                                    } else {
                                                        if (response.getListManttSubEdificios().size() > 0) {
                                                            for (ResponseDataManttoSubEdificios subedificios : response.getListManttSubEdificios()) {
                                                                if (subedificios.getCodigo().trim().
                                                                        equalsIgnoreCase(subEdificio.getCodigoRr())) {
                                                                    String codigoNodo = subedificios.getNodo().trim();
                                                                    if (codigoNodo != null && !codigoNodo.isEmpty()) {
                                                                        NodoMgl nodoMgl = nodoMglManager.findByCodigo(codigoNodo);
                                                                        if (nodoMgl != null) {
                                                                            if (nodoMgl.getNodTipo().getBasicaId().
                                                                                    compareTo(tecnologiaSub.getBasicaIdTecnologias().getBasicaId()) == 0) {
                                                                                //Si son las mismas tecnologias 
                                                                                subEdificio.setNodoObj(nodo);
                                                                                //espinosadie valida cuando un subedificio tiene propia direccion para mantener el estandar de RR 
                                                                                if (subEdificio.getDireccion() != null && !subEdificio.getDireccion().isEmpty()) {
                                                                                    subEdificio.setNombreEntSubedificio(subedificios.getDescripcion());//
                                                                                }
                                                                                actualizaNodoSubEdificio = true;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } else {
                                                subEdificio.setNodoObj(nodo);
                                                actualizaNodoSubEdificio = true;
                                            }
                                        } else {
                                            LOGGER.error("Error no se encontro el CmtSubEdificioMgl con el id " + m.getIdSubedificio());
                                        }
                                        boolean actNodo = true;
                                        try {
                                            List<HhppMgl> homePass = null;
                                            if (subEdificio != null) {
                                                homePass = hhppMglManager.findBy_SubEdifi_TecSubEdifi(
                                                        subEdificio.getSubEdificioId(), tecnologiaSub.getTecnoSubedificioId());
                                            }
                                            if (homePass != null) {
                                                for (HhppMgl temHhpp : homePass) {
                                                    if (actualizarRr && habilitarRR) {
                                                        if (temHhpp.getNodId() != null && temHhpp.getNodId().getComId() != null
                                                                && temHhpp.getNodId().getComId().getRegionalRr() != null) {
                                                            try {
                                                                DireccionRRManager drrm = new DireccionRRManager(true);
                                                                String codigo = null;
                                                                if (nodo != null) {
                                                                    codigo = nodo.getNodCodigo();
                                                                }
                                                                String respuestaCambioRR = drrm.cambioNodoHHPP(temHhpp.getHhpPlaca(), temHhpp.getHhpCalle(), temHhpp.getHhpApart(),
                                                                        temHhpp.getNodId().getComId().getCodigoRr(), temHhpp.getNodId().getComId().getRegionalRr().getCodigoRr(),
                                                                        codigo);
                                                                //espinosadiea se considera cambio realizado cuando retorna el substring CAB0074
                                                                if (!respuestaCambioRR.contains("CAB0074")) {
                                                                    LOGGER.error("CmtRunableMasivoCuentaMatrizManager:run: Error Cargue masivo RR: " + respuestaCambioRR);
                                                                    errorDos = true;
                                                                    errorTecnologiaSub.append(" Error en RR cambio de nodo para los home pass: ").append(respuestaCambioRR);
                                                                    actNodo = false;
                                                                    continue;
                                                                }
                                                            } catch (Exception exc) {
                                                                LOGGER.error("CmtRunableMasivoCuentaMatrizManager:run: Error Cargue masivo. bucle cambio de nodo.: "
                                                                        + "Error integridad de informacion:" + " " + exc.getMessage());
                                                                errorDos = true;
                                                                errorTecnologiaSub.append(" Error Cargue masivo. bucle cambio de nodo.: Error integridad de informacion.");
                                                                actNodo = false;
                                                                continue;
                                                            }
                                                        } else {
                                                            errorDos = true;
                                                            errorTecnologiaSub.append(" No existe referencia de Comunidad o Regional para el home pass: ").append(temHhpp.getHhpId()).append("; Error integridad de informacion.");
                                                            actNodo = false;
                                                            continue;
                                                        }
                                                    }
                                                    temHhpp.setNodId(nodo);
                                                    hhppMglManager.update(temHhpp);
                                                }
                                            }
                                        } catch (ApplicationException ae) {
                                            errorDos = true;
                                            if (subEdificio != null) {
                                                LOGGER.error("CmtRunableMasivoCuentaMatrizManager:run: No fue posible actualizar el nodo en los home pass "
                                                        + "para subEdificio: " + subEdificio.getSubEdificioId() + " " + ae.getMessage());
                                                errorTecnologiaSub.append(" No fue posible actualizar el nodo en los home pass para subEdificio: ").append(subEdificio.getSubEdificioId());
                                            } else {
                                                LOGGER.error("No fue posible actualizar el nodo en los home pass: " + ae.getMessage());
                                                errorTecnologiaSub.append(" No fue posible actualizar el nodo en los home pass para subEdificio: ").append(ae.getMessage());
                                            }
                                        }
                                        if (actNodo && nodo != null) {//si no presenta error en actualizacion de home_pass se actualiza nodo de tecnologia subedificio
                                            tecnologiaSub.setNodoId(nodo);
                                            actTecSubEdificio_Nodo = true;
                                            modTecSubEdificio = true;
                                            if (actualizaNodoSubEdificio){
                                                actSubEdificio = true;//espinosadiea si no hay cambios en los demas campos del subedificio pero si se realiza cambio en el nodo de la tecnologia del subedificio
                                            }
                                        } else {
                                            actTecSubEdificio_Nodo = false;
                                        }
                                    }
                                }
                                if (modTecSubEdificio) {
                                    tecnologiaSubManager.actualizar(tecnologiaSub, usuario, perfil);
                                } else {
                                    actTecSubEdificio = false;
                                }
                            }
                            if (actSubEdificio) {
                                if (subEdificio != null) {
                                    if (actSubEdificio_CompaAdministra) {
                                        subEdificio.setCompaniaAdministracionObj(adminitracion);
                                    }
                                    if (actSubEdificio_CompaAcensores) {
                                        subEdificio.setCompaniaAscensorObj(ascensor);
                                    }
                                    if (actSubEdificio_Administrador) {
                                        subEdificio.setAdministrador(m.getAdministrador());
                                    }
                                    if (actSubEdificio_Telefono1) {
                                        subEdificio.setTelefonoPorteria(m.getTelefonoUno());
                                    }
                                    if (actSubEdificio_Telefono2) {
                                        subEdificio.setTelefonoPorteria2(m.getTelefonoDos());
                                    }
                                    if (actSubEdificio_TipoProyecto) {
                                        subEdificio.setTipoProyectoObj(proyecto);
                                    }
                                    if (actSubEdificio_OrigenDatos) {
                                        subEdificio.setOrigenDatosObj(datos);
                                    }
                                    if (actSubEdificio_CompaConstructora) {
                                        subEdificio.setCompaniaConstructoraObj(constructora);
                                    }
                                    if (actSubEdificio_TipoCcmm) {
                                        subEdificio.setTipoEdificioObj(tipoCcmm);
                                    }
                                    if (actSubEdificio_BlacklistTec) {

                                        switch (controlBlack) {
                                            case 1:
                                                //Actualiza el blacklist
                                                CmtBlackListMglManager manager = new CmtBlackListMglManager();
                                                CmtBlackListMgl blackListMglUpd = manager.findById(idBlacklistTec);
                                                if (blackListMglUpd != null && blackListMglUpd.getBlackListId() != null) {
                                                    blackListMglUpd.setBlackListObj(blacklistTec);
                                                    manager.update(blackListMglUpd, usuario, perfil);
                                                }
                                                break;
                                            case 2:
                                                //Elimina el blacklist del edificio   
                                                CmtBlackListMglManager manager1 = new CmtBlackListMglManager();
                                                CmtBlackListMgl blackListMglEli = manager1.findById(idBlacklistTec);
                                                if (blackListMglEli != null && blackListMglEli.getBlackListId() != null) {
                                                    manager1.deleteCm(blackListMglEli, usuario, perfil);
                                                }
                                                break;
                                            case 3:
                                                //Valida y lo crea 
                                                if (blacklistTec != null) {
                                                    boolean controlBla = false;
                                                    if (subEdificio.getListCmtBlackListMgl() != null
                                                            && !subEdificio.getListCmtBlackListMgl().isEmpty()) {
                                                        for (CmtBlackListMgl l : subEdificio.getListCmtBlackListMgl()) {
                                                            if (l.getBlackListObj().getBasicaId().compareTo(blacklistTec.getBasicaId()) == 0) {
                                                                controlBla = true;
                                                            }
                                                        }
                                                        if (controlBla) {
                                                            LOGGER.info("Ya tiene un blacklist con ese nombre no se crea");
                                                        } else {
                                                            //Se crea el blacklist nuevo a el edificio
                                                            CmtBlackListMglManager manager2 = new CmtBlackListMglManager();
                                                            CmtBlackListMgl blackListMglCrear= new CmtBlackListMgl();
                                                            blackListMglCrear.setSubEdificioObj(subEdificio);
                                                            blackListMglCrear.setDetalle("Creacion Masivo CM");
                                                            blackListMglCrear.setActivado("Y");
                                                            manager2.create(blackListMglCrear, usuario, perfil); 
                                                        }
                                                    } else {
                                                        //No tiene blacklist el edificio se crea uno nuevo
                                                           CmtBlackListMglManager manager2 = new CmtBlackListMglManager();
                                                            CmtBlackListMgl blackListMglCrear= new CmtBlackListMgl();
                                                            blackListMglCrear.setSubEdificioObj(subEdificio);
                                                            blackListMglCrear.setDetalle("Creacion Masivo CM");
                                                            blackListMglCrear.setActivado("Y");
                                                            manager2.create(blackListMglCrear, usuario, perfil); 
                                                    }
                                                }
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                     
                                    if (actTecSubEdificio_Nodo && subEdificio.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null
                                            && subEdificio.getEstadoSubEdificioObj().getIdentificadorInternoApp().equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                                        subEdificio.setNodoObj(nodo);
                                    } else if (actTecSubEdificio_Nodo && isUnicoEdificio(m.getIdCuentamatriz())) {
                                        subEdificio.setNodoObj(nodo);
                                    }
                                    
                                    if (actCuentMatriz_Nombre) {
                                        subEdificio.setNombreSubedificio(m.getNombreCuenta());
                                    }
                                    if (actualizarRr && habilitarRR) {//inicio actualizar subEdificio en rr
                                        try {
                                            subEdificioManager.update(subEdificio, usuario, perfil, false,false);
                                        } catch (ApplicationException e) {
                                            errorUno = true;
                                            actSubEdificio = false;
                                            LOGGER.error("Error actualizando SubEdificio en RR: " + m.getIdSubedificio() + " " + e.getMessage());
                                            errorSubEdificio.append("Error actualizando SubEdificio en RR: ").append(m.getIdSubedificio())
                                                    .append(" Consultar el LOG del sistema para mayor detalle; el servicio de actualizacion "
                                                    + "SubEdificio no esta funcionando correctamente.");
                                        }
                                    }
                                    if (actSubEdificio) {//si no actualiza RR lo convierte en falso ver bloque try catch
                                        subEdificioManager.updateSinRr(subEdificio, usuario, perfil);
                                    }
                                }
                            }
                        }
                        if (errorUno || errorDos || errorTres) {
                            m.setProcesado(false);
                            if (errorUno) {
                                LOGGER.error(errorSubEdificio.toString());
                                m.setMensajeSubEdificio(errorSubEdificio.toString());
                            }
                            if (errorDos) {
                                LOGGER.error(errorTecnologiaSub.toString());
                                m.setMensajeTecnologiaSub(errorTecnologiaSub.toString());
                            }
                            if (errorTres) {
                                LOGGER.error(errorCuentaMatriz.toString());
                                m.setMensajeCuentaMatriz(errorCuentaMatriz.toString());
                            }
                        } else {
                            if (actCuentMatriz_Nombre || actSubEdificio || actTecSubEdificio) {
                                m.setProcesado(true);
                                if (!m.getNota().isEmpty()) {
                                    //inicio creacion de notas
                                    CmtNotasMglManager notasManager = new CmtNotasMglManager();
                                    CmtNotasMgl nota = new CmtNotasMgl();
                                    CmtBasicaMgl tipoNota = new CmtBasicaMgl();
                                    tipoNota.setBasicaId(new BigDecimal(339));//OBSERVACIONES
                                    nota.setTipoNotaObj(tipoNota);
                                    nota.setNota(m.getNota());
                                    nota.setSubEdificioObj(subEdificio);
                                    if (subEdificio != null) {
                                        nota.setDescripcion(subEdificio.getNombreSubedificio() != null ? subEdificio.getNombreSubedificio() : "ND");
                                    } else {
                                        nota.setDescripcion("ND");
                                    }
                                    nota.setDetalle("CREACION DE NOTA");
                                    notasManager.create(nota, usuario, perfil);
                                    //fin creacion de notas
                                }
                            } else {
                                m.setProcesado(false);
                                m.setMensajeCuentaMatriz("No hay cambios de informacion, en ninguno de los campos.");
                            }
                        }
                        // Fin cambio atributos cuenta matriz
                        count += 1;
                        CmtMasivoCuentaMatrizDtoMgl.setNumeroRegistrosProcesados(count);
                        //no hay motivo para dormir el proceso
                        // informa al managed bean que acabo de procesar el registro en cuestion.
                        this.setChanged();
                        this.notifyObservers(m);
                    } catch (ApplicationException | UniformInterfaceException | IOException extr) {
                        String mensaCM = m.getMensajeCuentaMatriz();
                        m.setMensajeCuentaMatriz(mensaCM + " Error general reporte General revisar el Log del sistema para mayor informacion");
                        LOGGER.error("Exepcion:CmtRunableMasivoCuentaMatrizManager:ReporteGeneral;"
                                + " Cuenta" + m.getNumeroCuenta() + "; SubEdificio:" + m.getIdSubedificio() + ";"
                                + " Tecnologia:" + m.getIdTecnoSubedificio() + ":" + extr.getMessage());
                    }
                }//for carga archivo General
            }//Finmodificacion general 
            else if (this.tipoModificacion == 2) {
                //Inicio modificacion detallado
                List<BigDecimal> listIdCuentaMatriz = new ArrayList<BigDecimal>();
                for (MasivoModificacionDto m : this.listaModificacion) {
                    try {
                        error = false;
                        boolean subEdif = false;
                        StringBuilder errorSubEdificio = new StringBuilder();
                        StringBuilder errorTecnologiaSub = new StringBuilder();
                        StringBuilder errorCuentaMatriz = new StringBuilder();
                        boolean actualizarSubEdi = true;
                        boolean actualizarTecnologiaSub = true;
                        boolean actualizarRr = false;
                        CmtBasicaMgl tecnologia;

                        tecnologia = buscarBasica(m.getTipoTecnologia(), listBasicaTecnologia);

                        if (tecnologia != null) {
                            if (tecnologia.getListCmtBasicaExtMgl() != null
                                    && !tecnologia.getListCmtBasicaExtMgl().isEmpty()) {
                                for (CmtBasicaExtMgl b : tecnologia.getListCmtBasicaExtMgl()) {
                                    if (b.getIdTipoBasicaExt() != null) {
                                        if (b.getIdTipoBasicaExt().getCampoEntidadAs400().equals(Constant.SINCRONIZA_RR)
                                                && b.getValorExtendido().equals("Y")) {
                                            actualizarRr = true;
                                            break;
                                        }
                                    }
                                }
                            }
                        }

                        switch (m.getNumDetallado()) {
                            case 1://nombre cuenta matriz
                                // se valida que la cuenta matriz ya se cambio el nombre
                                if (!listIdCuentaMatriz.contains(m.getIdCuentamatriz())) {
                                    listIdCuentaMatriz.add(m.getIdCuentamatriz());
                                    String respuestaCuenta = actualizarCuentaMatriz(m.getIdCuentamatriz(), m.getNombreCuenta());
                                    CmtCuentaMatrizMgl cuenta = new CmtCuentaMatrizMgl();
                                    cuenta.setCuentaMatrizId(m.getIdCuentamatriz());
                                    CmtSubEdificioMgl subEdificioGenralRr = subEdificioManager.findById(m.getIdSubedificio());
                                    // inicia actualiza el nombre de la cuenta matriz
                                    if (subEdificioGenralRr != null && subEdificioGenralRr.getSubEdificioId() != null
                                            && subEdificioGenralRr.getEstadoSubEdificioObj() != null && subEdificioGenralRr.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null
                                            && subEdificioGenralRr.getEstadoSubEdificioObj().getIdentificadorInternoApp().equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                                        subEdificioGenralRr.setNombreSubedificio(m.getNombreCuenta());
                                        subEdificioManager.updateSinRr(subEdificioGenralRr, usuario, perfil);
                                        if (actualizarRr && habilitarRR) {
                                            subEdificioManager.update(subEdificioGenralRr, usuario, perfil, false,false);
                                        }
                                    }else{
                                        if (isUnicoEdificio(m.getIdCuentamatriz())) {
                                            if (subEdificioGenralRr != null) {
                                                subEdificioGenralRr.setNombreSubedificio(m.getNombreCuenta());
                                                subEdificioManager.updateSinRr(subEdificioGenralRr, usuario, perfil);
                                                if (actualizarRr && habilitarRR) {
                                                    subEdificioManager.update(subEdificioGenralRr, usuario, perfil, false,false);
                                                }
                                            }
                                        }
                                    }
                                    // fin actualiza el nombre de la cuenta matriz
                                    if (respuestaCuenta != null) {
                                        errorTres = true;
                                        errorTecnologiaSub.append(Constant.MSN_ERROR_CUENTA_MATRIZ).append(m.getNombreCuenta());
                                    }
                                }
                                break;
                            case 2://CompaniaAdministracion
                                CmtCompaniaMgl adminitracion = null;
                                if (m.getCompaniaAdministracion() != null && !m.getCompaniaAdministracion().trim().isEmpty()) {
                                    adminitracion = buscarCompania(m.getCompaniaAdministracion(), listAdmin);
                                    if (adminitracion == null) {
                                        actualizarSubEdi = false;
                                        errorUno = true;
                                        errorSubEdificio.append(Constant.MSN_ERROR_COMPANIA_ADMIN).append(m.getCompaniaAdministracion());
                                    }
                                }
                                if (actualizarSubEdi) {
                                    CmtSubEdificioMgl subEdificio = subEdificioManager.findById(m.getIdSubedificio());
                                    if (subEdificio != null) {
                                        subEdificio.setCompaniaAdministracionObj(adminitracion);
                                        subEdificioManager.updateSinRr(subEdificio, usuario, perfil);
                                        subEdif = true;
                                    } else {
                                        LOGGER.error("Error no se encontro el CmtSubEdificioMgl con el id " + m.getIdSubedificio());
                                    }
                                }
                                break;
                            case 3://CompaniaAscensor
                                CmtCompaniaMgl ascensor = null;
                                if (m.getCompaniaAscensor() != null && !m.getCompaniaAscensor().trim().isEmpty()) {
                                    ascensor = buscarCompania(m.getCompaniaAscensor(), listAscensor);
                                    if (ascensor == null) {
                                        actualizarSubEdi = false;
                                        errorUno = true;
                                        errorSubEdificio.append(Constant.MSN_ERROR_COMPANIA_ASCENSOR).append(m.getCompaniaAscensor());
                                    }
                                }
                                if (actualizarSubEdi) {
                                    CmtSubEdificioMgl subEdificioA = subEdificioManager.findById(m.getIdSubedificio());
                                    if (subEdificioA != null) {
                                        subEdificioA.setCompaniaAscensorObj(ascensor);
                                        subEdificioManager.updateSinRr(subEdificioA, usuario, perfil);
                                        subEdif = true;
                                    } else {
                                        LOGGER.error("Error no se encontro el CmtSubEdificioMgl con el id " + m.getIdSubedificio());
                                    }
                                }
                                break;
                            case 4://Administrador
                                CmtSubEdificioMgl subEdificioB = subEdificioManager.findById(m.getIdSubedificio());
                                if (subEdificioB != null) {
                                    subEdificioB.setAdministrador(m.getAdministrador());
                                    subEdificioManager.updateSinRr(subEdificioB, usuario, perfil);
                                    subEdif = true;
                                } else {
                                    LOGGER.error("Error no se encontro el CmtSubEdificioMgl con el id " + m.getIdSubedificio());
                                }
                                break;
                            case 5://TelefonoUno
                                CmtSubEdificioMgl subEdificioC = subEdificioManager.findById(m.getIdSubedificio());
                                if (subEdificioC != null) {
                                    subEdificioC.setTelefonoPorteria(m.getTelefonoUno());
                                    subEdificioManager.updateSinRr(subEdificioC, usuario, perfil);
                                    subEdif = true;
                                } else {
                                    LOGGER.error("Error no se encontro el CmtSubEdificioMgl con el id " + m.getIdSubedificio());
                                }
                                break;
                            case 6://TelefonoDos
                                CmtSubEdificioMgl subEdificioD = subEdificioManager.findById(m.getIdSubedificio());
                                if (subEdificioD != null) {
                                    subEdificioD.setTelefonoPorteria2(m.getTelefonoDos());
                                    subEdificioManager.updateSinRr(subEdificioD, usuario, perfil);
                                    subEdif = true;
                                } else {
                                    LOGGER.error("Error no se encontro el CmtSubEdificioMgl con el id " + m.getIdSubedificio());
                                }
                                break;
                            case 7://TipoProyecto
                                CmtBasicaMgl proyecto = null;
                                if (m.getTipoProyecto() != null && !m.getTipoProyecto().trim().isEmpty()) {
                                    proyecto = buscarBasica(m.getTipoProyecto(), listTipoProyecto);
                                    if (proyecto == null) {
                                        actualizarSubEdi = false;
                                        errorUno = true;
                                        errorSubEdificio.append(Constant.MSN_ERROR_TIPO_PROYECTO).append(m.getTipoProyecto());
                                    }
                                }
                                if (actualizarSubEdi) {
                                    CmtSubEdificioMgl subEdificioF = subEdificioManager.findById(m.getIdSubedificio());
                                    if (subEdificioF != null) {
                                        subEdificioF.setTipoProyectoObj(proyecto);
                                        subEdificioManager.updateSinRr(subEdificioF, usuario, perfil);
                                        subEdif = true;
                                    } else {
                                        LOGGER.error("Error no se encontro el CmtSubEdificioMgl con el id " + m.getIdSubedificio());
                                    }
                                }
                                break;
                            case 8://OrigenDatos
                                CmtBasicaMgl datos = null;
                                if (m.getOrigenDatos() != null && !m.getOrigenDatos().isEmpty()) {
                                    datos = buscarBasica(m.getOrigenDatos(), listOrigenDatos);
                                    if (datos == null) {
                                        actualizarSubEdi = false;
                                        errorUno = true;
                                        errorSubEdificio.append(Constant.MSN_ERROR_ORIGEN_DATOS).append(m.getOrigenDatos());
                                    }
                                }
                                if (actualizarSubEdi) {
                                    CmtSubEdificioMgl subEdificioH = subEdificioManager.findById(m.getIdSubedificio());
                                    if (subEdificioH != null) {
                                        subEdificioH.setOrigenDatosObj(datos);
                                        subEdificioManager.updateSinRr(subEdificioH, usuario, perfil);
                                        subEdif = true;
                                    } else {
                                        LOGGER.error("Error no se encontro el CmtSubEdificioMgl con el id " + m.getIdSubedificio());
                                    }
                                }
                                break;
                            case 9://CodigoNodo
                                NodoMgl nodo = null;
                                if (m.getCodigoNodo() != null && !m.getCodigoNodo().trim().isEmpty()) {
                                    if (tecnologia == null) {
                                        actualizarTecnologiaSub = false;
                                        errorDos = true;
                                        errorTecnologiaSub.append(Constant.MSN_ERROR_TIPO_TECNOLOGIA).append(m.getEstadoTecnologia());
                                    } else {
                                        nodo = buscarNodo(m.getCodigoNodo(), new BigDecimal(m.getIdCentroPoblado()), tecnologia.getBasicaId());
                                    }

                                    if (nodo == null) {
                                        actualizarTecnologiaSub = false;
                                        errorDos = true;
                                        errorTecnologiaSub.append(Constant.MSN_ERROR_CODIGO_NODO).append(m.getCodigoNodo());
                                    }
                                }

                                if (actualizarTecnologiaSub) {
                                    //Asigno el nuevo nodo a la tecno sub
                                    CmtTecnologiaSubMgl tecnologiaSubM = tecnologiaSubManager.findIdTecnoSub(m.getIdTecnoSubedificio());
                                    if (tecnologiaSubM != null && nodo != null) {
                                        tecnologiaSubM.setNodoId(nodo);
                                    } else {
                                        LOGGER.error("Error no se encontro el CmtTecnologiaSubMgl con el id " + m.getIdTecnoSubedificio());
                                    }
                                    //Asigno el  nodo al subedificio 
                                    CmtSubEdificioMgl subEdificioCamNodo = subEdificioManager.findById(m.getIdSubedificio());
                                    if (subEdificioCamNodo != null) {
                                        //Consulto si son la misma tecnologia en MGL y en RR
                                        CmtCuentaMatrizMgl cuenta = cuentaMatrizManager.findByIdCM(m.getIdCuentamatriz());
                                        if (cuenta.isUnicoSubEdificioBoolean()) {
                                            RequestDataManttoEdificio request = new RequestDataManttoEdificio();
                                            request.setCodigoEdificio(cuenta.getNumeroCuenta().toString());

                                            ResponseManttoEdificioList response =
                                                    restClientCuentasMatrices.callWebService(
                                                    EnumeratorServiceName.CM_MANTTO_EDIFICIO_QUERY,
                                                    ResponseManttoEdificioList.class,
                                                    request);

                                            if (response.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                                                LOGGER.error(response.getMensaje());
                                            } else {
                                                List<ResponseDataManttoEdificio> lstDataManttoEdificios =
                                                        response.getListManttoEdificios();

                                                if (lstDataManttoEdificios.size() > 0) {
                                                    ResponseDataManttoEdificio manttoEdificio = lstDataManttoEdificios.get(0);
                                                    String codigoNodo = manttoEdificio.getNodo().trim();
                                                    if (codigoNodo != null && !codigoNodo.isEmpty()) {
                                                        NodoMgl nodoMgl = nodoMglManager.findByCodigo(codigoNodo);
                                                        if (nodoMgl != null && tecnologiaSubM != null) {
                                                            if (nodoMgl.getNodTipo().getBasicaId().
                                                                    compareTo(tecnologiaSubM.getBasicaIdTecnologias().getBasicaId()) == 0) {
                                                                //Si son las mismas tecnologias 
                                                                subEdificioCamNodo.setNodoObj(nodo);
                                                            }

                                                        }

                                                    }

                                                }
                                            }
                                        } else {
                                            //es multiedificio  
                                            RequestDataManttoSubEdificios request = new RequestDataManttoSubEdificios();
                                            request.setCodigoCuenta(cuenta.getNumeroCuenta().toString());

                                            ResponseManttoSubEdificiosList response =
                                                    restClientCuentasMatrices.callWebService(
                                                    EnumeratorServiceName.CM_MANTTO_SUBEDIFICIOS_QUERY,
                                                    ResponseManttoSubEdificiosList.class,
                                                    request);

                                            if (response.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
                                                LOGGER.error(response.getMensaje());
                                            } else {
                                                if (response.getListManttSubEdificios().size() > 0) {
                                                    for (ResponseDataManttoSubEdificios subedificios : response.getListManttSubEdificios()) {
                                                        if (subedificios.getCodigo().trim().
                                                                equalsIgnoreCase(subEdificioCamNodo.getCodigoRr())) {
                                                            String codigoNodo = subedificios.getNodo().trim();
                                                            if (codigoNodo != null && !codigoNodo.isEmpty()) {
                                                                NodoMgl nodoMgl = nodoMglManager.findByCodigo(codigoNodo);
                                                                if (nodoMgl != null && tecnologiaSubM != null) {
                                                                    if (nodoMgl.getNodTipo().getBasicaId().
                                                                            compareTo(tecnologiaSubM.getBasicaIdTecnologias().getBasicaId()) == 0) {
                                                                        //Si son las mismas tecnologias 
                                                                        subEdificioCamNodo.setNodoObj(nodo);
                                                                    }

                                                                }

                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                    } else {
                                        LOGGER.error("Error no se encontro el CmtSubEdificioMgl con el id " + m.getIdSubedificio());
                                    }
                                    //Actualizo hhpp en RR
                                    boolean actNodo = true;
                                    try {
                                        List<HhppMgl> homePass = null;

                                        if (subEdificioCamNodo != null && tecnologiaSubM != null) {
                                            homePass = hhppMglManager.findBy_SubEdifi_TecSubEdifi(
                                                    subEdificioCamNodo.getSubEdificioId(), tecnologiaSubM.getTecnoSubedificioId());
                                        }

                                        if (homePass != null) {
                                            for (HhppMgl temHhpp : homePass) {
                                                if (actualizarRr && habilitarRR) {
                                                    if (temHhpp.getNodId() != null && temHhpp.getNodId().getComId() != null
                                                            && temHhpp.getNodId().getComId().getRegionalRr() != null) {
                                                        try {
                                                            DireccionRRManager drrm = new DireccionRRManager(true);
                                                            String codigo = null;

                                                            if (nodo != null) {
                                                                codigo = nodo.getNodCodigo();
                                                            }
                                                            String respuestaCambioRR = drrm.cambioNodoHHPP(temHhpp.getHhpPlaca(), temHhpp.getHhpCalle(), temHhpp.getHhpApart(),
                                                                    temHhpp.getNodId().getComId().getCodigoRr(), temHhpp.getNodId().getComId().getRegionalRr().getCodigoRr(),
                                                                    codigo);

                                                            if (respuestaCambioRR != null) {
                                                                LOGGER.error("CmtRunableMasivoCuentaMatrizManager:run: Error Cargue masivo RR: " + respuestaCambioRR);
                                                                errorDos = true;
                                                                errorTecnologiaSub.append(" Error en RR cambio de nodo para los home pass: ").append(respuestaCambioRR);
                                                                actNodo = false;
                                                                continue;
                                                            }
                                                        } catch (Exception exc) {
                                                            LOGGER.error("CmtRunableMasivoCuentaMatrizManager:run: Error Cargue masivo. bucle cambio de nodo.: "
                                                                    + "Error integridad de informacion:" + " " + exc.getMessage());
                                                            errorDos = true;
                                                            errorTecnologiaSub.append(" Error Cargue masivo. bucle cambio de nodo.: Error integridad de informacion.");
                                                            actNodo = false;
                                                            continue;
                                                        }
                                                    } else {
                                                        errorDos = true;
                                                        errorTecnologiaSub.append(" No existe referencia de Comunidad o Regional para el home pass: ").append(temHhpp.getHhpId()).append("; Error integridad de informacion.");
                                                        actNodo = false;
                                                        continue;
                                                    }
                                                }
                                                temHhpp.setNodId(nodo);
                                                //Actualizo el hhpp en MGL
                                                hhppMglManager.update(temHhpp);
                                            }
                                        }
                                    } catch (ApplicationException ae) {
                                        if (subEdificioCamNodo != null) {
                                            LOGGER.error("CmtRunableMasivoCuentaMatrizManager:run: No fue posible actualizar el nodo en los home pass "
                                                    + "para subEdificio: " + subEdificioCamNodo.getSubEdificioId() + " " + ae.getMessage());
                                            errorDos = true;
                                            errorTecnologiaSub.append(" No fue posible actualizar el nodo en los home pass para subEdificio: ").append(subEdificioCamNodo.getSubEdificioId());
                                        } else {
                                            LOGGER.error("CmtRunableMasivoCuentaMatrizManager:run: No fue posible actualizar el nodo en los home pass " + ae.getMessage());
                                           
                                            errorDos = true;
                                            errorTecnologiaSub.append(" No fue posible actualizar el nodo en los home pass para subEdificio: ").append(ae.getMessage());
                                        }
                                    }
                                    if (actNodo) {//si no presenta error en actualizacion de home_pass se actualiza nodo de tecnologia subedificio
                                        tecnologiaSubManager.actualizar(tecnologiaSubM, usuario, perfil);
                                        subEdificioManager.updateSinRr(subEdificioCamNodo, usuario, perfil);
                                        subEdif = true;
                                    } else {
                                        if (subEdificioCamNodo != null) {
                                            errorTecnologiaSub.append(" No fue posible actualizar el nodo en los home pass para el  subEdificio: ").append(subEdificioCamNodo.getSubEdificioId()).append(" ");
                                        }
                                    }
                                }
                                break;
                            case 10://EstadoTecnologia
                                CmtBasicaMgl estado = null;
                                if (m.getEstadoTecnologia() != null && !m.getEstadoTecnologia().trim().isEmpty()) {
                                    estado = buscarBasica(m.getEstadoTecnologia(), listEstado);
                                    if (estado == null) {
                                        actualizarTecnologiaSub = false;
                                        errorDos = true;
                                        errorTecnologiaSub.append(Constant.MSN_ERROR_ESTADO_TECNOLOGIA).append(m.getEstadoTecnologia());
                                    }
                                }
                                if (actualizarTecnologiaSub) {
                                    CmtTecnologiaSubMgl tecnologiaSubO = tecnologiaSubManager.findIdTecnoSub(m.getIdTecnoSubedificio());
                                    if (tecnologiaSubO != null) {
                                        tecnologiaSubO.setBasicaIdEstadosTec(estado);
                                        tecnologiaSubManager.actualizar(tecnologiaSubO, usuario, perfil);
                                    } else {
                                        LOGGER.error("Error no se encontro el CmtTecnologiaSubMgl con el id " + m.getIdTecnoSubedificio());
                                    }
                                }
                                break;
                            case 11://TipoConfiguracion
                                CmtBasicaMgl configuracion = null;
                                if (m.getTipoConfiguracion() != null && !m.getTipoConfiguracion().trim().isEmpty()) {
                                    configuracion = buscarBasica(m.getTipoConfiguracion(), listConfiguracion);
                                    if (configuracion == null) {
                                        actualizarTecnologiaSub = false;
                                        errorDos = true;
                                        errorTecnologiaSub.append(Constant.MSN_ERROR_CONFIGURACION).append(m.getTipoConfiguracion());
                                    }
                                }
                                if (actualizarTecnologiaSub) {
                                    CmtTecnologiaSubMgl tecnologiaSubY = tecnologiaSubManager.findIdTecnoSub(m.getIdTecnoSubedificio());
                                    if (tecnologiaSubY != null) {
                                        tecnologiaSubY.setTipoConfDistribObj(configuracion);
                                        tecnologiaSubManager.actualizar(tecnologiaSubY, usuario, perfil);
                                    } else {
                                        LOGGER.error("Error no se encontro el CmtTecnologiaSubMgl con el id " + m.getIdTecnoSubedificio());
                                    }
                                }
                                break;
                            case 12://AlimentacionElectrica
                                CmtBasicaMgl alimentacion = null;
                                if (m.getAlimentacionElectrica() != null && !m.getAlimentacionElectrica().trim().isEmpty()) {
                                    alimentacion = buscarBasica(m.getAlimentacionElectrica(), listAlimentacion);
                                    if (alimentacion == null) {
                                        actualizarTecnologiaSub = false;
                                        errorDos = true;
                                        errorTecnologiaSub.append(Constant.MSN_ERROR_ALIMENTACION_ELECTRICA).append(m.getAlimentacionElectrica());
                                    }
                                }
                                if (actualizarTecnologiaSub) {
                                    CmtTecnologiaSubMgl tecnologiaSubX = tecnologiaSubManager.findIdTecnoSub(m.getIdTecnoSubedificio());
                                    if (tecnologiaSubX != null) {
                                        tecnologiaSubX.setAlimentElectObj(alimentacion);
                                        tecnologiaSubManager.actualizar(tecnologiaSubX, usuario, perfil);
                                    } else {
                                        LOGGER.error("Error no se encontro el CmtTecnologiaSubMgl con el id " + m.getIdTecnoSubedificio());
                                    }
                                }
                                break;
                            case 13://TipoDistribucion
                                CmtBasicaMgl distribucion = null;
                                if (m.getTipoDistribucion() != null && !m.getTipoDistribucion().isEmpty()) {
                                    distribucion = buscarBasica(m.getTipoDistribucion(), listDistribucion);
                                    if (distribucion == null) {
                                        actualizarTecnologiaSub = false;
                                        errorDos = true;
                                        errorTecnologiaSub.append(Constant.MSN_ERROR_DISTRIBUCION).append(m.getTipoDistribucion());
                                    }
                                }
                                if (actualizarTecnologiaSub) {
                                    CmtTecnologiaSubMgl tecnologiaSubZ = tecnologiaSubManager.findIdTecnoSub(m.getIdTecnoSubedificio());
                                    if (tecnologiaSubZ != null) {
                                        tecnologiaSubZ.setTipoDistribucionObj(distribucion);
                                        tecnologiaSubManager.actualizar(tecnologiaSubZ, usuario, perfil);
                                    } else {
                                        LOGGER.error("Error no se encontro el CmtTecnologiaSubMgl con el id " + m.getIdTecnoSubedificio());
                                    }
                                }
                                break;
                                
                              case 14://Tipo CCMM
                                CmtBasicaMgl tipoCcmm = null;
                                if (m.getTipoCcmm() != null && !m.getTipoCcmm().trim().isEmpty()) {
                                    tipoCcmm = buscarBasica(m.getTipoCcmm(), listBasicaTipoCcmm);
                                    if (tipoCcmm == null) {
                                        actualizarSubEdi = false;
                                        errorUno = true;
                                        errorSubEdificio.append(Constant.MSN_ERROR_TIPO_CCMM).append(m.getTipoCcmm());
                                    }
                                }
                                if (actualizarSubEdi) {
                                    CmtSubEdificioMgl subEdificioA = subEdificioManager.findById(m.getIdSubedificio());
                                    if (subEdificioA != null) {
                                        subEdificioA.setTipoEdificioObj(tipoCcmm);
                                        subEdificioManager.updateSinRr(subEdificioA, usuario, perfil);
                                        subEdif = true;
                                    } else {
                                        LOGGER.error("Error no se encontro el CmtSubEdificioMgl con el id " + m.getIdSubedificio());
                                    }
                                }
                                break;

                            case 15://CompaniaConstructora
                                CmtCompaniaMgl constructora = null;
                                if (m.getCompaniaConstructora() != null && !m.getCompaniaConstructora().trim().isEmpty()) {
                                    constructora = buscarCompania(m.getCompaniaConstructora(), listConstructoras);
                                    if (constructora == null) {
                                        actualizarSubEdi = false;
                                        errorUno = true;
                                        errorSubEdificio.append(Constant.MSN_ERROR_COMPANIA_CONSTRUCTORA).append(m.getCompaniaConstructora());
                                    }
                                }
                                if (actualizarSubEdi) {
                                    CmtSubEdificioMgl subEdificioA = subEdificioManager.findById(m.getIdSubedificio());
                                    if (subEdificioA != null) {
                                        subEdificioA.setCompaniaConstructoraObj(constructora);
                                        subEdificioManager.updateSinRr(subEdificioA, usuario, perfil);
                                        subEdif = true;
                                    } else {
                                        LOGGER.error("Error no se encontro el CmtSubEdificioMgl con el id " + m.getIdSubedificio());
                                    }
                                }
                                break;
                        }

                        CmtSubEdificioMgl subEdificioRr = subEdificioManager.findById(m.getIdSubedificio());
                        //inicio actualizar subEdificio en rr
                        if (subEdif) {
                            try {
                                if (actualizarRr && habilitarRR) {
                                    if (subEdificioRr != null && subEdificioRr.getSubEdificioId() != null) {
                                        subEdificioManager.update(subEdificioRr, usuario, perfil, false,false);
                                    } else {
                                        errorUno = true;
                                        actualizarSubEdi = false;
                                        errorSubEdificio.append("Error no existe subEdificio  ").append(m.getIdSubedificio());
                                    }
                                }
                            } catch (ApplicationException e) {
                                errorUno = true;
                                actualizarSubEdi = false;
                                LOGGER.error("Error en subEdificio RR " + m.getIdSubedificio());
                                errorSubEdificio.append("Error en subEdificio RR ").append(m.getIdSubedificio()).append(" ").append(e);
                            }
                        }
                        //Fin actualizar subEdificio en rr

                        if (!actualizarSubEdi || !actualizarTecnologiaSub) {
                            m.setProcesado(false);
                            if (errorUno) {
                                LOGGER.error(errorSubEdificio.toString());
                                m.setMensajeSubEdificio(errorSubEdificio.toString());
                            }
                            if (errorDos) {
                                LOGGER.error(errorTecnologiaSub.toString());
                                m.setMensajeTecnologiaSub(errorTecnologiaSub.toString());
                            }
                            if (errorTres) {
                                LOGGER.error(errorCuentaMatriz.toString());
                                m.setMensajeCuentaMatriz(errorCuentaMatriz.toString());
                            }
                        } else {
                            m.setProcesado(true);
                            //inicio creacion de notas
                            if (!m.getNota().isEmpty()) {
                                CmtNotasMglManager notasManager = new CmtNotasMglManager();
                                CmtNotasMgl nota = new CmtNotasMgl();
                                CmtBasicaMgl tipoNota = new CmtBasicaMgl();
                                tipoNota.setBasicaId(new BigDecimal(339));//OBSERVACIONES
                                nota.setTipoNotaObj(tipoNota);
                                nota.setNota(m.getNota());
                                nota.setSubEdificioObj(subEdificioRr);
                                if (subEdificioRr != null) {
                                    nota.setDescripcion(subEdificioRr.getNombreSubedificio() != null ? subEdificioRr.getNombreSubedificio() : "ND");
                                } else {
                                    nota.setDescripcion("ND");
                                }
                                nota.setDetalle("CREACION DE NOTA");
                                notasManager.create(nota, usuario, perfil);
                                //fin creacion de notas     
                            }

                        }
                        count += 1;
                        CmtMasivoCuentaMatrizDtoMgl.setNumeroRegistrosProcesados(count);
                        // los hilos no se deberian dormir
                        // informa al managed bean que acabo de procesar el registro en cuestion.
                        this.setChanged();
                        this.notifyObservers(m);
                    } catch (ApplicationException | UniformInterfaceException | IOException extra) {//permite continuar con el proceso a pesar de presentarse una falla no controlada
                        String mensaCM = m.getMensajeCuentaMatriz();
                        m.setMensajeCuentaMatriz(mensaCM + " Error general reporte Especifico revisar el Log del sistema para mayor informacion");
                        LOGGER.error("Exepcion:CmtRunableMasivoCuentaMatrizManager:ReporteEspecifico;"
                                + " Cuenta" + m.getNumeroCuenta() + "; SubEdificio:" + m.getIdSubedificio() + ";"
                                + " Tecnologia:" + m.getIdTecnoSubedificio() + ":" + extra.getMessage());
                    }
                }
            }
            cmMgls.addAll(this.listaModificacion);
            this.setChanged();
            this.notifyObservers(cmMgls);
        } catch (ApplicationException ex) {
            CmtMasivoCuentaMatrizDtoMgl.setIsProcessing(false);
            CmtMasivoCuentaMatrizDtoMgl.setMessage(ex.getMessage());
        }
        try {
            CmtMasivoCuentaMatrizDtoMgl.endProcess(cmMgls);
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            CmtMasivoCuentaMatrizDtoMgl.setMessage(ex.getMessage());
        }
    }

    private String actualizarCuentaMatriz(BigDecimal id, String nombre) {
        String respuesta = null;
        try {
            CmtCuentaMatrizMgl cuentaMatriz = new CmtCuentaMatrizMgl();
            cuentaMatriz.setCuentaMatrizId(id);
            cuentaMatriz = cuentaMatrizManager.findById(cuentaMatriz);
            cuentaMatriz.setNombreCuenta(nombre);
            CmtCuentaMatrizMgl cuenta = cuentaMatrizManager.updateSinRr(cuentaMatriz, this.usuario, this.perfil);
            if (cuenta == null) {
                respuesta = "Error al actualizar el nombre de la cuenta matriz ";
            }
        } catch (ApplicationException e) {
            LOGGER.error("Se genero error en actualizarCuentaMatriz de CmtRunableMasivoCuentaMatrizManager class ...", e);
            respuesta = "Error al actualizar el nombre de la cuenta matriz, " + e;
        }
        return respuesta;
    }

    /**
     * valbuenayf metodo para cargar todas las listas
     */
    private void cargarListas() {
        CmtTipoBasicaMglManager tipoBasicaMglManager = new CmtTipoBasicaMglManager();
        try {
            if (listBasicaTecnologia == null || listBasicaTecnologia.isEmpty()) {
                CmtTipoBasicaMgl tipoBasicaTecnologia;
                tipoBasicaTecnologia = tipoBasicaMglManager
                        .findByCodigoInternoApp(Constant.TIPO_BASICA_TECNOLOGIA);
                listBasicaTecnologia = basicaManager.findByTipoBasica(tipoBasicaTecnologia);
            }

            if (listTipoProyecto == null || listTipoProyecto.isEmpty()) {
                CmtTipoBasicaMgl tipoBasicaTecnologia;
                tipoBasicaTecnologia = tipoBasicaMglManager.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_TIPO_DE_PROYECTO);
                listTipoProyecto = basicaManager.findByTipoBasica(tipoBasicaTecnologia);
            }

            if (listOrigenDatos == null || listOrigenDatos.isEmpty()) {
                CmtTipoBasicaMgl tipoBasicaTecnologia =
                        tipoBasicaMglManager.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_ORIGEN_DE_DATOS);
                listOrigenDatos = basicaManager.findByTipoBasica(tipoBasicaTecnologia);
            }

            if (listConfiguracion == null || listConfiguracion.isEmpty()) {
                CmtTipoBasicaMgl tipoBasicaTecnologia;
                tipoBasicaTecnologia = tipoBasicaMglManager.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_CONFIGURACION_DISTRIBUCION);
                listConfiguracion = basicaManager.findByTipoBasica(tipoBasicaTecnologia);
            }

            if (listAlimentacion == null || listAlimentacion.isEmpty()) {
                CmtTipoBasicaMgl tipoBasicaTecnologia;
                tipoBasicaTecnologia = tipoBasicaMglManager.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_TIPO_DE_ALIMENTACION_ELECTRICA);
                listAlimentacion = basicaManager.findByTipoBasica(tipoBasicaTecnologia);
            }

            if (listDistribucion == null || listDistribucion.isEmpty()) {
                CmtTipoBasicaMgl tipoBasicaTecnologia;
                tipoBasicaTecnologia = tipoBasicaMglManager.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_DISTRIBUCION);
                listDistribucion = basicaManager.findByTipoBasica(tipoBasicaTecnologia);
            }

            if (listEstado == null || listEstado.isEmpty()) {
                CmtTipoBasicaMgl tipoBasicaTecnologia =
                        tipoBasicaMglManager.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_ESTADO_CUENTA_MATRIZ);
                listEstado = basicaManager.findByTipoBasica(tipoBasicaTecnologia);
            }
            
            if (listBasicaTipoCcmm == null || listBasicaTipoCcmm.isEmpty()) {
                CmtTipoBasicaMgl tipoBasicaTipoCcmm
                        = tipoBasicaMglManager.findByCodigoInternoApp(
                                Constant.TIPO_BASICA_TIPO_CUENTA_MATRIZ);
                listBasicaTipoCcmm = basicaManager.findByTipoBasica(tipoBasicaTipoCcmm);
            }

            if (listBasicaBlackListTec == null || listBasicaBlackListTec.isEmpty()) {
                CmtTipoBasicaMgl tipoBasicaBlacklistCm
                        = tipoBasicaMglManager.findByCodigoInternoApp(
                                Constant.TIPO_BASICA_BLACK_LIST_CM);
                listBasicaBlackListTec = basicaManager.findByTipoBasica(tipoBasicaBlacklistCm);
            }

            if (listAscensor == null || listAscensor.isEmpty()) {
                CmtTipoCompaniaMgl cmtTipoCompaniaMgl = new CmtTipoCompaniaMgl();
                cmtTipoCompaniaMgl.setTipoCompaniaId(Constant.TIPO_COMPANIA_ID_ASCENSORES);
                listAscensor = companiaManager.findByTipoCompania(cmtTipoCompaniaMgl);
            }

            if (listAdmin == null || listAdmin.isEmpty()) {
                CmtTipoCompaniaMgl cmtTipoCompaniaMgl = new CmtTipoCompaniaMgl();
                cmtTipoCompaniaMgl.setTipoCompaniaId(Constant.TIPO_COMPANIA_ID_ADMINISTRACION);
                listAdmin = companiaManager.findByTipoCompania(cmtTipoCompaniaMgl);
            }
            
            if (listConstructoras == null || listConstructoras.isEmpty()) {
                CmtTipoCompaniaMgl cmtTipoCompaniaMgl = new CmtTipoCompaniaMgl();
                cmtTipoCompaniaMgl.setTipoCompaniaId(Constant.TIPO_COMPANIA_ID_CONSTRUCTORAS);
                listConstructoras = companiaManager.findByTipoCompania(cmtTipoCompaniaMgl);
            }
        } catch (ApplicationException ex) {
            LOGGER.error("Se genero error en cargarListas de CmtRunableMasivoCuentaMatrizManager class ...", ex);
        }
    }

    private CmtBasicaMgl buscarBasica(String nombre, List<CmtBasicaMgl> lista) {
        CmtBasicaMgl basica = null;
        try {
            for (CmtBasicaMgl b : lista) {
                if (nombre != null) {
                    if (b.getNombreBasica().trim().toUpperCase().equals(nombre.trim().toUpperCase())) {
                        basica = b;
                        break;
                    }
                }

            }
        } catch (Exception e) {
            LOGGER.error("Se genero error en buscarBasica de CmtRunableMasivoCuentaMatrizManager class ...", e);
        }
        return basica;
    }

    private CmtCompaniaMgl buscarCompania(String nombre, List<CmtCompaniaMgl> lista) {
        CmtCompaniaMgl compania = null;
        try {
            for (CmtCompaniaMgl c : lista) {
                if (c.getNombreCompania().trim().toUpperCase().equals(nombre.trim().toUpperCase())) {
                    compania = c;
                    break;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Se genero error en buscarCompania de CmtRunableMasivoCuentaMatrizManager class ...", e);
        }
        return compania;
    }

    private NodoMgl buscarNodo(String codigoNodo, BigDecimal idCentroPoblado, BigDecimal idTecnologia) {
        NodoMgl nodo = null;
        try {
            nodo = nodoMglManager.findByCodigoNodo(codigoNodo, idCentroPoblado, idTecnologia);
        } catch (ApplicationException e) {
            LOGGER.error("Error en buscarNodo. " +e.getMessage(), e);  
        }
        return nodo;
    }
    
    
    private boolean isUnicoEdificio(BigDecimal idCuentaMatriz){
        try {
            CmtCuentaMatrizMgl cuentaMatrizMgl = cuentaMatrizManager.findByIdCM(idCuentaMatriz);
            if(cuentaMatrizMgl.isUnicoSubEdificioBoolean()){
                return true;
            }else{
                return false;
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
        }
        
        return false;
    }
}
