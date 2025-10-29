package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class LoginPage extends BasePage {
    By usernameField = By.cssSelector("[placeholder=\"Username\"]");
    By passwordField = By.cssSelector("[placeholder=\"Password\"]");
    By loginButton = By.cssSelector("[class=\"oxd-button oxd-button--medium oxd-button--main orangehrm-login-button\"]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo(String url) {
        driverActions.navigation().navigateToUrl(url);
    }

    public void enterUsername(String username) {
        sendData(usernameField, username);
    }

    public void enterPassword(String password) {
        sendData(passwordField, password);
    }

    public void clickLogin() {
        clickOnElement(loginButton);
    }
}
