package com.jlcg.db.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DataObjectIndex implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Object> data;
    private List<String> nameColumns;

    public DataObjectIndex(int size) {
        data = new ArrayList<>();
        nameColumns = new ArrayList<>();
    }

    public void addColumnName(String name) {
        nameColumns.add(name);
    }

    public String getColumnName(int index) {
        return nameColumns.get(index);
    }

    public void set(Object obj) {
        data.add(obj);
    }

    public String getFormatString(int index) {
        Object obj = data.get(index);
        if (obj != null) {
            String str = obj.toString();
            return str.trim();
        } else {
            return null;
        }
    }

    public String getString(int index) {
        String str = (String) data.get(index);
        if (str != null) {
            return str.trim();
        } else {
            return str;
        }
    }

    public Date getDate(int index) {
        Timestamp time = (Timestamp) data.get(index);
        if (time != null) {
            return new Date(time.getTime());
        } else {
            return null;
        }
    }

    public Object getObject(int index) {
        return data.get(index);
    }

    public Integer getInteger(int index) {
        return (Integer) data.get(index);
    }

    public Long getLong(int index) {
        return (Long) data.get(index);
    }

    public BigDecimal getBigDecimal(int index) {
        return (BigDecimal) data.get(index);
    }

    public Boolean getBoolean(int index) {
        return (Boolean) data.get(index);
    }

    public Timestamp getTimestamp(int index) {
        return (Timestamp) data.get(index);
    }

    public Object get(int index) {
        return data.get(index);
    }

    public List<Object> getData() {
        return data;
    }

    public List<String> getNameColumns() {
        return nameColumns;
    }

    public String toString() {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < data.size(); i++) {
            result.append((new StringBuilder("index: <")).append(i).append("> value: <").append(data.get(i)).append(">").toString());
        }

        return result.toString();
    }
}
