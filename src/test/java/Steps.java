import io.qameta.allure.Step;
import io.restassured.response.Response;
import objects.BookingDetails;
import objects.CreditCard;
import objects.Passenger;
import objects.User;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Steps {


@Test

    public void goWebTours() {
    User user = new User("rus", "screen");
    user.setFirstName("Ruslan");
    user.setLastName("Borodin");
    user.setAddress1("Begovaya");
    user.setAddress2("Moscow");
    Passenger pass1 = new Passenger(user.getFirstName() + " " + user.getLastName());
    Passenger pass2 = new Passenger("Jane Hannah");
    Passenger pass3 = new Passenger("Will Carter");
    CreditCard creditCard = new CreditCard("123456789012", "12/20");




    given()
            .spec(Specifications.URLEncodedRequestSpec())
            .when()
            .get(EndPoints.mainPage)
            .then()
            .spec(Specifications.responseSpec());


    given()
            .spec(Specifications.requestSpec())
            .when()
            .get(EndPoints.loginStep1)
            .then()
            .spec(Specifications.responseSpec());


    Response response = given()
            .spec(Specifications.requestSpec())
            .when()
            .get(EndPoints.loginStep2)
            .then()
            .spec(Specifications.responseSpec())
            .extract().response();

    Document resultPage = Jsoup.parse(response.asString());
    String sessionID = resultPage.body().getElementsByAttributeValue("name", "userSession").attr("value");
    user.setUserSession(sessionID);

    Response loginPage = given()
            .spec(Specifications.URLEncodedRequestSpec())
            .param("userSession", sessionID)
            .param("username", user.getUsername())
            .param("password", user.getPassword())
            .when()
            .post(EndPoints.submitLogin)
            .then()
            .spec(Specifications.responseSpec())
            .extract().response();
    Document loginResponse = Jsoup.parse(loginPage.asString());
    //System.out.println(loginResponse);
    //String cookies = loginPage.getCookies().toString();

    given()
            .when()
            .spec(Specifications.requestSpec())
            .get(EndPoints.afterLogin)
            .then()
            .spec(Specifications.responseSpec());


    BookingDetails bookingDetails = new BookingDetails(
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
            "622;1039;10/21/2020"
    );
    given()
            .spec(Specifications.requestSpec())
            .when()
            .get(EndPoints.bookingStep1)
            .then()
            .spec(Specifications.responseSpec());


    Response findResponse = given()
            .spec(Specifications.URLEncodedRequestSpec())
            .param("advanceDiscount", bookingDetails.getAdvanceDiscount())
            .param("depart", bookingDetails.getDepart())
            .param("departDate", bookingDetails.getDepartDate())
            .param("arrive", bookingDetails.getArrive())
            .param("returnDate", bookingDetails.getReturnDate())
            .param("numPassengers", bookingDetails.getNumPassengers())
            .param("roundtrip", bookingDetails.getRoundtrip())
            .param("seatPref", bookingDetails.getSeatPref())
            .param("seatType", bookingDetails.getSeatType())
            .param("findFlights.x", "51")
            .param("findFlights.y", "10")
            .param(".cgifields", "roundtrip")
            .param(".cgifields", "seatType")
            .param(".cgifields", "seatPref")
            .when()
            .post(EndPoints.bookingStep2)
            .then()
            .spec(Specifications.responseSpec())
            .extract().response();
    //Document findResponsePage = Jsoup.parse(findResponse.asString());
    //System.out.println(findResponsePage);

    Response secondRes =
            given()
                    .spec(Specifications.URLEncodedRequestSpec())
                    .param("outboundFlight", bookingDetails.getOutboundFlight())
                    .param("returnFlight", bookingDetails.getReturnFlight())
                    .param("numPassengers", bookingDetails.getNumPassengers())
                    .param("advanceDiscount", bookingDetails.getAdvanceDiscount())
                    .param("seatType", bookingDetails.getSeatType())
                    .param("seatPref", bookingDetails.getSeatPref())
                    .param("reserveFlights.x", "47")
                    .param("reserveFlights.y", "11")
                    .when()
                    .post(EndPoints.bookingStep2)
                    .then()
                    .spec(Specifications.responseSpec())
                    .extract().response();
    //Document secondResPage = Jsoup.parse(secondRes.asString());
    //System.out.println(secondResPage);

    Response thirdRes =
            given()
                    .spec(Specifications.URLEncodedRequestSpec())
                    .param("firstName", user.getFirstName())
                    .param("lastName", user.getLastName())
                    .param("address1", user.getAddress1())
                    .param("address2", user.getAddress2())
                    .param("pass1", pass1.getFullName())
                    .param("pass2", pass2.getFullName())
                    .param("pass3", pass3.getFullName())
                    .param("creditCard", creditCard.getCreditCard())
                    .param("expDate", creditCard.getExpDate())
                    .param("saveCC", "on")
                    .param("numPassengers", bookingDetails.getNumPassengers())
                    .param("seatType", bookingDetails.getSeatType())
                    .param("seatPref", bookingDetails.getSeatPref())
                    .param("outboundFlight", bookingDetails.getOutboundFlight())
                    .param("advanceDiscount", bookingDetails.getAdvanceDiscount())
                    .param("returnFlight", bookingDetails.getReturnFlight())
                    .param("JSFormSubmit", "off")
                    .param("buyFlights.x", "46")
                    .param("buyFlights.y", "10")
                    .param(".cgifields", "saveCC")
                    .when()
                    .post(EndPoints.bookingStep2)
                    .then()
                    .spec(Specifications.responseSpec())
                    .extract().response();
    Document thirdResPage = Jsoup.parse(thirdRes.asString());
    System.out.println(thirdResPage);
}
    }

