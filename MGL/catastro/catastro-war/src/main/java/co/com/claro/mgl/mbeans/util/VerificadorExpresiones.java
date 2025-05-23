/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.util;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.PreFichaTxtMgl;
import co.com.claro.mgl.jpa.entities.PreFichaXlsMgl;
import co.com.claro.mgl.jpa.entities.PreFichaXlsMglNew;
import co.com.claro.mgl.mbeans.direcciones.PrefichaBean;
import co.com.claro.mgl.utils.Constant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author castrofo
 */
@ManagedBean
@SessionScoped
public class VerificadorExpresiones {

    private List<String> listadoHHPP = new ArrayList<>();
    private static final int MAXIMO_HOME_PASS_PISO = 2;
    private static final String PISO = "PISO";
    private static final String APARTAMENTO = "APARTAMENTO";
    private static final String LOCAL = "LOCAL";

    @EJB
    private ParametrosMglFacadeLocal parametrosMglManager;
    private static final Logger LOGGER = LogManager.getLogger(VerificadorExpresiones.class);

    @PostConstruct
    public void init() {
    }

    public boolean validaBarrio(String barrio) {
        return barrio.matches("([A-Z0-9]\\s?)*");
    }

    /**
     *
     * Metodo para determinar el tipo de direccion a partir de la placa retorna
     * false si la estructura de la placa no es valoda
     *
     * @param valorPlaca
     * @return
     */
    public String validarDireccionPlaca(String valorPlaca) {
        ParametrosMgl parametroBM = null;
        ParametrosMgl parametroIT = null;
        ParametrosMgl parametroCK = null;
        String regExpression;
        Pattern patter;
        if (valorPlaca.contains("/")) {
            return "";
        }
        if (valorPlaca.contains("SC") || valorPlaca.contains("ST") || valorPlaca.contains("SECTOR")) {
            String s = valorPlaca.substring(0, valorPlaca.indexOf("-"));
            valorPlaca = valorPlaca.replace(s, "0");
        }
        try {
            parametroBM = parametrosMglManager.findByAcronimoName(Constant.EXP_REG_VALIDA_PLACA_BM_FICHAS_NODO);
            parametroIT = parametrosMglManager.findByAcronimoName(Constant.EXP_REG_VALIDA_PLACA_IT_FICHAS_NODO);
            parametroCK = parametrosMglManager.findByAcronimoName(Constant.EXP_REG_VALIDA_PLACA_CK_FICHAS_NODO);
        } catch (ApplicationException e) {
            String msgError = "Ocurrió un error al momento de validar la dirección de la placa. " + e.getMessage();
            LOGGER.error(msgError, e);
        } catch (Exception e) {
            String msgError = "Error al momento de validar la dirección de la placa. " + e.getMessage();
            LOGGER.error(msgError, e);
        }

        if (parametroBM != null && parametroIT != null && parametroCK != null) {
            regExpression = parametroBM.getParValor();

            patter = Pattern.compile(regExpression);

            if (patter.matcher(valorPlaca).matches()) {
                return valorPlaca.replace(" ", "").trim().length() > 10 ? "" : Constant.ACRONIMO_BM;
            } else {
                regExpression = parametroIT.getParValor();
                patter = Pattern.compile(regExpression);

                if (patter.matcher(valorPlaca).matches()) {
                    return valorPlaca.trim().length() > 10 ? "" : Constant.ACRONIMO_IT;
                } else {
                    regExpression = parametroCK.getParValor();
                    patter = Pattern.compile(regExpression);

                    if (patter.matcher(valorPlaca).matches()) {
                        return valorPlaca.trim().length() > 10 ? "" : Constant.ACRONIMO_CK;
                    }
                }
            }
        }

        return "";
    }

    /**
     *
     * Metodo para determinar el tipo de direccion a partir de la placa retorna
     * false si la estructura de la placa no es valoda
     *
     * @param valorPlaca
     * @return
     * @throws ApplicationException
     */
    public String validarDireccionNombreCalle(String valorPlaca) {
        ParametrosMgl parametroDirIT = null;
        ParametrosMgl parametroDirCK = null;
        ParametrosMgl parametroDirBM = null;
        if (valorPlaca.trim().contains("/") || valorPlaca.trim().replace(" ", "").length() > 50) {
            return "";
        }
        String regExpression;
        Pattern patter;
        try {
            parametroDirIT = parametrosMglManager.findByAcronimoName(Constant.EXP_REG_VALIDA_DIR_IT_FICHAS_NODO);
            parametroDirCK = parametrosMglManager.findByAcronimoName(Constant.EXP_REG_VALIDA_DIR_CK_FICHAS_NODO);
            parametroDirBM = parametrosMglManager.findByAcronimoName(Constant.EXP_REG_VALIDA_DIR_BM_FICHAS_NODO);
        } catch (ApplicationException e) {
            String msgError = "Ocurrió un error al momento de validar la dirección nombre de la calle. " + e.getMessage();
            LOGGER.error(msgError, e);
        } catch (Exception e) {
            String msgError = "Error al momento de validar la dirección nombre de la calle. " + e.getMessage();
            LOGGER.error(msgError, e);
        }
        if (parametroDirIT != null && parametroDirBM != null && parametroDirCK != null) {
            regExpression = parametroDirIT.getParValor();

            patter = Pattern.compile(regExpression);

            if (patter.matcher(valorPlaca).matches()) {
                return Constant.ACRONIMO_IT;
            } else {
                regExpression = parametroDirBM.getParValor();
                patter = Pattern.compile(regExpression);
                if (!valorPlaca.equals("")) {
                    String br = valorPlaca.trim();
                    if (patter.matcher(br).matches()) {
                        return Constant.ACRONIMO_BM;
                    } else {
                        regExpression = parametroDirCK.getParValor();
                        patter = Pattern.compile(regExpression);
                        String[] partsCalle = valorPlaca.trim().split("\\s");
                        boolean isNumber = false;
                        if (partsCalle.length > 1) {
                            isNumber = isNumeric(partsCalle[1]);
                        }

                        if (partsCalle[0].equalsIgnoreCase("DG")) {
                            if (partsCalle[0].equalsIgnoreCase("DG") && isNumber) {
                                if (patter.matcher(valorPlaca.trim()).matches()) {
                                    return Constant.ACRONIMO_CK;
                                }
                            }
                        } else {
                            if (patter.matcher(valorPlaca.trim()).matches()) {
                                return Constant.ACRONIMO_CK;
                            }
                        }

                    }

                }
            }
        } else {
            LOGGER.info("Error en al menos uno de los parametros de expresiones regulares");
        }
        return "";
    }

    /**
     *
     * Metodo para determinar el tipo de direccion a partir de la placa y del
     * Nombre Calle
     *
     * @param preFichaTxtMgl
     */
    public void validarTipoDireccion(PreFichaTxtMgl preFichaTxtMgl) {

        String tipoPlaca = validarDireccionPlaca(preFichaTxtMgl);
        String tipoVia = validarDireccionNombreCalle(preFichaTxtMgl);

        if (!tipoVia.isEmpty() && tipoVia.equals(tipoPlaca)) {
            preFichaTxtMgl.setRegistroValido(1);
            preFichaTxtMgl.setTipoDireccion(tipoVia);
            return;
        }

        if (tipoVia.equals(Constant.ACRONIMO_IT) && (tipoPlaca.equals(Constant.ACRONIMO_CK)
                || (tipoPlaca.equals(Constant.ACRONIMO_BM) && validaPlacaIT(preFichaTxtMgl.getPlacaUnida().replace(" ", ""))))) {
            preFichaTxtMgl.setRegistroValido(1);
            preFichaTxtMgl.setTipoDireccion(Constant.ACRONIMO_IT);
            return;
        }
        //validacion para procesar direccion cuya placa este marcada como CD
        if(tipoPlaca.equals(Constant.ACRONIMO_IT) && (tipoVia.equals(Constant.ACRONIMO_CK)
                || (tipoVia.equals(Constant.ACRONIMO_BM)))
                && validaPlacaCD(preFichaTxtMgl.getPlacaUnida().replace(" ", ""))){
            preFichaTxtMgl.setRegistroValido(1);
            preFichaTxtMgl.setTipoDireccion(tipoVia);
            return;
        }

        preFichaTxtMgl.setRegistroValido(0);
        preFichaTxtMgl.setTipoDireccion("");

    }

    public String validarDireccionPlaca(PreFichaTxtMgl preFichaTxtMgl) {
        ParametrosMgl parametroBM = null;
        ParametrosMgl parametroIT = null;
        ParametrosMgl parametroCK = null;

        boolean isCCMM = (preFichaTxtMgl.getPisos().intValue() > 3
                && preFichaTxtMgl.getBlockName().toUpperCase().equals("N_Casa".toUpperCase()))
                || preFichaTxtMgl.getBlockName().toUpperCase().equals("N_Edificio".toUpperCase());

        if (preFichaTxtMgl.getPlacaUnida().trim().contains("/") && !isCCMM) {
            preFichaTxtMgl.setRegistroValido(0);
            return "";
        }
        String regExpression;
        Pattern patter;
        try {
            parametroBM = parametrosMglManager.findByAcronimoName(Constant.EXP_REG_VALIDA_PLACA_BM_FICHAS_NODO);
            parametroIT = parametrosMglManager.findByAcronimoName(Constant.EXP_REG_VALIDA_PLACA_IT_FICHAS_NODO);
            parametroCK = parametrosMglManager.findByAcronimoName(Constant.EXP_REG_VALIDA_PLACA_CK_FICHAS_NODO);
        } catch (ApplicationException e) {
            String msgError = "Ocurrió un error al momento de validar la dirección de la placa. " + e.getMessage();
            LOGGER.error(msgError, e);
        } catch (Exception e) {
            String msgError = "Error al momento de validar la dirección de la placa. " + e.getMessage();
            LOGGER.error(msgError, e);
        }

        if (parametroBM != null && parametroIT != null && parametroCK != null) {
            regExpression = parametroBM.getParValor();

            patter = Pattern.compile(regExpression);

            String placaAValidar = !preFichaTxtMgl.getPlacaUnida().trim().contains("/")
                    ? preFichaTxtMgl.getPlacaUnida().trim()
                    : preFichaTxtMgl.getPlacaUnida().trim().substring(0, preFichaTxtMgl.getPlacaUnida().trim().indexOf("/"));

            if (patter.matcher(placaAValidar).matches()) {
                return !validaPlacaIT(placaAValidar) ? "" : Constant.ACRONIMO_BM;
            } else {
                regExpression = parametroCK.getParValor();
                patter = Pattern.compile(regExpression);

                if (patter.matcher(placaAValidar).matches()) {
                    return placaAValidar.length() > 10 ? "" : Constant.ACRONIMO_CK;
                } else {
                    regExpression = parametroIT.getParValor();
                    patter = Pattern.compile(regExpression);

                    if (patter.matcher(placaAValidar).matches()) {
                        return !validaPlacaIT(placaAValidar) ? "" : Constant.ACRONIMO_IT;
                    }
                }
            }
        }
        preFichaTxtMgl.setRegistroValido(0);
        return "";
    }

    public String validarDireccionNombreCalle(PreFichaTxtMgl preFichaTxtMgl) {

        ParametrosMgl parametroDirIT = null;
        ParametrosMgl parametroDirCK = null;
        ParametrosMgl parametroDirBM = null;

        if (preFichaTxtMgl.getNombreCall().trim().contains("/") || preFichaTxtMgl.getNombreCall().trim().replace(" ", "").length() > 50) {
            preFichaTxtMgl.setRegistroValido(0);
            return "";
        }

        String regExpression;
        Pattern patter;
        try {
            parametroDirIT = parametrosMglManager.findByAcronimoName(Constant.EXP_REG_VALIDA_DIR_IT_FICHAS_NODO);
            parametroDirCK = parametrosMglManager.findByAcronimoName(Constant.EXP_REG_VALIDA_DIR_CK_FICHAS_NODO);
            parametroDirBM = parametrosMglManager.findByAcronimoName(Constant.EXP_REG_VALIDA_DIR_BM_FICHAS_NODO);
        } catch (ApplicationException e) {
            String msgError = "Ocurrió un error al momento de validar el nombre de la calle. " + e.getMessage();
            LOGGER.error(msgError, e);
        } catch (Exception e) {
            String msgError = "Error al momento de validar el nombre de la calle. " + e.getMessage();
            LOGGER.error(msgError, e);
        }
        if (parametroDirIT != null && parametroDirCK != null && parametroDirBM != null) {
            regExpression = parametroDirIT.getParValor();

            patter = Pattern.compile(regExpression);

            if (patter.matcher(preFichaTxtMgl.getNombreCall().trim()).matches()) {
                if (preFichaTxtMgl.getNombreCall().trim().contains("VIA")
                        && preFichaTxtMgl.getNombreCall().trim().contains("VEREDA")
                        || ((preFichaTxtMgl.getNombreCall().trim().contains("VD")
                        || (preFichaTxtMgl.getNombreCall().trim().contains("VEREDA")))
                        && preFichaTxtMgl.getNombreCall().trim().contains("FINCA"))) {
                    preFichaTxtMgl.setRegistroValido(0);
                } else {
                    return Constant.ACRONIMO_IT;
                }

            } else {
                regExpression = parametroDirBM.getParValor();
                patter = Pattern.compile(regExpression);
                if (patter.matcher(preFichaTxtMgl.getNombreCall().trim()).matches()) {
                    return Constant.ACRONIMO_BM;
                } else {
                    regExpression = parametroDirCK.getParValor();
                    patter = Pattern.compile(regExpression);
                    String[] partsCalle = preFichaTxtMgl.getNombreCall().trim().split("\\s");
                    boolean isNumber = false;
                    if (partsCalle.length > 1) {
                        isNumber = isNumeric(partsCalle[1]);
                    }
                    if (partsCalle[0].equalsIgnoreCase("DG")) {
                        if (partsCalle[0].equalsIgnoreCase("DG") && isNumber) {
                            if (patter.matcher(preFichaTxtMgl.getNombreCall().trim()).matches()) {
                                return Constant.ACRONIMO_CK;
                            }
                        }
                    } else {
                        if (patter.matcher(preFichaTxtMgl.getNombreCall().trim()).matches()) {
                            return Constant.ACRONIMO_CK;
                        }
                    }
                }
            }
            preFichaTxtMgl.setRegistroValido(0);
        }

        return "";
    }

    public boolean isValidDistribucion(String distribucion) {
        String regExpression = "^((AP|LC|IN|BL)\\s((?!-)(-?\\w+)+)(\\s?))+$";
        Pattern patter;
        patter = Pattern.compile(regExpression);
        return patter.matcher(distribucion.toUpperCase()).matches();
    }

    public boolean isValidCountDistribucion(String distribucionToValidate, PreFichaXlsMgl preFicha) {
        if (distribucionToValidate == null) {
            return false;
        }
        boolean resutl = true;
        String regExpression = "((AP|LC|IN|BL)\\s((?!\\-)(\\-?\\w+)+)( ?))";
        Pattern patter;
        patter = Pattern.compile(regExpression);
        if (patter.matcher(distribucionToValidate).find()) {
            List<String> gruposStr = new ArrayList<>();
            Matcher matcher = patter.matcher(distribucionToValidate);
            int i = 0;
            while (matcher.find()) {
                String grupoStr = matcher.group();
                gruposStr.add(grupoStr);
            }

            if (gruposStr.size() > 0) {

                int cantDistLocales = 0;
                int cantDistAptos = 0;
                String observacionesValidacionNumHhpp = " ";
                for (String s : gruposStr) {

                    String tipo = s.trim().split("\\s")[0];
                    String[] dist = s.trim().split("\\s")[1].split("-");
                    PrefichaBean.DIST_TYPE type = PrefichaBean.DIST_TYPE.valueOf(tipo);
                    switch (type) {
                        case LC:
                            cantDistLocales = dist.length;
                            break;
                        case AP:
                            cantDistAptos = dist.length;
                            break;
                    }
                }
                if (cantDistLocales != preFicha.getLocales().intValue()) {

                    observacionesValidacionNumHhpp += "El numero de Locales no concuerda con la distribucion - ";
                    resutl = false;
                }
                if (cantDistAptos != preFicha.getAptos().intValue()) {
                    observacionesValidacionNumHhpp += "El numero de Apto no concuerda con la distribucion - ";
                    resutl = false;
                }
                if (!observacionesValidacionNumHhpp.trim().isEmpty()) {
                    observacionesValidacionNumHhpp += (preFicha.getObservaciones() == null ? "" : preFicha.getObservaciones());
                    preFicha.setObservaciones(observacionesValidacionNumHhpp);
                }
            }
        } else {
            return false;
        }

        return resutl;
    }

    // Metodo para nueva implementacion, modificar o elimnar despues
    public boolean isValidCountDistribucionNew(String distribucionToValidate, PreFichaXlsMglNew preFicha) {
        if (distribucionToValidate == null) {
            return false;
        }
        boolean resutl = true;
        String regExpression = "((AP|LC|IN|BL)\\s((?!\\-)(\\-?\\w+)+)( ?))";
        Pattern patter;
        patter = Pattern.compile(regExpression);
        if (patter.matcher(distribucionToValidate).find()) {
            List<String> gruposStr = new ArrayList<>();
            Matcher matcher = patter.matcher(distribucionToValidate);
            int i = 0;
            while (matcher.find()) {
                String grupoStr = matcher.group();
                gruposStr.add(grupoStr);
            }

            if (gruposStr.size() > 0) {

                int cantDistLocales = 0;
                int cantDistAptos = 0;
                String observacionesValidacionNumHhpp = " ";
                for (String s : gruposStr) {

                    String tipo = s.trim().split("\\s")[0];
                    String[] dist = s.trim().split("\\s")[1].split("-");
                    PrefichaBean.DIST_TYPE type = PrefichaBean.DIST_TYPE.valueOf(tipo);
                    switch (type) {
                        case LC:
                            cantDistLocales = dist.length;
                            break;
                        case AP:
                            cantDistAptos = dist.length;
                            break;
                    }
                }
                if (cantDistLocales != preFicha.getLocales().intValue()) {

                    observacionesValidacionNumHhpp += "El numero de Locales no concuerda con la distribucion - ";
                    resutl = false;
                }
                if (cantDistAptos != preFicha.getAptos().intValue()) {
                    observacionesValidacionNumHhpp += "El numero de Apto no concuerda con la distribucion - ";
                    resutl = false;
                }
                if (!observacionesValidacionNumHhpp.trim().isEmpty()) {
                    observacionesValidacionNumHhpp += (preFicha.getObservaciones() == null ? "" : preFicha.getObservaciones());
                    preFicha.setObservaciones(observacionesValidacionNumHhpp);
                }
            }
        } else {
            return false;
        }

        return resutl;
    }

    public List<String> calcularDistribucionCasasRedExterna(int totalHHPP, int aptos, int locales, int pisos) {
        listadoHHPP = new ArrayList<>();
        for (int i = 1; i <= pisos; i++) {
            listadoHHPP.add(new String());
        }
        if (totalHHPP == 1 && aptos == 1 && locales == 0) {
            //REGLA 1
            listadoHHPP.set(0, "CASA");
        } else if (aptos == 0 && locales > 0) {
            //REGLA 2
            enviarLocalesPiso1(locales);
        } else if (aptos > 1 && locales == 0) {
            //REGLA 3
            if (pisos == 1) {
                enviarAptosPiso1(aptos);
            } else if (pisos > 1) {
                if (aptos <= pisos) {
                    enviarAptoaCadaPiso(1, aptos);
                } else if (aptos > pisos) {
                    distribuirAptosEnPisos(aptos, pisos);
                }
            }
        } else if (aptos > 0 && locales > 0) {
            //REGLA 4
            if (pisos == 1) {
                enviarLocalesyAptosPiso1(totalHHPP, aptos, locales);
            } else if (pisos > 1) {
                enviarLocalesPiso1(locales);
                if (excedioMaximoHHPPPiso(MAXIMO_HOME_PASS_PISO, 1) == true) {
                    if (aptos < pisos) {
                        enviarAptoaCadaPiso(2, aptos);
                    } else if (aptos >= pisos) {
                        distribuirAptosEnPisos(aptos, pisos);
                    }
                } else {
                    if (aptos < pisos) {
                        enviarAptoaCadaPiso(2, aptos);
                    } else if (aptos >= pisos) {
                        distribuirAptosEnPisos(aptos, pisos);
                    }
                }

            }
        }

        List<String> complementosHHPP = new ArrayList<>();
        for (String hhpps : listadoHHPP) {
            if (hhpps != null && !hhpps.isEmpty()) {
                String[] hhppPorPiso = hhpps.split("\\;");
                for (int i = 0; i < hhppPorPiso.length; i++) {
                    complementosHHPP.add(hhppPorPiso[i]);
                }
            }
        }

        return complementosHHPP;
    }

    public List<String> calcularDistribucionEdificiosVt(int numAptos, int numLocales) {
        List<String> listadoComplementos = new ArrayList<>();
        String distribucion = "";
        int locales = numLocales;
        if (numLocales > 0) {
            int numeroLocalesRepartidos = 1;
            while (numLocales > 0) {
                distribucion = distribucion + "LOCAL-" + (100 + numeroLocalesRepartidos) + ";";
                numeroLocalesRepartidos++;
                numLocales--;
            }
        }
        if (numAptos > 0) {
            int piso = locales > 0 ? 2 : 1;
            while (numAptos > 0) {
                distribucion = distribucion + "APARTAMENTO-" + ((piso * 100) + 1) + ";";
                piso++;
                numAptos--;
            }
        }

        String[] complementos = distribucion.split("\\;");
        for (int i = 0; i < complementos.length; i++) {
            listadoComplementos.add(complementos[i]);
        }
        return listadoComplementos;
    }

    public int ubicarPisoConCupo(int maximoHHPPPiso) {
        int piso = 0;
        for (int i = 0; i <= listadoHHPP.size() - 1; i++) {
            if (listadoHHPP.get(i).split("\\;").length < maximoHHPPPiso) {
                piso = i + 1;
                break;
            }
        }
        return piso;
    }

    public void formatearHHPPporPiso() {
        for (int i = 0; i <= listadoHHPP.size() - 1; i++) {
            if (listadoHHPP.get(i).split("\\;").length == 1) {
                listadoHHPP.remove(i);
                listadoHHPP.add(i, "PISO " + (i + 1));
            }
        }
    }

    public boolean excedioMaximoHHPPPiso(int maximoHHPP, int piso) {
        return listadoHHPP.get(piso - 1).split("\\;").length >= maximoHHPP;
    }

    public void enviarLocalesPiso1(int locales) {
        String distribucionLocales = "";
        for (int i = 1; i <= locales; i++) {
            distribucionLocales = distribucionLocales + "PISO-1 LOCAL-" + (100 + i) + ";";
        }
        listadoHHPP.set(0, distribucionLocales);
    }

    public void enviarAptosPiso1(int aptos) {
        String distribucionApartamento = "";
        for (int i = 1; i <= aptos; i++) {
            distribucionApartamento = distribucionApartamento + "PISO-1 APARTAMENTO-" + (100 + i) + ";";
        }
        listadoHHPP.set(0, distribucionApartamento);
    }

    public void enviarAptoaCadaPiso(int desdeQuepiso, int aptos) {
        int piso = desdeQuepiso;
        while (aptos > 0) {
            String aptoPiso = "PISO " + piso;
            listadoHHPP.set(piso - 1, aptoPiso);
            aptos--;
            piso++;
        }
    }

    public void distribuirAptosEnPisos(int aptos, int pisos) {
        while (aptos > 0) {
            int pisoInicio = ubicarPisoConCupo(MAXIMO_HOME_PASS_PISO);
            for (int i = pisoInicio; i <= pisos; i++) {
                String hhppsPisoActual = listadoHHPP.get(i - 1);
                int cantidadHHPPPisoActual = 0;
                if (!hhppsPisoActual.isEmpty()) {
                    cantidadHHPPPisoActual = hhppsPisoActual.split("\\;").length;
                }
                hhppsPisoActual = hhppsPisoActual + "PISO-" + i + " APARTAMENTO-" + ((100 * i) + cantidadHHPPPisoActual + 1) + ";";
                listadoHHPP.set(i - 1, hhppsPisoActual);
                aptos--;
                if (aptos == 0) {
                    break;
                }
            }
        }
        formatearHHPPporPiso();
    }

    public void enviarLocalesyAptosPiso1(int totalHHPP, int aptos, int locales) {
        int contadorHHPP = 1;
        String distribucion = "";
        while (totalHHPP > 0) {
            for (int i = 1; i <= locales; i++) {
                distribucion = distribucion + "PISO-1 LOCAL-" + (100 + contadorHHPP) + ";";
                totalHHPP--;
                contadorHHPP++;
            }
            for (int i = 1; i <= aptos; i++) {
                distribucion = distribucion + "PISO-1 APARTAMENTO-" + (100 + contadorHHPP) + ";";
                totalHHPP--;
                contadorHHPP++;
            }
        }
        listadoHHPP.set(0, distribucion);
    }

    public List<String> getListadoHHPP() {
        return listadoHHPP;
    }

    public void setListadoHHPP(List<String> listadoHHPP) {
        this.listadoHHPP = listadoHHPP;
    }

    private static boolean esDistribucionValida(String regex, String dist) {
        return dist.matches(regex);
    }

    /**
     * Metodo para calcular distribuciones cuando se tiene una unidad ocupando
     * un piso
     *
     * @author Miguel Barrios
     * @param pisoUso Piso en uso
     * @param locales Numero de locales
     * @param aptos Numero de apartementos
     * @param pisos Numero de pisos
     * @param unidadUso Numero de unidad que se encuentra ocupada
     * @return Listado con distribuciones
     */
    public List<String> calcularDistActualizaCasa(int pisoUso, int locales, int aptos, int pisos, int unidadUso) {
        boolean flag = aptos > 0 && locales > 0;
        List<String> lista = new ArrayList<>();
        int pisoM = 1;
        int uC;
        boolean pU;
        for (int i = 1; i <= pisos; i++) {
            if (locales <= 0) break;
            uC = calcularUnidadesCrear(locales, pisoM, pisos, pisoUso, unidadUso);
            pU = pisoContieneUnidad(LOCAL, uC, pisoM, pisos, pisoUso, unidadUso, flag);

            if (debeAumentarPiso(pisos, uC, pU, pisoUso, pisoM)) pisoM++;

            lista.addAll(enviarUnidadesAPiso(LOCAL, flag, pU, pisoM, uC, unidadUso, pisoUso, pisos));
            locales = locales - uC;
            if (debeAumentarPiso(pisos, locales, pU, pisoUso, pisoM)) pisoM++;
        }

        for (int i = 1; i <= pisos; i++) {
            if (aptos <= 0) break;
            uC = calcularUnidadesCrear(aptos, pisoM, pisos, pisoUso, unidadUso);
            pU = pisoContieneUnidad(APARTAMENTO, uC, pisoM, pisos, pisoUso, unidadUso, flag);

            if (debeAumentarPiso(pisos, uC, pU, pisoUso, pisoM)) pisoM++;

            lista.addAll(enviarUnidadesAPiso(APARTAMENTO, flag, pU, pisoM, uC, unidadUso, pisoUso, pisos));
            aptos = aptos - uC;
            if (debeAumentarPiso(pisos, aptos, pU, pisoUso, pisoM)) pisoM++;
        }
        return lista;
    }
    
    /**
     * Metodo para almacenar el numero de unidades indicadas en el piso
     *
     * @author Miguel Barrios
     * @param verb Tipo de unidad APARTAMENTO-LOCAL
     * @param uC Numero de unidades a crear
     * @param pisoM Piso del proceso en ejecucion
     * @param pisos Pisos de la ficha
     * @param pisoU Piso que esta ocupado
     * @param uniUso Unidad que esta ocupada
     * @param flag Flag de validacion
     * @return Inidica si el piso contiene unidad
    */ 
    private boolean pisoContieneUnidad(String verb,int uC, int pisoM, int pisos, int pisoU, int uniUso, boolean flag) {
        boolean pU = false;
        boolean esPisoUso = pisoU == pisoM;
        
        if (esPisoUso && uC == 1 && uniUso != 0 && !flag) {
            pU = true;
        } else if (!esPisoUso && uC == 1) {
            pU = pisoM <= pisos && (verb.equals(APARTAMENTO));
        } else if (!esPisoUso && uC == 2) {
            pU = true;
        }
        return pU;
    }
    
    /**
     * Metodo para calcular el numero de unidades a crear
     *
     * @author Miguel Barrios        

     * @param unidades Numero de unidades a crear
     * @param pisoM Piso del proceso en ejecucion
     * @param pisos Numero de pisos     
     * @param pisoU Piso que esta ocupado
     * @param uniUso Unidad que esta ocupada     
     * @return Numero de unidades a crear
    */ 
    private int calcularUnidadesCrear(int unidades, int pisoM, int pisos, int pisoU, int uniUso) {
        int uC;
        boolean esPisoUso = pisoU == pisoM;
        if (esPisoUso && unidades == 1) {
            uC = 1;
        } else if (!esPisoUso && unidades == 1) {
            uC = 1;
        } else if (!esPisoUso && unidades == 2 && pisoM < pisos  && uniUso == 0) {
            uC = 2;
        } else {
            uC = unidades - 2 >= 0 ? 2 : 1;
        }
        return uC;
    }
    
    /**
     * Metodo para almacenar el numero de unidades indicadas en el piso
     *
     * @author Miguel Barrios
     * @param verb Tipo de unidad APARTAMENTO-LOCAL               
     * @param flag Flag de validacion
     * @param pisoContUnidad Inidica si el piso contiene una unidad
     * @param args Argumentos del metodo
     * @return Listado con distribuciones
    */ 
    private static List<String> enviarUnidadesAPiso(String verb,boolean flag, boolean pisoContUnidad, int... args) {
        int piso = args[0];
        int u = args[1];
        int uniUso = args[2];
        int pisoU = args[3];
        int pisos = args[4];
        
        List<String> lista = new ArrayList<>();
        int vUnidad = 1;
        for (int i = 1; i <= u; i++) {
            if (u == 1 && pisoContUnidad && pisos == 1) vUnidad++;                        
            if (pisoContUnidad && flag) vUnidad++;            
            if (uniUso == vUnidad && piso == pisoU) {
                vUnidad = i;
            }
            
            lista.add(PISO + "-" + piso + " " + verb + "-" + piso + "0" + vUnidad);
            if (u == 2) vUnidad++;
        }
        return lista;
    }
    
    /**
     * Metodo para decidir si se debe aumentar el piso o no
     *
     * @author Miguel Barrios       
     * @param pisos Numero de pisos
     * @param unidadesCreadas Numero de unidades a crear
     * @param pisoContUnidad Inidica si en el piso a crear la unidades ya se encuentra una unidad     
     * @param pisoU Piso que esta ocupado
     * @param pisoProcesado Piso de ejecucion del proceso     
     * @return Se debe aumentar el piso
    */ 
    private boolean debeAumentarPiso(int pisos,int unidadesCreadas, boolean pisoConUnidad, int pisoU, int pisoProcesado) {
        boolean resultado = false;
   
        boolean unicoPiso = pisos == 1; 
        boolean esPisoUso = pisoProcesado == pisoU;
        
        if ((unidadesCreadas == 1 && pisoU == pisoProcesado && !unicoPiso) || 
            (unidadesCreadas == 1 && pisoConUnidad && pisos > 1 && esPisoUso) || 
            (unidadesCreadas == 2 && esPisoUso)||
            (unidadesCreadas == 1 && esPisoUso && pisoProcesado < pisos)){
            resultado = true;           
        }
        return resultado;
    }

    /**
     * Metodo para validar si el tipo de complemento seleccionado es acorde al
     * valor ingresado
     *
     * @author Miguel Barrios
     * @param cp Valor de tipo complemento
     * @param distribucion Valor ingresado para el tipo de complemento
     * @return Si es valido
     */
    public static boolean esDistribucionValidaUnidad(String cp, String distribucion) {
        String rgxPi = "^[1-3]$";
        String rgxPiApto = "^(PI)[123]-(\\d{3})$";
        String rgxApto = "^[1-3]\\d{2}$";
        String rgxPiInt = "^(PI)[123]-(\\d{1,4})$";
        String rgxInt = "^\\d{1,4}$";

        boolean valido;

        switch (cp) {
            case PISO:
                valido = esDistribucionValida(rgxPi, distribucion);
                break;
            case APARTAMENTO:
                valido = esDistribucionValida(rgxApto, distribucion);
                break;
            case "PISO + APTO":
                valido = esDistribucionValida(rgxPiApto, distribucion);
                break;
            case "INTERIOR":
                valido = esDistribucionValida(rgxInt, distribucion);
                break;
            case "PISO + INTERIOR":
                valido = esDistribucionValida(rgxPiInt, distribucion);
                break;
            default:
                valido = false;
                break;
        }

        return valido;
    }

    public static boolean isDistribucionEsValida(int pisosFicha, String distribucion) {

        boolean distribucionValida = true;

        String fragmento = "";
        if (distribucion.contains("IN")) {
            int interiores = distribucion.indexOf("IN");
            int locales = distribucion.indexOf("LC");
            int aptos = distribucion.indexOf("AP");

            if (locales == -1 && aptos == -1) {
                return true;
            } else if (locales == -1 && aptos != -1 && interiores == 0) {
                fragmento = distribucion.substring(interiores, aptos);
            } else if (locales != -1 && aptos == -1 && interiores == 0) {
                fragmento = distribucion.substring(interiores, locales);
            } else if (locales != -1 && aptos != -1 && interiores == 0) {
                int mitad = aptos < locales ? aptos : locales;
                fragmento = distribucion.substring(interiores, mitad);
            } else if ((aptos == 0 && locales == -1) || (aptos == -1 && locales == 0)) {
                fragmento = distribucion.substring(interiores);
            } else if (aptos == 0 && locales != -1 && locales > interiores) {
                fragmento = distribucion.substring(interiores, locales);
            } else if (aptos != -1 && locales == 0 && aptos > interiores) {
                fragmento = distribucion.substring(interiores, aptos);
            } else if (aptos != -1 && locales != -1 && (interiores > locales && interiores > aptos)) {
                fragmento = distribucion.substring(interiores);
            }
        }
        if (!fragmento.isEmpty()) {
            distribucion = distribucion.replace(fragmento, "").trim();
        }
        if (distribucion.contains("LC ")) {
            distribucion = distribucion.replace("LC ", "");
        }
        if (distribucion.contains("AP ")) {
            distribucion = distribucion.replace("AP ", "");
        }

        distribucion = distribucion.replace(" ", "-");
        String HHPPS[] = distribucion.split("-");
        for (int i = 0; i < HHPPS.length; i++) {
            if (HHPPS[i].matches("[0-9]*") && !HHPPS[i].isEmpty()) {
                int pisoActual = Integer.parseInt(HHPPS[i].substring(0, 1));
                if (pisoActual > pisosFicha) {
                    distribucionValida = false;
                    break;
                }
            } else {
                LOGGER.error("Error en la estructura de la distribucion");
                distribucionValida = false;
                break;
            }
        }
        return distribucionValida;
    }

    private boolean validaPlacaIT(String st) {
        return st.contains("SC") || st.contains("ST") || st.contains("SECTOR") ? st.replace(st.substring(0, st.indexOf("-")), "0").length() <= 10 : st.replace(" ", "").length() <= 10;
    }

    private boolean validaPlacaCD(String st) {
        return st.indexOf("CD") == 0;
    }

    private static boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

}
