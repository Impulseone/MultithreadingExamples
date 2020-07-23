class HelloWorldApp {
    public static void main(String[] args) {
        Runnable task = () -> {
            System.out.println("Hello, World!");
        };
        Thread thread = new Thread(task);
        thread.start();

        Thread currentThread = Thread.currentThread();
        ThreadGroup threadGroup = currentThread.getThreadGroup();
        System.out.println("Thread: " + currentThread.getName());
        System.out.println("Thread Group: " + threadGroup.getName());
        System.out.println("Parent Group: " + threadGroup.getParent().getName());

    }
}
