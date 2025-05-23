package com.jlcg.db.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.Hashtable;

public class DataObject
        implements Serializable {

    private static final long serialVersionUID = 1L;
    private Hashtable<String,DataContainer> data;

    public DataObject(int size) {
        data = new Hashtable(size);
    }

    public void set(String label, Object obj) {
        DataContainer dataContainer = new DataContainer();
        dataContainer.setValue(obj);
        data.put(label, dataContainer);
    }

    public String getFormatString(String label) {
        Object obj = get(label);
        if (obj != null) {
            String str = obj.toString();
            return str.trim();
        } else {
            return null;
        }
    }

    public String getString(String label) {
        String str = (String) get(label);
        if (str != null) {
            return str.trim();
        } else {
            return str;
        }
    }

    public Date getDate(String label) {
        Timestamp time = (Timestamp) get(label);
        if (time != null) {
            return new Date(time.getTime());
        } else {
            return null;
        }
    }

    public Object getObject(String label) {
        return get(label);
    }

    public Integer getInteger(String label) {
        return (Integer) get(label);
    }

    public Long getLong(String label) {
        return (Long) get(label);
    }

    public BigDecimal getBigDecimal(String label) {
        return (BigDecimal) get(label);
    }

    public Boolean getBoolean(String label) {
        return (Boolean) get(label);
    }

    public Timestamp getTimestamp(String label) {
        return (Timestamp) get(label);
    }

    private Object get(String label) {
        DataContainer dataContainer = (DataContainer) data.get(label);
        return dataContainer.getValue();
    }

    protected Hashtable<String,DataContainer> getData() {
        return data;
    }

    public String toString() {
        StringBuffer result = new StringBuffer();
        String key;
        for (Enumeration e = data.keys(); e.hasMoreElements(); result.append((new StringBuilder("key: <")).append(key).append("> value: <").append(((DataContainer) data.get(key)).toString()).append(">").toString())) {
            key = (String) e.nextElement();
        }

        return result.toString();
    }
}
