package co.com.telmex.catastro.mbeans.solicitudes;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.AddressGeodata;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.GeograficoPolitico;
import co.com.telmex.catastro.data.Multivalor;
import co.com.telmex.catastro.data.Privilegios;
import co.com.telmex.catastro.data.SolicitudConsulta;
import co.com.telmex.catastro.delegate.ConsultasDelegate;
import co.com.telmex.catastro.delegate.GeoreferenciaDelegate;
import co.com.telmex.catastro.delegate.PrivilegiosRolDelegate;
import co.com.telmex.catastro.delegate.SolicitudNegocioDelegate;
import co.com.telmex.catastro.mbeans.comun.BaseMBean;
import co.com.telmex.catastro.services.util.Constant;
import co.com.telmex.catastro.util.FacesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 * Clase EstratoCuentaMatrizMBean Extiende de BaseMBean
 *
 * @author Ana María Malambo
 * @version 1.0
 */
@ViewScoped
@ManagedBean(name = "estratoCuentaMatrizMBean")
public class EstratoCuentaMatrizMBean extends BaseMBean {

    private static final Logger LOGGER = LogManager.getLogger(EstratoCuentaMatrizMBean.class);
    /**
     *
     */
    public static final String nombreFuncionalidad = "Estrato cuenta matriz";
    /**
     *
     */
    public String son_ciudad;
    /**
     *
     */
    public String dirRta_estandar;
    /**
     *
     */
    public String dirRta_barrio;
    /**
     *
     */
    public String dirRta_estrato;
    /**
     *
     */
    public String dirRta_actEconomica;
    /**
     *
     */
    public String dirRta_manzana;
    /**
     *
     */
    public String dirRta_alterna;
    /**
     *
     */
    public String dirRta_calificador;
    /**
     *
     */
    public String dirRta_existencia;
    /**
     *
     */
    public String dirRta_nvlCalidad;
    /**
     *
     */
    public String dirRta_recomendaciones;
    /**
     *
     */
    public String dirRta_flags;
    /**
     *
     */
    public boolean showPopUp = false;
    /**
     *
     */
    public boolean showConfirmar = false;
    /**
     *
     */
    public AddressGeodata responseSrv;
    /**
     *
     */
    public List<SolicitudConsulta> direcciones = null;
//Atributos de la direccion parametrizada
    /**
     *
     */
    public String seleccionar = "";
    /**
     *
     */
    public String son_calle = "";
    /**
     *
     */
    public String son_tcalle = "";
    /**
     *
     */
    public String tipocalle = "";
    /**
     *
     */
    public List<SelectItem> listTCalles = null;
    private List<Multivalor> tCalles = null;
    private List<Multivalor> tEstrato = null;
    /**
     *
     */
    public String son_letraCalle = "";
    /**
     *
     */
    public String son_letraCalles = "";
    /**
     *
     */
    public List<SelectItem> listLetras = null;
    /**
     *
     */
    public List<SelectItem> listEstratom = null;
    /**
     *
     */
    public List<SelectItem> listEstrato = null;
    private List<Multivalor> letras = null;
    /**
     *
     */
    public String son_prefijoCalle = "";
    /**
     *
     */
    public List<SelectItem> listPrefijos = null;
    private List<Multivalor> prefijos = null;
    /**
     *
     */
    public String son_cardinalCalle = "";
    /**
     *
     */
    public String cardinalCalle = "";
    /**
     *
     */
    public List<SelectItem> listCardinales = null;
    private List<Multivalor> cardinales = null;
    /**
     *
     */
    public String son_placa1 = "";
    /**
     *
     */
    public String son_letraPlaca = "";
    /**
     *
     */
    public String son_placa2 = "";
    /**
     *
     */
    public String son_prefijoPlaca = "";
    /**
     *
     */
    public String son_cardinalPlaca = "";
    /**
     *
     */
    public String cardinalPlaca = "";
    /**
     *
     */
    public String son_apto = "";
    /**
     *
     */
    public String son_complemento = "";
    //Variables para consumir el WS
    private GeograficoPolitico city = null;
    private GeograficoPolitico state = null;
    /**
     *
     */
    public String barrio;
    /**
     *
     */
    public String direccionIn;
    /**
     *
     */
    public String nivelSocio;
    /**
     *
     */
    public String estrato;
    /**
     *
     */
    public String dir_pais;
    /**
     *
     */
    public String dir_regional;
    /**
     *
     */
    public String dir_ciudad;
    /**
     *
     */
    public String dir_barrio;
    /**
     *
     */
    public String dir_ingresadaTexto;
    /**
     *
     */
    public String dir_NoEstandar;
    /**
     *
     */
    public String vacio = "";
    /**
     *
     */
    public String dird = "";
    /**
     *
     */
    public Boolean pop = false;
    private List<GeograficoPolitico> paises;
    /**
     *
     */
    public List<SelectItem> listPaises = null;
    private List<GeograficoPolitico> regionales = null;
    /**
     *
     */
    public List<SelectItem> listRegionales = null;
    private List<GeograficoPolitico> ciudades = null;
    /**
     *
     */
    public List<SelectItem> listCiudades = null;

    /**
     * @return
     */
    public List<SelectItem> getListEstrato() {
        return listEstrato;
    }

    /**
     * @param listEstrato
     */
    public void setListEstrato(List<SelectItem> listEstrato) {
        this.listEstrato = listEstrato;
    }

    private boolean habilitarok = false;

    /**
     * @return
     */
    public String getSon_ciudad() {
        return son_ciudad;
    }

    /**
     * @param son_ciudad
     */
    public void setSon_ciudad(String son_ciudad) {
        this.son_ciudad = son_ciudad;
    }

    /**
     * @return
     */
    public String getEstrato() {
        return estrato;
    }

    /**
     * @param estrato
     */
    public void setEstrato(String estrato) {
        this.estrato = estrato;
    }

    private String opcionSeleccionada;

    /**
     *
     */
    public EstratoCuentaMatrizMBean() {
        super();
        responseSrv = new AddressGeodata();
        init();
        initLists();
    }

    /**
     * @return
     */
    public Boolean getShowPopUp() {
        return showPopUp;
    }

    /**
     * @param showPopUp
     */
    public void setShowPopUp(Boolean showPopUp) {
        this.showPopUp = showPopUp;
    }

    /**
     *
     */
    public void init() {
        barrio = "";
        son_tcalle = "";
        son_calle = "";
        son_prefijoCalle = "";
        son_letraCalles = "";
        son_cardinalCalle = "";
        son_letraPlaca = "";
        son_prefijoPlaca = "";
        son_cardinalPlaca = "";
        son_placa1 = "";
        son_placa2 = "";
        son_complemento = "";
        son_apto = "";
        cardinalCalle = "";
        cardinalPlaca = "";
        city = null;
        state = null;
        dirRta_actEconomica = "";
        dirRta_alterna = "";
        dirRta_barrio = "";
        dirRta_calificador = "";
        dirRta_estandar = "";
        dirRta_estrato = "";
        dirRta_existencia = "";
        dirRta_flags = "";
        dirRta_nvlCalidad = "";
        dirRta_recomendaciones = "";
        dir_NoEstandar = "";
        dir_barrio = "";
        dir_ciudad = "";
        dir_ingresadaTexto = "";
        dir_pais = "";
        dir_regional = "";
        direccionIn = "";
        direcciones = null;
        habilitarok = false;
        nivelSocio = "";
        responseSrv = null;
        estrato = "";
    }

    /**
     * @return
     */
    public boolean validarDir() {

        createDirNoStandar();

        //Consultar el WebService para obtener los datos de la direccion ingresada
        AddressRequest address = new AddressRequest();
        try {
            //Obtenemos la ciudad
            city = loadCity();
            state = loadState();
            address.setCity(city.getGpoNombre());
            address.setState(state.getGpoNombre());

            //Se debe definir con cual direccion voy a consumir el servicio
            if (!dir_NoEstandar.equals("") && dir_NoEstandar.trim().length() > 0) {
                address.setAddress(dir_NoEstandar);
            } else if (!dir_ingresadaTexto.equals("")) {
                address.setAddress(dir_ingresadaTexto);
            }

            if (!"".equals(dir_barrio) && dir_barrio != null) {
                address.setNeighborhood(dir_barrio);
            }
            responseSrv = GeoreferenciaDelegate.queryAddressGeodata(address);

            if (responseSrv != null) {
                dirRta_estandar = responseSrv.getCoddir();
                buscarDir();
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al mostar Auditoria: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al mostar Auditoria: " + e.getMessage(), e, LOGGER);
        }

        return true;
    }

    /**
     *
     */
    private void createDirNoStandar() {
        loadTipoCalle();
        dir_NoEstandar = tipocalle + " " + son_calle;
        if (son_letraCalle.equalsIgnoreCase("")) {
            son_letraCalle = "";
        } else if (!son_letraCalle.equalsIgnoreCase("0")) {
            dir_NoEstandar = dir_NoEstandar + " " + son_letraCalle;
        }
        if (son_prefijoCalle.equalsIgnoreCase("")) {
            son_prefijoCalle = "";
        } else if (!son_prefijoCalle.equalsIgnoreCase("0")) {
            dir_NoEstandar = dir_NoEstandar + " " + son_prefijoCalle;
        }
        if (son_letraCalles.equalsIgnoreCase("")) {
            son_letraCalles = "";
        } else if (!son_letraCalles.equalsIgnoreCase("0")) {
            dir_NoEstandar = dir_NoEstandar + " " + son_letraCalles;
        }
        if ((loadCardinalidad(son_cardinalCalle)) != null) {
            cardinalCalle = (loadCardinalidad(son_cardinalCalle)).getDescripcion();
            dir_NoEstandar = dir_NoEstandar + " " + cardinalCalle;
        }
        dir_NoEstandar = dir_NoEstandar + " " + son_placa1;
        if (!son_letraPlaca.equalsIgnoreCase("") && !son_letraPlaca.equalsIgnoreCase("0")) {
            dir_NoEstandar = dir_NoEstandar + " " + son_letraPlaca;
        }
        if (!son_prefijoPlaca.equalsIgnoreCase("") && !son_prefijoPlaca.equalsIgnoreCase("0")) {
            dir_NoEstandar = dir_NoEstandar + " " + son_prefijoPlaca;
        }
        if ((loadCardinalidad(son_cardinalPlaca)) != null) {
            cardinalPlaca = (loadCardinalidad(son_cardinalPlaca)).getDescripcion();
            dir_NoEstandar = dir_NoEstandar + " " + cardinalPlaca;
        }
        dir_NoEstandar = dir_NoEstandar + " " + son_placa2;
        if (!son_complemento.equals("")) {
            dir_NoEstandar = dir_NoEstandar + " " + son_complemento;
        }
        if (!son_apto.equals("")) {
            dir_NoEstandar = dir_NoEstandar + " " + son_apto;
        }
    }

    /**
     * @param idAddress
     * @return
     */
    private String validarExistenciaOnRepositorio(String idAddress) {
        String existe = Constant.DIR_NO_EXISTE_MSJ;
        if (!"0".equals(idAddress) && idAddress != null) {
            existe = Constant.DIR_SI_EXISTE_MSJ;
        }
        return existe;
    }

    /**
     * @return
     */
    private GeograficoPolitico loadCity() {
        GeograficoPolitico geo = new GeograficoPolitico();
        for (int i = 0; i < ciudades.size(); i++) {
            GeograficoPolitico ciudad = ciudades.get(i);
            if (ciudad.getGpoId().equals(new BigDecimal(this.dir_ciudad))) {
                geo = ciudad;
            }
        }
        return geo;
    }

    /**
     * @return
     */
    private GeograficoPolitico loadState() {
        GeograficoPolitico geo = new GeograficoPolitico();
        for (int i = 0; i < regionales.size(); i++) {
            GeograficoPolitico regional = regionales.get(i);
            if (regional.getGpoId().equals(new BigDecimal(this.dir_regional))) {
                geo = regional;
            }
        }
        return geo;
    }

    /**
     *
     */
    private void loadTipoCalle() {
        for (int i = 0; i < tCalles.size(); i++) {
            Multivalor calle = tCalles.get(i);
            if (calle.getMulValor().equals(this.son_tcalle)) {
                tipocalle = calle.getDescripcion();
            }
        }
    }

    /**
     * @param cardib
     * @return
     */
    private Multivalor loadCardinalidad(String cardib) {
        Multivalor cardi = new Multivalor();
        if (cardib.equalsIgnoreCase("0") || cardib.equalsIgnoreCase("null")) {
            cardi = null;
        } else {
            for (int i = 0; i < cardinales.size(); i++) {
                Multivalor car = cardinales.get(i);
                if (car.getMulId().equals(new BigDecimal(cardib))) {
                    cardi = car;
                }
            }
        }
        return cardi;
    }

    /**
     * Actualiza la lista de Reginoales de acuerdo al pais seleccionado
     *
     * @param event
     * @throws ApplicationException
     */
    public void updateRegionales(ValueChangeEvent event) throws ApplicationException {
        String value = event.getNewValue().toString();
        try {
            regionales = SolicitudNegocioDelegate.queryRegionales(new BigDecimal(value));
            listRegionales = new ArrayList<SelectItem>();
            for (GeograficoPolitico gpo : regionales) {
                SelectItem item = new SelectItem();
                item.setValue(gpo.getGpoId().toString());
                item.setLabel(gpo.getGpoNombre());
                listRegionales.add(item);
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al actualizar Regionales: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al actualizar Regionales: " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * @param event
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void updateCiudades(ValueChangeEvent event) throws ApplicationException {
        String value = event.getNewValue().toString();
        try {
            ciudades = SolicitudNegocioDelegate.queryCiudades(new BigDecimal(value));
            listCiudades = new ArrayList<SelectItem>();
            for (GeograficoPolitico gpo : ciudades) {
                SelectItem item = new SelectItem();
                item.setValue(gpo.getGpoId().toString());
                item.setLabel(gpo.getGpoNombre());
                listCiudades.add(item);
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al actualizar Ciudades: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al actualizar Ciudades: " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     *
     */
    public void buscarDir() {
        try {
            AddressRequest address = new AddressRequest();
            address.setAddress(getDireccionIn());
            address.setCity(dir_ciudad);
            address.setNeighborhood(dir_barrio);
            direcciones = ConsultasDelegate.queryCuentaMatriz(dirRta_estandar);
            if (!direcciones.isEmpty()) {
                habilitarok = true;
                setDird(dir_NoEstandar);
            } else {
                message = "La dirección " + dir_NoEstandar + ", No tienen subdirecciones asociadas.";
            }
        } catch (ApplicationException e) {
            message = "La dirección " + dir_NoEstandar + ", " + Constant.DIR_NO_EXISTE_MSJ;
            FacesUtil.mostrarMensajeError(message + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            message = "La dirección " + dir_NoEstandar + ", " + Constant.DIR_NO_EXISTE_MSJ;
            FacesUtil.mostrarMensajeError(message + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * @param ev
     */
    public void doHabilitar(ActionEvent ev) {
        UIComponent cp = ev.getComponent();
        UIComponent btnver = cp.findComponent("modificar");
        UIComponent rtaSencilla = cp.findComponent("rtaSencilla");
        UIComponent nest = cp.findComponent("nest");
        UIComponent ddir = cp.findComponent("ddir");
        UIComponent socio = cp.findComponent("socio");
        UIComponent consul = cp.findComponent("consul");
        UIComponent dirr = cp.findComponent("dirr");
        UIComponent diren = cp.findComponent("diren");
        UIComponent confirmar = cp.findComponent("confirmar");
        consul.setRendered(false);
        dirr.setRendered(false);
        diren.setRendered(true);
        ddir.setRendered(true);
        btnver.setRendered(true);
        rtaSencilla.setRendered(true);
        nest.setRendered(true);
        socio.setRendered(true);
        confirmar.setRendered(false);
    }

    /**
     * @param ev
     */
    public void doDeshabilitar(ActionEvent ev) {
        UIComponent cp = ev.getComponent();
        UIComponent consul = cp.findComponent("consul");
        UIComponent socio = cp.findComponent("socio");
        UIComponent btnver = cp.findComponent("modificar");
        UIComponent nest = cp.findComponent("nest");
        UIComponent dirr = cp.findComponent("dirr");
        UIComponent ddir = cp.findComponent("ddir");
        UIComponent diren = cp.findComponent("diren");
        UIComponent rtaSencilla = cp.findComponent("rtaSencilla");
        rtaSencilla.setRendered(false);

        diren.setRendered(false);
        ddir.setRendered(false);
        btnver.setRendered(false);
        consul.setRendered(true);
        nest.setRendered(false);
        socio.setRendered(false);
        dirr.setRendered(true);

    }

    /**
     * @return
     */
    private String validarEstrato() {
        message = "";
        if ("0".equals(estrato) || estrato == null) {
            message = "\n El estrato es obligatorio, por favor Seleccionelo.";

        }
        return message;
    }

    /**
     * @param event
     */
    public void modificar(ActionEvent event) {

        message = validarEstrato();
        if (message.isEmpty()) {
            UIComponent cp = event.getComponent();
            UIComponent btnver = cp.findComponent("modificar");
            btnver.setRendered(false);
            UIComponent confirmar = cp.findComponent("confirmar");
            confirmar.setRendered(true);
            showConfirmar = true;
            showPopUp = true;
        } else {
            UIComponent cp = event.getComponent();
            UIComponent btnver = cp.findComponent("modificar");
            btnver.setRendered(true);
        }
    }

    /**
     * @param event
     */
    public void modificarEst(ActionEvent event) {
        try {
            message = "El estrato de la cuenta Matriz <b>" + dir_NoEstandar + "</b>, fue actualizado a: <b>" + estrato + "</b>";
            doDeshabilitar(event);
            String cod_solicitante = "";
            cod_solicitante = user.getUsuLogin();
            direcciones = ConsultasDelegate.modificarEst(estrato, dirRta_estandar, cod_solicitante, nombreFuncionalidad);
            showPopUp = false;
            showConfirmar = false;
            init();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en modificar Estrato: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en modificar Estrato: " + e.getMessage(), e, LOGGER);
        }

    }

    /**
     * @return
     */
    public String getDireccionIn() {
        return direccionIn;
    }

    /**
     *
     */
    private void initLists() {
        listPaises = new ArrayList<>();
        listTCalles = new ArrayList<>();
        listLetras = new ArrayList<>();
        listEstrato = new ArrayList<>();
        listPrefijos = new ArrayList<>();
        listCardinales = new ArrayList<>();
        try {
            paises = SolicitudNegocioDelegate.queryPaises();
            tCalles = SolicitudNegocioDelegate.queryCalles();
            letras = SolicitudNegocioDelegate.queryLetras();
            tEstrato = SolicitudNegocioDelegate.queryEstrato();
            prefijos = SolicitudNegocioDelegate.queryPrefijos();
            cardinales = SolicitudNegocioDelegate.queryCardinales();
            for (GeograficoPolitico gpo : paises) {
                SelectItem item = new SelectItem();
                item.setValue(gpo.getGpoId().toString());
                item.setLabel(gpo.getGpoNombre());
                listPaises.add(item);
            }
            for (Multivalor calle : tCalles) {
                SelectItem item = new SelectItem();
                item.setValue(calle.getMulValor());
                item.setLabel(calle.getDescripcion());
                listTCalles.add(item);
            }
            for (Multivalor letra : letras) {
                SelectItem item = new SelectItem();
                item.setValue(letra.getMulValor());
                item.setLabel(letra.getDescripcion());
                listLetras.add(item);

            }

            for (Multivalor estratos : tEstrato) {
                SelectItem item = new SelectItem();
                item.setValue(estratos.getMulValor());
                item.setLabel(estratos.getDescripcion());
                listEstrato.add(item);
            }

            for (Multivalor prefijo : prefijos) {
                SelectItem item = new SelectItem();
                item.setValue(prefijo.getMulValor());
                item.setLabel(prefijo.getDescripcion());
                listPrefijos.add(item);
            }
            for (Multivalor cardinal : cardinales) {
                SelectItem item = new SelectItem();
                item.setValue(cardinal.getMulId());
                item.setLabel(cardinal.getMulValor());
                listCardinales.add(item);
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al cargar las listas: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al cargar las listas: " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * ActionListener ejecutado al solicitar la validacion de la direccion
     *
     * @param ev
     */
    public void doValidar(ActionEvent ev) {
        message = "";
        //Se validan los campos obligatorios
        message = validarCamposObligatorios();
        UIComponent cp = ev.getComponent();
        UIComponent rtaSencilla = cp.findComponent("respuestaSencilla");

        if (message.isEmpty()) {
            validarDir();
            if (habilitarok) {
                doHabilitar(ev);
            }
        } else {

            rtaSencilla.setRendered(false);
        }
    }

    /**
     * Valida los campos obligatorios del formulario
     *
     * @return
     */
    private String validarCamposObligatorios() {
        message = "";
        if ("0".equals(dir_pais) || dir_pais == null) {
            message = "\n El Pais es  obligatorio, por favor Seleccionelo.";
            return message;
        }

        if ("0".equals(dir_regional) || dir_regional == null) {
            message = "\n La regional es  obligatoria, por favor Seleccionelo.";
            return message;
        }

        if ("0".equals(dir_ciudad) || dir_ciudad == null) {
            message = "\n La ciudad es  obligatoria, por favor diligenciela.";
            return message;
        }

        if ("".equals(dir_barrio) || ("''".equals(dir_barrio))) {
            message = "\n El barrio es  obligatorio, por favor diligencielo.";
            return message;
        }

        if (("".equals(son_tcalle) || "".equals(son_calle)) && (dir_ingresadaTexto == null || "".equals(dir_ingresadaTexto))) {
            message = "\n Debe ingresar una direccion para validar.";
            return message;
        }
        return message;
    }

    /**
     * @param direccionIn
     */
    public void setDireccionIn(String direccionIn) {
        this.direccionIn = direccionIn;
    }

    /**
     * @return
     */
    public String getNivelSocio() {
        return nivelSocio;
    }

    /**
     * @param nivelSocio
     */
    public void setNivelSocio(String nivelSocio) {
        this.nivelSocio = nivelSocio;
    }

    /**
     * @return @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<SolicitudConsulta> getCatalogs() throws ApplicationException {
        BigDecimal num = new BigDecimal(3);
        List<Privilegios> data = PrivilegiosRolDelegate.queryPrivilegiosRol(num, "ESTRATO CUENTA MATRIZ");

        return direcciones;
    }

    /**
     * @param direcciones
     */
    public void setCatalogs(List<SolicitudConsulta> direcciones) {
        this.direcciones = direcciones;
    }

    /**
     * @return
     */
    public String getOpcionSeleccionada() {
        return opcionSeleccionada;
    }

    /**
     * @param opcionSeleccionada
     */
    public void setOpcionSeleccionada(String opcionSeleccionada) {
        this.opcionSeleccionada = opcionSeleccionada;
    }

    /**
     * @return
     */
    public String getBarrio() {
        return barrio;
    }

    /**
     * @param barrio
     */
    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    /**
     * @return
     */
    public String getCardinalCalle() {
        return cardinalCalle;
    }

    /**
     * @param cardinalCalle
     */
    public void setCardinalCalle(String cardinalCalle) {
        this.cardinalCalle = cardinalCalle;
    }

    /**
     * @return
     */
    public String getCardinalPlaca() {
        return cardinalPlaca;
    }

    /**
     * @param cardinalPlaca
     */
    public void setCardinalPlaca(String cardinalPlaca) {
        this.cardinalPlaca = cardinalPlaca;
    }

    /**
     * @return
     */
    public String getDir_barrio() {
        return dir_barrio;
    }

    /**
     * @param dir_barrio
     */
    public void setDir_barrio(String dir_barrio) {
        this.dir_barrio = dir_barrio;
    }

    /**
     * @return
     */
    public String getDir_ciudad() {
        return dir_ciudad;
    }

    /**
     * @param dir_ciudad
     */
    public void setDir_ciudad(String dir_ciudad) {
        this.dir_ciudad = dir_ciudad;
    }

    /**
     * @return
     */
    public String getDir_ingresadaTexto() {
        return dir_ingresadaTexto;
    }

    /**
     * @param dir_ingresadaTexto
     */
    public void setDir_ingresadaTexto(String dir_ingresadaTexto) {
        this.dir_ingresadaTexto = dir_ingresadaTexto;
    }

    /**
     * @return
     */
    public Boolean getPop() {
        return pop;
    }

    /**
     * @param pop
     */
    public void setPop(Boolean pop) {
        this.pop = pop;
    }

    /**
     * @return
     */
    public String getDir_pais() {
        return dir_pais;
    }

    /**
     * @param dir_pais
     */
    public void setDir_pais(String dir_pais) {
        this.dir_pais = dir_pais;
    }

    /**
     * @return
     */
    public String getDir_regional() {
        return dir_regional;
    }

    /**
     * @param dir_regional
     */
    public void setDir_regional(String dir_regional) {
        this.dir_regional = dir_regional;
    }

    /**
     * @return
     */
    public List<SelectItem> getListCardinales() {
        return listCardinales;
    }

    /**
     * @param listCardinales
     */
    public void setListCardinales(List<SelectItem> listCardinales) {
        this.listCardinales = listCardinales;
    }

    /**
     * @return
     */
    public List<SelectItem> getListCiudades() {
        return listCiudades;
    }

    /**
     * @param listCiudades
     */
    public void setListCiudades(List<SelectItem> listCiudades) {
        this.listCiudades = listCiudades;
    }

    /**
     * @return
     */
    public List<SelectItem> getListLetras() {
        return listLetras;
    }

    /**
     * @param listLetras
     */
    public void setListLetras(List<SelectItem> listLetras) {
        this.listLetras = listLetras;
    }

    /**
     * @return
     */
    public List<SelectItem> getListPaises() {
        return listPaises;
    }

    /**
     * @param listPaises
     */
    public void setListPaises(List<SelectItem> listPaises) {
        this.listPaises = listPaises;
    }

    /**
     * @return
     */
    public List<SelectItem> getListPrefijos() {
        return listPrefijos;
    }

    /**
     * @param listPrefijos
     */
    public void setListPrefijos(List<SelectItem> listPrefijos) {
        this.listPrefijos = listPrefijos;
    }

    /**
     * @return
     */
    public List<SelectItem> getListRegionales() {
        return listRegionales;
    }

    /**
     * @param listRegionales
     */
    public void setListRegionales(List<SelectItem> listRegionales) {
        this.listRegionales = listRegionales;
    }

    /**
     * @return
     */
    public List<SelectItem> getListTCalles() {
        return listTCalles;
    }

    /**
     * @param listTCalles
     */
    public void setListTCalles(List<SelectItem> listTCalles) {
        this.listTCalles = listTCalles;
    }

    /**
     * @return
     */
    public String getSeleccionar() {
        return seleccionar;
    }

    /**
     * @param seleccionar
     */
    public void setSeleccionar(String seleccionar) {
        this.seleccionar = seleccionar;
    }

    /**
     * @return
     */
    public String getSon_apto() {
        return son_apto;
    }

    /**
     * @param son_apto
     */
    public void setSon_apto(String son_apto) {
        this.son_apto = son_apto;
    }

    /**
     * @return
     */
    public String getSon_calle() {
        return son_calle;
    }

    /**
     * @param son_calle
     */
    public void setSon_calle(String son_calle) {
        this.son_calle = son_calle;
    }

    /**
     * @return
     */
    public String getSon_cardinalCalle() {
        return son_cardinalCalle;
    }

    /**
     * @param son_cardinalCalle
     */
    public void setSon_cardinalCalle(String son_cardinalCalle) {
        this.son_cardinalCalle = son_cardinalCalle;
    }

    /**
     * @return
     */
    public String getSon_cardinalPlaca() {
        return son_cardinalPlaca;
    }

    /**
     * @param son_cardinalPlaca
     */
    public void setSon_cardinalPlaca(String son_cardinalPlaca) {
        this.son_cardinalPlaca = son_cardinalPlaca;
    }

    /**
     * @return
     */
    public String getSon_complemento() {
        return son_complemento;
    }

    /**
     * @param son_complemento
     */
    public void setSon_complemento(String son_complemento) {
        this.son_complemento = son_complemento;
    }

    /**
     * @return
     */
    public String getSon_letraCalle() {
        return son_letraCalle;
    }

    /**
     * @param son_letraCalle
     */
    public void setSon_letraCalle(String son_letraCalle) {
        this.son_letraCalle = son_letraCalle;
    }

    /**
     * @return
     */
    public String getSon_letraCalles() {
        return son_letraCalles;
    }

    /**
     * @param son_letraCalles
     */
    public void setSon_letraCalles(String son_letraCalles) {
        this.son_letraCalles = son_letraCalles;
    }

    /**
     * @return
     */
    public String getSon_letraPlaca() {
        return son_letraPlaca;
    }

    /**
     * @param son_letraPlaca
     */
    public void setSon_letraPlaca(String son_letraPlaca) {
        this.son_letraPlaca = son_letraPlaca;
    }

    /**
     * @return
     */
    public String getSon_placa1() {
        return son_placa1;
    }

    /**
     * @param son_placa1
     */
    public void setSon_placa1(String son_placa1) {
        this.son_placa1 = son_placa1;
    }

    /**
     * @return
     */
    public String getSon_placa2() {
        return son_placa2;
    }

    /**
     * @param son_placa2
     */
    public void setSon_placa2(String son_placa2) {
        this.son_placa2 = son_placa2;
    }

    /**
     * @return
     */
    public String getSon_prefijoCalle() {
        return son_prefijoCalle;
    }

    /**
     * @param son_prefijoCalle
     */
    public void setSon_prefijoCalle(String son_prefijoCalle) {
        this.son_prefijoCalle = son_prefijoCalle;
    }

    /**
     * @return
     */
    public String getSon_prefijoPlaca() {
        return son_prefijoPlaca;
    }

    /**
     * @param son_prefijoPlaca
     */
    public void setSon_prefijoPlaca(String son_prefijoPlaca) {
        this.son_prefijoPlaca = son_prefijoPlaca;
    }

    /**
     * @return
     */
    public String getSon_tcalle() {
        return son_tcalle;
    }

    /**
     * @param son_tcalle
     */
    public void setSon_tcalle(String son_tcalle) {
        this.son_tcalle = son_tcalle;
    }

    /**
     * @return
     */
    public String getTipocalle() {
        return tipocalle;
    }

    /**
     * @param tipocalle
     */
    public void setTipocalle(String tipocalle) {
        this.tipocalle = tipocalle;
    }

    /**
     * @return
     */
    public String getVacio() {
        return vacio;
    }

    /**
     * @param vacio
     */
    public void setVacio(String vacio) {
        this.vacio = vacio;
    }

    /**
     * @return
     */
    public String getDird() {
        return dird;
    }

    /**
     * @param dird
     */
    public void setDird(String dird) {
        this.dird = dird;
    }

    /**
     * @return
     */
    public boolean isShowConfirmar() {
        return showConfirmar;
    }

    /**
     * @param showConfirmar
     */
    public void setShowConfirmar(boolean showConfirmar) {
        this.showConfirmar = showConfirmar;
    }
}
