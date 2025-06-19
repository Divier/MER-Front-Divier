package co.com.telmex.catastro.util;

import com.jlcg.db.exept.ExceptionDB;
import com.jlcg.db.init.ResponseInit;
import com.jlcg.db.init.TaskInit;
import com.jlcg.db.sql.ManageAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Clase InitializedFilter Implementa Filter
 *
 * @author Ana María Malambo
 * @version	1.0
 */
public class InitializedFilter implements Filter {

    private static final Logger LOGGER = LogManager.getLogger(InitializedFilter.class);

    /**
     *
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig config) throws ServletException {

        initial();
    }

    /**
     *
     */
    @Override
    public void destroy() {
    }

    /**
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    }

    /**
     *
     */
    public void initial() {
        //inicializar Data sources / properties
        ResponseInit response = null;
        InitializeResource initialize = new InitializeResource("jdbc/DirQA");
        try {
            response = initialize.running(TaskInit.LOAD_DATASOURCE);
            if (response != null) {
                if (response.getDatasource() == null) {
                } else {
                    ManageAccess.init(response.getDatasource());
                    response = initialize.running(TaskInit.TEST_CONNECTION);
                    if (response != null && response.getState()) {
                        response = initialize.running(TaskInit.LOAD_SQL);
                    }
                }
            }
        } catch (ExceptionDB | InterruptedException ex) {
            LOGGER.error("Error en el filtro inicial. EX000 " + ex.getMessage(), ex);
        }
    }
}
