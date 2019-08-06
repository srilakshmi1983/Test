package ru.spb.smnv.jpmorgan.messages.model;

public class Sale {
    private final ProductType productType;
    private Double productValue;
    private int numberOfOccurrences;

    public Sale(ProductType productType, Double productValue) {
        this(productType, productValue, 1);
    }

    public Sale(ProductType productType, Double productValue, int numberOfOccurrences) {
        this.productType = productType;
        this.productValue = productValue;
        this.numberOfOccurrences = numberOfOccurrences;
    }

    public Double getEachProductValue() {
        return productValue;
    }

    public void setEachProductValue(Double productValue) {
        this.productValue = productValue;
    }

    public Double getProductValue() {
        return numberOfOccurrences * productValue;
    }

    public ProductType getProductType() {
        return productType;
    }

    public int getNumberOfOccurrences() {
        return numberOfOccurrences;
    }

    @Override
    public String toString() {
        return "\n Sale {" +
                "product type: " + getProductType().getCode() +
                ", each product value = " + getEachProductValue() +
                ", number of occurrences = " + numberOfOccurrences +
                ", total product value = " + getProductValue() +
                '}';
    }
}
