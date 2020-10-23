
import io.restassured.filter.session.SessionFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import objects.BookingDetails;
import objects.CreditCard;
import objects.Passenger;
import objects.User;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.IDataProviderMethod;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.*;

public class ApiTests {



    @Test(dataProvider = "bookingData", dataProviderClass = DP.class)
    public void mainTest1(BookingDetails bookingDetails){
        Steps steps = new Steps();
        Map<String,String> cookies = steps.goWebTours(bookingDetails);
        steps.getUserData(bookingDetails,cookies);
        steps.booking(bookingDetails);
    }

}
