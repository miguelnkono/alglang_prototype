package org.example;

import static org.example.AtomicTypes.*;

public class AtomicType implements Typable {
    private final String __name_type__;
    private final Valuable __zero_value__;

    /**
     * Construct an atomic type based on the name of the atomic type and the
     * zero value it returns by default.
     *
     * @param __name_type__ which represent the name of the underlining type.
     * @param __zero_value__ which represent the default value the underlining type should return.*/
    public AtomicType(String __name_type__, Valuable __zero_value__) {
        this.__name_type__ = __name_type__;
        this.__zero_value__ = __zero_value__;
    }

    @Override
    public boolean __equal__(Typable _other_) {
        if (this == _other_) return true;
        if (_other_ == null) return false;

        AtomicType that = (AtomicType) _other_;
        return this.__name_type__.equals(that.__name_type__) /*&& this.__zero_value__.equals(that.__zero_value__)*/;
    }

    @Override
    public Valuable __zero_value__() {
        return this.__zero_value__;
    }

    @Override
    public Object __actual_default_value__() {
        return switch (((AtomicValue<?>) this.__zero_value__).get__atomic_type__()) {
            case INTEGER -> 0;
            case DOUBLE -> 0.00;
            case CHAR -> '\u0000';
            case STRING -> "";
            case BOOLEAN -> false;
        };
    }
}
