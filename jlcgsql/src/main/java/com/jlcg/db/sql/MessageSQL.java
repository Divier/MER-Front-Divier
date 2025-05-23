
package com.jlcg.db.sql;


public class MessageSQL {

            public static String MESSAGE_ACCESSDATA = "ManageConnection: no logra conectarse a base de datos. ";
            public static String MESSAGE_DATASOURCE_NULL = "ManageConnection: DataSource es nulo. ";
            public static String MESSAGE_PROPERTIES_NULL = "ManageConnection: Properties es nulo. ";
            public static String MESSAGE_DATASOURCE_AND_PROPERTIES_ISNULL = "DataSource o Porpiedades de conexion son nulos";
            public static String MESSAGE_DRIVER = "ManageConnection: error en drivers. ";
            public static String MESSAGE_CONNECTION = "ManageConnection: error en crear de conexion. ";
            public static String MESSAGE_NOTPARAMETER = "ManageConnection: Parametro nulo: ";
            public static String MESSGE_AUTOCOMMIT = "AccessData.setAutoCommit: no puede establecer autocommiT. ";
            public static String MESSGE_PESISTENT = "AccessData: commit error en persitencia. ";
            public static String MESSGE_ROLLBACK = "AccessData: rollback error en deshacer ";
            public static final String MESSAGE_LOAD = "BuildSQL: error en consulta de tabla de sql. ";
            public static final String MESSAGE_SQL_ID = "BuildSQL: no existe la sentencia codigo: ";
            public static final String MESSAGE_SQL_PARAMETER = "BuildSQL: argumento invalido sobre la sentencia codigo: ";
            public static final String MESSAGE_ERROR_SQL = "ExecInput: error en sql: ";


}
