package ae.almosafer.automation.stepdefs;

import ae.almosafer.automation.apispecs.GetFaresCalenderSpecs;
import ae.almosafer.automation.dto.GetFaresCalenderDto;
import ae.almosafer.automation.utils.JsonFileReader;
import ae.almosafer.automation.utils.Parser;
import ae.almosafer.automation.utils.UtilCache;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.DocStringType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static ae.almosafer.automation.utils.Constant.PARAMETERS.*;

public class GetFaresCalenderStepDefs {

    private final GetFaresCalenderSpecs getFaresCalenderSpecs;
    private Response res;
    private Map<String, Map<String, String>> responseBody;
    private JsonNode responseErrorBody;

    public GetFaresCalenderStepDefs() {
        getFaresCalenderSpecs = new GetFaresCalenderSpecs();
    }

    @DocStringType(contentType = "json")
    public JsonNode json(String json) throws JsonProcessingException {
        return new ObjectMapper().readTree(json);
    }

    @Given("user has json file to call get-fares-calender POST API")
    public void userHasJsonFileToCallGetFaresCalenderPOSTAPI() {
        String request = new JsonFileReader().generateStringFromJsonFile("getFaresCalenderPayload.json");
        GetFaresCalenderDto dto = new Parser().convertFromString(request, GetFaresCalenderDto.class);
        UtilCache.put(DEPARTURE_FROM, dto.getLeg().get(0).getDepartureFrom());
        UtilCache.put(DEPARTURE_TO, dto.getLeg().get(0).getDepartureTo());
        res = getFaresCalenderSpecs.getFaresCalenderWithDtoRequest(dto);
    }

    @Given("user has data in pojo to call get-fares-calender POST API")
    public void userHasDataInPojoToCallGetFaresCalenderPOSTAPI(JsonNode jsonNode) {
        GetFaresCalenderDto dto = new Parser().convertFromJsonNode(jsonNode, GetFaresCalenderDto.class);
        UtilCache.put(DEPARTURE_FROM, dto.getLeg().get(0).getDepartureFrom());
        UtilCache.put(DEPARTURE_TO, dto.getLeg().get(0).getDepartureTo());
        res = getFaresCalenderSpecs.getFaresCalenderWithDtoRequest(dto);
    }

    @Then("verify http response code is {int}")
    public void verifyHttpResponseCodeIs(int statusCode) {
        Assert.assertEquals(statusCode, res.statusCode());
    }

    @And("verify response header has Content-Type as {string}")
    public void verifyResponseHeaderHasContentTypeAs(String contentType) {
        Assert.assertEquals(contentType, res.contentType());
    }

    @And("verify response body has dates within departureFrom and departureTo range")
    public void verifyResponseBodyHasCorrectDatesVisible() throws ParseException {
        Date departureFrom = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(UtilCache.getValue(DEPARTURE_FROM)));
        Date departureTo = new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(UtilCache.getValue(DEPARTURE_TO)));
        responseBody = res.body().as(Map.class);
        for (String key : responseBody.keySet()) {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(key);
            Assert.assertTrue(departureFrom.compareTo(date) * date.compareTo(departureTo) >= 0);
        }
    }

    @And("verify response body has date object with following fields")
    public void verifyResponseBodyHasDateObjectWithFollowingFields(List<String> fields) {
        for (Map.Entry<String, Map<String, String>> entry : responseBody.entrySet()) {
            for (String key : entry.getValue().keySet()) {
                Assert.assertTrue(fields.contains(key));
            }
        }
    }

    @And("verify error response body has following response")
    public void verifyErrorResponseBodyHasFollowingResponse(JsonNode jsonNode) throws IOException {
        responseErrorBody = res.body().as(JsonNode.class);
        Assert.assertEquals(jsonNode, responseErrorBody);
    }

    @When("user calls get-fares-calender POST API with past departureFrom and departureTo")
    public void userCallsGetFaresCalenderPOSTAPIWithUpdatedDepartureFromAndDepartureTo() {
        String request = new JsonFileReader().generateStringFromJsonFile("getFaresCalenderPayload.json");
        GetFaresCalenderDto dto = new Parser().convertFromString(request, GetFaresCalenderDto.class);
        String pastDate = LocalDate.now().minusDays(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        dto.getLeg().get(0).setDepartureFrom(pastDate);
        dto.getLeg().get(0).setDepartureTo(pastDate);
        res = getFaresCalenderSpecs.getFaresCalenderWithDtoRequest(dto);
    }

    @And("verify error response body has date {string} message as {string}")
    public void verifyErrorResponseBodyHasAs(String field, String message) {
        String todaysDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        responseErrorBody = res.body().as(JsonNode.class);
        Assert.assertEquals(String.format("%s%s.", message, todaysDate), responseErrorBody.get(DETAIL).get(field).get(0).asText());
    }
}
