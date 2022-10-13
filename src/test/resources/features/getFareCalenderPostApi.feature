@smoke
Feature: get-fares-calender POST API

  Scenario: Successful get-fares-calender POST API request
    When user has json file to call get-fares-calender POST API
    Then verify http response code is 200
    And verify response header has Content-Type as "application/json"
    And verify response body has dates within departureFrom and departureTo range
    And verify response body has date object with following fields
      | price     |
      | updatedAt |

  Scenario Outline: Verify get-fares-calender POST API response if <scenario>
    When user has data in pojo to call get-fares-calender POST API
    """
      {
        "leg": [
            {
                "originId": "<originId>",
                "destinationId": "<destinationId>",
                "departureFrom": "<departureFrom>",
                "departureTo": "<departureTo>"
            }
        ],
        "cabin": "<cabin>",
        "pax": {
            <pax>
        },
        "stops": [0],
        "airline": ["SV"]
      }
    """
    Then verify http response code is 400
    And verify response header has Content-Type as "application/problem+json"
    And verify error response body has following response
    """
      {
        "status": 400,
        "title": "[Gateway:``] Bad Request",
        "detail": {
            "<errorField>": [
                <error>
            ]
        },
        "type": "https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html"
      }
    """
    Examples:
      | scenario                           | originId | destinationId | departureFrom | departureTo | cabin   | pax                                    | errorField          | error                                                      |
      | originId is missing                |          | JED           | 2022-10-23    | 2022-10-27  | Economy | "adult": 1,"child": 0,"infant": 0      | leg.0.originId      | "The leg.0.originId field is required."                    |
      | destinationId is missing           | RUH      |               | 2022-10-23    | 2022-10-27  | Economy | "adult": 1,"child": 0,"infant": 0      | leg.0.destinationId | "The leg.0.destinationId field is required."               |
      | departureTo has incorrect format   | RUH      | JED           | 2022-10-23    | 27-10-2022  | Economy | "adult": 1,"child": 0,"infant": 0      | leg.0.departureTo   | "The leg.0.departureTo does not match the format Y-m-d."   |
      | departureFrom has incorrect format | RUH      | JED           | 23-10-2022    | 2022-10-27  | Economy | "adult": 1,"child": 0,"infant": 0      | leg.0.departureFrom | "The leg.0.departureFrom does not match the format Y-m-d." |
      | cabin is missing                   | RUH      | JED           | 2022-10-23    | 2022-10-27  |         | "adult": 1,"child": 0,"infant": 0      | cabin               | "The cabin field is required."                             |
      | cabin is invalid                   | RUH      | JED           | 2022-10-23    | 2022-10-27  | XYZ     | "adult": 1,"child": 0,"infant": 0      | cabin               | "invalid cabin type XYZ"                                   |
      | pax adult is missing               | RUH      | JED           | 2022-10-23    | 2022-10-27  | Economy | "adult": "", "child": 0,"infant": 0    | pax.adult           | "The pax.adult field is required."                         |
      | pax adult is invalid               | RUH      | JED           | 2022-10-23    | 2022-10-27  | Economy | "adult": "1.4a","child": 0,"infant": 0 | pax.adult           | "The pax.adult must be an integer."                        |
      | pax child is invalid               | RUH      | JED           | 2022-10-23    | 2022-10-27  | Economy | "adult": 1,"child": "1.4a","infant": 0 | pax.child           | "The pax.child must be an integer."                        |
      | pax infant is invalid              | RUH      | JED           | 2022-10-23    | 2022-10-27  | Economy | "adult": 1,"child": 0,"infant": "1.4a" | pax.infant          | "The pax.infant must be an integer."                       |


  Scenario: Verify get-fares-calender POST API response if departureFrom & departureTo date < present date
    When user calls get-fares-calender POST API with past departureFrom and departureTo
    Then verify http response code is 400
    And verify response header has Content-Type as "application/problem+json"
    And verify error response body has date "leg.0.departureFrom" message as "The leg.0.departureFrom must be a date after or equal to "
    And verify error response body has date "leg.0.departureTo" message as "The leg.0.departureTo must be a date after or equal to "


