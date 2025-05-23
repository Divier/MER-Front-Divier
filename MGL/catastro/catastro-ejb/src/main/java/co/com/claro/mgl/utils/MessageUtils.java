package co.com.claro.mgl.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author bernaldl
 */
public class MessageUtils {
    
    public static void buildFacesMessage(FacesMessage.Severity facesSeverityMessage, String message){
    FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(facesSeverityMessage, message, "")); 
    
    }
    
}
