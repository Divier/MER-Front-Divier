package com.jlcg.db;

import com.jlcg.db.data.DataList;
import com.jlcg.db.data.DataListIndex;
import com.jlcg.db.data.DataObject;
import com.jlcg.db.data.DataObjectIndex;
import com.jlcg.db.exept.ExceptionDB;
import com.jlcg.db.sql.MessageSQL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class AccessData {

    public static boolean AUTOCOMMIT = true;
    public static boolean NOT_AUTOCOMMIT = false;
    private Connection cn;

    public AccessData(Connection cn) throws ExceptionDB {
        this.cn = cn;
        setAutoCommit(AUTOCOMMIT);
    }

    public boolean in(String id) throws ExceptionDB {
        ExecInput indata = new ExecInput(id);
        return indata.exec(cn);
    }

    public boolean in(String id, Object... args) throws ExceptionDB {
        ExecInput indata = new ExecInput(id, args);
        return indata.exec(cn, args);
    }

    public DataObject outDataObject(String id) throws ExceptionDB {
        return outDataObjectProcess(id, null);
    }

    public DataObject outDataObjec(String id, Object... args) throws ExceptionDB {
        return outDataObjectProcess(id, args);
    }

    private DataObject outDataObjectProcess(String id, Object... args) throws ExceptionDB {
        DataObject dataobject = null;
        ExecOutput outdata = null;
        ResultSet rs = null;

        try {
            if (args == null) {
                outdata = new ExecOutput(id);
            } else {
                outdata = new ExecOutput(id, args);
            }
            rs = outdata.exec(cn, args);
            if (rs.next()) {
                ResultSetMetaData rsmd = rs.getMetaData();
                int numcolunm = rsmd.getColumnCount();
                dataobject = new DataObject(numcolunm);
                for (int h = 1; h <= numcolunm; h++) {
                    Object o = rs.getObject(h);
                    dataobject.set(rsmd.getColumnName(h), o);
                }

            }
        } catch (SQLException exc) {
            throw new ExceptionDB(exc.getMessage());
        } catch (ExceptionDB exc) {
            throw exc;
        } finally {
            CloseSQL.close(rs);

            if (outdata != null) {
                outdata.closeStatement();
            }
        }

        return dataobject;
    }

    public DataObjectIndex outDataObjectIndex(String id) throws ExceptionDB {
        return outDataObjectIndexProcess(id, null);
    }

    public DataObjectIndex outDataObjecIndex(String id, Object... args) throws ExceptionDB {
        return outDataObjectIndexProcess(id, args);
    }

    private DataObjectIndex outDataObjectIndexProcess(String id, Object... args) throws ExceptionDB {
        DataObjectIndex dataobjectindex = null;
        ExecOutput outdata  = null;
        ResultSet rs = null;

        try {
            if (args == null) {
                outdata = new ExecOutput(id);
            } else {
                outdata = new ExecOutput(id, args);
            }
            rs = outdata.exec(cn, args);
            if (rs.next()) {
                ResultSetMetaData rsmd = rs.getMetaData();
                int numcolunm = rsmd.getColumnCount();
                dataobjectindex = new DataObjectIndex(numcolunm);
                for (int h = 1; h <= numcolunm; h++) {
                    Object o = rs.getObject(h);
                    dataobjectindex.addColumnName(rsmd.getColumnName(h));
                    dataobjectindex.set(o);
                }

            }
        } catch (SQLException exc) {
            throw new ExceptionDB(exc.getMessage());
        } catch (ExceptionDB exc) {
            throw exc;
        } finally {
            CloseSQL.close(rs);

            if (outdata != null) {
                outdata.closeStatement();
            }
        }

        return dataobjectindex;
    }

    public DataList outDataList(String id) throws ExceptionDB {
        return outDataListProcess(id, null);
    }

    public DataList outDataList(String id, Object... args) throws ExceptionDB {
        return outDataListProcess(id, args);
    }

    private DataList outDataListProcess(String id, Object ... args) throws ExceptionDB {
        DataList datalist = null;
        ExecOutput outdata = null;
        ResultSet rs = null;

        try {
            if (args == null) {
                outdata = new ExecOutput(id);
            } else {
                outdata = new ExecOutput(id, args);
            }
            rs = outdata.exec(cn, args);
            boolean next = rs.next();
            if (next) {
                datalist = new DataList();
                ResultSetMetaData rsmd = rs.getMetaData();
                int numcol = rsmd.getColumnCount();
                for (; next; next = rs.next()) {
                    DataObject data = new DataObject(numcol);
                    for (int k = 1; k <= numcol; k++) {
                        String colname = rsmd.getColumnName(k);
                        Object objvalue = rs.getObject(k);
                        data.set(colname, objvalue);
                    }

                    datalist.add(data);
                }

            }

        } catch (SQLException exc) {
            throw new ExceptionDB(exc.getMessage());
        } catch (ExceptionDB exc) {
            throw exc;
        } finally {
            CloseSQL.close(rs);

            if (outdata != null) {
                outdata.closeStatement();
            }
        }

        return datalist;
    }

    public DataListIndex outDataListIndex(String id) throws ExceptionDB {
        return outDataListIndexProcess(id, null);
    }

    public DataListIndex outDataListIndex(String id, String args[]) throws ExceptionDB {
        return outDataListIndexProcess(id, args);
    }

    private DataListIndex outDataListIndexProcess(String id, String args[]) throws ExceptionDB {
        DataListIndex datalist = null;
        ExecOutput outdata = null;
        ResultSet rs = null;

        try {
            if (args == null) {
                outdata = new ExecOutput(id);
            } else {
                outdata = new ExecOutput(id, args);
            }
            rs = outdata.exec(cn, args);
            boolean next = rs.next();
            if (next) {
                datalist = new DataListIndex();
                ResultSetMetaData rsmd = rs.getMetaData();
                int numcol = rsmd.getColumnCount();
                for (int k = 1; k <= numcol; k++) {
                    datalist.addColumnName(rsmd.getColumnName(k));
                }

                for (; next; next = rs.next()) {
                    DataObjectIndex data = new DataObjectIndex(numcol);
                    for (int k = 1; k <= numcol; k++) {
                        Object objvalue = rs.getObject(k);
                        data.set(objvalue);
                    }

                    datalist.add(data);
                }

            }
        } catch (SQLException exc) {
            throw new ExceptionDB(exc.getMessage());
        } catch (ExceptionDB exc) {
            throw exc;
        } finally {
            CloseSQL.close(rs);

            if (outdata != null) {
                outdata.closeStatement();
            }
        }

        return datalist;
    }

    public void setAutoCommit(boolean iscommit) throws ExceptionDB {
        try {
            cn.setAutoCommit(iscommit);
        } catch (SQLException exc) {
            throw new ExceptionDB((new StringBuilder(String.valueOf(MessageSQL.MESSGE_AUTOCOMMIT))).append(exc.getMessage()).toString());
        }
    }

    public void commit() throws ExceptionDB {
        if (cn != null) {
            try {
                cn.commit();
            } catch (SQLException exc) {
                throw new ExceptionDB((new StringBuilder(String.valueOf(MessageSQL.MESSGE_PESISTENT))).append(exc.getMessage()).toString());
            }
        }
    }

    public void rollback() throws ExceptionDB {
        if (cn != null) {
            try {
                cn.rollback();
            } catch (SQLException exc) {
                throw new ExceptionDB((new StringBuilder(String.valueOf(MessageSQL.MESSGE_ROLLBACK))).append(exc.getMessage()).toString());
            }
        }
    }

    public void clear() throws ExceptionDB {
        CloseSQL.close(cn);
        cn = null;
    }
}
