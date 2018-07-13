package Utils;


import com.jpmorgan.messages.system.MessageConsumer;
import com.jpmorgan.messages.system.MessageConsumerImpl;
import com.jpmorgan.messages.system.Utils.SaleAdjuster;
import com.jpmorgan.messages.system.Utils.SaleAdjustmentStore;
import com.jpmorgan.messages.system.model.Adjustment;
import com.jpmorgan.messages.system.model.Operation;
import com.jpmorgan.messages.system.model.ProductType;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class MessageConsumerTest {
    private SaleAdjuster saleAdjuster;
    private SaleAdjustmentStore saleAdjustmentStore;
    private MessageConsumer messageConsumer;

    @Before
    public void init(){
        saleAdjuster = new SaleAdjuster();
        saleAdjustmentStore = new SaleAdjustmentStore(saleAdjuster);
        messageConsumer = new MessageConsumerImpl(saleAdjustmentStore);
        }
    @Test
    public void shouldCreateAdustment(){
        messageConsumer.process("Add 20p apples");
        saleAdjustmentStore = new SaleAdjustmentStore(saleAdjuster);
        messageConsumer = new MessageConsumerImpl(saleAdjustmentStore);
    }
    @Test
    public void shouldCreate3Adjustment(){
        messageConsumer.process("Add 20p apples");
        messageConsumer.process("Multiply 2p pineapples");
        messageConsumer.process("Subtract 5p oranges");

        List<Adjustment> adjustments = saleAdjustmentStore.getAdjustments();
        assertTrue(adjustments.size() == 3);
        assertTrue(adjustments.get(1).getOperation().equals(Operation.MULTIPLY));
        assertTrue(adjustments.get(1).getProductType().equals(ProductType.PINEAPPLE));
        assertTrue(adjustments.get(1).getOperation().equals(Operation.SUBTRACT));
        assertTrue(adjustments.get(1).getProductType().equals(ProductType.ORANGE));
    }
@Test
    public void shouldCreatedOneAppleSaleAndCreateOneAdjustmentForApple(){
        messageConsumer.process("Add 20p apples");
        messageConsumer.process("20 sales of apples at 10p each");
}

}
