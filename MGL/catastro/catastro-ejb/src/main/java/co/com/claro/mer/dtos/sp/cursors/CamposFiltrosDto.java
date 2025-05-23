package co.com.claro.mer.dtos.sp.cursors;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.procesomasivo.CarguesMasivosFacade;
import co.com.claro.mgl.facade.procesomasivo.CarguesMasivosFacadeLocal;
import co.com.claro.mgl.utils.ClassUtils;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase DTO para el listado de Departamentos y ciudades para que
 * el usuario pueda seleccionar en la vista de cargue masivos.
 *
 * @author Henry Sanchez Arango
 * @version 1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CamposFiltrosDto {
    
    private BigDecimal orden;
    private String columna;
    private String valor;
    private Date fecha;
    List<ListaItemsFiltrosDto> listaItemsFIltro = new ArrayList<>();
    private BigDecimal obligatoriedad;
    private String tipo;
    private BigDecimal longitud;
    private String html_e;

    private BigDecimal idColumna;
    private BigDecimal idColumnaPadre;
    private String nombreColumna;
    private String operador;
    private BigDecimal preloadLista;

    private String indicadorOblitoriedad;

    public CamposFiltrosDto(ConFiltrosCursorDto cursorDto) throws ApplicationException {
        this.orden = cursorDto.getOrden();
        this.columna = cursorDto.getColumna();
        this.valor= null;
        this.fecha = null;
        this.obligatoriedad = cursorDto.getObligatoriedad();
        this.tipo = cursorDto.getTipo();
        this.longitud = cursorDto.getLongitud();
        this.html_e = cursorDto.getHtml_e();

        this.idColumna = cursorDto.getIdColumna();
        this.idColumnaPadre = cursorDto.getIdColumnaPadre();
        this.nombreColumna = cursorDto.getNombreColumna();
        this.operador = StringUtils.isNotBlank(cursorDto.getOperador()) ? cursorDto.getOperador() : "=";
        this.indicadorOblitoriedad = (this.obligatoriedad.equals(BigDecimal.ONE)) ? "*" : "";

        final Logger LOGGER = LogManager.getLogger(CamposFiltrosDto.class);

        try {
            if (cursorDto.getPreloadLista().equals(BigDecimal.ONE)){
                consultarItemsListaFiltros();
            }
        } catch (Exception ex) {
            String msgError = "Ocurrió en error en : " + ClassUtils.getCurrentMethodName(this.getClass());
            LOGGER.error(msgError, ex);
            throw new ApplicationException(ex.getMessage());
        }
    }

    private void consultarItemsListaFiltros() throws ApplicationException {
        CarguesMasivosFacadeLocal carguesMasivosFacade = new CarguesMasivosFacade();
        this.listaItemsFIltro = carguesMasivosFacade.consultarListasFiltros(idColumna, null);

    }

}
