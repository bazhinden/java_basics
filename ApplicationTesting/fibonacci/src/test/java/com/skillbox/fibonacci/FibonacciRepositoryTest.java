package com.skillbox.fibonacci;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FibonacciRepositoryTest extends PostgresTestContainerInitializer {

    @Autowired
    private FibonacciRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Сохранение нового числа Фибоначчи в БД")
    void testSaveFibonacciNumber() {
        FibonacciNumber number = new FibonacciNumber(1, 1);
        repository.save(number);
        entityManager.flush();
        entityManager.detach(number);

        List<FibonacciNumber> actual = jdbcTemplate.query(
                "SELECT * FROM fibonacci_number WHERE index = ?",
                (rs, rowNum) -> new FibonacciNumber(rs.getInt("index"), rs.getInt("value")),
                number.getIndex()
        );

        assertEquals(1, actual.size());
        assertEquals(number.getIndex(), actual.get(0).getIndex());
        assertEquals(number.getValue(), actual.get(0).getValue());
    }

    @Test
    @DisplayName("Поиск числа Фибоначчи по порядковому номеру")
    void testFindByIndex() {
        FibonacciNumber number = new FibonacciNumber(2, 1);
        repository.save(number);
        entityManager.flush();
        entityManager.detach(number);

        Optional<FibonacciNumber> foundNumber = repository.findByIndex(2);
        assertTrue(foundNumber.isPresent());
        assertEquals(number, foundNumber.get());
    }

    @Test
    @DisplayName("Повторная вставка того же числа Фибоначчи не вызывает исключений и дубликатов")
    void testDuplicateInsertion() {
        FibonacciNumber number = new FibonacciNumber(3, 2);
        repository.save(number);
        entityManager.flush();
        entityManager.detach(number);

        FibonacciNumber duplicateNumber = new FibonacciNumber(3, 2);
        repository.save(duplicateNumber);
        entityManager.flush();
        entityManager.detach(duplicateNumber);

        List<FibonacciNumber> actual = jdbcTemplate.query(
                "SELECT * FROM fibonacci_number WHERE index = ?",
                (rs, rowNum) -> new FibonacciNumber(rs.getInt("index"), rs.getInt("value")),
                number.getIndex()
        );

        assertEquals(1, actual.size());
        assertEquals(number.getIndex(), actual.get(0).getIndex());
        assertEquals(number.getValue(), actual.get(0).getValue());
    }
}
