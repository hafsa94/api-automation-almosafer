package ae.almosafer.automation.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Parser {

    private ObjectMapper mapper = new ObjectMapper();

    public <T> T convertFromJsonNode(JsonNode jsonNode, Class<T> classType) {
        try {
            return mapper.readValue(jsonNode.traverse(), classType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T convertFromString(String body, Class<T> classType) {
        try {
            return mapper.readValue(body, classType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
