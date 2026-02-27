package tests;

import base.BaseTest;
import org.junit.jupiter.api.*;
import pages.DashboardPage;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.*;

public class DashboardTest extends BaseTest {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;

    @BeforeEach
    public void setupTest() {
        // BaseTest already created: page = browser.newPage();

        loginPage = new LoginPage(page, config);
        loginPage.openLoginPage();
        loginPage.loginWithValidUser();
        loginPage.waitForDashboard();

        dashboardPage = new DashboardPage(page, config);
    }

    @Test
    @DisplayName("PIM menu visible after login")
    public void testPimMenuVisible() {
        assertTrue(dashboardPage.isPimMenuVisible(), "PIM menu should be visible after login");
    }

    @Test
    @DisplayName("Navigate to Employee List page via PIM")
    public void testNavigateToEmployeeList() {
        dashboardPage.goToEmployeeList();
        assertTrue(dashboardPage.isOnEmployeeListPage(), "Should land on Employee List page");
    }
}
