package com.jpmorgan.messages.system.Utils;


import com.jpmorgan.messages.system.model.Sale;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    private Utils() {
    }

    public static Map<String, ProductTotalResult> createResultByEachProductBySales(final List<Sale> saleList) {
        Map<String, ProductTotalResult> resultMap = new HashMap<>();
        saleList.forEach(sale -> {
            ProductTotalResult totalResult = resultMap.get(sale.getProductType().getCode());
            if (totalResult == null) {
                totalResult = new ProductTotalResult(sale.getNumberOfOccurrences(), sale.getProductValue());
                resultMap.put(sale.getProductType().getCode(), totalResult);
            } else {
                totalResult.numberOfOccurrences = totalResult.numberOfOccurrences + sale.getNumberOfOccurrences();
                totalResult.totalValue = totalResult.totalValue + sale.getProductValue();
            }

        });
        return resultMap;
    }


    static class ProductTotalResult {
        int numberOfOccurrences;
        Double totalValue;

        ProductTotalResult(int numberOfOccurrences, Double totalValue) {
            this.numberOfOccurrences = numberOfOccurrences;
            this.totalValue = totalValue;
        }

        @Override
        public String toString() {
            return "  number of occurrences:" + numberOfOccurrences +
                    ", total value: " + totalValue;
        }
    }
}
