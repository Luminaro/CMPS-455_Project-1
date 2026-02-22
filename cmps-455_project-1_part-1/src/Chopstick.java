import java.util.concurrent.Semaphore;

public class Chopstick {
    int id;
    Semaphore s;
    Chopstick(int id){
        this.id = id;
        this.s = new Semaphore(1);
    }
    public void acquire() throws InterruptedException {
        s.acquire();
    }
    public void release(){
        s.release();
    }
}
