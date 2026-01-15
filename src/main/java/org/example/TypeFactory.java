package org.example;

import java.util.HashMap;
import java.util.Map;

public class TypeFactory
{

    private static final Map<AtomicTypes, AtomicType> __types__ = new HashMap<>();

    static
    {
        __types__.put(AtomicTypes.INTEGER, new AtomicType("entier", new AtomicValue<Integer>(0, AtomicTypes.INTEGER)));
        __types__.put(AtomicTypes.DOUBLE, new AtomicType("reel", new AtomicValue<Double>(0.0, AtomicTypes.DOUBLE)));
        __types__.put(AtomicTypes.STRING, new AtomicType("chaine", new AtomicValue<String>("", AtomicTypes.STRING)));
        __types__.put(AtomicTypes.CHAR, new AtomicType("char", new AtomicValue<Character>('\u0000', AtomicTypes.CHAR)));
        __types__.put(AtomicTypes.BOOLEAN, new AtomicType("booleen", new AtomicValue<Boolean>(false, AtomicTypes.BOOLEAN)));
        __types__.put(AtomicTypes.VOID, new AtomicType("nil", null));
    }

    public static final AtomicType INTEGER = __types__.get(AtomicTypes.INTEGER);
    public static final AtomicType FLOATING = __types__.get(AtomicTypes.DOUBLE);
    public static final AtomicType STRING = __types__.get(AtomicTypes.STRING);
    public static final AtomicType CHAR = __types__.get(AtomicTypes.CHAR);
    public static final AtomicType BOOLEAN = __types__.get(AtomicTypes.BOOLEAN);
    public static final AtomicType VOID = __types__.get(AtomicTypes.VOID);

    /**
     * This helper function returns the atomic type for an atomic value.
     * @param atomicType which represent the atomic type that we want to get its type.
     * */
    public static Typable getAtomicType(AtomicTypes atomicType)
    {
        Typable type = __types__.get(atomicType);
        if (type == null)
        {
            throw new IllegalArgumentException("Type basique non reconnu: " + atomicType);
        }
        return type;
    }
}
