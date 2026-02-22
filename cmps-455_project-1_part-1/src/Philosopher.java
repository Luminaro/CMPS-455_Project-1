import java.util.concurrent.Semaphore;

public class Philosopher extends Thread{
    MealCount mealCount;
    Chopstick first;
    Chopstick second;
    static final long cycle_duration_millis = 0;
    int id;
    public Philosopher(
            MealCount mealCount,
            boolean isLeftFirst,
            Chopstick left,
            Chopstick right,
            int id
    ){
        this.mealCount = mealCount;
        this.id = id;
        this.first = (isLeftFirst) ? left : right;
        this.second = (isLeftFirst) ? right : left;
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
    public int randint(int min, int max){
        return (int)(Math.random() * (max - min + 1) + max);
    }
    public void waitRandomCycles(int min_cycles, int max_cycles){
        int num_cycles = randint(min_cycles, max_cycles);
        for(int i=0; i<num_cycles; i++){
            try{
                Thread.sleep(cycle_duration_millis);
            } catch (InterruptedException e){
                throw(new RuntimeException(e));
            }
        }
    }
    public void eat(int meals_left){
        log("Started eating");
        waitRandomCycles(1, 3);
        log(String.format("Stopped eating. %d meals left...", meals_left));
    }
    public void think(){
        log("Started thinking");
        waitRandomCycles(1, 3);
        log("Stopped thinking");
    }
}
