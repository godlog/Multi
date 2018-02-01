package lsj.disruptor1;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author TF016519
 * @description:
 * @date 2018-1-19 13:16
 * @version:1.0.0
 */
public class TradePublisher implements Runnable {

    Disruptor<Trade> disruptor;
    CountDownLatch countDownLatch;
    private static int LOOP = 10;

    public TradePublisher(Disruptor<Trade> disruptor, CountDownLatch countDownLatch) {
        this.disruptor = disruptor;
        this.countDownLatch = countDownLatch;

    }


    @Override
    public void run() {
        TradeEventTranslator tradeEventTranslator =new TradeEventTranslator();
        for(int i = 0 ;i<LOOP; i++){
            disruptor.publishEvent(tradeEventTranslator);
        }
        this.countDownLatch.countDown();

    }
}

class TradeEventTranslator implements EventTranslator<Trade> {

    private Random random = new Random();

    @Override
    public void translateTo(Trade trade, long l) {
        this.generateTrade(trade);
    }

    private Trade generateTrade(Trade trade) {
        trade.setPrice(random.nextDouble());
        return trade;
    }
}
