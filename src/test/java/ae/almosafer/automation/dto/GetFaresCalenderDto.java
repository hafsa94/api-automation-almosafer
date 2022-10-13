package ae.almosafer.automation.dto;

import lombok.Data;

import java.util.List;

@Data
public class GetFaresCalenderDto {
    private List<LegDto> leg;
    private String cabin;
    private PaxDto pax;
    private List<Integer> stops;
    private List<String> airline;
}
