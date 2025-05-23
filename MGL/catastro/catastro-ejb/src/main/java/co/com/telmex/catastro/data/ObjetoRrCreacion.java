package co.com.telmex.catastro.data;

/**
 * Clase ObjetoRrCreacion
 * Objeto de negocio que guarda la información para el registro que se debe escribir para el archivo RR de creeación de HHPP
 * Implementa Serialización.
 *
 * @author 	Nataly Orozco Torres
 * @version	1.0
 */
public class ObjetoRrCreacion {

    //Division de la ciudad
    private String division;
    //comunidad del nodo
    private String comunidad;
    //CALLE + COMPLEMENTO + UBICACIÓN CARDINAL CALLE+ UBICACIÓN CARDINAL PLACA
    private String calle;
    //placa
    private String placa;
    //apto
    private String apto;
    //Geografico de la solicitud o NG si es nulo
    private String barrio;
    //999 por defecto
    private String vendedor;
    //estrato si lo tenemos, si no nivel socioeconomico
    private String estrato;
    //zipcode de la comunidad
    private String zipCode;
    //TIPO hhpp
    private String tipoUnidad;
    //CIUDAD - NOMBRE MUNICIPIO
    private String grid;
    //codigo tipo conexion
    private String tipoAcometida;
    //R6
    private String cable;
    //vacio
    private String propCable;
    //vacio
    private String campo1;
    //vacio
    private String campo2;
    //fecha del sistema en formato AAAAMMDD
    private String fecha;
    //head end del nodo
    private String codigoHeadEnd;
    //ND
    private String campoNd;
    //codigo nodo
    private String codigoNodo;
    //vacio
    private String spigot;
    //direccion alterna estandarizada
    private String dirAlterna;
    //localidad , NG si no tiene valor
    private String localidad;
    //lado Mz
    private String ladoMz;
    //manzana catastral o NG si no tiene valor
    private String manzana;
    //ID_SOLICITUD + NOMBRE_SOLICITANTE
    private String notaLinea1;
    //AREA SOLICITANTE
    private String notaLinea2;
    //"NODO" + CODIGO_NODO
    private String notaLinea3;
    //VACIO
    private String notaLinea4;
    //VACIO
    private String notaLinea5;
    //0
    private String campo3;
    //VACIO
    private String campo4;

    /**
     * Constructor
     */
    public ObjetoRrCreacion() {
    }

    /**
     * Obtiene el apto.
     * @return Cadena con el Apto.
     */
    public String getApto() {
        return apto;
    }

    /**
     * Establece el apto.
     * @param apto Cadena con el apto.
     */
    public void setApto(String apto) {
        this.apto = apto;
    }

    /**
     * Obtiene el barrio
     * @return Cadena con el barrio.
     */
    public String getBarrio() {
        return barrio;
    }

    /**
     * Establece el barrio
     * @param barrio Cadena con el barrio.
     */
    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    /**
     * Obtiene el tipo de cable empleado.
     * @return Cadena con el tipo de cable.
     */
    public String getCable() {
        return cable;
    }

    /**
     * Establece el tipo de Cable
     * @param cable Cadena con el tipo de cable.
     */
    public void setCable(String cable) {
        this.cable = cable;
    }

    /**
     * Obtiene CALLE
     * @return Cadena con CALLE + COMPLEMENTO + UBICACIÓN CARDINAL CALLE+ UBICACIÓN CARDINAL PLACA
     */
    public String getCalle() {
        return calle;
    }

    /**
     * Establece CALLE 
     * @param calle Cadena con CALLE + COMPLEMENTO + UBICACIÓN CARDINAL CALLE+ UBICACIÓN CARDINAL PLACA
     */
    public void setCalle(String calle) {
        this.calle = calle;
    }

    /**
     * Obtiene el Campo1 (Vacio)
     * @return Cadena con el Campo 1
     */
    public String getCampo1() {
        return campo1;
    }

    /**
     * Establece el Campo 1
     * @param campo1 Cadena con el Campo 1
     */
    public void setCampo1(String campo1) {
        this.campo1 = campo1;
    }

    /**
     * Obtiene el Campo2 (Vacio)
     * @return Cadena con el Campo 2
     */
    public String getCampo2() {
        return campo2;
    }

    /**
     * Establece el Campo 2
     * @param campo2 Cadena con el Campo 2
     */
    public void setCampo2(String campo2) {
        this.campo2 = campo2;
    }

    /**
     * Obtiene el Campo 3 (0)
     * @return Cadena con el Campo e (0)
     */
    public String getCampo3() {
        return campo3;
    }

    /**
     * Establece el Campo 3 (0)
     * @param campo3 Cadena con el Campo 3 (0)
     */
    public void setCampo3(String campo3) {
        this.campo3 = campo3;
    }

    /**
     * Obtiene el Campo 4 (Vacio)
     * @return Cadena con el Campo 4
     */
    public String getCampo4() {
        return campo4;
    }

    /**
     * Establece el Campo 4 
     * @param campo4 Cadena con el campo 4
     */
    public void setCampo4(String campo4) {
        this.campo4 = campo4;
    }

    /**
     * Obtiene el campo ND 
     * @return Cadena con el campo ND
     */
    public String getCampoNd() {
        return campoNd;
    }

    /**
     * Establece el campo ND
     * @param campoNd Cadena con el campo ND
     */
    public void setCampoNd(String campoNd) {
        this.campoNd = campoNd;
    }

    /**
     * Obtiene el código head end del nodo
     * @return Cadena con el código head end del nodo
     */
    public String getCodigoHeadEnd() {
        return codigoHeadEnd;
    }

    /**
     * Establece el código head end del nodo
     * @param codigoHeadEnd
     */
    public void setCodigoHeadEnd(String codigoHeadEnd) {
        this.codigoHeadEnd = codigoHeadEnd;
    }

    /**
     * Obtiene el código del nodo
     * @return Cadena con el código del nodo
     */
    public String getCodigoNodo() {
        return codigoNodo;
    }

    /**
     * Establece el código del nodo
     * @param codigoNodo Cadena con el código del nodo
     */
    public void setCodigoNodo(String codigoNodo) {
        this.codigoNodo = codigoNodo;
    }

    /**
     * Obtiene la comunidad
     * @return Cadena con la comunidad.
     */
    public String getComunidad() {
        return comunidad;
    }

    /**
     * Establece la comunidad.
     * @param comunidad Cadena con la comunidad.
     */
    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    /**
     * Obtiene la dirección alterna.
     * @return Cadena con la dirección alterna.
     */
    public String getDirAlterna() {
        return dirAlterna;
    }

    /**
     * Establece la dirección alterna.
     * @param dirAlterna Cadena con la dirección alterna.
     */
    public void setDirAlterna(String dirAlterna) {
        this.dirAlterna = dirAlterna;
    }

    /**
     * Obtiene la División
     * @return Cadena con la división
     */
    public String getDivision() {
        return division;
    }

    /**
     * Establece la División
     * @param division Cadena con la división.
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Obtiene el estrato.
     * @return Cadena con el estrato.
     */
    public String getEstrato() {
        return estrato;
    }

    /**
     * Establece el estrato.
     * @param estrato Cadena con el estrato.
     */
    public void setEstrato(String estrato) {
        this.estrato = estrato;
    }

    /**
     * Obtiene fecha del sistema en formato AAAAMMDD
     * @return Cadena con la fecha del sistema en formato AAAAMMDD
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha del sistema en formato AAAAMMDD
     * @param fecha Cadena con la fecha del sistema en formato AAAAMMDD
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene el nombre de la Ciudad - Municipio
     * @return Cadena con el nombre de la Ciudad - Municipio
     */
    public String getGrid() {
        return grid;
    }

    /**
     * Establece el nombre de la Ciudad - Municipio
     * @param grid Cadena con el nombre de la ciudad - Municipio
     */
    public void setGrid(String grid) {
        this.grid = grid;
    }

    /**
     * Obtiene la información del lado Mz
     * @return Cadena con el Lado Mz
     */
    public String getLadoMz() {
        return ladoMz;
    }

    /**
     * Establece el lado Mz.
     * @param ladoMz Cadena con el lado Mz.
     */
    public void setLadoMz(String ladoMz) {
        this.ladoMz = ladoMz;
    }

    /**
     * Obtiene la localidad
     * @return Cadena con la localidad , NG si no tiene valor
     */
    public String getLocalidad() {
        return localidad;
    }

    /**
     * Establece la localidad.
     * @param localidad Cadena con la localidad.
     */
    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    /**
     * Obtiene la Manzana
     * @return Cadena con la manzana catastral o NG si no tiene valor
     */
    public String getManzana() {
        return manzana;
    }

    /**
     * Establece la manzana.
     * @param manzana Cadena con la manzana.
     */
    public void setManzana(String manzana) {
        this.manzana = manzana;
    }

    /**
     * Obtiene la información ID_SOLICITUD + NOMBRE_SOLICITANTE
     * @return Cadena con la información ID_SOLICITUD + NOMBRE_SOLICITANTE
     */
    public String getNotaLinea1() {
        return notaLinea1;
    }

    /**
     * Establece la nota ID_SOLICITUD + NOMBRE_SOLICITANTE
     * @param notaLinea1 Cadena con el ID_SOLICITUD + NOMBRE_SOLICITANTE
     */
    public void setNotaLinea1(String notaLinea1) {
        this.notaLinea1 = notaLinea1;
    }

    /**
     * Obtiene el área del solicitante
     * @return Cadena con el área del solicitante.
     */
    public String getNotaLinea2() {
        return notaLinea2;
    }

    /**
     * Establece el área del solicitante.
     * @param notaLinea2 Cadena con el área del solicitante.
     */
    public void setNotaLinea2(String notaLinea2) {
        this.notaLinea2 = notaLinea2;
    }

    /**
     * Obtiene la cadena "NODO" + CODIGO_NODO
     * @return Cadena con "NODO" + CODIGO_NODO
     */
    public String getNotaLinea3() {
        return notaLinea3;
    }

    /**
     * Establece la cadena "NODO" + CODIGO_NODO
     * @param notaLinea3 Cadena con "NODO" + CODIGO_NODO
     */
    public void setNotaLinea3(String notaLinea3) {
        this.notaLinea3 = notaLinea3;
    }

    /**
     * Obtiene el campo Nota Linea 4 (Vacio)
     * @return Cadena con el campo NotaLinea4
     */
    public String getNotaLinea4() {
        return notaLinea4;
    }

    /**
     * Establece el campo nota Linea 4 
     * @param notaLinea4 Cadena con el valor de Nota Linea 4
     */
    public void setNotaLinea4(String notaLinea4) {
        this.notaLinea4 = notaLinea4;
    }

    /**
     * Obtiene el campo Nota Linea 5 (Vacio)
     * @return Cadena con el campo NotaLinea5
     */
    public String getNotaLinea5() {
        return notaLinea5;
    }

    /**
     * Establece el campo nota Linea 5
     * @param notaLinea5
     * @param notaLinea4 Cadena con el valor de Nota Linea 5
     */
    public void setNotaLinea5(String notaLinea5) {
        this.notaLinea5 = notaLinea5;
    }

    /**
     * Obtiene el campo de la placa.
     * @return Cadena con el valor de la placa.
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * Establece el valor de la placa.
     * @param placa Cadena con el valor de la placa.
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * Obtiene el valor propCable (Vacio)
     * @return Cadena con el valor propCable
     */
    public String getPropCable() {
        return propCable;
    }

    /**
     * Establece el valor propCable
     * @param propCable Cadena con el valor propCable
     */
    public void setPropCable(String propCable) {
        this.propCable = propCable;
    }

    /**
     * Obtiene el valor del Spigot (Vacio)
     * @return Cadena con el valor del spigot.
     */
    public String getSpigot() {
        return spigot;
    }

    /**
     * Establece el valor del Spigot
     * @param spigot Cadena con el valor del Spigot
     */
    public void setSpigot(String spigot) {
        this.spigot = spigot;
    }

    /**
     * Obtiene el código del tipo conexión
     * @return Cadena con el código del tipo de conexión.
     */
    public String getTipoAcometida() {
        return tipoAcometida;
    }

    /**
     * Establece el código de tipo de conexión
     * @param tipoAcometida Cadena con el código del tipo de conexión.
     */
    public void setTipoAcometida(String tipoAcometida) {
        this.tipoAcometida = tipoAcometida;
    }

    /**
     * Obtiene el tipo de HHpp
     * @return Cadena con el tipo de HHpp
     */
    public String getTipoUnidad() {
        return tipoUnidad;
    }

    /**
     * Establece el tipo de HHpp
     * @param tipoUnidad Cadena con el tipo de HHpp
     */
    public void setTipoUnidad(String tipoUnidad) {
        this.tipoUnidad = tipoUnidad;
    }

    /**
     * Obtiene el Vendedor
     * @return Cadena con el vendedor, 999 por defecto
     */
    public String getVendedor() {
        return vendedor;
    }

    /**
     * Establece el vendedor
     * @param vendedor Cadena con el vendedor
     */
    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    /**
     * Obtiene el código zip de la comunidad
     * @return Cadena con el código zip de la comunidad
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Establece el código zip de la comunidad.
     * @param zipCode Cadena con el código zip de la comunidad.
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Sobre Escritura del método toString()
     * @return Cadena con la información de la auditoría.
     */
    @Override
    public String toString() {
        return "ObjetoRrCreacion{" + "division=" + division + ", comunidad=" + comunidad + ", calle=" + calle + ", placa=" + placa + ", apto=" + apto + ", barrio=" + barrio + ", vendedor=" + vendedor + ", estrato=" + estrato + ", zipCode=" + zipCode + ", tipoUnidad=" + tipoUnidad + ", grid=" + grid + ", tipoAcometida=" + tipoAcometida + ", cable=" + cable + ", propCable=" + propCable + ", campo1=" + campo1 + ", campo2=" + campo2 + ", fecha=" + fecha + ", codigoHeadEnd=" + codigoHeadEnd + ", campoNd=" + campoNd + ", codigoNodo=" + codigoNodo + ", spigot=" + spigot + ", dirAlterna=" + dirAlterna + ", localidad=" + localidad + ", ladoMz=" + ladoMz + ", manzana=" + manzana + ", notaLinea1=" + notaLinea1 + ", notaLinea2=" + notaLinea2 + ", notaLinea3=" + notaLinea3 + ", notaLinea4=" + notaLinea4 + ", notaLinea5=" + notaLinea5 + ", campo3=" + campo3 + ", campo4=" + campo4 + '}';
    }
}
