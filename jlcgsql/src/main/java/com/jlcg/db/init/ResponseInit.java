package com.jlcg.db.init;

import java.util.Properties;
import javax.sql.DataSource;

public class ResponseInit {

    private Properties properties;
    private DataSource datasource;
    private boolean state;
    private int count;

    public ResponseInit() {
        properties = null;
        datasource = null;
        state = false;
        count = 0;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public DataSource getDatasource() {
        return datasource;
    }

    public void setDatasource(DataSource datasource) {
        this.datasource = datasource;
    }

    public Boolean getState() {
        return Boolean.valueOf(state);
    }

    public void setState(Boolean state) {
        this.state = state.booleanValue();
    }

    public Integer getCount() {
        return Integer.valueOf(count);
    }

    public void setCount(Integer count) {
        this.count = count.intValue();
    }
}
