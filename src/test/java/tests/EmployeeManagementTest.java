package tests;

import base.BaseTest;
import org.junit.jupiter.api.*;
import pages.EmployeeManagementPage;
import pages.LoginPage;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeManagementTest extends BaseTest {

    private LoginPage loginPage;
    private EmployeeManagementPage empPage;

    @BeforeEach
    public void setupTest() {
        // BaseTest already created: page = browser.newPage();

        loginPage = new LoginPage(page, config);
        loginPage.openLoginPage();
        loginPage.loginWithValidUser();

        empPage = new EmployeeManagementPage(page, config);
    }

    @Test
    @DisplayName("Add new employee")
    public void testAddEmployee() {
        String empId = generateNumericEmpId();

        empPage.openAddEmployeePage();
        empPage.addEmployee("John", "Doe", empId);

        // Positive expectation
        empPage.waitForPersonalDetailsPage();

        assertTrue(page.url().contains("/pim/viewPersonalDetails"),
                "Should navigate to Personal Details after saving employee");
    }

    @Test
    @DisplayName("Edit existing employee")
    public void testEditEmployee() {
        String empId = generateNumericEmpId();

        empPage.openAddEmployeePage();
        empPage.addEmployee("John", "Doe", empId);
        empPage.waitForPersonalDetailsPage();

        assertTrue(empPage.waitUntilEmployeeAppearsInList(empId),
                "Employee should exist after creation");

        empPage.editEmployeeName(empId, "Jane", "Smith");

        assertTrue(empPage.waitUntilEmployeeAppearsInList(empId),
                "Employee should exist after creation");
    }

    @Test
    @DisplayName("Delete employee")
    public void testDeleteEmployee() {
        String empId = generateNumericEmpId();

        empPage.openAddEmployeePage();
        empPage.addEmployee("John", "Doe", empId);
        empPage.waitForPersonalDetailsPage();

        assertTrue(empPage.waitUntilEmployeeAppearsInList(empId),
                "Employee should exist after creation");

        empPage.deleteEmployee(empId);

        empPage.searchByEmployeeId(empId);
        assertFalse(empPage.isEmployeeInList(empId), "Employee should be removed after deletion");
    }

    @Test
    @DisplayName("Add employee with duplicate Employee ID (Negative)")
    public void testAddEmployeeWithDuplicateId() {
        String empId = generateNumericEmpId();

        // First creation (positive)
        empPage.openAddEmployeePage();
        empPage.addEmployee("John", "Doe", empId);
        empPage.waitForPersonalDetailsPage();

        assertTrue(empPage.waitUntilEmployeeAppearsInList(empId),
                "Employee should exist after creation");

        // Second creation with same ID (negative)
        empPage.openAddEmployeePage();
        empPage.addEmployee("Jane", "Smith", empId);

        // Negative expectation (no navigation)
        empPage.waitForDuplicateEmployeeIdError();

        assertTrue(empPage.isDuplicateEmployeeIdErrorVisible(),
                "Duplicate employee ID error should be displayed");
    }

    private String generateNumericEmpId() {
        return String.valueOf(System.currentTimeMillis()).substring(5);
    }
}
