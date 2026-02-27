package utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private final Properties prop;

    public ConfigReader() {
        prop = new Properties();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found in src/test/resources");
            }
            prop.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    // Small helper: system property overrides config file (useful for CI)
    private String getValue(String key) {
        String sysValue = System.getProperty(key);
        if (sysValue != null && !sysValue.trim().isEmpty()) {
            return sysValue.trim();
        }
        String fileValue = prop.getProperty(key);
        return fileValue != null ? fileValue.trim() : null;
    }

    // URLs
    public String getBaseUrl() {

        return getValue("base.url");
    }

    public String getDashboardUrl() {

        return getValue("dashboard.url");
    }

    public String getEmployeeListUrl() {

        return getValue("employeeList.url");
    }

    public String getEmployeeManagementUrl() {

        return getValue("employeeManagement.url");
    }

    // Browser
    public String getBrowser() {
        String browser = getValue("browser");
        return browser != null ? browser : "chromium";
    }

    // Headless
    public boolean isHeadless() {
        String headless = getValue("headless");
        return headless != null ? Boolean.parseBoolean(headless) : true; // default true (safer for CI)
    }

    // Timeouts
    public int getTimeout() {
        String t = getValue("timeout");
        return (t != null) ? Integer.parseInt(t) : 10000;
    }

    public int getNavigationTimeout() {
        String t = getValue("navigation.timeout");
        return (t != null) ? Integer.parseInt(t) : 20000;
    }

    // Environment
    public String getEnv() {
        String env = getValue("env");
        return env != null ? env : "qa";
    }

    // Valid credentials
    public String getValidUsername() {
        return getValue("valid.username");
    }

    public String getValidPassword() {
        return getValue("valid.password");
    }

    // Invalid credentials
    public String getInvalidUsername() {
        return getValue("invalid.username");
    }

    public String getInvalidPassword() {
        return getValue("invalid.password");
    }
}
