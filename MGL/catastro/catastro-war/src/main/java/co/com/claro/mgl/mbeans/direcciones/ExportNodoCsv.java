package co.com.claro.mgl.mbeans.direcciones;

import co.com.claro.mgl.dtos.CmtFiltroConsultaNodosDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.NodoMglFacadeLocal;
import co.com.claro.mgl.rest.dtos.NodoMerDto;
import co.com.telmex.catastro.util.FacesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.PrimeFaces;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Dayver De la hoz
 * Se crea clase para separar la lógica del exporte de datos de Nodos
 */
public class ExportNodoCsv extends Thread implements Serializable {

    private static final Logger LOGGER = LogManager.getLogger(ExportNodoCsv.class);

    private NodoMglBean nodoMglBean;
    private String[] NOM_COLUMNAS;
    private NodoMglFacadeLocal nodoMglFacade;
    private CmtFiltroConsultaNodosDto cmtFiltroConsultaNodosDto;
    private int limitIni;
    private int limit;
    private int total;

    /**
     * Constructor de la clase
     * @param nomColumnas
     * @param nodoMglFacade
     * @param cmtFiltroConsultaNodosDto
     * @param limitIni
     * @param limit
     * @param total
     * @param nodoMglBean
     */
    public ExportNodoCsv(String[] nomColumnas, NodoMglFacadeLocal nodoMglFacade, CmtFiltroConsultaNodosDto cmtFiltroConsultaNodosDto, int limitIni, int limit, int total, NodoMglBean nodoMglBean) {
        this.NOM_COLUMNAS = nomColumnas;
        this.nodoMglFacade = nodoMglFacade;
        this.cmtFiltroConsultaNodosDto = cmtFiltroConsultaNodosDto;
        this.limitIni = limitIni;
        this.limit = limit;
        this.total = total;
        this.nodoMglBean = nodoMglBean;
    }

    public String[] getNOM_COLUMNAS() {
        return NOM_COLUMNAS;
    }

    public void setNOM_COLUMNAS(String[] NOM_COLUMNAS) {
        this.NOM_COLUMNAS = NOM_COLUMNAS;
    }

    public NodoMglFacadeLocal getNodoMglFacade() {
        return nodoMglFacade;
    }

    public void setNodoMglFacade(NodoMglFacadeLocal nodoMglFacade) {
        this.nodoMglFacade = nodoMglFacade;
    }

    /**
     * Después de instanciar el objeto, se ejecuta esta función para generar exporte de datos
     */
    @Override
    public void run(){
        try {

            if (nodoMglBean.isFlag()){
                String msn = "Ya se encuentra en proceso una descarga, intente en unos minutos. ";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
            }else {

                nodoMglBean.setFlag(true);
                int init = 0;
                int fin = 0;

                if (total > limitIni) {
                    fin = limitIni;
                } else {
                    fin = total;
                }

                StringBuilder sb = new StringBuilder();
                byte[] csvData;
                SimpleDateFormat formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss");

                for (int j = 0; j < NOM_COLUMNAS.length; j++) {
                    sb.append(NOM_COLUMNAS[j]);
                    if (j < NOM_COLUMNAS.length) {
                        sb.append(";");
                    }
                }
                sb.append("\n");

                String todayStr = formato.format(new Date());
                String fileName = "NodosMGLConsulte" + "_" + todayStr + "." + "csv";
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse response1 = (HttpServletResponse) fc.getExternalContext().getResponse();
                response1.setHeader("Content-disposition", "attached; filename=\""
                        + fileName + "\"");
                response1.setContentType("application/octet-stream;charset=UTF-8;");
                response1.setCharacterEncoding("UTF-8");

                csvData = sb.toString().getBytes();
                response1.getOutputStream().write(csvData);

                boolean continuar = true;

                while (continuar) {

                    List<NodoMerDto> listaNodos = nodoMglFacade.findAllPaginadoExport(init, fin, cmtFiltroConsultaNodosDto, false);

                    if (listaNodos != null && !listaNodos.isEmpty()) {

                        for (NodoMerDto nodoMer : listaNodos) {

                            if (sb.toString().length() > 1) {
                                sb.delete(0, sb.toString().length());
                            }

                            sb.append(nodoMer.getDepartamento());
                            sb.append(";");
                            sb.append(nodoMer.getCiudad());
                            sb.append(";");
                            sb.append(nodoMer.getCentroPoblado());
                            sb.append(";");
                            sb.append(nodoMer.getCodigo());
                            sb.append(";");
                            sb.append(nodoMer.getNombre());
                            sb.append(";");
                            sb.append(nodoMer.getComunidad());
                            sb.append(";");
                            sb.append(nodoMer.getDivision());
                            sb.append(";");
                            sb.append(nodoMer.getArea());
                            sb.append(";");
                            sb.append(nodoMer.getDistrito());
                            sb.append(";");
                            sb.append(nodoMer.getZona());
                            sb.append(";");
                            sb.append(nodoMer.getUnidadGestion());
                            sb.append(";");
                            sb.append(nodoMer.getTipoNodo());
                            sb.append(";");
                            sb.append(nodoMer.getEstadoNodo());
                            sb.append(";");
                            sb.append(nodoMer.getUsuarioCreacion());
                            sb.append(";");
                            sb.append(nodoMer.getFechaCreacion());
                            sb.append(";");
                            sb.append(nodoMer.getFechaModificacion());
                            sb.append(";");
                            sb.append(nodoMer.getFechaApertura());
                            sb.append(";");
                            sb.append("\n");
                            csvData = sb.toString().getBytes();
                            response1.getOutputStream().write(csvData);
                            response1.getOutputStream().flush();
                            response1.flushBuffer();
                        }
                    }

                    if (fin >= total) {
                        continuar = false;
                    }

                    init = fin;
                    fin = fin + limit;
                }

                response1.getOutputStream().close();
                fc.responseComplete();
                PrimeFaces.current().ajax().update("formPrincipal:panelButom");
                nodoMglBean.setFlag(false);
            }
        } catch (ApplicationException e) {
            nodoMglBean.setFlag(false);
            FacesUtil.mostrarMensajeError("Error en exportExcel. ", e, LOGGER);
        } catch (Exception e) {
            nodoMglBean.setFlag(false);
            FacesUtil.mostrarMensajeError("Error en exportExcel. ", e, LOGGER);
        }
    }
}
