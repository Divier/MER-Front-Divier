/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mer.integration.nodedistance.exception;

/**
 * @author Camilo Andres Ramirez
 * @version 1.0, 2024/09/30
 */
public class CalculateNodeDistanceRestClientException extends Exception {

    private static final long serialVersionUID = -7759716175830827714L;

    public CalculateNodeDistanceRestClientException(String message) {
        super(message);
    }

    public CalculateNodeDistanceRestClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public CalculateNodeDistanceRestClientException(Throwable cause) {
        super(cause);
    }
}