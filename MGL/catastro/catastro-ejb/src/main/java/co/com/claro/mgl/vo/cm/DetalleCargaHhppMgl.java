package co.com.claro.mgl.vo.cm;

import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import java.util.Date;

/**
 * Guardar el detalle de la carga.
 *
 * Cada vez que se cargue el archivo de Hhpp se debe guardar un detalle de la
 * acción realizada.
 *
 *
 * @author becerraarmr
 *
 * @version 1.0 revisión 1.0
 */
public class DetalleCargaHhppMgl {

    public final static String PROCESADO = "PROCESADO";
    public final static String PROCESADO_INCOMPLETO = "PROCESADO_INCOMPLETO";
    public final static String PROCESADO_SINCAMBIO = "PROCESADO_SIN_CAMBIO";
    public final static String NOPROCESADO = "NO_PROCESADO";
    public final static String PROCESO_NOCTURNO = "PROCESADO_NOCTURNO";
    public final static String PROCESO_NOCTURNO_SINCAMBIO = "PROCESADO_NOCTURNO_SIN_CAMBIO";
    public final static int DETALLEWARNING = 1;
    public final static int DETALLEINFO = 2;
    private String estado;
    //Hhpp que va ser detallado
    private HhppMgl hhppMgl;
    //Detalle del Hhpp una ves realizada la carga
    private String detalle;
    private String detalleInfo;
    private String detalleWarning;
    private CityEntity cityEntity;
    //Fecha de procesamiento del registro
    private Date fechaProcesamiento;
    //Estado Procesao y No_procesado.
    private int row;
    private  String infoOriginal;
    boolean direccionValidada;

    /**
     * @return the hhppMgl
     */
    public HhppMgl getHhppMgl() {
        return hhppMgl;
    }

    /**
     * @param hhppMgl the hhppMgl to set
     */
    public void setHhppMgl(HhppMgl hhppMgl) {
        this.hhppMgl = hhppMgl;
    }

    /**
     * @return the detalle
     */
    public String getDetalle() {
        return detalle;
    }

    /**
     * @param detalle the detalle to set
     */
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    /**
     * @return the fechaProcesamiento
     */
    public Date getFechaProcesamiento() {
        return fechaProcesamiento;
    }

    /**
     * @param fechaProcesamiento the fechaProcesamiento to set
     */
    public void setFechaProcesamiento(Date fechaProcesamiento) {
        this.fechaProcesamiento = fechaProcesamiento;
    }

    /**
     * Establecer si puede actualizar Hhpp
     *
     * Si al ver el estado del proceso se establece si se puede o no actualizar
     * o no el registro.
     *
     * @author becerraarmr
     * @return true si verEstadoProceso es "PROCESADO" y false en caso
     * contrario.
     */
    public boolean puedeActualizar() {
        return (NOPROCESADO.equalsIgnoreCase(estado));
    }

    /**
     * Agregar detalle
     *
     * Se agrega un nuevo detalle y se concatena con el existente.
     *
     * @author becerraarmr
     * @param detalleNuevo descripción del detalle
     * @param tipoDetalle detalle Warning o Infor
     */
    public void addDetalle(String detalleNuevo, int tipoDetalle) {
        if (detalleInfo == null ) {
            detalleInfo = "";
            
        }
        if (detalleWarning == null) {
            detalleWarning = "";
        }
        if (detalle== null) {
            detalle="";
        }
        switch (tipoDetalle) {
            case DETALLEWARNING: {
                detalleWarning = detalle + " [Warning]< " + detalleNuevo + " >; ";
                 detalle = detalleWarning;
                break;
            }
            case DETALLEINFO: {
                detalleInfo = detalle + " [Info]< " + detalleNuevo + " >; ";
                 detalle = detalleInfo;
                break;
            }
        }
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the cityEntity
     */
    public CityEntity getCityEntity() {
        return cityEntity;
    }

    /**
     * @param cityEntity the cityEntity to set
     */
    public void setCityEntity(CityEntity cityEntity) {
        this.cityEntity = cityEntity;
    }

    public String getInfoOriginal() {
        return infoOriginal;
    }

    
    public void setInfoOriginal(String infoOriginal) {
        this.infoOriginal = infoOriginal;
    }
    
    

    public String[] respuestaDirecciones() {
        String existencia = "";
        String nodo1 = "";
        String nodo2 = "";
        String nodo3 = "";
        String nodoDth = "";
        String estratoDir = "";
        String estadoDir = "";
        String confiabilidadDir = "";
        String fuente = "";
        if (cityEntity != null) {
            existencia = cityEntity.getExistencia();
            nodo1 = cityEntity.getNodo1();
            nodo2 = cityEntity.getNodo2();
            nodo3 = cityEntity.getNodo3();
            nodoDth = cityEntity.getNodoDTH();
            estratoDir = cityEntity.getEstratoDir();
            estadoDir = cityEntity.getEstadoDir();
            confiabilidadDir = cityEntity.getConfiabilidadDir();
            fuente = cityEntity.getFuente();
        }
        String[] data = {existencia, nodo1, nodo2, nodo3, nodoDth,
            estratoDir, estadoDir, confiabilidadDir, fuente};
        return data;
    }

    public boolean isDireccionValidada() {
        return direccionValidada;
    }

    public void setDireccionValidada(boolean direccionValidada) {
        this.direccionValidada = direccionValidada;
    }
    
    
    
    
    
}
