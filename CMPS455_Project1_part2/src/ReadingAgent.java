public class ReadingAgent implements Runnable {
    private int id;
    
    public ReadingAgent(int id) {
        this.id = id;
    }
    
    @Override
    public void run() {
        try {
            // Increment read count
            Main.readCountMutex.acquire();
            Main.readCount++;
            // If this is the first reader, acquire coordinator semaphore (blocks coordinators)
            if (Main.readCount == 1) {
                Main.coordSem.acquire();
            }
            Main.readCountMutex.release();
            
            // Attempt to acquire read access (limited to N concurrent readers)
            Main.readSem.acquire();
            
            // Perform read operation
            long startTime = System.currentTimeMillis();
            System.out.println("Reader " + id + " started reading.");
            
            // Simulate reading for a random time (0.5 to 2 seconds)
            Thread.sleep((long) (500 + Math.random() * 1500));
            
            long endTime = System.currentTimeMillis();
            System.out.println("Reader " + id + " finished reading (duration: " + 
                             (endTime - startTime) + "ms).");
            
            // Release read semaphore
            Main.readSem.release();
            
            // Decrement read count
            Main.readCountMutex.acquire();
            Main.readCount--;
            // If this is the last reader, release coordinator semaphore (unblocks coordinators)
            if (Main.readCount == 0) {
                Main.coordSem.release();
            }
            Main.readCountMutex.release();
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
