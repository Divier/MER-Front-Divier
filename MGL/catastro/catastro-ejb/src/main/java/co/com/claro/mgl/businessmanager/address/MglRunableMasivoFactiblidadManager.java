/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mer.utils.StringToolUtils;
import co.com.claro.mgl.businessmanager.cm.*;
import co.com.claro.mgl.constantes.cobertura.Constants;
import co.com.claro.mgl.dtos.CmtDireccionDetalladaMglDto;
import co.com.claro.mgl.dtos.MasivoControlFactibilidadDtoMgl;
import co.com.claro.mgl.dtos.MasivoFactibilidadDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.*;
import co.com.claro.mgl.jpa.entities.cm.*;
import co.com.claro.mgl.rest.dtos.CmtCoverDto;
import co.com.claro.mgl.rest.dtos.CmtRequestHhppByCoordinatesDto;
import co.com.claro.mgl.util.cm.ValidatePenetrationCuentaMatriz;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.CmtCoverEnum;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.ws.cm.request.RequestConstruccionDireccion;
import co.com.claro.mgl.ws.cm.response.ResponseConstruccionDireccion;
import co.com.claro.mgl.ws.cm.response.ResponseMesaje;
import co.com.claro.ofscCapacity.activityBookingOptions.GetActivityBookingResponses;
import co.com.claro.visitasTecnicas.business.NegocioParamMultivalor;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.services.georeferencia.AddressEJBRemote;
import co.com.telmex.catastro.utilws.ResponseMessage;
import com.jlcg.db.exept.ExceptionDB;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.file.UploadedFile;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author bocanegravm
 */
@Log4j2
public class MglRunableMasivoFactiblidadManager extends Observable implements Runnable {

    public static final String UNIDAD = "UNIDAD";
    public static final String MZTIPONIVEL_1 = "MZTIPONIVEL1";
    public static final String MZVALORNIVEL_1 = "MZVALORNIVEL1";
    public static final String MZTIPONIVEL_2 = "MZTIPONIVEL2";
    public static final String MZVALORNIVEL_2 = "MZVALORNIVEL2";
    public static final String MZTIPONIVEL_3 = "MZTIPONIVEL3";
    public static final String MZVALORNIVEL_3 = "MZVALORNIVEL3";
    public static final String MZTIPONIVEL_4 = "MZTIPONIVEL4";
    public static final String MZVALORNIVEL_4 = "MZVALORNIVEL4";
    public static final String MZTIPONIVEL_5 = "MZTIPONIVEL5";
    public static final String MZVALORNIVEL_5 = "MZVALORNIVEL5";
    public static final String MZTIPONIVEL_6 = "MZTIPONIVEL6";
    public static final String MZVALORNIVEL_6 = "MZVALORNIVEL6";
    public static final String BARRIO = "BARRIO";
    private String nombreArchivo;
    private String usuario;
    private List<String> lineasArchivoRetorno = new ArrayList<>();
    private int conError;
    private ResponseConstruccionDireccion responseConstruirDireccion = new ResponseConstruccionDireccion();
    private List<MasivoFactibilidadDto> listFacDtos;
    private UploadedFile file;
    /**
     * Contador de los registros de cargue de factibilidad masiva que se han procesado.
     */
    private int count;
    private CmtDireccionDetalleMglManager detalleMglManager;
    private GeograficoPoliticoManager geograficoPoliticoManager;
    private CmtDireccionMglManager cmtDireccionMglManager;
    private FactibilidadMglManager factibilidadMglManager;
    private NodoMglManager nodoMglManager;
    private HhppMglManager hhppMglManager;
    private ArrendatarioMglManager arrendatarioMglManager;
    private SlaEjecucionMglManager slaEjecucionMglManager;
    private DetalleSlaEjeMglManager detalleSlaEjeMglManager;
    private DetalleFactibilidadMglManager detalleFactibilidadMglManager;
    private CmtTipoBasicaMglManager tipoBasicaMglManager;
    private CmtTecnologiaSubMglManager tecnologiaSubMglManager;
    private CmtBasicaMglManager basicaMglManager;
    private NodoPoligonoManager nodoPoligonoManager;
    private List<CmtCoverDto> listCover;
    private double regTot;
    private List<CmtDireccionDetalladaMglDto> lstCmtDireccionDetalladaMglDtos;
    private static final String NOM_COLUMNAS = "codigoDane;indicador;ubicacion;"
            + "idTipoDireccion;Barrio;CpTipoNivel1;CpTipoNivel5;"
            + "CpValorNivel1;CpValorNivel5;MzTipoNivel1;MzTipoNivel2;MzTipoNivel3;"
            + "MzTipoNivel4;MzTipoNivel5;MzTipoNivel6;MzValorNivel1;MzValorNivel2;"
            + "MzValorNivel3;MzValorNivel4;MzValorNivel5;MzValorNivel6;ItTipoPlaca;"
            + "ItValorPlaca";

    private DrDireccion direccionConsultaSitiData = new DrDireccion();//Brief 98062
    private List<String> tecnologiasPenetracion; //Brief 98062

    public MglRunableMasivoFactiblidadManager() {

    }

    public MglRunableMasivoFactiblidadManager(Observer observer,
            UploadedFile uploadedFile, String usuario,
            String nombreArchivo) {

        this.file = uploadedFile;
        this.nombreArchivo = nombreArchivo;
        this.usuario = usuario;
        // managed bean 
        this.addObserver(observer);
    }

    @Override
    public void run() {
        try {
            verificarInicioFactibilidadMasiva();
            procesarArchivoCargueFactibilidadMasiva();
        } catch (ApplicationException | IOException ex) {
            String msgError = "Error en run de MglRunableMasivoFactiblidadManager: " + ex.getMessage();
            LOGGER.error(msgError, ex);
            MasivoControlFactibilidadDtoMgl.setIsProcessing(false);
        }

        ejecutarProcesoFactibilidadMasiva();
    }

    /**
     * Procesa el archivo del cargue de factibilidad masiva.
     *
     * @throws IOException Excepción generada en caso de errores en la lectura del archivo.
     * @throws ApplicationException Excepción personalizada de la aplicación.
     * @author Gildardo Mora
     */
    private void procesarArchivoCargueFactibilidadMasiva() throws IOException, ApplicationException {
        try (BufferedReader contenidoArchivo = new BufferedReader(new InputStreamReader(this.file.getInputStream(), StandardCharsets.ISO_8859_1));
             LineNumberReader reader = new LineNumberReader(new InputStreamReader(this.file.getInputStream(), StandardCharsets.ISO_8859_1))) {

            long cantReg = reader.lines().count();
            int reg = (int) cantReg - 1;
            regTot = (double) reg * 2;
            MasivoControlFactibilidadDtoMgl.setNombreArchivo(this.nombreArchivo);
            MasivoControlFactibilidadDtoMgl.setNumeroRegistrosAProcesar(reg);
            listFacDtos = validarAndGenerarArchivoProceso(contenidoArchivo);
        } catch (ApplicationException e) {
            String msgError = "Ocurrió un problema al procesar el archivo de factibilidad masiva: " + e.getMessage();
            LOGGER.error(msgError);
            throw new ApplicationException(msgError, e);
        }
    }

    /**
     * Realiza la ejecución del proceso de factibilidad masiva.
     *
     * @author Gildardo Mora
     */
    private void ejecutarProcesoFactibilidadMasiva() {
        try {
            if (conError == 0) {
                //Paso limpio
                procesarFactibilidadMasiva();
            } else {
                asignarRegistrosConErrores();
            }

            this.setChanged();
            this.notifyObservers(lstCmtDireccionDetalladaMglDtos);
            finalizarProcesoFactibilidadMasiva();

        } catch (ApplicationException ex) {
            MasivoControlFactibilidadDtoMgl.setIsProcessing(false);
            String msgError = "Ocurrió un problema al procesar la factibilidad masiva: " + ex.getMessage();
            LOGGER.error(msgError, ex);
        } catch (Exception e) {
            MasivoControlFactibilidadDtoMgl.setIsProcessing(false);
            String msgError = "Ocurrió un error al procesar la factibilidad masiva: " + e.getMessage();
            LOGGER.error(msgError, e);
        }
    }

    /**
     * Realiza la finalización del proceso de factibilidad masiva.
     *
     * @author Gildardo Mora
     */
    private void finalizarProcesoFactibilidadMasiva() {
        try {
            MasivoControlFactibilidadDtoMgl.endProcess(lstCmtDireccionDetalladaMglDtos);
        } catch (ApplicationException ex) {
            String msgError = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            MasivoControlFactibilidadDtoMgl.setIsProcessing(false);
            LOGGER.error(msgError, ex);
        }
    }

    /**
     * Se encarga de realizar la factibilidad a los registros del archivo cargado.
     *
     * @throws ApplicationException Excepción personalizada de la aplicación.
     */
    private void procesarFactibilidadMasiva() throws ApplicationException {

        if (Objects.isNull(listFacDtos) || listFacDtos.isEmpty()) {
            LOGGER.warn("No hay registros para procesar la factibilidad masiva");
            return;
        }

        LOGGER.info("Inicia el proceso de busqueda de direcciones");
        detalleMglManager = new CmtDireccionDetalleMglManager();
        geograficoPoliticoManager = new GeograficoPoliticoManager();
        cmtDireccionMglManager = new CmtDireccionMglManager();
        factibilidadMglManager = new FactibilidadMglManager();
        nodoMglManager = new NodoMglManager();
        arrendatarioMglManager = new ArrendatarioMglManager();
        slaEjecucionMglManager = new SlaEjecucionMglManager();
        detalleSlaEjeMglManager = new DetalleSlaEjeMglManager();
        detalleFactibilidadMglManager = new DetalleFactibilidadMglManager();
        lstCmtDireccionDetalladaMglDtos = new ArrayList<>();
        hhppMglManager = new HhppMglManager();
        tipoBasicaMglManager = new CmtTipoBasicaMglManager();
        basicaMglManager = new CmtBasicaMglManager();
        tecnologiaSubMglManager = new CmtTecnologiaSubMglManager();
        nodoPoligonoManager = new NodoPoligonoManager();

        for (MasivoFactibilidadDto m : this.listFacDtos) {

            if (m.getIndicador().equalsIgnoreCase("D")) {
                //Busqueda direccion
                if (m.getCentroPoblado() != null && m.getDrDireccion() != null) {
                    CmtDireccionDetalladaMglDto cmtDireccionDetalladaMglDto = direccionesDetalladasConsulta(m.getCentroPoblado().getGpoId(),
                            m.getDrDireccion());
                    if (cmtDireccionDetalladaMglDto != null) {
                        cmtDireccionDetalladaMglDto.setUbicacionIngresada(m.getUbicacion());
                        lstCmtDireccionDetalladaMglDtos.add(cmtDireccionDetalladaMglDto);
                    }
                }
            } else {
                buscaryAsignarDireccionDetalladaPorCoordenadas(m);
            }

            ajustarEstadisticasDeProceso();
            //no hay motivo para dormir el proceso
            // informa al managed bean que acabo de procesar el registro en cuestion.
            this.setChanged();
            this.notifyObservers(m);
        }
    }

    /**
     * Realiza la consulta de dirección cercana por coordenadas y la asigna a la lista de direcciones detalladas en proceso.
     *
     * @param masivoFactibilidadDto {@link MasivoFactibilidadDto}
     * @throws ApplicationException Excepción personalizada de la aplicación.
     */
    private void buscaryAsignarDireccionDetalladaPorCoordenadas(MasivoFactibilidadDto masivoFactibilidadDto) throws ApplicationException {

       if (Objects.isNull(masivoFactibilidadDto)){
           LOGGER.warn("Los datos son nulos para consultar la dirección cercana por coordenadas");
           return;
       }

        if (Objects.isNull(masivoFactibilidadDto.getCentroPoblado()) || masivoFactibilidadDto.getRadioInclusion() <= 0
                || Objects.isNull(masivoFactibilidadDto.getLatitud()) || Objects.isNull(masivoFactibilidadDto.getLongitud())) {
            LOGGER.warn("Los datos no son validos para consultar la dirección cercana por coordenadas");
            return;
        }

        //Busqueda coordenadas
        CmtDireccionDetalladaMglDto cmtDireccionDetalladaMglDto =
                consultarDireccionesCercanasByCoordenadas(masivoFactibilidadDto.getCentroPoblado().getGeoCodigoDane().replace("'", ""),
                        masivoFactibilidadDto.getRadioInclusion(), masivoFactibilidadDto.getLongitud(), masivoFactibilidadDto.getLatitud());

        if (cmtDireccionDetalladaMglDto != null) {
            cmtDireccionDetalladaMglDto.setUbicacionIngresada(masivoFactibilidadDto.getUbicacion());
            lstCmtDireccionDetalladaMglDtos.add(cmtDireccionDetalladaMglDto);
        }
    }

    /**
     * Asigna los registros con errores en el flujo de salida.
     *
     * @author Gildardo Mora
     */
    private void asignarRegistrosConErrores() {
        //Hubo errores se genera archivo con los errores
        MasivoControlFactibilidadDtoMgl.setListArchivoWithErrors(lineasArchivoRetorno);
    }

    /**
     * Verifica que no haya un proceso de factibilidad masiva en progreso, y configura las variables requeridas.
     *
     * @throws ApplicationException Excepción personalizada de la aplicación.
     * @author Gildardo Mora
     */
    private void verificarInicioFactibilidadMasiva() throws ApplicationException {
        MasivoControlFactibilidadDtoMgl.cleanBeforeStart();
        MasivoControlFactibilidadDtoMgl.startProcess(usuario);
        count = 0;
        MasivoControlFactibilidadDtoMgl.setTipoArchivo("General");
    }

    /**
     * Método para validar y generar la lista de factibilidad
     *
     * @param contenido {@link BufferedReader}
     * @return {@link List<MasivoFactibilidadDto>}
     * @author bocanegravm
     */
    private List<MasivoFactibilidadDto> validarAndGenerarArchivoProceso(BufferedReader contenido) throws ApplicationException {

        List<MasivoFactibilidadDto> listGeneral = new ArrayList<>();

        try {
            lineasArchivoRetorno = new ArrayList<>();
            String cabeceraFile = contenido.readLine().toUpperCase();
            lineasArchivoRetorno.add(cabeceraFile + ";" + "ERROR_REGISTRO");
            int h = 0;
            conError = 0;
            Map<String, Integer> cabecera = new HashMap<>();
            StringBuilder msgError = new StringBuilder();
            int desvMts = 50;
            String desviacion = this.consultarParametro(Constants.DESVIACION_EN_METROS);

            if (StringUtils.isNotBlank(desviacion) && StringToolUtils.containsOnlyNumbers(desviacion)) {
                desvMts = Integer.parseInt(desviacion);
            }

            if (!cabeceraFile.isEmpty()) {
                for (String s : cabeceraFile.split(";")) {
                    cabecera.put(s, h++);
                }
            }

            String lineActual;
            int longitudCabecera = cabecera.size();
            int linea = 1;

            if (!cabeceraFile.equalsIgnoreCase(NOM_COLUMNAS.toUpperCase())){
                msgError.append( "|")
                        .append("El archivo no tiene un encabezado valido para su correcto procesamiento ")
                        .append("por favor cambielo por este:").append(NOM_COLUMNAS).append(StringUtils.SPACE);
                conError++;
                lineasArchivoRetorno.add(msgError.toString());//linea con punto y coma + el Error de la linea
                return Collections.emptyList();
            }

            GeograficoPoliticoManager geoPoliticoManager = new GeograficoPoliticoManager();
            LOGGER.info("Inicia el cargue de archivo CSV");

                while (contenido.ready()) {
                    MasivoFactibilidadDto masivo = new MasivoFactibilidadDto();
                    masivo.setLinea(linea);
                    msgError = new StringBuilder();
                    lineActual = contenido.readLine().toUpperCase();

                    if (lineActual.isEmpty()) {
                        linea += 1;
                        listGeneral.add(masivo);
                        ajustarEstadisticasDeProceso();
                        continue;
                    }

                        String[] strSplit = lineActual.split(";", longitudCabecera);
                        int indexCodigoDane = cabecera.get("CODIGODANE");
                        String codigoDane = strSplit[indexCodigoDane];
                        int indexIndicador = cabecera.get("INDICADOR");
                        String indicador = strSplit[indexIndicador];
                        int indexUbicacion = cabecera.get("UBICACION");
                        String ubicacion = strSplit[indexUbicacion];
                        //valido el codigo dane ingresado
                    msgError = validarCodigoDaneYasignarCentroPoblado(msgError, geoPoliticoManager, masivo, codigoDane);
                        //valido el indicador ingresado
                    validarIndicador(msgError, masivo, indicador);
                        //valido la ubicacion ingresada
                    masivo = validarYprocesarUbicacion(cabecera, msgError, desvMts, masivo, strSplit, ubicacion);
                    lineasArchivoRetorno.add(lineActual.toUpperCase() + ";" + msgError);//linea con punto y coma + el Error de la linea
                    linea += 1;
                    listGeneral.add(masivo);
                    ajustarEstadisticasDeProceso();
                }
                contenido.close();

        } catch (ApplicationException | IOException | NumberFormatException e) {
            MasivoControlFactibilidadDtoMgl.setIsProcessing(false);
            LOGGER.info("Error en generarListaGeneral  de MglRunableMasivoFactiblidadManager : {}", e.getMessage(), e);
        }
        return listGeneral;
    }

    /**
     * Realiza el ajuste de estadísticas de avance del proceso.
     *
     * @author Gildardo Mora
     */
    private void ajustarEstadisticasDeProceso() {
        count += 1;
        double regActual = count;
        double porcentaje = returnPorcentaje(regActual, regTot);
        porcentaje = redondearDecimales(porcentaje, 1);
        MasivoControlFactibilidadDtoMgl.setNumeroRegistrosProcesados(porcentaje);
    }

    /**
     * Verifica y procesa la ubicación de la factibilidad.
     *
     * @param cabecera  {@code Map<String, Integer>}
     * @param msgError  {@link StringBuilder}
     * @param desvMts   {@code int}
     * @param masivo    {@link MasivoFactibilidadDto}
     * @param strSplit  {@link String}
     * @param ubicacion {@link String}
     * @return {@link MasivoFactibilidadDto}
     * @throws ApplicationException Excepción personalizada de la aplicación.
     * @author Gildardo Mora
     */
    private MasivoFactibilidadDto validarYprocesarUbicacion(Map<String, Integer> cabecera, StringBuilder msgError,
            int desvMts, MasivoFactibilidadDto masivo, String[] strSplit, String ubicacion) throws ApplicationException {

        if (!sonValidosDatosMasivo(masivo)) {
            return masivo;
        }

        String indicador = Optional.of(masivo).map(MasivoFactibilidadDto::getIndicador).orElse("");
        Predicate<String> validarIndicador = letra -> letra.equalsIgnoreCase(indicador);

        if (validarIndicador.test("D")) {
            return procesarUbicacionTipoD(cabecera, msgError, masivo, strSplit, ubicacion);
        }

        if (validarIndicador.test("C")) {
            procesarUbicacionTipoC(msgError, desvMts, masivo, ubicacion);
        }

        return masivo;
    }

    /**
     * Procesa la ubicación de la factibilidad de indicador tipo C.
     *
     * @param msgError {@link StringBuilder}
     * @param desvMts {@code int}
     * @param masivo {@link MasivoFactibilidadDto}
     * @param ubicacion {@link String}
     * @throws ApplicationException Excepción personalizada de la aplicación.
     * @author Gildardo Mora
     */
    private void procesarUbicacionTipoC(StringBuilder msgError, int desvMts, MasivoFactibilidadDto masivo, String ubicacion) throws ApplicationException {

        if (StringUtils.isBlank(ubicacion)) {
            msgError.append("|").append("Debe ingresar unas coordenadas validas");
            conError++;
            //Seteamos Radio Inclusion parametrizado
            masivo.setRadioInclusion(desvMts);
            return;
        }

        //valido el campo ubicacion debe ser unas coordenadas separadas con |
        String expRegCoorLis = this.consultarParametro(Constants.EXP_REG_VAL_COOR);
        String[] partsCoor = ubicacion.split("\\|");

        if (!partsCoor[0].isEmpty()) {
            Optional<String> longitudProcesada = validarYprocesarLongitud(msgError, expRegCoorLis, partsCoor[0]);
            longitudProcesada.ifPresent(masivo::setLongitud);
        }

        if (!partsCoor[1].isEmpty()) {
            Optional<String> latitudProcesada = validarYprocesarLatitud(msgError, expRegCoorLis, partsCoor[1]);
            latitudProcesada.ifPresent(masivo::setLatitud);
        }

        masivo.setUbicacion(ubicacion);
        //Seteamos Radio Inclusion parametrizado
        masivo.setRadioInclusion(desvMts);
    }

    /**
     * Verifica y procesa la latitud de la coordenada.
     *
     * @param msgError        {@link StringBuilder}
     * @param expRegCoorLis   {@link String}
     * @param partsCoordenada {@link String}
     * @return {^@link Optional<String>}
     * @author Gildardo Mora
     */
    private Optional<String> validarYprocesarLatitud(StringBuilder msgError, String expRegCoorLis, String partsCoordenada) {
        Pattern pat = Pattern.compile(expRegCoorLis);
        Matcher matcher = pat.matcher(partsCoordenada.trim());
        boolean validaLatitud = false;

        while (matcher.find()) {
            validaLatitud = true;
        }

        if (!validaLatitud) {
            msgError.append("|")
                    .append("Formato invalido de Latitud: por favor ingrese valores como: Latitud= 7,5678996");
            conError++;
            return Optional.empty();
        }

        if (partsCoordenada.trim().contains("-")) {
            msgError.append("|")
                    .append("La segunda coordenada debe ser una latitud como: = 7,5678996");
            conError++;
            return Optional.empty();
        }

        String latitudMod = partsCoordenada.trim().replace(",", ".");
        return Optional.of(latitudMod);
    }

    /**
     * Verifica y procesa la longitud de la coordenada.
     *
     * @param msgError        {@link StringBuilder}
     * @param expRegCoorLis   {@link String}
     * @param partsCoordenada {@link String}
     * @return {@link Optional<String>}
     * @author Gildardo Mora
     */
    private Optional<String> validarYprocesarLongitud(StringBuilder msgError, String expRegCoorLis, String partsCoordenada) {
        Pattern pat = Pattern.compile(expRegCoorLis);
        Matcher matcher = pat.matcher(partsCoordenada.trim());
        boolean validaLongitud = false;

        while (matcher.find()) {
            validaLongitud = true;
        }

        if (!validaLongitud) {
            msgError.append("|")
                    .append("Formato invalido de Longitud: por favor ingrese valores como: Longitud= -4,5678996");
            conError++;
            return Optional.empty();
        }

        if (!partsCoordenada.trim().contains("-")) {
            msgError.append("|")
                    .append("La primera coordenada debe ser una longitud como: = -4,5678996");
            conError++;
            return Optional.empty();
        }

        String longitudMod = partsCoordenada.trim().replace(",", ".");
        return Optional.of(longitudMod);
    }

    /**
     * Procesa la ubicación de la factibilidad de indicador tipo D.
     *
     * @param cabecera  {@code Map<String, Integer>}
     * @param msgError  {@link StringBuilder}
     * @param masivo    {@link MasivoFactibilidadDto}
     * @param strSplit  {@link String}
     * @param ubicacion {@link String}
     * @return {@link MasivoFactibilidadDto}
     * @throws ApplicationException Excepción personalizada de la aplicación.
     * @author Gildardo Mora
     */
    private MasivoFactibilidadDto procesarUbicacionTipoD(Map<String, Integer> cabecera, StringBuilder msgError,
            MasivoFactibilidadDto masivo, String[] strSplit, String ubicacion) throws ApplicationException {

        //valido el campo ubicacion debe ser una direccion CK
        if (StringUtils.isNotBlank(ubicacion)) {
            masivo.setUbicacion(ubicacion);
            masivo = validarDireccionCK(masivo);

            if (masivo.getErrorConstruccionDir() != null) {
                msgError.append("|").append(masivo.getErrorConstruccionDir());
                conError++;
            }

            return masivo;
        }

        //No viene ubicacion puede ser una BM o IT
        int indexTipoDir = cabecera.get("IDTIPODIRECCION");
        String tipoDir = strSplit[indexTipoDir];

        if (StringUtils.isNotBlank(tipoDir) && tipoDir.equalsIgnoreCase("BM")) {
            masivo.setIdTipoDireccion(tipoDir);
            masivo = validarDireccionBM(masivo, strSplit, cabecera);
            if (masivo.getErrorConstruccionDir() != null) {
                msgError.append("|").append(masivo.getErrorConstruccionDir());
                conError++;
            }
            return masivo;
        }

        if (StringUtils.isNotBlank(tipoDir) && tipoDir.equalsIgnoreCase("IT")) {
            masivo.setIdTipoDireccion(tipoDir);
            masivo = validarDireccionIT(masivo, strSplit, cabecera);

            if (masivo.getErrorConstruccionDir() != null) {
                msgError.append("|").append(masivo.getErrorConstruccionDir());
                conError++;
            }
            return masivo;
        }

        msgError.append("|")
                .append("No tiene un tipo de direccion ")
                .append("valido debe ingresar  si la direccion tabulada es 'BM' o 'IT'");
        conError++;
        return masivo;
    }

    /**
     * Verifica si los datos de masivo y el indicador son válidos.
     *
     * @param masivo {@link MasivoFactibilidadDto}
     * @return {@code boolean} Retorna true si los datos de masivo y el indicador son válidos, false en caso contrario.
     * @author Gildardo Mora
     */
    private boolean sonValidosDatosMasivo(MasivoFactibilidadDto masivo){
        if (Objects.isNull(masivo)){
           return false;
        }

        String indicador = Optional.of(masivo).map(MasivoFactibilidadDto::getIndicador).orElse("");
        if (StringUtils.isBlank(indicador)){
            return false;
        }

        Predicate<String> validarIndicador = letra -> letra.equalsIgnoreCase(indicador);
        //Si no tiene un indicador válido (D, C)
        return validarIndicador.test("D") || validarIndicador.test("C");
    }

    /**
     * Validar el indicador ingresado.
     *
     * @param msgError  {@link StringBuilder}
     * @param masivo    {@link MasivoFactibilidadDto}
     * @param indicador {@link String}
     * @author Gildardo Mora
     */
    private void validarIndicador(StringBuilder msgError, MasivoFactibilidadDto masivo, String indicador) {

        if (StringUtils.isBlank(indicador)) {
            msgError.append("|").append("Debe ingresar un indicador valido");
            conError++;
            return;
        }

        if (!indicador.equalsIgnoreCase("C") && !indicador.equalsIgnoreCase("D")) {
            msgError.append("|").append("El indicador ingresado no es valido debe ser 'C' o 'D'");
            conError++;
            return;
        }

        masivo.setIndicador(indicador);
    }

    /**
     * Validar el código dane y asigna el centro poblado.
     *
     * @param msgError           {@link StringBuilder}
     * @param geoPoliticoManager {@link GeograficoPoliticoManager}
     * @param masivo             {@link MasivoFactibilidadDto}
     * @param codigoDane         {@link String}
     * @return {@link StringBuilder}
     * @author Gildardo Mora
     */
    private StringBuilder validarCodigoDaneYasignarCentroPoblado(StringBuilder msgError, GeograficoPoliticoManager geoPoliticoManager,
            MasivoFactibilidadDto masivo, String codigoDane) {

        if (StringUtils.isBlank(codigoDane)) {
            msgError = new StringBuilder("Debe ingresar un codigo Dane valido");
            conError++;
            return msgError;
        }

        GeograficoPoliticoMgl geograficoPoliticoMgl = geoPoliticoManager.findCentroPobladoCodDane(codigoDane.replace("'", ""));
        if (Objects.isNull(geograficoPoliticoMgl)) {
            msgError = new StringBuilder("El codigo dane ingresado no existe en el repositorio");
            conError++;
            return msgError;
        }

        LOGGER.info("codigo dane ingresado valido");
        masivo.setCentroPoblado(geograficoPoliticoMgl);
        return msgError;
    }

    /**
     * Realiza la validación de la dirección de tipo calle-carrera.
     *
     * @param masivoFactibilidadDto {@link MasivoFactibilidadDto}
     * @return {@link MasivoFactibilidadDto}
     * @throws ApplicationException Excepción personalizada de la aplicación.
     */
    public MasivoFactibilidadDto validarDireccionCK(MasivoFactibilidadDto masivoFactibilidadDto) throws ApplicationException {

        if (Objects.isNull(masivoFactibilidadDto) || Objects.isNull(masivoFactibilidadDto.getCentroPoblado())
                || StringUtils.isBlank(masivoFactibilidadDto.getUbicacion())) {
            return masivoFactibilidadDto;
        }

        RequestConstruccionDireccion requestCk = new RequestConstruccionDireccion();
        DrDireccion drDireccionCK = new DrDireccion();
        requestCk.setDireccionStr(masivoFactibilidadDto.getUbicacion());
        requestCk.setComunidad(masivoFactibilidadDto.getCentroPoblado().getGeoCodigoDane().replace("'", ""));
        drDireccionCK.setIdTipoDireccion("CK");
        drDireccionCK.setDirPrincAlt("P");
        requestCk.setDrDireccion(drDireccionCK);
        requestCk.setTipoAdicion("N");
        requestCk.setTipoNivel("N");
        DrDireccionManager drDireccionManager = new DrDireccionManager();
        // Retorna la dirección calle-carrera traducida.
        responseConstruirDireccion = drDireccionManager.construirDireccionSolicitud(requestCk);

        //Direccion traducida correctamente
        if (StringUtils.isNotBlank(responseConstruirDireccion.getDireccionStr())
                && responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equalsIgnoreCase("I")) {

            drDireccionCK = responseConstruirDireccion.getDrDireccion();
            masivoFactibilidadDto.setDrDireccion(drDireccionCK);
            return masivoFactibilidadDto;
        }

        //Dirección que no pudo ser traducida
        if (StringUtils.isBlank(responseConstruirDireccion.getDireccionStr())
                && responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equalsIgnoreCase("E")) {

            String msnError = "La dirección calle-carrera no pudo ser traducida." + responseConstruirDireccion.getResponseMesaje().getMensaje();
            masivoFactibilidadDto.setErrorConstruccionDir(msnError);
        }

        return masivoFactibilidadDto;
    }

    /**
     * Función utilizada para construir dirección de tipo Barrio-Manzana
     *
     * @param masivoFactibilidadDto {@link MasivoFactibilidadDto}
     * @param strSplit {@link String[]}
     * @param cabecera {@code Map<String, Integer>}
     * @return {@link MasivoFactibilidadDto}
     * @throws ApplicationException Excepción personalizada de la aplicación.
     * @author Victor Bocanegra
     */
    public MasivoFactibilidadDto validarDireccionBM(MasivoFactibilidadDto masivoFactibilidadDto, String[] strSplit,
            Map<String, Integer> cabecera) {

        String msnError = "";

        try {
            RequestConstruccionDireccion requestBM = new RequestConstruccionDireccion();
            DrDireccion drDireccionBM = new DrDireccion();
            DrDireccionManager drDireccionManager = new DrDireccionManager();
            drDireccionBM.setIdTipoDireccion(masivoFactibilidadDto.getIdTipoDireccion());
            Map<String, String> valoresCabecera = obtenerValoresCabeceraBarrioManzana(cabecera, strSplit);
            String barrio = valoresCabecera.get(BARRIO);
            drDireccionBM.setBarrio(barrio);
            masivoFactibilidadDto.setBarrio(barrio);
            //BM Tipo Nivel 1
            msnError = procesarBarrioManzanaNivel1(masivoFactibilidadDto, msnError, requestBM,
                    drDireccionBM, drDireccionManager,valoresCabecera);
            //BM Tipo Nivel 2
            msnError = procesarBarrioManzanaNivel2(masivoFactibilidadDto, msnError, requestBM,
                    drDireccionBM, drDireccionManager, valoresCabecera);
            //BM Tipo Nivel 3
            msnError = procesarBarrioManzanaNivel3(masivoFactibilidadDto, msnError, requestBM,
                    drDireccionBM, drDireccionManager, valoresCabecera);
            //BM Tipo Nivel 4
            msnError = procesarBarrioManzanaNivel4(masivoFactibilidadDto, msnError, requestBM,
                    drDireccionBM, drDireccionManager, valoresCabecera);
            //BM Tipo Nivel 5
            msnError = procesarBarrioManzanaNivel5(masivoFactibilidadDto, msnError, requestBM,
                    drDireccionBM, drDireccionManager, valoresCabecera);
            //BM Tipo Nivel 6
            procesarBarrioManzanaNivel6(masivoFactibilidadDto, msnError, requestBM,
                    drDireccionBM, drDireccionManager, valoresCabecera);

        } catch (ApplicationException e) {
            msnError = msnError + "|" + "Error en validarDireccionBM: " + e.getMessage();
            masivoFactibilidadDto.setErrorConstruccionDir(msnError);
        }

        return masivoFactibilidadDto;
    }

    /**
     * Realiza las operaciones de construcción de dirección para Nivel 6 de
     * Barrio Manzana
     *
     * @param masivoFactibilidadDto {@link MasivoFactibilidadDto}
     * @param msnError              {@link String}
     * @param requestBM             {@link RequestConstruccionDireccion}
     * @param drDireccionBM         {@link DrDireccion}
     * @param drDireccionManager    {@link DrDireccionManager}
     * @param valoresCabecera       {@code Map<String, String>}
     * @throws ApplicationException Excepción personalizada de la aplicación.
     * @author Gildardo Mora
     */
    private void procesarBarrioManzanaNivel6(MasivoFactibilidadDto masivoFactibilidadDto,
            String msnError, RequestConstruccionDireccion requestBM,
            DrDireccion drDireccionBM, DrDireccionManager drDireccionManager,
            Map<String, String> valoresCabecera) throws ApplicationException {

        String tipoN6 = valoresCabecera.get(MZTIPONIVEL_6);
        String valorN6 = valoresCabecera.get(MZVALORNIVEL_6);

        if (StringUtils.isBlank(tipoN6)) {
            return;
        }

        if (StringUtils.isBlank(valorN6)) {
            msnError = msnError + "|" + "Debe ingresar un valor para el Tipo de nivel 6";
            masivoFactibilidadDto.setErrorConstruccionDir(msnError);
            return;
        }

        requestBM.setDrDireccion(drDireccionBM);
        requestBM.setTipoNivel(tipoN6.trim());
        requestBM.setValorNivel(valorN6.trim());
        masivoFactibilidadDto.setMzTipoNivel6(tipoN6);
        masivoFactibilidadDto.setMzValorNivel6(valorN6);
        requestBM.setTipoAdicion("P");
        /* Retorna la dirección barrio-manzana traducida. */
        responseConstruirDireccion = drDireccionManager.construirDireccionSolicitud(requestBM);

        if (responseConstruirDireccion == null
                || responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("E")) {
            String msgInfo = Optional.ofNullable(responseConstruirDireccion)
                    .map(ResponseConstruccionDireccion::getResponseMesaje)
                    .map(ResponseMesaje::getMensaje).orElse("");
            msnError = msnError + "|" + msgInfo;
            masivoFactibilidadDto.setErrorConstruccionDir(msnError);
            return;
        }

        if (responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("I")) {
            requestBM.setDrDireccion(responseConstruirDireccion.getDrDireccion());
            masivoFactibilidadDto.setDrDireccion(responseConstruirDireccion.getDrDireccion());
        }
    }

    /**
     * Realiza las operaciones de construcción de dirección para Nivel 5 de
     * Barrio Manzana
     *
     * @param masivoFactibilidadDto {@link MasivoFactibilidadDto}
     * @param msnError              {@link String}
     * @param requestBM             {@link RequestConstruccionDireccion}
     * @param drDireccionBM         {@link DrDireccion}
     * @param drDireccionManager    {@link DrDireccionManager}
     * @param valoresCabecera       {@code Map<String, String>}
     * @return {@link String}
     * @throws ApplicationException Excepción personalizada de la aplicación.
     * @author Gildardo Mora
     */
    private String procesarBarrioManzanaNivel5(MasivoFactibilidadDto masivoFactibilidadDto,
            String msnError, RequestConstruccionDireccion requestBM,
            DrDireccion drDireccionBM, DrDireccionManager drDireccionManager,
            Map<String, String> valoresCabecera) throws ApplicationException {

        String tipoN5 = valoresCabecera.get(MZTIPONIVEL_5);
        String valorN5 = valoresCabecera.get(MZVALORNIVEL_5);

        if (StringUtils.isBlank(tipoN5)) {
            return msnError;
        }

        if (StringUtils.isBlank(valorN5)) {
            msnError = msnError + "|" + "Debe ingresar un valor para el Tipo de nivel 5";
            masivoFactibilidadDto.setErrorConstruccionDir(msnError);
            return msnError;
        }

        requestBM.setDrDireccion(drDireccionBM);
        requestBM.setTipoNivel(tipoN5.trim());
        requestBM.setValorNivel(valorN5.trim());
        masivoFactibilidadDto.setMzTipoNivel5(tipoN5);
        masivoFactibilidadDto.setMzValorNivel5(valorN5);
        requestBM.setTipoAdicion("P");
        /* Retorna la dirección barrio-manzana traducida. */
        responseConstruirDireccion = drDireccionManager.construirDireccionSolicitud(requestBM);

        if (responseConstruirDireccion == null
                || responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("E")) {
            return asignarMsgErrorConstruirDir(masivoFactibilidadDto, msnError);
        }

        if (responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("I")) {
            requestBM.setDrDireccion(responseConstruirDireccion.getDrDireccion());
            masivoFactibilidadDto.setDrDireccion(responseConstruirDireccion.getDrDireccion());
        }

        return msnError;
    }

    /**
     * Realiza las operaciones de construcción de dirección para Nivel 4 de
     * Barrio Manzana
     *
     * @param masivoFactibilidadDto {@link MasivoFactibilidadDto}
     * @param msnError              {@link String}
     * @param requestBM             {@link RequestConstruccionDireccion}
     * @param drDireccionBM         {@link DrDireccion}
     * @param drDireccionManager    {@link DrDireccionManager}
     * @param valoresCabecera       {@code Map<String, String>}
     * @return {@link String}
     * @throws ApplicationException Excepción personalizada de la aplicación.
     * @author Gildardo Mora
     */
    private String procesarBarrioManzanaNivel4(MasivoFactibilidadDto masivoFactibilidadDto,
            String msnError, RequestConstruccionDireccion requestBM,
            DrDireccion drDireccionBM, DrDireccionManager drDireccionManager,
            Map<String, String> valoresCabecera) throws ApplicationException {

        String tipoN4 = valoresCabecera.get(MZTIPONIVEL_4);
        String valorN4 = valoresCabecera.get(MZVALORNIVEL_4);

        if (StringUtils.isBlank(tipoN4)) {
            return msnError;
        }

        if (StringUtils.isBlank(valorN4)) {
            msnError = msnError + "|" + "Debe ingresar un valor para el Tipo de nivel 4";
            masivoFactibilidadDto.setErrorConstruccionDir(msnError);
            return msnError;
        }

        requestBM.setDrDireccion(drDireccionBM);
        requestBM.setTipoNivel(tipoN4.trim());
        requestBM.setValorNivel(valorN4.trim());
        masivoFactibilidadDto.setMzTipoNivel4(tipoN4);
        masivoFactibilidadDto.setMzValorNivel4(valorN4);
        requestBM.setTipoAdicion("P");
        /* Retorna la dirección barrio-manzana traducida. */
        responseConstruirDireccion = drDireccionManager.construirDireccionSolicitud(requestBM);

        if (responseConstruirDireccion == null
                || responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("E")) {
            return asignarMsgErrorConstruirDir(masivoFactibilidadDto, msnError);
        }

        if (responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("I")) {
            requestBM.setDrDireccion(responseConstruirDireccion.getDrDireccion());
            masivoFactibilidadDto.setDrDireccion(responseConstruirDireccion.getDrDireccion());
        }

        return msnError;
    }

    /**
     * Realiza las operaciones de construcción de dirección para Nivel 3 de
     * Barrio Manzana
     *
     * @param masivoFactibilidadDto {@link MasivoFactibilidadDto}
     * @param msnError              {@link String}
     * @param requestBM             {@link RequestConstruccionDireccion}
     * @param drDireccionBM         {@link DrDireccion}
     * @param drDireccionManager    {@link DrDireccionManager}
     * @param valoresCabecera       {@code Map<String, String>}
     * @return {@link String}
     * @throws ApplicationException Excepción en caso de error
     * @author Gildardo Mora
     */
    private String procesarBarrioManzanaNivel3(MasivoFactibilidadDto masivoFactibilidadDto,
            String msnError, RequestConstruccionDireccion requestBM,
            DrDireccion drDireccionBM, DrDireccionManager drDireccionManager,
            Map<String, String> valoresCabecera) throws ApplicationException {

        String tipoN3 = valoresCabecera.getOrDefault(MZTIPONIVEL_3, "");
        String valorN3 = valoresCabecera.getOrDefault(MZVALORNIVEL_3, "");

        if (StringUtils.isBlank(tipoN3)) {
            return msnError;
        }

        if (StringUtils.isBlank(valorN3)) {
            msnError = msnError + "|" + "Debe ingresar un valor para el Tipo de nivel 3";
            masivoFactibilidadDto.setErrorConstruccionDir(msnError);
            return msnError;
        }

        requestBM.setDrDireccion(drDireccionBM);
        requestBM.setTipoNivel(tipoN3.trim());
        requestBM.setValorNivel(valorN3.trim());
        masivoFactibilidadDto.setMzTipoNivel3(tipoN3);
        masivoFactibilidadDto.setMzValorNivel3(valorN3);
        requestBM.setTipoAdicion("P");
        /* Retorna la dirección barrio-manzana traducida. */
        responseConstruirDireccion = drDireccionManager.construirDireccionSolicitud(requestBM);

        if (responseConstruirDireccion == null
                || responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("E")) {
            return asignarMsgErrorConstruirDir(masivoFactibilidadDto, msnError);
        }

        if (responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("I")) {
            requestBM.setDrDireccion(responseConstruirDireccion.getDrDireccion());
            masivoFactibilidadDto.setDrDireccion(responseConstruirDireccion.getDrDireccion());
        }

        return msnError;
    }

    /**
     * Realiza las operaciones de construcción de dirección para Nivel 2 de
     * Barrio Manzana
     *
     * @param masivoFactibilidadDto {@link MasivoFactibilidadDto}
     * @param msnError              {@link String}
     * @param requestBM             {@link RequestConstruccionDireccion}
     * @param drDireccionBM         {@link DrDireccion}
     * @param drDireccionManager    {@link DrDireccionManager}
     * @param valoresCabecera       {@code Map<String, String>}
     * @return {@link String}
     * @throws ApplicationException Excepción en caso de error
     * @author Gildardo Mora
     */
    private String procesarBarrioManzanaNivel2(MasivoFactibilidadDto masivoFactibilidadDto,
            String msnError, RequestConstruccionDireccion requestBM, DrDireccion drDireccionBM,
            DrDireccionManager drDireccionManager,
            Map<String, String> valoresCabecera) throws ApplicationException {

        String tipoN2 = valoresCabecera.getOrDefault(MZTIPONIVEL_2, "");
        String valorN2 = valoresCabecera.getOrDefault(MZVALORNIVEL_2, "");

        if (StringUtils.isBlank(tipoN2)) {
            return msnError;
        }

        if (StringUtils.isBlank(valorN2)) {
            msnError = msnError + "|" + "Debe ingresar un valor para el Tipo de nivel 2";
            masivoFactibilidadDto.setErrorConstruccionDir(msnError);
            return msnError;
        }

        requestBM.setDrDireccion(drDireccionBM);
        requestBM.setTipoNivel(tipoN2.trim());
        requestBM.setValorNivel(valorN2.trim());
        masivoFactibilidadDto.setMzTipoNivel2(tipoN2);
        masivoFactibilidadDto.setMzValorNivel2(valorN2);
        requestBM.setTipoAdicion("P");
        /* Retorna la dirección barrio-manzana traducida. */
        responseConstruirDireccion = drDireccionManager.construirDireccionSolicitud(requestBM);

        if (responseConstruirDireccion == null
                || responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("E")) {
            return asignarMsgErrorConstruirDir(masivoFactibilidadDto, msnError);
        }

        if (responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("I")) {
            requestBM.setDrDireccion(responseConstruirDireccion.getDrDireccion());
            masivoFactibilidadDto.setDrDireccion(responseConstruirDireccion.getDrDireccion());
        }

        return msnError;
    }

    /**
     * Realiza las operaciones de construcción de dirección para Nivel 1 de
     * Barrio Manzana
     *
     * @param masivoFactibilidadDto {@link MasivoFactibilidadDto}
     * @param msnError              {@link String}
     * @param requestBM             {@link RequestConstruccionDireccion}
     * @param drDireccionBM         {@link DrDireccion}
     * @param drDireccionManager    {@link DrDireccionManager}
     * @param valoresCabecera       {@code Map<String, String>}
     * @return {@link String} Retorna el mensaje de error en caso de presentarse.
     * @throws ApplicationException Excepción personalizada de la aplicación.
     * @author Gildardo Mora
     */
    private String procesarBarrioManzanaNivel1(MasivoFactibilidadDto masivoFactibilidadDto,
            String msnError, RequestConstruccionDireccion requestBM, DrDireccion drDireccionBM,
            DrDireccionManager drDireccionManager, Map<String, String> valoresCabecera) throws ApplicationException {

        String tipoN1 = valoresCabecera.getOrDefault(MZTIPONIVEL_1, "");
        String valorN1 = valoresCabecera.getOrDefault(MZVALORNIVEL_1, "");

        if (StringUtils.isBlank(tipoN1)) {
            return msnError;
        }

        if (StringUtils.isBlank(valorN1)) {
            msnError = msnError + "|" + "Debe ingresar un valor para el Tipo de nivel 1";
            masivoFactibilidadDto.setErrorConstruccionDir(msnError);
            return msnError;
        }

        requestBM.setDrDireccion(drDireccionBM);
        requestBM.setTipoNivel(tipoN1.trim());
        requestBM.setValorNivel(valorN1.trim());
        masivoFactibilidadDto.setMzTipoNivel1(tipoN1);
        masivoFactibilidadDto.setMzValorNivel1(valorN1);
        requestBM.setTipoAdicion("P");
        /* Retorna la dirección barrio-manzana traducida. */
        responseConstruirDireccion = drDireccionManager.construirDireccionSolicitud(requestBM);

        // Dirección no construida correctamente
        if (responseConstruirDireccion == null
                || responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("E")) {
            return asignarMsgErrorConstruirDir(masivoFactibilidadDto, msnError);
        }

        //Dirección construida correctamente
        if (responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("I")) {
            requestBM.setDrDireccion(responseConstruirDireccion.getDrDireccion());
            masivoFactibilidadDto.setDrDireccion(responseConstruirDireccion.getDrDireccion());
        }

        return msnError;
    }

    /**
     * Asigna el mensaje de error en caso de presentarse a la faactibilidad
     * procesada.
     *
     * @param masivoFactibilidadDto {@link MasivoFactibilidadDto}
     * @param msnError              {@link String}
     * @return {@link String}
     * @author Gildardo Mora
     */
    private String asignarMsgErrorConstruirDir(MasivoFactibilidadDto masivoFactibilidadDto, String msnError) {
        String infoMsg = Optional.ofNullable(responseConstruirDireccion)
                .map(ResponseConstruccionDireccion::getResponseMesaje)
                .map(ResponseMesaje::getMensaje).orElse("");
        msnError = msnError + "|" + infoMsg;
        masivoFactibilidadDto.setErrorConstruccionDir(msnError);
        return msnError;
    }

    /**
     * Permite obtener los valores correspondientes a cada nivel de la dirección
     * barrio manzana.
     *
     * @param cabecera {@code Map<String, Integer>}
     * @param strSplit {@code String[]}
     * @return {@code Map<String, String>}
     * @author Gildardo Mora
     */
    private Map<String, String> obtenerValoresCabeceraBarrioManzana(Map<String, Integer> cabecera, String[] strSplit) {
        Map<String, String> reultMap = new HashMap<>();
        IntFunction<String> retornaValor = position -> strSplit[position];
        reultMap.put(BARRIO, retornaValor.apply(cabecera.get(BARRIO)));
        reultMap.put(MZTIPONIVEL_1, retornaValor.apply(cabecera.get(MZTIPONIVEL_1)));
        reultMap.put(MZTIPONIVEL_2, retornaValor.apply(cabecera.get(MZTIPONIVEL_2)));
        reultMap.put(MZTIPONIVEL_3, retornaValor.apply(cabecera.get(MZTIPONIVEL_3)));
        reultMap.put(MZTIPONIVEL_4, retornaValor.apply(cabecera.get(MZTIPONIVEL_4)));
        reultMap.put(MZTIPONIVEL_5, retornaValor.apply(cabecera.get(MZTIPONIVEL_5)));
        reultMap.put(MZTIPONIVEL_6, retornaValor.apply(cabecera.get(MZTIPONIVEL_6)));
        reultMap.put(MZVALORNIVEL_1, retornaValor.apply(cabecera.get(MZVALORNIVEL_1)));
        reultMap.put(MZVALORNIVEL_2, retornaValor.apply(cabecera.get(MZVALORNIVEL_2)));
        reultMap.put(MZVALORNIVEL_3, retornaValor.apply(cabecera.get(MZVALORNIVEL_3)));
        reultMap.put(MZVALORNIVEL_4, retornaValor.apply(cabecera.get(MZVALORNIVEL_4)));
        reultMap.put(MZVALORNIVEL_5, retornaValor.apply(cabecera.get(MZVALORNIVEL_5)));
        reultMap.put(MZVALORNIVEL_6, retornaValor.apply(cabecera.get(MZVALORNIVEL_6)));
        return reultMap;
    }

    /**
     * Función utilizada para construir dirección de tipo Intraducible
     *
     * @author Victor Bocanegra
     */
    public MasivoFactibilidadDto validarDireccionIT(MasivoFactibilidadDto masivoFactibilidadDto,
            String[] strSplit, Map<String, Integer> cabecera)
            throws ApplicationException {

        String msnError = "";

        try {
            RequestConstruccionDireccion requestIT = new RequestConstruccionDireccion();
            DrDireccion drDireccionIT = new DrDireccion();
            DrDireccionManager drDireccionManager = new DrDireccionManager();
            drDireccionIT.setIdTipoDireccion(masivoFactibilidadDto.getIdTipoDireccion());
            int indexBarrio = cabecera.get(BARRIO);
            String barrio = strSplit[indexBarrio];
            drDireccionIT.setBarrio(barrio);
            masivoFactibilidadDto.setBarrio(barrio);
            int indexCpTN1 = cabecera.get("CPTIPONIVEL1");
            String cpTipoN1 = strSplit[indexCpTN1];
            int indexCpTN5 = cabecera.get("CPTIPONIVEL5");
            String cpTipoN5 = strSplit[indexCpTN5];
            int indexMzTN1 = cabecera.get(MZTIPONIVEL_1);
            String tipoN1 = strSplit[indexMzTN1];
            int indexMzTN2 = cabecera.get(MZTIPONIVEL_2);
            String tipoN2 = strSplit[indexMzTN2];
            int indexMzTN3 = cabecera.get(MZTIPONIVEL_3);
            String tipoN3 = strSplit[indexMzTN3];
            int indexMzTN4 = cabecera.get(MZTIPONIVEL_4);
            String tipoN4 = strSplit[indexMzTN4];
            int indexMzTN5 = cabecera.get(MZTIPONIVEL_5);
            String tipoN5 = strSplit[indexMzTN5];
            int indexMzTN6 = cabecera.get(MZTIPONIVEL_6);
            String tipoN6 = strSplit[indexMzTN6];
            int indexCpVN1 = cabecera.get("CPVALORNIVEL1");
            String cpValorN1 = strSplit[indexCpVN1];
            int indexCpVN5 = cabecera.get("CPVALORNIVEL5");
            String cpValorN5 = strSplit[indexCpVN5];
            int indexMzVN1 = cabecera.get(MZVALORNIVEL_1);
            String valorN1 = strSplit[indexMzVN1];
            int indexMzVN2 = cabecera.get(MZVALORNIVEL_2);
            String valorN2 = strSplit[indexMzVN2];
            int indexMzVN3 = cabecera.get(MZVALORNIVEL_3);
            String valorN3 = strSplit[indexMzVN3];
            int indexMzVN4 = cabecera.get(MZVALORNIVEL_4);
            String valorN4 = strSplit[indexMzVN4];
            int indexMzVN5 = cabecera.get(MZVALORNIVEL_5);
            String valorN5 = strSplit[indexMzVN5];
            int indexMzVN6 = cabecera.get(MZVALORNIVEL_6);
            String valorN6 = strSplit[indexMzVN6];
            int indexItTiPlaca = cabecera.get("ITTIPOPLACA");
            String tipoPlaca = strSplit[indexItTiPlaca];
            int indextValorPlaca = cabecera.get("ITVALORPLACA");
            String valorPlaca = strSplit[indextValorPlaca];

            //CP Tipo Nivel 1
            if (StringUtils.isNotBlank(cpTipoN1)) {
                if (cpValorN1 != null && !cpValorN1.isEmpty()) {
                    requestIT.setDrDireccion(drDireccionIT);
                    requestIT.setTipoNivel(cpTipoN1.trim());
                    requestIT.setValorNivel(cpValorN1.trim());
                    masivoFactibilidadDto.setCpTipoNivel1(cpTipoN1);
                    masivoFactibilidadDto.setCpValorNivel1(cpValorN1);
                    requestIT.setTipoAdicion("P");
                    /* Retorna la dirección barrio-manzana traducida. */
                    responseConstruirDireccion = drDireccionManager.construirDireccionSolicitud(requestIT);

                    // Dirección no construida correctamente
                    if (responseConstruirDireccion == null
                            || responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("E")) {
                        String msgInfo = Optional.ofNullable(responseConstruirDireccion).map(ResponseConstruccionDireccion::getResponseMesaje)
                                .map(ResponseMesaje::getMensaje).orElse("");
                        msnError = msnError + "|" + msgInfo;
                        masivoFactibilidadDto.setErrorConstruccionDir(msnError);
                    } else {
                        //Dirección construida correctamente
                        if (responseConstruirDireccion != null
                                && responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("I")) {
                            requestIT.setDrDireccion(responseConstruirDireccion.getDrDireccion());
                            masivoFactibilidadDto.setDrDireccion(responseConstruirDireccion.getDrDireccion());
                        }
                    }
                } else {
                    msnError = msnError + "|" + "Debe ingresar un valor para el CP Tipo de nivel 1";
                    masivoFactibilidadDto.setErrorConstruccionDir(msnError);
                }
            }
            //Fin CP Tipo Nivel 1  
            //CP Tipo Nivel 5
            if (StringUtils.isNotBlank(cpTipoN5)) {
                if (cpValorN5 != null && !cpValorN5.isEmpty()) {
                    requestIT.setDrDireccion(drDireccionIT);
                    requestIT.setTipoNivel(cpTipoN5.trim());
                    requestIT.setValorNivel(cpValorN5.trim());
                    masivoFactibilidadDto.setCpTipoNivel5(cpTipoN5);
                    masivoFactibilidadDto.setCpValorNivel5(cpValorN5);
                    requestIT.setTipoAdicion("P");
                    /* Retorna la dirección barrio-manzana traducida. */
                    responseConstruirDireccion = drDireccionManager.construirDireccionSolicitud(requestIT);

                    // Dirección no construida correctamente
                    if (responseConstruirDireccion == null
                            || responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("E")) {
                        String msgInfo = Optional.ofNullable(responseConstruirDireccion).map(ResponseConstruccionDireccion::getResponseMesaje)
                                .map(ResponseMesaje::getMensaje).orElse("");
                        msnError = msnError + "|" + msgInfo;
                        masivoFactibilidadDto.setErrorConstruccionDir(msnError);
                    } else {
                        //Dirección construida correctamente
                        if (responseConstruirDireccion != null
                                && responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("I")) {
                            requestIT.setDrDireccion(responseConstruirDireccion.getDrDireccion());
                            masivoFactibilidadDto.setDrDireccion(responseConstruirDireccion.getDrDireccion());
                        }
                    }
                } else {
                    msnError = msnError + "|" + "Debe ingresar un valor para el CP Tipo de nivel 5";
                    masivoFactibilidadDto.setErrorConstruccionDir(msnError);
                }
            }
            //Fin BM Tipo Nivel 1  

            //BM Tipo Nivel 1
            if (tipoN1 != null && !tipoN1.isEmpty()) {
                if (valorN1 != null && !valorN1.isEmpty()) {
                    requestIT.setDrDireccion(drDireccionIT);
                    requestIT.setTipoNivel(tipoN1.trim());
                    requestIT.setValorNivel(valorN1.trim());
                    masivoFactibilidadDto.setMzTipoNivel1(tipoN1);
                    masivoFactibilidadDto.setMzValorNivel1(valorN1);
                    requestIT.setTipoAdicion("P");
                    /* Retorna la dirección barrio-manzana traducida. */
                    responseConstruirDireccion = drDireccionManager.construirDireccionSolicitud(requestIT);

                    // Dirección no construida correctamente
                    if (responseConstruirDireccion == null
                            || responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("E")) {
                        String msgInfo = Optional.ofNullable(responseConstruirDireccion).map(ResponseConstruccionDireccion::getResponseMesaje).map(ResponseMesaje::getMensaje).orElse("");
                        msnError = msnError + "|" + msgInfo;
                        masivoFactibilidadDto.setErrorConstruccionDir(msnError);
                    } else {
                        //Dirección construida correctamente
                        if (responseConstruirDireccion != null
                                && responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("I")) {
                            requestIT.setDrDireccion(responseConstruirDireccion.getDrDireccion());
                            masivoFactibilidadDto.setDrDireccion(responseConstruirDireccion.getDrDireccion());
                        }
                    }
                } else {
                    msnError = msnError + "|" + "Debe ingresar un valor para el Tipo de nivel 1";
                    masivoFactibilidadDto.setErrorConstruccionDir(msnError);
                }
            }
            //Fin BM Tipo Nivel 1  
            //BM Tipo Nivel 2  
            if (tipoN2 != null && !tipoN2.isEmpty()) {
                if (valorN2 != null && !valorN2.isEmpty()) {
                    requestIT.setTipoNivel(tipoN2.trim());
                    requestIT.setValorNivel(valorN2.trim());
                    masivoFactibilidadDto.setMzTipoNivel2(tipoN2);
                    masivoFactibilidadDto.setMzValorNivel2(valorN2);
                    requestIT.setTipoAdicion("P");
                    /* Retorna la dirección barrio-manzana traducida. */
                    responseConstruirDireccion = drDireccionManager.construirDireccionSolicitud(requestIT);

                    if (responseConstruirDireccion == null
                            || responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("E")) {
                        String msgInfo = Optional.ofNullable(responseConstruirDireccion).map(ResponseConstruccionDireccion::getResponseMesaje)
                                .map(ResponseMesaje::getMensaje).orElse("");
                        msnError = msnError + "|" + msgInfo;
                        masivoFactibilidadDto.setErrorConstruccionDir(msnError);
                    } else {
                        if (responseConstruirDireccion != null
                                && responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("I")) {
                            requestIT.setDrDireccion(responseConstruirDireccion.getDrDireccion());
                            masivoFactibilidadDto.setDrDireccion(responseConstruirDireccion.getDrDireccion());
                        }
                    }

                } else {
                    msnError = msnError + "|" + "Debe ingresar un valor para el Tipo de nivel 2";
                    masivoFactibilidadDto.setErrorConstruccionDir(msnError);
                }
            }
            //Fin BM Tipo Nivel 2   
            //BM Tipo Nivel 3  
            if (tipoN3 != null && !tipoN3.isEmpty()) {
                if (valorN3 != null && !valorN3.isEmpty()) {

                    requestIT.setTipoNivel(tipoN3.trim());
                    requestIT.setValorNivel(valorN3.trim());
                    masivoFactibilidadDto.setMzTipoNivel3(tipoN3);
                    masivoFactibilidadDto.setMzValorNivel3(valorN3);
                    requestIT.setTipoAdicion("P");
                    /* Retorna la dirección barrio-manzana traducida. */
                    responseConstruirDireccion = drDireccionManager.construirDireccionSolicitud(requestIT);

                    if (responseConstruirDireccion == null
                            || responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("E")) {
                        String msgInfo = Optional.ofNullable(responseConstruirDireccion).map(ResponseConstruccionDireccion::getResponseMesaje)
                                .map(ResponseMesaje::getMensaje).orElse("");
                        msnError = msnError + "|" + msgInfo;
                        masivoFactibilidadDto.setErrorConstruccionDir(msnError);
                    } else {
                        if (responseConstruirDireccion != null
                                && responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("I")) {
                            requestIT.setDrDireccion(responseConstruirDireccion.getDrDireccion());
                            masivoFactibilidadDto.setDrDireccion(responseConstruirDireccion.getDrDireccion());
                        }
                    }

                } else {
                    msnError = msnError + "|" + "Debe ingresar un valor para el Tipo de nivel 3";
                    masivoFactibilidadDto.setErrorConstruccionDir(msnError);
                }
            }
            //Fin BM Tipo Nivel 3 
            //BM Tipo Nivel 4  
            if (tipoN4 != null && !tipoN4.isEmpty()) {
                if (valorN4 != null && !valorN4.isEmpty()) {
                    requestIT.setTipoNivel(tipoN4.trim());
                    requestIT.setValorNivel(valorN4.trim());
                    masivoFactibilidadDto.setMzTipoNivel4(tipoN4);
                    masivoFactibilidadDto.setMzValorNivel4(valorN4);
                    requestIT.setTipoAdicion("P");
                    /* Retorna la dirección barrio-manzana traducida. */
                    responseConstruirDireccion = drDireccionManager.construirDireccionSolicitud(requestIT);

                    if (responseConstruirDireccion == null
                            || responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("E")) {
                        String msgInfo = Optional.ofNullable(responseConstruirDireccion).map(ResponseConstruccionDireccion::getResponseMesaje)
                                .map(ResponseMesaje::getMensaje).orElse("");
                        msnError = msnError + "|" + msgInfo;
                        masivoFactibilidadDto.setErrorConstruccionDir(msnError);
                    } else {
                        if (responseConstruirDireccion != null
                                && responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("I")) {
                            requestIT.setDrDireccion(responseConstruirDireccion.getDrDireccion());
                            masivoFactibilidadDto.setDrDireccion(responseConstruirDireccion.getDrDireccion());
                        }
                    }

                } else {
                    msnError = msnError + "|" + "Debe ingresar un valor para el Tipo de nivel 4";
                    masivoFactibilidadDto.setErrorConstruccionDir(msnError);
                }
            }
            //Fin BM Tipo Nivel 4  
            //BM Tipo Nivel 5  
            if (tipoN5 != null && !tipoN5.isEmpty()) {
                if (valorN5 != null && !valorN5.isEmpty()) {
                    requestIT.setTipoNivel(tipoN5.trim());
                    requestIT.setValorNivel(valorN5.trim());
                    masivoFactibilidadDto.setMzTipoNivel5(tipoN5);
                    masivoFactibilidadDto.setMzValorNivel5(valorN5);
                    requestIT.setTipoAdicion("P");
                    /* Retorna la dirección barrio-manzana traducida. */
                    responseConstruirDireccion = drDireccionManager.construirDireccionSolicitud(requestIT);

                    if (responseConstruirDireccion == null
                            || responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("E")) {
                        String msgInfo = Optional.ofNullable(responseConstruirDireccion).map(ResponseConstruccionDireccion::getResponseMesaje)
                                .map(ResponseMesaje::getMensaje).orElse("");
                        msnError = msnError + "|" + msgInfo;
                        masivoFactibilidadDto.setErrorConstruccionDir(msnError);
                    } else {
                        if (responseConstruirDireccion != null
                                && responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("I")) {
                            requestIT.setDrDireccion(responseConstruirDireccion.getDrDireccion());
                            masivoFactibilidadDto.setDrDireccion(responseConstruirDireccion.getDrDireccion());
                        }
                    }

                } else {
                    msnError = msnError + "|" + "Debe ingresar un valor para el Tipo de nivel 5";
                    masivoFactibilidadDto.setErrorConstruccionDir(msnError);
                }
            }
            //Fin BM Tipo Nivel 5  
            //BM Tipo Nivel 6  
            if (tipoN6 != null && !tipoN6.isEmpty()) {
                if (valorN6 != null && !valorN6.isEmpty()) {
                    requestIT.setTipoNivel(tipoN6.trim());
                    requestIT.setValorNivel(valorN6.trim());
                    masivoFactibilidadDto.setMzTipoNivel6(tipoN6);
                    masivoFactibilidadDto.setMzValorNivel6(valorN6);
                    requestIT.setTipoAdicion("P");
                    /* Retorna la dirección barrio-manzana traducida. */
                    responseConstruirDireccion = drDireccionManager.construirDireccionSolicitud(requestIT);

                    if (responseConstruirDireccion == null
                            || responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("E")) {
                        String msgInfo = Optional.ofNullable(responseConstruirDireccion).map(ResponseConstruccionDireccion::getResponseMesaje)
                                .map(ResponseMesaje::getMensaje).orElse("");
                        msnError = msnError + "|" + msgInfo;
                        masivoFactibilidadDto.setErrorConstruccionDir(msnError);
                    } else {
                        if (responseConstruirDireccion != null
                                && responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("I")) {
                            requestIT.setDrDireccion(responseConstruirDireccion.getDrDireccion());
                            masivoFactibilidadDto.setDrDireccion(responseConstruirDireccion.getDrDireccion());
                        }
                    }

                } else {
                    msnError = msnError + "|" + "Debe ingresar un valor para el Tipo de nivel 6";
                    masivoFactibilidadDto.setErrorConstruccionDir(msnError);
                }
            }
            //Fin BM Tipo Nivel 6  
            //Tipo Placa and Placa
            if (tipoPlaca != null && !tipoPlaca.isEmpty()) {
                if (valorPlaca != null && !valorPlaca.isEmpty()) {
                    requestIT.setDrDireccion(drDireccionIT);
                    requestIT.setTipoNivel(tipoPlaca.trim());
                    requestIT.setValorNivel(valorPlaca.trim());
                    masivoFactibilidadDto.setItTipoPlaca(tipoPlaca);
                    masivoFactibilidadDto.setItValorPlaca(valorPlaca);
                    requestIT.setTipoAdicion("P");
                    /* Retorna la dirección barrio-manzana traducida. */
                    responseConstruirDireccion = drDireccionManager.construirDireccionSolicitud(requestIT);

                    // Dirección no construida correctamente
                    if (responseConstruirDireccion == null
                            || responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("E")) {
                        String msgInfo = Optional.ofNullable(responseConstruirDireccion).map(ResponseConstruccionDireccion::getResponseMesaje)
                                .map(ResponseMesaje::getMensaje).orElse("");
                        msnError = msnError + "|" + msgInfo;
                        masivoFactibilidadDto.setErrorConstruccionDir(msnError);
                    } else {
                        //Dirección construida correctamente
                        if (responseConstruirDireccion != null
                                && responseConstruirDireccion.getResponseMesaje().getTipoRespuesta().equals("I")) {
                            requestIT.setDrDireccion(responseConstruirDireccion.getDrDireccion());
                            masivoFactibilidadDto.setDrDireccion(responseConstruirDireccion.getDrDireccion());
                        }
                    }
                } else {
                    msnError = msnError + "|" + "Debe ingresar un valor para el Tipo de placa";
                    masivoFactibilidadDto.setErrorConstruccionDir(msnError);
                }
            }
            //Fin Tipo Placa and Placa

        } catch (ApplicationException e) {
            msnError = msnError + "|" + "Error en validarDireccionIT: " + e.getMessage();
            masivoFactibilidadDto.setErrorConstruccionDir(msnError);
        }
        return masivoFactibilidadDto;
    }

    /**
     * Realiza la consulta del valor de un parámetro, a través de su acrónimo.
     *
     * @param acronimo {@link String} Acrónimo del Parámetro a buscar.
     * @return {@link String} Valor del Parámetro.
     * @throws ApplicationException Excepción personalizada de la aplicación.
     */
    private String consultarParametro(String acronimo) throws ApplicationException {
        String valor = null;
        ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
        try {
            valor = parametrosMglManager.findByAcronimo(acronimo).iterator().next().getParValor();
        } catch (NullPointerException e) {
            LOGGER.info("No se encuentra configurado el parámetro '{}", acronimo);
            MasivoControlFactibilidadDtoMgl.setIsProcessing(false);
        }

        return (valor);
    }

    /**
     * Calcula el porcentaje de registros que se han obtenido según el registro actual y el total de registros.
     *
     * @param regActual {@code double}
     * @param regTotales {@code double}
     * @return {@code double} Retorna el porcentaje calculado del progreso de registros.
     * @author Gildardo Mora
     */
    public double returnPorcentaje(double regActual, double regTotales) {
        double cien = 100;
        return (regActual * cien) / regTotales;
    }

    /**
     * Método para consultar las direcciones.
     *
     * @param centroPoblado {@link BigDecimal}
     * @param direccion {@link DrDireccion}
     * @return {@link CmtDireccionDetalladaMglDto}
     * @throws ApplicationException Excepción personalizada de la aplicación.
     */
    private CmtDireccionDetalladaMglDto direccionesDetalladasConsulta(BigDecimal centroPoblado, DrDireccion direccion)
            throws ApplicationException {

        CmtDireccionDetalladaMglDto direccionDetalladaMglDtosPar = null;
        // Realiza la busqueda normal de una direccion completa, construida correctamente
        List<CmtDireccionDetalladaMgl> direccionDetalladaPar = detalleMglManager.buscarDireccionTabuladaUnica(centroPoblado, direccion);

        if (!direccionDetalladaPar.isEmpty()) {
            DireccionMgl dir = direccionDetalladaPar.get(0).getDireccion();
            // Convertir el listado de entidades en listado de DTOs para la visualizacion:
            direccionDetalladaMglDtosPar = this.convertirDireccionDetalladaMglADto(direccionDetalladaPar);

            if (direccionDetalladaMglDtosPar == null) {
                LOGGER.warn("La conversión de la lista de entidades a DTO arrojó un valor nulo.");
                return  direccionDetalladaMglDtosPar;
            }

            if (dir != null) {
                procesarDireccionDetallada(direccionDetalladaMglDtosPar, direccionDetalladaPar, dir);
            }
            //Busqueda cmt Direccion

            //Busco si la direccion tiene una factibilidad  vigente List
            List<FactibilidadMgl> factibilidadVig = factibilidadMglManager
                    .findFactibilidadVigByDetallada(direccionDetalladaMglDtosPar.getDireccionDetalladaId(), new Date());

            if (CollectionUtils.isNotEmpty(factibilidadVig)) {
                FactibilidadMgl factibilidadMgl = factibilidadVig.get(0);
                direccionDetalladaMglDtosPar.setFactibilidadId(factibilidadMgl.getFactibilidadId());
            }
            //Fin Busqueda si la direccion tiene una factibilidad  vigente

            /* Brief 98062 */
            //Se clona dirección para asignarla la dirección de consulta hacia SITIDATA
            // y asi obtener luego los nodos desde SITIDATA
            DrDireccion direccionSiti = new DrDireccion();
            try {
                direccionSiti = direccion.clone();
            } catch (CloneNotSupportedException e) {
                LOGGER.error("ERROR: Ocurrió un error al clonar drDireccionCK: ", e);
            }

            direccionConsultaSitiData = direccionSiti;
            /* Brief 98062 */

            //Consulto coberturas
            direccionDetalladaMglDtosPar = consultaCoberturasCreateFactibilidad(direccionDetalladaMglDtosPar);
            //Fin Consulto coberturas
            return direccionDetalladaMglDtosPar;
        }

        //Creamos la direccion
        GeograficoPoliticoMgl geograficoPolitico = geograficoPoliticoManager.findById(centroPoblado);

        if (geograficoPolitico != null) {
            crearDireccionConsultada(direccion, geograficoPolitico);
        }

        //Vuelve y se consulta despues de creada
        direccionDetalladaPar = detalleMglManager.buscarDireccionTabuladaUnica(centroPoblado, direccion);

        if (!direccionDetalladaPar.isEmpty()) {
            DireccionMgl dir = direccionDetalladaPar.get(0).getDireccion();
            // Convertir el listado de entidades en listado de DTOs para la visualizacion:
            direccionDetalladaMglDtosPar = this.convertirDireccionDetalladaMglADto(direccionDetalladaPar);

            if (direccionDetalladaMglDtosPar == null) {
                LOGGER.warn("La conversión de la entidad DireccionDetallada a DTo arrojó un valor nulo.");
                return direccionDetalladaMglDtosPar;
            }

            if (dir != null) {
                procesarDireccionDetallada(direccionDetalladaMglDtosPar, direccionDetalladaPar, dir);
                //Fin Busqueda cmt Direccion
            }
            //Consulto coberturas
            direccionDetalladaMglDtosPar = consultaCoberturasCreateFactibilidad(direccionDetalladaMglDtosPar);
            //Fin Consulto coberturas
        }

        return direccionDetalladaMglDtosPar;
    }

    /**
     * Realiza las operaciones sobre las direcciones detalladas encontradas.
     *
     * @param direccionDetalladaMglDtosPar {@link CmtDireccionDetalladaMglDto}
     * @param direccionDetalladaPar        {@link List<CmtDireccionDetalladaMgl>}
     * @param dir                          {@link DireccionMgl}
     * @throws ApplicationException Excepción personalizada de la aplicación
     * @author Gildardo Mora
     */
    private void procesarDireccionDetallada(CmtDireccionDetalladaMglDto direccionDetalladaMglDtosPar,
            List<CmtDireccionDetalladaMgl> direccionDetalladaPar, DireccionMgl dir) throws ApplicationException {
        direccionDetalladaMglDtosPar.setDireccionMgl(dir);
        SubDireccionMgl subDir = direccionDetalladaPar.get(0).getSubDireccion();
        direccionDetalladaMglDtosPar.setSubDireccionMgl(subDir);
        UbicacionMgl ubicacionMgl = dir.getUbicacion() != null ? dir.getUbicacion() : null;
        GeograficoPoliticoMgl centro;

        if (ubicacionMgl != null) {
            direccionDetalladaMglDtosPar.setLatitudDir(ubicacionMgl.getUbiLatitud());
            direccionDetalladaMglDtosPar.setLongitudDir(ubicacionMgl.getUbiLongitud());
            centro = ubicacionMgl.getGpoIdObj();

            if (centro != null) {
                direccionDetalladaMglDtosPar.setCentroPoblado(centro.getGpoNombre());
                GeograficoPoliticoMgl ciudad = geograficoPoliticoManager.findById(centro.getGeoGpoId());

                if (ciudad != null) {
                    direccionDetalladaMglDtosPar.setCiudad(ciudad.getGpoNombre());
                    GeograficoPoliticoMgl dpto = geograficoPoliticoManager.findById(ciudad.getGeoGpoId());

                    if (dpto != null) {
                        direccionDetalladaMglDtosPar.setDepartamento(dpto.getGpoNombre());
                    }
                }
            }
        }

        direccionDetalladaMglDtosPar.setConfiabilidad(dir.getDirConfiabilidad() != null ? dir.getDirConfiabilidad() : BigDecimal.ONE);
        //Busqueda cmt Direccion
        CmtDireccionMgl direccionCm = cmtDireccionMglManager.findCmtDireccion(dir.getDirId());

        if (direccionCm != null && direccionCm.getCuentaMatrizObj() != null) {
            CmtCuentaMatrizMgl cuenta = direccionCm.getCuentaMatrizObj();
            direccionDetalladaMglDtosPar.setTipoDireccion("CCMM");
            direccionDetalladaMglDtosPar.setCuentaMatrizMgl(cuenta);
        } else {
            direccionDetalladaMglDtosPar.setTipoDireccion(UNIDAD);
        }
    }

    /**
     * Realiza la conversión de listado de Entidad a listado de DTO.
     *
     * @param listaCmtDireccionDetalladaMgl {@link List<CmtDireccionDetalladaMgl>} Listado de entidad CmtDireccionDetalladaMgl.
     * @return {@link CmtDireccionDetalladaMglDto}
     */
    private CmtDireccionDetalladaMglDto convertirDireccionDetalladaMglADto(List<CmtDireccionDetalladaMgl> listaCmtDireccionDetalladaMgl) {
        CmtDireccionDetalladaMglDto result = null;

        if (listaCmtDireccionDetalladaMgl != null && !listaCmtDireccionDetalladaMgl.isEmpty()) {
            result = new CmtDireccionDetalladaMglDto();
            for (CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl : listaCmtDireccionDetalladaMgl) {
                CmtDireccionDetalladaMglDto cmtDireccionDetalladaMglDto = cmtDireccionDetalladaMgl.convertirADto();
                if (cmtDireccionDetalladaMglDto != null) {
                    result = cmtDireccionDetalladaMglDto;
                }
            }
        }

        return (result);
    }

    /**
     *  Función que realiza la creacion de una direccion en las tablas direccion, subdireccion, direcciondetallada
     *
     * @param direccionConstruida {@link DrDireccion}
     * @param centroPobladoSeleccionado {@link GeograficoPoliticoMgl}
     * @throws ApplicationException Excepción personalizada de la aplicación.
     * @author Victor Bocanegra
     */
    public void crearDireccionConsultada(DrDireccion direccionConstruida,
            GeograficoPoliticoMgl centroPobladoSeleccionado) throws ApplicationException {
        try {
            NegocioParamMultivalor param = new NegocioParamMultivalor();
            CityEntity cityEntityCreaDir = param.consultaDptoCiudadGeo(centroPobladoSeleccionado.getGeoCodigoDane().replace("'", ""));

            if (cityEntityCreaDir == null
                    || StringUtils.isBlank(cityEntityCreaDir.getCityName())
                    || StringUtils.isBlank(cityEntityCreaDir.getDpto())) {
                throw new ApplicationException("La Ciudad no esta configurada en Direcciones");
            }

            DrDireccionManager drDireccionManager = new DrDireccionManager();
            String address = drDireccionManager.getDireccion(direccionConstruida);
            String barrioStr = drDireccionManager.obtenerBarrio(direccionConstruida);
            AddressRequest addressRequest = new AddressRequest();
            addressRequest.setCodDaneVt(cityEntityCreaDir.getCodDane());
            addressRequest.setAddress(address);
            addressRequest.setCity(cityEntityCreaDir.getCityName());
            addressRequest.setState(cityEntityCreaDir.getDpto());

            //se llena el barrio solo si es multiorigen
            if (centroPobladoSeleccionado.getGpoMultiorigen() != null
                    && centroPobladoSeleccionado.getGpoMultiorigen().equalsIgnoreCase("1")
                    && direccionConstruida.getIdTipoDireccion() != null
                    && direccionConstruida.getIdTipoDireccion().equalsIgnoreCase("CK") && barrioStr != null && !barrioStr.isEmpty()) {
                addressRequest.setNeighborhood(barrioStr.toUpperCase());
            }

            AddressEJBRemote addressEJBRemote = lookupaddressEJBBean();
            ResponseMessage responseMessageCreateDir = addressEJBRemote.createAddress(addressRequest,
                            usuario, "MGL", "", direccionConstruida);

            String msgType = Optional.ofNullable(responseMessageCreateDir).map(ResponseMessage::getMessageType).orElse("");

            if (msgType.contains("ERROR")){
                LOGGER.error("Ocurrió un error en crearDireccionConsultada: {}" , responseMessageCreateDir.getMessageText());
            }

        } catch (ApplicationException | ExceptionDB ex) {
            LOGGER.error("Error en crearDireccionConsultada {}" , ex.getMessage(), ex);
            MasivoControlFactibilidadDtoMgl.setIsProcessing(false);
        }
    }

    private AddressEJBRemote lookupaddressEJBBean() throws ApplicationException {
        try {
            Context c = new InitialContext();
            return (AddressEJBRemote) c.lookup("addressEJB#co.com.telmex.catastro.services.georeferencia.AddressEJBRemote");
        } catch (NamingException ne) {
            LOGGER.error("Error en lookupaddressEJBBean. {}", ne.getMessage());
            MasivoControlFactibilidadDtoMgl.setIsProcessing(false);
            throw new ApplicationException(ne);
        }
    }

    /**
     * Realiza la consulta de coberturas para la dirección.
     *
     * @param direccionDetalladaMglDto {@link CmtDireccionDetalladaMglDto}
     * @return {@link CmtDireccionDetalladaMglDto}
     * @throws ApplicationException Excepción personalizada de la aplicación
     */
    public CmtDireccionDetalladaMglDto consultarCoberturas(CmtDireccionDetalladaMglDto direccionDetalladaMglDto) throws ApplicationException {
        List<CmtCoverDto> coberturas = null;
        listCover = new ArrayList<>();

        if (direccionDetalladaMglDto.getDireccionMgl() != null) {
            /*Brief 98062 */
            detalleMglManager.setDrDireccionConsultaSitiData(direccionConsultaSitiData);
            /*Cierre Brief 98062*/
            coberturas = detalleMglManager.coberturasDireccion(direccionDetalladaMglDto.getDireccionMgl(),
                    direccionDetalladaMglDto.getSubDireccionMgl());
        }

        if (coberturas != null && !coberturas.isEmpty()) {
            listCover.addAll(coberturas);
            direccionDetalladaMglDto.setListCover(listCover);
        }

        // Si la dirección es de barrio abierto (HHPP)
        if (direccionDetalladaMglDto.getTipoDireccion().equalsIgnoreCase(UNIDAD)) {
            return consultarCoberturaDirHhpp(direccionDetalladaMglDto);
        }

        // Si la dirección es de tipo cuenta matriz (CCMM)
        if (direccionDetalladaMglDto.getTipoDireccion().equalsIgnoreCase("CCMM")
                && direccionDetalladaMglDto.getCuentaMatrizMgl() != null) {

            consultarCoberturaDirCuentaMatriz(direccionDetalladaMglDto);
        }

        return direccionDetalladaMglDto;
    }

    private void consultarCoberturaDirCuentaMatriz(CmtDireccionDetalladaMglDto direccionDetalladaMglDto) throws ApplicationException {
        List<CmtTecnologiaSubMgl> tecnologiasCcmm = new ArrayList<>();

        if (direccionDetalladaMglDto.getCuentaMatrizMgl() != null && direccionDetalladaMglDto.getCuentaMatrizMgl().getSubEdificioGeneral() != null) {
            tecnologiasCcmm = tecnologiaSubMglManager.
                    findTecnoSubBySubEdi(direccionDetalladaMglDto.getCuentaMatrizMgl().getSubEdificioGeneral());
        }

        if (CollectionUtils.isEmpty(tecnologiasCcmm)) {
            LOGGER.debug("No se encontraron tecnologías para la dirección, al momento de consultar la cobertura");
            return;
        }

        CmtTipoBasicaMgl cmtTipoBasicaTec = tipoBasicaMglManager.findByCodigoInternoApp(Constant.TIPO_BASICA_TECNOLOGIA);
        CmtTipoBasicaExtMgl tipoBasicaExtMglVC = null;

        if (cmtTipoBasicaTec != null && !cmtTipoBasicaTec.getListCmtTipoBasicaExtMgl().isEmpty()) {
            for (CmtTipoBasicaExtMgl cmtTipoBasicaExtMgl : cmtTipoBasicaTec.getListCmtTipoBasicaExtMgl()) {
                if (cmtTipoBasicaExtMgl.getCampoEntidadAs400().equalsIgnoreCase(Constant.TECNOLOGIA_MANEJA_NODOS)) {
                    tipoBasicaExtMglVC = cmtTipoBasicaExtMgl;
                }
            }
        }

        for (CmtTecnologiaSubMgl tecno : tecnologiasCcmm) {
            procesarTecnologiasCoberturaCuentaMatriz(tipoBasicaExtMglVC, tecno);
        }

        direccionDetalladaMglDto.setListCover(listCover);
        direccionDetalladaMglDto.setLstTecnologiaSubMgls(tecnologiasCcmm);
        //Fin Busqueda hhpp de la direccion
    }

    /**
     * Procesa las tecnologías de cobertura de la cuenta matriz
     *
     * @param tipoBasicaExtMglVC {@link CmtTipoBasicaExtMgl}
     * @param tecno {@link CmtTecnologiaSubMgl}
     */
    private void procesarTecnologiasCoberturaCuentaMatriz(CmtTipoBasicaExtMgl tipoBasicaExtMglVC, CmtTecnologiaSubMgl tecno) {
        if (listCover != null && !listCover.isEmpty()) {
            int control = 0;

            for (CmtCoverDto coverDtoCon : new ArrayList<>(listCover)) {
                if (tecno != null && (coverDtoCon.getTechnology().equalsIgnoreCase(tecno.getBasicaIdTecnologias().getCodigoBasica()))) {
                    LOGGER.info("tecnologias iguales  agrego a la lista tecnoSub");
                    listCover.remove(coverDtoCon);
                    CmtCoverDto coverDto = new CmtCoverDto();
                    NodoMgl nodo = tecno.getNodoId();
                    CmtBasicaMgl tec = nodo != null ? nodo.getNodTipo() : null;
                    CmtBasicaMgl estNodo = nodo != null ? nodo.getEstado() : null;
                    coverDto.setTechnology(tec != null ? tec.getCodigoBasica() : "NA");
                    coverDto.setColorTecno(retornaColorTecno(coverDto.getTechnology()));
                    coverDto.setNode(nodo != null ? nodo.getNodCodigo() : "NA");
                    coverDto.setState(estNodo != null ? estNodo.getAbreviatura() : "NA");
                    coverDto.setTecnologiaSubMgl(tecno);
                    coverDto.setValidaCobertura(true);
                    /*Brief 98062*/
                    coverDto.setNodoSitiData(coverDtoCon.getNodoSitiData());
                    /*Brief 98062*/
                    listCover.add(coverDto);
                    control++;
                }
            }

            if (control == 0 && tecno != null
                    && tecnologiaValidaCobertura(tecno.getBasicaIdTecnologias(), tipoBasicaExtMglVC)) {
                CmtCoverDto coverDto = new CmtCoverDto();
                NodoMgl nodo = tecno.getNodoId();
                CmtBasicaMgl tec = nodo != null ? nodo.getNodTipo() : null;
                CmtBasicaMgl estNodo = nodo != null ? nodo.getEstado() : null;
                coverDto.setTechnology(tec != null ? tec.getCodigoBasica() : "NA");
                coverDto.setColorTecno(retornaColorTecno(coverDto.getTechnology()));
                coverDto.setNode(nodo != null ? nodo.getNodCodigo() : "NA");
                coverDto.setState(estNodo != null ? estNodo.getAbreviatura() : "NA");
                coverDto.setTecnologiaSubMgl(tecno);
                coverDto.setValidaCobertura(true);
                listCover.add(coverDto);
            }

        } else {
            NodoMgl nodo = tecno != null ? tecno.getNodoId() : null;
            CmtBasicaMgl tec = nodo != null ? nodo.getNodTipo() : null;
            CmtBasicaMgl estNodo = nodo != null ? nodo.getEstado() : null;

            if (tecnologiaValidaCobertura(tec, tipoBasicaExtMglVC)) {
                CmtCoverDto coverDto = new CmtCoverDto();
                coverDto.setTechnology(tec != null ? tec.getCodigoBasica() : "NA");
                coverDto.setColorTecno(retornaColorTecno(coverDto.getTechnology()));
                coverDto.setNode(nodo != null ? nodo.getNodCodigo() : "NA");
                coverDto.setState(estNodo != null ? estNodo.getAbreviatura() : "NA");
                coverDto.setTecnologiaSubMgl(tecno);
                coverDto.setValidaCobertura(true);
                listCover.add(coverDto);
            }
        }
    }

    private CmtDireccionDetalladaMglDto consultarCoberturaDirHhpp(CmtDireccionDetalladaMglDto direccionDetalladaMglDto) throws ApplicationException {
        //Busco hhpp de la direccion
        DireccionMgl direccionMgl = new DireccionMgl();
        direccionMgl.setDirId(direccionDetalladaMglDto.getDireccionId());
        SubDireccionMgl subDireccionMgl = new SubDireccionMgl();

        if (direccionDetalladaMglDto.getSubdireccionId() != null) {
            subDireccionMgl.setSdiId(direccionDetalladaMglDto.getSubdireccionId());
        } else {
            subDireccionMgl = null;
        }

        List<HhppMgl> listadoTecHab = hhppMglManager.findByDirAndSubDir(direccionMgl, subDireccionMgl);

        if (listadoTecHab != null && !listadoTecHab.isEmpty()) {
            CmtTipoBasicaMgl cmtTipoBasicaTec = tipoBasicaMglManager.findByCodigoInternoApp(Constant.TIPO_BASICA_TECNOLOGIA);
            CmtTipoBasicaExtMgl tipoBasicaExtMglVC = null;

            if (cmtTipoBasicaTec != null && !cmtTipoBasicaTec.getListCmtTipoBasicaExtMgl().isEmpty()) {
                for (CmtTipoBasicaExtMgl cmtTipoBasicaExtMgl : cmtTipoBasicaTec.getListCmtTipoBasicaExtMgl()) {
                    if (cmtTipoBasicaExtMgl.getCampoEntidadAs400().equalsIgnoreCase(Constant.TECNOLOGIA_MANEJA_NODOS)) {
                        tipoBasicaExtMglVC = cmtTipoBasicaExtMgl;
                    }
                }

            }

            for (HhppMgl hhppMgl : listadoTecHab) {
                if (listCover != null && !listCover.isEmpty()) {
                    int control = 0;

                    for (CmtCoverDto coverDtoCon : new ArrayList<>(listCover)) {
                        NodoMgl nodo = hhppMgl.getNodId();
                        CmtBasicaMgl tecnologia2 = nodo != null ? nodo.getNodTipo() : null;
                        CmtBasicaMgl estNodo = nodo != null ? nodo.getEstado() : null;

                        if (tecnologia2 != null && (coverDtoCon.getTechnology().equalsIgnoreCase(tecnologia2.getCodigoBasica()))) {
                            LOGGER.info("tecnologias iguales  agrego a la lista el HHPP");
                            listCover.remove(coverDtoCon);
                            CmtCoverDto coverDto = new CmtCoverDto();
                            coverDto.setHhppExistente(true);
                            coverDto.setTechnology(tecnologia2.getCodigoBasica());
                            coverDto.setColorTecno(retornaColorTecno(coverDto.getTechnology()));
                            coverDto.setNode(nodo != null ? nodo.getNodCodigo() : "NA");
                            coverDto.setState(estNodo != null ? estNodo.getAbreviatura() : "NA");
                            coverDto.setHhppMgl(hhppMgl);
                            coverDto.setValidaCobertura(true);
                            /* Brief 98062 */
                            coverDto.setNodoSitiData(coverDtoCon.getNodoSitiData());//asigna el nodo de SITIDATA
                            /* Cierre Brief 98062 */
                            listCover.add(coverDto);
                            control++;
                        }
                    }

                    if (control == 0) {
                        NodoMgl nodo = hhppMgl.getNodId();
                        CmtBasicaMgl tecnologia2 = nodo != null ? nodo.getNodTipo() : null;
                        CmtBasicaMgl estNodo = nodo != null ? nodo.getEstado() : null;

                        if (tecnologia2 != null && tecnologiaValidaCobertura(tecnologia2, tipoBasicaExtMglVC)) {
                            CmtCoverDto coverDto = new CmtCoverDto();
                            coverDto.setHhppExistente(true);
                            coverDto.setTechnology(tecnologia2.getCodigoBasica());
                            coverDto.setColorTecno(retornaColorTecno(coverDto.getTechnology()));
                            coverDto.setNode(nodo != null ? nodo.getNodCodigo() : "NA");
                            coverDto.setState(estNodo != null ? estNodo.getAbreviatura() : "NA");
                            coverDto.setHhppMgl(hhppMgl);
                            coverDto.setValidaCobertura(true);
                            listCover.add(coverDto);
                        }
                    }

                } else {
                    NodoMgl nodo = hhppMgl.getNodId();
                    CmtBasicaMgl tecnologia2 = nodo != null ? nodo.getNodTipo() : null;
                    CmtBasicaMgl estNodo = nodo != null ? nodo.getEstado() : null;

                    if (tecnologiaValidaCobertura(tecnologia2, tipoBasicaExtMglVC)) {
                        CmtCoverDto coverDto = new CmtCoverDto();
                        coverDto.setHhppExistente(true);
                        coverDto.setTechnology(tecnologia2 != null ? tecnologia2.getCodigoBasica() : "NA");
                        coverDto.setColorTecno(retornaColorTecno(coverDto.getTechnology()));
                        coverDto.setNode(nodo != null ? nodo.getNodCodigo() : "NA");
                        coverDto.setState(estNodo != null ? estNodo.getAbreviatura() : "NA");
                        coverDto.setHhppMgl(hhppMgl);
                        coverDto.setValidaCobertura(true);
                        listCover.add(coverDto);
                    }
                }
            }
            direccionDetalladaMglDto.setListCover(listCover);
            direccionDetalladaMglDto.setLstHhppMgl(listadoTecHab);
            //Fin Busqueda hhpp de la direccion
        }
        return direccionDetalladaMglDto;
    }

    /**
     * Consulta coberturar crea factibilidad y detalle de factibilidad
     *
     * @param direccionDetalladaMglDto {@link CmtDireccionDetalladaMglDto}
     * @return {@link CmtDireccionDetalladaMglDto}
     * @throws ApplicationException Excepción personalizada de la aplicación.
     */
    public CmtDireccionDetalladaMglDto consultaCoberturasCreateFactibilidad(CmtDireccionDetalladaMglDto direccionDetalladaMglDto)
            throws ApplicationException {

        try {
            //Consulto las coberturas
            direccionDetalladaMglDto = consultarCoberturas(direccionDetalladaMglDto);
            listCover = direccionDetalladaMglDto.getListCover();
            List<DetalleFactibilidadMgl> lstDetalleFactibilidadMgls = new ArrayList<>();

            if (Objects.nonNull(direccionDetalladaMglDto.getFactibilidadId())) {
                //Existe la factibilidad la muestro
                lstDetalleFactibilidadMgls = detalleFactibilidadMglManager.findListDetalleFactibilidad(direccionDetalladaMglDto.getFactibilidadId(),
                        direccionDetalladaMglDto.getTipoDireccion());
                direccionDetalladaMglDto.setLstDetalleFactibilidadMgls(lstDetalleFactibilidadMgls);
                return direccionDetalladaMglDto;
            }

            //Guardo la factibilidad si no hay una vigente
                FactibilidadMgl factibilidadMgl = new FactibilidadMgl();
                factibilidadMgl.setUsuario(usuario);
                factibilidadMgl.setDireccionDetalladaId(direccionDetalladaMglDto.getDireccionDetalladaId());
                String diasVen = this.consultarParametro(Constants.DIAS_VENCIMIENTO_FACTIBILIDAD);

                if (StringUtils.isBlank(diasVen)) {
                    LOGGER.info("No esta configurado el parametros de los dias de vencimiento de una factibilidad");
                    return direccionDetalladaMglDto;
                }

                    int diasForVencer = Integer.parseInt(diasVen);
                    factibilidadMgl.setFechaCreacion(new Date());
                    //Sumo los dias de vencimiento a la fecha de creacion
                    Date fechaVence = fechaVencimiento(new Date(), diasForVencer);
                    factibilidadMgl.setFechaVencimiento(fechaVence);
                    factibilidadMgl.setNombreArchivo(nombreArchivo);
                    //crea el detalle de factibilidad inicial
                    factibilidadMgl = factibilidadMglManager.create(factibilidadMgl);

                    if (factibilidadMgl.getFactibilidadId() == null){
                        String msnError = "Ocurrió un error creando la factibilidad, factibilidadId es nulo";
                        LOGGER.info(msnError);
                        return direccionDetalladaMglDto;
                    }

                          /* se encarga de contener de forma temporal la lista de
                        tecnologías de penetración de la CCMM para validarla sobre la
                        cobertura del detalle de factibilidad
                         */
                        tecnologiasPenetracion = new ArrayList<>();//Brief 98062

            if (listCover == null || listCover.isEmpty()) {
                direccionDetalladaMglDto.setFactibilidadId(factibilidadMgl.getFactibilidadId());
                return direccionDetalladaMglDto;
            }

                        //Creo el detalle de la factibilidad
                            for (CmtCoverDto coverDto : listCover) {
                                DetalleFactibilidadMgl detalleFactibilidadMgl = new DetalleFactibilidadMgl();
                                detalleFactibilidadMgl.setCodigoNodo(coverDto.getNode());
                                detalleFactibilidadMgl.setEstadoNodo(coverDto.getState());
                                detalleFactibilidadMgl.setNombreTecnologia(coverDto.getTechnology());
                                detalleFactibilidadMgl.setColorTecno(coverDto.getColorTecno());
                                /* Brief 98062 */
                                detalleFactibilidadMgl.setNodoSitiData(coverDto.getNodoSitiData());
                                /* Cierre Brief 98062 */

                                //Busco el nodo
                                CmtBasicaMgl tecnologia2;
                                NodoMgl nodo;
                                CmtTipoBasicaMgl cmtTipoBasicaTec = tipoBasicaMglManager.findByCodigoInternoApp(
                                        Constant.TIPO_BASICA_TECNOLOGIA);
                                if (coverDto.getNode() != null) {
                                    nodo = nodoMglManager.findByCodigo(coverDto.getNode());
                                    //cuando se obtuvo información del nodo desde MER
                                    if (nodo != null) {
                                        Predicate<NodoMgl> requiereValidarFactibilidad = nodoMgl -> Objects.isNull(nodoMgl.getFactibilidad())
                                                || nodoMgl.getFactibilidad().equalsIgnoreCase("P")
                                                || nodoMgl.getFactibilidad().equalsIgnoreCase("N");

                                        if (requiereValidarFactibilidad.test(nodo)
                                                || (!coverDto.isIsCobertura() && Objects.isNull(nodo.getFactibilidad()))) {
                                            detalleFactibilidadMgl.setClaseFactibilidad(obtenerClaseFactibilidad(coverDto));
                                        }

                                        detalleFactibilidadMgl.setSds(nodo.getLimites() != null ? nodo.getLimites() : "");
                                        detalleFactibilidadMgl.setProyecto(nodo.getProyecto() != null ? nodo.getProyecto() : "");
                                        tecnologia2 = nodo.getNodTipo();
                                        //Cuando no se tiene información del nodo en MER
                                    } else {
                                        /*Brief 98062 */
                                        detalleFactibilidadMgl.setClaseFactibilidad(obtenerClaseFactibilidad(coverDto));
                                        /* Cierre Brief 98062 */
                                        tecnologia2 = basicaMglManager.findByBasicaCode(coverDto.getTechnology(),
                                                cmtTipoBasicaTec.getTipoBasicaId());
                                    }
                                    //Si no se obtuvo información del nodo de la cobertura
                                } else {
                                    /* Brief 98062*/
                                    detalleFactibilidadMgl.setClaseFactibilidad(obtenerClaseFactibilidad(coverDto));
                                    /* Cierre Brief 98062*/
                                    tecnologia2 = basicaMglManager.findByBasicaCode(coverDto.getTechnology(),
                                            cmtTipoBasicaTec.getTipoBasicaId());
                                }

                                CmtBasicaMgl estadoTecno = null;

                                if (coverDto.getHhppMgl() != null) {
                                    HhppMgl hhppMgl = coverDto.getHhppMgl();
                                    EstadoHhppMgl estadoHhpp = hhppMgl.getEhhId();
                                    detalleFactibilidadMgl.setEstadoTecnologia(estadoHhpp != null ? estadoHhpp.getEhhNombre() : "NA");

                                } else if (coverDto.getTecnologiaSubMgl() != null) {
                                    estadoTecno = coverDto.getTecnologiaSubMgl().getBasicaIdEstadosTec();
                                    detalleFactibilidadMgl.setEstadoTecnologia(estadoTecno != null ? estadoTecno.getNombreBasica() : "NA");
                                }

                                //Detalle SLA de ejecucion
                                if (direccionDetalladaMglDto.getTipoDireccion().equalsIgnoreCase("CCMM")
                                        && tecnologia2 != null && tecnologia2.getBasicaId() != null) {
                                    //consulto sla de ejecucion
                                    SlaEjecucionMgl slaEjecucionMgl = slaEjecucionMglManager.findByTecnoAndEjecucion(tecnologia2, "CCMM");
                                    if (slaEjecucionMgl != null && estadoTecno != null) {
                                        DetalleSlaEjecucionMgl detalleSlaEjecucionMgls = detalleSlaEjeMglManager.
                                                findBySlaEjecucionAndEstCcmmAndTipoOt(slaEjecucionMgl, estadoTecno, null);
                                        if (detalleSlaEjecucionMgls != null
                                                && detalleSlaEjecucionMgls.getDetSlaEjecucionId() != null) {

                                            List<DetalleSlaEjecucionMgl> resulList = detalleSlaEjeMglManager.
                                                    findDetalleSlaEjecucionMaySecProcesoList(detalleSlaEjecucionMgls, 0);

                                            int totalSla = 0;
                                            StringBuilder detalleSla = new StringBuilder();
                                            if (resulList != null && !resulList.isEmpty()) {
                                                for (DetalleSlaEjecucionMgl detalleSlaEjecucionMgl1 : resulList) {
                                                    totalSla = totalSla + detalleSlaEjecucionMgl1.getSla();
                                                    detalleSla.append(detalleSlaEjecucionMgl1.getDetSlaEjecucionId().toString()).append(",");
                                                }
                                                detalleFactibilidadMgl.setDetallesSlas(detalleSla.toString());
                                            }
                                            detalleFactibilidadMgl.setTiempoUltimaLilla(totalSla);
                                            detalleFactibilidadMgl.setSlaEjecucionMglObj(slaEjecucionMgl);
                                        }
                                        detalleFactibilidadMgl.setSlaEjecucionMglObj(slaEjecucionMgl);
                                    }
                                } else if (direccionDetalladaMglDto.getTipoDireccion().equalsIgnoreCase(UNIDAD)
                                        && tecnologia2 != null && tecnologia2.getBasicaId() != null) {
                                    //consulto sla de ejecucion
                                    SlaEjecucionMgl slaEjecucionMgl = slaEjecucionMglManager.findByTecnoAndEjecucion(tecnologia2, UNIDAD);
                                    OtHhppMglManager otHhppMglManager = new OtHhppMglManager();
                                    List<OtHhppMgl> ordenDir = otHhppMglManager.findOtHhppByDireccionAndTecnologias(direccionDetalladaMglDto.getDireccionMgl(),
                                            direccionDetalladaMglDto.getSubDireccionMgl(), tecnologia2);

                                    if (slaEjecucionMgl != null) {

                                        if (ordenDir != null && !ordenDir.isEmpty()) {
                                            OtHhppMgl ot = ordenDir.get(0);
                                            DetalleSlaEjecucionMgl detalleSlaEjecucionMgls = detalleSlaEjeMglManager.
                                                    findBySlaEjecucionAndEstCcmmAndTipoOt(slaEjecucionMgl, null, ot.getTipoOtHhppId());

                                            if (detalleSlaEjecucionMgls != null
                                                    && detalleSlaEjecucionMgls.getDetSlaEjecucionId() != null) {

                                                CmtBasicaMgl estadoAbierto
                                                        = basicaMglManager.
                                                                findByCodigoInternoApp(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_ABIERTO_COMPLETO);
                                                CmtBasicaMgl estadoCerrado
                                                        = basicaMglManager.
                                                                findByCodigoInternoApp(Constant.ESTADO_GENERAL_OT_HHPP_RAZON_CERRADO_COMPLETO);

                                                int control;
                                                if (ot.getEstadoGeneral().getBasicaId().compareTo(estadoAbierto.getBasicaId()) == 0) {
                                                    control = 1;
                                                } else if (ot.getEstadoGeneral().getBasicaId().compareTo(estadoCerrado.getBasicaId()) == 0) {
                                                    control = 2;
                                                } else {
                                                    control = 3;
                                                }
                                                List<DetalleSlaEjecucionMgl> resulList = detalleSlaEjeMglManager.
                                                        findDetalleSlaEjecucionMaySecProcesoList(detalleSlaEjecucionMgls, control);

                                                int totalSla = 0;
                                                StringBuilder detalleSla = new StringBuilder();
                                                if (resulList != null && !resulList.isEmpty()) {
                                                    for (DetalleSlaEjecucionMgl detalleSlaEjecucionMgl1 : resulList) {
                                                        totalSla = totalSla + detalleSlaEjecucionMgl1.getSla();
                                                        detalleSla.append(detalleSlaEjecucionMgl1.getDetSlaEjecucionId().toString()).append(",");
                                                    }
                                                    detalleFactibilidadMgl.setDetallesSlas(detalleSla.toString());
                                                }
                                                detalleFactibilidadMgl.setTiempoUltimaLilla(totalSla);
                                            }
                                        } else {
                                            //No hay orden de trabajo
                                            int totalSla = 0;
                                            StringBuilder detalleSla = new StringBuilder();
                                            List<DetalleSlaEjecucionMgl> lstDetalleSlaEjecucionMgls = slaEjecucionMgl.getLstDetalleSlaEjecucionMgls();
                                            for (DetalleSlaEjecucionMgl detalleSlaEjecucionMgl : lstDetalleSlaEjecucionMgls) {
                                                if (detalleSlaEjecucionMgl.getEstadoRegistro() == 1) {
                                                    totalSla = totalSla + detalleSlaEjecucionMgl.getSla();
                                                    detalleSla.append(detalleSlaEjecucionMgl.getDetSlaEjecucionId().toString()).append(",");
                                                }
                                            }
                                            detalleFactibilidadMgl.setDetallesSlas(detalleSla.toString());
                                            detalleFactibilidadMgl.setTiempoUltimaLilla(totalSla);
                                        }

                                        detalleFactibilidadMgl.setSlaEjecucionMglObj(slaEjecucionMgl);
                                    }
                                }

                           
                                detalleFactibilidadMgl.setFactibilidadMglObj(factibilidadMgl);
                                if (coverDto.isHhppExistente()) {
                                    detalleFactibilidadMgl.setEsHhpp("1");
                                } else {
                                    detalleFactibilidadMgl.setEsHhpp("0");
                                }
                                //Consulta de la nueva informacion
                                detalleFactibilidadMgl = consultarNodoAproximadoBackupArrendatarios(direccionDetalladaMglDto, detalleFactibilidadMgl);
                                //Fin Consulta de la nueva informacion

                                /* Brief 98062*/
                                if(direccionDetalladaMglDto.getTipoDireccion().equalsIgnoreCase("CCMM")){
                                    //Valida la penetración de coberturas de la CCMM para definirla sobre el campo NODO MER
                                    validarPenetracionDetalleFactibilidad(direccionDetalladaMglDto.getCuentaMatrizMgl(), detalleFactibilidadMgl);
                                }
                                /*Cierre Brief 98062*/

                                //Crea el detalle de la factibilidad
                                detalleFactibilidadMgl = detalleFactibilidadMglManager.create(detalleFactibilidadMgl);
                                if (detalleFactibilidadMgl.getFactibilidadDetalleId() != null) {
                                    LOGGER.info("Detalle de la factibilidad creado satisfactoriamente.");
                                    lstDetalleFactibilidadMgls.add(detalleFactibilidadMgl);
                                } else {
                                    LOGGER.info("Ocurrio un error al crear el detalle de la factibilidad.");
                                }

                            }

                            direccionDetalladaMglDto.setLstDetalleFactibilidadMgls(lstDetalleFactibilidadMgls);
                        direccionDetalladaMglDto.setFactibilidadId(factibilidadMgl.getFactibilidadId());
                //Fin Guardo la factibilidads si no hay una vigente

        } catch (NumberFormatException ex) {
            String msnError = "Formato invalido de dias a vencer: "
                    + "No se pudo crear la factibilidad: " + ex.getMessage();
            LOGGER.info(msnError);
        }
        return direccionDetalladaMglDto;
    }

    /**
     * Obtiene la clase de factibilidad de la cobertura
     *
     * @param coberturaProcesada  Datos de cobertura a procesar
     * @return Tipo de factibilidad
     * @author Gildardo Mora
     */
    private String obtenerClaseFactibilidad(CmtCoverDto coberturaProcesada) {
        /*Nota: Para el caso de factibilidad individual procesada a través
         del visor de factibilidad, se realizó un método homónimo en la
         clase ConsutarCoberturasV1Bean */

        final String FACTIBILIDAD_POSITIVA = "POSITIVA";
        final String FACTIBILIDAD_NEGATIVA = "NEGATIVA";
        String tecnologia = Optional.ofNullable(coberturaProcesada).map(CmtCoverDto::getTechnology).orElse("");
        Predicate<String> isBlank = StringUtils::isBlank;
        BiPredicate<String,String> isGponAndStartsWithN = (tec, nodoSitidata) -> tecnologia.equals(tec) && nodoSitidata.startsWith("N");
        BiPredicate<String,String> isUnifiliarAndStartsWithS = (tec, nodoSitidata) -> tecnologia.equals(tec) && nodoSitidata.startsWith("S");
        String nodo = Optional.ofNullable(coberturaProcesada).map(CmtCoverDto::getNode).orElse("");

        /* Para el caso de la tecnología RFO se valida la factibilidad frente
         *  al nodo almacenado en MER y no a la respuesta de SITIDATA */
        if (tecnologia.equals(CmtCoverEnum.NODO_RFO.getCodigo())) {
            return isBlank.test(nodo) ? FACTIBILIDAD_NEGATIVA : FACTIBILIDAD_POSITIVA;
        }

        String nodoSitiData = Optional.ofNullable(coberturaProcesada).map(CmtCoverDto::getNodoSitiData).orElse("");

        /* Para los casos de tecnología FOG y FOU se tiene en cuenta la letra con que inicia el código del nodo,
        ya que esta determina si está habilitado en SITIDATA
        */
        if (isBlank.test(nodoSitiData)
                || isGponAndStartsWithN.test(CmtCoverEnum.NODO_TRES.getCodigo(), nodoSitiData)
                || isUnifiliarAndStartsWithS.test(CmtCoverEnum.NODO_FOU.getCodigo(), nodoSitiData)) {
            return FACTIBILIDAD_NEGATIVA;
        }

        return FACTIBILIDAD_POSITIVA;
    }

    /**
     * Obtiene las coberturas de penetración de la CCMM y los define sobre el nodo MER
     *
     * @param ccmmSeleccionada Cuenta matriz que se está evaluando
     * @throws ApplicationException Excepción personalizada de la aplicación
     * @author Gildardo Mora
     */
    private void validarPenetracionDetalleFactibilidad(CmtCuentaMatrizMgl ccmmSeleccionada,
            DetalleFactibilidadMgl detalleFactibilidadMgl) throws ApplicationException {

        if (Objects.isNull(ccmmSeleccionada)) {
            LOGGER.info("La cuenta matriz seleccionada es nula");
            return;
        }

        if (Objects.isNull(detalleFactibilidadMgl)){
            LOGGER.info("El detalle de factibilidad seleccionada es nula");
            return;
        }

        ValidatePenetrationCuentaMatriz validatePenetrationCuentaMatriz = new ValidatePenetrationCuentaMatriz();
        //verifica si no está poblada la lista de tecnologiasPenetracion para hacer la consulta
        if(tecnologiasPenetracion == null || tecnologiasPenetracion.isEmpty()){
            tecnologiasPenetracion = validatePenetrationCuentaMatriz.findTecnologiasPenetracionCuentaMatriz(ccmmSeleccionada);
        }

        /*Comprueba si la lista de penetración de tecnologías de la CCMM
         * contiene la tecnología presente en el detalle de factibilidad,
         * si no existe ajusta borra el código del nodo
         * */
        if (detalleFactibilidadMgl.getCodigoNodo() != null && !tecnologiasPenetracion.contains(detalleFactibilidadMgl.getNombreTecnologia())) {
            detalleFactibilidadMgl.setCodigoNodo("");//Deja el nodo vacio para que no sea mostrado en el front
        }
    }

    /**
     * Suma los días indicados a la fecha recibida.
     *
     * @param fecha {@link Date}
     * @param diasAsumar {@code int}
     * @return {@link Date}
     * @author Gildardo Mora
     */
    public Date fechaVencimiento(Date fecha, int diasAsumar) {
        LocalDateTime localDate = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        localDate = localDate.plusDays(diasAsumar);
        return Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Consulta las direcciones cercanas por coordenadas
     *
     * @param codigoDane {@link String}
     * @param radio      {@code int}
     * @param longitud   {@link String}
     * @param latitud    {@link String}
     * @return {@link CmtDireccionDetalladaMglDto}
     * @throws ApplicationException Excepción personalizada de la aplicación
     */
    public CmtDireccionDetalladaMglDto consultarDireccionesCercanasByCoordenadas(String codigoDane,
            int radio, String longitud, String latitud) throws ApplicationException {

        final int MAX_UNITS_NUMBER = 10;
        List<CmtDireccionDetalladaMgl> listaDireccionDetallada;
        CmtDireccionDetalladaMglDto cmtDireccionDetalladaMglDtoFinal = null;
        CmtRequestHhppByCoordinatesDto request1 = new CmtRequestHhppByCoordinatesDto();
        request1.setUnitsNumber(MAX_UNITS_NUMBER);
        request1.setDeviationMtr(radio);
        request1.setCiudad(codigoDane);
        String latitudMod = latitud.replace(",", ".");
        String longitudMod = longitud.replace(",", ".");
        request1.setLongitude(longitudMod);
        request1.setLatitude(latitudMod);

        try {
            listaDireccionDetallada = detalleMglManager.findDireccionDetalladaByCoordenadas(request1);
        } catch (ApplicationException ex) {
            LOGGER.error(ex.getMessage());
            listaDireccionDetallada = null;
        }

        if(Objects.isNull(listaDireccionDetallada) || listaDireccionDetallada.isEmpty()){
            return cmtDireccionDetalladaMglDtoFinal;
        }

        List<CmtDireccionDetalladaMglDto> dirIguales = new ArrayList<>();
        List<CmtDireccionDetalladaMglDto> dirDistintas = new ArrayList<>();

        for (CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl : listaDireccionDetallada) {
            procesarListaDireccionDetallada(request1, dirIguales, dirDistintas, cmtDireccionDetalladaMgl);
        }

        if (dirIguales.isEmpty()) {
            cmtDireccionDetalladaMglDtoFinal = calcularCoordenadaCercana(cmtDireccionDetalladaMglDtoFinal, request1, dirDistintas);
            //Consulto coberturas
            cmtDireccionDetalladaMglDtoFinal = consultaCoberturasCreateFactibilidad(cmtDireccionDetalladaMglDtoFinal);
            //Fin Consulto coberturas
            return cmtDireccionDetalladaMglDtoFinal;
        }

        for (CmtDireccionDetalladaMglDto detalladaMglDto : dirIguales) {
            if ((detalladaMglDto.getDireccionId() != null
                    && detalladaMglDto.getSubdireccionId() == null)
                    || (detalladaMglDto.getDireccionId() != null && detalladaMglDto.getSubdireccionId() != null)) {
                cmtDireccionDetalladaMglDtoFinal = detalladaMglDto;
                break;
            }
        }

        //Consulto coberturas
        cmtDireccionDetalladaMglDtoFinal = consultaCoberturasCreateFactibilidad(cmtDireccionDetalladaMglDtoFinal);
        //Fin Consulto coberturas
        return cmtDireccionDetalladaMglDtoFinal;
    }

    /**
     * Realiza el cálculo de la coordenada más cercana de la dirección
     *
     * @param cmtDireccionDetalladaMglDtoFinal {@link CmtDireccionDetalladaMglDto}
     * @param request1                         {@link CmtRequestHhppByCoordinatesDto}
     * @param dirDistintas                     {@link List<CmtDireccionDetalladaMglDto>}
     * @return {@link CmtDireccionDetalladaMglDto}
     * @author Gildardo Mora
     */
    private CmtDireccionDetalladaMglDto calcularCoordenadaCercana(CmtDireccionDetalladaMglDto cmtDireccionDetalladaMglDtoFinal,
            CmtRequestHhppByCoordinatesDto request1, List<CmtDireccionDetalladaMglDto> dirDistintas) {

        if (dirDistintas.isEmpty()) {
            return cmtDireccionDetalladaMglDtoFinal;
        }

        //Se realiza calculo coordenada mas cercana
        HashMap<CmtDireccionDetalladaMglDto, Double> coordenadasProximas = new HashMap<>();

        for (CmtDireccionDetalladaMglDto coordenadas : dirDistintas) {
            String latitudProxima = coordenadas.getLatitudDir().replace(",", ".");
            String longitudProxima = coordenadas.getLongitudDir().replace(",", ".");
            double latOrigen = Double.parseDouble(request1.getLatitude());
            double lonOrigen = Double.parseDouble(request1.getLongitude());
            double latProxima = Double.parseDouble(latitudProxima);
            double lonProxima = Double.parseDouble(longitudProxima);
            double distancia = distanciaCoord(latOrigen, lonOrigen, latProxima, lonProxima);
            coordenadasProximas.put(coordenadas, distancia);
        }

        double min = 99999.0;
        CmtDireccionDetalladaMglDto detalladaMglDtoCercano = null;

        for (Map.Entry<CmtDireccionDetalladaMglDto, Double> n : coordenadasProximas.entrySet()) {
            if (n.getValue() < min) {
                min = n.getValue();
                detalladaMglDtoCercano = n.getKey();
            }
        }

        cmtDireccionDetalladaMglDtoFinal = detalladaMglDtoCercano;
        return cmtDireccionDetalladaMglDtoFinal;
    }

    /**
     * Procesa la lista de direcciones de la CCMM y asigna los datos a la dirección
     *
     * @param request                  {@link CmtRequestHhppByCoordinatesDto}
     * @param dirIguales               {@link List<CmtDireccionDetalladaMglDto>}
     * @param dirDistintas             {@link List<CmtDireccionDetalladaMglDto>}
     * @param cmtDireccionDetalladaMgl {@link CmtDireccionDetalladaMgl}
     * @throws ApplicationException Excepción personalizada de la aplicación.
     * @author Gildardo Mora
     */
    private void procesarListaDireccionDetallada(CmtRequestHhppByCoordinatesDto request, List<CmtDireccionDetalladaMglDto> dirIguales,
            List<CmtDireccionDetalladaMglDto> dirDistintas, CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl) throws ApplicationException {

        DireccionMgl dir = cmtDireccionDetalladaMgl.getDireccion();
        // Construir un DTO a partir de la entidad, y adicionarlo a la lista:
        CmtDireccionDetalladaMglDto cmtDireccionDetalladaMglDto = cmtDireccionDetalladaMgl.convertirADto();

        if (dir != null) {
            asignarDatosDireccion(cmtDireccionDetalladaMgl, dir, cmtDireccionDetalladaMglDto);
        }
        //Busqueda cmt Direccion

        //Busco si la direccion tiene una factibilidad  vigente List
        List<FactibilidadMgl> factibilidadVig = factibilidadMglManager.findFactibilidadVigByDetallada(
                cmtDireccionDetalladaMglDto.getDireccionDetalladaId(), new Date());

        if (factibilidadVig != null && !factibilidadVig.isEmpty()) {
            FactibilidadMgl factibilidadMgl = factibilidadVig.get(0);
            cmtDireccionDetalladaMglDto.setFactibilidadId(factibilidadMgl.getFactibilidadId());
        }
        //Fin Busqueda si la direccion tiene una factibilidad  vigente

        if (cmtDireccionDetalladaMglDto.getLongitudDir() == null || cmtDireccionDetalladaMglDto.getLatitudDir() == null) {
            return;
        }

        String latitudMod1 = cmtDireccionDetalladaMglDto.getLatitudDir().replace(",", ".");
        String longitudMod1 = cmtDireccionDetalladaMglDto.getLongitudDir().replace(",", ".");

        if ((latitudMod1.trim().equalsIgnoreCase(request.getLatitude().trim())) && (longitudMod1.equalsIgnoreCase(request.getLongitude()))) {
            dirIguales.add(cmtDireccionDetalladaMglDto);
        } else {
            dirDistintas.add(cmtDireccionDetalladaMglDto);
        }
    }

    /**
     * Asigna los datos a la dirección
     *
     * @param cmtDireccionDetalladaMgl    {@link CmtDireccionDetalladaMgl}
     * @param dir                         {@link DireccionMgl}
     * @param cmtDireccionDetalladaMglDto {@link CmtDireccionDetalladaMglDto}
     * @throws ApplicationException Excepción personalizada de la aplicación.
     * @author Gildardo Mora
     */
    private void asignarDatosDireccion(CmtDireccionDetalladaMgl cmtDireccionDetalladaMgl, DireccionMgl dir,
            CmtDireccionDetalladaMglDto cmtDireccionDetalladaMglDto) throws ApplicationException {

        cmtDireccionDetalladaMglDto.setDireccionMgl(dir);
        SubDireccionMgl subDir = cmtDireccionDetalladaMgl.getSubDireccion();
        cmtDireccionDetalladaMglDto.setSubDireccionMgl(subDir);
        UbicacionMgl ubicacionMgl = dir.getUbicacion() != null ? cmtDireccionDetalladaMgl.getDireccion().getUbicacion() : null;

        if (ubicacionMgl != null) {
            cmtDireccionDetalladaMglDto.setLatitudDir(ubicacionMgl.getUbiLatitud());
            cmtDireccionDetalladaMglDto.setLongitudDir(ubicacionMgl.getUbiLongitud());
            GeograficoPoliticoMgl centro = ubicacionMgl.getGpoIdObj();

            if (centro != null) {
                cmtDireccionDetalladaMglDto.setCentroPoblado(centro.getGpoNombre());
                GeograficoPoliticoMgl ciudad = geograficoPoliticoManager.findById(centro.getGeoGpoId());
                if (ciudad != null) {
                    cmtDireccionDetalladaMglDto.setCiudad(ciudad.getGpoNombre());
                    GeograficoPoliticoMgl dpto = geograficoPoliticoManager.findById(ciudad.getGeoGpoId());
                    if (dpto != null) {
                        cmtDireccionDetalladaMglDto.setDepartamento(dpto.getGpoNombre());
                    }
                }
            }
        }

        cmtDireccionDetalladaMglDto.setConfiabilidad(dir.getDirConfiabilidad() != null ? dir.getDirConfiabilidad() : BigDecimal.ONE);
        //Busqueda cmt Direccion
        CmtDireccionMgl direccionCm = cmtDireccionMglManager.findCmtDireccion(dir.getDirId());

        if (Objects.isNull(direccionCm) || Objects.isNull(direccionCm.getCuentaMatrizObj())) {
            cmtDireccionDetalladaMglDto.setTipoDireccion(UNIDAD);
            return;
        }

        CmtCuentaMatrizMgl cuenta = direccionCm.getCuentaMatrizObj();
        cmtDireccionDetalladaMglDto.setTipoDireccion("CCMM");
        cmtDireccionDetalladaMglDto.setCuentaMatrizMgl(cuenta);
    }

    /**
     * Calcula la distancia entre dos coordenadas.
     *
     * @param latOrigen  {@code double}
     * @param longOrigen {@code double}
     * @param latProxima {@code double}
     * @param lngPoxima  {@code double}
     * @return {@code double}
     */
    public double distanciaCoord(double latOrigen, double longOrigen, double latProxima, double lngPoxima) {
        double radioTierra = 6371;//en kilómetros
        double dLat = Math.toRadians(latProxima - latOrigen);
        double dLng = Math.toRadians(lngPoxima - longOrigen);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(latOrigen)) * Math.cos(Math.toRadians(latProxima));
        double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));
        return radioTierra * va2;
    }

    /**
     * Redondea los decimales de un valor recibido, según el número de decimales indicado.
     *
     * @param valorInicial {@code double}
     * @param numeroDecimales {@code int}
     * @return {@code double} Retorna el valor redondeado de los decimales.
     */
    public double redondearDecimales(double valorInicial, int numeroDecimales) {
        double resultado = valorInicial;
        double parteEntera = Math.floor(resultado);
        resultado = (resultado - parteEntera) * Math.pow(10, numeroDecimales);
        resultado = Math.round(resultado);
        resultado = (resultado / Math.pow(10, numeroDecimales)) + parteEntera;
        return resultado;
    }

    /**
     * Comprueba si la tecnología ingresada es válida.
     *
     * @param tecnologia         {@link CmtBasicaMgl}
     * @param tipoBasicaExtMglVC {@link CmtTipoBasicaExtMgl}
     * @return {@code boolean}
     */
    public boolean tecnologiaValidaCobertura(CmtBasicaMgl tecnologia, CmtTipoBasicaExtMgl tipoBasicaExtMglVC) {

        if (tecnologia == null) {
            return false;
        }

        List<CmtBasicaExtMgl> extenList = tecnologia.getListCmtBasicaExtMgl();

        if (Objects.isNull(extenList) || extenList.isEmpty() || Objects.isNull(tipoBasicaExtMglVC)) {
            return false;
        }

        return extenList.stream()
                .anyMatch(basicaExtMgl ->
                        Objects.equals(basicaExtMgl.getIdTipoBasicaExt().getIdTipoBasicaExt(), tipoBasicaExtMglVC.getIdTipoBasicaExt())
                                && basicaExtMgl.getIdBasicaObj().getBasicaId().compareTo(tecnologia.getBasicaId()) == 0
                                && basicaExtMgl.getValorExtendido().equalsIgnoreCase("Y")
                                && basicaExtMgl.getEstadoRegistro() == 1);
    }

    /**
     * Retorna el color de la tecnología ingresada.
     *
     * @param codigoBasica {@link String} Código de la tecnología a la que se quiere obtener el color.
     * @return {@link String} retorna el color de la tecnología.
     * @author Gildardo Mora
     */
    public String retornaColorTecno(String codigoBasica) {
        if (StringUtils.isBlank(codigoBasica)) return StringUtils.EMPTY;

        Map<String, String> colorMap = new HashMap<>();
        colorMap.put(CmtCoverEnum.NODO_UNO.getCodigo(), "orange");
        colorMap.put(CmtCoverEnum.NODO_DOS.getCodigo(), "blue");
        colorMap.put(CmtCoverEnum.NODO_TRES.getCodigo(), "red");
        colorMap.put(CmtCoverEnum.NODO_DTH.getCodigo(), "green");
        colorMap.put(CmtCoverEnum.NODO_MOVIL.getCodigo(), "yellow");
        colorMap.put(CmtCoverEnum.NODO_FTTH.getCodigo(), "brown");
        colorMap.put(CmtCoverEnum.NODO_WIFI.getCodigo(), "pink");
        colorMap.put(CmtCoverEnum.NODO_FOU.getCodigo(), "violet");
        colorMap.put(CmtCoverEnum.NODO_ZONA_GPON_DISENIADO.getCodigo(), "turquoise");
        colorMap.put(CmtCoverEnum.NODO_ZONA_MICRO_ONDAS.getCodigo(), "fuchsia");
        colorMap.put(CmtCoverEnum.NODO_ZONA_COBERTURA_CAVS.getCodigo(), "gray");
        colorMap.put(CmtCoverEnum.NODO_ZONA_COBERTURA_ULTIMA_MILLA.getCodigo(), "black");
        colorMap.put(CmtCoverEnum.NODO_ZONA_CURRIER.getCodigo(), "purple");
        colorMap.put(CmtCoverEnum.NODO_ZONA_3G.getCodigo(), "yellowgreen");
        colorMap.put(CmtCoverEnum.NODO_ZONA_4G.getCodigo(), "salmon");
        colorMap.put(CmtCoverEnum.NODO_ZONA_5G.getCodigo(), "indigo");
        return Optional.ofNullable(colorMap.get(codigoBasica.toUpperCase())).orElse("");
    }

    /**
     * Consulta el nodo de cobertura aproximado.
     *
     * @param direccionDetalladaMglDto {@link CmtDireccionDetalladaMglDto}
     * @param detalleFac               {@link DetalleFactibilidadMgl}
     * @return {@link DetalleFactibilidadMgl}
     * @throws ApplicationException Excepción personalizada de la aplicación.
     */
    public DetalleFactibilidadMgl consultarNodoAproximadoBackupArrendatarios(CmtDireccionDetalladaMglDto direccionDetalladaMglDto,
            DetalleFactibilidadMgl detalleFac) throws ApplicationException {

        if (Objects.isNull(detalleFac)) {
            LOGGER.warn("El detalle de factibilidad recibido es nulo, para consultar el nodo aproximado de la direccion seleccionada");
            return new DetalleFactibilidadMgl();
        }

        Optional<String> codigoNodo = Optional.of(detalleFac).map(DetalleFactibilidadMgl::getCodigoNodo);
        Predicate<CmtDireccionDetalladaMglDto> noCumpleCondiciones = detalladaMglDto -> Objects.isNull(detalladaMglDto)
                || Objects.isNull(detalladaMglDto.getLongitudDir()) || Objects.isNull(detalladaMglDto.getLatitudDir());

        if (noCumpleCondiciones.test(direccionDetalladaMglDto) || codigoNodo.isPresent()) {
            String msnError = "La direccion seleccionada no presenta coordenadas u presenta nodo de cobertura";
            LOGGER.info(msnError);
            detalleFac = consultarNodoBackup(direccionDetalladaMglDto, detalleFac);
            return detalleFac;
        }

        Optional<UbicacionMgl> ubicacionMgl = Optional.of(direccionDetalladaMglDto).map(CmtDireccionDetalladaMglDto::getDireccionMgl)
                .map(DireccionMgl::getUbicacion);

        if (!ubicacionMgl.isPresent()) {
            detalleFac = consultarNodoBackup(direccionDetalladaMglDto, detalleFac);
            return detalleFac;
        }

        CmtTipoBasicaMgl tipoBasicaTecnologia = tipoBasicaMglManager.findByCodigoInternoApp(Constant.TIPO_BASICA_TECNOLOGIA);
        //Capturo la tecnologia
        String nombreTecnologia = Optional.of(detalleFac).map(DetalleFactibilidadMgl::getNombreTecnologia).orElse(null);
        CmtBasicaMgl tecno = basicaMglManager.findByBasicaCode(nombreTecnologia, tipoBasicaTecnologia.getTipoBasicaId());
        String desviacion = this.consultarParametro(Constants.DESVIACION_EN_METROS_NODO_APROXIMADO);

        if (StringUtils.isBlank(desviacion)) {
            String msnError = "No existe una configuracion para el parametro: " + Constants.DESVIACION_EN_METROS_NODO_APROXIMADO;
            LOGGER.info(msnError);
            detalleFac = consultarNodoBackup(direccionDetalladaMglDto, detalleFac);
            return detalleFac;
        }

        if (!StringUtils.isNumeric(desviacion)) {
            String msnError = "El valor parametrizado para la desviación en metros no es numérico";
            LOGGER.info(msnError);
            detalleFac = consultarNodoBackup(direccionDetalladaMglDto, detalleFac);
            return detalleFac;
        }

        String latitudMod = direccionDetalladaMglDto.getLatitudDir().replace(",", ".");
        String longitudMod = direccionDetalladaMglDto.getLongitudDir().replace(",", ".");
        //Capturo el centro poblado
        GeograficoPoliticoMgl centro = direccionDetalladaMglDto.getDireccionMgl().getUbicacion().getGpoIdObj();
        int desvMts = Integer.parseInt(desviacion);
        NodoPoligono nodoPoligono = nodoPoligonoManager.findNodosByCoordenadasDirTecnoAndCentro(latitudMod, longitudMod,
                tecno, centro, desvMts, null);

        if (Objects.isNull(nodoPoligono)) {
            String msnError = "No se encontró nodo cercano para la tecnología seleccionada";
            LOGGER.info(msnError);
            detalleFac = consultarNodoBackup(direccionDetalladaMglDto, detalleFac);
            return detalleFac;
        }

        detalleFac.setDistanciaNodoApro(nodoPoligono.getDistanciaMts());
        detalleFac.setNodoMglAproximado(nodoPoligono.getNodoVertice());
        detalleFac = consultarNodoBackup(direccionDetalladaMglDto, detalleFac);
        return detalleFac;
    }

    /**
     * Consulta el nodo backup para la dirección.
     *
     * @param direccionDetalladaMglDto {@link CmtDireccionDetalladaMglDto}
     * @param detalleFac               {@link DetalleFactibilidadMgl}
     * @return {@link DetalleFactibilidadMgl}
     * @throws ApplicationException Excepción personalizada de la aplicación.
     */
    public DetalleFactibilidadMgl consultarNodoBackup(CmtDireccionDetalladaMglDto direccionDetalladaMglDto,
            DetalleFactibilidadMgl detalleFac) throws ApplicationException {

        Predicate<CmtDireccionDetalladaMglDto> noCumpleCondiciones = detalladaMglDto -> Objects.isNull(detalladaMglDto)
                || Objects.isNull(detalladaMglDto.getLongitudDir()) || Objects.isNull(detalladaMglDto.getLatitudDir());

        if (noCumpleCondiciones.test(direccionDetalladaMglDto)) {
            String msnError = "La direccion seleccionada no presenta coordenadas";
            LOGGER.info(msnError);
            detalleFac = consultarCuadrante(detalleFac);
            return detalleFac;
        }

        Optional<UbicacionMgl> ubicacionMgl = Optional.of(direccionDetalladaMglDto).map(CmtDireccionDetalladaMglDto::getDireccionMgl)
                .map(DireccionMgl::getUbicacion);

        if (!ubicacionMgl.isPresent()) {
            detalleFac = consultarCuadrante(detalleFac);
            return detalleFac;
        }
        String desviacion = this.consultarParametro(Constants.DESVIACION_EN_METROS_NODO_APROXIMADO);

        if (StringUtils.isBlank(desviacion)) {
            String msnError = "No existe una configuracion para el parametro: " + Constants.DESVIACION_EN_METROS_NODO_APROXIMADO;
            LOGGER.info(msnError);
            detalleFac = consultarCuadrante(detalleFac);
            return detalleFac;
        }

        if (!StringUtils.isNumeric(desviacion)) {
            String msnError = "El valor parametrizado para la desviacion en metros no es numerico";
            LOGGER.info(msnError);
            detalleFac = consultarCuadrante(detalleFac);
            return detalleFac;
        }

        int desvMts = Integer.parseInt(desviacion);
        NodoMgl nodoMglRef = null;

        if (StringUtils.isNotBlank(detalleFac.getCodigoNodo())) {
            nodoMglRef = nodoMglManager.findByCodigo(detalleFac.getCodigoNodo());
        }

        //Capturo el centro poblado
        GeograficoPoliticoMgl centro = direccionDetalladaMglDto.getDireccionMgl().getUbicacion().getGpoIdObj();
        CmtTipoBasicaMgl tipoBasicaTecnologia = tipoBasicaMglManager.findByCodigoInternoApp(Constant.TIPO_BASICA_TECNOLOGIA);
        //Capturo la tecnologia
        CmtBasicaMgl tecno = basicaMglManager.findByBasicaCode(detalleFac.getNombreTecnologia(), tipoBasicaTecnologia.getTipoBasicaId());
        String latitudMod = direccionDetalladaMglDto.getLatitudDir().replace(",", ".");
        String longitudMod = direccionDetalladaMglDto.getLongitudDir().replace(",", ".");
        NodoPoligono nodoPoligono = nodoPoligonoManager.findNodosByCoordenadasDirTecnoAndCentro(latitudMod, longitudMod, tecno,
                centro, desvMts, nodoMglRef);

        if (Objects.isNull(nodoPoligono)) {
            String msnError = "No se encontró nodo cercano para la tecnología seleccionada";
            LOGGER.info(msnError);
            detalleFac = consultarCuadrante(detalleFac);
            return detalleFac;
        }

        detalleFac.setNodoMglBackup(nodoPoligono.getNodoVertice());
        detalleFac = consultarCuadrante(detalleFac);
        return detalleFac;
    }

    /**
     *  Permite consultar el cuadrante para el detalle de factibilidad.
     *
     * @param detalleFac {@link DetalleFactibilidadMgl}
     * @return {@link DetalleFactibilidadMgl}
     * @throws ApplicationException Excepción personalizada de la aplicación.
     */
    public DetalleFactibilidadMgl consultarCuadrante(DetalleFactibilidadMgl detalleFac) throws ApplicationException {
        Optional<String> codigoNodo = Optional.ofNullable(detalleFac).map(DetalleFactibilidadMgl::getCodigoNodo);

        if (Objects.isNull(detalleFac)) {
            LOGGER.warn("No se recibió detalle de factibilidad para consultar el cuadrante en OFSC");
            return detalleFac;
        }

        if (!codigoNodo.isPresent()) {
            LOGGER.info("No hay un nodo para consultar el cuadrante en OFSC");
            return detalleFac;
        }

        NodoMgl nodo = nodoMglManager.findByCodigo(codigoNodo.get());

        if (Objects.isNull(nodo)) {
            return detalleFac;
        }

        //Consulto cuadrante del nodo en OFSC
        String cuadrante = null;

        try {
            GetActivityBookingResponses responses = arrendatarioMglManager.consultarCuadrante(nodo);
            if (responses != null) {
                cuadrante = responses.getWorkZone();
            }
        } catch (ApplicationException ex) {
            String msnError = "Ocurrio un error consultando las zonas: " + ex.getMessage();
            LOGGER.info(msnError);
        }

        if (StringUtils.isBlank(cuadrante)) {
            String msnError = "No existe cuadrante en OFSC para el nodo: " + nodo.getNodCodigo();
            LOGGER.info(msnError);
            return detalleFac;
        }

        //Buscamos los arrendatarios que tiene ese cuadrante
        GeograficoPoliticoMgl centro = geograficoPoliticoManager.findById(nodo.getGpoId());
        Optional<BigDecimal> gpoId = Optional.ofNullable(centro).map(GeograficoPoliticoMgl::getGpoId);

        if (!gpoId.isPresent()) {
            String msnError = "No existe centro poblado con el id= " + nodo.getGpoId() + " ";
            LOGGER.info(msnError);
            return detalleFac;
        }

        final String SEPARADOR = ",";
        StringBuilder nombreArrendatarios = new StringBuilder();
        List<ArrendatarioMgl> cuadranteMglsCons = arrendatarioMglManager.findArrendatariosByCentro(centro);

        if (Objects.isNull(cuadranteMglsCons) || cuadranteMglsCons.isEmpty()){
            String nombreCentroPoblado = Optional.of(centro).map(GeograficoPoliticoMgl::getGpoNombre).orElse("");
            String msnError = "No hay creados arrendatarios  para el centro poblado: " + nombreCentroPoblado;
            LOGGER.info(msnError);
            return detalleFac;
        }

        for (ArrendatarioMgl arrendatarioCuadranteMgl : cuadranteMglsCons) {
            if (arrendatarioCuadranteMgl.getCuadrante().trim().equalsIgnoreCase(cuadrante)
                    && arrendatarioCuadranteMgl.getEstadoRegistro() == 1) {
                nombreArrendatarios.append(arrendatarioCuadranteMgl.getNombreArrendatario());
                nombreArrendatarios.append(SEPARADOR);
            }
        }

        if (nombreArrendatarios.toString().isEmpty()) {
            String msnError = "No existen arrendatarios creados para el cuadrante: " + cuadrante + " del Nodo";
            LOGGER.info(msnError);
            return detalleFac;
        }

        detalleFac.setNombreArrendatario(nombreArrendatarios.toString());
        return detalleFac;
    }

}
