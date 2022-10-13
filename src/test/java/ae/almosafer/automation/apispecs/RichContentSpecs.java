package ae.almosafer.automation.apispecs;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class RichContentSpecs extends BaseAPISpecs {

    public RequestSpecification requestSpec;

    public String endpoint = "/rich-content";

    public RichContentSpecs() {
        requestSpec = getCommonReqSpec();
    }

    public Response richContentRequest(Map<String, String> queryParamsMap) {
        return given(requestSpec).log().all()
                .queryParams(queryParamsMap)
                .when().log().all()
                .get(endpoint)
                .then().log().all()
                .extract()
                .response();
    }
}
