package co.com.claro.mgl.dtos;


/**
 * Data Transfer Object para el manejo de env&iacute;o de correos.
 *
 * @author Lenis C&aacute;rdenas (<i>cardenaslb</i>)
 */
public class MailSenderDTO {
    
    /** Nombre del <b>HOST SMTP</b>. */
    private String hostSmtp;
    /** Direcci&oacute;n de correo del <b>REMITENTE</b>. */
    private String senderAddress;
    /** Direcciones de correo para <b>DESTINATARIO</b> (separados por <i>coma</i>). */
    private String toAddress;
    /** Direcciones de correo de <b>CARBON COPY</b> (separados por <i>coma</i>). */
    private String ccAddress;
    /** Direcciones de correo de <b>BLIND CARBON COPY</b> (separados por <i>coma</i>). */
    private String bccAddress; 
    /** <b>ASUNTO</b> del mensaje. */
    private String subject;
    /** Flag que determina si el mensaje tiene formato <b>HTML</b>. */
    private boolean htmlFormat;
    /** <b>CUERPO</b> del mensaje. */
    private StringBuffer body;
    /** Flag que determina si se mostrar&aacute;n mensajes de <b>DEBUG</b>. */
    private boolean debug;
    /** Flag que determina si se realiza uso de autenticaci&oacute;n. */
    private boolean useAuth;
    /** Flag que determina si se realiza uso de <b>TLS</b>. */
    private boolean useTLS;
    /** Password de la cuenta que env&iacute;a el correo (emisor) (<i>OPCIONAL: en caso tal que useAuth sea true</i>). */
    private String senderPassword;
    /** <b>PUERTO</b> del servidor SMTP (<i>OPCIONAL</i>: por defecto tomar&aacute; el <i>25</i>). */
    private String smtpPort;
    
    
    
    public String getHostSmtp() {
        return hostSmtp;
    }

    public void setHostSmtp(String hostSmtp) {
        this.hostSmtp = hostSmtp;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getCcAddress() {
        return ccAddress;
    }

    public void setCcAddress(String ccAddress) {
        this.ccAddress = ccAddress;
    }

    public String getBccAddress() {
        return bccAddress;
    }

    public void setBccAddress(String bccAddress) {
        this.bccAddress = bccAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean isHtmlFormat() {
        return htmlFormat;
    }

    public void setHtmlFormat(boolean htmlFormat) {
        this.htmlFormat = htmlFormat;
    }

    public StringBuffer getBody() {
        return body;
    }

    public void setBody(StringBuffer body) {
        this.body = body;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean isUseAuth() {
        return useAuth;
    }

    public void setUseAuth(boolean useAuth) {
        this.useAuth = useAuth;
    }

    public boolean isUseTLS() {
        return useTLS;
    }

    public void setUseTLS(boolean useTLS) {
        this.useTLS = useTLS;
    }

    public String getSenderPassword() {
        return senderPassword;
    }

    public void setSenderPassword(String senderPassword) {
        this.senderPassword = senderPassword;
    }

    public String getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(String smtpPort) {
        this.smtpPort = smtpPort;
    }
    
}
