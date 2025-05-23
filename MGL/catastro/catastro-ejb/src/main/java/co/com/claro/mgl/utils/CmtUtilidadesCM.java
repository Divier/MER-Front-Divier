package co.com.claro.mgl.utils;

import co.com.claro.mgl.businessmanager.cm.CmtReglaEstadoCmMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtReglaEstadoCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class CmtUtilidadesCM {
    
    private static final Logger LOGGER = LogManager.getLogger(CmtUtilidadesCM.class);
    

    public static String calcularAbreviatura(String frase) {
        String abreviatura = "";
        if (!frase.isEmpty()) {
            String[] fraseDividida = frase.split(" ");
            if (fraseDividida.length == 1) {
                String palabra = fraseDividida[0];
                switch (palabra.trim().length()) {
                    case 1:
                        return palabra + "XX";
                    case 2:
                        return palabra + "X";
                    case 3:
                        return palabra;
                    default:
                        return palabra.substring(0, 3);
                }
            }
            if (fraseDividida.length == 2) {
                String palabra = fraseDividida[0];
                abreviatura += sacarPrimeraLetra(palabra);
                palabra = fraseDividida[1];
                abreviatura += sacarPrimeraLetra(palabra);
            }
            if (fraseDividida.length >= 3) {
                String palabra = fraseDividida[0];
                abreviatura += sacarPrimeraLetra(palabra);
                palabra = fraseDividida[1];
                abreviatura += sacarPrimeraLetra(palabra);
                palabra = fraseDividida[2];
                abreviatura += sacarPrimeraLetra(palabra);
            }
            return abreviatura;
        } else {
            return "XXX";
        }
    }

    private static String sacarPrimeraLetra(String palabra) {
        if (palabra.trim().length() == 0) {
            return "";
        }

        switch (palabra.trim().length()) {
            case 1:
                return palabra;
            default:
                return palabra.substring(0, 1);
        }
    }

    public static String strCNum(String numero, int digitosNum) {
        if (digitosNum > 0) {
            String cadenaCeros = "000000000000000000000000000000000000";
            String cadenaAplicar = cadenaCeros.substring(0, digitosNum);
            return (cadenaAplicar + numero).substring((cadenaAplicar + numero).length() - (digitosNum));
        }
        return numero;
    }
    /**
     * Vlaida si un strin es numerico.
     * @param n String con el numero
     * @return boolean.
     */
    
    public static boolean isNumeber(String n) {
        return n.matches("-?\\d+(\\.\\d+)?");
    }
    
    
    /**
     * valbuenayf Metodo para adicionar ceros al numero de cuenta
     *
     * @param idCuentaM
     * @return
     */
    public static String getIdCuentaMatriz(BigDecimal idCuentaM) {
        String respuesta = null;
        String numero = idCuentaM.toString();
        Integer total = idCuentaM.toString().length();

        switch (total) {
            case 1:
                return "00000000000000" + numero;
            case 2:
                return "0000000000000" + numero;
            case 3:
                return "000000000000" + numero;
            case 4:
                return "00000000000" + numero;
            case 5:
                return "0000000000" + numero;
            case 6:
                return "000000000" + numero;
            case 7:
                return "00000000" + numero;
            case 8:
                return "0000000" + numero;
            case 9:
                return "000000" + numero;
            case 10:
                return "00000" + numero;
            case 11:
                return "0000" + numero;
            case 12:
                return "000" + numero;
            case 13:
                return "00" + numero;
            case 14:
                return "0" + numero;
            case 15:
                return numero;
        }
        return respuesta;
    }
    
    /**
     * valbuenayf Metodo para calcular los dias que que han pasado hasta el dia
     * de hoy
     *
     * @param fecha
     * @return
     */
    public static Long calcularDiasHoy(Date fecha) {
        Long resultado;
        try {

            Date hoy = new Date();
            
            Calendar calFechaInicial = Calendar.getInstance();
            Calendar calFechaFinal = Calendar.getInstance();
            /**
             * Le pasamos el objeto Date al metodo set time
             */
            calFechaInicial.setTime(hoy);
            calFechaFinal.setTime(fecha);
            
            resultado=((calFechaInicial.getTimeInMillis()-calFechaFinal.getTimeInMillis())/1000/60/60);


        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(CmtUtilidadesCM.class)+"': " + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
        return resultado;
    }
    
      /**
     * Función el estado combinado segun cumplimiento de reglas parametrizadas
     *
     * @author Juan David Hernandez     
     * @param tecSubList     
     * 
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */ 
     public CmtBasicaMgl obtenerEstadoCmRrByReglaEstado(List<CmtTecnologiaSubMgl> tecSubList) throws ApplicationException {
        try {            
            //Si no llega nada retorna null
            if (tecSubList != null && !tecSubList.isEmpty()) {
                
                List<CmtTecnologiaSubMgl> tecSubListTmp = new ArrayList();
                
                tecSubListTmp = eliminarTecnologiaRedFoListado(tecSubList);

                //listado sin RED FO
                if (tecSubListTmp != null && !tecSubListTmp.isEmpty()) {

                CmtReglaEstadoCmMglManager cmtReglaEstadoCmMglManager = new CmtReglaEstadoCmMglManager();

                //listado con todos los numeros de reglas existentes.
                List<String> numeroReglaList = cmtReglaEstadoCmMglManager.findNumeroReglaList();
                int contadorReglasExitosas = 0;
                List<CmtReglaEstadoCmMgl> reglasExitosas = new ArrayList();
                
                //Si no existen regla parametrizadas retorna null
                if (numeroReglaList != null && !numeroReglaList.isEmpty()) {
                    //recorrido de cada una de las reglas parametrizadas
                    for (String numeroRegla : numeroReglaList) {
                        //Listado con cada regla cargada que contiene la tecnologia con sus estados
                        List<CmtReglaEstadoCmMgl> cmtReglaEstadoCmMglList = cmtReglaEstadoCmMglManager.findReglaByNumeroRegla(numeroRegla);

                        //si no encuentra la regla retorna null
                        if (cmtReglaEstadoCmMglList != null && !cmtReglaEstadoCmMglList.isEmpty()) {
                            int contEstado = 0;                            
                            //recorrido de listado de tecnologia y estados recibidos
                            for (CmtTecnologiaSubMgl cmtTecnologiaSubMgl : tecSubListTmp) {
                                //recorrido de listado de estados de la regla
                                for (CmtReglaEstadoCmMgl cmtReglaEstadoCmMgl1 : cmtReglaEstadoCmMglList) {
                                    //validacion de datos que se van a comparar
                                    if (cmtReglaEstadoCmMgl1.getTecnologiaBasicaId() != null
                                            && cmtReglaEstadoCmMgl1.getTecnologiaBasicaId().getBasicaId() != null
                                            && cmtReglaEstadoCmMgl1.getEstadoTecBasicaId() != null
                                            && cmtReglaEstadoCmMgl1.getEstadoTecBasicaId().getBasicaId() != null
                                            && cmtTecnologiaSubMgl.getBasicaIdTecnologias() != null
                                            && cmtTecnologiaSubMgl.getBasicaIdEstadosTec() != null) {

                                        //condición para determinar si se cumple la regla. (Si es la misma tecnologia y el mismo estado parametrizado comparado con el recibido)
                                        if (cmtTecnologiaSubMgl.getBasicaIdTecnologias().getBasicaId().equals(cmtReglaEstadoCmMgl1.getTecnologiaBasicaId().getBasicaId())
                                                && cmtTecnologiaSubMgl.getBasicaIdEstadosTec().getBasicaId().equals(cmtReglaEstadoCmMgl1.getEstadoTecBasicaId().getBasicaId())) {
                                            contEstado++;
                                        }
                                    }
                                }

                                //Escenarios de reglas
                                /*Si el contador de estados, el tamaño del listado recibido, y el listado de la regla
                                *coinciden se retorna el estado de la regla*/
                                if (contEstado == tecSubListTmp.size()
                                        && cmtReglaEstadoCmMglList != null 
                                        && !cmtReglaEstadoCmMglList.isEmpty() 
                                        && cmtReglaEstadoCmMglList.size() == tecSubListTmp.size()) {
                                    contadorReglasExitosas++;
                                    cmtReglaEstadoCmMglList = cmtReglaEstadoCmMglManager.findReglaByNumeroRegla(numeroRegla);
                                    if (cmtReglaEstadoCmMglList != null && !cmtReglaEstadoCmMglList.isEmpty()) {
                                        reglasExitosas.add(cmtReglaEstadoCmMglList.get(0));                                                                                
                                    }
                                }
                            }
                        }else{
                             return null;
                        }
                    }
                    
                    //ESCENARIOS CUMPLIMIENTO DE REGLAS
                    
                    //Si una única regla se cumple retorna el estado de la regla que debe retornar
                    if (reglasExitosas != null && !reglasExitosas.isEmpty() && reglasExitosas.size() == 1) {
                        return reglasExitosas.get(0).getEstadoCmBasicaId();
                    } else {
                        //Si varias reglas se cumplen retorna null
                        if (reglasExitosas != null && !reglasExitosas.isEmpty() && reglasExitosas.size() > 1) {
                            return null;
                        } else {
                            //Si ninguna regla se cumple retorna null
                            if (reglasExitosas == null || reglasExitosas.isEmpty()) {
                                return null;
                            }
                        }
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
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(CmtUtilidadesCM.class) + "': " + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
        return null;
    }
     
     
      /**
     * Función que copia todo un listado de tecnologias sub eliminando RED FO del listado
     *
     * @author Juan David Hernandez     
     * @param tecSubList     
     * 
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */ 
     public List<CmtTecnologiaSubMgl> eliminarTecnologiaRedFoListado(List<CmtTecnologiaSubMgl> tecSubList) throws ApplicationException{
         List<CmtTecnologiaSubMgl> tecSubListSinRedFO = new ArrayList();
         if (tecSubList != null && !tecSubList.isEmpty()) {
             //recorrido de tecnologias.
             tecSubList.stream().filter((cmtTecnologiaSubMgl) -> (cmtTecnologiaSubMgl.getBasicaIdTecnologias() != null
                     && !cmtTecnologiaSubMgl.getBasicaIdTecnologias().getIdentificadorInternoApp().equals(Constant.RED_FO)))
                     .forEachOrdered((cmtTecnologiaSubMgl) -> {
                         tecSubListSinRedFO.add(cmtTecnologiaSubMgl);
                     }); //se agregan todas menos RED FO.
             
             //retorna la lista pero sin RED FO
             if(tecSubListSinRedFO.isEmpty()){
                 return null;
             }else{
                 return tecSubListSinRedFO;
             }
         } else {
             return null;
         }
     }
     
     
}
