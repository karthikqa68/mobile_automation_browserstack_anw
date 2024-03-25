Feature: Launch the app

  @RegressionWorking
  Scenario Outline: Verify whether the app is getting launched
    When   I launch the Android app on device-'<device-name>' and with automation type as '<automation-type>'
    Then   I select english language
  Examples:
    | device-name | automation-type |
    | android11   |  app            |
    | android09   |  app            |



