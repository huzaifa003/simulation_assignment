enum FlightType {Arrival, Departure}

enum FlightStatus {Diverted, Delayed, Landed, Departed}

public class Flight {
    public static int flight_no = 0;
    String flight_id;

    int minInQueue;
    int minOutQueue;

    FlightStatus flightStatus;
    FlightType flightType;

    public Flight(int minInQueue) {
        this.minInQueue = minInQueue;
        setFlight_no();
    }

    public void setFlight_id() {
        this.flight_id = "UK" + Flight.flight_no;
    }

    public void setFlight_no() {
        setFlight_id();
        Flight.flight_no++;

    }

    public void setFlightTypeDeparture() {
        this.flightType = FlightType.Departure;

    }

    public void setFlightTypeArrival() {
        this.flightType = FlightType.Arrival;
    }

    public void setFlightStatusDiverted() {
        if ((flightType.name().compareTo("Arrival")) == 0) {
            this.flightStatus = FlightStatus.Diverted;
        } else {
            throw new IllegalCallerException("Departure Flights can't be diverted");
        }
    }

    public void setFlightStatusLanded() {
        if ((flightType.name().compareTo("Arrival")) == 0) {
            this.flightStatus = FlightStatus.Landed;
        } else {
            throw new IllegalCallerException("Departure Flights can't be Landed");
        }
    }

    public void setFlightStatusDeparted() {
        if ((flightType.name().compareTo("Departure")) == 0) {
            this.flightStatus = FlightStatus.Departed;
        } else {
            throw new IllegalCallerException("Arrival Flights can't be Landed");
        }
    }

    public void setFlightStatusDelayed() {
        if ((flightType.name().compareTo("Departure")) == 0) {
            this.flightStatus = FlightStatus.Delayed;
        } else {
            throw new IllegalCallerException("Arrival Flights can't be Delayed");
        }
    }

    public int getWaitTime() {
        return minOutQueue - minInQueue;
    }
}
