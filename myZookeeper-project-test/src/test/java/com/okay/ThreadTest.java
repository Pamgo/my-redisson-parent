package com.okay;

/**
 * <h2><h2>
 *
 * @author pangy
 * @create 2020-07-31 15:43
 */
public class ThreadTest {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5*1000);
                    System.out.println("end-child-thread");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        //thread.setDaemon(true);
        thread.start();
        System.out.println("main-end");
    }
}
