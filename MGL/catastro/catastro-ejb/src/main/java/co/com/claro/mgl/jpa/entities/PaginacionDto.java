/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import co.com.claro.mgl.rest.dtos.NodoMerDto;
import java.util.List;

/**
 *
 * @author ADMIN
 * @param <C>
 */
public class PaginacionDto<C> {
    private long numPaginas;
    private List<C> listResultado;
    private List<NodoMerDto> listResultadoNodoMer;

    public long getNumPaginas() {
        return numPaginas;
    }

    public void setNumPaginas(long numPaginas) {
        this.numPaginas = numPaginas;
    }

    public List<C> getListResultado() {
        return listResultado;
    }

    public void setListResultado(List<C> listResultado) {
        this.listResultado = listResultado;
    }

    public List<NodoMerDto> getListResultadoNodoMer() {
        return listResultadoNodoMer;
    }

    public void setListResultadoNodoMer(List<NodoMerDto> listResultadoNodoMer) {
        this.listResultadoNodoMer = listResultadoNodoMer;
    }
    
    
}
