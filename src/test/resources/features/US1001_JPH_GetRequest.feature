Feature: US1001 Kullanici JPH get request sonuclarini test eder

  @wip
  Scenario: TC01 yapilan GET request sonucunda response degerleri beklenen degerlere esit olmali

    Given Kullanici "jPHBaseUrl" base URL'ini kullanir
    Then Path parametreleri icin "posts/44" kullanir
    And jPH server a GET request gonderir ve testleri yapmak icin response degerini kaydeder
    Then jPH response'da status degerinin 200
    And jPH response'da content type degerinin "application/json; charset=utf-8"
    And jPH responseBody'sindeki attributeleri test etmek icin response i JsonPath objesine cast eder
    Then jPH GET response body'sinde "userId" degerinin Integer 5
    And jPH GET response body'sinde "title" degerinin String "optio dolor molestias sit"
