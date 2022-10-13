package ae.almosafer.automation.dto;

import lombok.Data;

@Data
public class LegDto {
    private String originId;
    private String destinationId;
    private String departureFrom;
    private String departureTo;
}
