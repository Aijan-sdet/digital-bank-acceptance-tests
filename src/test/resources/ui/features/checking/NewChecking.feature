Feature: Creating a new checking account
  Scenario: Create a standard individual checking account
    Given the user logged in as "madlen.p@yahoo.com" "Madlen123"
    When the user creates new checking account with following data
      | checkingAccountType | accountOwnership | accountName                   | initialDepositAmount |
      | Standard Checking   | Individual       | Madlen Pierre Second Checking | 100000.00            |
    Then the user should see the green "Successfully created new Standard Checking account named Madlen Pierre Second Checking" message
    And the user should see newly added account card
      | accountName                   | accountType       | ownership  | accountNumber | interestRate | balance   |
      | Madlen Pierre Second Checking | Standard Checking | Individual | 486139465    | 0.0%         | 100000.00 |
    And the user should see the following transaction
      | date             | category | description               | amount     | balance   |
      | 2024-09-02 02:26 | Income   | 845333592 (DPT) - Deposit | 100000.00  | 100000.00 |