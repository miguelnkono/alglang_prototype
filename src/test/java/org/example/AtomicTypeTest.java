package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AtomicTypeTest {

    @Test
    void __equal__() {
        AtomicValue<Integer> _zero_value_integer_ = new AtomicValue<>(0, AtomicTypes.INTEGER);
        AtomicType _integer_type_ = new AtomicType("entier", _zero_value_integer_);

        AtomicValue<Integer> _zero_value_integer_2_ = new AtomicValue<>(0, AtomicTypes.INTEGER);
        AtomicType _integer_type_2_ = new AtomicType("entier", _zero_value_integer_2_);

        assertTrue(_integer_type_.__equal__(_integer_type_2_));

        AtomicValue<Boolean> _zero_value_boolean_ = new AtomicValue<>(true, AtomicTypes.BOOLEAN);
        AtomicType _boolean_type_ = new AtomicType("booléen", _zero_value_boolean_);

        assertFalse(_integer_type_.__equal__(_boolean_type_));
    }

    @Test
    void __zero_value__() {
        AtomicValue<Integer> _zero_value_integer_ = new AtomicValue<>(0, AtomicTypes.INTEGER);
        AtomicType _integer_type_ = new AtomicType("entier", _zero_value_integer_);

        AtomicValue<Boolean> _zero_value_boolean_ = new AtomicValue<>(true, AtomicTypes.BOOLEAN);
        AtomicType _boolean_type_ = new AtomicType("booléen", _zero_value_boolean_);

        assertEquals(0, _integer_type_.__actual_default_value__());
        assertEquals(false, _boolean_type_.__actual_default_value__());
    }
}