import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int numThreads = 4;
        int numOperations = 10000;

        runExperiment("OptimisticList", numThreads, numOperations, new OptimisticListWithVersion<>());;
    }

    private static void runExperiment(String listType, int numThreads, int numOperations, OptimisticListWithVersion<Integer> list) {
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(new ExperimentThread(list, i < numThreads / 2, numOperations));
            threads[i].start();
        }

        long startTime = System.currentTimeMillis();
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("Execution Time: " + executionTime + " ms");
    }

    static class ExperimentThread implements Runnable {
        private final OptimisticListWithVersion<Integer> list;
        private final boolean isAddThread;
        private final int numOperations;

        public ExperimentThread(OptimisticListWithVersion<Integer> list, boolean isAddThread, int numOperations) {
            this.list = list;
            this.isAddThread = isAddThread;
            this.numOperations = numOperations;
        }

        @Override
        public void run() {
            Random random = new Random();

            for (int i = 0; i < numOperations; i++) {
                if (isAddThread) {
                    int randomElement = random.nextInt(10000) + 1;
                    list.add(randomElement);
                } else {
                    int randomElement = random.nextInt(10000) + 1;
                    list.remove(randomElement);
                }
            }
        }
    }
}
