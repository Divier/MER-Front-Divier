/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

/**
 * Permite devolver los valores de registros actualizados sobre la tabla
 * de inventarios subedificio tecnologia.
 * @author villamilc
 */
public class CmtResutadoPasaInventariosDto {
    
    int registrosAdd=0;
    
    int registrodDel=0;

    /*
     * permite optener el numero de registros adicionados a la tabla de 
     * inventariso subedificio tecnologia
     * @return int Numero de registros adicionados
     */
    public int getRegistrosAdd() {
        return registrosAdd;
    }

    /*
     * permite cambiar el numero de registros adicionados a la tabla de 
     * inventariso subedificio tecnologia
     * @param registrosAdd numero de registros adicionados
     */
    public void setRegistrosAdd(int registrosAdd) {
        this.registrosAdd = registrosAdd;
    }

    /*
     * permite optener el numero de registros adicionados a la tabla de 
     * inventariso subedificio tecnologia
     * @return int Numero de registros adicionados
     */
    
    public int getRegistrodDel() {
        return registrodDel;
    }
    /*
     * permite cambiar el numero de registros eliminados a la tabla de 
     * inventario subedificio tecnologia
     * @param registrodDel numero de registros elminados
     */

    public void setRegistrodDel(int registrodDel) {
        this.registrodDel = registrodDel;
    }
    
    
}
