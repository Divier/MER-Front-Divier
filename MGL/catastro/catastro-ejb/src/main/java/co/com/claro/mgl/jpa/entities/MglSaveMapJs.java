/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@Entity
@Table(name = "MGL_SAVE_MAPJS", schema = "MGL_SCHEME")
@XmlRootElement
public class MglSaveMapJs implements Serializable {

    private static final long serialVersionUID = 1L; 
    
    @Id
    @Column(name = "SAVE_MAPJS_ID", nullable = false)
    private BigDecimal saveMapJsId;
    @Column(name = "FECHA_SAVE")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaSave;
    @Column(name = "JS_MAP")
    private String jsMap;

    public BigDecimal getSaveMapJsId() {
        return saveMapJsId;
    }

    public void setSaveMapJsId(BigDecimal saveMapJsId) {
        this.saveMapJsId = saveMapJsId;
    }

    public Date getFechaSave() {
        return fechaSave;
    }

    public void setFechaSave(Date fechaSave) {
        this.fechaSave = fechaSave;
    }

    public String getJsMap() {
        return jsMap;
    }

    public void setJsMap(String jsMap) {
        this.jsMap = jsMap;
    }
 
}
