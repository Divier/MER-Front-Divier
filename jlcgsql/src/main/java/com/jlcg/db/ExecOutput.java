package com.jlcg.db;

import com.jlcg.db.exept.ExceptionDB;
import com.jlcg.db.sql.BuildSQL;

import java.sql.*;

public class ExecOutput {

    private Statement st;
    private String sql;

    protected ExecOutput(String id, Object ... args) throws ExceptionDB {
        sql = BuildSQL.getSQL(id, args);
    }

    protected ResultSet exec(Connection cn, Object ... params) throws ExceptionDB {
        ResultSet rs = null;
        try {
            PreparedStatement prepStmt = cn.prepareStatement(sql);
            int indexParam =1;
            for (Object obj : params){
                prepStmt.setObject(indexParam++, obj);
            }
            rs = prepStmt.executeQuery();
        } catch (SQLException exc) {
            throw new ExceptionDB((new StringBuilder("ExecInput: error en sql: [")).append(sql).append("]").append(exc.getMessage()).toString());
        }
        return rs;
    }

    protected void closeStatement() throws ExceptionDB {
        CloseSQL.close(st);
    }
}
