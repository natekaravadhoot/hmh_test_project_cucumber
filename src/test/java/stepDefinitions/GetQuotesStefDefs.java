package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.junit.Assert.assertNotNull;

@RunWith(Cucumber.class)
public class GetQuotesStefDefs {

    // Declaring response to store response of API and use it across multiple steps in test
    private Response response;
    private String endpoint = "https://favqs.com/api/quotes";

    @When("^method is \"([^\"]*)\" and authorisation set to \"([^\"]*)\"$")
    public void methodIsAndEndpointIs(String method, String authorisation) {
        //RestAssured.basePath="/quotes";
        response = given().header("Authorization", "Token token=" + authorisation + "\"").when().request(method,endpoint)
                .then().extract().response();
    }

    @Then("^verify response status against \"([^\"]*)\"$")
    public void verifyResponseStatusAgainst(int statusCode) {
        Assert.assertEquals(statusCode, response.statusCode());
    }

    @Then("^verify response body structure for page \"([^\"]*)\" and records \"([^\"]*)\"$")
    public void verifyResponseBodyStructure(int pageNumber, int numberOfRecords) {
        Assert.assertEquals(pageNumber, response.jsonPath().getInt("page"));
        Boolean last_page = response.jsonPath().getBoolean("last_page");
        if(pageNumber!=93)
        Assert.assertEquals(false, last_page);
        else
            Assert.assertEquals(true, last_page);
        //To verify the details within each quote json object is present for particular page. This can be extended to verify actual data if teh data source is known
        ArrayList quotes = response.jsonPath().getJsonObject("quotes");

        //Call method to verify the data elements for 25 records are not null
        verifyQuotesDataNotNull(quotes, numberOfRecords);
    }

    public void verifyQuotesDataNotNull(ArrayList quotes, int numberOfQuotes) {
        int i = 0;
        LinkedHashMap quote;
        while (i < numberOfQuotes) {
            quote = (LinkedHashMap) quotes.get(i);
            assertNotNull(quote.get("id"));
            assertNotNull(quote.get("dialogue"));
            assertNotNull(quote.get("private"));
            assertNotNull(quote.get("url"));
            assertNotNull(quote.get("favorites_count"));
            assertNotNull(quote.get("upvotes_count"));
            assertNotNull(quote.get("downvotes_count"));
            assertNotNull(quote.get("author"));
            assertNotNull(quote.get("author_permalink"));
            assertNotNull(quote.get("body"));
            i++;
        }
    }

    @When("method is {string}, {string} having {string} and authorisation set to {string}")
    public void methodIsHavingAuthorisationSetTo(String method, String parameter, String keyword, String authorisation) {
        //RestAssured.basePath="/quotes";
        response = given().header("Authorization", "Token token=" + authorisation + "\"").param(parameter, keyword).when().request(method,endpoint)
                .then().extract().response();
    }

    @And("verify the response contains quotes having keyword {string}")
    public void verifyTheResponseContainsQuotesHavingKeyword(String keyword) {
        ArrayList quotes = response.jsonPath().getJsonObject("quotes");
        int i = 0;
        LinkedHashMap quote;

        //Validate that response has only those quotes which have 'keyword' as 'funny'.
        //This is validated for page 1, this can be extended to validate all pages by adding validation for last_page parameter in response

        while (i <= 24) {
            quote = (LinkedHashMap) quotes.get(i);
            Assert.assertTrue(((quote.get("body")).toString().contains(keyword)) || (quote.get("tags").toString().contains(keyword)));
            i++;
        }
    }

    @When("method is {string}, {string} having {string}, {string} having {string} and authorisation set to {string}")
    public void methodIsHavingHavingAndAuthorisationSetTo(String method, String parameter, String keyword, String parameterType, String parameterTypevalue, String authorisation) {
        //RestAssured.basePath="/quotes";
        response = given().header("Authorization", "Token token=" + authorisation + "\"").params(parameter, keyword,parameterType,parameterTypevalue).when().request(method,endpoint)
                .then().extract().response();
    }

    @And("verify the response contains quotes having keyword {string} in {string}")
    public void verifyTheResponseContainsQuotesHavingKeywordIn(String keyword, String paramTypeValue) {
        //Handling jsonpath for tags in response
        if(paramTypeValue.equals("tag"))
            paramTypeValue=paramTypeValue+"s";
        //Execute the API, validate the response code and extract quotes from response
        ArrayList quotes = response.jsonPath().getJsonObject("quotes");
        int i = 0;
        LinkedHashMap quote;

        //Validate that response has only those quotes which have 'tag' as 'funny'.
        //This is validated for page 1, this can be extended to validate all pages by adding validation for last_page parameter in response

        while (i <= 24) {
            quote = (LinkedHashMap) quotes.get(i);
            Assert.assertTrue((quote.get(paramTypeValue)).toString().contains(keyword));
            i++;
        }
    }

    @And("verify {string} in the response")
    public void verifyInTheResponse(String errorMessage) {
        //Validate response body
        Assert.assertTrue(response.getBody().prettyPrint().contains(errorMessage));
    }

    @When("method is {string}, endpoint is {string} and authorisation set to {string}")
    public void methodIsEndpointIsAndAuthorisationSetTo(String method, String testEndpoint, String authorisation) {
        response = given().header("Authorization", "Token token=" + authorisation + "\"").when().request(method,testEndpoint)
                .then().extract().response();
    }
}
