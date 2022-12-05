import java.util.*;
import java.text.*;

public class DiceGameSimulation {
    public static long NUM_OF_ITERATIONS = 10000;
    public static int STARTING_DICE_COUNT = 5;
    public static int MIN_ROLL = 1;
    public static int MAX_ROLL = 6;
    
    public static void main(String args[]) {
        Map<Integer, Long> occurencesByScore = new TreeMap<>();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < NUM_OF_ITERATIONS; i ++) {
          playGame(occurencesByScore);
        }
        long endTime = System.currentTimeMillis();
        /*
        Number of simulations was 100 using 2 dice.
        Total 0 occurs 0.1 occurred 10.0 times.
        Total 1 occurs 0.05 occurred 5.0 times.
        Total 2 occurs 0.14 occurred 14.0 times.
        Total 3 occurs 0.05 occurred 5.0 times.
        Total 4 occurs 0.07 occurred 7.0 times.
        Total 5 occurs 0.13 occurred 13.0 times.
        Total 6 occurs 0.15 occurred 15.0 times.
        Total 7 occurs 0.1 occurred 10.0 times.
        Total 8 occurs 0.1 occurred 10.0 times.
        Total 9 occurs 0.02 occurred 2.0 times.
        Total 10 occurs 0.05 occurred 5.0 times.
        Total 11 occurs 0.03 occurred 3.0 times.
        Total 12 occurs 0.01 occurred 1.0 times.
        Total simulation took 0.1 seconds.
        */
        System.out.println("Number of simulations was " + NUM_OF_ITERATIONS + " using " + STARTING_DICE_COUNT + " dice");
        DecimalFormat df = new DecimalFormat("#.#");
        occurencesByScore
            .entrySet()
            .forEach(entry -> System.out.println("Total " + entry.getKey()
                    + " occurs " + df.format(entry.getValue() / (double) NUM_OF_ITERATIONS)
                    + " occurred " + entry.getValue() + " times."));
        System.out.println("Total simulation took " + df.format((endTime / 1000.0) - (startTime / 1000.0)) + " seconds.");
    }
    
    public static void playGame(Map<Integer, Long> occurencesByScore) {
        List<Integer> dieList = new ArrayList<>(STARTING_DICE_COUNT);
        Random random = new Random();
        for (int i = 0; i < STARTING_DICE_COUNT; i ++) {
            dieList.add(generateRandRoll(random));
        }
        int score = 0;
        while (dieList.size() > 0) {
            rollDice(dieList);
            score += modifyListAndGetScore(dieList);
        }
        occurencesByScore.compute(score, (k, v) -> {
            return v == null ? 1 : v + 1;
        });
    }
    
    public static void rollDice(List<Integer> dieList) {
        Random random = new Random();
        for (int i = 0; i < dieList.size(); i ++) {
            dieList.set(i, generateRandRoll(random));
        }
    }
    
    public static int generateRandRoll(Random random) {
        return random.nextInt(MAX_ROLL) + MIN_ROLL;
    }
    
    public static int modifyListAndGetScore(List<Integer> rollResults) {
        Integer lowest = MAX_ROLL + 1;
        for (Integer roll : rollResults) {
            if (roll == 3) { // remove all 3's on first occurence
                rollResults.removeIf(_roll -> _roll == 3);
                return 0;
            }
            if (roll < lowest) lowest = roll;
        }
        rollResults.remove(lowest);
        return lowest;
    }
}