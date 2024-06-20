package com.skillbox.fibonacci;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class FibonacciCalculatorTest {

    private final FibonacciCalculator fibonacciCalculator;

    public FibonacciCalculatorTest() {
        fibonacciCalculator = new FibonacciCalculator();
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1",
            "2, 1",
            "3, 2",
            "4, 3",
            "5, 5",
            "6, 8",
            "7, 13",
            "8, 21",
            "9, 34",
            "10, 55"
    })
    @DisplayName("Проверка чисел Фибоначчи для корректных индексов")
    void testFibonacciNumbers(int index, int expected) {
        assertEquals(Integer.valueOf(expected), fibonacciCalculator.getFibonacciNumber(index));
    }

    @Test
    @DisplayName("Проверка исключения для индекса меньше 1")
    void testExceptionForIndexLessThanOne() {
        assertThrows(IllegalArgumentException.class, () -> fibonacciCalculator.getFibonacciNumber(0));
        assertThrows(IllegalArgumentException.class, () -> fibonacciCalculator.getFibonacciNumber(-1));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    @DisplayName("Проверка, что для порядковых номеров 1 и 2 возвращается значение 1")
    void testFibonacciNumbersForFirstTwoIndices(int index) {
        assertEquals(Integer.valueOf(1), fibonacciCalculator.getFibonacciNumber(index));
    }
}
