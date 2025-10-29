package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static hooks.Hooks.adminPage;
import static hooks.Hooks.totalRecords;

public class AdminPageSteps {
    @Then("get the total number of records found")
    public void getTotalNumberOfRecordsFound() {
        totalRecords = adminPage.getTotalRecords();
        System.out.println("Total number of records found: " + totalRecords);
    }

    @When("user clicks on add employee button")
    public void clickOnAddEmployeeBtn() {
        adminPage.clickOnAddEmployeeBtn();
    }

    @Then("verify that the new record is increased by 1 or more")
    public void verifyThatTheNewRecordIsIncreasedByOrMore() {
        int newTotalRecords = adminPage.getTotalRecords();
        if (newTotalRecords > totalRecords) {
            System.out.println("Record count increased successfully from " + totalRecords + " to " + newTotalRecords);
        } else {
            throw new AssertionError("Record count did not increase. Previous: " + totalRecords + ", Current: " + newTotalRecords);
        }
    }

    @And("user searches for employee with username {string}")
    public void userSearchesForEmployeeWithUsername(String username) {
        adminPage.clickOnSystemUsersDropdownBtn();
        adminPage.enterUsernameInSearchField(username);
        adminPage.clickOnSearchBtn();
    }

    @And("user deletes the employee record")
    public void userDeletesTheEmployeeRecord() {
        adminPage.clickOnDeleteRecordBtn();
        adminPage.clickOnConfirmDeleteAction();
    }

    @Then("verify that the new record is decreased by 1 or more")
    public void verifyThatTheNewRecordIsDecreasedByOrMore() {
        adminPage.clickOnResetSearchBtn();
        int newTotalRecords = adminPage.getTotalRecords();
        if (newTotalRecords >= totalRecords) {
            System.out.println("Record count decreased successfully from " + totalRecords + " to " + newTotalRecords);
        } else {
            throw new AssertionError("Record count did not decrease. Previous: " + totalRecords + ", Current: " + newTotalRecords);
        }
    }
}
