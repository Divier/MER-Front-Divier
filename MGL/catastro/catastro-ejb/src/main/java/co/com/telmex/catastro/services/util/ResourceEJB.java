package co.com.telmex.catastro.services.util;

import co.com.claro.mgl.businessmanager.address.MglParametrosTrabajosManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.MglParametrosTrabajos;
import co.com.telmex.catastro.services.ws.initialize.Initialized;
import com.jlcg.db.AccessData;
import com.jlcg.db.data.DataObject;
import com.jlcg.db.exept.ExceptionDB;
import com.jlcg.db.sql.ManageAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * Clase ResourceEJB Implementa ResourceEJBRemote
 *
 * @author Deiver Rovira.
 * @version	1.0
 */
@Stateless(name = "resourceEJB", mappedName = "resourceEJB", description = "recursos")
@Remote({ResourceEJBRemote.class})
public class ResourceEJB implements ResourceEJBRemote {

    private static final Logger LOGGER = LogManager.getLogger(ResourceEJB.class);
    private static boolean LOAD = false;

    /**
     * @PostConstruct
     */
    @Override
    public void postConstruct() {
    }

    /**
     *
     * @param acronimo
     * @return
     */
    @Override
    public Parametros queryParametros(String acronimo) {
        Parametros param = null;
        AccessData adb = null;
        try {
            Initialized.getInstance();
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("par2", acronimo);
            if (obj != null) {
                param = new Parametros();
                String acro = obj.getString("ACRONIMO");
                String valor = obj.getString("VALOR");
                String desc = obj.getString("DESCRIPCION");
                param.setAcronimo(acro);
                param.setValor(valor);
                param.setDescripcion(desc);
                }
        } catch (ExceptionDB ex) {
            LOGGER.error("Error al momento de realizar la consulta del parámetro " + acronimo + ". EX000 " + ex.getMessage(), ex);
        } finally {
            DireccionUtil.closeConnection(adb);
        }

        return param;
    }

    public ParametrosTareasProgramadas queryParametrosTareasProgramadas(String nombre, String Servidor) 
            throws ApplicationException {
        ParametrosTareasProgramadas parametrosTareasProgramadas
                = new ParametrosTareasProgramadas();

        MglParametrosTrabajosManager mglParametrosTrabajosManager = new MglParametrosTrabajosManager();
        MglParametrosTrabajos mglParametrosTrabajos
                = mglParametrosTrabajosManager.findMglParametrosTrabajosByName( nombre, Servidor);
        if (mglParametrosTrabajos==null){
            return null;
        }
        parametrosTareasProgramadas.setDescripcion(mglParametrosTrabajos.getDescripcion());
        parametrosTareasProgramadas.setEstado(mglParametrosTrabajos.getEstado());
        parametrosTareasProgramadas.setValor(mglParametrosTrabajos.getValor());
        return parametrosTareasProgramadas;

    }
    /**
     * Realiza la consulta del sobre mgl_parametros_trabajos por nombre
     *
     * @param nombre String nombre del parametro a buscar
     * @return ParametrosTareasProgramadas Primer valor de la lista de valores
     * encontrados.
     * @throws ApplicationException
     */
    @Override
    public ParametrosTareasProgramadas queryParametrosTareasProgramadasByName(String nombre)
            throws ApplicationException {
        ParametrosTareasProgramadas parametrosTareasProgramadas
                = new ParametrosTareasProgramadas();

        MglParametrosTrabajosManager mglParametrosTrabajosManager = new MglParametrosTrabajosManager();
        ArrayList<MglParametrosTrabajos> mglParametrosTrabajosList
                = mglParametrosTrabajosManager.findMglParametrosTrabajosByName(nombre);
        if (mglParametrosTrabajosList == null || mglParametrosTrabajosList.isEmpty()) {
            return null;
        }
        parametrosTareasProgramadas.setDescripcion(mglParametrosTrabajosList.get(0).getDescripcion());
        parametrosTareasProgramadas.setEstado(mglParametrosTrabajosList.get(0).getEstado());
        parametrosTareasProgramadas.setValor(mglParametrosTrabajosList.get(0).getValor());
        parametrosTareasProgramadas.setNombreServidor(mglParametrosTrabajosList.get(0).getNombreServidor());
        
        return parametrosTareasProgramadas;

    }
}
