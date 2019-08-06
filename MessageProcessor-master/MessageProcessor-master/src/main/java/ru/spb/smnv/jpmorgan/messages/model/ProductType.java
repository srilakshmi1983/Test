package ru.spb.smnv.jpmorgan.messages.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public enum ProductType {
    APPLE("apple"),
    ORANGE("orange"),
    BANANA("banana"),
    PINEAPPLE("pineapple");

    private static final Map<String, ProductType> lookup = new HashMap<>();

    static {
        for (ProductType p : ProductType.values()) {
            lookup.put(p.getCode(), p);
        }
    }

    private String code;

    ProductType(String code) {
        this.code = code;
    }

    public static ProductType get(String abbreviation) {
        return lookup.get(abbreviation);
    }

    public static Collection<String> getCodes() {
        return lookup.keySet();
    }

    public String getCode() {
        return code;
    }
}
