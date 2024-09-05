Feature: Launch the app

  @RegressionWorking
  Scenario: Verify whether the app is getting launched
    When   I click on search wikipedia
    Then   I enter text in search field- 'BrowserStack'
