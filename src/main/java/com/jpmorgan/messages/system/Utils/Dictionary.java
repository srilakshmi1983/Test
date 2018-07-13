package com.jpmorgan.messages.system.Utils;


import com.jpmorgan.messages.system.model.ProductType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Dictionary {

    private static final Map<String, ProductType> dict = Collections.unmodifiableMap(
            new HashMap<String, ProductType>() {
                {
                    put("apples", ProductType.APPLE);
                    put("pineapples", ProductType.PINEAPPLE);
                    put("oranges", ProductType.ORANGE);
                    put("bananas", ProductType.BANANA);

                }
            });

    private Dictionary() {
    }

    public static ProductType getProductTypeByPluralName(String s) {
        return dict.get(s);
    }
}
