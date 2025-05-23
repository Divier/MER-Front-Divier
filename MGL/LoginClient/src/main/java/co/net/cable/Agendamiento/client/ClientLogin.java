package co.net.cable.Agendamiento.client;

import co.net.cable.Agendamiento.permsUsers.Permisos;
import co.net.cable.Agendamiento.permsUsers.PermisosLocator;
import co.net.cable.Agendamiento.permsUsers.PermisosPortType;
import co.net.cable.Agendamiento.permsUsers.PermisosUsuario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;

public class ClientLogin {

    private ClientLogin() {}

    private static final Logger LOGGER = LogManager.getLogger(ClientLogin.class);

    public static String obtenerDataRoles(String usuario, String verificacion, String url) throws RemoteException, ServiceException {
        try {
            Permisos permisos = new PermisosLocator(url);
            PermisosPortType permisosPortType = permisos.getPermisosPort();
            PermisosUsuario permisosUsuario = permisosPortType.consultaPermisos(usuario, verificacion);
            String permisosResult = permisosUsuario.getPermisos();
            LOGGER.error("***************************************");
            LOGGER.error("Permisos del Usuario: ".concat(permisosResult));
            LOGGER.error("***************************************");
            return permisosResult;
        } catch (RemoteException | ServiceException e) {
            LOGGER.error("Error en ClientLogin. EX000 " + e.getMessage(), e);
            throw e;
        }
    }
}
