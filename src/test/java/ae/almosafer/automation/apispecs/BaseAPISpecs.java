package ae.almosafer.automation.apispecs;

import ae.almosafer.automation.utils.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/**
 * Base template representing the common api data elements
 */
public class BaseAPISpecs {

    ConfigReader cr = new ConfigReader();

    /**
     * Create request specification with common headers.
     *
     * @return the request specification
     */
    public RequestSpecification getCommonReqSpec() {
        RequestSpecification req_spec = new RequestSpecBuilder().setBaseUri(cr.getBaseURI())
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON).build();
        return req_spec;
    }
}
