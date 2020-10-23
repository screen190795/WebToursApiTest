
import io.qameta.allure.Step;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import objects.BookingDetails;
import objects.CreditCard;
import objects.User;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.Assert;

import javax.net.ssl.StandardConstants;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
public class Steps {
    static Map <String,String>cookies;
    static  CreditCard creditCard = new CreditCard();




    @Step("Переход на сайт, авторизация")
    public  Map<String,String> goWebTours(BookingDetails bookingDetails) {

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
        Assert.assertFalse(sessionID.isEmpty());

    Response loginPage = given()
            .spec(Specifications.URLEncodedRequestSpec())
            .param("userSession", sessionID)
            .param("username",bookingDetails.user.username)
            .param("password", bookingDetails.user.password)
            .when()
            .post(EndPoints.submitLogin)
            .then()
            .spec(Specifications.responseSpec())
            .extract().response();
    Document loginResponse = Jsoup.parse(loginPage.asString());
    cookies = loginPage.getCookies();
        Assert.assertFalse(cookies.isEmpty());


    given()
            .cookies(cookies)
            .when()
            .spec(Specifications.requestSpec())
            .get(EndPoints.afterLogin)
            .then()
            .spec(Specifications.responseSpec());

    return cookies;

}


@Step("Получение данных о пользователе")
        public User getUserData(BookingDetails bookingDetails,Map<String,String> cookies) {
    List<String> cookieList = new ArrayList<>(cookies.values());
    StringBuilder builder = new StringBuilder();
    for(String cookie: cookieList) builder.append(cookie);
    cookieList= Arrays.asList(builder.toString().split("&"));
   cookieList = cookieList.stream()
          .map(x->x.replaceAll("(%0A)",""))
           .collect(Collectors.toList());
    Map<String,String> userData = new HashMap<>();
    for (int i = 0; i < cookieList.size(); i+=2) {
        userData.put(cookieList.get(i), cookieList.get(i + 1));
    }
    userData.forEach((key, value) -> System.out.println(key + " = " + value));
    for(Map.Entry<String, String> map : userData.entrySet()){

        switch(map.getKey()) {
            case "firstName":
                bookingDetails.user.setFirstName(map.getValue());
                break;
            case "lastName":
                bookingDetails.user.setLastName(map.getValue());
                break;
            case "address1":
                bookingDetails.user.setAddress1(map.getValue());
                break;
            case "address2":
                bookingDetails.user.setAddress2(map.getValue());
                break;
            case "expDate":
                creditCard.setExpDate(map.getValue());
                break;
            case "creditCard":
                creditCard.setCreditCard(map.getValue());
                break;
            case "username":
                bookingDetails.user.setUsername(map.getValue());
                break;
            default:
                break;
        }
    }
    creditCard.setExpDate(URLDecoder.decode(creditCard.getExpDate(), StandardCharsets.UTF_8));
    bookingDetails.user.setCreditCard(creditCard);
    Assert.assertFalse(bookingDetails.user.address1.isEmpty()|
            bookingDetails.user.address2.isEmpty()
            |bookingDetails.user.address1.isEmpty()
            |bookingDetails.user.firstName.isEmpty()
            |bookingDetails.user.lastName.isEmpty());
return bookingDetails.user;

}

    @Step("Оформление заказа")
        public void booking(BookingDetails bookingDetails) {

    given()
            .cookies(cookies)
            .spec(Specifications.requestSpec())
            .when()
            .get(EndPoints.bookingStep1)
            .then()
            .spec(Specifications.responseSpec());


    Response findResponse = given()
            .cookies(cookies)
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
                    .cookies(cookies)
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
                    .cookies(cookies)
                    .spec(Specifications.URLEncodedRequestSpec())
                    .param("firstName",bookingDetails.user.getFirstName())
                    .param("lastName",bookingDetails.user.getLastName())
                    .param("address1", bookingDetails.user.getAddress1())
                    .param("address2", bookingDetails.user.getAddress2())
                    .param("pass1", bookingDetails.user.getFirstName() + " " + bookingDetails.user.getLastName())
                    .param("pass2", "Helena Smith")
                    .param("pass3", "John Carter")
                    .param("creditCard", bookingDetails.user.getCreditCard().getCreditCard())
                    .param("expDate", bookingDetails.user.getCreditCard().getExpDate())
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
    //System.out.println(thirdResPage);

        Response itinerary=
                given()
                .spec(Specifications.requestSpec())
                .cookies(cookies)
                .when()
                .get("http://localhost:1080/cgi-bin/itinerary.pl")
                .then()
                .log().body().extract().response();
}

}


