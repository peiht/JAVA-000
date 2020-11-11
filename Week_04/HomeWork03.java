package com.javaAdvace.concurrent;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class HomeWork03 {

    public static void main(String[] args) throws Exception{
//        //线程池搭配Future
//        new HomeWork03().executeSync1();
//        //FutureTask配合线程池
//        new HomeWork03().executeSync2();
//        //FutureTask配合Thread
//        new HomeWork03().executeSync3();
//        //使用CompletableFuture实现
//        new HomeWork03().executeSync4();
//        //使用CountDownLatch实现
//        new HomeWork03().executeSync5();
//        //使用CyclicBarrier实现
//        new HomeWork03().executeSync6();
//        //使用Semaphore实现
//        new HomeWork03().executeSync7();
//        //使用synchronized和Object的wait()、notifyAll()实现
//        new HomeWork03().executeSync8();
//        //使用Lock和Condition实现
//        new HomeWork03().executeSync9();
//        //使用无锁
//        new HomeWork03().executeSync10();
        //LockSupport
        new HomeWork03().executeSync11();
    }

    /**
     * 线程池搭配Future
     */
    private void executeSync1(){
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Integer> task = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sum();
            }
        });
        executorService.shutdown();
        // 确保  拿到result 并输出
        try {
            System.out.println("异步计算结果为："+ task.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        // 然后退出main线程
    }

    /**
     * FutureTask配合线程池
     */
    public void executeSync2(){
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        ExecutorService executorService = Executors.newCachedThreadPool();
        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sum();
            }
        });
        executorService.submit(task);
        executorService.shutdown();
        // 确保  拿到result 并输出
        try {
            System.out.println("异步计算结果为："+ task.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        // 然后退出main线程
    }

    /**
     * FutureTask配合Thread
     */
    public void executeSync3(){
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sum();
            }
        });
        new Thread(task).start();
        // 确保  拿到result 并输出
        try {
            System.out.println("异步计算结果为："+ task.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        // 然后退出main线程
    }

    /**
     * 使用CompletableFuture
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void executeSync4() throws ExecutionException, InterruptedException {
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        // 确保  拿到result 并输出
        Integer result = CompletableFuture.supplyAsync(() -> {
            return sum();
        }).get();

        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        // 然后退出main线程
    }

    /**
     * CountDownLatch
     */
    public void executeSync5(){
        CountDownLatch latch = new CountDownLatch(1);
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        try {
            Task5 task5 = new Task5(latch);
            new Thread(task5).start();
            latch.await();
            // 确保  拿到result 并输出
            System.out.println("异步计算结果为：" + task5.getRes());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        // 然后退出main线程
    }

    public static class Task5 implements Runnable{
        private CountDownLatch latch;
        private int res;
        public Task5(CountDownLatch latch){
            this.latch = latch;
        }
        @Override
        public void run() {
            this.res = sum();
            latch.countDown();
        }
        public int getRes(){
            return this.res;
        }
    }

    /**
     * CyclicBarrier实现
     */
    public void executeSync6(){
        CyclicBarrier barrier = new CyclicBarrier(2);
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        // 确保  拿到result 并输出
        Task6 task6 = new Task6(barrier);
        new Thread(task6).start();
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e){
            e.printStackTrace();
        }
        System.out.println("异步计算结果为：" + task6.getRes());
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        // 然后退出main线程
    }



    public static class Task6 implements Runnable{
        private CyclicBarrier barrier;
        private int res;
        public Task6(CyclicBarrier barrier){
            this.barrier = barrier;
        }
        @Override
        public void run() {
            try {
                this.res = sum();
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        public int getRes(){
            return this.res;
        }
    }

    /**
     * Semaphore 实现
     */
    public void executeSync7(){
        Semaphore semaphore = new Semaphore(1);
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        Task7 task7 = new Task7(semaphore);
        try {
            //阻塞
            semaphore.acquire(1);
            new Thread(task7).start();
            semaphore.acquire();
            // 确保  拿到result 并输出
            System.out.println("异步计算结果为：" + task7.getRes());
            System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
            // 然后退出main线程
        }catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    static class Task7 implements Runnable{
        private Semaphore semaphore;
        private int res;
        public Task7(Semaphore semaphore){
            this.semaphore = semaphore;
        }
        @Override
        public void run() {
            try {
                this.res = sum();
            } finally {
                semaphore.release(1);
            }
        }

        public int getRes(){
            return this.res;
        }
    }

    /**
     * synchronized 实现
     */
    public void executeSync8(){
        Object obj = new Object();
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        Task8 task8 = new Task8(obj);
        new Thread(task8).start();
        synchronized (obj) {
            try {
                obj.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + task8.getRes());
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        // 然后退出main线程
    }

    static class Task8 implements Runnable{
        private Object obj;
        private int res;
        public Task8(Object obj){
            this.obj = obj;
        }
        @Override
        public void run() {
            synchronized (this.obj){
                this.res = sum();
                obj.notifyAll();
            }
        }

        public int getRes(){
            return this.res;
        }
    }

    /**
     * Lock 和 condition 实现
     *
     */
    public void executeSync9(){
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        Task9 task9 = new Task9(condition, lock);
        new Thread(task9).start();

        lock.lock();
        try {
            condition.await();
            // 确保  拿到result 并输出
            System.out.println("异步计算结果为：" + task9.getRes());
            System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    static class Task9 implements Runnable{
        private int res;
        private Condition condition;
        private Lock lock;

        public Task9(Condition condition, Lock lock){
            this.condition = condition;
            this.lock = lock;
        }
        @Override
        public void run() {
            lock.lock();
            try {
                this.res = sum();
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }

        public int getRes(){
            return this.res;
        }
    }


    private static volatile int res10;
    /**
     * 自旋，如果结果正确就退出自旋往下执行，虽然这样破坏了task的封装特性
     */
    public void executeSync10(){
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        Task10 task10 = new Task10();
        new Thread(task10).start();
        while (HomeWork03.res10 != 24157817){
            if (HomeWork03.res10 == 24157817){
                break;
            }
        }
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为：" + HomeWork03.res10);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    static class Task10 implements Runnable{
        @Override
        public void run() {
            res10 = sum();
        }
    }

    /**
     * LockSupport实现
     */
    public void executeSync11(){
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        Task11 task11 = new Task11(Thread.currentThread());
        new Thread(task11).start();
        // 确保  拿到result 并输出
        //阻塞当前主线程
        LockSupport.park();
        System.out.println("异步计算结果为：" + task11.getRes());
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    static class Task11 implements Runnable{
        int res;
        Thread thread;
        public Task11(Thread thread) {
            this.thread = thread;
        }
        @Override
        public void run() {
            this.res = sum();
            //释放主线程
            LockSupport.unpark(thread);
        }

        public int getRes(){
            return this.res;
        }
    }


    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2 )
            return 1;
        return fibo(a-1) + fibo(a-2);
    }


}
