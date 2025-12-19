package org.example;

public class AtomicValue<T> implements Valuable {
    // which represent the exact underlining value.
    private final T __value__;
    // the type that the value is associated.
    private final AtomicTypes __atomic_type__;

    /**
     * Construct an atomic value according to its underlining data type.
     * @param __value__  which represent the value to be store in the type area.
     * @param __atomic_type__ which represent the underlining data type.
     * */
    public AtomicValue (T __value__, AtomicTypes __atomic_type__) {
        if (__value__ instanceof Integer || __value__ instanceof Double
        || __value__ instanceof Character || __value__ instanceof String
        || __value__ instanceof Boolean)
            this.__value__ = __value__;
        else
            throw new IllegalArgumentException("Ne support que les types de base tels que: entiers, réel, chaîne de charactères, charactère et bouléen");
        this.__atomic_type__ = __atomic_type__;
    }

    /**
     * This helper function will return the underlining data type associated with the value.
     * @return AtomicTypes which represent the underlining atomic type.
     * */
    public AtomicTypes get__atomic_type__() { return this.__atomic_type__; }

    /**
     * This function returns the atomic value.
     * @return T which represent the value.
     * */
    public T get__value__() { return this.__value__; }

    @Override
    public String toString() {
        return String.format("%s", this.__value__);
    }
}
