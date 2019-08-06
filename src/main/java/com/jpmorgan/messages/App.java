package com.jpmorgan.messages;


import com.jpmorgan.messages.data.MessageData;
import com.jpmorgan.messages.utils.SalesAdjustmentsStore;
import com.jpmorgan.messages.utils.SaleAdjuster;

public class    App {
    public static void main(String[] args) {
        SaleAdjuster saleAdjuster = new SaleAdjuster();
        SalesAdjustmentsStore salesAdjustmentsStore = new SalesAdjustmentsStore(saleAdjuster);
        MessageConsumer messageConsumer = new MessageConsumerImpl(salesAdjustmentsStore);
        MessageData.messages.forEach(messageConsumer::process);
    }
}
