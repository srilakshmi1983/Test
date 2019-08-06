package com.jpmorgan.messages;

import com.jpmorgan.messages.model.Adjustment;
import com.jpmorgan.messages.model.Operation;
import com.jpmorgan.messages.model.ProductType;
import com.jpmorgan.messages.model.Sale;
import com.jpmorgan.messages.utils.Dictionary;
import com.jpmorgan.messages.utils.SalesAdjustmentsStore;
import com.jpmorgan.messages.utils.Utils;

public class MessageConsumerImpl implements MessageConsumer {

    private final String regexForDouble = "[^0-9]";
    private final SalesAdjustmentsStore salesAdjustmentsStore;
    private int counter;


    public MessageConsumerImpl(SalesAdjustmentsStore salesAdjustmentsStore) {
        this.salesAdjustmentsStore = salesAdjustmentsStore;
    }

    @Override
    public void process(String message) {

        if (counter >= 50) {
            return;
        }

        if (message == null) {
            return;
        }

        String[] arr = message.toLowerCase().split(" ");

        if (arr.length == 0) {
            return;
        }

        if (Operation.getValues().contains(arr[0])) {
            processAdjustment(arr);
        } else if (arr.length > 2 && arr[1].equals("sales")) {
            processMultipleSales(arr);
        } else if (arr.length == 3 && ProductType.getCodes().contains(arr[0])) {
            processSingleSale(arr);
        }

        counter++;

        if (counter % 10 == 0) {
            System.out.println(Utils.createResultByEachProductBySales(salesAdjustmentsStore.getSales()));
        }

        if (counter % 50 == 0) {
            System.out.println("================================= application will not accept messages anymore =====================================");
            System.out.println(salesAdjustmentsStore.getAdjustmentsForEachSale());
        }
    }

    private void processSingleSale(String[] arr) {
        if (arr == null || arr.length != 3) {
            return;
        }

        ProductType type = ProductType.get(arr[0]);
        Double value = Double.valueOf(arr[2].replaceAll(regexForDouble, ""));

        salesAdjustmentsStore.addSale(new Sale(type, value));
    }

    private void processMultipleSales(String[] arr) {
        if (arr == null || arr.length != 7) {
            return;
        }

        int occurrences = Integer.parseInt(arr[0]);
        Double value = Double.valueOf(arr[5].replaceAll(regexForDouble, ""));
        ProductType type = Dictionary.getProductTypeByPluralName(arr[3]);

        salesAdjustmentsStore.addSale(new Sale(type, value, occurrences));
    }

    private void processAdjustment(String[] arr) {
        if (arr == null || arr.length != 3) {
            return;
        }

        Operation operation = Operation.get(arr[0]);
        Double value = Double.valueOf(arr[1].replaceAll(regexForDouble, ""));
        ProductType type = Dictionary.getProductTypeByPluralName(arr[2]);

        salesAdjustmentsStore.addAdjustment(new Adjustment(operation, type, value));
    }


}
