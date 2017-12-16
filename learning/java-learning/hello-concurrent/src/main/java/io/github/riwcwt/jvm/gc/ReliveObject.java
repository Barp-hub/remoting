package io.github.riwcwt.jvm.gc;

public class ReliveObject {

    public static ReliveObject object = null;

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("执行垃圾回收！");
        object = this;
    }

    public static void main(String[] args) throws InterruptedException {

        object = new ReliveObject();

        object = null;

        System.gc();

        Thread.sleep(1000);

        if (object == null) {
            System.out.println("垃圾回收成功！");
        } else {
            System.out.println("垃圾回收失败！");
        }

        object = null;

        System.gc();

        Thread.sleep(1000);

        if (object == null) {
            System.out.println("垃圾回收成功！");
        } else {
            System.out.println("垃圾回收失败！");
        }
    }
}
