package ae.almosafer.automation.stepdefs;

import ae.almosafer.automation.apispecs.RichContentSpecs;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Transpose;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.Assert;

import java.io.File;
import java.util.Map;

import static ae.almosafer.automation.utils.Constant.PARAMETERS.AIRLINE_REFUND;

public class RichContentStepDefs {

    private Response res;
    private Map<String, Object> responseBody;
    private final RichContentSpecs richContentSpecs;

    public RichContentStepDefs() {
        richContentSpecs = new RichContentSpecs();
    }

    @When("user calls rich-content GET API with following query parameter")
    public void userCallsRichContentGETAPIWithFollowingQueryParameter(@Transpose DataTable data) {
        Map<String, String> queryParams = data.asMap();
        res = richContentSpecs.richContentRequest(queryParams);
    }

    @Then("verify rich-content API http response code is {int}")
    public void verifyHttpResponseCodeIs(int statusCode) {
        Assert.assertEquals(statusCode, res.statusCode());
    }

    @And("verify rich-content API response header has Content-Type as {string}")
    public void verifyResponseHeaderHasContentTypeAs(String contentType) {
        Assert.assertEquals(contentType, res.contentType());
    }

    @And("verify rich-content API response body has correct schema {string}")
    public void verifyRichContentAPIResponseBodyHasCorrectSchema(String schemaName) {
        String schemaFile = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" +
                File.separator + "resources" + File.separator + "schema" + File.separator + schemaName + ".json";
        res.then()
                .assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File(schemaFile)));
    }

    @And("verify rich-content API response has airlines as {string}")
    public void verifyRichContentAPIResponseHasAllTheAirlines(String airlines) {
        responseBody = res.body().as(Map.class);
        Map<String, Map<String, String>> airlineRefund = (Map<String, Map<String, String>>) responseBody.get(AIRLINE_REFUND);
        for (String key : airlineRefund.keySet()) {
            Assert.assertTrue(airlines.contains(key));
        }
    }
}
