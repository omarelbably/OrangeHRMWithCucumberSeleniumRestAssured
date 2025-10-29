package hooks;
import Ellithium.core.driver.DriverFactory;
import Ellithium.core.driver.HeadlessMode;
import Ellithium.core.driver.LocalDriverType;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import pages.AddEmployeePage;
import pages.AdminPage;
import pages.HomePage;
import pages.LoginPage;


public class Hooks {
    public static int totalRecords = 0;
    public static LoginPage loginPage;
    public static HomePage homePage;
    public static AdminPage adminPage;
    public static AddEmployeePage addEmployeePage;
    @Before("@E2E")
    public void setupDriver() {
        DriverFactory.getNewLocalDriver(LocalDriverType.Chrome, HeadlessMode.True);
        loginPage = new LoginPage(DriverFactory.getCurrentDriver());
        homePage = new HomePage(DriverFactory.getCurrentDriver());
        adminPage = new AdminPage(DriverFactory.getCurrentDriver());
        addEmployeePage = new AddEmployeePage(DriverFactory.getCurrentDriver());
    }



    @After("@E2E")
    public void tareDown() {
        DriverFactory.quitDriver();
    }
}

