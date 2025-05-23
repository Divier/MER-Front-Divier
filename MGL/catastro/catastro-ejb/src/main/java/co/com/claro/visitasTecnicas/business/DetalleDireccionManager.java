package co.com.claro.visitasTecnicas.business;

import co.com.claro.direcciones.business.NegocioGeograficoPolitico;
import co.com.claro.mgl.businessmanager.address.MultivalorManager;
import co.com.claro.mgl.businessmanager.cm.CmtComponenteDireccionesMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ConfigurationAddressComponent;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.OpcionIdNombre;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.telmex.catastro.data.GeograficoPolitico;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;


/**
 * objetivo de la clase. Convertir direcciones del codigo Georeferenciacion a
 * clase DetalleDireccionEntity.
 *
 * @author Carlos Leonardo Villamil
 * @versión 1.00.000
 */
public class DetalleDireccionManager {
    public static final String REGULAR_EXPRESION_LETRA_NUMERO = "(([a-z]|[A-Z])){1,2}";

    private String longAddressCodeGeo;
    private String shortAddressCodeGeo;
    private static final int LONGITUD_TIPO_BOGOTA = 78;
    private static final int LONGITUD_TIPO_HERENDAN_BOGOTA_MEDELLIN = 79;
    private static final int LONGITUD_TIPO_CALI = 87;
    private static final int LONGITUD_TIPO_BM_SHORT = 51;
    private static final int LONGITUD_TIPO_BM_LONG = 99;
    private static final String TIPO_BOGOTA = "B";
    private static final String TIPO_HERENDAN_BOGOTA = "HB";
    private static final String TIPO_MEDELLIN = "M";
    private static final String TIPO_CALI = "C";
    private static final String TIPO_BARRIO_MANZANA = "BM";
    private static final String TIPO_MAZANA_CASA = "BMC";
    private static final int indiceInicial = 0;
    private static final int LongitudATomar = 1;
    private String tipoDireccion;
    private String tipoViaPrincipal;
    private String numeroViaPrincipal;
    private String letraNumeroViaPrincipal;
    private String numerosLetrasPosterioresLetraViaPrincipal;
    private String numerosLetrasPosterioresPosterioresLetraViaPrincipal;
    private String bisViaPrincipal;
    private String letraNumeroposteriorBisViaPrincipal;
    private String cuadranteViaPrincipal;
    private String tipoViaGeneradora;
    private String numeroViaGeneradora;
    private String letraNumeroViaGeneradora;
    private String numeroLetrasPosterioresLetraViaGeneradora;
    private String numerosLetrasPosterioresPosterioresLetraViaGeneradora;
    private String bisViaGeneradora;
    private String letraNumeroPosteriorBisViaGeneradora;
    private String cuadranteViaGeneradora;
    private String placaDireccion;
    private String bmcTipoNivel2;
    private String bmcValorNivel2;
    private String bmcTipoNivel3;
    private String bmcValorNivel3;
    private String bmcTipoNivel4;
    private String bmcValorNivel4;
    private String bmcTipoNivel5;
    private String bmcValorNivel5;
    private String bmcTipoNivel6;
    private String bmcValorNivel6;
    private int[] vTipoViaPrincipal;
    private int[] vNumeroViaPrincipal;
    private int[] vLetraNumeroViaPrincipal;
    private int[] vNumerosLetrasPosterioresLetraViaPrincipal;
    private int[] vBisViaPrincipal;
    private int[] vLetraNumeroposteriorBisViaPrincipal;
    private int[] vCuadranteViaPrincipal;
    private int[] vTipoViaGeneradora;
    private int[] vNumeroViaGeneradora;
    private int[] vLetraNumeroViaGeneradora;
    private int[] vNumeroLetrasPosterioresLetraViaGeneradora;
    private int[] vBisViaGeneradora;
    private int[] vLetraNumeroPosteriorBisViaGeneradora;
    private int[] vCuadranteViaGeneradora;
    private int[] vPlacaDireccion;
    private int[] vBmcTipoNivel2;
    private int[] vBmcValorNivel2;
    private int[] vBmcTipoNivel3;
    private int[] vBmcValorNivel3;
    private int[] vBmcTipoNivel4;
    private int[] vBmcValorNivel4;
    private int[] vBmcTipoNivel5;
    private int[] vBmcValorNivel5;
    private int[] vBmcTipoNivel6;
    private int[] vBmcValorNivel6;
    private int[] vNumerosLetrasPosterioresPosterioresLetraViaPrincipal;
    private int[] vNumerosLetrasPosterioresPosterioresLetraViaGeneradora;
    private NegocioGeograficoPolitico negocioGeograficoPolitico =
            new NegocioGeograficoPolitico();
    private GeograficoPolitico geograficoPolitico;
    
    private static final Logger LOGGER = LogManager.getLogger(DetalleDireccionManager.class);
    
    
   

    /**
     * Descripción del objetivo del método. Inicializa las variables de la clase
     *
     * @author Carlos Leonardo Villamil
     */
    public DetalleDireccionManager() {
        this.longAddressCodeGeo = "";
        this.shortAddressCodeGeo = "";
    }
    
    /**
     * Descripción del objetivo del método.Llamada de entrada a la clase para
 convertir la direccion a la clase DetalleDireccionEntity, Llama a borrado
 de variables y Convertir ladireccion.
     *
     * @author Carlos Leonardo Villamil
     * @param longAddressCodeGeo Codigo de Geo hasta la placa.
     * @param shortAddressCodeGeo Codigo de Geo hasta el complemento.
     * @return DetalleDireccionEntity Direccion tabulada en sus componentes.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public DetalleDireccionEntity conversionADetalleDireccion(
            String longAddressCodeGeo, String shortAddressCodeGeo, BigDecimal city,
            String barrio)
            throws ApplicationException {
        return (this.conversionADetalleDireccion(longAddressCodeGeo, shortAddressCodeGeo, city, barrio, null));
    }
    
    
    /**
     * Descripción del objetivo del método.Llamada de entrada a la clase para
 convertir la direccion a la clase DetalleDireccionEntity, Llama a borrado
 de variables y Convertir ladireccion.
     *
     * @author Carlos Leonardo Villamil
     * @param longAddressCodeGeo Codigo de Geo hasta la placa.
     * @param shortAddressCodeGeo Codigo de Geo hasta el complemento.
     * @param origen Origen de la petici&oacute;n.
     * @return DetalleDireccionEntity Direccion tabulada en sus componentes.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public DetalleDireccionEntity conversionADetalleDireccion(
            String longAddressCodeGeo, String shortAddressCodeGeo, BigDecimal city,
            String barrio, String origen)
            throws ApplicationException {
        borraVariables();
        return GenerarDireccion(ValidarTipoDireccion(shortAddressCodeGeo, city, origen),
                longAddressCodeGeo, barrio);
    }

    public DrDireccion conversionADetalleDireccionDrDir(
            String longAddressCodeGeo, String shortAddressCodeGeo, BigDecimal city,
            String barrio)
            throws ApplicationException {

        DetalleDireccionEntity detalleDireccionEntity = conversionADetalleDireccion(
                longAddressCodeGeo, shortAddressCodeGeo, city, barrio);

        return converitaDrDireccion(detalleDireccionEntity);

    }

    private DrDireccion converitaDrDireccion(DetalleDireccionEntity detalleDireccionEntity) {
        DrDireccion drDirec = new DrDireccion();

        drDirec.setIdTipoDireccion(tipoDireccion);
        drDirec.setBarrio(detalleDireccionEntity.getBarrio());
        if (drDirec.getIdTipoDireccion().equals("CK")) {
            //Direccion Calle - Carrera
            drDirec.setTipoViaPrincipal(detalleDireccionEntity.getTipoviaprincipal());
            drDirec.setNumViaPrincipal(detalleDireccionEntity.getNumviaprincipal());
            drDirec.setLtViaPrincipal(detalleDireccionEntity.getLtviaprincipal());
            drDirec.setNlPostViaP(detalleDireccionEntity.getNlpostviap());
            drDirec.setBisViaPrincipal(detalleDireccionEntity.getBisviaprincipal());
            drDirec.setCuadViaPrincipal(detalleDireccionEntity.getCuadviaprincipal());
            drDirec.setPlacaDireccion(detalleDireccionEntity.getPlacadireccion());
            //Direccion Generadora de Calle - carrera
            drDirec.setTipoViaGeneradora(detalleDireccionEntity.getTipoviageneradora());
            drDirec.setNumViaGeneradora(detalleDireccionEntity.getNumviageneradora());
            drDirec.setLtViaGeneradora(detalleDireccionEntity.getLtviageneradora());
            drDirec.setNlPostViaG(detalleDireccionEntity.getNlpostviag());
            drDirec.setBisViaGeneradora(detalleDireccionEntity.getBisviageneradora());
            drDirec.setCuadViaGeneradora(detalleDireccionEntity.getCuadviageneradora());
        }

            //Direccion Manzana-Casa
            drDirec.setMzTipoNivel1(detalleDireccionEntity.getMztiponivel1());
            drDirec.setMzTipoNivel2(detalleDireccionEntity.getMztiponivel2());
            drDirec.setMzTipoNivel3(detalleDireccionEntity.getMztiponivel3());
            drDirec.setMzTipoNivel4(detalleDireccionEntity.getMztiponivel4());
            drDirec.setMzTipoNivel5(detalleDireccionEntity.getMztiponivel5());
            drDirec.setMzValorNivel1(detalleDireccionEntity.getMzvalornivel1());
            drDirec.setMzValorNivel2(detalleDireccionEntity.getMzvalornivel2());
            drDirec.setMzValorNivel3(detalleDireccionEntity.getMzvalornivel3());
            drDirec.setMzValorNivel4(detalleDireccionEntity.getMzvalornivel4());
            drDirec.setMzValorNivel5(detalleDireccionEntity.getMzvalornivel5());
        drDirec.setEstadoDirGeo(detalleDireccionEntity.getEstadoDir());
        return drDirec;
    }

    /**
     * Descripción del objetivo del método. Metodo que inicia los procesos de
     * casteo a la clase DetalleDireccion. Llama las funciones de casteo segun
     * el tipo de ciudad.
     *
     * @author Carlos Leonardo Villamil
     * @param tipoDireccion Establece el formato de el codigo de direccion del
     * geo.
     * @param CodigoDireccionGeo Codigo de Geo hasta el complemento.
     */
    private DetalleDireccionEntity GenerarDireccion(String tipoDireccion,
            String CodigoDireccionGeo,
            String barrio) throws ApplicationException {
        if (tipoDireccion.equalsIgnoreCase(TIPO_BOGOTA)) {
            prepararDireccionTipoBogota();
            extraerDireccionBogota(CodigoDireccionGeo);
            return generarDireccionTipoBogota(barrio);
        }
        if (tipoDireccion.equalsIgnoreCase(TIPO_HERENDAN_BOGOTA)) {
            prepararDireccionTipoHeredaBogota();
            extraerDireccionHeredaBogota(CodigoDireccionGeo);
            return generarDireccionTipoHeredaBogota(barrio);
        }
        if (tipoDireccion.equalsIgnoreCase(TIPO_CALI)) {
            prepararDireccionTipoCali();
            extraerDireccionCali(CodigoDireccionGeo);
            return generarDireccionTipoCali(barrio);
        }
        if (tipoDireccion.equalsIgnoreCase(TIPO_MEDELLIN)) {
            prepararDireccionTipoMedillin();
            extraerDireccionMedellin(CodigoDireccionGeo);
            return generarDireccionTipoMedellin(barrio);
        }
        if (tipoDireccion.equalsIgnoreCase(TIPO_MAZANA_CASA)) {
            prepararDireccionTipoBMC();
            extraerDireccionBCM(CodigoDireccionGeo);
            return generarDireccionTipoBmc(barrio);
        }
        if(tipoDireccion.equalsIgnoreCase(TIPO_BARRIO_MANZANA)){
            prepararDireccionTipoBM();
            extraerDireccionBM(CodigoDireccionGeo);
            return generarDireccionTipoBm(barrio);
        }

        throw new ApplicationException("NO existen Otras Mayas para Bario Casa Mansana o Intradusible");
    }

    /**
     * Descripción del objetivo del método. Metodo que inicia los procesos de
     * casteo a la clase DetalleDireccion. Llama las funciones de casteo segun
     * el tipo de ciudad.
     *
     * @author Carlos Leonardo Villamil
     * @param barrio Establece el formato de el codigo de direccion del geo.
     * @return DetalleDireccionEntity Direccion tabulada en sus componentes.
     */
    private DetalleDireccionEntity generarDireccionTipoBogota(String barrio)
            throws ApplicationException {
        DetalleDireccionEntity detalleDireccionEntity =
                new DetalleDireccionEntity();
        detalleDireccionEntity.setBarrio(barrio);
        detalleDireccionEntity.setCuadviaprincipal(this.cuadranteViaPrincipal);
        detalleDireccionEntity.setCuadviageneradora(this.cuadranteViaGeneradora);
        detalleDireccionEntity.setTipoviaprincipal(this.tipoViaPrincipal);
        detalleDireccionEntity.setNumviaprincipal(this.numeroViaPrincipal);
        detalleDireccionEntity.setNumviageneradora(this.numeroViaGeneradora);
        detalleDireccionEntity.setLtviaprincipal(this.letraNumeroViaPrincipal);
        detalleDireccionEntity.setLtviageneradora(this.letraNumeroViaGeneradora);
        //@Juan David Hernandez Ajuste Bis ||
        if (this.bisViaPrincipal.equalsIgnoreCase("BIS")) {
            if (this.letraNumeroposteriorBisViaPrincipal.equalsIgnoreCase("0") 
                    || letraNumeroposteriorBisViaPrincipal.isEmpty()) {
                detalleDireccionEntity.setBisviaprincipal(bisViaPrincipal);
            } else {
                detalleDireccionEntity.setBisviaprincipal(
                        this.letraNumeroposteriorBisViaPrincipal);
            }
        }
         //@Juan David Hernandez Ajuste Bis ||
        if (this.bisViaGeneradora.equalsIgnoreCase("BIS")) {
            if (this.letraNumeroPosteriorBisViaGeneradora.equalsIgnoreCase("0") 
                    || letraNumeroPosteriorBisViaGeneradora.isEmpty()) {
                detalleDireccionEntity.setBisviageneradora(bisViaGeneradora);
            } else {
                detalleDireccionEntity.setBisviageneradora(
                        this.letraNumeroPosteriorBisViaGeneradora);
            }
        }

        detalleDireccionEntity.setPlacadireccion(this.placaDireccion);
        castToAddressVt(detalleDireccionEntity);
        return detalleDireccionEntity;
    }

    /**
     * Descripción del objetivo del método. Metodo que inicia los procesos de
     * casteo a la clase DetalleDireccion. Llama las funciones de casteo segun
     * el tipo de ciudad.
     *
     * @author Carlos Leonardo Villamil
     * @param barrio Establece el formato de el codigo de direccion del geo.
     * @return DetalleDireccionEntity Direccion tabulada en sus componentes.
     */
    private DetalleDireccionEntity generarDireccionTipoHeredaBogota(String barrio)
            throws ApplicationException {
        DetalleDireccionEntity detalleDireccionEntity =
                new DetalleDireccionEntity();
        detalleDireccionEntity.setBarrio(barrio);
        detalleDireccionEntity.setCuadviaprincipal(this.cuadranteViaPrincipal);
        detalleDireccionEntity.setCuadviageneradora(this.cuadranteViaGeneradora);
        detalleDireccionEntity.setTipoviaprincipal(this.tipoViaPrincipal);
        detalleDireccionEntity.setNumviaprincipal(this.numeroViaPrincipal);
        detalleDireccionEntity.setNumviageneradora(this.numeroViaGeneradora);
        detalleDireccionEntity.setLtviaprincipal(this.letraNumeroViaPrincipal);
        detalleDireccionEntity.setLtviageneradora(this.letraNumeroViaGeneradora);
        if (this.bisViaPrincipal.equalsIgnoreCase("BIS")) {
            if (this.letraNumeroposteriorBisViaPrincipal.equalsIgnoreCase("0") 
                    || letraNumeroposteriorBisViaPrincipal.isEmpty()) {
                detalleDireccionEntity.setBisviaprincipal(bisViaPrincipal);
            } else {
                detalleDireccionEntity.setBisviaprincipal(
                        this.letraNumeroposteriorBisViaPrincipal);
            }
        }
        if (this.bisViaGeneradora.equalsIgnoreCase("BIS")) {
            if (this.letraNumeroPosteriorBisViaGeneradora.equalsIgnoreCase("0") 
                    || letraNumeroPosteriorBisViaGeneradora.isEmpty() ) {
                detalleDireccionEntity.setBisviageneradora(bisViaGeneradora);
            } else {
                detalleDireccionEntity.setBisviageneradora(
                        this.letraNumeroPosteriorBisViaGeneradora);
            }
        }

        detalleDireccionEntity.setPlacadireccion(this.placaDireccion);
        castToAddressVt(detalleDireccionEntity);
        return detalleDireccionEntity;

    }

    /**
     * Descripción del objetivo del método. Metodo que inicia los procesos de
     * casteo a la clase DetalleDireccion. Llama las funciones de casteo segun
     * el tipo de ciudad.
     *
     * @author Carlos Leonardo Villamil
     * @param barrio Establece el formato de el codigo de direccion del geo.
     * @return DetalleDireccionEntity Direccion tabulada en sus componentes.
     */
    private DetalleDireccionEntity generarDireccionTipoCali(String barrio)
            throws ApplicationException {
        DetalleDireccionEntity detalleDireccionEntity =
                new DetalleDireccionEntity();
        detalleDireccionEntity.setBarrio(barrio);
        detalleDireccionEntity.setCuadviaprincipal(this.cuadranteViaPrincipal);
        detalleDireccionEntity.setCuadviageneradora(this.cuadranteViaGeneradora);
        detalleDireccionEntity.setTipoviaprincipal(this.tipoViaPrincipal);
        detalleDireccionEntity.setTipoviageneradora(this.tipoViaGeneradora);
        detalleDireccionEntity.setNumviaprincipal(this.numeroViaPrincipal);
        detalleDireccionEntity.setNumviageneradora(this.numeroViaGeneradora);
        detalleDireccionEntity.setLtviaprincipal(this.letraNumeroViaPrincipal);
        detalleDireccionEntity.setLtviageneradora(this.letraNumeroViaGeneradora);
        detalleDireccionEntity.setNlpostviap(
                this.numerosLetrasPosterioresLetraViaPrincipal);
        detalleDireccionEntity.setNlpostviag(
                this.numeroLetrasPosterioresLetraViaGeneradora);
        detalleDireccionEntity.setLetra3g(
                this.numerosLetrasPosterioresPosterioresLetraViaGeneradora);
        if (this.bisViaPrincipal.equalsIgnoreCase("BIS")) {
            if (this.letraNumeroposteriorBisViaPrincipal.equalsIgnoreCase("0") 
                    || letraNumeroposteriorBisViaPrincipal.isEmpty()) {
                detalleDireccionEntity.setBisviaprincipal(bisViaPrincipal);
            } else {
                detalleDireccionEntity.setBisviaprincipal(this.letraNumeroposteriorBisViaPrincipal);
            }
        }
        if (this.bisViaGeneradora.equalsIgnoreCase("BIS")) {
            if (this.letraNumeroPosteriorBisViaGeneradora.equalsIgnoreCase("0")) {
                detalleDireccionEntity.setBisviageneradora(bisViaGeneradora);
            } else {
                detalleDireccionEntity.setBisviageneradora(this.letraNumeroPosteriorBisViaGeneradora);
            }
        }
        
        detalleDireccionEntity.setPlacadireccion(this.placaDireccion);
        castToAddressVt(detalleDireccionEntity);
        return detalleDireccionEntity;
    }

    /**
     * Descripción del objetivo del método. Metodo que inicia los procesos de
     * casteo a la clase DetalleDireccion. Llama las funciones de casteo segun
     * el tipo de ciudad.
     *
     * @author Carlos Leonardo Villamil
     * @param barrio Establece el formato de el codigo de direccion del geo.
     * @return DetalleDireccionEntity Direccion tabulada en sus componentes.
     */
    private DetalleDireccionEntity generarDireccionTipoMedellin(String barrio)
            throws ApplicationException {
        DetalleDireccionEntity detalleDireccionEntity =
                new DetalleDireccionEntity();
        detalleDireccionEntity.setBarrio(barrio);
        detalleDireccionEntity.setCuadviaprincipal(this.cuadranteViaPrincipal);
        detalleDireccionEntity.setCuadviageneradora(this.cuadranteViaGeneradora);
        detalleDireccionEntity.setTipoviaprincipal(this.tipoViaPrincipal);
        detalleDireccionEntity.setNumviaprincipal(this.numeroViaPrincipal);
        detalleDireccionEntity.setNumviageneradora(this.numeroViaGeneradora);
        detalleDireccionEntity.setLtviaprincipal(this.letraNumeroViaPrincipal);
        detalleDireccionEntity.setLtviageneradora(this.letraNumeroViaGeneradora);
        detalleDireccionEntity.setNlpostviap(
                this.numerosLetrasPosterioresLetraViaPrincipal);
        detalleDireccionEntity.setNlpostviag(
                this.numeroLetrasPosterioresLetraViaGeneradora);
        if (bisViaPrincipal != null && 
                this.bisViaPrincipal.equalsIgnoreCase("BIS")) {
            if (letraNumeroposteriorBisViaPrincipal !=  null && 
                    this.letraNumeroposteriorBisViaPrincipal.equalsIgnoreCase("0") 
                    || letraNumeroposteriorBisViaPrincipal.isEmpty()) {
                detalleDireccionEntity.setBisviaprincipal(bisViaPrincipal);
            } else {
                detalleDireccionEntity.setBisviaprincipal(
                        this.letraNumeroposteriorBisViaPrincipal);
            }
        }
        if (this.bisViaGeneradora != null && 
                this.bisViaGeneradora.equalsIgnoreCase("BIS")) {
            if (letraNumeroPosteriorBisViaGeneradora != null && 
                    this.letraNumeroPosteriorBisViaGeneradora.equalsIgnoreCase("0") 
                    ||  letraNumeroPosteriorBisViaGeneradora.isEmpty()) {
                detalleDireccionEntity.setBisviageneradora(bisViaGeneradora);
            } else {
                detalleDireccionEntity.setBisviageneradora(
                        this.letraNumeroPosteriorBisViaGeneradora);
            }
        }
        
        detalleDireccionEntity.setPlacadireccion(this.placaDireccion);
        castToAddressVt(detalleDireccionEntity);
        return detalleDireccionEntity;
    }

    private DetalleDireccionEntity generarDireccionTipoBmc(String barrio) throws ApplicationException {
        MultivalorManager multivalorManager = new MultivalorManager();

        bmcTipoNivel2 = multivalorManager.findByIdGmuAndmulValor(
                new BigDecimal(24), bmcTipoNivel2).getMulDescripcion();
        bmcValorNivel2 = multivalorManager.findByIdGmuAndmulValor(
                new BigDecimal(24), bmcValorNivel2).getMulDescripcion();
        bmcTipoNivel3 = multivalorManager.findByIdGmuAndmulValor(
                new BigDecimal(24), bmcTipoNivel3).getMulDescripcion();
        bmcValorNivel3 = multivalorManager.findByIdGmuAndmulValor(
                new BigDecimal(24), bmcValorNivel3).getMulDescripcion();
        bmcTipoNivel4 = multivalorManager.findByIdGmuAndmulValor(
                new BigDecimal(24), bmcTipoNivel4).getMulDescripcion();
        bmcValorNivel4 = multivalorManager.findByIdGmuAndmulValor(
                new BigDecimal(24), bmcValorNivel4).getMulDescripcion();
        bmcTipoNivel5 = multivalorManager.findByIdGmuAndmulValor(
                new BigDecimal(24), bmcTipoNivel5).getMulDescripcion();
        bmcValorNivel5 = multivalorManager.findByIdGmuAndmulValor(
                new BigDecimal(24), bmcValorNivel5).getMulDescripcion();
        bmcTipoNivel6 = multivalorManager.findByIdGmuAndmulValor(
                new BigDecimal(24), bmcTipoNivel6).getMulDescripcion();
        bmcValorNivel6 = multivalorManager.findByIdGmuAndmulValor(
                new BigDecimal(24), bmcValorNivel6).getMulDescripcion();

        return castToAddressVtBcm(barrio);
    }
    
     private DetalleDireccionEntity generarDireccionTipoBm(String barrio) throws ApplicationException {
        MultivalorManager multivalorManager = new MultivalorManager();

        bmcTipoNivel2 = multivalorManager.findByIdGmuAndmulValor(
                new BigDecimal(24), bmcTipoNivel2).getMulDescripcion();
        bmcValorNivel2 = multivalorManager.findByIdGmuAndmulValor(
                new BigDecimal(24), bmcValorNivel2).getMulDescripcion();
        bmcTipoNivel3 = multivalorManager.findByIdGmuAndmulValor(
                new BigDecimal(24), bmcTipoNivel3).getMulDescripcion();
        bmcValorNivel3 = multivalorManager.findByIdGmuAndmulValor(
                new BigDecimal(24), bmcValorNivel3).getMulDescripcion();
        bmcTipoNivel4 = multivalorManager.findByIdGmuAndmulValor(
                new BigDecimal(24), bmcTipoNivel4).getMulDescripcion();
        bmcValorNivel4 = multivalorManager.findByIdGmuAndmulValor(
                new BigDecimal(24), bmcValorNivel4).getMulDescripcion();
        bmcTipoNivel5 = multivalorManager.findByIdGmuAndmulValor(
                new BigDecimal(24), bmcTipoNivel5).getMulDescripcion();
        bmcValorNivel5 = multivalorManager.findByIdGmuAndmulValor(
                new BigDecimal(24), bmcValorNivel5).getMulDescripcion();
        bmcTipoNivel6 = multivalorManager.findByIdGmuAndmulValor(
                new BigDecimal(24), bmcTipoNivel6).getMulDescripcion();
        bmcValorNivel6 = multivalorManager.findByIdGmuAndmulValor(
                new BigDecimal(24), bmcValorNivel6).getMulDescripcion();

        return castToAddressVtBcm(barrio);
    }

    /**
     * Descripción del objetivo del método. Inicializa las variables de
     * componente de direccion en blanco
     *
     * @author Carlos leonardo villamil
     */
    private void borraVariables() {

        this.tipoViaPrincipal = null;
        this.numeroViaPrincipal = null;
        this.letraNumeroViaPrincipal = null;
        this.numerosLetrasPosterioresLetraViaPrincipal = null;
        this.numerosLetrasPosterioresPosterioresLetraViaPrincipal = null;
        this.bisViaPrincipal = null;
        this.letraNumeroposteriorBisViaPrincipal = null;
        this.cuadranteViaPrincipal = null;
        this.tipoViaGeneradora = null;
        this.numeroViaGeneradora = null;
        this.letraNumeroViaGeneradora = null;
        this.numeroLetrasPosterioresLetraViaGeneradora = null;
        this.numerosLetrasPosterioresPosterioresLetraViaGeneradora = null;
        this.bisViaGeneradora = null;
        this.letraNumeroPosteriorBisViaGeneradora = null;
        this.cuadranteViaGeneradora = null;
        this.placaDireccion = null;
        this.vTipoViaPrincipal = null;
        this.vNumeroViaPrincipal = null;
        this.vLetraNumeroViaPrincipal = null;
        this.vNumerosLetrasPosterioresLetraViaPrincipal = null;
        this.vNumerosLetrasPosterioresPosterioresLetraViaPrincipal = null;
        this.vBisViaPrincipal = null;
        this.vLetraNumeroposteriorBisViaPrincipal = null;
        this.vCuadranteViaPrincipal = null;
        this.vTipoViaGeneradora = null;
        this.vNumeroViaGeneradora = null;
        this.vLetraNumeroViaGeneradora = null;
        this.vNumeroLetrasPosterioresLetraViaGeneradora = null;
        this.vNumerosLetrasPosterioresPosterioresLetraViaGeneradora = null;
        this.vBisViaGeneradora = null;
        this.vLetraNumeroPosteriorBisViaGeneradora = null;
        this.vCuadranteViaGeneradora = null;
        this.vPlacaDireccion = null;
    }

    /**
     * Descripción del objetivo del método.Verifica el tipo de direccion por la
 longitud para determinar formato codigo direccion. Calculas las
 longitudes y consulta la ciudades para determinar el formato.
     *
     * @author Carlos Leonardo Villamil
     * @param codGeo Codigo Geo largo para calcular el tipo de formato.
     * @param city Determina el tipo de direccion que maneja laciodad B,M.
     * @param origen Origen de la petici&oacute;n.
     * @return String Tipo de Formato Geo del codigo segun ciudad.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public String ValidarTipoDireccion(String codGeo, BigDecimal city, String origen) throws ApplicationException {
        geograficoPolitico = negocioGeograficoPolitico.
                queryGeograficoPolitico(city);
        this.tipoDireccion = "CK";
        switch (codGeo.trim().length()) {
            case LONGITUD_TIPO_BOGOTA:
                return TIPO_BOGOTA;
            case LONGITUD_TIPO_CALI:
                return TIPO_CALI;
            case LONGITUD_TIPO_HERENDAN_BOGOTA_MEDELLIN:
                if (geograficoPolitico != null) {
                    if (geograficoPolitico.getGpoCodTipoDireccion().
                            equalsIgnoreCase("B")) {
                        return TIPO_HERENDAN_BOGOTA;
                    }
                    if (geograficoPolitico.getGpoCodTipoDireccion().
                            equalsIgnoreCase("M")) {
                        return TIPO_MEDELLIN;
                    }
                    if (geograficoPolitico.getGpoCodTipoDireccion().
                            equalsIgnoreCase("C")) {
                        return TIPO_CALI;
                    }
                    throw new ApplicationException("La ciudad no tiene tipo de direccion"
                            + ", Cali-Medellin-Bogota");
                } else {
                    throw new ApplicationException("La ciudad no se encontro en Base de "
                            + " Datos");
                }
            default:
                if (Constant.PROCESO_MASIVO_HHPP.equals(origen)) {
                    if (codGeo.trim().length() == LONGITUD_TIPO_BM_SHORT || 
                        codGeo.trim().length() == LONGITUD_TIPO_BM_LONG) {
                        return TIPO_BARRIO_MANZANA;
                    }
                }
                
                throw new ApplicationException("La longitud de codigo de direccion geo "
                        + "corresponde con establecidos, Direccion Intraducible"
                        + " o Barrio Casa Manzana");
                
        }
    }

    /**
     * Descripción del objetivo del método. Prepara las propiedades de la calse
     * para una direccion tipo bogota
     *
     * @author Carlos Leonardo Villamil
     */
    private void prepararDireccionTipoBogota() {
        cuadranteViaPrincipal = "";
        cuadranteViaGeneradora = "";
        tipoViaPrincipal = "";
        numeroViaPrincipal = "";
        numeroViaGeneradora = "";
        letraNumeroViaPrincipal = "";
        letraNumeroViaGeneradora = "";
        bisViaPrincipal = "";
        bisViaGeneradora = "";
        letraNumeroposteriorBisViaPrincipal = "";
        letraNumeroPosteriorBisViaGeneradora = "";
        placaDireccion = "";
        vCuadranteViaPrincipal = new int[]{8, 9};
        vCuadranteViaGeneradora = new int[]{9, 10};
        vTipoViaPrincipal = new int[]{10, 12};
        vNumeroViaPrincipal = new int[]{12, 15};
        vNumeroViaGeneradora = new int[]{15, 18};
        vLetraNumeroViaPrincipal = new int[]{18, 19};
        vLetraNumeroViaGeneradora = new int[]{19, 20};
        vBisViaPrincipal = new int[]{20, 23};
        vBisViaGeneradora = new int[]{23, 26};
        vLetraNumeroposteriorBisViaPrincipal = new int[]{26, 27};
        vLetraNumeroPosteriorBisViaGeneradora = new int[]{27, 28};
        vPlacaDireccion = new int[]{28, 30};
    }

    /**
     * Descripción del objetivo del método. Prepara las propiedades de la calse
     * para una direccion tipo Hereda bogota
     *
     * @author Carlos Leonardo Villamil
     */
    private void prepararDireccionTipoHeredaBogota() {
        cuadranteViaPrincipal = "";
        cuadranteViaGeneradora = "";
        tipoViaPrincipal = "";
        numeroViaPrincipal = "";
        letraNumeroViaPrincipal = "";
        letraNumeroViaGeneradora = "";
        bisViaPrincipal = "";
        bisViaGeneradora = "";
        letraNumeroposteriorBisViaPrincipal = "";
        letraNumeroPosteriorBisViaGeneradora = "";
        placaDireccion = "";
        vCuadranteViaPrincipal = new int[]{8, 9};
        vCuadranteViaGeneradora = new int[]{9, 10};
        vTipoViaPrincipal = new int[]{10, 12};
        vNumeroViaPrincipal = new int[]{12, 15};
        vNumeroViaGeneradora = new int[]{15, 18};
        vLetraNumeroViaPrincipal = new int[]{18, 19};
        vLetraNumeroViaGeneradora = new int[]{19, 20};
        vBisViaPrincipal = new int[]{20, 23};
        vBisViaGeneradora = new int[]{23, 26};
        vLetraNumeroposteriorBisViaPrincipal = new int[]{26, 27};
        vLetraNumeroPosteriorBisViaGeneradora = new int[]{27, 28};
        vPlacaDireccion = new int[]{28, 31};
    }

    /**
     * Descripción del objetivo del método. Prepara las propiedades de la calse
     * para una direccion tipo Cali
     *
     * @author Carlos Leonardo Villamil
     */
    private void prepararDireccionTipoCali() {
        cuadranteViaPrincipal = "";
        cuadranteViaGeneradora = "";
        tipoViaPrincipal = "";
        tipoViaGeneradora = "";
        numeroViaPrincipal = "";
        numeroViaGeneradora = "";
        letraNumeroViaPrincipal = "";
        letraNumeroViaGeneradora = "";
        numerosLetrasPosterioresLetraViaPrincipal = "";
        numeroLetrasPosterioresLetraViaGeneradora = "";
        numerosLetrasPosterioresPosterioresLetraViaPrincipal = "";
        numerosLetrasPosterioresPosterioresLetraViaGeneradora = "";
        numeroLetrasPosterioresLetraViaGeneradora = "";
        bisViaPrincipal = "";
        bisViaGeneradora = "";
        letraNumeroposteriorBisViaPrincipal = "";
        letraNumeroPosteriorBisViaGeneradora = "";
        placaDireccion = "";


        vCuadranteViaPrincipal = new int[]{8, 9};
        vCuadranteViaGeneradora = new int[]{9, 10};
        vTipoViaPrincipal = new int[]{10, 12};
        vTipoViaGeneradora = new int[]{12, 14};
        vNumeroViaPrincipal = new int[]{14, 17};
        vNumeroViaGeneradora = new int[]{17, 20};
        vLetraNumeroViaPrincipal = new int[]{20, 21};
        vLetraNumeroViaGeneradora = new int[]{21, 22};
        vNumerosLetrasPosterioresLetraViaPrincipal = new int[]{22, 24};
        vNumeroLetrasPosterioresLetraViaGeneradora = new int[]{24, 26};
        vNumerosLetrasPosterioresPosterioresLetraViaPrincipal = new int[]{26, 27};
        vNumerosLetrasPosterioresPosterioresLetraViaGeneradora = new int[]{26, 28};
        vBisViaPrincipal = new int[]{28, 31};
        vBisViaGeneradora = new int[]{31, 34};
        vLetraNumeroposteriorBisViaPrincipal = new int[]{34, 35};
        vLetraNumeroPosteriorBisViaGeneradora = new int[]{35, 36};
        vPlacaDireccion = new int[]{36, 39};
    }

    /**
     * Descripción del objetivo del método. Prepara las propiedades de la calse
     * para una direccion tipo Medellin
     *
     * @author Carlos Leonardo Villamil
     */
    private void prepararDireccionTipoMedillin() {
        cuadranteViaPrincipal = "";
        cuadranteViaGeneradora = "";
        tipoViaPrincipal = "";
        numeroViaPrincipal = "";
        numeroViaGeneradora = "";
        bisViaPrincipal = "";
        bisViaGeneradora = "";
        letraNumeroViaPrincipal = "";
        letraNumeroViaGeneradora = "";
        numerosLetrasPosterioresLetraViaPrincipal = "";
        numeroLetrasPosterioresLetraViaGeneradora = "";
        placaDireccion = "";

        vCuadranteViaPrincipal = new int[]{8, 9};
        vCuadranteViaGeneradora = new int[]{9, 10};
        vTipoViaPrincipal = new int[]{10, 12};
        vNumeroViaPrincipal = new int[]{12, 15};
        vNumeroViaGeneradora = new int[]{15, 18};
        vBisViaPrincipal = new int[]{18, 21};
        vBisViaGeneradora = new int[]{21, 24};
        vLetraNumeroViaPrincipal = new int[]{24, 25};
        vLetraNumeroViaGeneradora = new int[]{25, 26};
        vNumerosLetrasPosterioresLetraViaPrincipal = new int[]{26, 27};
        vNumeroLetrasPosterioresLetraViaGeneradora = new int[]{27, 28};
        vPlacaDireccion = new int[]{28, 31};

    }

    void prepararDireccionTipoBMC() {
        bmcTipoNivel2 = "";
        bmcValorNivel2 = "";
        bmcTipoNivel3 = "";
        bmcValorNivel3 = "";
        bmcTipoNivel4 = "";
        bmcValorNivel4 = "";
        bmcTipoNivel5 = "";
        bmcValorNivel5 = "";
        bmcTipoNivel6 = "";
        bmcValorNivel6 = "";

        vBmcTipoNivel2 = new int[]{11, 13};
        vBmcValorNivel2 = new int[]{13, 19};
        vBmcTipoNivel3 = new int[]{19, 21};
        vBmcValorNivel3 = new int[]{21, 27};
        vBmcTipoNivel4 = new int[]{27, 29};
        vBmcValorNivel4 = new int[]{29, 35};
        vBmcTipoNivel5 = new int[]{35, 37};
        vBmcValorNivel5 = new int[]{37, 43};
        vBmcTipoNivel6 = new int[]{43, 45};
        vBmcValorNivel6 = new int[]{45, 51};
    }
    void prepararDireccionTipoBM() {
        bmcTipoNivel2 = "";
        bmcValorNivel2 = "";
        bmcTipoNivel3 = "";
        bmcValorNivel3 = "";
        bmcTipoNivel4 = "";
        bmcValorNivel4 = "";
        bmcTipoNivel5 = "";
        bmcValorNivel5 = "";
        bmcTipoNivel6 = "";
        bmcValorNivel6 = "";

        vBmcTipoNivel2 = new int[]{11, 13};
        vBmcValorNivel2 = new int[]{13, 19};
        vBmcTipoNivel3 = new int[]{19, 21};
        vBmcValorNivel3 = new int[]{21, 27};
        vBmcTipoNivel4 = new int[]{27, 29};
        vBmcValorNivel4 = new int[]{29, 35};
        vBmcTipoNivel5 = new int[]{35, 37};
        vBmcValorNivel5 = new int[]{37, 43};
        vBmcTipoNivel6 = new int[]{43, 45};
        vBmcValorNivel6 = new int[]{45, 51};
    }

    /**
     * Descripción del objetivo del método. Extrae la direccion de tipo Bogota
     * de el codigo direccion servinformacion
     *
     * @author Carlos Leonardo Villamil
     * @param CodigoDireccionGeo Codigo direccion Geo.
     */
    private void extraerDireccionBogota(String CodigoDireccionGeo) {
        try{
        cuadranteViaPrincipal = CodigoDireccionGeo.
                substring(vCuadranteViaPrincipal[indiceInicial],
                vCuadranteViaPrincipal[LongitudATomar]);
        cuadranteViaGeneradora = CodigoDireccionGeo.
                substring(vCuadranteViaGeneradora[indiceInicial],
                vCuadranteViaGeneradora[LongitudATomar]);
        tipoViaPrincipal = CodigoDireccionGeo.
                substring(vTipoViaPrincipal[indiceInicial],
                vTipoViaPrincipal[LongitudATomar]);
        numeroViaPrincipal = CodigoDireccionGeo.
                substring(vNumeroViaPrincipal[indiceInicial],
                vNumeroViaPrincipal[LongitudATomar]);
        numeroViaGeneradora = CodigoDireccionGeo.
                substring(vNumeroViaGeneradora[indiceInicial],
                vNumeroViaGeneradora[LongitudATomar]);
        letraNumeroViaPrincipal = CodigoDireccionGeo.
                substring(vLetraNumeroViaPrincipal[indiceInicial],
                vLetraNumeroViaPrincipal[LongitudATomar]);
        letraNumeroViaGeneradora = CodigoDireccionGeo.
                substring(vLetraNumeroViaGeneradora[indiceInicial],
                vLetraNumeroViaGeneradora[LongitudATomar]);
        bisViaPrincipal = CodigoDireccionGeo.
                substring(vBisViaPrincipal[indiceInicial],
                vBisViaPrincipal[LongitudATomar]);
        bisViaGeneradora = CodigoDireccionGeo.
                substring(vBisViaGeneradora[indiceInicial],
                vBisViaGeneradora[LongitudATomar]);
        letraNumeroposteriorBisViaPrincipal = CodigoDireccionGeo.
                substring(vLetraNumeroposteriorBisViaPrincipal[indiceInicial],
                vLetraNumeroposteriorBisViaPrincipal[LongitudATomar]);
        letraNumeroPosteriorBisViaGeneradora = CodigoDireccionGeo.
                substring(vLetraNumeroPosteriorBisViaGeneradora[indiceInicial],
                vLetraNumeroPosteriorBisViaGeneradora[LongitudATomar]);
        placaDireccion = CodigoDireccionGeo.substring(
                vPlacaDireccion[indiceInicial],
                vPlacaDireccion[LongitudATomar]);

        if (!cuadranteViaPrincipal.matches("(N|S|E|O)")) {
            cuadranteViaPrincipal = "";
        }
        if (!cuadranteViaGeneradora.matches("(N|S|E|O)")) {
            cuadranteViaGeneradora = "";
        }
        numeroViaPrincipal = Integer.toString(
                Integer.parseInt(numeroViaPrincipal));
        numeroViaGeneradora = Integer.toString(
                Integer.parseInt(numeroViaGeneradora));
        letraNumeroViaPrincipal = formatearNumeroLetra(letraNumeroViaPrincipal);
        letraNumeroViaGeneradora = formatearNumeroLetra(letraNumeroViaGeneradora);
        if (!bisViaPrincipal.matches("(BIS)")) {
            bisViaPrincipal = "";
        }
        if (!bisViaGeneradora.matches("(BIS)")) {
            bisViaGeneradora = "";
        }

        letraNumeroposteriorBisViaPrincipal = formatearNumeroLetra(letraNumeroposteriorBisViaPrincipal);
        letraNumeroPosteriorBisViaGeneradora = formatearNumeroLetra(letraNumeroPosteriorBisViaGeneradora);
        placaDireccion = Integer.toString(Integer.parseInt(placaDireccion));
        }catch(StringIndexOutOfBoundsException ex){
            LOGGER.info(ex.getMessage());
        }
    }

    /**
     * Descripción del objetivo del método. Extrae la direccion de tipo Hereda
     * Bogota de el codigo direccion servinformacion
     *
     * @author Carlos Leonardo Villamil
     * @param CodigoDireccionGeo Codigo direccion Geo.
     */
    private void extraerDireccionHeredaBogota(String CodigoDireccionGeo) {
        try{
        cuadranteViaPrincipal = CodigoDireccionGeo.
                substring(vCuadranteViaPrincipal[indiceInicial],
                vCuadranteViaPrincipal[LongitudATomar]);
        cuadranteViaGeneradora = CodigoDireccionGeo.
                substring(vCuadranteViaGeneradora[indiceInicial],
                vCuadranteViaGeneradora[LongitudATomar]);
        tipoViaPrincipal = CodigoDireccionGeo.
                substring(vTipoViaPrincipal[indiceInicial],
                vTipoViaPrincipal[LongitudATomar]);
        numeroViaPrincipal = CodigoDireccionGeo.
                substring(vNumeroViaPrincipal[indiceInicial],
                vNumeroViaPrincipal[LongitudATomar]);
        numeroViaGeneradora = CodigoDireccionGeo.
                substring(vNumeroViaGeneradora[indiceInicial],
                vNumeroViaGeneradora[LongitudATomar]);
        letraNumeroViaPrincipal = CodigoDireccionGeo.
                substring(vLetraNumeroViaPrincipal[indiceInicial],
                vLetraNumeroViaPrincipal[LongitudATomar]);
        letraNumeroViaGeneradora = CodigoDireccionGeo.
                substring(vLetraNumeroViaGeneradora[indiceInicial],
                vLetraNumeroViaGeneradora[LongitudATomar]);
        bisViaPrincipal = CodigoDireccionGeo.
                substring(vBisViaPrincipal[indiceInicial],
                vBisViaPrincipal[LongitudATomar]);
        bisViaGeneradora = CodigoDireccionGeo.
                substring(vBisViaGeneradora[indiceInicial],
                vBisViaGeneradora[LongitudATomar]);
        letraNumeroposteriorBisViaPrincipal = CodigoDireccionGeo.
                substring(vLetraNumeroposteriorBisViaPrincipal[indiceInicial],
                vLetraNumeroposteriorBisViaPrincipal[LongitudATomar]);
        letraNumeroPosteriorBisViaGeneradora = CodigoDireccionGeo.
                substring(vLetraNumeroPosteriorBisViaGeneradora[indiceInicial],
                vLetraNumeroPosteriorBisViaGeneradora[LongitudATomar]);
        placaDireccion = CodigoDireccionGeo.
                substring(vPlacaDireccion[indiceInicial],
                vPlacaDireccion[LongitudATomar]);

        if (!cuadranteViaPrincipal.matches("(N|S|E|O)")) {
            cuadranteViaPrincipal = "";
        }
        if (!cuadranteViaGeneradora.matches("(N|S|E|O)")) {
            cuadranteViaGeneradora = "";
        }
        numeroViaPrincipal = Integer.toString(
                Integer.parseInt(numeroViaPrincipal));
        numeroViaGeneradora = Integer.toString(
                Integer.parseInt(numeroViaGeneradora));
        letraNumeroViaPrincipal = formatearNumeroLetra(letraNumeroViaPrincipal);
        letraNumeroViaGeneradora = formatearNumeroLetra(letraNumeroViaGeneradora);
        if (!bisViaPrincipal.matches("(BIS)")) {
            bisViaPrincipal = "";
        }
        if (!bisViaGeneradora.matches("(BIS)")) {
            bisViaGeneradora = "";
        }
        letraNumeroposteriorBisViaPrincipal = formatearNumeroLetra(letraNumeroposteriorBisViaPrincipal);

        letraNumeroPosteriorBisViaGeneradora = formatearNumeroLetra(letraNumeroPosteriorBisViaGeneradora);
        placaDireccion = Integer.toString(Integer.parseInt(placaDireccion));
        }catch(StringIndexOutOfBoundsException ex){
            LOGGER.info(ex.getMessage());
        }
    }

    /**
     * Descripción del objetivo del método. Extrae la direccion de tipo Cali de
     * el codigo direccion servinformacion
     *
     * @author Carlos Leonardo Villamil
     * @param CodigoDireccionGeo Codigo direccion Geo.
     */
    private void extraerDireccionCali(String CodigoDireccionGeo) throws ApplicationException {
        try{
        cuadranteViaPrincipal = CodigoDireccionGeo.substring(
                vCuadranteViaPrincipal[indiceInicial],
                vCuadranteViaPrincipal[LongitudATomar]);
        cuadranteViaGeneradora = CodigoDireccionGeo.substring(
                vCuadranteViaGeneradora[indiceInicial],
                vCuadranteViaGeneradora[LongitudATomar]);
        tipoViaPrincipal = CodigoDireccionGeo.substring(
                vTipoViaPrincipal[indiceInicial],
                vTipoViaPrincipal[LongitudATomar]);
        tipoViaGeneradora = CodigoDireccionGeo.substring(
                vTipoViaGeneradora[indiceInicial],
                vTipoViaGeneradora[LongitudATomar]);
        numeroViaPrincipal = CodigoDireccionGeo.substring(
                vNumeroViaPrincipal[indiceInicial],
                vNumeroViaPrincipal[LongitudATomar]);
        numeroViaGeneradora = CodigoDireccionGeo.substring(
                vNumeroViaGeneradora[indiceInicial],
                vNumeroViaGeneradora[LongitudATomar]);
        letraNumeroViaPrincipal = CodigoDireccionGeo.substring(
                vLetraNumeroViaPrincipal[indiceInicial],
                vLetraNumeroViaPrincipal[LongitudATomar]);
        letraNumeroViaGeneradora = CodigoDireccionGeo.substring(
                vLetraNumeroViaGeneradora[indiceInicial],
                vLetraNumeroViaGeneradora[LongitudATomar]);

        numerosLetrasPosterioresLetraViaPrincipal = CodigoDireccionGeo.
                substring(
                vNumerosLetrasPosterioresLetraViaPrincipal[indiceInicial],
                vNumerosLetrasPosterioresLetraViaPrincipal[LongitudATomar]);

        numerosLetrasPosterioresPosterioresLetraViaPrincipal = CodigoDireccionGeo.
                substring(
                vNumerosLetrasPosterioresPosterioresLetraViaPrincipal[indiceInicial],
                vNumerosLetrasPosterioresPosterioresLetraViaPrincipal[LongitudATomar]);

        numeroLetrasPosterioresLetraViaGeneradora = CodigoDireccionGeo.
                substring(
                vNumeroLetrasPosterioresLetraViaGeneradora[indiceInicial],
                vNumeroLetrasPosterioresLetraViaGeneradora[LongitudATomar]);

        numerosLetrasPosterioresPosterioresLetraViaGeneradora = CodigoDireccionGeo.
                substring(
                vNumerosLetrasPosterioresPosterioresLetraViaGeneradora[indiceInicial],
                vNumerosLetrasPosterioresPosterioresLetraViaGeneradora[LongitudATomar]);



        bisViaPrincipal = CodigoDireccionGeo.
                substring(vBisViaPrincipal[indiceInicial],
                vBisViaPrincipal[LongitudATomar]);
        bisViaGeneradora = CodigoDireccionGeo.
                substring(vBisViaGeneradora[indiceInicial],
                vBisViaGeneradora[LongitudATomar]);
        letraNumeroposteriorBisViaPrincipal = CodigoDireccionGeo.
                substring(vLetraNumeroposteriorBisViaPrincipal[indiceInicial],
                vLetraNumeroposteriorBisViaPrincipal[LongitudATomar]);
        letraNumeroPosteriorBisViaGeneradora = CodigoDireccionGeo.
                substring(vLetraNumeroPosteriorBisViaGeneradora[indiceInicial],
                vLetraNumeroPosteriorBisViaGeneradora[LongitudATomar]);
        placaDireccion = CodigoDireccionGeo.
                substring(vPlacaDireccion[indiceInicial],
                vPlacaDireccion[LongitudATomar]);


        if (!cuadranteViaPrincipal.matches("(N|S|E|O)")) {
            cuadranteViaPrincipal = "";
        }
        if (!cuadranteViaGeneradora.matches("(N|S|E|O)")) {
            cuadranteViaGeneradora = "";
        }

        if (tipoViaGeneradora.equalsIgnoreCase("00")) {
            tipoViaGeneradora = "";
        }

        numeroViaPrincipal = Integer.
                toString(Integer.parseInt(numeroViaPrincipal));
        numeroViaGeneradora = Integer.
                toString(Integer.parseInt(numeroViaGeneradora));
        letraNumeroViaPrincipal = formatearNumeroLetra(letraNumeroViaPrincipal);
        letraNumeroViaGeneradora = formatearNumeroLetra(letraNumeroViaGeneradora);
        numerosLetrasPosterioresLetraViaPrincipal = formatearNumeroLetra(numerosLetrasPosterioresLetraViaPrincipal);
        numerosLetrasPosterioresPosterioresLetraViaPrincipal = formatearNumeroLetra(numerosLetrasPosterioresLetraViaPrincipal);
        numeroLetrasPosterioresLetraViaGeneradora = formatearNumeroLetra(numeroLetrasPosterioresLetraViaGeneradora);
        numerosLetrasPosterioresPosterioresLetraViaGeneradora = formatearNumeroLetra(numerosLetrasPosterioresPosterioresLetraViaGeneradora);

        if (!bisViaPrincipal.matches("(BIS)")) {
            bisViaPrincipal = "";
        }
        if (!bisViaGeneradora.matches("(BIS)")) {
            bisViaGeneradora = "";
        }
        letraNumeroposteriorBisViaPrincipal = formatearNumeroLetra(letraNumeroposteriorBisViaPrincipal);
        letraNumeroPosteriorBisViaGeneradora = formatearNumeroLetra(letraNumeroPosteriorBisViaGeneradora);
        placaDireccion = Integer.toString(Integer.parseInt(placaDireccion));
               
        }catch(StringIndexOutOfBoundsException ex){
            throw new ApplicationException("Error extrayendo direccion CALI: "+ex.getMessage()+"");
        }
    }

    /**
     * Descripción del objetivo del método. Extrae la direccion de tipo Medellin
     * de el codigo direccion servinformacion
     *
     * @author Carlos Leonardo Villamil
     * @param CodigoDireccionGeo Codigo direccion Geo.
     */
    private void extraerDireccionMedellin(String CodigoDireccionGeo) {
        try{
        cuadranteViaPrincipal = CodigoDireccionGeo.substring(
                vCuadranteViaPrincipal[indiceInicial],
                vCuadranteViaPrincipal[LongitudATomar]);
        cuadranteViaGeneradora = CodigoDireccionGeo.substring(
                vCuadranteViaGeneradora[indiceInicial],
                vCuadranteViaGeneradora[LongitudATomar]);
        tipoViaPrincipal = CodigoDireccionGeo.substring(
                vTipoViaPrincipal[indiceInicial],
                vTipoViaPrincipal[LongitudATomar]);
        numeroViaPrincipal = CodigoDireccionGeo.substring(
                vNumeroViaPrincipal[indiceInicial],
                vNumeroViaPrincipal[LongitudATomar]);
        numeroViaGeneradora = CodigoDireccionGeo.substring(
                vNumeroViaGeneradora[indiceInicial],
                vNumeroViaGeneradora[LongitudATomar]);
        bisViaPrincipal = CodigoDireccionGeo.substring(
                vBisViaPrincipal[indiceInicial],
                vBisViaPrincipal[LongitudATomar]);
        bisViaGeneradora = CodigoDireccionGeo.substring(
                vBisViaGeneradora[indiceInicial], vBisViaGeneradora[LongitudATomar]);
        letraNumeroViaPrincipal = CodigoDireccionGeo.substring(
                vLetraNumeroViaPrincipal[indiceInicial],
                vLetraNumeroViaPrincipal[LongitudATomar]);
        letraNumeroViaGeneradora = CodigoDireccionGeo.substring(
                vLetraNumeroViaGeneradora[indiceInicial],
                vLetraNumeroViaGeneradora[LongitudATomar]);
        numerosLetrasPosterioresLetraViaPrincipal = CodigoDireccionGeo.
                substring(
                vNumerosLetrasPosterioresLetraViaPrincipal[indiceInicial],
                vNumerosLetrasPosterioresLetraViaPrincipal[LongitudATomar]);
        numeroLetrasPosterioresLetraViaGeneradora = CodigoDireccionGeo
                .substring(
                vNumeroLetrasPosterioresLetraViaGeneradora[indiceInicial],
                vNumeroLetrasPosterioresLetraViaGeneradora[LongitudATomar]);
        placaDireccion = CodigoDireccionGeo.substring(
                vPlacaDireccion[indiceInicial],
                vPlacaDireccion[LongitudATomar]);

        if (!cuadranteViaPrincipal.matches("(N|S|E|O)")) {
            cuadranteViaPrincipal = "";
        }
        if (!cuadranteViaGeneradora.matches("(N|S|E|O)")) {
            cuadranteViaGeneradora = "";
        }
        numeroViaPrincipal = Integer.toString(
                Integer.parseInt(numeroViaPrincipal));
        numeroViaGeneradora = Integer.toString(
                Integer.parseInt(numeroViaGeneradora));
        letraNumeroViaPrincipal = formatearNumeroLetra(letraNumeroViaPrincipal);
        letraNumeroViaGeneradora = formatearNumeroLetra(letraNumeroViaGeneradora);
        numerosLetrasPosterioresLetraViaPrincipal = formatearNumeroLetra(numerosLetrasPosterioresLetraViaPrincipal);
        numeroLetrasPosterioresLetraViaGeneradora = formatearNumeroLetra(numeroLetrasPosterioresLetraViaGeneradora);
        if (!bisViaPrincipal.matches("(BIS)")) {
            bisViaPrincipal = "";
        }
        if (!bisViaGeneradora.matches("(BIS)")) {
            bisViaGeneradora = "";
        }
        placaDireccion = Integer.toString(Integer.parseInt(placaDireccion));
        }catch(StringIndexOutOfBoundsException ex){
            LOGGER.info(ex.getMessage());
        }
    }
    
    private String formatearNumeroLetra(String numeroLetraToValidate){
        String strResult ="";
        
        try {
            strResult = Integer.toString(Integer.parseInt(numeroLetraToValidate));
            if (strResult.equalsIgnoreCase("0")){
                strResult ="";
            }
        } catch (NumberFormatException e) {
			String msg = "Se produjo un error al momento de ejecutar el método '"+
			ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
			LOGGER.error(msg);
            if (numeroLetraToValidate.matches(REGULAR_EXPRESION_LETRA_NUMERO)){
                strResult =numeroLetraToValidate;
            }
        }
        return strResult;
        
    }

    private void extraerDireccionBCM(String CodigoDireccionGeo) {
        try{
        bmcTipoNivel2 = CodigoDireccionGeo.substring(vBmcTipoNivel2[indiceInicial], vBmcTipoNivel2[LongitudATomar]);
        bmcValorNivel2 = CodigoDireccionGeo.substring(vBmcValorNivel2[indiceInicial], vBmcValorNivel2[LongitudATomar]);
        bmcTipoNivel3 = CodigoDireccionGeo.substring(vBmcTipoNivel3[indiceInicial], vBmcTipoNivel3[LongitudATomar]);
        bmcValorNivel3 = CodigoDireccionGeo.substring(vBmcValorNivel3[indiceInicial], vBmcValorNivel3[LongitudATomar]);
        bmcTipoNivel4 = CodigoDireccionGeo.substring(vBmcTipoNivel4[indiceInicial], vBmcTipoNivel4[LongitudATomar]);
        bmcValorNivel4 = CodigoDireccionGeo.substring(vBmcValorNivel4[indiceInicial], vBmcValorNivel4[LongitudATomar]);
        bmcTipoNivel5 = CodigoDireccionGeo.substring(vBmcTipoNivel5[indiceInicial], vBmcTipoNivel5[LongitudATomar]);
        bmcValorNivel5 = CodigoDireccionGeo.substring(vBmcValorNivel5[indiceInicial], vBmcValorNivel5[LongitudATomar]);
        bmcTipoNivel6 = CodigoDireccionGeo.substring(vBmcTipoNivel6[indiceInicial], vBmcTipoNivel6[LongitudATomar]);
        bmcValorNivel6 = CodigoDireccionGeo.substring(vBmcValorNivel6[indiceInicial], vBmcValorNivel6[LongitudATomar]);

        if (bmcTipoNivel2.equalsIgnoreCase("00")) {
            bmcTipoNivel2 = "";
        }
        if (bmcTipoNivel3.equalsIgnoreCase("00")) {
            bmcTipoNivel3 = "";
        }
        if (bmcTipoNivel4.equalsIgnoreCase("00")) {
            bmcTipoNivel4 = "";
        }
        if (bmcTipoNivel5.equalsIgnoreCase("00")) {
            bmcTipoNivel5 = "";
        }
        if (bmcTipoNivel6.equalsIgnoreCase("00")) {
            bmcTipoNivel6 = "";
        }

        if (bmcValorNivel2.equalsIgnoreCase("0000")) {
            bmcValorNivel2 = "";
        } else {
            bmcValorNivel2 = bmcValorNivel2.replaceFirst("0", "");
        }

        if (bmcValorNivel3.equalsIgnoreCase("0000")) {
            bmcValorNivel3 = "";
        } else {
            bmcValorNivel3 = bmcValorNivel2.replaceFirst("0", "");
        }

        if (bmcValorNivel4.equalsIgnoreCase("0000")) {
            bmcValorNivel4 = "";
        } else {
            bmcValorNivel4 = bmcValorNivel2.replaceFirst("0", "");
        }
        if (bmcValorNivel5.equalsIgnoreCase("0000")) {
            bmcValorNivel5 = "";
        } else {
            bmcValorNivel5 = bmcValorNivel2.replaceFirst("0", "");
        }

        if (bmcValorNivel6.equalsIgnoreCase("0000")) {
            bmcValorNivel6 = "";
        } else {
            bmcValorNivel6 = bmcValorNivel2.replaceFirst("0", "");
        }
        }catch(StringIndexOutOfBoundsException ex){
            LOGGER.info(ex.getMessage());
        }
    }
    
     private void extraerDireccionBM(String CodigoDireccionGeo) {
        try {
            bmcTipoNivel2 = CodigoDireccionGeo.substring(vBmcTipoNivel2[indiceInicial], vBmcTipoNivel2[LongitudATomar]);
            bmcValorNivel2 = CodigoDireccionGeo.substring(vBmcValorNivel2[indiceInicial], vBmcValorNivel2[LongitudATomar]);
            bmcTipoNivel3 = CodigoDireccionGeo.substring(vBmcTipoNivel3[indiceInicial], vBmcTipoNivel3[LongitudATomar]);
            bmcValorNivel3 = CodigoDireccionGeo.substring(vBmcValorNivel3[indiceInicial], vBmcValorNivel3[LongitudATomar]);
            bmcTipoNivel4 = CodigoDireccionGeo.substring(vBmcTipoNivel4[indiceInicial], vBmcTipoNivel4[LongitudATomar]);
            bmcValorNivel4 = CodigoDireccionGeo.substring(vBmcValorNivel4[indiceInicial], vBmcValorNivel4[LongitudATomar]);
            bmcTipoNivel5 = CodigoDireccionGeo.substring(vBmcTipoNivel5[indiceInicial], vBmcTipoNivel5[LongitudATomar]);
            bmcValorNivel5 = CodigoDireccionGeo.substring(vBmcValorNivel5[indiceInicial], vBmcValorNivel5[LongitudATomar]);
            bmcTipoNivel6 = CodigoDireccionGeo.substring(vBmcTipoNivel6[indiceInicial], vBmcTipoNivel6[LongitudATomar]);
            bmcValorNivel6 = CodigoDireccionGeo.substring(vBmcValorNivel6[indiceInicial], vBmcValorNivel6[LongitudATomar]);
            
            if (bmcTipoNivel2.equalsIgnoreCase("00")) {
                bmcTipoNivel2 = "";
            }
            if (bmcTipoNivel3.equalsIgnoreCase("00")) {
                bmcTipoNivel3 = "";
            }
            if (bmcTipoNivel4.equalsIgnoreCase("00")) {
                bmcTipoNivel4 = "";
            }
            if (bmcTipoNivel5.equalsIgnoreCase("00")) {
                bmcTipoNivel5 = "";
            }
            if (bmcTipoNivel6.equalsIgnoreCase("00")) {
                bmcTipoNivel6 = "";
            }
            
            if (bmcValorNivel2.equalsIgnoreCase("0000")) {
                bmcValorNivel2 = "";
            } else {
                bmcValorNivel2 = bmcValorNivel2.replaceFirst("0", "");
            }
            
            if (bmcValorNivel3.equalsIgnoreCase("0000")) {
                bmcValorNivel3 = "";
            } else {
                bmcValorNivel3 = bmcValorNivel2.replaceFirst("0", "");
            }
            
            if (bmcValorNivel4.equalsIgnoreCase("0000")) {
                bmcValorNivel4 = "";
            } else {
                bmcValorNivel4 = bmcValorNivel2.replaceFirst("0", "");
            }
            if (bmcValorNivel5.equalsIgnoreCase("0000")) {
                bmcValorNivel5 = "";
            } else {
                bmcValorNivel5 = bmcValorNivel2.replaceFirst("0", "");
            }
            
            if (bmcValorNivel6.equalsIgnoreCase("0000")) {
                bmcValorNivel6 = "";
            } else {
                bmcValorNivel6 = bmcValorNivel2.replaceFirst("0", "");
            }
        } catch (StringIndexOutOfBoundsException ex) {
            LOGGER.info(ex.getMessage());
        }
    }

    private DetalleDireccionEntity castToAddressVt(
            DetalleDireccionEntity detalleDireccionEntity)
            throws ApplicationException {
        MultivalorManager multivalorManager = new MultivalorManager();
        String varTipoviaprincipal = multivalorManager.findByIdGmuAndmulValor(
                new BigDecimal(24), detalleDireccionEntity.getTipoviaprincipal()).
                getMulDescripcion();
        String varTipoviageneradora = multivalorManager.findByIdGmuAndmulValor(
                new BigDecimal(24), detalleDireccionEntity.getTipoviageneradora()).
                getMulDescripcion();
        String varCuadviaprincipal = multivalorManager.findByIdGmuAndmulValor(
                new BigDecimal(24), detalleDireccionEntity.getCuadviaprincipal()).
                getMulDescripcion();
        String varCuadviageneradora = multivalorManager.findByIdGmuAndmulValor(
                new BigDecimal(24), detalleDireccionEntity.getCuadviageneradora()).
                getMulDescripcion();

        varTipoviaprincipal = (varTipoviaprincipal == null ? "" : varTipoviaprincipal);
        varTipoviageneradora = (varTipoviageneradora == null ? "" : varTipoviageneradora);
        varCuadviaprincipal = (varCuadviaprincipal == null ? "" : varCuadviaprincipal);
        varCuadviageneradora = (varCuadviageneradora == null ? "" : varCuadviageneradora);

        detalleDireccionEntity.setTipoviaprincipal(varTipoviaprincipal);
        detalleDireccionEntity.setTipoviageneradora(varTipoviageneradora);
        detalleDireccionEntity.setCuadviaprincipal(varCuadviaprincipal);
        detalleDireccionEntity.setCuadviageneradora(varCuadviageneradora);





        return detalleDireccionEntity;
    }

    private DetalleDireccionEntity castToAddressVtBcm(String barrio) {
        DetalleDireccionEntity detalleDireccionEntity = new DetalleDireccionEntity();
        try {

            CmtComponenteDireccionesMglManager cmtComponenteDireccionesMglManager = new CmtComponenteDireccionesMglManager();

            ConfigurationAddressComponent configurationAddressComponent = cmtComponenteDireccionesMglManager.getConfiguracionComponente();


            List<OpcionIdNombre> listNombreBmOp1 = configurationAddressComponent.getBmValues().getSubdivision1Bm();

            for (OpcionIdNombre opcionIdNombre : listNombreBmOp1) {
                if (opcionIdNombre.getDescripcion().equalsIgnoreCase(bmcTipoNivel2)) {
                    detalleDireccionEntity.setMztiponivel1(bmcTipoNivel2);
                    detalleDireccionEntity.setMzvalornivel1(bmcValorNivel2);
                }
                if (opcionIdNombre.getDescripcion().equalsIgnoreCase(bmcTipoNivel3)) {
                    detalleDireccionEntity.setMztiponivel1(bmcTipoNivel3);
                    detalleDireccionEntity.setMzvalornivel1(bmcValorNivel3);
                }
                if (opcionIdNombre.getDescripcion().equalsIgnoreCase(bmcTipoNivel4)) {
                    detalleDireccionEntity.setMztiponivel1(bmcTipoNivel4);
                    detalleDireccionEntity.setMzvalornivel1(bmcValorNivel4);
                }
                if (opcionIdNombre.getDescripcion().equalsIgnoreCase(bmcTipoNivel5)) {
                    detalleDireccionEntity.setMztiponivel1(bmcTipoNivel5);
                    detalleDireccionEntity.setMzvalornivel1(bmcValorNivel5);
                }
                if (opcionIdNombre.getDescripcion().equalsIgnoreCase(bmcTipoNivel6)) {
                    detalleDireccionEntity.setMztiponivel1(bmcTipoNivel6);
                    detalleDireccionEntity.setMzvalornivel1(bmcValorNivel6);
                }
            }

            List<OpcionIdNombre> listNombreBmOp2 = configurationAddressComponent.getBmValues().getSubdivision2Bm();

            for (OpcionIdNombre opcionIdNombre : listNombreBmOp2) {
                if (opcionIdNombre.getDescripcion().equalsIgnoreCase(bmcTipoNivel2)) {
                    detalleDireccionEntity.setMztiponivel1(bmcTipoNivel2);
                    detalleDireccionEntity.setMzvalornivel1(bmcValorNivel2);
                }
                if (opcionIdNombre.getDescripcion().equalsIgnoreCase(bmcTipoNivel3)) {
                    detalleDireccionEntity.setMztiponivel1(bmcTipoNivel3);
                    detalleDireccionEntity.setMzvalornivel1(bmcValorNivel3);
                }
                if (opcionIdNombre.getDescripcion().equalsIgnoreCase(bmcTipoNivel4)) {
                    detalleDireccionEntity.setMztiponivel1(bmcTipoNivel4);
                    detalleDireccionEntity.setMzvalornivel1(bmcValorNivel4);
                }
                if (opcionIdNombre.getDescripcion().equalsIgnoreCase(bmcTipoNivel5)) {
                    detalleDireccionEntity.setMztiponivel1(bmcTipoNivel5);
                    detalleDireccionEntity.setMzvalornivel1(bmcValorNivel5);
                }
                if (opcionIdNombre.getDescripcion().equalsIgnoreCase(bmcTipoNivel6)) {
                    detalleDireccionEntity.setMztiponivel1(bmcTipoNivel6);
                    detalleDireccionEntity.setMzvalornivel1(bmcValorNivel6);
                }
            }



            List<OpcionIdNombre> listNombreBmOp3 = configurationAddressComponent.getBmValues().getSubdivision3Bm();

            for (OpcionIdNombre opcionIdNombre : listNombreBmOp3) {
                if (opcionIdNombre.getDescripcion().equalsIgnoreCase(bmcTipoNivel2)) {
                    detalleDireccionEntity.setMztiponivel1(bmcTipoNivel2);
                    detalleDireccionEntity.setMzvalornivel1(bmcValorNivel2);
                }
                if (opcionIdNombre.getDescripcion().equalsIgnoreCase(bmcTipoNivel3)) {
                    detalleDireccionEntity.setMztiponivel1(bmcTipoNivel3);
                    detalleDireccionEntity.setMzvalornivel1(bmcValorNivel3);
                }
                if (opcionIdNombre.getDescripcion().equalsIgnoreCase(bmcTipoNivel4)) {
                    detalleDireccionEntity.setMztiponivel1(bmcTipoNivel4);
                    detalleDireccionEntity.setMzvalornivel1(bmcValorNivel4);
                }
                if (opcionIdNombre.getDescripcion().equalsIgnoreCase(bmcTipoNivel5)) {
                    detalleDireccionEntity.setMztiponivel1(bmcTipoNivel5);
                    detalleDireccionEntity.setMzvalornivel1(bmcValorNivel5);
                }
                if (opcionIdNombre.getDescripcion().equalsIgnoreCase(bmcTipoNivel6)) {
                    detalleDireccionEntity.setMztiponivel1(bmcTipoNivel6);
                    detalleDireccionEntity.setMzvalornivel1(bmcValorNivel6);
                }
            }



            List<OpcionIdNombre> listNombreBmOp4 = configurationAddressComponent.getBmValues().getSubdivision4Bm();

            for (OpcionIdNombre opcionIdNombre : listNombreBmOp2) {
                if (opcionIdNombre.getDescripcion().equalsIgnoreCase(bmcTipoNivel2)) {
                    detalleDireccionEntity.setMztiponivel1(bmcTipoNivel2);
                    detalleDireccionEntity.setMzvalornivel1(bmcValorNivel2);
                }
                if (opcionIdNombre.getDescripcion().equalsIgnoreCase(bmcTipoNivel3)) {
                    detalleDireccionEntity.setMztiponivel1(bmcTipoNivel3);
                    detalleDireccionEntity.setMzvalornivel1(bmcValorNivel3);
                }
                if (opcionIdNombre.getDescripcion().equalsIgnoreCase(bmcTipoNivel4)) {
                    detalleDireccionEntity.setMztiponivel1(bmcTipoNivel4);
                    detalleDireccionEntity.setMzvalornivel1(bmcValorNivel4);
                }
                if (opcionIdNombre.getDescripcion().equalsIgnoreCase(bmcTipoNivel5)) {
                    detalleDireccionEntity.setMztiponivel1(bmcTipoNivel5);
                    detalleDireccionEntity.setMzvalornivel1(bmcValorNivel5);
                }
                if (opcionIdNombre.getDescripcion().equalsIgnoreCase(bmcTipoNivel6)) {
                    detalleDireccionEntity.setMztiponivel1(bmcTipoNivel6);
                    detalleDireccionEntity.setMzvalornivel1(bmcValorNivel6);
                }
            }




            List<OpcionIdNombre> listTipoConjuntoBm = configurationAddressComponent.getBmValues().getTipoConjuntoBm();
            for (OpcionIdNombre opcionIdNombre : listTipoConjuntoBm) {
                if (opcionIdNombre.getDescripcion().equalsIgnoreCase(bmcTipoNivel2)) {
                    detalleDireccionEntity.setMztiponivel1(bmcTipoNivel2);
                    detalleDireccionEntity.setMzvalornivel1(bmcValorNivel2);
                }
                if (opcionIdNombre.getDescripcion().equalsIgnoreCase(bmcTipoNivel3)) {
                    detalleDireccionEntity.setMztiponivel1(bmcTipoNivel3);
                    detalleDireccionEntity.setMzvalornivel1(bmcValorNivel3);
                }
                if (opcionIdNombre.getDescripcion().equalsIgnoreCase(bmcTipoNivel4)) {
                    detalleDireccionEntity.setMztiponivel1(bmcTipoNivel4);
                    detalleDireccionEntity.setMzvalornivel1(bmcValorNivel4);
                }
                if (opcionIdNombre.getDescripcion().equalsIgnoreCase(bmcTipoNivel5)) {
                    detalleDireccionEntity.setMztiponivel1(bmcTipoNivel5);
                    detalleDireccionEntity.setMzvalornivel1(bmcValorNivel5);
                }
                if (opcionIdNombre.getDescripcion().equalsIgnoreCase(bmcTipoNivel6)) {
                    detalleDireccionEntity.setMztiponivel1(bmcTipoNivel6);
                    detalleDireccionEntity.setMzvalornivel1(bmcValorNivel6);
                }
            }

            detalleDireccionEntity.setBarrio(barrio);



        } catch (ApplicationException ex) {
                 String msg = "Se produjo un error al momento de ejecutar el método '"+
                         ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
                LOGGER.error(msg);
            detalleDireccionEntity = null;
        }
        return detalleDireccionEntity;
    }
}