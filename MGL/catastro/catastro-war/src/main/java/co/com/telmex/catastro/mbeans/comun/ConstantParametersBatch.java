package co.com.telmex.catastro.mbeans.comun;

/**
 * Clase ConstantParametersBatch
 * Extiende de HttpServlet
 *
 * @author 	Ana María Malambo
 * @version	1.0
 */
public class ConstantParametersBatch {

    private static String filePathSavedFiles = System.getProperty("DirectoryFilesLoadBatch");
    private static String filePathMistakesFiles = System.getProperty("DirectoryFilesReportBatch");
    private static int QCOLUMNSBATCH = 7;
    private static int QCOLUMNSBATCHLOG = 12;

    /**
     * 
     * @param propertyName
     * @return
     */
    public static String cargarPropiedad(String propertyName) {
        if (propertyName.equalsIgnoreCase("filePathSavedFiles")) {
            return filePathSavedFiles;
        } else if (propertyName.equalsIgnoreCase("QCOLUMNSBATCH")) {
            return String.valueOf(QCOLUMNSBATCH);
        } else if (propertyName.equalsIgnoreCase("QCOLUMNSBATCHLOG")) {
            return String.valueOf(QCOLUMNSBATCHLOG);
        } else if (propertyName.equalsIgnoreCase("filePathMistakesFiles")) {
            return String.valueOf(filePathMistakesFiles);
        } else {
            return "ERROR";
        }
    }
}
