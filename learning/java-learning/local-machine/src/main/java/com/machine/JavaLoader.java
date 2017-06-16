package com.machine;

/**
 * Created by michael on 2017-06-14.
 */
public class JavaLoader {

    public static void main(String[] args) throws InterruptedException {

        ClassLoader appClassLoader = JavaLoader.class.getClassLoader();
        System.out.println(appClassLoader);

        ClassLoader extClassLoader = appClassLoader.getParent();
        System.out.println(extClassLoader);
        //AppClassLoader的父加载器是ExtClassLoader

        System.out.println(extClassLoader.getParent()); //null
        //ExtClassLoader的父加载器是null, 也就是BootStrap,这是由c语言实现的

        ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(sysClassLoader);
        //由上面的验证可知, 应用程序类加载器和系统类加载器是相同的, 因为地址是一样的

        ////////////////////////////////////////////////
        Thread thread = new Thread(() -> {
            ClassLoader threadcontextClassLosder = Thread.currentThread().getContextClassLoader();
            System.out.println(threadcontextClassLosder);
        });
        thread.start();
        thread.join();

        ////////////////////////////////////////////////
        try {
            Class.forName("com.machine.Main", true,
                    JavaLoader.class.getClassLoader().getParent());
            System.out.println("1 -- 类被加载");
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            System.out.println("1 -- 未找到类");
        }

        ////////////////////////////////////////////////
        try {
            Class.forName("java.lang.String", true,
                    JavaLoader.class.getClassLoader());
            System.out.println("2 -- 类被加载");
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            System.out.println("2 -- 未找到类");
        }
    }

}
