package objects;

public class BookingDetails {
    public String getAdvanceDiscount() {
        return advanceDiscount;
    }

    public void setAdvanceDiscount(String advanceDiscount) {
        this.advanceDiscount = advanceDiscount;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getDepartDate() {
        return departDate;
    }

    public void setDepartDate(String departDate) {
        this.departDate = departDate;
    }

    public String getArrive() {
        return arrive;
    }

    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getNumPassengers() {
        return numPassengers;
    }

    public void setNumPassengers(String numPassengers) {
        this.numPassengers = numPassengers;
    }

    public String getRoundtrip() {
        return roundtrip;
    }

    public void setRoundtrip(String roundtrip) {
        this.roundtrip = roundtrip;
    }

    public String getSeatPref() {
        return seatPref;
    }

    public void setSeatPref(String seatPref) {
        this.seatPref = seatPref;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    private  String advanceDiscount;
    private String depart;
    private String departDate;
    private String arrive;
    private String returnDate;
    private String numPassengers;
    private String roundtrip;
    private String seatPref;
    private String seatType;
    private String outboundFlight;
    private String returnFlight;

    public String getOutboundFlight() {
        return outboundFlight;
    }

    public void setOutboundFlight(String outboundFlight) {
        this.outboundFlight = outboundFlight;
    }

    public String getReturnFlight() {
        return returnFlight;
    }

    public void setReturnFlight(String returnFlight) {
        this.returnFlight = returnFlight;
    }

    public BookingDetails() {
    }


    public BookingDetails(String advanceDiscount, String depart, String departDate, String arrive, String returnDate, String numPassengers, String roundtrip, String seatPref, String seatType, String outboundFlight, String returnFlight) {
        this.advanceDiscount = advanceDiscount;
        this.depart = depart;
        this.departDate = departDate;
        this.arrive = arrive;
        this.returnDate = returnDate;
        this.numPassengers = numPassengers;
        this.roundtrip = roundtrip;
        this.seatPref = seatPref;
        this.seatType = seatType;
        this.outboundFlight = outboundFlight;
        this.returnFlight = returnFlight;
    }
}
