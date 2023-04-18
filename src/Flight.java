enum FlightType {Arrival, Departure}
enum FlightStatus{Diverted, Landed, Departed}
public class Flight {
    public static int flight_no = 0;
    String flight_id;

    int minInQueue;
    int minOutQueue;

    FlightStatus flightStatus;
    FlightType flightType;
    public void setFlight_id() {
        this.flight_id = "UK" + Flight.flight_no;
    }

    public void setFlight_no() {
        Flight.flight_no++;
    }

    public void setFlightTypeDeparture() {
        this.flightType = FlightType.Departure;

    }

    public void setFlightTypeArrival(){
        this.flightType = FlightType.Arrival;
    }

    public void setFlightStatusRejected(){
        if ((flightType.name().compareTo("Arrival")) == 0){
            this.flightStatus = FlightStatus.Diverted;
        }
        else {
            throw  new IllegalCallerException("Departure Flights can't be diverted");
        }
    }

    public void setFlightStatusLanded(){
        if ((flightType.name().compareTo("Arrival")) == 0){
            this.flightStatus = FlightStatus.Landed;
        }
        else {
            throw  new IllegalCallerException("Departure Flights can't be Landed");
        }
    }
    public void setFlightStatusDeparted(){
        if ((flightType.name().compareTo("Departure")) == 0){
            this.flightStatus = FlightStatus.Departed;
        }
        else {
            throw  new IllegalCallerException("Arrival Flights can't be Landed");
        }
    }
}
