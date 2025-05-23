package co.com.telmex.catastro.services.util;

import co.com.claro.mgl.error.ApplicationException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Clase CrearDirFactBatch
 *
 * @author 	Carlos Villamil
 * @version	1.0
 */
public class manejoArchivos {

    /**
     * 
     * @param rutaArchivos
     * @param rootUpload
     * @param nombreArchivos
     * @param iniciArchivo
     * @param extesionArchivo
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public ArrayList<ArrayList<String>> leerListaArchivos(String rutaArchivos, String rootUpload, ArrayList<String> nombreArchivos,
            final String iniciArchivo, final String extesionArchivo)
            throws FileNotFoundException, IOException {
        ArrayList<ArrayList<String>> resultadoListaArchivos;
        ArrayList<String> contenidoArchivo;
        File dir = new File(rutaArchivos);
        resultadoListaArchivos = new ArrayList<ArrayList<String>>();
        String[] listaArchivos = dir.list(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return ((name.endsWith("." + extesionArchivo)) && name.startsWith(iniciArchivo) && !name.contains("rror") && !name.contains("rocesado"));
            }
        });
        String linea;
        for (int i = 0; i < listaArchivos.length; i++) {
            contenidoArchivo = new ArrayList<String>();
            FileReader fr = null;
            BufferedReader br = null;
            String nameProcesFile = listaArchivos[i];
            File fichero = new File(nameProcesFile);
            nombreArchivos.add(nameProcesFile);
            String archivoCarga = dir + rootUpload + fichero.getName();
            fr = new FileReader(archivoCarga);
            br = new BufferedReader(fr);
            while ((linea = br.readLine()) != null) {
                contenidoArchivo.add(linea);
            }
            resultadoListaArchivos.add(contenidoArchivo);
            br.close();
            fr.close();
        }
        return resultadoListaArchivos;
    }

    /**
     * 
     * @param archivoValidar
     * @param SeparadorColumna
     * @param numeroColumnas
     * @param titulos
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public boolean validarArchivo(ArrayList<String> archivoValidar, String SeparadorColumna, int numeroColumnas, String titulos) throws ApplicationException {

        boolean resultado = true;
        int empesarEnFila = 0;
        if (numeroColumnas < 1) {
            throw new ApplicationException("El numero de columnas no puede ser cero");
        }

        if (titulos != null) {
            empesarEnFila = 1;
            if (titulos.length() == 0) {
                throw new ApplicationException("si el archivo tiene titulo, el valor no puede ser vacio");
            } else {
                String encavezadoArchvo = (String) archivoValidar.get(0);
                if (!encavezadoArchvo.toUpperCase().equals(titulos.toUpperCase())) {
                    return false;
                }
            }
        }


        if (archivoValidar.size() < 1) {
            return false;
        }

        for (int contLinea = empesarEnFila; archivoValidar.size() > contLinea; contLinea++) {
            if (archivoValidar.get(contLinea).toString().length() == 0) {
                return false;
            }
            if (archivoValidar.get(contLinea).toString().split(SeparadorColumna).length != numeroColumnas) {
                return false;
            }
        }
        return resultado;
    }

    /**
     * 
     * @param nombreArchivo
     * @param rutaArchivos
     * @param rootUpload
     * @param archivo
     * @param error
     * @return
     * @throws IOException
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public boolean crearArchivo(String nombreArchivo, String rutaArchivos, String rootUpload,
            ArrayList<String> archivo, String error) throws IOException, ApplicationException {
        FileWriter escrivirArchivo = new FileWriter(rutaArchivos + rootUpload + nombreArchivo);
        BufferedWriter bufferArchivo = new BufferedWriter(escrivirArchivo);
        String message = "";
        if (archivo == null) {
            message = "Error de formato";
        } else {
            if (archivo.isEmpty()) {
                message = "Error de formato";
            }
        }
        if (!message.equals("")) {
            error = error + "" + message;
        }

        Iterator lineas = archivo.iterator();
        if (!error.equals("")) {
            bufferArchivo.write(error + "\n");
        }
        while (lineas.hasNext()) {
            bufferArchivo.write((String) lineas.next() + "\n");//8009877
        }
        bufferArchivo.close();
        escrivirArchivo.close();
        return true;
    }

    /**
     * 
     * @param nombreArchivo
     * @param rutaArchivos
     * @param rootUpload
     * @return
     */
    public boolean eleminarArchivo(String nombreArchivo, String rutaArchivos, String rootUpload) {
        boolean resultado;
        File file = new File(rutaArchivos + rootUpload + nombreArchivo);
        resultado = file.delete();
        return resultado;
    }
}
