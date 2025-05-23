package co.com.claro.mgl.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase para pruebas unitarias asociadas a ClassUtils
 *
 * @author Gildardo Mora
 * @version 1.0, 2022/09/27
 */
class ClassUtilsTest {

    @Test
    void getCurrentMethodName_withNameOfClass() {
        assertEquals("ClassUtilsTest.getCurrentMethodName_withNameOfClass",
                ClassUtils.getCurrentMethodName(this.getClass()));
    }

    @Test
    void getCurrentMethodName() {
        assertEquals("getCurrentMethodName", ClassUtils.getCurrentMethodName());
    }
}