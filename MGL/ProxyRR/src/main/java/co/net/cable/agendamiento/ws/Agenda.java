/**
 * Agenda.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */
package co.net.cable.agendamiento.ws;

public interface Agenda extends javax.xml.rpc.Service {

    public java.lang.String getAgendaPortAddress();

    public AgendaPortType getAgendaPort() throws javax.xml.rpc.ServiceException;

    public AgendaPortType getAgendaPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
