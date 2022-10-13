@smoke
Feature: rich-content GET API

  Scenario: Successful rich-content GET API request for airline_refund and baggage_pre_booking
    When user calls rich-content GET API with following query parameter
      | airline_refund | baggage_pre_booking |
      | SV,F3,X1,XY    | RUH-JED             |
    Then verify rich-content API http response code is 200
    And verify rich-content API response header has Content-Type as "application/json"
    And verify rich-content API response body has correct schema "richContentFullResponse"
    And verify rich-content API response has airlines as "SV,F3,X1,XY"

  Scenario: Successful rich-content GET API request for airline_refund
    When user calls rich-content GET API with following query parameter
      | airline_refund |
      | SV,F3,X1,XY    |
    Then verify rich-content API http response code is 200
    And verify rich-content API response header has Content-Type as "application/json"
    And verify rich-content API response body has correct schema "richContentAirlineRefundResponse"
    And verify rich-content API response has airlines as "SV,F3,X1,XY"

  Scenario: Successful rich-content GET API request for baggage_pre_booking
    When user calls rich-content GET API with following query parameter
      | baggage_pre_booking |
      | RUH-JED             |
    Then verify rich-content API http response code is 200
    And verify rich-content API response header has Content-Type as "application/json"
    And verify rich-content API response body has correct schema "richContentBaggageResponse"

  Scenario Outline: Verify rich-content GET API response if <scenario>
    When user calls rich-content GET API with following query parameter
      | airline_refund   | baggage_pre_booking   |
      | <airline_refund> | <baggage_pre_booking> |
    Then verify rich-content API http response code is 200
    And verify rich-content API response header has Content-Type as "application/json"
    And verify rich-content API response body has correct schema "<schema>"
    Examples:
      | scenario                              | airline_refund | baggage_pre_booking | schema                           |
      | airline_refund is empty               |                | RUH-JED             | richContentBaggageResponse       |
      | airline_refund has invalid value      | XYZ12          | RUH-JED             | richContentBaggageResponse       |
      | baggage_pre_booking is empty          | SV,F3,X1,XY    |                     | richContentAirlineRefundResponse |
      | baggage_pre_booking has invalid value | SV,F3,X1,XY    | XYZ12-XYZ12         | richContentAirlineRefundResponse |

  Scenario: Verify rich-content GET API response if airline_refund and baggage_pre_booking both are empty
    When user calls rich-content GET API with following query parameter
      | airline_refund   | baggage_pre_booking   |
      |                  |                       |
    Then verify rich-content API http response code is 500
    And verify rich-content API response header has Content-Type as "application/problem+json"


