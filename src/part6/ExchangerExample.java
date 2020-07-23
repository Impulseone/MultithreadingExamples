package part6;

import java.util.concurrent.Exchanger;
import java.util.concurrent.SynchronousQueue;

public class ExchangerExample {
    public static void main(String[] args) throws InterruptedException {
      //  exchangerExample();
        synchronousQueue();
    }

    public static void exchangerExample(){
        Exchanger<String> exchanger = new Exchanger<>();
        Runnable task = () -> {
            try {
                Thread thread = Thread.currentThread();
                String withThreadName = exchanger.exchange(thread.getName());
                System.out.println(thread.getName() + " обменялся с " + withThreadName);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        new Thread(task).start();
        new Thread(task).start();
    }

    public static void synchronousQueue() throws InterruptedException {
        SynchronousQueue<String> queue = new SynchronousQueue<>();
        Runnable task = () -> {
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        new Thread(task).start();
        queue.put("Message");
    }
}

