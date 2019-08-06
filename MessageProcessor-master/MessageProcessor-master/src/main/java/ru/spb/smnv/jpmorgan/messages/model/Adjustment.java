package ru.spb.smnv.jpmorgan.messages.model;

public final class Adjustment {
    private final ProductType productType;
    private final Operation operation;
    private final Double value;

    public Adjustment(Operation operation, ProductType productType, Double value) {
        this.operation = operation;
        this.productType = productType;
        this.value = value;
    }

    public Operation getOperation() {
        return operation;
    }

    public ProductType getProductType() {
        return productType;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return " \n Adjustment {" +
                "operation: " + operation +
                ", value: " + value +
                '}';
    }
}
