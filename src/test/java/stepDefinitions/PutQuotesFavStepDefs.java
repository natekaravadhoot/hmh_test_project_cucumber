package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import utils.FileReadWrite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static io.restassured.RestAssured.given;

public class PutQuotesFavStepDefs {

    // Declaring user token as static so that there will be one instance created for whole class
    static String userToken;

    // Declaring response to store response of API and use it across multiple steps in test
    Response response;

    @And("^user token is created with endpoint \"([^\"]*)\"$")
    public void userTokenIsCreated(String endpoint) {
        given().log().all();
        userToken = given().contentType("application/json").header("Authorization", "Token token=6daf51338ae894c80b9d942a5c8321f0").body("{ \n  \"user\": {\n    \"login\": \"natekaravadhoot@gmail.com\",\n    \"password\": \"f6ee517a670997dd\"\n  }\n}")
                .when().request("POST", endpoint)
                .then().statusCode(200)
                .extract().body().jsonPath().get("User-Token");
        System.out.println("User-Token:"+userToken);
    }

    @When("method is \"([^\"]*)\", endpoint is \"([^\"]*)\", quote_id \"([^\"]*)\" and authorisation set to \"([^\"]*)\"$")
    public void methodIsEndpointIsAndAuthorisationSetTo(String method, String endpoint, int quoteId, String authorisation) {
        response = given().headers("Authorization", "Token token="+authorisation+"\"","User-Token",userToken).pathParam("quote_id",quoteId)
                .when().request(method, endpoint).then().extract().response();
    }

    @Then("^verify response status against \"([^\"]*)\" for fav endpoint$")
    public void verifyResponseStatusAgainst(int statusCode) {
        Assert.assertEquals(statusCode,response.statusCode());
    }

    @Then("verify response body against the test data in \"([^\"]*)\" for test case \"([^\"]*)\"$")
    public void verifyResponseBodyAgainstTheTestDataIn(String testDataFile, String testCaseId) throws IOException {
        FileReadWrite fileRead = new FileReadWrite();
        HashMap<String, Object> mapData;

        //Got data in key-value pair from csv file below
        mapData = fileRead.readCSVFileWithHeader(testDataFile, testCaseId);

        // Verifying data from response against the test data in csv file
        Assert.assertEquals(mapData.get("id"),response.jsonPath().getString("id"));
        Assert.assertEquals(Boolean.parseBoolean(mapData.get("dialogue").toString()), response.jsonPath().get("dialogue"));
        Assert.assertEquals(Boolean.parseBoolean(mapData.get("private").toString()), response.jsonPath().get("private"));
        LinkedHashMap<String, Object> user_details = response.jsonPath().get("user_details");

        Assert.assertEquals(Boolean.parseBoolean(mapData.get("favorite").toString()), user_details.get("favorite"));
        Assert.assertEquals(Boolean.parseBoolean(mapData.get("upvote").toString()), user_details.get("upvote"));
        Assert.assertEquals(Boolean.parseBoolean(mapData.get("downvote").toString()), user_details.get("downvote"));
        Assert.assertEquals(Boolean.parseBoolean(mapData.get("hidden").toString()), user_details.get("hidden"));


        ArrayList tags = response.jsonPath().get("tags");

        Assert.assertEquals(mapData.get("tag1"), tags.get(0).toString());
        Assert.assertEquals(mapData.get("tag2"), tags.get(1).toString());
        Assert.assertEquals(mapData.get("url"), response.jsonPath().get("url").toString());
        Assert.assertEquals(mapData.get("favorites_count"), response.jsonPath().get("favorites_count").toString());
        Assert.assertEquals(mapData.get("upvotes_count"), response.jsonPath().get("upvotes_count").toString());
        Assert.assertEquals(mapData.get("author"), response.jsonPath().get("author").toString());
        Assert.assertEquals(mapData.get("author_permalink"), response.jsonPath().get("author_permalink").toString());
        Assert.assertEquals(mapData.get("body"), response.jsonPath().get("body").toString());

    }

    @Then("verify {string} in the response of fav api")
    public void verifyInTheResponseOfFavApi(String error) {
        Assert.assertEquals(error, response.jsonPath().get("error"));
    }
}
