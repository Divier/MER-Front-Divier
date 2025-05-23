package co.com.telmex.catastro.mbeans.solicitudes;

import co.com.telmex.catastro.mbeans.comun.ConstantSystem;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase UnificadorEstructuraArchivo
 *
 * @author 	Ana María Malambo
 * @version	1.0
 */
public class UnificadorEstructuraArchivo {

    /**
     * 
     */
    protected static final int QCOLUMNS = 21;
    /**
     * 
     */
    protected static final String HANDLE = "HANDLE";
    /**
     * 
     */
    protected static final String BLOCKNAME = "BLOCKNAME";
    /**
     * 
     */
    protected static final String NOMBRE_CONJ = "NOMBRE_CONJ";
    /**
     * 
     */
    protected static final String NOMBRECALL = "NOMBRECALL";
    /**
     * 
     */
    protected static final String PLACAUNIDA = "PLACAUNIDA";
    /**
     * 
     */
    protected static final String NOMBRE_ED = "NOMBRE_ED";
    /**
     * 
     */
    protected static final String NUM_CASAS = "NUM_CASAS";
    /**
     * 
     */
    protected static final String NUMINT = "NUMINT";
    /**
     * 
     */
    protected static final String APTOS = "APTOS";
    /**
     * 
     */
    protected static final String PI = "PI";
    /**
     * 
     */
    protected static final String LOCALES = "LOCALES";
    /**
     * 
     */
    protected static final String OFICINAS = "OFICINAS";
    /**
     * 
     */
    protected static final String NOMBRECALLANT2 = "NOMBRECALLANT2";
    /**
     * 
     */
    protected static final String PLACAUNIDAANT2 = "PLACAUNIDAANT2";
    /**
     * 
     */
    protected static final String NOMBRECALLANT3 = "NOMBRECALLANT3";
    /**
     * 
     */
    protected static final String PLACAUNIDAANT3 = "PLACAUNIDAANT3";
    /**
     * 
     */
    protected static final String NO = "NO.";
    /**
     * 
     */
    protected static final String AMP = "AMP";
    /**
     * 
     */
    protected static final String PISOS = "PISOS";
    /**
     * 
     */
    protected static final String ESTRATO = "ESTRATO";
    /**
     * 
     */
    protected static final String VALIDARGEO = "VALIDARGEO";
    /**
     * 
     */
    protected static final String CIUDAD = "CIUDAD";
    /**
     * 
     */
    protected static final String BARRIO = "BARRIO";
    /**
     * 
     */
    protected static final String NODO = "NODO";
    /**
     * 
     */
    public List<ColumnaSolicitudRed> estructura;

    /**
     * 
     */
    public UnificadorEstructuraArchivo() {
        estructura = new ArrayList<ColumnaSolicitudRed>();
        ColumnaSolicitudRed columna = new ColumnaSolicitudRed();

        columna.setColumnName(HANDLE);
        columna.setValue(0);
        estructura.add(columna);
        ColumnaSolicitudRed columna1 = new ColumnaSolicitudRed();
        columna1.setColumnName(BLOCKNAME);
        columna1.setValue(0);
        estructura.add(columna1);
        ColumnaSolicitudRed columna2 = new ColumnaSolicitudRed();
        columna2.setColumnName(NOMBRECALL);
        columna2.setValue(0);
        estructura.add(columna2);
        ColumnaSolicitudRed columna3 = new ColumnaSolicitudRed();
        columna3.setColumnName(PLACAUNIDA);
        columna3.setValue(0);
        estructura.add(columna3);
        ColumnaSolicitudRed columna4 = new ColumnaSolicitudRed();
        columna4.setColumnName(NOMBRE_ED);
        columna4.setValue(0);
        estructura.add(columna4);
        ColumnaSolicitudRed columna5 = new ColumnaSolicitudRed();
        columna5.setColumnName(NUMINT);
        columna5.setValue(0);
        estructura.add(columna5);
        ColumnaSolicitudRed columna6 = new ColumnaSolicitudRed();
        columna6.setColumnName(PI);
        columna6.setValue(0);
        estructura.add(columna6);
        ColumnaSolicitudRed columna7 = new ColumnaSolicitudRed();
        columna7.setColumnName(APTOS);
        columna7.setValue(0);
        estructura.add(columna7);
        ColumnaSolicitudRed columna8 = new ColumnaSolicitudRed();
        columna8.setColumnName(OFICINAS);
        columna8.setValue(0);
        estructura.add(columna8);
        ColumnaSolicitudRed columna9 = new ColumnaSolicitudRed();
        columna9.setColumnName(LOCALES);
        columna9.setValue(0);
        estructura.add(columna9);
        ColumnaSolicitudRed columna10 = new ColumnaSolicitudRed();
        columna10.setColumnName(NOMBRECALLANT2);
        columna10.setValue(0);
        estructura.add(columna10);
        ColumnaSolicitudRed columna11 = new ColumnaSolicitudRed();
        columna11.setColumnName(PLACAUNIDAANT2);
        columna11.setValue(0);
        estructura.add(columna11);
        ColumnaSolicitudRed columna12 = new ColumnaSolicitudRed();
        columna12.setColumnName(NOMBRECALLANT3);
        columna12.setValue(0);
        estructura.add(columna12);
        ColumnaSolicitudRed columna13 = new ColumnaSolicitudRed();
        columna13.setColumnName(PLACAUNIDAANT3);
        columna13.setValue(0);
        estructura.add(columna13);
        ColumnaSolicitudRed columna14 = new ColumnaSolicitudRed();
        columna14.setColumnName(NO);
        columna14.setValue(0);
        estructura.add(columna14);
        ColumnaSolicitudRed columna15 = new ColumnaSolicitudRed();
        columna15.setColumnName(AMP);
        columna15.setValue(0);
        estructura.add(columna15);
        ColumnaSolicitudRed columna16 = new ColumnaSolicitudRed();
        columna16.setColumnName(NOMBRE_CONJ);
        columna16.setValue(0);
        estructura.add(columna16);
        ColumnaSolicitudRed columna17 = new ColumnaSolicitudRed();
        columna17.setColumnName(NUM_CASAS);
        columna17.setValue(0);
        estructura.add(columna17);
        ColumnaSolicitudRed columna18 = new ColumnaSolicitudRed();
        columna18.setColumnName(PISOS);
        columna18.setValue(0);
        estructura.add(columna18);
        ColumnaSolicitudRed columna19 = new ColumnaSolicitudRed();
        columna19.setColumnName(CIUDAD);
        columna19.setValue(0);
        estructura.add(columna19);
        ColumnaSolicitudRed columna20 = new ColumnaSolicitudRed();
        columna20.setColumnName(BARRIO);
        columna20.setValue(0);
        estructura.add(columna20);
        ColumnaSolicitudRed columna21 = new ColumnaSolicitudRed();
        columna21.setColumnName(ESTRATO);
        columna21.setValue(0);
        estructura.add(columna21);
        ColumnaSolicitudRed columna22 = new ColumnaSolicitudRed();
        columna22.setColumnName(VALIDARGEO);
        columna22.setValue(0);
        estructura.add(columna22);
        ColumnaSolicitudRed columna23 = new ColumnaSolicitudRed();
        columna23.setColumnName(NODO);
        columna23.setValue(0);
        estructura.add(columna23);

        for (int p = 0; p < estructura.size(); p++) {
        }
    }

    /**
     * 
     * @param labels
     * @return
     */
    public String validarposiciones(ArrayList<String> labels) {
        String messagge = "";
        String linea = labels.get(0);
        String splitarray[] = linea.split("\t");
        for (int pos = 0; pos < splitarray.length; pos++) {

            if (Integer.parseInt(ConstantSystem.cargarPropiedad("QCOLUMNSPLANREDLOAD")) != splitarray.length) {
                messagge = "No es correcto la estructura del archivo";
            }
            if (pos == 0) {
                if (splitarray[pos].equalsIgnoreCase(HANDLE)) {
                    estructura.get(0).setValue(pos);
                }

            } else if (splitarray[pos].equalsIgnoreCase(BLOCKNAME)) {
                estructura.get(1).setValue(pos);
            } else if (splitarray[pos].equalsIgnoreCase(NOMBRECALL)) {
                estructura.get(2).setValue(pos);
            } else if (splitarray[pos].equalsIgnoreCase(PLACAUNIDA)) {
                estructura.get(3).setValue(pos);
            } else if (splitarray[pos].equalsIgnoreCase(NOMBRE_ED)) {
                estructura.get(4).setValue(pos);
            } else if (splitarray[pos].equalsIgnoreCase(NUMINT)) {
                estructura.get(5).setValue(pos);
            } else if (splitarray[pos].equalsIgnoreCase(PI)) {
                estructura.get(6).setValue(pos);
            } else if (splitarray[pos].equalsIgnoreCase(APTOS)) {
                estructura.get(7).setValue(pos);
            } else if (splitarray[pos].equalsIgnoreCase(OFICINAS)) {
                estructura.get(8).setValue(pos);
            } else if (splitarray[pos].equalsIgnoreCase(LOCALES)) {
                estructura.get(9).setValue(pos);
            } else if (splitarray[pos].equalsIgnoreCase(NOMBRECALLANT2)) {
                estructura.get(10).setValue(pos);
            } else if (splitarray[pos].equalsIgnoreCase(PLACAUNIDAANT2)) {
                estructura.get(11).setValue(pos);
            } else if (splitarray[pos].equalsIgnoreCase(NOMBRECALLANT3)) {
                estructura.get(12).setValue(pos);
            } else if (splitarray[pos].equalsIgnoreCase(PLACAUNIDAANT3)) {
                estructura.get(13).setValue(pos);
            } else if (splitarray[pos].equalsIgnoreCase(NO)) {
                estructura.get(14).setValue(pos);
            } else if (splitarray[pos].equalsIgnoreCase(AMP)) {
                estructura.get(15).setValue(pos);
            } else if (splitarray[pos].equalsIgnoreCase(NOMBRE_CONJ)) {
                estructura.get(16).setValue(pos);
            } else if (splitarray[pos].equalsIgnoreCase(NUM_CASAS)) {
                estructura.get(17).setValue(pos);
            } else if (splitarray[pos].equalsIgnoreCase(PISOS)) {
                estructura.get(18).setValue(pos);
            } else if (splitarray[pos].equalsIgnoreCase(CIUDAD)) {
                estructura.get(19).setValue(pos);
            } else if (splitarray[pos].equalsIgnoreCase(BARRIO)) {
                estructura.get(20).setValue(pos);
            } else if (splitarray[pos].equalsIgnoreCase(ESTRATO)) {
                estructura.get(21).setValue(pos);
            } else if (splitarray[pos].equalsIgnoreCase(VALIDARGEO)) {
                estructura.get(22).setValue(pos);
            } else if (splitarray[pos].equalsIgnoreCase(NODO)) {
                estructura.get(23).setValue(pos);
            }
        }
        for (int p = 0; p < estructura.size(); p++) {
        }
        return messagge;
    }

    /* 
     * Convertir una linea que esta en el orden original al orden en el que deben estar para cumplir con el orden establecido
     * @param line : arreglo de String en el orden en que fue cargado en el archivo original
     * @return lineResult: Cadena de String con los valores en el orden adecuado.
     * 
     * @param listaDatos
     * @return
     */
    public ArrayList<String> convetirPosiciones(ArrayList<String> listaDatos) {
        ArrayList<String> lineResult = new ArrayList<String>(QCOLUMNS);
        String linea = listaDatos.get(0);
        String splitarray[] = linea.split("\t");
        for (int i = 0; i < splitarray.length; i++) {
            if (i == 0) {
                int pos = estructura.get(0).getValue();
                lineResult.add(0, splitarray[pos]);
            } else if (i == 1) {
                int pos = estructura.get(1).getValue();
                lineResult.add(1, splitarray[pos]);
            } else if (i == 2) {
                int pos = estructura.get(2).getValue();
                lineResult.add(2, splitarray[pos]);
            } else if (i == 3) {
                int pos = estructura.get(3).getValue();
                lineResult.add(3, splitarray[pos]);
            } else if (i == 4) {
                int pos = estructura.get(4).getValue();
                lineResult.add(4, splitarray[pos]);
            } else if (i == 5) {
                int pos = estructura.get(5).getValue();
                lineResult.add(5, splitarray[pos]);
            } else if (i == 6) {
                int pos = estructura.get(6).getValue();
                lineResult.add(6, splitarray[pos]);
            } else if (i == 7) {
                int pos = estructura.get(7).getValue();
                lineResult.add(7, splitarray[pos]);
            } else if (i == 8) {
                int pos = estructura.get(8).getValue();
                lineResult.add(8, splitarray[pos]);
            } else if (i == 9) {
                int pos = estructura.get(9).getValue();
                lineResult.add(9, splitarray[pos]);
            } else if (i == 10) {
                int pos = estructura.get(10).getValue();
                lineResult.add(10, splitarray[pos]);
            } else if (i == 11) {
                int pos = estructura.get(11).getValue();
                lineResult.add(11, splitarray[pos]);
            } else if (i == 12) {
                int pos = estructura.get(12).getValue();
                lineResult.add(12, splitarray[pos]);
            } else if (i == 13) {
                int pos = estructura.get(13).getValue();
                lineResult.add(13, splitarray[pos]);
            } else if (i == 14) {
                int pos = estructura.get(14).getValue();
                lineResult.add(14, splitarray[pos]);
            } else if (i == 15) {
                int pos = estructura.get(15).getValue();
                lineResult.add(15, splitarray[pos]);
            } else if (i == 16) {
                int pos = estructura.get(16).getValue();
                lineResult.add(16, splitarray[pos]);
            } else if (i == 17) {
                int pos = estructura.get(17).getValue();
                lineResult.add(17, splitarray[pos]);
            } else if (i == 18) {
                int pos = estructura.get(18).getValue();
                lineResult.add(18, splitarray[pos]);
            } else if (i == 19) {
                int pos = estructura.get(19).getValue();
                lineResult.add(19, splitarray[pos]);
            } else if (i == 20) {
                int pos = estructura.get(20).getValue();
                lineResult.add(20, splitarray[pos]);
            } else if (i == 21) {
                int pos = estructura.get(21).getValue();
                lineResult.add(21, splitarray[pos]);
            } else if (i == 22) {
                int pos = estructura.get(22).getValue();
                lineResult.add(22, splitarray[pos]);
            } else if (i == 23) {
                int pos = estructura.get(23).getValue();
                lineResult.add(23, splitarray[pos]);
            } else if (i == 24) {
                int pos = estructura.get(24).getValue();
                lineResult.add(24, splitarray[pos]);
            }
        }
        return lineResult;
    }

    /**
     * 
     * @return
     */
    public List<ColumnaSolicitudRed> getEstructura() {
        return estructura;
    }

    /**
     * 
     * @param estructura
     */
    public void setEstructura(List<ColumnaSolicitudRed> estructura) {
        this.estructura = estructura;
    }
}
