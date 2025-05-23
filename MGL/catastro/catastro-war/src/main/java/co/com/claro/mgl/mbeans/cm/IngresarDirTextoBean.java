/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.utils.Constant;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author bocanegravm
 */
@ManagedBean(name = "ingresarDirTextoBean")
@ViewScoped
public class IngresarDirTextoBean {

    private static final Logger LOGGER = LogManager.getLogger(IngresarDirTextoBean.class);

    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private SecurityLogin securityLogin;
    private List<DrDireccion> dirTabuladaLst;
    private String direccionTexto;
    private List<String> divLst;
    private String[] partesDireccion;
    private String divisores = "";
    private ParametrosMgl parametroDirCK = null;
    private ParametrosMgl parametroDirPlacaCK = null;
    private String regExpression;
    private boolean controlFormatoDirVP = false;
    private boolean controlFormatoDirVG = false;
    private String tipoViaPr = "";
    private String[] numPriLetras;
    private String numPrincipal = "";
    private String letraPri1 = "";
    private String letraPri2 = "";
    private String bisPri = "";
    private String bisPriVal = "";
    private String bisPriFin = "";
    private String cuaViaPri = "";

    private String[] tipoAndNumGen;
    private String tipoViaGen = "";
    private String[] numGenLetras;
    private String numGeneradora = "";
    private String letraGen1 = "";
    private String letraGen2 = "";
    private String letraGen3 = "";
    private String bisgen = "";
    private String bisgenVal = "";
    private String bisgenFin = "";
    private String cuaViaGen = "";
    private String placa = "";

    private ParametrosMgl parametroDiv = null;

    @EJB
    private ParametrosMglFacadeLocal parametrosMglFacadeLocal;

    public IngresarDirTextoBean() {
        try {
            securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }

        } catch (IOException e) {
            String msg = "Error al iniciar el formulario de IngresarDirTexto:..." + e.getMessage();
            FacesUtil.mostrarMensajeError(msg + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en IngresarDirTextoBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void init() {

        try {

            parametroDiv = parametrosMglFacadeLocal.findByAcronimoName(Constant.DIVISION_DIR_CK);
            if (parametroDiv != null) {
                divisores = parametroDiv.getParValor();
            }
            String[] div = divisores.split("\\|");
            divLst = Arrays.asList(div);

            parametroDirCK = parametrosMglFacadeLocal.findByAcronimoName(Constant.EXP_REG_VALIDA_DIR_CK_FICHAS_NODO);
            parametroDirPlacaCK = parametrosMglFacadeLocal.findByAcronimoName(Constant.EXP_REG_VALIDA_PLACA_CK_FICHAS_NODO);

            dirTabuladaLst = new ArrayList<>();

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en init. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en init. ", e, LOGGER);
        }

    }

    public String tabularDireccion() {

        limpiarVariables();

        if (direccionTexto != null && !direccionTexto.isEmpty()) {

            for (String d : divLst) {

                int intIndex = direccionTexto.indexOf(d);

                if (intIndex != -1) {
                    partesDireccion = direccionTexto.split(d);
                    break;
                }

            }

            if (partesDireccion != null) {

                //Verificamos la primera parte de la direccion via Principal  
                regExpression = parametroDirCK.getParValor();
                Pattern pat = Pattern.compile(regExpression);
                final Matcher matcher = pat.matcher(partesDireccion[0].trim());

                while (matcher.find()) {

                    for (int i = 1; i <= matcher.groupCount(); i++) {

                        if (i == 1 && matcher.group(i) != null
                                && !matcher.group(i).isEmpty()) {
                            tipoViaPr = matcher.group(i);
                        }

                        if (i == 29 && matcher.group(i) != null
                                && !matcher.group(i).isEmpty()) {

                            numPriLetras = matcher.group(i).split(" ");
                            int j = 0;

                            if (numPriLetras.length > 1 && numPriLetras[1] != null
                                    && !numPriLetras[1].equalsIgnoreCase("BIS")) {
                                for (String partes : numPriLetras) {

                                    if (j == 0) {
                                        numPrincipal = partes;
                                    }
                                    if (j == 1) {
                                        letraPri1 = partes;
                                    }
                                    if (j == 2) {
                                        letraPri2 = partes;
                                    }

                                    j++;
                                }
                            } else {

                                for (String partes : numPriLetras) {

                                    if (j == 0) {
                                        numPrincipal = partes;
                                    }
                                    j++;
                                }
                            }
                        }

                        if (i == 33 && matcher.group(i) != null
                                && !matcher.group(i).isEmpty()) {
                            bisPri = matcher.group(i);
                        }
                        if (i == 34 && matcher.group(i) != null
                                && !matcher.group(i).isEmpty()) {
                            bisPriVal = matcher.group(i);
                        }
                        
                        if (bisPri != null && !bisPriVal.isEmpty()) {
                            bisPriFin = bisPriVal;
                        }

                        if (bisPri != null && bisPriVal.isEmpty()) {
                            bisPriFin = bisPri;
                        }

                        if (i == 41 && matcher.group(i) != null
                                && !matcher.group(i).isEmpty()) {
                            cuaViaPri = matcher.group(i);
                        }

                        controlFormatoDirVP = true;
                    }
                }

                if (!controlFormatoDirVP) {

                    String msg = "La via principal debe ir minimo TIPO VIA PRINCIPAL:'CK|CL|'"
                            + " + NUMERO VIA PRINCIPAL; maximo: TIPO VIA PRINCIPAL:"
                            + " 'CK|CL|'+ NUEMRO VIA PRINCIPAL + LETRA 1 + LETRA 2 + BIS + CARDINALES";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msg, ""));

                    return "";
                }

                //Verificamos la segunda parte de la direccion via Generadora o parte de la placa  
                regExpression = parametroDirPlacaCK.getParValor();
                Pattern pat2 = Pattern.compile(regExpression);
                final Matcher matcher2 = pat2.matcher(partesDireccion[1].trim());
                while (matcher2.find()) {
                    
                    for (int i = 1; i <= matcher2.groupCount(); i++) {

                        if (i == 3 && matcher2.group(i) != null
                                && !matcher2.group(i).isEmpty()) {
                            tipoAndNumGen = matcher2.group(i).split(" ");

                            int j = 0;
                            for (String partes : tipoAndNumGen) {
                                if (tipoAndNumGen.length == 1) {
                                    numGeneradora = partes;
                                } else {
                                    if (j == 0) {
                                        tipoViaGen = partes;
                                    }
                                    if (j == 1) {
                                        numGeneradora = partes;
                                    }
                                }
                                j++;
                            }
                        }

                        if (i == 9 && matcher2.group(i) != null
                                && !matcher2.group(i).isEmpty()) {

                            numGenLetras = matcher2.group(i).split(" ");

                            int j = 0;
                            for (String partes : numGenLetras) {

                                if (j == 0) {
                                    letraGen1 = partes;

                                }
                                if (j == 1) {
                                    letraGen2 = partes;

                                }
                                if (j == 2) {
                                    letraGen3 = partes;

                                }
                                j++;
                            }

                        }
                        
                       if (i == 11 && matcher2.group(i) != null
                                && !matcher2.group(i).isEmpty()) {

                            bisgen = matcher2.group(i);
                        }

                        if (i == 12 && matcher2.group(i) != null
                                && !matcher2.group(i).isEmpty()) {

                            bisgenVal = matcher2.group(i);
                        }
                        
                        if (bisgen != null && !bisgenVal.isEmpty()) {
                            bisgenFin = bisgenVal;
                        }

                        if (bisgen != null && bisgenVal.isEmpty()) {
                            bisgenFin = bisgen;
                        }

                        if (i == 13 && matcher2.group(i) != null
                                && !matcher2.group(i).isEmpty()) {

                            placa = matcher2.group(i);
                        }

                        if (i == 17 && matcher2.group(i) != null
                                && !matcher2.group(i).isEmpty()) {

                            cuaViaGen = matcher2.group(i);
                        }
                        controlFormatoDirVG = true;
                    }
                }

                if (!controlFormatoDirVG) {

                    String msg = "La via generadora debe ir minimo "
                            + "NUMERO VIA GENERADORA + '-' + PLACA; maximo: TIPO VIA GENERADORA:"
                            + " 'TV|TR|DG'+ NUEMRO VIA GENERADORA + LETRA 1 + "
                            + "LETRA 2 + LETRA 3 + BIS + '-' + PLACA+ CARDINALES";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msg, ""));

                    return "";
                }

                if (controlFormatoDirVP && controlFormatoDirVG) {
                    DrDireccion direccion = new DrDireccion();
                    direccion.setTipoViaPrincipal(tipoViaPr);
                    direccion.setNumViaPrincipal(numPrincipal);
                    direccion.setLtViaPrincipal(letraPri1);
                    direccion.setNlPostViaP(letraPri2);
                    direccion.setBisViaPrincipal(bisPriFin);
                    direccion.setCuadViaPrincipal(cuaViaPri);
                    direccion.setTipoViaGeneradora(tipoViaGen);
                    direccion.setNumViaGeneradora(numGeneradora);
                    direccion.setLtViaGeneradora(letraGen1);
                    direccion.setNlPostViaG(letraGen2);
                    direccion.setLetra3G(letraGen3);
                    direccion.setBisViaGeneradora(bisgenFin);
                    direccion.setCuadViaGeneradora(cuaViaGen);
                    direccion.setPlacaDireccion(placa);

                    dirTabuladaLst.add(direccion);

                } else {
                    String msg = "Ocurrio un error al menos formando una de las dos partes de la direccion";
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    msg, ""));

                    return "";
                }
            } else {
                String msg = "la direccion debe ir separado por alguno de estos separadores: " + divisores + " ";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                msg, ""));
            }
        } else {
            String msg = "ingrese una direccion valida para tabular";
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, ""));
        }

        return "";
    }

    public String limpiarCampos() {

        direccionTexto = "";
        dirTabuladaLst = new ArrayList<>();

        return "";
    }

    public void limpiarVariables() {

        partesDireccion = null;
        controlFormatoDirVP = false;
        controlFormatoDirVG = false;
        tipoViaPr = "";
        numPrincipal = "";
        letraPri1 = "";
        letraPri2 = "";
        bisPri = "";
        bisPriVal="";
        bisPriFin="";
        cuaViaPri = "";

        tipoAndNumGen = null;
        tipoViaGen = "";
        numGenLetras = null;
        numGeneradora = "";
        letraGen1 = "";
        letraGen2 = "";
        letraGen3 = "";
        bisgen = "";
        bisgenVal="";
        bisgenFin="";
        cuaViaGen = "";
        placa = "";

    }

    public List<DrDireccion> getDirTabuladaLst() {
        return dirTabuladaLst;
    }

    public void setDirTabuladaLst(List<DrDireccion> dirTabuladaLst) {
        this.dirTabuladaLst = dirTabuladaLst;
    }

    public String getDireccionTexto() {
        return direccionTexto;
    }

    public void setDireccionTexto(String direccionTexto) {
        this.direccionTexto = direccionTexto;
    }

}
