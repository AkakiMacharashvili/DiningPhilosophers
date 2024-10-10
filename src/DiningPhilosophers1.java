
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers1 {
    static boolean[] eat = new boolean[]{false, false, false, false, false};
    static Lock[] lock = new Lock[5];
    static CountDownLatch latch = new CountDownLatch(5);
    static class MyThread extends Thread{
        int index;

        public MyThread(int index){
            this.index = index;
        }

        @Override
        public void run() {
            try {
                while (!eat[index]) {
                    System.out.println("I am thinking" + index);
                    if (index % 2 == 0) {
                        lock[index].lock();
                        lock[(index + 1) % 5].lock();
                    } else {
                        lock[(index + 1) % 5].lock();
                        lock[index].lock();
                    }
                    eat[index] = true;

                    lock[index].unlock();
                    lock[(index + 1) % 5].unlock();
                }
                latch.countDown();
            }catch (Exception e){
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            lock[i] = new ReentrantLock();
        }

        for(int i = 0; i < 5; i++){
            MyThread myThread = new MyThread(i);
            myThread.start();

        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        for(var bool : eat){
            System.out.print(bool + " ");
        }
    }
}
