package com.jpmorgan.messages.system;


import com.jpmorgan.messages.system.Utils.SaleAdjuster;
import com.jpmorgan.messages.system.Utils.SaleAdjustmentStore;
import com.jpmorgan.messages.system.data.MessageData;

public class App {
    public static void main(String[] args) {
        SaleAdjuster saleAdjuster = new SaleAdjuster();
        SaleAdjustmentStore salesAdjustmentsStore = new SaleAdjustmentStore(saleAdjuster);
        MessageConsumer messageConsumer = new MessageConsumerImpl(salesAdjustmentsStore);
        MessageData.messages.forEach(messageConsumer::process);
    }
}
