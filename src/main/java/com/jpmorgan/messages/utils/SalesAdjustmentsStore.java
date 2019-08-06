package com.jpmorgan.messages.utils;

import com.jpmorgan.messages.model.Sale;
import com.jpmorgan.messages.model.Adjustment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SalesAdjustmentsStore {

    private final List<Sale> sales = new ArrayList<>();
    private final List<Adjustment> adjustments = new ArrayList<>();
    private final SaleAdjuster saleAdjuster;

    public SalesAdjustmentsStore(SaleAdjuster saleAdjuster) {
        this.saleAdjuster = saleAdjuster;
    }

    public void addSale(Sale sale) {
        if (sale == null) {
            return;
        }

        sales.add(sale);
    }

    public void addAdjustment(final Adjustment adjustment) {
        if (adjustment == null) {
            return;
        }
        adjustments.add(adjustment);
        sales.forEach(sale -> saleAdjuster.adjust(sale, adjustment));
    }

    public List<Sale> getSales() {
        return sales;
    }

    public List<Adjustment> getAdjustments() {
        return adjustments;
    }

    public Map<Sale, List<Adjustment>> getAdjustmentsForEachSale() {
        return saleAdjuster.getAdjustmentsForEachSale();
    }
}
