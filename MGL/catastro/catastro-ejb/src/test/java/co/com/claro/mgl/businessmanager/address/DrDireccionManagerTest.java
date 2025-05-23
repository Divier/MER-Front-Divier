package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.businessmanager.cm.CmtComunidadRrManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.rest.dtos.CmtRequestConstruccionDireccionDto;
import co.com.claro.mgl.rest.dtos.CmtResponseConstruccionDireccionDto;
import co.com.claro.mgl.ws.cm.request.RequestConstruccionDireccion;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;

/**
 * Clase para pruebas unitarias asociadas a DrDireccionManager
 *
 * @author Gildardo Mora
 * @version 1.0, 2022/09/27
 */
@Disabled("Se deshabilita ya que solo debe usar cuando se tenga desplegada la aplicación")
@ExtendWith(MockitoExtension.class)
class DrDireccionManagerTest {

    @InjectMocks
    DrDireccionManager direccionManager = new DrDireccionManager();
    @Mock
    CmtComunidadRrManager cmtComunidadRrManager;

    CmtRequestConstruccionDireccionDto request = new CmtRequestConstruccionDireccionDto();

    /* ------------------------------------------------- */

    /**
     * Verifica que cuando la petición saa nula,
     * el tipo de respuesta sea "E" que indica error y que el mensaje
     * de error incluya "construir peticion"
     */
    @Test
    void construirDireccionSolicitudHhpp_requestIsNull() {
        CmtResponseConstruccionDireccionDto responseDireccion = direccionManager.construirDireccionSolicitudHhpp(null);
        assertEquals("E", responseDireccion.getResponseMesaje().getTipoRespuesta());
        assertTrue(responseDireccion.getResponseMesaje().getMensaje()
                .equalsIgnoreCase("Es necesario construir una peticion para consumir el servicio"));
    }

    /**
     * Verifica que cuando la información de dirección sea nula,
     * el tipo de respuesta sea "E" que indica error y que el mensaje
     * de error indique que es "DrDireccion" la causa del problema
     */
    @Test
    void construirDireccionSolicitudHhpp_drDireccionIsNull() {
        request.setDrDireccion(null);
        CmtResponseConstruccionDireccionDto responseDireccion = direccionManager.construirDireccionSolicitudHhpp(request);
        assertEquals("E", responseDireccion.getResponseMesaje().getTipoRespuesta());
        assertTrue(responseDireccion.getResponseMesaje().getMensaje()
                .equalsIgnoreCase("Es necesario construir el DrDireccion de la petición para consumir el servicio"));
    }

    /**
     * Verifica que cuando el ID de dirección sea nulo, el tipo de respuesta sea "E"
     * que indica error y que el mensaje de error indique que
     * es "IdTipoDireccion" la causa del problema
     */
    @Test
    void construirDireccionSolicitudHhpp_idTipoDireccionIsNull() {
        request.setDrDireccion(new DrDireccion());
        CmtResponseConstruccionDireccionDto responseDireccion = direccionManager.construirDireccionSolicitudHhpp(request);
        assertEquals("E", responseDireccion.getResponseMesaje().getTipoRespuesta());
        assertTrue(responseDireccion.getResponseMesaje().getMensaje()
                .equalsIgnoreCase("Debe ingresar el IdTipoDireccion por favor: Los valores permitidos son CK, BM o IT"));
    }

    /**
     * Verifica que cuando el ID de dirección sea un valor diferente
     * a los permitidos (CK, BM, o IT), el tipo de respuesta sea "E"
     * que indica error y que el mensaje de error indique que
     * esa es la causa del problema.
     */
    @Test
    void construirDireccionSolicitudHhpp_idTipoDireccionIsNotAllowed() {
        DrDireccion drDireccion = new DrDireccion();
        drDireccion.setIdTipoDireccion("Z");
        request.setDrDireccion(drDireccion);
        CmtResponseConstruccionDireccionDto responseDireccion = direccionManager.construirDireccionSolicitudHhpp(request);
        assertEquals("E", responseDireccion.getResponseMesaje().getTipoRespuesta());
        assertTrue(responseDireccion.getResponseMesaje().getMensaje()
                .equalsIgnoreCase("Los valores permitidos para tipo de direccion son CK, BM o IT"));
    }

    /**
     * Verifica que cuando el tipo de dirección es "CK" (calle-carrera)
     * y el valor del campo direccionStr es nulo, el tipo de respuesta sea "E"
     * que indica error y que el mensaje de error indique que
     * esa es la causa del problema.
     */
    @Test
    void construirDireccionSolicitudHhpp_idTipoDireccionIsCkAndDireccionStrIsNull() {
        DrDireccion drDireccion = new DrDireccion();
        drDireccion.setIdTipoDireccion("CK");
        request.setDrDireccion(drDireccion);
        request.setDireccionStr(null);
        CmtResponseConstruccionDireccionDto responseDireccion = direccionManager.construirDireccionSolicitudHhpp(request);
        assertEquals("E", responseDireccion.getResponseMesaje().getTipoRespuesta());
        assertTrue(responseDireccion.getResponseMesaje().getMensaje()
                .equalsIgnoreCase("Debe ingresar el DireccionStr por ser una direccion Calle-Carrera"));
    }

    /**
     * Verifica que cuando comunidad es nula, el tipo de respuesta sea "E"
     * que indica error y que el mensaje de error indique que
     * esa es la causa del problema.
     */
    @Test
    void construirDireccionSolicitudHhpp_comunidadIsNull() {
        DrDireccion drDireccion = new DrDireccion();
        drDireccion.setIdTipoDireccion("CK");
        request.setDrDireccion(drDireccion);
        request.setDireccionStr("str");
        request.setComunidad(null);
        CmtResponseConstruccionDireccionDto responseDireccion = direccionManager.construirDireccionSolicitudHhpp(request);
        assertEquals("E", responseDireccion.getResponseMesaje().getTipoRespuesta());
        assertTrue(responseDireccion.getResponseMesaje().getMensaje()
                .equalsIgnoreCase("Debe ingresar el codigo dane en el campo comunidad"));
    }

    /**
     * Verifica que cuando tipo adición es nula, el tipo de respuesta sea "E"
     * que indica error y que el mensaje de error indique que
     * esa es la causa del problema.
     */
    @Test
    void construirDireccionSolicitudHhpp_tipoAdicionIsNull() {
        DrDireccion drDireccion = new DrDireccion();
        drDireccion.setIdTipoDireccion("CK");
        request.setDrDireccion(drDireccion);
        request.setDireccionStr("str");
        request.setComunidad("comunidad");
        request.setTipoAdicion(null);
        CmtResponseConstruccionDireccionDto responseDireccion = direccionManager.construirDireccionSolicitudHhpp(request);
        assertEquals("E", responseDireccion.getResponseMesaje().getTipoRespuesta());
        String msg = "Debe ingresar el tipo de adición: N para calle "
                + "carrera dirección hasta placa, C para complementos, P para dirección principal "
                + "barrio-manzana e intraducible, A para apartamento";
        assertTrue(responseDireccion.getResponseMesaje().getMensaje()
                .equalsIgnoreCase(msg));
    }

    /**
     * Verifica que cuando tipo adición es un valor no permitido, el tipo de respuesta sea "E"
     * que indica error y que el mensaje de error indique que esa es la causa del problema.
     */
    @Test
    void construirDireccionSolicitudHhpp_tipoAdicionIsNotAllowed() {
        DrDireccion drDireccion = new DrDireccion();
        drDireccion.setIdTipoDireccion("CK");
        request.setDrDireccion(drDireccion);
        request.setDireccionStr("str");
        request.setComunidad("comunidad");
        request.setTipoAdicion("Z");
        CmtResponseConstruccionDireccionDto responseDireccion = direccionManager.construirDireccionSolicitudHhpp(request);
        assertEquals("E", responseDireccion.getResponseMesaje().getTipoRespuesta());
        String msg = "Los valores permitidos para TipoAdición son: N para calle "
                + "carrera dirección hasta placa, C para complementos, P para dirección principal "
                + "barrio-manzana e intraducible, A para apartamento";
        assertTrue(responseDireccion.getResponseMesaje().getMensaje()
                .equalsIgnoreCase(msg));
    }

    /**
     * Verifica que cuando tipo de dirección es diferente a CK y el tipo de nivel es nulo,
     * que el tipo de respuesta sea "E" que indica error y que el mensaje de error indique que
     * esa es la causa del problema.
     */
    @Test
    void construirDireccionSolicitudHhpp_tipoDirecionIsCkAndTipoNivelIsNull() {
        DrDireccion drDireccion = new DrDireccion();
        drDireccion.setIdTipoDireccion("BM");
        request.setDrDireccion(drDireccion);
        request.setDireccionStr("str");
        request.setComunidad("comunidad");
        request.setTipoAdicion("C");
        request.setTipoNivel(null);
        CmtResponseConstruccionDireccionDto responseDireccion = direccionManager.construirDireccionSolicitudHhpp(request);
        assertEquals("E", responseDireccion.getResponseMesaje().getTipoRespuesta());
        String msg = "Debe ingresar el tipoNivel debido a que no es una direccion calle-carrera";
        assertTrue(responseDireccion.getResponseMesaje().getMensaje()
                .equalsIgnoreCase(msg));
    }

    /**
     * Verifica que cuando tipo de dirección es diferente a CK y el tipo de adición es distinto de "A",
     * el tipo de respuesta sea "E" que indica error y que el mensaje de error indique que
     * esa es la causa del problema.
     */
    @Test
    void construirDireccionSolicitudHhpp_tipoDireccionDifferentFromCkAndtipoAdicionIsDifferentFromA() {
        DrDireccion drDireccion = new DrDireccion();
        drDireccion.setIdTipoDireccion("IT");
        request.setDrDireccion(drDireccion);
        request.setDireccionStr("Carrera 13 30 SUR 61");
        request.setComunidad("11001000");
        request.setTipoAdicion("P");
        request.setTipoNivel("nivel");
        CmtResponseConstruccionDireccionDto responseDireccion = direccionManager.construirDireccionSolicitudHhpp(request);
        assertEquals("E", responseDireccion.getResponseMesaje().getTipoRespuesta());
        String msg = "Debe ingresar el valorNivel debido a que no es una direccion calle-carrera";
        assertTrue(responseDireccion.getResponseMesaje().getMensaje()
                .equalsIgnoreCase(msg));
    }

    /**
     * Verifica que cuando la comunidad no es numérico y la repuesta de
     * búsqueda de comunidad retorna null, el tipo de respuesta sea "E" que
     * indica error y que el mensaje de error indique que
     * esa es la causa del problema.
     */
    @Test
    void construirDireccionSolicitudHhpp_comunidadIsLetterAndFindComunidadByComunidadIsNull() {
        DrDireccion drDireccion = new DrDireccion();
        drDireccion.setIdTipoDireccion("IT");
        request.setDrDireccion(drDireccion);
        request.setDireccionStr("Carrera 13 30 SUR 61");
        request.setComunidad("BOG");
        request.setTipoAdicion("P");
        request.setTipoNivel("nivel");
        request.setValorNivel("valorNivel");
        lenient().when(cmtComunidadRrManager.findComunidadByComunidad(Mockito.any()))
                .thenReturn(null);
        CmtResponseConstruccionDireccionDto responseDireccion = direccionManager.construirDireccionSolicitudHhpp(request);
        assertEquals("E", responseDireccion.getResponseMesaje().getTipoRespuesta());
        String msg = "Ocurrio un error construyendo la direccion";
        assertTrue(responseDireccion.getResponseMesaje().getMensaje()
                .contains(msg));
    }

    /* --------------------------------------------------------- */

    /**
     * Verifica que cuando la comunidad no es numérico y la repuesta de consulta
     * comunidad es nula se lance una excepción que contenga el mensaje asociado.
     */
    @Test
    void construirDireccionCkSolicitud_comunidadIsLetterAndComunidadIsNull() {
        DrDireccion drDireccion = new DrDireccion();
        drDireccion.setIdTipoDireccion("IT");
        RequestConstruccionDireccion request = new RequestConstruccionDireccion();
        request.setDrDireccion(drDireccion);
        request.setDireccionStr("Carrera 13 30 SUR 61");
        request.setComunidad("BOG");
        request.setTipoAdicion("P");
        request.setTipoNivel("nivel");
        request.setValorNivel("nivel");
        lenient().when(cmtComunidadRrManager.findComunidadByComunidad(Mockito.any()))
                .thenReturn(null);

        try {
            direccionManager.construirDireccionCkSolicitud(request);
        } catch (ApplicationException e) {
            String msg = "No fue posible construir "
                    + "la direccion Calle-Carrera:  Comunidad RR no tiene "
                    + "configurado la ciudad en MGL para obtener el codigo dane";
            assertTrue(e.getMessage().equalsIgnoreCase(msg));
        }
    }
}