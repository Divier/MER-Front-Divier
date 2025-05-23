/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.war.documentos.util;

import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.Constant;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;


/**
 * Utilitarios relacionados con el <b>Gestor Documental</b>.
 *
 * @author Camilo Miranda (<i>mirandaca</i>)
 */
public class GestorDocumentalUtil {

    
    private static final Logger LOGGER = LogManager.getLogger(GestorDocumentalUtil.class);

    /**
     * Obtiene el <i>InputStream</i> asociado a la URL del documento alojado
     * en el <b>Gestor Documental</b> (<i>UCM</i>).
     *
     * @param ucmDocumentURL
     * @throws ApplicationException
     * @return
     */
    public static InputStream getInputStreamFromUcmURL(String ucmDocumentURL) throws ApplicationException {
        InputStream inputStream = null;

        if (ucmDocumentURL != null && !ucmDocumentURL.isEmpty()) {
            WebClient clienteHttp;

            try {
                ParametrosMglManager parametrosMglManager = new ParametrosMglManager();

                String usuario = parametrosMglManager.findByAcronimo(
                       Constant.USER_AUTENTICACION_GESTOR_DOCUMENTAL)
                        .iterator().next().getParValor();

                String password = parametrosMglManager.findByAcronimo(
                        Constant.PASS_AUTENTICACION_GESTOR_DOCUMENTAL)
                        .iterator().next().getParValor();

                String wccURL = parametrosMglManager.findByAcronimo(
                        Constant.GESTOR_DOCUMENTAL_LOGIN_URL)
                        .iterator().next().getParValor();
                

                clienteHttp = new WebClient();
                HtmlPage paginaLogin = clienteHttp.getPage(wccURL);
                HtmlForm formaLogin = paginaLogin.getFormByName("loginForm");
                
                DomElement element1 = null;
                HtmlSubmitInput botonSubmit = null;
                
                // Buscar el boton Submit entre los hijos del formulario (es el ultimo div):
                for (DomElement element : formaLogin.getChildElements()) {
                    element1 = element;
                }
                if (element1 != null) {
                    for (DomElement element : element1.getChildElements()) {
                        if (element instanceof HtmlSubmitInput) {
                            botonSubmit = (HtmlSubmitInput) element;
                        }
                    }
                }
                
                // Se establece el usuario.
                HtmlTextInput campoUsuario = formaLogin.getInputByName("j_username");
                campoUsuario.setValueAttribute(usuario);

                // Se establece el password.
                HtmlPasswordInput campoPassword = formaLogin.getInputByName("j_password");
                campoPassword.setValueAttribute(password);

                // Se realiza un submit para enviar los parametros de login.
                botonSubmit.click();
                
                
                esperar(5);
                
                // Una vez logueado, se obtiene el documento.
                Page archivoBinario = clienteHttp.getPage(ucmDocumentURL);

                 
                // Obtiene el InputStream del archivo obtenido del request.
                inputStream = archivoBinario
                        .getWebResponse().getContentAsStream();

            } catch (ConnectException e) {
                String msg = "No ha sido posible conectar con la URL del gestor documental";
                LOGGER.error(msg);
                throw new ApplicationException("No ha sido posible conectar con la URL del gestor documental.", e);
            } catch (IOException e) {
                String msg = "Se produjo un error al momento de realizar la lectura de la URL.";
                LOGGER.error(msg);
                throw new ApplicationException("Se produjo un error al momento de realizar la lectura de la URL.", e);
            } catch (IllegalStateException e) {
                String msg = "Se produjo un error al momento de realizar la lectura de la URL:";
                LOGGER.error(msg);
                throw new ApplicationException("Se produjo un error al momento de realizar la lectura de la URL: " + e.getMessage(), e);
            } catch (ApplicationException | ElementNotFoundException | FailingHttpStatusCodeException e) {
                String msg = "Se produjo un error al momento de procesar la URL:";
                LOGGER.error(msg);
                throw new ApplicationException("Se produjo un error al momento de procesar la URL: " + e.getMessage(), e);
            }
        }

        return (inputStream);
    }
    
    public static void esperar(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException ex) {        
            LOGGER.error("Ocurrio un error en el tiempo de espera: "+ex.getMessage());
        }
    }
}
