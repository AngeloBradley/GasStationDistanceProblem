/*
 *  Copyright Angelo Bradley 2018
 */
package GasStationDistanceProblem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainDriver {

    public static void main(String[] args) throws FileNotFoundException {

        File myFile = new File("inputData.txt");
        Scanner myData = new Scanner(myFile);

        final int maxDistanceOnFullTank = myData.nextInt(); //the maximum number of miles the car can travel on a full tank of gas
        final int distanceToFinalDest = myData.nextInt(); //total length of the trip in miles
        ArrayList<Integer> gasStationDistances = new ArrayList<Integer>(); //a list of the distances of each gas station along the route from the starting position (mile 0)

        while (myData.hasNext()) {
            gasStationDistances.add(myData.nextInt());
        }

        /**
         * ******************************************************************************************************
         * The ACTUAL PARAMETERS maxDistanceOnFullTank and gasStationDistances are passed to the method 
         * gasStation(int,ArrayList). The variable maxDistanceOnFullTank is PASSED BY VALUE while the variable 
         * (the ArrayList) gasStationDistances is PASSED BY REFERENCE -->what is actually passed here is the memory 
         * address of the first element of the ArrayList. 
         * ******************************************************************************************************
         */
        System.out.println(RouteAnalysis(maxDistanceOnFullTank, distanceToFinalDest, gasStationDistances));
    }

    /**
     * *************************************************************************************
     * The formal parameters listed here in function definition are maxDistance and gasStations
     *
     * @param maxDistance
     * @param finalDestination
     * @param gasStations
     * @return 
     * **************************************************************************************
     */
    private static int RouteAnalysis(int maxDistance, int finalDestination, ArrayList<Integer> gasStations) {

        int numStops = 0;
        int max = maxDistance;

        /*
        If the distance between the last gas station and the destination is greater than the maximum
        distance that the car can travel, the trip is considered to be impossible along this route and/or
        in this car. 
         */
        if (finalDestination - gasStations.get(gasStations.size() - 1) > maxDistance) {
            return -1;
        }

        /*
        This process checks to see if there are any consecutive gas stations that are separated by a distance
        greater than the maximum distance that the car can travel on a single fill-up. If such a gap
        is found, the trip is considered to be impossible. 
         */
        int x = 0;
        while (x + 1 < gasStations.size()) {
            if (gasStations.get(x + 1) - gasStations.get(x) > max) {
                return -1;
            }
            x++;
        }

        /*
        By this point, the trip is assumed to be possible. With this process the car will stop at the gas station
        before the gas station whose distance from the car's current location is greater than the car can travel
        on a single fill-up. 
         */
        for (int i = 0; i < gasStations.size(); i++) {

            if (gasStations.get(i) > maxDistance) {
                /*
                Since all of the input data is relative to the car's starting position, the maximum distance
                that the car can travel is updated by adding to it the number of miles that the car has already
                traveled. For instance, if the car travels from mile 0 to a gas station located at mile 290, and
                the maximum distance that the car can travel is 400 miles, then from mile 290 the car can travel
                to mile 690. 
                 */
                maxDistance += gasStations.get(i - 1);
                numStops++;
            }

        }

        /*
        This last process essentially checks to see if the distance between the last gas station where the driver stopped
        and the final destination is greater than the maximum distance the car can travel. If it is, then the driver should
        stop at the last gas station on the route to fill up before proceeding to the final destination. 
         */
        if (finalDestination - maxDistance > max) {
            maxDistance += gasStations.get(gasStations.size() - 1);
            numStops++;
        }

        return numStops;

    }
}
