
import io.qameta.allure.Step;
import io.restassured.response.Response;
import objects.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Collector;
import org.testng.Assert;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
public class Steps {
    static Map <String,String> cookies;

public String getCookie(Map<String,String>cookies, String key){
    List<String> cookie = cookies.entrySet()
            .stream()
            .filter(x->x.getKey().equals(key))
            .map(Map.Entry::getValue)
            .collect(Collectors.toList());
    return cookie.get(0);
}


    @Step("Переход на сайт, авторизация")
    public  Map<String,String> goWebTours(UserLogin userLogin) {

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

    Assert.assertFalse(sessionID.isEmpty(),"Данные сессии не получены");

    //Ввод логина и пароля пользователя
    Response loginPage = given()
            .spec(Specifications.URLEncodedRequestSpec())
            .param("userSession", sessionID)
            .param("username",userLogin.username)
            .param("password", userLogin.password)
            .when()
            .post(EndPoints.submitLogin)
            .then()
            .spec(Specifications.responseSpec())
            .extract().response();
    cookies = loginPage.getCookies();
        Assert.assertFalse(cookies.isEmpty());

    return cookies;

}


@Step("Получение данных о пользователе")
        public UserDetails getUserData(Map<String,String> cookies) {
        UserDetails userDetails=null;
    List<String> cookieList = new ArrayList<>(cookies.values());

    StringBuilder builder = new StringBuilder();
    for(String cookie: cookieList) builder.append(cookie);

    cookieList= Arrays.stream(builder.toString()
            .split("&"))
            .map(x->x.replaceAll("(%0A)",""))
            .collect(Collectors.toList());

    Map<String,String> userData = new HashMap<>();
    for (int i = 0; i < cookieList.size(); i+=2) {
        userData.put(cookieList.get(i), cookieList.get(i + 1));
    }


try{
    userDetails = new UserDetails(
            this.getCookie(userData,"firstName"),
            this.getCookie(userData,"lastName"),
            this.getCookie(userData,"address1"),
            this.getCookie(userData,"address2"),
            this.getCookie(userData,"creditCard"),
            this.getCookie(userData,"expDate"));
   }catch(NullPointerException e){
    Assert.fail("Данные введены неверно");
   }
        userDetails.setExpDate(URLDecoder.decode(userDetails.getExpDate(), StandardCharsets.UTF_8));
    System.out.println(userDetails);
return userDetails;
}


    @Step("Оформление заказа")
        public void booking(BookingDetails bookingDetails, UserDetails userDetails, Passenger[] passengers, HiddenDetails hiddenDetails) {

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
            .param("findFlights.x", hiddenDetails.getBuyFlightsX())
            .param("findFlights.y", hiddenDetails.getBuyFlightsY())
            .param(".cgifields", hiddenDetails.getCgifields()[0])
            .param(".cgifields", hiddenDetails.getCgifields()[1])
            .param(".cgifields", hiddenDetails.getCgifields()[2])
            .when()
            .post(EndPoints.bookingStep2)
            .then()
            .spec(Specifications.responseSpec())
            .extract().response();


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
                    .param("reserveFlights.x", hiddenDetails.getReserveFlightsX())
                    .param("reserveFlights.y", hiddenDetails.getReserveFlightsY())
                    .when()
                    .post(EndPoints.bookingStep2)
                    .then()
                    .spec(Specifications.responseSpec())
                    .extract().response();


    Response thirdRes =
            given()
                    .cookies(cookies)
                    .spec(Specifications.URLEncodedRequestSpec())
                    .param("firstName",userDetails.getFirstName())
                    .param("lastName",userDetails.getLastName())
                    .param("address1", userDetails.getAddress1())
                    .param("address2", userDetails.getAddress2())
                    .param("pass1", userDetails.getFirstName() + " " + userDetails.getLastName())
                    .param("pass2",passengers[0].getFullName())
                    .param("pass3", passengers[1].getFullName())
                    .param("creditCard", userDetails.getCreditCard())
                    .param("expDate",userDetails.getExpDate())
                    .param("saveCC", "off")
                    .param("numPassengers", bookingDetails.getNumPassengers())
                    .param("seatType", bookingDetails.getSeatType())
                    .param("seatPref", bookingDetails.getSeatPref())
                    .param("outboundFlight", bookingDetails.getOutboundFlight())
                    .param("advanceDiscount", bookingDetails.getAdvanceDiscount())
                    .param("returnFlight", bookingDetails.getReturnFlight())
                    .param("JSFormSubmit", hiddenDetails.getJSFormSubmit())
                    .param("buyFlights.x", hiddenDetails.getBuyFlightsX())
                    .param("buyFlights.y", hiddenDetails.getBuyFlightsY())
                    .param(".cgifields", hiddenDetails.getCgifields()[3])
                    .when()
                    .post(EndPoints.bookingStep2)
                    .then()
                    .spec(Specifications.responseSpec())
                    .extract().response();
    Document thirdResPage = Jsoup.parse(thirdRes.asString());

        Response itinerary=
                given()
                .spec(Specifications.requestSpec())
                .cookies(cookies)
                .when()
                .get(EndPoints.itinerary)
                .then()
                //.log().body()
        .extract().response();
}

}


