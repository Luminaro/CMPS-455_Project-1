import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main {
    // Shared resources
    static int readCount = 0;
    static int coordCount = 0;
    
    // Semaphores
    static Semaphore readCountMutex = new Semaphore(1);      // Protects readCount
    static Semaphore coordCountMutex = new Semaphore(1);     // Protects coordCount
    static Semaphore readSem = null;                          // Limits concurrent readers to N
    static Semaphore coordSem = new Semaphore(1);            // Gates coordinator access (released when last reader finishes)
    static Semaphore readerQueue = new Semaphore(1);         // Blocks new readers when coordinator is active
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("How many readers will there be?: ");
        int numReaders = scanner.nextInt();
        System.out.println("Number of readers: " + numReaders);

        System.out.println();
        
        System.out.print("How many coordinators will there be?: ");
        int numCoordinators = scanner.nextInt();
        System.out.println("Number of coordinators: " + numCoordinators);
        
        System.out.println();
        
        System.out.print("Maximum number of readers that may access the resource at once?: ");
        int maxReaders = scanner.nextInt();
        System.out.println("Maximum concurrent readers: " + maxReaders);
        
        System.out.println();
        
        // Initialize readSem with maxReaders permits
        readSem = new Semaphore(maxReaders);
        
        System.out.println("Starting simulation...\n");
        
        // Create and start reader threads
        Thread[] readerThreads = new Thread[numReaders];
        for (int i = 0; i < numReaders; i++) {
            readerThreads[i] = new Thread(new ReadingAgent(i + 1));
            readerThreads[i].start();
        }
        
        // Create and start coordinator threads
        Thread[] coordThreads = new Thread[numCoordinators];
        for (int i = 0; i < numCoordinators; i++) {
            coordThreads[i] = new Thread(new CoordAgent(i + 1));
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
