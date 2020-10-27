
import objects.*;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class ApiTests {



    @Test(dataProvider = "bookingData", dataProviderClass = DP.class)
    public void LoginAndBooking( UserLogin userLogin,BookingDetails bookingDetails, Passenger[] passengers, HiddenDetails hiddenDetails){
        Steps steps = new Steps();
        Map<String,String> cookies = steps.goWebTours(userLogin);
        UserDetails userDetails = steps.getUserData(cookies);
        steps.booking(bookingDetails, userDetails, passengers, hiddenDetails);
    }

}
