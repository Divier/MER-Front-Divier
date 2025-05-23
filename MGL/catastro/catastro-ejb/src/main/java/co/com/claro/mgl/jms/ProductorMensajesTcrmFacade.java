/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jms;

import co.com.claro.ejb.mgl.ws.client.updateCaseStatus.UpdateCaseStatusType;
import co.com.claro.mgl.error.ApplicationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Hashtable;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author valbuenayf
 */
@Stateless(name = "productorMensajesTcrm")
public class ProductorMensajesTcrmFacade {

    private static final Logger LOGGER = LogManager.getLogger(ProductorMensajesTcrmFacade.class);
    private static final String CONNECTION_FACTORY = "jms/ConnectionFactoryMgl";
    private static final String REQUEST_QUEUE = "jms/QueueMgl";
    private static final String INITIAL_CTX = "weblogic.jndi.WLInitialContextFactory";
    private String urlJms;
    Session session = null;
    Connection connection = null;
    ConnectionFactory connFactory = null;
    MessageProducer msgProducer = null;
    Destination dest = null;
    TextMessage outgoingMsg = null;
    Message incomingMsg = null;

    /**
     * valbuenayf metodo para crear mensajes en la cola, se debe asignar el
     * valor de la url a la vairable urlJms antes usar este metodo, nombre del
     * parametro URL_TCRM_JMS
     *
     * @param caseId
     * @param caseReason
     * @param caseIdMgl
     * @param caseStatus
     * @param caseCompleted
     * @throws ApplicationException
     */
    public void crearMensaje(String caseId, String caseReason, String caseIdMgl, String caseStatus, String caseCompleted) throws ApplicationException {


        if (urlJms == null || urlJms.trim().isEmpty()) {
            throw new ApplicationException(" el parametro URL_TCRM_JMS de base de datos es obligatorio ");
        }

        StringBuilder msn = new StringBuilder();

        if (caseId == null || caseId.trim().isEmpty()) {
            msn.append(" el campo caseId es obligatorio ");
        }
        if (caseReason == null || caseReason.trim().isEmpty()) {

            msn.append(" el campo caseReason es obligatorio ");
        }
        if (caseIdMgl == null || caseIdMgl.trim().isEmpty()) {
            msn.append(" el campo caseIdMgl es obligatorio ");
        }
        if (caseStatus == null || caseStatus.trim().isEmpty()) {
            msn.append(" el campo caseStatus es obligatorio ");
        }
        if (msn.toString() != null && !msn.toString().trim().isEmpty()) {
            throw new ApplicationException(msn.toString());
        }

        UpdateCaseStatusType mensaje = new UpdateCaseStatusType();

        mensaje.setCaseId(caseId);
        mensaje.setCaseReason(caseReason);
        mensaje.setCaseIdMGL(caseIdMgl);
        mensaje.setCaseStatus(caseStatus);
        mensaje.setCaseCompleted(caseCompleted);

        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CTX);
        env.put(Context.PROVIDER_URL, urlJms);


        try {
            Context initialContext;
            initialContext = new InitialContext(env);
            connFactory = (ConnectionFactory) initialContext.lookup(CONNECTION_FACTORY);
            dest = (Destination) initialContext.lookup(REQUEST_QUEUE);
            connection = connFactory.createConnection();
            connection.start();
            session = connection.createSession(false, 1);
            msgProducer = session.createProducer(dest);
            ObjectMessage objectMessage = session.createObjectMessage();
            objectMessage.setObject(mensaje);
            msgProducer.send(objectMessage);

        } catch (NamingException ex) {
            LOGGER.error("Error en crearMensaje de ProductorMensajesTcrm " + ex);
        } catch (JMSException ex) {
            LOGGER.error("Error en crearMensaje de ProductorMensajesTcrm " + ex);
        } catch (Exception e) {
            LOGGER.error("Error en crearMensaje de ProductorMensajesTcrm " + e);
        } finally {
            outgoingMsg = null;
            incomingMsg = null;
            try {
                msgProducer.close();
                msgProducer = null;
                session.close();
                session = null;
                connection.close();
                connection = null;
            } catch (JMSException e2) {
                LOGGER.error("Error en crearMensaje de ProductorMensajesTcrm " + e2);
                try {
                    throw e2;
                } catch (JMSException e) {
                    LOGGER.error("Error en crearMensaje. " +e.getMessage(), e);  
                }
            }
        }
    }

    /**
     * @param urlJms the urlJms to set
     */
    public void setUrlJms(String urlJms) {
        this.urlJms = urlJms;
    }
}
