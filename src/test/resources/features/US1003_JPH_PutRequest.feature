Feature: US1003 Kullanici JPH da PUT request yapar

  @wip
  Scenario: TC03 donen actual response expected response ile ayni olmali
    
    Given Kullanici "jPHBaseUrl" base URL'ini kullanir
    And Path parametreleri icin "posts/77" kullanir
    When PUT request icin "SonTest" "API Veda" ve 10 degerleri ile request body olusturur
    And  Test icin  "SonTest" "API Veda" 10 ve 77 degerleri ile expected response body olusturur
    Then jPH server'a PUT request gonderir ve response'i kaydeder
    And jPH responseBody'sindeki attributeleri test etmek icin response i JsonPath objesine cast eder
    And expected response body ile actual response'daki attribute degerlerinin ayni oldugunu test eder
    And jPH respons daki "Connection" header degerinin "keep-alive"
    And jPH response'da status degerinin 200



