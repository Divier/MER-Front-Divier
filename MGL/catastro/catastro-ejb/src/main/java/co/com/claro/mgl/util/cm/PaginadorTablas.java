package co.com.claro.mgl.util.cm;

import co.com.claro.mgl.constantes.cm.MensajeTipoValidacion;

/**
 * Administra la paginación de las páginas de manera genérica.
 * Maneja la paginación de manera genérica para cualquier tabla en la aplicación.
 * 
 * @author Ricardo Cortés Rodriguez
 * @version 1.0 revision 11/05/2017.
 */
public class PaginadorTablas {
    private Integer totalPaginas;
    private Integer pagina;
    private Integer numeroInicio;
    private Boolean esUltimaPagina;
    
    /**
     * Generar la pagina de desplazamiento y llenea los atributos de esta clase 
     * para su paginación.Metodo encargado de realizar la paginación de manera genérica para 
 cualquier tabla de la aplicación.
     * 
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param paginaActual Entero de la pagina actual en la cual se encuentra el 
     * paginador.
     * @param cantidadRegistros Tiene la cantidad de registros que contiene la 
     * consulta realizada en la tabla.
     * @param movimiento Dirección de donde se quiere ir en la página.
     * @return 
     */
    public Integer paginar(Integer paginaActual, Integer cantidadRegistros, String movimiento){
            Integer registrosPorPagina = new Integer(MensajeTipoValidacion.REGISTROS_POR_PAGINA.getValor());
            totalPaginas = (int) ((cantidadRegistros % registrosPorPagina != 0)
                    ? (cantidadRegistros / registrosPorPagina) + 1 : cantidadRegistros / registrosPorPagina);
            
            this.esUltimaPagina = false;
            
        if (movimiento.equals(MensajeTipoValidacion.SIGUIENTE.getValor())) {
            paginaActual ++;

            if (paginaActual >= totalPaginas) {
                this.esUltimaPagina = true;
                paginaActual = totalPaginas;
            }
            
        }else if(movimiento.equals(MensajeTipoValidacion.ATRAS.getValor())){
            paginaActual --;
            this.esUltimaPagina = false;
            
        }else if(movimiento.equals(MensajeTipoValidacion.PRIMERA.getValor())){
            paginaActual = 1;
            this.esUltimaPagina = false;
        
        }else if (movimiento.equals(MensajeTipoValidacion.ULTIMA.getValor())) {
            this.esUltimaPagina = true;
            paginaActual = totalPaginas;
            
        }else if(movimiento.equals(MensajeTipoValidacion.IR_A_PAGINA.getValor())){
            
            if ( paginaActual >= totalPaginas) {
                this.esUltimaPagina = true;
                paginaActual = totalPaginas;
            }else if (paginaActual < 1) {
                paginaActual = 1;
                this.esUltimaPagina = false;
            }else {
                this.esUltimaPagina = false;
            }
        }
        
        this.pagina = paginaActual;
        
        numeroInicio = 0;
        if (paginaActual > 1) {
            numeroInicio = (registrosPorPagina * (paginaActual - 1));
        }
        
        if (this.numeroInicio < 0) {
            this.numeroInicio = 0;
        }
        
        if (this.numeroInicio >= cantidadRegistros) {
            this.numeroInicio -= cantidadRegistros;
        }
        
        return this.numeroInicio;
    }

    /**
     * Obtener el total de paginas que contiene la tabla según criterios de búsqueda.
     * Obtiene el total de paginas que contiene la tabla según criterios de búsqueda.
     * 
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return El total de paginas que contiene la tabla según criterios de búsqueda.
     */
    public Integer getTotalPaginas() {
        return totalPaginas;
    }

    /**
     * Cambiar el total de paginas que contiene la tabla según criterios de búsqueda.
     * Cambia el total de paginas que contiene la tabla según criterios de búsqueda.
     * 
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param totalPaginas El total de paginas que contiene la tabla según criterios de búsqueda.
     */
    public void setTotalPaginas(Integer totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    /**
     * Obtener la pagina actual de la tabla.
     * Obtiene la pagina actual de la tabla.
     * 
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return La pagina actual de la tabla
     */
    public Integer getPagina() {
        return pagina;
    }

    /**
     * Cambiar la pagina actual de la tabla.
     * Cambia la pagina actual de la tabla.
     * 
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param pagina La pagina actual de la tabla.
     */
    public void setPagina(Integer pagina) {
        this.pagina = pagina;
    }

    /**
     * Obtener el número de inicio del registro para traer el segmento de datos.
     * Obtiene el número de inicio del registro para traer el segmento de datos.
     * 
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return El número de inicio del registro para traer el segmento de datos.
     */
    public Integer getNumeroInicio() {
        return numeroInicio;
    }

    /**
     * Cambiar el número de inicio del registro para traer el segmento de datos.
     * Cambia el número de inicio del registro para traer el segmento de datos.
     * 
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param numeroInicio El número de inicio del registro para traer el segmento de datos.
     */
    public void setNumeroInicio(Integer numeroInicio) {
        this.numeroInicio = numeroInicio;
    }

    /**
     * Obtener true si se encuentra en la última página de la tabla.
     * Obtiene true si se encuentra en la última página de la tabla.
     * 
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return True si se encuentra en la última página de la tabla.
     */
    public Boolean getEsUltimaPagina() {
        return esUltimaPagina;
    }

    /**
     * Cambiar true si se encuentra en la última página de la tabla.
     * Cambia true si se encuentra en la última página de la tabla.
     * 
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param esUltimaPagina True si se encuentra en la última página de la tabla.
     */
    public void setEsUltimaPagina(Boolean esUltimaPagina) {
        this.esUltimaPagina = esUltimaPagina;
    }
    
}
