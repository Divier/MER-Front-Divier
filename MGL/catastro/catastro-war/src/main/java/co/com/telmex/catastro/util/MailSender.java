/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.telmex.catastro.util;

/**
 *
 * @author gsantos1
 */
import co.com.claro.mgl.error.ApplicationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailSender {
    /**
    * @author Ana Maria Malambo
    * Generación de Logs
    * Agregado 24/04/201
    */
    private static final Logger logger = LogManager.getLogger(MailSender.class);



    public MailSender() {
    }

    public static boolean send(String hostSmtp, String senderAddress, String toAddress,
            String ccAddress, String bccAddress, String subject,
            boolean isHTMLFormat, StringBuffer body, boolean debug) throws ApplicationException{
        try {
            logger.info(" Metodo mailSender de la clase Mailsender --- Send email : -> " + hostSmtp + " -- " + senderAddress + " -- " + toAddress);
            MimeMultipart multipart = new MimeMultipart();
            Properties properties = new Properties();
            properties.put("mail.smtp.host", hostSmtp);
            Session session = Session.getDefaultInstance(properties, null);
            session.setDebug(debug);
            
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(senderAddress));
            msg.setRecipients(Message.RecipientType.TO, toAddress);
            msg.setRecipients(Message.RecipientType.CC, ccAddress);
            msg.setRecipients(Message.RecipientType.BCC, bccAddress);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            // BODY
            MimeBodyPart mbp = new MimeBodyPart();
            if (isHTMLFormat) {
                mbp.setContent(body.toString(), "text/html");
            } else {
                mbp.setText(body.toString());
            }
            multipart.addBodyPart(mbp);
            msg.setContent(multipart);
            session.getTransport("smtp");
            Transport.send(msg);
            return true;
        } catch (MessagingException e) {
            logger.error("Error en la clase MailSender y el metodo Mailsender");
            throw new ApplicationException("Error en la clase MailSender y el metodo Mailsender : "+e.getMessage(),e.getCause());
        } 
    }
    
    public static boolean enviarCorreo(String servidor,String correoSalida,InternetAddress[] destinatarios,
            InternetAddress[] copias, InternetAddress[] copiaOculta, String asunto , String cuerpo,
            boolean esHTML , boolean conAdjunto,DataSource archivoAdjunto,String nombreArchivoAdjunto, boolean debug) throws ApplicationException{
        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.host", servidor);
            Session session = Session.getDefaultInstance(properties, null);
            session.setDebug(debug);
            
            MimeMessage message = new MimeMessage(session);
            message.setSentDate(new Date());
            message.setFrom(new InternetAddress(correoSalida));
            message.setSubject(asunto);
            if(destinatarios != null && destinatarios.length >0){
                for (int i = 0; i < destinatarios.length; i++) {
                    message.addRecipient(Message.RecipientType.TO, destinatarios[i]);
                }
            }
            if(copias != null && copias.length > 0){
                for (int i = 0; i < destinatarios.length; i++) {
                    message.addRecipient(Message.RecipientType.CC, copias[i]);
                }
            }
            if(copiaOculta != null && copiaOculta.length > 0){
                for (int i = 0; i < copiaOculta.length; i++) {
                    message.addRecipient(Message.RecipientType.BCC, copiaOculta[i]);
                }
            }
            
            Multipart correo = new MimeMultipart(); 
            MimeBodyPart cuerpoCorreo = new MimeBodyPart();
            if(esHTML){
                cuerpoCorreo.setContent(cuerpo, "text/html; charset=utf-8");  
            }else{
                cuerpoCorreo.setText(cuerpo); 
            }
            
            MimeBodyPart adjunto = new MimeBodyPart();
            if(conAdjunto){
                adjunto.setDataHandler(new DataHandler(archivoAdjunto));
                adjunto.setFileName(nombreArchivoAdjunto);
            }
            
            correo.addBodyPart(cuerpoCorreo);
            
            if(conAdjunto){
                correo.addBodyPart(adjunto);  
            }
            message.setContent(correo); 
            session.getTransport("smtp");
            Transport.send(message, message.getAllRecipients());
            return true;
        } catch (MessagingException e) {
            logger.error("Error en la clase MailSender y el metodo enviarCorreo");
            throw new ApplicationException("Error en la clase MailSender y el metodo Mailsender : "+e.getMessage(),e.getCause());
        }
    }
} 
