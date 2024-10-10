import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophers2 {
    static int NUMBER_OF_PHILOSOPHERS = 10;
    static Lock[] fork = new Lock[NUMBER_OF_PHILOSOPHERS];
    static boolean[] eat = new boolean[NUMBER_OF_PHILOSOPHERS];
    static Philosopher[] philosophers = new Philosopher[NUMBER_OF_PHILOSOPHERS];

    static class Philosopher extends Thread{
        int seat;
        public Philosopher(int seat){
            this.seat = seat;
        }

        @Override
        public void run(){
            int left = Math.min(seat, (seat + 1) % NUMBER_OF_PHILOSOPHERS);
            int right = Math.max(seat, (seat + 1) % NUMBER_OF_PHILOSOPHERS);
            fork[left].lock();
            fork[right].lock();
            eat[seat] = true;
            fork[left].unlock();
            fork[right].unlock();
        }
    }


    public static void main(String[] args) {
        for(int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++){
            eat[i] = false;
            fork[i] = new ReentrantLock();
        }


        for(int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++){
            philosophers[i] = new Philosopher(i);
        }

        for(int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++){
            philosophers[i].start();
        }

        for(int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++){
            try {
                philosophers[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        for(var bool: eat){
            System.out.print(bool + " ");
        }
    }
}
