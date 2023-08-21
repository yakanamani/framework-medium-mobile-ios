package FrameworkMediumIos.stepdef;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import FrameworkMediumIos.pages.LoginPage;

public class LoginStepDef {
    LoginPage loginPage = new LoginPage();
    @And("I enter password as {string}")
    public void iEnterPasswordAs(String password) {
        loginPage.enterPassword(password);

    }

    @When("I enter username as {string}")
    public void iEnterUsernameAs(String username) throws InterruptedException {
        loginPage.enterUserName(username);
    }

    @And("I login")
    public void iLogin() {
        loginPage.pressLoginBtn();
    }

    @Then("login should fail with an error {string}")
    public void loginShouldFailWithAnError(String err) {
        Assert.assertTrue(loginPage.getErrTxt().equalsIgnoreCase(err));

    }
    @Given("I'm logged in")
    public void iMLoggedIn() throws InterruptedException {
        loginPage.login("standard_user", "secret_sauce");
    }
}
