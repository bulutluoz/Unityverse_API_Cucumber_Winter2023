package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;
import utilities.ConfigReader;

import java.util.Optional;

import static io.restassured.RestAssured.given;

public class JPHStepdefinitions {
    /*
    Request Body

        {
            "title":"Ahmet",
            "body":"Merhaba",
            "userId":10,
            "id":70
        }

        Expected Response Body:

        {
            "title":"Ahmet",
            "body":"Merhaba",
            "userId":10,
            "id":70
        }
     */
    String endpoint;
    Response response;
    JsonPath actualResponseJsonpath;
    JSONObject requestBody=new JSONObject();
    JSONObject expectedResponseBody = new JSONObject();

    @Given("Kullanici {string} base URL'ini kullanir")
    public void kullanici_base_url_ini_kullanir(String baseUrl) {
        endpoint = ConfigReader.getProperty(baseUrl);
    }
    @Then("Path parametreleri icin {string} kullanir")
    public void path_parametreleri_icin_kullanir(String pathParams) {
        endpoint += ("/"+pathParams);
    }
    @Then("jPH server a GET request gonderir ve testleri yapmak icin response degerini kaydeder")
    public void j_ph_server_a_get_request_gonderir_ve_testleri_yapmak_icin_response_degerini_kaydeder() {
        response = given()
                        .when()
                        .get(endpoint);
    }
    @Then("jPH response'da status degerinin {int}")
    public void j_ph_response_da_status_degerinin(Integer expectedStatusCode) {
        response.then().assertThat().statusCode(expectedStatusCode);
    }
    @Then("jPH response'da content type degerinin {string}")
    public void j_ph_response_da_content_type_degerinin(String expectedContentType) {
        response.then().contentType(expectedContentType);
    }
    @Then("jPH GET response body'sinde {string} degerinin Integer {int}")
    public void j_ph_get_response_body_sinde_degerinin_integer(String istenenAttribute, Integer expectedValue) {

        Assert.assertEquals(expectedValue,(Integer)actualResponseJsonpath.getInt(istenenAttribute));
    }
    @Then("jPH GET response body'sinde {string} degerinin String {string}")
    public void j_ph_get_response_body_sinde_degerinin_string(String istenenAttribute, String expectedValue) {

        Assert.assertEquals(expectedValue,actualResponseJsonpath.getString(istenenAttribute));
    }


    @And("jPH responseBody'sindeki attributeleri test etmek icin response i JsonPath objesine cast eder")
    public void jphResponseBodySindekiAttributeleriTestEtmekIcinResponseIJsonPathObjesineCastEder() {
        actualResponseJsonpath = response.jsonPath();
    }

    @Then("jPH server a POST request gonderir ve testleri yapmak icin response degerini kaydeder")
    public void j_ph_server_a_post_request_gonderir_ve_testleri_yapmak_icin_response_degerini_kaydeder() {


        response = given()
                    .when().body(requestBody.toString()).contentType(ContentType.JSON)
                    .post(endpoint);


    }
    @Then("jPH respons daki {string} header degerinin {string}")
    public void j_ph_respons_daki_header_degerinin(String istenenHeader, String expectedHeaderDegeri) {

        response.then().assertThat().header(istenenHeader,expectedHeaderDegeri);
    }
    @Then("response attribute degerlerinin {string},{string},{int} ve {int} oldugunu test eder")
    public void response_attribute_degerlerinin_ve_oldugunu_test_eder(String expectedTitle, String expectedBody, Integer expectedUserId, Integer expectedId) {

        Assert.assertEquals(expectedTitle, actualResponseJsonpath.getString("title"));
        Assert.assertEquals(expectedBody,actualResponseJsonpath.getString("body"));
        Assert.assertEquals(expectedUserId,(Integer) actualResponseJsonpath.getInt("userId"));
        Assert.assertEquals(expectedId,(Integer)actualResponseJsonpath.getInt("id"));
    }


    @Then("POST request icin {string},{string},{int} bilgileri ile request body olusturur")
    public void post_request_icin_bilgileri_ile_request_body_olusturur(String title, String body, Integer userId) {
        requestBody.put("title",title);
        requestBody.put("body",body);
        requestBody.put("userId",userId);
    }

    @When("PUT request icin {string} {string} ve {int} degerleri ile request body olusturur")
    public void put_request_icin_ve_degerleri_ile_request_body_olusturur(String title, String body, Integer userId) {
        requestBody.put("title",title);
        requestBody.put("body",body);
        requestBody.put("userId",userId);
    }
    @When("Test icin  {string} {string} {int} ve {int} degerleri ile expected response body olusturur")
    public void test_icin_ve_degerleri_ile_expected_response_body_olusturur(String title, String body, Integer userId, Integer id) {
        expectedResponseBody.put("title",title);
        expectedResponseBody.put("body",body);
        expectedResponseBody.put("userId",userId);
        expectedResponseBody.put("id",id);
    }
    @Then("jPH server'a PUT request gonderir ve response'i kaydeder")
    public void j_ph_server_a_put_request_gonderir_ve_response_i_kaydeder() {
        response = given().contentType(ContentType.JSON)
                        .when().body(requestBody.toString())
                        .put(endpoint);

    }
    @Then("expected response body ile actual response'daki attribute degerlerinin ayni oldugunu test eder")
    public void expected_response_body_ile_actual_response_daki_attribute_degerlerinin_ayni_oldugunu_test_eder() {

        Assert.assertEquals(expectedResponseBody.getString("title"),
                            actualResponseJsonpath.getString("title"));

        Assert.assertEquals(expectedResponseBody.getString("body"),
                            actualResponseJsonpath.getString("body"));

        Assert.assertEquals(expectedResponseBody.getInt("userId"),
                            actualResponseJsonpath.getInt("userId"));

        Assert.assertEquals(expectedResponseBody.getInt("id"),
                            actualResponseJsonpath.getInt("id"));
    }
}
