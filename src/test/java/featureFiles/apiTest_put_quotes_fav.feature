Feature: To test basic scenarios for PUT QUOTES API


  Background:
    Given logger is set to log request and response details
    And base URL is "https://favqs.com/api"

  Scenario: Generate user token for the suite
    Given user token is created with endpoint "/session"


  Scenario Outline: Verify PUT QUOTES API for valid URI, method and Authorisation, http response status is 200
    Given test case id "<testCaseId>" and test case name "<testCaseName>"
    When method is "<method>", endpoint is "<endpoint>", quote_id "<quoteId>" and authorisation set to "<authorisation>"
    Then verify response status against "<responseStatus>" for fav endpoint

    Examples:
      | testCaseId | testCaseName                                                                | method | endpoint               | quoteId | authorisation                    | responseStatus |
      | TC01       | Verify for valid URI, method and Authorisation, http response status is 200 | PUT    | /quotes/{quote_id}/fav | 19208   | 6daf51338ae894c80b9d942a5c8321f0 | 200            |


  Scenario Outline: Verify PUT QUOTES API for valid URI, method and Authorisation, response body is populated as expected
    Given test case id "<testCaseId>" and test case name "<testCaseName>"
    When method is "<method>", endpoint is "<endpoint>", quote_id "<quoteId>" and authorisation set to "<authorisation>"
    Then verify response status against "<responseStatus>" for fav endpoint
    Then verify response body against the test data in "<testDataFile>" for test case "<testCaseId>"
    Examples:
      | testCaseId | testCaseName                                                                           | method | endpoint               | quoteId | authorisation                    | responseStatus | testDataFile |
      | TC02       | Verify for valid URI, method and Authorisation, response body is populated as expected | PUT    | /quotes/{quote_id}/fav | 19208   | 6daf51338ae894c80b9d942a5c8321f0 | 200            | testData.csv |


  Scenario Outline: Verify the error in response for quote not found for fav
    Given test case id "<testCaseId>" and test case name "<testCaseName>"
    When method is "<method>", endpoint is "<endpoint>", quote_id "<quoteId>" and authorisation set to "<authorisation>"
    Then verify response status against "<responseStatus>" for fav endpoint
    Then verify "<error>" in the response of fav api

    Examples:
      | testCaseId | testCaseName                                             | method | endpoint                 | quoteId  | authorisation                    | responseStatus | error     |
      | TC03       | Verify the error in response for quote not found for fav | PUT    | /quotes/{quote_id}/fav   | 19208111 | 6daf51338ae894c80b9d942a5c8321f0 | 404            | Not Found |
      | TC05       | Verify the error in response for quote not found for fav | PUT    | /quotes/{quote_id}/unfav | 19208111 | 6daf51338ae894c80b9d942a5c8321f0 | 404            | Not Found |


  Scenario Outline: Verify the unmarking of favourite quote
    Given test case id "<testCaseId>" and test case name "<testCaseName>"
    When method is "<method>", endpoint is "<endpoint>", quote_id "<quoteId>" and authorisation set to "<authorisation>"
    Then verify response status against "<responseStatus>" for fav endpoint
    Then verify response body against the test data in "<testDataFile>" for test case "<testCaseId>"
    Examples:
      | testCaseId | testCaseName                            | method | endpoint                 | quoteId | authorisation                    | responseStatus | testDataFile |
      | TC04       | Verify the unmarking of favourite quote | PUT    | /quotes/{quote_id}/unfav | 19208   | 6daf51338ae894c80b9d942a5c8321f0 | 200            | testData.csv |
