/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amx.co.schema.claroheaders.v1;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Diana Enriquez
 */
public class HeaderError {
    @XmlElement
    protected String errorCode;
    
    @XmlElement
    protected String errorDescription;
    
    @XmlElement
    protected String errorType;
}
