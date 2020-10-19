import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.containsString;

public class Specifications {

    public static RequestSpecification requestSpec() {
        RequestSpecification reqSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(1080)
                .setContentType(ContentType.ANY)
                .build();
        return reqSpec;
    }

    public static RequestSpecification URLEncodedRequestSpec() {
        RequestSpecification reqSpec = new RequestSpecBuilder()
                .setContentType("application/x-www-form-urlencoded")
                .setBaseUri("http://localhost")
                .setPort(1080)
                .build();
        return reqSpec;
    }


    public static ResponseSpecification responseSpec() {
        ResponseSpecification resSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
        return resSpec;
    }
}
