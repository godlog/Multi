package lsj.disruptor1;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * @author TF016519
 * @description:
 * @date 2018-1-19 10:57
 * @version:1.0.0
 */
public class Handel4 implements EventHandler<Trade>,WorkHandler<Trade> {
    @Override
    public void onEvent(Trade event, long l, boolean b) throws Exception {
        this.onEvent(event);
    }

    @Override
    public void onEvent(Trade event) throws Exception {
        System.out.println("handler4: get name" + event.getName());
        event.setName(event.getName() + "h2");
        Thread.sleep(1000);
    }
}
