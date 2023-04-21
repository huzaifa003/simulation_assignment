import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;

public class Runway {
    public final int MIN_FLIGHT_TIME = 10;
    Queue<Flight> arrivingFlights;
    Queue<Flight> departingFlights;

    Queue<Flight> divertedFlights;

    Queue<Flight> departedFlights;
    Queue<Flight> delayedFlights;
    Queue<Flight> arrivedFlights;

    public boolean runwayTakenFlag;

    public Runway() {
        arrivingFlights = new ArrayDeque<>();
        departingFlights = new ArrayDeque<>();
        divertedFlights = new ArrayDeque<>();
        delayedFlights = new ArrayDeque<>();
        departedFlights = new ArrayDeque<>();
        arrivedFlights = new ArrayDeque<>();
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

    public int handleDeparture(int time) {

        Flight flight;
        if (arrivingFlights.size() == 0 && !runwayTakenFlag) {
            if (departingFlights.size() != 0) {
                runwayTakenFlag = true;

                flight = departingFlights.remove();
                flight.setFlightStatusDeparted();
                flight.minOutQueue = time;
                this.departedFlights.add(flight);
//                System.out.println("Flight " + flight.flight_id + "  Departed at " + flight.minOutQueue + "After Waiting : " + flight.getWaitTime());
                runwayTakenFlag = false;
                return MIN_FLIGHT_TIME;
            }
        }

        return 0;

    }

    public int handleArrival(int time){
        Flight flight;
        if(!runwayTakenFlag){
            if(arrivingFlights.size() != 0){
                runwayTakenFlag = true;
                flight = arrivingFlights.remove();
                flight.setFlightStatusLanded();
                flight.minOutQueue = time;
                this.arrivedFlights.add(flight);
//                System.out.println("Flight " + flight.flight_id + "  Arrived on " + flight.minOutQueue + "After Waiting : " + flight.getWaitTime());
                runwayTakenFlag = false;
                return MIN_FLIGHT_TIME;
            }
        }
        return 0;
    }

    public void addDeparture(int count, int time) {
        if (count == 0) {
            System.out.println("At time " + time / 60 + " : " + time%60 + " NO new flights were scheduled for departure");
            return;
        }
        System.out.println(count + " No of flights Generated FOR DEPARTURE in TIME:  " + time);
        for (int i = 0; i < count; i++) {
            Flight flight = new Flight(time);
            flight.setFlightTypeDeparture();
            if (departingFlights.size() > 5) {
                System.out.println("DEPARTING FLIGHTS CAN't BE PROCESSED DUE TO LOW SPACE");
                flight.setFlightStatusDelayed();
                delayedFlights.add(flight);
                return;
            }

            departingFlights.add(flight);
        }
    }

    public void addArrival(int count, int time) {
        if (count == 0) {
            System.out.println("At time " + time / 60 + " : " + time%60 + " NO new flights were scheduled for Arrival");
            return;
        }
        System.out.println(count + " No of flights Generated FOR ARRIVAL in TIME:  " + time);
        for (int i = 0; i < count; i++) {
            Flight flight = new Flight(time);
            flight.setFlightTypeArrival();
            if (arrivingFlights.size() > 5) {
                System.out.println("ARRIVNG FLIGHTS CAN't BE PROCESSED DUE TO LOW SPACE");
                flight.setFlightStatusDiverted();
                divertedFlights.add(flight);
                return;
            }

            arrivingFlights.add(flight);
        }
    }

    public void printDepartedFlights() {
        System.out.println("DEPARTED FLIGHTS");
        int size = this.departedFlights.size();
        if(size == 0){
            return;
        }
        int AvgWaitTIme = 0;
        for (int i = 0; i < this.departedFlights.size(); i++) {
            Flight flight = this.departedFlights.remove();
            System.out.println(flight.flight_id + "DEPARTED AT TIME: " + flight.getWaitTime() / 60 + " : " + String.format("%02d",flight.getWaitTime()%60));
            AvgWaitTIme+= flight.getWaitTime();
        }
        System.out.println("AVERAGE WAITING TIME FOR DEPARTURE = " +  (AvgWaitTIme/ size) / 60 + " : " + String.format("%02d",((AvgWaitTIme/size) % 60)));
    }

    public void printDelayedFlights() {
        System.out.println("DELAYED FLIGHTS");
        for (int i = 0; i < this.delayedFlights.size(); i++) {
            Flight flight = this.delayedFlights.remove();
            System.out.println(flight.flight_id + " DELAYED AT " + flight.minInQueue / 60 + " : " + flight.minInQueue % 60);
        }
    }

    public void printArrivedFlights() {
        int avgWaitTime =  0;
        int size = this.arrivedFlights.size();
        if (size==0){
            return;
        }
        System.out.println("Arrived FLIGHTS");
        for (int i = 0; i < this.arrivedFlights.size(); i++) {
            Flight flight = this.arrivedFlights.remove();
            System.out.println(flight.flight_id + " ARRIVED AT Time: " + flight.getWaitTime() / 60 + " : "+ String.format("%02d ", flight.getWaitTime()%60));
            avgWaitTime+= flight.getWaitTime();
        }
        System.out.println("AVERAGE WAITING TIME FOR DEPARTURE = " +  (avgWaitTime/ size) / 60 + " : " + String.format("%02d",((avgWaitTime/size) % 60)));
    }

    public void printDivertedFlights(){
        System.out.println("DIVERTED FLIGHTS");
        for (int i = 0; i < this.divertedFlights.size(); i++) {
            Flight flight = this.divertedFlights.remove();
            System.out.println(flight.flight_id + " DIVERTED AT " + flight.minInQueue / 60 + " : " + String.format("%02d",flight.minInQueue %60));

        }
    }

    public void printSizes(){
        System.out.println("No of DIVERTED FLIGHTS = " + this.divertedFlights.size());
        System.out.println("No of CANCELLED FLIGHTS = " + this.delayedFlights.size());
        System.out.println("No of departed FLIGHTS = " + this.departedFlights.size());
        System.out.println("No of arrived FLIGHTS = " + this.arrivedFlights.size());
    }


}
