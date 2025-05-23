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
public class ColumnDto implements Serializable {

    private static final long serialVersionUID = -4448991076889664665L;

    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    

}
