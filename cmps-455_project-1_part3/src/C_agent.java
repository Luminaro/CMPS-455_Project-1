public class C_agent implements Runnable{
    private int id;
    public C_agent(int id) {
        this.id = id;
    }
    @Override
    public void run() {
        try {

            Part2.cControl.acquire();
            long startTime = System.currentTimeMillis();
            System.out.println("CoordAgent " + id + " started coordinating.");

            // Simulate coordination for a random time (0.5 to 2 seconds)
            Thread.sleep((long) (500 + Math.random() * 1500));

            long endTime = System.currentTimeMillis();
            System.out.println("CoordAgent " + id + " finished coordinating (duration: " +
                        (endTime - startTime) + "ms).");

            for (int i = 0; i < Part2.maxReaders; i++) {
                Part2.rControl.release();
            }

            Part2.finCoordMutex.acquire();
            Part2.finishedCoord++;
            Part2.finCoordMutex.release();


            Part2.finReadMutex.acquire();
            if(Part2.finishedRead == Part2.numReaders){
                Part2.cControl.release();

            }
            Part2.finReadMutex.release();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
