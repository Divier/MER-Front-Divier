package co.com.claro.cmas400.ejb.pcmlinterface;

import co.com.claro.cmas400.ejb.core.Array;
import co.com.claro.cmas400.ejb.core.Data;
import co.com.claro.cmas400.ejb.core.Program;
import co.com.claro.cmas400.ejb.core.UsageType;
import java.util.ArrayList;

@Program(programName = "CMEDFARP", documentName = "co.com.claro.cmas400.ejb.pcml.CMEDFARP")
public class CMEDFARP {

    @Data(pcmlName = "NOMBRE", usage = UsageType.INPUTOUTPUT)
    private String NOMBRE = new String();
    @Data(pcmlName = "TIPOERROR", usage = UsageType.INPUTOUTPUT)
    private String TIPOERROR = new String();
    @Data(pcmlName = "ERROR", usage = UsageType.INPUTOUTPUT)
    private String ERROR = new String();
    @Array(pcmlName = "RESPUESTA", size = 500, type = java.lang.String.class, usage = UsageType.INPUTOUTPUT)
    private ArrayList RESPUESTA = new ArrayList();

    public String getTIPOERROR() {
        return TIPOERROR;
    }

    public void setTIPOERROR(String TIPOERROR) {
        this.TIPOERROR = TIPOERROR;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public String getERROR() {
        return ERROR;
    }

    public void setERROR(String ERROR) {
        this.ERROR = ERROR;
    }

    public ArrayList getRESPUESTA() {
        return RESPUESTA;
    }

    public void setRESPUESTA(ArrayList RESPUESTA) {
        this.RESPUESTA = RESPUESTA;
    }
}
