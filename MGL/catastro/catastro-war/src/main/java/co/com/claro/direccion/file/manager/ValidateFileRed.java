/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.direccion.file.manager;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.Geografico;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.data.Nodo;
import co.com.telmex.catastro.delegate.SolicitudRedDelegate;
import co.com.telmex.catastro.mbeans.solicitudes.OutputElementSolPlaneacionRed;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
public class ValidateFileRed {

    private String cruce;
    private String placa;
    private int totalHHPP;
    private int numApartamentos;
    private int numOficians; 
    private int numLocales;
    private int totalPisos;
    private String Distribution;
    
    private static final Logger LOGGER = LogManager.getLogger(ValidateFileRed.class);
    private OutputElementSolPlaneacionRed oespr = new OutputElementSolPlaneacionRed();
    private String messColum = "";
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private Geografico geografico = null;
    
    @Override
    public String toString(){
        String var =" Cruce:"+this.cruce + " " +
        " placa:"+this.placa+" "+
        " Distribution:"+this.Distribution+" "+
        " totalHHPP:"+this.totalHHPP+" "+
        " numApartamentos:"+this.numApartamentos+" "+
        " numOficians:"+this.numOficians+" "+
        " numLocales:"+this.numLocales+" "+
        " totalPisos:"+this.totalPisos+" ";
       return var;
    }
    
    public ValidateFileRed (String cruce,String placa,String totalHHPP,String numApartamentos,String numOficianas,String numLocales,String totalPisos,String Distribution){
        this.cruce=cruce;
        this.placa=placa; 
        this.Distribution = Distribution;
        this.totalHHPP=(!totalHHPP.matches("[0-9]+"))?0:Integer.parseInt(totalHHPP);
        this.numApartamentos=(!numApartamentos.matches("[0-9]+"))?0:Integer.parseInt(numApartamentos);
        this.numOficians=(!numOficianas.matches("[0-9]+"))?0:Integer.parseInt(numOficianas); 
        this.numLocales=(!numLocales.matches("[0-9]+"))?0:Integer.parseInt(numLocales);
        this.totalPisos=(!totalPisos.matches("[0-9]+"))?0:Integer.parseInt(totalPisos); 
    }
    
    /**
     * Metodo para validar los datos de la columna calle
     *
     * @param data
     * @return
     */
    public boolean validateCalles(String data) {
        oespr.setCalle(data);
        if (data == null || data.equalsIgnoreCase("")||data.equalsIgnoreCase("SN")) {
            messColum = "El dato no puede estar vacio y no puede contener SN";
            return true;
        }
        return false;
    }

    /**
     * Metodo para validar los datos de la columna Direccion
     *
     * @param data
     * @return
     */
    public boolean validateDireccion(String data) {
        oespr.setValidar(data);
        boolean error = false;
        return error;
    }

    /**
     * metodo para validar los datos de la placa descripcion: valida si existen
     * varias placas relacionadas de ser asi valida y asigna el valor de las
     * placas adicionales a la direccion alterna
     *
     * @param data
     * @return
     */
    public boolean validatePlaca(String data) {
        boolean error = false;
        oespr.setPlaca(data);    
        if (data.isEmpty() || data.contains("SN")) {
            messColum = "El dato no puede ser SN o Vacio";
            error=true;
        }
        String[] placasUnidas = data.split("-");
        if (placasUnidas.length <= 1) {
            messColum = "El dato debe contener '-' como separador";
            error=true;
        }
        return error;
    }

    /**
     * Metodo para validar los datos de la columna calle
     *
     * @param data
     * @return
     */
    public boolean validateEstrato(String data) {
        boolean error = false;
        oespr.setEstrato(data);
        if (data.toUpperCase().equals("NG")){
            return true;
        }
        if (!(data == null || data.equalsIgnoreCase(""))) {
            if (data.length() == 0 || !data.matches("[0-6]")) {
                messColum = "El estrado no es valido debe ser entre [0 - 6]";
                error = true;
            }
        }
        return error;
    }

    /**
     * metodo para validar los datos del total de pphh
     *
     * @param data
     * @return
     */
    public boolean validateTotalPphh(String data) {
        oespr.setTotalHhpp(data);
        if ("".equals(data) ||!data.matches("[0-9]*")) {
            messColum = "El dato no puede ser '0' o Vacio";
            return true;
        }
        return false;
    }

    /**
     * metodo para validar los datos del Barrio
     *
     * @param data
     * @param city
     * @return
     */
    public boolean validateBarrio(String data, GeograficoPolitico city) {
        boolean error = false;
        oespr.setBarrio(data);
        if (city.getGpoMultiorigen().equals("1")) {
            if ("".equals(data)) {
                messColum = "Ciudad multiorigen, barrio obligatorio";
                error = true;
            }
        }
        return error;
    }

    /**
     * metodo para validar los datos del Barrio
     *
     * @param data
     * @return
     */
    
    
    /**
     *
     * @param regionS
     * @return
     */
    public GeograficoPolitico loadRegion(String regionS) {
        GeograficoPolitico geo = new GeograficoPolitico();
        List regionl = (List) session.getAttribute("listRegion");

        for (int i = 0; i < regionl.size(); i++) {
            GeograficoPolitico region = (GeograficoPolitico) regionl.get(i);
            if (region.getGpoId().equals(new BigDecimal(regionS))) {
                geo = region;
            }
        }
        return geo;
    }

    /**
     * metodo para validar los datos del Nodo
     *
     * @param data
     * @param city
     * @return
     */
    public boolean validateNodo(String data, GeograficoPolitico city) {
        oespr.setNodo(data);
        if (data.equals("") || data.isEmpty()) {
            messColum = "El dato es obligatorio";
            return true;
        }
        return false;
    }

    /**
     * Medoto que realiza la busqueda del nodo
     *
     * @param nodo
     * @param gpoId
     * @return
     */
    public Nodo buscarNodo(String nodo, BigDecimal gpoId) {
        Nodo nodDir = new Nodo();
        try {
            nodDir = SolicitudRedDelegate.queryNodo(nodo, gpoId);
        } catch (ApplicationException ex) {
            LOGGER.error("ERROR: Intentando optener el nodo : ", ex);

        }
        return nodDir;
    }
    /**
     * Metodo para validar los datos de la columna Nombre
     *
     * @param data
     * @return
     */
    public boolean validateNombre(String data) {
        oespr.setNombre(data);
        return false;
    }

    /**
     * Metodo para validar los datos de la columna Aptos
     *
     * @param data
     * @return
     */
    public boolean validateAtpos(String data) {
        oespr.setAptos(data);
        if ("".equals(data) || !data.matches("[0-9]*")) {
            messColum = "El dato debe ser un número";
            return true;
        }
        return false;
    }

    /**
     * Metodo para validar los datos de la columna Locales
     *
     * @param data
     * @return
     */
    public boolean validateLocales(String data) {
        oespr.setLocales(data);
        if ("".equals(data) || !data.matches("[0-9]*")) {
            messColum = "El dato debe ser un número";
            return true;
        }        
        return false;
    }

    /**
     * Metodo para validar los datos de la columna Oficinas
     *
     * @param data
     * @return
     */
    public boolean validateOficinas(String data) {
        oespr.setOficinas(data);
            if ("".equals(data) || !data.matches("[0-9]*")) {
            messColum = "El dato debe ser un número";
            return true;
        }
        return false;
    }

    /**
     * Metodo para validar los datos de la columna Oficinas
     *
     * @param data
     * @return
     */
    public boolean validatePisos(String data) {
        oespr.setPisos(data);
        if ("".equals(data) || !data.matches("[0-9]*")) {
            messColum = "El dato debe ser un número";
            return true;
        }
        return false;
    }

    


    /**
     * Metodo para validar los datos de la columna valValidar
     *
     * @param data
     * @return
     */
    public boolean valValidar(String data) {
        data=data.replace((char)13,' ').replace(" ","");
        oespr.setValidar(data);        
        if(!(data.trim().equals("S")||data.trim().equals("N"))){
            messColum = "El campo validar solo puede tomar los valores 'S' o 'N'";
            return true;
        }
        return false;
    }


    /**
     * Metodo que maneja el mensaje de error
     *
     * @param columna
     * @param data
     * @param fila
     * @return 
     */
    public String setMesageError(String columna, String data, String fila) {
        return "El dato [ " + data + " ] de la fila : " + fila + " en la columna [ " + columna + " ] no es valido : " + messColum;
    }

    public OutputElementSolPlaneacionRed getOespr() {
        return oespr;
    }

    public void setOespr(OutputElementSolPlaneacionRed oespr) {
        this.oespr = oespr;
    }


    public Geografico getGeografico() {
        return geografico;
    }

    public void setGeografico(Geografico geografico) {
        this.geografico = geografico;
    }

    public boolean valDistHhpp(String distribucion) {
        oespr.setDistribucion(distribucion);
        if (this.totalPisos > 3) {
            messColum ="; Tiene mas de 3 pisos debe ser cuenta matriz";
            return true;
        }
        if (this.numLocales > 3) {
            messColum ="; Una direccion con mas de tres locales es una cuenta matriz";
            return true;
        }
        if (this.numApartamentos == 1 && this.numLocales == 0 && this.numOficians == 0) {
            if (!this.Distribution.isEmpty()) {
                if(!this.Distribution.matches("((INT) ((?!\\-)(\\-?\\w+)))")){
                    messColum ="; Una casa solo puede tener un interior como distribución";
                    return true;
                }
            }
        }
        if (this.totalHHPP > 3 && this.Distribution.isEmpty()) {
            messColum ="; Cuando hay  4 o más HHPP debe ir distribución ";
            return true;
        }
            
        if (!this.Distribution.isEmpty()) {
            if(this.Distribution.toUpperCase().contains("INT")){
                if(!(isCountDisTotalXEqualsHHPPX("INT", this.totalHHPP)&&isCountDisTotalXEqualsHHPPX("INT", this.numApartamentos))){
                    messColum ="; El total de interiores debe ser igual al total de HHPP y al número de apartamentos.";
                    return true;                        
                } 
            } else if (!isCountDisTotalXEqualsHHPPX("AP", this.numApartamentos)
                        || !isCountDisTotalXEqualsHHPPX("OF", this.numOficians)
                        || !isCountDisTotalXEqualsHHPPX("LO", this.numLocales)) {
                    messColum ="; El numero de AP,LO,OF no corresponde con la distribucion";
                    return true;
                }        
        }
        if (!isValidFormatDistribution()) {
            messColum ="; La distribucion no es valida";
            return true;
        }
        if (!equalsTotalHHPPToTotalApLoOf()) {
            messColum ="; El total de HHPP no corresponde con la sumatoria de AP,LO,OF";
            return true;
        }
        return false;
    }
   

    public List<String> getHHPPs() {
        List<String> grupoPisos = new ArrayList<String>();
        List<String> grupoApLoOf = new ArrayList<String>();
        List<String> grupoHHPP = new ArrayList<String>();
        List<String> hhpps = new ArrayList<String>();
        String tmpApLoOf;
        String regualaExpressionHHPP = "(\\w+)";
        String regularExpressionApLoOf = "(AP|LO|OF)";
        String regularExpressionINT = "(INT)";
        String regExpGrupoApLcOf = "((AP|LC|OF)\\s((?!\\-)(\\-?\\w+)+)( ?))";
        String regularExpressionInteriores = "((INT) ((?!\\-)(\\-?\\w+)+))";
        String piso = "";
        String apartamento = "";
        if (!Distribution.trim().isEmpty()) {
            
            if (isStringValidForExprecion(regExpGrupoApLcOf, Distribution)) {
                //obtenemos el grupo de elementos de cada tipo(LC-AP-OF)
                grupoApLoOf = extractMachesForExpression(regExpGrupoApLcOf, Distribution);
                
                for (int aPlOoF = 0; aPlOoF < grupoApLoOf.size(); aPlOoF++) {
                    
                    apartamento = extractMachesForExpression(regularExpressionApLoOf, grupoApLoOf.get(aPlOoF)).get(0);
                    tmpApLoOf = grupoApLoOf.get(aPlOoF).replace("AP", "").replace("OF", "").replace("LO", "");
                    grupoHHPP = extractMachesForExpression(regualaExpressionHHPP, tmpApLoOf);
                    
                    if(!(this.numApartamentos == 1 && this.numLocales == 0 && this.numOficians == 0)){
                        for (int conHHPP = 0; conHHPP < grupoHHPP.size(); conHHPP++) {
                            grupoHHPP.get(conHHPP);
                            hhpps.add(getCruce() + " # " + getPlaca().split("/")[0] +" |PISO1-"
                                    + apartamento.replace("AP", "APARTAMENTO").replace("LO", "LOCAL").replace("OF", "OFICINA") 
                                    +  grupoHHPP.get(conHHPP));
                        }
                    }
                }
            }
            
            if (isStringValidForExprecion(regularExpressionInteriores, this.getDistribution())) {
                grupoApLoOf = extractMachesForExpression(regularExpressionInteriores, this.getDistribution());
                for (int aPlOoF = 0; aPlOoF < grupoApLoOf.size(); aPlOoF++) {
                    apartamento = extractMachesForExpression(regularExpressionINT, grupoApLoOf.get(aPlOoF)).get(0);
                    tmpApLoOf = grupoApLoOf.get(aPlOoF).replace("INT", "");
                    grupoHHPP = extractMachesForExpression(regualaExpressionHHPP, tmpApLoOf);
                        for (int conHHPP = 0; conHHPP < grupoHHPP.size(); conHHPP++) {
                            grupoHHPP.get(conHHPP);
                            if(grupoHHPP.size()>3){
                                hhpps.add(getCruce() + " # " + getPlaca().split("/")[0] + "| " + "CASA" + " " + grupoHHPP.get(conHHPP));
                            }else{    
                                hhpps.add(getCruce() + " # " + getPlaca().split("/")[0] + "| PISO1-" 
                                        + apartamento.replace("INT", "INTERIOR") 
                                        + grupoHHPP.get(conHHPP));
                            }
                        }
                }
            }
        } else {
            if(!(this.numApartamentos == 1 && this.numLocales == 0 && this.numOficians == 0)){
                for (int i = 1; i <= this.getNumApartamentos(); i++) {
                    hhpps.add(getCruce() + " # " + getPlaca().split("/")[0] + "| PISO1-APARTAMENTO" + i);
                }
                for (int i = 1; i <= this.getNumOficians(); i++) {
                    hhpps.add(getCruce() + " # " + getPlaca().split("/")[0] + "| PISO1-OFICINA" + i);
                }
                for (int i = 1; i <= this.getNumLocales(); i++) {
                    hhpps.add(getCruce() + " # " + getPlaca().split("/")[0] + "| PISO1-LOCAL" + i);
                }
            }
        }
        if(hhpps.isEmpty()){
                hhpps.add(getCruce() + " # " + getPlaca().split("/")[0]+"| CASA");
        }
           
        return hhpps;
    }

    public boolean isCountDisTotalXEqualsHHPPX(String grupo, int num) {
        
        if (CountDisTotal(grupo) == num) {
            return true;
        }
        return false;
    }

    public int CountDisTotal(String grupo) {
        int numAPs = 0;
        List<String> GrupoApartamentos;
        List<String> apartamentos;
        String regularExpressionGrupoAP = "((" + grupo + ") ((?!\\-)(\\-?\\w+)+))";
        String regualaExpressionAP = "(\\w+)";
        GrupoApartamentos = extractMachesForExpression(regularExpressionGrupoAP, this.getDistribution());
        if (GrupoApartamentos.size() > 0) {
            for (int i = 0; i < GrupoApartamentos.size(); i++) {
                apartamentos = extractMachesForExpression(regualaExpressionAP, GrupoApartamentos.get(i).replace(grupo, ""));
                if (apartamentos.size() > 0) {
                    for (int j = 0; j < apartamentos.size(); j++) {
                        numAPs++;
                    }
                }
            }
        }
        return numAPs;
    }

    public List<String> extractMachesForExpression(String expression, String stringToValidate) {
        List<String> stringsExtracted;
        stringsExtracted = new ArrayList<String>();
        Pattern patter;
        Matcher matcher;
        patter = Pattern.compile(expression);

        if (patter.matcher(stringToValidate).find()) {
            matcher = patter.matcher(stringToValidate);
            while (matcher.find()) {
                stringsExtracted.add(matcher.group());
            }
        }
        return stringsExtracted;
    }

    public boolean equalsTotalHHPPToTotalApLoOf() {
        if (this.getTotalHHPP() == (this.getNumApartamentos() + this.getNumLocales() + this.getNumOficians())) {
            return true;
        }
        return false;
    }


    public boolean isValidFormatDistribution() {
        String regularExpressionAparamentoLocalOficina = "^((AP|LO|OF) ((?!\\-)(\\-?\\w+)+)( ?))+$";
        String regularExpressionInteriores = "^((INT) ((?!\\-)(\\-?\\w+)+))$";
        if (this.getDistribution().equals("")) {
            return true;
        }
        if (isStringValidForExprecion(regularExpressionAparamentoLocalOficina, this.getDistribution())) {
            return true;
        } else if (isStringValidForExprecion(regularExpressionInteriores, this.getDistribution())) {
            return true;
        }
        return false;
    }

    private boolean isStringValidForExprecion(String expression, String stringToValidate) {
        Pattern patter;
        patter = Pattern.compile(expression);
        if (patter.matcher(stringToValidate).find()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return the Cruce
     */
    public String getCruce() {
        return cruce;
    }

    /**
     * @param cruce
     */
    public void setCruce(String cruce) {
        this.cruce = cruce;
    }

    /**
     * @return the placa
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * @param placa the placa to set
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * @return the totalHHPP
     */
    public int getTotalHHPP() {
        return totalHHPP;
    }

    /**
     * @param totalHHPP the totalHHPP to set
     */
    public void setTotalHHPP(int totalHHPP) {
        this.totalHHPP = totalHHPP;
    }

    /**
     * @return the numApartamentos
     */
    public int getNumApartamentos() {
        return numApartamentos;
    }

    /**
     * @param numApartamentos the numApartamentos to set
     */
    public void setNumApartamentos(int numApartamentos) {
        this.numApartamentos = numApartamentos;
    }

    /**
     * @return the numOficians
     */
    public int getNumOficians() {
        return numOficians;
    }

    /**
     * @param numOficians the numOficians to set
     */
    public void setNumOficians(int numOficians) {
        this.numOficians = numOficians;
    }

    /**
     * @return the numLocales
     */
    public int getNumLocales() {
        return numLocales;
    }

    /**
     * @param numLocales the numLocales to set
     */
    public void setNumLocales(int numLocales) {
        this.numLocales = numLocales;
    }

    /**
     * @return the totalPisos
     */
    public int getTotalPisos() {
        return totalPisos;
    }

    /**
     * @param totalPisos the totalPisos to set
     */
    public void setTotalPisos(int totalPisos) {
        this.totalPisos = totalPisos;
    }

    /**
     * @return the Distribution
     */
    public String getDistribution() {
        return Distribution;
    }

    /**
     * @param Distribution the Distribution to set
     */
    public void setDistribution(String Distribution) {
        this.Distribution = Distribution;
    }
}
