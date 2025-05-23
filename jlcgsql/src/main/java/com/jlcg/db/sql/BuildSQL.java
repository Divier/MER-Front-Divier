package com.jlcg.db.sql;

import com.jlcg.db.exept.ExceptionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

public class BuildSQL {
    
    /** Database Schema. */
    private static final String DB_SCHEMA = "MGL_SCHEME";
    
    private static final String SQL = "select id,sentence from " + DB_SCHEMA + ".sqldata ";
    private static final String ID = "id";
    private static final String SENTENCE = "sentence";
    private static final Hashtable<String,String> tablesql = new Hashtable();

    public static int load() throws ExceptionDB {
        int countSentences;

        try (Connection cn = ManageAccess.getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(SQL)) {

            while (rs.next()) {
                String idRs = rs.getString("id").trim();
                String sentenceRs = (new StringBuilder(String.valueOf(rs.getString("sentence")))).append(" ").toString();
                 tablesql.put(idRs,sentenceRs);
            }
            countSentences = tablesql.size();
        } catch (ExceptionDB exc) {
            throw exc;
        } catch (SQLException exc) {
            throw new ExceptionDB((new StringBuilder("BuildSQL: error en consulta de tabla de sql. ")).append(exc.getMessage()).toString());
        }

        return countSentences;
    }

    public static String getSQL(String id) throws ExceptionDB {
        String sql = tablesql.get(id);
        if (sql == null) {
            throw new ExceptionDB((new StringBuilder("BuildSQL: no existe la sentencia codigo: [")).append(id).append("] ").toString());
        } else {
            sql = (new StringBuilder(String.valueOf(sql))).append(" ").toString();
            return sql;
        }
    }

    public static String getSQL(String id, Object... args) throws ExceptionDB {
        String sql = "";
        String sentence = tablesql.get(id);
        if (sentence != null) {
            sql = sentence.replace("'?'", "?");
            sql = sql.replace("'%?%'", "?");
            sql = sql.replace("'%?'", "?");
            sql = sql.replace("'?%'", "?");
        } else {
            throw new ExceptionDB((new StringBuilder("BuildSQL: no existe la sentencia codigo: [")).append(id).append("]").toString());
        }
        return sql;
    }
}
