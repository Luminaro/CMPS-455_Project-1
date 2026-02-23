public class CoordAgent implements Runnable {
    private int id;
    
    public CoordAgent(int id) {
        this.id = id;
    }
    
    @Override
    public void run() {
        try {
            // Increment coordinator count
            Main.coordCountMutex.acquire();
            Main.coordCount++;
            // If this is the first coordinator, acquire readerQueue to block new readers from starting
            if (Main.coordCount == 1) {
                Main.readerQueue.acquire();
            }
            Main.coordCountMutex.release();
            
            // Wait for all active readers to finish
            Main.coordSem.acquire();
            
            // Perform coordination operation
            long startTime = System.currentTimeMillis();
            System.out.println("CoordAgent " + id + " started coordinating.");
            
            // Simulate coordination for a random time (0.5 to 2 seconds)
            Thread.sleep((long) (500 + Math.random() * 1500));
            
            long endTime = System.currentTimeMillis();
            System.out.println("CoordAgent " + id + " finished coordinating (duration: " + 
                             (endTime - startTime) + "ms).");
            
            // Release exclusive access for readers
            Main.coordSem.release();
            
            // Decrement coordinator count
            Main.coordCountMutex.acquire();
            Main.coordCount--;
            // If this is the last coordinator, release readerQueue to allow new readers
            if (Main.coordCount == 0) {
                Main.readerQueue.release();
            }
            Main.coordCountMutex.release();
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
