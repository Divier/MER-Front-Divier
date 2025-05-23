package com.jlcg.db.data;

import java.io.Serializable;

public class DataContainer implements Serializable {

    private static final long serialVersionUID = 1L;
    private Object value;

    public DataContainer() {
        value = null;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
