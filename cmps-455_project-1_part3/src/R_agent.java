public class R_agent implements Runnable{
    private int id;
    public R_agent(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {

            Part2.rControl.acquire();

            long startTime = System.currentTimeMillis();
            System.out.println("Reader " + id + " started reading.");

            // Simulate reading for a random time (0.5 to 2 seconds)
            Thread.sleep((long) (500 + Math.random() * 1500));

            long endTime = System.currentTimeMillis();
            System.out.println("Reader " + id + " finished reading (duration: " +
                    (endTime - startTime) + "ms).");

            Part2.countMutex.acquire();
            Part2.readCount++;
            Part2.finReadMutex.acquire();
            Part2.finishedRead++;
            Part2.finReadMutex.release();
            if (Part2.readCount == Part2.maxReaders || Part2.finishedRead == Part2.numReaders) {
                Part2.readCount = 0;

                Part2.cControl.release();
            }

            Part2.countMutex.release();
            Part2.finCoordMutex.acquire();
            if(Part2.finishedCoord == Part2.numCoordinators){
                Part2.rControl.release();

            }
            Part2.finCoordMutex.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
