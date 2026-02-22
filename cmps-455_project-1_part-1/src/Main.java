public class Main {
    public static void main(String[] args) throws InterruptedException {
        for(int i=0; i<1; i++){
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            main2(args);
        }
    }
    public static void main2(String[] args) throws InterruptedException {
        int num_meals = 10;
        MealCount mealCount = new MealCount(num_meals);
        int num_philosophers = 5;
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
                    i
            );
        }

        for (int i = 0; i < num_philosophers; i++) {
            philosophers[i].start();
        }

        for(int i=0; i<num_philosophers; i++){
            philosophers[i].join();
        }


    }

}