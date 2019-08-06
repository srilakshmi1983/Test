package ru.spb.smnv.jpmorgan.messages;


import ru.spb.smnv.jpmorgan.messages.data.MessageData;
import ru.spb.smnv.jpmorgan.messages.utils.SaleAdjuster;
import ru.spb.smnv.jpmorgan.messages.utils.SalesAdjustmentsStore;

public class    App {
    public static void main(String[] args) {
        SaleAdjuster saleAdjuster = new SaleAdjuster();
        SalesAdjustmentsStore salesAdjustmentsStore = new SalesAdjustmentsStore(saleAdjuster);
        MessageConsumer messageConsumer = new MessageConsumerImpl(salesAdjustmentsStore);
        MessageData.messages.forEach(messageConsumer::process);
    }
}
