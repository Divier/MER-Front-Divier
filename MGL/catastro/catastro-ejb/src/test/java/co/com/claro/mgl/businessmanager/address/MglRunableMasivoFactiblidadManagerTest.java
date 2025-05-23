package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.utils.CmtCoverEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test unitarios para la clase MglRunableMasivoFactiblidadManager
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/05/22
 */
class MglRunableMasivoFactiblidadManagerTest {

    /**
     * Verifica que se retornen los colores indicados según la tecnología procesada.
     * @author Gildardo Mora
     */
    @Test
    void retornaColorTecno() {
        MglRunableMasivoFactiblidadManager masivoFactiblidadManager = new MglRunableMasivoFactiblidadManager();
        assertEquals("", masivoFactiblidadManager.retornaColorTecno(""));
        assertEquals("orange", masivoFactiblidadManager.retornaColorTecno(CmtCoverEnum.NODO_UNO.getCodigo()));
        assertEquals("orange", masivoFactiblidadManager.retornaColorTecno("BI"));
        assertEquals("blue", masivoFactiblidadManager.retornaColorTecno(CmtCoverEnum.NODO_DOS.getCodigo()));
        assertEquals("blue", masivoFactiblidadManager.retornaColorTecno("UNI"));
        assertEquals("red", masivoFactiblidadManager.retornaColorTecno(CmtCoverEnum.NODO_TRES.getCodigo()));
        assertEquals("red", masivoFactiblidadManager.retornaColorTecno("FOG"));
        assertEquals("green", masivoFactiblidadManager.retornaColorTecno(CmtCoverEnum.NODO_DTH.getCodigo()));
        assertEquals("green", masivoFactiblidadManager.retornaColorTecno("DTH"));
        assertEquals("yellow", masivoFactiblidadManager.retornaColorTecno(CmtCoverEnum.NODO_MOVIL.getCodigo()));
        assertEquals("yellow", masivoFactiblidadManager.retornaColorTecno("MOV"));
        assertEquals("brown", masivoFactiblidadManager.retornaColorTecno(CmtCoverEnum.NODO_FTTH.getCodigo()));
        assertEquals("brown", masivoFactiblidadManager.retornaColorTecno("FTTH"));
        assertEquals("pink", masivoFactiblidadManager.retornaColorTecno(CmtCoverEnum.NODO_WIFI.getCodigo()));
        assertEquals("pink", masivoFactiblidadManager.retornaColorTecno("LTE"));
        assertEquals("violet", masivoFactiblidadManager.retornaColorTecno(CmtCoverEnum.NODO_FOU.getCodigo()));
        assertEquals("violet", masivoFactiblidadManager.retornaColorTecno("FOU"));
        assertEquals("turquoise", masivoFactiblidadManager.retornaColorTecno(CmtCoverEnum.NODO_ZONA_GPON_DISENIADO.getCodigo()));
        assertEquals("turquoise", masivoFactiblidadManager.retornaColorTecno("ZONA_GPON_DISENIADO"));
        assertEquals("fuchsia", masivoFactiblidadManager.retornaColorTecno(CmtCoverEnum.NODO_ZONA_MICRO_ONDAS.getCodigo()));
        assertEquals("fuchsia", masivoFactiblidadManager.retornaColorTecno("ZONA_MICRO_ONDAS"));
        assertEquals("gray", masivoFactiblidadManager.retornaColorTecno(CmtCoverEnum.NODO_ZONA_COBERTURA_CAVS.getCodigo()));
        assertEquals("gray", masivoFactiblidadManager.retornaColorTecno("ZONA_COBERTURA_CAVS"));
        assertEquals("black", masivoFactiblidadManager.retornaColorTecno(CmtCoverEnum.NODO_ZONA_COBERTURA_ULTIMA_MILLA.getCodigo()));
        assertEquals("black", masivoFactiblidadManager.retornaColorTecno("ZONA_COBERTURA_ULTIMA_MILLA"));
        assertEquals("purple", masivoFactiblidadManager.retornaColorTecno(CmtCoverEnum.NODO_ZONA_CURRIER.getCodigo()));
        assertEquals("purple", masivoFactiblidadManager.retornaColorTecno("ZONA_CURRIER"));
        assertEquals("yellowgreen", masivoFactiblidadManager.retornaColorTecno(CmtCoverEnum.NODO_ZONA_3G.getCodigo()));
        assertEquals("yellowgreen", masivoFactiblidadManager.retornaColorTecno("ZONA_3G"));
        assertEquals("salmon", masivoFactiblidadManager.retornaColorTecno(CmtCoverEnum.NODO_ZONA_4G.getCodigo()));
        assertEquals("salmon", masivoFactiblidadManager.retornaColorTecno("ZONA_4G"));
        assertEquals("indigo", masivoFactiblidadManager.retornaColorTecno(CmtCoverEnum.NODO_ZONA_5G.getCodigo()));
        assertEquals("indigo", masivoFactiblidadManager.retornaColorTecno("NODO_ZONA_5G"));
    }

}