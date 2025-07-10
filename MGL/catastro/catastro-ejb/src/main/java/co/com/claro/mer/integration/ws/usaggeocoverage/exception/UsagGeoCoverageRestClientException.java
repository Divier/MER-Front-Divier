/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mer.integration.ws.usaggeocoverage.exception;


/**
 * @author Camilo Andres Ramirez
 * @version 1.0, 2024/09/30
 */
public class UsagGeoCoverageRestClientException extends Exception {

    private static final long serialVersionUID = -7759716175830827714L;

    public UsagGeoCoverageRestClientException(String message) {
        super(message);
    }

    public UsagGeoCoverageRestClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsagGeoCoverageRestClientException(Throwable cause) {
        super(cause);
    }
}