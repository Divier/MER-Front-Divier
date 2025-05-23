package co.net.cable.Agendamiento.permsUsers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PermisosPortTypeProxy implements co.net.cable.Agendamiento.permsUsers.PermisosPortType {
    
    private static final Logger LOGGER = LogManager.getLogger(PermisosPortTypeProxy.class);
    private String _endpoint = null;
    private co.net.cable.Agendamiento.permsUsers.PermisosPortType permisosPortType = null;
  
  public PermisosPortTypeProxy() {
    _initPermisosPortTypeProxy();
  }
  
  public PermisosPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initPermisosPortTypeProxy();
  }
  
  private void _initPermisosPortTypeProxy() {
    try {
      permisosPortType = (new co.net.cable.Agendamiento.permsUsers.PermisosLocator()).getPermisosPort();
      if (permisosPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)permisosPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)permisosPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {
        LOGGER.error("Se produjo un error al momento de iniciar los permisos: " + serviceException.getMessage(), serviceException);
    }
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (permisosPortType != null)
      ((javax.xml.rpc.Stub)permisosPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public co.net.cable.Agendamiento.permsUsers.PermisosPortType getPermisosPortType() {
    if (permisosPortType == null)
      _initPermisosPortTypeProxy();
    return permisosPortType;
  }
  
  @Override
  public co.net.cable.Agendamiento.permsUsers.PermisosUsuario consultaPermisos(java.lang.String usuario, java.lang.String verificacion) throws java.rmi.RemoteException{
    if (permisosPortType == null)
      _initPermisosPortTypeProxy();
    return permisosPortType.consultaPermisos(usuario, verificacion);
  }
  
  
}