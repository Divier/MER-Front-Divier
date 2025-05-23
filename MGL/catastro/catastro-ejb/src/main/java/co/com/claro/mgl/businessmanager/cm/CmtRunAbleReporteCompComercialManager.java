/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dtos.CmtReporteCompromisoComercialDto;
import co.com.claro.mgl.dtos.CuentaMatrizCompComercialDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;


/**
 *
 * @author cardenaslb
 */
public class CmtRunAbleReporteCompComercialManager implements Runnable{
    
    private static final Logger LOGGER = LogManager.getLogger(CmtRunAbleReporteCompComercialManager.class);

    String tipoReporte;
    Date fechaInicio;
    Date fechaFin;
    BigDecimal estrato;
     BigDecimal tecnologia;
    String nodo;
    String usuario;
    Timer timer;

    public CmtRunAbleReporteCompComercialManager(BigDecimal tecnologia, 
            BigDecimal estrato,String nodo,Date fechaInicio,Date fechaFinal, String usuarioVT) {
        this.tecnologia = tecnologia;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFinal;
        this.estrato = estrato;
        this.nodo = nodo;
        this.usuario = usuarioVT;
        
        this.timer = new Timer();
    }

    @Override
    public void run() {
        List<CuentaMatrizCompComercialDto> listaCuentaMatrizCompComercialDto = new ArrayList<>();
        try {
            CmtReporteCompromisoComercialDto.cleanBeforeStart();
            CmtReporteCompromisoComercialDto.startProcess(usuario);
        } catch (ApplicationException ex) {
            CmtReporteCompromisoComercialDto.setMessage(ex.getMessage());
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
        try {
            CmtReporteCompromisoComercialDto.endProcess(listaCuentaMatrizCompComercialDto);
        } catch (ApplicationException ex) {
            CmtReporteCompromisoComercialDto.setMessage(ex.getMessage());
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
    }
}
