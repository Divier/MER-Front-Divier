package co.com.telmex.catastro.mbeans.comun;

/**
 * Clase ConstantSystem 
 *
 * @author 	Ana María Malambo
 * @version	1.0
 */
public class ConstantSystem {

    private static String filePathSavedFiles = "C://Users//alquiler//Desktop//SavedFiles//";
    private static String filePathMistakesFiles = "C:/Users/alquiler/Desktop/ReportFiles/";
    private static int QCOLUMNSPLANRED = 22;
    private static int QCOLUMNSPLANREDLOAD = 24;
    private static int QCOLUMNSMASIVA = 4;
    private static int QCOLUMNSMARCASMASIVA = 6;
    private static String DEPARTAMENTO = "DEPARTAMENTO";
    private static String CIUDAD = "CIUDAD";
    private static String BARRIO = "BARRIO";
    private static String DIRECCION = "DIRECCION";
    private static String MARCA = "MARCA";
    private static String ACCION = "ACCION";
    private static String VALIDAR = "VALIDAR";
    private static String NIVSOCECONOMICO = "NIVSOCECONOMICO";

    /**
     * 
     * @param propertyName
     * @return
     */
    public static String cargarPropiedad(String propertyName) {
        if (propertyName.equalsIgnoreCase("filePathSavedFiles")) {
            return filePathSavedFiles;
        } else if (propertyName.equalsIgnoreCase("QCOLUMNSPLANRED")) {
            return String.valueOf(QCOLUMNSPLANRED);
        } else if (propertyName.equalsIgnoreCase("QCOLUMNSPLANREDLOAD")) {
            return String.valueOf(QCOLUMNSPLANREDLOAD);
        } else if (propertyName.equalsIgnoreCase("filePathMistakesFiles")) {
            return String.valueOf(filePathMistakesFiles);
        } else if (propertyName.equalsIgnoreCase("QCOLUMNSMARCASMASIVA")) {
            return String.valueOf(QCOLUMNSMARCASMASIVA);
        } else if (propertyName.equalsIgnoreCase("QCOLUMNSMASIVA")) {
            return String.valueOf(QCOLUMNSMASIVA);
        } else if (propertyName.equalsIgnoreCase("DEPARTAMENTO")) {
            return String.valueOf(DEPARTAMENTO);
        } else if (propertyName.equalsIgnoreCase("CIUDAD")) {
            return String.valueOf(CIUDAD);
        } else if (propertyName.equalsIgnoreCase("BARRIO")) {
            return String.valueOf(BARRIO);
        } else if (propertyName.equalsIgnoreCase("DIRECCION")) {
            return String.valueOf(DIRECCION);
        } else if (propertyName.equalsIgnoreCase("MARCA")) {
            return String.valueOf(MARCA);
        } else if (propertyName.equalsIgnoreCase("ACCION")) {
            return String.valueOf(ACCION);
        }
        if (propertyName.equalsIgnoreCase("NIVSOCECONOMICO")) {
            return String.valueOf(NIVSOCECONOMICO);

        }
        if (propertyName.equalsIgnoreCase("VALIDAR")) {
            return String.valueOf(VALIDAR);
        } else {
            return "ERROR";
        }
    }
}
