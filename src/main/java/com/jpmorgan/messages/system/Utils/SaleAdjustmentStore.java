package com.jpmorgan.messages.system.Utils;


import com.jpmorgan.messages.system.model.Adjustment;
import com.jpmorgan.messages.system.model.Sale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaleAdjustmentStore {

    private final List<Sale> sales = new ArrayList<>();
    private final List<Adjustment> adjustments = new ArrayList<>();
    private final SaleAdjuster saleAdjuster;

    public SaleAdjustmentStore(SaleAdjuster saleAdjuster){
        this.saleAdjuster = saleAdjuster;

    }

}

