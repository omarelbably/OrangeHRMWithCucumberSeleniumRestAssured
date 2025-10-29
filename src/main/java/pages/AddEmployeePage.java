package pages;

import org.openqa.selenium.By;

public class AddEmployeePage extends BasePage {
    private final By userRoleDropdown = By.xpath("(//*[@class=\"oxd-select-text-input\"])[1]");
    private final By statusDropdown = By.xpath("(//*[@class=\"oxd-select-text-input\"])[2]");
    private final By employeeNameInput = By.cssSelector("input[placeholder=\"Type for hints...\"]");
    private final By usernameInput = By.xpath("//input[@autocomplete=\"off\" and not(@type=\"password\")]");
    private final By passwordInput = By.xpath("(//input[@type=\"password\"])[1]");
    private final By confirmPasswordInput = By.xpath("(//input[@type=\"password\"])[2]");
    private final By saveButton = By.cssSelector("button[type=\"submit\"]");
    private final By cancelButton = By.cssSelector("button.oxd-button--ghost");
    private final By employeeNameSuggestion = By.cssSelector("[role=\"option\"]:not(:has-text(\"Searching\"))");
    private final By employeeFirstSuggestion = By.xpath("(//div[@role='option'])[1]");

    public AddEmployeePage(org.openqa.selenium.WebDriver driver) {
        super(driver);
    }

    public void selectUserRole(String role) {
        clickOnElement(userRoleDropdown);
        By roleOption = By.xpath("//div[@role='option']//span[text()='" + role + "']");
        clickOnElement(roleOption);
    }

    public void selectStatus(String status) {
        clickOnElement(statusDropdown);
        By statusOption = By.xpath("//div[@role='option']//span[text()='" + status + "']");
        clickOnElement(statusOption);
    }

    public void enterEmployeeName(String name) {
        sendData(employeeNameInput, name);
        driverActions.waits().waitForElementToBeVisible(employeeFirstSuggestion, 60, 500);
        String searching = "Searching";
        if (driverActions.elements().getText(employeeFirstSuggestion).contains(searching)){
            try {
                driverActions.waits().waitForElementToBeVisible(employeeNameSuggestion, 5, 500);
            } catch (Exception e) {
                System.out.println("Suggestion did not appear: " + e.getMessage());
            }
        }
        clickOnElement(employeeFirstSuggestion);
    }

    public void enterUsername(String username) {
        sendData(usernameInput, username);
    }

    public void enterPassword(String password) {
        sendData(passwordInput, password);
    }

    public void enterConfirmPassword(String password) {
        sendData(confirmPasswordInput, password);
    }

    public void clickSaveButton() {
        clickOnElement(saveButton);
    }

    public void clickCancelButton() {
        clickOnElement(cancelButton);
    }

    public void fillInEmployeeDetails(String userRole, String employeeName, String status, String username, String password) {
        selectUserRole(userRole);
        enterEmployeeName(employeeName);
        selectStatus(status);
        enterUsername(username);
        enterPassword(password);
        enterConfirmPassword(password);
    }

}
