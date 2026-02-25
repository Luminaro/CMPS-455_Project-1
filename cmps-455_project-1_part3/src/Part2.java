import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Part2 {
    static Semaphore rControl;
    static Semaphore cControl;
    static Semaphore countMutex;
    static Semaphore finCoordMutex;
    static Semaphore finReadMutex;
    static int readCount;
    static int maxReaders;
    static int finishedCoord;
    static int finishedRead;
    static int numCoordinators;
    static int numReaders;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("How many readers will there be?: ");
        numReaders = scanner.nextInt();
        System.out.println("Number of readers: " + numReaders);

        System.out.println();

        System.out.print("How many coordinators will there be?: ");
        numCoordinators = scanner.nextInt();
        System.out.println("Number of coordinators: " + numCoordinators);

        System.out.println();

        System.out.print("Maximum number of readers that may access the resource at once?: ");
        maxReaders = scanner.nextInt();
        System.out.println("Maximum concurrent readers: " + maxReaders);

        System.out.println();

        rControl = new Semaphore(maxReaders);
        cControl = new Semaphore(0);
        countMutex = new Semaphore(1);
        finCoordMutex = new Semaphore(1);
        finReadMutex = new Semaphore(1);
        readCount = 0;
        finishedCoord = 0;
        finishedRead = 0;

        Thread[] readerThreads = new Thread[numReaders];
        for (int i = 0; i < numReaders; i++) {
            readerThreads[i] = new Thread(new R_agent(i + 1));
            readerThreads[i].start();
        }

        // Create and start coordinator threads
        Thread[] coordThreads = new Thread[numCoordinators];
        for (int i = 0; i < numCoordinators; i++) {
            coordThreads[i] = new Thread(new C_agent(i + 1));
            coordThreads[i].start();
        }

        // Wait for all threads to complete
        try {
            for (Thread t : readerThreads) {
                t.join();
            }
            for (Thread t : coordThreads) {
                t.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nSimulation completed.");
        scanner.close();


    }
}