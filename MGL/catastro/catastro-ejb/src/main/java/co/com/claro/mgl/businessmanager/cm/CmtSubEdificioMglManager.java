package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.cmas400.ejb.request.RequestDataManttoEdificio;
import co.com.claro.cmas400.ejb.request.RequestDataManttoSubEdificios;
import co.com.claro.cmas400.ejb.respons.ResponseDataManttoSubEdificios;
import co.com.claro.cmas400.ejb.respons.ResponseManttoEdificioList;
import co.com.claro.cmas400.ejb.respons.ResponseManttoSubEdificiosList;
import co.com.claro.mgl.businessmanager.address.DireccionMglManager;
import co.com.claro.mgl.businessmanager.address.DrDireccionManager;
import co.com.claro.mgl.businessmanager.address.HhppMglManager;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.businessmanager.address.SubDireccionMglManager;
import co.com.claro.mgl.dao.impl.cm.CmtSubEdificioMglDaoImpl;
import co.com.claro.mgl.dtos.CmtNombreSubEdificioDivDto;
import co.com.claro.mgl.ejb.wsclient.rest.cm.EnumeratorServiceName;
import co.com.claro.mgl.ejb.wsclient.rest.cm.RestClientCuentasMatrices;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSolicitudCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioAuditoriaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoOtMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.UtilsCMAuditoria;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.claro.visitasTecnicas.business.NegocioParamMultivalor;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import co.com.claro.visitasTecnicas.entities.HhppResponseRR;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.services.georeferencia.AddressEJBRemote;
import co.com.telmex.catastro.utilws.ResponseMessage;
import com.sun.jersey.api.client.UniformInterfaceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.NoResultException;

/**
 *
 * @author Admin
 */
public class CmtSubEdificioMglManager {

    private static final Logger LOGGER = LogManager.getLogger(CmtSubEdificioMglManager.class);
    ParametrosMglManager parametrosMglManager;
    RestClientCuentasMatrices restClientCuentasMatrices;
    String BASE_URI;
    
    
    public CmtSubEdificioMglManager(){
        
    }
    
    public List<CmtSubEdificioMgl> findAll() throws ApplicationException {
        List<CmtSubEdificioMgl> resulList;
        CmtSubEdificioMglDaoImpl cmtSubEdificioMglDaoImpl = new CmtSubEdificioMglDaoImpl();
        resulList = cmtSubEdificioMglDaoImpl.findAll();
        return resulList;
    }

    public List<CmtSubEdificioMgl> findSubEdificioByCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException {
        CmtSubEdificioMglDaoImpl cmtSubEdificioMglDaoImpl = new CmtSubEdificioMglDaoImpl();
        return cmtSubEdificioMglDaoImpl.findSubEdificioByCuentaMatriz(cuentaMatriz);
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
    public List<CmtSubEdificioMgl> findSubEdifByCmAndEstado(
            CmtCuentaMatrizMgl cuentaMatriz,
            CmtBasicaMgl estado) throws ApplicationException {
        CmtSubEdificioMglDaoImpl cmtSubEdificioMglDaoImpl = new CmtSubEdificioMglDaoImpl();
        return cmtSubEdificioMglDaoImpl.findSubEdifByCmAndEstado(
                cuentaMatriz, estado);
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
    public List<CmtSubEdificioMgl> findSubEdifByCmSinVt(
            CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl) throws ApplicationException {
        CmtTipoOtMglManager cmtTipoOtMglManager = new CmtTipoOtMglManager();
        CmtTipoOtMgl cmtTipoOtMgl = cmtTipoOtMglManager.findById(cmtOrdenTrabajoMgl.getTipoOtObj().getIdTipoOt());
        CmtBasicaMgl estadoValidoParaOt = cmtTipoOtMgl.getEstadoIniCm();
        return findSubEdifByCmAndEstado(cmtOrdenTrabajoMgl.getCmObj(), estadoValidoParaOt);
    }

    public CmtSubEdificioMgl create(CmtSubEdificioMgl cmtSubEdificioMgl, String usuario, int perfil, boolean crearSoloEncabezado) throws ApplicationException {
        CmtCuentaMatrizRRMglManager rRMglManager = new CmtCuentaMatrizRRMglManager();
        boolean habilitarRR = false;
        boolean isCreateEncabezadoRr = false;
        ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
        ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(co.com.claro.mgl.utils.Constant.HABILITAR_RR);
        if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
            habilitarRR = true;
        }// guarda la torre que no existe en rr
        if (habilitarRR) {
            isCreateEncabezadoRr = rRMglManager.manttoSubEdificiosAdd(cmtSubEdificioMgl, usuario);
        }

        boolean isCreateInfoAdicionalRr = true;
        boolean isCreateRR = false;

        if (crearSoloEncabezado) {
            if (isCreateEncabezadoRr) {
                isCreateRR = true;
            }
        } else {
            if (isCreateEncabezadoRr && isCreateInfoAdicionalRr) {
                isCreateRR = true;
            }
        }
        if (habilitarRR) {
            if (isCreateRR) {
                CmtSubEdificioMglDaoImpl cmtSubEdificioMglDaoImpl = new CmtSubEdificioMglDaoImpl();

                return cmtSubEdificioMglDaoImpl.createCm(cmtSubEdificioMgl, usuario, perfil);
            } else {
                throw new ApplicationException("No fue posible Crear el Subedificio en RR");
            }
        } else {
            CmtSubEdificioMglDaoImpl cmtSubEdificioMglDaoImpl = new CmtSubEdificioMglDaoImpl();

            return cmtSubEdificioMglDaoImpl.createCm(cmtSubEdificioMgl, usuario, perfil);
        }

    }

    public CmtSubEdificioMgl update(CmtSubEdificioMgl cmtSubEdificioMgl, String usuario,
            int perfil, boolean actualizarHhpp, boolean cambioNombreSubEdificioPropiaDir) throws ApplicationException {
        try {
            CmtCuentaMatrizRRMglManager rRMglManager = new CmtCuentaMatrizRRMglManager();           
            HhppMglManager hhppMglManager = new HhppMglManager();
            DireccionRRManager direccionRRManager = new DireccionRRManager(true);
            HhppResponseRR hhppResponseRR = new HhppResponseRR();
            CmtDireccionDetalleMglManager direccionDetalladaManager = new CmtDireccionDetalleMglManager();
            DrDireccionManager drDireccionManager = new DrDireccionManager();
            CmtDireccionDetalleMglManager direccionDetalladaMglManager = new CmtDireccionDetalleMglManager();
            CmtComunidadRrManager comunidadRrManager = new CmtComunidadRrManager();
            CmtSubEdificioMglManager cmtSubEdificioMglManager = new CmtSubEdificioMglManager();            
            CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
          
            boolean isActualizadoRr = false;
            boolean habilitarRR = false;
            parametrosMglManager = new ParametrosMglManager();
            ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(co.com.claro.mgl.utils.Constant.HABILITAR_RR);
            if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
                habilitarRR = true;
            }   
            
            //JDHT
            /*Si es un cambio de nombre a un subEdificio que tiene su propia dirección (entrada)
             * no se debe hacer actulizaciones en RR, solo en MGL, las logicas son diferentes
             */
            if(cambioNombreSubEdificioPropiaDir){
                habilitarRR = false;
            }


            if (habilitarRR) {
                if (cmtSubEdificioMgl.getCuentaMatrizObj().getNumeroCuenta().
                        compareTo(Constant.NUMERO_CM_MGL) == 0) {

                    isActualizadoRr = true;

                } else {
                    isActualizadoRr = rRMglManager.updateInfoSubEdificio(cmtSubEdificioMgl, usuario);
                }
            }


            if (isActualizadoRr || !habilitarRR) {
                CmtSubEdificioMglDaoImpl cmtSubEdificioMglDaoImpl = new CmtSubEdificioMglDaoImpl();
                CmtCuentaMatrizMgl cuentaMatriz = cmtSubEdificioMgl.getCuentaMatrizObj();
                boolean isMultiEdificio = cuentaMatriz.isUnicoSubEdificioBoolean();
                if (!isMultiEdificio) {
                    boolean isEdificioSelectedGeneral =
                            cmtSubEdificioMgl.getCuentaMatrizObj().getSubEdificioGeneral().getSubEdificioId().compareTo(cmtSubEdificioMgl.getSubEdificioId()) == 0;
                    if (isEdificioSelectedGeneral) {
                        cuentaMatriz.setNombreCuenta(cmtSubEdificioMgl.getNombreSubedificio());
                        CmtCuentaMatrizMglManager cuentaMatrizMglManager = new CmtCuentaMatrizMglManager();
                        cuentaMatrizMglManager.updateSinRr(cuentaMatriz, usuario, perfil);
                    } else {
                        CmtCuentaMatrizMglManager cuentaMatrizMglManager = new CmtCuentaMatrizMglManager();
                        cuentaMatrizMglManager.updateSinRr(cuentaMatriz, usuario, perfil);
                    }
                } else {
                    cuentaMatriz.setNombreCuenta(cmtSubEdificioMgl.getNombreSubedificio());
                    CmtCuentaMatrizMglManager cuentaMatrizMglManager = new CmtCuentaMatrizMglManager();
                    cuentaMatrizMglManager.updateSinRr(cuentaMatriz, usuario, perfil);
                }
                // validacion del nuevo valor del estado del subedificio
                if (cmtSubEdificioMgl.getEstadoSubNuevo() != null) {
                 cmtSubEdificioMgl.setEstadoSubEdificioObj(cmtBasicaMglManager.findById(cmtSubEdificioMgl.getEstadoSubNuevo()));

                }   
                
                //JDHT // cambio de direccion a hhpp cuando existe un cambio de nombre del subedificio (torres, entrada, etc)
                if (!isMultiEdificio && actualizarHhpp) {
                    //Obtener Hhpps asociados al subedificio
                    List<HhppMgl> hhppSubEdificioList = new ArrayList();
                    if (cmtSubEdificioMgl != null && cmtSubEdificioMgl.getSubEdificioId() != null) {
                        hhppSubEdificioList = hhppMglManager.findHhppBySubEdificioId(cmtSubEdificioMgl.getSubEdificioId());
                    }

                    //Si se realizó un cambio de nombre al subedificio
                    if (cmtSubEdificioMgl != null && cmtSubEdificioMgl.getNombreSubedificio() != null
                            && !cmtSubEdificioMgl.getNombreSubedificio().isEmpty()) {

                        //Se divide el nombre para separarlo en los niveles del object DrDireccion
                        String[] nombreEdificioDividido = cmtSubEdificioMgl.getNombreSubedificio().split(" ");
                        String valorCpNivel1 = "";
                        for (int i = 1; i < nombreEdificioDividido.length; i++) {
                            valorCpNivel1 += nombreEdificioDividido[i] + " ";
                        }
                        String tipoNivel1 = nombreEdificioDividido[0].trim().toUpperCase();
                        String valorNivel1 = valorCpNivel1.trim().toUpperCase();

                        //se desea obtener los hhpp asociados al edificio principal para saber si se debe cambiar esas direcciones
                        //  en caso de que se trate de la torre 1 de la cuenta matriz
                        if (cmtSubEdificioMgl != null && cmtSubEdificioMgl.getCmtCuentaMatrizMglObj() != null) {
                            //Basica de subedificio principal (general) de una cuenta matriz
                            CmtBasicaMgl basicaTipoEdificio =
                                    cmtBasicaMglManager.findByCodigoInternoApp(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO);
                            //Listado de subedificios de la cuenta matriz
                            List<CmtSubEdificioMgl> subEdificioList = cmtSubEdificioMglManager
                                    .findSubEdificioByCuentaMatriz(cmtSubEdificioMgl.getCmtCuentaMatrizMglObj());

                            BigDecimal idEdificioPrincipalCuentaMatriz = null;
                            CmtSubEdificioMgl subEdificioMglOriginal = null;

                            if (subEdificioList != null && !subEdificioList.isEmpty()) {
                                for (CmtSubEdificioMgl cmtSubEdificio : subEdificioList) {
                                    if (cmtSubEdificioMgl.getEstadoSubEdificioObj() != null
                                            && basicaTipoEdificio != null) {
                                        //obtenemos el subedificio principal de la cuenta matriz
                                        if (cmtSubEdificio.getEstadoSubEdificioObj().getBasicaId()
                                                .equals(basicaTipoEdificio.getBasicaId())) {
                                            idEdificioPrincipalCuentaMatriz = cmtSubEdificio.getSubEdificioId();
                                            break;
                                        }
                                    }
                                }
                                //Se consulta el subedificio original que se esta cambiando el nombre para 
                                // saber si se debe cambiar la direccion de los hhpp asociados al edificio 
                                // principal de la cuenta matriz
                                subEdificioMglOriginal = cmtSubEdificioMglManager.findById(cmtSubEdificioMgl.getSubEdificioId());

                                //Obtener Hhpps asociados al subedificio principal de la cuenta matriz (salaventa y campamento)
                                List<HhppMgl> hhppSubEdificioPrincipalList = new ArrayList();
                                if (idEdificioPrincipalCuentaMatriz != null && subEdificioMglOriginal != null) {
                                    //listado de hhpp asociados al edificio principal de la cuenta matriz
                                    hhppSubEdificioPrincipalList = hhppMglManager.findHhppBySubEdificioId(idEdificioPrincipalCuentaMatriz);

                                    if (hhppSubEdificioPrincipalList != null && !hhppSubEdificioPrincipalList.isEmpty()) {
                                        //Se divide el nombre para separarlo en los niveles del object DrDireccion
                                        String[] nombreEdificioOriginalDividido = subEdificioMglOriginal.getNombreSubedificio().split(" ");
                                        String originalValorCpNivel1 = "";
                                        for (int i = 1; i < nombreEdificioOriginalDividido.length; i++) {
                                            originalValorCpNivel1 += nombreEdificioOriginalDividido[i] + " ";
                                        }
                                        String originalTipoNivel1 = nombreEdificioOriginalDividido[0].trim().toUpperCase();
                                        String originalValorNivel1 = valorCpNivel1.trim().toUpperCase();

                                        //obtener nombre del subEdificio en formato RR                                   
                                        DetalleDireccionEntity direccion = new DetalleDireccionEntity();
                                        direccion.setCptiponivel1(originalTipoNivel1);
                                        direccion.setCpvalornivel1(originalValorNivel1);
                                        direccion.setCptiponivel2(null);
                                        direccion.setCpvalornivel2(null);
                                        direccion.setCptiponivel3(null);
                                        direccion.setCpvalornivel3(null);
                                        direccion.setCptiponivel4(null);
                                        direccion.setCpvalornivel4(null);

                                        String nombreSubEdificioFormatoRR =
                                                direccionRRManager.generarSubEdificioMZ(direccion);
                                        for (HhppMgl hhppMgl : hhppSubEdificioPrincipalList) {

                                            if (hhppMgl.getHhpCalle().contains(nombreSubEdificioFormatoRR.trim())) {
                                                hhppSubEdificioList.add(hhppMgl);
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        //Actualiza los hhpp asociados el cambio de sub edificio
                        if (hhppSubEdificioList != null && !hhppSubEdificioList.isEmpty()) {

                            for (HhppMgl hhppMgl : hhppSubEdificioList) {                           

                                    BigDecimal idSubDireccion = null;
                                    BigDecimal idDireccion = hhppMgl.getDireccionObj().getDirId();
                                    if (hhppMgl.getSubDireccionObj() != null && hhppMgl.getSubDireccionObj().getSdiId() != null) {
                                        idSubDireccion = hhppMgl.getSubDireccionObj().getSdiId();
                                    }
                                    List<CmtDireccionDetalladaMgl> direccionDetallada = direccionDetalladaManager.findDireccionDetallaByDirIdSdirId(hhppMgl.getDireccionObj().getDirId(), idSubDireccion);
                                    DrDireccion drDireccionHhpp = new DrDireccion();
                                    if (direccionDetallada != null && !direccionDetallada.isEmpty()) {
                                        drDireccionHhpp = direccionDetalladaManager
                                                .parseCmtDireccionDetalladaMglToDrDireccion(direccionDetallada.get(0));

                                        //Reseteamos los valores viejos y asignamos los nuevos
                                        drDireccionHhpp.setCpTipoNivel1(null);
                                        drDireccionHhpp.setCpValorNivel1(null);
                                        drDireccionHhpp.setCpTipoNivel1(tipoNivel1);
                                        drDireccionHhpp.setCpValorNivel1(valorNivel1);

                                        //Obtenemos la direccion actualizada en texto para mandarla a georeferenciar
                                        String direccionNuevaTextoActualizada = drDireccionManager.getDireccion(drDireccionHhpp);


                                        CityEntity cityEntityCreaDir = new CityEntity();
                                        NegocioParamMultivalor param = new NegocioParamMultivalor();
                                        cityEntityCreaDir = param.consultaDptoCiudad(cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getCentroPoblado().getGeoCodigoDane());
                                        if (cityEntityCreaDir == null || cityEntityCreaDir.getCityName() == null
                                                || cityEntityCreaDir.getDpto() == null
                                                || cityEntityCreaDir.getCityName().isEmpty()
                                                || cityEntityCreaDir.getDpto().isEmpty()) {
                                            throw new ApplicationException("La Ciudad no esta configurada en Direcciones");
                                        }

                                        String barrioStr = drDireccionManager.obtenerBarrio(drDireccionHhpp);
                                        AddressRequest addressRequest = new AddressRequest();
                                        addressRequest.setCodDaneVt(cityEntityCreaDir.getCodDane());
                                        addressRequest.setAddress(direccionNuevaTextoActualizada);
                                        addressRequest.setCity(cityEntityCreaDir.getCityName());
                                        addressRequest.setState(cityEntityCreaDir.getDpto());
                                        addressRequest.setNeighborhood(barrioStr);

                                        AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();
                                        ResponseMessage responseMessageCreateDir =
                                                addressEJBRemote.createAddress(addressRequest,
                                                usuario, "MGL", "", drDireccionHhpp);
                                        if (responseMessageCreateDir != null && responseMessageCreateDir.getNuevaDireccionDetallada() != null) {

                                            CmtDireccionDetalladaMgl direccionDetalladaMgl = direccionDetalladaMglManager.findById(responseMessageCreateDir.getNuevaDireccionDetallada().getDireccionDetalladaId());

                                            //Si la direccion nueva ya existe en detallada hace el cambios de id en el hhpp
                                            if (direccionDetalladaMgl != null) {

                                                //START AJUSTE ACTUALIZACION DE ESTRATO ANTERIOR A NUEVA DIRECCION

                                                //actualizacion de estrato en el cual mantiene el estrato anterior.
                                                //id subdireccion anterior
                                                if (idSubDireccion != null) {
                                                    SubDireccionMglManager subDireccionManager = new SubDireccionMglManager();
                                                    SubDireccionMgl subDireccion = subDireccionManager.findById(idSubDireccion);

                                                    //si se encontro la subdireccion anterior y la nueva tiene subdireccion se actualiza
                                                    if (subDireccion != null && direccionDetalladaMgl.getSubDireccion() != null
                                                            && direccionDetalladaMgl.getSubDireccion().getSdiId() != null) {
                                                        //se asigna el estrato anterior a la subdireccion
                                                        direccionDetalladaMgl.getSubDireccion().setSdiEstrato(subDireccion.getSdiEstrato());
                                                        //se actualiza la nueva subdireccion con el estrato anterior
                                                        subDireccionManager.update(direccionDetalladaMgl.getSubDireccion());
                                                    } else {
                                                        //si existe subdireccion anterior y la nueva direccion no tiene subdireccion
                                                        if (subDireccion != null && direccionDetalladaMgl.getDireccion() != null
                                                                && direccionDetalladaMgl.getDireccion().getDirId() != null) {
                                                            DireccionMglManager direccionMglManager = new DireccionMglManager();
                                                            DireccionMgl direccionMglAnterior = new DireccionMgl();
                                                            direccionMglAnterior.setDirId(idDireccion);
                                                            //direccion anterior
                                                            DireccionMgl direccionAnt = direccionMglManager.findById(direccionMglAnterior);

                                                            if (direccionAnt != null && direccionDetalladaMgl.getDireccion() != null) {
                                                                //se asigna el estrato anterior a la nueva direccion y se actualiza
                                                                direccionDetalladaMgl.getDireccion().setDirEstrato(direccionAnt.getDirEstrato());
                                                                direccionMglManager.update(direccionDetalladaMgl.getDireccion());
                                                            }
                                                        }
                                                    }

                                                } else {
                                                    //si la direccion no es una subdireccion
                                                    if (idDireccion != null) {
                                                        if (direccionDetalladaMgl.getSubDireccion() != null) {

                                                            DireccionMglManager direccionMglManager = new DireccionMglManager();
                                                            DireccionMgl direccionMglAnterior = new DireccionMgl();
                                                            direccionMglAnterior.setDirId(idDireccion);
                                                            //direccion anterior
                                                            DireccionMgl direccionAnt = direccionMglManager.findById(direccionMglAnterior);

                                                            //si la direccion anterior es direccion y la nueva una subdireccion
                                                            if (direccionAnt != null && direccionDetalladaMgl.getSubDireccion() != null) {
                                                                SubDireccionMglManager subDireccionManager = new SubDireccionMglManager();
                                                                //se asigna el estrato anterior a la subdireccion
                                                                direccionDetalladaMgl.getSubDireccion().setSdiEstrato(direccionAnt.getDirEstrato());
                                                                //se actualiza la nueva subdireccion con el estrato anterior
                                                                subDireccionManager.update(direccionDetalladaMgl.getSubDireccion());
                                                            }

                                                        } else {
                                                            //si existe direccion anterior y la nueva direccion no tiene subdireccion
                                                            if (direccionDetalladaMgl.getDireccion() != null
                                                                    && direccionDetalladaMgl.getDireccion().getDirId() != null) {

                                                                DireccionMglManager direccionMglManager = new DireccionMglManager();
                                                                DireccionMgl direccionMglAnterior = new DireccionMgl();
                                                                direccionMglAnterior.setDirId(idDireccion);
                                                                //direccion anterior
                                                                DireccionMgl direccionAnt = direccionMglManager.findById(direccionMglAnterior);

                                                                if (direccionAnt != null && direccionDetalladaMgl.getDireccion() != null) {
                                                                    //se asigna el estrato anterior a la nueva direccion y se actualiza
                                                                    direccionDetalladaMgl.getDireccion().setDirEstrato(direccionAnt.getDirEstrato());
                                                                    direccionMglManager.update(direccionDetalladaMgl.getDireccion());
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                //FIN AJUSTE ACTUALIZACION DE ESTRATO ANTERIOR A NUEVA DIRECCION

                                                //actualización en formato RR al Hhpp                           
                                                //Se convierte el DrDireccion en formato detallaDireccionEntity 
                                                DetalleDireccionEntity detalleDireccionEntity = drDireccionHhpp.convertToDetalleDireccionEntity();

                                                direccionRRManager = new DireccionRRManager(detalleDireccionEntity,
                                                        cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getCentroPoblado().getGpoMultiorigen(), cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getCentroPoblado());

                                                //Se obtiene el detalle de la direccion en formato RR
                                                DireccionRREntity detalleDireccionEntityRR = direccionRRManager.getDireccion();

                                                //Si RR esta encendido y el Hhpp tiene id de RR
                                                if (habilitarRR && hhppMgl.getHhpIdrR() != null && !hhppMgl.getHhpIdrR().isEmpty()) {
                                                    //Consume servicio que busca hhpp por Id de RR
                                                    hhppResponseRR = direccionRRManager.getHhppByIdRR(hhppMgl.getHhpIdrR());
                                                    if (hhppResponseRR != null && hhppResponseRR.getTipoMensaje().equalsIgnoreCase("I")) {
                                                        try {
                                                            CmtComunidadRr cmtComunidadRr = comunidadRrManager.findComunidadByCodigo(hhppResponseRR.getComunidad());

                                                            //Cambio de direccion antigua a nueva en RR
                                                            direccionRRManager.cambiarDirHHPPRR_CM(
                                                                    hhppResponseRR.getComunidad().trim(),
                                                                    hhppResponseRR.getDivision().trim(),
                                                                    hhppResponseRR.getHouse().trim(),
                                                                    hhppResponseRR.getStreet().trim(),
                                                                    hhppResponseRR.getApartamento().trim(),
                                                                    hhppResponseRR.getComunidad().trim(),
                                                                    hhppResponseRR.getDivision().trim(),
                                                                    detalleDireccionEntityRR.getNumeroUnidad().trim(),
                                                                    detalleDireccionEntityRR.getCalle().trim(),
                                                                    hhppResponseRR.getApartamento().trim(),
                                                                    "Modificación CM",
                                                                    usuario, "MOD CM", "",
                                                                    direccionDetalladaMgl.getIdTipoDireccion(), cmtComunidadRr);
                                                        }
                                                        catch (ApplicationException e) {
                                                            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                                                            LOGGER.error(msg, e);
                                                            throw new ApplicationException("Error de conectividad al momento de actualizar la información en RR: " + e.getMessage(), e);
                                                        }
                                                    }
                                                }

                                                if (!cambioNombreSubEdificioPropiaDir) {
                                                    //Se establecen los valores al hhpp que se desea actualizar
                                                    hhppMgl.setHhpCalle(detalleDireccionEntityRR.getCalle() != null
                                                            ? detalleDireccionEntityRR.getCalle().toUpperCase() : null);
                                                    hhppMgl.setHhpPlaca(detalleDireccionEntityRR.getNumeroUnidad() != null
                                                            ? detalleDireccionEntityRR.getNumeroUnidad().toUpperCase() : null);
                                                    hhppMgl.setHhpApart(detalleDireccionEntityRR.getNumeroApartamento() != null
                                                            ? detalleDireccionEntityRR.getNumeroApartamento().toUpperCase() : null);
                                                    //actualiza hhpp los campos calle, unidad, placa
                                                }

                                                if (direccionDetalladaMgl.getSubDireccion() != null) {
                                                    hhppMgl.setSubDireccionObj(direccionDetalladaMgl.getSubDireccion());
                                                    hhppMgl.setDireccionObj(direccionDetalladaMgl.getDireccion());
                                                } else {
                                                    if (direccionDetalladaMgl.getDireccion() != null) {
                                                        hhppMgl.setSubDireccionObj(null);
                                                        hhppMgl.setDireccionObj(direccionDetalladaMgl.getDireccion());
                                                    }
                                                }

                                                hhppMglManager.updateHhppMgl(hhppMgl, usuario, perfil);
                                                LOGGER.error("Hhpp actualizado de subDireccion. idHhpp: " + hhppMgl.getHhpId());

                                            }
                                        }
                                    }
                                
                            }
                        }
                    }
                }
                // actualiza en subedificoMGL
                return cmtSubEdificioMglDaoImpl.updateCm(cmtSubEdificioMgl, usuario, perfil);
            } else {
                throw new ApplicationException("No fue posible actualizar el Subedificio en RR");
            }
        }
        catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg, ex);
            throw new ApplicationException("Error al momento de realizar la actualización: " + ex.getMessage(), ex);
        }
    }

    public CmtSubEdificioMgl updateSinRr(CmtSubEdificioMgl cmtSubEdificioMgl, String usuario, int perfil) throws ApplicationException {
        CmtSubEdificioMglDaoImpl cmtSubEdificioMglDaoImpl = new CmtSubEdificioMglDaoImpl();
        return cmtSubEdificioMglDaoImpl.updateCm(cmtSubEdificioMgl, usuario, perfil);
    }

    public boolean deleteSinRr(CmtSubEdificioMgl cmtSubEdificioMgl, String usuario, int perfil) throws ApplicationException {
        CmtSubEdificioMglDaoImpl cmtSubEdificioMglDaoImpl = new CmtSubEdificioMglDaoImpl();
        return cmtSubEdificioMglDaoImpl.deleteCm(cmtSubEdificioMgl, usuario, perfil);
    }

    public boolean delete(CmtSubEdificioMgl cmtSubEdificioMgl, String usuario, int perfil) throws ApplicationException {
        CmtCuentaMatrizRRMglManager rRMglManager = new CmtCuentaMatrizRRMglManager();
        ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
        ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(co.com.claro.mgl.utils.Constant.HABILITAR_RR);
        boolean habilitarRR = false;
        boolean isActualizadoRr = false;
        if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
            habilitarRR = true;
        }// guarda la torre que no existe en rr
        if (habilitarRR) {
            isActualizadoRr = rRMglManager.manttoSubEdificiosDelete(cmtSubEdificioMgl, usuario);
        }
        
       
 
        if (isActualizadoRr || !habilitarRR) {
            CmtSubEdificioMglDaoImpl cmtSubEdificioMglDaoImpl = new CmtSubEdificioMglDaoImpl();
            return cmtSubEdificioMglDaoImpl.deleteCm(cmtSubEdificioMgl, usuario, perfil);
        } else {
            throw new ApplicationException("No fue posible borrar el Subedificio en RR");
        }
    }

    /**
     * Borrado logico de un subedificio en MGL
     *
     * @author Victor Bocanegra
     * @param t CmtSubEdificioMgl
     * @param perfil
     * @return 
     * @throws ApplicationException
     */
    public boolean deleteCm(CmtSubEdificioMgl cmtSubEdificioMgl, String usuario, int perfil) throws ApplicationException {

        CmtSubEdificioMglDaoImpl cmtSubEdificioMglDaoImpl = new CmtSubEdificioMglDaoImpl();
        return cmtSubEdificioMglDaoImpl.deleteCm(cmtSubEdificioMgl, usuario, perfil);

    }

    public CmtSubEdificioMgl findById(CmtSubEdificioMgl cmtSubEdificioMgl) throws ApplicationException {
        CmtSubEdificioMglDaoImpl cmtSubEdificioMglDaoImpl = new CmtSubEdificioMglDaoImpl();
        return cmtSubEdificioMglDaoImpl.find(cmtSubEdificioMgl.getSubEdificioId());
    }

    public CmtSubEdificioMgl findById(BigDecimal id) throws ApplicationException {
        CmtSubEdificioMglDaoImpl cmtSubEdificioMglDaoImpl = new CmtSubEdificioMglDaoImpl();
        try {
            if (id.compareTo(new BigDecimal(0)) == 0) {
                LOGGER.error("Subedificio con id '0' no se consulta");
                return null;
            } else {
                return cmtSubEdificioMglDaoImpl.find(id);
            }

        } catch (NoResultException ex) {
            LOGGER.error(ex.getMessage());
            return null;
        }

    }

    /**
     * Metodo para construir la auditoria de SubEdificios
     *
     * @autor Julie Sarmiento
     * @param cmtSubEdificioMgl
     * @return Auditoria SubEdificios
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public List<AuditoriaDto> construirAuditoria(CmtSubEdificioMgl cmtSubEdificioMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        UtilsCMAuditoria<CmtSubEdificioMgl, CmtSubEdificioAuditoriaMgl> utilsCMAuditoria =
                new UtilsCMAuditoria<CmtSubEdificioMgl, CmtSubEdificioAuditoriaMgl>();
        List<AuditoriaDto> listAuditoriaDto = utilsCMAuditoria.construirAuditoria(cmtSubEdificioMgl);
        return listAuditoriaDto;

    }

    public CmtSubEdificioMgl create(CmtSubEdificioMgl cmtSubEdificioMgl) throws ApplicationException {
        CmtSubEdificioMglDaoImpl cmtSubEdificioMglDaoImpl = new CmtSubEdificioMglDaoImpl();
        return cmtSubEdificioMglDaoImpl.create(cmtSubEdificioMgl);
    }

    public CmtSubEdificioMgl create(CmtSubEdificioMgl cmtSubEdificioMgl, String user, int perfil) throws ApplicationException {
        CmtSubEdificioMglDaoImpl cmtSubEdificioMglDaoImpl = new CmtSubEdificioMglDaoImpl();
        return cmtSubEdificioMglDaoImpl.createCm(cmtSubEdificioMgl, user, perfil);
    }

    public CmtSubEdificioMgl update(CmtSubEdificioMgl cmtSubEdificioMgl) throws ApplicationException {
        CmtSubEdificioMglDaoImpl cmtSubEdificioMglDaoImpl = new CmtSubEdificioMglDaoImpl();
        return cmtSubEdificioMglDaoImpl.update(cmtSubEdificioMgl);
    }

    public boolean delete(CmtSubEdificioMgl cmtSubEdificioMgl) throws ApplicationException {
        CmtSubEdificioMglDaoImpl cmtSubEdificioMglDaoImpl = new CmtSubEdificioMglDaoImpl();
        return cmtSubEdificioMglDaoImpl.delete(cmtSubEdificioMgl);
    }

    public String obtenerNombreSubEdEstandarRr(CmtSubEdificioMgl subEdificioMgl) {
        String resultName = "";
        try {
            CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
            DireccionRRManager direccionRRManager = new DireccionRRManager(false);
            DetalleDireccionEntity direccionEntity = new DetalleDireccionEntity();
            //Obtenemos el nombre del subEdificio en estandar RR
            CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
            CmtTipoBasicaMgl cmtTipoBasicaMgl;
            cmtTipoBasicaMgl = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TIPO_SUBEDIFICIO);
            List<CmtBasicaMgl> tipoSubEdificioList = cmtBasicaMglManager.findByTipoBasica(cmtTipoBasicaMgl);
            //Obtenesmos los niveles del nombre
            CmtNombreSubEdificioDivDto nombreSubEdDividido =
                    obtenerNombreDivididoSubEd(subEdificioMgl, tipoSubEdificioList);
            if (nombreSubEdDividido != null) {
                if (nombreSubEdDividido.getNivel1() != null
                        && !nombreSubEdDividido.getNivel1().trim().isEmpty()) {
                    direccionEntity.setCptiponivel1(nombreSubEdDividido.getNivel1());
                    if (nombreSubEdDividido.getValorNivel1() != null
                            && !nombreSubEdDividido.getValorNivel1().trim().isEmpty()) {
                        direccionEntity.setCpvalornivel1(nombreSubEdDividido.getValorNivel1());
                    }
                }

                if (nombreSubEdDividido.getNivel2() != null
                        && !nombreSubEdDividido.getNivel2().trim().isEmpty()) {
                    direccionEntity.setCptiponivel2(nombreSubEdDividido.getNivel2());
                    if (nombreSubEdDividido.getValorNivel2() != null
                            && !nombreSubEdDividido.getValorNivel2().trim().isEmpty()) {
                        direccionEntity.setCpvalornivel2(nombreSubEdDividido.getValorNivel2());
                    }
                }

                if (nombreSubEdDividido.getNivel3() != null
                        && !nombreSubEdDividido.getNivel3().trim().isEmpty()) {
                    direccionEntity.setCptiponivel3(nombreSubEdDividido.getNivel3());
                    if (nombreSubEdDividido.getValorNivel3() != null
                            && !nombreSubEdDividido.getValorNivel3().trim().isEmpty()) {
                        direccionEntity.setCpvalornivel3(nombreSubEdDividido.getValorNivel3());
                    }
                }

                if (nombreSubEdDividido.getNivel4() != null
                        && !nombreSubEdDividido.getNivel4().trim().isEmpty()) {
                    direccionEntity.setCptiponivel4(nombreSubEdDividido.getNivel4());
                    if (nombreSubEdDividido.getValorNivel4() != null
                            && !nombreSubEdDividido.getValorNivel4().trim().isEmpty()) {
                        direccionEntity.setCpvalornivel4(nombreSubEdDividido.getValorNivel4());
                    }
                }
                ArrayList<String> nameSubEdArray = direccionRRManager.generarSubEdificioApto(direccionEntity);
                resultName = nameSubEdArray.get(0);
            } else {
                resultName = "";
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error al obtener la información del subedificio. EX000: " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Error al obtener la información del subedificio. EX000: " + e.getMessage(), e);
        } 
        return resultName;

    }

    public CmtNombreSubEdificioDivDto obtenerNombreDivididoSubEd(CmtSubEdificioMgl subEdificioMgl, List<CmtBasicaMgl> tipoSubEdificioList) throws ApplicationException {
        int numeroNivel = 1;
        CmtNombreSubEdificioDivDto divDto = new CmtNombreSubEdificioDivDto();
        if (subEdificioMgl.getCuentaMatrizObj().getSubEdificioGeneral().getEstadoSubEdificioObj()
                .getIdentificadorInternoApp().equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
            if (!subEdificioMgl.getEstadoSubEdificioObj()
                    .getIdentificadorInternoApp().equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                if (subEdificioMgl.getNombreSubedificio() != null
                        && !subEdificioMgl.getNombreSubedificio().trim().isEmpty()) {
                    String[] niveles = subEdificioMgl.getNombreSubedificio().split(" ");
                    for (int i = 0; i <= niveles.length - 1; i++) {
                        if (esNivelDireccion(niveles[i], tipoSubEdificioList)) {
                            if (numeroNivel == 1) {
                                divDto.setNivel1(niveles[i]);
                                numeroNivel--;
                            }
                            if (numeroNivel == 2) {
                                divDto.setNivel2(niveles[i]);
                                numeroNivel--;
                            }
                            if (numeroNivel == 3) {
                                divDto.setNivel3(niveles[i]);
                                numeroNivel--;
                            }
                            if (numeroNivel == 4) {
                                divDto.setNivel4(niveles[i]);
                                numeroNivel--;
                            }

                        } else {
                            if (numeroNivel == 1) {
                                divDto.setValorNivel1(divDto.getValorNivel1() + niveles[i]);
                            }
                            if (numeroNivel == 2) {
                                divDto.setValorNivel2(divDto.getValorNivel2() + niveles[i]);
                            }
                            if (numeroNivel == 3) {
                                divDto.setValorNivel3(divDto.getValorNivel3() + niveles[i]);
                            }
                            if (numeroNivel == 4) {
                                divDto.setValorNivel4(divDto.getValorNivel4() + niveles[i]);
                            }
                        }
                        numeroNivel++;
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
        return divDto;
    }

    private boolean esNivelDireccion(String nivel, List<CmtBasicaMgl> tipoSubEdificioList) {
        boolean result = false;
        for (CmtBasicaMgl tipoSubEdi : tipoSubEdificioList) {
            if (nivel.toUpperCase().trim().
                    equalsIgnoreCase(tipoSubEdi.getNombreBasica().toUpperCase().trim())) {
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean updateSubEdificioNumPisos(BigDecimal cmtSubEdificioMglId, int numPisos) throws ApplicationException {
        CmtSubEdificioMglDaoImpl cmtSubEdificioMglDaoImpl = new CmtSubEdificioMglDaoImpl();
        return cmtSubEdificioMglDaoImpl.updateSubEdificioNumPisos(cmtSubEdificioMglId, numPisos);
    }

    /**
     * Buscar un sub edificio general de una cuenta matriz especifica
     *
     * @author rodriguezluim
     * @param cuentaMatriz Condicional para buscar el subedificio
     * @return Un obnbjeto de tipo CmtSubEdificioMgl
     * @throws ApplicationException
     */
    public CmtSubEdificioMgl findSubEdificioGeneralByCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException {
        CmtSubEdificioMglDaoImpl cmtSubEdificioMglDaoImpl = new CmtSubEdificioMglDaoImpl();
        CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();
        CmtBasicaMgl multiedificio = basicaMglManager.findByCodigoInternoApp(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO);
        return cmtSubEdificioMglDaoImpl.findSubEdificioGeneralByCuentaMatriz(cuentaMatriz, multiedificio);
    }

    /**
     * Perimte eliminar un subedificio desde la visita tecnica, el cual no tenga
     * ningun registro relacionado ni yna visita tecnica iniciada.
     *
     * @author Carlos Leonardo villamil.
     * @param cmtSubEdificioMgl
     * @param usuario
     * @param perfil
     * @throws ApplicationException
     */
    public void deleteSubEdificioOnProcesVt(
            CmtSubEdificioMgl cmtSubEdificioMgl, String usuario, int perfil)
            throws ApplicationException {
        CmtTecnologiaSubMglManager cmtTecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
       

        if (cmtSubEdificioMgl.getListHhpp() != null && !cmtSubEdificioMgl.getListHhpp().isEmpty()) {
            throw new ApplicationException("No se pueden eliminar un SubEdificio con HHPP Asociados");
        }

        List<CmtTecnologiaSubMgl> cmtTecnologiaSubMglList =
                cmtTecnologiaSubMglManager.findTecnoSubBySubEdi(cmtSubEdificioMgl);
        
        if (validarEliminacionTecnologia(cmtTecnologiaSubMglList)) {
            for (CmtTecnologiaSubMgl cmtTecnologiaSubMgl : cmtTecnologiaSubMglList) {
                cmtTecnologiaSubMglManager.deleteSubEdificioTecnologia(cmtTecnologiaSubMgl, usuario, perfil);
            }
        }

       

        CmtOrdenTrabajoMglManager manager = new CmtOrdenTrabajoMglManager();
        List<CmtOrdenTrabajoMgl> listCmtOrdenTrabajoMgl =
                manager.ordenesTrabajoActivas(cmtSubEdificioMgl.getCuentaMatrizObj());
        if (listCmtOrdenTrabajoMgl != null && !listCmtOrdenTrabajoMgl.isEmpty()) {
            if (listCmtOrdenTrabajoMgl.size() > 1) {
                for (CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl : listCmtOrdenTrabajoMgl) {
                    if (cmtOrdenTrabajoMgl.getEstadoInternoObj().getIdentificadorInternoApp().
                            equals(Constant.BASICA_EST_INT_CANCELADO)) {
                        String msn2 = "Existen ordenes abiertas para esta tecnologia, proceda a cerrarlas o cancelarlas";
                        FacesContext.getCurrentInstance()
                                .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                    }
                }
            }
        }



        delete(cmtSubEdificioMgl, usuario, perfil);
    }

    public CmtSubEdificioMgl consultarSubedificioXSol(CmtSolicitudCmMgl cmtSolicitudCmMgl,
            CmtBasicaMgl tecBasica) {

        CmtSubEdificioMgl cmtSubEdificioMglRes = null;

        try {

            List<CmtSubEdificioMgl> lstCmtSubEdificioMgls = null;
            CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();

            CmtBasicaMgl basicaMgl = new CmtBasicaMgl();
            basicaMgl.setBasicaId(basicaMglManager.findByCodigoInternoApp(
                    Constant.BASICA_ESTADO_SIN_VISITA_TECNICA).getBasicaId());

            lstCmtSubEdificioMgls = findSubEdifByCmAndEstado(cmtSolicitudCmMgl.getCuentaMatrizObj(), basicaMgl);

            for (CmtSubEdificioMgl cmtSubEdificioMgl : lstCmtSubEdificioMgls) {
                if (cmtSubEdificioMgl.getListTecnologiasSub().size() > 0) {
                    for (CmtTecnologiaSubMgl cmtTecnologiaSubMgl
                            : cmtSubEdificioMgl.getListTecnologiasSub()) {
                        if (cmtTecnologiaSubMgl.getBasicaIdTecnologias().getBasicaId().
                                compareTo(tecBasica.getBasicaId()) == 0) {
                            cmtSubEdificioMglRes = cmtSubEdificioMgl;
                        }
                    }
                }
            }

        } catch (ApplicationException e) {
            LOGGER.error("Error al momento de consultar el subedificio. EX000: " + e.getMessage(), e);
        }

        return cmtSubEdificioMglRes;
    }

    public Long countSubEdificiosCuentaMatriz(BigDecimal id_cuenta_matriz) throws ApplicationException {
        CmtSubEdificioMglDaoImpl cmtSubEdificioMglDaoImpl = new CmtSubEdificioMglDaoImpl();
        return cmtSubEdificioMglDaoImpl.countSubEdificiosCuentaMatriz(id_cuenta_matriz);
    }

    /**
     * Busca lista de sub edificios por nodo especifico
     *
     * @author bocanegra vm
     * @param nodo
     * @return List<CmtSubEdificioMgl>
     * @throws ApplicationException
     */
    public List<CmtSubEdificioMgl> findSubEdificioByNodo(NodoMgl nodo) throws ApplicationException {

        CmtSubEdificioMglDaoImpl cmtSubEdificioMglDaoImpl = new CmtSubEdificioMglDaoImpl();
        return cmtSubEdificioMglDaoImpl.findSubEdificioByNodo(nodo);
    }

    public CmtSubEdificioMgl findByName(String nombreCuentaMatriz) throws ApplicationException {
        CmtSubEdificioMglDaoImpl cmtSubEdificioMglDaoImpl = new CmtSubEdificioMglDaoImpl();
        return cmtSubEdificioMglDaoImpl.countSubEdificiosCuentaMatriz(nombreCuentaMatriz);
    }

    /**
     * Buscar un sub edificio general de una cuenta matriz especifica
     *
     * @author Bocanegra vm
     * @param cuentaMatriz Condicional para buscar el subedificio
     * @return Un obnbjeto de tipo CmtSubEdificioMgl
     * @throws ApplicationException
     */
    public CmtSubEdificioMgl findSubEdificioGeneralByCuentaMatrizEliminado(CmtCuentaMatrizMgl cuentaMatriz)
            throws ApplicationException {

        CmtSubEdificioMglDaoImpl cmtSubEdificioMglDaoImpl = new CmtSubEdificioMglDaoImpl();
        CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();
        CmtBasicaMgl multiedificio = basicaMglManager.findByCodigoInternoApp(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO);
        return cmtSubEdificioMglDaoImpl.findSubEdificioGeneralByCuentaMatrizEliminado(cuentaMatriz, multiedificio);
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
    public String nombreSubedificioRR(CmtSubEdificioMgl subEdificioMgl)
            throws UniformInterfaceException, IOException, ApplicationException {

        String nombreSubedificio = "";
        
        parametrosMglManager = new ParametrosMglManager();
        try {
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg, ex);
        }
        restClientCuentasMatrices = new RestClientCuentasMatrices(BASE_URI);

        //consulto la direccion del subedificio
        RequestDataManttoSubEdificios request =
                new RequestDataManttoSubEdificios();
        request.setCodigoCuenta(subEdificioMgl.getCmtCuentaMatrizMglObj().getNumeroCuenta().toString());

        ResponseManttoSubEdificiosList response = restClientCuentasMatrices.callWebService(
                EnumeratorServiceName.CM_MANTTO_SUBEDIFICIOS_QUERY,
                ResponseManttoSubEdificiosList.class,
                request);

        if (response.getResultado().equalsIgnoreCase(Constant.ERROR_RESULTADO_RR)) {
            LOGGER.error(response.getMensaje());
        } else {
            if (response.getListManttSubEdificios().size() > 0) {
                for (ResponseDataManttoSubEdificios subedificios : response.getListManttSubEdificios()) {
                    if (subedificios.getCodigo().trim().
                            equalsIgnoreCase(subEdificioMgl.getCodigoRr())) {
                        nombreSubedificio = subedificios.getDescripcion().trim();
                    }
                }
            }
        }
        return nombreSubedificio;

    }
    
        private AddressEJBRemote lookupaddressEJBBean() {
        try {
            Context c = new InitialContext();
            return (AddressEJBRemote) c.lookup("addressEJB#co.com.telmex.catastro.services.georeferencia.AddressEJBRemote");
        } catch (NamingException ne) {
            LOGGER.error(ne.getMessage());
            throw new RuntimeException(ne);
        }
    }
    
    public boolean validarEliminacionTecnologia(List<CmtTecnologiaSubMgl> cmtTecnologiaSubMglList)
            throws ApplicationException {

        CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
        CmtBasicaExtMglManager cmtBasicaExtMglManager = new CmtBasicaExtMglManager();
        CmtTipoBasicaMgl cmtTipoBasicaMgl;
        boolean respuesta=false;
        int conTrue = 0;


        cmtTipoBasicaMgl = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                co.com.claro.mgl.utils.Constant.TIPO_BASICA_ESTADO_CUENTA_MATRIZ);

        if (cmtTecnologiaSubMglList != null && !cmtTecnologiaSubMglList.isEmpty()) {
            for (CmtTecnologiaSubMgl cmtTecnologiaSubMgl : cmtTecnologiaSubMglList) {
                if (cmtTipoBasicaMgl.getListCmtTipoBasicaExtMgl().size() > 0) {
                    for (CmtTipoBasicaExtMgl cmtTipoBasicaExtMgl : cmtTipoBasicaMgl.getListCmtTipoBasicaExtMgl()) {
                        if (cmtTipoBasicaExtMgl.getCampoEntidadAs400().equalsIgnoreCase(cmtTecnologiaSubMgl.getBasicaIdTecnologias().getIdentificadorInternoApp())) {
                            List<CmtBasicaExtMgl> estadosDeLaTecno
                                    = cmtBasicaExtMglManager.findByTipoBasicaExt(cmtTipoBasicaExtMgl);

                            if (estadosDeLaTecno != null && estadosDeLaTecno.size() > 0) {
                                for (CmtBasicaExtMgl cmtBasicaExtMgl : estadosDeLaTecno) {

                                    if (cmtBasicaExtMgl != null && cmtBasicaExtMgl.getIdBasicaObj() != null
                                            && cmtBasicaExtMgl.getIdBasicaObj().getBasicaId().
                                                    compareTo(cmtTecnologiaSubMgl.getBasicaIdEstadosTec().getBasicaId()) == 0) {

                                        if (cmtBasicaExtMgl.getValorExtendido() != null
                                                && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")) {
                                            conTrue++;
                                            LOGGER.info("Se puede eliminar la tecnologia: "
                                                    + "" + cmtTecnologiaSubMgl.getBasicaIdTecnologias().getNombreBasica() + "  "
                                                    + "por que esta en su estado inicial: " + cmtTecnologiaSubMgl.getBasicaIdEstadosTec().getNombreBasica() + "");

                                        } else {
                                            throw new ApplicationException("No se pueden borrar el sub-edificio porque la tecnologia:  "
                                                    + "" + cmtTecnologiaSubMgl.getBasicaIdTecnologias().getNombreBasica() + " no tiene 'Y'"
                                                    + "en su estado inicial. Actualmente "
                                                    + "esta en:" + cmtTecnologiaSubMgl.getBasicaIdEstadosTec().getNombreBasica() + "");

                                        }

                                    }
                                }
                                if (conTrue == cmtTecnologiaSubMglList.size()) {
                                    respuesta = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return respuesta;
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
    public ResponseManttoSubEdificiosList consultaSubedificioRR(CmtSubEdificioMgl subEdificioMgl)
            throws UniformInterfaceException, IOException, ApplicationException {

        ResponseManttoSubEdificiosList response;

        parametrosMglManager = new ParametrosMglManager();
        try {
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg, ex);
        }
        restClientCuentasMatrices = new RestClientCuentasMatrices(BASE_URI);

        //consulto la direccion del subedificio
        RequestDataManttoSubEdificios request
                = new RequestDataManttoSubEdificios();
        request.setCodigoCuenta(subEdificioMgl.getCmtCuentaMatrizMglObj().getNumeroCuenta().toString());

        response = restClientCuentasMatrices.callWebService(
                EnumeratorServiceName.CM_MANTTO_SUBEDIFICIOS_QUERY,
                ResponseManttoSubEdificiosList.class,
                request);

        return response;

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
    public ResponseManttoEdificioList consultaUnicoSubedificioRR(CmtCuentaMatrizMgl cuenta)
            throws UniformInterfaceException, IOException, ApplicationException {

        parametrosMglManager = new ParametrosMglManager();
        try {
            BASE_URI = parametrosMglManager.findByAcronimo(
                    co.com.telmex.catastro.services.util.Constant.BASE_URI_RESTFUL)
                    .iterator().next().getParValor();
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg, ex);
        }
        restClientCuentasMatrices = new RestClientCuentasMatrices(BASE_URI);

        RequestDataManttoEdificio request = new RequestDataManttoEdificio();
        request.setCodigoEdificio(cuenta.getNumeroCuenta().toString());

        ResponseManttoEdificioList response
                = restClientCuentasMatrices.callWebService(
                        EnumeratorServiceName.CM_MANTTO_EDIFICIO_QUERY,
                        ResponseManttoEdificioList.class,
                        request);

        return response;

    }
    
    public void updateCompania(CmtSubEdificioMgl t, String usuario, int perfil) throws ApplicationException {
        CmtSubEdificioMglDaoImpl cmtSubEdificioMglDaoImpl = new CmtSubEdificioMglDaoImpl();
        cmtSubEdificioMglDaoImpl.updateCompania(t, usuario, perfil);
    }

    
}
