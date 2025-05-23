/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.error;

import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;

/**
 *
 * @author User
 */
public class ApplicationExceptionCM extends Exception{
    CmtCuentaMatrizMgl matrizMgl;
    public ApplicationExceptionCM() {
    }

    public ApplicationExceptionCM(String message) {
        super(message);
    }

    public ApplicationExceptionCM(String message, CmtCuentaMatrizMgl ccmm) {
        super(message);
        matrizMgl=ccmm;
    }
    
    public ApplicationExceptionCM(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ApplicationExceptionCM(String message, Throwable cause, CmtCuentaMatrizMgl ccmm) {
        super(message, cause);
        matrizMgl = ccmm;
    }

    public ApplicationExceptionCM(Throwable cause) {
        super(cause);
    }    

    public CmtCuentaMatrizMgl getMatrizMgl() {
        return matrizMgl;
    }

    public void setMatrizMgl(CmtCuentaMatrizMgl matrizMgl) {
        this.matrizMgl = matrizMgl;
    }
    
}
