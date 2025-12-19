package org.example;

public interface Typable {
    /**
     * This function checks the equality of two types.
     *
     * @param _other_ represent the other type we want to check to see they match.
     * @return [true] of they match otherwise [false].
     * */
    public abstract boolean __equal__(Typable _other_);

    /**
     * This function will default initialize a type to its default value.
     * @return return the default value of a type.
     * */
    public abstract Valuable __zero_value__();

    /**
     * This function will describe the underling type.
     * @return will return a string representing the description of the type.
     * */
    @Override
    public abstract String toString();

    /**
     * This function will return a string representation of the underlining default value.
     *
     * @return return the actual value stored.
     * */
    public Object __actual_default_value__();
}
