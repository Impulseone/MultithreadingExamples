package part4;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Function;

public class CompletableFutureExample {
    public static void main(String []args) throws Exception {
        // CompletableFuture уже содержащий результат
        // CompletableFuture, запускающий (run) новый поток с Runnable, поэтому он Void
        CompletableFuture<Void> voidCompletableFuture;
        voidCompletableFuture = CompletableFuture.runAsync(() -> {
            System.out.println("run " + Thread.currentThread().getName());
        });
        // CompletableFuture, запускающий новый поток, результат которого возьмём у Supplier
        CompletableFuture<String> supplier = CompletableFuture.supplyAsync(() -> {
            System.out.println("supply " + Thread.currentThread().getName());
            return "Значение";
        });
    }

    public static void atomicLongExample(){
        AtomicLong longValue = new AtomicLong(0);
        Runnable task = () -> longValue.set(new Date().getTime());
        Function<Long, Date> dateConverter = (longvalue) -> new Date(longvalue);
        Consumer<Date> printer = date -> {
            System.out.println(date);
            System.out.flush();
        };
        // CompletableFuture computation
        CompletableFuture.runAsync(task)
                .thenApply((v) -> longValue.get())
                .thenApply(dateConverter)
                .thenAccept(printer);
    }
}
