/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.ofscCore.findMatchingResources;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author bocanegravm
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "find_tecnicos_request", propOrder = {
    "criteria",
    "date",
    "fields",
    "activity",
    "schedulesToReturn",
    "schedulesFields"
})
public class ApiFindTecnicosRequest {

    /**
     *
     */
    @XmlElement
    protected Criteria criteria;
    /**
     *
     */
    @XmlElement
    protected String date;
    /**
     *
     */
    @XmlElement
    protected Activity activity;
    /**
     *
     */
    @XmlElement
    protected List<String> fields;

    /**
     *
     */
    @XmlElement
    protected List<String> schedulesToReturn;

    /**
     *
     */
    @XmlElement
    protected List<String> schedulesFields;

    public Criteria getCriteria() {
        return criteria;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public List<String> getSchedulesToReturn() {
        return schedulesToReturn;
    }

    public void setSchedulesToReturn(List<String> schedulesToReturn) {
        this.schedulesToReturn = schedulesToReturn;
    }

    public List<String> getSchedulesFields() {
        return schedulesFields;
    }

    public void setSchedulesFields(List<String> schedulesFields) {
        this.schedulesFields = schedulesFields;
    }
    
    
}
