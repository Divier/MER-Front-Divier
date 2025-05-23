/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.telmex.catastro.utilws;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author gabriel.santos
 */
public class DataSourceFactory {

    private static DataSource dst = null;
    private final static ThreadLocal<Connection> threadLocalOracle = new ThreadLocal<Connection>();
    private final static ThreadLocal<Connection> threadLocalAS400 = new ThreadLocal<Connection>();
    public final static String oracle = "jdbc/Oracle";
    public final static String as400 = "jdbc/As400";

    /**
     * @author Ana Maria Malambo Generación de Logs Agregado 24/04/201
     */
    private static final Logger LOGGER = LogManager.getLogger(DataSourceFactory.class);

    /**
     *
     * @return @throws java.sql.SQLException
     * @throws javax.naming.NamingException
     */
    public static Connection getOracleConnection() throws SQLException,
            NamingException {

        Connection conn = threadLocalOracle.get();
        if (conn == null || conn.isClosed()) {
            try {
                Context c = new InitialContext();

                dst = (DataSource) c.lookup(oracle);
                conn = dst.getConnection();
                threadLocalOracle.set(conn);
            } catch (NameNotFoundException ex) { // Hija de NamingException
                /**
                 * @author Ana Maria Malambo Generación de Logs Agregado
                 * 24/04/201
                 */
                LOGGER.error("Error al momento de obtener la conexión de Oracle. EX000: " + ex.getMessage(), ex);
            } catch (SQLException | NamingException ex) {
                /**
                 * @author Ana Maria Malambo Generación de Logs Agregado
                 * 24/04/201
                 */
                LOGGER.error("Error al momento de obtener la conexión de Oracle. EX000: " + ex.getMessage(), ex);
            }
        }
        return conn;
    }

    /**
     *
     * @return @throws java.sql.SQLException
     * @throws javax.naming.NamingException
     */
    public static Connection getAS400Connection() throws SQLException,
            NamingException {
        Connection conn = threadLocalAS400.get();
        if (conn == null || conn.isClosed()) {
            try {
                Context c = new InitialContext();

                dst = (DataSource) c.lookup(as400);
                conn = dst.getConnection();
                threadLocalAS400.set(conn);
            } catch (NameNotFoundException ex) { // Hija de NamingException
                /**
                 * @author Ana Maria Malambo Generación de Logs Agregado
                 * 24/04/201
                 */
                LOGGER.error("Error al momento de obtener la conexión de AS400. EX000: " + ex.getMessage(), ex);
            } catch (SQLException | NamingException ex) {
                /**
                 * @author Ana Maria Malambo Generación de Logs Agregado
                 * 24/04/201
                 */
                LOGGER.error("Error al momento de obtener la conexión de AS400. EX000: " + ex.getMessage(), ex);
            }
        }
        return conn;
    }

    /**
     *
     */
    public static void closeConnection() {
        Connection connOracle = threadLocalOracle.get();
        Connection connAS400 = threadLocalAS400.get();
        threadLocalAS400.set(null);
        threadLocalOracle.set(null);
        try {
            if (connAS400 != null && !connAS400.isClosed()) {
                connAS400.close();
            } else {
            }
        } catch (SQLException e) {
            /**
             * @author Ana Maria Malambo Generación de Logs Agregado 24/04/201
             */
            LOGGER.error("Error SALE_164E", e);
        }
        try {
            if (connOracle != null && !connOracle.isClosed()) {
                connOracle.close();
            } else {
            }
        } catch (SQLException e) {
            /**
             * @author Ana Maria Malambo Generación de Logs Agregado 24/04/201
             */
            LOGGER.error("Error SALE_164F", e);
        }
    }
}
