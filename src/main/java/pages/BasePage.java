package pages;

import Ellithium.Utilities.interactions.DriverActions;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
public class BasePage {
    WebDriver driver;
    DriverActions driverActions;
    public static Cookie csrfCookie;
    public BasePage(WebDriver driver) {
        this.driver=driver;
        driverActions=new DriverActions(driver);
    }

    protected void clickOnElement(By locator) {
        driverActions.elements().clickOnElement(locator,60, 500);
    }
    protected void sendData(By locator, String text){
        driverActions.elements().sendData(locator, text, 60, 500);
    }
}
