package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AtomicValueTest {

    @Test
    void get__atomic_type__() {
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> new AtomicValue<>(AtomicTypes.STRING, AtomicTypes.STRING),
                "test that this does not throw any exception"
        );
        assertEquals("Ne support que les types de base tels que: entiers, réel, chaîne de charactères, charactère et bouléen", thrown.getMessage());
        assertDoesNotThrow(
                () -> new AtomicValue<>("String", AtomicTypes.STRING)
        );
        assertDoesNotThrow(
                () -> new AtomicValue<>(10, AtomicTypes.INTEGER)
        );
        assertDoesNotThrow(
                () -> new AtomicValue<Double>(0.0, AtomicTypes.DOUBLE)
        );
        assertDoesNotThrow(
                () -> new AtomicValue<>('M', AtomicTypes.CHAR)
        );
        assertDoesNotThrow(
                () -> new AtomicValue<>(true, AtomicTypes.BOOLEAN)
        );

        assertEquals(AtomicTypes.INTEGER, new AtomicValue<Integer>(10, AtomicTypes.INTEGER).get__atomic_type__());
        assertEquals(AtomicTypes.DOUBLE, new AtomicValue<>(10, AtomicTypes.DOUBLE).get__atomic_type__());
        assertNotEquals(AtomicTypes.CHAR, new AtomicValue<>(10, AtomicTypes.INTEGER).get__atomic_type__());
        assertNotEquals(AtomicTypes.STRING, new AtomicValue<>(10, AtomicTypes.INTEGER).get__atomic_type__());
        assertEquals(AtomicTypes.BOOLEAN, new AtomicValue<>(10, AtomicTypes.BOOLEAN).get__atomic_type__());
    }

    @Test
    void testToString() {
        AtomicValue<String> _str_desc_ = new AtomicValue<>("bonjour", AtomicTypes.STRING);
        assertEquals("bonjour", _str_desc_.toString());
        assertEquals("bonjour", _str_desc_.get__value__());

        AtomicValue<Integer> _int_desc_ = new AtomicValue<>(10, AtomicTypes.INTEGER);
        assertEquals("10", _int_desc_.toString());
        assertEquals(10, _int_desc_.get__value__());
    }
}