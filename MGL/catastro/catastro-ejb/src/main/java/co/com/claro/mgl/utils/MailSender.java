package co.com.claro.mgl.utils;

/**
 *
 * @author gsantos1
 */
import co.com.claro.mer.utils.enums.ParametrosMerEnum;
import co.com.claro.mgl.dtos.MailSenderDTO;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.telmex.catastro.services.util.Parametros;
import co.com.telmex.catastro.services.util.ResourceEJB;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Function;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


/**
 * Clase utilizada para el env&iacute;o de correos electr&oacute;nicos.
 * 
 * @author Camilo Miranda (<i>mirandaca</i>)
 * @version 20190524
 */
public class MailSender {
    /**
    * @author Ana Maria Malambo
    * Generación de Logs
    * Agregado 24/04/201
    */
    private static final Logger LOGGER = LogManager.getLogger(MailSender.class);

    private MailSender() {
        //Impedir instancias invalidas
    }

    /**
     * Realiza el envío de un correo.
     *
     * @param mailSenderDTO DTO con la información del correo a enviar.
     * @return Correo enviado?
     * @throws ApplicationException  Excepción personalizada de la aplicación.
     */
    public static boolean send(MailSenderDTO mailSenderDTO) throws ApplicationException {
        try {
            if (mailSenderDTO == null) {
                throw new ApplicationException("No ha configurado la información del mail.");
            }

            LOGGER.debug(" Metodo mailSender de la clase Mailsender --- Send email : -> {} -- {} -- {}",
                    mailSenderDTO.getHostSmtp(), mailSenderDTO.getSenderAddress(), mailSenderDTO.getToAddress());
                MimeMultipart multipart = new MimeMultipart();
                Properties properties = new Properties();
                properties.put("mail.smtp.host", mailSenderDTO.getHostSmtp());

                if (mailSenderDTO.isUseAuth()) {
                    /* Cuenta desde la cual se va a enviar el correo (<i>MAIL FROM</i>). */
                    properties.put("mail.smtp.user", mailSenderDTO.getSenderAddress());
                    /* Password de la cuenta desde la cual se va a enviar el correo. */
                    properties.put("mail.smtp.password", mailSenderDTO.getSenderPassword());
                    /* Flag que determina si requiere Autenticaci&oacute;n. */
                    properties.put("mail.smtp.auth", mailSenderDTO.isUseAuth());
                    /* Flag para activar el TLS, para conectar de manera segura al servidor SMTP. */
                    properties.put("mail.smtp.starttls.enable", mailSenderDTO.isUseTLS());
                    /* Puerto SMTP seguro. */
                    properties.put("mail.smtp.port", mailSenderDTO.getSmtpPort());
                }

                Session session = Session.getDefaultInstance(properties, null);
                session.setDebug(mailSenderDTO.isDebug());

                MimeMessage msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress(mailSenderDTO.getSenderAddress()));
                
                if (mailSenderDTO.getToAddress() != null && !mailSenderDTO.getToAddress().isEmpty()) {
                    msg.setRecipients(Message.RecipientType.TO, 
                            InternetAddress.parse(mailSenderDTO.getToAddress()));
                }
                if (mailSenderDTO.getCcAddress() != null && !mailSenderDTO.getCcAddress().isEmpty()) { 
                    msg.setRecipients(Message.RecipientType.CC, 
                            InternetAddress.parse(mailSenderDTO.getCcAddress()));
                }
                if (mailSenderDTO.getBccAddress() != null && !mailSenderDTO.getBccAddress().isEmpty()) {
                    msg.setRecipients(Message.RecipientType.BCC, 
                            InternetAddress.parse(mailSenderDTO.getBccAddress()));
                }
                
                msg.setSubject(mailSenderDTO.getSubject());
                msg.setSentDate(new Date());
                // BODY
                MimeBodyPart mbp = new MimeBodyPart();
                if (mailSenderDTO.getBody() != null) {
                    if (mailSenderDTO.isHtmlFormat()) {
                        mbp.setContent(mailSenderDTO.getBody().toString(), "text/html");
                    } else {
                        mbp.setText(mailSenderDTO.getBody().toString());
                    }
                }
                multipart.addBodyPart(mbp);
                msg.setContent(multipart);
                session.getTransport("smtp");
                
                if (mailSenderDTO.isUseAuth()) {
                    // Cuando se requiere autenticación:
                    Transport transport = session.getTransport("smtp");
                    transport.connect(mailSenderDTO.getHostSmtp(), 
                            mailSenderDTO.getSenderAddress(), 
                            mailSenderDTO.getSenderPassword());
                    transport.sendMessage(msg, msg.getAllRecipients());
                } else {
                    // Cuando No se requiere autenticación:
                    Transport.send(msg);
                }
                
                return true;
        } catch (MessagingException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(MailSender.class)+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg,e);
        } 
    }

    /**
     * Obtiene la informaci&oacute;n requerida para el env&iacute;o de correos electr&oacute;nicos.
     * 
     * @param mailToAddress Direcci&oacute;n de correo del <b>DESTINATARIO</b>.
     * @param mailSubject <b>ASUNTO</b> del mensaje.
     * @param mailBody <b>CUERPO</b> del mensaje.
     * @return Objeto con la informaci&oacute;n del correo a enviar.
     * @throws ApplicationException 
     */
    public static MailSenderDTO getMailSenderDTO(String mailToAddress, String mailSubject, String mailBody) 
        throws ApplicationException {
        StringBuffer mailBodyBuffer = mailBody != null ? new StringBuffer(mailBody) : null;
        return ( getMailSenderDTO(null, mailToAddress, null, null, mailSubject, mailBodyBuffer) );
    }

    /**
     * Obtiene la informaci&oacute;n requerida para el env&iacute;o de correos electr&oacute;nicos.
     * 
     * @param mailToAddress Direcci&oacute;n de correo del <b>DESTINATARIO</b>.
     * @param mailSubject <b>ASUNTO</b> del mensaje.
     * @param mailBody <b>CUERPO</b> del mensaje.
     * @return Objeto con la informaci&oacute;n del correo a enviar.
     * @throws ApplicationException 
     */
    public static MailSenderDTO getMailSenderDTO(String mailToAddress, String mailSubject, StringBuffer mailBody) 
        throws ApplicationException {
        return ( getMailSenderDTO(null, mailToAddress, null, null, mailSubject, mailBody) );
    }

    /**
     * Obtiene la informaci&oacute;n requerida para el env&iacute;o de correos electr&oacute;nicos.
     * @param parametrosMglFacadeLocal Interfaz local para par&aacute;metros MGL.
     * @param mailToAddress Direcci&oacute;n de correo del <b>DESTINATARIO</b>.
     * @param ccAddress Direcci&oacute;n de correo de <b>CARBON COPY</b>.
     * @param bccAddress Direcci&oacute;n de correo de <b>BLIND CARBON COPY</b>
     * @param mailSubject <b>ASUNTO</b> del mensaje.
     * @param mailBody <b>CUERPO</b> del mensaje.
     * @return Objeto con la informaci&oacute;n del correo a enviar.
     * @throws ApplicationException  Excepción personalizada de la aplicación.
     */
    private static MailSenderDTO getMailSenderDTO(ParametrosMglFacadeLocal parametrosMglFacadeLocal, 
            String mailToAddress, String ccAddress, String bccAddress, String mailSubject, StringBuffer mailBody) 
        throws ApplicationException {
        
        MailSenderDTO mailSenderDTO;
        String correoServer;
        String mailFrom;
        String authFlag;
        String tlsFlag;
        String mailFromPassword;
        String smtpPort;

        if (mailToAddress == null) {
            throw new ApplicationException("Falta especificar la dirección del remitente del correo.");
        }

            if (parametrosMglFacadeLocal != null) {
                Function<ParametrosMgl, String> obtenerValorParametro = paramtroMgl -> Optional.ofNullable(paramtroMgl)
                        .map(ParametrosMgl::getParValor).orElse(null);
                ParametrosMgl parametroMgl = parametrosMglFacadeLocal.findByAcronimoName(
                        ParametrosMerEnum.MAIL_SMTPSERVER.getAcronimo());
                correoServer = obtenerValorParametro.apply(parametroMgl);
                parametroMgl = parametrosMglFacadeLocal.findByAcronimoName(
                        ParametrosMerEnum.MAIL_FROM.getAcronimo());
                mailFrom = obtenerValorParametro.apply(parametroMgl);
                
                //////////////////////////////////
                // Parámetros de autenticación. // 
                //////////////////////////////////
                parametroMgl = parametrosMglFacadeLocal.findByAcronimoName(
                        ParametrosMerEnum.MAIL_AUTH_FLAG.getAcronimo());
                authFlag = obtenerValorParametro.apply(parametroMgl);
                parametroMgl = parametrosMglFacadeLocal.findByAcronimoName(
                        ParametrosMerEnum.MAIL_TLS_FLAG.getAcronimo());
                tlsFlag = obtenerValorParametro.apply(parametroMgl);
                parametroMgl = parametrosMglFacadeLocal.findByAcronimoName(
                        ParametrosMerEnum.MAIL_FROM_PASSWORD.getAcronimo());
                mailFromPassword = obtenerValorParametro.apply(parametroMgl);
                parametroMgl = parametrosMglFacadeLocal.findByAcronimoName(
                        ParametrosMerEnum.MAIL_SMTPPORT.getAcronimo());
                smtpPort = obtenerValorParametro.apply(parametroMgl);
            
            } else {
                ResourceEJB recursos = new ResourceEJB();
                Function<Parametros, String> obtenerValor = parametro -> Optional.ofNullable(parametro).map(Parametros::getValor).orElse(null);
                Parametros param = recursos.queryParametros(ParametrosMerEnum.MAIL_SMTPSERVER.getAcronimo());
                correoServer = obtenerValor.apply(param);
                param = recursos.queryParametros(ParametrosMerEnum.MAIL_FROM.getAcronimo());
                mailFrom = obtenerValor.apply(param);
                // Parámetros de autenticación.
                param = recursos.queryParametros(ParametrosMerEnum.MAIL_AUTH_FLAG.getAcronimo());
                authFlag = obtenerValor.apply(param);
                param = recursos.queryParametros(ParametrosMerEnum.MAIL_TLS_FLAG.getAcronimo());
                tlsFlag = obtenerValor.apply(param);
                param = recursos.queryParametros(ParametrosMerEnum.MAIL_FROM_PASSWORD.getAcronimo());
                mailFromPassword = obtenerValor.apply(param);
                param = recursos.queryParametros(ParametrosMerEnum.MAIL_SMTPPORT.getAcronimo());
                smtpPort = obtenerValor.apply(param);
            }

            if (StringUtils.isBlank(correoServer)){
                throw new ApplicationException("Falta especificar el servidor de envío de correos.");
            }

            if (StringUtils.isBlank(mailFrom)){
                throw new ApplicationException("Falta especificar el remitente del correo.");
            }

                    mailSenderDTO = new MailSenderDTO();
                    mailSenderDTO.setHostSmtp(correoServer);
                    mailSenderDTO.setSenderAddress(mailFrom);
                    mailSenderDTO.setToAddress(mailToAddress);
                    mailSenderDTO.setCcAddress(ccAddress != null ? ccAddress : "");
                    mailSenderDTO.setBccAddress(bccAddress != null ? bccAddress : "");
                    mailSenderDTO.setSubject(mailSubject);
                    mailSenderDTO.setHtmlFormat(true);
                    mailSenderDTO.setBody(mailBody);
                    mailSenderDTO.setDebug(true);

                    if (StringUtils.isNotBlank(authFlag)) {
                        mailSenderDTO.setUseAuth(Boolean.valueOf(authFlag));
                    }

                    if (StringUtils.isNotBlank(tlsFlag)) {
                        mailSenderDTO.setUseTLS(Boolean.valueOf(tlsFlag));
                    }

                    if (StringUtils.isNotBlank(mailFromPassword)) {
                        mailSenderDTO.setSenderPassword(mailFromPassword);
                    }

                    if (StringUtils.isNotBlank(smtpPort)) {
                        mailSenderDTO.setSmtpPort(smtpPort);
                    }

        return (mailSenderDTO);
    }

    /**
     * Realiza el env&iacute;o de un correo, a trav&eacute;s de la configuraci&oacute;n de base de datos.
     * 
     * @param mailToAddress Direcci&oacute;n de correo del <b>DESTINATARIO</b>.
     * @param mailSubject <b>ASUNTO</b> del mensaje.
     * @param mailBody <b>CUERPO</b> del mensaje.
     * @return Correo enviado?
     * @throws ApplicationException 
     */
    public static boolean send(String mailToAddress, String mailSubject, String mailBody) 
        throws ApplicationException {
        StringBuffer mailBodyBuffer = mailBody != null ? new StringBuffer(mailBody) : null;
        return ( send(mailToAddress, mailSubject, mailBodyBuffer) );
    }

    /**
     * Realiza el env&iacute;o de un correo, a trav&eacute;s de la configuraci&oacute;n de base de datos.
     * 
     * @param mailToAddress Direcci&oacute;n de correo del <b>DESTINATARIO</b>.
     * @param mailSubject <b>ASUNTO</b> del mensaje.
     * @param mailBody <b>CUERPO</b> del mensaje.
     * @return Correo enviado?
     * @throws ApplicationException 
     */
    public static boolean send(String mailToAddress, String mailSubject, StringBuffer mailBody) 
        throws ApplicationException {
        return ( send(null, mailToAddress, mailSubject, mailBody));
    }

    /**
     * Realiza el env&iacute;o de un correo, a trav&eacute;s de la configuraci&oacute;n de base de datos.
     * 
     * @param mailToAddress Direcci&oacute;n de correo del <b>DESTINATARIO</b>.
     * @param ccAddress Direcci&oacute;n de correo de <b>CARBON COPY</b>.
     * @param bccAddress Direcci&oacute;n de correo de <b>BLIND CARBON COPY</b>
     * @param mailSubject <b>ASUNTO</b> del mensaje.
     * @param mailBody <b>CUERPO</b> del mensaje.
     * @return Correo enviado?
     * @throws ApplicationException 
     */
    public static boolean send(String mailToAddress, String ccAddress, String bccAddress, String mailSubject, String mailBody) 
        throws ApplicationException {
        StringBuffer mailBodyBuffer = mailBody != null ? new StringBuffer(mailBody) : null;
        return ( send(mailToAddress, mailSubject, ccAddress, bccAddress, mailBodyBuffer) );
    }
    
    /**
     * Realiza el env&iacute;o de un correo, a trav&eacute;s de la configuraci&oacute;n de base de datos.
     * 
     * @param mailToAddress Direcci&oacute;n de correo del <b>DESTINATARIO</b>.
     * @param ccAddress Direcci&oacute;n de correo de <b>CARBON COPY</b>.
     * @param bccAddress Direcci&oacute;n de correo de <b>BLIND CARBON COPY</b>
     * @param mailSubject <b>ASUNTO</b> del mensaje.
     * @param mailBody <b>CUERPO</b> del mensaje.
     * @return Correo enviado?
     * @throws ApplicationException 
     */
    public static boolean send(String mailToAddress, String ccAddress, String bccAddress, String mailSubject, StringBuffer mailBody) 
        throws ApplicationException {
        return ( send(null, mailToAddress, ccAddress, bccAddress, mailSubject, mailBody));
    }

    /**
     * Realiza el env&iacute;o de un correo, a trav&eacute;s de la configuraci&oacute;n de base de datos.
     * 
     * @param parametrosMglFacadeLocal Interfaz local para par&aacute;metros MGL.
     * @param mailToAddress Direcci&oacute;n de correo del <b>DESTINATARIO</b>.
     * @param mailSubject <b>ASUNTO</b> del mensaje.
     * @param mailBody <b>CUERPO</b> del mensaje.
     * @return Correo enviado?
     * @throws ApplicationException 
     */
    public static boolean send(ParametrosMglFacadeLocal parametrosMglFacadeLocal, 
            String mailToAddress, String mailSubject, StringBuffer mailBody) 
        throws ApplicationException {
        
        MailSenderDTO mailSenderDTO = 
                getMailSenderDTO(parametrosMglFacadeLocal, mailToAddress, null, null, mailSubject, mailBody);
        return ( send(mailSenderDTO) );
    }

    /**
     * Realiza el env&iacute;o de un correo, a trav&eacute;s de la configuraci&oacute;n de base de datos.
     * 
     * @param parametrosMglFacadeLocal Interfaz local para par&aacute;metros MGL.
     * @param mailToAddress Direcci&oacute;n de correo del <b>DESTINATARIO</b>.
     * @param ccAddress Direcci&oacute;n de correo de <b>CARBON COPY</b>.
     * @param bccAddress Direcci&oacute;n de correo de <b>BLIND CARBON COPY</b>
     * @param mailSubject <b>ASUNTO</b> del mensaje.
     * @param mailBody <b>CUERPO</b> del mensaje.
     * @return Correo enviado?
     * @throws ApplicationException 
     */
    private static boolean send(ParametrosMglFacadeLocal parametrosMglFacadeLocal, 
            String mailToAddress, String ccAddress, String bccAddress, String mailSubject, StringBuffer mailBody) 
        throws ApplicationException {
        
        MailSenderDTO mailSenderDTO = 
                getMailSenderDTO(parametrosMglFacadeLocal, mailToAddress, ccAddress, bccAddress, mailSubject, mailBody);
        return ( send(mailSenderDTO) );
    }
} 
