package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

public class CommonStepDefs {

    @And("logger is set to log request and response details")
    public void loggerIsSetToLogRequestAndResponseDetails() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Given("^base URL is \"([^\"]*)\"$")
    public void baseURLIs(String baseURL) {
        RestAssured.baseURI = baseURL;
    }


    @Given("^test case id \"([^\"]*)\" and test case name \"([^\"]*)\"$")
    public void testCaseIdAndTestCaseName(String testCaseId, String testCaseName) {
        System.out.println("Test Case ID: " + testCaseId + "\nTest Case name:" + testCaseName);
    }
}
