package ae.almosafer.automation.apispecs;

import ae.almosafer.automation.dto.GetFaresCalenderDto;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class GetFaresCalenderSpecs extends BaseAPISpecs {

    public RequestSpecification requestSpec;

    public String endpoint = "/get-fares-calender";

    public GetFaresCalenderSpecs() {
        requestSpec = getCommonReqSpec();
    }

    public Response getFaresCalenderWithDtoRequest(GetFaresCalenderDto payload) {
        return given(requestSpec).log().all()
                .body(payload)
                .when().log().all()
                .post(endpoint)
                .then().log().all()
                .extract()
                .response();
    }
}
