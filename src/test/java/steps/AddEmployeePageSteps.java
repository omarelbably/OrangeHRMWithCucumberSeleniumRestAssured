package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

import java.util.Map;

import static hooks.Hooks.addEmployeePage;

public class AddEmployeePageSteps {
    @And("user fill in the new employee details with the following data:")
    public void userFillInTheNewEmployeeDetailsWithTheFollowingData(DataTable dataTable) {
        Map<String, String> employeeData = dataTable.asMap(String.class, String.class);

        String userRole = employeeData.get("userRole");
        String employeeName = employeeData.get("employeeName");
        String status = employeeData.get("status");
        String username = employeeData.get("username");
        String password = employeeData.get("password");

        addEmployeePage.fillInEmployeeDetails(userRole, employeeName, status, username, password);
    }

    @Given("user clicks on save button")
    public void user_clicks_on_save_button() {
        addEmployeePage.clickSaveButton();
    }
}
