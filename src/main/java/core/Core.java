package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Core {
    private static final Core instance = new Core();
    static long inCircle = 0;
    static long totalPoints = 0;
    static int numThreads = 8;

    private Core() {
    }

    public void start() {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        IntStream.range(0, numThreads)
                .forEach(i -> executor.execute(new MonteCarloTask()));
        executor.shutdown();
        while (!executor.isTerminated()) {
        }

        double pi = 4.0 * (double) inCircle / (double) totalPoints;
        System.out.println("Pi = " + pi);
    }


    class MonteCarloTask implements Runnable {
        public void run() {
            IntStream.range(0,1000000).forEach(i -> {
                double x = function(Math.random());
                double y = function(Math.random());
                if (x * x + y * y <= 1) {
                    inCircle++;
                }
                totalPoints++;
            });
        }
    }

    public double function(double x) {
        return (2*x)-1;
    }

    public static Core getInstance() {
        return instance;
    }
}
