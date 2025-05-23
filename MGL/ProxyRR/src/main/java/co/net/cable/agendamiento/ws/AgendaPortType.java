/**
 * AgendaPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */
package co.net.cable.agendamiento.ws;

public interface AgendaPortType extends java.rmi.Remote {

    public TipoTrabajoConError TTAsignado(java.lang.String ordenTrabajo, java.lang.String programacion) throws java.rmi.RemoteException;

    public AliadoConError aliadoCupos(java.lang.String ordenTrabajo, java.lang.String programacion, java.lang.String tipoTrabajo) throws java.rmi.RemoteException;

    public AgendarConError agendar(java.lang.String ordenTrabajo, java.lang.String programacion, java.lang.String tipoTrabajo, java.lang.String idDia, java.lang.String idHora, java.lang.String usuario, java.lang.String idAliado, java.lang.String direccion) throws java.rmi.RemoteException;
}
