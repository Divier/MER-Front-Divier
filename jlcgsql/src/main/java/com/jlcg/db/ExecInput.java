package com.jlcg.db;

import com.jlcg.db.exept.ExceptionDB;
import com.jlcg.db.sql.BuildSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ExecInput {

    private String sql;

    protected ExecInput(String id, Object... args) throws ExceptionDB {
        sql = BuildSQL.getSQL(id, args);
    }

    protected boolean exec(Connection cn, Object... params) throws ExceptionDB {
        boolean ok;

        try {
            PreparedStatement prepStmt = cn.prepareStatement(sql);
            int indexParam =1;
            for (Object obj : params){
                prepStmt.setObject(indexParam++, obj);
            }
            int resultSet = prepStmt.executeUpdate();
            ok = true;
        } catch (SQLException exc) {
            throw new ExceptionDB((new StringBuilder("ExecInput: error en sql: [")).append(sql).append("]").append(exc.getMessage()).toString());
        }

        return ok;
    }
}
