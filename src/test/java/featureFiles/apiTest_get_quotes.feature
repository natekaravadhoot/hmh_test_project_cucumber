Feature: To test basic scenarios for GET QUOTES API

  Background:
    Given logger is set to log request and response details
    And base URL is "https://favqs.com/api"


  Scenario Outline: Verify GET QUOTES API for valid URI, method and Authorisation, http response status is 200
    Given test case id "<testCaseId>" and test case name "<testCaseName>"
    When method is "<method>" and authorisation set to "<authorisation>"
    Then verify response status against "<responseStatus>"

    Examples:
      | testCaseId | testCaseName                                                                | method | authorisation                    | responseStatus |
      | TC01       | Verify for valid URI, method and Authorisation, http response status is 200 | GET    | 6daf51338ae894c80b9d942a5c8321f0 | 200            |


  Scenario Outline: Verify fVerify GET QUOTES API  or valid URI, method and Authorisation, response body is populated as expected
    Given test case id "<testCaseId>" and test case name "<testCaseName>"
    When method is "<method>" and authorisation set to "<authorisation>"
    Then verify response body structure for page "<pageNumber>" and records "<numberOfRecords>"

    Examples:
      | testCaseId | testCaseName                                                                           | method | authorisation                    | pageNumber | numberOfRecords |
      | TC02       | Verify for valid URI, method and Authorisation, response body is populated as expected | GET    | 6daf51338ae894c80b9d942a5c8321f0 | 1          | 25              |


  Scenario Outline: Verify that List Quotes API filters results based on keyword
    Given test case id "<testCaseId>" and test case name "<testCaseName>"
    When method is "<method>", "<parameter>" having "<keyword>" and authorisation set to "<authorisation>"
    Then verify response status against "<responseStatus>"
    And verify response body structure for page "<pageNumber>" and records "<numberOfRecords>"
    And verify the response contains quotes having keyword "<keyword>"

    Examples:
      | testCaseId | testCaseName                                                 | method | authorisation                    | parameter | keyword | responseStatus | pageNumber | numberOfRecords |
      | TC03       | Verify that List Quotes API filters results based on keyword | GET    | 6daf51338ae894c80b9d942a5c8321f0 | filter    | funny   | 200            | 1          | 25              |


  Scenario Outline: Verify that List Quotes API filters results based on keyword in the tag or author or user
    Given test case id "<testCaseId>" and test case name "<testCaseName>"
    When method is "<method>", "<parameter>" having "<keyword>", "<parameterType>" having "<parameterTypevalue>" and authorisation set to "<authorisation>"
    Then verify response status against "<responseStatus>"
    And verify the response contains quotes having keyword "<keyword>" in "<parameterTypevalue>"

    Examples:
      | testCaseId | testCaseName                                                            | method | authorisation                    | parameter | keyword    | parameterType | parameterTypevalue | responseStatus |
      | TC04       | Verify that List Quotes API filters results based on keyword in the tag | GET    | 6daf51338ae894c80b9d942a5c8321f0 | filter    | funny      | type          | tag                | 200            |
      | TC05       | Verify that List Quotes API filters results based on author name        | GET    | 6daf51338ae894c80b9d942a5c8321f0 | filter    | Mark Twain | type          | author             | 200            |


  Scenario Outline: Verify that List Quotes API filters results based on user
    #As data is know in our control, the only validation that can be done is to check availability of all elements in the response
    Given test case id "<testCaseId>" and test case name "<testCaseName>"
    When method is "<method>", "<parameter>" having "<keyword>", "<parameterType>" having "<parameterTypevalue>" and authorisation set to "<authorisation>"
    Then verify response status against "<responseStatus>"
    Then verify response body structure for page "<pageNumber>" and records "<numberOfRecords>"

    Examples:
      | testCaseId | testCaseName                                                          | method | authorisation                    | parameter | keyword | parameterType | parameterTypevalue | responseStatus | pageNumber | numberOfRecords |
      | TC06       | Verify that List Quotes API filters results based on user             | GET    | 6daf51338ae894c80b9d942a5c8321f0 | filter    | gose    | type          | user               | 200            | 1          | 25              |


  Scenario Outline: Verify that List Quotes API is able to show only hidden quotes
    #As data is know in our control, the only validation that can be done is to check availability of all elements in the response
    Given test case id "<testCaseId>" and test case name "<testCaseName>"
    When method is "<method>", "<parameter>" having "<keyword>" and authorisation set to "<authorisation>"
    Then verify response status against "<responseStatus>"
    Then verify response body structure for page "<pageNumber>" and records "<numberOfRecords>"

    Examples:
      | testCaseId | testCaseName                                                          | method | authorisation                    | parameter | keyword | responseStatus | pageNumber | numberOfRecords |
      | TC07       | Verify that List Quotes API is able to give hidden quotes in response | GET    | 6daf51338ae894c80b9d942a5c8321f0 | hidden    | 1       | 200            | 1          | 25              |


  Scenario Outline: Verify negative scenarios where Authorisation, URI or method is invalid
    Given test case id "<testCaseId>" and test case name "<testCaseName>"
    When method is "<method>", endpoint is "<endpoint>" and authorisation set to "<authorisation>"
    Then verify response status against "<responseStatus>"
    And verify "<errorMessage>" in the response

    Examples:
      | testCaseId | testCaseName                                            | method | endpoint | authorisation                      | responseStatus | errorMessage               |
      | TC012      | Verify the API response for invalid authorisation token | GET    | /quotes  | 6daf51338ae894c80b9d942a5c8321f011 | 401            | HTTP Token: Access denied. |
      | TC013      | Verify the API response for invalid URI                 | GET    | /quotess | 6daf51338ae894c80b9d942a5c8321f01  | 404            | Not found                  |
      | TC014      | Verify the API response for invalid method              | PUT    | /quotes  | 6daf51338ae894c80b9d942a5c8321f01  | 405            | Not Allowed                |


  Scenario Outline: Verify that List Quotes API displays results on multiple pages
    Given test case id "<testCaseId>" and test case name "<testCaseName>"
    #Execute API with page number as 2
    When method is "<method>", "<parameter>" having "<pageNumberFirstTest>" and authorisation set to "<authorisation>"
    Then verify response status against "<responseStatus>"
    And verify response body structure for page "<pageNumberFirstTest>" and records "<numberOfRecordsPage2>"

        #Execute API with page number as 93
    When method is "<method>", "<parameter>" having "<pageNumberSecondTest>" and authorisation set to "<authorisation>"
    Then verify response status against "<responseStatus>"
    And verify response body structure for page "<pageNumberSecondTest>" and records "<numberOfRecordsPage93>"

    Examples:
      | testCaseId | testCaseName                                                   | method | authorisation                    | parameter | pageNumberFirstTest | responseStatus | numberOfRecordsPage2 | pageNumberSecondTest | numberOfRecordsPage93 |
      | TC08       | Verify that List Quotes API displays results on multiple pages | GET    | 6daf51338ae894c80b9d942a5c8321f0 | page      | 2                   | 200            | 25                   | 93                   | 3                     |


  Scenario Outline: Verify that List Quotes API filters based on keyword and displays results on page based on page parameter
    Given test case id "<testCaseId>" and test case name "<testCaseName>"
    #Execute API with page number as 3 and keyword as funny
    When method is "<method>", "<pageParameter>" having "<pageNumber>", "<keywordParameter>" having "<keyword>" and authorisation set to "<authorisation>"
    Then verify response status against "<responseStatus>"
    And verify response body structure for page "<pageNumber>" and records "<numberOfRecords>"

    Examples:
      | testCaseId | testCaseName                                                                                              | method | authorisation                    | pageParameter | pageNumber | keywordParameter | keyword | responseStatus | numberOfRecords |
      | TC09       | Verify that List Quotes API filters based on keyword and displays results on page based on page parameter | GET    | 6daf51338ae894c80b9d942a5c8321f0 | page          | 3          | filter           | funny   | 200            | 25              |