import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the Departure Mean");
        double departureMean = scan.nextDouble();
        System.out.println("Enter the Arrrival Mean");
        double arrivalMean = scan.nextDouble();

        if (departureMean + arrivalMean > 1.0) {
            System.out.println("Probabillities can't be above 1.0");
            return;
        }

        Runway runway = new Runway();
        for (int i = 0; i < 1440; i++) {
            int departureCount = runway.getPoissonRandom(departureMean);
            int arrivalCount = runway.getPoissonRandom(arrivalMean);
            runway.addDeparture(departureCount, i);
            runway.addArrival(arrivalCount, i);
            int runwayTakenTime = runway.handleArrival(i);
            for (int j = 0; j < runwayTakenTime; j++) {
                arrivalCount = runway.getPoissonRandom(arrivalMean);
                runway.addArrival(arrivalCount,i+j+1);

                departureCount = runway.getPoissonRandom(departureMean);
                runway.addDeparture(departureCount, i + j + 1);
            }
            i+= runwayTakenTime;
            runwayTakenTime = runway.handleDeparture(i);
            for (int j = 0; j < runwayTakenTime; j++) {
                departureCount = runway.getPoissonRandom(0.45);
                runway.addDeparture(departureCount, i + j + 1);

                arrivalCount = runway.getPoissonRandom(arrivalMean);
                runway.addArrival(arrivalCount,i+j+1);
            }
            i += runwayTakenTime;
        }

        runway.printDepartedFlights();
        runway.printDelayedFlights();
        runway.printArrivedFlights();
        runway.printDivertedFlights();
        runway.printSizes();
    }
}