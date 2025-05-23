/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.telmex.catastro.services.util;

import co.com.claro.mer.utils.enums.ParametrosMerEnum;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;


/**
 * Gestiona la URL del GEO (SITIDATA).
 *
 * @author carlos.villa.ext
 */
@Log4j2
public class UrlProvGeo {

    /**
     * URL del GEO.
     */
    private static String url = "";
    /**
     * Path usado por los servicios de SitiData.
     */
    private static String pathSitidata = "";

    /**
     * Constructor.
     */
    private UrlProvGeo() {
    }

    /**
     * Obtiene la URL del GEO.
     *
     * @return URL GEO.
     */
    public static String getUrlProvGeo() {
        if (StringUtils.isNotBlank(url)) {
            return url;
        }

        ResourceEJBRemote resourceEJBRemote = new ResourceEJB();
        Parametros parametros = null;

        try {
            parametros = resourceEJBRemote.queryParametros(ParametrosMerEnum.IP_WS_GEO.getAcronimo());
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(UrlProvGeo.class) + "': " + ex.getMessage();
            LOGGER.error(msg, ex);
        }

        // Se asigna el valor de la URL.
        url = parametros != null ? parametros.getValor() : "";
        return url;
    }

    /**
     * Obtiene la ruta usada por los servicios de SitiData.
     *
     * @return Path SitiData.
     */
    public static String getPathSitiData() {
        if (StringUtils.isNotBlank(pathSitidata)) {
            return pathSitidata;
        }

        ResourceEJBRemote resourceEJBRemote = new ResourceEJB();
        Parametros parametros = null;

        // Obtener el Path SitiData.
        try {
            parametros = resourceEJBRemote.queryParametros(Constant.PATH_SITIDATA);
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(UrlProvGeo.class) + "': " + ex.getMessage();
            LOGGER.error(msg);
        }

        // Se asigna el valor de la ruta.
        pathSitidata = parametros != null ? parametros.getValor() : "";
        return pathSitidata;
    }
}
