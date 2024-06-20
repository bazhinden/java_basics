package com.skillbox.fibonacci;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FibonacciServiceTest {
    @InjectMocks
    private FibonacciService service;

    @Mock
    private FibonacciCalculator calculator;
    @Mock
    private FibonacciRepository repository;

    @BeforeEach
    void setUp() {
        service = new FibonacciService(repository, calculator);
    }

    @Test
    @DisplayName("Сервис должен вернуть число Фибоначчи из БД, если оно там есть")
    void testFibonacciNumberFromDatabase() {
        int index = 5;
        FibonacciNumber fibonacciNumber = new FibonacciNumber(index, 5);

        when(repository.findByIndex(index)).thenReturn(Optional.of(fibonacciNumber));

        FibonacciNumber result = service.fibonacciNumber(index);

        assertEquals(fibonacciNumber, result);
        verify(repository).findByIndex(index);
        verify(calculator, never()).getFibonacciNumber(index);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Сервис должен рассчитывать и сохранять число Фибоначчи, если его нет в БД")
    void testFibonacciNumberCalculatedAndSaved() {
        int index = 6;
        int fibonacciValue = 8;

        when(repository.findByIndex(index)).thenReturn(Optional.empty());
        when(calculator.getFibonacciNumber(index)).thenReturn(fibonacciValue);

        FibonacciNumber result = service.fibonacciNumber(index);

        assertEquals(new FibonacciNumber(index, fibonacciValue), result);
        verify(repository).findByIndex(index);
        verify(calculator).getFibonacciNumber(index);
        verify(repository).save(new FibonacciNumber(index, fibonacciValue));
    }

    @Test
    @DisplayName("Сервис должен выбрасывать исключение для индекса меньше 1")
    void testExceptionForIndexLessThanOne() {
        int invalidIndex = -1;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.fibonacciNumber(invalidIndex);
        });

        assertEquals("Index should be greater or equal to 1", exception.getMessage());
        verify(repository, never()).findByIndex(anyInt());
        verify(calculator, never()).getFibonacciNumber(anyInt());
        verify(repository, never()).save(any());
    }
}
