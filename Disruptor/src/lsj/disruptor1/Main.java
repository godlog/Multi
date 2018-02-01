package lsj.disruptor1;


import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author TF016519
 * @description:
 * @date 2018-1-19 11:07
 * @version:1.0.0
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        long beginTime = System.currentTimeMillis();
        int bufferSize = 1024;
        ExecutorService executor = Executors.newFixedThreadPool(8);
        Disruptor<Trade> disruptor = new Disruptor(new EventFactory() {
            @Override
            public Object newInstance() {
                return new Trade();
            }
        }, bufferSize, executor, ProducerType.SINGLE, new BusySpinWaitStrategy());


        //菱形操作
//        EventHandlerGroup<Trade> handlerGroup =
//                disruptor.handleEventsWith(new Handel1(), new Handel2());
//        handlerGroup.then(new Handel3());


//        //六边形操作
//        Handel1 h1 = new Handel1();
//        Handel2 h2 = new Handel2();
//        Handel3 h3 = new Handel3();
//        Handel4 h4 = new Handel4();
//        Handel5 h5 = new Handel5();
//        disruptor.handleEventsWith(h1, h2);
//        disruptor.after(h1).handleEventsWith(h4);
//        disruptor.after(h2).handleEventsWith(h5);
//        disruptor.after(h4, h5).handleEventsWith(h3);



        //顺序操作
        disruptor.handleEventsWith(new Handel1()).
                handleEventsWith(new Handel2()).
                handleEventsWith(new Handel3());





        disruptor.start();//启动
        CountDownLatch latch = new CountDownLatch(1);
        //生产者准备
        executor.submit(new TradePublisher(disruptor, latch));
        latch.await();

        disruptor.shutdown();
        executor.shutdown();

        System.out.println("总耗时:" + (System.currentTimeMillis() - beginTime));
    }
}
