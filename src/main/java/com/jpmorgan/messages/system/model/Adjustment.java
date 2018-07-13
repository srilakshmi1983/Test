package com.jpmorgan.messages.system.model;


public class Adjustment {
private final ProductType productType;
private final Operation operation;
private final Double value;

public Adjustment(Operation operation, ProductType productType, Double value){
    this.operation = operation;
    this.productType = productType;
    this.value = value;
}

    public Operation getOperation() {
        return operation;
    }

    public Double getValue() {
        return value;
    }

    public ProductType getProductType() {
        return productType;
    }
    @Override
    public String toString() {
        return " \n Adjustment {" +
                "operation: " + operation +
                ", value: " + value +
                '}';
        }
}
