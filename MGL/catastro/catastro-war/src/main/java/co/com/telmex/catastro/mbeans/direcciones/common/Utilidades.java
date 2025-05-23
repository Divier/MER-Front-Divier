/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.telmex.catastro.mbeans.direcciones.common;

import co.com.claro.direcciones.delegate.DelegateNodos;
import co.com.claro.direcciones.facade.NodosBeanFacadeEJBRemote;
import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.services.util.ConsultaMasivaTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author user
 */
public final class Utilidades {
    
    private static final Logger LOGGER = LogManager.getLogger(Utilidades.class);

    public static void cargarNodo(List<SelectItem> list, String pais, String departamento, String ciudad, String tipoNodo) throws ApplicationException {
        try {
            NodosBeanFacadeEJBRemote dn = DelegateNodos.getNodoBeanFacadeEjb();
            List<co.com.claro.direcciones.entities.Nodo> listNodo = null;
            listNodo = dn.listaNodosGeograficoPolitico(pais, departamento, ciudad, tipoNodo);
            for (co.com.claro.direcciones.entities.Nodo nodo : listNodo) {
                SelectItem item123;
                item123 = new SelectItem();
                item123.setValue(nodo.getNodo());
                item123.setLabel(nodo.getNodo());
                list.add(item123);
            }
        } catch (NamingException ex) {
            String msg = "Error al momento de cargar el nodo. EX000:";
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de cargar el nodo. EX000: " + ex.getMessage(), ex);
        } catch (Exception ex) {
            String msg = "Error al momento de cargar el nodo. EX000:";
            LOGGER.error(msg);
            throw new ApplicationException("Error al momento de cargar el nodo. EX000: " + ex.getMessage(), ex);
        }
    }

    public static boolean isVacioONulo(String cadena) {
        return (cadena == null || cadena.isEmpty());
    }

    public static final void doExportSelectedDataToCSV(List<ConsultaMasivaTable> listConsultamasiva, boolean incluidId) {
        final StringBuffer sb = new StringBuffer();
        generarCabecera(sb, incluidId);
        for (ConsultaMasivaTable data : listConsultamasiva) {
            if (incluidId) {
                sb.append((("".equals(data.getCm_idDireccion())) || (data.getCm_idDireccion() == null)) ? "" : data.getCm_idDireccion());
                sb.append(Constantes.SEPARATOR);
            }
            sb.append((("".equals(data.getCm_ciudad())) || (data.getCm_ciudad() == null)) ? "" : data.getCm_ciudad());
            sb.append(Constantes.SEPARATOR);
            sb.append((("".equals(data.getCm_barrio())) || (data.getCm_barrio() == null)) ? "" : data.getCm_barrio());
            sb.append(Constantes.SEPARATOR);
            sb.append(data.getCm_direccion());
            sb.append(Constantes.SEPARATOR);
            sb.append(data.getOrigen());
            sb.append(Constantes.SEPARATOR);
            //Inicio Modificacion Ivan Turriago
            String nivelSocioEconomico = data.getCm_nivelSocio();
            sb.append((nivelSocioEconomico == null || nivelSocioEconomico.isEmpty()) ? "" : nivelSocioEconomico);
            sb.append(Constantes.SEPARATOR);
            String estrato = data.getCm_estrato();
            sb.append((estrato == null || estrato.isEmpty()) ? "" : estrato);
            sb.append(Constantes.SEPARATOR);
            String actividadEconomica = data.getActividadEconomica();
            sb.append((actividadEconomica == null || actividadEconomica.isEmpty()) ? "" : actividadEconomica);
            sb.append(Constantes.SEPARATOR);
            String codManzana = data.getCmDirManzanaCatastral();
            sb.append((codManzana == null || codManzana.isEmpty()) ? "" : codManzana);
            sb.append(Constantes.SEPARATOR);
            String direccionAlterna = data.getDireccionAlterna();
            sb.append((direccionAlterna == null || direccionAlterna.isEmpty()) ? "" : direccionAlterna);
            sb.append(Constantes.SEPARATOR);
            String existencia = data.getExistencia();
            sb.append((existencia == null || existencia.isEmpty()) ? "" : existencia);
            sb.append(Constantes.SEPARATOR);
            String cm_confiabilidad = data.getCm_confiabilidad();
            sb.append((cm_confiabilidad == null || cm_confiabilidad.isEmpty()) ? "" : cm_confiabilidad);
            //FIN Modificacion Ivan Turriago

            //INICIO Direcciones face I Carlos Vilamil 2013-05-24 V 1.1
            sb.append(Constantes.SEPARATOR);
            sb.append((("".equals(data.getNodoUno())) || (data.getNodoUno() == null)) ? "" : data.getNodoUno());
            sb.append(Constantes.SEPARATOR);
            sb.append((("".equals(data.getNodoDos())) || (data.getNodoDos() == null)) ? "" : data.getNodoDos());
            sb.append(Constantes.SEPARATOR);
            sb.append((("".equals(data.getNodoTres())) || (data.getNodoTres() == null)) ? "" : data.getNodoTres());
            //FIN Direcciones face I Carlos Vilamil 2013-05-24 V 1.1
            sb.append("\n");
        }
        byte[] csvData = null;
        // in case you need some specific charset : 
        // here is an exemple with some standard utf-8 

        try {
            csvData = sb.toString().getBytes("utf-8");
        } catch (final UnsupportedEncodingException e1) {
            String msg = e1.getMessage();
            LOGGER.error(msg);
        } catch (Exception e1) {
             String msg = e1.getMessage();
            LOGGER.error(msg);
        }
        final FacesContext context = FacesContext.getCurrentInstance();
        final HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.setHeader("Content-disposition", "attached; filename=\"consultaMasiva.csv\"");
        // provided you want to ensure the file will be downloaded 
        // up to you to do it another way if you don't mind that the navigator 
        // manages the file on itself
        response.setContentType("application/force.download");
        try {
            response.getOutputStream().write(csvData);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            context.responseComplete();
            //Telling the framework that the response has been completed. 
            FacesContext.getCurrentInstance().responseComplete();
        } catch (final IOException e) {
            LOGGER.error(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

    }

    private static void generarCabecera(StringBuffer sb, boolean incluirId) {

        if (incluirId) {
            sb.append(Constantes.ID);
            sb.append(Constantes.SEPARATOR);
        }

        sb.append(Constantes.CIUDAD);
        sb.append(Constantes.SEPARATOR);
        sb.append(Constantes.BARRIO);
        sb.append(Constantes.SEPARATOR);
        sb.append(Constantes.DIRECCION);
        sb.append(Constantes.SEPARATOR);
        sb.append(Constantes.ORIGEN);
        sb.append(Constantes.SEPARATOR);
        sb.append(Constantes.NIVEL_ECONOMICO);
        sb.append(Constantes.SEPARATOR);
        sb.append(Constantes.ESTRATO);
        sb.append(Constantes.SEPARATOR);
        sb.append(Constantes.ACTIVIDAD_ECONOMICA);
        sb.append(Constantes.SEPARATOR);
        sb.append(Constantes.COD_MANZANA);
        sb.append(Constantes.SEPARATOR);
        sb.append(Constantes.DIRECCION_ALTERNA);
        sb.append(Constantes.SEPARATOR);
        sb.append(Constantes.EXISTENCIA);
        sb.append(Constantes.SEPARATOR);
        sb.append(Constantes.CONFIABILIDAD);
        sb.append(Constantes.SEPARATOR);
        sb.append(Constantes.NODOUNO);
        sb.append(Constantes.SEPARATOR);
        sb.append(Constantes.NODODOS);
        sb.append(Constantes.SEPARATOR);
        sb.append(Constantes.NODOTRES);
        sb.append("\n");
    }
}
