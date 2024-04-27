
import java.util.ArrayList;

/**
 * This class accomplishes Mission POWER GRID OPTIMIZATION
 */
public class PowerGridOptimization {
    private ArrayList<Integer> amountOfEnergyDemandsArrivingPerHour;

    public PowerGridOptimization(ArrayList<Integer> amountOfEnergyDemandsArrivingPerHour){
        this.amountOfEnergyDemandsArrivingPerHour = amountOfEnergyDemandsArrivingPerHour;
    }

    public ArrayList<Integer> getAmountOfEnergyDemandsArrivingPerHour() {
        return amountOfEnergyDemandsArrivingPerHour;
    }

    /**
     *     Function to implement the given dynamic programming algorithm
     *     SOL(0) <- 0
     *     HOURS(0) <- [ ]
     *     For{j <- 1...N}
     *         SOL(j) <- max_{0<=i<j} [ (SOL(i) + min[ E(j), P(j − i) ] ]
     *         HOURS(j) <- [HOURS(i), j]
     *     EndFor
     *
     * @return OptimalPowerGridSolution
     */
    public OptimalPowerGridSolution getOptimalPowerGridSolutionDP(){
        int N = amountOfEnergyDemandsArrivingPerHour.size();
        int[] SOL = new int[N + 1];
        ArrayList<ArrayList<Integer>> HOURS = new ArrayList<>(N + 1);
        SOL[0] = 0;
        HOURS.add(new ArrayList<>());

        for (int j = 1; j <= N; j++) {
            int maxSatisfied = 0;
            ArrayList<Integer> bestHours = new ArrayList<>();
            for (int i = 0; i < j; i++) {
                int satisfied = SOL[i] + Math.min(amountOfEnergyDemandsArrivingPerHour.get(j - 1), ((j - i) * (j - i)));

                if (satisfied > maxSatisfied) {
                    maxSatisfied = satisfied;
                    bestHours = new ArrayList<>(HOURS.get(i));
                    bestHours.add(j);
                }
            }
            SOL[j] = maxSatisfied;
            HOURS.add(bestHours);
        }

        return new OptimalPowerGridSolution(SOL[N], HOURS.get(N));
    }

}
