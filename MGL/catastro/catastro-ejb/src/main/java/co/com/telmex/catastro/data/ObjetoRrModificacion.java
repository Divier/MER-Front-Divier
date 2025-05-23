package co.com.telmex.catastro.data;

/**
 * Clase Privilegios
 * Implementa Serialización.
 *
 * @author 	Nataly Orozco Torres
 * @version	1.0
 */
public class ObjetoRrModificacion {

    //NUMERO DE CUENTA, SIEMPRE 0
    private String acct;
    //CALLE : CALLE + COMPLEMENTO + UBICACIÓN CARDINAL CALLE+ UBICACIÓN CARDINAL PLACA
    private String strtnm;
    //placa
    private String homeno;
    //apto
    private String aptno;
    // comunidad actual
    private String curcom;
    // GRID actual - Comunidad
    private String curgrd;
    // Nueva comunidad
    private String newcom;
    //nuevo GRID
    private String newgrd;
    // TIPO DE HEAD END - HE
    private String hendtp;
    // CODIGO DEL HEAD END - head end del nodo
    private String hendcd;
    //TIPO CMTS
    private String cmtstp;
    //CODIGO DE CMTS
    private String cmtscd;
    //TIPO NODO - 
    private String nodetp;
    //CODIGO NODO
    private String nodecd;
    // Nombre del barrio
    private String barrio;
    //Estrato de la unidad
    private String estrato;
    //Campo 1 de la Informacion Adicional de la unidad
    private String direccn;
    // Campo 2 de la Informacion Adicional de la unidad
    private String sector;
    //Campo 3 de la Informacion Adicional de la unidad
    private String seccion;
    //Campo 4 de la Informacion Adicional de la unidad
    private String manzana;
    // ZIPCODE
    private String postcode;
    //Estado Unidad
    private String status;
    //Tipo Unidad
    private String unittype;
    //vacio
    private String droptype;
    //vacio 
    private String dropcabl;
    //cable propietario
    private String cablownr;
    //vacio
    private String dealercd;
    //blacklist 1
    private String probcd01;
    //blacklist 2
    private String probcd02;
    //blacklist 3
    private String probcd03;
    //blacklist 4
    private String probcd04;
    //blacklist 5
    private String probcd05;
    //blacklist 6
    private String probcd06;
    //blacklist 7
    private String probcd07;
    //blacklist 8
    private String probcd08;
    //blacklist 9
    private String probcd09;
    //valor adicional  U0 en RR
    private String additn01;
    //valor adicional  U2 en RR
    private String additn02;
    //valor adicional  U3 en RR
    private String additn03;
    //valor adicional  U4 en RR
    private String additn04;
    //valor adicional  U5 en RR
    private String additn05;
    //valor adicional  U6 en RR
    private String additn06;
    //valor adicional  U7 en RR
    private String additn07;
    //valor adicional  U8 en RR
    private String additn08;
    //valor adicional  U9 en RR
    private String additn09;
    //valor adicional  U10 en RR
    private String additn10;

    /**
     * Constructor
     */
    public ObjetoRrModificacion() {
    }

    /**
     * Obtiene el numero de cuenta.
     * @return Cadena con el numero de cuenta.
     */
    public String getAcct() {
        return acct;
    }

    /**
     * Establece el numero de cuenta.
     * @param acct Cadena con el numero de cuenta.
     */
    public void setAcct(String acct) {
        this.acct = acct;
    }

    /**
     * Obtiene el valor adicional  U0 en RR.
     * @return Cadena con el valor adicional  U0 en RR
     */
    public String getAdditn01() {
        return additn01;
    }

    /**
     * Establece el valor adicional  U0 en RR.
     * @param additn01 Cadena con el valor adicional  U0 en RR.
     */
    public void setAdditn01(String additn01) {
        this.additn01 = additn01;
    }

    /**
     * Obtiene el valor adicional  U2 en RR
     * @return Cadena con el valor adicional  U2 en RR
     */
    public String getAdditn02() {
        return additn02;
    }

    /**
     * Establece el valor adicional  U2 en RR.
     * @param additn02 Cadena con el valor adicional  U2 en RR
     */
    public void setAdditn02(String additn02) {
        this.additn02 = additn02;
    }

    /**
     * Obtiene el valor adicional  U3 en RR.
     * @return Cadena con el valor adicional  U3 en RR.
     */
    public String getAdditn03() {
        return additn03;
    }

    /**
     * Establece el valor adicional  U3 en RR
     * @param additn03 Cadena con el valor adicional  U3 en RR
     */
    public void setAdditn03(String additn03) {
        this.additn03 = additn03;
    }

    /**
     * Obtiene el valor adicional  U4 en RR
     * @return Cadena con el valor adicional  U4 en RR
     */
    public String getAdditn04() {
        return additn04;
    }

    /**
     * Establece el valor adicional  U4 en RR
     * @param additn04 Cadena con el valor adicional  U4 en RR
     */
    public void setAdditn04(String additn04) {
        this.additn04 = additn04;
    }

    /**
     * Obtiene el valor adicional  U5 en RR
     * @return Cadena con el valor adicional  U5 en RR
     */
    public String getAdditn05() {
        return additn05;
    }

    /**
     * Establece el valor adicional  U5 en RR
     * @param additn05 Cadena con el valor adicional  U5 en RR
     */
    public void setAdditn05(String additn05) {
        this.additn05 = additn05;
    }

    /**
     * Obtiene el valor adicional  U6 en RR
     * @return Cadena con el valor adicional  U6 en RR
     */
    public String getAdditn06() {
        return additn06;
    }

    /**
     * Establece el valor adicional  U6 en RR
     * @param additn06 Cadena con el valor adicional  U6 en RR
     */
    public void setAdditn06(String additn06) {
        this.additn06 = additn06;
    }

    /**
     * Obtiene el valor adicional  U7 en RR
     * @return Cadena con el valor adicional  U7 en RR
     */
    public String getAdditn07() {
        return additn07;
    }

    /**
     * Establece el valor adicional  U7 en RR
     * @param additn07 Cadena con el valor adicional  U7 en RR
     */
    public void setAdditn07(String additn07) {
        this.additn07 = additn07;
    }

    /**
     * Obtiene el valor adicional  U8 en RR
     * @return Cadena con el valor adicional  U8 en RR
     */
    public String getAdditn08() {
        return additn08;
    }

    /**
     * Establece el valor valor adicional  U8 en RR
     * @param additn08 Cadena con el valor adicional  U8 en RR
     */
    public void setAdditn08(String additn08) {
        this.additn08 = additn08;
    }

    /**
     * Obtiene el valor adicional  U9 en RR
     * @return Cadena con el valor adicional  U9 en RR
     */
    public String getAdditn09() {
        return additn09;
    }

    /**
     * Establece el valor adicional  U9 en RR
     * @param additn09 Cadena con el valor adicional  U9 en RR 
     */
    public void setAdditn09(String additn09) {
        this.additn09 = additn09;
    }

    /**
     * Obtiene el valor adicional  U10 en RR
     * @return Cadena con el valor adicional  U10 en RR
     */
    public String getAdditn10() {
        return additn10;
    }

    /**
     * Establece el valor adicional  U10 en RR
     * @param additn10 Cadena con el valor adicional  U10 en RR
     */
    public void setAdditn10(String additn10) {
        this.additn10 = additn10;
    }

    /**
     * Obtiene el Apto.
     * @return Cadena con el Apto.
     */
    public String getAptno() {
        return aptno;
    }

    /**
     * Establece el apto.
     * @param aptno Cadena con el apto.
     */
    public void setAptno(String aptno) {
        this.aptno = aptno;
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
     * Obtiene el cable propietario
     * @return Cadena con el cable propietario
     */
    public String getCablownr() {
        return cablownr;
    }

    /**
     * Establece el cable propietario
     * @param cablownr Cadena con el cable propietario
     */
    public void setCablownr(String cablownr) {
        this.cablownr = cablownr;
    }

    /**
     * Obtiene el código de CMTS
     * @return Cadena con el código de CMTS
     */
    public String getCmtscd() {
        return cmtscd;
    }

    /**
     * Establece el código de CMTS
     * @param cmtscd Cadena con el código de CMTS
     */
    public void setCmtscd(String cmtscd) {
        this.cmtscd = cmtscd;
    }

    /**
     * Obtiene el tipo de CMTS
     * @return Cadena con el tipo de CMTS
     */
    public String getCmtstp() {
        return cmtstp;
    }

    /**
     * Establece el tipo de CMTS
     * @param cmtstp Cadena con el tipo de CMTS
     */
    public void setCmtstp(String cmtstp) {
        this.cmtstp = cmtstp;
    }

    /**
     * Obtiene la comunidad actual
     * @return Cadena con la comunidad actual.
     */
    public String getCurcom() {
        return curcom;
    }

    /**
     * Establece la comunidad actual
     * @param curcom Cadena con la comunidad actual
     */
    public void setCurcom(String curcom) {
        this.curcom = curcom;
    }

    /**
     * Obtiene el GRID actual - Comunidad
     * @return Cadena con el GRID actual - Comunidad
     */
    public String getCurgrd() {
        return curgrd;
    }

    /**
     * Establece el GRID actual - Comunidad
     * @param curgrd Cadena con el GRID actual - Comunidad
     */
    public void setCurgrd(String curgrd) {
        this.curgrd = curgrd;
    }

    /**
     * Obtiene el Dealer (Actualmente Vacio)
     * @return Cadena con el Dealer.
     */
    public String getDealercd() {
        return dealercd;
    }

    /**
     * Establece el Dealer
     * @param dealercd Cadena con el Dealercd
     */
    public void setDealercd(String dealercd) {
        this.dealercd = dealercd;
    }

    /**
     * Obtiene el Campo 1 de la Información Adicional de la unidad
     * @return Cadena con el Campo 1 de la Información Adicional de la unidad
     */
    public String getDireccn() {
        return direccn;
    }

    /**
     * Establece el Campo 1 de la Información Adicional de la unidad
     * @param direccn cadena con el el Campo 1 de la Información Adicional de la unidad
     */
    public void setDireccn(String direccn) {
        this.direccn = direccn;
    }

    /**
     * Obtiene el Dropcabl (Actualmente vacio)
     * @return Cadena con el Dropcabl
     */
    public String getDropcabl() {
        return dropcabl;
    }

    /**
     * Establece el Dropcabl
     * @param dropcabl Cadena con el Dropcabl
     */
    public void setDropcabl(String dropcabl) {
        this.dropcabl = dropcabl;
    }

    /**
     * Obtiene el Droptype (Actualmente vacio)
     * @return Cadena con el Droptype
     */
    public String getDroptype() {
        return droptype;
    }

    /**
     * Establece el Droptype
     * @param droptype Cadena con el Droptype
     */
    public void setDroptype(String droptype) {
        this.droptype = droptype;
    }

    /**
     * Obtiene el estrato.
     * @return Cadena con el estrato
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
     * Obtiene el código del head end del nodo
     * @return Cadena con el código del head end del nodo
     */
    public String getHendcd() {
        return hendcd;
    }

    /**
     * Establece el código del head end del nodo
     * @param hendcd Cadena con el código del head end del nodo
     */
    public void setHendcd(String hendcd) {
        this.hendcd = hendcd;
    }

    /**
     * Obtiene el tipo de head end
     * @return Cadena con el tipo de head end
     */
    public String getHendtp() {
        return hendtp;
    }

    /**
     * Establece el tipo de head end
     * @param hendtp Cadena con el tipo de head end
     */
    public void setHendtp(String hendtp) {
        this.hendtp = hendtp;
    }

    /**
     * Obtiene el numero de la placa
     * @return Cadena con el numero de la placa
     */
    public String getHomeno() {
        return homeno;
    }

    /**
     * Establece el numero de la placa.
     * @param homeno Cadena con el numero de la placa.
     */
    public void setHomeno(String homeno) {
        this.homeno = homeno;
    }

    /**
     * Obtiene la manzana.
     * @return Cadena con la manzana.
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
     * Obtiene la Nueva comunidad
     * @return Cadena con la Nueva comunidad
     */
    public String getNewcom() {
        return newcom;
    }

    /**
     * Establece la Nueva comunidad
     * @param newcom Cadena con la Nueva comunidad
     */
    public void setNewcom(String newcom) {
        this.newcom = newcom;
    }

    /**
     * Obtiene el nuevo GRID
     * @return Cadena con el nuevo GRID
     */
    public String getNewgrd() {
        return newgrd;
    }

    /**
     * Establece el nuevo GRID
     * @param newgrd Cadena con el nuevo GRID
     */
    public void setNewgrd(String newgrd) {
        this.newgrd = newgrd;
    }

    /**
     * Obtiene el código del nodo
     * @return cadena con el código del nodo
     */
    public String getNodecd() {
        return nodecd;
    }

    /**
     * Establece el código del nodo
     * @param nodecd Cadena con el código del nodo
     */
    public void setNodecd(String nodecd) {
        this.nodecd = nodecd;
    }

    /**
     * Obtiene el tipo del nodo
     * @return Cadena con el tipo del nodo
     */
    public String getNodetp() {
        return nodetp;
    }

    /**
     * Establece el tipo del nodo
     * @param nodetp Cadena con el tipo del nodo
     */
    public void setNodetp(String nodetp) {
        this.nodetp = nodetp;
    }

    /**
     * Obtiene el ZipCode
     * @return Cadena con el ZipCode
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * Establece el ZipCode
     * @param postcode Cadena con el ZipCode
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     * Obtiene el BlackList 01
     * @return Cadena con el BlackList 01
     */
    public String getProbcd01() {
        return probcd01;
    }

    /**
     * Establece el BlackList 01
     * @param probcd01 Cadena con el BlackList 01
     */
    public void setProbcd01(String probcd01) {
        this.probcd01 = probcd01;
    }

    /**
     * Obtiene el BlackList 02
     * @return Cadena con el BlackList 02
     */
    public String getProbcd02() {
        return probcd02;
    }

    /**
     * Establece el BlackList 02
     * @param probcd02 Cadena con el BlackList 02
     */
    public void setProbcd02(String probcd02) {
        this.probcd02 = probcd02;
    }

    /**
     * Obtiene el BlackList 03
     * @return Cadena con el BlackList 03
     */
    public String getProbcd03() {
        return probcd03;
    }

    /**
     * Establece el BlackList 03
     * @param probcd03 Cadena con el BlackList 03
     */
    public void setProbcd03(String probcd03) {
        this.probcd03 = probcd03;
    }

    /**
     * Obtiene el BlackList 04
     * @return Cadena con el BlackList 04
     */
    public String getProbcd04() {
        return probcd04;
    }

    /**
     * Establece el BlackList 04
     * @param probcd04 Cadena con el BlackList 04
     */
    public void setProbcd04(String probcd04) {
        this.probcd04 = probcd04;
    }

    /**
     * Obtiene el BlackList 05
     * @return Cadena con el BlackList 05
     */
    public String getProbcd05() {
        return probcd05;
    }

    /**
     * Establece el BlackList 05
     * @param probcd05 Cadena con el BlackList 05
     */
    public void setProbcd05(String probcd05) {
        this.probcd05 = probcd05;
    }

    /**
     * Obtiene el BlackList 06
     * @return Cadena con el BlackList 06
     */
    public String getProbcd06() {
        return probcd06;
    }

    /**
     * Establece el BlackList 06
     * @param probcd06 Cadena con el BlackList 06
     */
    public void setProbcd06(String probcd06) {
        this.probcd06 = probcd06;
    }

    /**
     * Obtiene el BlackList 07
     * @return Cadena con el BlackList 07
     */
    public String getProbcd07() {
        return probcd07;
    }

    /**
     * Establece el BlackList 07
     * @param probcd07 Cadena con el BlackList 07
     */
    public void setProbcd07(String probcd07) {
        this.probcd07 = probcd07;
    }

    /**
     * Obtiene el BlackList 08
     * @return Cadena con el BlackList 08
     */
    public String getProbcd08() {
        return probcd08;
    }

    /**
     * Establece el BlackList 08
     * @param probcd08 Cadena con el BlackList 08
     */
    public void setProbcd08(String probcd08) {
        this.probcd08 = probcd08;
    }

    /**
     * Obtiene el BlackList 09
     * @return Cadena con el BlackList 09
     */
    public String getProbcd09() {
        return probcd09;
    }

    /**
     * Establece el BlackList 09
     * @param probcd09 Cadena con el BlackList 09
     */
    public void setProbcd09(String probcd09) {
        this.probcd09 = probcd09;
    }

    /**
     * Obtiene el Campo 3 de la Información Adicional de la unidad
     * @return Cadena con el Campo 3 de la Información Adicional de la unidad
     */
    public String getSeccion() {
        return seccion;
    }

    /**
     * Establece el Campo 3 de la Información Adicional de la unidad
     * @param seccion Cadena con el Campo 3 de la Información Adicional de la unidad
     */
    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    /**
     * Obtiene el Campo 2 de la Información Adicional de la unidad
     * @return Cadena con el Campo 2 de la Información Adicional de la unidad
     */
    public String getSector() {
        return sector;
    }

    /**
     * Establece el Campo 2 de la Información Adicional de la unidad
     * @param sector Cadena con el Campo 2 de la Información Adicional de la unidad
     */
    public void setSector(String sector) {
        this.sector = sector;
    }

    /**
     * Obtiene el estado de la unidad.
     * @return Cadena con el estado de la unidad.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Establece el estado de la unidad.
     * @param status Cadena con el estado de la unidad.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Obtiene la cadena CALLE
     * @return Cadena con CALLE : CALLE + COMPLEMENTO + UBICACIÓN CARDINAL CALLE+ UBICACIÓN CARDINAL PLACA
     */
    public String getStrtnm() {
        return strtnm;
    }

    /**
     * Establece la CALLE
     * @param strtnm Cadena con CALLE : CALLE + COMPLEMENTO + UBICACIÓN CARDINAL CALLE+ UBICACIÓN CARDINAL PLACA
     */
    public void setStrtnm(String strtnm) {
        this.strtnm = strtnm;
    }

    /**
     * Obtiene el tipo de unidad
     * @return Cadena con el tipo de unidad.
     */
    public String getUnittype() {
        return unittype;
    }

    /**
     * Establece el tipo de unidad.
     * @param unittype Cadena con el tipo de unidad.
     */
    public void setUnittype(String unittype) {
        this.unittype = unittype;
    }
}
