package ru.itmo

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource

class CosTest {

    @Test
    fun `test 0 value`(){
        assertEquals(1.0, CosinusFunction.cos(0.0), CosinusFunction.PRECISION)
    }

    @Test
    fun `test PI value`(){
        assertEquals(-1.0, CosinusFunction.cos(Math.PI), CosinusFunction.PRECISION)
    }

    @Test
    fun `test -PI value`(){
        assertEquals(-1.0, CosinusFunction.cos(-Math.PI), CosinusFunction.PRECISION)
    }

    @Test
    fun `test 0,5PI value`(){
        assertEquals(0.0, CosinusFunction.cos(0.5 * Math.PI), CosinusFunction.PRECISION)
    }

    @Test
    fun `test -0,5PI value`(){
        assertEquals(0.0, CosinusFunction.cos(-0.5 * Math.PI), CosinusFunction.PRECISION)
    }

    @Test
    fun `test 0,33333PI value`(){
        assertEquals(0.5, CosinusFunction.cos(0.33333 * Math.PI), CosinusFunction.PRECISION)
    }

    @Test
    fun `test -0,33333PI value`(){
        assertEquals(0.5, CosinusFunction.cos(-0.33333 * Math.PI), CosinusFunction.PRECISION)
    }
    @Test
    fun `test 0,66666666PI value`(){
        assertEquals(-0.5, CosinusFunction.cos(0.66666666 * Math.PI), CosinusFunction.PRECISION)
    }

    @Test
    fun `test -0,66666666PI value`(){
        assertEquals(-0.5, CosinusFunction.cos(-0.66666666 * Math.PI), CosinusFunction.PRECISION)
    }

    @Test
    fun `test values more than PI and less than -PI return NaN`(){
        assertEquals(Double.NaN, CosinusFunction.cos(-Math.PI - 0.00000001), CosinusFunction.PRECISION)
        assertEquals(Double.NaN, CosinusFunction.cos(Math.PI + 0.00000001), CosinusFunction.PRECISION)
        assertEquals(Double.NaN, CosinusFunction.cos(5 * Math.PI), CosinusFunction.PRECISION)
        assertEquals(Double.NaN, CosinusFunction.cos(-3 * Math.PI), CosinusFunction.PRECISION)
    }

    @ParameterizedTest
    @CsvFileSource(resources = ["/cos.csv"])
    fun `Cos param test`(expected: Double, x: Double){
        assertEquals(expected, CosinusFunction.cos(x), CosinusFunction.PRECISION)
    }

}