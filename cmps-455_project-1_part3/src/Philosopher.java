import java.util.concurrent.Semaphore;

public class Philosopher extends Thread{
    MealCount mealCount;
    Chopstick first;
    Chopstick second;
    long cycle_duration_millis;
    int id;
    public Philosopher(
            MealCount mealCount,
            boolean isLeftFirst,
            Chopstick left,
            Chopstick right,
            int id,
            long cycle_duration_millis
    ){
        this.mealCount = mealCount;
        this.id = id;
        this.first = (isLeftFirst) ? left : right;
        this.second = (isLeftFirst) ? right : left;
        this.cycle_duration_millis = cycle_duration_millis;
    }
    @Override
    public void run(){
        log("Sat down");
        int meals_left = mealCount.tryEat();

        while (meals_left > 0) {
            pickUp(first);
            pickUp(second);

            eat(meals_left);

            putDown(first);
            putDown(second);

            think();

            meals_left = mealCount.tryEat();
        }
    }
    public void log(String message){
        System.out.printf("<PHILOSOPHER %d>: %s\n",
                this.id,
                message
        );
    }
    public void pickUp(Chopstick chopstick){
        try{
            chopstick.acquire();
        } catch(InterruptedException e){
            throw(new RuntimeException(e));
        }
        log(String.format("Picked up chopstick %d", chopstick.id));
    }
    public void putDown(Chopstick chopstick){
        chopstick.release();
        log(String.format("Put down chopstick %d", chopstick.id));
    }
    static public int randint(int min, int max){
        return (int)(Math.random() * (max - min + 1) + max);
    }
    static public void waitRandomCycles(int min_cycles, int max_cycles, long cycle_duration_millis){
        int num_cycles = randint(min_cycles, max_cycles);
        try {
            Thread.sleep(cycle_duration_millis * num_cycles);
        } catch (InterruptedException e){
            throw(new RuntimeException(e));
        }
    }
    public void eat(int meals_left){
        log("Started eating");
        waitRandomCycles(3, 6, cycle_duration_millis);
        log(String.format("Stopped eating. %d meals left...", meals_left));
    }
    public void think(){
        log("Started thinking");
        waitRandomCycles(3, 6, cycle_duration_millis);
        log("Stopped thinking");
    }
}
