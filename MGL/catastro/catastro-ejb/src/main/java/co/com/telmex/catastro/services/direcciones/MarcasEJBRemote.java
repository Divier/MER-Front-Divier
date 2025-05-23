package co.com.telmex.catastro.services.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.Geografico;
import co.com.telmex.catastro.data.Hhpp;
import java.math.BigDecimal;
import javax.ejb.Remote;

/**
 * Interfase MarcasEJBRemote
 *
 * @author 	Ana María Malambo.
 * @version	1.0
 */
@Remote
public interface MarcasEJBRemote {

    /**
     * 
     * @param codDireccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public BigDecimal queryHhppCodDir(String codDireccion) throws ApplicationException;

    /**
     * 
     * @param codDireccion
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public Hhpp queryHhpp(String codDireccion) throws ApplicationException;

    /**
     * 
     * @param codigo
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public BigDecimal searchMarca(String codigo) throws ApplicationException;

    /**
     * 
     * @param hhpp
     * @param idMarca
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public BigDecimal searchMarcaHhpp(BigDecimal hhpp, BigDecimal idMarca) throws ApplicationException;

    /**
     * 
     * @param codigo
     * @param hhpp
     * @param idMarca
     * @param usuario
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public boolean createMarcaHhp(String codigo, BigDecimal hhpp, BigDecimal idMarca, String usuario) throws ApplicationException;

    /**
     * 
     * @param codigo
     * @param hhpp
     * @param idMarca
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public boolean deleteMarcaHhp(String codigo, BigDecimal hhpp, BigDecimal idMarca) throws ApplicationException;

    /**
     * 
     * @param codigoComunidad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public Geografico searchComunidad(String codigoComunidad) throws ApplicationException;

    /**
     * 
     * @param codigoDepto
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public String searchDepto(BigDecimal codigoDepto) throws ApplicationException;

    /**
     * 
     * @param estadoI
     * @param estadoF
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public BigDecimal searchEstadoHhp(String estadoI, String estadoF) throws ApplicationException;

    /**
     * 
     * @param estado
     * @param hhpp
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public boolean updateEstadoHhp(String estado, BigDecimal hhpp) throws ApplicationException;
}
