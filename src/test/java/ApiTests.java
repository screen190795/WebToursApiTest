
import io.restassured.filter.session.SessionFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import objects.BookingDetails;
import objects.CreditCard;
import objects.Passenger;
import objects.User;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class ApiTests {


    @Test
    public void mainTest(){
        Steps steps = new Steps();
        steps.goWebTours();
        steps.booking();
    }

}
