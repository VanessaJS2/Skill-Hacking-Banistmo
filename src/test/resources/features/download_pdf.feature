Feature: Consultar PDF de Prohibiciones en Préstamos de Auto

  Scenario: Usuario visualiza el PDF de Prohibiciones
    Given el usuario entra ala pagina de banistmo Personas
    When el usuario busca el documento requerido
    And hace clic en el ícono PDF de Prohibiciones
    Then el PDF de Prohibiciones se muestra correctamente