package com.jlcg.db;

import com.jlcg.db.exept.ExceptionDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CloseSQL {

    private CloseSQL() {
        // impedir instancias invalidas
    }

    public static void close(Connection connection) throws ExceptionDB {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException exc) {
                throw new ExceptionDB((new StringBuilder("CloseSQL, no cierra connection ")).append(exc.getMessage()).toString());
            }
        }
    }

    public static void close(Statement statement) throws ExceptionDB {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException exc) {
                throw new ExceptionDB((new StringBuilder("CloseSQL, no cierra statement ")).append(exc.getMessage()).toString());
            }
        }
    }

    public static void close(ResultSet resultset) throws ExceptionDB {
        if (resultset != null) {
            try {
                resultset.close();
            } catch (SQLException exc) {
                throw new ExceptionDB((new StringBuilder("CloseSQL, no cierra resultset ")).append(exc.getMessage()).toString());
            }
        }
    }
}
