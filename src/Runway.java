import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

public class Runway {
    public final int MIN_FLIGHT_TIME = 10;
    Queue<Flight> arrivingFlights;
    Queue<Flight> departingFlights;

    Queue<Flight> rejectedFlights;

    Queue<Flight> departedFlights;
    Queue<Flight> delayedFlights;
    public static int arrivedFlights;

    public boolean runwayTakenFlag;

    public Runway() {
        arrivingFlights = new ArrayDeque<>();
        departingFlights = new ArrayDeque<>();
        rejectedFlights = new ArrayDeque<>();
        delayedFlights = new ArrayDeque<>();
        departedFlights = new ArrayDeque<>();
    }

    Random r = new Random(System.nanoTime());

    public int getPoissonRandom(double mean) {
        double L = Math.exp(-mean);
        int x = 0;
        double p = 1.0;
        do {
            p = p * r.nextDouble();
            x++;
        } while (p > L);
        return x - 1;
    }

    public int handleDeparture(int time){
        Flight flight;
        if (arrivingFlights.size() == 0 && !runwayTakenFlag){
            if (departingFlights.size() != 0){
                runwayTakenFlag = true;

                flight = departingFlights.remove();
                flight.setFlightStatusDeparted();
                flight.minOutQueue = time;
                this.departedFlights.add(flight);
                System.out.println("Flight "+ flight.flight_id +"  Departed at " + flight.minInQueue + "After Waiting : " + flight.getWaitTime());
                runwayTakenFlag =false;
                return MIN_FLIGHT_TIME;
            }
        }

        return 0;

    }

    public void addDeparture(int count, int time){
        if (count == 0)
        {
            System.out.println("At time " + time + " NO new flights were scheduled for departure");
            return;
        }





        System.out.println(count + " No of flights Generated in TIME:  " + time);
        for (int i = 0; i < count; i++) {
            Flight flight = new Flight(time);
            flight.setFlightTypeDeparture();
            if (departingFlights.size() > 5){
                System.out.println("DEPARTING FLIGHTS CAN't BE PROCESSED DUE TO LOW SPACE");
                flight.setFlightStatusDelayed();
                delayedFlights.add(flight);
                return;
            }

            departingFlights.add(flight);
        }
    }

    public void printDepartedFlights(){
        for (int i = 0; i < this.departedFlights.size(); i++) {
            Flight flight = this.departedFlights.remove();
            System.out.println(flight.flight_id + "DEPARTED at " + flight.minOutQueue + "AT Time: " +  flight.getWaitTime());
        }
    }

}
