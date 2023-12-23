package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import utilities.ConfigReader;

import java.util.Optional;

import static io.restassured.RestAssured.given;

public class JPHStepdefinitions {
    String endpoint;
    Response response;
    JsonPath actualResponseJsonpath;

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
}
