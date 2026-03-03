package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import utils.ConfigReader;

public class EmployeeManagementPage {

    private final Page page;
    private final ConfigReader config;

    public EmployeeManagementPage(Page page, ConfigReader config) {
        this.page = page;
        this.config = config;
    }

    // ---------- Navigation ----------

    public void openEmployeeListPage() {
        page.navigate(config.getEmployeeListUrl());
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.waitForURL("**/pim/viewEmployeeList**",
                new Page.WaitForURLOptions().setTimeout(config.getNavigationTimeout()));
        waitForResultsArea();
    }

    public void openAddEmployeePage() {
        page.navigate(config.getEmployeeManagementUrl()); // /pim/addEmployee
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.waitForURL("**/pim/addEmployee**",
                new Page.WaitForURLOptions().setTimeout(config.getNavigationTimeout()));
    }

    // ---------- Add Employee (Create) ----------
    // IMPORTANT: keep this method NEUTRAL (no waitForURL), so it works for both positive & negative
    public void addEmployee(String firstName, String lastName, String employeeId) {

        Locator firstNameInput = page.locator("input[name='firstName']");
        Locator lastNameInput = page.locator("input[name='lastName']");
        Locator employeeIdInput = inputByLabel("Employee Id").first();
        Locator saveBtn = page.locator("button:has-text('Save')");

        firstNameInput.waitFor(new Locator.WaitForOptions().setTimeout(config.getTimeout()));

        firstNameInput.fill(firstName);
        lastNameInput.fill(lastName);

        employeeIdInput.click();
        employeeIdInput.press("Control+A");
        employeeIdInput.fill(employeeId);

        saveBtn.click();
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
    }

    // ---------- Success / Error Wait Helpers ----------
    public void waitForPersonalDetailsPage() {
        page.waitForURL("**/pim/viewPersonalDetails/**",
                new Page.WaitForURLOptions().setTimeout(config.getNavigationTimeout()));
    }

    public void waitForDuplicateEmployeeIdError() {
        Locator errorMsg = duplicateEmployeeIdErrorLocator();
        errorMsg.waitFor(new Locator.WaitForOptions().setTimeout(config.getTimeout()));
    }

    // ---------- Search in Employee List ----------
    public void searchByEmployeeId(String employeeId) {
        goToEmployeeListIfNeeded();

        Locator empIdFilter = inputByLabel("Employee Id").first();

        empIdFilter.waitFor(new Locator.WaitForOptions().setTimeout(config.getTimeout()));
        empIdFilter.click();
        empIdFilter.press("Control+A");
        empIdFilter.fill(employeeId);

        Locator searchBtn = page.locator("button:has-text('Search')");
        searchBtn.click();

        waitForResultsArea();
        page.waitForTimeout(500);
    }

    public boolean isEmployeeInList(String employeeId) {
        Locator noRecords = page.locator(".oxd-table-body").locator("text=No Records Found");
        if (noRecords.count() > 0 && noRecords.isVisible()) return false;

        Locator cell = page.locator(".oxd-table-body").locator("text=" + employeeId).first();
        return cell.count() > 0 && cell.isVisible();
    }

    // ---------- Edit Employee ----------
    public void editEmployeeName(String employeeId, String newFirstName, String newLastName) {
        searchByEmployeeId(employeeId);

        Locator row = rowByEmployeeId(employeeId);
        Locator editBtn = row.locator("button:has(i.bi-pencil-fill)").first();
        editBtn.click();

        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
        page.waitForURL("**/pim/viewPersonalDetails/**",
                new Page.WaitForURLOptions().setTimeout(config.getNavigationTimeout()));

        Locator firstNameInput = page.locator("input[name='firstName']");
        Locator lastNameInput = page.locator("input[name='lastName']");

        firstNameInput.waitFor(new Locator.WaitForOptions().setTimeout(config.getTimeout()));

        firstNameInput.click();
        firstNameInput.press("Control+A");
        firstNameInput.fill(newFirstName);

        lastNameInput.click();
        lastNameInput.press("Control+A");
        lastNameInput.fill(newLastName);

        page.locator("button:has-text('Save')").first().click();
        page.waitForTimeout(500);
    }

    // ---------- Delete Employee ----------
    public void deleteEmployee(String employeeId) {
        searchByEmployeeId(employeeId);

        Locator rows = page.locator(".oxd-table-row");
        rows.first().waitFor(new Locator.WaitForOptions().setTimeout(config.getTimeout()));

        Locator row = rowByEmployeeId(employeeId);
        Locator deleteBtn = row.locator("button i.bi-trash").first();
        deleteBtn.click();

        Locator confirmDelete = page.locator("button:has-text('Yes, Delete')");
        confirmDelete.waitFor(new Locator.WaitForOptions().setTimeout(config.getTimeout()));
        confirmDelete.click();

        page.waitForTimeout(800);
        waitForResultsArea();
    }

    // ---------- Duplicate Error ----------
    public boolean isDuplicateEmployeeIdErrorVisible() {
        Locator errorMsg = duplicateEmployeeIdErrorLocator();
        return errorMsg.count() > 0 && errorMsg.isVisible();
    }

    private Locator duplicateEmployeeIdErrorLocator() {
        return page.locator("span:has-text('Employee Id already exists')");
    }

    // ---------- Helpers ----------
    private void goToEmployeeListIfNeeded() {
        if (!page.url().contains("/pim/viewEmployeeList")) {
            openEmployeeListPage();
        }
    }

    private Locator inputByLabel(String labelText) {
        return page.locator(".oxd-input-group")
                .filter(new Locator.FilterOptions().setHasText(labelText))
                .locator("input");
    }

    private Locator rowByEmployeeId(String employeeId) {
        return page.locator(".oxd-table-body .oxd-table-row")
                .filter(new Locator.FilterOptions().setHasText(employeeId))
                .first();
    }

    public void waitForResultsArea() {
        Locator rows = page.locator(".oxd-table-row");
        Locator noRecords = page.locator("text=No Records Found");

        page.waitForTimeout(400);

        if (rows.count() > 0) {
            rows.first().waitFor(new Locator.WaitForOptions().setTimeout(config.getTimeout()));
        } else if (noRecords.count() > 0) {
            noRecords.first().waitFor(new Locator.WaitForOptions().setTimeout(config.getTimeout()));
        }
    }
}
