package co.com.telmex.catastro.services.seguridad;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.data.Rol;
import co.com.telmex.catastro.data.Usuario;
import co.com.telmex.catastro.services.util.DireccionUtil;
import co.com.telmex.catastro.services.util.LdapAuthentication;
import com.jlcg.db.AccessData;
import com.jlcg.db.data.DataList;
import com.jlcg.db.data.DataObject;
import com.jlcg.db.exept.ExceptionDB;
import com.jlcg.db.sql.ManageAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * Clase AuditoriaEJB implementa AuditoriaEJBRemote
 *
 * @author José Luis Caicedo
 * @version	1.0
 */
@Stateless(name = "authenticEJB", mappedName = "authenticEJB", description = "autenticacion")
@Remote({AuthenticEJBRemote.class})
public class AuthenticEJB implements AuthenticEJBRemote {

    private static String MESSAGE_NOT_LDAP = "El usuario o la contraseña no son válidos.";
    private static String MESSAGE_NOT_DB = "El usuario no se encuentra registrado en la aplicación ADC";
    private String nombreLog;
    private static final Logger LOGGER = LogManager.getLogger(AuthenticEJB.class);

    /**
     *
     * @throws IOException
     */
    public AuthenticEJB() throws IOException {
        super();
    }

    /**
     *
     * @param login
     * @param pwd
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public Usuario processAuthentic(String login, String pwd) throws ApplicationException {
        Usuario user = new Usuario();
        AccessData adb = null;
        try {
            ///comentar 
            if (pwd.isEmpty()) {
                user.setMessage(MESSAGE_NOT_LDAP);
                return user;
            }
            boolean existUser = serviceAuthenticLDAP(login, pwd);
            if (!existUser) {
                user.setMessage(MESSAGE_NOT_LDAP);
            } else {
                adb = ManageAccess.createAccessData();
                DataObject obj = adb.outDataObjec("aut1", login);
                DireccionUtil.closeConnection(adb);
                if (obj == null) {
                    user.setMessage(MESSAGE_NOT_DB);
                } else {
                    user = new Usuario();
                    user.setUsuId(obj.getBigDecimal("USU_ID"));
                    user.setUsuIdentifica(obj.getBigDecimal("USU_IDENTIFICA"));
                    user.setUsuLogin(obj.getString("USU_LOGIN"));
                    user.setUsuNombre(obj.getString("USU_NOMBRE"));
                    user.setUsuApellidos(obj.getString("USU_APELLIDOS"));
                    user.setUsuEstado(obj.getString("USU_ESTADO"));
                }
            }
            
            return user;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de procesar la autenticación. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param login
     * @param pwd
     * @return
     * @throws Exception
     */
    private boolean serviceAuthenticLDAP(String login, String pwd) throws ApplicationException {
        LdapAuthentication ldap = new LdapAuthentication();
        // para ldap  .....       / 
        return ldap.validarLdap(login, pwd);
    }

    /**
     * Consulta la lista de roles que tiene asginado un usuario
     *
     * @param usuId
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public List<Rol> queryListRol(BigDecimal usuId) throws ApplicationException {
        List<Rol> listRol = null;
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataList list = adb.outDataList("aut2", usuId.toString());
            DireccionUtil.closeConnection(adb);
            if (list != null) {
                listRol = new ArrayList<Rol>();
                for (DataObject obj : list.getList()) {
                    Rol rol = new Rol();
                    BigDecimal id = obj.getBigDecimal("ROL_ID");
                    String name = obj.getString("ROL_NOMBRE");
                    String ldap = obj.getString("ROL_LDAP");
                    rol.setRolId(id);
                    rol.setRolNombre(name);
                    rol.setRolLdap(ldap);
                    listRol.add(rol);
                }
            }
            return listRol;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de consultar el listado de roles. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @param idRol
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    @Override
    public boolean validateRolMenu(String idRol) throws ApplicationException {
        AccessData adb = null;
        try {
            adb = ManageAccess.createAccessData();
            DataObject obj = adb.outDataObjec("aut3", idRol);
            DireccionUtil.closeConnection(adb);
            if (obj == null) {
                return false;
            }
            return true;
        } catch (ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            DireccionUtil.closeConnection(adb);
            throw new ApplicationException("Error al momento de validar el rol en menú. EX000: " + e.getMessage(), e);
        }
    }

    /**
     *
     * @return
     */
    public String getNombreLog() {
        return nombreLog;
    }

    /**
     *
     * @param nombreLog
     */
    public void setNombreLog(String nombreLog) {
        this.nombreLog = nombreLog;
    }
}
