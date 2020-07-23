package part5;

import java.util.concurrent.*;

public class ExecutorExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
       // threadPoolExample();
        rejectExecutionExample();
    }

    public static void threadPoolExample() throws ExecutionException, InterruptedException {
        Callable<String> task = () -> Thread.currentThread().getName();
        ExecutorService service = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 5; i++) {
            Future result = service.submit(task);
            System.out.println(result.get());
        }
        service.shutdown();
    }

    public static void rejectExecutionExample(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.SECONDS, new SynchronousQueue());
        Callable<String> task = () -> Thread.currentThread().getName();
        threadPoolExecutor.setRejectedExecutionHandler((runnable, executor) -> System.out.println("Rejected"));
        for (int i = 0; i < 5; i++) {
            threadPoolExecutor.submit(task);
        }
        threadPoolExecutor.shutdown();
    }

    public static void sheduledExecutorServiceExample(){
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);
        Callable<String> task = () -> {
            System.out.println(Thread.currentThread().getName());
            return Thread.currentThread().getName();
        };
        scheduledExecutorService.schedule(task, 1, TimeUnit.MINUTES);
        scheduledExecutorService.shutdown();
    }
}
