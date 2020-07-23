package part4;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class HelloWorld {

    public static void main(String []args) throws Exception {
        Callable task = () -> "Hello, World!";
        FutureTask<String> future = new FutureTask<>(task);
        new Thread(future).start();
        System.out.println(future.get());
    }
}
