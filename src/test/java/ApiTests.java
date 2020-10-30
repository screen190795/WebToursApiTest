
import objects.*;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class ApiTests {



    @Test(dataProvider = "bookingData", dataProviderClass = DP.class)
    public void LoginAndBooking( UserLogin userLogin,BookingDetails bookingDetails, Passenger[] passengers, HiddenDetails hiddenDetails){
        Steps steps = new Steps();
        Map<String,String> cookies;
        List<String> flightIDs;
        UserDetails userDetails;

        cookies = steps.goWebTours(userLogin);
        userDetails = steps.getUserData(cookies);
        steps.booking(cookies, bookingDetails, userDetails, passengers, hiddenDetails);
        flightIDs = steps.checkItinerary(cookies);
        steps.cancelAll(cookies,flightIDs);
    }

}
