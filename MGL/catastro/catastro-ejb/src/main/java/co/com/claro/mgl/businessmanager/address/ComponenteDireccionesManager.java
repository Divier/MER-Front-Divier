/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.PcmlFacadeLocal;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.UnidadStructPcml;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.claro.visitasTecnicas.facade.DetalleDireccionEJBRemote;
import co.com.claro.visitasTecnicas.facade.ParametrosMultivalorEJB;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.services.comun.Constantes;
import co.com.telmex.catastro.services.georeferencia.AddressEJBRemote;
import co.com.telmex.catastro.services.util.Constant;
import co.com.telmex.catastro.utilws.ResponseMessage;
import com.jlcg.db.exept.ExceptionDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Parzifal de León
 */
public class ComponenteDireccionesManager {

    private ParametrosMultivalorEJB parametrosMultivalorEJBRemote = new ParametrosMultivalorEJB();
    private static final Logger LOGGER = LogManager.getLogger(ComponenteDireccionesManager.class);
    private static String ADDRESSEJB = "addressEJB#co.com.telmex.catastro.services.georeferencia.AddressEJBRemote";
    private static final String EXISTE = "true";

    /**
     *
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException @throws Exception
     * @throws javax.naming.NamingException
     */
    public static AddressEJBRemote getAddressEjb() throws ApplicationException, NamingException {
        InitialContext ctx = new InitialContext();
        AddressEJBRemote obj = (AddressEJBRemote) ctx.lookup(ADDRESSEJB);
        return obj;
    }

    /**
     * consulta los datos de Ciudad, Departamento y Pais
     *
     * @param codCity
     * @return
     */
    public CityEntity getCityData(String codCity) {

        try {
            return parametrosMultivalorEJBRemote.getCiudadByCodDane(codCity);
        } catch (ApplicationException e) {
            LOGGER.error("Error al consultando los datos de la Ciudad ", e);
            return null;
        }

    }

    public BigDecimal findSolicitudInProcess(DetalleDireccionEntity detalleDireccionEntity, String comunidad) {
        try {
            return parametrosMultivalorEJBRemote.isSolicitudInProcess(detalleDireccionEntity, comunidad);
        } catch (Exception ex) {
            LOGGER.error("No se puede realizar la consulta de la solicitud para cononcer si esta en proceso");
        }
        return BigDecimal.ZERO;
    }

    /**
     * Valida la direccion ingresada por el usuario
     *
     * @param cityEntityRequest
     * @return
     */
    public String validarDir(CityEntity cityEntityRequest) {
        try {
            //Consultar el WebService parera obtener los datos de la direccion ingresada
            AddressRequest requestSrv = new AddressRequest();

            //Asignar la ciudad
            requestSrv.setCity(cityEntityRequest.getCityName());
            //Asignar el departamento
            requestSrv.setState(cityEntityRequest.getDpto());
            //Asignar la direccion a consultar
            requestSrv.setAddress(cityEntityRequest.getAddress());
            //Asignar el barrio
            requestSrv.setNeighborhood(cityEntityRequest.getBarrio());

            requestSrv.setLevel(Constant.TIPO_CONSULTA_COMPLETA);

            AddressService responseSrv = queryAddressEnrich(requestSrv);
            if (responseSrv != null) {
                cityEntityRequest.setExistencia(responseSrv.getExist());

                // Estandarizacion del Nodo
                cityEntityRequest.setNodo1(estandarizaNodo(responseSrv.getNodoUno()));
                cityEntityRequest.setNodo2(estandarizaNodo(responseSrv.getNodoDos()));
                cityEntityRequest.setNodo3(estandarizaNodo(responseSrv.getNodoTres()));

                cityEntityRequest
                        .setEstratoDir(responseSrv.getLeveleconomic() == null ? "0" : responseSrv.getLeveleconomic());
                cityEntityRequest.setEstadoDir(responseSrv.getState() == null ? "" : responseSrv.getState());

                cityEntityRequest.setDirStandar(responseSrv.getTraslate());

                // si el barrio viene nulo o vacio asignamos el que nos entrega el GEO
                if (responseSrv.getNeighborhood() != null
                        && !responseSrv.getNeighborhood().isEmpty()
                        && !responseSrv.getNeighborhood().equalsIgnoreCase("N.N.")) {

                    cityEntityRequest.setBarrio(responseSrv.getNeighborhood());

                }
                cityEntityRequest.setExistencia(responseSrv.getExist());
                if (responseSrv.getAddress() == null || responseSrv.getAddress().equals("")) {
                    cityEntityRequest.setDireccion(" ");
                } else {
                    cityEntityRequest.setDireccion(responseSrv.getAddress());
                }

                if (responseSrv.getAddressSuggested() != null) {
                    cityEntityRequest.setBarrioSugerido(responseSrv.getAddressSuggested());
                }

                if (responseSrv.getExist().equalsIgnoreCase(EXISTE)
                        && (responseSrv.getIdaddress() == null
                                || responseSrv.getIdaddress().equals(Constantes.ID_DIR_REPO_NULL))) {
                    FacesContext facesContext = FacesContext.getCurrentInstance();
                    HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
                    String usuario = (String) session.getAttribute("usuarioDir");
                    return guardarDireccionRepo(cityEntityRequest.getAddress(),
                            cityEntityRequest.getCityName(),
                            cityEntityRequest.getDpto(),
                            cityEntityRequest.getBarrio(),
                            usuario, cityEntityRequest.getCodDane());
                }
                return responseSrv.getIdaddress();
            }
            return "";
        } catch (Exception ex) {
            LOGGER.error("Error al validar la direccion ", ex);
            return null;
        }

    }

    /**
     * @param cityEntityRequest
     * @return queryAddressEnrich
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public AddressService validarDirAlterna(CityEntity cityEntityRequest) throws ApplicationException {
        AddressRequest requestSrv = new AddressRequest();
        //Asignar la ciudad
        requestSrv.setCity(cityEntityRequest.getCityName());
        //Asignar el departamento
        requestSrv.setState(cityEntityRequest.getDpto());
        //Asignar la direccion a consultar
        requestSrv.setAddress(cityEntityRequest.getAddress());
        //Asignar el barrio
        requestSrv.setNeighborhood(cityEntityRequest.getBarrio());

        requestSrv.setLevel(Constant.TIPO_CONSULTA_COMPLETA);
        return queryAddressEnrich(requestSrv);
    }
    
    public String guardarDireccionRepo(String address, String city, String dpto,
            String barrio, String user, DrDireccion dirCrear, String codigoDane) {
        try {
            AddressRequest req = new AddressRequest();
            req.setAddress(address);
            req.setCity(city);
            req.setState(dpto);
            req.setNeighborhood(barrio);
            req.setCodDaneVt(codigoDane);
            ResponseMessage response = createAddress(req, user, Constantes.NOMBRE_FUNCIONALIDAD, "", dirCrear);
            return response != null ? response.getIdaddress() : "";
        } catch (Exception ex) {
            LOGGER.error("Error al guardar la direccion ", ex);
            return null;
        }
    }
    
    public String guardarDireccionRepo(String address, String city, String dpto, String barrio, String user, String codigoDane) {
        try {
            AddressRequest req = new AddressRequest();
            req.setAddress(address);
            req.setCity(city);
            req.setState(dpto);
            req.setNeighborhood(barrio);
            req.setCodDaneVt(codigoDane);
            ResponseMessage response = createAddress(req, user, Constantes.NOMBRE_FUNCIONALIDAD, "");
            return response != null ? response.getIdaddress() : "";
        } catch (Exception ex) {
            LOGGER.error("Error al guardar la direccion ", ex);
            return null;
        }
    }

    public static String estandarizaNodo(String nodo) {
        String nodoResult = nodo;
        if (isNodo6080(nodo)) {
            nodoResult = "0" + nodo.trim();
        }
        return nodoResult;
    }

    private static boolean isNodo6080(String nodo) {

        Pattern pat = Pattern.compile("6[0-9]|7[234789]|80");
        Matcher mat = pat.matcher(nodo);

        return !nodo.trim().isEmpty() && (mat.matches());
    }

    /**
     *
     * @param addressRequest
     * @param user
     * @param nombreFuncionalidad
     * @param validate
     * @param dirCrear
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     * @throws Exception
     */
    public static ResponseMessage createAddress(AddressRequest addressRequest, String user, String nombreFuncionalidad,
            String validate, DrDireccion dirCrear) throws ApplicationException {
        ResponseMessage response = null;
        try {
            AddressEJBRemote obj = getAddressEjb();
            if (obj != null) {
                response = obj.createAddress(addressRequest, user, nombreFuncionalidad, validate, dirCrear);
            }
        } catch (NamingException|ExceptionDB e) {
            LOGGER.error("Ha ocurrido un error bucando el EJB " + ADDRESSEJB, e);
        } 
        return response;
    }
    
    public static ResponseMessage createAddress(AddressRequest addressRequest, String user, String nombreFuncionalidad,
            String validate) throws ApplicationException {
        ResponseMessage response = null;
        try {
            AddressEJBRemote obj = getAddressEjb();
            if (obj != null) {
                response = obj.createAddress(addressRequest, user, nombreFuncionalidad, validate);
            }
        } catch (NamingException e) {
            LOGGER.error("Ha ocurrido un error bucando el EJB " + ADDRESSEJB, e);
        }
        return response;
    }

    /**
     *
     * @param addressRequest
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static AddressService queryAddressEnrich(AddressRequest addressRequest) throws ApplicationException {
        AddressService addressService = null;
        try {
            AddressEJBRemote obj = getAddressEjb();
            if (obj != null) {
                addressService = obj.queryAddressEnrich(addressRequest);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al momento de consultar la dirección. EX000: " + ex.getMessage(), ex);
        }
        return addressService;
    }

    public static UnidadStructPcml getUnidadesDir(PcmlFacadeLocal pcmlFacadeLocal, DetalleDireccionEntity direccionEntity, String codCity) throws ApplicationException {
        UnidadStructPcml result = null;
        if (direccionEntity != null) {
            DireccionRRManager direccionRRManager = new DireccionRRManager(direccionEntity, direccionEntity.getMultiOrigen(), null);
            String calle = direccionRRManager.getDireccion().getCalle();
            String casa = direccionRRManager.getDireccion().getNumeroUnidad();
            String apto = direccionRRManager.getDireccion().getNumeroApartamento();
            List<UnidadStructPcml> lista;
            lista = pcmlFacadeLocal.getUnidades(calle, casa, apto, codCity);
            if (!lista.isEmpty()) {
                return lista.get(0);
            }
        }
        return result;
    }

    public static ArrayList<String> getDirStandarRR(DetalleDireccionEntity direccionEntity) throws ApplicationException {
        ArrayList<String> formatoRR = new ArrayList<String>();
        if (direccionEntity != null) {
            DireccionRRManager direccionRRManager = new DireccionRRManager(direccionEntity, direccionEntity.getMultiOrigen(), null);
            formatoRR.add(0, direccionRRManager.getDireccion().getCalle());
            formatoRR.add(1,  direccionRRManager.getDireccion().getNumeroUnidad());
            formatoRR.add(2, direccionRRManager.getDireccion().getNumeroApartamento());            
        }
        return formatoRR;
    }
    
    public static List<DetalleDireccionEntity> queryConsultarDetalle(String idSolicitud) {
        List<DetalleDireccionEntity> direcciones = null;
        try {
            DetalleDireccionEJBRemote obj = getDetalleDireccionEjb();
            if (obj != null) {
                try {
                    direcciones = obj.consultarDireccionPorSolicitud(idSolicitud);
                } catch (ApplicationException e) {
                    LOGGER.error("Error Consultando la informacion del detalle de la direccion " + e.getMessage());
                }
            }

        } catch (NamingException e) {
            LOGGER.info("Ha ocurrido un error bucando el EJB " + DETALLEDIRECCIONEJB, e);
        }
        return direcciones;
    }
    private static String DETALLEDIRECCIONEJB = "detalleDireccionEJB#co.com.claro.visitasTecnicas.facade.DetalleDireccionEJBRemote";

    private static DetalleDireccionEJBRemote getDetalleDireccionEjb() throws NamingException {
        InitialContext ctx = new InitialContext();
        DetalleDireccionEJBRemote obj = (DetalleDireccionEJBRemote) ctx.lookup(DETALLEDIRECCIONEJB);
        return obj;
    }

    public static boolean isIdDirCatastroNull(String idCatastro) {
        LOGGER.error("Validando idCatastro isIdDirCatastroNull: " + idCatastro);
        return idCatastro == null
                || idCatastro.equals(Constantes.ID_DIR_REPO_NULL)
                || idCatastro.isEmpty();
    }

    /**
     * Objetivo y descripcion.metodo para consultar el servicio pcmlService, y
 traer la informacion de las unidades encontradas
     *
     * @author yimy diaz
     * @param pcmlFacadeLocal
     * @param direccionEntity
     * @param codCity
     * @return List<UnidadStructPcml>
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<UnidadStructPcml> getUnidadesCruce(PcmlFacadeLocal pcmlFacadeLocal,
            DetalleDireccionEntity direccionEntity, String codCity)
            throws ApplicationException {
        List<UnidadStructPcml> lista = null;
        if (direccionEntity != null) {
            DireccionRRManager direccionRRManager =
                    new DireccionRRManager(direccionEntity, direccionEntity.getMultiOrigen(), null);
            String calle = direccionRRManager.getDireccion().getCalle();
            String casa =
                    direccionRRManager.getDireccion().getNumeroUnidad().contains("-")
                    ? direccionRRManager.getDireccion().getNumeroUnidad().split("-")[0]
                    : direccionRRManager.getDireccion().getNumeroUnidad();
            String apto = "";
            lista = pcmlFacadeLocal.getUnidades(calle, casa, apto, codCity);
        }
        return lista;
    }
}
