public class Part1 {
    public static void main(String[] args) throws InterruptedException {
        int num_meals = 10000;
        int num_philosophers = 20;
        long cycle_duration_millis = 1000;

        MealCount mealCount = new MealCount(num_meals);
        Chopstick[] chopsticks = new Chopstick[num_philosophers];
        for (int i = 0; i < chopsticks.length; i++) {
            chopsticks[i] = new Chopstick(i);
        }

        Philosopher[] philosophers = new Philosopher[num_philosophers];

        for (int i = 0; i < num_philosophers; i++) {
            //System.out.printf("i: %d | isLeftFirst: %b\n", i, i%2==0);
            philosophers[i] = new Philosopher(
                    mealCount,
                    i % 2 == 0,
                    chopsticks[i],
                    chopsticks[(i + 1) % num_philosophers],
                    i,
                    cycle_duration_millis
            );
        }
        long start_time_ns = System.nanoTime();
        for (int i = 0; i < num_philosophers; i++) {
            philosophers[i].start();
        }

        for(int i=0; i<num_philosophers; i++){
            philosophers[i].join();
        }

        long stop_time_ns = System.nanoTime();

        long duration_ns = stop_time_ns - start_time_ns;
        System.out.printf("Runtime in milliseconds = ");
        System.out.println((duration_ns) / 1000000.0);

    }

}