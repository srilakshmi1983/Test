package com.jpmorgan.messages.utils;

import com.jpmorgan.messages.MessageConsumerImpl;
import org.junit.Before;
import org.junit.Test;
import com.jpmorgan.messages.MessageConsumer;

import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UtilsTest {

    private SalesAdjustmentsStore salesAdjustmentsStore;
    private MessageConsumer messageConsumer;

    @Before
    public void init() {
        SaleAdjuster saleAdjuster = new SaleAdjuster();
        salesAdjustmentsStore = new SalesAdjustmentsStore(saleAdjuster);
        messageConsumer = new MessageConsumerImpl(salesAdjustmentsStore);
    }

    @Test
    public void shouldReturnTotalValueForApplesAndPineapples() {
        messageConsumer.process("apple at 10p");
        messageConsumer.process("apple at 50p");
        messageConsumer.process("apple at 2p");

        messageConsumer.process("20 sales of apples at 10p each");

        messageConsumer.process("pineapple at 10p");
        messageConsumer.process("pineapple at 50p");

        messageConsumer.process("20 sales of pineapples at 10p each");


        Map<String, Utils.ProductTotalResult> resultMap = Utils.createResultByEachProductBySales(salesAdjustmentsStore.getSales());

        assertTrue(resultMap.values().size() == 2);

        Utils.ProductTotalResult result = resultMap.get("apple");
        assertNotNull(result);

        assertTrue(result.numberOfOccurrences == 23);
        assertTrue(result.totalValue == 262d);
    }
}