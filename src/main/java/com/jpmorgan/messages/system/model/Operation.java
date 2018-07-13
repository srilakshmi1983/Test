package com.jpmorgan.messages.system.model;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public enum Operation {
    ADD("add"),
    SUBTRACT("subtract"),
    MULTIPLY("multiply");

    private static final Map<String, Operation> Lookup = new HashMap<>();

    static {
        for (Operation o : Operation.values()){
            Lookup.put(o.getOperation(), o);
        }

        }

        private String operation;
    Operation (String s){
        this.operation = s;
    }
    public static Operation get(String abbreviation) {
        return Lookup.get(abbreviation);
    }

    public static Collection<String> getValues() {
        return Lookup.keySet();
    }

    public String getOperation() {
        return operation;
    }

}
