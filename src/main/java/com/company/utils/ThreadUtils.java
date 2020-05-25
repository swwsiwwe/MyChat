package com.company.utils;

import java.net.DatagramSocket;
import java.util.concurrent.*;

public class ThreadUtils {

    public static ExecutorService exec = Executors.newCachedThreadPool();

    public static int execute(Callable<Boolean> f,DatagramSocket ds) {
        FutureTask<Boolean> future = new FutureTask<Boolean>(f);
        exec.submit(future);
        try {
            System.out.println("!");
            future.get(5000, TimeUnit.MILLISECONDS);
        }catch (InterruptedException | ExecutionException | TimeoutException ie){
            future.cancel(true);
            ds.close();
            return 0;
        }
        return 1;
    }

    public static void close(){
        exec.shutdown();
    }

}
