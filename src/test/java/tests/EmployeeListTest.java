package tests;

import base.BaseTest;
import org.junit.jupiter.api.*;
import pages.DashboardPage;
import pages.EmployeeListPage;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeListTest extends BaseTest {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private EmployeeListPage employeeListPage;

    @BeforeEach
    public void setupTest() {
        // BaseTest already created: page = browser.newPage();

        // Login
        loginPage = new LoginPage(page, config);
        loginPage.openLoginPage();
        loginPage.loginWithValidUser();
        loginPage.waitForDashboard();

        // Navigate to Employee List via UI (recommended)
        dashboardPage = new DashboardPage(page, config);
        dashboardPage.goToEmployeeList();

        employeeListPage = new EmployeeListPage(page, config);
    }

    @Test
    @DisplayName("Search employee by name (basic)")
    public void testSearchEmployee() {

        // Broad search because demo data can change
        employeeListPage.searchEmployee("a");

        String firstName = employeeListPage.getFirstEmployeeName();
        assertNotNull(firstName);
        assertFalse(firstName.trim().isEmpty(), "First row employee name should not be empty");
    }
}
