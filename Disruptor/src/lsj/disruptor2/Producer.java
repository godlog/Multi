package lsj.disruptor2;

import com.lmax.disruptor.RingBuffer;

/**
 * @author TF016519
 * @description:
 * @date 2018-1-22 17:24
 * @version:1.0.0
 */
public class Producer {
    private RingBuffer<Order> ringBuffer;

    public Producer(RingBuffer<Order> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(String data) {
        long sequence = ringBuffer.next();
        try{
            Order order = ringBuffer.get(sequence);
            order.setId(data);
        }finally {
            ringBuffer.publish(sequence);
        }
    }
}
