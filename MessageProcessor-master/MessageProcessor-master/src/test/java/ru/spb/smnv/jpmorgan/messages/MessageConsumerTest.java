package ru.spb.smnv.jpmorgan.messages;

import org.junit.Before;
import org.junit.Test;
import ru.spb.smnv.jpmorgan.messages.model.Adjustment;
import ru.spb.smnv.jpmorgan.messages.model.Operation;
import ru.spb.smnv.jpmorgan.messages.model.ProductType;
import ru.spb.smnv.jpmorgan.messages.model.Sale;
import ru.spb.smnv.jpmorgan.messages.utils.SaleAdjuster;
import ru.spb.smnv.jpmorgan.messages.utils.SalesAdjustmentsStore;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class MessageConsumerTest {
    private SaleAdjuster saleAdjuster;
    private SalesAdjustmentsStore salesAdjustmentsStore;
    private MessageConsumer messageConsumer;

    @Before
    public void init() {
        saleAdjuster = new SaleAdjuster();
        salesAdjustmentsStore = new SalesAdjustmentsStore(saleAdjuster);
        messageConsumer = new MessageConsumerImpl(salesAdjustmentsStore);
    }

    @Test
    public void shouldCreateAdjustment() {
        messageConsumer.process("Add 20p apples");
        List<Adjustment> adjustments = salesAdjustmentsStore.getAdjustments();

        assertTrue(adjustments.size() == 1);
        assertTrue(adjustments.get(0).getOperation().equals(Operation.ADD));
        assertTrue(adjustments.get(0).getValue().equals(20d));
        assertTrue(adjustments.get(0).getProductType().equals(ProductType.APPLE));
    }

    @Test
    public void shouldCreate3Adjustments() {
        messageConsumer.process("Add 20p apples");
        messageConsumer.process("Multiply 2p pineapples");
        messageConsumer.process("Subtract 5p oranges");

        List<Adjustment> adjustments = salesAdjustmentsStore.getAdjustments();
        assertTrue(adjustments.size() == 3);
        assertTrue(adjustments.get(1).getOperation().equals(Operation.MULTIPLY));
        assertTrue(adjustments.get(1).getProductType().equals(ProductType.PINEAPPLE));

        assertTrue(adjustments.get(2).getOperation().equals(Operation.SUBTRACT));
        assertTrue(adjustments.get(2).getProductType().equals(ProductType.ORANGE));
    }

    @Test
    public void shouldCreateOneAppleSaleAndCreateOneAdjustmentForApples() {
        messageConsumer.process("Add 20p apples");
        messageConsumer.process("20 sales of apples at 10p each");

        List<Sale> sales = salesAdjustmentsStore.getSales();
        List<Adjustment> adjustments = salesAdjustmentsStore.getAdjustments();

        assertTrue(sales.size() == 1);
        assertTrue(adjustments.size() == 1);

        assertTrue(sales.get(0).getProductType().equals(ProductType.APPLE));
        assertTrue(sales.get(0).getEachProductValue().equals(10d));
        assertTrue(sales.get(0).getNumberOfOccurrences() == 20);
    }

    @Test
    public void shouldCreateOneSingleSaleOfPineApples() {
        messageConsumer.process("pineapple at 10p");
        List<Sale> sales = salesAdjustmentsStore.getSales();

        assertTrue(sales.size() == 1);
    }

    @Test
    public void shouldAdjustNothing() {
        messageConsumer.process("pineapple at 10p");
        messageConsumer.process("Add 20p apples");

        List<Sale> sales = salesAdjustmentsStore.getSales();
        List<Adjustment> adjustments = salesAdjustmentsStore.getAdjustments();

        assertTrue(sales.size() == 1);
        assertTrue(adjustments.size() == 1);


        assertTrue(sales.get(0).getProductType().equals(ProductType.PINEAPPLE));
        assertTrue(sales.get(0).getEachProductValue().equals(10d));

        assertTrue(adjustments.get(0).getOperation().equals(Operation.ADD));
        assertTrue(adjustments.get(0).getValue().equals(20d));
        assertTrue(adjustments.get(0).getProductType().equals(ProductType.APPLE));
    }

    @Test
    public void shouldAdjustApple() {
        messageConsumer.process("apple at 10p");

        List<Sale> sales = salesAdjustmentsStore.getSales();

        assertTrue(sales.size() == 1);
        assertTrue(sales.get(0).getProductType().equals(ProductType.APPLE));
        assertTrue(sales.get(0).getEachProductValue().equals(10d));

        messageConsumer.process("Add 20p apples");

        List<Adjustment> adjustments = salesAdjustmentsStore.getAdjustments();
        sales = salesAdjustmentsStore.getSales();

        assertTrue(sales.size() == 1);
        assertTrue(adjustments.size() == 1);


        assertTrue(sales.get(0).getProductType().equals(ProductType.APPLE));
        assertTrue(sales.get(0).getEachProductValue().equals(30d));

        assertTrue(adjustments.get(0).getOperation().equals(Operation.ADD));
        assertTrue(adjustments.get(0).getValue().equals(20d));
        assertTrue(adjustments.get(0).getProductType().equals(ProductType.APPLE));
    }

    @Test
    public void shouldAddSubtractMultiplyOperationForOranges() {
        messageConsumer.process("orange at 10p");
        messageConsumer.process("orange at 4p");
        messageConsumer.process("20 sales of oranges at 20p each");

        messageConsumer.process("Add 20p oranges");
        messageConsumer.process("Multiply 2p oranges");
        messageConsumer.process("Subtract 5p oranges");

        List<Sale> sales = salesAdjustmentsStore.getSales();
        List<Adjustment> adjustments = salesAdjustmentsStore.getAdjustments();
        Map<Sale, List<Adjustment>> saleAdjustmentMap = saleAdjuster.getAdjustmentsForEachSale();

        assertTrue(sales.size() == 3);
        assertTrue(adjustments.size() == 3);

        assertTrue(sales.get(0).getProductType().equals(ProductType.ORANGE));
        assertTrue(sales.get(1).getProductType().equals(ProductType.ORANGE));
        assertTrue(sales.get(2).getProductType().equals(ProductType.ORANGE));

        assertTrue(sales.get(0).getNumberOfOccurrences() == 1);
        assertTrue(sales.get(1).getNumberOfOccurrences() == 1);
        assertTrue(sales.get(2).getNumberOfOccurrences() == 20);

        assertTrue(sales.get(0).getEachProductValue().equals(10d));
        assertTrue(sales.get(1).getEachProductValue().equals(7d));
        assertTrue(sales.get(2).getEachProductValue().equals(15d));


        List<Adjustment> adjustmentList = saleAdjustmentMap.get(sales.get(0));
        assertTrue(adjustmentList.size() == 3);
    }
}