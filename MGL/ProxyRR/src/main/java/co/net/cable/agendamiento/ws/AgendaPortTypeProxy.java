package co.net.cable.agendamiento.ws;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AgendaPortTypeProxy implements AgendaPortType {
    
    private static final Logger LOGGER = LogManager.getLogger(AgendaPortTypeProxy.class);

    private String _endpoint = null;
    private AgendaPortType agendaPortType = null;

    public AgendaPortTypeProxy() {
        _initAgendaPortTypeProxy();
    }

    public AgendaPortTypeProxy(String endpoint) {
        _endpoint = endpoint;
        _initAgendaPortTypeProxy();
    }

    private void _initAgendaPortTypeProxy() {
        try {
            agendaPortType = (new AgendaLocator()).getAgendaPort();
            if (agendaPortType != null) {
                if (_endpoint != null) {
                    ((javax.xml.rpc.Stub) agendaPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
                } else {
                    _endpoint = (String) ((javax.xml.rpc.Stub) agendaPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
                }
            }

        } catch (javax.xml.rpc.ServiceException e) {
            LOGGER.error("Error en _initAgendaPortTypeProxy. " +e.getMessage(), e); 
        }
    }

    public String getEndpoint() {
        return _endpoint;
    }

    public void setEndpoint(String endpoint) {
        _endpoint = endpoint;
        if (agendaPortType != null) {
            ((javax.xml.rpc.Stub) agendaPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        }

    }

    public AgendaPortType getAgendaPortType() {
        if (agendaPortType == null) {
            _initAgendaPortTypeProxy();
        }
        return agendaPortType;
    }

    @Override
    public TipoTrabajoConError TTAsignado(java.lang.String ordenTrabajo, java.lang.String programacion) throws java.rmi.RemoteException {
        if (agendaPortType == null) {
            _initAgendaPortTypeProxy();
        }
        return agendaPortType.TTAsignado(ordenTrabajo, programacion);
    }

    @Override
    public AliadoConError aliadoCupos(java.lang.String ordenTrabajo, java.lang.String programacion, java.lang.String tipoTrabajo) throws java.rmi.RemoteException {
        if (agendaPortType == null) {
            _initAgendaPortTypeProxy();
        }
        return agendaPortType.aliadoCupos(ordenTrabajo, programacion, tipoTrabajo);
    }

    @Override
    public AgendarConError agendar(java.lang.String ordenTrabajo, java.lang.String programacion, java.lang.String tipoTrabajo, java.lang.String idDia, java.lang.String idHora, java.lang.String usuario, java.lang.String idAliado, java.lang.String direccion) throws java.rmi.RemoteException {
        if (agendaPortType == null) {
            _initAgendaPortTypeProxy();
        }
        return agendaPortType.agendar(ordenTrabajo, programacion, tipoTrabajo, idDia, idHora, usuario, idAliado, direccion);
    }

}
