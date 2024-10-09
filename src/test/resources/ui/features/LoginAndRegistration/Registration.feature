@Registration
Feature: Digital Bank Registration Page

  Background:
    Given The user with "jenny.pa@aol.com" is not in DB
    And User navigates to Digital Bank signup page

#THIS IS FULLY AUTOMATED END-TO-END TESTING SCENARIO BELOW
  @Test
  Scenario: Positive Case. As a user i want to successfully create Digital Bank account

    When User creates account with following fields
      | title | firstName | lastName | gender | dob        | ssn         | email            | password     | address     | locality | region | postalCode | country | homePhone  | mobilePhone | workPhone  | termsCheckMark |
      | Mrs.  | Jenny     | Park     | F      | 12/12/1990 | 564-66-9786 | jenny.pa@aol.com | JennyPark123 | 123 Main St | City     | VA     | 99921      | US      | 7182345709 | 9298567432  | 9297564372 | true           |
    Then User should be displayed with the message "Success Registration Successful. Please Login."
    Then the following user info should be saved in the db
      | title | firstName | lastName | gender | dob        | ssn         | email            | password     | address     | locality | region | postalCode | country | homePhone  | mobilePhone | workPhone  | termsCheckMark | accountNonExpired | accountNonLocked | credentialsNonExpired | enabled |
      | Mrs.  | Jenny     | Park     | F      | 12/12/1990 | 564-66-9786 | jenny.pa@aol.com | JennyPark123 | 123 Main St | City     | VA     | 99921      | US      | 7182345709 | 9298567432  | 9297564372 | true           | true              | true             | true                  | true    |
#FULY END-TO-END SCENARIO ENDS HERE

    #empty input negative case
  @NegativeRegistrationCases
  Scenario Outline: Negative Test Cases. As a Digital Bank Admin I want to make sure users can not register without providing all valid data

    When User creates account with following fields
      | title   | firstName   | lastName   | gender   | dob   | ssn   | email   | password   | address   | locality   | region   | postalCode   | country   | homePhone   | mobilePhone   | workPhone   | termsCheckMark   |
      | <title> | <firstName> | <lastName> | <gender> | <dob> | <ssn> | <email> | <password> | <address> | <locality> | <region> | <postalCode> | <country> | <homePhone> | <mobilePhone> | <workPhone> | <termsCheckMark> |
    Then the User should see the "<fieldWithError>" required field error message "<errorMessage>"

      #before we see any error message we need to understand which field gives an error
    Examples:
      | title | firstName | lastName | gender | dob | ssn | email | password | address | locality | region | postalCode | country | homePhone | mobilePhone | workPhone | termsCheckMark | fieldWithError | errorMessage                        |
      |       |           |          |        |     |     |       |          |         |          |        |            |         |           |             |           |                | title          | Please select an item in the list.  |
      | Mrs.  |           |          |        |     |     |       |          |         |          |        |            |         |           |             |           |                | firstName      | Please fill out this field.         |
      | Mrs.  | Jenny     |          |        |     |     |       |          |         |          |        |            |         |           |             |           |                | lastName       | Please fill out this field.         |
      | Mrs.  | Jenny     | Park     |        |     |     |       |          |         |          |        |            |         |           |             |           |                | gender         | Please select one of these options. |