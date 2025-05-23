
package com.jlcg.db.init;

import com.jlcg.db.exept.ExceptionDB;
import java.io.IOException;
import javax.naming.NamingException;


public interface TaskInit {

    public static final int LOAD_DATASOURCE = 1;
    public static final int LOAD_PROPERTIES = 2;
    public static final int TEST_CONNECTION = 3;
    public static final int LOAD_SQL = 4;

    public abstract ResponseInit loadDataSource() throws NamingException;

    public abstract ResponseInit loadProperties() throws IOException;

    public abstract ResponseInit testConnection() throws ExceptionDB;

    public abstract ResponseInit loadSQL() throws ExceptionDB;
}
