/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.telmex.catastro.services.ws.initialize;

import com.jlcg.db.exept.ExceptionDB;
import com.jlcg.db.init.ResponseInit;
import com.jlcg.db.init.TaskInit;
import com.jlcg.db.sql.BuildSQL;
import com.jlcg.db.sql.ManageAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author user
 */
public class TaskLoad implements Runnable, TaskInit {

    private int operation = 0;
    private ResponseInit response = null;
    private Thread thread = null;
    private String resource = null;
    private static final Logger LOGGER = LogManager.getLogger(TaskLoad.class);

    /**
     *
     * @param operation
     * @param resource
     */
    public TaskLoad(int operation, String resource) {
        this.operation = operation;
        this.resource = resource;
        thread = new Thread(this);
    }

    /**
     *
     */
    public void init() {
        thread.start();
    }

    /**
     *
     */
    @Override
    public void run() {
        if (LOAD_DATASOURCE == operation) {
            try {
                response = loadDataSource();
            } catch (NamingException ex) {
                LOGGER.error("Error al momento de ejecutar el TaskLoad. EX000: " + ex.getMessage(), ex);
            }
        } else if (LOAD_PROPERTIES == operation) {
            try {
                response = loadProperties();
            } catch (IOException ex) {
                LOGGER.error("Error al momento de ejecutar el TaskLoad. EX000: " + ex.getMessage(), ex);
            }
        } else if (TEST_CONNECTION == operation) {
            try {
                response = testConnection();
            } catch (ExceptionDB ex) {
                LOGGER.error("Error al momento de ejecutar el TaskLoad. EX000: " + ex.getMessage(), ex);
            }
        } else if (LOAD_SQL == operation) {
            try {
                response = loadSQL();
            } catch (ExceptionDB ex) {
                LOGGER.error("Error al momento de ejecutar el TaskLoad. EX000: " + ex.getMessage(), ex);
            }
        } else {
            response = null;
        }
    }

    /**
     *
     * @return @throws NamingException
     */
    @Override
    public ResponseInit loadDataSource() throws NamingException {
        ResponseInit response = new ResponseInit();
        DataSource datasource = null;
        InitialContext context = new InitialContext();
        datasource = (javax.sql.DataSource) context.lookup(resource);
        response.setDatasource(datasource);
        return response;
    }

    /**
     *
     * @return @throws IOException
     */
    @Override
    public ResponseInit loadProperties() throws IOException {
        ResponseInit response = new ResponseInit();
        Properties properties = new Properties();
        properties.load(new FileInputStream(resource));
        response.setProperties(properties);
        return response;
    }

    /**
     *
     * @return @throws ExceptionDB
     */
    @Override
    public ResponseInit testConnection() throws ExceptionDB {
        ResponseInit response = new ResponseInit();
        boolean state = ManageAccess.ping();
        response.setState(state);
        return response;
    }

    /**
     *
     * @return @throws ExceptionDB
     */
    @Override
    public ResponseInit loadSQL() throws ExceptionDB {
        ResponseInit response = new ResponseInit();
        int count = BuildSQL.load();
        response.setCount(count);
        return response;
    }

    /*
     * access atribute
     */

    /**
     *
     * @return
     */
    public ResponseInit getResponse() {
        return response;
    }

    /**
     *
     * @param response
     */
    public void setResponse(ResponseInit response) {
        this.response = response;
    }

    /**
     *
     * @return
     */
    public String getResource() {
        return resource;
    }

    /**
     *
     * @param resource
     */
    public void setResource(String resource) {
        this.resource = resource;
    }

    /**
     *
     * @return
     */
    public Thread getThread() {
        return thread;
    }

    /**
     *
     * @param thread
     */
    public void setThread(Thread thread) {
        this.thread = thread;
    }
}
