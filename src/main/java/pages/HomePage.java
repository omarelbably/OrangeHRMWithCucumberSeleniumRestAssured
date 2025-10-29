package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private final By sideMenuBtn = By.cssSelector("[class=\"oxd-icon bi-list oxd-topbar-header-hamburger\"]");
    private final By adminPageBtn = By.cssSelector("[href=\"/web/index.php/admin/viewAdminModule\"]");
    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void clickOnSideMenu() {
        try{
        driverActions.waits().waitForElementToBeVisible(sideMenuBtn, 5, 500);
        if (driverActions.findWebElement(sideMenuBtn).isDisplayed()) {
            clickOnElement(sideMenuBtn);
        }else{
            System.out.println("Side menu button is not displayed");
        }
        }catch (Exception e){
            System.out.println("Side menu button is not displayed");
        }
    }
    public void clickOnAdminPageBtn() {
        clickOnElement(adminPageBtn);
    }
}
