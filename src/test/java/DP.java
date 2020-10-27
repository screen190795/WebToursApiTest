import objects.*;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;


public class DP {


    @DataProvider(name = "bookingData")
    public static Object[][] bookingData() {

        return new Object[][]{
                {       new UserLogin("rus1","screen1"),
                        new BookingDetails(
                        "50",
                        "London",
                        "10/20/2020",
                        "San Francisco",
                        "10/21/2020",
                        "3",
                        "on",
                        "Window",
                        "First",
                        "262;1039;10/20/2020",
                        "622;1039;10/21/2020"),
                        new Passenger[]
                                {new Passenger("Sara Turner")
                                        ,new Passenger("Tom Cruise")},
                        new HiddenDetails(
                                "51",
                                "10",
                                new String[] {"roundtrip", "seatType","seatPref","saveCC"},
                                "47",
                                "11",
                                "off",
                                "46",
                                "10")
                }
        };
    }
}
