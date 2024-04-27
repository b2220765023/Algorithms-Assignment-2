
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main class
 */
// FREE CODE HERE
public class Main {
    public static void main(String[] args) throws IOException {

       /** MISSION POWER GRID OPTIMIZATION BELOW **/

        System.out.println("##MISSION POWER GRID OPTIMIZATION##");
        // TODO: Your code goes here
        // You are expected to read the file given as the first command-line argument to read 
        // the energy demands arriving per hour. Then, use this data to instantiate a 
        //  PowerGridOptimization object.You need to call GetOptimalPowerGridSolutionDP() method
        // of your PowerGridOptimization object to get the solution, and finally print it to STDOUT.

        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        String line = reader.readLine();
        String[] demandSchedule = line.split(" ");
        ArrayList<Integer> amountOfEnergyDemandsArrivingPerHour = new ArrayList<>();
        
        for(String demand : demandSchedule)
            amountOfEnergyDemandsArrivingPerHour.add(Integer.parseInt(demand));

        reader.close();
        
        PowerGridOptimization powerGridOptimization = new PowerGridOptimization(amountOfEnergyDemandsArrivingPerHour);
        OptimalPowerGridSolution optimalSolution = powerGridOptimization.getOptimalPowerGridSolutionDP();

  
        System.out.println("The total number of demanded gigawatts: " + calculateTotalDemand(amountOfEnergyDemandsArrivingPerHour));
        System.out.println("Maximum number of satisfied gigawatts: " + optimalSolution.getmaxNumberOfSatisfiedDemands());
        System.out.print("Hours at which the battery bank should be discharged: ");

        List<Integer> hours = optimalSolution.getHoursToDischargeBatteriesForMaxEfficiency();
        for (int i = 0; i < hours.size(); i++) {
            System.out.print(hours.get(i));
            if (i < hours.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
        System.out.println("The number of unsatisfied gigawatts: " + (calculateTotalDemand(amountOfEnergyDemandsArrivingPerHour) - optimalSolution.getmaxNumberOfSatisfiedDemands()));


        System.out.println("##MISSION POWER GRID OPTIMIZATION COMPLETED##");



        /** MISSION ECO-MAINTENANCE BELOW **/

        System.out.println("##MISSION ECO-MAINTENANCE##");
        String maintenanceTasksFile = args[1];

        BufferedReader secondReader = new BufferedReader(new FileReader(maintenanceTasksFile));
        line = secondReader.readLine();
        String[] tasksInfo = line.split(" ");
        int maxNumberOfAvailableESVs = Integer.parseInt(tasksInfo[0]);
        int maxESVCapacity = Integer.parseInt(tasksInfo[1]);

        line = secondReader.readLine();
        String[] taskDemands = line.split(" ");
        ArrayList<Integer> maintenanceTaskEnergyDemands = new ArrayList<>();
        for (String demand : taskDemands)
            maintenanceTaskEnergyDemands.add(Integer.parseInt(demand));
        secondReader.close();

        OptimalESVDeploymentGP optimalESVDeploymentGP = new OptimalESVDeploymentGP(maintenanceTaskEnergyDemands);
        int minNumESVsToDeploy = optimalESVDeploymentGP.getMinNumESVsToDeploy(maxNumberOfAvailableESVs, maxESVCapacity);

        if (minNumESVsToDeploy != -1) {
            System.out.println("The minimum number of ESVs to deploy: " + minNumESVsToDeploy);
            ArrayList<ArrayList<Integer>> tasksAssignedToESVs = optimalESVDeploymentGP.getMaintenanceTasksAssignedToESVs();
            for (int i = 0; i < tasksAssignedToESVs.size(); i++) {
                System.out.println("ESV " + (i + 1) + " tasks: " + tasksAssignedToESVs.get(i));
            }
        } else {
            System.out.println("Warning: Mission Eco-Maintenance Failed.");
        }

        // TODO: Your code goes here
        // You are expected to read the file given as the second command-line argument to read
        // the number of available ESVs, the capacity of each available ESV, and the energy requirements 
        // of the maintenance tasks. Then, use this data to instantiate an OptimalESVDeploymentGP object.
        // You need to call getMinNumESVsToDeploy(int maxNumberOfAvailableESVs, int maxESVCapacity) method
        // of your OptimalESVDeploymentGP object to get the solution, and finally print it to STDOUT.
        System.out.println("##MISSION ECO-MAINTENANCE COMPLETED##");
    }
    public static int calculateTotalDemand(ArrayList<Integer> demands) {
        int totalDemand = 0;
        for (int demand : demands) {
            totalDemand += demand;
        }
        return totalDemand;
    }
}
