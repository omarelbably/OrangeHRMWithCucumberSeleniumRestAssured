package steps;

import io.cucumber.java.en.When;

import static hooks.Hooks.homePage;

public class HomePageSteps {
    @When("user clicks on the side menu button")
    public void user_clicks_on_the_side_menu_button() {
        homePage.clickOnSideMenu();
    }
    @When("user clicks on the admin page button")
    public void user_clicks_on_admin_page_button() {
        homePage.clickOnAdminPageBtn();
    }

}
