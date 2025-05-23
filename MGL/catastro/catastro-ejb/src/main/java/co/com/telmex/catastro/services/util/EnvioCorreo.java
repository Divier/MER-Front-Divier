package co.com.telmex.catastro.services.util;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.MailSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.Properties;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Clase EnvioCorreo
 *
 * @author Nataly Orozco Torres
 * @version	1.0
 */
public class EnvioCorreo {
    
    private static final Logger LOGGER = LogManager.getLogger(EnvioCorreo.class);
    
    
    /**
     * Constructor.
     */
    public EnvioCorreo() {
    }



    /**
     *
     * @param emails Direcciones de email para destinatarios.
     * @param asunto Asunto del mensaje.
     * @param mensaje Cuerpo del mensaje.
     * @throws ApplicationException
     */
    public void envio(String emails, String asunto, String mensaje) throws ApplicationException {
        MailSender.send(emails, asunto, mensaje);
    }
    

    public static boolean sendMailWithAttach(String hostSmtp,
            String senderAddress,
            String toAddress, String ccAddress, String bccAddress,
            String subject,
            boolean isHTMLFormat, StringBuffer body, boolean debug,
            DataSource source) throws ApplicationException {
        try {

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
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(mbp);

            msg.setContent(multipart);
            session.getTransport("smtp");
            Transport.send(msg);
            return true;
        } catch (MessagingException e) {
            LOGGER.error(e);
            throw new ApplicationException(e.getMessage());
        }
    }
}
