import objects.BookingDetails;
import objects.CreditCard;
import objects.Passenger;
import objects.User;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.List;


public class DP
{



    @DataProvider(name = "bookingData")
    public static Object[][] bookingData() {

        return new Object[][] {
                {new BookingDetails(
                        new User("rus","screen"),
                                "0",
                                "London",
                                "10/20/2020",
                                "San Francisco",
                                "10/21/2020",
                                "3",
                                "on",
                                "Window",
                                "First",
                                "262;1039;10/20/2020",
                                "622;1039;10/21/2020")
                }};
    }



    /*
                        )*/
}
