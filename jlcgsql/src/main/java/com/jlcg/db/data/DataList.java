package com.jlcg.db.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class DataList implements Serializable {

    private static final long serialVersionUID = 1L;
    private ArrayList<DataObject> list;

    public DataList() {
        list = new ArrayList<>();
    }

    public DataList(int size) {
        list = new ArrayList<>(size);
    }

    public ArrayList<DataObject> getList() {
        return list;
    }

    public void add(DataObject data) {
        list.add(data);
    }

    public DataObject get(int index) {
        return (DataObject) list.get(index);
    }

    public ArrayList<DataObject> getArrayList() {
        return list;
    }

    public void delete(int index) {
        list.remove(index);
    }

    public void deleteAll() {
        list.clear();
    }

    public int size() {
        return list.size();
    }

    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append("<------------------------->");
        result.append((new StringBuilder("DataList.cantidad: ")).append(list.size()).toString());
        for (int h = 0; h < list.size(); h++) {
            DataObject data = (DataObject) list.get(h);
            Hashtable datatable = data.getData();
            String key;
            for (Enumeration e = datatable.keys(); e.hasMoreElements(); result.append((new StringBuilder("key: ")).append(key).append(" value: ").append(((DataContainer) datatable.get(key)).toString()).toString())) {
                key = (String) e.nextElement();
            }

            result.append("-----");
        }

        result.append("<------------------------->");
        return result.toString();
    }
}
