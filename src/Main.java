public class Main {
    public static void main(String[] args) {

        Runway runway = new Runway();
        for (int i = 0; i < 60; i++) {
            int departureCount = runway.getPoissonRandom(0.45);
            runway.addDeparture(departureCount,i);
            int runwayTakenTime = runway.handleDeparture(i);
            for (int j = 0; j < runwayTakenTime; j++) {
                departureCount = runway.getPoissonRandom(0.45);
                runway.addDeparture(departureCount,i+j + 1);

            }
            i+= runwayTakenTime;
        }

        runway.printDepartedFlights();
    }
}