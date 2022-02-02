package base;

import Driver.DriverFactory;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class TestUtil {
    public WebDriver driver;
    private String applicationUrl,browser;
    private int implicitWaitSeconds;

    @DataProvider(name = "usersCsv")
    public static Object[][] readUsersFromCsv() throws IOException, CsvException {
        try (CSVReader csvReader = new CSVReader(new FileReader("src/test/resources/userList.csv.csv"))) {
            List<String[]> csvData = csvReader.readAll();
            Object[][] csvDataObject = new Object[csvData.size()][2];
            for (int i = 0; i < csvData.size(); i++) {
                csvDataObject[i] = csvData.get(i);
            }
            return csvDataObject;
        }
    }
    @BeforeMethod
    public void setUp() {
        setupBrowserDriver();
        loadInitialPage();
    }

    private void loadInitialPage() {driver.get(applicationUrl);}

    @AfterMethod

    public void tearDown() {
        driver.quit();

    }

    private void setupBrowserDriver() {
        try {
            FileInputStream configFile = new
                    FileInputStream("src/test/resources/config.properties");
            Properties config = new Properties();
            config.load(configFile);
            applicationUrl = config.getProperty("url");
            //implicitWaitSeconds = Integer.parseInt(config.getProperty("implicitWait"));
            browser = config.getProperty("targetBrowser");

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        switch (browser){
            case "chrome":
                driver = DriverFactory.getChromeDriver();
                break;
            case "firefox":
                driver = DriverFactory.getFireFoxDriver();
                break;
        }
    }
}
