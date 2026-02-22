import java.util.concurrent.Semaphore;

public class MealCount {
    int meals_left;
    Semaphore meals_left_mutex;
    public MealCount(
            int initial_meal_count
    ){
        this.meals_left = initial_meal_count;
        this.meals_left_mutex = new Semaphore(1);
    }

    public int tryEat(){
        try {
            this.meals_left_mutex.acquire();
        } catch (InterruptedException e){
            throw(new RuntimeException(e));
        }
        meals_left -= 1;
        int meals_left_temp = meals_left;
        this.meals_left_mutex.release();
        return meals_left_temp;
    }

}
