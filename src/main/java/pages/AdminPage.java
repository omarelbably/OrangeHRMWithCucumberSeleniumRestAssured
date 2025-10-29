package pages;

import org.openqa.selenium.By;

public class AdminPage extends BasePage {
    private final By totalRecordsLocator = By.cssSelector("[role=row]");
    private final By addEmployeeBtn = By.cssSelector("[class=\"oxd-icon bi-plus oxd-button-icon\"]");
    private final By systemUsersDropdownBtn = By.cssSelector("[class=\"oxd-icon bi-caret-down-fill\"]");
    private final By usernameSearchField = By.xpath("(//*[@class=\"oxd-input-group oxd-input-field-bottom-space\"]//input)[1]");
    private final By searchBtn = By.cssSelector("[class=\"oxd-button oxd-button--medium oxd-button--secondary orangehrm-left-space\"]");
    private final By deleteRecordBtn = By.cssSelector(".oxd-table-cell-actions button:first-child");
    private final By confirmDeleteBtn = By.xpath("//i[@class=\"oxd-icon bi-trash oxd-button-icon\"]/..");
    private final By resetSearchBtn = By.cssSelector("[class=\"oxd-button oxd-button--medium oxd-button--ghost\"]");

    public AdminPage(org.openqa.selenium.WebDriver driver) {
        super(driver);
    }
    public int getTotalRecords() {
        return driverActions.elements().getElements(totalRecordsLocator, 60, 500).size() - 1;
    }
    public void clickOnAddEmployeeBtn() {
        clickOnElement(addEmployeeBtn);
    }
    public void clickOnSystemUsersDropdownBtn() {
        try{
            driverActions.elements().clickOnElement(systemUsersDropdownBtn, 1, 500);
        } catch (Exception e){
            System.out.println("System Users dropdown was already clicked");
        }
    }
    public void enterUsernameInSearchField(String username) {
        sendData(usernameSearchField, username);
    }
    public void clickOnSearchBtn() {
        clickOnElement(searchBtn);
    }
    public void clickOnDeleteRecordBtn() {
        clickOnElement(deleteRecordBtn);
    }
    public void clickOnResetSearchBtn() {
        clickOnElement(resetSearchBtn);
    }
    public void clickOnConfirmDeleteAction() {
        clickOnElement(confirmDeleteBtn);
    }
}
