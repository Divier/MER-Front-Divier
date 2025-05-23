/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.business.direcciones;

import co.com.claro.direcciones.business.NegocioGeograficoPolitico;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.services.util.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author carlos.villa.ext
 */
public class NegocioDireccionesRRDrDir {
    
    private static final Logger LOGGER = LogManager.getLogger(NegocioDireccionesRRDrDir.class);

    private String resultadoDirRRNumeroUnidad = "";
    private String resultadoDirRRCalle = "";
    private String resultadoDirRRNumeroApartamento = "";
    private static final int LONGITUD_TIPO_BOGOTA = 78;
    private static final int LONGITUD_TIPO_HERENDAN_BOGOTA_MEDELLIN = 79;
    private static final int LONGITUD_TIPO_CALI = 87;
    private static final int LONGITUD_TIPO_MAZANA_CASA = 99;
    private static final String TIPO_BOGOTA = "B";
    private static final String TIPO_HERENDAN_BOGOTA = "HB";
    private static final String TIPO_MEDELLIN = "M";
    private static final String TIPO_CALI = "C";
    private static final String TIPO_MAZANA_CASA = "BMC";
    private static final int indiceInicial = 0;
    private static final int LongitudATomar = 1;
    private String tipoViaPrincipal;
    private String numeroViaPrincipal;
    private String letraNumeroViaPrincipal;
    private String numerosLetrasPosterioresLetraViaPrincipal;
    private String bisViaPrincipal;
    private String letraNumeroposteriorBisViaPrincipal;
    private String cuadranteViaPrincipal;
    private String tipoViaGeneradora;
    private String numeroViaGeneradora;
    private String letraNumeroViaGeneradora;
    private String numeroLetrasPosterioresLetraViaGeneradora;
    private String bisViaGeneradora;
    private String letraNumeroPosteriorBisViaGeneradora;
    private String cuadranteViaGeneradora;
    private String placaDireccion;
    
    private String bmcTipoNivel2;
    private String bmcValorNivel2;
    private String bmcTipoNivel3;
    private String bmcValorNivel3;
    private String bmcTipoNivel4;
    private String bmcValorNivel4;
    private String bmcTipoNivel5;
    private String bmcValorNivel5;
    private String bmcTipoNivel6;
    private String bmcValorNivel6;
    
    
    private int[] vTipoViaPrincipal;
    private int[] vNumeroViaPrincipal;
    private int[] vLetraNumeroViaPrincipal;
    private int[] vNumerosLetrasPosterioresLetraViaPrincipal;
    private int[] vBisViaPrincipal;
    private int[] vLetraNumeroposteriorBisViaPrincipal;
    private int[] vCuadranteViaPrincipal;
    private int[] vTipoViaGeneradora;
    private int[] vNumeroViaGeneradora;
    private int[] vLetraNumeroViaGeneradora;
    private int[] vNumeroLetrasPosterioresLetraViaGeneradora;
    private int[] vBisViaGeneradora;
    private int[] vLetraNumeroPosteriorBisViaGeneradora;
    private int[] vCuadranteViaGeneradora;
    private int[] vPlacaDireccion;
    private int[] vBmcTipoNivel2;
    private int[] vBmcValorNivel2;
    private int[] vBmcTipoNivel3;
    private int[] vBmcValorNivel3;
    private int[] vBmcTipoNivel4;
    private int[] vBmcValorNivel4;
    private int[] vBmcTipoNivel5;
    private int[] vBmcValorNivel5;
    private int[] vBmcTipoNivel6;
    private int[] vBmcValorNivel6;
    private NegocioGeograficoPolitico negocioGeograficoPolitico = new NegocioGeograficoPolitico();
    private GeograficoPolitico geograficoPolitico;
    private String tipoDireccion;

    /**
     * @return the resultadoDirRRNumeroUnidad
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public String getResultadoDirRRNumeroUnidad() throws ApplicationException {
        if (resultadoDirRRNumeroUnidad == null || resultadoDirRRNumeroUnidad.isEmpty()) {
            LOGGER.error("No hay Numero unidad RR, verifique la siganción del codigo de georeferenciacion correcto");
        }
        return resultadoDirRRNumeroUnidad;
    }

    /**
     * @return the resultadoDirRRCalle
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public String getResultadoDirRRCalle() throws ApplicationException {
        if (resultadoDirRRCalle == null || resultadoDirRRCalle.isEmpty()) {
            LOGGER.error("No hay Calle RR, verifique la siganción del codigo de georeferenciacion correcto");
        }
        return resultadoDirRRCalle;
    }

    /**
     * @return the resultadoDirRRNumeroApartamento
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public String getResultadoDirRRNumeroApartamento() throws ApplicationException {
        if (resultadoDirRRNumeroApartamento == null || resultadoDirRRNumeroApartamento.isEmpty()) {
            LOGGER.error("No hay Apartamento RR, verifique la siganción del codigo de georeferenciacion correcto");
        }
        return resultadoDirRRNumeroApartamento;
    }

    private void borraVariables() {

        tipoViaPrincipal = null;
        numeroViaPrincipal = null;
        letraNumeroViaPrincipal = null;
        numerosLetrasPosterioresLetraViaPrincipal = null;
        bisViaPrincipal = null;
        letraNumeroposteriorBisViaPrincipal = null;
        cuadranteViaPrincipal = null;
        tipoViaGeneradora = null;
        numeroViaGeneradora = null;
        letraNumeroViaGeneradora = null;
        numeroLetrasPosterioresLetraViaGeneradora = null;
        bisViaGeneradora = null;
        letraNumeroPosteriorBisViaGeneradora = null;
        cuadranteViaGeneradora = null;
        placaDireccion = null;
        bmcTipoNivel2 = null;
        bmcValorNivel2 = null;
        bmcTipoNivel3 = null;
        bmcValorNivel3 = null;
        bmcTipoNivel4 = null;
        bmcValorNivel4 = null;
        bmcTipoNivel5 = null;
        bmcValorNivel5 = null;
        bmcTipoNivel6 = null;
        bmcValorNivel6 = null;
        vTipoViaPrincipal = null;
        vNumeroViaPrincipal = null;
        vLetraNumeroViaPrincipal = null;
        vNumerosLetrasPosterioresLetraViaPrincipal = null;
        vBisViaPrincipal = null;
        vLetraNumeroposteriorBisViaPrincipal = null;
        vCuadranteViaPrincipal = null;
        vTipoViaGeneradora = null;
        vNumeroViaGeneradora = null;
        vLetraNumeroViaGeneradora = null;
        vNumeroLetrasPosterioresLetraViaGeneradora = null;
        vBisViaGeneradora = null;
        vLetraNumeroPosteriorBisViaGeneradora = null;
        vCuadranteViaGeneradora = null;
        vPlacaDireccion = null;
        vBmcTipoNivel2 = null;
        vBmcValorNivel2 = null;
        vBmcTipoNivel3 = null;
        vBmcValorNivel3 = null;
        vBmcTipoNivel4 = null;
        vBmcValorNivel4 = null;
        vBmcTipoNivel5 = null;
        vBmcValorNivel5 = null;
        vBmcTipoNivel6 = null;
        vBmcValorNivel6 = null;
    }

    private String ValidarTipoDireccion(String codGeo, BigDecimal city) throws ApplicationException {
        geograficoPolitico = negocioGeograficoPolitico.queryGeograficoPolitico(city);
        tipoDireccion = "CK";
        switch (codGeo.trim().length()) {
            case LONGITUD_TIPO_BOGOTA:
                return TIPO_BOGOTA;
            case LONGITUD_TIPO_CALI:
                return TIPO_CALI;
            case LONGITUD_TIPO_HERENDAN_BOGOTA_MEDELLIN:
                if (geograficoPolitico != null) {
                    if (geograficoPolitico.getGpoCodTipoDireccion().equalsIgnoreCase("B")) {
                        return TIPO_HERENDAN_BOGOTA;
                    }
                    if (geograficoPolitico.getGpoCodTipoDireccion().equalsIgnoreCase("M")) {
                        return TIPO_MEDELLIN;
                    }
                    throw new ApplicationException("La ciudad no tiene tipo de dirección, Cali-Medellin-Bogota");
                } else {
                    throw new ApplicationException("La ciudad no se encontró en Base de Datos");
                }
            case LONGITUD_TIPO_MAZANA_CASA:
                tipoDireccion = "BM";
                return TIPO_MAZANA_CASA;
            default:
                throw new ApplicationException("La longitud de codigo de direccion geo corresponde con establecidos, Direccion Intraducible");
        }
    }

    public void calculateDireccion(String codigoDireccionGeo, BigDecimal city, String barrio, String complemento) throws ApplicationException {
        borraVariables();
        GenerarDireccion(ValidarTipoDireccion(codigoDireccionGeo, city), codigoDireccionGeo, barrio, complemento);

    }

    private void GenerarDireccion(String tipoDireccion, String CodigoDireccionGeo, String barrio, String complemento) throws ApplicationException {
        if (tipoDireccion.equalsIgnoreCase(TIPO_BOGOTA)) {
            prepararDireccionTipoBogota();
            extraerDireccionBogota(CodigoDireccionGeo);
            remplazarValoresRR();
            crearDireccionTipoBogota(barrio, complemento);
        }
        if (tipoDireccion.equalsIgnoreCase(TIPO_HERENDAN_BOGOTA)) {
            prepararDireccionTipoHeredaBogota();
            extraerDireccionHeredaBogota(CodigoDireccionGeo);
            remplazarValoresRR();
            crearDireccionTipoBogota(barrio, complemento);
        }
        if (tipoDireccion.equalsIgnoreCase(TIPO_CALI)) {
            prepararDireccionTipoCali();
            extraerDireccionCali(CodigoDireccionGeo);
            remplazarValoresRR();
            crearDireccionTipoCali(barrio, complemento);
        }
        if (tipoDireccion.equalsIgnoreCase(TIPO_MEDELLIN)) {
            prepararDireccionTipoMedillin();
            extraerDireccionMedellin(CodigoDireccionGeo);
            remplazarValoresRR();
            crearDireccionTipoMedellin(barrio, complemento);
        }
        if (tipoDireccion.equalsIgnoreCase(TIPO_MAZANA_CASA)) {
            prepararDireccionTipoBMC();
            extraerDireccionBCM(CodigoDireccionGeo);
            remplazarValoresRR();
            crearDireccionTipoBMC(barrio, complemento);
        }

    }

    private void prepararDireccionTipoBogota() {
        cuadranteViaPrincipal = "";
        cuadranteViaGeneradora = "";
        tipoViaPrincipal = "";
        numeroViaPrincipal = "";
        numeroViaGeneradora = "";
        letraNumeroViaPrincipal = "";
        letraNumeroViaGeneradora = "";
        bisViaPrincipal = "";
        bisViaGeneradora = "";
        letraNumeroposteriorBisViaPrincipal = "";
        letraNumeroPosteriorBisViaGeneradora = "";
        placaDireccion = "";
        vCuadranteViaPrincipal = new int[]{8, 9};
        vCuadranteViaGeneradora = new int[]{9, 10};
        vTipoViaPrincipal = new int[]{10, 12};
        vNumeroViaPrincipal = new int[]{12, 15};
        vNumeroViaGeneradora = new int[]{15, 18};
        vLetraNumeroViaPrincipal = new int[]{18, 19};
        vLetraNumeroViaGeneradora = new int[]{19, 20};
        vBisViaPrincipal = new int[]{20, 23};
        vBisViaGeneradora = new int[]{23, 26};
        vLetraNumeroposteriorBisViaPrincipal = new int[]{26, 27};
        vLetraNumeroPosteriorBisViaGeneradora = new int[]{27, 28};
        vPlacaDireccion = new int[]{28, 30};
    }

    private void prepararDireccionTipoHeredaBogota() {
        cuadranteViaPrincipal = "";
        cuadranteViaGeneradora = "";
        tipoViaPrincipal = "";
        numeroViaPrincipal = "";
        letraNumeroViaPrincipal = "";
        letraNumeroViaGeneradora = "";
        bisViaPrincipal = "";
        bisViaGeneradora = "";
        letraNumeroposteriorBisViaPrincipal = "";
        letraNumeroPosteriorBisViaGeneradora = "";
        placaDireccion = "";
        vCuadranteViaPrincipal = new int[]{8, 9};
        vCuadranteViaGeneradora = new int[]{9, 10};
        vTipoViaPrincipal = new int[]{10, 12};
        vNumeroViaPrincipal = new int[]{12, 15};
        vNumeroViaGeneradora = new int[]{15, 18};
        vLetraNumeroViaPrincipal = new int[]{18, 19};
        vLetraNumeroViaGeneradora = new int[]{19, 20};
        vBisViaPrincipal = new int[]{20, 23};
        vBisViaGeneradora = new int[]{23, 26};
        vLetraNumeroposteriorBisViaPrincipal = new int[]{26, 27};
        vLetraNumeroPosteriorBisViaGeneradora = new int[]{27, 28};
        vPlacaDireccion = new int[]{28, 31};
    }

    private void prepararDireccionTipoCali() {
        cuadranteViaPrincipal = "";
        cuadranteViaGeneradora = "";
        tipoViaPrincipal = "";
        tipoViaGeneradora = "";
        numeroViaPrincipal = "";
        numeroViaGeneradora = "";
        letraNumeroViaPrincipal = "";
        letraNumeroViaGeneradora = "";
        numerosLetrasPosterioresLetraViaPrincipal = "";
        numeroLetrasPosterioresLetraViaGeneradora = "";
        bisViaPrincipal = "";
        bisViaGeneradora = "";
        letraNumeroposteriorBisViaPrincipal = "";
        letraNumeroPosteriorBisViaGeneradora = "";
        placaDireccion = "";


        vCuadranteViaPrincipal = new int[]{8, 9};
        vCuadranteViaGeneradora = new int[]{9, 10};
        vTipoViaPrincipal = new int[]{10, 12};
        vTipoViaGeneradora = new int[]{12, 14};
        vNumeroViaPrincipal = new int[]{14, 17};
        vNumeroViaGeneradora = new int[]{17, 20};
        vLetraNumeroViaPrincipal = new int[]{20, 21};
        vLetraNumeroViaGeneradora = new int[]{21, 22};
        vNumerosLetrasPosterioresLetraViaPrincipal = new int[]{22, 24};
        vNumeroLetrasPosterioresLetraViaGeneradora = new int[]{24, 26};
        vBisViaPrincipal = new int[]{28, 31};
        vBisViaGeneradora = new int[]{31, 34};
        vLetraNumeroposteriorBisViaPrincipal = new int[]{34, 35};
        vLetraNumeroPosteriorBisViaGeneradora = new int[]{35, 36};
        vPlacaDireccion = new int[]{36, 39};
    }

    private void prepararDireccionTipoMedillin() {
        cuadranteViaPrincipal = "";
        cuadranteViaGeneradora = "";
        tipoViaPrincipal = "";
        numeroViaPrincipal = "";
        numeroViaGeneradora = "";
        bisViaPrincipal = "";
        bisViaGeneradora = "";
        letraNumeroViaPrincipal = "";
        letraNumeroViaGeneradora = "";
        numerosLetrasPosterioresLetraViaPrincipal = "";
        numeroLetrasPosterioresLetraViaGeneradora = "";
        placaDireccion = "";

        vCuadranteViaPrincipal = new int[]{8, 9};
        vCuadranteViaGeneradora = new int[]{9, 10};
        vTipoViaPrincipal = new int[]{10, 12};
        vNumeroViaPrincipal = new int[]{12, 15};
        vNumeroViaGeneradora = new int[]{15, 18};
        vBisViaPrincipal = new int[]{18, 21};
        vBisViaGeneradora = new int[]{21, 24};
        vLetraNumeroViaPrincipal = new int[]{24, 25};
        vLetraNumeroViaGeneradora = new int[]{25, 26};
        vNumerosLetrasPosterioresLetraViaPrincipal = new int[]{26, 27};
        vNumeroLetrasPosterioresLetraViaGeneradora = new int[]{27, 28};
        vPlacaDireccion = new int[]{28, 31};

    }

    void prepararDireccionTipoBMC() {
        bmcTipoNivel2 = "";
        bmcValorNivel2 = "";
        bmcTipoNivel3 = "";
        bmcValorNivel3 = "";
        bmcTipoNivel4 = "";
        bmcValorNivel4 = "";
        bmcTipoNivel5 = "";
        bmcValorNivel5 = "";
        bmcTipoNivel6 = "";
        bmcValorNivel6 = "";

        vBmcTipoNivel2 = new int[]{11, 13};
        vBmcValorNivel2 = new int[]{13, 19};
        vBmcTipoNivel3 = new int[]{19, 21};
        vBmcValorNivel3 = new int[]{21, 27};
        vBmcTipoNivel4 = new int[]{27, 29};
        vBmcValorNivel4 = new int[]{29, 35};
        vBmcTipoNivel5 = new int[]{35, 37};
        vBmcValorNivel5 = new int[]{37, 43};
        vBmcTipoNivel6 = new int[]{43, 45};
        vBmcValorNivel6 = new int[]{45, 51};
    }

    private void extraerDireccionBogota(String CodigoDireccionGeo) {
        cuadranteViaPrincipal = CodigoDireccionGeo.substring(vCuadranteViaPrincipal[indiceInicial], vCuadranteViaPrincipal[LongitudATomar]);
        cuadranteViaGeneradora = CodigoDireccionGeo.substring(vCuadranteViaGeneradora[indiceInicial], vCuadranteViaGeneradora[LongitudATomar]);
        tipoViaPrincipal = CodigoDireccionGeo.substring(vTipoViaPrincipal[indiceInicial], vTipoViaPrincipal[LongitudATomar]);
        numeroViaPrincipal = CodigoDireccionGeo.substring(vNumeroViaPrincipal[indiceInicial], vNumeroViaPrincipal[LongitudATomar]);
        numeroViaGeneradora = CodigoDireccionGeo.substring(vNumeroViaGeneradora[indiceInicial], vNumeroViaGeneradora[LongitudATomar]);
        letraNumeroViaPrincipal = CodigoDireccionGeo.substring(vLetraNumeroViaPrincipal[indiceInicial], vLetraNumeroViaPrincipal[LongitudATomar]);
        letraNumeroViaGeneradora = CodigoDireccionGeo.substring(vLetraNumeroViaGeneradora[indiceInicial], vLetraNumeroViaGeneradora[LongitudATomar]);
        bisViaPrincipal = CodigoDireccionGeo.substring(vBisViaPrincipal[indiceInicial], vBisViaPrincipal[LongitudATomar]);
        bisViaGeneradora = CodigoDireccionGeo.substring(vBisViaGeneradora[indiceInicial], vBisViaGeneradora[LongitudATomar]);
        letraNumeroposteriorBisViaPrincipal = CodigoDireccionGeo.substring(vLetraNumeroposteriorBisViaPrincipal[indiceInicial], vLetraNumeroposteriorBisViaPrincipal[LongitudATomar]);
        letraNumeroPosteriorBisViaGeneradora = CodigoDireccionGeo.substring(vLetraNumeroPosteriorBisViaGeneradora[indiceInicial], vLetraNumeroPosteriorBisViaGeneradora[LongitudATomar]);
        placaDireccion = CodigoDireccionGeo.substring(vPlacaDireccion[indiceInicial], vPlacaDireccion[LongitudATomar]);

        if (!cuadranteViaPrincipal.matches("(N|S|E|O)")) {
            cuadranteViaPrincipal = "";
        }
        if (!cuadranteViaGeneradora.matches("(N|S|E|O)")) {
            cuadranteViaGeneradora = "";
        }
        numeroViaPrincipal = Integer.toString(Integer.parseInt(numeroViaPrincipal));
        numeroViaGeneradora = Integer.toString(Integer.parseInt(numeroViaGeneradora));
        if (!letraNumeroViaPrincipal.matches("([a-z]|[A-Z])")) {
            letraNumeroViaPrincipal = "";
        }
        if (!letraNumeroViaGeneradora.matches("([a-z]|[A-Z])")) {
            letraNumeroViaGeneradora = "";
        }
        if (!bisViaPrincipal.matches("(BIS)")) {
            bisViaPrincipal = "";
        }
        if (!bisViaGeneradora.matches("(BIS)")) {
            bisViaGeneradora = "";
        }
        if (!letraNumeroposteriorBisViaPrincipal.matches("((([a-z]|[A-Z])|[1-9]))")) {
            letraNumeroposteriorBisViaPrincipal = "";
        }
        if (!letraNumeroPosteriorBisViaGeneradora.matches("((([a-z]|[A-Z])|[1-9]))")) {
            letraNumeroPosteriorBisViaGeneradora = "";
        }
        placaDireccion = Integer.toString(Integer.parseInt(placaDireccion));
    }

    private void extraerDireccionHeredaBogota(String CodigoDireccionGeo) {
        cuadranteViaPrincipal = CodigoDireccionGeo.substring(vCuadranteViaPrincipal[indiceInicial], vCuadranteViaPrincipal[LongitudATomar]);
        cuadranteViaGeneradora = CodigoDireccionGeo.substring(vCuadranteViaGeneradora[indiceInicial], vCuadranteViaGeneradora[LongitudATomar]);
        tipoViaPrincipal = CodigoDireccionGeo.substring(vTipoViaPrincipal[indiceInicial], vTipoViaPrincipal[LongitudATomar]);
        numeroViaPrincipal = CodigoDireccionGeo.substring(vNumeroViaPrincipal[indiceInicial], vNumeroViaPrincipal[LongitudATomar]);
        numeroViaGeneradora = CodigoDireccionGeo.substring(vNumeroViaGeneradora[indiceInicial], vNumeroViaGeneradora[LongitudATomar]);
        letraNumeroViaPrincipal = CodigoDireccionGeo.substring(vLetraNumeroViaPrincipal[indiceInicial], vLetraNumeroViaPrincipal[LongitudATomar]);
        letraNumeroViaGeneradora = CodigoDireccionGeo.substring(vLetraNumeroViaGeneradora[indiceInicial], vLetraNumeroViaGeneradora[LongitudATomar]);
        bisViaPrincipal = CodigoDireccionGeo.substring(vBisViaPrincipal[indiceInicial], vBisViaPrincipal[LongitudATomar]);
        bisViaGeneradora = CodigoDireccionGeo.substring(vBisViaGeneradora[indiceInicial], vBisViaGeneradora[LongitudATomar]);
        letraNumeroposteriorBisViaPrincipal = CodigoDireccionGeo.substring(vLetraNumeroposteriorBisViaPrincipal[indiceInicial], vLetraNumeroposteriorBisViaPrincipal[LongitudATomar]);
        letraNumeroPosteriorBisViaGeneradora = CodigoDireccionGeo.substring(vLetraNumeroPosteriorBisViaGeneradora[indiceInicial], vLetraNumeroPosteriorBisViaGeneradora[LongitudATomar]);
        placaDireccion = CodigoDireccionGeo.substring(vPlacaDireccion[indiceInicial], vPlacaDireccion[LongitudATomar]);

        if (!cuadranteViaPrincipal.matches("(N|S|E|O)")) {
            cuadranteViaPrincipal = "";
        }
        if (!cuadranteViaGeneradora.matches("(N|S|E|O)")) {
            cuadranteViaGeneradora = "";
        }
        numeroViaPrincipal = Integer.toString(Integer.parseInt(numeroViaPrincipal));
        numeroViaGeneradora = Integer.toString(Integer.parseInt(numeroViaGeneradora));
        if (!letraNumeroViaPrincipal.matches("([a-z]|[A-Z])")) {
            letraNumeroViaPrincipal = "";
        }
        if (!letraNumeroViaGeneradora.matches("([a-z]|[A-Z])")) {
            letraNumeroViaGeneradora = "";
        }
        if (!bisViaPrincipal.matches("(BIS)")) {
            bisViaPrincipal = "";
        }
        if (!bisViaGeneradora.matches("(BIS)")) {
            bisViaGeneradora = "";
        }
        if (!letraNumeroposteriorBisViaPrincipal.matches("((([a-z]|[A-Z])|[1-9]))")) {
            letraNumeroposteriorBisViaPrincipal = "";
        }
        if (!letraNumeroPosteriorBisViaGeneradora.matches("((([a-z]|[A-Z])|[1-9]))")) {
            letraNumeroPosteriorBisViaGeneradora = "";
        }
        placaDireccion = Integer.toString(Integer.parseInt(placaDireccion));
    }

    private void extraerDireccionCali(String CodigoDireccionGeo) {
        cuadranteViaPrincipal = CodigoDireccionGeo.substring(vCuadranteViaPrincipal[indiceInicial], vCuadranteViaPrincipal[LongitudATomar]);
        cuadranteViaGeneradora = CodigoDireccionGeo.substring(vCuadranteViaGeneradora[indiceInicial], vCuadranteViaGeneradora[LongitudATomar]);
        tipoViaPrincipal = CodigoDireccionGeo.substring(vTipoViaPrincipal[indiceInicial], vTipoViaPrincipal[LongitudATomar]);
        tipoViaGeneradora = CodigoDireccionGeo.substring(vTipoViaGeneradora[indiceInicial], vTipoViaGeneradora[LongitudATomar]);
        numeroViaPrincipal = CodigoDireccionGeo.substring(vNumeroViaPrincipal[indiceInicial], vNumeroViaPrincipal[LongitudATomar]);
        numeroViaGeneradora = CodigoDireccionGeo.substring(vNumeroViaGeneradora[indiceInicial], vNumeroViaGeneradora[LongitudATomar]);
        letraNumeroViaPrincipal = CodigoDireccionGeo.substring(vLetraNumeroViaPrincipal[indiceInicial], vLetraNumeroViaPrincipal[LongitudATomar]);
        letraNumeroViaGeneradora = CodigoDireccionGeo.substring(vLetraNumeroViaGeneradora[indiceInicial], vLetraNumeroViaGeneradora[LongitudATomar]);
        numerosLetrasPosterioresLetraViaPrincipal = CodigoDireccionGeo.substring(vNumerosLetrasPosterioresLetraViaPrincipal[indiceInicial], vNumerosLetrasPosterioresLetraViaPrincipal[LongitudATomar]);
        numeroLetrasPosterioresLetraViaGeneradora = CodigoDireccionGeo.substring(vNumeroLetrasPosterioresLetraViaGeneradora[indiceInicial], vNumeroLetrasPosterioresLetraViaGeneradora[LongitudATomar]);
        bisViaPrincipal = CodigoDireccionGeo.substring(vBisViaPrincipal[indiceInicial], vBisViaPrincipal[LongitudATomar]);
        bisViaGeneradora = CodigoDireccionGeo.substring(vBisViaGeneradora[indiceInicial], vBisViaGeneradora[LongitudATomar]);
        letraNumeroposteriorBisViaPrincipal = CodigoDireccionGeo.substring(vLetraNumeroposteriorBisViaPrincipal[indiceInicial], vLetraNumeroposteriorBisViaPrincipal[LongitudATomar]);
        letraNumeroPosteriorBisViaGeneradora = CodigoDireccionGeo.substring(vLetraNumeroPosteriorBisViaGeneradora[indiceInicial], vLetraNumeroPosteriorBisViaGeneradora[LongitudATomar]);
        placaDireccion = CodigoDireccionGeo.substring(vPlacaDireccion[indiceInicial], vPlacaDireccion[LongitudATomar]);

        if (!cuadranteViaPrincipal.matches("(N|S|E|O)")) {
            cuadranteViaPrincipal = "";
        }
        if (!cuadranteViaGeneradora.matches("(N|S|E|O)")) {
            cuadranteViaGeneradora = "";
        }

        if (tipoViaGeneradora.equalsIgnoreCase("00")) {
            tipoViaGeneradora = "";
        }

        numeroViaPrincipal = Integer.toString(Integer.parseInt(numeroViaPrincipal));
        numeroViaGeneradora = Integer.toString(Integer.parseInt(numeroViaGeneradora));
        if (!letraNumeroViaPrincipal.matches("([a-z]|[A-Z])")) {
            letraNumeroViaPrincipal = "";
        }
        if (!letraNumeroViaGeneradora.matches("([a-z]|[A-Z])")) {
            letraNumeroViaGeneradora = "";
        }
        if (!numerosLetrasPosterioresLetraViaPrincipal.matches("[1-9]*[0-9]")) {
            numerosLetrasPosterioresLetraViaPrincipal = "";
        }
        if (!numeroLetrasPosterioresLetraViaGeneradora.matches("[1-9]*[0-9]")) {
            numeroLetrasPosterioresLetraViaGeneradora = "";
        }
        if (!bisViaPrincipal.matches("(BIS)")) {
            bisViaPrincipal = "";
        }
        if (!bisViaGeneradora.matches("(BIS)")) {
            bisViaGeneradora = "";
        }
        if (!letraNumeroposteriorBisViaPrincipal.matches("((([a-z]|[A-Z])|[1-9]))")) {
            letraNumeroposteriorBisViaPrincipal = "";
        }
        if (!letraNumeroPosteriorBisViaGeneradora.matches("((([a-z]|[A-Z])|[1-9]))")) {
            letraNumeroPosteriorBisViaGeneradora = "";
        }
        placaDireccion = Integer.toString(Integer.parseInt(placaDireccion));
    }

    private void extraerDireccionMedellin(String CodigoDireccionGeo) {
        cuadranteViaPrincipal = CodigoDireccionGeo.substring(vCuadranteViaPrincipal[indiceInicial], vCuadranteViaPrincipal[LongitudATomar]);
        cuadranteViaGeneradora = CodigoDireccionGeo.substring(vCuadranteViaGeneradora[indiceInicial], vCuadranteViaGeneradora[LongitudATomar]);
        tipoViaPrincipal = CodigoDireccionGeo.substring(vTipoViaPrincipal[indiceInicial], vTipoViaPrincipal[LongitudATomar]);
        numeroViaPrincipal = CodigoDireccionGeo.substring(vNumeroViaPrincipal[indiceInicial], vNumeroViaPrincipal[LongitudATomar]);
        numeroViaGeneradora = CodigoDireccionGeo.substring(vNumeroViaGeneradora[indiceInicial], vNumeroViaGeneradora[LongitudATomar]);
        bisViaPrincipal = CodigoDireccionGeo.substring(vBisViaPrincipal[indiceInicial], vBisViaPrincipal[LongitudATomar]);
        bisViaGeneradora = CodigoDireccionGeo.substring(vBisViaGeneradora[indiceInicial], vBisViaGeneradora[LongitudATomar]);
        letraNumeroViaPrincipal = CodigoDireccionGeo.substring(vLetraNumeroViaPrincipal[indiceInicial], vLetraNumeroViaPrincipal[LongitudATomar]);
        letraNumeroViaGeneradora = CodigoDireccionGeo.substring(vLetraNumeroViaGeneradora[indiceInicial], vLetraNumeroViaGeneradora[LongitudATomar]);
        numerosLetrasPosterioresLetraViaPrincipal = CodigoDireccionGeo.substring(vNumerosLetrasPosterioresLetraViaPrincipal[indiceInicial], vNumerosLetrasPosterioresLetraViaPrincipal[LongitudATomar]);
        numeroLetrasPosterioresLetraViaGeneradora = CodigoDireccionGeo.substring(vNumeroLetrasPosterioresLetraViaGeneradora[indiceInicial], vNumeroLetrasPosterioresLetraViaGeneradora[LongitudATomar]);
        placaDireccion = CodigoDireccionGeo.substring(vPlacaDireccion[indiceInicial], vPlacaDireccion[LongitudATomar]);

        if (!cuadranteViaPrincipal.matches("(N|S|E|O)")) {
            cuadranteViaPrincipal = "";
        }
        if (!cuadranteViaGeneradora.matches("(N|S|E|O)")) {
            cuadranteViaGeneradora = "";
        }
        numeroViaPrincipal = Integer.toString(Integer.parseInt(numeroViaPrincipal));
        numeroViaGeneradora = Integer.toString(Integer.parseInt(numeroViaGeneradora));
        if (!letraNumeroViaPrincipal.matches("([a-z]|[A-Z])")) {
            letraNumeroViaPrincipal = "";
        }
        if (!letraNumeroViaGeneradora.matches("([a-z]|[A-Z])")) {
            letraNumeroViaGeneradora = "";
        }
        if (!numerosLetrasPosterioresLetraViaPrincipal.matches("[1-9]*[0-9]")) {
            numerosLetrasPosterioresLetraViaPrincipal = "";
        }
        if (!numeroLetrasPosterioresLetraViaGeneradora.matches("[1-9]*[0-9]")) {
            numeroLetrasPosterioresLetraViaGeneradora = "";
        }
        if (!bisViaPrincipal.matches("(BIS)")) {
            bisViaPrincipal = "";
        }
        if (!bisViaGeneradora.matches("(BIS)")) {
            bisViaGeneradora = "";
        }
        placaDireccion = Integer.toString(Integer.parseInt(placaDireccion));
    }

    private void extraerDireccionBCM(String CodigoDireccionGeo) {
        bmcTipoNivel2 = CodigoDireccionGeo.substring(vBmcTipoNivel2[indiceInicial], vBmcTipoNivel2[LongitudATomar]);
        bmcValorNivel2 = CodigoDireccionGeo.substring(vBmcValorNivel2[indiceInicial], vBmcValorNivel2[LongitudATomar]);
        bmcTipoNivel3 = CodigoDireccionGeo.substring(vBmcTipoNivel3[indiceInicial], vBmcTipoNivel3[LongitudATomar]);
        bmcValorNivel3 = CodigoDireccionGeo.substring(vBmcValorNivel3[indiceInicial], vBmcValorNivel3[LongitudATomar]);
        bmcTipoNivel4 = CodigoDireccionGeo.substring(vBmcTipoNivel4[indiceInicial], vBmcTipoNivel4[LongitudATomar]);
        bmcValorNivel4 = CodigoDireccionGeo.substring(vBmcValorNivel4[indiceInicial], vBmcValorNivel4[LongitudATomar]);
        bmcTipoNivel5 = CodigoDireccionGeo.substring(vBmcTipoNivel5[indiceInicial], vBmcTipoNivel5[LongitudATomar]);
        bmcValorNivel5 = CodigoDireccionGeo.substring(vBmcValorNivel5[indiceInicial], vBmcValorNivel5[LongitudATomar]);
        bmcTipoNivel6 = CodigoDireccionGeo.substring(vBmcTipoNivel6[indiceInicial], vBmcTipoNivel6[LongitudATomar]);
        bmcValorNivel6 = CodigoDireccionGeo.substring(vBmcValorNivel6[indiceInicial], vBmcValorNivel6[LongitudATomar]);

        if (bmcTipoNivel2.equalsIgnoreCase("00")) {
            bmcTipoNivel2 = "";
        }
        if (bmcTipoNivel3.equalsIgnoreCase("00")) {
            bmcTipoNivel3 = "";
        }
        if (bmcTipoNivel4.equalsIgnoreCase("00")) {
            bmcTipoNivel4 = "";
        }
        if (bmcTipoNivel5.equalsIgnoreCase("00")) {
            bmcTipoNivel5 = "";
        }
        if (bmcTipoNivel6.equalsIgnoreCase("00")) {
            bmcTipoNivel6 = "";
        }

        if (bmcValorNivel2.equalsIgnoreCase("0000")) {
            bmcValorNivel2 = "";
        } else {
            bmcValorNivel2 = bmcValorNivel2.replaceFirst("0", "");
        }

        if (bmcValorNivel3.equalsIgnoreCase("0000")) {
            bmcValorNivel3 = "";
        } else {
            bmcValorNivel3 = bmcValorNivel2.replaceFirst("0", "");
        }

        if (bmcValorNivel4.equalsIgnoreCase("0000")) {
            bmcValorNivel4 = "";
        } else {
            bmcValorNivel4 = bmcValorNivel2.replaceFirst("0", "");
        }
        if (bmcValorNivel5.equalsIgnoreCase("0000")) {
            bmcValorNivel5 = "";
        } else {
            bmcValorNivel5 = bmcValorNivel2.replaceFirst("0", "");
        }

        if (bmcValorNivel6.equalsIgnoreCase("0000")) {
            bmcValorNivel6 = "";
        } else {
            bmcValorNivel6 = bmcValorNivel2.replaceFirst("0", "");
        }

    }

    private void remplazarValoresRR() throws ApplicationException {
        DireccionRRManager direccionRRManager = new DireccionRRManager(false);

        if (tipoViaPrincipal != null && !tipoViaPrincipal.isEmpty()) {
            tipoViaPrincipal = direccionRRManager.formatoRR(tipoViaPrincipal);
        }
        if (bisViaPrincipal != null && !bisViaPrincipal.isEmpty()) {
            bisViaPrincipal = direccionRRManager.formatoRR(bisViaPrincipal);
        }
        if (cuadranteViaPrincipal != null && !cuadranteViaPrincipal.isEmpty()) {
            cuadranteViaPrincipal = direccionRRManager.formatoRR(cuadranteViaPrincipal);
        }
        if (tipoViaGeneradora != null && !tipoViaGeneradora.isEmpty()) {
            tipoViaGeneradora = direccionRRManager.formatoRR(tipoViaGeneradora);
        }
        if (bisViaGeneradora != null && !bisViaGeneradora.isEmpty()) {
            bisViaGeneradora = direccionRRManager.formatoRR(bisViaGeneradora);
        }
        if (cuadranteViaGeneradora != null && !cuadranteViaGeneradora.isEmpty()) {
            cuadranteViaGeneradora = direccionRRManager.formatoRR(cuadranteViaGeneradora);
        }
        if (bmcTipoNivel2 != null && !bmcTipoNivel2.isEmpty()) {
            bmcTipoNivel2 = direccionRRManager.formatoRR(bmcTipoNivel2);
        }
        if (bmcTipoNivel3 != null && !bmcTipoNivel3.isEmpty()) {
            bmcTipoNivel3 = direccionRRManager.formatoRR(bmcTipoNivel3);
        }
        if (bmcTipoNivel4 != null && !bmcTipoNivel4.isEmpty()) {
            bmcTipoNivel4 = direccionRRManager.formatoRR(bmcTipoNivel4);
        }
        if (bmcTipoNivel5 != null && !bmcTipoNivel5.isEmpty()) {
            bmcTipoNivel5 = direccionRRManager.formatoRR(bmcTipoNivel5);
        }
        if (bmcTipoNivel6 != null && !bmcTipoNivel6.isEmpty()) {
            bmcTipoNivel6 = direccionRRManager.formatoRR(bmcTipoNivel6);
        }
    }

    void crearDireccionTipoBogota(String barrio, String complemento) {
        String cuadranteCompleto = generarCuadranteRR(cuadranteViaPrincipal, cuadranteViaGeneradora);
        String modificadoresViaPrincipal = "";
        if (letraNumeroViaPrincipal.matches("[0-9]+")) {
            modificadoresViaPrincipal = numeroViaPrincipal + "-" + letraNumeroViaPrincipal;
        } else {
            modificadoresViaPrincipal = numeroViaPrincipal + letraNumeroViaPrincipal;
        }
        String modificadoresViaGeneradora = "";
        if (letraNumeroViaGeneradora.matches("[0-9]+")) {
            modificadoresViaGeneradora = numeroViaGeneradora + "-" + letraNumeroViaGeneradora;
        } else {
            modificadoresViaGeneradora = numeroViaGeneradora + letraNumeroViaGeneradora;
        }
        resultadoDirRRCalle = tipoViaPrincipal + " " + modificadoresViaPrincipal + bisViaPrincipal + letraNumeroposteriorBisViaPrincipal + cuadranteCompleto;

        if (geograficoPolitico.getGpoMultiorigen().equalsIgnoreCase(Constant.TIPO_CIUDAD_MULTIORIGEN)) {
            resultadoDirRRCalle += " " + barrio.replace(" ", "");
        }
        String placa = (placaDireccion.length() < 1) ? ("00" + placaDireccion).substring(0, 1) : placaDireccion;

        resultadoDirRRNumeroUnidad = modificadoresViaGeneradora + bisViaGeneradora + letraNumeroPosteriorBisViaGeneradora + "-" + placa;
        if (complemento == null || complemento.isEmpty()) {
            complemento = "CASA";
        }
        resultadoDirRRNumeroApartamento = complemento.replace("INTERIOR", "IN").replace("APARTAMENTO ", "").replace("LOCAL", "LC").replace("OFICINA", "OF").replace("PISO", "PI");
        resultadoDirRRNumeroApartamento = applyFormatToApto(resultadoDirRRNumeroApartamento);
    }

    void crearDireccionTipoCali(String barrio, String complemento) {
        String cuadranteCompleto = generarCuadranteRR(cuadranteViaPrincipal, cuadranteViaGeneradora);
        String modificadoresViaPrincipal = "";

        if (letraNumeroViaPrincipal.matches("[0-9]+")) {
            modificadoresViaPrincipal = numeroViaPrincipal + "-" + letraNumeroViaPrincipal;
        } else {
            modificadoresViaPrincipal = numeroViaPrincipal + letraNumeroViaPrincipal;
        }
        if (letraNumeroViaPrincipal.matches("[0-9]+") && numerosLetrasPosterioresLetraViaPrincipal.matches("[0-9]+")) {
            modificadoresViaPrincipal += "-" + numerosLetrasPosterioresLetraViaPrincipal;
        } else {
            modificadoresViaPrincipal += numerosLetrasPosterioresLetraViaPrincipal;
        }
        if (geograficoPolitico.getGpoMultiorigen().equalsIgnoreCase(Constant.TIPO_CIUDAD_MULTIORIGEN)) {
            resultadoDirRRCalle = tipoViaPrincipal + " " + modificadoresViaPrincipal + bisViaPrincipal + letraNumeroposteriorBisViaPrincipal + cuadranteCompleto + " " + barrio.replace(" ", "");
        } else {
            resultadoDirRRCalle = tipoViaPrincipal + " " + modificadoresViaPrincipal + bisViaPrincipal + letraNumeroposteriorBisViaPrincipal + " " + cuadranteCompleto;
        }

        String modificadoresViaGeneradora = "";
        if (letraNumeroViaGeneradora.matches("[0-9]+")) {
            modificadoresViaGeneradora = numeroViaGeneradora + "-" + letraNumeroViaGeneradora;
        } else {
            modificadoresViaGeneradora = numeroViaGeneradora + letraNumeroViaGeneradora;
        }

        if (letraNumeroViaGeneradora.matches("[0-9]+") && numeroLetrasPosterioresLetraViaGeneradora.matches("[0-9]+")) {
            modificadoresViaGeneradora += "-" + numeroLetrasPosterioresLetraViaGeneradora;
        } else {
            modificadoresViaGeneradora += numeroLetrasPosterioresLetraViaGeneradora;
        }

        String placa = (placaDireccion.length() < 1) ? ("00" + placaDireccion).substring(0, 1) : placaDireccion;

        resultadoDirRRNumeroUnidad = modificadoresViaGeneradora + bisViaGeneradora + letraNumeroPosteriorBisViaGeneradora + "-" + placa;

        if (complemento == null || complemento.isEmpty()) {
            complemento = "CASA";
        }
        resultadoDirRRNumeroApartamento = complemento.trim().replace("INTERIOR", "IN").replace("APARTAMENTO ", "").replace("LOCAL", "LC").replace("OFICINA", "OF").replace("PISO", "PI");
        resultadoDirRRNumeroApartamento = applyFormatToApto(resultadoDirRRNumeroApartamento);
    }

    void crearDireccionTipoMedellin(String barrio, String complemento) {
        String cuadranteCompleto = generarCuadranteRR(cuadranteViaPrincipal, cuadranteViaGeneradora);

        String modificadoresViaPrincipal = "";
        if (letraNumeroViaPrincipal.matches("[0-9]+")) {
            modificadoresViaPrincipal = numeroViaPrincipal + "-" + letraNumeroViaPrincipal;
        } else {
            modificadoresViaPrincipal = numeroViaPrincipal + letraNumeroViaPrincipal;
        }

        if (letraNumeroViaPrincipal.matches("[0-9]+") && numerosLetrasPosterioresLetraViaPrincipal.matches("[0-9]+")) {
            modificadoresViaPrincipal += "-" + numerosLetrasPosterioresLetraViaPrincipal;
        } else {
            modificadoresViaPrincipal += numerosLetrasPosterioresLetraViaPrincipal;
        }
        
        if (geograficoPolitico.getGpoMultiorigen().equalsIgnoreCase(Constant.TIPO_CIUDAD_MULTIORIGEN)) {
            resultadoDirRRCalle = tipoViaPrincipal + " " + modificadoresViaPrincipal + bisViaPrincipal + cuadranteCompleto + " " + barrio.replace(" ", "");
        } else {
            resultadoDirRRCalle = tipoViaPrincipal + " " + modificadoresViaPrincipal + bisViaPrincipal + cuadranteCompleto + " " + cuadranteCompleto;
        }

        String modificadoresViaGeneradora = "";
        if (letraNumeroViaGeneradora.matches("[0-9]+")) {
            modificadoresViaGeneradora = numeroViaGeneradora + "-" + letraNumeroViaGeneradora;
        } else {
            modificadoresViaGeneradora = numeroViaGeneradora + letraNumeroViaGeneradora;
        }

        if (letraNumeroViaGeneradora.matches("[0-9]+") && numeroLetrasPosterioresLetraViaGeneradora.matches("[0-9]+")) {
            modificadoresViaGeneradora += "-" + numeroLetrasPosterioresLetraViaGeneradora;
        } else {
            modificadoresViaGeneradora += numeroLetrasPosterioresLetraViaGeneradora;
        }
        String placa = (placaDireccion.length() < 1) ? ("00" + placaDireccion).substring(0, 1) : placaDireccion;
        resultadoDirRRNumeroUnidad = modificadoresViaGeneradora + bisViaGeneradora + "-" + placa;
        if (complemento == null || complemento.isEmpty()) {
            complemento = "CASA";
        }
        resultadoDirRRNumeroApartamento = complemento.trim().replace("INTERIOR", "IN").replace("APARTAMENTO ", "").replace("LOCAL", "LC").replace("OFICINA", "OF").replace("PISO", "PI");
        resultadoDirRRNumeroApartamento = applyFormatToApto(resultadoDirRRNumeroApartamento);
    }

    void crearDireccionTipoBMC(String barrio, String complemento) {
        String barrioTraduciodoRR1 = "";
        String barrioTraduciodoRR2 = "";
        if (barrio.trim().contains(" ")) {
            barrioTraduciodoRR1 = barrio.trim().split(" ")[0];
            String[] barrioPartido;
            barrioPartido = barrio.trim().split(" ");
            for (int i = 1; i < barrioPartido.length; i++) {
                barrioTraduciodoRR2 += barrioPartido[1];
            }
        } else {
            barrioTraduciodoRR1 = "BARRIO";
            barrioTraduciodoRR2 = "";
        }
        resultadoDirRRCalle = barrioTraduciodoRR1 + " " + barrioTraduciodoRR2 + bmcTipoNivel2 + bmcValorNivel2 + " " + bmcTipoNivel3 + bmcValorNivel3;
        resultadoDirRRNumeroUnidad = bmcTipoNivel3 + bmcValorNivel3 + bmcTipoNivel4.replace("CASA", "C").replace("LOTE", "L").replace("PREDIO", "P") + bmcValorNivel4;
        if (complemento == null || complemento.isEmpty()) {
            complemento = "CASA";
        }
        resultadoDirRRNumeroApartamento = bmcTipoNivel4 + bmcValorNivel4 + " " + complemento.trim().replace("INTERIOR", "IN").replace("APARTAMENTO ", "").replace("LOCAL", "LC").replace("OFICINA", "OF").replace("PISO", "PI");
    }

    private String generarCuadranteRR(String cdViaPrincipal, String cdViaGeneradora) {
        String cuadranteFormaRR = "";
        if (cdViaPrincipal.equalsIgnoreCase(Constant.CUADRANTE_ESTE)) {
            cuadranteFormaRR = cdViaPrincipal + cdViaGeneradora;
        } else if (cdViaGeneradora.equalsIgnoreCase(Constant.CUADRANTE_ESTE)) {
            cuadranteFormaRR = cdViaGeneradora + cdViaPrincipal;
        } else if (cdViaPrincipal.equalsIgnoreCase(Constant.CUADRANTE_OESTE)) {
            cuadranteFormaRR = cdViaPrincipal + cdViaGeneradora;
        } else if (cdViaGeneradora.equalsIgnoreCase(Constant.CUADRANTE_OESTE)) {
            cuadranteFormaRR = cdViaGeneradora + cdViaPrincipal;
        } else {
            cuadranteFormaRR = cdViaPrincipal + cdViaGeneradora;
        }
        return cuadranteFormaRR;
    }
    
    private String applyFormatToApto(String aptoNumber){
        String aptoResult = aptoNumber.trim();
        String formatoMulApto = "^[aA-zZ]+[0-9]+\\s+\\w+";
        String formatoLc =  "^[aA-zZ]+[0-9]+\\s+[aA-zZ]+\\s+[0-9]+";
        Pattern patApto = Pattern.compile(formatoMulApto);
        Pattern patLc = Pattern.compile(formatoLc);
        
        Matcher mat = patApto.matcher(aptoResult);
        if (mat.matches()){
            aptoResult = aptoResult.replace(" ", "-");
        }else if (patLc.matcher(aptoResult).matches()){
            int pos = aptoResult.indexOf(" ");
            aptoResult = aptoResult.substring(0, pos) 
                                + "-" 
                                + aptoResult.substring(pos + 1);
            aptoResult = aptoResult.replace(" ", "");
        }
        return aptoResult;
        
    }

    public String getTipoDireccion() {
        return tipoDireccion;
    }    
    
     /**
     * @return drDirec
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public DrDireccion getDireccionDrDirecion() throws ApplicationException {
        DrDireccion drDirec = new DrDireccion();        
                drDirec.setIdTipoDireccion(tipoDireccion);
        if (drDirec.getIdTipoDireccion().equals("CK")) {
            //Direccion Calle - Carrera
            drDirec.setTipoViaPrincipal(tipoViaPrincipal);
            drDirec.setNumViaPrincipal(numeroViaPrincipal);
            drDirec.setLtViaPrincipal(letraNumeroViaPrincipal);
            drDirec.setNlPostViaP(numerosLetrasPosterioresLetraViaPrincipal);
            drDirec.setBisViaPrincipal(bisViaPrincipal);
            drDirec.setCuadViaPrincipal(cuadranteViaPrincipal);
            drDirec.setPlacaDireccion(placaDireccion);
            //Direccion Generadora de Calle - carrera
            drDirec.setTipoViaGeneradora(tipoViaGeneradora);
            drDirec.setNumViaGeneradora(numeroViaGeneradora);
            drDirec.setLtViaGeneradora(letraNumeroViaGeneradora);
            drDirec.setNlPostViaG(numeroLetrasPosterioresLetraViaGeneradora);
            drDirec.setBisViaGeneradora(bisViaGeneradora);
            drDirec.setCuadViaGeneradora(cuadranteViaGeneradora);
        }
        if (drDirec.getIdTipoDireccion().equals("BM")) {

        
        
        
        return drDirec;
    }    
        return null;}
}