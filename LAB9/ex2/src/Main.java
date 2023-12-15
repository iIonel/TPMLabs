import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int numThreads = 4;
        int numOperations = 10000;

        runExperiment("OptimisticList", numThreads, numOperations, new OptimisticList<>());
    }

    private static void runExperiment(String listType, int numThreads, int numOperations, OptimisticList<Integer> list) {
        for (int i = 1; i <= 100000; i++) {
            list.add(i);
        }

        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(new ExperimentThread(list, i < numThreads / 2, numOperations));
            threads[i].start();
        }

        long addTime = measureOperationTime(list, OperationType.ADD, numThreads, numOperations);
        long removeTime = measureOperationTime(list, OperationType.REMOVE, numThreads, numOperations);
        long containsTime = measureOperationTime(list, OperationType.CONTAINS, numThreads, numOperations);

        System.out.println(listType + " - Threads: " + numThreads + ", Operations per Thread: " + numOperations +
                ", Total Operations: " + (numThreads * numOperations) +
                "\n\tAverage Add Time: " + addTime + " ms" +
                "\n\tAverage Remove Time: " + removeTime + " ms" +
                "\n\tAverage Contains Time: " + containsTime + " ms");
    }

    private static long measureOperationTime(OptimisticList<Integer> list, OperationType operationType, int numThreads, int numOperations) {
        long totalTime = 0;

        for (int i = 0; i < numThreads; i++) {
            long startTime = System.currentTimeMillis();

            switch (operationType) {
                case ADD:
                    executeAddOperation(list, numOperations);
                    break;
                case REMOVE:
                    executeRemoveOperation(list, numOperations);
                    break;
                case CONTAINS:
                    executeContainsOperation(list, numOperations);
                    break;
            }

            long endTime = System.currentTimeMillis();
            long threadTime = endTime - startTime;
            totalTime += threadTime;
        }

        return totalTime / numThreads;
    }

    private static void executeAddOperation(OptimisticList<Integer> list, int numOperations) {
        Random random = new Random();
        for (int i = 0; i < numOperations; i++) {
            int randomElement = random.nextInt(100000) + 1;
            list.add(randomElement);
        }
    }

    private static void executeRemoveOperation(OptimisticList<Integer> list, int numOperations) {
        Random random = new Random();
        for (int i = 0; i < numOperations; i++) {
            int randomElement = random.nextInt(100000) + 1;
            list.remove(randomElement);
        }
    }

    private static void executeContainsOperation(OptimisticList<Integer> list, int numOperations) {
        Random random = new Random();
        for (int i = 0; i < numOperations; i++) {
            int randomElement = random.nextInt(100000) + 1;
            list.contains(randomElement);
        }
    }

    enum OperationType {
        ADD, REMOVE, CONTAINS
    }

    static class ExperimentThread implements Runnable {
        private final OptimisticList<Integer> list;
        private final boolean isAddThread;
        private final int numOperations;

        public ExperimentThread(OptimisticList<Integer> list, boolean isAddThread, int numOperations) {
            this.list = list;
            this.isAddThread = isAddThread;
            this.numOperations = numOperations;
        }

        @Override
        public void run() {
            if (isAddThread) {
                executeAddOperation(list, numOperations);
            } else {
                executeRemoveOperation(list, numOperations);
            }
        }
    }
}
