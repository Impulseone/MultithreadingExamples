import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class Part2 {
    public static void main(String[] args) throws InterruptedException {
//       sleepExample();
//       interruptExample();
        //lockExample();
      //  waitExample();
     //   parkExample();
        reentrantLockExample();
    }

    public static void sleepExample(){
        Runnable task = () -> {
            try {
//                int secToWait = 1000 * 60;
//                Thread.currentThread().sleep(secToWait);
//                System.out.println("Waked up");
                TimeUnit.SECONDS.sleep(3);
                System.out.println("Waked up");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    public static void interruptExample(){
        Runnable task = () -> {
            try {
                TimeUnit.SECONDS.sleep(60);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
        };
        Thread thread = new Thread(task);
        thread.start();
        thread.interrupt();
    }

    public static void joinExample() throws InterruptedException {
        Runnable task = () -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
        };
        Thread thread = new Thread(task);
        thread.start();
        thread.join();
        System.out.println("Finished");
    }

    public static void lockExample() throws InterruptedException {
        Object lock = new Object();

        Runnable task = () -> {
            synchronized (lock) {
                System.out.println("thread");
            }
        };
        Thread th1 = new Thread(task);
        th1.start();
        synchronized (lock) {
            for (int i = 0; i < 8; i++) {
                Thread.currentThread().sleep(1000);
                System.out.print("  " + i);
            }
            System.out.println(" ...");
        }
    }

    public static void waitExample() throws InterruptedException {
        Object lock = new Object();
        // task будет ждать, пока его не оповестят через lock
        Runnable task = () -> {
            synchronized(lock) {
                try {
                    lock.wait();
                } catch(InterruptedException e) {
                    System.out.println("interrupted");
                }
            }
            // После оповещения нас мы будем ждать, пока сможем взять лок
            System.out.println("thread");
        };
        Thread taskThread = new Thread(task);
        taskThread.start();
        // Ждём и после этого забираем себе лок, оповещаем и отдаём лок
        Thread.currentThread().sleep(3000);
        System.out.println("main");
        synchronized(lock) {
            lock.notify();
        }
    }

    public static void parkExample() throws InterruptedException {
        Runnable task = () -> {
            //Запаркуем текущий поток
            System.err.println("Will be Parked");
            LockSupport.park();
            // Как только нас распаркуют - начнём действовать
            System.err.println("Unparked");
        };
        Thread th = new Thread(task);
        th.start();
        Thread.currentThread().sleep(2000);
        System.err.println("Thread state: " + th.getState());

        LockSupport.unpark(th);
        Thread.currentThread().sleep(2000);
    }

    public static void reentrantLockExample() throws InterruptedException {
        Lock lock = new ReentrantLock();
        Runnable task = () -> {
            lock.lock();
            System.out.println("Thread");
            lock.unlock();
        };
        lock.lock();

        Thread th = new Thread(task);
        th.start();
        System.out.println("main");
        Thread.currentThread().sleep(2000);
        lock.unlock();
    }
}
