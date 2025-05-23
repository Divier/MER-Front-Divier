/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.manager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import co.com.claro.cmas400.ejb.constant.Constantes;
import co.com.claro.mgl.error.ApplicationException;

/**
 *
 * @author Nelson Parra
 */
public class VectorizarTablaRta {

    private VectorizarTablaRta() {
    }

    private static final Logger LOGGER = LogManager.getLogger(VectorizarTablaRta.class);
    private static final String MESSAGE_ERROR = "Error en vectorizar. EX000 ";
    private static final String MESSAGE_ERROR_HASH = "Error en hashMapObject. EX000 ";

    public static String vectorizar(
            List<ConfiguracionColumnas> configuracion,
            Object respuesta) throws ApplicationException {
        try {
            StringBuilder cadena = new StringBuilder("");
            String aux = "", campo = "";
            HashMap<String, Object> mapaObjecto = hashMapObject(respuesta);
            for (ConfiguracionColumnas columna : configuracion) {
                aux = (String) mapaObjecto.get(columna.getNomColumna().toUpperCase());
                campo = columna.getTipoColumna();
                int numCaracteres = Integer.parseInt(columna.getNumCaracteres());
                if (campo.equals(Constantes.ALFANUMERICO)) {
                    if (aux == null || aux.equalsIgnoreCase("null") || aux.equals("")) {
                        aux = String.format("%-".concat(columna.getNumCaracteres() + "s|"), "");
                    } else {
                        aux = String.format("%-".concat(columna.getNumCaracteres() + "s|"),
                                (aux.length() <= numCaracteres) ? aux : aux.substring(0, numCaracteres));
                    }
                } else if (campo.equals(Constantes.ALFANUMERICO_ALIGN_RIGHT)) {
                    if (aux == null || aux.equalsIgnoreCase("null") || aux.equals("")) {
                        aux = String.format("%".concat(columna.getNumCaracteres() + "s|"), "");
                    } else {
                        if (aux.length() <= numCaracteres) {
                            aux = String.format("%".concat(columna.getNumCaracteres() + "s|"), aux);
                        } else {
                            aux = String.format("%".concat(columna.getNumCaracteres() + "s|"),
                                    aux.substring(0, numCaracteres));
                        }
                    }
                } else {
                    if (aux == null || aux.equalsIgnoreCase("")) {
                        aux = String.format("%0".concat(columna.getNumCaracteres() + "d|"), 0);
                    } else {
                        if (aux.length() <= numCaracteres) {
                            aux = String.format("%0".concat(columna.getNumCaracteres() + "d|"), Long.parseLong(aux));
                        } else {
                            aux = String.format("%0".concat(columna.getNumCaracteres() + "d|"),
                                    Long.parseLong(aux.substring(0, numCaracteres)));
                        }
                    }
                }
                cadena.append(aux);
            }
            return cadena.toString().toUpperCase();

        } catch (Exception ex) {
            LOGGER.error(MESSAGE_ERROR.concat(ex.getMessage()), ex);
            throw new ApplicationException(MESSAGE_ERROR.concat(ex.getMessage()), ex);
        }
    }

    private static HashMap<String, Object> hashMapObject(Object objeto) throws ApplicationException {
        HashMap<String, Object> mapa = new HashMap<>();
        Field[] fields = objeto.getClass().getDeclaredFields();
        for (Field f : fields) {
            try {
                String field = f.getName();
                Method getter = objeto.getClass().getMethod("get"
                        + String.valueOf(field.charAt(0)).toUpperCase()
                        + field.substring(1));

                Object value = getter.invoke(objeto, new Object[0]);
                mapa.put(field.toUpperCase(), value);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException ex) {
                LOGGER.error(MESSAGE_ERROR_HASH.concat(ex.getMessage()), ex);
                throw new ApplicationException(MESSAGE_ERROR_HASH.concat(ex.getMessage()), ex);
            } catch (Exception ex) {
                LOGGER.error(MESSAGE_ERROR_HASH.concat(ex.getMessage()), ex);
                throw new ApplicationException(MESSAGE_ERROR_HASH.concat(ex.getMessage()), ex);
            }
        }
        return mapa;
    }

    protected static final List<ConfiguracionColumnas> tablaAlimentacionElectrica = new ArrayList<>(Arrays.asList(
            new ConfiguracionColumnas("codigo", "3", Constantes.ALFANUMERICO),
            new ConfiguracionColumnas("descripcion", "30", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaAdminCompany = new ArrayList<>(Arrays.asList(
            new ConfiguracionColumnas("codigo", "4", Constantes.NUMERICO),
            new ConfiguracionColumnas("nombre", "30", Constantes.ALFANUMERICO),
            new ConfiguracionColumnas("identificacion", "11", Constantes.ALFANUMERICO),
            new ConfiguracionColumnas("telefono1", "10", Constantes.NUMERICO),
            new ConfiguracionColumnas("telefono2", "10", Constantes.NUMERICO),
            new ConfiguracionColumnas("telefono3", "10", Constantes.NUMERICO),
            new ConfiguracionColumnas("telefono4", "10", Constantes.NUMERICO),
            new ConfiguracionColumnas("nombreAdmin", "30", Constantes.ALFANUMERICO),
            new ConfiguracionColumnas("direccion", "30", Constantes.ALFANUMERICO),
            new ConfiguracionColumnas("email1", "30", Constantes.ALFANUMERICO),
            new ConfiguracionColumnas("email2", "30", Constantes.ALFANUMERICO),
            new ConfiguracionColumnas("paginaWeb", "30", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaEdificio = new ArrayList<>(Arrays.asList(
            new ConfiguracionColumnas("codigo", "9", Constantes.NUMERICO),
            new ConfiguracionColumnas("descripcion", "40", Constantes.ALFANUMERICO),
            new ConfiguracionColumnas("codDivision", "3", Constantes.ALFANUMERICO),
            new ConfiguracionColumnas("nomDivision", "30", Constantes.ALFANUMERICO),
            new ConfiguracionColumnas("codComunidad", "3", Constantes.ALFANUMERICO),
            new ConfiguracionColumnas("nomComunidad", "25", Constantes.ALFANUMERICO),
            new ConfiguracionColumnas("codBarrio", "9", Constantes.NUMERICO),
            new ConfiguracionColumnas("nomBarrio", "50", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaEdificioCompleta = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("nomEdificio", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("codCalle", "6", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("descCalle", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("numCasa", "10", Constantes.ALFANUMERICO_ALIGN_RIGHT),
                    new ConfiguracionColumnas("codTipoEdificio", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("descTipoEdificio", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("telPorteria", "10", Constantes.NUMERICO),
                    new ConfiguracionColumnas("codEstado", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("descEstad", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("telOtro", "10", Constantes.NUMERICO),
                    new ConfiguracionColumnas("codDir1", "6", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("descDir1", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("casaDir1", "10", Constantes.ALFANUMERICO_ALIGN_RIGHT),
                    new ConfiguracionColumnas("codDir2", "6", Constantes.NUMERICO),
                    new ConfiguracionColumnas("descDir2", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("casaDir2", "10", Constantes.ALFANUMERICO_ALIGN_RIGHT),
                    new ConfiguracionColumnas("codDir3", "6", Constantes.NUMERICO),
                    new ConfiguracionColumnas("descDir3", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("casaDir3", "10", Constantes.ALFANUMERICO_ALIGN_RIGHT),
                    new ConfiguracionColumnas("codDir4", "6", Constantes.NUMERICO),
                    new ConfiguracionColumnas("descDir4", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("casaDir4", "10", Constantes.ALFANUMERICO_ALIGN_RIGHT),
                    new ConfiguracionColumnas("nomContacto", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("codAdministracion", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("descAdministracion", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("codCiaAscensores", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("descCiaAscensores", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("headEnd", "8", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("tipo", "2", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nodo", "8", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("totalUnidades", "7", Constantes.NUMERICO),
                    new ConfiguracionColumnas("costoApartament", "15", Constantes.NUMERICO),
                    new ConfiguracionColumnas("codEstrato", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("descEstrato", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("Codproducto", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nomProducto", "50", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaEdificioCompetencia = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigoCompetencia", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("descripcion", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("tipo", "4", Constantes.NUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaCiaAscensores = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigo", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nombre", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("identificacion", "11", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("telefono1", "10", Constantes.NUMERICO),
                    new ConfiguracionColumnas("telefono2", "10", Constantes.NUMERICO),
                    new ConfiguracionColumnas("telefono3", "10", Constantes.NUMERICO),
                    new ConfiguracionColumnas("telefono4", "10", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nombreContacto", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("direccion", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("email1", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("email2", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("paginaWeb", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("convenio", "1", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaBlackList = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigo", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombre", "30", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaCompetencia = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigo", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nombre", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("tipo", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("descripcion", "50", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaCrearTrabajo = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("tipoTrabajo", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nombreTipoTrabajo", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("codigoCuenta", "9", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nombreCuenta", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("numeroTrabajo", "5", Constantes.NUMERICO),
                    new ConfiguracionColumnas("tipoEdificio", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nombreTipoEdificio", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombreEstado", "15", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("direccion", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("telefono1", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("telefono2", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("contacto", "34", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("totalUnidades", "7", Constantes.NUMERICO),
                    new ConfiguracionColumnas("totalHomePassed", "7", Constantes.NUMERICO),
                    new ConfiguracionColumnas("usuarioGrabacion", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("dealer", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombreDealer", "25", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("observacion1", "75", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("observacion2", "75", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("observacion3", "75", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("observacion4", "75", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("observacion5", "75", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("observacion6", "75", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("observacion7", "75", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("arregloCodigo", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("arregloNombre", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("fechaProgramacion", "8", Constantes.NUMERICO),
                    new ConfiguracionColumnas("horaProgramacion", "6", Constantes.NUMERICO),
                    new ConfiguracionColumnas("tecnicoOt", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("resultadoModificacion", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("clase", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("fabrica", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("serie", "20", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("estado", "4", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nitCliente", "15", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("numeroOTPadre", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombreCliente", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("segmento", "20", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("numeroOThija", "10", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaCrearTrabajoSubEdificio = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("tipoTrabajo", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nombreTipoTrabajo", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("codigoCuenta", "9", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nombreCuenta", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("numeroTrabajo", "5", Constantes.NUMERICO),
                    new ConfiguracionColumnas("tipoEdificio", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nombreTipoEdificio", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombreEstado", "15", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("direccion", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("telefono1", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("telefono2", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("contacto", "34", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("totalUnidades", "7", Constantes.NUMERICO),
                    new ConfiguracionColumnas("totalHomePassed", "7", Constantes.NUMERICO),
                    new ConfiguracionColumnas("usuarioGrabacion", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("dealer", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombreDealer", "25", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("observacion1", "75", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("observacion2", "75", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("observacion3", "75", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("observacion4", "75", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("observacion5", "75", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("observacion6", "75", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("tipoTrabajo", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nombreTipoTrabajo", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("codigoCuenta", "9", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nombreCuenta", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("numeroTrabajo", "5", Constantes.NUMERICO),
                    new ConfiguracionColumnas("tipoEdificio", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nombreTipoEdificio", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombreEstado", "15", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("direccion", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("telefono1", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("telefono2", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("contacto", "34", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("totalUnidades", "7", Constantes.NUMERICO),
                    new ConfiguracionColumnas("totalHomePassed", "7", Constantes.NUMERICO),
                    new ConfiguracionColumnas("usuarioGrabacion", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("dealer", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombreDealer", "25", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("observacion1", "75", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("observacion2", "75", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("observacion3", "75", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("observacion4", "75", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("observacion5", "75", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("observacion6", "75", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("observacion7", "75", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("arregloCodigo", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("arregloNombre", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("fechaProgramacion", "8", Constantes.NUMERICO),
                    new ConfiguracionColumnas("horaProgramacion", "6", Constantes.NUMERICO),
                    new ConfiguracionColumnas("tecnicoOt", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("resultadoModificacion", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("clase", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("fabrica", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("serie", "20", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("estado", "4", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nitCliente", "15", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("numeroOTPadre", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombreCliente", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("segmento", "20", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("numeroOThija", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("observacion7", "75", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("arregloCodigo", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("arregloNombre", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("fechaProgramacion", "8", Constantes.NUMERICO),
                    new ConfiguracionColumnas("horaProgramacion", "6", Constantes.NUMERICO),
                    new ConfiguracionColumnas("tecnicoOt", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("resultadoModificacion", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("clase", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("fabrica", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("serie", "20", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("estado", "4", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nitCliente", "15", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("numeroOTPadre", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombreCliente", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("segmento", "20", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("numeroOThija", "10", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaManttoEstadoEdificio = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigo", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("descripcion", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("estado", "1", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("validarEstado", "1", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("excepcionRadiografia", "1", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaCiaAdminCompleta = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("descripcionEdificio", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("codigoOrigenDatos", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("descripcionOrigenDatos", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("codigoTipoProyecto", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nombreTipoProyecto", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("codigoConstructor", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nombreConstructor", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("fechaEntrega", "8", Constantes.NUMERICO),
                    new ConfiguracionColumnas("codigoEstado", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("descripcionEstado", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("codigoAsesor", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombreAsesor", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("codigoEspecialista", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombreEspecialista", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("codigoSupervisor", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombreSupervisor", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("codigoTecnico", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombreTecnico", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("VT", "1", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("fechaVT", "8", Constantes.NUMERICO),
                    new ConfiguracionColumnas("costoVT", "13", Constantes.NUMERICO),
                    new ConfiguracionColumnas("reDiseno", "1", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("fechaReporteDiseno", "8", Constantes.NUMERICO),
                    new ConfiguracionColumnas("fechaRespuestaDiseno", "8", Constantes.NUMERICO),
                    new ConfiguracionColumnas("cierre", "1", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("fechaRecibidoCierre", "8", Constantes.NUMERICO),
                    new ConfiguracionColumnas("meta", "8", Constantes.NUMERICO),
                    new ConfiguracionColumnas("fechaInicioEjecucion", "8", Constantes.NUMERICO),
                    new ConfiguracionColumnas("rePlanos", "1", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("fechaSolicitudPlanos", "8", Constantes.NUMERICO),
                    new ConfiguracionColumnas("fechaEntregaPlanos", "8", Constantes.NUMERICO),
                    new ConfiguracionColumnas("conectRCorriente", "1", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("fechaSolicitudConectRCorriente", "8", Constantes.NUMERICO),
                    new ConfiguracionColumnas("fechaEntregaConectRCorriente", "8", Constantes.NUMERICO),
                    new ConfiguracionColumnas("codigoMotivo", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("descripcionMotivo", "60", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("fechaFinEjecucion", "8", Constantes.NUMERICO),
                    new ConfiguracionColumnas("costoEjecucion", "13", Constantes.NUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaInfoAdSubEdificio = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("descripcionSubedificio", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombreEdificio", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("codigoOrigenDatos", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("descripcionOrigenDatos", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("codigoTipoProyecto", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nombreTipoProyecto", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("codigoConstructor", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nombreConstructor", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("fechaEntrega", "8", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("codigoEstado", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("descripcionEstado", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("codigoAsesor", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombreAsesor", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("codigoEspecialista", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombreEspecialista", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("codigoSupervisor", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombreSupervisor", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("codigoTecnico", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombreTecnico", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("VT", "1", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("fechaVT", "8", Constantes.NUMERICO),
                    new ConfiguracionColumnas("costoVT", "13", Constantes.NUMERICO),
                    new ConfiguracionColumnas("reDiseno", "1", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("fechaReporteDiseno", "8", Constantes.NUMERICO),
                    new ConfiguracionColumnas("fechaRespuestaDiseno", "8", Constantes.NUMERICO),
                    new ConfiguracionColumnas("cierre", "1", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("fechaRecibidoCierre", "8", Constantes.NUMERICO),
                    new ConfiguracionColumnas("meta", "8", Constantes.NUMERICO),
                    new ConfiguracionColumnas("fechaInicioEjecucion", "8", Constantes.NUMERICO),
                    new ConfiguracionColumnas("rePlanos", "1", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("fechaSolicitudPlanos", "8", Constantes.NUMERICO),
                    new ConfiguracionColumnas("fechaEntregaPlanos", "8", Constantes.NUMERICO),
                    new ConfiguracionColumnas("conectRCorriente", "1", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("fechaSolicitudConectRCorriente", "8", Constantes.NUMERICO),
                    new ConfiguracionColumnas("fechaEntregaConectRCorriente", "8", Constantes.NUMERICO),
                    new ConfiguracionColumnas("codigoMotivo", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("descripcionMotivo", "60", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("fechaFinEjecucion", "8", Constantes.NUMERICO),
                    new ConfiguracionColumnas("costoEjecucion", "13", Constantes.NUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaMaterialProveedor = new ArrayList<>(
            Arrays.asList(new ConfiguracionColumnas("codigoProveedor", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("codigoMaterial", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("costoUnitario", "15", Constantes.NUMERICO),
                    new ConfiguracionColumnas("fecha", "8", Constantes.NUMERICO),
                    new ConfiguracionColumnas("tipoMaterial", "20", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombreProveedor", "50", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaSeleccionMateriales = new ArrayList<ConfiguracionColumnas>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigo", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nombre", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("tipo", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("descripcion", "50", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaRazonArreglo = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigo", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nombre", "30", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaOrigenDatos = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigo", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("descripcion", "30", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaTipoAcometida = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigo", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombre", "30", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaTipoMateriales = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigo", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nombre", "30", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaTipoProyecto = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigo", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nombre", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("indicador", "1", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaEstadoResultadoOt = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigo", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombre", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("programado", "1", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("enCurso", "1", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("realizado", "1", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("cancelado", "1", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("estadoCodigo", "1", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaManttoCompetencia = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigo", "4", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombre", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("tipo", "4", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("descripcion", "50", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaProductos = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigo", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nombre", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("validacionMeta", "15", Constantes.NUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaProveedores = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigo", "4", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombre", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nit", "11", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("telefono1", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("telefono2", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("telefono3", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("telefono4", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("gerente", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("direccion", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("email1", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("email2", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("paginaWeb", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("personaContacto1", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("personaContacto2", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("personaContacto3", "50", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaManttoPuntoInicial = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigo", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombre", "30", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaManttoTipoDistribucionRedInterna = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigo", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombre", "30", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaManttoTipoEdificio = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigo", "4", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombre", "30", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaManttoTipoNotas = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigo", "4", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombre", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("puntoInicial", "1", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("tipoAcometida", "1", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("alimentacionElectrica", "1", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("tipoDistribucion", "1", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("ubicacionCaja", "1", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaManttoEdificios = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigo", "9", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("descripcion", "40", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("codigoDivision", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombreDivision", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("codigoComunidad", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombreComunidad", "25", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("codigoBarrio", "9", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombreBarrio", "50", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaManttoMateriales = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigo", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nombre", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("tipo", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("tipoNombre", "50", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaSupervisorAvanzada = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigo", "15", Constantes.NUMERICO),
                    new ConfiguracionColumnas("descripcion", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("codigoDivision", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombreDivision", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("celular", "10", Constantes.NUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaConstructoras = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigo", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("descripcion", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("telefono1", "10", Constantes.NUMERICO),
                    new ConfiguracionColumnas("telefono2", "10", Constantes.NUMERICO),
                    new ConfiguracionColumnas("correoElectronico", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombreContacto", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("direccion", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("observacion1", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("observacion2", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("observacion3", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("observacion4", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("observacion5", "50", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaTipoEstrato = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigo", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nombre", "30", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaTipoTrabajo = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigo", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nombre", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("notasTrabajo", "1", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("costoPresupuesto", "1", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("costoReal", "1", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("asignacionTecnico", "1", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("razonArreglo", "1", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("asignacionInventario", "1", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("desvinculaInventario", "1", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaUbicacionCaja = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigo", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombre", "30", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaManttoSubedificios = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigo", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("descripcion", "24", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("codigoEstado", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("estado", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nodo", "6", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaManttoInfoNodo = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("numero", "5", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("codigo", "10", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombre", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("fechaApertura", "8", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("costoRed", "15", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("limites", "50", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaBarrios = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigo", "9", Constantes.NUMERICO),
                    new ConfiguracionColumnas("descripcion", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("codigoDivision", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombreDivision", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("codigoComunidad", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombreComunidad", "30", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaInventarioCuentaMatriz = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigoSubedificio", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("tipo", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("fabrica", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("numeroSerie", "20", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("estado", "4", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaNotasCuentaMatriz = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigoNota", "5", Constantes.NUMERICO),
                    new ConfiguracionColumnas("codigoDescripcion", "3", Constantes.NUMERICO),
                    new ConfiguracionColumnas("descripcionNota", "75", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("fechaCreacion", "8", Constantes.NUMERICO),
                    new ConfiguracionColumnas("fechaModificacion", "8", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nombreUsuario", "10", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaGestionDeAvanzada = new ArrayList<ConfiguracionColumnas>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigoEdificio", "9", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nombreEdificio", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("nombreAsesor", "40", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("telefono1", "10", Constantes.NUMERICO),
                    new ConfiguracionColumnas("codigoSupervisor", "40", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("telefono2", "10", Constantes.NUMERICO),
                    new ConfiguracionColumnas("descripcionEstado", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("estado", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("codigoSubEdificio", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nombreSubEdificio", "20", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("fechaGestion", "8", Constantes.NUMERICO),
                    new ConfiguracionColumnas("observacion1", "79", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("observacion2", "79", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("observacion3", "79", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("observacion4", "79", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("observacion5", "79", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaAsesorAvanzada = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigo", "15", Constantes.NUMERICO),
                    new ConfiguracionColumnas("descripcion", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("codigoSupervisor", "15", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nombreSupervisor", "24", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("division", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("celular", "10", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaEstadosAvanzada = new ArrayList<ConfiguracionColumnas>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigo", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("descripcion", "30", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaAsignarAsesorAvanzada = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigoEdificio", "9", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nombreEdificio", "30", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("comunidad", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("codigoAsesor", "15", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nombreAsesor", "20", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaMotivosCambioFecha = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigo", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("descripcion", "60", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaManttoTipoCompetencia = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigo", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("descripcion", "30", Constantes.ALFANUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaNotasOtCuentaMatriz = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigoNota", "5", Constantes.NUMERICO),
                    new ConfiguracionColumnas("codigoDescripcion", "3", Constantes.NUMERICO),
                    new ConfiguracionColumnas("tipoNota", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nombreTipo", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("ptoInic", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("aliElec", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("tipAcom", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("ubiCaja", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("tipDist", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("descripcion", "75", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("fechaCreacion", "8", Constantes.NUMERICO)));

    protected static final List<ConfiguracionColumnas> tablaNotasOtCmSubEdificio = new ArrayList<>(
            Arrays.asList(
                    new ConfiguracionColumnas("codigoNota", "5", Constantes.NUMERICO),
                    new ConfiguracionColumnas("codigoDescripcion", "3", Constantes.NUMERICO),
                    new ConfiguracionColumnas("tipoNota", "4", Constantes.NUMERICO),
                    new ConfiguracionColumnas("nombreTipo", "50", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("ptoInic", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("aliElec", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("tipAcom", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("ubiCaja", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("tipDist", "3", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("descripcion", "75", Constantes.ALFANUMERICO),
                    new ConfiguracionColumnas("fechaCreacion", "8", Constantes.NUMERICO)));
}