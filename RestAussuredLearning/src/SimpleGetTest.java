import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class SimpleGetTest {

	public void GetWeatherDetails() {
		// Specify the base URL to the RESTful web service
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		// Get the RequestSpecification of the request that you want to sent
		// to the server. The server is specified by the BaseURI that we have
		// specified in the above step.
		RequestSpecification httpRequest = RestAssured.given();
		System.out.println(httpRequest);

		// Make a request to the server by specifying the method Type and the method
		// URL.
		// This will return the Response from the server. Store the response in a
		// variable.
		Response response = httpRequest.request(Method.GET, "/Hyderabad");

		// Now let us print the body of the message to see what response
		// we have recieved from the server
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);

	}

	public void GetWeatherHeader() {
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/Hyderabad");

		// Reader header of a give name. In this line we will get
		// Header named Content-Type
		String contentType = response.header("Content-Type");
		Assert.assertEquals(contentType /* actual value */, "application/json" /* expected value */);

		// Reader header of a give name. In this line we will get
		// Header named Server
		String serverType = response.header("Server");
		Assert.assertEquals(serverType /* actual value */, "nginx" /* expected value */);

		// Reader header of a give name. In this line we will get
		// Header named Content-Encoding
		String contentEncoding = response.header("Content-Encoding");
		Assert.assertEquals(contentEncoding /* actual value */, "gzip" /* expected value */);
	}

	public void IteratingOverHeaders() {
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/Hyderabad");

		// Get all the headers. Return value is of type Headers.
		// Headers class implements Iterable interface, hence we
		// can apply an advance for loop to go through all Headers
		// as shown in the code below
		Headers allHeaders = response.headers();

		// Iterate over all the Headers
		for (Header header : allHeaders) {
			System.out.println("Key: " + header.getName() + " Value: " + header.getValue());
		}
	}

	public void weatherMessageBody() {
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/Hyderabad");

		// Retrieve the body of the Response
		// ResponseBody body = response.getBody();
		ResponseBody body = response.body();

		// By using the ResponseBody.asString() method, we can convert the body
		// into the string representation.
		System.out.println("Response Body is: " + body.asString());

	}

	public void WeatherMessageBody() {
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/Hyderabad");

		// Retrieve the body of the Response
		ResponseBody body = response.getBody();

		// To check for sub string presence get the Response body as a String.
		// Do a String.contains
		String bodyAsString = body.asString();

		// convert the body into lower case and then do a comparison to ignore casing.
		Assert.assertEquals(bodyAsString.toLowerCase().contains("hyderabad") /* Expected value */,
				true /* Actual Value */, "Response body contains Hyderabad");
	}

	public void VerifyCityInJsonResponse() {
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/Hyderabad");

		// First get the JsonPath object instance from the Response interface
		JsonPath jsonPathEvaluator = response.jsonPath();

		// Then simply query the JsonPath object to get a String value of the node
		// specified by JsonPath: City (Note: You should not put $. in the Java code)
		String city = jsonPathEvaluator.get("City");

		// Let us print the city variable to see what we got
		System.out.println("City received from Response " + city);

		// Validate the response
		Assert.assertEquals(city, "Hyderabad", "Correct city name received in the Response");

	}

	
	public void DisplayAllNodesInWeatherAPI() {
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.get("/Hyderabad");

		// First get the JsonPath object instance from the Response interface
		JsonPath jsonPathEvaluator = response.jsonPath();

		// Let us print the city variable to see what we got
		System.out.println("City received from Response: " + jsonPathEvaluator.get("City"));

		// Print the temperature node
		System.out.println("Temperature received from Response: " + jsonPathEvaluator.get("Temperature"));

		// Print the humidity node
		System.out.println("Humidity received from Response: " + jsonPathEvaluator.get("Humidity"));

		// Print weather description
		System.out.println("Weather description received from Response: " + jsonPathEvaluator.get("Weather"));

		// Print Wind Speed
		System.out.println("City received from Response: " + jsonPathEvaluator.get("WindSpeed"));

		// Print Wind Direction Degree
		System.out.println("City received from Response: " + jsonPathEvaluator.get("WindDirectionDegree"));
	}

	
	public void queryParameter() {

		RestAssured.baseURI = "https://samples.openweathermap.org/data/2.5/";
		RequestSpecification request = RestAssured.given();

		Response response = request.queryParam("q", "London,UK").queryParam("appid", "2b1fd2d7f77ccf1b7de9b441571b39b8")
				.get("/weather");

		String jsonString = response.asString();
		System.out.println(jsonString);
		System.out.println(response.getStatusCode());
		Assert.assertEquals(jsonString.contains("London"), true);

	}
	@Test
	public void RegistrationSuccessful()
	{ 
	 RestAssured.baseURI ="http://restapi.demoqa.com/customer";
	 RequestSpecification request = RestAssured.given();
	 
	 JSONObject requestParams = new JSONObject();
	 requestParams.put("FirstName", "Virender"); // Cast
	 requestParams.put("LastName", "Singh");
	 requestParams.put("UserName", "sdimpleuser2dd2011");
	 requestParams.put("Password", "password1"); 
	 requestParams.put("Email",  "sample2ee26d9@gmail.com");
	 
	 request.body(requestParams.toJSONString());
	 Response response = request.get("/register");
	 
	 int statusCode = response.getStatusCode();
	 System.out.println("The status code recieved: " + statusCode);
	 
	 System.out.println("Response body: " + response.body().asString());
	}
}
