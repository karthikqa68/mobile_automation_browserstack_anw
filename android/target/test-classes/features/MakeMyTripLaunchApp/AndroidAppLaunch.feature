Feature: Launch the app

  @RegressionWorking
  Scenario Outline: Verify whether the app is getting launched
    When   I launch the Android app on device-'<device-name>'
    Then   I select english language
  Examples:
    | device-name |
    | android11   |



