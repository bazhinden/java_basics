package com.skillbox.fibonacci;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import org.hibernate.annotations.SQLInsert;

import java.util.Objects;


@Entity
@Table(name = "fibonacci_number", uniqueConstraints = { @UniqueConstraint(columnNames={"index"}) })
@SQLInsert(sql = "INSERT INTO fibonacci_number(index, value) VALUES (?, ?) ON CONFLICT(index) DO UPDATE SET value = EXCLUDED.value RETURNING id" )
public class FibonacciNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "serial")
    @JsonIgnore
    private Integer id;

    private int index;

    private int value;

    public FibonacciNumber() {

    }

    public FibonacciNumber(int index, int value) {
        this();
        this.index = index;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FibonacciNumber that = (FibonacciNumber) o;
        return index == that.index && value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, value);
    }

    @Override
    public String toString() {
        return "FibonacciNumber{" +
                "id=" + id +
                ", index=" + index +
                ", value=" + value +
                '}';
    }
}
