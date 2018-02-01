package lsj.disruptor2;

import com.lmax.disruptor.WorkHandler;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author TF016519
 * @description:
 * @date 2018-1-22 17:23
 * @version:1.0.0
 */
public class Consumer implements WorkHandler<Order> {
    private String threadName;
    private static AtomicInteger count = new AtomicInteger(0);

    public Consumer(String s) {
        this.threadName = s;
    }

    @Override
    public void onEvent(Order order) throws Exception {
        System.err.println("当前消费者" + threadName + "消费对象" + order.getId());
        count.incrementAndGet();
    }
    public int getCount(){
        return count.get();
    }
}
