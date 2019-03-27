public class Hello implements Runnable {

    //共享资源(临界资源)
    static int i = 0;

    /**
     * synchronized 修饰实例方法
     */
    public void increase() {
        i++;
    }

    @Override
    public void run() {
        for (int j = 0; j < 100000; j++) {
            increase();
            System.out.println(Thread.currentThread().getName() + ":" + i);
            //System.out.println(i);
            Thread.yield();
        }
    }

    public static void main(String[] args) {

        Thread t1 = new Thread(new Hello());
        Thread t2 = new Thread(new Hello());
        Thread t3 = new Thread(new Hello());
        Thread t4 = new Thread(new Hello());

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(i);
    }

}