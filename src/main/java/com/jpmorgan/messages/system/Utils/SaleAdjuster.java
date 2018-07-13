package com.jpmorgan.messages.system.Utils;


import com.jpmorgan.messages.system.model.Adjustment;
import com.jpmorgan.messages.system.model.Sale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaleAdjuster {

    private final Map<Sale, List<Adjustment>> map = new HashMap<>();

    void adjust (Sale sale, Adjustment adjustment){
        if(sale == null || adjustment == null) {
            return;
        }
        if(!sale.getProductType().equals(adjustment.getProductType())){
            return;
        }

        List<Adjustment> adjustmentList = map.get(sale);
        if (adjustmentList == null){
            adjustmentList = new ArrayList<>();
        }

        Double saleValue = sale.getEachProductValue();
        Double value = adjustment.getValue();

        switch (adjustment.getOperation()){
            case ADD:
                sale.setEachProductValue(saleValue + value);
                break;
            case MULTIPLY:
                sale.setEachProductValue(saleValue / value);
                break;
            case SUBTRACT:
                sale.setEachProductValue(saleValue - value);
        }
          adjustmentList.add(adjustment);
          map.put(sale, adjustmentList);

    }

    public Map<Sale, List<Adjustment>> getAdjustmentForEachSale() {
        return map;
    }
}
