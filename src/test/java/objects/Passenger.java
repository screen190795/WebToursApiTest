package objects;

public class Passenger {
    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Passenger(String fullName) {
        this.fullName = fullName;
    }
}
