/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mer.utils;

import java.util.UUID;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

/**
 * Descripcion: Clase encargada de almacenar el tiempo inicial de la transaccion
 * y el id de la transaccion
 *
 * @author Johan Gómez
 * @version 1.0.
 *
 */
public class TransactionParameters {
    
    private Long time;
    private Long uuid;

    /**
     * Metodo encargado de registrar en el log el tiempo de procesamiento de la
     * solicitud. Adicionalmente genera la variable de UUID mostrada en el log.
     */
    protected TransactionParameters() {
        this.time = System.currentTimeMillis();
        this.uuid = UUID.randomUUID().getMostSignificantBits();
    }

    /**
     * @return el tiempo inicial de la transaccion
     */
    public Long getTime() {
        return time;
    }

    /**
     * @return el identificador de la transaccion
     */
    public Long getUuid() {
        return uuid;
    }

    /**
     * Metodo encargado de retornar el tiempo inicial de la transaccion junto
     * con el id de transaccion. Configura el log para registrar el id de
     * transaccion
     *
     * @return Objeto con los parametros de tiempo inicial de la transaccion y
     * id de transaccion
     */
    public static TransactionParameters iniciarTransaccion() {
        TransactionParameters param = new TransactionParameters();
        ThreadContext.put("UUID", Long.toString(param.getUuid()));
        return param;
    }

    /**
     * Metodo encargado de generar en el Log el tiempo de procesamiento de la
     * transaccion. Adicionalmente limpia la variable de UUID mostrada en el
     * log.
     *
     * @param param Parametros calculados al inicio de la transaccion
     * @param logger Logger de la aplicacion para imprimir el tiempo total de la
     * transaccion
     */
    public static void cerrarTransaccion(TransactionParameters param, Logger logger) {
        logger.info("Tiempo de procesamiento (ms) " + (System.currentTimeMillis() - param.getTime()));
        ThreadContext.clearAll();
    }
}

