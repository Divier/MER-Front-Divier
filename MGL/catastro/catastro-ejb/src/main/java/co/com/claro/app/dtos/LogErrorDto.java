/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.app.dtos;

import java.io.Serializable;

/**
 *
 * @author Fabian Duarte
 * duartey@globalhitss.com
 */
public class LogErrorDto implements Serializable {

    private static final long serialVersionUID = -4448991076889664665L;

    private String request;
    private String response;

    public String getRequest() {
        return request;
    }

    public String getResponse() {
        return response;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    

}
