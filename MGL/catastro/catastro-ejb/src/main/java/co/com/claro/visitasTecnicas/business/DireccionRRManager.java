package co.com.claro.visitasTecnicas.business;

import co.com.claro.mer.utils.TransactionParameters;
import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.businessmanager.address.HhppMglManager;
import co.com.claro.mgl.businessmanager.address.MarcasHhppMglManager;
import co.com.claro.mgl.businessmanager.address.MarcasMglManager;
import co.com.claro.mgl.businessmanager.address.NodoMglManager;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtBasicaMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtComunidadRrManager;
import co.com.claro.mgl.businessmanager.cm.CmtTipoBasicaMglManager;
import co.com.claro.mgl.dao.impl.MarcasMglDaoImpl;
import co.com.claro.mgl.dtos.CmtDefaultBasicResponse;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.EstadoHhppMgl;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.MarcasHhppMgl;
import co.com.claro.mgl.jpa.entities.MarcasMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.NotasAdicionalesMgl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.PreFichaGeorreferenciaMgl;
import co.com.claro.mgl.jpa.entities.PreFichaHHPPDetalleMgl;
import co.com.claro.mgl.jpa.entities.PreFichaXlsMgl;
import co.com.claro.mgl.jpa.entities.PreFichaXlsMglNew;
import co.com.claro.mgl.jpa.entities.TecTipoMarcas;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtHhppVtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import co.com.claro.visitasTecnicas.entities.HhppResponseQueryRegularUnitRR;
import co.com.claro.visitasTecnicas.entities.HhppResponseRR;
import co.com.claro.visitastecnicas.ws.proxy.AddUnitRequest;
import co.com.claro.visitastecnicas.ws.proxy.CRUDUnitManagerResponse;
import co.com.claro.visitastecnicas.ws.proxy.ChangeUnitAddressRequest;
import co.com.claro.visitastecnicas.ws.proxy.ChangeUnitAddressResponse;
import co.com.claro.visitastecnicas.ws.proxy.CreateServiceCallRequest;
import co.com.claro.visitastecnicas.ws.proxy.CreateServiceCallResponse;
import co.com.claro.visitastecnicas.ws.proxy.PortManager;
import co.com.claro.visitastecnicas.ws.proxy.QueryRegularUnitResponse;
import co.com.claro.visitastecnicas.ws.proxy.RequestCRUDUnit;
import co.com.claro.visitastecnicas.ws.proxy.RequestQueryRegularUnit;
import co.com.claro.visitastecnicas.ws.proxy.RequestSpecialUpdate;
import co.com.claro.visitastecnicas.ws.proxy.RequestStreetGrid;
import co.com.claro.visitastecnicas.ws.proxy.RequestUnitAddInf;
import co.com.claro.visitastecnicas.ws.proxy.SpecialUpdateManagerResponse;
import co.com.claro.visitastecnicas.ws.proxy.UnitAddInfManagerResponse;
import co.com.claro.visitastecnicas.ws.proxy.UnitManagerResponse;
import co.com.claro.visitastecnicas.ws.proxy.UpdateStreetGridResponse;
import co.com.telmex.catastro.data.Direccion;
import co.com.telmex.catastro.data.EstadoHhpp;
import co.com.telmex.catastro.data.Hhpp;
import co.com.telmex.catastro.data.Nodo;
import co.com.telmex.catastro.data.SubDireccion;
import co.com.telmex.catastro.data.TipoHhpp;
import co.com.telmex.catastro.data.TipoHhppConexion;
import co.com.telmex.catastro.data.TipoHhppRed;
import co.com.telmex.catastro.services.comun.Constantes;
import co.com.telmex.catastro.services.util.Constant;
import co.com.telmex.catastro.services.util.DireccionUtil;
import co.com.telmex.catastro.services.util.Parametros;
import co.com.telmex.catastro.services.util.ResourceEJB;
import co.com.telmex.catastro.services.ws.initialize.Initialized;
import co.net.cable.agendamiento.ws.AgendaPortType;
import co.net.cable.agendamiento.ws.AgendaPortTypeProxy;
import co.net.cable.agendamiento.ws.AgendarConError;
import co.net.cable.agendamiento.ws.Aliado;
import co.net.cable.agendamiento.ws.AliadoConError;
import co.net.cable.agendamiento.ws.Cupo;
import co.net.cable.agendamiento.ws.RetornoAgenda;
import co.net.cable.agendamiento.ws.TipoTrabajo;
import co.net.cable.agendamiento.ws.TipoTrabajoConError;
import com.jlcg.db.AccessData;
import com.jlcg.db.data.DataObject;
import com.jlcg.db.exept.ExceptionDB;
import com.jlcg.db.sql.ManageAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import co.com.claro.mer.utils.DateToolUtils;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

/**
 *
 * @author ADMIN
 */
public class DireccionRRManager {

    private static final Logger LOGGER = LogManager.getLogger(DireccionRRManager.class);
    private static final String VERIFICACION_CASAS = "VERIFICACION_CASAS";
    private static final String UNIDIRECCIONAL = "UNIDIRECCIONAL";
    private static final String CUENTA_MATRIZ = co.com.claro.mgl.utils.Constant.CARPETA_CUENTA_MATRIZ;
    public static final String NFI = "NFI";
    public static final String UNI = "UNI";
    private static final String ESTE = "ESTE";
    private static final String OESTE = "OESTE";
    private static final String ESPACIO = " ";
    private static final String SEPARADOR_DIRECCION = "-";
    private static final String BARRIO = "BARRIO";
    private static final String BIS = "BIS";
    private static final String V_APTO_DEFECTO = "CASA";
    private final NodoManager nodoManager = new NodoManager();
    private final NegocioParamMultivalor negocioMultivalor = new NegocioParamMultivalor();
    private final HHPPManager hhppManager = new HHPPManager();
    private DireccionRREntity direccionRREntity;
    private Direccion detalleDireccion;
    private SubDireccion detalleSubDireccion;
    private DetalleDireccionEntity direccionEntity;
    private String wsURL = "";
    private String wsAgendamientoURL;
    private String wsService = "";
    private String fiel1F18 = "";
    private String bookingTimeCode = "VT";
    private String reasonforService = "VC";
    private String workForceCode = "A";
    private String priority = "1";
    private String nyDefault = "Y";
    private String bookingSeq = "0";
    private String requestServiceTime = "0800";
    private String idProgramacionAgenda = "L";
    private String idTipoTrabajoAgenda = "16";
    private String truckRequiredFlag = "N";
    private List<String> mensajesRespuestaRR = new ArrayList<>();
    private Map<String, List<String>> respuestaXhhpp = new HashMap<>();
    private String direccion = "";
    private CmtBasicaMgl estadoHhppExiste = new CmtBasicaMgl();
    private CmtTipoBasicaMgl cmtTipoBasicaMgl = null;
    private PreFichaXlsMgl prefichaXlsMgl;
    //Nueva implementacion
    private PreFichaXlsMglNew prefichaXlsMglNew;

    public DireccionRRManager(boolean interaccionRR) {
        if (interaccionRR) {
            queryParametrosConfig();
        }
    }

    public DireccionRRManager(DetalleDireccionEntity direccion, String multiOrigen, GeograficoPoliticoMgl centroPoblado) throws ApplicationException {
        queryParametrosConfig();
        String calleRR = "";
        String numeroUnidadRR = "";
        String numeroApartamentoRR = "";
        LOGGER.error("al  constructor DireccionRRManager 1");

        if (direccion != null) {
            if (multiOrigen != null && !multiOrigen.isEmpty()) {
                direccion.setMultiOrigen(multiOrigen);
            } else {
                if (direccion.getMultiOrigen() != null && !direccion.getMultiOrigen().isEmpty()) {
                    multiOrigen = direccion.getMultiOrigen();
                }
            }

            ArrayList<String> arrDirRR = generarDirFormatoRR(direccion);

            if (arrDirRR != null && arrDirRR.size() == 3) {

                calleRR = arrDirRR.get(0).toUpperCase().toUpperCase().replace("Ñ", "N");
                numeroUnidadRR = arrDirRR.get(1).toUpperCase().toUpperCase().replace("Ñ", "N");
                if (arrDirRR.get(2) != null && !arrDirRR.get(2).isEmpty()) {
                    numeroApartamentoRR = arrDirRR.get(2).toUpperCase().toUpperCase().replace("Ñ", "N");
                } else {
                    numeroApartamentoRR = arrDirRR.get(2);
                }

                if (direccion.getCptiponivel5() != null) {
                    if (direccion.getCptiponivel5().equalsIgnoreCase("OTROS")) {
                        numeroApartamentoRR = numeroApartamentoRR.substring(3, numeroApartamentoRR.length());
                    }
                    if (numeroApartamentoRR.trim().length() > 10) {
                        numeroApartamentoRR = numeroApartamentoRR.substring(0, 10);
                    }
                }

                //@author Juan David Hernandez
                boolean habilitarRR = false;
                ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
                ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(co.com.claro.mgl.utils.Constant.HABILITAR_RR);
                if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
                    habilitarRR = true;
                }

                //solo si es corregimiento recibir el centro poblado, sino no es necesario.
                if (centroPoblado != null) {
                    if (centroPoblado.getCorregimiento() != null
                            && !centroPoblado.getCorregimiento().isEmpty()
                            && centroPoblado.getCorregimiento().equalsIgnoreCase("Y")) {

                        calleRR += " ";
                        String[] corregimiento = centroPoblado.getGpoNombre().split(" ");
                        for (int i = 0; i < corregimiento.length; i++) {
                            calleRR += corregimiento[i];
                        }

                        if (calleRR.trim().length() > 50) {
                            throw new ApplicationException("Campo Calle ha superado el número máximo de caracteres en formato RR [" + calleRR + "] al añadir el corregimiento.");
                        }
                    }
                }
                //Si RR esta apagado no debe realizar validaciones de longitud de caracteres
                if (habilitarRR) {
                    if (calleRR.trim().length() > 50) {
                        throw new ApplicationException("Campo Calle ha superado el número máximo de caracteres en formato RR [" + calleRR + "]");
                    } else if (numeroUnidadRR.trim().length() > 10) {
                        throw new ApplicationException("El campo Número de Unidad ha superado el número máximo de caracteres en formato RR [" + numeroUnidadRR + "]");
                    } else if (numeroApartamentoRR.trim().length() > 10) {
                        throw new ApplicationException("El campo Número de Apartamento ha superado el número máximo de caracteres en formato RR [" + numeroApartamentoRR + "]");
                    }
                }

            } else {
                throw new ApplicationException("No fue posible formatear la Direccion a RR");
            }
        }

        LOGGER.error("al  constructor DireccionRRManager 2");
        this.direccionRREntity = new DireccionRREntity(calleRR, numeroUnidadRR, numeroApartamentoRR);
        this.direccionEntity = direccion;
    }

    public HhppResponseRR getHhppRr(String division, String comunidad, String aptoNum, String numeroCasa, String nombreCalle) {
        SOAPMessage soapMessage;
        HhppResponseRR result = null;
        try {
            RequestQueryRegularUnit requestQueryRegularUnit = new RequestQueryRegularUnit();
            requestQueryRegularUnit.setCommunity(comunidad);
            requestQueryRegularUnit.setDivision(division);
            requestQueryRegularUnit.setHouseNumber(numeroCasa);
            requestQueryRegularUnit.setApartmentNumber(aptoNum);
            requestQueryRegularUnit.setStreetName(nombreCalle);
            PortManager portManager = new PortManager(wsURL, wsService);
            try {
                soapMessage = portManager.queryRegularUnitHhPp(requestQueryRegularUnit);
            } catch (ApplicationException e) {
                soapMessage = portManager.queryRegularUnitHhPp(requestQueryRegularUnit);
                LOGGER.error("Error al intentar obtener las ordenes de trabajo, se consume nuevamente el servicio " + e.getMessage());
            }
            result = setSoapMessage(soapMessage);
        } catch (ApplicationException e) {
            LOGGER.error("Error al momento de obtener el HHPP de RR. EX000: " + e.getMessage(), e);
        }
        return result;
    }

    public HhppResponseRR setSoapMessage(SOAPMessage soapMessage) {
        HhppResponseRR responseRR = new HhppResponseRR();
        ByteArrayOutputStream baos;
        try {
            baos = new ByteArrayOutputStream();
            soapMessage.writeTo(baos);
            String result = baos.toString();
            String[] lines = result.split(System.getProperty("line.separator"));
            // ExistenciaEnRR
            if (lines.length == 1) {
                responseRR.setExistenciaUnidad(false);
                responseRR.setMensaje("Direccion no se encuentra registrada");
                return responseRR;
            }

            for (int i = 1; i <= lines.length - 1; i++) {
                //Estado Unidad
                if (lines[i].contains("(XOR1000)")) {
                    responseRR.setExistenciaUnidad(true);
                }
                if (lines[i].contains("STAT")) {
                    responseRR.setEstadoUnidad(lines[i].replace("STAT|", "").trim());
                }
                if (lines[i].contains("WONO")) {
                    responseRR.setwOrder(lines[i].replace("WONO|", "").trim());
                }
                if (lines[i].contains("STNM")) {
                    responseRR.setStreet(lines[i].replace("STNM|", "").trim());
                }
                if (lines[i].contains("APTN")) {
                    responseRR.setApartamento(lines[i].replace("APTN|", "").trim());
                }
                if (lines[i].contains("HOME")) {
                    responseRR.setHouse(lines[i].replace("HOME|", "").trim());
                }
                if (lines[i].contains("SUBS")) {
                    responseRR.setCuenta(lines[i].replace("SUBS|", "").trim());
                }

                if (lines[i].contains("CCDE")) {
                    responseRR.setComunidad(lines[i].replace("CCDE|", "").trim());
                }

                if (lines[i].contains("DCDE")) {
                    responseRR.setDivision(lines[i].replace("DCDE|", "").trim());
                }

            }
        } catch (SOAPException | IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
        }
        return responseRR;
    }

    public DireccionRRManager(DetalleDireccionEntity direccion) throws ApplicationException, ApplicationException {
        LOGGER.error("al inicio constructor DireccionRRManager");
        queryParametrosConfig();

        String calleRR = "";
        String numeroUnidadRR = "";
        String numeroApartamentoRR = "";
        LOGGER.error("al  constructor DireccionRRManager 1");

        ArrayList<String> arrDirRR = generarDirFormatoRR(direccion);

        if (arrDirRR != null && arrDirRR.size() == 3) {

            calleRR = arrDirRR.get(0).toUpperCase().toUpperCase().replace("Ñ", "N");;
            numeroUnidadRR = arrDirRR.get(1).toUpperCase().toUpperCase().replace("Ñ", "N");
            if (arrDirRR.get(2) != null && !arrDirRR.get(2).isEmpty()) {
                numeroApartamentoRR = arrDirRR.get(2).toUpperCase().toUpperCase().replace("Ñ", "N");
            } else {
                numeroApartamentoRR = arrDirRR.get(2);
            }
            if (direccion.getCptiponivel5() != null) {
                if (direccion.getCptiponivel5().equalsIgnoreCase("Otros")) {
                    numeroApartamentoRR = numeroApartamentoRR.substring(3, numeroApartamentoRR.length());
                }
                if (numeroApartamentoRR.trim().length() > 10) {
                    numeroApartamentoRR = numeroApartamentoRR.substring(0, 10);
                }
            }

            //@author Juan David Hernandez
            boolean habilitarRR = false;
            ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
            ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(co.com.claro.mgl.utils.Constant.HABILITAR_RR);
            if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
                habilitarRR = true;
            }
            //Si RR esta apagado no debe realizar validaciones de longitud de caracteres
            if (habilitarRR) {
                if (calleRR.trim().length() > 50) {
                    throw new ApplicationException("Campo Calle ha superado el número máximo de caracteres en formato RR [" + calleRR + "]");
                } else if (numeroUnidadRR.trim().length() > 10) {
                    throw new ApplicationException("El campo Número de Unidad ha superado el número máximo de caracteres en formato RR [" + numeroUnidadRR + "]");
                } else if (numeroApartamentoRR.trim().length() > 10) {
                    throw new ApplicationException("El campo Número de Apartamento ha superado el número máximo de caracteres en formato RR [" + numeroApartamentoRR + "]");
                }
            }
        } else {
            throw new ApplicationException("No fue posible formatear la Direccion a RR");
        }

        LOGGER.error("al  constructor DireccionRRManager 2");

        ConsultaEspecificaManager manager = new ConsultaEspecificaManager();
        String idDirCatastro = direccion.getIdDirCatastro();
        if (idDirCatastro != null && !idDirCatastro.isEmpty()) {
            String indicador = idDirCatastro.substring(0, 1);
            if (indicador.equalsIgnoreCase("d")) {
                detalleDireccion = manager.queryAddressOnRepoById(idDirCatastro.substring(1));
            } else if (indicador.equalsIgnoreCase("s")) {
                detalleSubDireccion = manager.querySubAddressOnRepositoryById(new BigDecimal(idDirCatastro.substring(1)));
                detalleDireccion = manager.queryAddressOnRepoById(detalleSubDireccion.getDireccion().getDirId().toString());

            }
        }

        //JDHT
        /*Ajuste para determinar si la dirección se encuentra en 
         un corregimiento y adicionar el corregimiento a la calle 
         de la dirección al formato RR*/
        if (detalleDireccion != null
                && detalleDireccion.getUbicacion() != null
                && detalleDireccion.getUbicacion().getGpoId() != null
                && detalleDireccion.getUbicacion().getGpoId().getGpoId() != null) {

            GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
            GeograficoPoliticoMgl centroPobladoCompleto;
            centroPobladoCompleto = geograficoPoliticoManager.findGeoPoliticoById(detalleDireccion.getUbicacion().getGpoId().getGpoId());

            if (centroPobladoCompleto != null) {
                if (centroPobladoCompleto.getCorregimiento() != null
                        && !centroPobladoCompleto.getCorregimiento().isEmpty()
                        && centroPobladoCompleto.getCorregimiento().equalsIgnoreCase("Y")) {

                    calleRR += " ";
                    String[] corregimiento = centroPobladoCompleto.getGpoNombre().split(" ");
                    for (int i = 0; i < corregimiento.length; i++) {
                        calleRR += corregimiento[i];
                    }

                    if (calleRR.trim().length() > 50) {
                        throw new ApplicationException("Campo Calle ha superado el número máximo de caracteres en formato RR [" + calleRR + "] al añadir el corregimiento.");
                    }
                }
            }
        }

        direccionEntity = direccion;
        this.direccionRREntity = new DireccionRREntity(calleRR, numeroUnidadRR, numeroApartamentoRR);
        direccionRREntity.setField1F18(fiel1F18);

        LOGGER.error("al  constructor DireccionRRManager 4");
    }

    private String ajustarNumeroApto(String numeroApartamentoRR) {
        String resultNumApto = numeroApartamentoRR;
        if (numeroApartamentoRR.contains("LC")) {
            if (!numeroApartamentoRR.startsWith("PI")) {
                resultNumApto = numeroApartamentoRR.replace("LC", "LOCAL");
            }
        }
        if ((numeroApartamentoRR.startsWith("CS") || numeroApartamentoRR.startsWith("CASA"))
                && numeroApartamentoRR.contains("PISO")) {
            resultNumApto = numeroApartamentoRR.replace("PISO", "PI");
        }
        //garantizamos que el numero de apartamento tenga como minimo 3 caracteres
        String complementoStr = "0";
        Pattern patternN = Pattern.compile("[0-9]{1,2}|[0-9][a-zA-Z]");//numero de 1-2 posiciones - numero letra
        Pattern patternLn = Pattern.compile("[a-zA-Z][0-9]");//letra numero

        if (patternN.matcher(numeroApartamentoRR).matches()) {
            if (numeroApartamentoRR.length() == 1) {
                complementoStr += "0";
            }
            resultNumApto = complementoStr + numeroApartamentoRR;

        } else if (patternLn.matcher(numeroApartamentoRR).matches()) {            
            resultNumApto = numeroApartamentoRR.substring(0, 1) + complementoStr + numeroApartamentoRR.substring(1);
        }
        return resultNumApto;
    }

    private String ajustarNumeroUnidad(String numeroUnidadRR) {
        String numUnidad = numeroUnidadRR;
        Pattern pattern = Pattern.compile("^([MZ|mz])0.*");
        while (pattern.matcher(numUnidad).matches()) {
            int pos = numUnidad.indexOf("0");
            numUnidad = "MZ" + numUnidad.substring(pos + 1);
        }
        pattern = Pattern.compile(".*-([Ll]|[Cc])0.*");
        while (pattern.matcher(numUnidad).matches()) {
            int pos = numUnidad.indexOf("0");
            numUnidad = numUnidad.substring(0, pos) + numUnidad.substring(pos + 1);
        }
        return numUnidad;
    }

    private void queryParametrosConfig() {
        ResourceEJB resourceEJB = new ResourceEJB();
        Initialized.getInstance();
        Parametros param;
        CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();
        CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();

        try {
            param = resourceEJB.queryParametros(Constant.PROPERTY_URL_WSRR);
            if (param != null) {
                wsURL = param.getValor();
            }

            param = resourceEJB.queryParametros(Constant.PROPERTY_SERVICE_WSRR);
            if (param != null) {
                wsService = param.getValor();
            }

            //Cargue parametros configuracion Service Call
            param = resourceEJB.queryParametros(Constant.SERVICE_CALL_BOOKING_TIME_CODE);
            if (param != null) {
                bookingTimeCode = param.getValor();
            }
            param = resourceEJB.queryParametros(Constant.SERVICE_CALL_REASON_FOR_SERVICE);
            if (param != null) {
                reasonforService = param.getValor();
            }
            param = resourceEJB.queryParametros(Constant.SERVICE_CALL_WORK_FORCE_CODE);
            if (param != null) {
                workForceCode = param.getValor();
            }
            param = resourceEJB.queryParametros(Constant.SERVICE_CALL_PRIORITY);
            if (param != null) {
                priority = param.getValor();
            }
            param = resourceEJB.queryParametros(Constant.SERVICE_CALL_NY_DEFAULT);
            if (param != null) {
                nyDefault = param.getValor();
            }
            param = resourceEJB.queryParametros(Constant.SERVICE_CALL_BOOKING_SEQ);
            if (param != null) {
                bookingSeq = param.getValor();
            }
            param = resourceEJB.queryParametros(Constant.SERVICE_CALL_REQUEST_TIME);
            if (param != null) {
                requestServiceTime = param.getValor();
            }
            //Cargue Parametros configuracion Agenda Llamada de servicio
            param = resourceEJB.queryParametros(Constant.AGENDA_SERVICE_CALL_IDPROGRAMACION);
            if (param != null) {
                idProgramacionAgenda = param.getValor();
            }
            param = resourceEJB.queryParametros(Constant.AGENDA_SERVICE_CALL_IDTIPOTRABAJO);
            if (param != null) {
                idTipoTrabajoAgenda = param.getValor();
            }
            param = resourceEJB.queryParametros(Constant.SERVICE_CALL_TRUCK_REQUIRED_FLAG);
            if (param != null) {
                truckRequiredFlag = param.getValor();
            }

            cmtTipoBasicaMgl = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                    co.com.claro.mgl.utils.Constant.TIPO_BASICA_ESTADOS_HHPP);

            estadoHhppExiste = cmtBasicaMglManager.findByCodigoInternoApp(co.com.claro.mgl.utils.Constant.BASICA_EST_HHPP_TIPO_POTENCIAL);

        } catch (ApplicationException ex) {
            LOGGER.error("Error al momento de obtener el HHPP de RR. EX000: " + ex.getMessage(), ex);
        }
    }

    private String formatearCuadranteRR(String cuadViaPrincipal, String cuadViaGeneradora) {
        String cuadranteRR = "";
        if (cuadViaPrincipal == null || cuadViaPrincipal.trim().isEmpty()) {
            if (cuadViaGeneradora != null && !cuadViaGeneradora.trim().isEmpty()) {
                cuadranteRR = cuadViaGeneradora;
            }
        } else if (cuadViaGeneradora == null || cuadViaGeneradora.trim().isEmpty()) {
            if (!cuadViaPrincipal.trim().isEmpty()) {
                cuadranteRR = cuadViaPrincipal;
            }
        } else if (cuadViaPrincipal.equals(cuadViaGeneradora)) {
            cuadranteRR = cuadViaPrincipal.concat(cuadViaGeneradora);
        } else if (cuadViaPrincipal.equals(ESTE) || cuadViaPrincipal.equals(OESTE)) {
            cuadranteRR = (cuadViaPrincipal.concat(cuadViaGeneradora));
        } else if (cuadViaGeneradora.equals(ESTE) || cuadViaGeneradora.equals(OESTE)) {
            cuadranteRR = (cuadViaGeneradora.concat(cuadViaPrincipal));
        }
        return cuadranteRR;
    }

    private String ordenarDireccionCalleCarreraIT(DetalleDireccionEntity direccion) throws ApplicationException {
        StringBuilder strDireccionIT = new StringBuilder();
        if (direccion.getMztiponivel1() != null && !direccion.getMztiponivel1().trim().isEmpty()
                && !direccion.getMztiponivel1().equals(Constantes.VACIO) && !direccion.getMztiponivel1().equals(Constantes.V_VACIO)
                && direccion.getMzvalornivel1() != null && !direccion.getMzvalornivel1().trim().isEmpty()) {

            String tipoNilvel1IT = formatoRR(direccion.getMztiponivel1());
            String strNivel1IT = "";
            if (tipoNilvel1IT.equalsIgnoreCase(BARRIO)) {
                String strNivel1ITBarrio = direccion.getMzvalornivel1();
                String[] palabras = strNivel1ITBarrio.split(ESPACIO);
                if (palabras.length == 1) {
                    strNivel1IT = BARRIO + ESPACIO + strNivel1ITBarrio;
                } else if (palabras.length == 2) {
                    strNivel1IT = palabras[0] + ESPACIO + palabras[1];
                } else if (palabras.length > 2) {
                    strNivel1IT = palabras[0] + ESPACIO;
                    for (int i = 1; i < palabras.length; i++) {
                        strNivel1IT += palabras[i];
                    }
                }
                strDireccionIT.append(strNivel1IT);
            } else {
                strDireccionIT.append(formatoRR(direccion.getMztiponivel1()));
                strDireccionIT.append(ESPACIO);
                strDireccionIT.append(direccion.getMzvalornivel1().replace(" ", ""));
            }
        }

        if (direccion.getMztiponivel2() != null && !direccion.getMztiponivel2().trim().isEmpty()
                && !direccion.getMztiponivel2().equals(Constantes.VACIO) && !direccion.getMztiponivel2().equals(Constantes.V_VACIO)
                && direccion.getMzvalornivel2() != null && !direccion.getMzvalornivel2().trim().isEmpty()) {
            strDireccionIT.append(formatoRR(direccion.getMztiponivel2()));
            strDireccionIT.append(direccion.getMzvalornivel2().replace(" ", ""));
        }
        String temp = "";

        if (direccion.getMztiponivel3() != null && !direccion.getMztiponivel3().trim().isEmpty()
                && !direccion.getMztiponivel3().equals(Constantes.VACIO) && !direccion.getMztiponivel3().equals(Constantes.V_VACIO)
                && direccion.getMzvalornivel3() != null && !direccion.getMzvalornivel3().trim().isEmpty()) {
            temp += formatoRR(direccion.getMztiponivel3()) + direccion.getMzvalornivel3().replace(" ", "");

        }
        if (direccion.getMztiponivel4() != null && !direccion.getMztiponivel4().trim().isEmpty()
                && !direccion.getMztiponivel4().equals(Constantes.VACIO) && !direccion.getMztiponivel4().equals(Constantes.V_VACIO)
                && direccion.getMzvalornivel4() != null && !direccion.getMzvalornivel4().trim().isEmpty()) {
            temp += formatoRR(direccion.getMztiponivel4()) + direccion.getMzvalornivel4().replace(" ", "");

        }
        if (direccion.getMztiponivel5() != null && !direccion.getMztiponivel5().trim().isEmpty()
                && !direccion.getMztiponivel5().equals(Constantes.VACIO) && !direccion.getMztiponivel5().equals(Constantes.V_VACIO)
                && direccion.getMzvalornivel5() != null && !direccion.getMzvalornivel5().trim().isEmpty()) {
            temp += formatoRR(direccion.getMztiponivel5()) + direccion.getMzvalornivel5().replace(" ", "");

        }
        if (direccion.getMztiponivel6() != null && !direccion.getMztiponivel6().trim().isEmpty()
                && !direccion.getMztiponivel6().equals(Constantes.VACIO) && !direccion.getMztiponivel6().equals(Constantes.V_VACIO)
                && direccion.getMzvalornivel6() != null && !direccion.getMzvalornivel6().trim().isEmpty()) {
            temp += formatoRR(direccion.getMztiponivel6()) + direccion.getMzvalornivel6().replace(" ", "");

        }
        if (!temp.trim().isEmpty()) {
            strDireccionIT.append(ESPACIO);
            strDireccionIT.append(temp);
        }
        fiel1F18 = strDireccionIT.toString();
        String strSubEd = generarsubEdificioIT(direccion);
        if (strSubEd != null && !strSubEd.trim().isEmpty()) {
            strDireccionIT.append(ESPACIO);
            strDireccionIT.append(generarsubEdificioIT(direccion));
        }
        return strDireccionIT.toString();
    }

    private String ordenarNumUnidadCalleCarreraIT(DetalleDireccionEntity direccion) throws ApplicationException {
        StringBuilder str = new StringBuilder();
        String strTipo;

        if (direccion.getItTipoPlaca() != null && !direccion.getItTipoPlaca().isEmpty()
                && !direccion.getItTipoPlaca().equals(Constantes.VACIO) && !direccion.getItTipoPlaca().equals(Constantes.V_VACIO)
                && direccion.getItValorPlaca() != null && !direccion.getItValorPlaca().trim().isEmpty()) {
            strTipo = direccion.getItTipoPlaca();
            if (strTipo.equalsIgnoreCase("VP-PLACA")
                    || strTipo.equalsIgnoreCase("PLACA")
                    || strTipo.equalsIgnoreCase("MANZANA-CASA")) {
                str.append(direccion.getItValorPlaca());
            } else {
                str.append(formatoRR(direccion.getItTipoPlaca()));
                str.append(SEPARADOR_DIRECCION);
                str.append(direccion.getItValorPlaca());
            }
        } else {
            str.append("0-00");
        }
        fiel1F18 += " " + str.toString().trim();
        return str.toString().trim();
    }

    private void obtenerModificadores(StringBuilder strDireccion, String letra) {
        boolean requiereLinea = requiereLinea(letra);
        if (requiereLinea) {
            strDireccion.append(SEPARADOR_DIRECCION);
        }
    }

    private boolean requiereLinea(String letra) {
        boolean result = false;
        try {
            if (letra != null && !letra.isEmpty()) {
                Integer.parseInt(letra);
                result = true;
            }
        } catch (NumberFormatException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            result = false;
        }
        return result;
    }

    private void obtenerModificadorLetraDos(StringBuilder strDireccion, String letra, String letra2) {
        boolean isNumberLetra1 = requiereLineaLetraDos(letra);
        if (isNumberLetra1) {
            Pattern pat = Pattern.compile("[0-9]{1,}");
            Matcher mat = pat.matcher(letra2);
            if (mat.matches()) {
                strDireccion.append(SEPARADOR_DIRECCION);
            }
        }
    }

    private boolean requiereLineaLetraDos(String letra) {
        boolean result;
        try {
            Pattern pat = Pattern.compile("[a-zA-Z]{1}");
            Matcher mat = pat.matcher(letra);
            result = !mat.matches();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            result = false;
        }
        return result;
    }

    public String traerHeadEnd(String comunidad) throws ApplicationException {
        String resultHeadEnd = "01";
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject res = adb.outDataObjec("HEADEND", comunidad);
            DireccionUtil.closeConnection(adb);
            if (res != null) {
                resultHeadEnd = res.getString("HEAD_END").trim();
            }
            return resultHeadEnd;
        } catch (ExceptionDB e) {
            DireccionUtil.closeConnection(adb);
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de obtener el Head End. EX000: " + e.getMessage(), e);
        }
    }

    public String formatoRR(String valorCatastro) throws ApplicationException {
        String resutlFormatoRR = valorCatastro;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            LOGGER.info(valorCatastro);
            DataObject obj = adb.outDataObjec("formato1", valorCatastro);
            DireccionUtil.closeConnection(adb);
            if (obj != null) {
                resutlFormatoRR = obj.getString(Constantes.VALOR_RR);
            }
            return resutlFormatoRR;
        } catch (ExceptionDB e) {
            DireccionUtil.closeConnection(adb);
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de obtener el formato de RR. EX000: " + e.getMessage(), e);
        }
    }

    public boolean validaPresicion() {
        int comparador = 95;
        int confiabilidad = detalleDireccion.getDirConfiabilidad().intValue();
        return (confiabilidad >= comparador);
    }

    public boolean validarGeoreferencia() {
        String latitud = detalleDireccion.getUbicacion().getUbiLatitud();
        String longitud = detalleDireccion.getUbicacion().getUbiLongitud();
        return (latitud.equals("00") && longitud.equals("00"));
    }

    @Deprecated
    public DireccionRREntity cambiarDireccionHHPPRR(String comunidad, String division, String codDane,
            boolean validarConfiabilidad, String solicitud,
            String houseNumber, String streetName, String apartmentNumber,
            String usuario, String tipoSol, String campoUno,
            String tipoDir) throws ApplicationException {
        if (validarConfiabilidad) {
            if (!validaPresicion()) {
                return null;
            }
        }

        return cambiarDirHHPPRR(comunidad, division,
                houseNumber, streetName, apartmentNumber,
                comunidad, division,
                direccionRREntity.getNumeroUnidad(),
                direccionRREntity.getCalle(),
                direccionRREntity.getNumeroApartamento(),
                solicitud, usuario, tipoSol, campoUno, tipoDir);
    }

    public DireccionRREntity cambiarDirHHPPRR(String comunidadOld, String divisionOld,
            String houseNumberOld, String streetNameOld, String apartmentNumberOld,
            String comunidadNew, String divisionNew,
            String houseNumberNew, String streetNameNew, String apartmentNumberNew,
            String solicitud, String usuario, String tipoSol, String campoUno,
            String tipoDir) throws ApplicationException {

        DireccionRREntity entityRR = new DireccionRREntity();
        String nota = "SE CAMBIO LA DIRECCION RR DE: " + streetNameOld + " " + houseNumberOld + " " + apartmentNumberOld
                + " A: " + streetNameNew + " " + houseNumberNew + " " + apartmentNumberNew
                + " SOLICITUD: " + solicitud
                + " SOLICITANTE: " + usuario
                + " CANAL: " + tipoSol;
        CityEntity cityEntity = negocioMultivalor.consultaRRCiudadByCodCidadCodReg(comunidadNew, divisionNew);
        String postalCode = "000";
        if (cityEntity != null) {
            postalCode = cityEntity.getPostalCode();
        }

        //creamos la calle de la nueva direccion
        if (crearStreetHHPPRR(comunidadNew, divisionNew, streetNameNew)) {
            //Creamos el cruce de la nueva direccion
            if (crearCruceRR(comunidadNew, divisionNew,
                    streetNameNew, houseNumberNew,
                    tipoDir)) {
                //Cambiamos la direccion del HHPP
                if (cambiarHHPPRR(comunidadOld, divisionOld,
                        houseNumberOld, streetNameOld, apartmentNumberOld,
                        comunidadNew, divisionNew,
                        houseNumberNew, streetNameNew, apartmentNumberNew,
                        postalCode)) {
                    if (agregarNotasF8(comunidadNew, divisionNew, nota,
                            streetNameNew,
                            houseNumberNew,
                            apartmentNumberNew,
                            solicitud, usuario)) {

                        if (campoUno != null && !campoUno.trim().isEmpty()) {
                            cambiarCampo1NotasAdicionales(comunidadNew, divisionNew,
                                    streetNameNew, houseNumberNew, apartmentNumberNew, campoUno);
                        }
                    }
                }
            }
        }
        return entityRR;
    }

    /**
     * Función que obtiene cambia una dirección por otra en RR con ajustes de
     * proyecto Inspira (aplica para antigua a nueva)
     *
     * @author Juan David Hernandez
     * @return
     */
    public DireccionRREntity cambiarDirHHPPRR_Inspira(String comunidadOld, String divisionOld,
            String houseNumberOld, String streetNameOld, String apartmentNumberOld,
            String comunidadNew, String divisionNew,
            String houseNumberNew, String streetNameNew, String apartmentNumberNew,
            String solicitud, String usuario, String tipoSol, String campoUno,
            String tipoDir, CmtComunidadRr comunidadRegionalRr) throws ApplicationException {

        DireccionRREntity entityRR = new DireccionRREntity();
        String nota = "SE CAMBIO LA DIRECCION RR DE: " + streetNameOld + " " + houseNumberOld + " " + apartmentNumberOld
                + " A: " + streetNameNew + " " + houseNumberNew + " " + apartmentNumberNew
                + " SOLICITUD: " + solicitud
                + " SOLICITANTE: " + usuario
                + " CANAL: " + tipoSol;
        String postalCode = "000";
        if (comunidadRegionalRr != null) {
            postalCode = comunidadRegionalRr.getCodigoPostal();
        }

        //creamos la calle de la nueva direccion
        if (crearStreetHHPPRR(comunidadNew, divisionNew, streetNameNew)) {
            //Creamos el cruce de la nueva direccion
            if (crearCruceRR(comunidadNew, divisionNew,
                    streetNameNew, houseNumberNew,
                    tipoDir)) {
                //Cambiamos la direccion del HHPP
                if (cambiarHHPPRR(comunidadOld, divisionOld,
                        houseNumberOld, streetNameOld, apartmentNumberOld,
                        comunidadNew, divisionNew,
                        houseNumberNew, streetNameNew, apartmentNumberNew,
                        postalCode)) {
                    if (agregarNotasF8(comunidadNew, divisionNew, nota,
                            streetNameNew,
                            houseNumberNew,
                            apartmentNumberNew,
                            solicitud, usuario)) {
                        //verificamos si el parametro campoUno trae informacion para 
                        //realizar la actualizacion de este en la informacion adicinal del HHPP
                        if (campoUno != null && !campoUno.trim().isEmpty()) {
                            cambiarCampo1NotasAdicionales(comunidadNew, divisionNew,
                                    streetNameNew, houseNumberNew, apartmentNumberNew, campoUno);
                        }
                    }
                }
            }
        }
        return entityRR;
    }

    public DireccionRREntity cambiarDirHHPPRRCm(
            String comunidadOld, String divisionOld,
            String houseNumberOld, String streetNameOld, String apartmentNumberOld,
            String comunidadNew, String divisionNew,
            String houseNumberNew, String streetNameNew, String apartmentNumberNew,
            String solicitud, String usuario, String tipoSol, String campoUno,
            String tipoDir, String subDirIdNew) throws ApplicationException {

        DireccionRREntity entityRR = new DireccionRREntity();
        String nota = "SE CAMBIO LA DIRECCION RR DE: " + streetNameOld
                + " " + houseNumberOld + " " + apartmentNumberOld
                + " A: " + streetNameNew + " "
                + houseNumberNew + " " + apartmentNumberNew
                + " SOLICITUD: " + solicitud
                + " SOLICITANTE: " + usuario
                + " CANAL: " + tipoSol;

        CmtComunidadRrManager manager = new CmtComunidadRrManager();
        CmtComunidadRr comunidadRr = manager.findComunidadByCodigo(comunidadNew);

        String postalCode = "000";

        if (comunidadRr != null) {
            postalCode = comunidadRr.getCodigoPostal();
        }

        //creamos la calle de la nueva direccion
        if (crearStreetHHPPRR(comunidadNew, divisionNew, streetNameNew)) {
            //Creamos el cruce de la nueva direccion
            if (crearCruceRR(comunidadNew, divisionNew,
                    streetNameNew, houseNumberNew,
                    tipoDir)) {
                //Cambiamos la direccion del HHPP
                if (cambiarHHPPRR(comunidadOld, divisionOld,
                        houseNumberOld, streetNameOld, apartmentNumberOld,
                        comunidadNew, divisionNew,
                        houseNumberNew, streetNameNew, apartmentNumberNew,
                        postalCode)) {
                    if (agregarNotasF8(comunidadNew, divisionNew, nota,
                            streetNameNew,
                            houseNumberNew,
                            apartmentNumberNew,
                            solicitud, usuario)) {
                        //verificamos si el parametro campoUno trae informacion para 
                        //realizar la actualizacion de este en la informacion adicinal del HHPP
                        if (campoUno != null && !campoUno.trim().isEmpty()) {
                            cambiarCampo1NotasAdicionales(comunidadNew, divisionNew,
                                    streetNameNew, houseNumberNew, apartmentNumberNew, campoUno);
                        }
                    }
                }
            }
        }
        return entityRR;
    }

    public boolean cambiarEdificioHHPPRR(String solicitud, String comunidad, String division,
            String streetName, String houseNumber, String oldApto, String newApto,
            String usuario, String tipoSol) throws ApplicationException {
        boolean resutl = false;

        String nota = "SE CAMBIO LA DIRECCION RR DE: " + streetName + " " + houseNumber + " " + oldApto
                + " A: " + streetName + " " + houseNumber + " " + newApto
                + " SOLICITUD: " + solicitud
                + " SOLICITANTE: " + usuario
                + " CANAL: " + tipoSol;
        CityEntity cityEntity = negocioMultivalor.consultaRRCiudadByCodCidadCodReg(comunidad, division);
        String postalCode = cityEntity.getPostalCode();
        if (crearStreetHHPPRR(comunidad, division, streetName)) {

            if (cambiarHHPPRR(comunidad, division, houseNumber, streetName, oldApto,
                    comunidad, division, houseNumber, streetName, newApto, postalCode)) {

                if (agregarNotasF8(comunidad, division, nota, streetName, houseNumber, newApto, solicitud, usuario)) {
                    resutl = true;
                }
            }
        }
        return resutl;
    }
    
    /**
     * Función que cambiar una dirección de Apto con ajustes adaptados al
     * proyecto Inpira *
     *
     * @author Juan David Hernandez
     * @return
     */
    public boolean cambiarEdificioHHPPRR_Inspira(String solicitud, String comunidad, String division,
            String streetName, String houseNumber, String oldApto, String newApto,
            String usuario, String tipoSol, CmtComunidadRr comunidadRegionalRr) throws ApplicationException {
        boolean resutl = false;

        String nota = "SE CAMBIO LA DIRECCION RR DE: " + streetName + " " + houseNumber + " " + oldApto
                + " A: " + streetName + " " + houseNumber + " " + newApto
                + " SOLICITUD: " + solicitud
                + " SOLICITANTE: " + usuario
                + " CANAL: " + tipoSol;
        String postalCode = comunidadRegionalRr.getCodigoPostal();
        if (crearStreetHHPPRR(comunidad, division, streetName)) {

            if (cambiarHHPPRR(comunidad, division, houseNumber, streetName, oldApto,
                    comunidad, division, houseNumber, streetName, newApto, postalCode)) {

                if (agregarNotasF8(comunidad, division, nota, streetName, houseNumber, newApto, solicitud, usuario)) {
                    resutl = true;
                }
            }
        }
        return resutl;
    }

    /**
     * Función que cambiar una dirección de Apto con ajustes adaptados al
     * proyecto Inpira sin dejar Notas*
     *
     * @author Miguel Barrios
     * @param comunidad
     * @param division
     * @param streetName
     * @param houseNumber
     * @param oldApto
     * @param newApto
     * @param comunidadRegionalRr
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public boolean cambiarEdificioHhppRrInspira(String comunidad, String division,
            String streetName, String houseNumber, String oldApto, String newApto,
            CmtComunidadRr comunidadRegionalRr) throws ApplicationException {
        boolean resutl = false;

        String postalCode = comunidadRegionalRr.getCodigoPostal();
        
        if (crearStreetHHPPRR(comunidad, division, streetName)
            &&  cambiarHHPPRR(comunidad, division, houseNumber,
                    streetName, oldApto, comunidad,
                    division, houseNumber, streetName,
                    newApto, postalCode)) {
            resutl = true;
        }
        
        return resutl;
    }

    public List<ChangeUnitAddressRequest> cambiarDirHHPPRRMasivo(List<ChangeUnitAddressRequest> changeUnitAddressRequestList) throws ApplicationException {

        Map<String, List<ChangeUnitAddressRequest>> resultProcess = new TreeMap<String, List<ChangeUnitAddressRequest>>();
        List<ChangeUnitAddressRequest> noProcesadosList = new ArrayList<ChangeUnitAddressRequest>();
        List<ChangeUnitAddressRequest> procesadosList = new ArrayList<ChangeUnitAddressRequest>();
        for (ChangeUnitAddressRequest changeUnitAddressRequest : changeUnitAddressRequestList) {
            //creamos la calle de la nueva direccion
            if (crearStreetHHPPRR(changeUnitAddressRequest.getNewCommunity(), changeUnitAddressRequest.getNewDivision(), changeUnitAddressRequest.getNewStreetName())) {
                //Cambiamos la direccion del HHPP
                boolean hhppActualizado;
                try {
                    hhppActualizado = cambiarHHPPRR(changeUnitAddressRequest);
                } catch (Exception e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                    LOGGER.error(msg);
                    hhppActualizado = false;
                }

                if (hhppActualizado) {
                    procesadosList.add(changeUnitAddressRequest);
                } else {
                    noProcesadosList.add(changeUnitAddressRequest);
                }
            }
        }
        resultProcess.put("PROCESADOS", procesadosList);
        resultProcess.put("NOPROCESADOS", noProcesadosList);
        //creamos la calle de la nueva direccion        
        return noProcesadosList;
    }

    private boolean cambiarHHPPRR(String comunidad, String division, String codPostal, String houseNumber, String streetName, String apartmentNumber) throws ApplicationException {
        ChangeUnitAddressRequest changeUnitAddressRequest = new ChangeUnitAddressRequest();
        changeUnitAddressRequest.setCommunity(comunidad);
        changeUnitAddressRequest.setNewCommunity(comunidad);
        changeUnitAddressRequest.setDivision(division);
        changeUnitAddressRequest.setNewDivision(division);
        changeUnitAddressRequest.setStreetName(streetName);
        changeUnitAddressRequest.setHouseNumber(houseNumber);
        changeUnitAddressRequest.setApartment(apartmentNumber);
        changeUnitAddressRequest.setNewStreetName(direccionRREntity.getCalle());
        changeUnitAddressRequest.setNewHouseNumber(direccionRREntity.getNumeroUnidad());
        changeUnitAddressRequest.setNewApartment(direccionRREntity.getNumeroApartamento());
        changeUnitAddressRequest.setNewPostalCode(codPostal);
        PortManager portManager = new PortManager(wsURL, wsService);
        ChangeUnitAddressResponse responseMessageUnit;

        try {
            responseMessageUnit = portManager.changeAddress(changeUnitAddressRequest);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            responseMessageUnit = portManager.changeAddress(changeUnitAddressRequest);
        }

        if (responseMessageUnit.getResponse().getMessageText().trim().toUpperCase().contains("(CAB0226)")) {
            return true;
        } else if (getInfoHHPP(comunidad, division, direccionRREntity)) {
            return true;
        } else {
            throw new ApplicationException("No se pudo actualizar el HHPP de :"
                    + streetName + " " + houseNumber + " " + apartmentNumber
                    + "  A: " + direccionRREntity.getCalle() + " " + direccionRREntity.getNumeroUnidad() + " "
                    + direccionRREntity.getNumeroApartamento() + ", error RR :" + responseMessageUnit.getResponse().getMessageText());
        }
    }

    public boolean cambiarHHPPRR(String comunidadOld, String divisionOld,
            String houseNumberOld, String streetNameOld, String apartmentNumberOld,
            String comunidadNew, String divisionNew,
            String houseNumberNew, String streetNameNew, String apartmentNumberNew,
            String postalCodeNew) throws ApplicationException {
        try {
            ChangeUnitAddressRequest changeUnitAddressRequest = new ChangeUnitAddressRequest();
            changeUnitAddressRequest.setCommunity(comunidadOld);
            changeUnitAddressRequest.setDivision(divisionOld);
            changeUnitAddressRequest.setStreetName(streetNameOld);
            changeUnitAddressRequest.setHouseNumber(houseNumberOld);
            changeUnitAddressRequest.setApartment(apartmentNumberOld);

            changeUnitAddressRequest.setNewCommunity(comunidadNew);
            changeUnitAddressRequest.setNewDivision(divisionNew);
            changeUnitAddressRequest.setNewStreetName(streetNameNew);
            changeUnitAddressRequest.setNewHouseNumber(houseNumberNew);
            changeUnitAddressRequest.setNewApartment(apartmentNumberNew);

            changeUnitAddressRequest.setNewPostalCode(postalCodeNew);

            PortManager portManager = new PortManager(wsURL, wsService);
            ChangeUnitAddressResponse responseMessageUnit;

            try {
                responseMessageUnit = portManager.changeAddress(changeUnitAddressRequest);
            } catch (Exception e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg);
                responseMessageUnit = portManager.changeAddress(changeUnitAddressRequest);
            }

            if (responseMessageUnit.getResponse() != null
                    && responseMessageUnit.getResponse().getMessageText() != null
                    && responseMessageUnit.getResponse().getMessageText().trim().toUpperCase().contains("(CAB0226)")) {
                return true;
            } else {
                return getInfoHHPP(comunidadNew, divisionNew, streetNameNew, houseNumberNew, apartmentNumberNew);

            }
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de cambiar el HHPP de RR. EX000: " + e.getMessage(), e);
        }
    }

    private boolean cambiarHHPPRR(ChangeUnitAddressRequest changeUnitAddressRequest) throws ApplicationException {
        try {
            PortManager portManager = new PortManager(wsURL, wsService);
            ChangeUnitAddressResponse responseMessageUnit;

            try {
                responseMessageUnit = portManager.changeAddress(changeUnitAddressRequest);
            } catch (Exception e) {
                LOGGER.error("Error al intentar Cambiar el HHPP, se consume nuevamente el servicio " + e.getMessage());
                responseMessageUnit = portManager.changeAddress(changeUnitAddressRequest);
            }

            if (responseMessageUnit.getResponse() != null
                    && responseMessageUnit.getResponse().getMessageText() != null
                    && responseMessageUnit.getResponse().getMessageText().trim().toUpperCase().contains("(CAB0226)")) {
                return true;
            } else {
                throw new ApplicationException("No se pudo actualizar el HHPP de :"
                        + changeUnitAddressRequest.getStreetName() + " "
                        + changeUnitAddressRequest.getHouseNumber() + " " + changeUnitAddressRequest.getApartment()
                        + "  A: " + changeUnitAddressRequest.getNewStreetName() + " " + changeUnitAddressRequest.getNewHouseNumber() + " "
                        + changeUnitAddressRequest.getNewApartment() + ", error RR :" + responseMessageUnit.getResponse().getMessageText());
            }
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de cambiar el HHPP de RR. EX000: " + e.getMessage(), e);
        }
    }

    public boolean crearHHPPFicha(PreFichaXlsMgl fichaXlsMgl, Nodo nodo) throws ApplicationException {
        try {
            boolean result = false;
            //buscamos el nodo el la tabla RR_NODOS para obtener la comunidad y la division
            NodoRR nodoRR = nodoManager.queryNodoRR(nodo.getNodCodigo());
            if (nodoRR == null) {
                throw new ApplicationException("El Nodo " + nodo.getNodCodigo() + " no existe");
            }
            String comunidad = nodoRR.getCodCiudad();
            String division = nodoRR.getCodRegional();
            EstadoHhpp estado = obtenerEstadoHHPP(nodo.getNodCodigo(), VERIFICACION_CASAS);
            return result;
        } catch (ExceptionDB ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de crear el HHPP de la Ficha. EX000: " + ex.getMessage(), ex);
        }
    }

    public DireccionRREntity registrarHHPP(String strNodo, String nodoReal, String usuario,
            String carpeta, String nombreFuncionalidad, String nvlSocioEconomico,
            boolean validarConfiabilidad, String solicitud, String tipoSol,
            String codCiudadRr, String observaciones,
            String usuarioSol, String razon, String idUsuario,
            String contacto, String telContacto, String campoUno,
            BigDecimal idCentroPobladoGpo, CmtHhppVtMgl tipoVivienda) throws ApplicationException {

        NodoMglManager nodoMglManager = new NodoMglManager();
        CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
        try {

            DireccionRREntity entityRR = new DireccionRREntity();
            entityRR.setCalle(direccionRREntity.getCalle().toUpperCase());
            entityRR.setNumeroUnidad(direccionRREntity.getNumeroUnidad().toUpperCase());
            entityRR.setNumeroApartamento(direccionRREntity.getNumeroApartamento().toUpperCase());
            entityRR.setResptRegistroHHPPRR(false);
            CmtComunidadRrManager cmtComunidadRrManager = new CmtComunidadRrManager();

            if (validarConfiabilidad) {
                if (!validaPresicion()) {
                    entityRR.setResptRegistroHHPPRR(false);
                    return entityRR;
                }
            }
            Hhpp hhpp = new Hhpp();
            hhpp.setDireccion(detalleDireccion);

            hhpp.setSubDireccion(detalleSubDireccion);
            EstadoHhpp estado = obtenerEstadoHHPP(strNodo, carpeta);
            hhpp.setEstadoHhpp(estado);
            //Ajuste para creacion de HHPP de CM-Constructora.
            if (direccionRREntity.getNumeroApartamento().equalsIgnoreCase("CAMPAMENTO")
                    || direccionRREntity.getNumeroApartamento().equalsIgnoreCase("SALAVENTAS")) {
                String exist = cmtBasicaMglManager.findValorExtendidoEstHhpp(estadoHhppExiste);
                estado = hhppManager.queryEstadoHhpp(exist);//EXISTS
                hhpp.setEstadoHhpp(estado);
            }

            Nodo nodo = null;
            if (idCentroPobladoGpo != null) {
                nodo = nodoManager.queryNodo(strNodo, idCentroPobladoGpo);//VILLAMILC
            } else {
                nodo = nodoManager.queryNodo(strNodo, detalleDireccion.getUbicacion().getGpoId().getGpoId());//VILLAMILC
            }

            NodoMgl nodoMgl = null;
            if (idCentroPobladoGpo != null) {
                nodoMgl = nodoMglManager.findByCodigoAndCity(strNodo, idCentroPobladoGpo);
            } else {
                nodoMgl = nodoMglManager.findByCodigoAndCity(strNodo, detalleDireccion.getUbicacion().getGpoId().getGpoId());
            }
            String comunidad = "000";
            String division = "000";

            //JDHT
            if (nodoMgl != null && nodoMgl.getNodId() != null) {
                if (nodoMgl.getComId() != null && nodoMgl.getComId().getCodigoRr() != null
                        && nodoMgl.getComId().getRegionalRr() != null) {
                    comunidad = nodoMgl.getComId().getCodigoRr();
                    division = nodoMgl.getComId().getRegionalRr().getCodigoRr();
                    entityRR.setComunidad(comunidad);
                    entityRR.setDivision(division);
                }
            }

            if (nodo != null) {
                hhpp.setNodo(nodo);
            }
            //@cardenaslb
            // se coloca el valor del subedificio si se selecciono de la vista
            String tipoEdificioHHPP = "";
            if (tipoVivienda != null) {
                if (tipoVivienda.getThhVtId() != null) {
                    tipoEdificioHHPP = tipoVivienda.getThhVtId();
                }
            } else {
                tipoEdificioHHPP = obtenerTipoEdificio(direccionRREntity.getNumeroApartamento(), carpeta, tipoSol);
            }
            TipoHhpp tipoHhpp = new TipoHhpp(tipoEdificioHHPP);
            hhpp.setTipoHhpp(tipoHhpp);
            TipoHhppConexion tipoHhppConexion = new TipoHhppConexion();
            TipoHhppRed tipoHhppRed = new TipoHhppRed();
            if (carpeta.equals(UNIDIRECCIONAL)) {
                tipoHhppConexion.setThcId(new BigDecimal("2"));//Conexion Temporal
                tipoHhppRed.setThrId(BigDecimal.ONE);
            } else if (carpeta.equals(VERIFICACION_CASAS)) { //VERIFICACION_CASAS
                tipoHhppConexion.setThcId(BigDecimal.ONE);//Conexion Temporal
                tipoHhppRed.setThrId(new BigDecimal("2"));
            } else if (carpeta.equals(CUENTA_MATRIZ)) { //VERIFICACION_CASAS
                tipoHhppConexion.setThcId(new BigDecimal("2"));//Conexion Temporal
                tipoHhppRed.setThrId(BigDecimal.ONE);
            }

            hhpp.setTipoConexionHhpp(tipoHhppConexion);
            hhpp.setTipoRedHhpp(tipoHhppRed);
            hhpp.setMarcas(null);
            String nota = "";

            if (carpeta.equalsIgnoreCase(UNIDIRECCIONAL) || carpeta.equalsIgnoreCase(VERIFICACION_CASAS)
                    || carpeta.equalsIgnoreCase(CUENTA_MATRIZ)) {
                nota = "NODO REAL: " + nodoReal + "; SOLICITUD: " + solicitud
                        + "; SOLICITANTE: " + usuarioSol + "; CANAL: " + tipoSol
                        + "; OBSERVACION: " + observaciones;
            }

            //@author Juan David Hernandez
            boolean habilitarRR = false;
            ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
            ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(co.com.claro.mgl.utils.Constant.HABILITAR_RR);
            if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
                habilitarRR = true;
            }

            UnitManagerResponse strIdHHPPRR = new UnitManagerResponse();
            boolean tecnologiaSincronizaRr = false;
            if (habilitarRR) {
                if (nodoMgl != null && nodoMgl.getNodId() != null && nodoMgl.getComId() != null
                        && nodoMgl.getComId().getCodigoRr() != null
                        && !nodoMgl.getComId().getRegionalRr().getCodigoRr().isEmpty()
                        && nodoMgl.getComId().getCodigoRr() != null
                        && !nodoMgl.getComId().getRegionalRr().getCodigoRr().isEmpty()) {

                    //Validación para saber si sincroniza con RR la tecnologia del Hhpp
                    if (nodoMgl.getNodTipo().getListCmtBasicaExtMgl() != null
                            && !nodoMgl.getNodTipo().getListCmtBasicaExtMgl().isEmpty()) {

                        for (CmtBasicaExtMgl cmtBasicaExtMgl
                                : nodoMgl.getNodTipo().getListCmtBasicaExtMgl()) {

                            if (cmtBasicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().
                                    equalsIgnoreCase(co.com.claro.mgl.utils.Constant.SINCRONIZA_RR)
                                    && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")) {
                                tecnologiaSincronizaRr = true;

                                if (crearStreetHHPPRR(comunidad, division, direccionRREntity.getCalle().toUpperCase())) {

                                    if (crearCruceRR(comunidad, division, direccionRREntity.getCalle(),
                                            direccionRREntity.getNumeroUnidad(), direccionEntity.getIdtipodireccion())) {

                                        strIdHHPPRR = registrarHHPPRR(hhpp, nvlSocioEconomico, carpeta, comunidad,
                                                division, tipoSol, direccionRREntity.getNumeroApartamento(), direccionRREntity.getCalle(),
                                                direccionRREntity.getNumeroUnidad(), razon,null);

                                        /*Si el hhpp NO fue creado correctamente en RR (CAB0075) y no se encuentra repetido (CAB0420)
                                     es un error de otro tipo*/
                                        if ((strIdHHPPRR == null || strIdHHPPRR.getResponseAddUnit() == null) || (!strIdHHPPRR.getResponseAddUnit().getMessageText().contains("CAB0075")
                                                && !strIdHHPPRR.getResponseAddUnit().getMessageText().contains("CAB0420"))) {
                                            entityRR.setMensaje(strIdHHPPRR.getResponseAddUnit().getMessageText());
//                                            throw new ApplicationException("El HHPP de NODO " + nodoMgl.getNodCodigo()
//                                                    + " y con comunidad " + comunidad + " en RR no se pudo crear debido a: "
//                                                    + strIdHHPPRR.getResponseAddUnit().getMessageText());
                                        }

                                        //Si el hhpp fue creado correctamente en RR (CAB0075) y no se encuentra repetido (CAB0420)
                                        if (strIdHHPPRR != null && strIdHHPPRR.getResponseAddUnit().getAkyn() != null
                                                && !strIdHHPPRR.getResponseAddUnit().getAkyn().isEmpty()
                                                && strIdHHPPRR.getResponseAddUnit().getMessageText().contains("CAB0075")
                                                && !strIdHHPPRR.getResponseAddUnit().getMessageText().contains("CAB0420")) {

                                            agregarNotasF8(comunidad, division, nota,
                                                    direccionRREntity.getCalle(),
                                                    direccionRREntity.getNumeroUnidad(),
                                                    direccionRREntity.getNumeroApartamento(),
                                                    solicitud, usuario);

                                            agregarNotaF18_Inspira(comunidad, division,
                                                    direccionRREntity, detalleDireccion,
                                                    campoUno, nodoReal);
                                        }

                                    }
                                }
                            }
                        }
                    }
                } else {
                    throw new ApplicationException("RR se encuentra encendido y el Nodo "
                            + "del hhpp no cuenta con la comunidad RR asociada para "
                            + "crear el Hhpp en RR para la tecnologia o el nodo es nulo");
                }
            }

            /*author Juan David Hernandez*/
            //Ajuste de quitar dependencia de RR al crear Hhpp
            // mayor a nueve cuando la creacion del hhpp no es exitosa 
            if (habilitarRR && tecnologiaSincronizaRr && strIdHHPPRR != null && strIdHHPPRR.getResponseAddUnit().getAkyn() != null
                    && !strIdHHPPRR.getResponseAddUnit().getAkyn().isEmpty()
                    && strIdHHPPRR.getResponseAddUnit().getMessageText().contains("CAB0075")
                    && !strIdHHPPRR.getResponseAddUnit().getMessageText().contains("CAB0420")) {
                hhpp.setIdRR(strIdHHPPRR.getResponseAddUnit().getAkyn());
                hhpp.setCalleRR(direccionRREntity.getCalle().toUpperCase());
                hhpp.setUnidadRR(direccionRREntity.getNumeroUnidad().toUpperCase());
                hhpp.setAptoRR(direccionRREntity.getNumeroApartamento().toUpperCase());
                hhpp.setComunidadRR(comunidad);
                hhpp.setDivisionRR(division);
                //guarda hhpp en MGL
                entityRR.setIdHhpp((hhppManager.saveHhppInRepo(hhpp, nombreFuncionalidad)).toString());
                entityRR.setCalle(direccionRREntity.getCalle().toUpperCase());
                entityRR.setNumeroUnidad(direccionRREntity.getNumeroUnidad().toUpperCase());
                entityRR.setNumeroApartamento(direccionRREntity.getNumeroApartamento().toUpperCase());
                direccionRREntity.setIdHhpp(entityRR.getIdHhpp());
            } else {
                //Si RR se encuentra encendido y se registro en RR el hhpp exitosamente
                if (habilitarRR && !tecnologiaSincronizaRr) {
                    hhpp.setCalleRR(direccionRREntity.getCalle().toUpperCase());
                    hhpp.setUnidadRR(direccionRREntity.getNumeroUnidad().toUpperCase());
                    hhpp.setAptoRR(direccionRREntity.getNumeroApartamento().toUpperCase());
                    hhpp.setComunidadRR(comunidad);
                    hhpp.setDivisionRR(division);
                    //guarda hhpp en MGL
                    entityRR.setIdHhpp((hhppManager.saveHhppInRepo(hhpp, nombreFuncionalidad)).toString());
                    entityRR.setCalle(direccionRREntity.getCalle().toUpperCase());
                    entityRR.setNumeroUnidad(direccionRREntity.getNumeroUnidad().toUpperCase());
                    entityRR.setNumeroApartamento(direccionRREntity.getNumeroApartamento().toUpperCase());
                    direccionRREntity.setIdHhpp(entityRR.getIdHhpp());
                } else {
                    //Si RR se encuentra apagado, registrar el Hhpp solo en MGL  
                    if (!habilitarRR) {
                        hhpp.setCalleRR(direccionRREntity.getCalle().toUpperCase());
                        hhpp.setUnidadRR(direccionRREntity.getNumeroUnidad().toUpperCase());
                        hhpp.setAptoRR(direccionRREntity.getNumeroApartamento().toUpperCase());
                        //guarda hhpp en MGL
                        entityRR.setIdHhpp((hhppManager.saveHhppInRepo(hhpp, nombreFuncionalidad)).toString());
                        entityRR.setCalle(direccionRREntity.getCalle().toUpperCase());
                        entityRR.setNumeroUnidad(direccionRREntity.getNumeroUnidad().toUpperCase());
                        entityRR.setNumeroApartamento(direccionRREntity.getNumeroApartamento().toUpperCase());
                        direccionRREntity.setIdHhpp(entityRR.getIdHhpp());
                    } else {
                        if (strIdHHPPRR != null && strIdHHPPRR.getResponseAddUnit().getAkyn() != null) {
                            hhpp.setCalleRR(direccionRREntity.getCalle().toUpperCase());
                            hhpp.setUnidadRR(direccionRREntity.getNumeroUnidad().toUpperCase());
                            hhpp.setAptoRR(direccionRREntity.getNumeroApartamento().toUpperCase());
                            //guarda hhpp en MGL
                            entityRR.setIdHhpp((hhppManager.saveHhppInRepo(hhpp, nombreFuncionalidad)).toString());
                            entityRR.setCalle(direccionRREntity.getCalle().toUpperCase());
                            entityRR.setNumeroUnidad(direccionRREntity.getNumeroUnidad().toUpperCase());
                            entityRR.setNumeroApartamento(direccionRREntity.getNumeroApartamento().toUpperCase());
                            direccionRREntity.setIdHhpp(entityRR.getIdHhpp());
                        } else {
                            // cuando el hhpp ya existe pero con otra tecnologia 

                            hhpp.setCalleRR(direccionRREntity.getCalle().toUpperCase());
                            hhpp.setUnidadRR(direccionRREntity.getNumeroUnidad().toUpperCase());
                            hhpp.setAptoRR(direccionRREntity.getNumeroApartamento().toUpperCase());
                            hhpp.setComunidadRR(comunidad);
                            hhpp.setDivisionRR(division);
                            if (strIdHHPPRR.getResponseAddUnit().getMessageText().contains("CAB0420")) {
                                String idHHPP = (hhppManager.saveHhppInRepo(hhpp, nombreFuncionalidad)).toString();
                                entityRR.setIdHhpp(idHHPP);
                                direccionRREntity.setIdHhpp(idHHPP);
                            }

                        }

                    }
                }
            }
            return entityRR;

        } catch (ApplicationException | ExceptionDB ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de crear el HHPP de la Ficha. EX000: " + ex.getMessage(), ex);
        }

    }

    public DireccionRREntity registrarHHPP_Inspira(NodoMgl nodoMgl, String usuario,
            String carpeta, String nombreFuncionalidad, String nvlSocioEconomico,
            boolean validarConfiabilidad, String solicitud, String tipoSol,
            String codCiudadRr, String observaciones,
            String usuarioSol, String razon, String idUsuario,
            String contacto, String telContacto, String campoUno,
            BigDecimal idCentroPobladoGpo, boolean sincronizaRr, String tipoHhppGestion,
            List<MarcasMgl> marcasAgregadasSolicitudList, boolean habilitarRR,String nap, boolean flujoAutomatico) throws ApplicationException {

        NodoMglManager nodoMglManager = new NodoMglManager();
        CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
        HhppMglManager hhppMglManager = new HhppMglManager();
        try {

            DireccionRREntity entityRR = new DireccionRREntity();
            entityRR.setCalle(direccionRREntity.getCalle().toUpperCase());
            entityRR.setNumeroUnidad(direccionRREntity.getNumeroUnidad().toUpperCase());
            entityRR.setNumeroApartamento(direccionRREntity.getNumeroApartamento().toUpperCase());
            entityRR.setResptRegistroHHPPRR(false);
            CmtComunidadRrManager cmtComunidadRrManager = new CmtComunidadRrManager();

            if (validarConfiabilidad) {
                if (!validaPresicion()) {
                    entityRR.setResptRegistroHHPPRR(false);
                    return entityRR;
                }
            }

            Hhpp hhpp = new Hhpp();
            hhpp.setDireccion(detalleDireccion);

            hhpp.setSubDireccion(detalleSubDireccion);
            EstadoHhpp estado = obtenerEstadoHHPP(nodoMgl.getNodCodigo(), carpeta);
            hhpp.setEstadoHhpp(estado);
            //Ajuste para creacion de HHPP de CM-Constructora.
            if (direccionRREntity.getNumeroApartamento().equalsIgnoreCase("CAMPAMENTO")
                    || direccionRREntity.getNumeroApartamento().equalsIgnoreCase("SALAVENTAS")) {
                String exist = cmtBasicaMglManager.findValorExtendidoEstHhpp(estadoHhppExiste);
                estado = hhppManager.queryEstadoHhpp(exist);//EXISTS
                hhpp.setEstadoHhpp(estado);
            }

            Nodo nodo = null;
            if (idCentroPobladoGpo != null) {
                nodo = nodoManager.queryNodo(nodoMgl.getNodCodigo(), idCentroPobladoGpo);//VILLAMILC
            } else {
                nodo = nodoManager.queryNodo(nodoMgl.getNodCodigo(), detalleDireccion.getUbicacion().getGpoId().getGpoId());//VILLAMILC
            }
            if (nodo != null) {
                hhpp.setNodo(nodo);
            }

            String comunidad = "000";
            String division = "000";

            //JDHT
            if (nodoMgl != null && nodoMgl.getNodId() != null) {
                if (nodoMgl.getComId() != null && nodoMgl.getComId().getCodigoRr() != null && nodoMgl.getComId().getRegionalRr() != null) {
                    comunidad = nodoMgl.getComId().getCodigoRr();
                    division = nodoMgl.getComId().getRegionalRr().getCodigoRr();
                    entityRR.setComunidad(comunidad);
                    entityRR.setDivision(division);
                }
            }

            // cardenaslb
            // tipo de vivienda desde la gestion
            String tipoEdificioHHPP;
            if (tipoHhppGestion != null && !tipoHhppGestion.isEmpty()) {
                tipoEdificioHHPP = tipoHhppGestion;
            } else {
                tipoEdificioHHPP = obtenerTipoEdificio(direccionRREntity.getNumeroApartamento(), carpeta, tipoSol);
            }
            TipoHhpp tipoHhpp = new TipoHhpp(tipoEdificioHHPP);
            hhpp.setTipoHhpp(tipoHhpp);
            TipoHhppConexion tipoHhppConexion = new TipoHhppConexion();
            TipoHhppRed tipoHhppRed = new TipoHhppRed();
            switch (carpeta) {
                case UNIDIRECCIONAL:
                    tipoHhppConexion.setThcId(new BigDecimal("2"));//Conexion Temporal
                    tipoHhppRed.setThrId(BigDecimal.ONE);
                    break;
                case VERIFICACION_CASAS:
                    //VERIFICACION_CASAS
                    tipoHhppConexion.setThcId(BigDecimal.ONE);//Conexion Temporal
                    tipoHhppRed.setThrId(new BigDecimal("2"));
                    break;
                case CUENTA_MATRIZ:
                    //VERIFICACION_CASAS
                    tipoHhppConexion.setThcId(new BigDecimal("2"));//Conexion Temporal
                    tipoHhppRed.setThrId(BigDecimal.ONE);
                    break;
                default:
                    break;
            }

            hhpp.setTipoConexionHhpp(tipoHhppConexion);
            hhpp.setTipoRedHhpp(tipoHhppRed);
            hhpp.setMarcas(null);
            String nota = "";

            if (carpeta.equalsIgnoreCase(UNIDIRECCIONAL) || carpeta.equalsIgnoreCase(VERIFICACION_CASAS)
                    || carpeta.equalsIgnoreCase(CUENTA_MATRIZ)) {
                nota = "NODO REAL: " + nodoMgl.getNodCodigo() + "; SOLICITUD: " + solicitud
                        + "; SOLICITANTE: " + usuarioSol + "; CANAL: " + tipoSol
                        + "; OBSERVACION: " + observaciones;
            }
            
            if(flujoAutomatico){               
                String fechaHoraSol = DateToolUtils.formatoFechaLocalDateTime(LocalDateTime.now(),"yyyy-MM-dd HH:mm");
                nota = "NUMERO RADICADO: " + solicitud
                        + "; FECHA Y HORA: " + fechaHoraSol
                        + "; ESTADO: " + co.com.claro.mgl.utils.Constant.CREADO_AUTOMATICAMENTE;
            }
            
            UnitManagerResponse strIdHHPPRR = new UnitManagerResponse();
            boolean tecnologiaSincronizaRr = false;
            if (habilitarRR && sincronizaRr) {
                if (nodoMgl != null && nodoMgl.getNodId() != null && nodoMgl.getComId() != null
                        && nodoMgl.getComId().getCodigoRr() != null
                        && !nodoMgl.getComId().getRegionalRr().getCodigoRr().isEmpty()
                        && nodoMgl.getComId().getCodigoRr() != null
                        && !nodoMgl.getComId().getRegionalRr().getCodigoRr().isEmpty()) {

                    //Validación para saber si sincroniza con RR la tecnologia del Hhpp
                    if (nodoMgl.getNodTipo().getListCmtBasicaExtMgl() != null
                            && !nodoMgl.getNodTipo().getListCmtBasicaExtMgl().isEmpty()) {

                        for (CmtBasicaExtMgl cmtBasicaExtMgl
                                : nodoMgl.getNodTipo().getListCmtBasicaExtMgl()) {

                            if (cmtBasicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().
                                    equalsIgnoreCase(co.com.claro.mgl.utils.Constant.SINCRONIZA_RR)
                                    && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")) {
                                tecnologiaSincronizaRr = true;

                                if (crearStreetHHPPRR(comunidad, division, direccionRREntity.getCalle().toUpperCase())) {

                                    if (crearCruceRR(comunidad, division, direccionRREntity.getCalle(),
                                            direccionRREntity.getNumeroUnidad(), direccionEntity.getIdtipodireccion())) {

                                        strIdHHPPRR = registrarHHPPRR(hhpp, nvlSocioEconomico, carpeta, comunidad,
                                                division, tipoSol, direccionRREntity.getNumeroApartamento(), direccionRREntity.getCalle(),
                                                direccionRREntity.getNumeroUnidad(), razon, nap);

                                        /*Si el hhpp NO fue creado correctamente en RR (CAB0075) y no se encuentra repetido (CAB0420)
                                     es un error de otro tipo*/
                                        if ((strIdHHPPRR == null || strIdHHPPRR.getResponseAddUnit() == null) || (!strIdHHPPRR.getResponseAddUnit().getMessageText().contains("CAB0075")
                                                && !strIdHHPPRR.getResponseAddUnit().getMessageText().contains("CAB0420"))) {
                                            throw new ApplicationException("El HHPP de NODO " + nodoMgl.getNodCodigo()
                                                    + " y con comunidad " + comunidad + " en RR no se pudo crear debido a: "
                                                    + strIdHHPPRR.getResponseAddUnit().getMessageText());
                                        }

                                        //Si el hhpp fue creado correctamente en RR (CAB0075) y no se encuentra repetido (CAB0420)
                                        if (strIdHHPPRR != null && strIdHHPPRR.getResponseAddUnit().getAkyn() != null
                                                && !strIdHHPPRR.getResponseAddUnit().getAkyn().isEmpty()
                                                && strIdHHPPRR.getResponseAddUnit().getMessageText().contains("CAB0075")
                                                && !strIdHHPPRR.getResponseAddUnit().getMessageText().contains("CAB0420")) {

                                            agregarNotasF8(comunidad, division, nota,
                                                    direccionRREntity.getCalle(),
                                                    direccionRREntity.getNumeroUnidad(),
                                                    direccionRREntity.getNumeroApartamento(),
                                                    solicitud, usuario);

                                            agregarNotaF18_Inspira(comunidad, division,
                                                    direccionRREntity, detalleDireccion,
                                                    campoUno, nodoMgl.getNodCodigo());
                                        }

                                    }
                                }
                            }
                        }
                    }
                } else {
                    throw new ApplicationException("RR se encuentra encendido y el Nodo del hhpp no cuenta "
                            + "con la comunidad RR asociada para crear el Hhpp en RR"
                            + " para la tecnologia " + nodoMgl.getNodTipo() != null ? nodoMgl.getNodTipo().getNombreBasica() : "NA");
                }
            }

            /*author Juan David Hernandez*/
            //Ajuste de quitar dependencia de RR al crear Hhpp
            //Guarda en MGL despues de haber guardado en RR.
            //Si el hhpp fue creado correctamente en RR (CAB0075) y no se encuentra repetido (CAB0420)
            if (habilitarRR && strIdHHPPRR != null && strIdHHPPRR.getResponseAddUnit() != null
                    && strIdHHPPRR.getResponseAddUnit().getAkyn() != null
                    && !strIdHHPPRR.getResponseAddUnit().getAkyn().isEmpty()
                    && strIdHHPPRR.getResponseAddUnit().getMessageText().contains("CAB0075")
                    && !strIdHHPPRR.getResponseAddUnit().getMessageText().contains("CAB0420")) {
                hhpp.setIdRR(strIdHHPPRR.getResponseAddUnit().getAkyn());
                hhpp.setCalleRR(direccionRREntity.getCalle().toUpperCase());
                hhpp.setUnidadRR(direccionRREntity.getNumeroUnidad().toUpperCase());
                hhpp.setAptoRR(direccionRREntity.getNumeroApartamento().toUpperCase());
                hhpp.setHhppUsuarioCreacion(usuario);
                hhpp.setNap(nap);
                //guarda hhpp en MGL
                BigDecimal hhppId = hhppManager.saveHhppInRepo(hhpp, nombreFuncionalidad);
                if (hhppId != null) {
                    //se asigna Id del hhpp creado    
                    entityRR.setIdHhpp(hhppId.toString());
                    direccionRREntity.setIdHhpp(entityRR.getIdHhpp());
                    HhppMgl hhppMgl = new HhppMgl();
                    hhppMgl.setHhpId(hhppId);
                    //se agrega marca de Hhpp sin verificación por default
                    MarcasMglManager marcasManager = new MarcasMglManager();
                    MarcasHhppMglManager marcasHhppMglManager = new MarcasHhppMglManager();
                    MarcasMgl marca = marcasManager.findMarcasMglByCodigo(co.com.claro.mgl.utils.Constant.MARCA_HHPP_SIN_VERIFICACION);
                    if (marca != null) {
                        MarcasHhppMgl marcasHhppMgl = new MarcasHhppMgl();
                        marcasHhppMgl.setHhpp(hhppMgl);
                        marcasHhppMgl.setMarId(marca);
                        marcasHhppMgl.setMhhFechaCreacion(new Date());
                        marcasHhppMgl.setMhhUsuarioCreacion("MER");
                        marcasHhppMgl.setEstadoRegistro(1);
                        marcasHhppMglManager.create(marcasHhppMgl);
                    }

                    //listado de marcas agregado en la solicitud de creacion por el usuario.
                    if (marcasAgregadasSolicitudList != null && !marcasAgregadasSolicitudList.isEmpty()) {
                        CmtDefaultBasicResponse responseAgregarMarcasHhpp
                                = hhppMglManager.agregarMarcasHhpp(hhppMgl, marcasAgregadasSolicitudList, usuario);

                        /*Si ocurre un error diferente a que la marca ya se encuentra registrada en el HHPP muestra el error al usuario,
                      de lo contrario simplemente no la agrega*/
                        if (responseAgregarMarcasHhpp != null && responseAgregarMarcasHhpp.getMessageType() != null
                                && !responseAgregarMarcasHhpp.getMessageType().isEmpty()
                                && responseAgregarMarcasHhpp.getMessage() != null
                                && !responseAgregarMarcasHhpp.getMessage().isEmpty()
                                && !responseAgregarMarcasHhpp.getMessageType().equalsIgnoreCase("I")
                                && responseAgregarMarcasHhpp.getMessageType().equalsIgnoreCase("E")
                                && responseAgregarMarcasHhpp.getMessage().contains("(RPTD)")) {
                            throw new ApplicationException(responseAgregarMarcasHhpp.getMessage());
                        }
                    }
                }

            } else {
                //se establecen los valores de RR del hhpp que se va a crear
                if (hhpp.getEstadoHhpp() != null
                        && hhpp.getEstadoHhpp().getEhhNombre() != null) {
                    hhpp.setEstado_unit(hhpp.getEstadoHhpp().getEhhNombre().substring(0, 1));
                }

                String tipoEdificio;
                tipoEdificio = obtenerTipoEdificio(direccionRREntity.getNumeroApartamento(), carpeta, tipoSol);
                hhpp.setTipo_unidad(tipoEdificio);
                hhpp.setCalleRR(direccionRREntity.getCalle().toUpperCase());
                hhpp.setUnidadRR(direccionRREntity.getNumeroUnidad().toUpperCase());
                hhpp.setAptoRR(direccionRREntity.getNumeroApartamento().toUpperCase());
                hhpp.setComunidadRR(comunidad);
                hhpp.setDivisionRR(division);
                hhpp.setVendedor("9999");
                hhpp.setNap(nap);
                CityEntity cityEntity = new CityEntity();
                CmtComunidadRr cmtComunidadRr = cmtComunidadRrManager.findComunidadByComunidad(comunidad);
                cityEntity.setPostalCode(cmtComunidadRr.getCodigoPostal() != null ? cmtComunidadRr.getCodigoPostal() : null);
                if (cityEntity != null) {
                    hhpp.setCodigo_postal(cityEntity.getPostalCode() != null ? cityEntity.getPostalCode() : null);
                }
                if (carpeta.equals(VERIFICACION_CASAS)) {
                    hhpp.setTipo_acomet("A");//Tipo Acometida
                    hhpp.setTipo_cbl_acometida("11");//Tipo Cable Acometida
                } else if (carpeta.equals(UNIDIRECCIONAL)
                        || carpeta.equals(CUENTA_MATRIZ)) {
                    hhpp.setTipo_acomet("C");//Tipo Acometida
                    hhpp.setTipo_cbl_acometida("R6");//Tipo Cable Acometida
                }
                hhpp.setUlt_ubicacion(hhpp.getNodo() != null ? hhpp.getNodo().getNodCodigo() : null);
                hhpp.setHead_end((nodoMgl != null && nodoMgl.getNodHeadEnd() != null) == true ? nodoMgl.getNodHeadEnd() : null);
                hhpp.setTipo("ND");
                String buildingName = direccionEntity.getBarrio() == null
                        || direccionEntity.getBarrio().trim().isEmpty()
                        || direccionEntity.getBarrio().trim().equalsIgnoreCase("_") ? "NG" : direccionEntity.getBarrio();
                if (buildingName != null && buildingName.length() > 25) {
                    buildingName = buildingName.substring(0, 25);
                }
                hhpp.setEdificio(buildingName.trim());

                hhpp.setHhppUsuarioCreacion(usuario);
                BigDecimal hhppId = hhppManager.saveHhppInRepo(hhpp, nombreFuncionalidad);
                if (hhppId != null) {
                    //se asigna Id del hhpp creado    
                    entityRR.setIdHhpp(hhppId.toString());
                    direccionRREntity.setIdHhpp(entityRR.getIdHhpp());
                    HhppMgl hhppMgl = new HhppMgl();
                    hhppMgl.setHhpId(hhppId);
                    //se agrega marca de Hhpp sin verificación por default
                    MarcasMglManager marcasManager = new MarcasMglManager();
                    MarcasHhppMglManager marcasHhppMglManager = new MarcasHhppMglManager();
                    MarcasMgl marca = marcasManager.findMarcasMglByCodigo(co.com.claro.mgl.utils.Constant.MARCA_HHPP_SIN_VERIFICACION);
                    if (marca != null) {
                        MarcasHhppMgl marcasHhppMgl = new MarcasHhppMgl();
                        marcasHhppMgl.setHhpp(hhppMgl);
                        marcasHhppMgl.setMarId(marca);
                        marcasHhppMgl.setMhhFechaCreacion(new Date());
                        marcasHhppMgl.setMhhUsuarioCreacion("MGL");
                        marcasHhppMgl.setEstadoRegistro(1);
                        marcasHhppMglManager.create(marcasHhppMgl);
                    }

                    //listado de marcas agregado en la solicitud de creacion por el usuario.
                    if (marcasAgregadasSolicitudList != null && !marcasAgregadasSolicitudList.isEmpty()) {
                        CmtDefaultBasicResponse responseAgregarMarcasHhpp
                                = hhppMglManager.agregarMarcasHhpp(hhppMgl, marcasAgregadasSolicitudList, usuario);

                        /*Si ocurre un error diferente a que la marca ya se encuentra registrada en el HHPP muestra el error al usuario,
                      de lo contrario simplemente no la agrega*/
                        if (responseAgregarMarcasHhpp != null && responseAgregarMarcasHhpp.getMessageType() != null
                                && !responseAgregarMarcasHhpp.getMessageType().isEmpty()
                                && responseAgregarMarcasHhpp.getMessage() != null
                                && !responseAgregarMarcasHhpp.getMessage().isEmpty()
                                && !responseAgregarMarcasHhpp.getMessageType().equalsIgnoreCase("I")
                                && responseAgregarMarcasHhpp.getMessageType().equalsIgnoreCase("E")
                                && responseAgregarMarcasHhpp.getMessage().contains("(RPTD)")) {
                            throw new ApplicationException(responseAgregarMarcasHhpp.getMessage());
                        }
                    }
                }
            }
            return entityRR;
        } catch (ApplicationException | ExceptionDB ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de registrar el HHPP. EX000: " + ex.getMessage(), ex);
        }
    }

    public DireccionRREntity registrarHHPP_Inspira_Independiente(String strNodo, String nodoReal, String usuario,
            String carpeta, String nombreFuncionalidad, String nvlSocioEconomico,
            boolean validarConfiabilidad, String tipoSol, String razon, String idUsuario,
            BigDecimal idCentroPobladoGpo, boolean sincronizaRr, String tipoHhppGestion, NodoMgl nodoMglHhpp) throws ApplicationException {
        try {
            DireccionRREntity entityRR = new DireccionRREntity();
            entityRR.setCalle(direccionRREntity.getCalle().toUpperCase());
            entityRR.setNumeroUnidad(direccionRREntity.getNumeroUnidad().toUpperCase());
            entityRR.setNumeroApartamento(direccionRREntity.getNumeroApartamento().toUpperCase());
            entityRR.setResptRegistroHHPPRR(false);
            NodoMglManager nodoMglManager = new NodoMglManager();
            CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();

            if (validarConfiabilidad) {
                if (!validaPresicion()) {
                    entityRR.setResptRegistroHHPPRR(false);
                    return entityRR;
                }
            }

            //si el nodo llega cargado desde fichas no lo carga nuevamente
            NodoMgl nodoMglAproximado;
            if (nodoMglHhpp == null) {
                nodoMglAproximado = nodoMglManager.findByCodigo(nodoReal);
            } else {
                nodoMglAproximado = nodoMglHhpp;
            }

            Hhpp hhpp = new Hhpp();
            hhpp.setDireccion(detalleDireccion);

            hhpp.setSubDireccion(detalleSubDireccion);
            EstadoHhpp estado = obtenerEstadoHHPP(strNodo, carpeta);
            hhpp.setEstadoHhpp(estado);
            //Ajuste para creacion de HHPP de CM-Constructora.
            if (direccionRREntity.getNumeroApartamento().equalsIgnoreCase("CAMPAMENTO")
                    || direccionRREntity.getNumeroApartamento().equalsIgnoreCase("SALAVENTAS")) {
                String exist = cmtBasicaMglManager.findValorExtendidoEstHhpp(estadoHhppExiste);
                estado = hhppManager.queryEstadoHhpp(exist);//EXISTS
                hhpp.setEstadoHhpp(estado);
            }

            Nodo nodo = nodoManager.queryNodo(strNodo, nodoMglAproximado.getGpoId());//VILLAMILC
            nodoMglManager = new NodoMglManager();
            NodoMgl nodoMgl;
            String comunidad = "000";
            String division = "000";
            if (nodoMglAproximado.getNodId() != null) {
                nodoMgl = nodoMglManager.findByCodigoAndCity(strNodo, nodoMglAproximado.getGpoId());
            } else {
                nodoMgl = nodoMglAproximado;
            }

            //SOAINT
            if (nodoMgl != null && nodoMgl.getNodId() != null) {
                if (nodoMgl.getComId() != null && nodoMgl.getComId().getCodigoRr() != null
                        && nodoMgl.getComId().getRegionalRr() != null) {
                    comunidad = nodoMgl.getComId().getCodigoRr();
                    division = nodoMgl.getComId().getRegionalRr().getCodigoRr();
                    entityRR.setComunidad(comunidad);
                    entityRR.setDivision(division);
                }
            }

            if (nodo != null) {
                hhpp.setNodo(nodo);
            }
            // tipo de vivienda desde la Creacion de ficha
            String tipoEdificioHHPP;
            if (tipoHhppGestion != null && !tipoHhppGestion.isEmpty()) {
                tipoEdificioHHPP = tipoHhppGestion;
            } else {
                tipoEdificioHHPP = obtenerTipoEdificio(direccionRREntity.getNumeroApartamento(), carpeta, tipoSol);
            }

            TipoHhpp tipoHhpp = new TipoHhpp(tipoEdificioHHPP);
            hhpp.setTipoHhpp(tipoHhpp);
            TipoHhppConexion tipoHhppConexion = new TipoHhppConexion();
            TipoHhppRed tipoHhppRed = new TipoHhppRed();
            switch (carpeta) {
                case UNIDIRECCIONAL:
                    tipoHhppConexion.setThcId(new BigDecimal("2"));//Conexion Temporal
                    tipoHhppRed.setThrId(BigDecimal.ONE);
                    break;
                case VERIFICACION_CASAS:
                    //VERIFICACION_CASAS
                    tipoHhppConexion.setThcId(BigDecimal.ONE);//Conexion Temporal
                    tipoHhppRed.setThrId(new BigDecimal("2"));
                    break;
                case CUENTA_MATRIZ:
                    //VERIFICACION_CASAS
                    tipoHhppConexion.setThcId(new BigDecimal("2"));//Conexion Temporal
                    tipoHhppRed.setThrId(BigDecimal.ONE);
                    break;
                default:
                    break;
            }

            hhpp.setTipoConexionHhpp(tipoHhppConexion);
            hhpp.setTipoRedHhpp(tipoHhppRed);
            hhpp.setMarcas(null);
            hhpp.setAmp(getPrefichaXlsMgl().getAmp());
            String nota = "";

            //@author SOAINT
            boolean habilitarRR = false;
            ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
            ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(co.com.claro.mgl.utils.Constant.HABILITAR_RR);
            if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
                habilitarRR = true;
            }

            UnitManagerResponse strIdHHPPRR = new UnitManagerResponse();
            boolean tecnologiaSincronizaRr = false;
            if (habilitarRR && sincronizaRr) {
                if (nodoMgl != null && nodoMgl.getNodId() != null && nodoMgl.getComId() != null
                        && nodoMgl.getComId().getCodigoRr() != null
                        && !nodoMgl.getComId().getRegionalRr().getCodigoRr().isEmpty()
                        && nodoMgl.getComId().getCodigoRr() != null
                        && !nodoMgl.getComId().getRegionalRr().getCodigoRr().isEmpty()) {

                    //Validación para saber si sincroniza con RR la tecnologia del Hhpp
                    if (nodoMgl.getNodTipo().getListCmtBasicaExtMgl() != null
                            && !nodoMgl.getNodTipo().getListCmtBasicaExtMgl().isEmpty()) {

                        for (CmtBasicaExtMgl cmtBasicaExtMgl
                                : nodoMgl.getNodTipo().getListCmtBasicaExtMgl()) {

                            if (cmtBasicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().
                                    equalsIgnoreCase(co.com.claro.mgl.utils.Constant.SINCRONIZA_RR)
                                    && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")) {
                                tecnologiaSincronizaRr = true;

                                if (crearStreetHHPPRR(comunidad, division, direccionRREntity.getCalle().toUpperCase())) {

                                    if (crearCruceRR(comunidad, division, direccionRREntity.getCalle(),
                                            direccionRREntity.getNumeroUnidad(), direccionEntity.getIdtipodireccion())) {

                                        strIdHHPPRR = registrarHHPPRR(hhpp, nvlSocioEconomico, carpeta, comunidad,
                                                division, tipoSol, direccionRREntity.getNumeroApartamento(), direccionRREntity.getCalle(),
                                                direccionRREntity.getNumeroUnidad(), razon,null);

                                        /*Si el hhpp NO fue creado correctamente en RR (CAB0075) y no se encuentra repetido (CAB0420)
                                     es un error de otro tipo*/
                                        if ((strIdHHPPRR == null || strIdHHPPRR.getResponseAddUnit() == null) || (!strIdHHPPRR.getResponseAddUnit().getMessageText().contains("CAB0075")
                                                && !strIdHHPPRR.getResponseAddUnit().getMessageText().contains("CAB0420"))) {
                                            throw new ApplicationException("El HHPP de NODO " + nodoMgl.getNodCodigo()
                                                    + " y con comunidad " + comunidad + " en RR no se pudo crear debido a: "
                                                    + strIdHHPPRR.getResponseAddUnit().getMessageText());
                                        }

                                        //Si el hhpp fue creado correctamente en RR (CAB0075) y no se encuentra repetido (CAB0420)
                                        if (strIdHHPPRR.getResponseAddUnit().getAkyn() != null
                                                && !strIdHHPPRR.getResponseAddUnit().getAkyn().isEmpty()
                                                && strIdHHPPRR.getResponseAddUnit().getMessageText().contains("CAB0075")
                                                && !strIdHHPPRR.getResponseAddUnit().getMessageText().contains("CAB0420") && !nota.equalsIgnoreCase("")) {

                                            agregarNotasF8(comunidad, division, nota,
                                                    direccionRREntity.getCalle(),
                                                    direccionRREntity.getNumeroUnidad(),
                                                    direccionRREntity.getNumeroApartamento(),
                                                    null, usuario);

                                            agregarNotaF18_Inspira(comunidad, division,
                                                    direccionRREntity, detalleDireccion,
                                                    null, nodoReal);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    throw new ApplicationException("RR se encuentra encendido y el Nodo del hhpp no cuenta "
                            + "con la comunidad RR asociada para crear"
                            + " el Hhpp en RR para la tecnologia " + nodoMgl.getNodTipo() != null ? nodoMgl.getNodTipo().getNombreBasica() : "NA");
                }
            }

            /*author SOAINT*/
            //Ajuste de quitar dependencia de RR al crear Hhpp
            //Guarda en MGL despues de haber guardado en RR.
            if (habilitarRR && tecnologiaSincronizaRr && sincronizaRr
                    && strIdHHPPRR.getResponseAddUnit().getAkyn() != null
                    && !strIdHHPPRR.getResponseAddUnit().getAkyn().trim().isEmpty()
                    && strIdHHPPRR.getResponseAddUnit().getMessageText().contains("CAB0075")
                    && !strIdHHPPRR.getResponseAddUnit().getMessageText().contains("CAB0420")) {
                hhpp.setIdRR(strIdHHPPRR.getResponseAddUnit().getAkyn());
                hhpp.setCalleRR(direccionRREntity.getCalle().toUpperCase());
                hhpp.setUnidadRR(direccionRREntity.getNumeroUnidad().toUpperCase());
                hhpp.setAptoRR(direccionRREntity.getNumeroApartamento().toUpperCase());
                hhpp.setHhppUsuarioCreacion(usuario);
                //guarda hhpp en MGL
                BigDecimal hhppId = hhppManager.saveHhppInRepo(hhpp, nombreFuncionalidad);
                if (hhppId != null) {
                    //se asigna Id del hhpp creado    
                    entityRR.setIdHhpp(hhppId.toString());
                    direccionRREntity.setIdHhpp(entityRR.getIdHhpp());
                    HhppMgl hhppMgl = new HhppMgl();
                    hhppMgl.setHhpId(hhppId);
                    //se agrega marca de Hhpp sin verificación por default
                    MarcasMglManager marcasManager = new MarcasMglManager();
                    MarcasHhppMglManager marcasHhppMglManager = new MarcasHhppMglManager();
                    MarcasMgl marca = marcasManager.findMarcasMglByCodigo(co.com.claro.mgl.utils.Constant.MARCA_HHPP_SIN_VERIFICACION);
                    if (marca != null) {
                        MarcasHhppMgl marcasHhppMgl = new MarcasHhppMgl();
                        marcasHhppMgl.setHhpp(hhppMgl);
                        marcasHhppMgl.setMarId(marca);
                        marcasHhppMgl.setMhhFechaCreacion(new Date());
                        marcasHhppMgl.setMhhUsuarioCreacion("MGL");
                        marcasHhppMgl.setEstadoRegistro(1);
                        marcasHhppMglManager.create(marcasHhppMgl);
                    }
                }

            } else {
                //se establecen los valores de RR del hhpp que se va a crear
                if (hhpp.getEstadoHhpp() != null
                        && hhpp.getEstadoHhpp().getEhhNombre() != null) {
                    hhpp.setEstado_unit(hhpp.getEstadoHhpp().getEhhNombre().substring(0, 1));
                }

                String tipoEdificio;
                tipoEdificio = obtenerTipoEdificio(direccionRREntity.getNumeroApartamento(), carpeta, tipoSol);
                hhpp.setTipo_unidad(tipoEdificio);
                hhpp.setCalleRR(direccionRREntity.getCalle().toUpperCase());
                hhpp.setUnidadRR(direccionRREntity.getNumeroUnidad().toUpperCase());
                hhpp.setAptoRR(direccionRREntity.getNumeroApartamento().toUpperCase());
                hhpp.setComunidadRR(comunidad);
                hhpp.setDivisionRR(division);
                hhpp.setVendedor("9999");
                CityEntity cityEntity = new CityEntity();
                CmtComunidadRrManager cmtComunidadRrManager = new CmtComunidadRrManager();

                CmtComunidadRr cmtComunidadRr = cmtComunidadRrManager.findComunidadByComunidad(comunidad);
                cityEntity.setPostalCode(cmtComunidadRr.getCodigoPostal() != null ? cmtComunidadRr.getCodigoPostal() : null);
                if (cityEntity.getPostalCode() != null) {
                    hhpp.setCodigo_postal(cityEntity.getPostalCode());
                } else {
                    hhpp.setCodigo_postal("000");
                }
                if (carpeta.equals(VERIFICACION_CASAS)) {
                    hhpp.setTipo_acomet("A");//Tipo Acometida
                    hhpp.setTipo_cbl_acometida("11");//Tipo Cable Acometida
                } else if (carpeta.equals(UNIDIRECCIONAL)
                        || carpeta.equals(CUENTA_MATRIZ)) {
                    hhpp.setTipo_acomet("C");//Tipo Acometida
                    hhpp.setTipo_cbl_acometida("R6");//Tipo Cable Acometida
                }
                hhpp.setUlt_ubicacion(hhpp.getNodo() != null ? hhpp.getNodo().getNodCodigo() : null);
                hhpp.setHead_end((nodoMgl != null && nodoMgl.getNodHeadEnd() != null) == true ? nodoMgl.getNodHeadEnd() : null);
                hhpp.setTipo("ND");
                String buildingName = direccionEntity.getBarrio() == null
                        || direccionEntity.getBarrio().trim().isEmpty()
                        || direccionEntity.getBarrio().trim().equalsIgnoreCase("_") ? "NG" : direccionEntity.getBarrio();
                if (buildingName != null && buildingName.length() > 25) {
                    buildingName = buildingName.substring(0, 25);
                }
                hhpp.setEdificio(buildingName.trim());

                hhpp.setHhppUsuarioCreacion(usuario);
                BigDecimal hhppId = hhppManager.saveHhppInRepo(hhpp, nombreFuncionalidad);
                if (hhppId != null) {
                    //se asigna Id del hhpp creado    
                    entityRR.setIdHhpp(hhppId.toString());
                    direccionRREntity.setIdHhpp(entityRR.getIdHhpp());
                    HhppMgl hhppMgl = new HhppMgl();
                    hhppMgl.setHhpId(hhppId);
                    //se agrega marca de Hhpp sin verificación por default
                    MarcasMglManager marcasManager = new MarcasMglManager();
                    MarcasHhppMglManager marcasHhppMglManager = new MarcasHhppMglManager();
                    MarcasMgl marca = marcasManager.findMarcasMglByCodigo(co.com.claro.mgl.utils.Constant.MARCA_HHPP_SIN_VERIFICACION);
                    if (marca != null) {
                        MarcasHhppMgl marcasHhppMgl = new MarcasHhppMgl();
                        marcasHhppMgl.setHhpp(hhppMgl);
                        marcasHhppMgl.setMarId(marca);
                        marcasHhppMgl.setMhhFechaCreacion(new Date());
                        marcasHhppMgl.setMhhUsuarioCreacion("MGL");
                        marcasHhppMgl.setEstadoRegistro(1);
                        marcasHhppMglManager.create(marcasHhppMgl);
                    }
                }
            }
            return entityRR;
        } catch (ApplicationException | ExceptionDB ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de registrar el HHPP. EX000: " + ex.getMessage(), ex);
        }
    }

    // Metodo nueva implementacion fichas
    public DireccionRREntity registrarHHPP_Inspira_Independiente_New(String strNodo, String nodoReal, String usuario,
            String carpeta, String nombreFuncionalidad, String nvlSocioEconomico,
            boolean validarConfiabilidad, String tipoSol, String razon, String idUsuario,
            BigDecimal idCentroPobladoGpo, boolean sincronizaRr, String tipoHhppGestion, NodoMgl nodoMglHhpp, String amp) throws ApplicationException {
        try {
            DireccionRREntity entityRR = new DireccionRREntity();
            entityRR.setCalle(direccionRREntity.getCalle().toUpperCase());
            entityRR.setNumeroUnidad(direccionRREntity.getNumeroUnidad().toUpperCase());
            entityRR.setNumeroApartamento(direccionRREntity.getNumeroApartamento().toUpperCase());
            entityRR.setResptRegistroHHPPRR(false);
            NodoMglManager nodoMglManager = new NodoMglManager();
            CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();

            if (validarConfiabilidad) {
                if (!validaPresicion()) {
                    entityRR.setResptRegistroHHPPRR(false);
                    return entityRR;
                }
            }

            //si el nodo llega cargado desde fichas no lo carga nuevamente
            NodoMgl nodoMglAproximado;
            if (nodoMglHhpp == null) {
                nodoMglAproximado = nodoMglManager.findByCodigo(nodoReal);
            } else {
                nodoMglAproximado = nodoMglHhpp;
            }

            Hhpp hhpp = new Hhpp();
            hhpp.setDireccion(detalleDireccion);

            hhpp.setSubDireccion(detalleSubDireccion);
            EstadoHhpp estado = obtenerEstadoHHPP(strNodo, carpeta);
            hhpp.setEstadoHhpp(estado);
            //Ajuste para creacion de HHPP de CM-Constructora.
            if (direccionRREntity.getNumeroApartamento().equalsIgnoreCase("CAMPAMENTO")
                    || direccionRREntity.getNumeroApartamento().equalsIgnoreCase("SALAVENTAS")) {
                String exist = cmtBasicaMglManager.findValorExtendidoEstHhpp(estadoHhppExiste);
                estado = hhppManager.queryEstadoHhpp(exist);//EXISTS
                hhpp.setEstadoHhpp(estado);
            }

            Nodo nodo = nodoManager.queryNodo(strNodo, nodoMglAproximado.getGpoId());//VILLAMILC
            nodoMglManager = new NodoMglManager();
            NodoMgl nodoMgl;
            String comunidad = "000";
            String division = "000";
            if (nodoMglAproximado.getNodId() != null) {
                nodoMgl = nodoMglManager.findByCodigoAndCity(strNodo, nodoMglAproximado.getGpoId());
            } else {
                nodoMgl = nodoMglAproximado;
            }

            //SOAINT
            if (nodoMgl != null && nodoMgl.getNodId() != null) {
                if (nodoMgl.getComId() != null && nodoMgl.getComId().getCodigoRr() != null
                        && nodoMgl.getComId().getRegionalRr() != null) {
                    comunidad = nodoMgl.getComId().getCodigoRr();
                    division = nodoMgl.getComId().getRegionalRr().getCodigoRr();
                    entityRR.setComunidad(comunidad);
                    entityRR.setDivision(division);
                }
            }

            if (nodo != null) {
                hhpp.setNodo(nodo);
            }
            // tipo de vivienda desde la Creacion de ficha
            String tipoEdificioHHPP;
            if (tipoHhppGestion != null && !tipoHhppGestion.isEmpty()) {
                tipoEdificioHHPP = tipoHhppGestion;
            } else {
                tipoEdificioHHPP = obtenerTipoEdificio(direccionRREntity.getNumeroApartamento(), carpeta, tipoSol);
            }

            TipoHhpp tipoHhpp = new TipoHhpp(tipoEdificioHHPP);
            hhpp.setTipoHhpp(tipoHhpp);
            TipoHhppConexion tipoHhppConexion = new TipoHhppConexion();
            TipoHhppRed tipoHhppRed = new TipoHhppRed();
            switch (carpeta) {
                case UNIDIRECCIONAL:
                    tipoHhppConexion.setThcId(new BigDecimal("2"));//Conexion Temporal
                    tipoHhppRed.setThrId(BigDecimal.ONE);
                    break;
                case VERIFICACION_CASAS:
                    //VERIFICACION_CASAS
                    tipoHhppConexion.setThcId(BigDecimal.ONE);//Conexion Temporal
                    tipoHhppRed.setThrId(new BigDecimal("2"));
                    break;
                case CUENTA_MATRIZ:
                    //VERIFICACION_CASAS
                    tipoHhppConexion.setThcId(new BigDecimal("2"));//Conexion Temporal
                    tipoHhppRed.setThrId(BigDecimal.ONE);
                    break;
                default:
                    break;
            }

            hhpp.setTipoConexionHhpp(tipoHhppConexion);
            hhpp.setTipoRedHhpp(tipoHhppRed);
            hhpp.setMarcas(null);
            hhpp.setAmp(Optional.ofNullable(amp).orElse(""));
            String nota = "";

            //@author SOAINT
            boolean habilitarRR = false;
            ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
            ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(co.com.claro.mgl.utils.Constant.HABILITAR_RR);
            if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
                habilitarRR = true;
            }

            UnitManagerResponse strIdHHPPRR = new UnitManagerResponse();
            boolean tecnologiaSincronizaRr = false;
            if (habilitarRR && sincronizaRr) {
                if (nodoMgl != null && nodoMgl.getNodId() != null && nodoMgl.getComId() != null
                        && nodoMgl.getComId().getCodigoRr() != null
                        && !nodoMgl.getComId().getRegionalRr().getCodigoRr().isEmpty()
                        && nodoMgl.getComId().getCodigoRr() != null
                        && !nodoMgl.getComId().getRegionalRr().getCodigoRr().isEmpty()) {

                    //Validación para saber si sincroniza con RR la tecnologia del Hhpp
                    if (nodoMgl.getNodTipo().getListCmtBasicaExtMgl() != null
                            && !nodoMgl.getNodTipo().getListCmtBasicaExtMgl().isEmpty()) {

                        for (CmtBasicaExtMgl cmtBasicaExtMgl
                                : nodoMgl.getNodTipo().getListCmtBasicaExtMgl()) {

                            if (cmtBasicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().
                                    equalsIgnoreCase(co.com.claro.mgl.utils.Constant.SINCRONIZA_RR)
                                    && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")) {
                                tecnologiaSincronizaRr = true;

                                if (crearStreetHHPPRR(comunidad, division, direccionRREntity.getCalle().toUpperCase())) {

                                    if (crearCruceRR(comunidad, division, direccionRREntity.getCalle(),
                                            direccionRREntity.getNumeroUnidad(), direccionEntity.getIdtipodireccion())) {

                                        strIdHHPPRR = registrarHHPPRR(hhpp, nvlSocioEconomico, carpeta, comunidad,
                                                division, tipoSol, direccionRREntity.getNumeroApartamento(), direccionRREntity.getCalle(),
                                                direccionRREntity.getNumeroUnidad(), razon,null);

                                        /*Si el hhpp NO fue creado correctamente en RR (CAB0075) y no se encuentra repetido (CAB0420)
                                     es un error de otro tipo*/
                                        if ((strIdHHPPRR == null || strIdHHPPRR.getResponseAddUnit() == null) || (!strIdHHPPRR.getResponseAddUnit().getMessageText().contains("CAB0075")
                                                && !strIdHHPPRR.getResponseAddUnit().getMessageText().contains("CAB0420"))) {
                                            throw new ApplicationException("El HHPP de NODO " + nodoMgl.getNodCodigo()
                                                    + " y con comunidad " + comunidad + " en RR no se pudo crear debido a: "
                                                    + strIdHHPPRR.getResponseAddUnit().getMessageText());
                                        }

                                        //Si el hhpp fue creado correctamente en RR (CAB0075) y no se encuentra repetido (CAB0420)
                                        if (strIdHHPPRR.getResponseAddUnit().getAkyn() != null
                                                && !strIdHHPPRR.getResponseAddUnit().getAkyn().isEmpty()
                                                && strIdHHPPRR.getResponseAddUnit().getMessageText().contains("CAB0075")
                                                && !strIdHHPPRR.getResponseAddUnit().getMessageText().contains("CAB0420") && !nota.equalsIgnoreCase("")) {

                                            agregarNotasF8(comunidad, division, nota,
                                                    direccionRREntity.getCalle(),
                                                    direccionRREntity.getNumeroUnidad(),
                                                    direccionRREntity.getNumeroApartamento(),
                                                    null, usuario);

                                            agregarNotaF18_Inspira(comunidad, division,
                                                    direccionRREntity, detalleDireccion,
                                                    null, nodoReal);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    throw new ApplicationException("RR se encuentra encendido y el Nodo del hhpp no cuenta "
                            + "con la comunidad RR asociada para crear"
                            + " el Hhpp en RR para la tecnologia " + nodoMgl.getNodTipo() != null ? nodoMgl.getNodTipo().getNombreBasica() : "NA");
                }
            }

            /*author SOAINT*/
            //Ajuste de quitar dependencia de RR al crear Hhpp
            //Guarda en MGL despues de haber guardado en RR.
            if (habilitarRR && tecnologiaSincronizaRr && sincronizaRr
                    && strIdHHPPRR.getResponseAddUnit().getAkyn() != null
                    && !strIdHHPPRR.getResponseAddUnit().getAkyn().trim().isEmpty()
                    && strIdHHPPRR.getResponseAddUnit().getMessageText().contains("CAB0075")
                    && !strIdHHPPRR.getResponseAddUnit().getMessageText().contains("CAB0420")) {
                hhpp.setIdRR(strIdHHPPRR.getResponseAddUnit().getAkyn());
                hhpp.setCalleRR(direccionRREntity.getCalle().toUpperCase());
                hhpp.setUnidadRR(direccionRREntity.getNumeroUnidad().toUpperCase());
                hhpp.setAptoRR(direccionRREntity.getNumeroApartamento().toUpperCase());
                hhpp.setHhppUsuarioCreacion(usuario);
                //guarda hhpp en MGL
                BigDecimal hhppId = hhppManager.saveHhppInRepo(hhpp, nombreFuncionalidad);
                if (hhppId != null) {
                    //se asigna Id del hhpp creado    
                    entityRR.setIdHhpp(hhppId.toString());
                    direccionRREntity.setIdHhpp(entityRR.getIdHhpp());
                    HhppMgl hhppMgl = new HhppMgl();
                    hhppMgl.setHhpId(hhppId);
                    //se agrega marca de Hhpp sin verificación por default
                    MarcasMglManager marcasManager = new MarcasMglManager();
                    MarcasHhppMglManager marcasHhppMglManager = new MarcasHhppMglManager();
                    MarcasMgl marca = marcasManager.findMarcasMglByCodigo(co.com.claro.mgl.utils.Constant.MARCA_HHPP_SIN_VERIFICACION);
                    if (marca != null) {
                        MarcasHhppMgl marcasHhppMgl = new MarcasHhppMgl();
                        marcasHhppMgl.setHhpp(hhppMgl);
                        marcasHhppMgl.setMarId(marca);
                        marcasHhppMgl.setMhhFechaCreacion(new Date());
                        marcasHhppMgl.setMhhUsuarioCreacion("MGL");
                        marcasHhppMgl.setEstadoRegistro(1);
                        marcasHhppMglManager.create(marcasHhppMgl);
                    }
                }

            } else {
                //se establecen los valores de RR del hhpp que se va a crear
                if (hhpp.getEstadoHhpp() != null
                        && hhpp.getEstadoHhpp().getEhhNombre() != null) {
                    hhpp.setEstado_unit(hhpp.getEstadoHhpp().getEhhNombre().substring(0, 1));
                }

                String tipoEdificio;
                tipoEdificio = obtenerTipoEdificio(direccionRREntity.getNumeroApartamento(), carpeta, tipoSol);
                hhpp.setTipo_unidad(tipoEdificio);
                hhpp.setCalleRR(direccionRREntity.getCalle().toUpperCase());
                hhpp.setUnidadRR(direccionRREntity.getNumeroUnidad().toUpperCase());
                hhpp.setAptoRR(direccionRREntity.getNumeroApartamento().toUpperCase());
                hhpp.setComunidadRR(comunidad);
                hhpp.setDivisionRR(division);
                hhpp.setVendedor("9999");
                CityEntity cityEntity = new CityEntity();
                CmtComunidadRrManager cmtComunidadRrManager = new CmtComunidadRrManager();

                CmtComunidadRr cmtComunidadRr = cmtComunidadRrManager.findComunidadByComunidad(comunidad);
                cityEntity.setPostalCode(cmtComunidadRr.getCodigoPostal() != null ? cmtComunidadRr.getCodigoPostal() : null);
                if (cityEntity.getPostalCode() != null) {
                    hhpp.setCodigo_postal(cityEntity.getPostalCode());
                } else {
                    hhpp.setCodigo_postal("000");
                }
                if (carpeta.equals(VERIFICACION_CASAS)) {
                    hhpp.setTipo_acomet("A");//Tipo Acometida
                    hhpp.setTipo_cbl_acometida("11");//Tipo Cable Acometida
                } else if (carpeta.equals(UNIDIRECCIONAL)
                        || carpeta.equals(CUENTA_MATRIZ)) {
                    hhpp.setTipo_acomet("C");//Tipo Acometida
                    hhpp.setTipo_cbl_acometida("R6");//Tipo Cable Acometida
                }
                hhpp.setUlt_ubicacion(hhpp.getNodo() != null ? hhpp.getNodo().getNodCodigo() : null);
                hhpp.setHead_end((nodoMgl != null && nodoMgl.getNodHeadEnd() != null) == true ? nodoMgl.getNodHeadEnd() : null);
                hhpp.setTipo("ND");
                String buildingName = direccionEntity.getBarrio() == null
                        || direccionEntity.getBarrio().trim().isEmpty()
                        || direccionEntity.getBarrio().trim().equalsIgnoreCase("_") ? "NG" : direccionEntity.getBarrio();
                if (buildingName != null && buildingName.length() > 25) {
                    buildingName = buildingName.substring(0, 25);
                }
                hhpp.setEdificio(buildingName.trim());

                hhpp.setHhppUsuarioCreacion(usuario);
                BigDecimal hhppId = hhppManager.saveHhppInRepo(hhpp, nombreFuncionalidad);
                if (hhppId != null) {
                    //se asigna Id del hhpp creado    
                    entityRR.setIdHhpp(hhppId.toString());
                    direccionRREntity.setIdHhpp(entityRR.getIdHhpp());
                    HhppMgl hhppMgl = new HhppMgl();
                    hhppMgl.setHhpId(hhppId);
                    //se agrega marca de Hhpp sin verificación por default
                    MarcasMglManager marcasManager = new MarcasMglManager();
                    MarcasHhppMglManager marcasHhppMglManager = new MarcasHhppMglManager();
                    MarcasMgl marca = marcasManager.findMarcasMglByCodigo(co.com.claro.mgl.utils.Constant.MARCA_HHPP_SIN_VERIFICACION);
                    if (marca != null) {
                        MarcasHhppMgl marcasHhppMgl = new MarcasHhppMgl();
                        marcasHhppMgl.setHhpp(hhppMgl);
                        marcasHhppMgl.setMarId(marca);
                        marcasHhppMgl.setMhhFechaCreacion(new Date());
                        marcasHhppMgl.setMhhUsuarioCreacion("MGL");
                        marcasHhppMgl.setEstadoRegistro(1);
                        marcasHhppMglManager.create(marcasHhppMgl);
                    }
                }
            }
            return entityRR;
        } catch (ApplicationException | ExceptionDB ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de registrar el HHPP. EX000: " + ex.getMessage(), ex);
        }
    }

    public DireccionRREntity registrarHHPPSubPropiaDireccion(CmtSubEdificioMgl cmtSubEdificioMgl,
            CmtDireccionMgl direccion, String strNodo, String nodoReal, String usuario,
            String carpeta, String nombreFuncionalidad, String nvlSocioEconomico,
            boolean validarConfiabilidad, String solicitud, String tipoSol,
            String codCiudad, String observaciones,
            String usuarioSol, String razon, String idUsuario,
            String contacto, String telContacto, String campoUno, String calle, String unidad, CmtHhppVtMgl cmtHhppVtMgl) throws ApplicationException {
        DireccionRREntity entityRR = new DireccionRREntity();
        String calleRR;
        String calleSinEnt;
        String ent;
        NodoMglManager nodoMglManager = new NodoMglManager();
        CmtComunidadRrManager comunidadRrManager = new CmtComunidadRrManager();
        CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
        ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
        ParametrosMgl parametrosMgl = parametrosMglManager.findByAcronimo(co.com.claro.mgl.utils.Constant.NIVELES_CASAS_CON_DIRECCION).get(0);
        String nivel = "";
        if (parametrosMgl != null) {
            nivel = parametrosMgl.getParValor();
        }
        if (!nivel.equals("")) {
            List<String> niveles = Arrays.asList(nivel.split("\\|"));
            if (cmtHhppVtMgl.getOpcionNivel5() != null && !cmtHhppVtMgl.getOpcionNivel5().isEmpty()) {
                if (niveles.contains(cmtHhppVtMgl.getOpcionNivel5())) {
                    nivel = cmtHhppVtMgl.getOpcionNivel5();
                }
            }
        }

        try {
            if (razon.contains("CM")) {
                if (direccion.getCpTipoNivel1() == null) {
                    if ((cmtHhppVtMgl.getOpcionNivel5().equalsIgnoreCase(nivel)) && cmtHhppVtMgl.getValorNivel5() == null) {
                        // escenario unico edificio conjunto casas con su propia direccion 
                        if (cmtHhppVtMgl.getSubEdificioVtObj().getSubEdificioObj().getCmtCuentaMatrizMglObj().getDireccionPrincipal().getCodTipoDir().contains("BM")) {
                            direccionRREntity.setCalle(calle.toUpperCase());
                        } else {
                            direccionRREntity.setCalle(calle.toUpperCase());
                        }
                        direccionRREntity.setNumeroUnidad(unidad);
                        direccionRREntity.setNumeroApartamento(cmtHhppVtMgl.getUnidadRr());
                        entityRR.setResptRegistroHHPPRR(false);
                    } else if (cmtHhppVtMgl.getOpcionNivel5().equalsIgnoreCase(co.com.claro.mgl.utils.Constant.OPCION_NIVEL_OTROS)) {
                        // escenario otros sin direccion en la entrada torres de la cm
                        direccionRREntity.setCalle(direccionRREntity.getCalle().toUpperCase());
                        direccionRREntity.setNumeroUnidad(direccionRREntity.getNumeroUnidad().toUpperCase());
                        direccionRREntity.setNumeroApartamento(cmtHhppVtMgl.getNombreValidoRR().toUpperCase());
                        entityRR.setResptRegistroHHPPRR(false);

                    } else {
                        direccionRREntity.setCalle(direccionRREntity.getCalle().toUpperCase());
                        direccionRREntity.setNumeroUnidad(direccionRREntity.getNumeroUnidad());
                        direccionRREntity.setNumeroApartamento(direccionRREntity.getNumeroApartamento());
                        entityRR.setResptRegistroHHPPRR(false);
                    }

                } else {
                    if (cmtHhppVtMgl.getOpcionNivel5().equalsIgnoreCase(nivel) && cmtHhppVtMgl.getValorNivel5() != null) {
                        calleSinEnt = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getCalleRr();
                        ent = "EN" + " " + cmtHhppVtMgl.getSubEdificioVtObj().getCalleRr() + " " + cmtHhppVtMgl.getSubEdificioVtObj().getUnidadRr();
                        if (ent.length() > 24) {
                            ent = ent.substring(0, 24);
                            calleRR = calleSinEnt + " " + ent;
                        } else {
                            calleRR = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getCalleRr() + " " + "EN" + " " + cmtHhppVtMgl.getSubEdificioVtObj().getCalleRr() + " " + cmtHhppVtMgl.getSubEdificioVtObj().getUnidadRr();
                        }
                        if (calleRR.length() > 50) {
                            calleRR = calleRR.substring(0, 50);
                        }
                        String unidadRR = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getUnidadRr();
                        direccionRREntity.setCalle(calleRR);
                        direccionRREntity.setNumeroUnidad(unidadRR);
                        direccionRREntity.setNumeroApartamento(direccionRREntity.getNumeroApartamento().toUpperCase());
                        entityRR.setResptRegistroHHPPRR(false);

                    } else if (cmtHhppVtMgl.getOpcionNivel5().equalsIgnoreCase(nivel) && cmtHhppVtMgl.getValorNivel5() == null) {
                        // subedificios con direccion em la entrada y con su propia direccion
                        calleSinEnt = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getCalleRr();
                        ent = "EN" + " " + cmtHhppVtMgl.getSubEdificioVtObj().getCalleRr() + " " + cmtHhppVtMgl.getSubEdificioVtObj().getUnidadRr();
                        if (ent.length() > 24) {
                            ent = ent.substring(0, 24);
                            calleRR = calleSinEnt + " " + ent;
                        } else {
                            calleRR = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getCalleRr() + " " + "EN" + " " + cmtHhppVtMgl.getSubEdificioVtObj().getCalleRr() + " " + cmtHhppVtMgl.getSubEdificioVtObj().getUnidadRr();
                        }
                        String unidadRR = cmtHhppVtMgl.getUnidadRr();
                        direccionRREntity.setCalle(calleRR);
                        direccionRREntity.setNumeroUnidad(cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getUnidadRr().toUpperCase());
                        direccionRREntity.setNumeroApartamento(unidadRR);
                        entityRR.setResptRegistroHHPPRR(false);

                    } else if (!cmtHhppVtMgl.getOpcionNivel5().equalsIgnoreCase(nivel)) {
                        // edificios con entrada , escenario otros
                        String unidadRR;
                        calleSinEnt = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getCalleRr();
                        ent = "EN" + " " + cmtHhppVtMgl.getSubEdificioVtObj().getCalleRr() + " " + cmtHhppVtMgl.getSubEdificioVtObj().getUnidadRr();
                        if (ent.length() > 24) {
                            ent = ent.substring(0, 24);
                            calleRR = calleSinEnt + " " + ent;
                        } else {
                            calleRR = calleSinEnt + " " + ent;
                        }
                        if (calleRR.length() > 50) {
                            calleRR = calleRR.substring(0, 49);
                        }
                        unidadRR = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getUnidadRr();
                        direccionRREntity.setCalle(calleRR);
                        direccionRREntity.setNumeroUnidad(unidadRR);
                        direccionRREntity.setNumeroApartamento(direccionRREntity.getNumeroApartamento().toUpperCase());
                        entityRR.setResptRegistroHHPPRR(false);
                    } else {
                        direccionRREntity.setCalle(calle);
                        direccionRREntity.setNumeroUnidad(unidad);
                        direccionRREntity.setNumeroApartamento(direccionRREntity.getNumeroApartamento().toUpperCase());
                        entityRR.setResptRegistroHHPPRR(false);
                    }
                }
            } else {
                // hhpp que provieen de solicitud de hhpp
                calleSinEnt = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getCalleRr();
                if (cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getCentroPoblado().getGpoMultiorigen().equals("1")) {
                    ent = "EN" + " " + cmtSubEdificioMgl.getListDireccionesMgl().get(0).getCalleRr() + " " + cmtSubEdificioMgl.getListDireccionesMgl().get(0).getUnidadRr();
                } else {
                    ent = "EN" + " " + cmtSubEdificioMgl.getListDireccionesMgl().get(0).getCalleRr() + " " + cmtSubEdificioMgl.getListDireccionesMgl().get(0).getUnidadRr();
                }
                if (ent.length() > 24) {
                    ent = ent.substring(0, 24);
                    calleRR = calleSinEnt + " " + ent;
                } else {
                    calleRR = cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getCalleRr() + " " + "EN" + " " + cmtSubEdificioMgl.getListDireccionesMgl().get(0).getCalleRr() + " " + cmtSubEdificioMgl.getListDireccionesMgl().get(0).getUnidadRr();
                }
                if (calleRR.length() > 50) {
                    calleRR = calleRR.substring(0, 49);
                }

                direccionRREntity.setCalle(calleRR);
                direccionRREntity.setNumeroUnidad(unidad);
                direccionRREntity.setNumeroApartamento(direccionRREntity.getNumeroApartamento().toUpperCase());
                entityRR.setResptRegistroHHPPRR(false);
            }

            if (validarConfiabilidad) {
                if (!validaPresicion()) {
                    entityRR.setResptRegistroHHPPRR(false);
                    return entityRR;
                }
            }
            //@cardenaslb
            // ajuste migracion de tabla rr_nodos a TEC_NODO 
            NodoMgl nodoMglAproximado = nodoMglManager.findByCodigo(nodoReal);

            Hhpp hhpp = new Hhpp();
            hhpp.setDireccion(detalleDireccion);

            hhpp.setSubDireccion(detalleSubDireccion);
            EstadoHhpp estado = obtenerEstadoHHPP(strNodo, carpeta);
            hhpp.setEstadoHhpp(estado);
            //Ajuste para creacion de HHPP de CM-Constructora.
            if (direccionRREntity.getNumeroApartamento().equalsIgnoreCase("CAMPAMENTO")
                    || direccionRREntity.getNumeroApartamento().equalsIgnoreCase("SALAVENTAS")) {
                String exist = cmtBasicaMglManager.findValorExtendidoEstHhpp(estadoHhppExiste);
                estado = hhppManager.queryEstadoHhpp(exist);//EXIST
                hhpp.setEstadoHhpp(estado);
            }

            Nodo nodo = nodoManager.queryNodo(strNodo, nodoMglAproximado.getGpoId());//VILLAMILC
            NodoMgl nodoMgl = nodoMglManager.findByCodigoAndCity(strNodo, nodoMglAproximado.getGpoId());
            String comunidad = "000";
            String division = "000";

            if (nodoMgl.getNodId() != null) {
                if (nodoMgl.getComId() != null && nodoMgl.getComId().getCodigoRr() != null
                        && nodoMgl.getComId().getRegionalRr() != null) {
                    comunidad = nodoMglAproximado.getComId().getCodigoRr();
                    division = nodoMglAproximado.getComId().getRegionalRr().getCodigoRr();
                    entityRR.setComunidad(comunidad);
                    entityRR.setDivision(division);
                }
            }
            if (nodo != null) {
                hhpp.setNodo(nodo);
            }
            //@cardenaslb
            // se coloca el valor del subedificio si se selecciono de la vista
            String tipoEdificioHHPP = "";
            if (razon.contains("CM")) {
                if (cmtHhppVtMgl != null) {
                    if (cmtHhppVtMgl.getOpcionNivel5().equalsIgnoreCase(co.com.claro.mgl.utils.Constant.OPCION_NIVEL_OTROS)) {
                        // escenario otros el apartamento viene con el string + el valor rr 
                        // se asigna casa de manera que coincida con la regla
                        String apartamento = "CASA";
                        tipoEdificioHHPP = obtenerTipoEdificio(apartamento, carpeta, tipoSol);
                    } else {
                        if (cmtHhppVtMgl.getThhVtId() != null) {
                            tipoEdificioHHPP = cmtHhppVtMgl.getThhVtId();
                        } else {
                            tipoEdificioHHPP = obtenerTipoEdificio(direccionRREntity.getNumeroApartamento(), carpeta, tipoSol);
                        }
                    }

                }
            } else {
                if (cmtHhppVtMgl.getThhVtId() != null) {
                    tipoEdificioHHPP = cmtHhppVtMgl.getThhVtId();
                } else {
                    tipoEdificioHHPP = obtenerTipoEdificio(direccionRREntity.getNumeroApartamento(), carpeta, tipoSol);
                }
            }

            TipoHhpp tipoHhpp = new TipoHhpp(tipoEdificioHHPP);
            hhpp.setTipoHhpp(tipoHhpp);
            TipoHhppConexion tipoHhppConexion = new TipoHhppConexion();
            TipoHhppRed tipoHhppRed = new TipoHhppRed();
            switch (carpeta) {
                case UNIDIRECCIONAL:
                    tipoHhppConexion.setThcId(new BigDecimal("2"));//Conexion Temporal
                    tipoHhppRed.setThrId(BigDecimal.ONE);
                    break;
                case VERIFICACION_CASAS:
                    //VERIFICACION_CASAS
                    tipoHhppConexion.setThcId(BigDecimal.ONE);//Conexion Temporal
                    tipoHhppRed.setThrId(new BigDecimal("2"));
                    break;
                case CUENTA_MATRIZ:
                    //VERIFICACION_CASAS
                    tipoHhppConexion.setThcId(new BigDecimal("2"));//Conexion Temporal
                    tipoHhppRed.setThrId(BigDecimal.ONE);
                    break;
                default:
                    break;
            }

            hhpp.setTipoConexionHhpp(tipoHhppConexion);
            hhpp.setTipoRedHhpp(tipoHhppRed);
            hhpp.setMarcas(null);
            String nota = "";

            if (carpeta.equalsIgnoreCase(UNIDIRECCIONAL) || carpeta.equalsIgnoreCase(VERIFICACION_CASAS)
                    || carpeta.equalsIgnoreCase(CUENTA_MATRIZ)) {
                nota = "NODO REAL: " + nodoReal + "; SOLICITUD: " + solicitud
                        + "; SOLICITANTE: " + usuarioSol + "; CANAL: " + tipoSol
                        + "; OBSERVACION: " + observaciones;
            }

            //@author Juan David Hernandez
            boolean tecnologiaSincronizaRr = false;
            boolean habilitarRR = false;
            parametrosMglManager = new ParametrosMglManager();
            ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(co.com.claro.mgl.utils.Constant.HABILITAR_RR);
            if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
                habilitarRR = true;
            }

            UnitManagerResponse strIdHHPPRR = new UnitManagerResponse();

            if (habilitarRR) {
                if (nodoMgl.getNodId() != null && nodoMgl.getComId() != null
                        && nodoMgl.getComId().getCodigoRr() != null
                        && !nodoMgl.getComId().getRegionalRr().getCodigoRr().isEmpty()
                        && nodoMgl.getComId().getCodigoRr() != null
                        && !nodoMgl.getComId().getRegionalRr().getCodigoRr().isEmpty()) {

                    //Validación para saber si sincroniza con RR la tecnologia del Hhpp
                    if (nodoMgl.getNodTipo().getListCmtBasicaExtMgl() != null
                            && !nodoMgl.getNodTipo().getListCmtBasicaExtMgl().isEmpty()) {

                        for (CmtBasicaExtMgl cmtBasicaExtMgl
                                : nodoMgl.getNodTipo().getListCmtBasicaExtMgl()) {
                            if (cmtBasicaExtMgl.getIdTipoBasicaExt().getCampoEntidadAs400().
                                    equalsIgnoreCase(co.com.claro.mgl.utils.Constant.SINCRONIZA_RR)
                                    && cmtBasicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")) {
                                tecnologiaSincronizaRr = true;
                                if (crearStreetHHPPRR(comunidad, division, direccionRREntity.getCalle().toUpperCase())) {

                                    if (crearCruceRR(comunidad, division, direccionRREntity.getCalle(),
                                            direccionRREntity.getNumeroUnidad(), direccionEntity.getIdtipodireccion())) {

                                        strIdHHPPRR = registrarHHPPRR(hhpp, nvlSocioEconomico, carpeta, comunidad,
                                                division, tipoSol, direccionRREntity.getNumeroApartamento(), direccionRREntity.getCalle(),
                                                direccionRREntity.getNumeroUnidad(), razon, null);

                                        /*Si el hhpp NO fue creado correctamente en RR (CAB0075) y no se encuentra repetido (CAB0420)
                                     es un error de otro tipo*/
                                        if ((strIdHHPPRR == null || strIdHHPPRR.getResponseAddUnit() == null) || (!strIdHHPPRR.getResponseAddUnit().getMessageText().contains("CAB0075")
                                                && !strIdHHPPRR.getResponseAddUnit().getMessageText().contains("CAB0420"))) {
                                            if (strIdHHPPRR.getResponseAddUnit() != null) {
                                                entityRR.setMensaje(strIdHHPPRR.getResponseAddUnit().getMessageText());
                                            }
                                        }

                                        //Si el hhpp fue creado correctamente en RR (CAB0075) y no se encuentra repetido (CAB0420)
                                        if (strIdHHPPRR != null && strIdHHPPRR.getResponseAddUnit().getAkyn() != null
                                                && !strIdHHPPRR.getResponseAddUnit().getAkyn().isEmpty()
                                                && strIdHHPPRR.getResponseAddUnit().getMessageText().contains("CAB0075")
                                                && !strIdHHPPRR.getResponseAddUnit().getMessageText().contains("CAB0420")) {

                                            agregarNotasF8(comunidad, division, nota,
                                                    direccionRREntity.getCalle(),
                                                    direccionRREntity.getNumeroUnidad(),
                                                    direccionRREntity.getNumeroApartamento(),
                                                    solicitud, usuario);

                                            agregarNotaF18_Inspira(comunidad, division,
                                                    direccionRREntity, detalleDireccion,
                                                    campoUno, nodoReal);
                                        }

                                    }
                                }
                            }
                        }
                    }
                } else {
                    entityRR.setMensaje("El Nodo no existe o no pertenece a ninguna comunidad");
                    throw new ApplicationException("El Nodo no existe o no pertenece a ninguna comunidad");
                }
            }

            //Ajuste de quitar dependencia de RR al crear Hhpp
            //Guarda en MGL despues de haber guardado en RR.
            //Si el hhpp fue creado correctamente en RR (CAB0075) y no se encuentra repetido (CAB0420)
            if (habilitarRR && strIdHHPPRR != null && strIdHHPPRR.getResponseAddUnit() != null
                    && strIdHHPPRR.getResponseAddUnit().getAkyn() != null
                    && !strIdHHPPRR.getResponseAddUnit().getAkyn().isEmpty()
                    && strIdHHPPRR.getResponseAddUnit().getMessageText().contains("CAB0075")
                    && !strIdHHPPRR.getResponseAddUnit().getMessageText().contains("CAB0420")) {

                hhpp.setIdRR(strIdHHPPRR.getResponseAddUnit().getAkyn());
                if (razon.contains("CM")) {

                    if (cmtHhppVtMgl != null && cmtHhppVtMgl.getOpcionNivel5().equalsIgnoreCase(nivel)
                            && cmtHhppVtMgl.getValorNivel5() == null) {
                        hhpp.setCalleRR(direccionRREntity.getCalle().toUpperCase());
                        hhpp.setUnidadRR(cmtHhppVtMgl.getUnidadRr());
                        hhpp.setAptoRR(cmtHhppVtMgl.getOpcionNivel5());
                    } else {
                        if (cmtHhppVtMgl.getSubEdificioVtObj() != null && cmtHhppVtMgl.getSubEdificioVtObj().getDireccionSubEdificio() != null) {
                            hhpp.setCalleRR(direccionRREntity.getCalle().toUpperCase());
                        } else {
                            hhpp.setCalleRR(direccionRREntity.getCalle().toUpperCase());
                        }
                        if (cmtHhppVtMgl.getOpcionNivel5().equalsIgnoreCase(co.com.claro.mgl.utils.Constant.OPCION_NIVEL_OTROS)) {
                            hhpp.setAptoRR(cmtHhppVtMgl.getValorNivel5());
                        } else {
                            hhpp.setAptoRR(direccionRREntity.getNumeroApartamento().toUpperCase());
                        }
                        hhpp.setUnidadRR(direccionRREntity.getNumeroUnidad().toUpperCase());

                    }
                } else {
                    hhpp.setIdRR(strIdHHPPRR.getResponseAddUnit().getAkyn());
                    hhpp.setCalleRR(direccionRREntity.getCalle().toUpperCase());
                    hhpp.setUnidadRR(direccionRREntity.getNumeroUnidad().toUpperCase());
                    hhpp.setAptoRR(direccionRREntity.getNumeroApartamento().toUpperCase());
                }
                //guarda hhpp en MGL
                BigDecimal hhppId = hhppManager.saveHhppInRepo(hhpp, nombreFuncionalidad);
                if (hhppId != null) {
                    //se asigna Id del hhpp creado    
                    entityRR.setIdHhpp(hhppId.toString());
                    direccionRREntity.setIdHhpp(entityRR.getIdHhpp());
                    if (razon.contains("CM")) {
                        if (cmtHhppVtMgl != null && cmtHhppVtMgl.getOpcionNivel5().equalsIgnoreCase(nivel)
                                && cmtHhppVtMgl.getValorNivel5() == null) {
                            entityRR.setCalle(direccionRREntity.getCalle().toUpperCase());
                            entityRR.setNumeroUnidad(cmtHhppVtMgl.getUnidadRr());
                            entityRR.setNumeroApartamento(cmtHhppVtMgl.getOpcionNivel5());
                        } else {
                            if (cmtHhppVtMgl != null && cmtHhppVtMgl.getSubEdificioVtObj() != null && cmtHhppVtMgl.getSubEdificioVtObj().getDireccionSubEdificio() != null) {
                                entityRR.setCalle(direccionRREntity.getCalle().toUpperCase());
                            } else {
                                entityRR.setCalle(direccionRREntity.getCalle().toUpperCase());
                            }
                            entityRR.setNumeroUnidad(direccionRREntity.getNumeroUnidad().toUpperCase());
                            entityRR.setNumeroApartamento(direccionRREntity.getNumeroApartamento().toUpperCase());
                        }
                    } else {
                        entityRR.setCalle(direccionRREntity.getCalle().toUpperCase());
                        entityRR.setNumeroUnidad(direccionRREntity.getNumeroUnidad().toUpperCase());
                        entityRR.setNumeroApartamento(direccionRREntity.getNumeroApartamento().toUpperCase());
                    }
                    HhppMgl hhppMgl = new HhppMgl();
                    hhppMgl.setHhpId(hhppId);
                    //se agrega marca de Hhpp sin verificación por default
                    MarcasMglManager marcasManager = new MarcasMglManager();
                    MarcasHhppMglManager marcasHhppMglManager = new MarcasHhppMglManager();
                    MarcasMgl marca = marcasManager.findMarcasMglByCodigo(co.com.claro.mgl.utils.Constant.MARCA_HHPP_SIN_VERIFICACION);
                    if (marca != null) {
                        MarcasHhppMgl marcasHhppMgl = new MarcasHhppMgl();
                        marcasHhppMgl.setHhpp(hhppMgl);
                        marcasHhppMgl.setMarId(marca);
                        marcasHhppMgl.setMhhFechaCreacion(new Date());
                        marcasHhppMgl.setMhhUsuarioCreacion("MGL");
                        marcasHhppMgl.setEstadoRegistro(1);
                        marcasHhppMglManager.create(marcasHhppMgl);
                    }
                }

            } else {
                //se establecen los valores de RR del hhpp que se va a crear
                if (hhpp.getEstadoHhpp() != null
                        && hhpp.getEstadoHhpp().getEhhNombre() != null) {
                    hhpp.setEstado_unit(hhpp.getEstadoHhpp().getEhhNombre().substring(0, 1));
                }

                String tipoEdificio = "";
                if (cmtHhppVtMgl != null) {
                    if (cmtHhppVtMgl.getOpcionNivel5().equalsIgnoreCase(co.com.claro.mgl.utils.Constant.OPCION_NIVEL_OTROS)) {
                        // escenario otros el apartamento viene con el string + el valor rr 
                        // se asigna casa de manera que coincida con la regla
                        String apartamento = "CASA";
                        tipoEdificio = obtenerTipoEdificio(apartamento, carpeta, tipoSol);
                    } else {
                        if (cmtHhppVtMgl.getThhVtId() != null) {
                            tipoEdificio = cmtHhppVtMgl.getThhVtId();
                        } else {
                            tipoEdificio = obtenerTipoEdificio(direccionRREntity.getNumeroApartamento(), carpeta, tipoSol);
                        }
                    }

                }

                hhpp.setTipo_unidad(tipoEdificio);
                if (razon.contains("CM")) {
                    if (cmtHhppVtMgl != null && cmtHhppVtMgl.getOpcionNivel5().equalsIgnoreCase(nivel)
                            && cmtHhppVtMgl.getValorNivel5() == null) {
                        hhpp.setCalleRR(direccionRREntity.getCalle().toUpperCase());
                        hhpp.setUnidadRR(direccionRREntity.getNumeroUnidad().toUpperCase());
                        hhpp.setAptoRR("CASA");
                    } else {
                        if (cmtHhppVtMgl != null && cmtHhppVtMgl.getSubEdificioVtObj() != null
                                && cmtHhppVtMgl.getSubEdificioVtObj().getDireccionSubEdificio() != null) {
                            hhpp.setCalleRR(direccionRREntity.getCalle().toUpperCase());
                        } else {
                            hhpp.setCalleRR(direccionRREntity.getCalle().toUpperCase());
                        }
                        if (cmtHhppVtMgl != null
                                && cmtHhppVtMgl.getOpcionNivel5().equalsIgnoreCase(co.com.claro.mgl.utils.Constant.OPCION_NIVEL_OTROS)) {
                            hhpp.setAptoRR(cmtHhppVtMgl.getValorNivel5());
                        } else {
                            hhpp.setAptoRR(direccionRREntity.getNumeroApartamento().toUpperCase());
                        }
                        hhpp.setUnidadRR(direccionRREntity.getNumeroUnidad().toUpperCase());
                    }
                } else {
                    if (strIdHHPPRR != null && strIdHHPPRR.getResponseAddUnit() != null && strIdHHPPRR.getResponseAddUnit() != null
                            && strIdHHPPRR.getResponseAddUnit().getAkyn() != null) {
                        hhpp.setIdRR(strIdHHPPRR.getResponseAddUnit().getAkyn());
                    }
                    hhpp.setCalleRR(direccionRREntity.getCalle().toUpperCase());
                    hhpp.setUnidadRR(direccionRREntity.getNumeroUnidad().toUpperCase());
                    hhpp.setAptoRR(direccionRREntity.getNumeroApartamento().toUpperCase());
                }

                hhpp.setComunidadRR(comunidad);
                hhpp.setDivisionRR(division);
                hhpp.setVendedor("9999");
                CityEntity cityEntity = new CityEntity();
                CmtComunidadRr cmtComunidadRr = comunidadRrManager.findComunidadByComunidad(comunidad);
                cityEntity.setPostalCode(cmtComunidadRr.getCodigoPostal() != null ? cmtComunidadRr.getCodigoPostal() : null);
                if (cityEntity.getPostalCode() != null) {
                    hhpp.setCodigo_postal(cityEntity.getPostalCode());
                } else {
                    hhpp.setCodigo_postal("000");
                }
                if (carpeta.equals(VERIFICACION_CASAS)) {
                    hhpp.setTipo_acomet("A");//Tipo Acometida
                    hhpp.setTipo_cbl_acometida("11");//Tipo Cable Acometida
                } else if (carpeta.equals(UNIDIRECCIONAL)
                        || carpeta.equals(CUENTA_MATRIZ)) {
                    hhpp.setTipo_acomet("C");//Tipo Acometida
                    hhpp.setTipo_cbl_acometida("R6");//Tipo Cable Acometida
                }
                hhpp.setUlt_ubicacion(hhpp.getNodo() != null ? hhpp.getNodo().getNodCodigo() : null);
                hhpp.setHead_end(true == (nodoMgl.getNodHeadEnd() != null) ? nodoMgl.getNodHeadEnd() : null);
                hhpp.setTipo("ND");
                String buildingName = direccionEntity.getBarrio() == null
                        || direccionEntity.getBarrio().trim().isEmpty()
                        || direccionEntity.getBarrio().trim().equalsIgnoreCase("_") ? "NG" : direccionEntity.getBarrio();
                if (buildingName != null && buildingName.length() > 25) {
                    buildingName = buildingName.substring(0, 25);
                }
                hhpp.setEdificio(buildingName != null ? buildingName.trim() : null);

                //Si RR se encuentra encendido y la tecnologia no sincroniza con RR         
                BigDecimal hhppId = hhppManager.saveHhppInRepo(hhpp, nombreFuncionalidad);
                if (hhppId != null) {
                    //se asigna Id del hhpp creado    
                    entityRR.setIdHhpp(hhppId.toString());
                    direccionRREntity.setIdHhpp(entityRR.getIdHhpp());
                    if (cmtHhppVtMgl != null && cmtHhppVtMgl.getSubEdificioVtObj() != null
                            && cmtHhppVtMgl.getSubEdificioVtObj().getDireccionSubEdificio() != null) {
                        entityRR.setCalle(direccionRREntity.getCalle().toUpperCase());
                    } else {
                        entityRR.setCalle(direccionRREntity.getCalle().toUpperCase());
                    }
                    entityRR.setNumeroUnidad(direccionRREntity.getNumeroUnidad().toUpperCase());
                    entityRR.setNumeroApartamento(direccionRREntity.getNumeroApartamento().toUpperCase());
                    HhppMgl hhppMgl = new HhppMgl();
                    hhppMgl.setHhpId(hhppId);
                    //se agrega marca de Hhpp sin verificación por default
                    MarcasMglManager marcasManager = new MarcasMglManager();
                    MarcasHhppMglManager marcasHhppMglManager = new MarcasHhppMglManager();
                    MarcasMgl marca = marcasManager.findMarcasMglByCodigo(co.com.claro.mgl.utils.Constant.MARCA_HHPP_SIN_VERIFICACION);
                    if (marca != null) {
                        MarcasHhppMgl marcasHhppMgl = new MarcasHhppMgl();
                        marcasHhppMgl.setHhpp(hhppMgl);
                        marcasHhppMgl.setMarId(marca);
                        marcasHhppMgl.setMhhFechaCreacion(new Date());
                        marcasHhppMgl.setMhhUsuarioCreacion("MGL");
                        marcasHhppMgl.setEstadoRegistro(1);
                        marcasHhppMglManager.create(marcasHhppMgl);
                    }
                }

            }

            return entityRR;
        } catch (ApplicationException | ExceptionDB ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de registrar el HHPP de propia dirección. EX000: " + ex.getMessage(), ex);
        }
    }

    public DireccionRREntity getDireccion() {
        return direccionRREntity;
    }

    public void setDireccion(DireccionRREntity direccion) {
        this.direccionRREntity = direccion;
    }

    private EstadoHhpp obtenerEstadoHHPP(String nodo, String carpeta) throws ExceptionDB, ApplicationException {

        CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
        String exist = cmtBasicaMglManager.findValorExtendidoEstHhpp(estadoHhppExiste);

        return hhppManager.queryEstadoHhpp(exist); //EXISTS

    }

    private boolean crearStreetHHPPRR(String comunidad, String division) throws ApplicationException {
        try {
            RequestCRUDUnit requestCRUDUnit = new RequestCRUDUnit();
            requestCRUDUnit.setComunity(comunidad.toUpperCase());
            requestCRUDUnit.setDivision(division.toUpperCase());
            requestCRUDUnit.setStreetName(direccionRREntity.getCalle().toUpperCase());
            requestCRUDUnit.setMode("CREATE");
            PortManager portManager = new PortManager(wsURL, wsService);
            CRUDUnitManagerResponse retorno;

            try {
                retorno = portManager.addStreet(requestCRUDUnit);
            } catch (ApplicationException e) {
                LOGGER.error("Error al intentar crear la Calle, se consume nuevamente el servicio " + e.getMessage());
                retorno = portManager.addStreet(requestCRUDUnit);
            }

            if (retorno.getResponseCRUDUnit().getMessageText().trim().toUpperCase().contains("(CAB0075)")
                    || retorno.getResponseCRUDUnit().getMessageText().trim().toUpperCase().contains("(CAB0218)")) {
                return true;
            } else {
                throw new ApplicationException("Calle no existe, se presento error al crearla :"
                        + direccionRREntity.getCalle() + " ,error RR:" + retorno.getResponseCRUDUnit().getMessageText());
            }
        } catch (ApplicationException ex) {
            LOGGER.error("error al  crear street " + ex.getMessage());
            throw ex;
        }
    }

    public boolean crearStreetHHPPRR(String comunidad, String division, String calle) throws ApplicationException {
        try {
            RequestCRUDUnit requestCRUDUnit = new RequestCRUDUnit();
            requestCRUDUnit.setComunity(comunidad);
            requestCRUDUnit.setDivision(division);
            String calleRR = calle.toUpperCase().replace("Ñ", "N");
            requestCRUDUnit.setStreetName(calleRR);
            requestCRUDUnit.setMode("CREATE");
            PortManager portManager = new PortManager(wsURL, wsService);
            CRUDUnitManagerResponse retorno;
            try {
                retorno = portManager.addStreet(requestCRUDUnit);
            } catch (ApplicationException e) {
                LOGGER.error("Error al intentar crear la Calle, se consume nuevamente el servicio " + e.getMessage());
                retorno = portManager.addStreet(requestCRUDUnit);
            }

            if (retorno.getResponseCRUDUnit().getMessageText().trim().toUpperCase().contains("(CAB0075)")
                    || retorno.getResponseCRUDUnit().getMessageText().trim().toUpperCase().contains("(CAB0218)")) {
                return true;
            } else {
                throw new ApplicationException("Se presento error la Calle en RR :"
                        + calle + " ,error RR:" + retorno.getResponseCRUDUnit().getMessageText());
            }
        } catch (ApplicationException ex) {
            LOGGER.error("error al crear street " + ex.getMessage());
            throw ex;
        }
    }

    public List<PreFichaHHPPDetalleMgl> actualizarHHPPFicha(List<PreFichaHHPPDetalleMgl> hhppDetalleList) {
        try {
            for (PreFichaHHPPDetalleMgl detalleHHPPMgl : hhppDetalleList) {
                //si se debe actualizar el estrato de la unidad
                if (detalleHHPPMgl.isModifiedEstrato()) {
                    DireccionRREntity direccionRREntityCES = new DireccionRREntity(detalleHHPPMgl.getStreetName(),
                            detalleHHPPMgl.getHouseNumber(), detalleHHPPMgl.getApartmentNumber());
                    direccionRREntityCES.setComunidad(detalleHHPPMgl.getCummunity());
                    direccionRREntityCES.setDivision(detalleHHPPMgl.getDivision());
                    cambioEstratoRR(detalleHHPPMgl.getCummunity(), detalleHHPPMgl.getDivision(),
                            detalleHHPPMgl.getFichaGeorreferenciaMgl().getLevelEconomic(), direccionRREntityCES);
                }
                if (detalleHHPPMgl.isModifiedInfoCatastral()) {
                    String f18 = detalleHHPPMgl.getStreetName() + " " + detalleHHPPMgl.getHouseNumber();
                    agregarNotaF18(detalleHHPPMgl.getCummunity(), detalleHHPPMgl.getDivision(), f18,
                            detalleHHPPMgl.getStreetName(),
                            detalleHHPPMgl.getHouseNumber(),
                            detalleHHPPMgl.getApartmentNumber(), detalleHHPPMgl.getFichaGeorreferenciaMgl());
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al momento de actualizar el HHPP de la Ficha. EX000: " + e.getMessage(), e);
        }
        return hhppDetalleList;
    }

    public List<PreFichaHHPPDetalleMgl> createHHPPinRR(List<PreFichaHHPPDetalleMgl> hhppDetalleList, NodoMgl nodo) {

        HhppMglManager hhppMglManager = new HhppMglManager();

        try {
            for (PreFichaHHPPDetalleMgl detalleHHPPMgl : hhppDetalleList) {
                //creacion request para el servicio de rr
                AddUnitRequest unitRequest = new AddUnitRequest();
                unitRequest.setStreetName(detalleHHPPMgl.getStreetName());
                unitRequest.setHouseNumber(detalleHHPPMgl.getHouseNumber());
                unitRequest.setApartmentNumber(detalleHHPPMgl.getApartmentNumber());
                unitRequest.setCommunity(detalleHHPPMgl.getCummunity());
                unitRequest.setDivision(detalleHHPPMgl.getDivision());
                unitRequest.setBuildingName(detalleHHPPMgl.getBuildingName());
                unitRequest.setDealer(detalleHHPPMgl.getDealer());
                unitRequest.setDropType(detalleHHPPMgl.getDropType());
                unitRequest.setDropTypeCable(detalleHHPPMgl.getDropTypeCable());
                unitRequest.setGridPosition(detalleHHPPMgl.getGridPosition());
                unitRequest.setHeadEnd(detalleHHPPMgl.getHeadEnd());
                unitRequest.setPlantLocType(detalleHHPPMgl.getPlantLocType());
                unitRequest.setPlantLocation(detalleHHPPMgl.getPlantLocation());
                unitRequest.setPostalCode(detalleHHPPMgl.getPostalCode());
                unitRequest.setProblemUnitCodes(detalleHHPPMgl.getProblemUnitCodes());
                unitRequest.setStratus(detalleHHPPMgl.getStratus());
                unitRequest.setUnitStatus(detalleHHPPMgl.getUnitStatus());
                unitRequest.setUnitType(detalleHHPPMgl.getUnitType());
                unitRequest.setTypeRequest("ADDUNIT");

                //asignacion valores instancia HHPP para crearlo en el repositorio
                ConsultaEspecificaManager manager = new ConsultaEspecificaManager();
                String idDirCatastro = detalleHHPPMgl.getFichaGeorreferenciaMgl().getIdAddress();
                String indicador = idDirCatastro.substring(0, 1);
                Direccion direccionDetalle = null;
                SubDireccion subDireccionDetalle = null;
                if (indicador.equalsIgnoreCase("d")) {
                    direccionDetalle = manager.queryAddressOnRepoById(idDirCatastro.substring(1));
                } else if (indicador.equalsIgnoreCase("s")) {
                    subDireccionDetalle = manager.querySubAddressOnRepositoryById(new BigDecimal(idDirCatastro.substring(1)));
                    direccionDetalle = manager.queryAddressOnRepoById(subDireccionDetalle.getDireccion().getDirId().toString());
                }

                HhppMgl hhpp = new HhppMgl();
                hhpp.setDirId(direccionDetalle != null ? direccionDetalle.getDirId() : null);
                if (subDireccionDetalle != null) {
                    hhpp.setSdiId(subDireccionDetalle.getSdiId());
                }
                EstadoHhppMgl unitStatusEstadoHhppMgl = new EstadoHhppMgl();
                unitStatusEstadoHhppMgl.setEhhID(detalleHHPPMgl.getUnitStatus());
                hhpp.setEhhId(unitStatusEstadoHhppMgl);
                hhpp.setNodId(nodo);
                hhpp.setThhId(detalleHHPPMgl.getUnitType());
                hhpp.setThcId(BigDecimal.ONE);
                hhpp.setThrId(new BigDecimal("2"));

                hhpp.setHhpCalle(detalleHHPPMgl.getStreetName());
                hhpp.setHhpPlaca(detalleHHPPMgl.getHouseNumber());
                hhpp.setHhpApart(detalleHHPPMgl.getApartmentNumber());
                hhpp.setHhpComunidad(detalleHHPPMgl.getCummunity());
                hhpp.setHhpDivision(detalleHHPPMgl.getDivision());
                hhpp.setHhpEdificio(detalleHHPPMgl.getBuildingName());
                hhpp.setHhpVendedor(detalleHHPPMgl.getDealer());
                hhpp.setHhpTipoAcomet(detalleHHPPMgl.getDropType());
                hhpp.setHhpTipoCblAcometida(detalleHHPPMgl.getDropTypeCable());
                hhpp.setHhpHeadEnd(detalleHHPPMgl.getHeadEnd());
                hhpp.setHhpTipo(detalleHHPPMgl.getPlantLocType());
                hhpp.setHhpUltUbicacion(detalleHHPPMgl.getPlantLocation());
                hhpp.setHhpCodigoPostal(detalleHHPPMgl.getPostalCode());
                hhpp.setHhpEstadoUnit(detalleHHPPMgl.getUnitStatus());
                hhpp.setHhpTipoUnidad(detalleHHPPMgl.getUnitType());

                //Creacion calle en rr
                if (crearStreetHHPPRR(detalleHHPPMgl.getCummunity(),
                        detalleHHPPMgl.getDivision(),
                        detalleHHPPMgl.getStreetName())) {
                    //creacion cruce en rr
                    if (crearCruceRR(detalleHHPPMgl.getCummunity(),
                            detalleHHPPMgl.getDivision(),
                            detalleHHPPMgl.getStreetName(),
                            detalleHHPPMgl.getHouseNumber(),
                            detalleHHPPMgl.getTipoDireccion())) {

                        //creacion HHPP en RR
                        PortManager portManager = new PortManager(wsURL, wsService);
                        UnitManagerResponse respuesta = portManager.addUnit(unitRequest);
                        String obsv;
                        if (respuesta.getResponseAddUnit().getMessageText().contains("CAB0075")
                                && respuesta.getResponseAddUnit().getAkyn() != null
                                && !respuesta.getResponseAddUnit().getAkyn().trim().isEmpty()) {
                            //si el HHPP ha sido creado se retorna el valor del id de RR
                            detalleHHPPMgl.setCreateInRR("1");
                            hhpp.setHhpIdrR(respuesta.getResponseAddUnit().getAkyn());
                            obsv = "Respuesta RR: " + respuesta.getResponseAddUnit().getMessageText();

                            //Creacion HHPP en repositorio
                            hhppMglManager.create(hhpp);
                            detalleHHPPMgl.setCreateInRepo("1");
                            obsv += " - Respuesta Repositorio: HHPP creado ";

                            String nota = "NODO REAL: " + nodo.getNodCodigo() + "; SOLICITUD: PEN"
                                    + "; SOLICITANTE: DISE#O " + "; CANAL: DISE#O"
                                    + "; OBSERVACIONES: SE CREA HHPP POR FICHA DE NODO " + nodo.getNodCodigo();
                            //creacion de las NOTAS F8 al HHPP creado
                            if (agregarNotasF8(detalleHHPPMgl.getCummunity(),
                                    detalleHHPPMgl.getDivision(),
                                    nota,
                                    detalleHHPPMgl.getStreetName(),
                                    detalleHHPPMgl.getHouseNumber(),
                                    detalleHHPPMgl.getApartmentNumber(),
                                    "0", "HITSS")) {

                                obsv += " - Notas F8 agregadas al HHPP ";
                                DireccionRREntity direccionRREntityToF18 = new DireccionRREntity();
                                direccionRREntityToF18.setCalle(detalleHHPPMgl.getStreetName());
                                direccionRREntityToF18.setNumeroUnidad(detalleHHPPMgl.getHouseNumber());
                                direccionRREntityToF18.setNumeroApartamento(detalleHHPPMgl.getApartmentNumber());
                                direccionRREntityToF18.setField1F18(detalleHHPPMgl.getStreetName() + " " + detalleHHPPMgl.getHouseNumber());
                                //TODO: para el nuevo desarrollo de fichas enviar 
                                //el campo Uno para notas adicionales adecuadamente
                                //Revisar nodoReal para enviar a notas F18
                                if (agregarNotaF18(detalleHHPPMgl.getCummunity(),
                                        detalleHHPPMgl.getDivision(),
                                        direccionRREntityToF18,
                                        direccionDetalle, "", detalleHHPPMgl.getPlantLocation())) {
                                    obsv += " - Informacion catastral agregada al HHPP ";
                                }
                            }
                        } else {
                            obsv = "Respuesta RR: " + respuesta.getResponseAddUnit().getMessageText();
                        }
                        detalleHHPPMgl.setObservaciones(obsv);
                    }
                }
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error al momento de crear el HHPP en RR. EX000: " + e.getMessage(), e);
        }
        return hhppDetalleList;

    }
//crea hhpp
    static final String RESULTADO_HHPP_CREADO = "HHPP CREADO";
    static final String RESULTADO_VERIFICACION_AGENDA = "VERIFICACION AGENDADA";

    private UnitManagerResponse registrarHHPPRR(Hhpp hhpp, String nvlSocioEconomico, String aplicacion,
            String comunidad, String division, String tipoSol,
            String aptoRR, String calleRR, String unidadRR, String razon,String nap) throws ApplicationException, ApplicationException {

        NodoMglManager nodoMglManager = new NodoMglManager();
        CmtComunidadRrManager comunidadRrManager = new CmtComunidadRrManager();
        try {
            //escribir en log tiempo de transaccion para registrar HHPP en RR
            LOGGER.info("Ingresa al metodo registrarHHPPRR");
            TransactionParameters ini = TransactionParameters.iniciarTransaccion();
            
            long timeIni = 0;
            long timeFin = 0;
            int dataSize = 1024 * 1024;

            Runtime runtime = Runtime.getRuntime();
            timeIni = System.currentTimeMillis();
    
            AddUnitRequest unitRequest = new AddUnitRequest();
            //estado
            String estadoHhpp = hhpp.getEstadoHhpp().getEhhNombre().substring(0, 1);
            unitRequest.setUnitStatus(estadoHhpp);
            hhpp.setEstado_unit(estadoHhpp);
            // NAP
            // si se igreso alguna nap se introduce al llamado del servicio de RR
            if (nap != null) {
                unitRequest.setProject(nap);
            }else{
                unitRequest.setProject(hhpp.getAmp() != null ? hhpp.getAmp() : new String());
            }
            // JDHT
            // tipo de vivienda desde la gestion
            String tipoEdificio;
            if (hhpp.getTipoHhpp() != null
                    && !hhpp.getTipoHhpp().getThhId().isEmpty()) {
                tipoEdificio = hhpp.getTipoHhpp().getThhId();
            } else {
                tipoEdificio = obtenerTipoEdificio(aptoRR, aplicacion, tipoSol);
            }
            hhpp.setTipo_unidad(tipoEdificio);

            //Estrato
            String estrato = "";

            if (nvlSocioEconomico != null) {
                estrato = obtenerEstrato(nvlSocioEconomico, tipoSol, tipoEdificio);
            }

            unitRequest.setStratus(estrato);
            if (aplicacion.equals(VERIFICACION_CASAS)) {
                unitRequest.setDropType("A");//Tipo Acometida
                unitRequest.setDropTypeCable("11");//Tipo Cable Acometida
                hhpp.setTipo_acomet("A");//Tipo Acometida
                hhpp.setTipo_cbl_acometida("11");//Tipo Cable Acometida
            } else if (aplicacion.equals(UNIDIRECCIONAL)
                    || aplicacion.equals(CUENTA_MATRIZ)) {
                unitRequest.setDropType("C");//Tipo Acometida   
                unitRequest.setDropTypeCable("R6");//Tipo Cable Acometida
                hhpp.setTipo_acomet("C");//Tipo Acometida
                hhpp.setTipo_cbl_acometida("R6");//Tipo Cable Acometida
            }

            unitRequest.setGridPosition(comunidad);

            NodoMgl nodoMgl = nodoMglManager.findByCodigoAndCity(hhpp.getNodo().getNodCodigo(), hhpp.getNodo().getGeograficoPolitico().getGpoId());

            if (nodoMgl.getNodId() != null) {
                unitRequest.setPostalCode(nodoMgl.getComId().getCodigoPostal());
                hhpp.setCodigo_postal(nodoMgl.getComId().getCodigoPostal());
            } else {
                unitRequest.setPostalCode("000");
                hhpp.setCodigo_postal("000");
            }

            //TODO: obtener Headend
            String dirHeadEnd = nodoMgl.getNodHeadEnd();

            unitRequest.setHeadEnd(dirHeadEnd);
            hhpp.setHead_end(dirHeadEnd);
            //Tipo nodo Siempre es ND
            unitRequest.setPlantLocType("ND");
            hhpp.setTipo("ND");

            //Se asigna el nodo
            unitRequest.setPlantLocation(hhpp.getNodo().getNodCodigo());//TODO: obtener location
            hhpp.setUlt_ubicacion(hhpp.getNodo().getNodCodigo());
            CmtComunidadRr cmtComunidadRr = comunidadRrManager.findComunidadByComunidad(comunidad);
            CityEntity cityEntity = new CityEntity();
            cityEntity.setPostalCode(cmtComunidadRr.getCodigoPostal());
            if (cityEntity.getPostalCode() != null) {
                unitRequest.setPostalCode(cityEntity.getPostalCode());
                hhpp.setCodigo_postal(cityEntity.getPostalCode());
            } else {
                unitRequest.setPostalCode("000");
                hhpp.setCodigo_postal("000");
            }

            unitRequest.setCommunity(comunidad);
            unitRequest.setDivision(division);
            hhpp.setComunidadRR(comunidad);
            hhpp.setDivisionRR(division);

            unitRequest.setDealer("9999");
            hhpp.setVendedor("9999");

            Date toDay = new Date();
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            unitRequest.setAuditCompletedDate(df.format(toDay));
            unitRequest.setApartmentNumber(aptoRR);
            if (calleRR != null && !calleRR.isEmpty()) {
                String calle = calleRR.toUpperCase().replace("Ñ", "N");
                unitRequest.setStreetName(calle);
            } else {
                unitRequest.setStreetName(calleRR);
            }

            unitRequest.setHouseNumber(unidadRR);
            hhpp.setCalleRR(calleRR);
            hhpp.setUnidadRR(unidadRR);
            hhpp.setAptoRR(aptoRR);

            unitRequest.setUnitType(tipoEdificio);

            String buildingName = direccionEntity.getBarrio() == null
                    || direccionEntity.getBarrio().trim().isEmpty()
                    || direccionEntity.getBarrio().trim().equalsIgnoreCase("_") ? "NG" : direccionEntity.getBarrio();
            if (buildingName != null && buildingName.length() > 25) {
                buildingName = buildingName.substring(0, 25);
            }
            unitRequest.setBuildingName(buildingName != null ? buildingName.trim() : "");
            hhpp.setEdificio(buildingName != null ? buildingName.trim() : "");

            unitRequest.setTypeRequest("ADDUNIT");

            if (RESULTADO_VERIFICACION_AGENDA.equalsIgnoreCase(razon)) {
                unitRequest.setProblemUnitCodes("HSO");

            } else if (RESULTADO_HHPP_CREADO.equalsIgnoreCase(razon)) {
                unitRequest.setProblemUnitCodes("HSV");
            }
            PortManager portManager = new PortManager(wsURL, wsService);
            UnitManagerResponse respuesta;

            try {
                respuesta = portManager.addUnit(unitRequest);
                timeFin = System.currentTimeMillis() - timeIni;
                LOGGER.info("Tiempo de procesamiento Transaccion: " +ini.getUuid().toString()+ " - " + timeFin + " ms");
                LOGGER.info("Memoria total: " + runtime.totalMemory() / dataSize + "MB " + 
                        "Memoria libre: " + runtime.freeMemory() / dataSize + "MB " +  
                        "Memoria usada: " + (runtime.totalMemory() - runtime.freeMemory()) / dataSize + "MB");
            } catch (ApplicationException e) {
                respuesta = portManager.addUnit(unitRequest);
            }

            if (respuesta.getResponseAddUnit().getMessageText().contains("CAB0075")
                    && respuesta.getResponseAddUnit().getAkyn() != null
                    && !respuesta.getResponseAddUnit().getAkyn().trim().isEmpty()) {
                //si el HHPP ha sido creado se retorna el valor del id de RR
                return respuesta;
            } else if (respuesta.getResponseAddUnit().getMessageText().contains("CAB0420")
                    || respuesta.getResponseAddUnit().getMessageText().contains("CAB1006")) {
                if (respuesta.getResponseAddUnit().getMessageType().contains("E")) {

                    return respuesta;

                } else {
                    return respuesta;
                }
            } else {
                return respuesta;
            }

        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de registrar el HHPP en RR. EX000: " + e.getMessage(), e);
        }
    }

    /**
     * Crea una llamada de Servicio. Permite crear una llamada de servicio sobre
     * RR para un HHPP.
     *
     * @author Johnnatan Ortiz
     * @param division comunidad del HHPP.
     * @param comunidad comunidad del HHPP.
     * @param street calle en formato RR del HHPP.
     * @param house numero de unidad en formato RR del HHPP.
     * @param apto numero de apartamento en formato RR del HHPP.
     * @return Si la llamada de servicio fue creada o no.
     */
    private boolean crearServiceCall(String division, String comunidad,
            String street, String house, String apto, String usuario,
            String numeroContacto, String nota) throws ApplicationException {
        boolean result = false;
        try {

            String bookingTimeCodeL = "VT";
            String reasonforServiceL = "VR";
            String workForceCodeL = "A";
            String priorityL = "1";
            String nyDefaultL = "Y";
            String bookingSeqL = "0";
            String requestServiceTimeL = "0800";

            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            Date dateNow = new Date();
            Calendar calendarRequest = Calendar.getInstance();
            calendarRequest.add(Calendar.DAY_OF_YEAR, 1);
            Date dateRequest = new Date(calendarRequest.getTimeInMillis());
            CreateServiceCallRequest request = new CreateServiceCallRequest();

            request.setDivision(division);
            request.setCommunity(comunidad);
            request.setStreetName(street);
            request.setHouseNumber(house);
            request.setApartmentNumber(apto);

            request.setDateofCall(format.format(dateNow));
            request.setBookingDate(format.format(dateRequest));
            request.setRequestServiceDate(format.format(dateRequest));
            request.setRequestServiceTimeHhmm(requestServiceTimeL);
            request.setBookingTimeCode(bookingTimeCodeL);
            request.setBookingSEQ(bookingSeqL);
            request.setReasonforService(reasonforServiceL);
            request.setWorkForceCode(workForceCodeL);
            request.setProblemDescription(nota);
            request.setPriority(priorityL);
            request.setN_Y_Default(nyDefaultL);
            request.setContactPhoneNumber(numeroContacto);
            request.setCalledInName(usuario);

            PortManager portManager = new PortManager(wsURL, wsService);

            CreateServiceCallResponse response = portManager.createServiceCall(request);
            if (response != null
                    && response.getResponseCreateServiceCall() != null
                    && response.getResponseCreateServiceCall().
                            getCallServiceId() != null
                    && !response.getResponseCreateServiceCall().
                            getCallServiceId().isEmpty()) {
                result = true;
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Ocurrio un error creando la llamda de servicio "
                    + e.getMessage());
        }
        return result;
    }

    /**
     * Crea una llamada de Servicio. Permite crear una llamada de servicio sobre
     * RR para un HHPP.
     *
     * @author Johnnatan Ortiz
     * @param idUnidad id del HHPP.
     * @param usuario usuario que esta gestionando la solicitud y con el cual se
     * creara la llamada de servicio.
     * @param numeroContacto numero telefonico de contacto.
     * @param nota nota para adicinar a la llamada de servicio.
     * @return Si la llamada de servicio fue creada o no.
     */
    private String crearServiceCall(String idUnidad, String usuario,
            String numeroContacto, String nota) throws ApplicationException {
        String idServiceCall = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            Date dateNow = new Date();
            Calendar calendarRequest = Calendar.getInstance();
            int daysToAdd = 1;
            //verificamos si la llamada de servicio de esta creando un dia Sabado
            //de ser asi se debe agendar para el dia Lunes
            if (calendarRequest.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                daysToAdd += 1;
            }
            calendarRequest.add(Calendar.DAY_OF_YEAR, daysToAdd);
            Date dateRequest = new Date(calendarRequest.getTimeInMillis());
            CreateServiceCallRequest request = new CreateServiceCallRequest();

            request.setUnitNumber(idUnidad);

            request.setDateofCall(format.format(dateNow));
            request.setBookingDate(format.format(dateRequest));
            request.setRequestServiceDate(format.format(dateRequest));
            request.setRequestServiceTimeHhmm(requestServiceTime);
            request.setBookingTimeCode(bookingTimeCode);
            request.setBookingSEQ(bookingSeq);
            request.setReasonforService(reasonforService);
            request.setWorkForceCode(workForceCode);
            request.setProblemDescription(nota);
            request.setPriority(priority);
            request.setN_Y_Default(nyDefault);
            request.setTruckRequiredFlag(truckRequiredFlag);

            request.setContactPhoneNumber(numeroContacto);
            request.setCalledInName(usuario);
            PortManager portManager = new PortManager(wsURL, wsService);

            CreateServiceCallResponse response = portManager.createServiceCall(request);
            if (response != null
                    && response.getResponseCreateServiceCall() != null
                    && response.getResponseCreateServiceCall().
                            getCallServiceId() != null
                    && !response.getResponseCreateServiceCall().
                            getCallServiceId().isEmpty()) {
                idServiceCall = response.getResponseCreateServiceCall().
                        getCallServiceId();
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("EX009-Ocurrio un error creando la llamda de servicio "
                    + e.getMessage());
        }
        return idServiceCall;
    }

    /**
     * Agenda una llamada de Servicio. Permite Agendar una llamada de servicio
     * sobre RR para un HHPP.
     *
     * @author Johnnatan Ortiz
     * @param idServiceCall id de la Llamada de Servicio.
     * @param idUsuario id del usuario que esta gestionando la solicitud y con
     * el cual se creara la agenda de la llamada de servicio.
     * @return La informacion de la agenda de la llamada de servicio.
     */
    private RetornoAgenda crearAgendamiento(String idServiceCall, String idUsuario,
            String nodoHhpp, String nodoReal,
            String houseNumber, String streetName, String apartmentNumber,
            String comunidad, String division) throws ApplicationException {
        boolean isCambioNodo = false;
        try {
            //Verificamos si el HHPP esta creado en un nodo NFI, para realizar
            //el cambio al nodo Real para realizar el agendamiento
            if (nodoHhpp.contains(NFI)) {
                isCambioNodo = true;
                cambioNodoHHPP(houseNumber, streetName, apartmentNumber,
                        comunidad, division, nodoReal);
            }

            RetornoAgenda agendaServiceCall = new RetornoAgenda();
            AgendaPortTypeProxy agendaPortTypeProxy = new AgendaPortTypeProxy(wsAgendamientoURL);
            AgendaPortType agendaPortType = agendaPortTypeProxy.getAgendaPortType();
            //Consultamos el Tipo de Trabajo, debe ser 61-VISITAS TECNICAS CASAS
            TipoTrabajoConError tipoTrabajoList = null;
            try {
                tipoTrabajoList = agendaPortType.TTAsignado(idServiceCall, idProgramacionAgenda);
            } catch (RemoteException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg);
                throw new ApplicationException("Ocurio un Error al obtener los Tipos de Trabajo Diponibles");
            }

            TipoTrabajo tipoTrabajo = null;
            if (tipoTrabajoList != null
                    && tipoTrabajoList.getTiposTrabajo() != null
                    && tipoTrabajoList.getTiposTrabajo().length > 0) {
                for (TipoTrabajo tr : tipoTrabajoList.getTiposTrabajo()) {
                    LOGGER.info("Tipo Trabajo: " + tr.getTipoTrabajoId()
                            + "-" + tr.getTipoTrabajoNombre());
                    if (tr.getTipoTrabajoId().trim().equalsIgnoreCase(idTipoTrabajoAgenda)) {
                        tipoTrabajo = tr;
                        break;
                    }
                }
            } else {
                LOGGER.error("No Existen Tipos de Trabajo Disponibles");
                throw new ApplicationException("No Existen Tipos de Trabajo Diponibles");
            }

            Aliado aliado;
            boolean isAgendado = false;
            //consultamos los cupos de los aliados para el tipo de trabajo que 
            //consultamos para agendar la llamada de servicio
            if (tipoTrabajo != null
                    && !tipoTrabajo.getTipoTrabajoId().trim().isEmpty()) {
                AliadoConError aliadoCuposList = agendaPortType.aliadoCupos(idServiceCall,
                        idProgramacionAgenda,
                        tipoTrabajo.getTipoTrabajoId());
                //Verificamos que existan aliados para realizar el trabajo
                if (aliadoCuposList != null
                        && aliadoCuposList.getAliados() != null
                        && aliadoCuposList.getAliados().length > 0) {
                    //Recorremos los aliados en busca de cupos para realizar 
                    //la agenda de la llamada de servicio
                    int indexAliado = 0;
                    while (!isAgendado && indexAliado < aliadoCuposList.getAliados().length) {
                        aliado = aliadoCuposList.getAliados()[indexAliado++];
                        LOGGER.info("Revisando Aliado: " + aliado.getAliadoId() + "-" + aliado.getAliadoNombre());
                        //Verificamos que el aliado tenga cupos
                        if (aliado.getCuposAliados() != null
                                && aliado.getCuposAliados().length > 0) {
                            //recorremos los cupos del aliado para buscar disponibilidad para poder agendar
                            for (Cupo c : aliado.getCuposAliados()) {
                                LOGGER.info("Revisando cupo: " + c.getFecha() + "-" + c.getRangoHora()
                                        + " del Aliado " + aliado.getAliadoId());
                                //Verificamos que existan cupos disponibles para realizar la agenda
                                if (c.getCuposDisponibles() != null) {
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                                    Date fechaCupo = dateFormat.parse(c.getFecha());
                                    Date toDay = new Date();
                                    //Verificamos que la fecha del cupo sea superior a la fecha de hoy
                                    // y que existan cupos
                                    if (fechaCupo.compareTo(toDay) > 0
                                            && !c.getCuposDisponibles().trim().isEmpty()
                                            && Integer.parseInt(c.getCuposDisponibles().trim()) > 0) {
                                        //si existen cupos disponibles realizamos 
                                        //la agenda de la llamada de servicio
                                        AgendarConError agendaResult = agendaPortType.agendar(idServiceCall, idProgramacionAgenda,
                                                tipoTrabajo.getTipoTrabajoId(),
                                                c.getIdFecha(), c.getIdRangoHora(), idUsuario, aliado.getAliadoId(), "");
                                        //Verificamos que la agenda se haya realizado exitosamente
                                        //no debe arrojar error y debe tener un id de aliado
                                        if (agendaResult != null
                                                && !agendaResult.getErrorNo().trim().isEmpty()
                                                && agendaResult.getErrorNo().trim().equalsIgnoreCase("0")
                                                && agendaResult.getAgendar() != null
                                                && agendaResult.getAgendar().getAliadoId() != null) {
                                            LOGGER.info("Agenda Realizada Exitosamente");
                                            agendaServiceCall = agendaResult.getAgendar();
                                            String fechaAgenda = agendaResult.getAgendar().getDiaAgenda()
                                                    + " " + c.getRangoHora();
                                            agendaServiceCall.setDiaAgenda(fechaAgenda);
                                            agendaServiceCall.setAgendado(true);
                                        } else {
                                            LOGGER.error("Error al intentar Agendar: " + agendaResult.getErrorDesc());
                                            agendaServiceCall.setAgendado(false);
                                            agendaServiceCall.setIdOrdenTrabajo(idServiceCall);
                                            if (agendaResult != null
                                                    && !agendaResult.getErrorDesc().trim().isEmpty()) {
                                                agendaServiceCall.setMsnError(agendaResult.getErrorDesc().trim());
                                            }
                                        }
                                        isAgendado = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if (!agendaServiceCall.isAgendado()) {
                        agendaServiceCall.setIdOrdenTrabajo(idServiceCall);
                        LOGGER.error("Se revisaron todas las agendas y no Existen cupos");
                        throw new ApplicationException("Se revisaron todas las agendas y no Existen cupos");
                    }
                } else {
                    LOGGER.error("No Existen Aliados para realizar el trabajo");
                    throw new ApplicationException("No Existen Aliados para realizar el trabajo");
                }
            } else {
                LOGGER.error("No se encontro el tipo de trabajo 61-VISITAS TECNICAS CASAS");
                throw new ApplicationException("No se encontro el tipo de trabajo 61-VISITAS TECNICAS CASAS");
            }

            return agendaServiceCall;
        } catch (ApplicationException | NumberFormatException | RemoteException | ParseException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de crear el agendamiento. EX000: " + e.getMessage(), e);
        } finally {
            //Si se realizo cambio de nodo, asignamos nuevamente el nodo 
            //original al HHPP
            if (isCambioNodo) {
                cambioNodoHHPP(houseNumber, streetName, apartmentNumber,
                        comunidad, division, nodoHhpp);
            }
        }
    }

    private boolean agregarNotasF8(String comunidad, String division, String nota,
            String street, String house, String apto, String solicitud, String usuario) throws ApplicationException {
        try {
            RequestSpecialUpdate requestSpecialUpdate = new RequestSpecialUpdate();
            requestSpecialUpdate.setApartmentNumber(apto);
            requestSpecialUpdate.setHomeNumber(house);
            requestSpecialUpdate.setStreetName(street);
            requestSpecialUpdate.setComunity(comunidad);
            requestSpecialUpdate.setDivision(division);
            requestSpecialUpdate.setLines(nota);
            requestSpecialUpdate.setTypeRequest(Constantes.TYPE_REQUEST_NOTES_F8);

            PortManager portManager = new PortManager(wsURL, wsService);
            SpecialUpdateManagerResponse specialUpdateManagerResponse;
            try {
                specialUpdateManagerResponse = portManager.specialUpdate(requestSpecialUpdate);
            } catch (Exception e) {
                LOGGER.error("Error al intentar agregar las nostas F8, se consume nuevamente el servicio " + e.getMessage());
                specialUpdateManagerResponse = portManager.specialUpdate(requestSpecialUpdate);
            }

            if (specialUpdateManagerResponse.getResponseSpecialUpdate().getMessageText().contains("CAB0080")) {
                return true;
            } else {
                String mensaje = "Se presento error al Agregar la Notas F8 al HHPP: "
                        + apto + " " + house + " " + street + " " + comunidad + " " + division
                        + " ,error RR:" + specialUpdateManagerResponse.getResponseSpecialUpdate().getMessageText();
                mensajesRespuestaRR.add(mensaje);
                respuestaXhhpp.put(direccion, mensajesRespuestaRR);
                throw new ApplicationException("Se presento error al Agregar la Notas F8 al HHPP: "
                        + apto + " " + house + " " + street + " " + comunidad + " " + division
                        + " ,error RR:" + specialUpdateManagerResponse.getResponseSpecialUpdate().getMessageText());
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de agregar notas F8. EX000: " + e.getMessage(), e);
        }
    }

    public String obtenerTipoEdificio(String numeroApto, String carpeta, String tipoSol) {
        String tipoEdificio = "A";
        if (carpeta.equals(CUENTA_MATRIZ)) {
            if ((numeroApto.contains("PI") && numeroApto.contains("LC"))
                    || numeroApto.contains("LOCAL") || numeroApto.contains("OFC")
                    || numeroApto.contains("OFICINA") || numeroApto.contains("BODEGA")
                    || numeroApto.contains("BURBUJA") || numeroApto.contains("STAND")) {
                tipoEdificio = "K";
            }
        } else {
            //si la solicitud es de tipo PYMES el tipo de Edificio es K
            if (tipoSol != null && tipoSol.equalsIgnoreCase(Constantes.TIPOSOL_PYMES)) {
                tipoEdificio = "K";
            } else {
                if (numeroApto.contains("CASA") || numeroApto.contains("PISO") || numeroApto.startsWith("PI")) {
                    tipoEdificio = "C";
                }
                if ((numeroApto.contains("PI") && numeroApto.contains("LC")) || numeroApto.contains("LOCAL")) {
                    tipoEdificio = "K";
                }
                if (numeroApto.contains("FUENTE")) {
                    tipoEdificio = "F";
                }
                if (numeroApto.contains("BODEGA") || numeroApto.contains("OFC") || numeroApto.contains("OFICINA") || 
                        numeroApto.contains("CAJERO") || numeroApto.contains("CAJ")) {
                    tipoEdificio = "K";
                }
            }
        }
        return tipoEdificio;
    }

    public ArrayList<String> generarDirFormatoRR(DetalleDireccionEntity direccion) {
        ArrayList<String> result = null;

        if (direccion != null) {
            try {
                fiel1F18 = "";
                String value = direccion.getIdtipodireccion();
                if (value != null) {
                    Constant.TYPO_DIR valida = Constant.TYPO_DIR.valueOf(value);

                    if (valida != null) {
                        switch (valida) {
                            case CK:
                                result = generarDireccionCalleCarreraRR(direccion);
                                break;
                            case BM:
                                result = generarDireccionBarrioManzanaRR(direccion);
                                break;
                            case IT:
                                result = generarDireccionIntraducibleRR(direccion);
                                break;
                            default:
                                result = null;
                                break;
                        }

                        if (result != null) {
                            String numUnidadAjustado = ajustarNumeroUnidad(result.get(1));
                            result.set(1, numUnidadAjustado);
                        } else {
                            LOGGER.error("No se encontró resultado para tipo dirección " + valida);
                        }
                    }
                } else {
                    LOGGER.error("No se encontró valor para tipo de dirección " + direccion.getIdtipodireccion());
                }
            } catch (Exception e) {
                LOGGER.error("Error al momento de generar la dirección en formato RR: " + e.getMessage(), e);
            }
        } else {
            LOGGER.error("Parámetro dirección nulo.");
        }

        return result;
    }

    private ArrayList<String> generarDireccionCalleCarreraRR(DetalleDireccionEntity direccion) {
        ArrayList<String> resultDirCalleCarreraRR;
        try {
            //obtenemos CalleRR y numeroApartamento
            resultDirCalleCarreraRR = ordenarDireccionCalleCarreraRR(direccion);
            //obtenemos numeroUnidadRR
            resultDirCalleCarreraRR.set(1, ordenarNumUnidadCalleCarreraRR(direccion).trim());
        } catch (ApplicationException e) {
            resultDirCalleCarreraRR = null;
            LOGGER.error("Error Generando la direccion RR CK " + e.getMessage(), e);
        }
        return resultDirCalleCarreraRR;
    }

    private ArrayList<String> generarDireccionBarrioManzanaRR(DetalleDireccionEntity direccion) {
        ArrayList<String> resultBarrioManzanaRR = new ArrayList<>(3);
        try {
            resultBarrioManzanaRR.add(" ");
            resultBarrioManzanaRR.add(" ");
            resultBarrioManzanaRR.add(" ");
            //calleRR
            resultBarrioManzanaRR.set(0, ordenarDireccionBarrioManzanaRR(direccion).trim());
            //numeroUnidadRR
            resultBarrioManzanaRR.set(1, ordenarNumUnidadCalleCarreraBM(direccion).trim());
            //numeroApartamentoRR
            resultBarrioManzanaRR.set(2, generarNumAptoBMRR(direccion).trim());
        } catch (ApplicationException e) {
            resultBarrioManzanaRR = null;
            LOGGER.info("Error Generando la direccion RR BM " + e.getMessage(), e);
        }
        return resultBarrioManzanaRR;
    }

    private ArrayList<String> generarDireccionIntraducibleRR(DetalleDireccionEntity direccion) {
        ArrayList<String> resultIntraducibleRR = new ArrayList<>();
        try {
            resultIntraducibleRR.add(" ");
            resultIntraducibleRR.add(" ");
            resultIntraducibleRR.add(" ");
            //calleRR
            resultIntraducibleRR.set(0, ordenarDireccionCalleCarreraIT(direccion).trim());
            //numeroUnidadRR
            resultIntraducibleRR.set(1, ordenarNumUnidadCalleCarreraIT(direccion).trim());
            //numeroApartamentoRR
            resultIntraducibleRR.set(2, generarNumeroApartamentoIT(direccion).trim());
        } catch (ApplicationException e) {
            resultIntraducibleRR = null;
            LOGGER.info("Error Generando la direccion RR IT " + e.getMessage(), e);
        }
        return resultIntraducibleRR;
    }

    /*
     * Metodos para generar Direccion Tipo Calle Carrera
     */
    private ArrayList<String> ordenarDireccionCalleCarreraRR(DetalleDireccionEntity direccion) throws ApplicationException {
        ArrayList<String> resultCalleRR = new ArrayList<String>();
        StringBuilder strDireccion = new StringBuilder();
        strDireccion.append(formatoRR(direccion.getTipoviaprincipal()));
        strDireccion.append(ESPACIO);
        strDireccion.append(direccion.getNumviaprincipal().trim().replace(" ", ""));
        String letra = direccion.getLtviaprincipal() == null ? "" : direccion.getLtviaprincipal();
        String letra1 = direccion.getNlpostviap() == null ? "" : direccion.getNlpostviap();
        obtenerModificadores(strDireccion, letra);
        strDireccion.append(letra);
        obtenerModificadorLetraDos(strDireccion, letra, letra1);
        strDireccion.append(letra1);
        if (direccion.getBisviaprincipal() != null && !direccion.getBisviaprincipal().trim().isEmpty()) {
            strDireccion.append(BIS);
            if (!direccion.getBisviaprincipal().equalsIgnoreCase(BIS)) {
                strDireccion.append(direccion.getBisviaprincipal());
            }
        }

        strDireccion.append(formatearCuadranteRR(
                direccion.getCuadviaprincipal() == null ? ""
                : direccion.getCuadviaprincipal(),
                direccion.getCuadviageneradora() == null ? ""
                : direccion.getCuadviageneradora()));
        //validamos si la direccion es multiorigen para incluir el barrio
        // en el objeto direccion se debe cargar el campo multiorigen desde el metodo que invoco el presente
        if (direccion.getMultiOrigen() != null && direccion.getMultiOrigen().equalsIgnoreCase("1")
                && direccion.getBarrio() != null && !direccion.getBarrio().trim().isEmpty()) {
            strDireccion.append(ESPACIO);
            strDireccion.append(direccion.getBarrio().replace(" ", ""));
        }
        fiel1F18 = strDireccion.toString().trim();
        resultCalleRR.add("  ");
        resultCalleRR.add("  ");
        resultCalleRR.add("  ");
        ArrayList<String> subEdificioApto = generarSubEdificioApto(direccion);
        //Adicionamos el complemento del SubEdificio
        if (subEdificioApto.get(0) != null && !subEdificioApto.get(0).trim().isEmpty()) {
            strDireccion.append(ESPACIO);
            strDireccion.append(subEdificioApto.get(0).trim());
        }

        //CalleRR
        resultCalleRR.set(0, strDireccion.toString().trim());
        //numeroApartamentoRR
        resultCalleRR.set(2, subEdificioApto.get(1).trim());
        return resultCalleRR;
    }

    public ArrayList<String> generarSubEdificioApto(DetalleDireccionEntity direccion) throws ApplicationException {
        String subEdifStr = generarSubEdificioMZ(direccion);
        ArrayList<String> resultSubEdificioApto = new ArrayList<>();
        resultSubEdificioApto.add("  ");
        resultSubEdificioApto.add("  ");
        //Complemento CalleRR
        resultSubEdificioApto.set(0, subEdifStr.trim());
        //numeroApartamentoRR
        resultSubEdificioApto.set(1, generarNumAptoBMRR(direccion).trim());
        return resultSubEdificioApto;
    }

    public String generarSubEdificioMZ(DetalleDireccionEntity direccion) throws ApplicationException {
        StringBuilder strSubEdificio = new StringBuilder();
        List<String> complementos = new ArrayList<>();
        List<String> valores = new ArrayList<>();
        addComplementoLista(direccion.getCptiponivel1(), direccion.getCpvalornivel1(), complementos, valores);
        addComplementoLista(direccion.getCptiponivel2(), direccion.getCpvalornivel2(), complementos, valores);
        addComplementoLista(direccion.getCptiponivel3(), direccion.getCpvalornivel3(), complementos, valores);
        addComplementoLista(direccion.getCptiponivel4(), direccion.getCpvalornivel4(), complementos, valores);

        if (complementos.size() > 0) {
            for (int i = 0; i < complementos.size(); i++) {
                if (complementos.get(i) != null && !complementos.get(i).trim().isEmpty()
                        && valores.get(i) != null && !valores.get(i).trim().isEmpty()) {
                    String formaRR = formatoRR(complementos.get(i));
                    strSubEdificio.append(formaRR);
                    if (formaRR.equalsIgnoreCase("TORRE")
                            || formaRR.equalsIgnoreCase("ETAPA")
                            || formaRR.equalsIgnoreCase("EN")
                            || formaRR.equalsIgnoreCase("ENTRADA")
                            || formaRR.equalsIgnoreCase("SECTOR")
                            || formaRR.equalsIgnoreCase("ZONA")
                            || formaRR.equalsIgnoreCase("SUBETAPA")
                            || formaRR.equalsIgnoreCase("NIVEL")
                            || formaRR.equalsIgnoreCase("UNDIR")) {
                        strSubEdificio.append(ESPACIO);
                    }
                    strSubEdificio.append(valores.get(i));
                    strSubEdificio.append(ESPACIO);
                }
            }
        }

        return strSubEdificio.toString().trim();
    }

    private void addComplementoLista(String contenidoCpm, String valorCpm, List<String> listaCmp, List<String> listaVcmp) {

        listaCmp.add(contenidoCpm == null || contenidoCpm.equals(Constantes.VACIO) ? " " : contenidoCpm);
        listaVcmp.add(valorCpm == null ? " " : valorCpm);
    }

    private String ordenarNumUnidadCalleCarreraRR(DetalleDireccionEntity direccion) throws ApplicationException {
        StringBuilder str = new StringBuilder();
        String numViaGeneradora = "";
        if (direccion.getNumviageneradora() != null) {
            numViaGeneradora = direccion.getNumviageneradora();
            Pattern pattern = Pattern.compile("^0.*");
            while (pattern.matcher(numViaGeneradora).matches()
                    && numViaGeneradora.length() > 1) {
                int pos = numViaGeneradora.indexOf("0");
                numViaGeneradora = numViaGeneradora.substring(pos + 1);
            }
        }

        if (direccion.getTipoviageneradora() != null
                && !direccion.getTipoviageneradora().isEmpty()
                && !direccion.getTipoviageneradora().equalsIgnoreCase(Constantes.V_VACIO)
                && !direccion.getTipoviageneradora().equalsIgnoreCase(Constantes.VACIO)) {
            str.append(formatoRR(direccion.getTipoviageneradora()));
        }

        str.append(numViaGeneradora);
        String letra = direccion.getLtviageneradora() == null ? "" : direccion.getLtviageneradora();
        String letra1 = direccion.getNlpostviag() == null ? "" : direccion.getNlpostviag();
        String letra3 = direccion.getLetra3g() == null ? "" : direccion.getLetra3g();
        obtenerModificadores(str, letra);
        str.append(letra);
        obtenerModificadorLetraDos(str, letra, letra1);
        str.append(letra1);
        if (letra1 != null && !letra1.trim().isEmpty()
                && letra3 != null && !letra3.trim().isEmpty()) {
            str.append(SEPARADOR_DIRECCION);
            str.append(letra3);
        }

        if (direccion.getBisviageneradora() != null && !direccion.getBisviageneradora().trim().isEmpty()) {
            str.append(BIS);
            if (!direccion.getBisviageneradora().equalsIgnoreCase(BIS)) {
                str.append(direccion.getBisviageneradora());
            }
        }
        str.append(SEPARADOR_DIRECCION);
        String placa = direccion.getPlacadireccion();
		if (placa.indexOf("CD") == 0){
            str = new StringBuilder();
        }
        if (placa.length() < 2) {
            str.append("0");
        }
        str.append(placa);
        fiel1F18 += " " + str.toString().trim();
        return str.toString().trim();
    }

    /*
     * Metodos para generar Direccion Tipo Barrio Manzana
     */
    private String ordenarDireccionBarrioManzanaRR(DetalleDireccionEntity direccion) throws ApplicationException {
        StringBuilder strDireccion = new StringBuilder();
        String barrio = direccion.getBarrio() == null || direccion.getBarrio().trim().isEmpty()
                ? "" : direccion.getBarrio();
        //Cambio solicitado por Maribel (31/01/2014), si el nivel 1 tiene información el barrio dede ser este         
        if (direccion.getMztiponivel1() != null && !direccion.getMztiponivel1().trim().isEmpty()
                && !direccion.getMztiponivel1().equals(Constantes.VACIO) && !direccion.getMztiponivel1().equals(Constantes.V_VACIO)
                && direccion.getMzvalornivel1() != null && !direccion.getMzvalornivel1().isEmpty()) {
            if (direccion.getMztiponivel1().equalsIgnoreCase("CIUDADELA")) {
                barrio = direccion.getMztiponivel1() + ESPACIO + direccion.getMzvalornivel1();
            } else {
                barrio = "UR" + ESPACIO + direccion.getMzvalornivel1();
            }
        }
        String sectEtp = "";
        //Para RR omitimos el valor de Etapa o Sector que se encuentra el nivel 2, pero se debe concatenar
        //el valor de este nivel de tener información
        if (direccion.getMztiponivel2() != null && !direccion.getMztiponivel2().trim().isEmpty()
                && !direccion.getMztiponivel2().equals(Constantes.VACIO) && !direccion.getMztiponivel2().equals(Constantes.V_VACIO)
                && direccion.getMzvalornivel2() != null && !direccion.getMzvalornivel2().trim().isEmpty()) {
            Pattern pattern = Pattern.compile("[0-9]+");
            //validamos si el sector son solo numeros
            if (pattern.matcher(direccion.getMzvalornivel2().trim()).matches()) {
                sectEtp += direccion.getMzvalornivel2().trim().replace(" ", "");
            } else {//para el caso del sector / etapa alfanumerico
                sectEtp += ESPACIO + direccion.getMztiponivel2() + ESPACIO + direccion.getMzvalornivel2().trim().replace(" ", "");
            }
        }
        String[] palabras = barrio.split(ESPACIO);
        if (palabras.length == 1) {
            strDireccion.append(BARRIO);
            strDireccion.append(ESPACIO);
            strDireccion.append(barrio);
        } else if (palabras.length == 2) {
            strDireccion.append(palabras[0]);
            strDireccion.append(ESPACIO);
            strDireccion.append(palabras[1]);
        } else if (palabras.length > 2) {
            strDireccion.append(palabras[0]);
            strDireccion.append(ESPACIO);
            for (int i = 1; i < palabras.length; i++) {
                strDireccion.append(palabras[i]);
            }
        }

        if (!sectEtp.isEmpty()) {
            strDireccion.append(sectEtp);
        }

        //incluimos en Calle RR el valor de la Supermazana
        if (direccion.getMztiponivel3() != null && !direccion.getMztiponivel3().trim().isEmpty()
                && !direccion.getMztiponivel3().equals("VACIO") && !direccion.getMztiponivel3().equals("V")
                && direccion.getMzvalornivel3() != null && !direccion.getMzvalornivel3().trim().isEmpty()) {
            strDireccion.append(ESPACIO);
            strDireccion.append(formatoRR(direccion.getMztiponivel3()));
            strDireccion.append(direccion.getMzvalornivel3().replace(" ", ""));
        }
        fiel1F18 = strDireccion.toString();
        String subEdificio = generarSubEdificioMZ(direccion);
        if (subEdificio != null && !subEdificio.trim().isEmpty()) {
            strDireccion.append(ESPACIO);
            strDireccion.append(subEdificio.trim());
        }
        return strDireccion.toString().trim();
    }

    private String ordenarNumUnidadCalleCarreraBM(DetalleDireccionEntity direccion) throws ApplicationException {
        StringBuilder str = new StringBuilder();
        if (direccion.getMztiponivel4() != null && !direccion.getMztiponivel4().trim().isEmpty()
                && !direccion.getMztiponivel4().equals("VACIO") && !direccion.getMztiponivel4().equals("V")
                && direccion.getMzvalornivel4() != null && !direccion.getMzvalornivel4().trim().isEmpty()) {
            str.append(formatoRR(direccion.getMztiponivel4()));
            str.append(direccion.getMzvalornivel4());
        } else if (direccion.getPlacadireccion() != null){
            str.append(direccion.getPlacadireccion());
        } else {
            str.append("0");
        }
        if (direccion.getMztiponivel5() != null && !direccion.getMztiponivel5().trim().isEmpty()
                && !direccion.getMztiponivel5().equals("VACIO") && !direccion.getMztiponivel5().equals("V")
                && direccion.getMzvalornivel5() != null && !direccion.getMzvalornivel5().trim().isEmpty()) {
            str.append(SEPARADOR_DIRECCION);
            if (direccion.getMztiponivel5().equalsIgnoreCase("CASA")) {
                str.append("C");
            } else if (direccion.getMztiponivel5().equalsIgnoreCase("LOTE")) {
                str.append("L");
            } else {
                str.append(formatoRR(direccion.getMztiponivel5()));
            }
            str.append(direccion.getMzvalornivel5());
        }

        fiel1F18 += " " + str.toString().trim();
        return str.toString().trim();
    }

    public String generarNumAptoBMRR(DetalleDireccionEntity direccion) {

        StringBuilder strNumeroApto = new StringBuilder();
        List<String> complementos = new ArrayList<>();
        List<String> valores = new ArrayList<>();
        try {
            addComplementoLista(direccion.getCptiponivel5(), direccion.getCpvalornivel5(), complementos, valores);
            addComplementoLista(direccion.getCptiponivel6(), direccion.getCpvalornivel6(), complementos, valores);

            if (complementos.size() > 0) {
                int posApto = 0;
                //Revisamos el nivel 5 
                if (complementos.get(posApto) != null && !complementos.get(posApto).trim().isEmpty()) {
                    String formaRR = formatoRR(complementos.get(posApto));
                    if (formaRR != null && !formaRR.trim().isEmpty() && (formaRR.equalsIgnoreCase("FUENTE") || formaRR.equalsIgnoreCase("ADMIN"))) {
                        strNumeroApto.append(formaRR);
                        if (valores.get(posApto) != null && !valores.get(posApto).trim().isEmpty()) {
                            strNumeroApto.append(valores.get(posApto));
                        }
                    } else if (valores.get(posApto) != null && !valores.get(posApto).trim().isEmpty()) {
                        // si el formato RR es apartamento solo se debe colocar el numero 
                        if (formaRR != null
                                && (!formaRR.equalsIgnoreCase("APARTAMENTO")
                                && !formaRR.equalsIgnoreCase("AP")
                                && !formaRR.equalsIgnoreCase("APTO"))) {
                            strNumeroApto.append(formaRR);
                        }
                        strNumeroApto.append(valores.get(posApto));
                    }
                }
                posApto += 1;
                if (complementos.get(posApto) != null && !complementos.get(posApto).trim().isEmpty()
                        && valores.get(posApto) != null && !valores.get(posApto).trim().isEmpty()) {
                    String formaRR = formatoRR(complementos.get(posApto));
                    strNumeroApto.append(SEPARADOR_DIRECCION);
                    // si el formato RR es apartamento solo se debe colocar el numero 
                    if (formaRR != null
                            && (!formaRR.equalsIgnoreCase("APARTAMENTO")
                            && !formaRR.equalsIgnoreCase("AP")
                            && !formaRR.equalsIgnoreCase("APTO"))) {
                        strNumeroApto.append(formaRR);
                    }
                    strNumeroApto.append(valores.get(posApto));
                }
            }

            //si no existe complemento el subEdificio se deja vacio 
            //y el numero de apartamento por defecto es "CASA"
            if (strNumeroApto.toString() == null || strNumeroApto.toString().trim().isEmpty()) {
                strNumeroApto.append(V_APTO_DEFECTO);
            }
            String numAptoAjustado = ajustarNumeroApto(strNumeroApto.toString());
            return numAptoAjustado.trim();
        } catch (ApplicationException e) {
            LOGGER.error("Error al momento de generar el número apartamento BM. EX000: " + e.getMessage(), e);
        }
        return null;
    }

    private String generarsubEdificioIT(DetalleDireccionEntity direccion) throws ApplicationException {
        return generarSubEdificioMZ(direccion);
    }

    private String generarNumeroApartamentoIT(DetalleDireccionEntity direccion) throws ApplicationException {
        return generarNumAptoBMRR(direccion);
    }

    public String obtenerEstrato(String nvlSocioEconomico, String tipoSol, String tipoEdificio) {
        String estrato = (nvlSocioEconomico.equalsIgnoreCase("0") || nvlSocioEconomico.equalsIgnoreCase("-1") ? "NG" : nvlSocioEconomico);
        if (tipoSol != null && tipoSol.equalsIgnoreCase(Constantes.TIPOSOL_PYMES)) {
            estrato = "NR";
        } else if (tipoEdificio.equalsIgnoreCase("K")
                || tipoEdificio.equalsIgnoreCase("O")
                || tipoEdificio.equalsIgnoreCase("L")) {
            estrato = "NR";
        }
        return estrato;
    }

    public boolean crearCruceRR(String comunidad, String division, String calleRR, String unidadRR, String tipoDireccion) throws ApplicationException {
        try {
            RequestStreetGrid requestStreetGrid = new RequestStreetGrid();
            requestStreetGrid.setCommunity(comunidad);
            requestStreetGrid.setDivision(division);
            String grpi = obtenerGrpiCruceRR(unidadRR.trim(), comunidad, tipoDireccion);
            requestStreetGrid.setGrpi(grpi == null || grpi.trim().isEmpty() ? "" : grpi);
            if (calleRR != null) {
                String calle = calleRR.toUpperCase().replace("Ñ", "N");
                requestStreetGrid.setStreetName(calle.trim());
            }

            PortManager portManager = new PortManager(wsURL, wsService);
            UpdateStreetGridResponse updateStreetGridResponse;

            try {
                updateStreetGridResponse = portManager.updateStreetGrid(requestStreetGrid);
            } catch (ApplicationException e) {
                LOGGER.error("Error al intentar crear el cruce, se consume nuevamente el servicio " + e.getMessage());
                updateStreetGridResponse = portManager.updateStreetGrid(requestStreetGrid);
            }
            if (updateStreetGridResponse.getResponseSpecialUpdate().getMessageText().contains("XOR1000")
                    || updateStreetGridResponse.getResponseSpecialUpdate().getMessageText().contains("XOR1070")) {
                return true;
            } else {
                throw new ApplicationException("Se presento error al crear el cruce: "
                        + (calleRR != null ? calleRR : "") + " " + (grpi != null ? grpi : "") + " " + (comunidad != null ? comunidad : "") + " " + (division != null ? division : "")
                        + " ,error RR:" + updateStreetGridResponse.getResponseSpecialUpdate().getMessageText());
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de crear el cruce RR. EX000: " + e.getMessage(), e);
        }
    }

    private String obtenerGrpiCruceRR(String numeroUnidad, String comunidad, String tipoDireccion) {
        String grpiResult = "";
        String value = tipoDireccion;
        Constant.TYPO_DIR valida = Constant.TYPO_DIR.valueOf(value);

        switch (valida) {
            case CK:
                grpiResult = obtenerGrpiRRCK(numeroUnidad, comunidad);
                break;
            case BM:
                grpiResult = obtenerGrpiRRBM(numeroUnidad, comunidad);
                break;
            case IT:
                grpiResult = obtenerGrpiRRIT(numeroUnidad, comunidad);
                break;
            default:
                break;
        }
        return grpiResult;
    }

    private String obtenerGrpiRRCK(String numeroUnidad, String comunidad) {
        String grpiPar;
        String grpiImpar;
        String grpiResult;
        int index = numeroUnidad.indexOf("-");
        grpiPar = Constantes.ADD_CRUCE_RR + "(" + numeroUnidad.substring(0, index + 1);
        String strPlaca = numeroUnidad.substring(index + 1);
        if (strPlaca.length() <= 2) {
            grpiPar += "9";
        } else if (strPlaca.length() >= 3) {
            grpiPar += "99";
        }
        grpiImpar = grpiPar;
        grpiPar += "8)2";
        grpiImpar += "9)1";
        grpiPar += comunidad;
        grpiImpar += comunidad;
        grpiResult = grpiPar + grpiImpar;
        return grpiResult;
    }

    private String obtenerGrpiRRBM(String numeroUnidad, String comunidad) {
        String grpiPar;
        String grpiImpar;
        String grpiResult;
        int index = numeroUnidad.indexOf("-");
        grpiPar = Constantes.ADD_CRUCE_RR + "(" + numeroUnidad.substring(0, index + 1);
        String strPlaca = numeroUnidad.substring(index + 1);
        char charPlaca = numeroUnidad.charAt(numeroUnidad.length() - 1);
        grpiPar += strPlaca.substring(0, strPlaca.length() - 1);
        grpiImpar = grpiPar;
        //verificamos si el ultimo caracter de la placa es numero
        if (Character.isDigit(charPlaca)) {
            int digit = Character.getNumericValue(charPlaca);
            if (digit % 2 == 0) {
                grpiPar += digit + ")2";
                grpiImpar += digit + 1 + ")1";
            } else {
                grpiPar += digit + 1 + ")2";
                grpiImpar += digit + ")1";
            }
        } else {
            int ascii = String.valueOf(charPlaca).codePointAt(0);
            if (ascii + 1 == 91 || ascii + 1 == 123) {
                ascii = ascii - 26;
            }
            grpiPar += charPlaca + ")2";
            grpiImpar += (char) (ascii + 1) + ")1";
        }
        grpiPar += comunidad;
        grpiImpar += comunidad;
        grpiResult = grpiPar + grpiImpar;
        return grpiResult;
    }

    private String obtenerGrpiRRIT(String numeroUnidad, String comunidad) {
        String grpiPar;
        String grpiImpar;
        String grpiResult;

        String sinEsp = numeroUnidad.replace(" ", "");
        Pattern patCK = Pattern.compile("([0-9]+.*)-([0-9]{1,3})");
        Matcher matCK = patCK.matcher(sinEsp);
        Pattern patBM = Pattern.compile("([Mm][Zz]|0){1}(.*)-(.*)");
        Matcher matBM = patBM.matcher(sinEsp);
        Pattern patCD = Pattern.compile("([Cc][Dd]){1}-[0-9]{1,6}");
        Matcher matCD = patCD.matcher(sinEsp);

        if (matCK.matches()) {
            grpiResult = obtenerGrpiRRCK(sinEsp, comunidad);
        } else if (matBM.matches()) {
            grpiResult = obtenerGrpiRRBM(sinEsp, comunidad);
        } else if (matCD.matches()) {
            int index = sinEsp.indexOf("-");
            grpiPar = "A" + "(" + sinEsp.substring(0, index + 1);

            String strPlaca = sinEsp.substring(index + 1);
            for (int i = 0; i < strPlaca.length() - 1; i++) {
                grpiPar += "9";
            }
            grpiImpar = grpiPar;
            grpiPar += "8)2";
            grpiImpar += "9)1";
            grpiPar += comunidad;
            grpiImpar += comunidad;
            grpiResult = grpiPar + grpiImpar;

        } else {
            grpiResult = null;
        }

        return grpiResult;
    }

    public boolean cambiarCampo1NotasAdicionales(String comunidad, String division,
            String calle, String unidad, String apto, String campoUno) throws ApplicationException {
        try {
            RequestUnitAddInf requestUnitAddInf = new RequestUnitAddInf();
            requestUnitAddInf.setApartmentNumber(apto);
            requestUnitAddInf.setHomeNumber(unidad);
            requestUnitAddInf.setStreetName(calle);
            requestUnitAddInf.setComunity(comunidad);
            requestUnitAddInf.setDivision(division);

            if (campoUno != null
                    && !campoUno.trim().isEmpty()
                    && campoUno.length() > 34) {
                campoUno = campoUno.substring(0, 34);
            }

            requestUnitAddInf.setField1(campoUno);
            PortManager portManager = new PortManager(wsURL, wsService);
            UnitAddInfManagerResponse unitAddInfManagerResponse = portManager.unitAddInfManager(requestUnitAddInf);

            if (unitAddInfManagerResponse.getResponseUnitAddInf().
                    getMessageText().contains("XOR1000")) {
                return true;
            } else {
                throw new ApplicationException("Se presento error al actualizar el campo1 de las notas F18: "
                        + unitAddInfManagerResponse.getResponseUnitAddInf().getMessageText());
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de cambiar el campo 1 de notas adicionales. EX000: " + e.getMessage(), e);
        }
    }

    private boolean agregarNotaF18(String comunidad, String division,
            DireccionRREntity dirRREntity,
            Direccion direccion, String campoUno, String campoCuatro) throws ApplicationException {
        try {
            RequestUnitAddInf requestUnitAddInf = new RequestUnitAddInf();
            requestUnitAddInf.setApartmentNumber(dirRREntity.getNumeroApartamento());
            requestUnitAddInf.setHomeNumber(dirRREntity.getNumeroUnidad());
            requestUnitAddInf.setStreetName(dirRREntity.getCalle());
            requestUnitAddInf.setComunity(comunidad);
            requestUnitAddInf.setDivision(division);

            String field1 = (campoUno == null ? "" : campoUno.trim());
            if (field1 != null && !field1.trim().isEmpty() && field1.length() > 34) {
                field1 = field1.substring(0, 34);
            }
            requestUnitAddInf.setField1(field1);
            ConsultaEspecificaManager manager = new ConsultaEspecificaManager();
            String localidad = manager.obtenerLocalidadDir(direccion.getDirId().toString().trim());
            String field2 = localidad == null || localidad.trim().isEmpty() ? "NG" : localidad;
            if (!field2.trim().isEmpty() && field2.length() > 34) {
                field2 = field2.substring(0, 34);
            }
            requestUnitAddInf.setField2(field2);
            String field3;
            field3 = (direccion.getNodoUno() == null || direccion.getNodoUno().trim().isEmpty() ? "NG" : direccion.getNodoUno().trim())
                    + " - " + (direccion.getNodoDos() == null || direccion.getNodoDos().trim().isEmpty() ? "NG" : direccion.getNodoDos().trim());

            requestUnitAddInf.setField3(field3);

            //Se envia nodoReal.
            requestUnitAddInf.setField4(campoCuatro);

            if (direccion.getUbicacion() != null) {
                requestUnitAddInf.setLatitude(direccion.getUbicacion().getUbiLatitud() == null ? "0" : direccion.getUbicacion().getUbiLatitud());
                requestUnitAddInf.setLongitude(direccion.getUbicacion().getUbiLongitud() == null ? "0" : direccion.getUbicacion().getUbiLongitud());
            } else {
                requestUnitAddInf.setLatitude("0");
                requestUnitAddInf.setLongitude("0");
            }

            PortManager portManager = new PortManager(wsURL, wsService);
            UnitAddInfManagerResponse unitAddInfManagerResponse;

            try {
                unitAddInfManagerResponse = portManager.unitAddInfManager(requestUnitAddInf);
            } catch (ApplicationException e) {
                LOGGER.error("Error al intentar agregar las nostas F18, se consume nuevamente el servicio " + e.getMessage());
                unitAddInfManagerResponse = portManager.unitAddInfManager(requestUnitAddInf);
            }

            if (unitAddInfManagerResponse.getResponseUnitAddInf().getMessageText().contains("XOR1000")) {
                HhppMglManager hhppmanager = new HhppMglManager();
                Hhpp hhpp = new Hhpp();
                hhpp.setAptoRR(dirRREntity.getNumeroApartamento());
                hhpp.setCalleRR(dirRREntity.getCalle());
                hhpp.setUnidadRR(dirRREntity.getNumeroUnidad());
                hhpp.setComunidadRR(comunidad);
                hhpp.setDivisionRR(division);
                HhppMgl hhppMgl = hhppmanager.findHhppByRRFields(hhpp);
                if (hhppMgl != null) {
                    hhppMgl.setHhpNotasAdd1(requestUnitAddInf.getField1());
                    hhppMgl.setHhpNotasAdd2(requestUnitAddInf.getField2());
                    hhppMgl.setHhpNotasAdd3(requestUnitAddInf.getField3());
                    hhppMgl.setHhpNotasAdd4(requestUnitAddInf.getField4());
                    hhppmanager.update(hhppMgl);
                }
                return true;
            } else {
                throw new ApplicationException("Se presento error al agregar las notas F18: "
                        + unitAddInfManagerResponse.getResponseUnitAddInf().getMessageText());
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de agregar Nota F18. EX000: " + e.getMessage(), e);
        }
    }

    /**
     * Función que agrega una nota en RR y HHPP modificada para inspira.
     *
     * @author Juan David Hernandez
     */
    private boolean agregarNotaF18_Inspira(String comunidad, String division,
            DireccionRREntity dirRREntity,
            Direccion direccion, String campoUno, String campoCuatro) throws ApplicationException {
        try {
            RequestUnitAddInf requestUnitAddInf = new RequestUnitAddInf();
            requestUnitAddInf.setApartmentNumber(dirRREntity.getNumeroApartamento());
            requestUnitAddInf.setHomeNumber(dirRREntity.getNumeroUnidad());
            requestUnitAddInf.setStreetName(dirRREntity.getCalle());
            requestUnitAddInf.setComunity(comunidad);
            requestUnitAddInf.setDivision(division);

            //Campo 1 RR: Direccion antigua (si existe)
            String field1 = (campoUno == null ? "" : campoUno.trim());
            if (field1 != null && !field1.trim().isEmpty() && field1.length() > 34) {
                field1 = field1.substring(0, 34);
            }
            requestUnitAddInf.setField1(field1);

            //campo 2 RR: Localidad
            ConsultaEspecificaManager manager = new ConsultaEspecificaManager();
            String localidad = manager.obtenerLocalidadDir(direccion.getDirId().toString().trim());
            String field2 = localidad == null || localidad.trim().isEmpty() ? "NG" : localidad;
            if (!field2.trim().isEmpty() && field2.length() > 34) {
                field2 = field2.substring(0, 34);
            }
            requestUnitAddInf.setField2(field2);

            //Campo 3 RR: Concatenacion de Nodos
            String field3;
            field3 = (direccion.getGeoZonaGponDiseniado() == null || direccion.getGeoZonaGponDiseniado().trim().isEmpty() ? "NG" : direccion.getGeoZonaGponDiseniado().trim())
                    + " - " + (direccion.getGeoZonaUnifilar() == null || direccion.getGeoZonaUnifilar().trim().isEmpty() ? "NG" : direccion.getGeoZonaUnifilar().trim());
            if (!field3.trim().isEmpty() && field3.length() > 34) {
                field3 = field3.substring(0, 34);
            }
            requestUnitAddInf.setField3(field3);

            //Campo 4 RR: //Se envia nodoReal.
            requestUnitAddInf.setField4(campoCuatro);

            if (direccion.getUbicacion() != null) {
                requestUnitAddInf.setLatitude(direccion.getUbicacion().getUbiLatitud() == null ? "0" : direccion.getUbicacion().getUbiLatitud());
                requestUnitAddInf.setLongitude(direccion.getUbicacion().getUbiLongitud() == null ? "0" : direccion.getUbicacion().getUbiLongitud());
            } else {
                requestUnitAddInf.setLatitude("0");
                requestUnitAddInf.setLongitude("0");
            }

            PortManager portManager = new PortManager(wsURL, wsService);
            UnitAddInfManagerResponse unitAddInfManagerResponse;

            try {
                unitAddInfManagerResponse = portManager.unitAddInfManager(requestUnitAddInf);
            } catch (ApplicationException e) {
                LOGGER.error("Error al intentar agregar las nostas F18, se consume nuevamente el servicio " + e.getMessage());
                unitAddInfManagerResponse = portManager.unitAddInfManager(requestUnitAddInf);
            }

            if (unitAddInfManagerResponse.getResponseUnitAddInf().getMessageText().contains("XOR1000")) {
                return true;
            } else {
                throw new ApplicationException("Se presento error al agregar las notas F18: "
                        + unitAddInfManagerResponse.getResponseUnitAddInf().getMessageText());
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de agregar Nota F18. EX000: " + e.getMessage(), e);
        }
    }

    private boolean agregarNotaF18(String comunidad, String division, String fieldF18,
            String calleRR, String unidadRR, String aptoRR, PreFichaGeorreferenciaMgl direccion) throws ApplicationException {
        try {
            RequestUnitAddInf requestUnitAddInf = new RequestUnitAddInf();
            requestUnitAddInf.setApartmentNumber(aptoRR);
            requestUnitAddInf.setHomeNumber(unidadRR);
            requestUnitAddInf.setStreetName(calleRR);
            requestUnitAddInf.setComunity(comunidad);
            requestUnitAddInf.setDivision(division);

            String field1 = fieldF18 != null && !fieldF18.trim().isEmpty()
                    ? fieldF18.trim()
                    : (direccion.getAddress().length() > 34 ? direccion.getAddress().substring(0, 34) : direccion.getAddress());
            requestUnitAddInf.setField1(field1);

            ConsultaEspecificaManager manager = new ConsultaEspecificaManager();
            String localidad = direccion.getLocality();
            requestUnitAddInf.setField2(localidad == null || localidad.trim().isEmpty() ? "NG" : localidad);

            String field3
                    = (direccion.getNodoUno() == null || direccion.getNodoUno().trim().isEmpty() ? "NG" : direccion.getNodoUno().trim())
                    + " - " + (direccion.getNodoDos() == null || direccion.getNodoDos().trim().isEmpty() ? "NG" : direccion.getNodoDos().trim());
            //Cuando la comunidad es Bogotá
            if (comunidad.equals("BOG") || comunidad.equals("ZBO")) {
                field3 += " - " + (direccion.getNodoTres() == null || direccion.getNodoTres().trim().isEmpty() ? "NG" : direccion.getNodoTres().trim());
            }
            requestUnitAddInf.setField3(field3);

            requestUnitAddInf.setField4(direccion.getAppletStandar() == null
                    || direccion.getAppletStandar().trim().isEmpty() ? "NG" : direccion.getAppletStandar().trim());

            requestUnitAddInf.setLatitude(direccion.getCy() == null ? "0" : direccion.getCy());
            requestUnitAddInf.setLongitude(direccion.getCx() == null ? "0" : direccion.getCx());

            PortManager portManager = new PortManager(wsURL, wsService);
            UnitAddInfManagerResponse unitAddInfManagerResponse = portManager.unitAddInfManager(requestUnitAddInf);

            if (unitAddInfManagerResponse.getResponseUnitAddInf().getMessageText().contains("XOR1000")) {
                HhppMglManager hhppmanager = new HhppMglManager();
                Hhpp hhpp = new Hhpp();
                hhpp.setAptoRR(aptoRR);
                hhpp.setCalleRR(calleRR);
                hhpp.setUnidadRR(unidadRR);
                hhpp.setComunidadRR(comunidad);
                hhpp.setDivisionRR(division);
                HhppMgl hhppMgl = hhppmanager.findHhppByRRFields(hhpp);
                if (hhppMgl != null) {
                    hhppMgl.setHhpNotasAdd1(requestUnitAddInf.getField1());
                    hhppMgl.setHhpNotasAdd2(requestUnitAddInf.getField2());
                    hhppMgl.setHhpNotasAdd3(requestUnitAddInf.getField3());
                    hhppMgl.setHhpNotasAdd4(requestUnitAddInf.getField4());
                    hhppmanager.update(hhppMgl);
                }
                return true;
            } else {
                throw new ApplicationException("Se presento error al agregar las notas F18: "
                        + unitAddInfManagerResponse.getResponseUnitAddInf().getMessageText());
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de agregar Nota F18. EX000: " + e.getMessage(), e);
        }
    }

    @Deprecated
    public boolean cambioEstrato(String comunidad, String division, String nuevoEstrato, String solicitud, String usuario, String observaciones) throws ApplicationException {
        String nota;
        nota = "SOLICITUD: " + solicitud + ";"
                + " SOLICITANTE: " + usuario + ";"
                + " CANAL: DITRIBUIDORES;"
                + " ESTRATO REAL: " + nuevoEstrato + ";"
                + " OBSERVACION: SE REALIZA CAMBIO DE ESTRATO " + observaciones;

        if (!WorkOrderEnCurso(comunidad, division, direccionRREntity)) {
            if (cambioEstratoRR(comunidad, division, nuevoEstrato, direccionRREntity)) {
                if (agregarNotasF8(comunidad, division, nota,
                        direccionRREntity.getCalle(),
                        direccionRREntity.getNumeroUnidad(),
                        direccionRREntity.getNumeroApartamento(),
                        solicitud, usuario)) {
                    return true;
                }
            }
        } else {
            throw new ApplicationException("El HHPP posee ordenes de trabajo activas en este momento");
        }
        return false;
    }

    public boolean cambioEstrato(String comunidad, String division, String nuevoEstrato, String solicitud, String usuario, String observaciones,
            String houseNumber, String streetName, String apartmentNumber, String tipoSol, String tipoDocSoporte) throws ApplicationException {
        String nota;
        nota = "SOLICITUD: " + solicitud + ";"
                + " SOLICITANTE: " + usuario + ";"
                + " CANAL: " + tipoSol + ";"
                + " ESTRATO REAL: " + nuevoEstrato + ";"
                + " OBSERVACION: SE REALIZA CAMBIO DE ESTRATO " + observaciones + ";"
                + " TIPO DOCUMENTO SPORTE: " + tipoDocSoporte;
        direccion = streetName + houseNumber + apartmentNumber;

        DireccionRREntity direccionRREntityCES = new DireccionRREntity(streetName, houseNumber, apartmentNumber);
        direccionRREntityCES.setComunidad(comunidad);
        direccionRREntityCES.setDivision(division);
        if (!WorkOrderEnCurso(comunidad, division, direccionRREntityCES)) {
            if (cambioEstratoRR(comunidad, division, nuevoEstrato, direccionRREntityCES)) {
                if (agregarNotasF8(comunidad, division, nota,
                        direccionRREntityCES.getCalle(),
                        direccionRREntityCES.getNumeroUnidad(),
                        direccionRREntityCES.getNumeroApartamento(),
                        solicitud, usuario)) {
                    return true;
                }
            }
        } else {
            String mensaje = "El HHPP posee ordenes de trabajo activas en este momento";
            mensajesRespuestaRR.add(mensaje);
            respuestaXhhpp.put(direccion, mensajesRespuestaRR);
            throw new ApplicationException("El HHPP posee ordenes de trabajo activas en este momento");
        }
        return false;
    }

    public boolean WorkOrderEnCurso(String comunidad, String division, DireccionRREntity dirRREntity) throws ApplicationException {
        try {
            boolean result = true;
            RequestQueryRegularUnit requestQueryRegularUnit = new RequestQueryRegularUnit();
            requestQueryRegularUnit.setApartmentNumber(dirRREntity.getNumeroApartamento());
            requestQueryRegularUnit.setCommunity(comunidad);
            requestQueryRegularUnit.setDivision(division);
            requestQueryRegularUnit.setHouseNumber(dirRREntity.getNumeroUnidad());
            requestQueryRegularUnit.setStreetName(dirRREntity.getCalle());

            PortManager portManager = new PortManager(wsURL, wsService);
            QueryRegularUnitResponse regularUnitResponse;
            try {
                regularUnitResponse = portManager.queryRegularUnit(requestQueryRegularUnit);
            } catch (ApplicationException e) {
                regularUnitResponse = portManager.queryRegularUnit(requestQueryRegularUnit);
                LOGGER.error("Error al intentar obtener las ordenes de trabajo, se consume nuevamente el servicio " + e.getMessage());
            }

            if (regularUnitResponse != null && regularUnitResponse.getQueryRegularUnitManager() != null) {
                if (regularUnitResponse.getQueryRegularUnitManager().getMessageText() != null
                        && regularUnitResponse.getQueryRegularUnitManager().getMessageText().contains("(XOR1000)")) {
                    if (regularUnitResponse.getQueryRegularUnitManager().getWONO() != null && !regularUnitResponse.getQueryRegularUnitManager().getWONO().trim().isEmpty()) {
                        result = true;
                    } else {
                        result = false;
                        mensajesRespuestaRR.add(regularUnitResponse.getQueryRegularUnitManager().getMessageText());
                        respuestaXhhpp.put(direccion, mensajesRespuestaRR);
                    }
                } else {
                    String mensaje = "Se presentó error al consultar las ordenes de trabajo: " + regularUnitResponse.getQueryRegularUnitManager().getMessageText();
                    mensajesRespuestaRR.add(mensaje);
                    respuestaXhhpp.put(direccion, mensajesRespuestaRR);
                    throw new ApplicationException("Se presentó error al consultar las ordenes de trabajo: " + regularUnitResponse.getQueryRegularUnitManager().getMessageText());
                }
            } else {
                String mensaje = "Error al intentar obtener las ordenes de trabajo.";
                mensajesRespuestaRR.add(mensaje);
                respuestaXhhpp.put(direccion, mensajesRespuestaRR);
                throw new ApplicationException("Error al intentar obtener las ordenes de trabajo.");
            }
            return result;

        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de agregar Nota F18. EX000: " + e.getMessage(), e);
        }
    }

    private boolean getInfoHHPP(String comunidad, String division, DireccionRREntity dirRREntity) throws ApplicationException {
        boolean result = false;
        try {

            RequestQueryRegularUnit requestQueryRegularUnit = new RequestQueryRegularUnit();
            requestQueryRegularUnit.setApartmentNumber(dirRREntity.getNumeroApartamento());
            requestQueryRegularUnit.setCommunity(comunidad);
            requestQueryRegularUnit.setDivision(division);
            requestQueryRegularUnit.setHouseNumber(dirRREntity.getNumeroUnidad());
            requestQueryRegularUnit.setStreetName(dirRREntity.getCalle());

            PortManager portManager = new PortManager(wsURL, wsService);
            QueryRegularUnitResponse regularUnitResponse;
            try {
                regularUnitResponse = portManager.queryRegularUnit(requestQueryRegularUnit);
            } catch (Exception e) {
                regularUnitResponse = portManager.queryRegularUnit(requestQueryRegularUnit);
                LOGGER.error("Error al intentar obtener la info del HHPP");
            }

            String strMessage = regularUnitResponse.getQueryRegularUnitManager().getMessageText();
            if (strMessage.contains("HOME")) {
                result = true;
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de obtener la información del HHPP. EX000: " + e.getMessage(), e);
        }
        return result;
    }

    private boolean getInfoHHPP(String comunidad, String division, String calle, String unidad, String apto) throws ApplicationException {
        boolean result = false;
        try {
            RequestQueryRegularUnit requestQueryRegularUnit = new RequestQueryRegularUnit();
            requestQueryRegularUnit.setCommunity(comunidad);
            requestQueryRegularUnit.setDivision(division);
            requestQueryRegularUnit.setHouseNumber(unidad);
            requestQueryRegularUnit.setStreetName(calle);
            requestQueryRegularUnit.setApartmentNumber(apto);

            PortManager portManager = new PortManager(wsURL, wsService);
            QueryRegularUnitResponse regularUnitResponse;
            try {
                regularUnitResponse = portManager.queryRegularUnit(requestQueryRegularUnit);
            } catch (ApplicationException e) {
                regularUnitResponse = portManager.queryRegularUnit(requestQueryRegularUnit);
                LOGGER.error("Error al intentar obtener la info del HHPP", e);
            }

            String strMessage = regularUnitResponse.getQueryRegularUnitManager().getMessageText();
            if (strMessage.contains("HOME")) {
                result = true;
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de obtener la información del HHPP. EX000: " + e.getMessage(), e);
        }
        return result;
    }

    public boolean cambioEstratoRR(String comunidad, String division, String nuevoEstrato, DireccionRREntity dirRREntity) throws ApplicationException {
        try {
            AddUnitRequest addUnitRequest = new AddUnitRequest();
            Date toDay = new Date();
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            addUnitRequest.setAuditCompletedDate(df.format(toDay));
            addUnitRequest.setCommunity(comunidad);
            addUnitRequest.setDivision(division);
            addUnitRequest.setGridPosition(comunidad);
            addUnitRequest.setApartmentNumber(dirRREntity.getNumeroApartamento());
            addUnitRequest.setHouseNumber(dirRREntity.getNumeroUnidad());
            addUnitRequest.setStreetName(dirRREntity.getCalle());

            if (nuevoEstrato.equalsIgnoreCase("-1")) {
                addUnitRequest.setStratus(co.com.claro.mgl.utils.Constant.NO_GEOREFENCIADO);
            } else if (nuevoEstrato.equalsIgnoreCase("0")) {
                addUnitRequest.setStratus(co.com.claro.mgl.utils.Constant.NO_RESIDENCIAL);
                addUnitRequest.setUnitType("K");
            } else {
                addUnitRequest.setStratus(nuevoEstrato);
            }
            //cuando realizamos un cambio de estrato asignamos black list "ESO" al hhpp
            addUnitRequest.setTypeRequest(Constantes.TYPE_REQUEST_MODIFYUNIT);

            PortManager portManager = new PortManager(wsURL, wsService);
            UnitManagerResponse respuesta;
            try {
                respuesta = portManager.addUnit(addUnitRequest);
            } catch (ApplicationException e) {
                LOGGER.error("Error al intentar cambiar el estrato, se consume nuevamente el servicio " + e.getMessage());
                respuesta = portManager.addUnit(addUnitRequest);
            }

            if (respuesta.getResponseAddUnit().getMessageText().contains("CAB0074")) {
                return true;
            } else {
                String mensaje = "Se presento error al actualizar el estrato: " + respuesta.getResponseAddUnit().getMessageText();
                mensajesRespuestaRR.add(mensaje);
                respuestaXhhpp.put(direccion, mensajesRespuestaRR);
                throw new ApplicationException("Se presento error al actualizar el estrato: " + respuesta.getResponseAddUnit().getMessageText());
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de cambiar el estrato en RR. EX000: " + e.getMessage(), e);
        }
    }

    public DireccionRREntity reactivarHHPPRR(String houseNumber,
            String streetName,
            String apartmentNumber,
            String comunidad, String division, String nodo,
            String numSolicitud, String usuarioSol,
            String tipoSol, String observaciones) throws ApplicationException {
        DireccionRREntity result = new DireccionRREntity();
        result.setResptRegistroHHPPRR(false);
        try {
            result.setCalle(streetName);
            result.setNumeroUnidad(houseNumber);
            result.setNumeroApartamento(apartmentNumber);
            result.setComunidad(comunidad);
            result.setDivision(division);

            StringBuilder nota = new StringBuilder();
            nota.append("REACTIVACION DE HHPP; ");
            nota.append("NODO REAL: ");
            nota.append(nodo);
            nota.append("; SOLICITUD: ");
            nota.append(numSolicitud);
            nota.append("; SOLICITANTE: ");
            nota.append(usuarioSol);
            nota.append("; CANAL: ");
            nota.append(tipoSol);
            nota.append("; OBSERVACION: ");
            nota.append(observaciones);

            AddUnitRequest unitRequest = new AddUnitRequest();
            unitRequest.setCommunity(comunidad);
            unitRequest.setDivision(division);

            unitRequest.setApartmentNumber(apartmentNumber);
            unitRequest.setStreetName(streetName);
            unitRequest.setHouseNumber(houseNumber);
            unitRequest.setPlantLocation(nodo);
            unitRequest.setUnitStatus("E");
            unitRequest.setTypeRequest("MODIFYUNIT");

            PortManager portManager = new PortManager(wsURL, wsService);
            UnitManagerResponse respuesta;

            try {
                respuesta = portManager.addUnit(unitRequest);
            } catch (ApplicationException e) {
                respuesta = portManager.addUnit(unitRequest);
            }

            if (respuesta.getResponseAddUnit().getMessageText().contains("CAB0074")) {
                //si el HHPP ha sido modificado
                result.setResptRegistroHHPPRR(true);
            } else {
                throw new ApplicationException("No fue posible reactivar el HHPP: "
                        + respuesta.getResponseAddUnit().getMessageText());
            }

            if (result.isResptRegistroHHPPRR()) {
                result.setResptRegistroHHPPRR(
                        agregarNotasF8(comunidad, division, nota.toString(),
                                streetName, houseNumber, apartmentNumber,
                                numSolicitud, usuarioSol));
                if (!result.isResptRegistroHHPPRR()) {
                    throw new ApplicationException("EX001-No fue posible agregar las"
                            + " notas al reactivar el HHPP: ");
                }
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de reactivar el HHPP en RR. EX000: " + e.getMessage(), e);
        }
        return result;
    }

    public String cambioEstadoNodoHHPP(String houseNumber, String streetName, String apartmentNumber,
            String comunidad, String division, String nodo, String estado, String tipoSol,
            String solicitud, String usuario, String tipoSolicitud, HhppMgl hhppMglCambia) throws ApplicationException {

        String result;
        String nota = "SE REALIZA CAMBIO ";
        HhppMgl hhppMgl;
        CmtTipoBasicaMglManager cmtTipoBasicaMglManager = new CmtTipoBasicaMglManager();

        cmtTipoBasicaMgl = cmtTipoBasicaMglManager.findByCodigoInternoApp(
                co.com.claro.mgl.utils.Constant.TIPO_BASICA_ESTADOS_HHPP);
        NodoMglManager nodoMglManager = new NodoMglManager();
        CmtComunidadRrManager comunidadRrManager = new CmtComunidadRrManager();
        CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();

        try {
            AddUnitRequest unitRequest = new AddUnitRequest();
            unitRequest.setCommunity(comunidad);
            unitRequest.setDivision(division);
            //sólo N P D E
            unitRequest.setApartmentNumber(apartmentNumber);
            unitRequest.setStreetName(streetName);
            unitRequest.setHouseNumber(houseNumber);
            unitRequest.setTypeRequest("MODIFYUNIT");
            HhppMglManager hhppmanager = new HhppMglManager();
            Hhpp hhpp = new Hhpp();
            hhpp.setAptoRR(apartmentNumber);
            hhpp.setCalleRR(streetName);
            hhpp.setUnidadRR(houseNumber);
            hhpp.setComunidadRR(comunidad);
            hhpp.setDivisionRR(division);

            if (hhppMglCambia != null) {
                hhppMgl = hhppMglCambia;
            } else {
                hhppMgl = hhppmanager.findHhppByRRFields(hhpp);
            }

            if (tipoSol != null && tipoSol.contains("4")) {
                unitRequest.setPlantLocation(nodo);
                String newState;
                if (nodo.contains(NFI)) {
                    newState = "N";
                } else {
                    newState = "E";
                }
                unitRequest.setUnitStatus(newState);
                nota += "DE NODO A " + nodo + " ";
                String estNewHom;
                EstadoHhppMgl newStateEstadoHhppMgl = new EstadoHhppMgl();
                estNewHom = cmtBasicaMglManager.findCodigoOrExtEstHhpp(cmtTipoBasicaMgl, newState, true);
                newStateEstadoHhppMgl.setEhhID(estNewHom);
                hhppMgl.setEhhId(newStateEstadoHhppMgl);
                hhppMgl.setHhpEstadoUnit(newState);

                CmtComunidadRr cmtComunidadRr = comunidadRrManager.findComunidadByCodigo(comunidad);
                NodoMgl nodoMgl = nodoMglManager.findByCodigoAndComunidadRR(nodo, cmtComunidadRr);
                hhppMgl.setNodId(nodoMgl);
                hhppMgl.setHhpUltUbicacion(nodoMgl.getNodCodigo());
            }
            if (tipoSol != null && tipoSol.contains("3")) {
                unitRequest.setUnitStatus(estado);
                String estHom;
                nota += "DE ESTADO A " + estado;
                EstadoHhppMgl estadoHhppMgl = new EstadoHhppMgl();
                estHom = cmtBasicaMglManager.findCodigoOrExtEstHhpp(cmtTipoBasicaMgl, estado, true);
                estadoHhppMgl.setEhhID(estHom);
                hhppMgl.setEhhId(estadoHhppMgl);
            }

            nota += " SOLICITUD: " + solicitud
                    + " SOLICITANTE: " + usuario
                    + " CANAL: " + tipoSolicitud;
            PortManager portManager = new PortManager(wsURL, wsService);
            UnitManagerResponse respuesta;

            try {
                respuesta = portManager.addUnit(unitRequest);
            } catch (ApplicationException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg);
                respuesta = portManager.addUnit(unitRequest);
            }

            if (respuesta.getResponseAddUnit().getMessageText().contains("CAB0074")) {
                //si el HHPP ha sido modificado
                hhppmanager.update(hhppMgl);
            } else {
                result = respuesta.getResponseAddUnit().getMessageText();
                return result;
            }
            if (agregarNotasF8(comunidad, division, nota,
                    streetName,
                    houseNumber,
                    apartmentNumber,
                    solicitud, usuario)) {
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de cambiar el estado del nodo del HHPP. EX000: " + e.getMessage(), e);
        }
        return null;
    }

    /**
     * Crea una llamada de Servicio.Permite crear una llamada de servicio sobre
     * RR para un HHPP.
     *
     * @author Johnnatan Ortiz
     * @param houseNumber Placa del HHPP.
     * @param streetName Calle del HHPP.
     * @param apartmentNumber Apartamento del HHPP.
     * @param comunidad comunidad del HHPP.
     * @param division division del HHPP.
     * @param nodo nodo para cambiar el nodo al HHPP.
     * @return el cambio fue ejecutado.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public String cambioNodoHHPP(String houseNumber, String streetName, String apartmentNumber,
            String comunidad, String division, String nodo) throws ApplicationException {
        String result;
        try {
            AddUnitRequest unitRequest = new AddUnitRequest();

            unitRequest.setCommunity(comunidad);
            unitRequest.setDivision(division);
            unitRequest.setApartmentNumber(apartmentNumber);
            unitRequest.setStreetName(streetName);
            unitRequest.setHouseNumber(houseNumber);

            unitRequest.setPlantLocation(nodo);
            unitRequest.setTypeRequest("MODIFYUNIT");
            PortManager portManager = new PortManager(wsURL, wsService);
            UnitManagerResponse respuesta = portManager.addUnit(unitRequest);

            if (respuesta.getResponseAddUnit().getMessageText().contains("CAB0074")) {
                result = respuesta.getResponseAddUnit().getMessageText();
            } else {
                result = respuesta.getResponseAddUnit().getMessageText();
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de cambiar el nodo del HHPP. EX000: " + e.getMessage(), e);
        }
        return result;
    }

    public boolean borrarHHPPRR(String comunidad, String division, String apartamento, String calle, String numeroUnidad) {
        try {
            AddUnitRequest unitRequest = new AddUnitRequest();
            unitRequest.setCommunity(comunidad);
            unitRequest.setDivision(division);

            unitRequest.setApartmentNumber(apartamento);
            unitRequest.setStreetName(calle);
            unitRequest.setHouseNumber(numeroUnidad);
            unitRequest.setTypeRequest("DELETEUNIT");
            PortManager portManager = new PortManager(wsURL, wsService);
            UnitManagerResponse respuesta;

            try {
                respuesta = portManager.addUnit(unitRequest);
            } catch (ApplicationException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg);
                respuesta = portManager.addUnit(unitRequest);
            }

            return respuesta.getResponseAddUnit().getMessageText().contains("CAB0076")//CAB0076  Cuando la borra // CAB0075
                    ; //si el HHPP ha sido creado se retorna el valor del id de RR
        } catch (ApplicationException e) {
            LOGGER.error("Error al momento de eliminar el HHPP de RR. EX000: " + e.getMessage(), e);
            return false;
        }
    }

    public String eliminarHHPPRR(String comunidad, String division, String calle,
            String placa, String apartamento, String estado) throws ApplicationException {
        try {
            AddUnitRequest deleteRequest = new AddUnitRequest();
            deleteRequest.setCommunity(comunidad);
            deleteRequest.setDivision(division);
            deleteRequest.setApartmentNumber(apartamento);
            deleteRequest.setStreetName(calle);
            deleteRequest.setHouseNumber(placa);
            deleteRequest.setUnitStatus(estado);
            deleteRequest.setTypeRequest("DELETEUNIT");

            PortManager portManager = new PortManager(wsURL, wsService);
            UnitManagerResponse respuesta;

            try {
                respuesta = portManager.addUnit(deleteRequest);
            } catch (ApplicationException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg);
                respuesta = portManager.addUnit(deleteRequest);
            }

            if (respuesta.getResponseAddUnit().getMessageText().contains("CAB0076")
                    && !respuesta.getResponseAddUnit().getAkyn().trim().isEmpty()) {
                return respuesta.getResponseAddUnit().getAkyn();
            } else {
                return "Error RR >> " + respuesta.getResponseAddUnit().getAkyn();
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de eliminar el HHPP de RR. EX000: " + e.getMessage(), e);
        }
    }

    public String getInfoRegularHHPP(String comunidad, String division, String apartamento, String calle, String numeroUnidad) throws ApplicationException {
        String strMessage;
        try {
            RequestQueryRegularUnit requestQueryRegularUnit = new RequestQueryRegularUnit();
            requestQueryRegularUnit.setApartmentNumber(apartamento);
            requestQueryRegularUnit.setCommunity(comunidad);
            requestQueryRegularUnit.setDivision(division);
            requestQueryRegularUnit.setHouseNumber(numeroUnidad);
            requestQueryRegularUnit.setStreetName(calle);

            PortManager portManager = new PortManager(wsURL, wsService);
            QueryRegularUnitResponse regularUnitResponse;
            try {
                regularUnitResponse = portManager.queryRegularUnit(requestQueryRegularUnit);
            } catch (ApplicationException e) {
                regularUnitResponse = portManager.queryRegularUnit(requestQueryRegularUnit);
                LOGGER.error("Error al intentar obtener la info del HHPP");
            }
            strMessage = regularUnitResponse.getQueryRegularUnitManager().getMessageText();
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de obtener la información regular del HHPP. EX000: " + e.getMessage(), e);
        }
        return strMessage;
    }

    /* Pruba cracion 800HHPP en RR***/
    public String rHRs(String args, String placa, String calle) {
        String respuesta = registrarHHPPRR800(calle, placa, args, "N");
        return respuesta;
    }

    private String registrarHHPPRR800(String calle, String unidad, String apto, String estadoHhpp) {
        try {
            String wsURLL = "http://172.31.3.61:8080/UnitService2/";
            String wsServiceL = "management";
            String comunidad = "PER";
            String division = "REC";
            //Creacion HHPP BI-Direccional
            AddUnitRequest unitRequest = new AddUnitRequest();
            //estado unidad (S/N)      
            unitRequest.setUnitStatus(estadoHhpp);
            //Estrato
            unitRequest.setStratus("3");
            unitRequest.setUnitType("A");

            unitRequest.setDropType("A");//Tipo Acometida
            unitRequest.setDropTypeCable("11");//Tipo Cable Acometida

            //Comuni
            unitRequest.setGridPosition(comunidad);
            unitRequest.setHeadEnd("04");
            //Tipo nodo Siempre es ND
            unitRequest.setPlantLocType("ND");
            //Se asigna el nodo
            unitRequest.setPlantLocation("PQ21");//TODO: obtener location

            unitRequest.setPostalCode("006");

            unitRequest.setCommunity(comunidad);
            unitRequest.setDivision(division);

            unitRequest.setDealer("9999");
            Date toDay = new Date();
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            unitRequest.setAuditCompletedDate(df.format(toDay));

            unitRequest.setApartmentNumber(apto);
            unitRequest.setStreetName(calle);
            unitRequest.setHouseNumber(unidad);

            unitRequest.setTypeRequest("ADDUNIT");

            PortManager portManager = new PortManager(wsURLL, wsServiceL);
            UnitManagerResponse respuesta = portManager.addUnit(unitRequest);

            if (respuesta.getResponseAddUnit().getMessageText().contains("CAB0075")) {
                //si el HHPP ha sido creado se retorna el valor del id de RR
                return "OK";
            } else {
                return "MAL";
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error al momento de registrar el HHPP en RR. EX000: " + e.getMessage(), e);
            return "MAL";
        }
    }

    private void SimpleDateFormat(String yyyyMMdd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public DetalleDireccionEntity getDireccionEntity() {
        return direccionEntity;
    }

    public void setDireccionEntity(DetalleDireccionEntity direccionEntity) {
        this.direccionEntity = direccionEntity;
    }

    /**
     * cambioEstadoHhppRR Permite realizar la actualizacion de estado del HHPP
     * segun lo que benga en el Ehh del objeto hhpp
     *
     *
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     * @param hhppMgl informacion del hhpp que se quiere modificar tiene que
     * venir con el estado a modificar
     */
    public void cambioEstadoHhppRR(HhppMgl hhppMgl, String estadoRr, String estadoMER, String usuario) {
        try {

            //(CAB0065) EN RR Solamente cambios entre "N", "P", "D", y "E" son permitidos.
            if (hhppMgl.getHhpIdrR() != null && !hhppMgl.getHhpIdrR().isEmpty()) {

                HhppResponseRR hhppResponseRR = new HhppResponseRR();
                //Consume servicio que busca hhpp por Id de RR
                hhppResponseRR = getHhppByIdRR(hhppMgl.getHhpIdrR());

                if (hhppResponseRR != null && hhppResponseRR.getTipoMensaje().equalsIgnoreCase("I")) {

                    HhppMglManager hhppMglManager = new HhppMglManager();
                    MarcasMglDaoImpl marcasMglDaoImpl = new MarcasMglDaoImpl();
                    AddUnitRequest addUnitRequest = new AddUnitRequest();
                    addUnitRequest.setTypeRequest(Constantes.TYPE_REQUEST_MODIFYUNIT);

                    addUnitRequest.setCommunity(hhppResponseRR.getComunidad());
                    addUnitRequest.setDivision(hhppResponseRR.getDivision());
                    addUnitRequest.setApartmentNumber(hhppResponseRR.getApartamento());
                    addUnitRequest.setStreetName(hhppResponseRR.getStreet());
                    addUnitRequest.setHouseNumber(hhppResponseRR.getHouse());
                    addUnitRequest.setUnitStatus(estadoRr);

                    //Consulta la abreviatura del estado Con servicio "CS"
                    HhppMgl hhpp = hhppMglManager.findById(hhppMgl.getHhpId());
                    //Valida si es necesario o no enviar el Codigo MFS de BlackList a RR 
                    MarcasMgl uniteCode = null;
                    if (estadoMER.equals("CS")) {
                        //Al pasar de un estado siguiente a cs (SU, DF, D) esta linea borraba las marcas anteriores
                        uniteCode = marcasMglDaoImpl.findMarcasMglByCodigo("MFS");
                    }

                    PortManager portManager = new PortManager(wsURL, wsService);
                    UnitManagerResponse unitManagerResponse;

                    try {
                        unitManagerResponse = portManager.addUnit(addUnitRequest);

                        if (unitManagerResponse != null
                                && unitManagerResponse.getResponseAddUnit().getMessageType().equals("I")
                                && estadoMER.equals("CS")) {

                            MarcasHhppMglManager marcasHhppMglManager = new MarcasHhppMglManager();
                            MarcasMgl HSV = marcasMglDaoImpl.findMarcasMglByCodigo("HSV");
                            //Verificamos que el HHPP tenga la marca HSV; Marca con que nace cuando se crea.
                            if (!(marcasHhppMglManager.findMarcasHhppByHhppAndMarca(hhpp, HSV).size() > 0)) {
                                marcasHhppMglManager.asignarMarcaHhppMgl(hhpp, new MarcasHhppMgl(), HSV.getMarCodigo(), usuario);
                            }
                            List<MarcasMgl> listaMarcasMgl = new ArrayList<>();
                            //Agrega la nueva marca a la lista en la primera posicion (Marca que quedara como UniteProblemCode Actual)
                            listaMarcasMgl.add(uniteCode);
                            //Traer los datos anteriores
                            listaMarcasMgl.addAll(marcasMglDaoImpl.findMarcasMglByHhpp(hhppMgl));
                            //Se agregan las marcas
                            hhppMglManager.agregarMarcasHhpp(hhpp, listaMarcasMgl, usuario);

                        }

                    } catch (ApplicationException e) {
                        LOGGER.error("Error al intentar cambiar el estado, se consume nuevamente el servicio " + e.getMessage());
                        unitManagerResponse = portManager.addUnit(addUnitRequest);
                        if (unitManagerResponse != null
                                && unitManagerResponse.getResponseAddUnit().getMessageType().equals("I")
                                && estadoMER.equals("CS")) {

                            MarcasHhppMglManager marcasHhppMglManager = new MarcasHhppMglManager();
                            MarcasMgl HSV = marcasMglDaoImpl.findMarcasMglByCodigo("HSV");
                            //Verificamos que el HHPP tenga la marca HSV; Marca con que nace cuando se crea.
                            if (!(marcasHhppMglManager.findMarcasHhppByHhppAndMarca(hhpp, HSV).size() > 0)) {
                                marcasHhppMglManager.asignarMarcaHhppMgl(hhpp, new MarcasHhppMgl(), HSV.getMarCodigo(), usuario);
                            }
                            List<MarcasMgl> listaMarcasMgl = new ArrayList<>();
                            //Agrega la nueva marca a la lista en la primera posicion (Marca que quedara como UniteProblemCode Actual)
                            listaMarcasMgl.add(uniteCode);
                            //Traer los datos anteriores
                            listaMarcasMgl.addAll(marcasMglDaoImpl.findMarcasMglByHhpp(hhpp));
                            //Se agregan las marcas
                            hhppMglManager.agregarMarcasHhpp(hhpp, listaMarcasMgl, usuario);

                        }
                    }
                    if (unitManagerResponse != null && unitManagerResponse.getResponseAddUnit() != null
                            && unitManagerResponse.getResponseAddUnit().getMessageType().equals("I")) {
                        LOGGER.info("Se realizo el cambio de estado en el HHPP RR " + unitManagerResponse.getResponseAddUnit().getMessageText());
                    } else {
                        LOGGER.info("Se genero un error modificando el estado en HHPP RR " + unitManagerResponse.getResponseAddUnit().getMessageText());
                    }
                }
            }

        } catch (ApplicationException e) {
            LOGGER.error("Error al momento de cambiar el estado del HHPP en RR. EX000: " + e.getMessage(), e);
        }
    }

    /**
     * editarMarcasHhppRR Permite por medio de una lista de marcas, actualizar
     * el hhpp en RR
     *
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     * @param hhppMgl hhpp que se quiere actualizar
     * @param marcasMgls lista de marcas las cuales van a quedar en el hhpp
     */
    public void editarMarcasHhppRR(HhppMgl hhppMgl, List<MarcasMgl> marcasMgls)
            throws ApplicationException {
        AddUnitRequest addUnitRequest = new AddUnitRequest();
        addUnitRequest.setTypeRequest(Constantes.TYPE_REQUEST_MODIFYUNIT);

        HhppResponseRR responseHhppRR = getHhppByIdRR(hhppMgl.getHhpIdrR());
        if (responseHhppRR.getTipoMensaje() != null
                && responseHhppRR.getTipoMensaje().equalsIgnoreCase("I")) {

            addUnitRequest.setCommunity(responseHhppRR.getComunidad());
            addUnitRequest.setDivision(responseHhppRR.getDivision());
            addUnitRequest.setApartmentNumber(responseHhppRR.getApartamento());
            addUnitRequest.setStreetName(responseHhppRR.getStreet());
            addUnitRequest.setHouseNumber(responseHhppRR.getHouse());

        } else {
            addUnitRequest.setCommunity(hhppMgl.getNodId().getComId().getCodigoRr());
            addUnitRequest.setDivision(hhppMgl.getNodId().getComId().getRegionalRr().getCodigoRr());
            addUnitRequest.setApartmentNumber(hhppMgl.getHhpApart());
            addUnitRequest.setStreetName(hhppMgl.getHhpCalle());
            addUnitRequest.setHouseNumber(hhppMgl.getHhpPlaca());

        }

        //Iteracion  de marcas
        String textoMarcas = "";
        String textoMarcasTmp = "";
        boolean fraude = false;
        boolean fraudeTmp = false;
        TecTipoMarcas tipoMarcaId;
        BigDecimal tipoMarca;
        String marcaFraude = "";

        for (MarcasMgl marcasMgl : marcasMgls) {

            int tamañoCodigo = marcasMgl.getMarCodigo().length();
            int tam = 3 - tamañoCodigo;
            textoMarcasTmp = marcasMgl.getMarCodigo();
            tipoMarcaId = marcasMgl.getTmaId();
            tipoMarca = tipoMarcaId.getTipoMarcasId();
            if (Integer.parseInt(co.com.claro.mgl.utils.Constant.TIPO_MARCA_FRAUDE_HHPP) == tipoMarca.intValue()) {

                fraudeTmp = true;
                fraude = true;
            }
            if (tam != 0) {
                for (int i = 0; i < tam; i++) {
                    textoMarcasTmp += " ";
                }
            }
            if (fraudeTmp) {
                marcaFraude += textoMarcasTmp;
            } else {
                textoMarcas += textoMarcasTmp;
            }
            fraudeTmp = false;
        }

        if (fraude) {
            marcaFraude += textoMarcas;
            addUnitRequest.setProblemUnitCodes(marcaFraude.trim());
        } else {
            addUnitRequest.setProblemUnitCodes(textoMarcas.trim());
        }

        PortManager portManager = new PortManager(wsURL, wsService);
        UnitManagerResponse soapMessage;
        soapMessage = portManager.addUnit(addUnitRequest);
        if (soapMessage.getResponseAddUnit().getMessageText().contains("CAB0015")) {
            LOGGER.info("El home pass no fue encontrado en RR: id(" + hhppMgl.getHhpIdrR() + ") "
                    + soapMessage.getResponseAddUnit().getMessageText());
            return;
        }
        if (soapMessage.getResponseAddUnit().getMessageType().equals("I")) {
            LOGGER.info("Se realizo la modificacion de las marcas con exito en RR "
                    + soapMessage.getResponseAddUnit().getMessageText());
        } else {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + "Genero un error realizando la modificacion de las marcas RR "
                    + soapMessage.getResponseAddUnit().getMessageText();
            LOGGER.info(msg);
            throw new ApplicationException(msg);
        }
    }

    /**
     * agregarNotasHhppRR Agregar una lista de notas a un hhpp en RR, las agrega
     * dinamicamente y se conectaa RR
     *
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     * @param hhppMgl Hhpp el cual se le van a agregar las notas
     * @param notasAdicionalesMgls
     * @param marcasMgls Lista de notas que se van a agregar
     */
    public void agregarNotasHhppRR(HhppMgl hhppMgl, List<NotasAdicionalesMgl> notasAdicionalesMgls) {
        try {
            PortManager portManager = new PortManager(wsURL, wsService);

            RequestSpecialUpdate requestSpecialUpdate = new RequestSpecialUpdate();
            requestSpecialUpdate.setTypeRequest(Constantes.TYPE_REQUEST_NOTES_F8);
            requestSpecialUpdate.setComunity(hhppMgl.getHhpComunidad());
            requestSpecialUpdate.setDivision(hhppMgl.getHhpDivision());
            requestSpecialUpdate.setApartmentNumber(hhppMgl.getHhpApart());
            requestSpecialUpdate.setStreetName(hhppMgl.getHhpCalle());
            requestSpecialUpdate.setHomeNumber(hhppMgl.getHhpPlaca());
            //Iteracion  de Notas
            for (NotasAdicionalesMgl notasAdicionalesMgl : notasAdicionalesMgls) {
                requestSpecialUpdate.setLines(notasAdicionalesMgl.getNota());
                SpecialUpdateManagerResponse specialUpdateManagerResponse;
                try {
                    specialUpdateManagerResponse = portManager.specialUpdate(requestSpecialUpdate);
                } catch (ApplicationException e) {
                    LOGGER.error("Error al intentar agregar las notas , se consume nuevamente el servicio " + e.getMessage());
                    specialUpdateManagerResponse = portManager.specialUpdate(requestSpecialUpdate);
                }
                if (specialUpdateManagerResponse != null && specialUpdateManagerResponse.getResponseSpecialUpdate().getMessageType().equals("I")) {
                    LOGGER.info("Se realizo la modificacion de las notas con exito en RR " + specialUpdateManagerResponse.getResponseSpecialUpdate().getMessageText());
                } else {
                    LOGGER.info("Genero un error realizando la modificacion de las notas RR " + specialUpdateManagerResponse.getResponseSpecialUpdate().getMessageText());
                }
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error al momento de agregar notas al HHPP en RR. EX000: " + e.getMessage(), e);
        }
    }

    public DireccionRREntity registrarHHPP_CM(String strNodo, String nodoReal, String usuario,
            String carpeta, String nombreFuncionalidad, String nvlSocioEconomico,
            boolean validarConfiabilidad, String solicitud, String tipoSol,
            CmtComunidadRr cmtComunidadRr, String observaciones, boolean isNodoReal,
            String usuarioSol, String razon, String idUsuario,
            String contacto, String telContacto, String campoUno,
            BigDecimal idCentroPobladoGpo, boolean sincronizaRR) throws ApplicationException {

        NodoMglManager nodoMglManager = new NodoMglManager();
        CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();
        try {
            DireccionRREntity entityRR = new DireccionRREntity();
            entityRR.setCalle(direccionRREntity.getCalle());
            entityRR.setNumeroUnidad(direccionRREntity.getNumeroUnidad());
            entityRR.setNumeroApartamento(direccionRREntity.getNumeroApartamento());
            entityRR.setResptRegistroHHPPRR(false);

            if (validarConfiabilidad && !validaPresicion()) {
                entityRR.setResptRegistroHHPPRR(false);
                return entityRR;
            }
            NodoMgl nodoMglAproximado = nodoMglManager.findByCodigo(nodoReal);
            NodoRR nodoAproximadoRR = new NodoRR();
            nodoAproximadoRR.setCodArea(nodoMglAproximado.getAreId() != null ? nodoMglAproximado.getAreId().toString() : null);
            nodoAproximadoRR.setCodDistrito(nodoMglAproximado.getDisId() != null ? nodoMglAproximado.getDisId().toString() : null);
            nodoAproximadoRR.setCodUnidad(nodoMglAproximado.getUgeId() != null ? nodoMglAproximado.getUgeId().toString() : null);
            nodoAproximadoRR.setCodZona(nodoMglAproximado.getZonId() != null ? nodoMglAproximado.getZonId().toString() : null);
            nodoAproximadoRR.setDicDivision(nodoMglAproximado.getDivId() != null ? nodoMglAproximado.getDivId().toString() : null);
            nodoAproximadoRR.setNombre(nodoMglAproximado.getNodNombre() != null ? nodoMglAproximado.getNodNombre() : null);
            nodoAproximadoRR.setEstado(nodoMglAproximado.getEstado() != null ? nodoMglAproximado.getEstado().toString() : null);
            nodoAproximadoRR.setCodigo(nodoMglAproximado.getNodCodigo() != null ? nodoMglAproximado.getNodCodigo() : null);
            nodoAproximadoRR.setCodCiudad(nodoMglAproximado.getComId() != null ? nodoMglAproximado.getComId().getCodigoRr() : null);
            if (nodoMglAproximado.getComId() != null && nodoMglAproximado.getComId().getRegionalRr() != null) {
                nodoAproximadoRR.setCodRegional(nodoMglAproximado.getComId().getRegionalRr() != null
                        ? nodoMglAproximado.getComId().getRegionalRr().getCodigoRr() : null);
            }
            nodoAproximadoRR.setReferencia(nodoMglAproximado.getNodHeadEnd() != null ? nodoMglAproximado.getNodHeadEnd().toString() : null);
            nodoAproximadoRR.setCodEq(nodoMglAproximado.getNodTipo() != null ? nodoMglAproximado.getNodTipo().getNombreBasica() : null);

            Hhpp hhpp = new Hhpp();
            hhpp.setDireccion(detalleDireccion);

            hhpp.setSubDireccion(detalleSubDireccion);
            EstadoHhpp estado = obtenerEstadoHHPP(strNodo, carpeta);

            hhpp.setEstadoHhpp(estado);
            //Ajuste para creacion de HHPP de CM-Constructora.
            if (direccionRREntity.getNumeroApartamento().equalsIgnoreCase("CAMPAMENTO")
                    || direccionRREntity.getNumeroApartamento().equalsIgnoreCase("SALAVENTAS")) {
                String exist = cmtBasicaMglManager.findValorExtendidoEstHhpp(estadoHhppExiste);
                estado = hhppManager.queryEstadoHhpp(exist);//EXIST
                hhpp.setEstadoHhpp(estado);
            }

            Nodo nodo = nodoManager.queryNodo(strNodo, nodoMglAproximado.getGpoId());//VILLAMILC
            nodoMglManager = new NodoMglManager();
            NodoMgl nodoMgl = nodoMglManager.findByCodigoAndCity(strNodo, nodoMglAproximado.getGpoId());

            String comunidad = cmtComunidadRr.getCodigoRr();
            String division = cmtComunidadRr.getRegionalRr().getCodigoRr();

            entityRR.setComunidad(comunidad);
            entityRR.setDivision(division);

            if (nodoMgl != null && nodoMgl.getNodId() != null) {
                if (nodoMgl.getComId() != null && nodoMgl.getComId().getCodigoRr() != null
                        && nodoMgl.getComId().getRegionalRr() != null) {
                    comunidad = nodoMgl.getComId().getCodigoRr();
                    division = nodoMgl.getComId().getRegionalRr().getCodigoRr();
                    entityRR.setComunidad(comunidad);
                    entityRR.setDivision(division);
                }
            }

            if (nodo != null) {
                hhpp.setNodo(nodo);
            }
            String tipoEdificioHHPP = obtenerTipoEdificio(direccionRREntity.getNumeroApartamento(), carpeta, tipoSol);
            TipoHhpp tipoHhpp = new TipoHhpp(tipoEdificioHHPP);
            hhpp.setTipoHhpp(tipoHhpp);
            TipoHhppConexion tipoHhppConexion = new TipoHhppConexion();
            TipoHhppRed tipoHhppRed = new TipoHhppRed();
            switch (carpeta) {
                case UNIDIRECCIONAL:
                    tipoHhppConexion.setThcId(new BigDecimal("2"));//Conexion Temporal
                    tipoHhppRed.setThrId(BigDecimal.ONE);
                    break;
                case VERIFICACION_CASAS:
                    //VERIFICACION_CASAS
                    tipoHhppConexion.setThcId(BigDecimal.ONE);//Conexion Temporal
                    tipoHhppRed.setThrId(new BigDecimal("2"));
                    break;
                case CUENTA_MATRIZ:
                    //VERIFICACION_CASAS
                    tipoHhppConexion.setThcId(new BigDecimal("2"));//Conexion Temporal
                    tipoHhppRed.setThrId(BigDecimal.ONE);
                    break;
                default:
                    break;
            }

            hhpp.setTipoConexionHhpp(tipoHhppConexion);
            hhpp.setTipoRedHhpp(tipoHhppRed);
            hhpp.setMarcas(null);
            hhpp.setHhppUsuarioCreacion(usuario);
            String nota = "";

            if (carpeta.equalsIgnoreCase(UNIDIRECCIONAL) || carpeta.equalsIgnoreCase(VERIFICACION_CASAS)
                    || carpeta.equalsIgnoreCase(CUENTA_MATRIZ)) {
                nota = "NODO REAL: " + nodoReal + "; SOLICITUD: " + solicitud
                        + "; SOLICITANTE: " + usuarioSol + "; CANAL: " + tipoSol
                        + "; OBSERVACION: " + observaciones;
            }

            boolean habilitarRR = false;
            ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
            ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(co.com.claro.mgl.utils.Constant.HABILITAR_RR);
            if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
                habilitarRR = true;
            }
            if (habilitarRR) {
                if (sincronizaRR) {

                    if (direccionRREntity.getNumeroApartamento().equalsIgnoreCase("CAMPAMENTO")) {

                        direccionRREntity.setCalle(direccionRREntity.getCalle() + " " + "TORRE 1");
                    }

                    if (crearStreetHHPPRR(comunidad, division, direccionRREntity.getCalle().toUpperCase())) {

                        if (crearCruceRR(comunidad, division, direccionRREntity.getCalle(), direccionRREntity.getNumeroUnidad(), direccionEntity.getIdtipodireccion())) {

                            String strIdHHPPRR = registrarHHPPRR_CM(hhpp, nvlSocioEconomico, carpeta, comunidad, division, tipoSol,
                                    direccionRREntity.getNumeroApartamento(), direccionRREntity.getCalle(), direccionRREntity.getNumeroUnidad(), razon, cmtComunidadRr);
                            if (strIdHHPPRR != null && !strIdHHPPRR.trim().isEmpty()) {
                                hhpp.setIdRR(strIdHHPPRR);
                                entityRR.setIdHhpp((hhppManager.saveHhppInRepo(hhpp, nombreFuncionalidad)).toString());
                                direccionRREntity.setIdHhpp(entityRR.getIdHhpp());
                                if (agregarNotasF8(comunidad, division, nota,
                                        direccionRREntity.getCalle(),
                                        direccionRREntity.getNumeroUnidad(),
                                        direccionRREntity.getNumeroApartamento(),
                                        solicitud, usuario)) {
                                    if (agregarNotaF18(comunidad, division,
                                            direccionRREntity, detalleDireccion,
                                            campoUno, nodoReal)) {
                                        entityRR.setResptRegistroHHPPRR(true);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    //guarda hhpp en MGL
                    hhpp.setHhppUsuarioCreacion(usuario);
                    if (hhpp.getEstadoHhpp() != null
                            && hhpp.getEstadoHhpp().getEhhNombre() != null) {
                        hhpp.setEstado_unit(hhpp.getEstadoHhpp().getEhhNombre().substring(0, 1));
                    }
                    String tipoEdificio;
                    tipoEdificio = obtenerTipoEdificio(direccionRREntity.getNumeroApartamento(), carpeta, tipoSol);
                    hhpp.setTipo_unidad(tipoEdificio);
                    hhpp.setCalleRR(direccionRREntity.getCalle().toUpperCase());
                    hhpp.setUnidadRR(direccionRREntity.getNumeroUnidad().toUpperCase());
                    hhpp.setAptoRR(direccionRREntity.getNumeroApartamento().toUpperCase());
                    hhpp.setComunidadRR(comunidad);
                    hhpp.setDivisionRR(division);
                    hhpp.setVendedor("9999");
                    CityEntity cityEntity = new CityEntity();
                    cityEntity.setPostalCode(cmtComunidadRr.getCodigoPostal() != null ? cmtComunidadRr.getCodigoPostal() : null);
                    if (cityEntity.getPostalCode() != null) {
                        hhpp.setCodigo_postal(cityEntity.getPostalCode());
                    } else {
                        hhpp.setCodigo_postal("000");
                    }
                    if (carpeta.equals(VERIFICACION_CASAS)) {
                        hhpp.setTipo_acomet("A");//Tipo Acometida
                        hhpp.setTipo_cbl_acometida("11");//Tipo Cable Acometida
                    } else if (carpeta.equals(UNIDIRECCIONAL)
                            || carpeta.equals(CUENTA_MATRIZ)) {
                        hhpp.setTipo_acomet("C");//Tipo Acometida
                        hhpp.setTipo_cbl_acometida("R6");//Tipo Cable Acometida
                    }
                    hhpp.setUlt_ubicacion(hhpp.getNodo() != null ? hhpp.getNodo().getNodCodigo() : null);
                    hhpp.setHead_end((nodoMgl != null && nodoMgl.getNodHeadEnd() != null) == true ? nodoMgl.getNodHeadEnd() : null);
                    hhpp.setTipo("ND");
                    String buildingName = direccionEntity.getBarrio() == null
                            || direccionEntity.getBarrio().trim().isEmpty()
                            || direccionEntity.getBarrio().trim().equalsIgnoreCase("_") ? "NG" : direccionEntity.getBarrio();
                    if (buildingName != null && buildingName.length() > 25) {
                        buildingName = buildingName.substring(0, 25);
                    }
                    hhpp.setEdificio(buildingName != null ? buildingName.trim() : null);
                    BigDecimal hhppId = hhppManager.saveHhppInRepo(hhpp, nombreFuncionalidad);
                    if (hhppId != null) {
                        //se asigna Id del hhpp creado    
                        entityRR.setIdHhpp(hhppId.toString());

                        direccionRREntity.setIdHhpp(entityRR.getIdHhpp());
                        HhppMgl hhppMgl = new HhppMgl();
                        hhppMgl.setHhpId(hhppId);
                        //se agrega marca de Hhpp sin verificación por default
                        MarcasMglManager marcasManager = new MarcasMglManager();
                        MarcasHhppMglManager marcasHhppMglManager = new MarcasHhppMglManager();
                        MarcasMgl marca = marcasManager.findMarcasMglByCodigo(co.com.claro.mgl.utils.Constant.MARCA_HHPP_SIN_VERIFICACION);
                        if (marca != null) {
                            MarcasHhppMgl marcasHhppMgl = new MarcasHhppMgl();
                            marcasHhppMgl.setHhpp(hhppMgl);
                            marcasHhppMgl.setMarId(marca);
                            marcasHhppMgl.setMhhFechaCreacion(new Date());
                            marcasHhppMgl.setMhhUsuarioCreacion("MGL");
                            marcasHhppMgl.setEstadoRegistro(1);
                            marcasHhppMglManager.create(marcasHhppMgl);
                        }

                    }
                }
            } else {
                //guarda hhpp en MGL
                hhpp.setHhppUsuarioCreacion(usuario);
                if (hhpp.getEstadoHhpp() != null
                        && hhpp.getEstadoHhpp().getEhhNombre() != null) {
                    hhpp.setEstado_unit(hhpp.getEstadoHhpp().getEhhNombre().substring(0, 1));
                }
                String tipoEdificio;
                tipoEdificio = obtenerTipoEdificio(direccionRREntity.getNumeroApartamento(), carpeta, tipoSol);
                hhpp.setTipo_unidad(tipoEdificio);
                hhpp.setCalleRR(direccionRREntity.getCalle().toUpperCase());
                hhpp.setUnidadRR(direccionRREntity.getNumeroUnidad().toUpperCase());
                hhpp.setAptoRR(direccionRREntity.getNumeroApartamento().toUpperCase());
                hhpp.setComunidadRR(comunidad);
                hhpp.setDivisionRR(division);
                hhpp.setVendedor("9999");
                CityEntity cityEntity = new CityEntity();
                cityEntity.setPostalCode(cmtComunidadRr.getCodigoPostal() != null ? cmtComunidadRr.getCodigoPostal() : null);
                if (cityEntity.getPostalCode() != null) {
                    hhpp.setCodigo_postal(cityEntity.getPostalCode());
                } else {
                    hhpp.setCodigo_postal("000");
                }
                if (carpeta.equals(VERIFICACION_CASAS)) {
                    hhpp.setTipo_acomet("A");//Tipo Acometida
                    hhpp.setTipo_cbl_acometida("11");//Tipo Cable Acometida
                } else if (carpeta.equals(UNIDIRECCIONAL)
                        || carpeta.equals(CUENTA_MATRIZ)) {
                    hhpp.setTipo_acomet("C");//Tipo Acometida
                    hhpp.setTipo_cbl_acometida("R6");//Tipo Cable Acometida
                }
                hhpp.setUlt_ubicacion(hhpp.getNodo() != null ? hhpp.getNodo().getNodCodigo() : null);
                hhpp.setHead_end((nodoMgl != null && nodoMgl.getNodHeadEnd() != null) == true ? nodoMgl.getNodHeadEnd() : null);
                hhpp.setTipo("ND");
                String buildingName = direccionEntity.getBarrio() == null
                        || direccionEntity.getBarrio().trim().isEmpty()
                        || direccionEntity.getBarrio().trim().equalsIgnoreCase("_") ? "NG" : direccionEntity.getBarrio();
                if (buildingName != null && buildingName.length() > 25) {
                    buildingName = buildingName.substring(0, 25);
                }
                hhpp.setEdificio(buildingName != null ? buildingName.trim() : null);
                BigDecimal hhppId = hhppManager.saveHhppInRepo(hhpp, nombreFuncionalidad);
                if (hhppId != null) {
                    //se asigna Id del hhpp creado    
                    entityRR.setIdHhpp(hhppId.toString());

                    direccionRREntity.setIdHhpp(entityRR.getIdHhpp());
                    HhppMgl hhppMgl = new HhppMgl();
                    hhppMgl.setHhpId(hhppId);
                    //se agrega marca de Hhpp sin verificación por default
                    MarcasMglManager marcasManager = new MarcasMglManager();
                    MarcasHhppMglManager marcasHhppMglManager = new MarcasHhppMglManager();
                    MarcasMgl marca = marcasManager.findMarcasMglByCodigo(co.com.claro.mgl.utils.Constant.MARCA_HHPP_SIN_VERIFICACION);
                    if (marca != null) {
                        MarcasHhppMgl marcasHhppMgl = new MarcasHhppMgl();
                        marcasHhppMgl.setHhpp(hhppMgl);
                        marcasHhppMgl.setMarId(marca);
                        marcasHhppMgl.setMhhFechaCreacion(new Date());
                        marcasHhppMgl.setMhhUsuarioCreacion("MGL");
                        marcasHhppMgl.setEstadoRegistro(1);
                        marcasHhppMglManager.create(marcasHhppMgl);
                    }

                }
            }

            return entityRR;
        } catch (ApplicationException | ExceptionDB ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "'. EX000: " + ex.getMessage(), ex);
        }
    }

    private String registrarHHPPRR_CM(Hhpp hhpp, String nvlSocioEconomico, String aplicacion,
            String comunidad, String division, String tipoSol,
            String aptoRR, String calleRR, String unidadRR, String razon, CmtComunidadRr cmtComunidadRr) throws ApplicationException {

        NodoMglManager nodoMglManager = new NodoMglManager();
        try {
            AddUnitRequest unitRequest = new AddUnitRequest();
            //estado
            String estadoHhpp = hhpp.getEstadoHhpp().getEhhNombre().substring(0, 1);
            unitRequest.setUnitStatus(estadoHhpp);
            hhpp.setEstado_unit(estadoHhpp);

            String tipoEdificio;
            tipoEdificio = obtenerTipoEdificio(aptoRR, aplicacion, tipoSol);
            hhpp.setTipo_unidad(tipoEdificio);
            //Estrato
            String estrato = "";

            if (nvlSocioEconomico != null) {
                estrato = obtenerEstrato(nvlSocioEconomico, tipoSol, tipoEdificio);
            }
            if (cmtComunidadRr != null && cmtComunidadRr.getNombreComunidad() != null) {
                unitRequest.setNeighborhood(cmtComunidadRr.getNombreComunidad()); // FCP Toca verificar el dato exactamete BUG 4987
            }
            unitRequest.setStratus(estrato);
            if (aplicacion.equals(VERIFICACION_CASAS)) {
                unitRequest.setDropType("A");//Tipo Acometida
                unitRequest.setDropTypeCable("11");//Tipo Cable Acometida
                hhpp.setTipo_acomet("A");//Tipo Acometida
                hhpp.setTipo_cbl_acometida("11");//Tipo Cable Acometida
            } else if (aplicacion.equals(UNIDIRECCIONAL)
                    || aplicacion.equals(CUENTA_MATRIZ)) {
                unitRequest.setDropType("C");//Tipo Acometida   
                unitRequest.setDropTypeCable("R6");//Tipo Cable Acometida
                hhpp.setTipo_acomet("C");//Tipo Acometida
                hhpp.setTipo_cbl_acometida("R6");//Tipo Cable Acometida
            }

            unitRequest.setGridPosition(comunidad);
            //TODO: obtener Headend
            NodoMgl nodoMgl = nodoMglManager.findByCodigoAndCity(hhpp.getNodo().getNodCodigo(), hhpp.getNodo().getGeograficoPolitico().getGpoId());

            String dirHeadEnd = "01";

            if (nodoMgl.getNodId() != null) {
                dirHeadEnd = nodoMgl.getNodHeadEnd();
            }

            unitRequest.setHeadEnd(dirHeadEnd);
            hhpp.setHead_end(dirHeadEnd);
            //Tipo nodo Siempre es ND
            unitRequest.setPlantLocType("ND");
            hhpp.setTipo("ND");

            //Se asigna el nodo
            unitRequest.setPlantLocation(hhpp.getNodo().getNodCodigo());//TODO: obtener location
            hhpp.setUlt_ubicacion(hhpp.getNodo().getNodCodigo());

            if (cmtComunidadRr != null) {
                unitRequest.setPostalCode(cmtComunidadRr.getCodigoPostal());
                hhpp.setCodigo_postal(cmtComunidadRr.getCodigoPostal());
            } else {
                unitRequest.setPostalCode("000");
                hhpp.setCodigo_postal("000");
            }

            unitRequest.setCommunity(comunidad);
            unitRequest.setDivision(division);
            hhpp.setComunidadRR(comunidad);
            hhpp.setDivisionRR(division);

            unitRequest.setDealer("9999");
            hhpp.setVendedor("9999");

            Date toDay = new Date();
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            unitRequest.setAuditCompletedDate(df.format(toDay));
            unitRequest.setApartmentNumber(aptoRR);
            unitRequest.setStreetName(calleRR);
            unitRequest.setHouseNumber(unidadRR);
            hhpp.setCalleRR(calleRR);
            hhpp.setUnidadRR(unidadRR);
            hhpp.setAptoRR(aptoRR);

            unitRequest.setUnitType(tipoEdificio);

            String buildingName = direccionEntity.getBarrio() == null
                    || direccionEntity.getBarrio().trim().isEmpty()
                    || direccionEntity.getBarrio().trim().equalsIgnoreCase("_") ? "NG" : direccionEntity.getBarrio();
            if (buildingName != null && buildingName.length() > 25) {
                buildingName = buildingName.substring(0, 25);
            }
            unitRequest.setBuildingName(buildingName.trim());
            hhpp.setEdificio(buildingName.trim());

            unitRequest.setTypeRequest("ADDUNIT");

            if (RESULTADO_VERIFICACION_AGENDA.equalsIgnoreCase(razon)) {
                unitRequest.setProblemUnitCodes("HSO");

            } else if (RESULTADO_HHPP_CREADO.equalsIgnoreCase(razon)) {
                unitRequest.setProblemUnitCodes("HSV");
            }
            PortManager portManager = new PortManager(wsURL, wsService);
            UnitManagerResponse respuesta;

            try {
                respuesta = portManager.addUnit(unitRequest);
            } catch (ApplicationException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg);
                respuesta = portManager.addUnit(unitRequest);
            }

            if (respuesta.getResponseAddUnit().getMessageText().contains("CAB0075")
                    && respuesta.getResponseAddUnit().getAkyn() != null
                    && !respuesta.getResponseAddUnit().getAkyn().trim().isEmpty()) {
                //si el HHPP ha sido creado se retorna el valor del id de RR
                return respuesta.getResponseAddUnit().getAkyn();
            } else if (respuesta.getResponseAddUnit().getMessageText().contains("CAB0420")
                    || respuesta.getResponseAddUnit().getMessageText().contains("CAB1006")) {
                throw new ApplicationException("El HHPP ya ha sido creado anteriormente en RR - "
                        + respuesta.getResponseAddUnit().getMessageText());
            } else {
                throw new ApplicationException(respuesta.getResponseAddUnit().getMessageText());
            }

        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de agregar notas al HHPP en RR. EX000: " + e.getMessage(), e);
        }
    }

    public Map<String, List<String>> retornaMensajes() {

        return respuestaXhhpp;
    }

    /**
     * Cambia de estado un Hhpp en RR inactivandolo.
     *
     * @author Juan David Hernandez
     * @param houseNumber
     * @param streetName
     * @param apartmentNumber
     * @param comunidad
     * @param division
     * @param numSolicitud
     * @param nodo
     * @param usuarioSol
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public DireccionRREntity inactivarHHPPRR(String houseNumber,
            String streetName,
            String apartmentNumber,
            String comunidad, String division, String nodo,
            String numSolicitud, String usuarioSol) throws ApplicationException {
        DireccionRREntity result = new DireccionRREntity();
        result.setResptRegistroHHPPRR(false);
        try {
            result.setCalle(streetName);
            result.setNumeroUnidad(houseNumber);
            result.setNumeroApartamento(apartmentNumber);
            result.setComunidad(comunidad);
            result.setDivision(division);

            StringBuilder nota = new StringBuilder();
            nota.append("INACTIVACION DE HHPP REALIZADO POR PROCESO MASIVO DE MGL; ");

            AddUnitRequest unitRequest = new AddUnitRequest();
            unitRequest.setCommunity(comunidad);
            unitRequest.setDivision(division);

            unitRequest.setApartmentNumber(apartmentNumber);
            unitRequest.setStreetName(streetName);
            unitRequest.setHouseNumber(houseNumber);
            unitRequest.setPlantLocation(nodo);
            unitRequest.setUnitStatus("N");
            unitRequest.setTypeRequest("MODIFYUNIT");

            PortManager portManager = new PortManager(wsURL, wsService);
            UnitManagerResponse respuesta;

            try {
                respuesta = portManager.addUnit(unitRequest);
            } catch (ApplicationException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg);
                respuesta = portManager.addUnit(unitRequest);
            }

            if (respuesta.getResponseAddUnit().getMessageText().contains("CAB0074")) {
                //si el HHPP ha sido modificado
                result.setResptRegistroHHPPRR(true);
            } else {
                throw new ApplicationException("No fue posible inactivar el HHPP: "
                        + respuesta.getResponseAddUnit().getMessageText());
            }

            if (result.isResptRegistroHHPPRR()) {
                result.setResptRegistroHHPPRR(
                        agregarNotasF8(comunidad, division, nota.toString(),
                                streetName, houseNumber, apartmentNumber,
                                numSolicitud, usuarioSol));
                if (!result.isResptRegistroHHPPRR()) {
                    throw new ApplicationException("EX001-No fue posible agregar las"
                            + " notas al inactivar el HHPP: ");
                }
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de inactivar el HHPP en RR. EX000: " + e.getMessage(), e);
        }
        return result;
    }

    /**
     * Metodo para cambiar Direccion al los hhpp de Salaventas y Campamento
     *
     * @author Lenis Cardenas
     * @return
     */
    public DireccionRREntity cambiarDirHHPPRR_CM(String comunidadOld, String divisionOld,
            String houseNumberOld, String streetNameOld, String apartmentNumberOld,
            String comunidadNew, String divisionNew,
            String houseNumberNew, String streetNameNew, String apartmentNumberNew,
            String solicitud, String usuario, String tipoSol, String campoUno,
            String tipoDir, CmtComunidadRr comunidadRegionalRr) throws ApplicationException {

        DireccionRREntity entityRR = new DireccionRREntity();
        String postalCode = "000";
        if (comunidadRegionalRr != null) {
            postalCode = comunidadRegionalRr.getCodigoPostal();
        }
        //creamos la calle de la nueva direccion
        if (crearStreetHHPPRR(comunidadNew, divisionNew, streetNameNew)) {
            //Creamos el cruce de la nueva direccion
            if (crearCruceRR(comunidadNew, divisionNew,
                    streetNameNew, houseNumberNew,
                    tipoDir)) {
                //Cambiamos la direccion del HHPP
                if (cambiarHHPPRR(comunidadOld, divisionOld,
                        houseNumberOld, streetNameOld, apartmentNumberOld,
                        comunidadNew, divisionNew,
                        houseNumberNew, streetNameNew, apartmentNumberNew,
                        postalCode)) {

                }
            }
        }
        return entityRR;
    }

    /**
     * Cambia una dirección en RR desde Carga masiva HHPP
     *
     * @author aleal
     * @param comunidadOld
     * @param divisionOld
     * @param houseNumberOld
     * @param streetNameOld
     * @param apartmentNumberOld
     * @param comunidadNew
     * @param divisionNew
     * @param houseNumberNew
     * @param streetNameNew
     * @param apartmentNumberNew
     * @param tipoDir
     * @param comunidadRegionalRr
     * @param barrio
     */
    public void cambiarDirHHPPRR_Masiva(String comunidadOld, String divisionOld,
            String houseNumberOld, String streetNameOld, String apartmentNumberOld,
            String comunidadNew, String divisionNew, String houseNumberNew,
            String streetNameNew, String apartmentNumberNew, String tipoDir,
            CmtComunidadRr comunidadRegionalRr, String barrio) {

        try {
            String postalCode = "000";
            if (comunidadRegionalRr != null) {
                postalCode = comunidadRegionalRr.getCodigoPostal();
            }
            if (crearStreetHHPPRR(comunidadNew, divisionNew, streetNameNew)) {
                //Creamos el cruce de la nueva direccion
                if (crearCruceRR(comunidadNew, divisionNew, streetNameNew, houseNumberNew,
                        tipoDir)) {
                    //Cambiar Direccion en RR
                    if (cambiarHHPPRR(comunidadOld, divisionOld,
                            houseNumberOld, streetNameOld, apartmentNumberOld,
                            comunidadNew, divisionNew, houseNumberNew,
                            streetNameNew, apartmentNumberNew, postalCode)) {
                        cambiarBarrioRR(apartmentNumberNew, barrio, comunidadNew,
                                divisionNew, houseNumberNew, streetNameNew);
                    }
                }
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error al momento de cambiar la dirección del HHPP en RR masivamente. EX000: " + e.getMessage(), e);
        }
    }

    public void cambiarBarrioRR(String apartmentNumberNew, String barrio, String comunidadNew, String divisionNew, String houseNumberNew, String streetNameNew) throws ApplicationException {
        try {
            AddUnitRequest addUnitRequest = new AddUnitRequest();
            addUnitRequest.setApartmentNumber(apartmentNumberNew);
            addUnitRequest.setBuildingName(barrio);
            addUnitRequest.setCommunity(comunidadNew);
            addUnitRequest.setDivision(divisionNew);
            addUnitRequest.setHouseNumber(houseNumberNew);
            addUnitRequest.setStreetName(streetNameNew);
            addUnitRequest.setTypeRequest(Constantes.TYPE_REQUEST_MODIFYUNIT);

            PortManager portManager = new PortManager(wsURL, wsService);
            try {
                portManager.addUnit(addUnitRequest);
            } catch (ApplicationException e) {
                LOGGER.error("Error al intentar cambiar el barrio HHPP, se consume nuevamente el servicio " + e.getMessage());
                portManager.addUnit(addUnitRequest);
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de cambiar el barrio en RR. EX000: " + e.getMessage(), e);
        }
    }

    /**
     * consulta y obtiene la informacion un hhpp en RR mediante ID de la unidad.
     *
     * @author Juan David Hernandez
     * @param idHhppRR
     * @return HhppResponseRR que contiene la informacion correspondiente al
     * hhpp consultado
     */
    public HhppResponseRR getHhppByIdRR(String idHhppRR) {
        HhppResponseRR result = new HhppResponseRR();
        try {
            if (idHhppRR != null && !idHhppRR.isEmpty()) {
                RequestQueryRegularUnit requestQueryRegularUnit = new RequestQueryRegularUnit();
                QueryRegularUnitResponse queryRegularUnitResponse;
                //Id Hhpp RR
                requestQueryRegularUnit.setUnitNumber(idHhppRR);
                PortManager portManager = new PortManager(wsURL, wsService);
                try {
                    queryRegularUnitResponse = portManager.queryRegularUnit(requestQueryRegularUnit);
                } catch (Exception e) {
                    queryRegularUnitResponse = portManager.queryRegularUnit(requestQueryRegularUnit);
                    LOGGER.error("Error al intentar obtener domicilio en RR por id. " + idHhppRR + " " + e.getMessage());
                }

                //domicilio no encontrado
                if (queryRegularUnitResponse.getQueryRegularUnitManager() != null
                        && queryRegularUnitResponse.getQueryRegularUnitManager().getMessageText().contains("CAB0015")) {
                    result.setMensaje("NO se encontro ningun domicilio con el ID ingresado en RR");
                    result.setTipoMensaje("E");
                    return result;
                } else {
                    //Hhpp existente
                    if (queryRegularUnitResponse.getQueryRegularUnitManager() != null
                            && queryRegularUnitResponse.getQueryRegularUnitManager().getAKYN() != null
                            && !queryRegularUnitResponse.getQueryRegularUnitManager().getAKYN().trim().isEmpty()) {

                        //calle
                        result.setStreet(queryRegularUnitResponse.getQueryRegularUnitManager().getSTNM() != null
                                ? queryRegularUnitResponse.getQueryRegularUnitManager().getSTNM().trim() : null);
                        //Placa
                        result.setHouse(queryRegularUnitResponse.getQueryRegularUnitManager().getHOME() != null
                                ? queryRegularUnitResponse.getQueryRegularUnitManager().getHOME().trim() : null);
                        //Apartamento
                        result.setApartamento(queryRegularUnitResponse.getQueryRegularUnitManager().getAPTN() != null
                                ? queryRegularUnitResponse.getQueryRegularUnitManager().getAPTN().trim() : null);
                        //Comunidad
                        result.setComunidad(queryRegularUnitResponse.getQueryRegularUnitManager().getCCDE() != null
                                ? queryRegularUnitResponse.getQueryRegularUnitManager().getCCDE().trim() : null);
                        //Division
                        result.setDivision(queryRegularUnitResponse.getQueryRegularUnitManager().getDCDE() != null
                                ? queryRegularUnitResponse.getQueryRegularUnitManager().getDCDE().trim() : null);
                        result.setMensaje("Domicilio encontrado de manera exitosa en RR");
                        result.setTipoMensaje("I");

                        return result;
                    } else {
                        result.setMensaje("NO se encontro ningun domicilio en RR con el id " + idHhppRR);
                        result.setTipoMensaje("E");
                        return result;
                    }
                }
            } else {
                result.setMensaje("Debe ingresar el id del domicilio que desea realizar la consulta en RR");
                result.setTipoMensaje("E");
                return result;
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            result.setMensaje("Ocurrio un error intentando consumir el "
                    + "servicio de busqueda de domicilio en RR por id");
            result.setTipoMensaje("E");
            return result;
        }
    }

    /**
     * consulta y obtiene la toda la informacion un hhpp en RR mediante ID de la
     * unidad.
     *
     * @author Orlando Velasquez
     * @param idHhppRR
     * @return HhppResponseRR que contiene toda la informacion retornada por el
     * queryRegularUnit correspondiente al hhpp
     *
     */
    public HhppResponseQueryRegularUnitRR getAllHhppInfoByIdRR(String idHhppRR) {
        HhppResponseQueryRegularUnitRR result = new HhppResponseQueryRegularUnitRR();
        try {
            if (idHhppRR != null && !idHhppRR.isEmpty()) {
                RequestQueryRegularUnit requestQueryRegularUnit = new RequestQueryRegularUnit();
                QueryRegularUnitResponse queryRegularUnitResponse;
                //Id Hhpp RR
                requestQueryRegularUnit.setUnitNumber(idHhppRR);
                PortManager portManager = new PortManager(wsURL, wsService);
                try {
                    queryRegularUnitResponse = portManager.queryRegularUnit(requestQueryRegularUnit);
                } catch (ApplicationException e) {
                    queryRegularUnitResponse = portManager.queryRegularUnit(requestQueryRegularUnit);
                    LOGGER.error("Error al intentar obtener domicilio en RR por id. " + idHhppRR + " " + e.getMessage());
                }

                //domicilio no encontrado
                if (queryRegularUnitResponse.getQueryRegularUnitManager() != null
                        && (queryRegularUnitResponse.getQueryRegularUnitManager().getMessageText().contains("CAB0015")
                        || queryRegularUnitResponse.getQueryRegularUnitManager().getMessageType().equalsIgnoreCase("E")
                        || queryRegularUnitResponse.getQueryRegularUnitManager().getAKYN() == null)) {
                    result.setMensaje("NO se encontro ningun domicilio con el ID ingresado en RR");
                    result.setTipoMensaje("E");
                    return result;
                } else {
                    //Hhpp existente
                    if (queryRegularUnitResponse.getQueryRegularUnitManager() != null
                            && queryRegularUnitResponse.getQueryRegularUnitManager().getAKYN() != null
                            && !queryRegularUnitResponse.getQueryRegularUnitManager().getAKYN().trim().isEmpty()) {

                        //Fecha Auditoria
                        result.setFechaAuditoria(queryRegularUnitResponse.getQueryRegularUnitManager().getAUDD() != null
                                ? queryRegularUnitResponse.getQueryRegularUnitManager().getAUDD().trim() : null);

                        //Edificio
                        result.setEdificio(queryRegularUnitResponse.getQueryRegularUnitManager().getBLDG() != null
                                ? queryRegularUnitResponse.getQueryRegularUnitManager().getBLDG().trim() : null);

                        //Vendedor
                        result.setVendedor(queryRegularUnitResponse.getQueryRegularUnitManager().getDLRC() != null
                                ? queryRegularUnitResponse.getQueryRegularUnitManager().getDLRC().trim() : null);

                        //Tipo Acometida
                        result.setTipoAcometida(queryRegularUnitResponse.getQueryRegularUnitManager().getDROP() != null
                                ? queryRegularUnitResponse.getQueryRegularUnitManager().getDROP().trim() : null);

                        //Tipo Cbl Acometida
                        result.setTipoCblAcometida(queryRegularUnitResponse.getQueryRegularUnitManager().getDRPC() != null
                                ? queryRegularUnitResponse.getQueryRegularUnitManager().getDRPC().trim() : null);

                        //Head End
                        result.setHeadEnd(queryRegularUnitResponse.getQueryRegularUnitManager().getHEND() != null
                                ? queryRegularUnitResponse.getQueryRegularUnitManager().getHEND().trim() : null);

                        //Tipo
                        result.setTipo(queryRegularUnitResponse.getQueryRegularUnitManager().getLTYP() != null
                                ? queryRegularUnitResponse.getQueryRegularUnitManager().getLTYP().trim() : null);

                        //Nodo
                        result.setNodo(queryRegularUnitResponse.getQueryRegularUnitManager().getNODE() != null
                                ? queryRegularUnitResponse.getQueryRegularUnitManager().getNODE().trim() : null);

                        //Ultima Ubicacion
                        result.setUltimaUbicacion(queryRegularUnitResponse.getQueryRegularUnitManager().getPLOC() != null
                                ? queryRegularUnitResponse.getQueryRegularUnitManager().getPLOC().trim() : null);

                        //Codigo Postal
                        result.setCodigoPostal(queryRegularUnitResponse.getQueryRegularUnitManager().getPOST() != null
                                ? queryRegularUnitResponse.getQueryRegularUnitManager().getPOST().trim() : null);

                        //Estrato
                        result.setEstrato(queryRegularUnitResponse.getQueryRegularUnitManager().getSTRA() != null
                                ? queryRegularUnitResponse.getQueryRegularUnitManager().getSTRA().trim() : null);

                        //Tipo Tecnologia Habilitada
                        result.setTipoTecnologiaHabilitada(queryRegularUnitResponse.getQueryRegularUnitManager().getUTYP() != null
                                ? queryRegularUnitResponse.getQueryRegularUnitManager().getUTYP().trim() : null);

                        //calle
                        result.setStreet(queryRegularUnitResponse.getQueryRegularUnitManager().getSTNM() != null
                                ? queryRegularUnitResponse.getQueryRegularUnitManager().getSTNM().trim() : null);
                        //Placa
                        result.setHouse(queryRegularUnitResponse.getQueryRegularUnitManager().getHOME() != null
                                ? queryRegularUnitResponse.getQueryRegularUnitManager().getHOME().trim() : null);
                        //Apartamento
                        result.setApartamento(queryRegularUnitResponse.getQueryRegularUnitManager().getAPTN() != null
                                ? queryRegularUnitResponse.getQueryRegularUnitManager().getAPTN().trim() : null);
                        //Comunidad
                        result.setComunidad(queryRegularUnitResponse.getQueryRegularUnitManager().getCCDE() != null
                                ? queryRegularUnitResponse.getQueryRegularUnitManager().getCCDE().trim() : null);
                        //Division
                        result.setDivision(queryRegularUnitResponse.getQueryRegularUnitManager().getDCDE() != null
                                ? queryRegularUnitResponse.getQueryRegularUnitManager().getDCDE().trim() : null);
                        result.setMensaje("Domicilio encontrado de manera exitosa en RR");
                        result.setTipoMensaje("I");

                        return result;
                    } else {
                        result.setMensaje("NO se encontro ningun domicilio en RR con el id " + idHhppRR);
                        result.setTipoMensaje("E");
                        return result;
                    }
                }
            } else {
                result.setMensaje("Debe ingresar el id del domicilio que desea realizar la consulta en RR");
                result.setTipoMensaje("E");
                return result;
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            result.setMensaje("Ocurrio un error intentando consumir el "
                    + "servicio de busqueda de domicilio en RR por id");
            result.setTipoMensaje("E");
            return result;
        }
    }

    /**
     * cambioTipoUnidad(vivienda)HhppRR Permite realizar la actualizacion de
     * tipo de unidad (vivienda) del HHPP segun lo que venga en el THID del
     * objeto hhpp
     *
     *
     * @author Juan David Hernandez
     * @version 1.0 revision .
     * @param hhppMgl informacion del hhpp que se quiere modificar tiene que
     * venir con el tipo de unidad (vivienda) a modificar
     */
    public void cambioTipoUnidadViviendaHhppRR(HhppMgl hhppMgl) {
        try {

            AddUnitRequest addUnitRequest = new AddUnitRequest();
            addUnitRequest.setTypeRequest(Constantes.TYPE_REQUEST_MODIFYUNIT);

            addUnitRequest.setCommunity(hhppMgl.getHhpComunidad());
            addUnitRequest.setDivision(hhppMgl.getHhpDivision());
            addUnitRequest.setApartmentNumber(hhppMgl.getHhpApart());
            addUnitRequest.setStreetName(hhppMgl.getHhpCalle());
            addUnitRequest.setHouseNumber(hhppMgl.getHhpPlaca());
            //modificacion del tipo de unidad (vivienda)
            addUnitRequest.setUnitType(hhppMgl.getThhId());

            PortManager portManager = new PortManager(wsURL, wsService);
            UnitManagerResponse unitManagerResponse;

            try {
                unitManagerResponse = portManager.addUnit(addUnitRequest);

            } catch (ApplicationException e) {
                LOGGER.error("Error al intentar cambiar el tipo de unidad (vivienda), se consume nuevamente el servicio " + e.getMessage());
                unitManagerResponse = portManager.addUnit(addUnitRequest);
            }
            if (unitManagerResponse.getResponseAddUnit().getMessageType().equals("I")) {
                LOGGER.info("Se realizo el cambio de tipo de unidad (vivienda) en el HHPP RR " + unitManagerResponse.getResponseAddUnit().getMessageText());
            } else {
                LOGGER.info("Se genero un error modificando el tipo de unidad (vivienda) en HHPP RR " + unitManagerResponse.getResponseAddUnit().getMessageText());
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error al momento de cambiar el estado del HHPP en RR. EX000: " + e.getMessage(), e);
        }
    }

    public PreFichaXlsMgl getPrefichaXlsMgl() {
        return prefichaXlsMgl;
    }

    public void setPrefichaXlsMgl(PreFichaXlsMgl prefichaXlsMgl) {
        this.prefichaXlsMgl = prefichaXlsMgl;
    }
    
    public PreFichaXlsMglNew getPrefichaXlsMglNew() {
        return prefichaXlsMglNew;
    }

    public void setPrefichaXlsMglNew(PreFichaXlsMglNew prefichaXlsMglNew) {
        this.prefichaXlsMglNew = prefichaXlsMglNew;
    }
}
