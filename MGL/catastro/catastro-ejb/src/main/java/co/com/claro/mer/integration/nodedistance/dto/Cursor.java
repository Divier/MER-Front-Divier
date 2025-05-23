/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mer.integration.nodedistance.dto;

/**
 * @author Camilo Andres Ramirez
 * @version 1.0, 2024/09/30
 */
public class Cursor {

    private Row[] row;

    public Row[] getRow() {
        return row;
    }

    public void setRow(Row[] value) {
        this.row = value;
    }

    @Override
    public String toString() {
        return "Cursor{" + "row=" + row.toString() + '}';
    }
    
    
}
