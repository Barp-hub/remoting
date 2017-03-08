package io.github.riwcwt.concurrent;

/**
 * Created by michael on 2017-03-08.
 */
public class VolatileExample {

    //设置类静态变量,各线程访问这同一共享变量
    private static volatile boolean flag = false;


    public static void main(String[] args) throws Exception {
        Thread thread = new Thread(() -> {
            while (!flag) {
                //System.out.println(flag);
            }
        });
        thread.start();
        //sleep的目的是等待线程启动完毕,也就是说进入run的无限循环体了
        Thread.sleep(10);
        flag = true;
    }

}
