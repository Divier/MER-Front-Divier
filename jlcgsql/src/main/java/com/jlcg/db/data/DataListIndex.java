package com.jlcg.db.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataListIndex implements Serializable {

    private static final long serialVersionUID = 1L;
    private ArrayList<DataObjectIndex> list;
    private List<String> nameColumns;

    public DataListIndex() {
        list = new ArrayList<>();
        nameColumns = new ArrayList<>();
    }

    public DataListIndex(int size) {
        nameColumns = null;
        list = new ArrayList<>(size);
    }

    public void addColumnName(String name) {
        nameColumns.add(name);
    }

    public String getColumnName(int index) {
        return (String) nameColumns.get(index);
    }

    public void add(DataObjectIndex data) {
        list.add(data);
    }

    public DataObjectIndex get(int index) {
        return (DataObjectIndex) list.get(index);
    }

    public ArrayList<DataObjectIndex> getArrayList() {
        return list;
    }

    public ArrayList<DataObjectIndex> getList() {
        return list;
    }

    public List<String> getNameColumns() {
        return nameColumns;
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
            DataObjectIndex dataObjectIndex = (DataObjectIndex) list.get(h);
            List datalist = dataObjectIndex.getData();
            for (int i = 0; i < datalist.size(); i++) {
                result.append((new StringBuilder("index: <")).append(i).append("> value: <").append(datalist.get(i)).append(">").toString());
            }
            result.append("-----");
        }
        result.append("<------------------------->");
        return result.toString();
    }
}
