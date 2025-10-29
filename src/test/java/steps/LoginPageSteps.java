package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import static hooks.Hooks.loginPage;

public class LoginPageSteps {

    @Given("user navigates to {string}")
    public void user_navigates_to(String url) {
        loginPage.navigateTo(url);
    }

    @When("user enters username {string} and password {string}")
    public void the_user_logs_in_with_username_and_password(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }
    @When("user clicks on the login button")
    public void the_user_clicks_on_the_login_button() {
        loginPage.clickLogin();
    }
}
