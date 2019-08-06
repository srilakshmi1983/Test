package com.jpmorgan.messages.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public enum Operation {
    ADD("add"),
    SUBTRACT("subtract"),
    MULTIPLY("multiply");

    private static final Map<String, Operation> lookup = new HashMap<>();

    static {
        for (Operation o : Operation.values()) {
            lookup.put(o.getOperation(), o);
        }
    }

    private String operation;

    Operation(String s) {
        this.operation = s;
    }

    public static Operation get(String abbreviation) {
        return lookup.get(abbreviation);
    }

    public static Collection<String> getValues() {
        return lookup.keySet();
    }

    public String getOperation() {
        return operation;
    }
}
