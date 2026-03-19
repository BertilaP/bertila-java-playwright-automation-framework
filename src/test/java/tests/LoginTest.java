package tests;

import base.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeEach
    void setupTest() {
        loginPage = new LoginPage(page, config);
        loginPage.openLoginPage();
    }

    @ParameterizedTest(name = "Login with username=''{0}'' and expected result=''{2}''")
    @DisplayName("Login scenarios using external CSV DDT")
    @CsvFileSource(resources = "/testdata/login-data.csv", numLinesToSkip = 1)
    void shouldValidateLoginScenarios(String username, String password, String expectedResult) {

        loginPage.login(username, password);

        if (expectedResult.equalsIgnoreCase("success")) {
            loginPage.waitForDashboard();

            assertTrue(loginPage.isDashboardVisible(),
                    "Dashboard header should be visible after valid login");

        } else if (expectedResult.equalsIgnoreCase("invalid_credentials")) {
            String error = loginPage.getErrorMessage();

            assertFalse(error == null || error.trim().isEmpty(),
                    "Error message should be displayed for invalid login");

            assertTrue(error.toLowerCase().contains("invalid"),
                    "Error message should mention 'invalid'");

        } else if (expectedResult.equalsIgnoreCase("required_fields")) {
            assertTrue(loginPage.areRequiredFieldMessagesVisible(),
                    "Required field validation message should be displayed for blank login fields");
        }
    }
}